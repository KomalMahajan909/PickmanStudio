package com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.DirectArrival;

import com.google.gson.annotations.SerializedName;

public class DirectArrivalSubmissionRequest {

    @SerializedName("admin_id")
    private  String admin_id;

    @SerializedName("authId")
    private  String authId;

    @SerializedName("shop_id")
    private  String shop_id;

    @SerializedName("order_no")
    private  String order_no;

    @SerializedName("product_id")
    private  String product_id;

    @SerializedName("nyuka_id")
    private  String nyuka_id;

    @SerializedName("quantity")
    private  String quantity;

    @SerializedName("lot_no")
    private  String lot_no;

    @SerializedName("expiration_date")
    private  String expiration_date;

    @SerializedName("location")
    private String location;

    @SerializedName("stock_type_id")
    private  String stock_type_id;

    @SerializedName("app_version")
    private  String app_version;

    @SerializedName("time_taken")
    private  String time_taken;

    public DirectArrivalSubmissionRequest(String admin_id, String authId, String shop_id, String order_no, String product_id, String nyuka_id, String quantity, String lot_no, String expiration_date, String location, String stock_type_id, String app_version, String time_taken) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.order_no = order_no;
        this.product_id = product_id;
        this.nyuka_id = nyuka_id;
        this.quantity = quantity;
        this.lot_no = lot_no;
        this.expiration_date = expiration_date;
        this.location = location;
        this.stock_type_id = stock_type_id;
        this.app_version = app_version;
        this.time_taken = time_taken;
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

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getNyuka_id() {
        return nyuka_id;
    }

    public void setNyuka_id(String nyuka_id) {
        this.nyuka_id = nyuka_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLot_no() {
        return lot_no;
    }

    public void setLot_no(String lot_no) {
        this.lot_no = lot_no;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStock_type_id() {
        return stock_type_id;
    }

    public void setStock_type_id(String stock_type_id) {
        this.stock_type_id = stock_type_id;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getTime_taken() {
        return time_taken;
    }

    public void setTime_taken(String time_taken) {
        this.time_taken = time_taken;
    }
}
