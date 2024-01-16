package com.itechvision.ecrobo.pickman.Models.CustomerArrival.SubmitArrival;

import com.google.gson.annotations.SerializedName;

public class SubmitArrivalResponse {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public SubmitArrivalResponse(String code, String message) {
        this.code = code;
        this.message = message;
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
}
