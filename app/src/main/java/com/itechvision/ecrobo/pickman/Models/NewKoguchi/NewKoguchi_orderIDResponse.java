package com.itechvision.ecrobo.pickman.Models.NewKoguchi;

import com.google.gson.annotations.SerializedName;

public class NewKoguchi_orderIDResponse {

@SerializedName("shipping_name")
        private String shipping_name;


@SerializedName("shipping_id")
        private String shipping_id;


@SerializedName("koguchi")
        private String koguchi;


@SerializedName("code")
        private String code;

@SerializedName("message")
        private String message;

    public NewKoguchi_orderIDResponse(String shipping_name, String shipping_id, String koguchi, String code, String message) {
        this.shipping_name = shipping_name;
        this.shipping_id = shipping_id;
        this.koguchi = koguchi;
        this.code = code;
        this.message = message;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(String shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getKoguchi() {
        return koguchi;
    }

    public void setKoguchi(String koguchi) {
        this.koguchi = koguchi;
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
}
