package com.itechvision.ecrobo.pickman.Models.TasBatchInvoice;

import com.google.gson.annotations.SerializedName;

public class SubmitBatchInvoice {
    public SubmitBatchInvoice(String code, String message, String status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    @SerializedName("code")
    private String code;

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

    @SerializedName("message")
    private String message;


    @SerializedName("status")
    private String status;


}
