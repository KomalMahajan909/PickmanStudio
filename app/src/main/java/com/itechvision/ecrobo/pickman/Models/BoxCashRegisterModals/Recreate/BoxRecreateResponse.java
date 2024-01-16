package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Recreate;

import com.google.gson.annotations.SerializedName;

public class
BoxRecreateResponse {

    @SerializedName("result")
    private String result;

    @SerializedName("message")
    private String message;

    @SerializedName("all_order_count")
    private String all_order_count;

    @SerializedName("packed_count")
    private String packed_count;

    public BoxRecreateResponse(String result, String all_order_count, String packed_count) {
        this.result = result;
        this.all_order_count = all_order_count;
        this.packed_count = packed_count;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAll_order_count() {
        return all_order_count;
    }

    public void setAll_order_count(String all_order_count) {
        this.all_order_count = all_order_count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPacked_count() {
        return packed_count;
    }

    public void setPacked_count(String packed_count) {
        this.packed_count = packed_count;
    }
}
