package com.itechvision.ecrobo.pickman.Models.NewPrinter.SelectedprinterList;

import com.google.gson.annotations.SerializedName;

public class selectedlistdata {

    @SerializedName("id")
    private String id;

    @SerializedName("ap_form_name")
    private String ap_form_name;

    @SerializedName("printer_name")
    private String printer_name;

    @SerializedName("machine_id")
    private String machine_id;

    public selectedlistdata(String id, String ap_form_name, String printer_name, String ap_form_category_name,String machine_id) {
        this.id = id;
        this.ap_form_name = ap_form_name;
        this.printer_name = printer_name;
        this.ap_form_category_name = ap_form_category_name;
        this.machine_id = machine_id;
    }

    public String getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(String machine_id) {
        this.machine_id = machine_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAp_form_name() {
        return ap_form_name;
    }

    public void setAp_form_name(String ap_form_name) {
        this.ap_form_name = ap_form_name;
    }

    public String getPrinter_name() {
        return printer_name;
    }

    public void setPrinter_name(String printer_name) {
        this.printer_name = printer_name;
    }

    public String getAp_form_category_name() {
        return ap_form_category_name;
    }

    public void setAp_form_category_name(String ap_form_category_name) {
        this.ap_form_category_name = ap_form_category_name;
    }

    @SerializedName("ap_form_category_name")
    private String ap_form_category_name;






}
