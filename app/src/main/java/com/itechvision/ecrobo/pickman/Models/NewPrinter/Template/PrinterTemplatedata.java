package com.itechvision.ecrobo.pickman.Models.NewPrinter.Template;

import com.google.gson.annotations.SerializedName;

public class PrinterTemplatedata {

    @SerializedName("ap_form_id")
    private String ap_form_id;

    public PrinterTemplatedata(String ap_form_id, String name) {
        this.ap_form_id = ap_form_id;
        this.name = name;
    }

    public String getAp_form_id() {
        return ap_form_id;
    }

    public void setAp_form_id(String ap_form_id) {
        this.ap_form_id = ap_form_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("name")
    private String name;



}
