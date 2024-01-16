package com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnLocationSubmit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReturnLocationsubmitData {

        @SerializedName("product_id")
        private String  product_id;

        @SerializedName("sendback_id")
        private String  sendback_id;

        @SerializedName("pending")
        private ArrayList<ReturnLocationsubmitPendingData> pending;

    public ReturnLocationsubmitData(String product_id, String sendback_id, ArrayList<ReturnLocationsubmitPendingData> pending) {
        this.product_id = product_id;
        this.sendback_id = sendback_id;
        this.pending = pending;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSendback_id() {
        return sendback_id;
    }

    public void setSendback_id(String sendback_id) {
        this.sendback_id = sendback_id;
    }

    public ArrayList<ReturnLocationsubmitPendingData> getPending() {
        return pending;
    }

    public void setPending(ArrayList<ReturnLocationsubmitPendingData> pending) {
        this.pending = pending;
    }
}
