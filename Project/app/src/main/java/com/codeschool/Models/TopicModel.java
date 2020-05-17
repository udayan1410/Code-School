package com.codeschool.Models;

import androidx.annotation.NonNull;

public class TopicModel {
    private String topicName;
    private String topicInfo;
    private Integer isTopicCompleted;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicInfo() {
        return topicInfo;
    }

    public void setTopicInfo(String topicInfo) {
        this.topicInfo = topicInfo;
    }

    public Integer isTopicCompleted() {
        return isTopicCompleted;
    }

    public void setTopicCompleted(Integer topicCompleted) {
        isTopicCompleted = topicCompleted;
    }


    @NonNull
    @Override
    public String toString() {
        return "Name: "+topicName+" info: "+topicInfo+" iscompleted: "+isTopicCompleted;

    }
}
