package com.jpmc.theater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Theater {

    LocalDateProvider provider;
    private List<ShowSchedule> schedule;

    public Theater(LocalDateProvider provider) {
        this.provider = provider;

        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
        schedule = List.of(
            new ShowSchedule(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
            new ShowSchedule(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
            new ShowSchedule(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
            new ShowSchedule(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
            new ShowSchedule(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
            new ShowSchedule(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
            new ShowSchedule(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
            new ShowSchedule(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
            new ShowSchedule(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0)))
        );
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        ShowSchedule showSchedule;
        try {
            showSchedule = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }
        return new Reservation(customer, showSchedule, howManyTickets);
    }

    public void printSchedule() {
        System.out.println(provider.currentDate());
        System.out.println("===================================================");
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        schedule.forEach(s -> {
                    try {
                        String json = mapper.writeValueAsString(s);
                        System.out.println(json);
                    } catch (JsonProcessingException e) {
                        System.out.println("Error when parsing schedule to json " + e.getMessage());
                        throw new RuntimeException(e);
                    }
                }

        );
        System.out.println("===================================================");
    }

    public String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }

    public static void main(String[] args) {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }
}
