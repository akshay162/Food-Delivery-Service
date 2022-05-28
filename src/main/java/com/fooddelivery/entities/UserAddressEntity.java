package com.fooddelivery.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooddelivery.constants.DatabaseConstants;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = DatabaseConstants.USER_ADDRESS_TABLE)
public class UserAddressEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(value = "user_details")
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "address_id")
    @JsonProperty(value = "address_details")
    private AddressEntity addressEntity;
}
