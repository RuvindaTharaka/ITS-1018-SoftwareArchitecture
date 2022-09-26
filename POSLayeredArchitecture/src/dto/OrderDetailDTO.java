package dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetailDTO implements Serializable {
    private String oid;
    private String itemCode;
    private int qty;
    private BigDecimal price;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String oid, String itemCode, int qty, BigDecimal price) {
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

    public void setUnitPrice(BigDecimal unitPrice) {
        this.price = unitPrice;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OrderDetailDTO{");
        sb.append("oid='").append(oid).append('\'');
        sb.append(", itemCode='").append(itemCode).append('\'');
        sb.append(", qty=").append(qty);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
