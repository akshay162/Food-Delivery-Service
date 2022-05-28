package com.fooddelivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooddelivery.constants.DatabaseConstants;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = DatabaseConstants.RESTAURANT_TABLE)
public class RestaurantEntity extends AbstractEntity{

    @Column(name = "name")
    @JsonProperty(value = "name")
    private String name;

    @Column(name = "rating")
    @JsonProperty(value = "rating")
    private Float rating;

    @Column(name = "total_ratings")
    @JsonProperty(value = "total_ratings")
    private Long totalRatings;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "address_id")
    @JsonIgnore
    private AddressEntity addressEntity;
}