package com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking;

import com.google.gson.annotations.SerializedName;

public class TasbarcodeData {

    @SerializedName("quantity")
    private String qunatity;

    @SerializedName("batch_no")
    private String batch_name;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("code")
    private String code;

    @SerializedName("location")
    private String location;
    @SerializedName("group_id")
    private String psh_id;

    @SerializedName("name")
    private String name;

    @SerializedName("order_no")
    private String order_no;

    @SerializedName("inspection_batch")
    private String inspection_batch;

    @SerializedName("diff")
    private String diff;

    @SerializedName("row_no")
    private String row_no;

    @SerializedName("spec1")
    private String spec1;


    @SerializedName("spec2")
    private String spec2;

    @SerializedName("expiration_date")
    private String expiration_date;

    @SerializedName("lot")
    private String lot;

    public TasbarcodeData(String qunatity, String batch_name, String barcode, String code, String location, String psh_id, String name, String order_no, String inspection_batch, String diff, String row_no, String spec1, String spec2, String expiration_date, String lot) {
        this.qunatity = qunatity;
        this.batch_name = batch_name;
        this.barcode = barcode;
        this.code = code;
        this.location = location;
        this.psh_id = psh_id;
        this.name = name;
        this.order_no = order_no;
        this.inspection_batch = inspection_batch;
        this.diff = diff;
        this.row_no = row_no;
        this.spec1 = spec1;
        this.spec2 = spec2;
        this.expiration_date = expiration_date;
        this.lot = lot;
    }

    public String getQunatity() {
        return qunatity;
    }

    public void setQunatity(String qunatity) {
        this.qunatity = qunatity;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPsh_id() {
        return psh_id;
    }

    public void setPsh_id(String psh_id) {
        this.psh_id = psh_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getInspection_batch() {
        return inspection_batch;
    }

    public void setInspection_batch(String inspection_batch) {
        this.inspection_batch = inspection_batch;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getRow_no() {
        return row_no;
    }

    public void setRow_no(String row_no) {
        this.row_no = row_no;
    }

    public String getSpec1() {
        return spec1;
    }

    public void setSpec1(String spec1) {
        this.spec1 = spec1;
    }

    public String getSpec2() {
        return spec2;
    }

    public void setSpec2(String spec2) {
        this.spec2 = spec2;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }
}



