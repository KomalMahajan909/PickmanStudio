package com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnLocationSubmit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReturnLocationSubmitResponse {


    @SerializedName("code")
    private String  code;

    @SerializedName("message")
    private String  message;

    @SerializedName("results")

    private ArrayList<ReturnLocationsubmitData> results;

    public ReturnLocationSubmitResponse(String code, String message, ArrayList<ReturnLocationsubmitData> results) {
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

    public ArrayList<ReturnLocationsubmitData> getResults() {
        return results;
    }

    public void setResults(ArrayList<ReturnLocationsubmitData> results) {
        this.results = results;
    }
}
