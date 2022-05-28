package com.fooddelivery.service.impl;

import com.fooddelivery.constants.CommandStatus;
import com.fooddelivery.constants.Constants;
import com.fooddelivery.entities.*;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.request.OrderCreationRequest;
import com.fooddelivery.response.BaseResponse;
import com.fooddelivery.response.GenericListResponse;
import com.fooddelivery.response.OrderCreationResponse;
import com.fooddelivery.response.OrderDetailsResponse;
import com.fooddelivery.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepo;

    private UserService userService;
    private RestaurantService restaurantService;
    private AddressService addressService;
    private MenuItemService menuItemService;
    private OrderItemService orderItemService;
    private UserWalletService userWalletService;
    private RestaurantWalletService restaurantWalletService;
    private UserWalletTransactionService userWalletTransactionService;
    private RestaurantWalletTransactionService restaurantWalletTransactionService;

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final String classTag = this.getClass().getSimpleName();

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepo,
                            UserService userService,
                            RestaurantService restaurantService,
                            AddressService addressService,
                            MenuItemService menuItemService,
                            OrderItemService orderItemService,
                            UserWalletService userWalletService,
                            RestaurantWalletService restaurantWalletService,
                            UserWalletTransactionService userWalletTransactionService,
                            RestaurantWalletTransactionService restaurantWalletTransactionService) {
        this.orderRepo = orderRepo;

        this.userService = userService;
        this.restaurantService = restaurantService;
        this.addressService = addressService;
        this.menuItemService = menuItemService;
        this.orderItemService = orderItemService;
        this.userWalletService = userWalletService;
        this.restaurantWalletService = restaurantWalletService;
        this.userWalletTransactionService = userWalletTransactionService;
        this.restaurantWalletTransactionService = restaurantWalletTransactionService;
    }


    @Transactional
    @Override
    public OrderCreationResponse createOrder(OrderCreationRequest orderCreationRequest) {
        try {

            Long userId = orderCreationRequest.getUserId();
            Long addressId = orderCreationRequest.getAddressId();
            Long restaurantId = orderCreationRequest.getRestaurantId();
            List<OrderCreationRequest.MenuItemDetails> menuItemDetails = orderCreationRequest.getItemDetails();

            if (userId == null || addressId == null || restaurantId == null || menuItemDetails == null || menuItemDetails.isEmpty()) {
                return new OrderCreationResponse(false, CommandStatus.BAD_REQUEST);
            }

            HashMap<Long, Integer> itemsToServingsMap = new HashMap<>();
            BigDecimal totalItemsValue = BigDecimal.ZERO;
            List<OrderItemsEntity> orderItemsEntities = new ArrayList<>();

            for (OrderCreationRequest.MenuItemDetails itemDetails : menuItemDetails) {
                itemsToServingsMap.put(itemDetails.getItemId(), itemDetails.getServing());
            }

            List<Long> menuItemIds = menuItemDetails.stream()
                    .map(OrderCreationRequest.MenuItemDetails::getItemId)
                    .collect(Collectors.toList());

            Optional<UserEntity> userEntity = userService.getUserById(userId);
            Optional<RestaurantEntity> restaurantEntity = restaurantService.getRestaurantById(restaurantId);
            Optional<AddressEntity> addressEntity = addressService.getAddressById(addressId);
            List<MenuItemEntity> menuItemEntities = menuItemService.findByMenuItemIds(menuItemIds);

            if (!userEntity.isPresent() || !restaurantEntity.isPresent() || !addressEntity.isPresent() ||
                    menuItemEntities == null || menuItemEntities.isEmpty()) {
                return new OrderCreationResponse(false, CommandStatus.BAD_REQUEST);
            }

            for (MenuItemEntity menuItemEntity : menuItemEntities) {

                // checking if all the items belong to the same restaurant or not.
                if (!menuItemEntity.getRestaurantEntity().getId().equals(restaurantId)) {
                    return new OrderCreationResponse(false, CommandStatus.WRONG_RESTAURANT);
                }

                Integer serving = itemsToServingsMap.get(menuItemEntity.getId());
                if (serving != null) {
                    BigDecimal totalItemPrice = menuItemEntity.getItemPrice().multiply(BigDecimal.valueOf(serving));
                    totalItemsValue = totalItemsValue.add(totalItemPrice);

                    OrderItemsEntity orderItemsEntity = new OrderItemsEntity();
                    orderItemsEntity.setMenuItemEntity(menuItemEntity);
                    orderItemsEntity.setServing(serving);
                    orderItemsEntity.setPrice(totalItemPrice);

                    orderItemsEntities.add(orderItemsEntity);
                }
            }

            UserWalletEntity userWalletEntity = userWalletService.getUserWalletDetails(userEntity.get());

            if (userWalletEntity.getBalance().subtract(totalItemsValue).compareTo(BigDecimal.ZERO) < 0) {
                return new OrderCreationResponse(false, CommandStatus.INSUFFICIENT_BALANCE);
            }

            BigDecimal deliveryFee = BigDecimal.valueOf(0);
            BigDecimal serviceFee = BigDecimal.valueOf(0);
            BigDecimal discountAmount = BigDecimal.valueOf(0);

            BigDecimal finalOrderAmount = totalItemsValue.add(deliveryFee).add(serviceFee).subtract(discountAmount);

            OrderEntity orderEntity = new OrderEntity();

            orderEntity.setUserEntity(userEntity.get());
            orderEntity.setRestaurantEntity(restaurantEntity.get());
            orderEntity.setAddressEntity(addressEntity.get());
            orderEntity.setOrderTimeEpoch(System.currentTimeMillis());
            orderEntity.setIsActive(true);
            orderEntity.setStatus(Constants.ORDER_STATUS_PLACED);
            orderEntity.setDeliveryFee(deliveryFee);
            orderEntity.setServiceFee(serviceFee);
            orderEntity.setDiscountAmount(discountAmount);
            orderEntity.setEstimatedTimeArrival(null);
            orderEntity.setActualDeliveryTime(null);
            orderEntity.setItemsCost(totalItemsValue);
            orderEntity.setFinalOrderAmount(finalOrderAmount); // since the delivery fee, service fee and discount amount are assumed to be zero.

            orderEntity = orderRepo.save(orderEntity);

            OrderEntity finalOrderEntity = orderEntity;
            orderItemsEntities.forEach(orderItemsEntity -> orderItemsEntity.setOrderEntity(finalOrderEntity));

            orderItemService.saveAll(orderItemsEntities);
            userWalletService.blockAmount(userId, totalItemsValue);

//            MessagingUtil.notifyRestaurantAboutOrder(restaurantEntity.get(), orderEntity);
            logger.info("Notifying Restaurant about order Id " + orderEntity.getId());

            OrderCreationResponse response = new OrderCreationResponse(true, CommandStatus.SUCCESS);
            response.setOrderId(orderEntity.getId());
            response.setOrderStatus(orderEntity.getStatus());

            return response;
        } catch (Exception e) {
            logger.error("[" + classTag + "] [createOrder] error is : " + e.getMessage());
            return new OrderCreationResponse(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GenericListResponse<OrderEntity> getOrdersForUser(Long userId, Integer pageNo, Integer pageSize) {
        try {

            if (userId == null) {
                return new GenericListResponse<>(false, CommandStatus.BAD_REQUEST);
            }

            pageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
            pageSize = (pageSize == null || pageSize < 1) ? 1 : pageSize;

            Pageable pageable = PageRequest.of(pageNo - 1, pageSize); // since pageNo is 1 based indexed.

            Page<OrderEntity> orderEntities = orderRepo.getOrdersForUser(userId, pageable);
            if (!orderEntities.hasContent()) {
                return new GenericListResponse<>(true, CommandStatus.SUCCESS);
            }

            GenericListResponse<OrderEntity> response = new GenericListResponse<>(true, CommandStatus.SUCCESS);
            response.setData(orderEntities.toList());

            return response;
        } catch (Exception e) {

            logger.error("[" + classTag + "] [getOrdersForUser] error is : " + e.getMessage());
            return new GenericListResponse<>(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public OrderDetailsResponse getOrderDetails(Long orderId) {
        try {

            if (orderId == null) {
                return new OrderDetailsResponse(false, CommandStatus.BAD_REQUEST);
            }

            Optional<OrderEntity> orderEntity = orderRepo.findById(orderId);
            if (!orderEntity.isPresent()) {
                return new OrderDetailsResponse(false, CommandStatus.ORDER_NOT_FOUND);
            }

            List<OrderItemsEntity> orderItemsEntities = orderItemService.getOrderItemsByOrderId(orderEntity.get());

            List<OrderDetailsResponse.OrderItemInfo> orderItemInfoList = new ArrayList<>();

            for (OrderItemsEntity orderItemsEntity : orderItemsEntities) {
                OrderDetailsResponse.OrderItemInfo orderItemInfo = new OrderDetailsResponse.OrderItemInfo();
                orderItemInfo.setItemId(orderItemsEntity.getMenuItemEntity().getId());
                orderItemInfo.setItemName(orderItemsEntity.getMenuItemEntity().getItemName());
                orderItemInfo.setServing(orderItemsEntity.getServing());
                orderItemInfo.setItemPrice(orderItemsEntity.getPrice());

                orderItemInfoList.add(orderItemInfo);
            }

            OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse(true, CommandStatus.SUCCESS);

            orderDetailsResponse.setOrderEntity(orderEntity.get());
            orderDetailsResponse.setOrderItemsEntities(orderItemInfoList);

            return orderDetailsResponse;
        } catch (Exception e) {
            logger.error("[" + classTag + "] [getOrderDetails] error is : " + e.getMessage());
            return new OrderDetailsResponse(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GenericListResponse<OrderEntity> getOrdersForRestaurant(Long restaurantId, Integer pageNo, Integer pageSize) {
        try {

            if (restaurantId == null) {
                return new GenericListResponse<>(false, CommandStatus.BAD_REQUEST);
            }

            pageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
            pageSize = (pageSize == null || pageSize < 1) ? 1 : pageSize;

            Pageable pageable = PageRequest.of(pageNo - 1, pageSize); // since pageNo is 1 based indexed.

            Page<OrderEntity> orderEntities = orderRepo.getOrdersForRestaurant(restaurantId, pageable);
            if (!orderEntities.hasContent()) {
                return new GenericListResponse<>(true, CommandStatus.SUCCESS);
            }

            GenericListResponse<OrderEntity> response = new GenericListResponse<>(true, CommandStatus.SUCCESS);
            response.setData(orderEntities.toList());

            return response;
        } catch (Exception e) {
            logger.error("[" + classTag + "] [getOrdersForRestaurant] error is : " + e.getMessage());
            return new GenericListResponse<>(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public BaseResponse updateOrderStatus(Long orderId, Integer updatedOrderStatus, Long userId) {
        try {

            Optional<OrderEntity> orderEntity = orderRepo.findById(orderId);
            if (!orderEntity.isPresent()) {
                return new BaseResponse(false, CommandStatus.BAD_REQUEST);
            }

            /* checking if the userId passed belongs to either the user who has ordered
            or the restaurant which the order belongs to. Will add the delivery person logic here
            also when he will be in the system. */

            if (orderEntity.get().getStatus().equals(updatedOrderStatus) ||
                    orderEntity.get().getStatus().equals(Constants.ORDER_STATUS_DELIVERED) ||
                    orderEntity.get().getStatus().equals(Constants.ORDER_STATUS_CANCELLED)) {

                return new BaseResponse(false, CommandStatus.CANNOT_UPDATE_ORDER_STATUS);
            }

            if (!orderEntity.get().getUserEntity().getId().equals(userId) &&
                 !orderEntity.get().getRestaurantEntity().getUserEntity().getId().equals(userId)) {
                return new BaseResponse(false, CommandStatus.UNAUTHORIZED);
            }

            // order can only be canceled if it is not confirmed.
            if (updatedOrderStatus.equals(Constants.ORDER_STATUS_CANCELLED)
                    && orderEntity.get().getStatus().equals(Constants.ORDER_STATUS_PLACED)) {
                    // then only order can be cancelled.
                    orderRepo.updateOrderStatus(orderId, updatedOrderStatus);
                    return new BaseResponse(true, CommandStatus.SUCCESS);
            }

            if (updatedOrderStatus.equals(Constants.ORDER_STATUS_CONFIRMED)) {
                Boolean payRestaurant = payRestaurantForOrder(orderEntity.get());
                if (!payRestaurant) {
                    return new BaseResponse(false, CommandStatus.INSUFFICIENT_BALANCE);
                }
            }

            if (updatedOrderStatus.equals(Constants.ORDER_STATUS_READY)) {
                updateOrderETA(orderEntity.get());
            }

            orderRepo.updateOrderStatus(orderId, updatedOrderStatus);

            return new BaseResponse(true, CommandStatus.SUCCESS);
        } catch (Exception e) {
            logger.error("[" + classTag + "] [updateOrderStatus] error is : " + e.getMessage());
            return new BaseResponse(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    private boolean payRestaurantForOrder(OrderEntity orderEntity) {
        // deducting the amount from the user Wallet and crediting the amount to the restaurant wallet
        try {
            BigDecimal orderAmount = orderEntity.getFinalOrderAmount();

            UserWalletEntity userWalletEntity = userWalletService.getUserWalletDetails(orderEntity.getUserEntity());

            // if blocked amount >= orderAmount
            if (userWalletEntity.getBlockedAmount().compareTo(BigDecimal.ZERO)> 0 &&
                    (userWalletEntity.getBlockedAmount().subtract(orderAmount).compareTo(BigDecimal.ZERO)) >= 0) {
                userWalletService.deductAmount(orderEntity.getUserEntity().getId(), orderAmount);
                restaurantWalletService.creditAmount(orderEntity.getRestaurantEntity().getId(), orderAmount);

                userWalletTransactionService.saveTransaction(userWalletEntity.getUserEntity(),
                        orderEntity, orderAmount, Constants.TRANSACTION_TYPE_DEBIT);
                restaurantWalletTransactionService.saveTransaction(orderEntity.getRestaurantEntity(), orderEntity,
                        orderAmount, Constants.TRANSACTION_TYPE_CREDIT);

                logger.info("Amount of " + orderAmount + " Deducted from User " + orderEntity.getUserEntity().getEmail() + "" +
                        " and credited to restaurant " + orderEntity.getRestaurantEntity().getName() +
                        " for Order Id = " + orderEntity.getId());

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private void updateOrderETA(OrderEntity orderEntity) {
        Integer eta = calculateETA(orderEntity);
        orderRepo.updateOrderETA(orderEntity.getId(), eta);

        logger.info("Notification Sent to User " + orderEntity.getUserEntity().getEmail() +
                " --> ETA -> " + eta);
    }

    private Integer calculateETA(OrderEntity orderEntity) {

        // hardcoding 10 minutes for now, as there is no information of delivery person in the system.
        return 10;

    }
}
