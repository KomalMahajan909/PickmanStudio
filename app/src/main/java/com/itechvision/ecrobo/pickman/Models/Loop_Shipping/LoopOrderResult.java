package com.itechvision.ecrobo.pickman.Models.Loop_Shipping;

import com.google.gson.annotations.SerializedName;

public class LoopOrderResult {

    @SerializedName("message")
    private String message;

    @SerializedName("code")
    private String code;

    @SerializedName("all_order_count")
    private String all_order_count;

    @SerializedName("pending_row_count")
    private String pending_row_count;

    @SerializedName("skip_row_count")
    private String skip_row_count;

    @SerializedName("order_data")
    private Loop_Ship_Data order_data;

    public LoopOrderResult(String message, String code, String all_order_count, String pending_row_count, String skip_row_count, Loop_Ship_Data order_data) {
        this.message = message;
        this.code = code;
        this.all_order_count = all_order_count;
        this.pending_row_count = pending_row_count;
        this.skip_row_count = skip_row_count;
        this.order_data = order_data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAll_order_count() {
        return all_order_count;
    }

    public void setAll_order_count(String all_order_count) {
        this.all_order_count = all_order_count;
    }

    public String getPending_row_count() {
        return pending_row_count;
    }

    public void setPending_row_count(String pending_row_count) {
        this.pending_row_count = pending_row_count;
    }

    public String getSkip_row_count() {
        return skip_row_count;
    }

    public void setSkip_row_count(String skip_row_count) {
        this.skip_row_count = skip_row_count;
    }

    public Loop_Ship_Data getOrder_data() {
        return order_data;
    }

    public void setOrder_data(Loop_Ship_Data order_data) {
        this.order_data = order_data;
    }
}


