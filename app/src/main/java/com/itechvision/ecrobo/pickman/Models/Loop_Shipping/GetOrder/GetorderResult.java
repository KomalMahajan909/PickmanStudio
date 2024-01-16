package com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetorderResult {


    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("all_order_count")
    private String all_order_count;

    @SerializedName("orders")
    private ArrayList<GetorderData> orders;

    public GetorderResult(String code, String message, String all_order_count, ArrayList<GetorderData> orders) {
        this.code = code;
        this.message = message;
        this.all_order_count = all_order_count;
        this.orders = orders;
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

    public String getAll_order_count() {
        return all_order_count;
    }

    public void setAll_order_count(String all_order_count) {
        this.all_order_count = all_order_count;
    }

    public ArrayList<GetorderData> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<GetorderData> orders) {
        this.orders = orders;
    }
}
