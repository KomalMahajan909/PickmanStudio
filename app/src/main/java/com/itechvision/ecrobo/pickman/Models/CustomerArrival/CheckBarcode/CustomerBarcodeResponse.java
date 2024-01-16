package com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckBarcode;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomerBarcodeResponse {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private ArrayList<NyukaData> results;


    public CustomerBarcodeResponse(String code, String message, ArrayList<NyukaData> results) {
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

    public ArrayList<NyukaData> getResults() {
        return results;
    }

    public void setResults(ArrayList<NyukaData> results) {
        this.results = results;
    }
}
