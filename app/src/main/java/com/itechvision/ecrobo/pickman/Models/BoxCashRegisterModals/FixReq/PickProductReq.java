package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.FixReq;

import com.google.gson.annotations.SerializedName;

public class PickProductReq {

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("authId")
    private String authId;

    @SerializedName("shop_id")
    private String shop_id;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("product_stock_history_id")
    private String product_stock_history_id;

    @SerializedName("time_taken")
    private String time_taken;

    @SerializedName("ems_box_code")
    private String ems_box_code;

    @SerializedName("shipping_date")
    private String shipping_date;

    @SerializedName("is_polythene")
    private String is_polythene;

    @SerializedName("box_no")
    private String box_no;

    @SerializedName("app_version")
    private  String app_version ;

    public PickProductReq(String admin_id, String authId, String shop_id, String order_id, String product_id, String quantity, String product_stock_history_id, String time_taken, String ems_box_code, String shipping_date, String is_polythene, String box_no, String app_version) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.product_stock_history_id = product_stock_history_id;
        this.time_taken = time_taken;
        this.ems_box_code = ems_box_code;
        this.shipping_date = shipping_date;
        this.is_polythene = is_polythene;
        this.box_no = box_no;
        this.app_version = app_version;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
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

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct_stock_history_id() {
        return product_stock_history_id;
    }

    public void setProduct_stock_history_id(String product_stock_history_id) {
        this.product_stock_history_id = product_stock_history_id;
    }

    public String getTime_taken() {
        return time_taken;
    }

    public void setTime_taken(String time_taken) {
        this.time_taken = time_taken;
    }

    public String getEms_box_code() {
        return ems_box_code;
    }

    public void setEms_box_code(String ems_box_code) {
        this.ems_box_code = ems_box_code;
    }

    public String getShipping_date() {
        return shipping_date;
    }

    public void setShipping_date(String shipping_date) {
        this.shipping_date = shipping_date;
    }

    public String getIs_polythene() {
        return is_polythene;
    }

    public void setIs_polythene(String is_polythene) {
        this.is_polythene = is_polythene;
    }

    public String getBox_no() {
        return box_no;
    }

    public void setBox_no(String box_no) {
        this.box_no = box_no;
    }
}
