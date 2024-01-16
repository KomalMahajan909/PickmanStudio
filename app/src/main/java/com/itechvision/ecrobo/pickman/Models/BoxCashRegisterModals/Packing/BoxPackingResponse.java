package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Packing;

import com.google.gson.annotations.SerializedName;

public class BoxPackingResponse {
    @SerializedName("all_order_count")
    private String all_order_count;

    @SerializedName("koguchi_mismatch")
    private String koguchi_mismatch;

    @SerializedName("result")
    private String result;

    public BoxPackingResponse(String all_order_count, String koguchi_mismatch, String result) {
        this.all_order_count = all_order_count;
        this.koguchi_mismatch = koguchi_mismatch;
        this.result = result;
    }

    public String getAll_order_count() {
        return all_order_count;
    }

    public void setAll_order_count(String all_order_count) {
        this.all_order_count = all_order_count;
    }

    public String getKoguchi_mismatch() {
        return koguchi_mismatch;
    }

    public void setKoguchi_mismatch(String koguchi_mismatch) {
        this.koguchi_mismatch = koguchi_mismatch;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
