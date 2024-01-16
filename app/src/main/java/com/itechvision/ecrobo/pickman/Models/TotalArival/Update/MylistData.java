package com.itechvision.ecrobo.pickman.Models.TotalArival.Update;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 14-01-2020.
 */

public class MylistData {


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNyuka_id() {
        return nyuka_id;
    }

    public void setNyuka_id(String nyuka_id) {
        this.nyuka_id = nyuka_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPicking_no() {
        return picking_no;
    }

    public void setPicking_no(String picking_no) {
        this.picking_no = picking_no;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_barcode() {
        return product_barcode;
    }

    public void setProduct_barcode(String product_barcode) {
        this.product_barcode = product_barcode;
    }

    public String getPicking_user() {
        return picking_user;
    }

    public void setPicking_user(String picking_user) {
        this.picking_user = picking_user;
    }

    public String getPicking_user_name() {
        return picking_user_name;
    }

    public void setPicking_user_name(String picking_user_name) {
        this.picking_user_name = picking_user_name;
    }

    public MylistData(String code, String nyuka_id, String quantity, String picking_no, String product_id, String product_name, String product_barcode, String picking_user, String picking_user_name) {
        this.code = code;
        this.nyuka_id = nyuka_id;
        this.quantity = quantity;
        this.picking_no = picking_no;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_barcode = product_barcode;
        this.picking_user = picking_user;
        this.picking_user_name = picking_user_name;
    }

    @SerializedName("code")
    private String code ;


    @SerializedName("nyuka_id")
    private String nyuka_id ;

    @SerializedName("quantity")
    private String quantity ;


    @SerializedName("picking_no")
    private String picking_no ;


    @SerializedName("product_id")
    private String product_id ;

    @SerializedName("product_name")
    private String product_name ;


    @SerializedName("product_barcode")
    private String product_barcode ;

    @SerializedName("picking_user")
    private String picking_user ;


    @SerializedName("picking_user_name")
    private String picking_user_name ;




}
