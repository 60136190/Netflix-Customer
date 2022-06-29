package com.example.moviettn.model.request;

public class CheckPassword {
    String password;

    public CheckPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
