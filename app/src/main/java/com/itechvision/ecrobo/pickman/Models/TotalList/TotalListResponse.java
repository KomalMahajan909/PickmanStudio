package com.itechvision.ecrobo.pickman.Models.TotalList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by admin on 09-01-2020.
 */

public class TotalListResponse {

    @SerializedName("code")
    private String code ;

    @SerializedName("message")
    private String message ;

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

    public ArrayList<TolatResultData> getResult() {
        return result;
    }

    public void setResult(ArrayList<TolatResultData> result) {
        this.result = result;
    }

    public TotalListResponse(String code, String message, ArrayList<TolatResultData> result) {

        this.code = code;
        this.message = message;
        this.result = result;
    }

    @SerializedName("results")
    private ArrayList<TolatResultData> result;




}
