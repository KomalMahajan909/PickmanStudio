package com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnOrderId;

import com.google.gson.annotations.SerializedName;

public class ReturnOrderResponseData {

    @SerializedName("sendback_id")
    String sendback_id;

    @SerializedName("order_id")
    String order_id;

    @SerializedName("order_no")
    String order_no;

    @SerializedName("mediacode")
    String mediacode;

    @SerializedName("wf_status")
    String wf_status;

    public ReturnOrderResponseData(String sendback_id, String order_id, String order_no, String mediacode, String wf_status) {
        this.sendback_id = sendback_id;
        this.order_id = order_id;
        this.order_no = order_no;
        this.mediacode = mediacode;
        this.wf_status = wf_status;
    }

    public String getSendback_id() {
        return sendback_id;
    }

    public void setSendback_id(String sendback_id) {
        this.sendback_id = sendback_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getMediacode() {
        return mediacode;
    }

    public void setMediacode(String mediacode) {
        this.mediacode = mediacode;
    }

    public String getWf_status() {
        return wf_status;
    }

    public void setWf_status(String wf_status) {
        this.wf_status = wf_status;
    }
}
