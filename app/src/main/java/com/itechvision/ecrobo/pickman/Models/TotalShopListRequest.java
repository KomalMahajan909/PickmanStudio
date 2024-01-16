package com.itechvision.ecrobo.pickman.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 09-01-2020.
 */

public class TotalShopListRequest {

    public TotalShopListRequest(String authId, String admin_id, String warehouse_id) {
        this.authId = authId;
        this.admin_id = admin_id;
        this.warehouse_id = warehouse_id;
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

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    @SerializedName("authId")
    private String authId  ;

    @SerializedName("admin_id")
    private String admin_id  ;

    @SerializedName("warehouse_id")
    private String warehouse_id  ;


}
