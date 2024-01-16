package com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Arrival_ID_Check;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Arrival_ID_Check.Arrival_ID_list_data;

import java.util.ArrayList;

public class ArrivalIDCheckResponse {

    @SerializedName("code")
    private  String code;

    @SerializedName("message")
    private  String message;

    @SerializedName("results")
    private ArrayList<Arrival_ID_list_data> results;

    @SerializedName("pending_orders")
    private  String pending_orders;

    @SerializedName("pending_arrivals")
    private  String pending_arrivals;

    public ArrivalIDCheckResponse(String code, String message, ArrayList<Arrival_ID_list_data> results, String pending_orders, String pending_arrivals) {
        this.code = code;
        this.message = message;
        this.results = results;
        this.pending_orders = pending_orders;
        this.pending_arrivals = pending_arrivals;
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

    public ArrayList<Arrival_ID_list_data> getResults() {
        return results;
    }

    public void setResults(ArrayList<Arrival_ID_list_data> results) {
        this.results = results;
    }

    public String getPending_orders() {
        return pending_orders;
    }

    public void setPending_orders(String pending_orders) {
        this.pending_orders = pending_orders;
    }

    public String getPending_arrivals() {
        return pending_arrivals;
    }

    public void setPending_arrivals(String pending_arrivals) {
        this.pending_arrivals = pending_arrivals;
    }
}
