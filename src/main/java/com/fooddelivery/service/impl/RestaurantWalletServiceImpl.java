package com.fooddelivery.service.impl;

import com.fooddelivery.entities.RestaurantWalletEntity;
import com.fooddelivery.repository.RestaurantWalletRepository;
import com.fooddelivery.service.RestaurantWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RestaurantWalletServiceImpl implements RestaurantWalletService {

    private RestaurantWalletRepository restaurantWalletRepo;

    @Autowired
    public RestaurantWalletServiceImpl(RestaurantWalletRepository restaurantWalletRepo) {
        this.restaurantWalletRepo = restaurantWalletRepo;
    }

    @Override
    public void creditAmount(Long restaurantId, BigDecimal amount) {
        restaurantWalletRepo.creditAmount(restaurantId, amount);
    }

    @Override
    public void save(RestaurantWalletEntity restaurantWalletEntity) {
        restaurantWalletRepo.save(restaurantWalletEntity);
    }
}
