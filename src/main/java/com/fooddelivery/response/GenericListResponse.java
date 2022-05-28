package com.fooddelivery.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GenericListResponse<T> extends BaseResponse {

    @JsonProperty("data")
    public List<T> data;

    public GenericListResponse(boolean success, Object message) {
        super(success, message);
    }

    public GenericListResponse(boolean success, Object message, List<T> data) {
        super(success, message);
        this.data = data;
    }
}
