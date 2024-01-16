package com.itechvision.ecrobo.pickman.Models.InvoiceShipping.CheckInvoiceOrder;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CheckInvoiceShipOrderResponse {
    @SerializedName("code")
    private String code ;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private ArrayList<MediaCodeData> results;

    public CheckInvoiceShipOrderResponse(String code, String message, ArrayList<MediaCodeData> results) {
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

    public ArrayList<MediaCodeData> getResults() {
        return results;
    }

    public void setResults(ArrayList<MediaCodeData> results) {
        this.results = results;
    }
}

