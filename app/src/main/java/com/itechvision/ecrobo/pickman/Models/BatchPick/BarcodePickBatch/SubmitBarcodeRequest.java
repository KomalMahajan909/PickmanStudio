package com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 24-01-2020.
 */

public class SubmitBarcodeRequest {


    @SerializedName("authId")
    private String authId;

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("shop_id")
    private String shop_id;

    @SerializedName("create_date")
    private String create_date;

    @SerializedName("batch_id")
    private String batch_id;

    @SerializedName("psh_id")
    private String psh_id;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("auto_scan")
    private String auto_scan;

    @SerializedName("tas_flag")
    private String tas_flag;

    @SerializedName("app_version")
    private String app_version;

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

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getPsh_id() {
        return psh_id;
    }

    public void setPsh_id(String psh_id) {
        this.psh_id = psh_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getAuto_scan() {
        return auto_scan;
    }

    public void setAuto_scan(String auto_scan) {
        this.auto_scan = auto_scan;
    }

    public String getTas_flag() {
        return tas_flag;
    }

    public void setTas_flag(String tas_flag) {
        this.tas_flag = tas_flag;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }


    public SubmitBarcodeRequest(String authId, String admin_id, String shop_id, String create_date, String batch_id, String psh_id, String quantity, String order_id, String product_id, String auto_scan, String tas_flag, String app_version) {
        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.create_date = create_date;
        this.batch_id = batch_id;
        this.psh_id = psh_id;
        this.quantity = quantity;
        this.order_id = order_id;
        this.product_id = product_id;
        this.auto_scan = auto_scan;
        this.tas_flag = tas_flag;
        this.app_version = app_version;
    }


}
