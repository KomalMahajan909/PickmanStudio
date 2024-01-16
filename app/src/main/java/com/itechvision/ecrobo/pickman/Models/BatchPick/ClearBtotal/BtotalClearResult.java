package com.itechvision.ecrobo.pickman.Models.BatchPick.ClearBtotal;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking.TasSubmitData;

import java.util.ArrayList;

public class BtotalClearResult {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

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

    public BtotalClearResult(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
