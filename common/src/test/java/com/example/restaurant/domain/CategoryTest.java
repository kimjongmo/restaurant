package com.example.restaurant.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CategoryTest {

    @Test
    public void creation(){
        Category category = Category.builder().name("한국 음식").build();
        assertThat(category.getName(),is("한국 음식"));
    }
}