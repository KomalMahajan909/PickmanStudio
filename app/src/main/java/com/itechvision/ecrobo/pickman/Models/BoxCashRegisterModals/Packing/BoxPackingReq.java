package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Packing;

import com.google.gson.annotations.SerializedName;

public class BoxPackingReq {

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("box_no")
    private String box_no;

    @SerializedName("order_sub_id")
    private String order_sub_id;

    @SerializedName("qty")
    private String qty;

    @SerializedName("group_by_barcode")
    private String group_by_barcode;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("product_stock_history_id")
    private String product_stock_history_id;

    @SerializedName("ems_box_code")
    private String ems_box_code;

    @SerializedName("is_polythene")
    private String is_polythene;


    public BoxPackingReq(String order_id, String box_no, String order_sub_id, String qty, String group_by_barcode, String barcode, String product_stock_history_id, String ems_box_code, String is_polythene) {
        this.order_id = order_id;
        this.box_no = box_no;
        this.order_sub_id = order_sub_id;
        this.qty = qty;
        this.group_by_barcode = group_by_barcode;
        this.barcode = barcode;
        this.product_stock_history_id = product_stock_history_id;
        this.ems_box_code = ems_box_code;
        this.is_polythene = is_polythene;
    }

    public String getIs_polythene() {
        return is_polythene;
    }

    public void setIs_polythene(String is_polythene) {
        this.is_polythene = is_polythene;
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

    public String getOrder_sub_id() {
        return order_sub_id;
    }

    public void setOrder_sub_id(String order_sub_id) {
        this.order_sub_id = order_sub_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getGroup_by_barcode() {
        return group_by_barcode;
    }

    public void setGroup_by_barcode(String group_by_barcode) {
        this.group_by_barcode = group_by_barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProduct_stock_history_id() {
        return product_stock_history_id;
    }

    public void setProduct_stock_history_id(String product_stock_history_id) {
        this.product_stock_history_id = product_stock_history_id;
    }

    public String getEms_box_code() {
        return ems_box_code;
    }

    public void setEms_box_code(String ems_box_code) {
        this.ems_box_code = ems_box_code;
    }
}

