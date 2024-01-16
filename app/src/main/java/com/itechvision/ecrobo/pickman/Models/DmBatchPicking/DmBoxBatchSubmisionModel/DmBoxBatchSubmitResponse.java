package com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmBoxBatchSubmisionModel;

import com.google.gson.annotations.SerializedName;

public class DmBoxBatchSubmitResponse {
    @SerializedName("code")
    private  String code;

    @SerializedName("message")
    private  String message;

    @SerializedName("status")
    private String status;

    public DmBoxBatchSubmitResponse(String code, String message, String status) {
        this.code = code;
        this.message = message;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
