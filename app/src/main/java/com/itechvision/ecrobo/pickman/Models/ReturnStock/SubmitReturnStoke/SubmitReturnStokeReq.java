package com.itechvision.ecrobo.pickman.Models.ReturnStock.SubmitReturnStoke;

import com.google.gson.annotations.SerializedName;

public class SubmitReturnStokeReq {

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("mode")
    private String mode;

    @SerializedName("tracking_code")
    private String tracking_code;

    @SerializedName("reason")
    private String reason;

    @SerializedName("shipping_method")
    private String shipping_method;

    @SerializedName("time_taken")
    private String time_taken;

    @SerializedName("order_sub_id")
    private String order_sub_id;


    @SerializedName("condition_id")
    private String condition_id;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("code")
    private String code;

    @SerializedName("shipping_price")
    private String shipping_price;

    @SerializedName("lot")
    private String lot;

    @SerializedName("expiration_date")
    private String expiration_date;

    @SerializedName("authId")
    private String authId ;

    @SerializedName("shop_id")
    private String shop_id ;

    @SerializedName("app_version")
    private String app_version ;

    public SubmitReturnStokeReq(String admin_id, String order_id, String mode, String tracking_code, String reason, String shipping_method, String time_taken, String order_sub_id, String condition_id, String quantity, String code, String shipping_price, String lot, String expiration_date, String authId, String shop_id, String app_version) {
        this.admin_id = admin_id;
        this.order_id = order_id;
        this.mode = mode;
        this.tracking_code = tracking_code;
        this.reason = reason;
        this.shipping_method = shipping_method;
        this.time_taken = time_taken;
        this.order_sub_id = order_sub_id;
        this.condition_id = condition_id;
        this.quantity = quantity;
        this.code = code;
        this.shipping_price = shipping_price;
        this.lot = lot;
        this.expiration_date = expiration_date;
        this.authId = authId;
        this.shop_id = shop_id;
        this.app_version = app_version;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTracking_code() {
        return tracking_code;
    }

    public void setTracking_code(String tracking_code) {
        this.tracking_code = tracking_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getShipping_method() {
        return shipping_method;
    }

    public void setShipping_method(String shipping_method) {
        this.shipping_method = shipping_method;
    }

    public String getTime_taken() {
        return time_taken;
    }

    public void setTime_taken(String time_taken) {
        this.time_taken = time_taken;
    }

    public String getOrder_sub_id() {
        return order_sub_id;
    }

    public void setOrder_sub_id(String order_sub_id) {
        this.order_sub_id = order_sub_id;
    }

    public String getCondition_id() {
        return condition_id;
    }

    public void setCondition_id(String condition_id) {
        this.condition_id = condition_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(String shipping_price) {
        this.shipping_price = shipping_price;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
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

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }
}
