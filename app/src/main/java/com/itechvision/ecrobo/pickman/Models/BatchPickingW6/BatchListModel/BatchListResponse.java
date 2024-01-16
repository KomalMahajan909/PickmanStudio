package com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BatchListModel;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BatchListModel.BatchListData;


import java.util.ArrayList;

public class BatchListResponse {
    @SerializedName("code")
    private  String code;

    @SerializedName("message")
    private  String message;

    @SerializedName("results")
    private ArrayList<BatchListData> results;

    public BatchListResponse(String code, String message, ArrayList<BatchListData> results) {
        this.code = code;
        this.message = message;
        this.results = results;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<BatchListData> getResults() {
        return results;
    }

    public void setResults(ArrayList<BatchListData> results) {
        this.results = results;
    }
}
