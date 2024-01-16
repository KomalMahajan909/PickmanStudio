package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxList;

import com.google.gson.annotations.SerializedName;

public class KoguchiReq {

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("koguchi")
    private String koguchi;

    public KoguchiReq(String order_id, String koguchi) {
        this.order_id = order_id;
        this.koguchi = koguchi;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getKoguchi() {
        return koguchi;
    }

    public void setKoguchi(String koguchi) {
        this.koguchi = koguchi;
    }
}
