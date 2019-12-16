package com.example.restaurant.application;

import com.example.restaurant.domain.MenuItem;
import com.example.restaurant.domain.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems){
        menuItems.stream().forEach(menuItem -> {
            menuItem.setRestaurantId(restaurantId);
            menuItemRepository.save(menuItem);
        });
    }
}
