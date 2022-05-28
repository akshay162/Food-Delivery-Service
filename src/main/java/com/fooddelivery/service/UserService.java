package com.fooddelivery.service;

import com.fooddelivery.entities.UserEntity;
import com.fooddelivery.request.AddAddressRequest;
import com.fooddelivery.response.BaseResponse;
import com.fooddelivery.response.UserCreationResponse;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserService {

    public Optional<UserEntity> getUserById(Long userId);

    public UserCreationResponse createUser(String firstName, String lastName, String email);

    public BaseResponse updateWalletBalance(Long userId, BigDecimal amount);

    public BaseResponse addAddress(AddAddressRequest addAddressRequest);
}
