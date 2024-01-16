package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.KoguchiCount;

import com.google.gson.annotations.SerializedName;

public class KoguchiResp {

    @SerializedName("result")
    private String result;

    @SerializedName("tracking_insert")
    private String tracking_insert;


    public KoguchiResp(String result, String tracking_insert) {
        this.result = result;
        this.tracking_insert = tracking_insert;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTracking_insert() {
        return tracking_insert;
    }

    public void setTracking_insert(String tracking_insert) {
        this.tracking_insert = tracking_insert;
    }
}
