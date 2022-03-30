package com.example.moviettn.model.request;

import com.example.moviettn.model.response.Image;

import java.io.Serializable;


public class UpdateUserRequest implements Serializable {
    private String fullname;
    private Image image;
    private String phone_number;
    private int sex;
    private String date_of_birth;

    public UpdateUserRequest(String fullname, Image image, String phone_number, int sex, String date_of_birth) {
        this.fullname = fullname;
        this.image = image;
        this.phone_number = phone_number;
        this.sex = sex;
        this.date_of_birth = date_of_birth;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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
}
