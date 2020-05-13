
package com.codeschool.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizAnswerStatusModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("isEndOfQuiz")
    @Expose
    private Boolean isEndOfQuiz;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsEndOfQuiz() {
        return isEndOfQuiz;
    }

    public void setIsEndOfQuiz(Boolean isEndOfQuiz) {
        this.isEndOfQuiz = isEndOfQuiz;
    }

}
