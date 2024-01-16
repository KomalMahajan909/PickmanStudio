package com.itechvision.ecrobo.pickman.Models.NewPrinter;

import com.google.gson.annotations.SerializedName;

public class CheckPrinterResponse {

    @SerializedName("code")
    private String code ;


    @SerializedName("message")
    private String message ;


    @SerializedName("printer_id")
    private String printer_id ;

    @SerializedName("machine_id")
    private String machine_id ;



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

    public String getPrinter_id() {
        return printer_id;
    }

    public void setPrinter_id(String printer_id) {
        this.printer_id = printer_id;
    }

    public String getPrinter_name() {
        return printer_name;
    }

    public void setPrinter_name(String printer_name) {
        this.printer_name = printer_name;
    }

    public String getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(String machine_id) {
        this.machine_id = machine_id;
    }

    public CheckPrinterResponse(String code, String message, String printer_id, String printer_name, String machine_id) {
        this.code = code;
        this.message = message;
        this.printer_id = printer_id;
        this.printer_name = printer_name;
        this.machine_id = machine_id;
    }

    @SerializedName("printer_name")
    private String printer_name ;


}
