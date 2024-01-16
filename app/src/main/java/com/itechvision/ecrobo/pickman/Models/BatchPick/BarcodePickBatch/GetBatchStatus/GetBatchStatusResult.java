package com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.GetBatchStatus;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetBatchStatusResult {

    @SerializedName("code")
    private  String code ;

    public GetBatchStatusResult(String code, String message, ArrayList<Batchstatusdata> result) {
        this.code = code;
        this.message = message;
        this.result = result;
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

    public ArrayList<Batchstatusdata> getResult() {
        return result;
    }

    public void setResult(ArrayList<Batchstatusdata> result) {
        this.result = result;
    }

    @SerializedName("message")
    private  String message ;

    @SerializedName("results")
    private ArrayList<Batchstatusdata> result ;
}
