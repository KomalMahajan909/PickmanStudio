package com.itechvision.ecrobo.pickman.Models.W6Arrival.GetOrderArrival;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetArrival.ArrivalData;

import java.util.ArrayList;

public class GetArrivalBarcodeOrderNoResponse {
    @SerializedName("code")
    private  String code;

    @SerializedName("message")
    private  String message;

    @SerializedName("results")
    private ArrayList<ArrivalData> results;

    public GetArrivalBarcodeOrderNoResponse(String code, String message, ArrayList<ArrivalData> results) {
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

    public ArrayList<ArrivalData> getResults() {
        return results;
    }

    public void setResults(ArrayList<ArrivalData> results) {
        this.results = results;
    }
}
