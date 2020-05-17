package com.codeschool.Models;

public class SubtopicDescriptionModel {

    private String subtopicName;
    private String subtopicImage;
    private String subtopicInfo;

    public SubtopicDescriptionModel(){

    }

    public SubtopicDescriptionModel(String subtopicName, String subtopicImage, String subtopicInfo) {
        this.subtopicName = subtopicName;
        this.subtopicImage = subtopicImage;
        this.subtopicInfo = subtopicInfo;
    }

    public String getSubtopicName() {
        return subtopicName;
    }

    public void setSubtopicName(String subtopicName) {
        this.subtopicName = subtopicName;
    }

    public String getSubtopicImage() {
        return subtopicImage;
    }

    public void setSubtopicImage(String subtopicImage) {
        this.subtopicImage = subtopicImage;
    }

    public String getSubtopicInfo() {
        return subtopicInfo;
    }

    public void setSubtopicInfo(String subtopicInfo) {
        this.subtopicInfo = subtopicInfo;
    }

    @Override
    public String toString() {
        return "SubtopicDescriptionModel{" +
                "subtopicName='" + subtopicName + '\'' +
                ", subtopicImage='" + subtopicImage + '\'' +
                ", subtopicInfo='" + subtopicInfo + '\'' +
                '}';
    }
}
