package com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnBarcode;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReturnProductResponse {
    @SerializedName("code")
    private String  code;

    @SerializedName("message")
    private String  message;

    @SerializedName("results")
    private ArrayList<ReturnProductResponseData> results;

    public ReturnProductResponse(String code, String message, ArrayList<ReturnProductResponseData> results) {
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

    public ArrayList<ReturnProductResponseData> getResults() {
        return results;
    }

    public void setResults(ArrayList<ReturnProductResponseData> results) {
        this.results = results;
    }
}
