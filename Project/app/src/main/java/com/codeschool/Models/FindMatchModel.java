package com.codeschool.Models;

import androidx.annotation.NonNull;

public class FindMatchModel {

    private String course,id,userName;

    public FindMatchModel(String course, String id,String userName) {
        this.course = course;
        this.id = id;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
