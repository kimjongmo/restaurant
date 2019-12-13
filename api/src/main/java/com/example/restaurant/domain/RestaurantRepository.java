package com.example.restaurant.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantRepository {

    private List<Restaurant> list;

    public RestaurantRepository(){
        list = Arrays.asList(
                new Restaurant(1004L, "Bob zip", "Seoul"),
                new Restaurant(1005L, "Bob zip", "Seoul")
        );
    }

    public List<Restaurant> findAll(){
        return list;
    }

    public Restaurant findById(Long id) {
        return list.stream()
                .filter(restaurant -> restaurant.getId().equals(id))
                .findFirst()
                .get();
    }
}
