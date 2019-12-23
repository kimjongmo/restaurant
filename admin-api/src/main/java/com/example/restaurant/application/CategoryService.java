package com.example.restaurant.application;

import com.example.restaurant.domain.Category;
import com.example.restaurant.domain.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(String name) {
        return categoryRepository.save(Category.builder().name(name).build());
    }
}
