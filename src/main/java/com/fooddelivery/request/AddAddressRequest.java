package com.fooddelivery.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddAddressRequest extends BaseRequest {

    @JsonProperty(value = "user_id")
    private Long userId;

    @JsonProperty(value = "street")
    private String street;

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "state")
    private String state;

    @JsonProperty(value = "country")
    private String country;

    @JsonProperty(value = "zipcode")
    private String zipcode;

}
