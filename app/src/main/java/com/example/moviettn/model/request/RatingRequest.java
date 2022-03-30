package com.example.moviettn.model.request;

public class RatingRequest {
    private float score;

    public RatingRequest(float score) {
        this.score = score;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
