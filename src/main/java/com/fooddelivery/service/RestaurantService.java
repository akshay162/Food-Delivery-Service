package com.fooddelivery.service;

import com.fooddelivery.entities.MenuItemEntity;
import com.fooddelivery.entities.RestaurantEntity;
import com.fooddelivery.request.MenuRequest;
import com.fooddelivery.response.BaseResponse;
import com.fooddelivery.response.GenericListResponse;
import com.fooddelivery.response.RestaurantCreationResponse;

import java.util.Optional;

public interface RestaurantService {


    public Optional<RestaurantEntity> getRestaurantById(Long restaurant);

    public RestaurantCreationResponse createRestaurant(String restaurantName, Long userId);

    public GenericListResponse<RestaurantEntity> getAllRestaurants(Integer pageNo, Integer pageSize);

    public GenericListResponse<MenuItemEntity> getMenuByRestaurant(Long restaurantId);

    public BaseResponse addMenu(MenuRequest addMenuRequest);

    public BaseResponse editMenu(MenuRequest menuRequest);
}
