package view.tdm;

import java.time.LocalDate;

public class OrderTM {
    String orderID;
    LocalDate orderDate;
    String cusID;
    double amount;

    public OrderTM(String orderID, LocalDate orderDate, String cusID, double amount) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.cusID = cusID;
        this.amount = amount;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
