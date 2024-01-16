package com.itechvision.ecrobo.pickman.Models.BatchPick;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by admin on 24-01-2020.
 */

public class BatchpickResponse {

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

    public ArrayList<BatchpickResponse_result> getResults() {
        return results;
    }

    public void setResults(ArrayList<BatchpickResponse_result> results) {
        this.results = results;
    }

    public BatchpickResponse(String code, String message, ArrayList<BatchpickResponse_result> results) {

        this.code = code;
        this.message = message;
        this.results = results;
    }

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private ArrayList<BatchpickResponse_result> results;
}
