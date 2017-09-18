package mum.cs401.cinemabooking.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mum.cs401.cinemabooking.common.RandomNumber;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class Movie {

    private final Integer id;
    private List<MoviePrice> moviePriceList;
    private String director;
    private String name;
    private Integer duration;
    private String trailerLink;
    private String rateType;
    private String actors;
    private Date startDate;
    private Date endDate;
    private List<MovieCategories> movieCategoryList;
    public static final String AGETYPE_ADULT = "adult";
    public static final String AGETYPE_CHILD = "child";
    private static final Byte CHILDREN_MIN_AGE = 0;
    private static final Byte CHILDREN_MAX_AGE = 17;
    private static final Byte ADULT_MIN_AGE = 18;
    private static final Byte ADULT_MAX_AGE = 125;

    public Movie(String director, String name, Integer duration,
            String trailerLink, String rateType, String actors,
            Date startDate, Date endDate, Double price, String ageType,
            Double price1, String ageType1) {
        this.id = RandomNumber.generateId();
        this.director = director;
        this.name = name;
        this.duration = duration;
        this.trailerLink = trailerLink;
        this.rateType = rateType;
        this.actors = actors;
        this.startDate = startDate;
        this.endDate = endDate;
        this.moviePriceList = new ArrayList<>();
        addMoviePrice(price, ageType);
        addMoviePrice(price1, ageType1);
        this.movieCategoryList = new ArrayList<>();
    }

    public Movie(Integer id, String director, String name, Integer duration,
            String trailerLink, String rateType, String actors,
            Date startDate, Date endDate) {
        this.id = id;
        this.director = director;
        this.name = name;
        this.duration = duration;
        this.trailerLink = trailerLink;
        this.rateType = rateType;
        this.actors = actors;
        this.startDate = startDate;
        this.endDate = endDate;
        this.moviePriceList = new ArrayList<>();
        this.movieCategoryList = new ArrayList<>();
    }

    public List<MovieCategories> getMovieCategoryList() {
        return movieCategoryList;
    }

    public void setMovieCategoryList(List<MovieCategories> movieCategoryList) {
        this.movieCategoryList = movieCategoryList;
    }

    private void addMoviePrice(Double price, String ageType) throws IllegalArgumentException {
        if (ageType.toLowerCase().equals(AGETYPE_ADULT)) {
            this.moviePriceList.add(new MoviePrice(price, ageType,
                    ADULT_MIN_AGE, ADULT_MAX_AGE));
        } else if (ageType.toLowerCase().equals(AGETYPE_CHILD)) {
            this.moviePriceList.add(new MoviePrice(price, ageType,
                    CHILDREN_MIN_AGE, CHILDREN_MAX_AGE));
        } else {
            throw new IllegalArgumentException("AgeType can't be " + ageType);
        }
    }

    public Double getPriceByAgeType(String ageType) {
        Double ret = null;
        for (MoviePrice mp : moviePriceList) {
            if (mp.getAgeType().equals(ageType)) {
                ret = mp.getPrice();
                break;
            }
        }
        return ret;
    }

    public Double getPriceByAge(Byte age) {
        Double ret = null;
        for (MoviePrice mp : moviePriceList) {
            if (mp.getMaxAge() <= age && age < mp.getMaxAge()) {
                ret = mp.getPrice();
                break;
            }
        }
        return ret;
    }

    public Integer getId() {
        return id;
    }

    public List<MoviePrice> getMoviePriceList() {
        return moviePriceList;
    }

    public void setMoviePriceList(List<MoviePrice> moviePriceList) {
        this.moviePriceList = moviePriceList;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return name;
    }

}
