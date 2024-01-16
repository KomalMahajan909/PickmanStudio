package com.itechvision.ecrobo.pickman.Models.DirectArrival;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DirArrivalLoctionResult {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private ArrayList<DirArrivalListData> results;

    public DirArrivalLoctionResult(String code, String message, ArrayList<DirArrivalListData> results) {
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

    public ArrayList<DirArrivalListData> getResults() {
        return results;
    }

    public void setResults(ArrayList<DirArrivalListData> results) {
        this.results = results;
    }
}


