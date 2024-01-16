package com.itechvision.ecrobo.pickman.Models.Invoice_Printer.ShipCompany;

import com.google.gson.annotations.SerializedName;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.KoguchiShipCompany.NKoguchiShipData;

import java.util.ArrayList;

public class InvoiceShipCompResult {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("shipping_methods")
    private ArrayList<NKoguchiShipData> shipping_methods ;

    public InvoiceShipCompResult(String code, String message, ArrayList<NKoguchiShipData> shipping_methods) {
        this.code = code;
        this.message = message;
        this.shipping_methods = shipping_methods;
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

    public ArrayList<NKoguchiShipData> getShipping_methods() {
        return shipping_methods;
    }

    public void setShipping_methods(ArrayList<NKoguchiShipData> shipping_methods) {
        this.shipping_methods = shipping_methods;
    }
}
