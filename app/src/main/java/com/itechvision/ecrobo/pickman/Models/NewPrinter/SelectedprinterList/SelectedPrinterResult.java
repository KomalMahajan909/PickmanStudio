package com.itechvision.ecrobo.pickman.Models.NewPrinter.SelectedprinterList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SelectedPrinterResult {

    @SerializedName("code")
    private String code ;

        @SerializedName("message")
    private String message ;

    public SelectedPrinterResult(String code, String message, ArrayList<selectedlistdata> results) {
        this.code = code;
        this.message = message;
        this.results = results;
    }

    @SerializedName("results")
    private ArrayList<selectedlistdata> results ;

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

    public ArrayList<selectedlistdata> getResults() {
        return results;
    }

    public void setResults(ArrayList<selectedlistdata> results) {
        this.results = results;
    }
}
