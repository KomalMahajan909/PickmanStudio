package com.itechvision.ecrobo.pickman.Models.NewKoguchi.SubmitNewKoguchi;

import com.google.gson.annotations.SerializedName;

public class SubmitnewKoguchiReq {

    @SerializedName("admin_id")
    private  String admin_id;

    @SerializedName("authId")
    private  String authId;

    @SerializedName("shop_id")
    private  String shop_id;

     @SerializedName("order_id")
    private String order_id ;

     @SerializedName("ship_up")
    private String ship_up;

     @SerializedName("koguchi_up")
    private String koguchi_up;

     @SerializedName("app_version")
    private String version;


    public SubmitnewKoguchiReq(String admin_id, String authId, String shop_id, String order_id, String ship_up, String koguchi_up, String version) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.order_id = order_id;
        this.ship_up = ship_up;
        this.koguchi_up = koguchi_up;
        this.version = version;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getShip_up() {
        return ship_up;
    }

    public void setShip_up(String ship_up) {
        this.ship_up = ship_up;
    }

    public String getKoguchi_up() {
        return koguchi_up;
    }

    public void setKoguchi_up(String koguchi_up) {
        this.koguchi_up = koguchi_up;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
