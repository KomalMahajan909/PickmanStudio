package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals;

import com.google.gson.annotations.SerializedName;

public class NextBoxReq {
    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("authId")
    private String authId;

    @SerializedName("shop_id")
    private String shop_id;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("koguchi_weight")
    private String koguchi_weight;

    public NextBoxReq(String admin_id, String authId, String shop_id, String order_id, String koguchi_weight) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.order_id = order_id;
        this.koguchi_weight = koguchi_weight;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getKoguchi_weight() {
        return koguchi_weight;
    }

    public void setKoguchi_weight(String koguchi_weight) {
        this.koguchi_weight = koguchi_weight;
    }
}
