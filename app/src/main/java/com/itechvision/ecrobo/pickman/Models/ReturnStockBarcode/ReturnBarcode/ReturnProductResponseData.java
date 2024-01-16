package com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnBarcode;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReturnProductResponseData {

    @SerializedName("product_id")
    private String  product_id;

    @SerializedName("sendback_id")
    private String  sendback_id;

    @SerializedName("order_id")
    private String  order_id;

    @SerializedName("products")
    private ArrayList<ReturnProductResponseDataProducts> products;

    public ReturnProductResponseData(String product_id, String sendback_id, String order_id, ArrayList<ReturnProductResponseDataProducts> products) {
        this.product_id = product_id;
        this.sendback_id = sendback_id;
        this.order_id = order_id;
        this.products = products;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSendback_id() {
        return sendback_id;
    }

    public void setSendback_id(String sendback_id) {
        this.sendback_id = sendback_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public ArrayList<ReturnProductResponseDataProducts> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ReturnProductResponseDataProducts> products) {
        this.products = products;
    }
}
