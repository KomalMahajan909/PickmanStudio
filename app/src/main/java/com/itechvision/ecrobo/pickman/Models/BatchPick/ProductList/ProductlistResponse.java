package com.itechvision.ecrobo.pickman.Models.BatchPick.ProductList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductlistResponse {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public ProductlistResponse(String code, String message, ArrayList<ProductData> barcode_data) {
        this.code = code;
        this.message = message;
        this.barcode_data = barcode_data;
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

    public ArrayList<ProductData> getBarcode_data() {
        return barcode_data;
    }

    public void setBarcode_data(ArrayList<ProductData> barcode_data) {
        this.barcode_data = barcode_data;
    }

    @SerializedName("barcode_data")
    private ArrayList<ProductData> barcode_data;


}
