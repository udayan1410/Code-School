package com.codeschool.Models;

public class SignupStatus extends Status {
    private String id;

    public SignupStatus(String status, String id) {
        super(status);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
