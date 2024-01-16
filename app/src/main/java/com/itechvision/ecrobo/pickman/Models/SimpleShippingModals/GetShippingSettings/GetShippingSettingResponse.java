package com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.GetShippingSettings;

import com.google.gson.annotations.SerializedName;

public class GetShippingSettingResponse {

    @SerializedName("code")
    private  String code;

    @SerializedName("message")
    private  String message;

    @SerializedName("order_days_check")
    private String order_days_check;

    @SerializedName("product_category")
    private String product_category;


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

    public String getOrder_days_check() {
        return order_days_check;
    }

    public void setOrder_days_check(String order_days_check) {
        this.order_days_check = order_days_check;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }
}
