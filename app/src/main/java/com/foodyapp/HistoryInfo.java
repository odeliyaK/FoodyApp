package com.foodyapp;

public class HistoryInfo {

    private int packageNum;
    private String name;
    private String address;
    private String Date;

    public HistoryInfo(int packageNum,String name, String address, String Date) {
        super();
        this.packageNum = packageNum;
        this.name = name;
        this.address = address;
        this.Date = Date;

    }


    public int getNum() {
        return packageNum;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String  getDate() {
        return Date;
    }


    public void setNum(int packageNum) {
        this.packageNum = packageNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
}
