package com.example.restaurant.interfaces;

import com.example.restaurant.application.ReviewService;
import com.example.restaurant.domain.Review;
import com.example.restaurant.interfaces.exception.TokenNotExistedException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity create(
            @PathVariable Long restaurantId,
            @Valid @RequestBody Review resource,
            Authentication auth) throws URISyntaxException {

        if(auth == null){
            throw new TokenNotExistedException();
        }
        Claims claims = (Claims)auth.getPrincipal();

        String name = claims.get("name",String.class);
        Integer score = resource.getScore();
        String description = resource.getDescription();

        Review review = reviewService.addReview(restaurantId, name, score, description);
        String url = "/restaurants/" + restaurantId + "/review/" + review.getId();
        return ResponseEntity.created(new URI(url)).body("");

    }

}
