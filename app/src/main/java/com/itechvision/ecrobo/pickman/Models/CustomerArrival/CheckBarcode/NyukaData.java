package com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckBarcode;

import com.google.gson.annotations.SerializedName;

public class NyukaData {

    @SerializedName("nyuka_id")
    private String nyuka_id;

    @SerializedName("code")
    private String code;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("spec1")
    private String spec1;

    @SerializedName("spec2")
    private String spec2;

    @SerializedName("name")
    private String name;

    public NyukaData(String nyuka_id, String code, String barcode, String product_id, String spec1, String spec2, String name) {
        this.nyuka_id = nyuka_id;
        this.code = code;
        this.barcode = barcode;
        this.product_id = product_id;
        this.spec1 = spec1;
        this.spec2 = spec2;
        this.name = name;
    }

    public String getNyuka_id() {
        return nyuka_id;
    }

    public void setNyuka_id(String nyuka_id) {
        this.nyuka_id = nyuka_id;
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

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
