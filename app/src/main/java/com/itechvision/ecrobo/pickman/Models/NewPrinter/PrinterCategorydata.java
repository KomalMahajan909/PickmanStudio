package com.itechvision.ecrobo.pickman.Models.NewPrinter;

import com.google.gson.annotations.SerializedName;

public class PrinterCategorydata {

    @SerializedName("ap_form_category_id")
    private String ap_form_category_id;


    public PrinterCategorydata(String ap_form_category_id, String name) {
        this.ap_form_category_id = ap_form_category_id;
        this.name = name;
    }

    public String getAp_form_category_id() {
        return ap_form_category_id;
    }

    public void setAp_form_category_id(String ap_form_category_id) {
        this.ap_form_category_id = ap_form_category_id;
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
