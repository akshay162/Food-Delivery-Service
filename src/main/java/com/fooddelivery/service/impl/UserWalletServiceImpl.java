package com.fooddelivery.service.impl;

import com.fooddelivery.entities.UserEntity;
import com.fooddelivery.entities.UserWalletEntity;
import com.fooddelivery.repository.UserWalletRepository;
import com.fooddelivery.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserWalletServiceImpl implements UserWalletService {

    private UserWalletRepository userWalletRepo;

    @Autowired
    public UserWalletServiceImpl(UserWalletRepository userWalletRepo) {
        this.userWalletRepo = userWalletRepo;
    }

    @Override
    public UserWalletEntity save(UserWalletEntity userWalletEntity) {
        return userWalletRepo.save(userWalletEntity);
    }

    @Override
    public UserWalletEntity getUserWalletDetails(UserEntity userEntity) {
        return userWalletRepo.findByUserEntity(userEntity);
    }

    @Override
    public void deductAmount(Long userId, BigDecimal amount) {
        userWalletRepo.deductAmount(userId, amount);
    }

    @Override
    public void blockAmount(Long userId, BigDecimal amount) {
        userWalletRepo.blockAmount(userId, amount);
    }

    @Override
    public void addAmount(Long userId, BigDecimal amount) {
        userWalletRepo.addAmount(userId, amount);
    }
}
