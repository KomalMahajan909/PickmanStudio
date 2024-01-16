package com.itechvision.ecrobo.pickman.Models.Loop_Shipping.Submit;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.Barcode.Loop_Ship_Barcode_Data;

import java.util.ArrayList;

public class LoopSubmitResult {

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

    @SerializedName("all_product_ids")
    private String all_barcode;

    @SerializedName("pending_barcode")
    private String pending_barcode;

    @SerializedName("product_data")
    private ArrayList<Loop_Ship_Barcode_Data> product_data;

    public LoopSubmitResult(String message, String code, String all_order_count, String pending_row_count, String skip_row_count, String all_barcode, String pending_barcode, ArrayList<Loop_Ship_Barcode_Data> product_data) {
        this.message = message;
        this.code = code;
        this.all_order_count = all_order_count;
        this.pending_row_count = pending_row_count;
        this.skip_row_count = skip_row_count;
        this.all_barcode = all_barcode;
        this.pending_barcode = pending_barcode;
        this.product_data = product_data;
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

    public String getAll_barcode() {
        return all_barcode;
    }

    public void setAll_barcode(String all_barcode) {
        this.all_barcode = all_barcode;
    }

    public String getPending_barcode() {
        return pending_barcode;
    }

    public void setPending_barcode(String pending_barcode) {
        this.pending_barcode = pending_barcode;
    }

    public ArrayList<Loop_Ship_Barcode_Data> getProduct_data() {
        return product_data;
    }

    public void setProduct_data(ArrayList<Loop_Ship_Barcode_Data> product_data) {
        this.product_data = product_data;
    }
}


