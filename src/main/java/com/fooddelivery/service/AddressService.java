package com.fooddelivery.service;

import com.fooddelivery.entities.AddressEntity;

import java.util.Optional;

public interface AddressService {

    Optional<AddressEntity> getAddressById(Long addressId);

    AddressEntity save(AddressEntity addressEntity);
}
