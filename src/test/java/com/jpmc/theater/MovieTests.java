package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTests {
    @Test
    void testSpecialCode() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 0);
        ShowSchedule showSchedule = new ShowSchedule(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(11,0)));
        assertEquals(2, spiderMan.getSpecialCode());

        System.out.println(Duration.ofMinutes(90));
    }

}
