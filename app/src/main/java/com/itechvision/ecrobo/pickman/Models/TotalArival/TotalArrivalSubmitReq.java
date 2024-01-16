package com.itechvision.ecrobo.pickman.Models.TotalArival;

import com.google.gson.annotations.SerializedName;

public class TotalArrivalSubmitReq {


    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("authId")
    private String authId;

    @SerializedName("shop_id")
    private String shop_id;

    @SerializedName("app_version")
    private String app_version;

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("nyuka_id")
    private String nyuka_id;

    @SerializedName("case_val")
    private String case_val;

    @SerializedName("qty")
    private String qty;

    @SerializedName("case_quantity")
    private String case_quantity;

    @SerializedName("plate")
    private String plate;

    @SerializedName("comment")
    private String comment;

    @SerializedName("printer_id")
    private String printer_id;




    public TotalArrivalSubmitReq(String admin_id, String authId, String shop_id, String app_version, String product_id, String nyuka_id, String case_val, String qty, String case_quantity, String plate, String comment , String printer_id) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.app_version = app_version;
        this.product_id = product_id;
        this.nyuka_id = nyuka_id;
        this.case_val = case_val;
        this.qty = qty;
        this.case_quantity = case_quantity;
        this.plate = plate;
        this.comment = comment;
        this.printer_id = printer_id;
    }


    public String getPrinter_id() {
        return printer_id;
    }

    public void setPrinter_id(String printer_id) {
        this.printer_id = printer_id;
    }

    public String getCase_quantity() {
        return case_quantity;
    }

    public void setCase_quantity(String case_quantity) {
        this.case_quantity = case_quantity;
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

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
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

    public String getCase_val() {
        return case_val;
    }

    public void setCase_val(String case_val) {
        this.case_val = case_val;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
