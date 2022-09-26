package entity;

import java.time.LocalDate;

public class Order {
   String orderID;
   LocalDate orderDate;
   String cusID;
   double amount;
    public Order(String orderID, LocalDate orderDate, String cusID) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.cusID = cusID;
    }

    public Order(String orderID, LocalDate orderDate, String cusID, double amount) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.cusID = cusID;
        this.amount = amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
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


}
