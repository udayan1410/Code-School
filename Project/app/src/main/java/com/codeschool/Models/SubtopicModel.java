package com.codeschool.Models;

public class SubtopicModel {

    String subtopicName;
    Integer isSubtopicCompleted;
    String imageLink;

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getSubtopicName() {
        return subtopicName;
    }

    public void setSubtopicName(String subtopicName) {
        this.subtopicName = subtopicName;
    }

    public Integer getIsSubtopicCompleted() {
        return isSubtopicCompleted;
    }

    public void setIsSubtopicCompleted(Integer isSubtopicCompleted) {
        this.isSubtopicCompleted = isSubtopicCompleted;
    }
}
