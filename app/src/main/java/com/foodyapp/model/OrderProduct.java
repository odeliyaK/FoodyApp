package com.foodyapp.model;

public class OrderProduct {

    int orderID;
    String productName;
    int quantity;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderProduct(int orderID, String productName, int quantity) {
        this.orderID = orderID;
        this.productName = productName;
        this.quantity = quantity;
    }
}
