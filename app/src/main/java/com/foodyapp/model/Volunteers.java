package com.foodyapp.model;

public class Volunteers {
    String email;
    String name;
    String lastName;
    String phone;

    public Volunteers() {
    }

    public Volunteers(String email, String name, String phone, String lastName) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
