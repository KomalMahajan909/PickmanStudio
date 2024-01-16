package com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.CheckBarcode;

import com.google.gson.annotations.SerializedName;

public class CheckBarcodeShipReq {

    @SerializedName("authId")
    private  String authId ;

    @SerializedName("admin_id")
    private  String admin_id ;

    @SerializedName("shop_id")
    private  String shop_id ;

    @SerializedName("order_id")
    private  String order_id ;

    @SerializedName("app_version")
    private  String app_version ;

    @SerializedName("order_days_check")
    private String order_days_check;

    @SerializedName("product_category")
    private String product_category;

    @SerializedName("barcode")
    private String barcode;

    public CheckBarcodeShipReq(String authId, String admin_id, String shop_id, String order_id, String app_version, String order_days_check, String product_category, String barcode) {
        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.order_id = order_id;
        this.app_version = app_version;
        this.order_days_check = order_days_check;
        this.product_category = product_category;
        this.barcode = barcode;
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

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getOrder_days_check() {
        return order_days_check;
    }

    public void setOrder_days_check(String order_days_check) {
        this.order_days_check = order_days_check;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
