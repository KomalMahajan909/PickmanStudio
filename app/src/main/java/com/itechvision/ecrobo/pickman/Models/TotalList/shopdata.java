package com.itechvision.ecrobo.pickman.Models.TotalList;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 09-01-2020.
 */

public class shopdata {


    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public shopdata(String shop_id, String shop_name) {

        this.shop_id = shop_id;
        this.shop_name = shop_name;
    }

    @SerializedName("shop_id")
    private String shop_id ;

 @SerializedName("shop_name")
    private String shop_name ;


}
