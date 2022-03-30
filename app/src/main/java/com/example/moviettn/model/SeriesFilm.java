package com.example.moviettn.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SeriesFilm implements Serializable {
    @SerializedName("episode")
    @Expose
    private Integer episode;
    @SerializedName("public_id_video")
    @Expose
    private String publicIdVideo;
    @SerializedName("url_video")
    @Expose
    private String urlVideo;
    @SerializedName("public_id_image")
    @Expose
    private String publicIdImage;
    @SerializedName("url_image")
    @Expose
    private String urlImage;
    @SerializedName("_id")
    @Expose
    private String id;

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public String getPublicIdVideo() {
        return publicIdVideo;
    }

    public void setPublicIdVideo(String publicIdVideo) {
        this.publicIdVideo = publicIdVideo;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getPublicIdImage() {
        return publicIdImage;
    }

    public void setPublicIdImage(String publicIdImage) {
        this.publicIdImage = publicIdImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
