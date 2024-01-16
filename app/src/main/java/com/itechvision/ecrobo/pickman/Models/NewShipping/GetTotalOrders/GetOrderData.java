package com.itechvision.ecrobo.pickman.Models.NewShipping.GetTotalOrders;

import com.google.gson.annotations.SerializedName;

public class GetOrderData {

    @SerializedName("all_order_count")
    private String all_order_count;

    public GetOrderData(String all_order_count) {
        this.all_order_count = all_order_count;
    }

    public String getAll_order_count() {
        return all_order_count;
    }

    public void setAll_order_count(String all_order_count) {
        this.all_order_count = all_order_count;
    }

}
