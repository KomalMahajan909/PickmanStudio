package com.itechvision.ecrobo.pickman.Models.BatchPick.CheckTasOrder;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.GetBatchStatus.Batchstatusdata;

import java.util.ArrayList;

public class TasworkingRespose {

    @SerializedName("code")
    private  String code ;

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

    public ArrayList<TasworkingData> getResult() {
        return result;
    }

    public void setResult(ArrayList<TasworkingData> result) {
        this.result = result;
    }

    public TasworkingRespose(String code, String message, ArrayList<TasworkingData> result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @SerializedName("message")
    private  String message ;

    @SerializedName("results")
    private ArrayList<TasworkingData> result ;
}

