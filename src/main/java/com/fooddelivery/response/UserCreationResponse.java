package com.fooddelivery.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserCreationResponse extends BaseResponse {

    @JsonProperty("user_id")
    Long userId;

    public UserCreationResponse(boolean success, Object message) {
        super(success, message);
    }

    public UserCreationResponse(boolean success, Object message, Long userId) {
        super(success, message);
        this.userId = userId;
    }
}
