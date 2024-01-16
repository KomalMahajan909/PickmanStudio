package com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnOrderId;

import com.google.gson.annotations.SerializedName;

public class ReturnOrderIDReq {

    @SerializedName("admin_id")
    String admin_id;

    @SerializedName("authId")
    String authId;

    @SerializedName("shop_id")
    String shop_id;

    @SerializedName("app_version")
    String app_version;

    @SerializedName("order_id")
    String order_id;

    @SerializedName("sort_by")
    String sort_by;

    @SerializedName("tracking_code")
    String tracking_code;

    public ReturnOrderIDReq(String admin_id, String authId, String shop_id, String app_version, String order_id ,String sortBy, String tracking_code) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.app_version = app_version;
        this.order_id = order_id;
        this.sort_by = sortBy;
        this.tracking_code = tracking_code;
    }

    public String getTracking_code() {
        return tracking_code;
    }

    public void setTracking_code(String tracking_code) {
        this.tracking_code = tracking_code;
    }

    public String getSort_by() {
        return sort_by;
    }

    public void setSort_by(String sort_by) {
        this.sort_by = sort_by;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
