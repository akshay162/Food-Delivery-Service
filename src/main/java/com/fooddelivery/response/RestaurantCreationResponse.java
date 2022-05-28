package com.fooddelivery.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RestaurantCreationResponse extends BaseResponse {

    @JsonProperty("restaurant_id")
    Long restaurantId;

    public RestaurantCreationResponse(boolean success, Object message) {
        super(success, message);
    }

    public RestaurantCreationResponse(boolean success, Object message, Long restaurantId) {
        super(success, message);
        this.restaurantId = restaurantId;
    }
}
