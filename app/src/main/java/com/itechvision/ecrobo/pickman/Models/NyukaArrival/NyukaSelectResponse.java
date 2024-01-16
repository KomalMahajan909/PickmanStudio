package com.itechvision.ecrobo.pickman.Models.NyukaArrival;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.TotalArival.nyuka_data;

import java.util.ArrayList;

public class NyukaSelectResponse {

    public NyukaSelectResponse(String code, String message, ArrayList<String> results) {
        this.code = code;
        this.message = message;
        this.results = results;

    }

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

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


    @SerializedName("results")
    private  ArrayList<String> results;


    public  ArrayList<String> getResults() {
        return results;
    }

    public void setResults( ArrayList<String> results) {
        this.results = results;
    }
}
