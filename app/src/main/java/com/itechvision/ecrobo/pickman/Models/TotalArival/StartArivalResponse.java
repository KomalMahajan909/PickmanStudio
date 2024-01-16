package com.itechvision.ecrobo.pickman.Models.TotalArival;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by admin on 10-01-2020.
 */

public class StartArivalResponse {


    @SerializedName("code")
    private String code;

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

    public ArrayList<nyuka_startdata> getNyuka_start() {
        return nyuka_start;
    }

    public void setNyuka_start(ArrayList<nyuka_startdata> nyuka_start) {
        this.nyuka_start = nyuka_start;
    }

    public String getIncrease() {
        return increase;
    }

    public void setIncrease(String increase) {
        this.increase = increase;
    }

    public StartArivalResponse(String code, String message, ArrayList<nyuka_startdata> nyuka_start, String increase) {

        this.code = code;
        this.message = message;
        this.nyuka_start = nyuka_start;
        this.increase = increase;
    }

    @SerializedName("message")
    private String message;



    @SerializedName("nyuka_start")
    private ArrayList<nyuka_startdata> nyuka_start;


    @SerializedName("increase")
    private String increase ;


  /*   @SerializedName("nyuka_other")
    private ArrayList<nyuka_data> nyuka_other;

     @SerializedName("nyuka_user")
    private ArrayList<nyuka_data> nyuka_user;*/


}


