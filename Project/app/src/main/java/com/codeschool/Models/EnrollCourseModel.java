package com.codeschool.Models;

public class EnrollCourseModel {

    private String courseName;
    private String courseInfo;
    private int courseImage;

    public EnrollCourseModel(String courseName,String courseInfo,int courseImage){
        this.courseName = courseName;
        this.courseInfo = courseInfo;
        this.courseImage = courseImage;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseInfo() {
        return courseInfo;
    }

    public int getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(int courseImage) {
        this.courseImage = courseImage;
    }
}
