package com.itechvision.ecrobo.pickman.Models.SyakkiID;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SakiResponse {

    @SerializedName("code")
private String code;

    @SerializedName("message")
private String message;

    @SerializedName("condition1")
private String condition1;

    @SerializedName("condition2")
private String condition2;

    @SerializedName("results")
private ArrayList<SakiArry> results;

    public SakiResponse(String code, String message, String condition1, String condition2, ArrayList<SakiArry> results) {
        this.code = code;
        this.message = message;
        this.condition1 = condition1;
        this.condition2 = condition2;
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

    public String getCondition1() {
        return condition1;
    }

    public void setCondition1(String condition1) {
        this.condition1 = condition1;
    }

    public String getCondition2() {
        return condition2;
    }

    public void setCondition2(String condition2) {
        this.condition2 = condition2;
    }

    public ArrayList<SakiArry> getResults() {
        return results;
    }

    public void setResults(ArrayList<SakiArry> results) {
        this.results = results;
    }
}
