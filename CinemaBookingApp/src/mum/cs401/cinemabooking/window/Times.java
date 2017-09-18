package mum.cs401.cinemabooking.window;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class Times {

    private static List<Times> timeList;
    private final String strTime;
    private final Integer intTime;

    public Times(String strTime, Integer intTime) {
        this.strTime = strTime;
        this.intTime = intTime;
    }

    public String getStrTime() {
        return strTime;
    }

    public Integer getIntTime() {
        return intTime;
    }

    public static List<Times> getTimeList() {
        timeList = new ArrayList<>();
        timeList.add(new Times("00:00", 0));
        timeList.add(new Times("01:00", 1));
        timeList.add(new Times("02:00", 2));
        timeList.add(new Times("03:00", 3));
        timeList.add(new Times("04:00", 4));
        timeList.add(new Times("05:00", 5));
        timeList.add(new Times("06:00", 6));
        timeList.add(new Times("07:00", 7));
        timeList.add(new Times("08:00", 8));
        timeList.add(new Times("09:00", 9));
        timeList.add(new Times("10:00", 10));
        timeList.add(new Times("11:00", 11));
        timeList.add(new Times("12:00", 12));
        timeList.add(new Times("13:00", 13));
        timeList.add(new Times("14:00", 14));
        timeList.add(new Times("15:00", 15));
        timeList.add(new Times("16:00", 16));
        timeList.add(new Times("17:00", 17));
        timeList.add(new Times("18:00", 18));
        timeList.add(new Times("19:00", 19));
        timeList.add(new Times("20:00", 20));
        timeList.add(new Times("21:00", 21));
        timeList.add(new Times("22:00", 22));
        timeList.add(new Times("23:00", 23));
        return timeList;
    }

    @Override
    public String toString() {
        return strTime;
    }

}
