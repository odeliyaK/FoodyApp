package com.foodyapp.model;


import java.util.Date;

public class Order {

    String id;
    String supplier;
    String date;

    public Order() {
    }

    public Order(String id, String supplier, String date) {
        this.id = id;
        this.supplier = supplier;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
