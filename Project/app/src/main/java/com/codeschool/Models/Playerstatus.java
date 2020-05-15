
package com.codeschool.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class Playerstatus {

    @SerializedName("playerId")
    @Expose
    private String playerId;
    @SerializedName("playerScore")
    @Expose
    private String playerScore;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(String playerScore) {
        this.playerScore = playerScore;
    }


    @NonNull
    @Override
    public String toString() {
        return "playerID:  "+playerId+" playerScore: "+playerScore;
    }
}
