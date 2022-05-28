package com.fooddelivery.repository;

import com.fooddelivery.entities.UserEntity;
import com.fooddelivery.entities.UserWalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWalletEntity, Long> {

    UserWalletEntity findByUserEntity(UserEntity userEntity);

    @Modifying
    @Transactional
    @Query(value = "update user_wallet" +
            " set blocked_amount = blocked_amount - :amount" +
            " where user_id = :userId", nativeQuery = true)
    void deductAmount(@RequestParam("userId") Long userId,
                             @RequestParam("amount") BigDecimal amount);

    @Modifying
    @Transactional
    @Query(value = "update user_wallet" +
            " set blocked_amount = blocked_amount + :amount," +
            "  balance = balance - :amount" +
            " where user_id = :userId", nativeQuery = true)
    void blockAmount(@RequestParam("userId") Long userId,
                      @RequestParam("amount") BigDecimal amount);

    @Modifying
    @Transactional
    @Query(value = "update user_wallet" +
            " set balance = balance + :amount" +
            " where user_id = :userId", nativeQuery = true)
    void addAmount(@RequestParam("userId") Long userId,
                     @RequestParam("amount") BigDecimal amount);
}
