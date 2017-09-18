package mum.cs401.cinemabooking.entity;

import mum.cs401.cinemabooking.common.RandomNumber;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class Ticket {

    private final Integer id;
    private final Double amount;
    private final Integer seatNumber;

    public Ticket(Double amount, Integer seatNumber) {
        this.id = RandomNumber.generateId();
        this.amount = amount;
        this.seatNumber = seatNumber;
    }

    public Ticket(Integer id, Double amount, Integer seatNumber) {
        this.id = id;
        this.amount = amount;
        this.seatNumber = seatNumber;
    }

    public Integer getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }
}
