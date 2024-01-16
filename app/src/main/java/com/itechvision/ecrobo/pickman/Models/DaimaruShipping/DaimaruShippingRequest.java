package com.itechvision.ecrobo.pickman.Models.DaimaruShipping;

import com.google.gson.annotations.SerializedName;

public class DaimaruShippingRequest {

    @SerializedName("authId")
    private String authId;

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("shop_id")
    private String shop_id;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("category")
    private String category;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getSort_by() {
        return sort_by;
    }

    public void setSort_by(String sort_by) {
        this.sort_by = sort_by;
    }

    @SerializedName("sort_by")
    private String sort_by;

    @SerializedName("airprint_printer")
    private String printer;

    @SerializedName("getPrinterSelectedgetPrinterSelected")
    private String selectedPrinter;

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("num")
    private String quantity;

    @SerializedName("app_version")
    private String app_version;

    @SerializedName("product_stock_history_id")
    private String psh_id;

    @SerializedName("time_taken")
    private String timetaken;

    @SerializedName("auto_complete")
    private String auto_complete;

    @SerializedName("box_flag")
    private String box_flag;

    @SerializedName("shipping_flag")
    private String shipping_flag;

    @SerializedName("update_box")
    private String update_box;

    @SerializedName("ap_printer_db")
    private String ap_printer_db;

    public String getAp_printer_db() {
        return ap_printer_db;
    }

    public void setAp_printer_db(String ap_printer_db) {
        this.ap_printer_db = ap_printer_db;
    }

    @SerializedName("update_status")
    private String update_status;

    @SerializedName("boxsize")
    private String boxsize;


    public String getShipping_flag() {
        return shipping_flag;
    }

    public void setShipping_flag(String shipping_flag) {
        this.shipping_flag = shipping_flag;
    }

    public String getAuto_complete() {
        return auto_complete;
    }

    public void setAuto_complete(String auto_complete) {
        this.auto_complete = auto_complete;
    }

    public String getBox_flag() {
        return box_flag;
    }

    public void setBox_flag(String box_flag) {
        this.box_flag = box_flag;
    }


    public DaimaruShippingRequest(String authId, String admin_id, String shop_id, String order_id, String barcode, String category, String sortby, String product_id, String psh_id, String quantity, String timetaken, String auto_complete, String box_flag, String shipping_flag, String update_box, String boxsize, String update_status, String ap_printer_db, String printer, String selectedPrinter, String app_version) {
        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.order_id = order_id;
        this.barcode = barcode;
        this.category = category;
        this.sort_by = sort_by;
        this.printer = printer;
        this.selectedPrinter = selectedPrinter;
        this.product_id = product_id;
        this.quantity = quantity;
        this.app_version = app_version;
        this.psh_id = psh_id;
        this.timetaken = timetaken;
        this.auto_complete = auto_complete;
        this.box_flag = box_flag;
        this.shipping_flag = shipping_flag;
        this.update_box = update_box;
        this.update_status = update_status;
        this.boxsize = boxsize;
        this.ap_printer_db = ap_printer_db;
    }

    public DaimaruShippingRequest(String authId, String admin_id, String shop_id, String order_id, String barcode, String category, String sortby, String product_id, String psh_id, String quantity, String timetaken, String auto_complete, String box_flag, String shipping_flag, String printer, String selectedPrinter, String app_version) {

        this.authId = authId;
        this.admin_id = admin_id;
        this.shop_id = shop_id;
        this.sort_by = sortby;
        this.product_id = product_id;
        this.printer = printer;
        this.selectedPrinter = selectedPrinter;
        this.order_id = order_id;
        this.quantity = quantity;
        this.barcode = barcode;
        this.category = category;
        this.app_version = app_version;
        this.psh_id = psh_id;
        this.timetaken = timetaken;
        this.auto_complete = auto_complete;
        this.box_flag = box_flag;
        this.shipping_flag = shipping_flag;


    }

    public String getPsh_id() {
        return psh_id;
    }

    public void setPsh_id(String psh_id) {
        this.psh_id = psh_id;
    }

    public String getTimetaken() {
        return timetaken;
    }

    public void setTimetaken(String timetaken) {
        this.timetaken = timetaken;
    }


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

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

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getSelectedPrinter() {
        return selectedPrinter;
    }

    public void setSelectedPrinter(String selectedPrinter) {
        this.selectedPrinter = selectedPrinter;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }
}

