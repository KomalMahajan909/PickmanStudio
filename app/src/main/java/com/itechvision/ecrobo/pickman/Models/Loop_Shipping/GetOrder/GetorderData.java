package com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder;

import com.google.gson.annotations.SerializedName;

public class GetorderData {

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("order_no")
    private String order_no;

    @SerializedName("name")
    private String name;

    @SerializedName("batch_detail_name")
    private String batch_detail_name;

    @SerializedName("batch_detail_id")
    private String batch_detail_id;

    public GetorderData(String order_id, String order_no, String name, String batch_detail_name, String batch_detail_id) {
        this.order_id = order_id;
        this.order_no = order_no;
        this.name = name;
        this.batch_detail_name = batch_detail_name;
        this.batch_detail_id = batch_detail_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatch_detail_name() {
        return batch_detail_name;
    }

    public void setBatch_detail_name(String batch_detail_name) {
        this.batch_detail_name = batch_detail_name;
    }

    public String getBatch_detail_id() {
        return batch_detail_id;
    }

    public void setBatch_detail_id(String batch_detail_id) {
        this.batch_detail_id = batch_detail_id;
    }
}

