
package com.codeschool.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizQuestionStatusModel {

    @SerializedName("question")
    @Expose
    private Question question;
    @SerializedName("roomid")
    @Expose
    private String roomid;
    @SerializedName("playerData")
    @Expose
    private List<PlayerDatum> playerData = null;
    @SerializedName("gameOver")
    @Expose
    private Boolean gameOver;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public List<PlayerDatum> getPlayerData() {
        return playerData;
    }

    public void setPlayerData(List<PlayerDatum> playerData) {
        this.playerData = playerData;
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }



}
