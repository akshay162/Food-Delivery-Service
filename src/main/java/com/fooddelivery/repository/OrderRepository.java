package com.fooddelivery.repository;

import com.fooddelivery.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "select o from OrderEntity o" +
            " where o.userEntity.id = :userId" +
            " order by o.orderTimeEpoch desc")
    Page<OrderEntity> getOrdersForUser(@RequestParam("userId") Long userId,
                                       Pageable pageable);

    @Query(value = "select o from OrderEntity o" +
            " where o.restaurantEntity.id = :restaurantId" +
            " order by o.orderTimeEpoch desc")
    Page<OrderEntity> getOrdersForRestaurant(@RequestParam("restaurantId") Long restaurantId,
                                       Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update orders" +
            " set status = :updatedStatus" +
            " where id = :orderId", nativeQuery = true)
    void updateOrderStatus(@RequestParam("orderId") Long orderId,
                           @RequestParam("updatedStatus") Integer updatedStatus);

    @Modifying
    @Transactional
    @Query(value = "update orders" +
            " set estimated_time_arrival = :eta" +
            " where id = :orderId", nativeQuery = true)
    void updateOrderETA(@RequestParam("orderId") Long orderId,
                           @RequestParam("eta") Integer eta);
}
