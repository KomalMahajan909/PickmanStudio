package com.itechvision.ecrobo.pickman.Models.NewMoveStock.BarcodeCheck;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductList {

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("name")
    private String name;

    @SerializedName("code")
    private String code;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("spec1")
    private String spec1;

    @SerializedName("spec2")
    private String spec2;

    @SerializedName("stocks")
    private ArrayList<StockList> stocks;

    @SerializedName("total_quantity")
    private String total_quantity;

    public ProductList(String product_id, String name, String code, String barcode, String spec1, String spec2, ArrayList<StockList> stocks, String total_quantity) {
        this.product_id = product_id;
        this.name = name;
        this.code = code;
        this.barcode = barcode;
        this.spec1 = spec1;
        this.spec2 = spec2;
        this.stocks = stocks;
        this.total_quantity = total_quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

    public ArrayList<StockList> getStocks() {
        return stocks;
    }

    public void setStocks(ArrayList<StockList> stocks) {
        this.stocks = stocks;
    }

    public String getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(String total_quantity) {
        this.total_quantity = total_quantity;
    }
}