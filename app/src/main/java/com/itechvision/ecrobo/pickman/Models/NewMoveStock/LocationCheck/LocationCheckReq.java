package com.itechvision.ecrobo.pickman.Models.NewMoveStock.LocationCheck;

import com.google.gson.annotations.SerializedName;

public class LocationCheckReq {

    @SerializedName("admin_id")
    private String admin_id;
    @SerializedName("authId")
    private String authId;
    @SerializedName("shop_id")
    private String shop_id;
    @SerializedName("app_version")
    private String app_version;

    @SerializedName("location")
    private String location;

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
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

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocationCheckReq(String admin_id, String authId, String shop_id, String app_version, String location) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.app_version = app_version;
        this.location = location;
    }
}
