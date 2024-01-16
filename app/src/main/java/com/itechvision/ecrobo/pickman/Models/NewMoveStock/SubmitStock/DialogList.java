package com.itechvision.ecrobo.pickman.Models.NewMoveStock.SubmitStock;

import com.google.gson.annotations.SerializedName;

public class DialogList {
    @SerializedName("source")
    private String source;

    @SerializedName("destination")
    private String destination;

    @SerializedName("qty")
    private String qty;

    @SerializedName("code")
    private String code;


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
