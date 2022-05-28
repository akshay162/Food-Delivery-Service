package com.fooddelivery.service;

import com.fooddelivery.entities.OrderEntity;
import com.fooddelivery.entities.OrderItemsEntity;

import java.util.List;

public interface OrderItemService {

    public List<OrderItemsEntity> saveAll(List<OrderItemsEntity> orderItemsEntityList);

    public List<OrderItemsEntity> getOrderItemsByOrderId(OrderEntity orderEntity);
}
