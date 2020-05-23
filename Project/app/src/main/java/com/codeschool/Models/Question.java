
package com.codeschool.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("options")
    @Expose
    private List<String> options = null;
    @SerializedName("question")
    @Expose
    private String question;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Question{" +
                "answer='" + answer + '\'' +
                ", id=" + id +
                ", options=" + options +
                ", question='" + question + '\'' +
                '}';
    }
}
