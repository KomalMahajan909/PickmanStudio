package com.itechvision.ecrobo.pickman.Models.TotalArival;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by admin on 10-01-2020.
 */

public class TotalArivalResponse {

    public TotalArivalResponse(String code, String message, String nyuka_user_count, String nyuka_other_count, String nyuka_complete_count, ArrayList<nyuka_data> nyuka_list, ArrayList<package_data> packages) {
        this.code = code;
        this.message = message;
        this.nyuka_user_count = nyuka_user_count;
        this.nyuka_other_count = nyuka_other_count;
        this.nyuka_complete_count = nyuka_complete_count;
        this.nyuka_list = nyuka_list;
        this.packages = packages;
    }

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

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

    public ArrayList<nyuka_data> getNyuka_list() {
        return nyuka_list;
    }

    public void setNyuka_list(ArrayList<nyuka_data> nyuka_list) {
        this.nyuka_list = nyuka_list;
    }

    public ArrayList<package_data> getPackages() {
        return packages;
    }

    public void setPackages(ArrayList<package_data> packages) {
        this.packages = packages;
    }

    public String getNyuka_complete_count() {
        return nyuka_complete_count;
    }

    public void setNyuka_complete_count(String nyuka_complete_count) {
        this.nyuka_complete_count = nyuka_complete_count;
    }

    @SerializedName("nyuka_user_count")
    private String nyuka_user_count;

    @SerializedName("nyuka_other_count")
    private String nyuka_other_count;

    @SerializedName("nyuka_complete_count")
    private String nyuka_complete_count;

    @SerializedName("nyuka_list")
    private ArrayList<nyuka_data> nyuka_list;

    @SerializedName("packages")
    private ArrayList<package_data> packages;

 /*    @SerializedName("nyuka_other")
    private ArrayList<nyuka_data> nyuka_other;

     @SerializedName("nyuka_user")
    private ArrayList<nyuka_data> nyuka_user;*/


}


