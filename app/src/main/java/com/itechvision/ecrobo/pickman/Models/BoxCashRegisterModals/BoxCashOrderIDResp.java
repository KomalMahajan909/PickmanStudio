package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BoxCashOrderIDResp {

    @SerializedName("result")
    private String result;

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("pending_count")
    private String pending_count;

    @SerializedName("finished_count")
    private String finished_count;

    @SerializedName("koguchi")
    private String koguchi;

    @SerializedName("not_inspection_row_count")
    private String not_inspection_row_count;

    @SerializedName("shortage_row_count")
    private String shortage_row_count;

    @SerializedName("packed_count")
    private String packed_count;

    @SerializedName("username")
    private String username;

    @SerializedName("box_no")
    private String box_no;

    @SerializedName("shipco_method")
    private String shipco_method;

    @SerializedName("data")
    private ArrayList<BoxCashList> data;

    public BoxCashOrderIDResp(String result, String pending_count, String finished_count, String koguchi, String not_inspection_row_count, String shortage_row_count, String packed_count, String username, String box_no, String shipco_method, ArrayList<BoxCashList> data) {
        this.result = result;
        this.pending_count = pending_count;
        this.finished_count = finished_count;
        this.koguchi = koguchi;
        this.not_inspection_row_count = not_inspection_row_count;
        this.shortage_row_count = shortage_row_count;
        this.packed_count = packed_count;
        this.username = username;
        this.box_no = box_no;
        this.shipco_method = shipco_method;
        this.data = data;
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

    public String getShipco_method() {
        return shipco_method;
    }

    public void setShipco_method(String shipco_method) {
        this.shipco_method = shipco_method;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPending_count() {
        return pending_count;
    }

    public void setPending_count(String pending_count) {
        this.pending_count = pending_count;
    }

    public String getFinished_count() {
        return finished_count;
    }

    public void setFinished_count(String finished_count) {
        this.finished_count = finished_count;
    }

    public String getKoguchi() {
        return koguchi;
    }

    public void setKoguchi(String koguchi) {
        this.koguchi = koguchi;
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

    public String getPacked_count() {
        return packed_count;
    }

    public void setPacked_count(String packed_count) {
        this.packed_count = packed_count;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBox_no() {
        return box_no;
    }

    public void setBox_no(String box_no) {
        this.box_no = box_no;
    }

    public ArrayList<BoxCashList> getData() {
        return data;
    }

    public void setData(ArrayList<BoxCashList> data) {
        this.data = data;
    }
}
