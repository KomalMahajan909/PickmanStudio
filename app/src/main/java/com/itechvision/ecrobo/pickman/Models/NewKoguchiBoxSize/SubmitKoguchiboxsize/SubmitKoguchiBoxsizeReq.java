package com.itechvision.ecrobo.pickman.Models.NewKoguchiBoxSize.SubmitKoguchiboxsize;

import com.google.gson.annotations.SerializedName;

public class SubmitKoguchiBoxsizeReq {

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("authId")
    private String authId;

    @SerializedName("shop_id")
    private String shop_id;

    @SerializedName("boxsize")
    private String boxsize;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("tracking_no")
    private String tracking_no;

    public SubmitKoguchiBoxsizeReq(String admin_id, String authId, String shop_id, String boxsize, String order_id, String tracking_no) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.boxsize = boxsize;
        this.order_id = order_id;
        this.tracking_no = tracking_no;
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

    public String getBoxsize() {
        return boxsize;
    }

    public void setBoxsize(String boxsize) {
        this.boxsize = boxsize;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTracking_no() {
        return tracking_no;
    }

    public void setTracking_no(String tracking_no) {
        this.tracking_no = tracking_no;
    }
}
