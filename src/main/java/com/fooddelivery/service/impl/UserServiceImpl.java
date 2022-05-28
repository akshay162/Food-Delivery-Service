package com.fooddelivery.service.impl;

import com.fooddelivery.constants.CommandStatus;
import com.fooddelivery.entities.UserEntity;
import com.fooddelivery.entities.UserWalletEntity;
import com.fooddelivery.repository.UserRepository;
import com.fooddelivery.response.BaseResponse;
import com.fooddelivery.response.UserCreationResponse;
import com.fooddelivery.service.UserService;
import com.fooddelivery.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;

    private UserWalletService userWalletService;

    @Autowired
    public UserServiceImpl(UserRepository userRepo,
                           UserWalletService userWalletService) {
        this.userRepo = userRepo;
        this.userWalletService = userWalletService;
    }

    @Override
    public Optional<UserEntity> getUserById(Long userId) {
        return userRepo.findById(userId);
    }

    @Transactional
    @Override
    public UserCreationResponse createUser(String firstName, String lastName, String email) {
        try{

            UserEntity userEntity = userRepo.findByEmail(email);

            if (userEntity != null) {
                return new UserCreationResponse(false, CommandStatus.USER_ALREADY_EXISTS);
            }

            userEntity = new UserEntity();
            userEntity.setFirstName(firstName);
            userEntity.setLastName(lastName);
            userEntity.setEmail(email);

            userEntity = userRepo.save(userEntity);

            UserWalletEntity userWalletEntity = new UserWalletEntity();
            userWalletEntity.setUserEntity(userEntity);
            userWalletEntity.setBalance(BigDecimal.ZERO);
            userWalletEntity.setBlockedAmount(BigDecimal.ZERO);
            userWalletService.save(userWalletEntity);

            return new UserCreationResponse(true, CommandStatus.USER_CREATED, userEntity.getId());
        } catch (Exception e) {
            return new UserCreationResponse(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BaseResponse updateWalletBalance(Long userId, BigDecimal amount) {
        try {
            userWalletService.addAmount(userId, amount);
            return new BaseResponse(true, CommandStatus.SUCCESS);
        } catch (Exception e) {
            return new BaseResponse(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
