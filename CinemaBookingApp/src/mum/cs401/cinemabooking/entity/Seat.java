package mum.cs401.cinemabooking.entity;

import mum.cs401.cinemabooking.common.RandomNumber;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class Seat {

    private final Integer id;
    private final Integer number;

    public Seat(Integer number) {
        this.id = RandomNumber.generateId();
        this.number = number;
    }

    public Seat(Integer id, Integer number) {
        this.id = id;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

}
