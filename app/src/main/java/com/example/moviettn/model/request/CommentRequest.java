package com.example.moviettn.model.request;

public class CommentRequest {
    private String content;

    public CommentRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
