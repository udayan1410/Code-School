
package com.codeschool.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class WinnerStatusModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("winnerId")
    @Expose
    private String winnerId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
    }


    @NonNull
    @Override
    public String toString() {
            return "status: "+status+" winner: "+winnerId;
    }
}
