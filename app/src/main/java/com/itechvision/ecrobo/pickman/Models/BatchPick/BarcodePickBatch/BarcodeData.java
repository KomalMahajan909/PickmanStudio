package com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch;

import com.google.gson.annotations.SerializedName;

public class BarcodeData {


    public BarcodeData(String batchno, String id, String quantity, String diff, String inspection_num, String order_id, String product_id, String code) {
        this.batchno = batchno;
        this.id = id;
        this.quantity = quantity;
        this.diff = diff;
        this.inspection_num = inspection_num;
        this.order_id = order_id;
        this.product_id = product_id;
        this.code = code;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getInspection_num() {
        return inspection_num;
    }

    public void setInspection_num(String inspection_num) {
        this.inspection_num = inspection_num;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @SerializedName("batch_detail_no")
    private String batchno;

    @SerializedName("id")
    private String id;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("diff")
    private String diff;

    @SerializedName("inspection_num")
    private String inspection_num;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
