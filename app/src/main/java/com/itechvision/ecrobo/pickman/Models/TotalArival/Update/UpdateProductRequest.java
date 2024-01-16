package com.itechvision.ecrobo.pickman.Models.TotalArival.Update;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 14-01-2020.
 */

public class UpdateProductRequest {

    @SerializedName("authId")
    private  String authId ;

    @SerializedName("admin_id")
    private  String admin_id ;

    @SerializedName("shop_id")
    private  String shop_id ;


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


    @SerializedName("piece_depth")
    private  String piece_depth ;

    @SerializedName("piece_width")
    private  String piece_width ;

    @SerializedName("piece_height")
    private  String piece_height ;

    @SerializedName("piece_weight")
    private  String piece_weight ;

    @SerializedName("package")
    private String type;

    @SerializedName("app_version")
    private  String app_version ;

    @SerializedName("disposal_flag")
    private String disposal_flag;

    @SerializedName("case_qty")
    private String case_qty;

    public UpdateProductRequest(String authId, String admin_id, String shop_id, String product_id, String depth, String width, String height, String weight, String piece_depth, String piece_width, String piece_height, String piece_weight, String type, String app_version, String disposal_flag, String case_qty) {
        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.product_id = product_id;
        this.depth = depth;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.piece_depth = piece_depth;
        this.piece_width = piece_width;
        this.piece_height = piece_height;
        this.piece_weight = piece_weight;
        this.type = type;
        this.app_version = app_version;
        this.disposal_flag = disposal_flag;
        this.case_qty = case_qty;
    }

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

    public String getPiece_depth() {
        return piece_depth;
    }

    public void setPiece_depth(String piece_depth) {
        this.piece_depth = piece_depth;
    }

    public String getPiece_width() {
        return piece_width;
    }

    public void setPiece_width(String piece_width) {
        this.piece_width = piece_width;
    }

    public String getPiece_height() {
        return piece_height;
    }

    public void setPiece_height(String piece_height) {
        this.piece_height = piece_height;
    }

    public String getPiece_weight() {
        return piece_weight;
    }

    public void setPiece_weight(String piece_weight) {
        this.piece_weight = piece_weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getDisposal_flag() {
        return disposal_flag;
    }

    public void setDisposal_flag(String disposal_flag) {
        this.disposal_flag = disposal_flag;
    }

    public String getCase_qty() {
        return case_qty;
    }

    public void setCase_qty(String case_qty) {
        this.case_qty = case_qty;
    }
}
