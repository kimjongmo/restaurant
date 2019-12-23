package com.example.restaurant.interfaces;

import com.example.restaurant.application.ReservationService;
import com.example.restaurant.domain.Reservation;
import com.example.restaurant.utils.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ReservationService reservationService;
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void list() throws Exception {
        Long userId = 1L;
        String name = "Tester";
        Long restaurantId = 1004L;
        String token = jwtUtils.createToken(userId,name,restaurantId);

        mvc.perform(get("/reservations")
                .header("Authorization","Bearer "+token))
                .andExpect(status().isOk())
        ;

        verify(reservationService).getReservations(restaurantId);

    }
}