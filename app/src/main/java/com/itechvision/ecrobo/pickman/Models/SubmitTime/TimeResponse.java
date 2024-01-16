package com.itechvision.ecrobo.pickman.Models.SubmitTime;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 15-01-2020.
 */

public class TimeResponse {
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

    public TimeResponse(String code, String message) {

        this.code = code;
        this.message = message;
    }

    @SerializedName("message")
    private String message;
}
