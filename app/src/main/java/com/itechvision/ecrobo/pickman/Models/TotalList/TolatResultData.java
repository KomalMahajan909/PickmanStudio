package com.itechvision.ecrobo.pickman.Models.TotalList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by admin on 09-01-2020.
 */

public class TolatResultData {
    public ArrayList<shopdata> getShop_data() {
        return shop_data;
    }

    public void setShop_data(ArrayList<shopdata> shop_data) {
        this.shop_data = shop_data;
    }

    public TolatResultData(ArrayList<shopdata> shop_data) {

        this.shop_data = shop_data;
    }

    @SerializedName("shop_data")
    private ArrayList<shopdata> shop_data ;



}
