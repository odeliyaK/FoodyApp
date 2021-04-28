package com.foodyapp;

public class usersInfo {


    private int packageNum;
    private String name;
    private String address;
    private int image;


    public usersInfo(int packageNum,String name, String address, int image) {
        this.packageNum = packageNum;
        this.name = name;
        this.address = address;
        this.image = image;

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

    public int getImage() {
        return image;
    }


    public void setNun(String name) {
        this.packageNum = packageNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
