package com.example.restaurant.interfaces;


import com.example.restaurant.application.ReviewService;
import com.example.restaurant.domain.Review;
import com.example.restaurant.utils.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void createWithValidData() throws Exception {

        Long id = 1004L;
        String name = "tester";
        String description = "Good";
        Integer score = 3;
        String token = jwtUtils.createToken(id,name);

        given(reviewService.addReview(1L,name,score,description)).willReturn(Review.builder().id(123L).build());

        mockMvc.perform(post("/restaurants/1/reviews")
                .header("Authorization","Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"score\":"+score+",\"description\":\""+description+"\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1/review/123"))
        ;
        verify(reviewService).addReview(1L,name,score,description);
    }

    @Test
    public void createWithInvalidData() throws Exception {
        mockMvc.perform(post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"score\":3,\"description\":\"Good\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("토큰")))
        ;
        verify(reviewService, never()).addReview(eq(1L),any(),any(),any());
    }

}