package com.itechvision.ecrobo.pickman.Models.NewShipping.CheckOrder;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.SelectedprinterList.selectedlistdata;

import java.util.ArrayList;
import java.util.Map;

public class CheckOrderNoProductResponse {
    @SerializedName("code")
    private String code ;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ArrayList<Map<String,String>> data ;

    public CheckOrderNoProductResponse(String code, String message, ArrayList<Map<String,String>> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ArrayList<Map<String,String>> getData() {
        return data;
    }

    public void setData(ArrayList<Map<String,String>> data) {
        this.data = data;
    }

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
}
