package com.jpmc.theater;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationTests {
    static Customer john;
    static Movie movie;
    @BeforeAll
    public static void init() {
        john = new Customer("John Doe", "id-12345");
        movie = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
    }
    @Test
    void testTotalFeeWithDiscount() {
        Customer customer = new Customer("John Doe", "unused-id");
        ShowSchedule showing = new ShowSchedule(movie, 1, LocalDateTime.now());
        assertEquals( 28.5, new Reservation(customer, showing, 3).totalFee());
    }

    @Test
    void testMaxDiscount() {
        // movie has original special code 1 but showing at 11am, qualify for 25% discount
        Customer customer = new Customer("John Doe", "unused-id");
        ShowSchedule showing = new ShowSchedule(movie,  1, LocalDateTime.of(LocalDate.now(), LocalTime.of(11,0)));
        assertEquals( 3.125, new Reservation(customer, showing, 3).getDiscount(showing.getSequenceOfTheDay()));
    }
}
