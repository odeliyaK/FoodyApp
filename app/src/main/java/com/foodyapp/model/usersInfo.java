package com.foodyapp.model;

public class usersInfo {


    private String packageNum;
    private String name;
    private String address;
    private String id;

    public usersInfo(){
    }

    public usersInfo(String name, String address, String id) {
        this.name = name;
        this.address = address;
        this.id = id;
    }
    public usersInfo(String name, String address){
        this.name = name;
        this.address = address;
    }

    public usersInfo(String packageNum, String name, String address, String id) {
        this.packageNum = packageNum;
        this.name = name;
        this.address = address;
        this.id = id;
    }

    //    public usersInfo(String packageNum,String name, String address, int image) {
//        this.packageNum = packageNum;
//        this.name = name;
//        this.address = address;
//        this.image = image;
//
//    }

    public String getNum() {
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



    public void setNum(String num) {
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

    public String toStringOrg() {
        return "usersInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
