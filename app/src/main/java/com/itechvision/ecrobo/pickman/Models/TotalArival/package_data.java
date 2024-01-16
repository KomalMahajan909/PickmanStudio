package com.itechvision.ecrobo.pickman.Models.TotalArival;

import com.google.gson.annotations.SerializedName;

public class package_data {

    @SerializedName("value")
    private String value;

    @SerializedName("name")
    private String name;

    public package_data(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
