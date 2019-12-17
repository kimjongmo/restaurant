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
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class RestaurantServiceTest {
    private RestaurantService restaurantService;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuItemRepository menuItemRepository;
    @Mock
    private ReviewRepository reviewRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockRestaurantRepository();
        mockMenuItemRepository();
        mockReviewRepository();
        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository,reviewRepository);
    }

    private void mockReviewRepository() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder().name("joker").score(4).description("Bad").build());
        reviews.add(Review.builder().name("BeRyong").score(3).description("Good").build());

        given(reviewRepository.findAllByRestaurantId(1004L)).willReturn(reviews);
    }

    public void mockRestaurantRepository(){
        Restaurant restaurant =  Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(restaurant);

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
        given(restaurantRepository.findAll()).willReturn(restaurants);
    }

    public void mockMenuItemRepository(){
        MenuItem menuItem = MenuItem.builder().name("kimchi").build();
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem);

        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }


    @Test
    public void getRestaurant() {

        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        verify(menuItemRepository).findAllByRestaurantId(1004L);
        verify(reviewRepository).findAllByRestaurantId(1004L);

        assertThat(restaurant.getId(), is(1004L));
        MenuItem menuItem = restaurant.getMenuItemList().get(0);
        assertThat(menuItem.getName(), is("kimchi"));
        assertThat(restaurant.getReviews().get(0).getName(),is("joker"));
        assertThat(restaurant.getReviews().get(1).getDescription(),is("Good"));
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void getRestaurantWithNotExisted() {
        restaurantService.getRestaurant(404L);
    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();


        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId(), is(1004L));
    }

    @Test
    public void addRestaurant(){
        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();

        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant r = invocation.getArgument(0);
            r.setId(1234L);
            return r;
        });
        Restaurant created = restaurantService.addRestaurant(restaurant);

        assertThat(created.getId(),is(1234L));
    }

    @Test

    public void updateRestaurant(){
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        Restaurant saved = Restaurant.builder()
                .id(1004L)
                .name("Alcohol zip")
                .address("Busan")
                .build();
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
        given(restaurantRepository.save(any())).willReturn(saved);

        Restaurant updated = restaurantService.updateRestaurant(1004L,"Alcohol zip","Busan");

        assertThat(updated.getName(),is("Alcohol zip"));
        assertThat(updated.getAddress(),is("Busan"));
    }
}