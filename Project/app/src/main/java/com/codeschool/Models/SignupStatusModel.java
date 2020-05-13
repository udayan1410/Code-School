
package com.codeschool.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupStatusModel {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("id")
    @Expose
    private String id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
