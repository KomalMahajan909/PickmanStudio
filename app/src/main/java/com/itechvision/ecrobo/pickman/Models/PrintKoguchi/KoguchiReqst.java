package com.itechvision.ecrobo.pickman.Models.PrintKoguchi;

import com.google.gson.annotations.SerializedName;

public class KoguchiReqst {

    @SerializedName("authId")
    String authId;

    @SerializedName("shop_id")
    String shop_id;

    @SerializedName("admin_id")
    String admin_id;

    @SerializedName("order_id")
    String order_id;

    @SerializedName("koguchi")
    String koguchi;

    @SerializedName("csv_flag")
    String csvflag;

    @SerializedName("edi2_id")
    String edi2_id;

    public KoguchiReqst(String authId, String shop_id, String admin_id, String order_id, String koguchi, String csvflag, String edi2_id) {
        this.authId = authId;
        this.shop_id = shop_id;
        this.admin_id = admin_id;
        this.order_id = order_id;
        this.koguchi = koguchi;
        this.csvflag = csvflag;
        this.edi2_id = edi2_id;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
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

    public String getCsvflag() {
        return csvflag;
    }

    public void setCsvflag(String csvflag) {
        this.csvflag = csvflag;
    }
}
