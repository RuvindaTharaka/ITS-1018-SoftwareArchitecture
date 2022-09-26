package dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO implements Serializable {
    private String orderId;
    private LocalDate orderDate;
    private String customerId;
    private String customerName;
    private BigDecimal orderTotal;
    private List<OrderDetailDTO> orderDetail;
    private double amount;

    public OrderDTO(String orderId, LocalDate orderDate, String customerId, String customerName, BigDecimal orderTotal, List<OrderDetailDTO> orderDetail, double amount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.customerName = customerName;
        this.orderTotal = orderTotal;
        this.orderDetail = orderDetail;
        this.amount = amount;
    }

    public OrderDTO(String orderId, LocalDate orderDate, String customerId,  List<OrderDetailDTO> orderDetail, double amount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.orderDetail = orderDetail;
        this.amount = amount;
    }
    public OrderDTO(String orderId, LocalDate orderDate, String customerId, double amount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public void setOrderDetail(ArrayList<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", orderTotal=" + orderTotal +
                '}';
    }
}
