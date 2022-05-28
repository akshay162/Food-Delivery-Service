package com.fooddelivery.service;

import com.fooddelivery.entities.RestaurantWalletEntity;

import java.math.BigDecimal;

public interface RestaurantWalletService {

    public void creditAmount(Long restaurantId, BigDecimal amount);

    public void save(RestaurantWalletEntity restaurantWalletEntity);
}
