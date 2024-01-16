package com.itechvision.ecrobo.pickman.Models.W6Arrival.GetOrderArrival;

import com.google.gson.annotations.SerializedName;

public class GetArrivalBarcodeOrderNoReq {
    @SerializedName("admin_id")
    private  String admin_id;

    @SerializedName("authId")
    private  String authId;

    @SerializedName("shop_id")
    private  String shop_id;

    @SerializedName("code_barcode")
    private  String barcode;

    @SerializedName("order_no")
    private  String order_no;

    public GetArrivalBarcodeOrderNoReq(String admin_id, String authId, String shop_id, String barcode, String order_no) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.barcode = barcode;
        this.order_no = order_no;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }
}
