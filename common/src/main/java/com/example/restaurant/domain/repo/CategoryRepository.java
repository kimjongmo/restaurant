package com.example.restaurant.domain.repo;


import com.example.restaurant.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    List<Category> findAll();
}
