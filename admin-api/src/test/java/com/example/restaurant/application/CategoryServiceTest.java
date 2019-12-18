package com.example.restaurant.application;

import com.example.restaurant.domain.Category;
import com.example.restaurant.domain.CategoryRepository;
import com.example.restaurant.domain.Region;
import com.example.restaurant.domain.RegionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class CategoryServiceTest {

    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryService(categoryRepository);

        cateogryRepositorySetUp();
    }

    private void cateogryRepositorySetUp(){
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder().name("한국 음식").build());
        given(categoryRepository.findAll()).willReturn(categories);
    }

    @Test
    public void getCategories(){
        List<Category> categories = categoryService.getCategories();
        Category category = categories.get(0);
        assertThat(category.getName(),is("한국 음식"));
    }

    @Test
    public void addCategory(){

        given(categoryRepository.save(any())).willReturn(Category.builder().id(1L).name("한국 음식").build());

        Category category = categoryService.addCategory("한국 음식");
        assertThat(category.getName(),is("한국 음식"));
    }
}