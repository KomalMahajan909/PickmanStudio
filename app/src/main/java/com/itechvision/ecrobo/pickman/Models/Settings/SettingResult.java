package com.itechvision.ecrobo.pickman.Models.Settings;

import com.google.gson.annotations.SerializedName;

public class SettingResult {

    @SerializedName("code")
    private String code ;


    @SerializedName("message")
    private  String message ;


    public SettingResult(String code, String message, SettingData result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

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

    public SettingData getResult() {
        return result;
    }

    public void setResult(SettingData result) {
        this.result = result;
    }

    @SerializedName("result")
    private SettingData result;

}
