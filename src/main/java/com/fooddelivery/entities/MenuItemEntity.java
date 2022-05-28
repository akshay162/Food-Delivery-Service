package com.fooddelivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooddelivery.constants.DatabaseConstants;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = DatabaseConstants.MENU_ITEM_TABLE)
public class MenuItemEntity extends AbstractEntity{

    @Column(name = "item_name")
    @JsonProperty(value = "item_name")
    private String itemName;

    @Column(name = "item_description")
    @JsonProperty(value = "item_description")
    private String itemDescription;

    @Column(name = "image_url")
    @JsonProperty(value = "image_url")
    private String imageUrl;

    @Column(name = "item_price")
    @JsonProperty(value = "item_price")
    private BigDecimal itemPrice;

    @Column(name = "item_discount")
    @JsonProperty(value = "item_discount")
    private BigDecimal itemDiscount;

    @Column(name = "diet_type")
    @JsonProperty(value = "diet_type")
    private Integer dietType; // 0 - vegan, 1 - vegetarian, 2 - eggetarian, 3 - non vegetarian.

    @Column(name = "is_available")
    @JsonProperty(value = "is_available")
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RestaurantEntity restaurantEntity;

}
