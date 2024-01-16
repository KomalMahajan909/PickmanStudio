package com.itechvision.ecrobo.pickman.Models.TotalArival;

import com.google.gson.annotations.SerializedName;

public class ReprintRequest {
    @SerializedName("authId")
    private  String authId ;

    @SerializedName("admin_id")
    private  String admin_id ;

    @SerializedName("shop_id")
    private  String shop_id ;

    @SerializedName("nyuka_id")
    private  String nyuka_id ;

    @SerializedName("mode")
    private  String mode ;

    @SerializedName("product_id")
    private  String product_id ;

    @SerializedName("app_version")
    private  String version ;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }



    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getNyuka_id() {
        return nyuka_id;
    }

    public void setNyuka_id(String nyuka_id) {
        this.nyuka_id = nyuka_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String order_no) {
        this.product_id = order_no;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }


    public ReprintRequest(String authId, String admin_id, String shop_id, String mode,String nyuka_id, String product_id ,String version) {

        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.nyuka_id = nyuka_id;
        this.product_id = product_id;
        this.mode = mode;
        this.version = version;
    }

}
