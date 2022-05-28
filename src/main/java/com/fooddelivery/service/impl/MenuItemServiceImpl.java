package com.fooddelivery.service.impl;

import com.fooddelivery.entities.MenuItemEntity;
import com.fooddelivery.repository.MenuItemRepository;
import com.fooddelivery.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private MenuItemRepository menuItemRepo;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepository menuItemRepo) {
        this.menuItemRepo = menuItemRepo;
    }

    @Override
    public List<MenuItemEntity> findByRestaurantId(Long restaurantId) {
        return menuItemRepo.findByRestaurantId(restaurantId);
    }

    @Override
    public List<MenuItemEntity> findByMenuItemIds(List<Long> menuItemIds) {
        return menuItemRepo.findByMenuItemIds(menuItemIds);
    }

    @Override
    public List<MenuItemEntity> saveAll(List<MenuItemEntity> menuItemEntities) {
        return menuItemRepo.saveAll(menuItemEntities);
    }
}
