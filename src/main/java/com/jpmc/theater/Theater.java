package com.jpmc.theater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Theater {

    LocalDateProvider provider;
    private List<ShowSchedule> schedule;

    public Theater(List<ShowSchedule> schedule, LocalDateProvider provider) {
        this.schedule = schedule;
        this.provider = provider;
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

    public static void main(String[] args) {
        LocalDateProvider provider = LocalDateProvider.singleton();
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
        List<ShowSchedule> schedule = List.of(
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

        Theater theater = new Theater(schedule, provider);
        theater.printSchedule();
    }
}
