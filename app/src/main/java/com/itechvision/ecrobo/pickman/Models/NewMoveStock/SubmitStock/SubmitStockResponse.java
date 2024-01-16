package com.itechvision.ecrobo.pickman.Models.NewMoveStock.SubmitStock;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubmitStockResponse {
    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private ArrayList<DialogList> results;

    public SubmitStockResponse(String code, String message, ArrayList<DialogList> results) {
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

    public ArrayList<DialogList> getResults() {
        return results;
    }

    public void setResults(ArrayList<DialogList> results) {
        this.results = results;
    }



}
