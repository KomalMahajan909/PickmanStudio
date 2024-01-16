package com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TasSubmitResult {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public TasSubmitResult(String code, String message, ArrayList<TasSubmitData> results) {
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

    public ArrayList<TasSubmitData> getResults() {
        return results;
    }

    public void setResults(ArrayList<TasSubmitData> results) {
        this.results = results;
    }

    @SerializedName("results")
    private ArrayList<TasSubmitData> results;


}
