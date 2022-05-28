package com.fooddelivery.repository;

import com.fooddelivery.entities.OrderEntity;
import com.fooddelivery.entities.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemsEntity, Long> {

    List<OrderItemsEntity> findByOrderEntity(OrderEntity orderEntity);
}
