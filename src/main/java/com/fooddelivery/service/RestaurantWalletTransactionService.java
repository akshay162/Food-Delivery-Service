package com.fooddelivery.service;

import com.fooddelivery.entities.OrderEntity;
import com.fooddelivery.entities.RestaurantEntity;

import java.math.BigDecimal;

public interface RestaurantWalletTransactionService {

    void saveTransaction(RestaurantEntity restaurantEntity,
                         OrderEntity orderEntity,
                         BigDecimal amount,
                         String transactionType);
}
