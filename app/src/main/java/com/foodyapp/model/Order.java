package com.foodyapp.model;


import java.util.Date;

public class Order {

    int id;
    int volID;
    Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVolID() {
        return volID;
    }

    public void setVolID(int volID) {
        this.volID = volID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Order(int id, int volID, Date date) {
        this.id = id;
        this.volID = volID;
        this.date = date;
    }
}
