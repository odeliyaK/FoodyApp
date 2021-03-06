package com.foodyapp.model;

import java.util.Date;

public class Products {

    String name;
    int quantity;
    String update;
    String supplier;

    public Products() {
    }

    public Products(String name, int quantity, String supplier) {
        this.name = name;
        this.quantity = quantity;
        this.supplier = supplier;
    }

    public Products(String name, int quantity, String update, String supplier) {
        this.name = name;
        this.quantity = quantity;
        this.update = update;
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

}
