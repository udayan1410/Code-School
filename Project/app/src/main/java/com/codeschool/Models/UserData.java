
package com.codeschool.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class UserData {

    @SerializedName("MultiPlayerStreak")
    @Expose
    private String multiPlayerStreak;
    @SerializedName("SinglePlayerStreak")
    @Expose
    private String singlePlayerStreak;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;

    public String getMultiPlayerStreak() {
        return multiPlayerStreak;
    }

    public void setMultiPlayerStreak(String multiPlayerStreak) {
        this.multiPlayerStreak = multiPlayerStreak;
    }

    public String getSinglePlayerStreak() {
        return singlePlayerStreak;
    }

    public void setSinglePlayerStreak(String singlePlayerStreak) {
        this.singlePlayerStreak = singlePlayerStreak;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @NonNull
    @Override
    public String toString() {
        return "Id : "+id+" Email:  "+email+" Username: "+username+" single: "+singlePlayerStreak+" multi: "+multiPlayerStreak;
    }
}
