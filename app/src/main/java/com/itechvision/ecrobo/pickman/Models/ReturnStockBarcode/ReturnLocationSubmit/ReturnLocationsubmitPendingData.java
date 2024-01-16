package com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnLocationSubmit;

import com.google.gson.annotations.SerializedName;

public class ReturnLocationsubmitPendingData {

    @SerializedName("product_id")
    private String  product_id;

    @SerializedName("order_id")
    private String  order_id;

    public ReturnLocationsubmitPendingData(String product_id, String order_id) {
        this.product_id = product_id;
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
