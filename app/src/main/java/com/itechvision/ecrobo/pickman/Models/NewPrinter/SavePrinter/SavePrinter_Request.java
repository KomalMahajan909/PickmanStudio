package com.itechvision.ecrobo.pickman.Models.NewPrinter.SavePrinter;

import com.google.gson.annotations.SerializedName;

public class SavePrinter_Request {

    @SerializedName("admin_id")
    private  String admin_id;

    @SerializedName("authId")
    private  String authId;

    @SerializedName("shop_id")
    private  String shop_id;

    @SerializedName("ap_form_id")
    private  String ap_form_id;


    @SerializedName("printer_id")
    private  String printer_id;


    @SerializedName("printer_name")
    private  String printer_name;

    @SerializedName("ap_machine_id")
    private  String ap_machine_id;

    public String getAp_machine_id() {
        return ap_machine_id;
    }

    public void setAp_machine_id(String ap_machine_id) {
        this.ap_machine_id = ap_machine_id;
    }

    public SavePrinter_Request(String admin_id, String authId, String shop_id, String ap_form_id, String printer_id, String printer_name, String ap_form_category_id, String ap_machine_id) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.ap_form_id = ap_form_id;
        this.printer_id = printer_id;
        this.printer_name = printer_name;
        this.ap_form_category_id = ap_form_category_id;
        this.ap_machine_id = ap_machine_id;

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

    public String getAp_form_id() {
        return ap_form_id;
    }

    public void setAp_form_id(String ap_form_id) {
        this.ap_form_id = ap_form_id;
    }

    public String getPrinter_id() {
        return printer_id;
    }

    public void setPrinter_id(String printer_id) {
        this.printer_id = printer_id;
    }

    public String getPrinter_name() {
        return printer_name;
    }

    public void setPrinter_name(String printer_name) {
        this.printer_name = printer_name;
    }

    public String getAp_form_category_id() {
        return ap_form_category_id;
    }

    public void setAp_form_category_id(String ap_form_category_id) {
        this.ap_form_category_id = ap_form_category_id;
    }

    @SerializedName("ap_form_category_id")
    private  String ap_form_category_id;


}

