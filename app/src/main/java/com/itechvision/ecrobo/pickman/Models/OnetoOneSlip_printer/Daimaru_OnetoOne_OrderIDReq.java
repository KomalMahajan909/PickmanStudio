package com.itechvision.ecrobo.pickman.Models.OnetoOneSlip_printer;

import com.google.gson.annotations.SerializedName;

public class Daimaru_OnetoOne_OrderIDReq {

    @SerializedName("admin_id")
    private String admin_id ;

    @SerializedName("app_version")
    private String app_version ;

    @SerializedName("authId")
    private String authId ;

    @SerializedName("batch_id")
    private String batch_id ;

    @SerializedName("shop_id")
    private String shop_id ;

    @SerializedName("barcode")
    private String barcode ;

    @SerializedName("date_time")
    private String date_time ;


    public Daimaru_OnetoOne_OrderIDReq(String admin_id, String app_version, String authId, String batch_id, String shop_id, String barcode, String date_time) {
        this.admin_id = admin_id;
        this.app_version = app_version;
        this.authId = authId;
        this.batch_id = batch_id;
        this.shop_id = shop_id;
        this.barcode = barcode;
        this.date_time = date_time;
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

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
