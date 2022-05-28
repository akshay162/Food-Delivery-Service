package com.fooddelivery.controllers;

import com.fooddelivery.request.MenuRequest;
import com.fooddelivery.response.BaseResponse;
import com.fooddelivery.response.GenericListResponse;
import com.fooddelivery.response.RestaurantCreationResponse;
import com.fooddelivery.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    // get all restaurants
    @GetMapping(value = "/all")
    public GenericListResponse getAllRestaurants(@RequestParam(value = "page_no", required = false) Integer pageNo,
                                                 @RequestParam(value = "page_size", required = false) Integer pageSize) {

        return restaurantService.getAllRestaurants(pageNo, pageSize);
    }

    // create restaurant
    @PostMapping(value = "/create")
    public RestaurantCreationResponse createRestaurant(@RequestParam(value = "restaurant_name") String restaurantName,
                                                       @RequestParam(value = "user_id") Long userId) {
        return restaurantService.createRestaurant(restaurantName, userId);
    }

    // get menu of a restaurant
    @GetMapping(value = "/menu")
    public GenericListResponse getMenu(@RequestParam(value = "restaurant_id") Long restaurantId) {
        return restaurantService.getMenuByRestaurant(restaurantId);
    }

    // add items in menu
    @PostMapping(value = "/menu")
    public BaseResponse addMenu(@RequestBody MenuRequest addMenuRequest) {
        return restaurantService.addMenu(addMenuRequest);
    }

    // edit items in menu
    @PutMapping(value = "/menu")
    public BaseResponse editMenu(@RequestBody MenuRequest editMenuRequest) {
        return restaurantService.editMenu(editMenuRequest);
    }
}
