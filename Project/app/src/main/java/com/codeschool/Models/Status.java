package com.codeschool.Models;

public class Status {

    protected String status;

    public Status(){
        status = new String("");
    }

    public Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
