package com.fooddelivery.service.impl;

import com.fooddelivery.entities.OrderEntity;
import com.fooddelivery.entities.RestaurantEntity;
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
    public void saveTransaction(RestaurantEntity restaurantEntity, OrderEntity orderEntity, BigDecimal amount, String transactionType) {
        RestaurantWalletTransactionEntity restaurantWalletTransactionEntity = new RestaurantWalletTransactionEntity();
        restaurantWalletTransactionEntity.setRestaurantId(restaurantEntity.getId());
        restaurantWalletTransactionEntity.setOrderId(orderEntity.getId());
        restaurantWalletTransactionEntity.setAmount(amount);
        restaurantWalletTransactionEntity.setTransactionType(transactionType);

        restaurantWalletTransactionRepo.save(restaurantWalletTransactionEntity);
    }
}
