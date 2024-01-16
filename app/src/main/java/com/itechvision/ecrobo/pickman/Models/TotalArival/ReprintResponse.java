package com.itechvision.ecrobo.pickman.Models.TotalArival;

import com.google.gson.annotations.SerializedName;

public class ReprintResponse {
    @SerializedName("code")
    private String code ;


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

    public ReprintResponse(String code, String message) {

        this.code = code;
        this.message = message;
    }

    @SerializedName("message")
    private String message;
}
