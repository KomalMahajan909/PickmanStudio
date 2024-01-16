package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals;

import com.google.gson.annotations.SerializedName;

public class GetBoxNoResponse {
    @SerializedName("result")
    private String result;

    @SerializedName("message")
    private String message;

    @SerializedName("box_no")
    private String box_no;

    public GetBoxNoResponse(String result, String box_no) {
        this.result = result;
        this.box_no = box_no;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBox_no() {
        return box_no;
    }

    public void setBox_no(String box_no) {
        this.box_no = box_no;
    }
}
