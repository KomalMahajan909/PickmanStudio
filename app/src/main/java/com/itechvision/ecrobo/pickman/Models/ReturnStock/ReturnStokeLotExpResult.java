package com.itechvision.ecrobo.pickman.Models.ReturnStock;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReturnStokeLotExpResult {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private ArrayList<ReturnStokeLotExpData> results;

    public ReturnStokeLotExpResult(String code, String message, ArrayList<ReturnStokeLotExpData> results) {
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

    public ArrayList<ReturnStokeLotExpData> getResults() {
        return results;
    }

    public void setResults(ArrayList<ReturnStokeLotExpData> results) {
        this.results = results;
    }
}
