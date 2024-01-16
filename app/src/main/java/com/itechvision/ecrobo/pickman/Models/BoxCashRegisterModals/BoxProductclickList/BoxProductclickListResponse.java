package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxProductclickList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BoxProductclickListResponse {

    @SerializedName("result")
    private String result ;
    @SerializedName("message")
    private String message;
    @SerializedName("product_list")
    ArrayList<BoxProductlistclick> product_list  ;

    public BoxProductclickListResponse(String result, ArrayList<BoxProductlistclick> product_list) {
        this.result = result;
        this.product_list = product_list;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<BoxProductlistclick> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(ArrayList<BoxProductlistclick> product_list) {
        this.product_list = product_list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
