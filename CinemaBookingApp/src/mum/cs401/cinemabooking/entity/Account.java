package mum.cs401.cinemabooking.entity;

import java.util.Date;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class Account {

    private final String username;
    private final String password;
    private final Date createDate;

    public Account(String username, String password, Date createDate) {
        this.username = username;
        this.password = password;
        this.createDate = createDate;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
