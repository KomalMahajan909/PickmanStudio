package com.itechvision.ecrobo.pickman.Models.PrintKoguchi.CSV_spinner;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetCSVSpinnerResult {

    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    @SerializedName("results")
    ArrayList<CSVList> results;

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

    public ArrayList<CSVList> getResults() {
        return results;
    }

    public void setResults(ArrayList<CSVList> results) {
        this.results = results;
    }
}
