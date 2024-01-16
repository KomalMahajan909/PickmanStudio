package com.itechvision.ecrobo.pickman.Models.NewShipping.CheckOrder;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetProductDetail {

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("code")
    private String code;

    @SerializedName("product_name")
    private String product_name;

    @SerializedName("location")
    private String location;

    @SerializedName("order_sub_id")
    private String order_sub_id;


    @SerializedName("product_stock_history_id")
    private String product_stock_history_id;

    @SerializedName("lot")
    private String lot;

    @SerializedName("category")
    private String category;

    @SerializedName("pikking_method")
    private String pikking_method;


    public GetProductDetail(String quantity, String barcode, String code, String product_name, String location, String order_sub_id, String product_stock_history_id, String lot, String category, String pikking_method) {
        this.quantity = quantity;
        this.barcode = barcode;
        this.code = code;
        this.product_name = product_name;
        this.location = location;
        this.order_sub_id = order_sub_id;
        this.product_stock_history_id = product_stock_history_id;
        this.lot = lot;
        this.category = category;
        this.pikking_method = pikking_method;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrder_sub_id() {
        return order_sub_id;
    }

    public void setOrder_sub_id(String order_sub_id) {
        this.order_sub_id = order_sub_id;
    }

    public String getProduct_stock_history_id() {
        return product_stock_history_id;
    }

    public void setProduct_stock_history_id(String product_stock_history_id) {
        this.product_stock_history_id = product_stock_history_id;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPikking_method() {
        return pikking_method;
    }

    public void setPikking_method(String pikking_method) {
        this.pikking_method = pikking_method;
    }
}
