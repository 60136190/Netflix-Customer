package com.example.moviettn.model.response;

import com.example.moviettn.model.DetailFilm;
import com.example.moviettn.model.Film;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DetailFilmResponse implements Serializable {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<DetailFilm> data = null;
    @SerializedName("avg_score")
    @Expose
    private String avgScore;
    @SerializedName("numRatings")
    @Expose
    private Integer numRatings;
    @SerializedName("msg")
    @Expose
    private String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<DetailFilm> getData() {
        return data;
    }

    public void setData(List<DetailFilm> data) {
        this.data = data;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }

    public Integer getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(Integer numRatings) {
        this.numRatings = numRatings;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
