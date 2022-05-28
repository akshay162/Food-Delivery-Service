package com.fooddelivery.service.impl;

import com.fooddelivery.entities.OrderEntity;
import com.fooddelivery.entities.OrderItemsEntity;
import com.fooddelivery.repository.OrderItemRepository;
import com.fooddelivery.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {


    private OrderItemRepository orderItemRepo;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepo) {
        this.orderItemRepo = orderItemRepo;
    }

    @Override
    public List<OrderItemsEntity> saveAll(List<OrderItemsEntity> orderItemsEntityList) {
        return orderItemRepo.saveAll(orderItemsEntityList);
    }

    @Override
    public List<OrderItemsEntity> getOrderItemsByOrderId(OrderEntity orderEntity) {
        return orderItemRepo.findByOrderEntity(orderEntity);
    }
}
