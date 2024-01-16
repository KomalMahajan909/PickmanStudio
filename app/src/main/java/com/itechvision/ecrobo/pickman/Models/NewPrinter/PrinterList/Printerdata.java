package com.itechvision.ecrobo.pickman.Models.NewPrinter.PrinterList;

import com.google.gson.annotations.SerializedName;

public class Printerdata {

    @SerializedName("printer_id")
    private String printer_id;


    public Printerdata(String printer_id, String printer_name) {
        this.printer_id = printer_id;
        this.printer_name = printer_name;
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

    @SerializedName("printer_name")
    private String printer_name;



}
