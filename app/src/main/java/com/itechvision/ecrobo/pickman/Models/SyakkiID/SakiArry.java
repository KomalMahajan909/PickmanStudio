package com.itechvision.ecrobo.pickman.Models.SyakkiID;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SakiArry {

    @SerializedName("stock_ids")
    private ArrayList<SakiData> stock_ids;

    public SakiArry(ArrayList<SakiData> stock_ids) {
        this.stock_ids = stock_ids;
    }

    public ArrayList<SakiData> getStock_ids() {
        return stock_ids;
    }

    public void setStock_ids(ArrayList<SakiData> stock_ids) {
        this.stock_ids = stock_ids;
    }
}
