package com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BarcodePickResponse {


    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("working")
    private String total_rows;

    @SerializedName("pending")
    private String pending;

    @SerializedName("finished")
    private String finished;

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

    public String getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(String total_rows) {
        this.total_rows = total_rows;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public ArrayList<BarcodeData> getBarcode_data() {
        return barcode_data;
    }

    public void setBarcode_data(ArrayList<BarcodeData> barcode_data) {
        this.barcode_data = barcode_data;
    }

    public BarcodePickResponse(String code, String message, String total_rows, String pending, String finished, ArrayList<BarcodeData> barcode_data) {
        this.code = code;
        this.message = message;
        this.total_rows = total_rows;
        this.pending = pending;
        this.finished = finished;
        this.barcode_data = barcode_data;
    }

    @SerializedName("barcode_data")
    private ArrayList<BarcodeData> barcode_data;


}
