package com.itechvision.ecrobo.pickman.Models.Invoice_Printer;

import com.google.gson.annotations.SerializedName;

public class Invoice_orderIDResponse {

    @SerializedName("shipping_name")
    private String shipping_name;


    @SerializedName("shipping_id")
    private String shipping_id;


    @SerializedName("koguchi")
    private String koguchi;

    @SerializedName("cod_flag")
    private String cod_flag;

    @SerializedName("print_flag")
    private String print_flag;

    @SerializedName("csv_print_flag")
    private String csv_print_flag;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCsv_print_flag() {
        return csv_print_flag;
    }

    public void setCsv_print_flag(String csv_print_flag) {
        this.csv_print_flag = csv_print_flag;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public String getCod_flag() {
        return cod_flag;
    }

    public void setCod_flag(String cod_flag) {
        this.cod_flag = cod_flag;
    }

    public String getPrint_flag() {
        return print_flag;
    }

    public void setPrint_flag(String print_flag) {
        this.print_flag = print_flag;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(String shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getKoguchi() {
        return koguchi;
    }

    public void setKoguchi(String koguchi) {
        this.koguchi = koguchi;
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
}
