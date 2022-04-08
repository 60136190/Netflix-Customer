package com.example.moviettn.model.request;

public class UpdateStateUserRequest {
    String adult;

    public UpdateStateUserRequest(String adult) {
        this.adult = adult;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }
}
