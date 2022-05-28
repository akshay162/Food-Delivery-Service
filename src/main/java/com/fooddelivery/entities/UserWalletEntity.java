package com.fooddelivery.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooddelivery.constants.DatabaseConstants;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = DatabaseConstants.USER_WALLET_TABLE)
public class UserWalletEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(value = "user_details")
    private UserEntity userEntity;

    @Column(name = "balance")
    @JsonProperty(value = "balance")
    private BigDecimal balance;

    @Column(name = "blocked_amount")
    @JsonProperty(value = "blocked_amount")
    private BigDecimal blockedAmount;
}
