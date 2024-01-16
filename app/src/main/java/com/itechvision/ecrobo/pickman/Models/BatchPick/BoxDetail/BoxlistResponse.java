package com.itechvision.ecrobo.pickman.Models.BatchPick.BoxDetail;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BoxlistResponse {


    @SerializedName("code")
    private String  code;

    @SerializedName("message")
    private String  message;

    public BoxlistResponse(String code, String message, ArrayList<BoxData> batch_status) {
        this.code = code;
        this.message = message;
        this.batch_status = batch_status;
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

    public ArrayList<BoxData> getBatch_status() {
        return batch_status;
    }

    public void setBatch_status(ArrayList<BoxData> batch_status) {
        this.batch_status = batch_status;
    }

    @SerializedName("batch_status")
    private ArrayList<BoxData> batch_status;

}
