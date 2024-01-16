package com.itechvision.ecrobo.pickman.Models.W6Arrival.GetArrival;

import com.google.gson.annotations.SerializedName;

public class ArrivalData {

    @SerializedName("nyuka_id")
    private  String nyuka_id;

    @SerializedName("code")
    private  String code;

    @SerializedName("rsv_cnt")
    private  String rsv_cnt;

    @SerializedName("rsv_date")
    private String rsv_date;

    @SerializedName("act_cnt")
    private  String act_cnt;

    @SerializedName("quantity")
    private  String quantity;

    @SerializedName("product_name")
    private  String product_name;

    @SerializedName("product_barcode")
    private  String product_barcode;

    @SerializedName("product_id")
    private  String product_id;

    @SerializedName("complete_qty")
    private  String complete_qty;

    @SerializedName("attribute_type")
    private  String attribute_type;

    @SerializedName("lot")
    private  String lot;

    @SerializedName("expiration_date")
    private  String expiration_date;

    @SerializedName("comment")
    private String comment;

    @SerializedName("spec1")
    private String spec1;

    @SerializedName("spec2")
    private String spec2;

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("arrival_exp_days")
    private String arrival_exp_days;

    @SerializedName("arrival_exp_flag")
    private String arrival_exp_flag;

    @SerializedName("order_no")
    private String order_no;

    @SerializedName("comp_name")
    private String comp_name;

    public ArrivalData(String nyuka_id, String code, String rsv_cnt, String rsv_date, String act_cnt, String quantity, String product_name, String product_barcode, String product_id, String complete_qty, String attribute_type, String lot, String expiration_date, String comment, String spec1, String spec2, String admin_id, String arrival_exp_days, String arrival_exp_flag, String order_no, String comp_name) {
        this.nyuka_id = nyuka_id;
        this.code = code;
        this.rsv_cnt = rsv_cnt;
        this.rsv_date = rsv_date;
        this.act_cnt = act_cnt;
        this.quantity = quantity;
        this.product_name = product_name;
        this.product_barcode = product_barcode;
        this.product_id = product_id;
        this.complete_qty = complete_qty;
        this.attribute_type = attribute_type;
        this.lot = lot;
        this.expiration_date = expiration_date;
        this.comment = comment;
        this.spec1 = spec1;
        this.spec2 = spec2;
        this.admin_id = admin_id;
        this.arrival_exp_days = arrival_exp_days;
        this.arrival_exp_flag = arrival_exp_flag;
        this.order_no = order_no;
        this.comp_name = comp_name;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getRsv_date() {
        return rsv_date;
    }

    public void setRsv_date(String rsv_date) {
        this.rsv_date = rsv_date;
    }

    public String getArrival_exp_days() {
        return arrival_exp_days;
    }

    public void setArrival_exp_days(String arrival_exp_days) {
        this.arrival_exp_days = arrival_exp_days;
    }

    public String getArrival_exp_flag() {
        return arrival_exp_flag;
    }

    public void setArrival_exp_flag(String arrival_exp_flag) {
        this.arrival_exp_flag = arrival_exp_flag;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getNyuka_id() {
        return nyuka_id;
    }

    public void setNyuka_id(String nyuka_id) {
        this.nyuka_id = nyuka_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRsv_cnt() {
        return rsv_cnt;
    }

    public void setRsv_cnt(String rsv_cnt) {
        this.rsv_cnt = rsv_cnt;
    }

    public String getAct_cnt() {
        return act_cnt;
    }

    public void setAct_cnt(String act_cnt) {
        this.act_cnt = act_cnt;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_barcode() {
        return product_barcode;
    }

    public void setProduct_barcode(String product_barcode) {
        this.product_barcode = product_barcode;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getComplete_qty() {
        return complete_qty;
    }

    public void setComplete_qty(String complete_qty) {
        this.complete_qty = complete_qty;
    }

    public String getAttribute_type() {
        return attribute_type;
    }

    public void setAttribute_type(String attribute_type) {
        this.attribute_type = attribute_type;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSpec1() {
        return spec1;
    }

    public void setSpec1(String spec1) {
        this.spec1 = spec1;
    }

    public String getSpec2() {
        return spec2;
    }

    public void setSpec2(String spec2) {
        this.spec2 = spec2;
    }
}
