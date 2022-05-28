package com.fooddelivery.service;

import java.math.BigDecimal;

public interface RestaurantWalletTransactionService {

    void saveTransaction(Long restaurantId, Long orderId, BigDecimal amount, String transactionType);
}
