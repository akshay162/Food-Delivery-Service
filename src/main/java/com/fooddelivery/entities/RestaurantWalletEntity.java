package com.fooddelivery.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooddelivery.constants.DatabaseConstants;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = DatabaseConstants.RESTAURANT_WALLET_TABLE)
public class RestaurantWalletEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonProperty(value = "restaurant_details")
    private RestaurantEntity restaurantEntity;

    @Column(name = "balance")
    @JsonProperty(value = "balance")
    private BigDecimal balance;
}
