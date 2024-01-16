package com.itechvision.ecrobo.pickman.Models.TotalArival;

import com.google.gson.annotations.SerializedName;

public class TotalArrivalSubmitResponse {
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

    public TotalArrivalSubmitResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
