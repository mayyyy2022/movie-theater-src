package com.jpmc.theater;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Schedule of a movie
 * */
public class ShowSchedule {
    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;

    /**
     * Schedule of a movie
     * @param movie movie details
     * @param sequenceOfTheDay movie's showing order of the day
     * @param showStartTime starting time of the movie
     */
    public ShowSchedule(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;

        LocalDateProvider provider = LocalDateProvider.singleton();
        // update movie special code when the show start time is scheduled
        if (showStartTime.isAfter(LocalDateTime.of(provider.currentDate(), LocalTime.of(10, 59)))
                && showStartTime.isBefore(LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 1)))) {
            movie.setSpecialCode(2);
        }
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartTime() {
        return showStartTime;
    }

    public boolean isSequence(int sequence) {
        return this.sequenceOfTheDay == sequence;
    }

    public double getMovieFee() {
        return movie.getTicketPrice();
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

}
