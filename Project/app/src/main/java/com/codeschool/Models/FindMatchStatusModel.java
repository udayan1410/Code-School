
package com.codeschool.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FindMatchStatusModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sessionId")
    @Expose
    private String sessionId;

    public FindMatchStatusModel(String status, String sessionId) {
        this.status = status;
        this.sessionId = sessionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "FindMatchStatusModel{" +
                "status='" + status + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
