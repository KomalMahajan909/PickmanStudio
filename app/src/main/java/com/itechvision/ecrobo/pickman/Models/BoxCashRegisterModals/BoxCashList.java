package com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals;

import com.google.gson.annotations.SerializedName;

public class BoxCashList {

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("order_no")
    private String order_no;

    @SerializedName("mediacode")
    private String mediacode;

    @SerializedName("wf_status")
    private String wf_status;

    @SerializedName("name")
    private String name;

    @SerializedName("koguchi")
    private String koguchi;

    @SerializedName("pikking_method")
    private String pikking_method;

    @SerializedName("shipping_method")
    private String shipping_method;

    @SerializedName("order_sub_id")
    private String order_sub_id;

    @SerializedName("num")
    private String num;

    @SerializedName("code")
    private String code;

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("product_name")
    private String product_name;

    @SerializedName("spec1")
    private String spec1;

    @SerializedName("spec2")
    private String spec2;

    @SerializedName("category")
    private String category;

    @SerializedName("product_stock_history_id")
    private String product_stock_history_id;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("product_qty")
    private String product_qty;

    @SerializedName("location")
    private String location;

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("stock_type_id")
    private String stock_type_id;

    @SerializedName("lot")
    private String lot;

    @SerializedName("expiration_date")
    private String expiration_date;

    @SerializedName("inspection_flag")
    private String inspection_flag;

    @SerializedName("inspection_num")
    private String inspection_num;

    @SerializedName("is_scanned")
    private String is_scanned;

    @SerializedName("scanned_user")
    private String scanned_user;

    private String scanned_qty;
    private String packed_qty;
    private String BoxNo;
    private String BoxCode;


    public BoxCashList(String order_id, String order_no, String mediacode, String wf_status, String name, String koguchi, String pikking_method, String shipping_method, String order_sub_id, String num, String code, String barcode, String product_name, String spec1, String spec2, String category, String product_stock_history_id, String quantity, String product_qty, String location, String product_id, String stock_type_id, String lot, String expiration_date, String inspection_flag, String inspection_num, String is_scanned, String scanned_user, String scanned_qty, String packed_qty, String boxNo, String boxCode) {
        this.order_id = order_id;
        this.order_no = order_no;
        this.mediacode = mediacode;
        this.wf_status = wf_status;
        this.name = name;
        this.koguchi = koguchi;
        this.pikking_method = pikking_method;
        this.shipping_method = shipping_method;
        this.order_sub_id = order_sub_id;
        this.num = num;
        this.code = code;
        this.barcode = barcode;
        this.product_name = product_name;
        this.spec1 = spec1;
        this.spec2 = spec2;
        this.category = category;
        this.product_stock_history_id = product_stock_history_id;
        this.quantity = quantity;
        this.product_qty = product_qty;
        this.location = location;
        this.product_id = product_id;
        this.stock_type_id = stock_type_id;
        this.lot = lot;
        this.expiration_date = expiration_date;
        this.inspection_flag = inspection_flag;
        this.inspection_num = inspection_num;
        this.is_scanned = is_scanned;
        this.scanned_user = scanned_user;
        this.scanned_qty = scanned_qty;
        this.packed_qty = packed_qty;
        BoxNo = boxNo;
        BoxCode = boxCode;
    }

    public String getPacked_qty() {
        return packed_qty;
    }

    public void setPacked_qty(String packed_qty) {
        this.packed_qty = packed_qty;
    }

    public String getBoxCode() {
        return BoxCode;
    }

    public void setBoxCode(String boxCode) {
        BoxCode = boxCode;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getMediacode() {
        return mediacode;
    }

    public void setMediacode(String mediacode) {
        this.mediacode = mediacode;
    }

    public String getWf_status() {
        return wf_status;
    }

    public void setWf_status(String wf_status) {
        this.wf_status = wf_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKoguchi() {
        return koguchi;
    }

    public void setKoguchi(String koguchi) {
        this.koguchi = koguchi;
    }

    public String getPikking_method() {
        return pikking_method;
    }

    public void setPikking_method(String pikking_method) {
        this.pikking_method = pikking_method;
    }

    public String getShipping_method() {
        return shipping_method;
    }

    public void setShipping_method(String shipping_method) {
        this.shipping_method = shipping_method;
    }

    public String getOrder_sub_id() {
        return order_sub_id;
    }

    public void setOrder_sub_id(String order_sub_id) {
        this.order_sub_id = order_sub_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSpec1() {
        return spec1;
    }

    public void setSpec1(String spec1) {
        this.spec1 = spec1;
    }

    public String getSpec2() {
        return spec2;
    }

    public void setSpec2(String spec2) {
        this.spec2 = spec2;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
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

    public String getStock_type_id() {
        return stock_type_id;
    }

    public void setStock_type_id(String stock_type_id) {
        this.stock_type_id = stock_type_id;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getInspection_flag() {
        return inspection_flag;
    }

    public void setInspection_flag(String inspection_flag) {
        this.inspection_flag = inspection_flag;
    }

    public String getInspection_num() {
        return inspection_num;
    }

    public void setInspection_num(String inspection_num) {
        this.inspection_num = inspection_num;
    }

    public String getIs_scanned() {
        return is_scanned;
    }

    public void setIs_scanned(String is_scanned) {
        this.is_scanned = is_scanned;
    }

    public String getScanned_user() {
        return scanned_user;
    }

    public void setScanned_user(String scanned_user) {
        this.scanned_user = scanned_user;
    }
}
