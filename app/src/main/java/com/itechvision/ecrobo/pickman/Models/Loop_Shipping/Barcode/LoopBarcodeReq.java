package com.itechvision.ecrobo.pickman.Models.Loop_Shipping.Barcode;

import com.google.gson.annotations.SerializedName;

public class LoopBarcodeReq {

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("authId")
    private String authId;

    @SerializedName("shop_id")
    private String shop_id;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("all_product_ids")
    private String all_barcode;


    public LoopBarcodeReq(String admin_id, String authId, String shop_id, String order_id, String barcode, String all_barcode) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.order_id = order_id;
        this.barcode = barcode;
        this.all_barcode = all_barcode;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAll_barcode() {
        return all_barcode;
    }

    public void setAll_barcode(String all_barcode) {
        this.all_barcode = all_barcode;
    }
}


