package com.itechvision.ecrobo.pickman.Models.NewReturnClassification;

import com.google.gson.annotations.SerializedName;

public class GetReturnClassificationReq {
    @SerializedName("authId")
    String authId;

    @SerializedName("shop_id")
    String shop_id;

    @SerializedName("admin_id")
    String admin_id;

    @SerializedName("app_version")
    String app_version;

    public GetReturnClassificationReq(String authId, String shop_id, String admin_id, String app_version) {
        this.authId = authId;
        this.shop_id = shop_id;
        this.admin_id = admin_id;
        this.app_version = app_version;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }
}
