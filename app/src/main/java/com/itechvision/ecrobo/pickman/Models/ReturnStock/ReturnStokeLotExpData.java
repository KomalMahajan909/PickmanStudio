package com.itechvision.ecrobo.pickman.Models.ReturnStock;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReturnStokeLotExpData {

    @SerializedName("lot")
    private String lot;

    @SerializedName("expiration_date")
    private String expiration_date;

    @SerializedName("num")
    private String num;

    public ReturnStokeLotExpData(String lot, String expiration_date, String num) {
        this.lot = lot;
        this.expiration_date = expiration_date;
        this.num = num;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
