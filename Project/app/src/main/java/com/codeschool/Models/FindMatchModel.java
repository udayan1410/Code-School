package com.codeschool.Models;

import androidx.annotation.NonNull;

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

    @NonNull
    @Override
    public String toString() {
        return "course: "+course+" id: "+id;
    }
}
