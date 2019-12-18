package com.example.restaurant.interfaces;

import com.example.restaurant.application.CategoryService;
import com.example.restaurant.domain.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void list() throws Exception {

        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(Category.builder().name("한국 음식").build());

        given(categoryService.getCategories()).willReturn(mockCategories);

        mvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("한국 음식")))
        ;
    }

}