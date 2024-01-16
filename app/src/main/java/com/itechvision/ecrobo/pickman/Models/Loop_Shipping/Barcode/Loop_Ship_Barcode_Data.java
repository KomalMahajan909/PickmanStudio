package com.itechvision.ecrobo.pickman.Models.Loop_Shipping.Barcode;

import com.google.gson.annotations.SerializedName;

public class Loop_Ship_Barcode_Data {


    @SerializedName("product_id")
    private String product_id;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("serial_no_flg")
    private String serial_no_flg;

    @SerializedName("psh_id")
    private String psh_id;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("productname")
    private String productname;

    @SerializedName("productcode")
    private String productcode;

    @SerializedName("lotno")
    private String lotno;

    public Loop_Ship_Barcode_Data(String product_id, String barcode, String serial_no_flg, String psh_id, String quantity, String productname, String productcode, String lotno) {
        this.product_id = product_id;
        this.barcode = barcode;
        this.serial_no_flg = serial_no_flg;
        this.psh_id = psh_id;
        this.quantity = quantity;
        this.productname = productname;
        this.productcode = productcode;
        this.lotno = lotno;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSerial_no_flg() {
        return serial_no_flg;
    }

    public void setSerial_no_flg(String serial_no_flg) {
        this.serial_no_flg = serial_no_flg;
    }

    public String getPsh_id() {
        return psh_id;
    }

    public void setPsh_id(String psh_id) {
        this.psh_id = psh_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getLotno() {
        return lotno;
    }

    public void setLotno(String lotno) {
        this.lotno = lotno;
    }
}
