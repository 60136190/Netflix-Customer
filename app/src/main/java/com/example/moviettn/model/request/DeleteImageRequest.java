package com.example.moviettn.model.request;

public class DeleteImageRequest {
    private String public_id;

    public DeleteImageRequest(String public_id) {
        this.public_id = public_id;
    }

    public String getPublic_id() {
        return public_id;
    }

    public void setPublic_id(String public_id) {
        this.public_id = public_id;
    }
}
