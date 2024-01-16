package com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmGetBatchPicking;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DmBatchPickingResponse {
    @SerializedName("code")
    private  String code;

    @SerializedName("row")
    private  String row;

    @SerializedName("message")
    private  String message;

    @SerializedName("total_product")
    private String total_product;

    @SerializedName("pending_row_counts")
    private String pending_row_counts;

    @SerializedName("batch_orders_count")
    private String batch_orders_count;

    @SerializedName("batch_orders_remaining_count")
    private String batch_orders_remaining_count;

    @SerializedName("order_data")
    private ArrayList<DmProductListData> order_data;

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getPending_row_counts() {
        return pending_row_counts;
    }

    public void setPending_row_counts(String pending_row_counts) {
        this.pending_row_counts = pending_row_counts;
    }

    public String getBatch_orders_count() {
        return batch_orders_count;
    }

    public void setBatch_orders_count(String batch_orders_count) {
        this.batch_orders_count = batch_orders_count;
    }

    public String getBatch_orders_remaining_count() {
        return batch_orders_remaining_count;
    }

    public void setBatch_orders_remaining_count(String batch_orders_remaining_count) {
        this.batch_orders_remaining_count = batch_orders_remaining_count;
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

    public String getTotal_product() {
        return total_product;
    }

    public void setTotal_product(String total_product) {
        this.total_product = total_product;
    }

    public ArrayList<DmProductListData> getOrder_data() {
        return order_data;
    }

    public void setOrder_data(ArrayList<DmProductListData> order_data) {
        this.order_data = order_data;
    }
}
