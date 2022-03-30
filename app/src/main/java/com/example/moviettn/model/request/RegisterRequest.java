package com.example.moviettn.model.request;

import java.io.Serializable;

public class RegisterRequest implements Serializable {
    private String fullname;
    private String email;
    private String password;
    private int sex;
    private String date_of_birth;
    private String phone_number;

    public RegisterRequest(String fullname, String email, String password, int sex, String date_of_birth, String phone_number) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.date_of_birth = date_of_birth;
        this.phone_number = phone_number;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
