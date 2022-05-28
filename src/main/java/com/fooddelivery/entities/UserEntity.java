package com.fooddelivery.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooddelivery.constants.DatabaseConstants;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = DatabaseConstants.USER_TABLE)
public class UserEntity extends AbstractEntity{

    @Column(name = "first_name")
    @JsonProperty(value = "first_name")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty(value = "last_name")
    private String lastName;

    @Column(name = "email")
    @JsonProperty(value = "email")
    private String email;
}
