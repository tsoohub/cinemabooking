package mum.cs401.cinemabooking.entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class Customer extends Person {

    private final Date birthDate;
    private final Account account;
    private final Date createDate;
    private final List<Order> orderList;

    public Customer(Date birthDate, Account account, Date createDate,
            String firstName, String lastName, String middleName, String email) {
        super(firstName, lastName, middleName, email);
        this.birthDate = birthDate;
        this.account = account;
        this.createDate = createDate;
        this.orderList = new ArrayList<>();
    }

    public Customer(Date birthDate, Account account, Date createDate,
            Integer id, String firstName, String lastName, String middleName,
            String email) {
        super(id, firstName, lastName, middleName, email);
        this.birthDate = birthDate;
        this.account = account;
        this.createDate = createDate;
        this.orderList = new ArrayList<>();
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Account getAccount() {
        return account;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public List<Order> getOrderList() {
        return orderList;
    }
}
