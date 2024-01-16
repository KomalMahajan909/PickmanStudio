package com.itechvision.ecrobo.pickman.Models.NyukaArrival;

import com.google.gson.annotations.SerializedName;

public class NyukaSelectReq {


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


        @SerializedName("authId")
        private String authId  ;

        @SerializedName("admin_id")
        private String admin_id  ;

        @SerializedName("shop_id")
        private String shop_id  ;

        @SerializedName("nyuka_id")
        private String nyuka_id  ;

    public NyukaSelectReq(String authId, String admin_id, String shop_id, String nyuka_id) {
        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.nyuka_id = nyuka_id;
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
}
