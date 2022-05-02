package com.example.moviettn.model.request;

public class FeedBack {
    private String fullname;
    private String email;
    private String subject;
    private String content;

    public FeedBack(String fullname, String email, String subject, String content) {
        this.fullname = fullname;
        this.email = email;
        this.subject = subject;
        this.content = content;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
