package com.fooddelivery.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreationRequest {

    @JsonProperty("user_id")
    public Long userId;

    @JsonProperty("address_id")
    public Long addressId;

    @JsonProperty("restaurant_id")
    public Long restaurantId;

    @JsonProperty("item_details")
    public List<MenuItemDetails> itemDetails;

    @Data
    public static class MenuItemDetails {

        @JsonProperty("item_id")
        public Long itemId;

        @JsonProperty("serving")
        public Integer serving;

    }
}
