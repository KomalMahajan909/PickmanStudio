package com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking;

import com.google.gson.annotations.SerializedName;

public class TasSubmitData {

    public String getBatch_status() {
        return batch_status;
    }

    public void setBatch_status(String batch_status) {
        this.batch_status = batch_status;
    }

    public String getRow_no() {
        return row_no;
    }

    public void setRow_no(String row_no) {
        this.row_no = row_no;
    }

    public TasSubmitData(String batch_status, String row_no) {
        this.batch_status = batch_status;
        this.row_no = row_no;
    }

    @SerializedName("batch_status")
    private String batch_status;

    @SerializedName("row_no")
    private String row_no;


}
