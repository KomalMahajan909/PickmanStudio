package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxProductclickList;

import com.google.gson.annotations.SerializedName;

public class BoxProductlistclick {

    @SerializedName("box_no")
    private  String box_no ;
    @SerializedName("quantity")
    private  String quantity ;
    @SerializedName("code")
    private  String code ;
    @SerializedName("product_name")
    private  String product_name ;

    public BoxProductlistclick(String box_no, String quantity, String code, String product_name) {
        this.box_no = box_no;
        this.quantity = quantity;
        this.code = code;
        this.product_name = product_name;
    }

    public String getBox_no() {
        return box_no;
    }

    public void setBox_no(String box_no) {
        this.box_no = box_no;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
