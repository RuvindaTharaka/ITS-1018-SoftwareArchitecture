package entity;

import java.math.BigDecimal;

public class OrderDetails {
    private String oid;
    private String itemCode;
    private int qty;
    private BigDecimal price;

    public OrderDetails() {
    }

    public OrderDetails(String oid, String itemCode, int qty, BigDecimal price) {
        this.oid = oid;
        this.itemCode = itemCode;
        this.qty = qty;
        this.price = price;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return price;
    }

    public void setUnitPrice(BigDecimal price) {
        this.price = price;
    }
}
