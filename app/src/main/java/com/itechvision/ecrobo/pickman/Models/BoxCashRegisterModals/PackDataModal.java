package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals;

public class PackDataModal {

    private String order_sub_id;

    private String code;
    private String barcode;
    private String product_stock_history_id;
    private String quantity;

    private String location;
    private String product_id;
    private String scanned_qty;
    private String BoxNo;
    private String BoxCode;

    public PackDataModal(String order_sub_id, String code, String barcode, String product_stock_history_id, String quantity, String location, String product_id, String scanned_qty, String boxNo, String boxCode) {
        this.order_sub_id = order_sub_id;
        this.code = code;
        this.barcode = barcode;
        this.product_stock_history_id = product_stock_history_id;
        this.quantity = quantity;
        this.location = location;
        this.product_id = product_id;
        this.scanned_qty = scanned_qty;
        BoxNo = boxNo;
        BoxCode = boxCode;
    }

    public String getOrder_sub_id() {
        return order_sub_id;
    }

    public void setOrder_sub_id(String order_sub_id) {
        this.order_sub_id = order_sub_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProduct_stock_history_id() {
        return product_stock_history_id;
    }

    public void setProduct_stock_history_id(String product_stock_history_id) {
        this.product_stock_history_id = product_stock_history_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getScanned_qty() {
        return scanned_qty;
    }

    public void setScanned_qty(String scanned_qty) {
        this.scanned_qty = scanned_qty;
    }

    public String getBoxNo() {
        return BoxNo;
    }

    public void setBoxNo(String boxNo) {
        BoxNo = boxNo;
    }

    public String getBoxCode() {
        return BoxCode;
    }

    public void setBoxCode(String boxCode) {
        BoxCode = boxCode;
    }
}

