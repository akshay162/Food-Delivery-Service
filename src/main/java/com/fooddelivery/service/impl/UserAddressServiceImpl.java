package com.fooddelivery.service.impl;

import com.fooddelivery.entities.UserAddressEntity;
import com.fooddelivery.repository.UserAddressRepository;
import com.fooddelivery.service.UserAddressService;
import org.springframework.stereotype.Service;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    private UserAddressRepository userAddressRepo;

    public UserAddressServiceImpl(UserAddressRepository userAddressRepo) {
        this.userAddressRepo = userAddressRepo;
    }

    @Override
    public UserAddressEntity save(UserAddressEntity userAddressEntity) {
        return userAddressRepo.save(userAddressEntity);
    }
}
