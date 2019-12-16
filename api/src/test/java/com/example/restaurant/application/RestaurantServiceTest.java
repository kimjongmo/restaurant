package com.example.restaurant.application;

import com.example.restaurant.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class RestaurantServiceTest {
    private RestaurantService restaurantService;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuItemRepository menuItemRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockRestaurantRepository();
        mockMenuItemRepository();
        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository);
    }

    public void mockRestaurantRepository(){
        Restaurant restaurant =  new Restaurant(1004L,"Bob zip","Seoul");
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(restaurant);

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
        given(restaurantRepository.findAll()).willReturn(restaurants);
    }

    public void mockMenuItemRepository(){
        MenuItem menuItem = new MenuItem("kimchi");
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem);

        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }


    @Test
    public void getRestaurant() {

        Restaurant restaurant = restaurantService.getRestaurant(1004L);
        assertThat(restaurant.getId(), is(1004L));

        MenuItem menuItem = restaurant.getMenuItemList().get(0);
        assertThat(menuItem.getName(), is("kimchi"));
    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId(), is(1004L));
    }

    @Test
    public void addRestaurant(){
        Restaurant restaurant = new Restaurant("BeRyong","Busan");
        Restaurant saved = new Restaurant(1234L,"BeRyong","Busan");

        given(restaurantRepository.save(any())).willReturn(saved);
        Restaurant created = restaurantService.addRestaurant(restaurant);

        assertThat(created.getId(),is(1234L));
    }
}