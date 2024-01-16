package com.itechvision.ecrobo.pickman.Models.TruckPicking;

import com.google.gson.annotations.SerializedName;

public class ShippingCompanyData {

    @SerializedName("batch_id")
    private  String batch_id ;

    @SerializedName("batch_no")
    private  String batch_no ;

    @SerializedName("shipping_method")
    private  String shipping_method ;

    public ShippingCompanyData(String batch_id, String batch_no, String shipping_method) {
        this.batch_id = batch_id;
        this.batch_no = batch_no;
        this.shipping_method = shipping_method;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getShipping_method() {
        return shipping_method;
    }

    public void setShipping_method(String shipping_method) {
        this.shipping_method = shipping_method;
    }
}


