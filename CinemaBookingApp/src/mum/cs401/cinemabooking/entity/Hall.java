package mum.cs401.cinemabooking.entity;

import java.util.ArrayList;
import java.util.List;
import mum.cs401.cinemabooking.common.RandomNumber;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class Hall {

    private final Integer id;
    private final String name;
    private final Integer doorNumber;
    private final Integer capacity;
    private final List<Seat> seatList;

    public Hall(String name, Integer doorNumber, Integer capacity,
            List<Seat> seatList) {
        this.id = RandomNumber.generateId();
        this.name = name;
        this.doorNumber = doorNumber;
        this.capacity = capacity;
        this.seatList = seatList;
        addSeat();
    }

    public Hall(Integer id, String name, Integer doorNumber, Integer capacity,
            List<Seat> seatList) {
        this.id = id;
        this.name = name;
        this.doorNumber = doorNumber;
        this.capacity = capacity;
        this.seatList = seatList;
    }

    private void addSeat() {
        for (int i = 1; i <= capacity; i++) {
            this.seatList.add(new Seat(i));
        }
    }

    public List<Integer> getAvailableSeat(List<Integer> seatNumberList, Integer count) {
        List<Integer> ret = null;
        int cnt = 0;
        if (seatNumberList == null || seatNumberList.isEmpty()) {
            for (Seat seat : seatList) {
                if (ret == null) {
                    ret = new ArrayList<>();
                }
                ret.add(seat.getNumber());
                cnt++;
                if (cnt == count) {
                    break;
                }
            }
        } else {
            for (Seat seat : seatList) {
                for (Integer seatNumber : seatNumberList) {
                    if (!seat.getNumber().equals(seatNumber)) {
                        if (ret == null) {
                            ret = new ArrayList<>();
                        }
                        ret.add(seat.getNumber());
                        cnt++;
                        break;
                    }
                }
                if (cnt == count) {
                    break;
                }
            }
        }
        return ret;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getDoorNumber() {
        return doorNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    @Override
    public String toString() {
        return name;
    }
}
