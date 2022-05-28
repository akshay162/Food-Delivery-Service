package com.fooddelivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooddelivery.constants.DatabaseConstants;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = DatabaseConstants.ORDER_ITEMS_TABLE)
public class OrderItemsEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderEntity orderEntity;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    @JsonProperty(value = "menu_item_detail")
    private MenuItemEntity menuItemEntity;

    @Column(name = "serving")
    @JsonProperty(value = "serving")
    private Integer serving;

    @Column(name = "price")
    @JsonProperty(value = "price")
    private BigDecimal price;
}
