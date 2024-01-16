package com.itechvision.ecrobo.pickman.Models.Settings;

import com.google.gson.annotations.SerializedName;

public class SettingRequest {

    @SerializedName("admin_id")
    private String admin_id;

    @SerializedName("authId")
    private String authId;


    @SerializedName("shop_id")
    private String shop_id;


    @SerializedName("setLotPress")
    private String setLotPress;

    @SerializedName("setRemarkPress")
    private String setRemarkPress;

    @SerializedName("setSupplierList")
    private String setSupplierList;


    @SerializedName("setNoSupplierList")
    private String setNoSupplierList;


    @SerializedName("setPackingList")
    private String setPackingList;


    @SerializedName("setPrinterSelected")
    private String setPrinterSelected;


    @SerializedName("setShippingflag")
    private String setShippingflag;


    @SerializedName("setBoxNo")
    private String setBoxNo;

    @SerializedName("tshipping_change")
    private String tshipping_change;


    @SerializedName("setBoxSelected")
    private String setBoxSelected;

    @SerializedName("setTrackCheck")
    private String setTrackCheck;

    @SerializedName("setaddKeyboard")
    private String setaddKeyboard;

    @SerializedName("setsinclude")
    private String setsinclude;



    @SerializedName("setincludeScreen")
    private String setincludeScreen;

    @SerializedName("setArrivalNyuka")
    private String arrivalNyuka;



    //menu setting



    @SerializedName("setbatchScreen")
    private String setbatchScreen;



    @SerializedName("sdBatchCheck")
    private String sdBatchCheck;



    @SerializedName("dBatchCheck")
    private String dBatchCheck;



    @SerializedName("temporaryLocationCheck")
    private String temporaryLocationCheck;

    @SerializedName("truckBatchCheck")
    private String truckBatchCheck;

    @SerializedName("shippingCheck")
    private String shippingCheck;

    @SerializedName("koguchiCheck")
    private String koguchiCheck;

    @SerializedName("boxSizeCheck")
    private String boxSizeCheck;

    @SerializedName("printerCheck")
    private String printerCheck;

    @SerializedName("StockChangeCheck")
    private String StockChangeCheck;

    @SerializedName("packSetCheck")
    private String packSetCheck;

    @SerializedName("newPackSetCheck")
    private String newPackSetCheck;


    @SerializedName("rfNewArrivalCheck")
    private String rfNewArrivalCheck;


    @SerializedName("rfNewReturnCheck")
    private String rfNewReturnCheck;


    @SerializedName("slipPrinterCheck")
    private String slipPrinterCheck;


    @SerializedName("rfPickingCheck")
    private String rfPickingCheck;


    @SerializedName("machinePrinterCheck")
    private String machinePrinterCheck;


    @SerializedName("barcodeSlipPrinter")
    private String barcodeSlipPrinter;

    @SerializedName("product_scan_flag")
    private String productscanflag;

    @SerializedName("rfWriter")
    private String rfWriter;

    @SerializedName("ecms")
    private String ecms;

    @SerializedName("pdBoxSize")
    private String pdBoxSize;




    @SerializedName("picklist")
    private String picklist;


    @SerializedName("totalarrival")
    private String totalarrival;

    @SerializedName("newbatchpick")
    private String newbatchpick;

    @SerializedName("tshipping")
    private String tshipping;

    @SerializedName("newTasPick")
    private String newTasPick;



    @SerializedName("newreturnStock")
    private String newreturnStock;


    @SerializedName("newPrinterSelect")
    private String newPrinterSelect;


    @SerializedName("tas_flag")
    private String tas_flag;

    @SerializedName("after_pay_check")
    private String after_pay_check;

    @SerializedName("use_barcode_match")
    private String use_dolphin_arrival;

    @SerializedName("use_double_pick")
    private String use_double_pick;

    @SerializedName("ng_tune")
    private String ng_tune;

    @SerializedName("ok_tune")
    private String ok_tune;

    @SerializedName("return_stock_barcode")
    private String return_stock_barcode;

    @SerializedName("print_csv")
    private String print_csv;

    @SerializedName("use_mitsukoshi")
    private String use_mitsukoshi;


    @SerializedName("invoice_printing")
    private String invoice_printing;

    @SerializedName("multipick_dm")
    private String multipick_dm;

    @SerializedName("menu_simple_shipping")
    private String menu_simple_shipping;

    @SerializedName("use_customer_arrival")
    private String customer_arrival;

    @SerializedName("new_move_stock_screen")
    private String new_move_stock_screen;

    @SerializedName("multiuser_shipping")
    private String multiuser_shipping;

    @SerializedName("media_shipping")
    private String media_shipping;

    @SerializedName("pickman_batch")
    private String pickman_batch;

    @SerializedName("new_invoice_print")
    private String new_invoice_print;

    @SerializedName("check_box_size")
    private String check_box_size;

    @SerializedName("use_reship")
    private String use_reship;

    @SerializedName("tracking_date_check")
    private String tracking_date_check;

    @SerializedName("iris_arrival_screen")
    private String iris_arrival_screen;

    @SerializedName("iris_picking")
    private String iris_picking;



    public SettingRequest(String admin_id, String authId, String shop_id, String tshipping_change,String setLotPress, String setRemarkPress, String setSupplierList, String setNoSupplierList, String setPackingList, String setPrinterSelected, String setShippingflag, String setBoxNo, String setBoxSelected, String setTrackCheck, String setaddKeyboard, String setsinclude, String setincludeScreen, String arrivalNyuka, String setbatchScreen, String sdBatchCheck, String dBatchCheck, String temporaryLocationCheck, String truckBatchCheck, String shippingCheck, String koguchiCheck, String boxSizeCheck, String printerCheck, String stockChangeCheck, String packSetCheck, String newPackSetCheck, String rfNewArrivalCheck,
                          String rfNewReturnCheck, String slipPrinterCheck, String rfPickingCheck, String machinePrinterCheck, String barcodeSlipPrinter, String rfWriter, String ecms, String pdBoxSize, String picklist, String totalarrival, String newbatchpick, String newTasPick, String newreturnStock, String newPrinterSelect, String tas_flag, String after_pay_check, String use_dolphin_arrival, String use_double_pick, String ng_tune, String ok_tune, String productscanflag, String return_stock_barcode, String print_csv, String use_mitsukoshi, String invoice_printing, String multipick_dm, String menu_simple_shipping, String tshipping, String customer_arrival, String new_move_stock_screen, String multiuser_shipping,
                          String media_shipping, String pickman_batch, String new_invoice_print, String check_box_size, String tracking_date_check, String iris_arrival_screen, String iris_picking, String use_reship) {
        this.admin_id = admin_id;
        this.authId = authId;
        this.shop_id = shop_id;
        this.tshipping_change = tshipping_change;
        this.setLotPress = setLotPress;
        this.setRemarkPress = setRemarkPress;
        this.setSupplierList = setSupplierList;
        this.setNoSupplierList = setNoSupplierList;
        this.setPackingList = setPackingList;
        this.setPrinterSelected = setPrinterSelected;
        this.setShippingflag = setShippingflag;
        this.setBoxNo = setBoxNo;
        this.setBoxSelected = setBoxSelected;
        this.setTrackCheck = setTrackCheck;
        this.setaddKeyboard = setaddKeyboard;
        this.setsinclude = setsinclude;
        this.setincludeScreen = setincludeScreen;
        this.arrivalNyuka = arrivalNyuka;
        this.setbatchScreen = setbatchScreen;
        this.sdBatchCheck = sdBatchCheck;
        this.dBatchCheck = dBatchCheck;
        this.temporaryLocationCheck = temporaryLocationCheck;
        this.truckBatchCheck = truckBatchCheck;
        this.shippingCheck = shippingCheck;
        this.koguchiCheck = koguchiCheck;
        this.boxSizeCheck = boxSizeCheck;
        this.printerCheck = printerCheck;
        StockChangeCheck = stockChangeCheck;
        this.packSetCheck = packSetCheck;
        this.newPackSetCheck = newPackSetCheck;
        this.rfNewArrivalCheck = rfNewArrivalCheck;
        this.rfNewReturnCheck = rfNewReturnCheck;
        this.slipPrinterCheck = slipPrinterCheck;
        this.rfPickingCheck = rfPickingCheck;
        this.machinePrinterCheck = machinePrinterCheck;
        this.barcodeSlipPrinter = barcodeSlipPrinter;
        this.rfWriter = rfWriter;
        this.ecms = ecms;
        this.pdBoxSize = pdBoxSize;
        this.picklist = picklist;
        this.totalarrival = totalarrival;
        this.newbatchpick = newbatchpick;
        this.newTasPick = newTasPick;
        this.newreturnStock = newreturnStock;
        this.newPrinterSelect = newPrinterSelect;
        this.tas_flag = tas_flag;
        this.after_pay_check = after_pay_check;
        this.use_dolphin_arrival = use_dolphin_arrival;
        this.use_double_pick = use_double_pick;
        this.ng_tune = ng_tune;
        this.ok_tune = ok_tune;
        this.productscanflag = productscanflag;
        this.return_stock_barcode =return_stock_barcode;
        this.print_csv = print_csv;
        this.use_mitsukoshi = use_mitsukoshi;
        this.invoice_printing = invoice_printing;
        this.multipick_dm = multipick_dm;
        this.menu_simple_shipping = menu_simple_shipping;
        this.tshipping = tshipping;
        this.customer_arrival = customer_arrival;
        this.multiuser_shipping = multiuser_shipping;
        this.new_move_stock_screen = new_move_stock_screen;
        this.media_shipping = media_shipping;
        this.pickman_batch = pickman_batch;
        this.new_invoice_print = new_invoice_print;
        this.check_box_size = check_box_size;
        this.tracking_date_check = tracking_date_check;
        this.iris_arrival_screen = iris_arrival_screen;
        this.iris_picking= iris_picking;

        this.use_reship = use_reship;
    }




    public String getMedia_shipping() {
        return media_shipping;
    }

    public void setMedia_shipping(String media_shipping) {
        this.media_shipping = media_shipping;
    }

    public String getInvoice_printing() {
        return invoice_printing;
    }

    public void setInvoice_printing(String invoice_printing) {
        this.invoice_printing = invoice_printing;
    }

    public String getNew_move_stock_screen() {
        return new_move_stock_screen;
    }

    public void setNew_move_stock_screen(String new_move_stock_screen) {
        this.new_move_stock_screen = new_move_stock_screen;
    }

    public String getPickman_batch() {
        return pickman_batch;
    }

    public void setPickman_batch(String pickman_batch) {
        this.pickman_batch = pickman_batch;
    }

    public String getMultiuser_shipping() {
        return multiuser_shipping;
    }

    public void setMultiuser_shipping(String multiuser_shipping) {
        this.multiuser_shipping = multiuser_shipping;
    }

    public String getCustomer_arrival() {
        return customer_arrival;
    }

    public void setCustomer_arrival(String customer_arrival) {
        this.customer_arrival = customer_arrival;
    }

    public String getTshipping_change() {
        return tshipping_change;
    }

    public void setTshipping_change(String tshipping_change) {
        this.tshipping_change = tshipping_change;
    }

    public String getTshipping() {
        return tshipping;
    }

    public void setTshipping(String tshipping) {
        this.tshipping = tshipping;
    }

    public String getMenu_simple_shipping() {
        return menu_simple_shipping;
    }

    public void setMenu_simple_shipping(String menu_simple_shipping) {
        this.menu_simple_shipping = menu_simple_shipping;
    }

    public String getUse_mitsukoshi() {
        return use_mitsukoshi;
    }

    public void setUse_mitsukoshi(String use_mitsukoshi) {
        this.use_mitsukoshi = use_mitsukoshi;
    }

    public String getPrint_csv() {
        return print_csv;
    }

    public void setPrint_csv(String print_csv) {
        this.print_csv = print_csv;
    }

    public String getReturn_stock_barcode() {
        return return_stock_barcode;
    }

    public void setReturn_stock_barcode(String return_stock_barcode) {
        this.return_stock_barcode = return_stock_barcode;
    }

    public String getProductscanflag() {
        return productscanflag;
    }

    public String getMultipick_dm() {
        return multipick_dm;
    }

    public void setMultipick_dm(String multipick_dm) {
        this.multipick_dm = multipick_dm;
    }

    public void setProductscanflag(String productscanflag) {
        this.productscanflag = productscanflag;
    }

    public String getng_tune() {
        return ng_tune;
    }

    public void setng_tune(String ng_tune) {
        this.ng_tune = ng_tune;
    }

    public String getok_tune() {
        return ok_tune;
    }

    public void setok_tune(String ok_tune) {
        this.ok_tune = ok_tune;
    }

    public String getUse_double_pick() {
        return use_double_pick;
    }

    public void setUse_double_pick(String use_double_pick) {
        this.use_double_pick = use_double_pick;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getSetLotPress() {
        return setLotPress;
    }

    public void setSetLotPress(String setLotPress) {
        this.setLotPress = setLotPress;
    }

    public String getSetRemarkPress() {
        return setRemarkPress;
    }

    public void setSetRemarkPress(String setRemarkPress) {
        this.setRemarkPress = setRemarkPress;
    }

    public String getSetSupplierList() {
        return setSupplierList;
    }

    public void setSetSupplierList(String setSupplierList) {
        this.setSupplierList = setSupplierList;
    }

    public String getSetNoSupplierList() {
        return setNoSupplierList;
    }

    public void setSetNoSupplierList(String setNoSupplierList) {
        this.setNoSupplierList = setNoSupplierList;
    }

    public String getSetPackingList() {
        return setPackingList;
    }

    public void setSetPackingList(String setPackingList) {
        this.setPackingList = setPackingList;
    }

    public String getSetPrinterSelected() {
        return setPrinterSelected;
    }

    public void setSetPrinterSelected(String setPrinterSelected) {
        this.setPrinterSelected = setPrinterSelected;
    }

    public String getSetShippingflag() {
        return setShippingflag;
    }

    public void setSetShippingflag(String setShippingflag) {
        this.setShippingflag = setShippingflag;
    }

    public String getSetBoxNo() {
        return setBoxNo;
    }

    public void setSetBoxNo(String setBoxNo) {
        this.setBoxNo = setBoxNo;
    }

    public String getSetBoxSelected() {
        return setBoxSelected;
    }

    public void setSetBoxSelected(String setBoxSelected) {
        this.setBoxSelected = setBoxSelected;
    }

    public String getSetTrackCheck() {
        return setTrackCheck;
    }

    public void setSetTrackCheck(String setTrackCheck) {
        this.setTrackCheck = setTrackCheck;
    }

    public String getSetaddKeyboard() {
        return setaddKeyboard;
    }

    public void setSetaddKeyboard(String setaddKeyboard) {
        this.setaddKeyboard = setaddKeyboard;
    }

    public String getSetsinclude() {
        return setsinclude;
    }

    public void setSetsinclude(String setsinclude) {
        this.setsinclude = setsinclude;
    }

    public String getSetincludeScreen() {
        return setincludeScreen;
    }

    public void setSetincludeScreen(String setincludeScreen) {
        this.setincludeScreen = setincludeScreen;
    }

    public String getArrivalNyuka() {
        return arrivalNyuka;
    }

    public void setArrivalNyuka(String arrivalNyuka) {
        this.arrivalNyuka = arrivalNyuka;
    }

    public String getSetbatchScreen() {
        return setbatchScreen;
    }

    public void setSetbatchScreen(String setbatchScreen) {
        this.setbatchScreen = setbatchScreen;
    }

    public String getSdBatchCheck() {
        return sdBatchCheck;
    }

    public void setSdBatchCheck(String sdBatchCheck) {
        this.sdBatchCheck = sdBatchCheck;
    }

    public String getdBatchCheck() {
        return dBatchCheck;
    }

    public void setdBatchCheck(String dBatchCheck) {
        this.dBatchCheck = dBatchCheck;
    }

    public String getTemporaryLocationCheck() {
        return temporaryLocationCheck;
    }

    public void setTemporaryLocationCheck(String temporaryLocationCheck) {
        this.temporaryLocationCheck = temporaryLocationCheck;
    }

    public String getTruckBatchCheck() {
        return truckBatchCheck;
    }

    public void setTruckBatchCheck(String truckBatchCheck) {
        this.truckBatchCheck = truckBatchCheck;
    }

    public String getShippingCheck() {
        return shippingCheck;
    }

    public void setShippingCheck(String shippingCheck) {
        this.shippingCheck = shippingCheck;
    }

    public String getKoguchiCheck() {
        return koguchiCheck;
    }

    public void setKoguchiCheck(String koguchiCheck) {
        this.koguchiCheck = koguchiCheck;
    }

    public String getBoxSizeCheck() {
        return boxSizeCheck;
    }

    public void setBoxSizeCheck(String boxSizeCheck) {
        this.boxSizeCheck = boxSizeCheck;
    }

    public String getPrinterCheck() {
        return printerCheck;
    }

    public void setPrinterCheck(String printerCheck) {
        this.printerCheck = printerCheck;
    }

    public String getStockChangeCheck() {
        return StockChangeCheck;
    }

    public void setStockChangeCheck(String stockChangeCheck) {
        StockChangeCheck = stockChangeCheck;
    }

    public String getPackSetCheck() {
        return packSetCheck;
    }

    public void setPackSetCheck(String packSetCheck) {
        this.packSetCheck = packSetCheck;
    }

    public String getNewPackSetCheck() {
        return newPackSetCheck;
    }

    public void setNewPackSetCheck(String newPackSetCheck) {
        this.newPackSetCheck = newPackSetCheck;
    }

    public String getRfNewArrivalCheck() {
        return rfNewArrivalCheck;
    }

    public void setRfNewArrivalCheck(String rfNewArrivalCheck) {
        this.rfNewArrivalCheck = rfNewArrivalCheck;
    }

    public String getRfNewReturnCheck() {
        return rfNewReturnCheck;
    }

    public void setRfNewReturnCheck(String rfNewReturnCheck) {
        this.rfNewReturnCheck = rfNewReturnCheck;
    }

    public String getSlipPrinterCheck() {
        return slipPrinterCheck;
    }

    public void setSlipPrinterCheck(String slipPrinterCheck) {
        this.slipPrinterCheck = slipPrinterCheck;
    }

    public String getRfPickingCheck() {
        return rfPickingCheck;
    }

    public void setRfPickingCheck(String rfPickingCheck) {
        this.rfPickingCheck = rfPickingCheck;
    }

    public String getMachinePrinterCheck() {
        return machinePrinterCheck;
    }

    public void setMachinePrinterCheck(String machinePrinterCheck) {
        this.machinePrinterCheck = machinePrinterCheck;
    }

    public String getBarcodeSlipPrinter() {
        return barcodeSlipPrinter;
    }

    public void setBarcodeSlipPrinter(String barcodeSlipPrinter) {
        this.barcodeSlipPrinter = barcodeSlipPrinter;
    }

    public String getRfWriter() {
        return rfWriter;
    }

    public void setRfWriter(String rfWriter) {
        this.rfWriter = rfWriter;
    }

    public String getEcms() {
        return ecms;
    }

    public void setEcms(String ecms) {
        this.ecms = ecms;
    }

    public String getPdBoxSize() {
        return pdBoxSize;
    }

    public void setPdBoxSize(String pdBoxSize) {
        this.pdBoxSize = pdBoxSize;
    }

    public String getPicklist() {
        return picklist;
    }

    public void setPicklist(String picklist) {
        this.picklist = picklist;
    }

    public String getTotalarrival() {
        return totalarrival;
    }

    public void setTotalarrival(String totalarrival) {
        this.totalarrival = totalarrival;
    }

    public String getNewbatchpick() {
        return newbatchpick;
    }

    public void setNewbatchpick(String newbatchpick) {
        this.newbatchpick = newbatchpick;
    }

    public String getNewTasPick() {
        return newTasPick;
    }

    public void setNewTasPick(String newTasPick) {
        this.newTasPick = newTasPick;
    }

    public String getNewreturnStock() {
        return newreturnStock;
    }

    public void setNewreturnStock(String newreturnStock) {
        this.newreturnStock = newreturnStock;
    }

    public String getNewPrinterSelect() {
        return newPrinterSelect;
    }

    public void setNewPrinterSelect(String newPrinterSelect) {
        this.newPrinterSelect = newPrinterSelect;
    }

    public String getTas_flag() {
        return tas_flag;
    }

    public void setTas_flag(String tas_flag) {
        this.tas_flag = tas_flag;
    }

    public String getAfter_pay_check() {
        return after_pay_check;
    }

    public void setAfter_pay_check(String after_pay_check) {
        this.after_pay_check = after_pay_check;
    }

    public String getUse_dolphin_arrival() {
        return use_dolphin_arrival;
    }

    public void setUse_dolphin_arrival(String use_dolphin_arrival) {
        this.use_dolphin_arrival = use_dolphin_arrival;
    }
    public String getinvoice_printing() {
        return invoice_printing;
    }

    public void setinvoice_printing(String invoice_printing) {
        this.invoice_printing = invoice_printing;
    }
}









