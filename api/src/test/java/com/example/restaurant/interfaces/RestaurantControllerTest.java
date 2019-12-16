package com.example.restaurant.interfaces;

import com.example.restaurant.application.RestaurantService;
import com.example.restaurant.domain.MenuItem;
import com.example.restaurant.domain.Restaurant;
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

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;


    @Test
    public void list() throws Exception {

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1004L,"Joker house","Seoul"));
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"Joker house\"")))
                .andExpect(content().string(containsString("\"id\":1004")))
        ;
    }

    @Test
    public void detail() throws Exception {

        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant1 = new Restaurant(1004L,"Bob zip","Seoul");
        restaurant1.addMenuItem(new MenuItem("kimchi"));
        Restaurant restaurant2 = new Restaurant(1005L,"Bob zip","Seoul");
        restaurant2.addMenuItem(new MenuItem("kimchi"));
        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);
        given(restaurantService.getRestaurant(1005L)).willReturn(restaurant2);


        Long id = 1004L;
        mvc.perform(get("/restaurants/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
                .andExpect(content().string(containsString("\"id\":" + id)))
                .andExpect(content().string(containsString("kimchi")))
        ;


        id = 1005L;
        mvc.perform(get("/restaurants/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
                .andExpect(content().string(containsString("\"id\":" + id)))
                .andExpect(content().string(containsString("kimchi")))
        ;
    }



    @Test
    public void create() throws Exception {

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\":\"BeRyong\",\"address\":\"Busan\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(""))
        ;

        verify(restaurantService).addRestaurant(ArgumentMatchers.any());
    }
}