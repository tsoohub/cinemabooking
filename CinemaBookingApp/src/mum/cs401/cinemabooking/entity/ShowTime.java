package mum.cs401.cinemabooking.entity;

import java.util.ArrayList;
import java.util.List;
import mum.cs401.cinemabooking.common.RandomNumber;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class ShowTime {

    private Hall hall;
    private Integer movieTime;
    private Movie movie;
    private final Integer id;
    private final List<Order> orderList;

    public ShowTime(Hall hall, Movie movie, Integer movieTime) {
        this.id = RandomNumber.generateId();
        this.hall = hall;
        this.movieTime = movieTime;
        this.movie = movie;
        this.orderList = new ArrayList<>();
    }

    public ShowTime(Integer id, Hall hall, Movie movie, Integer movieTime) {
        this.id = id;
        this.hall = hall;
        this.movieTime = movieTime;
        this.movie = movie;
        this.orderList = new ArrayList<>();
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(Integer movieTime) {
        this.movieTime = movieTime;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(movieTime);
        if (str.toString().length() < 2) {
            str.insert(0, "0");
        }
        str.append(":00");
        return str.toString();
    }

}
