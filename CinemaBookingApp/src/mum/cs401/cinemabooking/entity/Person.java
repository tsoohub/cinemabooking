package mum.cs401.cinemabooking.entity;

import mum.cs401.cinemabooking.common.RandomNumber;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class Person {

    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String email;

    public Person(String firstName, String lastName,
            String middleName, String email) {
        this.id = RandomNumber.generateId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
    }

    public Person(Integer id, String firstName, String lastName,
            String middleName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getEmail() {
        return email;
    }

}
