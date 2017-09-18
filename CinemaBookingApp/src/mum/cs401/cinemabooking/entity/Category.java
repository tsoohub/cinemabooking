package mum.cs401.cinemabooking.entity;

import java.util.ArrayList;
import java.util.List;
import mum.cs401.cinemabooking.common.RandomNumber;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class Category {

    private final Integer id;
    private final String name;
    private final List<MovieCategories> movieCategoryList;

    public Category(String name) {
        this.id = RandomNumber.generateId();
        this.name = name;
        this.movieCategoryList = new ArrayList<>();
    }

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.movieCategoryList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MovieCategories> getMovieCategoryList() {
        return movieCategoryList;
    }

    @Override
    public String toString() {
        return name;
    }

}
