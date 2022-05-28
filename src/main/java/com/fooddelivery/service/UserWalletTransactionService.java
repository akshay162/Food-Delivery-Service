package com.fooddelivery.service;

import com.fooddelivery.entities.OrderEntity;
import com.fooddelivery.entities.UserEntity;

import java.math.BigDecimal;

public interface UserWalletTransactionService {

    void saveTransaction(UserEntity userEntity,
                         OrderEntity orderEntity,
                         BigDecimal amount,
                         String transactionType);

}
