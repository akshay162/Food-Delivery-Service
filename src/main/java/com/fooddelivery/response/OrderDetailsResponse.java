package com.fooddelivery.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooddelivery.entities.OrderEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDetailsResponse extends BaseResponse {

    @JsonProperty("order_details")
    OrderEntity orderEntity;

    @JsonProperty("order_items")
    List<OrderItemInfo> orderItemsEntities;

    @Data
    public static class OrderItemInfo {

        @JsonProperty("item_id")
        Long itemId;

        @JsonProperty("item_name")
        String itemName;

        @JsonProperty("item_price")
        BigDecimal itemPrice;

        @JsonProperty("serving")
        Integer serving;

    }


    public OrderDetailsResponse(boolean success, Object message) {
        super(success, message);
    }
}
