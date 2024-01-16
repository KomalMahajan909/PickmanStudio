package com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.CheckOrder;

import com.google.gson.annotations.SerializedName;

public class CheckShipOrderResponse {
    @SerializedName("code")
    private String code ;

    @SerializedName("message")
    private String message;

    @SerializedName("all_order_count")
    private String all_order_count ;

    @SerializedName("not_inspection_row_count")
    private String not_inspection_row_count;

    @SerializedName("shortage_row_count")
    private String shortage_row_count ;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("receiver_name")
    private String receiver_name;

    @SerializedName("order_no")
    private String order_no ;

    @SerializedName("mediacode")
    private String mediacode;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
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

    public String getAll_order_count() {
        return all_order_count;
    }

    public void setAll_order_count(String all_order_count) {
        this.all_order_count = all_order_count;
    }

    public String getNot_inspection_row_count() {
        return not_inspection_row_count;
    }

    public void setNot_inspection_row_count(String not_inspection_row_count) {
        this.not_inspection_row_count = not_inspection_row_count;
    }


    public String getShortage_row_count() {
        return shortage_row_count;
    }

    public void setShortage_row_count(String shortage_row_count) {
        this.shortage_row_count = shortage_row_count;
    }



}



