package com.example.restaurant.interfaces;

import com.example.restaurant.application.ReviewService;
import com.example.restaurant.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;


    @GetMapping("/reviews")
    public List<Review> list(){
        return reviewService.getReviews();
    }


//    @PostMapping("/restaurants/{restaurantId}/reviews")
//    public ResponseEntity create(
//            @PathVariable Long restaurantId,
//            @Valid @RequestBody Review resource) throws URISyntaxException {
//
//        Review review = reviewService.addReview(restaurantId,resource);
//        String url = "/restaurants/" + restaurantId + "/review/" + review.getId();
//        return ResponseEntity.created(new URI(url)).body("");
//    }

}
