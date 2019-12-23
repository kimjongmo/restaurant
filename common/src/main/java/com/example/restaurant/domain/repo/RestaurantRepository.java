package com.example.restaurant.domain.repo;

import com.example.restaurant.domain.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant,Long> {
    List<Restaurant> findAll();
    Optional<Restaurant> findById(Long id);
    Restaurant save(Restaurant restaurant);
    List<Restaurant> findAllByAddressContainingAndCategoryId(String region,Long categoryId);
}
