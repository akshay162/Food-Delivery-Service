package com.fooddelivery.service;

import com.fooddelivery.entities.AddressEntity;

import java.util.Optional;

public interface AddressService {

    public Optional<AddressEntity> getAddressById(Long addressId);
}
