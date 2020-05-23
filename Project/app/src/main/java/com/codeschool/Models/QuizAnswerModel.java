
package com.codeschool.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizAnswerModel {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("answer")
    @Expose
    private String answer;

    @SerializedName("sessionid")
    @Expose
    private String sessionid;

    public QuizAnswerModel(String id, String answer, String sessionid) {
        this.id = id;
        this.answer = answer;
        this.sessionid = sessionid;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
