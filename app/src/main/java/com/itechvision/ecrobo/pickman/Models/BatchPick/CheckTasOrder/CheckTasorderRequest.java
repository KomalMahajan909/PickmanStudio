package com.itechvision.ecrobo.pickman.Models.BatchPick.CheckTasOrder;

import com.google.gson.annotations.SerializedName;

public class CheckTasorderRequest {

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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public CheckTasorderRequest(String authId, String admin_id, String shop_id, String create_date, String order_id) {
        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.create_date = create_date;
        this.order_id = order_id;
    }

    @SerializedName("authId")
    private String authId ;

    @SerializedName("admin_id")
    private String admin_id ;

    @SerializedName("shop_id")
    private String shop_id ;

    @SerializedName("create_date")
    private String create_date ;

    @SerializedName("order_id")
    private String order_id ;

}
