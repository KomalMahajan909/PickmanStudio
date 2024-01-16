package com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.GetBatchStatus;

import com.google.gson.annotations.SerializedName;

public class Batchstatusdata {

    @SerializedName("pending")
     private String pending;

    public Batchstatusdata(String pending, String working, String completed) {
        this.pending = pending;
        this.working = working;
        this.completed = completed;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public String getWorking() {
        return working;
    }

    public void setWorking(String working) {
        this.working = working;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    @SerializedName("working")
     private String working;


    @SerializedName("completed")
     private String completed;


}
