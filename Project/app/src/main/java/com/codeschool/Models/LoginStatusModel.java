
package com.codeschool.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class LoginStatusModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userData")
    @Expose
    private UserData userData;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    @NonNull
    @Override
    public String toString() {
        return "Status: "+status+" userData: "+userData;
    }
}
