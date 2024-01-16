package com.itechvision.ecrobo.pickman.Models.TotalArival;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 10-01-2020.
 */

public class TotalArivalReq {


    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getNyuka_id() {
        return nyuka_id;
    }

    public void setNyuka_id(String nyuka_id) {
        this.nyuka_id = nyuka_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public TotalArivalReq(String authId, String admin_id, String shop_id, String order_no, String product_name, String mode, String nyuka_id, String product_id, String depth, String width, String height, String weight, String quantity, String app_version) {

        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.order_no = order_no;
        this.product_name = product_name;
        this.mode = mode;
        this.nyuka_id = nyuka_id;
        this.product_id = product_id;
        this.depth = depth;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.quantity = quantity;
        this.app_version = app_version;
    }


    @SerializedName("authId")
    private  String authId ;

    @SerializedName("admin_id")
    private  String admin_id ;

    @SerializedName("shop_id")
    private  String shop_id ;

    @SerializedName("order_no")
    private  String order_no ;

    @SerializedName("product_name")
    private  String product_name ;

    @SerializedName("mode")
    private  String mode ;

    @SerializedName("nyuka_id")
    private  String nyuka_id ;

    @SerializedName("product_id")
    private  String product_id ;

    @SerializedName("depth")
    private  String depth ;

    @SerializedName("width")
    private  String width ;

    @SerializedName("height")
    private  String height ;

    @SerializedName("weight")
    private  String weight ;

    @SerializedName("quantity")
    private  String quantity ;

    @SerializedName("app_version")
    private  String app_version ;



}
