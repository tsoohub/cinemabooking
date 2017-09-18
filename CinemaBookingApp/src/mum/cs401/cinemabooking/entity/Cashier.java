package mum.cs401.cinemabooking.entity;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class Cashier extends Person {

    private final Double salary;
    private final String phone;

    public Cashier(Double salary, String phone, String firstName,
            String lastName, String middleName, String email) {
        super(firstName, lastName, middleName, email);
        this.salary = salary;
        this.phone = phone;
    }

    public Cashier(Integer id, Double salary, String phone, String firstName,
            String lastName, String middleName, String email) {
        super(id, firstName, lastName, middleName, email);
        this.salary = salary;
        this.phone = phone;
    }

    public Double getSalary() {
        return salary;
    }

    public String getPhone() {
        return phone;
    }

}
