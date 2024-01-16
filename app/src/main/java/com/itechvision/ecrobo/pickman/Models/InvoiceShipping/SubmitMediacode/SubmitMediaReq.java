package com.itechvision.ecrobo.pickman.Models.InvoiceShipping.SubmitMediacode;

import com.google.gson.annotations.SerializedName;

public class SubmitMediaReq {
    @SerializedName("authId")
    private String authId ;

    @SerializedName("admin_id")
    private String admin_id ;

    @SerializedName("shop_id")
    private String shop_id ;

    @SerializedName("order_id")
    private String order_id ;

    @SerializedName("mediacode")
    private String mediacode ;

    @SerializedName("app_version")
    private String app_version ;

    public SubmitMediaReq(String authId, String admin_id, String shop_id, String order_id, String mediacode, String app_version) {
        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.order_id = order_id;
        this.mediacode = mediacode;
        this.app_version = app_version;
    }

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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMediacode() {
        return mediacode;
    }

    public void setMediacode(String mediacode) {
        this.mediacode = mediacode;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }
}
