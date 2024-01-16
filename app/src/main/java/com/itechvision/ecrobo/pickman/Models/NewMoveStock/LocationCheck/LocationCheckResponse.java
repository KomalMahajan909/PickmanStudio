package com.itechvision.ecrobo.pickman.Models.NewMoveStock.LocationCheck;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.BarcodeCheck.ProductList;

import java.util.ArrayList;

public class LocationCheckResponse {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private ArrayList<ProductList> results;

    public LocationCheckResponse(String code, String message, ArrayList<ProductList> results) {
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

    public ArrayList<ProductList> getResults() {
        return results;
    }

    public void setResults(ArrayList<ProductList> results) {
        this.results = results;
    }
}
