package com.example.moviettn.model.response;

import java.io.Serializable;

public class Image implements Serializable {
    private String public_id;
    private String url;

    public Image(String public_id, String url) {
        this.public_id = public_id;
        this.url = url;
    }

    public String getPublic_id() {
        return public_id;
    }

    public void setPublic_id(String public_id) {
        this.public_id = public_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
