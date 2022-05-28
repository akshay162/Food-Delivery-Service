package com.fooddelivery.service;

import com.fooddelivery.entities.OrderEntity;
import com.fooddelivery.request.OrderCreationRequest;
import com.fooddelivery.response.BaseResponse;
import com.fooddelivery.response.GenericListResponse;
import com.fooddelivery.response.OrderCreationResponse;
import com.fooddelivery.response.OrderDetailsResponse;

public interface OrderService {

    public OrderCreationResponse createOrder(OrderCreationRequest orderCreationRequest);

    public GenericListResponse<OrderEntity> getOrdersForUser(Long userId, Integer pageNo, Integer pageSize);

    public OrderDetailsResponse getOrderDetails(Long orderId);

    public GenericListResponse<OrderEntity> getOrdersForRestaurant(Long restaurantId, Integer pageNo, Integer pageSize);

    public BaseResponse updateOrderStatus(Long orderId, Integer updatedOrderStatus, Long userId);
}
