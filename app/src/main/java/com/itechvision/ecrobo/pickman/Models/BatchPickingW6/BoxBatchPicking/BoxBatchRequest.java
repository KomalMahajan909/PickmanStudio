package com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BoxBatchPicking;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Query;

public class BoxBatchRequest {

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("authId")
    private String authId;

    @SerializedName("shop_id")
    private String shop_id;

    @SerializedName("batch_id")
    private String batch_id;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("app_version")
    private String app_version;

    @SerializedName("create_date")
    private String create_date;

    @SerializedName("get_by")
    private String getby;

    public BoxBatchRequest(String admin_id, String authId, String shop_id, String batch_id, String order_id, String app_version, String create_date, String getby) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.batch_id = batch_id;
        this.order_id = order_id;
        this.app_version = app_version;
        this.create_date = create_date;
        this.getby = getby;
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

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
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

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getGetby() {
        return getby;
    }

    public void setGetby(String getby) {
        this.getby = getby;
    }
}

