package com.fooddelivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooddelivery.constants.DatabaseConstants;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = DatabaseConstants.ADDRESS_TABLE)
public class AddressEntity extends AbstractEntity{

    @Column(name = "street")
    @JsonProperty(value = "street")
    private String street;

    @Column(name = "city")
    @JsonProperty(value = "city")
    private String city;

    @Column(name = "state")
    @JsonProperty(value = "state")
    private String state;

    @Column(name = "country")
    @JsonProperty(value = "country")
    private String country;

    @Column(name = "zipcode")
    @JsonProperty(value = "zipcode")
    private String zipcode;

    @Column(name = "latitude")
    @JsonIgnore
    private Double latitude;

    @Column(name = "longitude")
    @JsonIgnore
    private Double longitude;
}
