package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.FixReq;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxCashList;

import java.util.ArrayList;



public class PickProductRes {

    @SerializedName("result")
    private String result;

    @SerializedName("message")
    private String message;

    @SerializedName("all_order_count")
    private String all_order_count;

    @SerializedName("not_inspection_row_count")
    private String not_inspection_row_count;

    @SerializedName("shortage_row_count")
    private String shortage_row_count;

    @SerializedName("koguchi_mismatch")
    private String koguchi_mismatch;

    @SerializedName("data")
    private ArrayList<BoxCashList> data;

    @SerializedName("packed_count")
    private String packed_count;


    public PickProductRes(String result, String all_order_count, String not_inspection_row_count, String shortage_row_count, String koguchi_mismatch, ArrayList<BoxCashList> data, String packed_count) {
        this.result = result;
        this.all_order_count = all_order_count;
        this.not_inspection_row_count = not_inspection_row_count;
        this.shortage_row_count = shortage_row_count;
        this.koguchi_mismatch = koguchi_mismatch;
        this.data = data;
        this.packed_count = packed_count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getKoguchi_mismatch() {
        return koguchi_mismatch;
    }

    public void setKoguchi_mismatch(String koguchi_mismatch) {
        this.koguchi_mismatch = koguchi_mismatch;
    }

    public ArrayList<BoxCashList> getData() {
        return data;
    }

    public void setData(ArrayList<BoxCashList> data) {
        this.data = data;
    }

    public String getPacked_count() {
        return packed_count;
    }

    public void setPacked_count(String packed_count) {
        this.packed_count = packed_count;
    }
}