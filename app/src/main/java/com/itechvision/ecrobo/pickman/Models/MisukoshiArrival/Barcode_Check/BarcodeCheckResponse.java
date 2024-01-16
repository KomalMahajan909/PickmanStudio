package com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Barcode_Check;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Arrival_ID_Check.Arrival_ID_list_data;

import java.util.ArrayList;

public class BarcodeCheckResponse {

    @SerializedName("code")
    private  String code;

    @SerializedName("message")
    private  String message;

    @SerializedName("results")
    private ArrayList<Product_List_data> results;

    public BarcodeCheckResponse(String code, String message, ArrayList<Product_List_data> results) {
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

    public ArrayList<Product_List_data> getResults() {
        return results;
    }

    public void setResults(ArrayList<Product_List_data> results) {
        this.results = results;
    }
}
