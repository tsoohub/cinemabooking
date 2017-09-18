package mum.cs401.cinemabooking.entity;

import java.util.Date;
import java.util.List;
import mum.cs401.cinemabooking.common.RandomNumber;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class Order {

    private final Integer id;
    private final String secretCode;
    private final Date secretCodeExpireDate;
    private List<Ticket> ticketList;
    private Date stateDate;
    private Byte state;
    private final String paymentType;
    private Double amount;

    public Order(Date secretCodeExpireDate, List<Ticket> ticketList,
            Date stateDate, Byte state, String paymentType, Double amount) {
        this.id = RandomNumber.generateId();
        this.secretCode = RandomNumber.generateSecretCode();
        this.secretCodeExpireDate = secretCodeExpireDate;
        this.ticketList = ticketList;
        this.stateDate = stateDate;
        this.state = state;
        this.paymentType = paymentType;
        this.amount = amount;
    }

    public Order(Integer id, Date secretCodeExpireDate, List<Ticket> ticketList,
            Date stateDate, Byte state, String paymentType, Double amount) {
        this.id = id;
        this.secretCode = RandomNumber.generateSecretCode();
        this.secretCodeExpireDate = secretCodeExpireDate;
        this.ticketList = ticketList;
        this.stateDate = stateDate;
        this.state = state;
        this.paymentType = paymentType;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public Date getSecretCodeExpireDate() {
        return secretCodeExpireDate;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public Date getStateDate() {
        return stateDate;
    }

    public Byte getState() {
        return state;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
