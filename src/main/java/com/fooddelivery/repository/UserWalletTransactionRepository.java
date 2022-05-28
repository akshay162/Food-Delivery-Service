package com.fooddelivery.repository;

import com.fooddelivery.entities.UserWalletTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWalletTransactionRepository extends JpaRepository<UserWalletTransactionEntity, Long> {
}
