package com.example.restaurant.application;

import com.example.restaurant.domain.Reservation;
import com.example.restaurant.domain.repo.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ReservationServiceTest {

    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    public void addReservation() {

        Long restaurantId = 2L;
        Long userId = 1L;
        String name = "Tester";
        String date = "2019-12-24";
        String time = "20:00";
        Integer partySize = 20;

        Reservation mockReservation = Reservation.builder()
                .id(1L)
                .restaurantId(restaurantId)
                .userId(userId)
                .name(name)
                .date(date)
                .time(time)
                .partySize(partySize)
                .build();

        given(reservationRepository.save(any())).will(invocation -> {
            Reservation reservation = invocation.getArgument(0);
            return reservation;
        });

        Reservation reservation = reservationService.addReservation(restaurantId, userId, name, date, time, partySize);
        verify(reservationRepository).save(any());

        assertThat(reservation.getName(), is(name));
    }
}