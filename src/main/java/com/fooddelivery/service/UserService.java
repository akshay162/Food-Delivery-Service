package com.fooddelivery.service;

import com.fooddelivery.entities.UserEntity;
import com.fooddelivery.response.UserCreationResponse;

import java.util.Optional;

public interface UserService {

    public Optional<UserEntity> getUserById(Long userId);

    public UserCreationResponse createUser(String firstName, String lastName, String email);
}
