package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Packing;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetPackingListResponse {

    @SerializedName("result")
    private String result;

    @SerializedName("box_no")
    private String box_no;

    @SerializedName("message")
    private String message;


    @SerializedName("packed_boxes")
    private ArrayList<PackListData> packed_boxes;

    public GetPackingListResponse(String result, String box_no, ArrayList<PackListData> packed_boxes) {
        this.result = result;
        this.box_no = box_no;
        this.packed_boxes = packed_boxes;
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

    public String getBox_no() {
        return box_no;
    }

    public void setBox_no(String box_no) {
        this.box_no = box_no;
    }

    public ArrayList<PackListData> getPacked_boxes() {
        return packed_boxes;
    }

    public void setPacked_boxes(ArrayList<PackListData> packed_boxes) {
        this.packed_boxes = packed_boxes;
    }
}
