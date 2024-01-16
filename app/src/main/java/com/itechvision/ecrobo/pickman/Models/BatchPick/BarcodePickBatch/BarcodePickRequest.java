package com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 24-01-2020.
 */

public class BarcodePickRequest {

    @SerializedName("authId")
    private String authId;

    @SerializedName("admin_id")
    private String admin_id;

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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BarcodePickRequest(String authId, String admin_id, String shop_id, String create_date, String batch_id, String barcode) {

        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.create_date = create_date;
        this.batch_id = batch_id;
        this.barcode = barcode;
    }

    @SerializedName("shop_id")
    private String shop_id;

    @SerializedName("create_date")
    private String create_date;

    @SerializedName("batch_id")
    private String batch_id;

    @SerializedName("barcode")
    private String barcode;


}
