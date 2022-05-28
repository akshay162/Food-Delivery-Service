package com.fooddelivery.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderCreationResponse extends BaseResponse {

    @JsonProperty("order_id")
    public Long orderId;

    @JsonProperty("order_status")
    public Integer orderStatus;

    public OrderCreationResponse(boolean success, Object message) {
        super(success, message);
    }
}
