package com.itechvision.ecrobo.pickman.Models.NewKoguchi.KoguchiShipCompany;

import com.google.gson.annotations.SerializedName;

public class NKoguchiShipData {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String named;

    public NKoguchiShipData(String id, String named) {
        this.id = id;
        this.named = named;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamed() {
        return named;
    }

    public void setNamed(String named) {
        this.named = named;
    }
}
