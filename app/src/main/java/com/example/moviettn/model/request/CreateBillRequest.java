package com.example.moviettn.model.request;

public class CreateBillRequest {
    private String mode_of_payment;
    private String id_payment;
    private String filmId;
    private String price;

    public CreateBillRequest(String mode_of_payment, String id_payment, String filmId, String price) {
        this.mode_of_payment = mode_of_payment;
        this.id_payment = id_payment;
        this.filmId = filmId;
        this.price = price;
    }

    public String getMode_of_payment() {
        return mode_of_payment;
    }

    public void setMode_of_payment(String mode_of_payment) {
        this.mode_of_payment = mode_of_payment;
    }

    public String getId_payment() {
        return id_payment;
    }

    public void setId_payment(String id_payment) {
        this.id_payment = id_payment;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
