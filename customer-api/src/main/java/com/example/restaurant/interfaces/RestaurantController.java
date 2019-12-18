package com.example.restaurant.interfaces;

import com.example.restaurant.application.RestaurantService;
import com.example.restaurant.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list(@RequestParam String region, @RequestParam("category") Long categoryId) {
        return restaurantService.getRestaurants(region, categoryId );
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        return restaurant;
    }
}

