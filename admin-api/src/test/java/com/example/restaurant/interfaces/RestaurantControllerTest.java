package com.example.restaurant.interfaces;

import com.example.restaurant.application.RestaurantService;
import com.example.restaurant.domain.Restaurant;
import com.example.restaurant.domain.RestaurantNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private RestaurantErrorAdvice restaurantErrorAdvice;

    @Test
    public void list() throws Exception {

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .name("Joker house")
                .address("Seoul")
                .build());
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"Joker house\"")))
                .andExpect(content().string(containsString("\"id\":1004")))
        ;
    }

    @Test
    public void detailWithExisted() throws Exception {

        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
                .andExpect(content().string(containsString("\"id\":1004")))
        ;
    }

    @Test
    public void detailWithNotExisted() throws Exception {

        given(restaurantService.getRestaurant(404L)).willThrow(new RestaurantNotFoundException(404L));

        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""))
        ;
    }


    @Test
    public void createWithInvalidData() throws Exception {

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\":\"BeRyong\"}"))
                .andExpect(status().isBadRequest())
        ;

    }

    @Test
    public void createWithValidData() throws Exception {

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\":\"BeRyong\",\"address\":\"Busan\",\"categoryId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(""))
        ;


        verify(restaurantService).addRestaurant(ArgumentMatchers.any());
    }

    @Test
    public void updateValidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\":\"Bar\",\"address\":\"Busan\",\"categoryId\":1}"))
                .andExpect(status().isOk())
        ;

        verify(restaurantService).updateRestaurant(1004L, "Bar", "Busan");
    }

    @Test
    public void updateInvalidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"address\":\"Busan\"}"))
                .andExpect(status().isBadRequest())
        ;
    }
}