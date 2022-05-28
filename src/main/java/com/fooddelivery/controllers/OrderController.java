package com.fooddelivery.controllers;

import com.fooddelivery.entities.OrderEntity;
import com.fooddelivery.request.OrderCreationRequest;
import com.fooddelivery.response.BaseResponse;
import com.fooddelivery.response.GenericListResponse;
import com.fooddelivery.response.OrderCreationResponse;
import com.fooddelivery.response.OrderDetailsResponse;
import com.fooddelivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // create order
    @PostMapping("/create")
    public OrderCreationResponse createOrder(@RequestBody OrderCreationRequest orderCreationRequest){
        return orderService.createOrder(orderCreationRequest);
    }

    // get recent orders of users
    @GetMapping("/user")
    public GenericListResponse<OrderEntity> getOrdersForUser(@RequestParam("user_id") Long userId,
                                                             @RequestParam(value = "page_no", required = false) Integer pageNo,
                                                             @RequestParam(value = "page_size", required = false) Integer pageSize) {
        return orderService.getOrdersForUser(userId, pageNo, pageSize);
    }

    // get order details of a particular order
    @GetMapping("/details")
    public OrderDetailsResponse getOrderDetails(@RequestParam("order_id") Long orderId) {
        return orderService.getOrderDetails(orderId);
    }

    // get orders of a restaurant
    @GetMapping(value = "/restaurant")
    public GenericListResponse<OrderEntity> getOrdersForRestaurant(@RequestParam("restaurant_id") Long restaurantId,
                                                      @RequestParam(value = "page_no", required = false) Integer pageNo,
                                                      @RequestParam(value = "page_size", required = false) Integer pageSize) {
        return orderService.getOrdersForRestaurant(restaurantId, pageNo, pageSize);
    }

    // update the status of the order
    @PutMapping(value = "/status-update")
    public BaseResponse updateOrderStatus(@RequestParam("order_id") Long orderId,
                                          @RequestParam("updated_status") Integer updatedStatus,
                                          @RequestParam("user_id") Long userId){
        // user Id is there to check if the person requesting the status update is authorised to do so.
        return orderService.updateOrderStatus(orderId, updatedStatus, userId);
    }
}
