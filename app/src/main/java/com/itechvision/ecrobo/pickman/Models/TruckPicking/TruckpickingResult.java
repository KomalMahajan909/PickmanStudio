package com.itechvision.ecrobo.pickman.Models.TruckPicking;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TruckpickingResult {

    @SerializedName("code")
    private  String  code;

    @SerializedName("message")
    private  String  message;

    @SerializedName("batch_data")
    private ArrayList<ShippingCompanyData> results;

    public TruckpickingResult(String code, String message, ArrayList<ShippingCompanyData> results) {
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

    public ArrayList<ShippingCompanyData> getResults() {
        return results;
    }

    public void setResults(ArrayList<ShippingCompanyData> results) {
        this.results = results;
    }
}
