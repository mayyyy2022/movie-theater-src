package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterTests {
    @Test
    void totalFeeForCustomer() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        // spiderman - $12.5, starting time 11am, max discount 25% off
        Reservation reservation = theater.reserve(john, 2, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        assertEquals(37.5, reservation.totalFee());
    }

    @Test
    void printMovieSchedule() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }
}
