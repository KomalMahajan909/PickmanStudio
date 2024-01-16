package com.itechvision.ecrobo.pickman.Models.Loop_Shipping;

import com.google.gson.annotations.SerializedName;

public class Loop_Ship_Data {


    @SerializedName("order_id")
    private String order_id;

    @SerializedName("order_no")
    private String order_no;

    @SerializedName("name2")
    private String name2;

    @SerializedName("mediacode")
    private String mediacode;


    public Loop_Ship_Data(String order_id, String order_no, String name2, String mediacode) {
        this.order_id = order_id;
        this.order_no = order_no;
        this.name2 = name2;
        this.mediacode = mediacode;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getMediacode() {
        return mediacode;
    }

    public void setMediacode(String mediacode) {
        this.mediacode = mediacode;
    }
}
