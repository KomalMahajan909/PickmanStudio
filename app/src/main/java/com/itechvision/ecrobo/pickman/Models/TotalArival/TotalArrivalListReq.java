package com.itechvision.ecrobo.pickman.Models.TotalArival;

import com.google.gson.annotations.SerializedName;

public class TotalArrivalListReq {


    @SerializedName("authId")
    private String authId;

    @SerializedName("shop_id")
    private String shop_id;

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("app_version")
    private String app_version;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("schedule_date")
    private String schedule_date;

    @SerializedName("show_all")
    private String show_all;

    public TotalArrivalListReq(String authId, String shop_id, String admin_id, String app_version, String barcode, String schedule_date, String show_all) {
        this.authId = authId;
        this.shop_id = shop_id;
        this.admin_id = admin_id;
        this.app_version = app_version;
        this.barcode = barcode;
        this.schedule_date = schedule_date;
        this.show_all = show_all;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getShow_all() {
        return show_all;
    }

    public void setShow_all(String show_all) {
        this.show_all = show_all;
    }
}
