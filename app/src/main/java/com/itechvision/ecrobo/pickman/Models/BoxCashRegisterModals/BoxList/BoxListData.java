package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxList;

import com.google.gson.annotations.SerializedName;

public class BoxListData {

    @SerializedName("ems_box_code")
    private String ems_box_code;

    @SerializedName("size")
    private String size;

    public BoxListData(String ems_box_code, String size) {
        this.ems_box_code = ems_box_code;
        this.size = size;
    }

    public String getEms_box_code() {
        return ems_box_code;
    }

    public void setEms_box_code(String ems_box_code) {
        this.ems_box_code = ems_box_code;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
