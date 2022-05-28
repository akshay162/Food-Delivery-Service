package com.fooddelivery.service.impl;

import com.fooddelivery.constants.CommandStatus;
import com.fooddelivery.constants.Constants;
import com.fooddelivery.entities.AddressEntity;
import com.fooddelivery.entities.UserAddressEntity;
import com.fooddelivery.entities.UserEntity;
import com.fooddelivery.entities.UserWalletEntity;
import com.fooddelivery.repository.UserRepository;
import com.fooddelivery.request.AddAddressRequest;
import com.fooddelivery.response.BaseResponse;
import com.fooddelivery.response.UserCreationResponse;
import com.fooddelivery.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;
    private UserWalletService userWalletService;
    private UserWalletTransactionService userWalletTransactionService;
    private AddressService addressService;
    private UserAddressService userAddressService;

    @Autowired
    public UserServiceImpl(UserRepository userRepo,
                           UserWalletService userWalletService,
                           UserWalletTransactionService userWalletTransactionService,
                           AddressService addressService,
                           UserAddressService userAddressService) {
        this.userRepo = userRepo;
        this.userWalletService = userWalletService;
        this.userWalletTransactionService = userWalletTransactionService;
        this.addressService = addressService;
        this.userAddressService = userAddressService;
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
            userWalletTransactionService.saveTransaction(userId, null, amount, Constants.TRANSACTION_TYPE_CREDIT);
            return new BaseResponse(true, CommandStatus.SUCCESS);
        } catch (Exception e) {
            return new BaseResponse(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public BaseResponse addAddress(AddAddressRequest addAddressRequest) {
        try {
            Long userId = addAddressRequest.getUserId();
            Optional<UserEntity> userEntity = userRepo.findById(userId);

            if (!userEntity.isPresent()) {
                return new BaseResponse(false, CommandStatus.USER_NOT_FOUND);
            }

            if (addAddressRequest.getStreet() == null || addAddressRequest.getCity() == null
                    || addAddressRequest.getState() == null || addAddressRequest.getCountry() == null
                    || addAddressRequest.getZipcode() == null) {
                return new BaseResponse(false, CommandStatus.INCOMPLETE_ADDRESS_DETAILS);
            }

            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setStreet(addAddressRequest.getStreet());
            addressEntity.setCity(addAddressRequest.getCity());
            addressEntity.setState(addAddressRequest.getState());
            addressEntity.setCountry(addAddressRequest.getCountry());
            addressEntity.setZipcode(addAddressRequest.getZipcode());
            addressEntity = addressService.save(addressEntity);

            UserAddressEntity userAddressEntity = new UserAddressEntity();
            userAddressEntity.setUserEntity(userEntity.get());
            userAddressEntity.setAddressEntity(addressEntity);
            userAddressService.save(userAddressEntity);

            return new BaseResponse(true, CommandStatus.ADDRESS_ADDED);
        } catch (Exception e) {
            return new BaseResponse(false, CommandStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
