package com.itechvision.ecrobo.pickman.Models.NewMoveStock.SubmitStock;

import com.google.gson.annotations.SerializedName;

public class SubmitStockReq {

    @SerializedName("admin_id")
    private String admin_id;
    @SerializedName("authId")
    private String authId;
    @SerializedName("shop_id")
    private String shop_id;
    @SerializedName("app_version")
    private String app_version;

    @SerializedName("source")
    private String source;

    @SerializedName("destination")
    private String destination;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("qty")
    private String qty;



    @SerializedName("product_id")
    private String product_id;

    @SerializedName("time_taken")
    private String time_taken;

    @SerializedName("check_length")
    private String check_length;

    public SubmitStockReq(String admin_id, String authId, String shop_id, String app_version, String source, String destination, String barcode, String qty, String product_id, String time_taken, String check_length) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.app_version = app_version;
        this.source = source;
        this.destination = destination;
        this.barcode = barcode;
        this.qty = qty;

        this.product_id = product_id;
        this.time_taken = time_taken;
        this.check_length = check_length;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTime_taken() {
        return time_taken;
    }

    public void setTime_taken(String time_taken) {
        this.time_taken = time_taken;
    }

    public String getCheck_length() {
        return check_length;
    }

    public void setCheck_length(String check_length) {
        this.check_length = check_length;
    }
}
