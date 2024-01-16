package com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking;

import com.google.gson.annotations.SerializedName;

public class tasSubmitRequest {

    @SerializedName("authId")
    private String  authId;

    @SerializedName("admin_id")
    private String  admin_id;

    @SerializedName("create_date")
    private String  create_date;

    @SerializedName("shop_id")
    private String  shop_id;

    @SerializedName("batch_id")
    private String  batch_id;

    @SerializedName("barcode")
    private String  barcode;

    @SerializedName("quantity")
    private String  quantity;

    @SerializedName("psh_id")
    private String  psh_id;

    @SerializedName("row_no")
    private  String row_no ;

    @SerializedName("app_version")
    private  String app_version ;

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

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
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

    public String getPsh_id() {
        return psh_id;
    }

    public void setPsh_id(String psh_id) {
        this.psh_id = psh_id;
    }

    public String getRow_no() {
        return row_no;
    }

    public void setRow_no(String row_no) {
        this.row_no = row_no;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public tasSubmitRequest(String authId, String admin_id, String create_date, String shop_id, String batch_id, String barcode, String quantity, String psh_id, String row_no, String app_version) {
        this.authId = authId;
        this.admin_id = admin_id;
        this.create_date = create_date;
        this.shop_id = shop_id;
        this.batch_id = batch_id;
        this.barcode = barcode;
        this.quantity = quantity;
        this.psh_id = psh_id;
        this.row_no = row_no;
        this.app_version = app_version;
    }
}
