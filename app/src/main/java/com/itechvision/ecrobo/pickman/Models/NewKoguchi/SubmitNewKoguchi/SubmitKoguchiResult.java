package com.itechvision.ecrobo.pickman.Models.NewKoguchi.SubmitNewKoguchi;

import com.google.gson.annotations.SerializedName;

public class SubmitKoguchiResult {

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

    public SubmitKoguchiResult(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
