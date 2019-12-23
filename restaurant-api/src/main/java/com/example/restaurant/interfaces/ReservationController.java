package com.example.restaurant.interfaces;

import com.example.restaurant.application.ReservationService;
import com.example.restaurant.domain.Reservation;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public List<Reservation> list(Authentication authentication){

        if (authentication == null) {
            throw new TokenNotExistedException();
        }

        Claims claims = (Claims) authentication.getPrincipal();

        Long restaurantId = claims.get("restaurantId",Long.class);

        return reservationService.getReservations(restaurantId);
    }
}
