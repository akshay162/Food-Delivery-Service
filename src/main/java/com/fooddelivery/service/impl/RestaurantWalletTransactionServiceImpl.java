package com.fooddelivery.service.impl;

import com.fooddelivery.entities.RestaurantWalletTransactionEntity;
import com.fooddelivery.repository.RestaurantWalletTransactionRepository;
import com.fooddelivery.service.RestaurantWalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RestaurantWalletTransactionServiceImpl implements RestaurantWalletTransactionService {

    private RestaurantWalletTransactionRepository restaurantWalletTransactionRepo;

    @Autowired
    public RestaurantWalletTransactionServiceImpl(RestaurantWalletTransactionRepository restaurantWalletTransactionRepo) {
        this.restaurantWalletTransactionRepo = restaurantWalletTransactionRepo;
    }

    @Override
    public void saveTransaction(Long restaurantId, Long orderId, BigDecimal amount, String transactionType) {
        RestaurantWalletTransactionEntity restaurantWalletTransactionEntity = new RestaurantWalletTransactionEntity();
        restaurantWalletTransactionEntity.setRestaurantId(restaurantId);
        restaurantWalletTransactionEntity.setOrderId(orderId);
        restaurantWalletTransactionEntity.setAmount(amount);
        restaurantWalletTransactionEntity.setTransactionType(transactionType);

        restaurantWalletTransactionRepo.save(restaurantWalletTransactionEntity);
    }
}
