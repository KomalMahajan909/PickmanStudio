package com.itechvision.ecrobo.pickman.Models.NewPrinter.PrinterList;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.PrinterCategorydata;

import java.util.ArrayList;

public class PrinterResult {

    @SerializedName("code")
    private  String code;

    @SerializedName("message")
    private  String message;

    public PrinterResult(String code, String message, ArrayList<Printerdata> results) {
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

    public ArrayList<Printerdata> getResults() {
        return results;
    }

    public void setResults(ArrayList<Printerdata> results) {
        this.results = results;
    }

    @SerializedName("results")
    private ArrayList<Printerdata> results;


}
