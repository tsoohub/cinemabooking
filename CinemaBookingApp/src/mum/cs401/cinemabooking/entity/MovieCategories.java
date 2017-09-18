package mum.cs401.cinemabooking.entity;

import mum.cs401.cinemabooking.common.RandomNumber;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class MovieCategories {

    private final Integer id;
    private Movie movie;
    private Category category;

    public MovieCategories(Movie movie, Category category) {
        this.id = RandomNumber.generateId();
        this.movie = movie;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
