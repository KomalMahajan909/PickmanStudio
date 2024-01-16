package com.itechvision.ecrobo.pickman.Models.Logout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 21-01-2020.
 */

public class LogoutRequest {
    @SerializedName("authId")
    private String authId;

    @SerializedName("admin_id")
    private String admin_id;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public LogoutRequest(String authId, String admin_id) {

        this.authId = authId;
        this.admin_id = admin_id;
    }
}
