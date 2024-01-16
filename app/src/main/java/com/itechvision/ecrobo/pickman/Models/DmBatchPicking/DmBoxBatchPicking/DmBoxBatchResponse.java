package com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmBoxBatchPicking;

import com.google.gson.annotations.SerializedName;

public class DmBoxBatchResponse {
    @SerializedName("code")
    private  String code;

    @SerializedName("message")
    private  String message;

    @SerializedName("box_no")
    private String box_no;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBox_no() {
        return box_no;
    }

    public void setBox_no(String box_no) {
        this.box_no = box_no;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
