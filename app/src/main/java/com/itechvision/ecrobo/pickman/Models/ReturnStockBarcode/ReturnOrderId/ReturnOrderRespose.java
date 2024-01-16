package com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnOrderId;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReturnOrderRespose {

    @SerializedName("code")
    private String  code;

    @SerializedName("message")
    private String  message;

    @SerializedName("results")
    private ArrayList<ReturnOrderResponseData> results;

    public ReturnOrderRespose(String code, String message, ArrayList<ReturnOrderResponseData> results) {

        this.code = code;
        this.message = message;
        this.results = results;
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

    public ArrayList<ReturnOrderResponseData> getResults() {
        return results;
    }

    public void setResults(ArrayList<ReturnOrderResponseData> results) {
        this.results = results;
    }
}
