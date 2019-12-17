package com.example.restaurant.domain;

import com.example.restaurant.domain.MenuItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuItemRepository extends CrudRepository<MenuItem,Long> {
    List<MenuItem> findAllByRestaurantId(Long restaurantId);
    void deleteById(Long id);
}
