package com.itechvision.ecrobo.pickman.Models.ReturnStock;

import com.google.gson.annotations.SerializedName;

public class ReturnStokeLotExpReq {

    @SerializedName("admin_id")
    private String admin_id ;

    @SerializedName("order_id")
    private String order_id ;

    @SerializedName("mode")
    private String mode ;

    @SerializedName("barcode")
    private String barcode ;

    @SerializedName("authId")
    private String authId ;

    @SerializedName("shop_id")
    private String shop_id ;

    @SerializedName("app_version")
    private String app_version ;

    public ReturnStokeLotExpReq(String admin_id, String order_id, String mode, String barcode, String authId, String shop_id, String app_version) {
        this.admin_id = admin_id;
        this.order_id = order_id;
        this.mode = mode;
        this.barcode = barcode;
        this.authId = authId;
        this.shop_id = shop_id;
        this.app_version = app_version;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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
}


