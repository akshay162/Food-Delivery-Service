package com.fooddelivery.service;

import java.math.BigDecimal;

public interface UserWalletTransactionService {

    void saveTransaction(Long userId, Long orderId, BigDecimal amount, String transactionType);

}
