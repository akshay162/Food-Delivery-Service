package com.fooddelivery.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooddelivery.constants.DatabaseConstants;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = DatabaseConstants.ORDER_TABLE)
public class OrderEntity extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(value = "user_details")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @JsonProperty(value = "address_details")
    private AddressEntity addressEntity;

    @Column(name = "order_time")
    @JsonProperty(value = "order_time")
    private Long orderTimeEpoch;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonProperty(value = "restaurant_details")
    private RestaurantEntity restaurantEntity;

    @Column(name = "is_active")
    @JsonProperty(value = "is_active")
    private Boolean isActive;

    @Column(name = "status")
    @JsonProperty(value = "status")
    private Integer status; // (Placed, Confirmed, Ready, Picked-up, Delivered, Canceled)

    @Column(name = "items_cost")
    @JsonProperty(value = "items_cost")
    private BigDecimal itemsCost;

    @Column(name = "delivery_fee")
    @JsonProperty(value = "delivery_fee")
    private BigDecimal deliveryFee;

    @Column(name = "service_fee")
    @JsonProperty(value = "service_fee")
    private BigDecimal serviceFee;

    @Column(name = "discount_amount")
    @JsonProperty(value = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "final_order_amount")
    @JsonProperty(value = "final_order_amount")
    private BigDecimal finalOrderAmount;

    @Column(name = "estimated_time_arrival")
    @JsonProperty(value = "estimated_time_arrival")
    private Integer estimatedTimeArrival;

    @Column(name = "actual_delivery_time")
    @JsonProperty(value = "actual_delivery_time")
    private Integer actualDeliveryTime;
}
