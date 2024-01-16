package com.itechvision.ecrobo.pickman.Models.NewPrinter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PrinterCategoryResult {

    @SerializedName("code")
    private  String code;

    @SerializedName("message")
    private  String message;

    public PrinterCategoryResult(String code, String message, ArrayList<PrinterCategorydata> results) {
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

    public ArrayList<PrinterCategorydata> getResults() {
        return results;
    }

    public void setResults(ArrayList<PrinterCategorydata> results) {
        this.results = results;
    }

    @SerializedName("results")
    private ArrayList<PrinterCategorydata> results;


}
