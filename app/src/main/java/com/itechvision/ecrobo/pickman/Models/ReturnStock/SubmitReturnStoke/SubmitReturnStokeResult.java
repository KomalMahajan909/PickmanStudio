package com.itechvision.ecrobo.pickman.Models.ReturnStock.SubmitReturnStoke;

import com.google.gson.annotations.SerializedName;

public class SubmitReturnStokeResult {

    @SerializedName("code")
    private String code ;

    @SerializedName("message")
    private String message ;

    @SerializedName("sendback_id")
    private String sendback_id ;


    @SerializedName("order_id")
    private String order_id ;

    public SubmitReturnStokeResult(String code, String message, String sendback_id, String order_id) {
        this.code = code;
        this.message = message;
        this.sendback_id = sendback_id;
        this.order_id = order_id;
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

    public String getSendback_id() {
        return sendback_id;
    }

    public void setSendback_id(String sendback_id) {
        this.sendback_id = sendback_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
