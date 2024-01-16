package com.itechvision.ecrobo.pickman.Models.OnetoOneSlip_printer;

import com.google.gson.annotations.SerializedName;

public class Daimaru_OnetoOne_OrderID_Res {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("comment")
    private String comment;

    public Daimaru_OnetoOne_OrderID_Res(String code, String message, String order_id, String comment) {
        this.code = code;
        this.message = message;
        this.order_id = order_id;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
