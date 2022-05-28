package com.fooddelivery.repository;

import com.fooddelivery.entities.RestaurantWalletTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantWalletTransactionRepository extends JpaRepository<RestaurantWalletTransactionEntity, Long> {
}
