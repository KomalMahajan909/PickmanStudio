package com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetpendingorderResult {


    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("all_order_count")
    private String all_order_count;

    @SerializedName("pending_row_count")
    private String pending_row_count;

    @SerializedName("product_data")
    private ArrayList<PendingData> product_data;

    public GetpendingorderResult(String code, String message, String all_order_count, String pending_row_count, ArrayList<PendingData> product_data) {
        this.code = code;
        this.message = message;
        this.all_order_count = all_order_count;
        this.pending_row_count = pending_row_count;
        this.product_data = product_data;
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

    public String getPending_row_count() {
        return pending_row_count;
    }

    public void setPending_row_count(String pending_row_count) {
        this.pending_row_count = pending_row_count;
    }

    public ArrayList<PendingData> getProduct_data() {
        return product_data;
    }

    public void setProduct_data(ArrayList<PendingData> product_data) {
        this.product_data = product_data;
    }
}
