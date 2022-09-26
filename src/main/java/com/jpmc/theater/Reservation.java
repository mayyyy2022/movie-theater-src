package com.jpmc.theater;

public class Reservation {

    private Customer customer;
    private ShowSchedule showSchedule;
    private int audienceCount;

    public Reservation(Customer customer, ShowSchedule showSchedule, int audienceCount) {
        this.customer = customer;
        this.showSchedule = showSchedule;
        this.audienceCount = audienceCount;
    }

    public double totalFee() {
        return calculateTicketPrice(showSchedule) * audienceCount;
    }

    public double calculateTicketPrice(ShowSchedule showSchedule) {
        return showSchedule.getMovie().getTicketPrice() - getDiscount(showSchedule.getSequenceOfTheDay());
    }

    public double getDiscount(int showSequence) {
        double maxDiscount = 0;
        // in case there will be more special discount in the future
        switch(showSchedule.getMovie().getSpecialCode()) {
            case 1 : // 20% discount for special movie 1
                maxDiscount = showSchedule.getMovie().getTicketPrice() * 0.2;
                break;
            case 2 : // starting between 11AM ~ 4pm, you'll get 25% discount
                maxDiscount = showSchedule.getMovie().getTicketPrice() * 0.25;
                break;
        }

        switch (showSequence) {
            case 1 :
                maxDiscount = Math.max(3, maxDiscount); // $3 discount for 1st show
                break;
            case 2 :
                maxDiscount = Math.max(2, maxDiscount); // $2 discount for 2nd show
                break;
            case 7 :
                maxDiscount = Math.max(1, maxDiscount); // $1 discount for 7th show
                break;
        }

        // biggest discount wins
        return maxDiscount;
    }
}