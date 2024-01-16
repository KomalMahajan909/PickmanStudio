package com.itechvision.ecrobo.pickman.Models.SyakkiID;

import com.google.gson.annotations.SerializedName;

public class SakiData {

    @SerializedName("id")
    private String id ;


     @SerializedName("name")
    private String name;


    public SakiData(String id, String name) {
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
