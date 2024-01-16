package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Packing;

import com.google.gson.annotations.SerializedName;

public class PackListData {

    @SerializedName("ems_box_code")
    private String ems_box_code;

    @SerializedName("box_no")
    private String box_no;

    @SerializedName("status")
    private String status;

    private boolean isChecked;

    public PackListData(String ems_box_code, String box_no, String status, boolean isChecked) {
        this.ems_box_code = ems_box_code;
        this.box_no = box_no;
        this.status = status;
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getEms_box_code() {
        return ems_box_code;
    }

    public void setEms_box_code(String ems_box_code) {
        this.ems_box_code = ems_box_code;
    }

    public String getBox_no() {
        return box_no;
    }

    public void setBox_no(String box_no) {
        this.box_no = box_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
