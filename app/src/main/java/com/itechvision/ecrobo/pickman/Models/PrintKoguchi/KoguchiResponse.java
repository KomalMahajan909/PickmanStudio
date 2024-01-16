package com.itechvision.ecrobo.pickman.Models.PrintKoguchi;

import com.google.gson.annotations.SerializedName;

public class KoguchiResponse {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public KoguchiResponse(String code, String message) {
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
