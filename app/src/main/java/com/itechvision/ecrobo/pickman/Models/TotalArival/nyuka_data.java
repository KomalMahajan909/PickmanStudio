package com.itechvision.ecrobo.pickman.Models.TotalArival;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 10-01-2020.
 */

public class nyuka_data {



    @SerializedName("lot")
    private String lot;

    @SerializedName("print")
    private String print;

    @SerializedName("nyuka_id")
    private String nyuka_id ;

    @SerializedName("quantity")
    private String quantity ;

    @SerializedName("case_qty")
    private String case_qty;

    @SerializedName("code")
    private String code ;

    @SerializedName("depth")
    private String depth ;

    @SerializedName("product_name")
    private String product_name ;

    @SerializedName("width")
    private String width ;

    @SerializedName("picking_no")
    private String picking_no ;

    @SerializedName("attribute_type")
    private String attribute_type;

    @SerializedName("height")
    private String height ;

    @SerializedName("weight")
    private String weight ;

    @SerializedName("product_id")
    private String product_id ;


    @SerializedName("piece_depth")
    private String piece_depth ;

    @SerializedName("piece_width")
    private String piece_width ;

    @SerializedName("piece_height")
    private String piece_height ;

    @SerializedName("piece_weight")
    private String piece_weight ;

    @SerializedName("package")
    private String package1 ;

    @SerializedName("comment")
    private String comment ;

    @SerializedName("product_barcode")
    private String product_barcode ;

    @SerializedName("rsv_date")
    private String date;

    @SerializedName("disposal_flag")
    private String disposal_flag;

    public nyuka_data(String lot, String print, String nyuka_id, String quantity, String case_qty, String code, String depth, String product_name, String width, String picking_no, String attribute_type, String height, String weight, String product_id, String piece_depth, String piece_width, String piece_height, String piece_weight, String package1, String comment, String product_barcode, String date, String disposal_flag) {
        this.lot = lot;
        this.print = print;
        this.nyuka_id = nyuka_id;
        this.quantity = quantity;
        this.case_qty = case_qty;
        this.code = code;
        this.depth = depth;
        this.product_name = product_name;
        this.width = width;
        this.picking_no = picking_no;
        this.attribute_type = attribute_type;
        this.height = height;
        this.weight = weight;
        this.product_id = product_id;
        this.piece_depth = piece_depth;
        this.piece_width = piece_width;
        this.piece_height = piece_height;
        this.piece_weight = piece_weight;
        this.package1 = package1;
        this.comment = comment;
        this.product_barcode = product_barcode;
        this.date = date;
        this.disposal_flag = disposal_flag;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
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

    public String getCase_qty() {
        return case_qty;
    }

    public void setCase_qty(String case_qty) {
        this.case_qty = case_qty;
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

    public String getPicking_no() {
        return picking_no;
    }

    public void setPicking_no(String picking_no) {
        this.picking_no = picking_no;
    }

    public String getAttribute_type() {
        return attribute_type;
    }

    public void setAttribute_type(String attribute_type) {
        this.attribute_type = attribute_type;
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

    public String getProduct_barcode() {
        return product_barcode;
    }

    public void setProduct_barcode(String product_barcode) {
        this.product_barcode = product_barcode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisposal_flag() {
        return disposal_flag;
    }

    public void setDisposal_flag(String disposal_flag) {
        this.disposal_flag = disposal_flag;
    }
}
