package com.fooddelivery.service.impl;

import com.fooddelivery.entities.AddressEntity;
import com.fooddelivery.repository.AddressRepository;
import com.fooddelivery.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {


    private AddressRepository addressRepo;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public Optional<AddressEntity> getAddressById(Long addressId) {
        return addressRepo.findById(addressId);
    }
}
