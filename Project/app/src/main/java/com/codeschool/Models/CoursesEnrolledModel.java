package com.codeschool.Models;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class CoursesEnrolledModel {


    List<String> coursesEnrolled;
    List<String> coursesEnrolledInformation;
    List<Integer> courseImage;

    public List<String> getCoursesEnrolledInformation() {
        return coursesEnrolledInformation;
    }

    public CoursesEnrolledModel(){
        coursesEnrolled = new ArrayList<>();
        coursesEnrolledInformation = new ArrayList<>();
        courseImage = new ArrayList<>();
    }

    public List<Integer> getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(List<Integer> courseImage) {
        this.courseImage = courseImage;
    }

    public void setCoursesEnrolledInformation(List<String> coursesEnrolledInformation) {
        this.coursesEnrolledInformation = coursesEnrolledInformation;
    }

    public CoursesEnrolledModel(List<String> coursesEnrolled) {
        this.coursesEnrolled = coursesEnrolled;
    }

    public List<String> getCoursesEnrolled() {
        return coursesEnrolled;
    }

    public void setCoursesEnrolled(List<String> coursesEnrolled) {
        this.coursesEnrolled = coursesEnrolled;
    }

    @NonNull
    @Override
    public String toString() {
        return "List enrolled : "+coursesEnrolled+" info: "+coursesEnrolledInformation;
    }
}
