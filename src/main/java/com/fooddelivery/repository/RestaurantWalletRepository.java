package com.fooddelivery.repository;

import com.fooddelivery.entities.RestaurantWalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
public interface RestaurantWalletRepository extends JpaRepository<RestaurantWalletEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "update restaurant_wallet" +
            " set balance = balance + :amount" +
            " where restaurant_id = :restaurantId", nativeQuery = true)
    void creditAmount(@RequestParam("restaurantId") Long restaurantId,
                      @RequestParam("amount") BigDecimal amount);
}
