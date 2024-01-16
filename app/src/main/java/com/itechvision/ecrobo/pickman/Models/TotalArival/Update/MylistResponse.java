package com.itechvision.ecrobo.pickman.Models.TotalArival.Update;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.TotalArival.nyuka_data;

import java.util.ArrayList;

/**
 * Created by admin on 14-01-2020.
 */

public class MylistResponse {


    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;



    @SerializedName("nyuka_user_count")
    private String nyuka_user_count;

    @SerializedName("nyuka_other_count")
    private String nyuka_other_count;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNyuka_user_count() {
        return nyuka_user_count;
    }

    public void setNyuka_user_count(String nyuka_user_count) {
        this.nyuka_user_count = nyuka_user_count;
    }

    public String getNyuka_other_count() {
        return nyuka_other_count;
    }

    public void setNyuka_other_count(String nyuka_other_count) {
        this.nyuka_other_count = nyuka_other_count;
    }

    public ArrayList<MylistData> getNyuka_list() {
        return nyuka_list;
    }

    public void setNyuka_list(ArrayList<MylistData> nyuka_list) {
        this.nyuka_list = nyuka_list;
    }

    public MylistResponse(String code, String message, String nyuka_user_count, String nyuka_other_count, ArrayList<MylistData> nyuka_list) {

        this.code = code;
        this.message = message;
        this.nyuka_user_count = nyuka_user_count;
        this.nyuka_other_count = nyuka_other_count;
        this.nyuka_list = nyuka_list;
    }

    @SerializedName("nyuka_list")
    private ArrayList<MylistData> nyuka_list;

}
