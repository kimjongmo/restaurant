package com.example.restaurant.domain.repo;

import com.example.restaurant.domain.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ReviewRepository extends CrudRepository<Review,Long> {
    List<Review> findAllByRestaurantId(Long id);
    List<Review> findAll();
}
