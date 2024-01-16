package com.itechvision.ecrobo.pickman.Models.Loop_Shipping.Submit;

import com.google.gson.annotations.SerializedName;

public class SubmitReqLoop {

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

    @SerializedName("psh_id")
    private String psh_id;

    @SerializedName("serial_no")
    private String serial_no;

    @SerializedName("qty")
    private String qty;

    @SerializedName("serial_skip")
    private String serial_skip;

    @SerializedName("all_product_ids")
    private String all_barcode;


    @SerializedName("shipping_flag")
    private String shipping_flag;

    public SubmitReqLoop(String admin_id, String authId, String shop_id, String order_id, String product_id, String psh_id, String serial_no, String qty, String serial_skip, String all_barcode, String shipping_flag) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.psh_id = psh_id;
        this.serial_no = serial_no;
        this.qty = qty;
        this.serial_skip = serial_skip;
        this.all_barcode = all_barcode;
        this.shipping_flag = shipping_flag;
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

    public String getPsh_id() {
        return psh_id;
    }

    public void setPsh_id(String psh_id) {
        this.psh_id = psh_id;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSerial_skip() {
        return serial_skip;
    }

    public void setSerial_skip(String serial_skip) {
        this.serial_skip = serial_skip;
    }

    public String getAll_barcode() {
        return all_barcode;
    }

    public void setAll_barcode(String all_barcode) {
        this.all_barcode = all_barcode;
    }

    public String getShipping_flag() {
        return shipping_flag;
    }

    public void setShipping_flag(String shipping_flag) {
        this.shipping_flag = shipping_flag;
    }
}

