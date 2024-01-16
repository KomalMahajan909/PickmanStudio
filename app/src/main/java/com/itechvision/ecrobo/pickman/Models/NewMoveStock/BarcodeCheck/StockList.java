package com.itechvision.ecrobo.pickman.Models.NewMoveStock.BarcodeCheck;

import com.google.gson.annotations.SerializedName;

public class StockList {

    @SerializedName("id")
    private String id;

    @SerializedName("stock_type_id")
    private String stock_type_id;

    @SerializedName("code")
    private String code;

    @SerializedName("location")
    private String location;

    @SerializedName("expiration_date")
    private String expiration_date;

    @SerializedName("lot")
    private String lot;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("stock_type_label")
    private String stock_type_label;

    @SerializedName("arrival_date")
    private String arrival_date;

    public StockList(String id, String stock_type_id, String code, String location, String expiration_date, String lot, String quantity, String stock_type_label, String arrival_date) {
        this.id = id;
        this.stock_type_id = stock_type_id;
        this.code = code;
        this.location = location;
        this.expiration_date = expiration_date;
        this.lot = lot;
        this.quantity = quantity;
        this.stock_type_label = stock_type_label;
        this.arrival_date = arrival_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStock_type_id() {
        return stock_type_id;
    }

    public void setStock_type_id(String stock_type_id) {
        this.stock_type_id = stock_type_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStock_type_label() {
        return stock_type_label;
    }

    public void setStock_type_label(String stock_type_label) {
        this.stock_type_label = stock_type_label;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
    }
}
