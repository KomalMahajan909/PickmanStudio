package com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckCustomer;

import com.google.gson.annotations.SerializedName;

public class CustomerCheckReq {

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("authId")
    private String authId;
    @SerializedName("shop_id")
    private String shop_id;
    @SerializedName("app_version")
    private String app_version;
    @SerializedName("customer_no")
    private String customer_no;

    public CustomerCheckReq(String admin_id, String authId, String shop_id, String app_version, String customer_no) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.app_version = app_version;
        this.customer_no = customer_no;
    }

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

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }
}
