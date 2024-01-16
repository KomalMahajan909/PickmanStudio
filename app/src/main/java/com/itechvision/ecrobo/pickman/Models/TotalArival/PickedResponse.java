package com.itechvision.ecrobo.pickman.Models.TotalArival;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 14-01-2020.
 */

public class PickedResponse {

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

    public String getNyuka_id() {
        return nyuka_id;
    }

    public void setNyuka_id(String nyuka_id) {
        this.nyuka_id = nyuka_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public Boolean getReturn_time() {
        return return_time;
    }

    public void setReturn_time(Boolean return_time) {
        this.return_time = return_time;
    }

    public PickedResponse(String code, String message, String nyuka_id, String order_no, Boolean return_time) {

        this.code = code;
        this.message = message;
        this.nyuka_id = nyuka_id;
        this.order_no = order_no;
        this.return_time = return_time;
    }

    @SerializedName("code")
    private String code ;


    @SerializedName("message")
    private String message;


    @SerializedName("nyuka_id")
    private String nyuka_id;


    @SerializedName("order_no")
    private String order_no;


    @SerializedName("return_time")
    private Boolean return_time;


}
