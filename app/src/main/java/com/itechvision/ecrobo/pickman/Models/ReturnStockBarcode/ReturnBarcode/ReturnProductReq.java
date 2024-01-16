package com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnBarcode;

import com.google.gson.annotations.SerializedName;

public class ReturnProductReq {


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

    @SerializedName("sendback_id")
    String sendback_id;

    @SerializedName("barcode")
    String barcode;

    public ReturnProductReq(String admin_id, String authId, String shop_id, String app_version, String order_id, String sendback_id, String barcode) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.app_version = app_version;
        this.order_id = order_id;
        this.sendback_id = sendback_id;
        this.barcode = barcode;
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

    public String getSendback_id() {
        return sendback_id;
    }

    public void setSendback_id(String sendback_id) {
        this.sendback_id = sendback_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
