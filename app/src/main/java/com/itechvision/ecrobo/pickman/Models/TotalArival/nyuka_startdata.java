package com.itechvision.ecrobo.pickman.Models.TotalArival;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 10-01-2020.
 */

public class nyuka_startdata {

    @SerializedName("package")
    private String package1;

    @SerializedName("comment")
    private String comment;

    @SerializedName("lot")
    private String lot;

    @SerializedName("piece_height")
    private String piece_height;

    @SerializedName("piece_width")
    private String piece_width;

    @SerializedName("piece_depth")
    private String piece_depth;

    @SerializedName("piece_weight")
    private String piece_weight;

    @SerializedName("attribute_type")
    private String attribute_type;

    @SerializedName("case_qty")
    private String case_qty;

    @SerializedName("nyuka_id")
    private String nyuka_id ;

    @SerializedName("quantity")
    private String quantity ;

    @SerializedName("code")
    private String code ;

    @SerializedName("depth")
    private String depth ;

    @SerializedName("product_name")
    private String product_name ;

    @SerializedName("width")
    private String width ;

    @SerializedName("height")
    private String height ;

    @SerializedName("weight")
    private String weight ;

    @SerializedName("product_id")
    private String product_id ;

    @SerializedName("product_barcode")
    private String product_barcode ;

    @SerializedName("order_no")
    private String order_no ;

    @SerializedName("rsv_date")
    private String rsv_date;


    public nyuka_startdata(String package1, String comment, String lot, String piece_height, String piece_width, String piece_depth, String piece_weight, String attribute_type, String case_qty, String nyuka_id, String quantity, String code, String depth, String product_name, String width, String height, String weight, String product_id, String product_barcode, String order_no, String rsv_date) {
        this.package1 = package1;
        this.comment = comment;
        this.lot = lot;
        this.piece_height = piece_height;
        this.piece_width = piece_width;
        this.piece_depth = piece_depth;
        this.piece_weight = piece_weight;
        this.attribute_type = attribute_type;
        this.case_qty = case_qty;
        this.nyuka_id = nyuka_id;
        this.quantity = quantity;
        this.code = code;
        this.depth = depth;
        this.product_name = product_name;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.product_id = product_id;
        this.product_barcode = product_barcode;
        this.order_no = order_no;
        this.rsv_date = rsv_date;
    }

    public String getPackage1() {
        return package1;
    }

    public void setPackage1(String package1) {
        this.package1 = package1;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPiece_height() {
        return piece_height;
    }

    public void setPiece_height(String piece_height) {
        this.piece_height = piece_height;
    }

    public String getPiece_width() {
        return piece_width;
    }

    public void setPiece_width(String piece_width) {
        this.piece_width = piece_width;
    }

    public String getPiece_depth() {
        return piece_depth;
    }

    public void setPiece_depth(String piece_depth) {
        this.piece_depth = piece_depth;
    }

    public String getPiece_weight() {
        return piece_weight;
    }

    public void setPiece_weight(String piece_weight) {
        this.piece_weight = piece_weight;
    }

    public String getRsv_date() {
        return rsv_date;
    }

    public void setRsv_date(String rsv_date) {
        this.rsv_date = rsv_date;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
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

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_barcode() {
        return product_barcode;
    }

    public void setProduct_barcode(String product_barcode) {
        this.product_barcode = product_barcode;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getAttribute_type() {
        return attribute_type;
    }

    public void setAttribute_type(String attribute_type) {
        this.attribute_type = attribute_type;
    }

    public String getCase_qty() {
        return case_qty;
    }

    public void setCase_qty(String case_qty) {
        this.case_qty = case_qty;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }
}
