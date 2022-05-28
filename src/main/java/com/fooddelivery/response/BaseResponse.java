package com.fooddelivery.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class BaseResponse implements Serializable {

	private static final long serialVersionUID = 5825723010632699704L;

	@JsonProperty("message")
    public Object message;

    @JsonProperty("success")
    public boolean success;

    public BaseResponse(boolean success, Object message) {
        this.message = message;
        this.success = success;
    }

    public Object getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
