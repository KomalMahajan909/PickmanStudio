package com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BatchListModel;

import com.google.gson.annotations.SerializedName;

public class BatchListData {

    @SerializedName("batch_id")
    private  String batch_id;

    @SerializedName("batch_no")
    private  String batch_no;

    @SerializedName("batch_type")
    private  String batch_type;

    @SerializedName("batch_status")
    private  String batch_status;

    @SerializedName("batch_order_count")
    private  String batch_order_count;

    @SerializedName("sku_count")
    private  String sku_count;

    @SerializedName("invoice_status")
    private String invoice_status;

    @SerializedName("batch_remarks")
    private String batch_remarks;

    @SerializedName("username")
    private String username;


    public BatchListData(String batch_id, String batch_no, String batch_type, String batch_status, String batch_order_count, String sku_count) {
        this.batch_id = batch_id;
        this.batch_no = batch_no;
        this.batch_type = batch_type;
        this.batch_status = batch_status;
        this.batch_order_count = batch_order_count;
        this.sku_count = sku_count;
    }

    public String getBatch_remarks() {
        return batch_remarks;
    }

    public void setBatch_remarks(String batch_remarks) {
        this.batch_remarks = batch_remarks;
    }

    public String getInvoice_status() {
        return invoice_status;
    }

    public void setInvoice_status(String invoice_status) {
        this.invoice_status = invoice_status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getBatch_type() {
        return batch_type;
    }

    public void setBatch_type(String batch_type) {
        this.batch_type = batch_type;
    }

    public String getBatch_status() {
        return batch_status;
    }

    public void setBatch_status(String batch_status) {
        this.batch_status = batch_status;
    }

    public String getBatch_order_count() {
        return batch_order_count;
    }

    public void setBatch_order_count(String batch_order_count) {
        this.batch_order_count = batch_order_count;
    }

    public String getSku_count() {
        return sku_count;
    }

    public void setSku_count(String sku_count) {
        this.sku_count = sku_count;
    }
}
