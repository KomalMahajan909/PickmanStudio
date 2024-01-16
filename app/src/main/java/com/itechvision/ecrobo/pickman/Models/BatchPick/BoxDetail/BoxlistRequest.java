package com.itechvision.ecrobo.pickman.Models.BatchPick.BoxDetail;

import com.google.gson.annotations.SerializedName;

public class BoxlistRequest {

    public BoxlistRequest(String authId, String admin_id, String shop_id, String batch_id) {
        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.batch_id = batch_id;
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

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    @SerializedName("authId")
    private String  authId ;

    @SerializedName("admin_id")
    private String  admin_id ;

    @SerializedName("shop_id")
    private String  shop_id ;

    @SerializedName("batch_id")
    private String  batch_id ;



}
