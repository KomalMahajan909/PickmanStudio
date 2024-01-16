package com.itechvision.ecrobo.pickman.Models.BatchPick.BoxDetail;

import com.google.gson.annotations.SerializedName;

public class BoxData {

    @SerializedName("batch_detail_no")
     private  String batch_detail_no;

    public BoxData(String batch_detail_no, String status) {
        this.batch_detail_no = batch_detail_no;
        this.status = status;
    }

    public String getBatch_detail_no() {
        return batch_detail_no;
    }

    public void setBatch_detail_no(String batch_detail_no) {
        this.batch_detail_no = batch_detail_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
     private  String status;



}
