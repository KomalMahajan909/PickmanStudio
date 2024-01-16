package com.itechvision.ecrobo.pickman.Models.NewShipping;

import com.google.gson.annotations.SerializedName;

public class NotScanProductShippingRequest {

    @SerializedName("authId")
    private  String authId ;

    @SerializedName("admin_id")
    private  String admin_id ;

    @SerializedName("shop_id")
    private  String shop_id ;

    @SerializedName("order_id")
    private  String order_id ;


    @SerializedName("sort_by")
    private  String sort_by ;

    @SerializedName("shipping_flag")
    private String shipping_flag;

    @SerializedName("app_version")
    private  String app_version ;

    public NotScanProductShippingRequest(String authId, String admin_id, String shop_id, String order_id, String sort_by, String shipping_flag, String app_version) {
        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.order_id = order_id;
        this.sort_by = sort_by;
        this.shipping_flag = shipping_flag;
        this.app_version = app_version;
    }

    public String getShipping_flag() {
        return shipping_flag;
    }

    public void setShipping_flag(String shipping_flag) {
        this.shipping_flag = shipping_flag;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getSort_by() {
        return sort_by;
    }

    public void setSort_by(String sort_by) {
        this.sort_by = sort_by;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }
}
