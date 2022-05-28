package com.fooddelivery.service.impl;

import com.fooddelivery.constants.CommandStatus;
import com.fooddelivery.entities.MenuItemEntity;
import com.fooddelivery.entities.RestaurantEntity;
import com.fooddelivery.entities.RestaurantWalletEntity;
import com.fooddelivery.entities.UserEntity;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.request.MenuRequest;
import com.fooddelivery.response.BaseResponse;
import com.fooddelivery.response.GenericListResponse;
import com.fooddelivery.response.RestaurantCreationResponse;
import com.fooddelivery.service.MenuItemService;
import com.fooddelivery.service.RestaurantService;
import com.fooddelivery.service.RestaurantWalletService;
import com.fooddelivery.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepo;
    private MenuItemService menuItemService;
    private UserService userService;
    private RestaurantWalletService restaurantWalletService;

    private final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);
    private final String classTag = this.getClass().getSimpleName();


    public RestaurantServiceImpl(RestaurantRepository restaurantRepo,
                                 MenuItemService menuItemService,
                                 UserService userService,
                                 RestaurantWalletService restaurantWalletService) {
        this.restaurantRepo = restaurantRepo;
        this.menuItemService = menuItemService;
        this.userService = userService;
        this.restaurantWalletService = restaurantWalletService;
    }

    @Override
    public Optional<RestaurantEntity> getRestaurantById(Long restaurantId) {
        return restaurantRepo.findById(restaurantId);
    }

    @Transactional
    @Override
    public RestaurantCreationResponse createRestaurant(String restaurantName, Long userId) {
        try {

            Optional<UserEntity> userEntity = userService.getUserById(userId);
            if (!userEntity.isPresent() || restaurantName.isEmpty()) {
                return new RestaurantCreationResponse(false, CommandStatus.USER_NOT_FOUND);
            }

            RestaurantEntity restaurantEntity = new RestaurantEntity();
            restaurantEntity.setName(restaurantName);
            restaurantEntity.setUserEntity(userEntity.get());
            restaurantEntity.setRating(0F);
            restaurantEntity.setTotalRatings(0L);

            restaurantEntity = restaurantRepo.save(restaurantEntity);

            RestaurantWalletEntity restaurantWalletEntity = new RestaurantWalletEntity();
            restaurantWalletEntity.setRestaurantEntity(restaurantEntity);
            restaurantWalletEntity.setBalance(BigDecimal.ZERO);

            restaurantWalletService.save(restaurantWalletEntity);

            return new RestaurantCreationResponse(true, CommandStatus.SUCCESS, restaurantEntity.getId());
        } catch (Exception e) {
            logger.error("[" + classTag + "][createRestaurant] error is : " + e.getMessage());
            return new RestaurantCreationResponse(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GenericListResponse<RestaurantEntity> getAllRestaurants(Integer pageNo, Integer pageSize) {
        try {

            pageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
            pageSize = (pageSize == null || pageSize < 5) ? 5 : pageSize;
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

            Page<RestaurantEntity> restaurantEntities = restaurantRepo.findAll(pageable);

            GenericListResponse<RestaurantEntity> response = new GenericListResponse<>(true, CommandStatus.SUCCESS);
            response.setData(restaurantEntities.toList());

            return response;
        } catch (Exception e) {
            logger.error("[" + classTag + "][getAllRestaurants] error is : " + e.getMessage());
            return new GenericListResponse<>(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GenericListResponse<MenuItemEntity> getMenuByRestaurant(Long restaurantId) {
        try {

            if (restaurantId == null) {
                return new GenericListResponse<>(false, CommandStatus.RESTAURANT_NOT_FOUND);
            }
            List<MenuItemEntity> menuItems = menuItemService.findByRestaurantId(restaurantId);

            return new GenericListResponse<>(true, CommandStatus.SUCCESS, menuItems);
        } catch (Exception e) {
            logger.error("[" + classTag + "][getMenuByRestaurant] error is : " + e.getMessage());
            return new GenericListResponse<>(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BaseResponse addMenu(MenuRequest addMenuRequest) {
        try {

            Long restaurantId = addMenuRequest.getRestaurantId();
            if (restaurantId == null || addMenuRequest.getMenuItemRequestList() == null || addMenuRequest.getMenuItemRequestList().isEmpty()) {
                return new BaseResponse(false, CommandStatus.BAD_REQUEST);
            }

            Optional<RestaurantEntity> restaurantEntity = restaurantRepo.findById(restaurantId);
            if (!restaurantEntity.isPresent()) {
                return new BaseResponse(false, CommandStatus.RESTAURANT_NOT_FOUND);
            }

            List<MenuItemEntity> menuItemEntities = new ArrayList<>();

            for (MenuRequest.MenuItemRequest itemRequest: addMenuRequest.getMenuItemRequestList()) {

                MenuItemEntity menuItemEntity = new MenuItemEntity();

                menuItemEntity.setItemName(itemRequest.getItemName());
                menuItemEntity.setItemDescription(itemRequest.getItemDescription());
                menuItemEntity.setItemPrice(itemRequest.getPrice());
                menuItemEntity.setDietType(itemRequest.getDietTypeId());
                menuItemEntity.setImageUrl(itemRequest.getImageUrl());
                menuItemEntity.setIsAvailable(itemRequest.getIsAvailable());
                menuItemEntity.setRestaurantEntity(restaurantEntity.get());

                menuItemEntities.add(menuItemEntity);
            }

            if (menuItemEntities.size() > 0) {
                menuItemService.saveAll(menuItemEntities);
            }

            return new BaseResponse(true, CommandStatus.SUCCESS);
        } catch (Exception e) {
            logger.error("[" + classTag + "][addMenu] error is : " + e.getMessage());
            return new BaseResponse(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BaseResponse editMenu(MenuRequest editMenuRequest) {
        try {
            Long restaurantId = editMenuRequest.getRestaurantId();
            if (restaurantId == null || editMenuRequest.getMenuItemRequestList() == null || editMenuRequest.getMenuItemRequestList().isEmpty()) {
                return new BaseResponse(false, CommandStatus.BAD_REQUEST);
            }

            Optional<RestaurantEntity> restaurantEntity = restaurantRepo.findById(restaurantId);
            if (!restaurantEntity.isPresent()) {
                return new BaseResponse(false, CommandStatus.RESTAURANT_NOT_FOUND);
            }

            HashMap<Long, MenuRequest.MenuItemRequest> itemRequestHashMap = new HashMap<>();

            for (MenuRequest.MenuItemRequest itemRequest : editMenuRequest.getMenuItemRequestList()) {
                itemRequestHashMap.put(itemRequest.getId(), itemRequest);
            }

            List<Long> menuItemIds = editMenuRequest.getMenuItemRequestList().stream()
                    .map(MenuRequest.MenuItemRequest::getId)
                    .collect(Collectors.toList());
            List<MenuItemEntity> menuItemsToEdit = menuItemService.findByMenuItemIds(menuItemIds);

            for (MenuItemEntity itemEntity: menuItemsToEdit) {
                MenuRequest.MenuItemRequest menuItemRequest = itemRequestHashMap.get(itemEntity.getId());
                if (menuItemRequest != null) {
                    if (menuItemRequest.getItemName() != null) {
                        itemEntity.setItemName(menuItemRequest.getItemName());
                    }
                    if (menuItemRequest.getItemDescription() != null) {
                        itemEntity.setItemDescription(menuItemRequest.getItemDescription());
                    }
                    if (menuItemRequest.getPrice() != null) {
                        itemEntity.setItemPrice(menuItemRequest.getPrice());
                    }

                    if (menuItemRequest.getImageUrl() != null) {
                        itemEntity.setImageUrl(menuItemRequest.getImageUrl());
                    }
                    if (menuItemRequest.getDietTypeId() != null) {
                        itemEntity.setDietType(menuItemRequest.getDietTypeId());
                    }
                    if (menuItemRequest.getIsAvailable() != null) {
                        itemEntity.setIsAvailable(menuItemRequest.getIsAvailable());
                    }
                }
            }

            if (menuItemsToEdit.size() > 0) {
                menuItemService.saveAll(menuItemsToEdit);
            }

            return new BaseResponse(true, CommandStatus.SUCCESS);
        } catch (Exception e) {
            logger.error("[" + classTag + "][editMenu] error is : " + e.getMessage());
            return new BaseResponse(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
