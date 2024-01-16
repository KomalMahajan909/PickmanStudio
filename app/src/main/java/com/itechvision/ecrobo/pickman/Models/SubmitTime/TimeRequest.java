package com.itechvision.ecrobo.pickman.Models.SubmitTime;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 15-01-2020.
 */

public class TimeRequest {
    @SerializedName("authId")
    private  String authId ;

    @SerializedName("admin_id")
    private  String admin_id ;

    @SerializedName("shop_id")
    private  String shop_id ;

    @SerializedName("nyuka_id")
    private  String nyuka_id ;

    @SerializedName("order_no")
    private  String order_no ;

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

    public String getNyuka_id() {
        return nyuka_id;
    }

    public void setNyuka_id(String nyuka_id) {
        this.nyuka_id = nyuka_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public TimeRequest(String authId, String admin_id, String shop_id, String nyuka_id, String order_no, String return_time) {

        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.nyuka_id = nyuka_id;
        this.order_no = order_no;
        this.return_time = return_time;
    }

    @SerializedName("return_time")
    private  String return_time ;




}
