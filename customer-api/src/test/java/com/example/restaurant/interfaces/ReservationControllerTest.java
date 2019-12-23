package com.example.restaurant.interfaces;

import com.example.restaurant.application.ReservationService;
import com.example.restaurant.domain.Reservation;
import com.example.restaurant.utils.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void create() throws Exception {
        Long restaurantId = 1L;
        Long userId = 1L;
        String name = "Tester";
        String date = "2019-12-24";
        String time = "20:00";
        Integer partySize = 20;
        String token = jwtUtils.createToken(userId,name,null);

        Reservation mockReservation = Reservation.builder().id(1L).build();
        given(reservationService.addReservation(any(),any(),any(),any(),any(),any()))
                .willReturn(mockReservation);
        mvc.perform(post("/restaurants/1/reservations")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization","Bearer "+token)
                .content("{\"date\":\"2019-12-24\"," +
                        "\"time\":\"20:00\"," +
                        "\"partySize\":20}"))
                .andExpect(status().isCreated())
        ;

        verify(reservationService).addReservation(restaurantId, userId, name, date, time, partySize);
    }
}