package com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckBarcode;

public class BatchList {

    private String barcode;
    private String quantity;

    public BatchList(String barcode, String quantity) {
        this.barcode = barcode;
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
