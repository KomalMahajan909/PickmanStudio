package com.itechvision.ecrobo.pickman.Models.BatchPick;

import com.google.gson.annotations.SerializedName;

public class BatchPick_Request {

    @SerializedName("authId")
    private String authId;

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("shop_id")
    private String shop_id;

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

    public BatchPick_Request(String authId, String admin_id, String shop_id, String create_date) {

        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.create_date = create_date;
    }

    @SerializedName("create_date")
    private String create_date;


}
