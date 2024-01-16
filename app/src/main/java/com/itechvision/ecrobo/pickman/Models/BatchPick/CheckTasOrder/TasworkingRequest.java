package com.itechvision.ecrobo.pickman.Models.BatchPick.CheckTasOrder;

import com.google.gson.annotations.SerializedName;

public class TasworkingRequest {

    @SerializedName("authId")
    private String authId;

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("shop_id")
    private String shop_id;

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

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public TasworkingRequest(String authId, String admin_id, String shop_id) {
        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
    }
}
