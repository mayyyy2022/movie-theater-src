package com.jpmc.theater;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowScheduleTest {
    static LocalDateProvider provider;
    static Customer john;
    @BeforeAll
    public static void init() {
        provider = LocalDateProvider.singleton();
        john = new Customer("John Doe", "id-12345");
    }

    @Test
    public void testUpdateSpecialCode() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);

        ShowSchedule schedule = new ShowSchedule(spiderMan, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 0)));
        assertEquals(2, schedule.getMovie().getSpecialCode());
    }
}
