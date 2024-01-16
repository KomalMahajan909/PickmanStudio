package com.itechvision.ecrobo.pickman.Models.InvoiceShipping.CheckInvoiceOrder;

import com.google.gson.annotations.SerializedName;

public class MediaCodeData {

    @SerializedName("order_id")
    private String order_id ;

    @SerializedName("order_no")
    private String order_no;

    @SerializedName("mediacode")
    private String mediacode;

    public MediaCodeData(String order_id, String order_no, String mediacode) {
        this.order_id = order_id;
        this.order_no = order_no;
        this.mediacode = mediacode;
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
}
