package com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.CheckBarcode;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CheckBarcodeShipResponse {
    @SerializedName("code")
    private String code ;

    @SerializedName("message")
    private String message;

    @SerializedName("all_order_count")
    private String all_order_count ;

    @SerializedName("not_inspection_row_count")
    private String not_inspection_row_count;

    @SerializedName("shortage_row_count")
    private String shortage_row_count ;

    @SerializedName("data")
    private ArrayList<ShipProductList> data;

    public CheckBarcodeShipResponse(String code, String message, String all_order_count, String not_inspection_row_count, String shortage_row_count, ArrayList<ShipProductList> data) {
        this.code = code;
        this.message = message;
        this.all_order_count = all_order_count;
        this.not_inspection_row_count = not_inspection_row_count;
        this.shortage_row_count = shortage_row_count;
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

    public String getAll_order_count() {
        return all_order_count;
    }

    public void setAll_order_count(String all_order_count) {
        this.all_order_count = all_order_count;
    }

    public String getNot_inspection_row_count() {
        return not_inspection_row_count;
    }

    public void setNot_inspection_row_count(String not_inspection_row_count) {
        this.not_inspection_row_count = not_inspection_row_count;
    }

    public String getShortage_row_count() {
        return shortage_row_count;
    }

    public void setShortage_row_count(String shortage_row_count) {
        this.shortage_row_count = shortage_row_count;
    }

    public ArrayList<ShipProductList> getData() {
        return data;
    }

    public void setData(ArrayList<ShipProductList> data) {
        this.data = data;
    }
}
