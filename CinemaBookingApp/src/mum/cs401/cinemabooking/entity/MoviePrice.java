package mum.cs401.cinemabooking.entity;

import mum.cs401.cinemabooking.common.RandomNumber;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class MoviePrice {

    private final Integer id;
    private Double price;
    private String ageType;
    private Byte minAge;
    private Byte maxAge;

    public MoviePrice(Double price, String ageType, Byte minAge, Byte maxAge) {
        this.id = RandomNumber.generateId();
        this.price = price;
        this.ageType = ageType;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public MoviePrice(Integer id, Double price, String ageType, Byte minAge, Byte maxAge) {
        this.id = id;
        this.price = price;
        this.ageType = ageType;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public Integer getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAgeType() {
        return ageType;
    }

    public void setAgeType(String ageType) {
        this.ageType = ageType;
    }

    public Byte getMinAge() {
        return minAge;
    }

    public void setMinAge(Byte minAge) {
        this.minAge = minAge;
    }

    public Byte getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Byte maxAge) {
        this.maxAge = maxAge;
    }

}
