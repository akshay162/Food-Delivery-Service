package com.fooddelivery.repository;

import com.fooddelivery.entities.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {

    @Query(value = "Select m from MenuItemEntity m" +
            " where m.restaurantEntity.id = :restaurantId")
    List<MenuItemEntity> findByRestaurantId(@RequestParam("restaurantId") Long restaurantId);

    @Query(value = "select m from MenuItemEntity m" +
            " where m.id in :menuItemIds")
    List<MenuItemEntity> findByMenuItemIds(@RequestParam("menuItemIds") List<Long> menuItemIds);
}
