package com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BatchListModel;

import com.google.gson.annotations.SerializedName;

public class BatchListRequest {
    @SerializedName("admin_id")
    private  String admin_id;

    @SerializedName("authId")
    private  String authId;

    @SerializedName("shop_id")
    private  String shop_id;

    @SerializedName("app_version")
    private  String app_version;

    @SerializedName("create_date")
    private  String create_date;

    @SerializedName("status")
    private  String status;

    @SerializedName("batch_remarks")
    private  String batch_remarks;


    public BatchListRequest(String admin_id, String authId, String shop_id, String app_version, String create_date, String status, String batch_remarks) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.app_version = app_version;
        this.create_date = create_date;
        this.status = status;
        this.batch_remarks = batch_remarks;
    }

    public String getBatch_remarks() {
        return batch_remarks;
    }

    public void setBatch_remarks(String batch_remarks) {
        this.batch_remarks = batch_remarks;
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

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
