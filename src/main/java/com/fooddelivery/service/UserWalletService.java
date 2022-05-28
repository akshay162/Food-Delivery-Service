package com.fooddelivery.service;

import com.fooddelivery.entities.UserEntity;
import com.fooddelivery.entities.UserWalletEntity;

import java.math.BigDecimal;

public interface UserWalletService {

    public UserWalletEntity save(UserWalletEntity userWalletEntity);

    public UserWalletEntity getUserWalletDetails(UserEntity userEntity);

    public void deductAmount(Long userId, BigDecimal amount);

    public void blockAmount(Long userId, BigDecimal amount);

}
