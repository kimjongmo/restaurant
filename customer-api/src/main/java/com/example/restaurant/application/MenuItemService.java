package com.example.restaurant.application;

import com.example.restaurant.domain.MenuItem;
import com.example.restaurant.domain.repo.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems){
        for(MenuItem menu : menuItems){
            update(restaurantId,menu);
        }
    }

    private void update(Long restaurantId, MenuItem menuItem){
        if(menuItem.isDestroy()){
            menuItemRepository.deleteById(menuItem.getId());
            return;
        }
        menuItem.setRestaurantId(restaurantId);
        menuItemRepository.save(menuItem);
    }
}
