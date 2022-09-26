package view.tdm;

import java.awt.*;
import java.math.BigDecimal;

public class OrderDetailTM {
    private String itemCode;
    private String description;
    private int qtyOnHand;
    private int qty;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private Button btn;

    /*public OrderDetailTM(String itemCode, String description, int qtyOnHand, BigDecimal unitPrice, Button btn) {
        this.itemCode = itemCode;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.btn = btn;
    }*/

    public OrderDetailTM(String itemCode, String description, int qtyOnHand, BigDecimal unitPrice, BigDecimal total) {
        this.itemCode = itemCode;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.total = total;
    }

  /*  public OrderDetailTM(String itemCode, String description, int qtyOnHand, BigDecimal unitPrice) {
        this.itemCode = itemCode;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
    }*/

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
