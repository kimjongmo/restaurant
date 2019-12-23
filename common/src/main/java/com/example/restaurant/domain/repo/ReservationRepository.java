package com.example.restaurant.domain.repo;

import com.example.restaurant.domain.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation,Long> {
    List<Reservation> findAllByRestaurantId(Long restaurantId);
}
