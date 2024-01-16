package com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.StockClassification;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StockClassificationResponse {

    @SerializedName("code")
    private  String code;

    @SerializedName("message")
    private  String message;

    @SerializedName("results")
    private  ArrayList<StockClassificationListData> results;

    public StockClassificationResponse(String code, String message, ArrayList<StockClassificationListData> results) {
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

    public ArrayList<StockClassificationListData> getResults() {
        return results;
    }

    public void setResults(ArrayList<StockClassificationListData> results) {
        this.results = results;
    }
}
