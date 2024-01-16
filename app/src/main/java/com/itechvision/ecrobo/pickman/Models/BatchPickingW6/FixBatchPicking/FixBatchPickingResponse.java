package com.itechvision.ecrobo.pickman.Models.BatchPickingW6.FixBatchPicking;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.GetBatchPicking.ProductListData;

import java.util.ArrayList;

public class FixBatchPickingResponse {

    @SerializedName("code")
    private  String code;

    @SerializedName("row")
    private  String row;

    @SerializedName("message")
    private  String message;

    @SerializedName("pending_row_counts")
    private String pending_row_counts;

    @SerializedName("shortage_row_count")
    private String shortage_row_count;

    @SerializedName("shortage_row_counts")
    private String shortage_row_counts;

    @SerializedName("batch_orders_count")
    private String batch_orders_count;

    @SerializedName("batch_orders_complete_count")
    private String batch_orders_complete_count;

    @SerializedName("batch_orders_remaining_count")
    private String batch_orders_remaining_count;

    @SerializedName("total_orders_remaining_count")
    private String total_orders_remaining_count;


    @SerializedName("order_data")
    private ArrayList<ProductListData> order_data;

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

    public String getPending_row_counts() {
        return pending_row_counts;
    }

    public void setPending_row_counts(String pending_row_counts) {
        this.pending_row_counts = pending_row_counts;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getShortage_row_count() {
        return shortage_row_count;
    }

    public void setShortage_row_count(String shortage_row_count) {
        this.shortage_row_count = shortage_row_count;
    }

    public String getShortage_row_counts() {
        return shortage_row_counts;
    }

    public void setShortage_row_counts(String shortage_row_counts) {
        this.shortage_row_counts = shortage_row_counts;
    }

    public String getBatch_orders_count() {
        return batch_orders_count;
    }

    public void setBatch_orders_count(String batch_orders_count) {
        this.batch_orders_count = batch_orders_count;
    }

    public String getBatch_orders_complete_count() {
        return batch_orders_complete_count;
    }

    public void setBatch_orders_complete_count(String batch_orders_complete_count) {
        this.batch_orders_complete_count = batch_orders_complete_count;
    }

    public String getBatch_orders_remaining_count() {
        return batch_orders_remaining_count;
    }

    public void setBatch_orders_remaining_count(String batch_orders_remaining_count) {
        this.batch_orders_remaining_count = batch_orders_remaining_count;
    }

    public String getTotal_orders_remaining_count() {
        return total_orders_remaining_count;
    }

    public void setTotal_orders_remaining_count(String total_orders_remaining_count) {
        this.total_orders_remaining_count = total_orders_remaining_count;
    }

    public ArrayList<ProductListData> getOrder_data() {
        return order_data;
    }

    public void setOrder_data(ArrayList<ProductListData> order_data) {
        this.order_data = order_data;
    }
}
