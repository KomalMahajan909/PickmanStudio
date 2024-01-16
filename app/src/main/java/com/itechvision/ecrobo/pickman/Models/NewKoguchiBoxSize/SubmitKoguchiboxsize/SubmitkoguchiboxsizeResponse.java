package com.itechvision.ecrobo.pickman.Models.NewKoguchiBoxSize.SubmitKoguchiboxsize;

import com.google.gson.annotations.SerializedName;

public class SubmitkoguchiboxsizeResponse {

    @SerializedName("code")
    private String code;
    @SerializedName("status")
    private String status;
 @SerializedName("message")
    private String message;


    public SubmitkoguchiboxsizeResponse(String code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
