package com.fooddelivery.controllers;

import com.fooddelivery.response.BaseResponse;
import com.fooddelivery.response.UserCreationResponse;
import com.fooddelivery.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/user")
public class UserController {

    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // create a user
    @PostMapping(value = "/create")
    public UserCreationResponse createRestaurant(@RequestParam(value = "first_name") String firstName,
                                                 @RequestParam(value = "last_name") String lastName,
                                                 @RequestParam(value = "email") String email) {
        return userService.createUser(firstName, lastName, email);
    }

    @PutMapping(value = "/add-money-to-wallet")
    public BaseResponse updateWalletBalance(@RequestParam(value = "user_id") Long userId,
                                            @RequestParam(value = "amount") BigDecimal amount) {
        return userService.updateWalletBalance(userId, amount);
    }
}
