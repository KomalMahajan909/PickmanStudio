package com.itechvision.ecrobo.pickman.Models.BatchPick.ProductList;

import com.google.gson.annotations.SerializedName;

public class ProductData {
    @SerializedName("batch_detail_no")
    private String batch_detail_no;

    public ProductData(String batch_detail_no, String quantity) {
        this.batch_detail_no = batch_detail_no;
        this.quantity = quantity;
    }

    public String getBatch_detail_no() {
        return batch_detail_no;
    }

    public void setBatch_detail_no(String batch_detail_no) {
        this.batch_detail_no = batch_detail_no;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @SerializedName("quantity")
    private String quantity;



}
