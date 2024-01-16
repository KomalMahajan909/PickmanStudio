package com.itechvision.ecrobo.pickman.Models.NewKoguchiBoxSize;

import com.google.gson.annotations.SerializedName;

public class OrderIdScanKoguchiResponse {

    @SerializedName("code")
    private String code;

    @SerializedName("koguchi")
    private String koguchi;

    @SerializedName("shipping_method")
    private String shipping_method;

    public OrderIdScanKoguchiResponse(String code, String koguchi, String shipping_method) {
        this.code = code;
        this.koguchi = koguchi;
        this.shipping_method = shipping_method;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKoguchi() {
        return koguchi;
    }

    public void setKoguchi(String koguchi) {
        this.koguchi = koguchi;
    }

    public String getShipping_method() {
        return shipping_method;
    }

    public void setShipping_method(String shipping_method) {
        this.shipping_method = shipping_method;
    }
}
