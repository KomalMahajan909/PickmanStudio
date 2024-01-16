package com.itechvision.ecrobo.pickman.Models.NewPrinter.Template;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.PrinterCategorydata;

import java.util.ArrayList;

public class PrinterTempResult {

    @SerializedName("code")
    private  String code;

    @SerializedName("message")
    private  String message;

    public PrinterTempResult(String code, String message, ArrayList<PrinterTemplatedata> results) {
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

    public ArrayList<PrinterTemplatedata> getResults() {
        return results;
    }

    public void setResults(ArrayList<PrinterTemplatedata> results) {
        this.results = results;
    }

    @SerializedName("results")
    private ArrayList<PrinterTemplatedata> results;


}
