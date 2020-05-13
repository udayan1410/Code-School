package com.codeschool.Models;

public class FindMatchModel {

    private String course,id;

    public FindMatchModel(String course, String id) {
        this.course = course;
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
