package com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch;

import com.google.gson.annotations.SerializedName;

public class SubmitBarcodeResponse {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("working")
    private String total_rows;

    @SerializedName("finished")
    private String finished;

    @SerializedName("pending")
    private String pending;

    @SerializedName("status")
    private String status;

    @SerializedName("box_status")
    private String box_status;

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

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBox_status() {
        return box_status;
    }

    public void setBox_status(String box_status) {
        this.box_status = box_status;
    }

    public SubmitBarcodeResponse(String code, String message, String total_rows, String finished, String pending, String status, String box_status) {
        this.code = code;
        this.message = message;
        this.total_rows = total_rows;
        this.finished = finished;
        this.pending = pending;
        this.status = status;
        this.box_status = box_status;
    }


}



