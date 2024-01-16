package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxProductclickList;

import com.google.gson.annotations.SerializedName;

public class BoxProductClickRequest {

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("authId")
    private String authId;

    @SerializedName("shop_id")
    private String shop_id;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("box_no")
    private String box_no;

    public BoxProductClickRequest(String admin_id, String authId, String shop_id, String order_id, String box_no) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.order_id = order_id;
        this.box_no = box_no;
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

    public String getBox_no() {
        return box_no;
    }

    public void setBox_no(String box_no) {
        this.box_no = box_no;
    }
}
