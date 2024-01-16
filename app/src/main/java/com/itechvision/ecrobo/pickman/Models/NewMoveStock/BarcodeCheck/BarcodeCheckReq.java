package com.itechvision.ecrobo.pickman.Models.NewMoveStock.BarcodeCheck;

import com.google.gson.annotations.SerializedName;

public class BarcodeCheckReq {

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

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("check_length")
    private String check_length;

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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCheck_length() {
        return check_length;
    }

    public void setCheck_length(String check_length) {
        this.check_length = check_length;
    }

    public BarcodeCheckReq(String admin_id, String authId, String shop_id, String app_version, String location, String barcode, String check_length) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.app_version = app_version;
        this.location = location;
        this.barcode = barcode;
        this.check_length = check_length;
    }
}
