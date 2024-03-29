package com.itechvision.ecrobo.pickman.Models.Invoice_Printer.SubmitInvoice;

import com.google.gson.annotations.SerializedName;

public class SubmitInvoiceResult {

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

    public SubmitInvoiceResult(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
