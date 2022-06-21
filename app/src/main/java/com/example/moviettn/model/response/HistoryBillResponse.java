package com.example.moviettn.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryBillResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("succes")
    @Expose
    private Boolean succes;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("history")
    @Expose
    private List<HistoryBill> history = null;

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

    public List<HistoryBill> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryBill> history) {
        this.history = history;
    }

}
