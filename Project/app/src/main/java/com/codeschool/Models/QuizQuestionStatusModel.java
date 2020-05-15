
package com.codeschool.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class QuizQuestionStatusModel {

    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("options")
    @Expose
    private List<String> options = null;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("playerStatus")
    @Expose
    private List<Playerstatus> playerStatus = null;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Playerstatus> getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(List<Playerstatus> playerStatus) {
        this.playerStatus = playerStatus;
    }

    @NonNull
    @Override
    public String toString() {
        return "question: "+question+" options: "+options+" answer: "+answer+" playerStatus: "+playerStatus;
    }
}
