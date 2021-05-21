package com.foodyapp.model;

public class usersInfo {


    private int packageNum;
    private String name;
    private String address;
    private String id;
    private int image;


    public usersInfo(int packageNum,String name, String address, int image) {
        this.packageNum = packageNum;
        this.name = name;
        this.address = address;
        this.image = image;

    }
    public usersInfo(String name, String address, String id) {
        this.name = name;
        this.address = address;
        this.id = id;

    }
    public int getNum() {
        return packageNum;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public int getImage() {
        return image;
    }


    public void setNum(int num) {
        this.packageNum = num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String toStringOrg() {
        return "usersInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
