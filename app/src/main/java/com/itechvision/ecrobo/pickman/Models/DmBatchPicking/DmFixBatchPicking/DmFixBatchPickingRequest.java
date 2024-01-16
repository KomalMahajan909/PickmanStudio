package com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmFixBatchPicking;

import com.google.gson.annotations.SerializedName;

public class DmFixBatchPickingRequest {

    @SerializedName("admin_id")
    private  String admin_id;

    @SerializedName("authId")
    private  String authId;

    @SerializedName("shop_id")
    private  String shop_id;

    @SerializedName("app_version")
    private  String app_version;

    @SerializedName("batch_id")
    private  String batch_id;

    @SerializedName("barcode")
    private  String barcode;

    @SerializedName("quantity")
    private  String quantity;

    @SerializedName("psh_id")
    private  String psh_id;

    @SerializedName("row")
    private  String row;


    public DmFixBatchPickingRequest(String admin_id, String authId, String shop_id, String app_version, String batch_id, String barcode, String quantity, String psh_id, String row) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.app_version = app_version;
        this.batch_id = batch_id;
        this.barcode = barcode;
        this.quantity = quantity;
        this.psh_id = psh_id;
        this.row = row;
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

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPsh_id() {
        return psh_id;
    }

    public void setPsh_id(String psh_id) {
        this.psh_id = psh_id;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }
}
