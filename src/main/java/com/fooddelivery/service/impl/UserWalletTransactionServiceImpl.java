package com.fooddelivery.service.impl;

import com.fooddelivery.entities.UserWalletTransactionEntity;
import com.fooddelivery.repository.UserWalletTransactionRepository;
import com.fooddelivery.service.UserWalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserWalletTransactionServiceImpl implements UserWalletTransactionService {

    private UserWalletTransactionRepository userWalletTransactionRepo;

    @Autowired
    public UserWalletTransactionServiceImpl(UserWalletTransactionRepository userWalletTransactionRepo) {
        this.userWalletTransactionRepo = userWalletTransactionRepo;
    }

    @Override
    public void saveTransaction(Long userId, Long orderId, BigDecimal amount, String transactionType) {
        UserWalletTransactionEntity userWalletTransactionEntity = new UserWalletTransactionEntity();
        userWalletTransactionEntity.setUserId(userId);
        userWalletTransactionEntity.setOrderId(orderId);
        userWalletTransactionEntity.setAmount(amount);
        userWalletTransactionEntity.setTransactionType(transactionType);

        userWalletTransactionRepo.save(userWalletTransactionEntity);
    }
}
