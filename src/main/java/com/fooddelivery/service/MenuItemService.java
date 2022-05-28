package com.fooddelivery.service;

import com.fooddelivery.entities.MenuItemEntity;

import java.util.List;

public interface MenuItemService {

    public List<MenuItemEntity> findByRestaurantId(Long restaurantId);

    public List<MenuItemEntity> findByMenuItemIds(List<Long> menuItemIds);

    List<MenuItemEntity> saveAll(List<MenuItemEntity> menuItemEntities);
}
