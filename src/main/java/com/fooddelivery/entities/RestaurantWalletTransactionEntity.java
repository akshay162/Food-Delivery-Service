package com.fooddelivery.entities;

import com.fooddelivery.constants.DatabaseConstants;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = DatabaseConstants.RESTAURANT_WALLET_TRANSACTIONS_TABLE)
public class RestaurantWalletTransactionEntity extends AbstractEntity {

    @Column(name = "restaurant_id")
    Long restaurantId;

    @Column(name = "order_id")
    Long orderId;

    @Column(name = "amount")
    BigDecimal amount;

    @Column(name = "transaction_type")
    String transactionType;
}
