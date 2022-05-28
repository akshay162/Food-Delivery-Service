package com.fooddelivery.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MenuRequest extends BaseRequest {

    @JsonProperty("restaurant_id")
    Long restaurantId;

    @JsonProperty("menu_item_list")
    List<MenuItemRequest> menuItemRequestList;

    @Data
    public static class MenuItemRequest {

        @JsonProperty("id")
        Long id;

        @JsonProperty("item_name")
        String itemName;

        @JsonProperty("item_description")
        String itemDescription;

        @JsonProperty("image_url")
        String imageUrl;

        @JsonProperty("price")
        BigDecimal price;

        @JsonProperty("diet_type_id")
        Integer dietTypeId;

        @JsonProperty("is_available")
        Boolean isAvailable;
    }
}
