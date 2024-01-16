package com.itechvision.ecrobo.pickman.Models.NewShipping.Boxsize;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class SetBoxSizeResponse {
    @SerializedName("code")
    private String code ;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    protected ArrayList<Map<String, String>> results ;

    public SetBoxSizeResponse(String code, String message, ArrayList<Map<String, String>> results) {
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

    public ArrayList<Map<String, String>> getResults() {
        return results;
    }

    public void setResults(ArrayList<Map<String, String>> results) {
        this.results = results;
    }
}
