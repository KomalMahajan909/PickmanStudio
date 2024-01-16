package com.itechvision.ecrobo.pickman.Models.NewKoguchi.NewkoguchiPrint;

import com.google.gson.annotations.SerializedName;

public class NewKoguchiPrintRequest {

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("shop_id")
    private String shop_id;

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("authId")
    private String authID;

    public NewKoguchiPrintRequest(String order_id, String shop_id, String admin_id, String authID) {
        this.order_id = order_id;
        this.shop_id = shop_id;
        this.admin_id = admin_id;
        this.authID = authID;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }
}
