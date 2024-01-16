package com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.StockClassification;

import com.google.gson.annotations.SerializedName;

public class StockClassificationListData {

    @SerializedName("id")
    private  String id;

    @SerializedName("name")
    private  String name;

    public StockClassificationListData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
