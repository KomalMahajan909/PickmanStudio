package com.itechvision.ecrobo.pickman.Models.TasBatchInvoice;

import com.google.gson.annotations.SerializedName;

public class getTasinvoiceRespose {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;
    @SerializedName("box_no")
    private String fontage_no;

    public getTasinvoiceRespose(String code, String message, String fontage_no) {
        this.code = code;
        this.message = message;
        this.fontage_no = fontage_no;
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

    public String getFontage_no() {
        return fontage_no;
    }

    public void setFontage_no(String fontage_no) {
        this.fontage_no = fontage_no;
    }




}
