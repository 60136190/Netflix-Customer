package com.example.moviettn.model.test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {
    @SerializedName("country_production")
    @Expose
    private String countryProduction;
    @SerializedName("category")
    @Expose
    private List<Test> category = null;

    public String getCountryProduction() {
        return countryProduction;
    }

    public void setCountryProduction(String countryProduction) {
        this.countryProduction = countryProduction;
    }

    public List<Test> getCategory() {
        return category;
    }

    public void setCategory(List<Test> category) {
        this.category = category;
    }
}
