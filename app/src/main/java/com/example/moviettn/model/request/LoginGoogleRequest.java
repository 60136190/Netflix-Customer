package com.example.moviettn.model.request;

public class LoginGoogleRequest {
    private String tokenId;

    public LoginGoogleRequest(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
