package com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnBarcode;

import com.google.gson.annotations.SerializedName;

public class ReturnProductResponseDataProducts {


    @SerializedName("product_id")
    private String  product_id;

    @SerializedName("code")
    private String  code;

    @SerializedName("barcode")
    private String  barcode;

    @SerializedName("quantity")
    private String  quantity;

    @SerializedName("condition_id")
    private String  condition_id;

    @SerializedName("sendback_detail_id")
    private String  sendback_detail_id;

    @SerializedName("rest_qty")
    private String  rest_qty;

    @SerializedName("moved_qty")
    private String  moved_qty;

    @SerializedName("pname")
    private String  pname ;

    public ReturnProductResponseDataProducts(String product_id, String code, String barcode, String quantity, String condition_id, String sendback_detail_id, String rest_qty, String moved_qty, String pname) {
        this.product_id = product_id;
        this.code = code;
        this.barcode = barcode;
        this.quantity = quantity;
        this.condition_id = condition_id;
        this.sendback_detail_id = sendback_detail_id;
        this.rest_qty = rest_qty;
        this.moved_qty = moved_qty;
        this.pname = pname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCondition_id() {
        return condition_id;
    }

    public void setCondition_id(String condition_id) {
        this.condition_id = condition_id;
    }

    public String getSendback_detail_id() {
        return sendback_detail_id;
    }

    public void setSendback_detail_id(String sendback_detail_id) {
        this.sendback_detail_id = sendback_detail_id;
    }

    public String getRest_qty() {
        return rest_qty;
    }

    public void setRest_qty(String rest_qty) {
        this.rest_qty = rest_qty;
    }

    public String getMoved_qty() {
        return moved_qty;
    }

    public void setMoved_qty(String moved_qty) {
        this.moved_qty = moved_qty;
    }
}
