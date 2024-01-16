package com.itechvision.ecrobo.pickman.Models.DirectArrival;

import com.google.gson.annotations.SerializedName;

public class DirArrivalListData {


    @SerializedName("name")
    private String name;

    @SerializedName("code")
    private String code;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("location")
    private String location;

    @SerializedName("expiration_date")
    private String expiration_date;

    @SerializedName("lot")
    private String lot;
    @SerializedName("quantity")
    private String quantity;

    public DirArrivalListData(String name, String code, String barcode, String location, String expiration_date, String lot, String quantity) {
        this.name = name;
        this.code = code;
        this.barcode = barcode;
        this.location = location;
        this.expiration_date = expiration_date;
        this.lot = lot;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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
}
