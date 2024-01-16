package com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TasBarcoderesult {

    @SerializedName("code")
    private String code ;

    @SerializedName("message")
    private String message ;

    @SerializedName("pending_sku")
    private String pending_sku ;

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

    public String getPending_sku() {
        return pending_sku;
    }

    public void setPending_sku(String pending_sku) {
        this.pending_sku = pending_sku;
    }

    public ArrayList<TasbarcodeData> getResults() {
        return results;
    }

    public void setResults(ArrayList<TasbarcodeData> results) {
        this.results = results;
    }

    public TasBarcoderesult(String code, String message, String pending_sku, ArrayList<TasbarcodeData> results) {
        this.code = code;
        this.message = message;
        this.pending_sku = pending_sku;
        this.results = results;
    }

    @SerializedName("results")
    private ArrayList<TasbarcodeData >results ;


}
