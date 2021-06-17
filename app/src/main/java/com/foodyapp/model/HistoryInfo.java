package com.foodyapp.model;

public class HistoryInfo {

    private String id;
    private String packageNum;
    private String name;
    private String address;
    private String Date;

    public HistoryInfo(String id, String packageNum,String name, String address, String Date) {
        super();
        this.id = id;
        this.packageNum = packageNum;
        this.name = name;
        this.address = address;
        this.Date = Date;

    }

    public HistoryInfo(String packageNum,String name, String address) {
        super();
        this.packageNum = packageNum;
        this.name = name;
        this.address = address;
        this.Date = Date;

    }

    public HistoryInfo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(String packageNum) {
        this.packageNum = packageNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
