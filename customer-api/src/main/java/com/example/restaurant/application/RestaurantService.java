package com.example.restaurant.application;

import com.example.restaurant.domain.*;
import com.example.restaurant.domain.repo.MenuItemRepository;
import com.example.restaurant.domain.repo.RestaurantRepository;
import com.example.restaurant.domain.repo.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;
    private MenuItemRepository menuItemRepository;
    private ReviewRepository reviewRepository;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             MenuItemRepository menuItemRepository,
                             ReviewRepository reviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.reviewRepository = reviewRepository;
    }

    public Restaurant getRestaurant(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
        restaurant.setMenuItemList(menuItemRepository.findAllByRestaurantId(id));
        restaurant.setReviews(reviewRepository.findAllByRestaurantId(id));
        return restaurant;
    }

    public List<Restaurant> getRestaurants(String region, Long categoryId) {
        return restaurantRepository.findAllByAddressContainingAndCategoryId(region,categoryId);
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public Restaurant updateRestaurant(Long id, String name, String address) {
        Restaurant restaurant = restaurantRepository.findById(id).get();
        restaurant.updateInformation(name, address);
        Restaurant updated = restaurantRepository.save(restaurant);
        return updated;
    }
}
