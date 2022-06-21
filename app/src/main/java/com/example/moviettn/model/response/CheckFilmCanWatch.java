package com.example.moviettn.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckFilmCanWatch {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("succes")
    @Expose
    private Boolean succes;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("canWatch")
    @Expose
    private Boolean canWatch;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getCanWatch() {
        return canWatch;
    }

    public void setCanWatch(Boolean canWatch) {
        this.canWatch = canWatch;
    }
}
