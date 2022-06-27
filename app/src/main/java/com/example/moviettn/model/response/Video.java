package com.example.moviettn.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Video implements Serializable {

    public Video(String publicId, String url) {
        this.publicId = publicId;
        this.url = url;
    }

    @SerializedName("public_id_video")
    @Expose
    private String publicId;
    @SerializedName("url_video")
    @Expose
    private String url;

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
