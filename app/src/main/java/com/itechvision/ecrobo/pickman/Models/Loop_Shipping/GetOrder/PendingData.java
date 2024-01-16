package com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder;

import com.google.gson.annotations.SerializedName;

public class PendingData {

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("code")
    private String code;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("product_qty")
    private String product_qty;

    @SerializedName("category")
    private String category;

    public PendingData(String product_id, String code, String quantity, String product_qty, String category) {
        this.product_id = product_id;
        this.code = code;
        this.quantity = quantity;
        this.product_qty = product_qty;
        this.category = category;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
