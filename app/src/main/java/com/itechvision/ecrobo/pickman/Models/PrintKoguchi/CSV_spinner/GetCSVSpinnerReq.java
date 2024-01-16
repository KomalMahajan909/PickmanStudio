package com.itechvision.ecrobo.pickman.Models.PrintKoguchi.CSV_spinner;

import com.google.gson.annotations.SerializedName;

public class GetCSVSpinnerReq {

    @SerializedName("authId")
    String authId;

    @SerializedName("shop_id")
    String shop_id;

    @SerializedName("admin_id")
    String admin_id;

    public GetCSVSpinnerReq(String authId, String shop_id, String admin_id) {
        this.authId = authId;
        this.shop_id = shop_id;
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

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }
}
