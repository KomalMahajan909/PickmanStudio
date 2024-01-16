package com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmGetBatchPicking;

import com.google.gson.annotations.SerializedName;

public class DmProductListData {


    @SerializedName("order_no")
    private  String order_no;

    @SerializedName("orderer")
    private  String orderer;

    @SerializedName("location")
    private String location;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("name")
    private  String name;

    @SerializedName("code")
    private  String code;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("spec1")
    private String spec1;

    @SerializedName("spec2")
    private String spec2;

    @SerializedName("psh_id")
    private String psh_id;

    @SerializedName("frontage_number")
    private String frontage_number;

    public String getFrontage_number() {
        return frontage_number;
    }

    public void setFrontage_number(String frontage_number) {
        this.frontage_number = frontage_number;
    }

    public String getPsh_id() {
        return psh_id;
    }

    public void setPsh_id(String psh_id) {
        this.psh_id = psh_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrderer() {
        return orderer;
    }

    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
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

    public String getSpec1() {
        return spec1;
    }

    public void setSpec1(String spec1) {
        this.spec1 = spec1;
    }

    public String getSpec2() {
        return spec2;
    }

    public void setSpec2(String spec2) {
        this.spec2 = spec2;
    }
}
