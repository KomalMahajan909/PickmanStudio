package com.itechvision.ecrobo.pickman.Models.DaimaruShipping;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.NewShipping.GetTotalOrders.GetOrderData;

import java.util.ArrayList;

public class Daimaru_GetOrderRespose {

    @SerializedName("code")
    private String code ;

    @SerializedName("results")
    private ArrayList<GetOrderData> results ;

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

    public ArrayList<GetOrderData> getResults() {
        return results;
    }

    public void setResults(ArrayList<GetOrderData> results) {
        this.results = results;
    }


}
