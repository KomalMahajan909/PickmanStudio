package com.itechvision.ecrobo.pickman.Models.TruckPicking;

import com.google.gson.annotations.SerializedName;

public class GetShippingCompanyRequest {

    @SerializedName("admin_id")
    private String admin_id ;

    @SerializedName("shipping_schedule")
    private String shipping_schedule ;

    @SerializedName("mode")
    private String mode ;

    @SerializedName("zone_no")
    private String zone_no ;

    @SerializedName("order_group")
    private String order_group ;

    @SerializedName("items_group")
    private String items_group ;

    @SerializedName("authId")
    private String authId ;

    @SerializedName("shop_id")
    private String shop_id ;

    @SerializedName("app_version")
    private String app_version ;

    public GetShippingCompanyRequest(String admin_id, String shipping_schedule, String mode, String zone_no, String order_group, String items_group, String authId, String shop_id, String app_version) {
        this.admin_id = admin_id;
        this.shipping_schedule = shipping_schedule;
        this.mode = mode;
        this.zone_no = zone_no;
        this.order_group = order_group;
        this.items_group = items_group;
        this.authId = authId;
        this.shop_id = shop_id;
        this.app_version = app_version;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getShipping_schedule() {
        return shipping_schedule;
    }

    public void setShipping_schedule(String shipping_schedule) {
        this.shipping_schedule = shipping_schedule;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getZone_no() {
        return zone_no;
    }

    public void setZone_no(String zone_no) {
        this.zone_no = zone_no;
    }

    public String getOrder_group() {
        return order_group;
    }

    public void setOrder_group(String order_group) {
        this.order_group = order_group;
    }

    public String getItems_group() {
        return items_group;
    }

    public void setItems_group(String items_group) {
        this.items_group = items_group;
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

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }
}
