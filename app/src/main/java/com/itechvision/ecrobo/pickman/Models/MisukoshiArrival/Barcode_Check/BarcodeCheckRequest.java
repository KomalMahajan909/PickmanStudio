package com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Barcode_Check;

import com.google.gson.annotations.SerializedName;

public class BarcodeCheckRequest {

    @SerializedName("admin_id")
    private  String admin_id;

    @SerializedName("authId")
    private  String authId;

    @SerializedName("shop_id")
    private  String shop_id;

    @SerializedName("order_no")
    private  String order_no;

    @SerializedName("code_barcode")
    private  String code_barcode;

    public BarcodeCheckRequest(String admin_id, String authId, String shop_id, String order_no, String code_barcode) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.order_no = order_no;
        this.code_barcode = code_barcode;
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

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getCode_barcode() {
        return code_barcode;
    }

    public void setCode_barcode(String code_barcode) {
        this.code_barcode = code_barcode;
    }
}
