package com.itechvision.ecrobo.pickman.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefrences {
    static SharedPreferences preferences;
    static SharedPreferences.Editor editor;
    static String value = "";
    static Boolean boolValue = false;

    public static void set_AdminId(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("UID", uid);
        editor.commit();

    }

    public static String get_AdminId(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("UID", "");
        return value;
    }

    public static void set_authId(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("role", uid);
        editor.commit();
    }

    public static String get_authId(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("role", "");
        return value;
    }

    public static void set_shopid(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("clientid", uid);
        editor.commit();
    }

    public static String get_shopid(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("clientid", "");
        return value;
    }

    public static void set_trackCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("trackCheck", uid);
        editor.commit();

    }

    public static String get_trackCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("trackCheck", "");
        return value;
    }

    public static void set_trackdigit(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("trackDigit", uid);
        editor.commit();

    }

    public static String get_trackdigit(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("trackDigit", "");
        return value;
    }


    public static void set_newTasPick(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("aaaaa", uid);
        editor.commit();

    }

    public static String get_newTasPick(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("aaaaa", "");
        return value;
    }
    public static void set_newdolArrival(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("newdolArrival", uid);
        editor.commit();

    }

    public static String get_newdolArrival(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("newdolArrival", "");
        return value;
    }

    public static void set_sdBatchCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("sdBatchCheck", uid);
        editor.commit();

    }

    public static String get_sdBatchCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("sdBatchCheck", "");
        return value;
    }

    public static void set_dBatchCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("dBatchCheck", uid);
        editor.commit();

    }

    public static String get_dBatchCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("dBatchCheck", "");
        return value;
    }

    public static void setW6BatchPicking(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("W6BatchPicking", uid);
        editor.commit();

    }

    public static String getW6BatchPicking(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("W6BatchPicking", "");
        return value;
    }

    public static void setDmBatchPicking(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("DmBatchPicking", uid);
        editor.commit();

    }

    public static String getDmBatchPicking(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("DmBatchPicking", "");
        return value;
    }

    public static void setNewInvoicePrint(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("NewInvoicePrint", uid);
        editor.commit();

    }

    public static String getNewInvoicePrint(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("NewInvoicePrint", "");
        return value;
    }
    public static void set_temporaryLocationCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("temporaryLocationCheck", uid);
        editor.commit();

    }

    public static String get_temporaryLocationCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("temporaryLocationCheck", "");
        return value;
    }

    public static void set_tshippingCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("tshippingCheck", uid);
        editor.commit();

    }

    public static String get_tshippingCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("tshippingCheck", "");
        return value;
    }

    public static void set_new_move_stock(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("new_move_stock", uid);
        editor.commit();

    }

    public static String get_new_move_stock(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("new_move_stock", "");
        return value;
    }

    public static void set_invoice_shipping(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("invoice_shipping", uid);
        editor.commit();

    }

    public static String get_invoice_shipping(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("invoice_shipping", "");
        return value;
    }
    public static void set_truckBatchCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("truckBatchCheck", uid);
        editor.commit();

    }


    public static String get_truckBatchCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("truckBatchCheck", "");
        return value;
    }

    public static void setCustomerArrival(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("CustomerArrival", uid);
        editor.commit();

    }

    public static String getCustomerArrival(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("CustomerArrival", "");
        return value;
    }

    public static void set_shippingCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("shippingCheck", uid);
        editor.commit();

    }

    public static String get_shippingCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("shippingCheck", "");
        return value;
    }

    public static void set_koguchiCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("koguchiCheck", uid);
        editor.commit();

    }

    public static String get_koguchiCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("koguchiCheck", "");
        return value;
    }

    public static void set_boxSizeCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("boxSizeCheck", uid);
        editor.commit();

    }

    public static String get_boxSizeCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("boxSizeCheck", "");
        return value;
    }

    public static void set_printerCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("printerCheck", uid);
        editor.commit();

    }

    public static String get_printerCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("printerCheck", "");
        return value;
    }

    public static void set_StockChangeCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("StockChangeCheck", uid);
        editor.commit();

    }

    public static String get_StockChangeCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("StockChangeCheck", "");
        return value;
    }

    public static void set_packSetCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("packSetCheck", uid);
        editor.commit();

    }

    public static String get_packSetCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("packSetCheck", "");
        return value;
    }

    public static void set_newPackSetCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("newPackSetCheck", uid);
        editor.commit();

    }

    public static String get_newPackSetCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("newPackSetCheck", "");
        return value;
    }

    public static void set_rfNewArrivalCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("rfNewArrivalCheck", uid);
        editor.commit();

    }

    public static String get_rfNewArrivalCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("rfNewArrivalCheck", "");
        return value;
    }

    public static void set_rfNewReturnCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("rfNewReturnCheck", uid);
        editor.commit();

    }

    public static String get_rfNewReturnCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("rfNewReturnCheck", "");
        return value;
    }

    public static void set_slipPrinterCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("slipPrinterCheck", uid);
        editor.commit();

    }

    public static String get_slipPrinterCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("slipPrinterCheck", "");
        return value;
    }

    public static void set_rfPickingCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("rfPickingCheck", uid);
        editor.commit();

    }

    public static String get_rfPickingCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("rfPickingCheck", "");
        return value;
    }

    public static void set_machinePrinterCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("machinePrinterCheck", uid);
        editor.commit();

    }

    public static String get_machinePrinterCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("machinePrinterCheck", "");
        return value;
    }

    public static void setdirectStockPos(Context activity, int uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putInt("directStockPos", uid);
        editor.commit();

    }

    public static int getdirectStockPos(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        int value = preferences.getInt("directStockPos", 0);
        return value;
    }

    public static void setorderPos(Context activity, int uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putInt("OrderPos", uid);
        editor.commit();

    }

    public static int getorderPos(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        int value = preferences.getInt("OrderPos", 0);
        return value;
    }

    public static void set_barcodeSlipPrinter(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("barcodeSlipPrinter", uid);
        editor.commit();

    }

    public static String get_barcodeSlipPrinter(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("barcodeSlipPrinter", "");
        return value;
    }

    public static void set_rfWriter(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("rfWriter", uid);
        editor.commit();

    }

    public static String get_rfWriter(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("rfWriter", "");
        return value;
    }

    public static void set_ecms(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("ecms", uid);
        editor.commit();

    }

    public static String get_ecms(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("ecms", "");
        return value;
    }

    public static void set_pdBoxSize(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("pdBoxSize", uid);
        editor.commit();

    }

    public static String get_pdBoxSize(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("pdBoxSize", "");
        return value;
    }

    public static void set_picklist(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("picklist", uid);
        editor.commit();

    }

    public static String get_picklist(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("picklist", "");
        return value;
    }


    public static void set_totalarrival(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("totalarrival", uid);
        editor.commit();

    }

    public static String get_totalarrival(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("totalarrival", "");
        return value;
    }

    public static void set_newbatchpick(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("newbatchpick", uid);
        editor.commit();

    }

    public static String get_newbatchpick(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("newbatchpick", "");
        return value;
    }



    public static void set_newreturnStock(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("newreturnStock", uid);
        editor.commit();

    }

    public static String get_newreturnStock(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("newreturnStock", "");
        return value;
    }

    public static void set_newPrinterSelect(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("newPrinterSelect", uid);
        editor.commit();

    }

    public static String get_newPrinterSelect(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("newPrinterSelect", "");
        return value;
    }


    public static void set_Tas_ReShip(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("tasflag", uid);
        editor.commit();

    }

    public static String get_Tas_ReShip(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("tasflag", "0");
        return value;
    }
    public static void set_Tshipping_change(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("Tshipping_change", uid);
        editor.commit();

    }

    public static String get_Tshipping_change(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("Tshipping_change", "0");
        return value;
    }
    public static void set_ShipOrderNumber(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("ShipOrderNumber", uid);
        editor.commit();

    }

    public static String get_ShipOrderNumber(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("ShipOrderNumber", "0");
        return value;
    }

    public static void set_returnStockBarcode(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("returnStockBarcode", uid);
        editor.commit();

    }

    public static String get_returnStockBarcode(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("returnStockBarcode", "");
        return value;
    }

    public static void set_koguchiPrint(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("KoguchiPrint", uid);
        editor.commit();

    }

    public static String get_koguchiPrint(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("KoguchiPrint", "");
        return value;
    }



    public static void set_Mitsukoshi_arrival(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("mitsukoshi_arrival", uid);
        editor.commit();

    }

    public static String get_Mitsukoshi_arrival(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("mitsukoshi_arrival", "");
        return value;
    }


    public static void set_invoice_printing(Context activity, String invoice_printing) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("invoice_printing", invoice_printing);
        editor.commit();

    }

    public static String get_invoice_printing(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("invoice_printing", "");
        return value;
    }

    public static void set_multiuser_shipping(Context activity, String tag) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("multiuser_shipping", tag);
        editor.commit();

    }

    public static String get_multiuser_shipping(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("multiuser_shipping", "");
        return value;
    }

    public static void setSimpleShipping(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("SimpleShipping", uid);
        editor.commit();

    }

    public static String getSimpleShipping(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("SimpleShipping", "");
        return value;
    }

    public static String getTracking_date_check(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("Tracking_date_check", "0");
        return value;
    }
    public static void setTracking_date_check(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("Tracking_date_check", uid);
        editor.commit();

    }
    public static String getBoxChangeCheck(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("BoxChangeCheck", "0");
        return value;
    }

    public static void setBoxChangeCheck(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("BoxChangeCheck", uid);
        editor.commit();

    }

    public static String getIrisArrival(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("IrisArrival", "0");
        return value;
    }

    public static void setIrisArrival(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("IrisArrival", uid);
        editor.commit();

    }

    public static String getIrisPicking(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("IrisPicking", "0");
        return value;
    }

    public static void setIrisPicking(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("IrisPicking", uid);
        editor.commit();

    }

    public static String getReshipment(Context activity) {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        value = preferences.getString("ShipReshipment", "0");
        return value;
    }

    public static void setReshipment(Context activity, String uid) {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
        editor.putString("ShipReshipment", uid);
        editor.commit();

    }

}
