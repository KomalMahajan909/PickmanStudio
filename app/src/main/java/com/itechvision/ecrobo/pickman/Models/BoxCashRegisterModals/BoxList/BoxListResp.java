package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BoxListResp {

    @SerializedName("result")
    private String result;

    @SerializedName("message")
    private String message;

    @SerializedName("boxes")
    private ArrayList<BoxListData> boxes;

    public BoxListResp(String result, ArrayList<BoxListData> boxes) {
        this.result = result;
        this.boxes = boxes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<BoxListData> getBoxes() {
        return boxes;
    }

    public void setBoxes(ArrayList<BoxListData> boxes) {
        this.boxes = boxes;
    }
}
