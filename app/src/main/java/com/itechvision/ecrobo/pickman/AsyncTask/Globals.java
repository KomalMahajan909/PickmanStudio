package com.itechvision.ecrobo.pickman.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Globals {
    public static List<ParamsGetter> getterList = new ArrayList<>();
    public static List<String> serialList = new ArrayList<>();
    public static JSONObject jsonObj, jsnObj1;
    public static JSONArray jsonArr;

    public static class Webservice {
        public static String ListShopAll="/api3/list_shop_all.php";
        public static String Login ="/api3/user_validation.php";
        public static String GetLocation ="/api3/get_location.php";
        public static String Getshipping ="/api3/order_cat_piking.php";
        public static String Fixedshipping = "/api3/fixed_cat_piking.php";
        public static String BatchPicking = "/api3/get_sdbatch.php";
        public static String DBatchPicking = "/api3/get_dbatch.php";
        public static String sendLog="/api3/log_files.php";
        public static String listPrinter = "/api3/list_printer.php";
        public static String getArrival= "/api3/get_arrival.php";
        public static String getAllocationArrival = "/api3/get_allocation_arrival.php";
        public static String moveStockArrival = "/api3/move_stock_arrival.php";
        public static String addArrival = "/api3/add_arrival.php";
        public static String getProduct = "/api3/get_product.php";
        public static String getStockReturn = "/api3/get_stock_return.php";
        public static String addStockReturn="/api3/add_stock_return.php";
        public static String getStockReturnLotno ="/api3/get_stock_return_lotno.php";
        public static String shippingStatus ="/api3/shipping_status.php";
        public static String koguchi = "/api3/koguchi.php";
        public static String getAllocation = "/api3/get_allocation.php";
        public static String movestock = "/api3/move_stock.php";
        public static String productMoveStock =  "/api3/move_product_stock.php";


        //Iriis Picking
        public static String getIrisPiking = "/api3/case_get_piking.php";
        public static String getIrisShipping ="/api3/case_order_cat_piking.php";
        public static String checkIrisOrder = "/api3/case_check_order.php";
        public static String fixedIrisPiking = "/api3/case_fixed_piking.php";

        public static String listStockIDs = "/api3/list_stock_ids.php";
        public static String getLocationTally = "/api3/get_location_tally.php";
        public static String getLocation = "/api3/get_location.php";
        public static String moveLocation = "/api3/move_location.php";
        public static String getReturn = "/api3/get_return.php";
        public static String sendBack = "/api3/order_sendback.php";
        public static String getPiking = "/api3/get_piking.php";

        public static String fixedPiking = "/api3/fixed_piking.php";
        public static String fixedPikingGroup = "/api3/fixed_group_picking.php";
        public static String getPickingOrderDetail ="/api3/get_order_detail.php";
        public static String addPrint = "/api3/add_print.php";
        public static String addPacking = "/api3/add_packing.php" ;

        public static String getIrirsArrival= "/api3/iris_get_arrival.php";
        public static String moveStockIrirsArrival= "/api3/iris_move_stock_arrival.php";
        public static String addIrirsArrival= "/api3/iris_add_arrival.php";

        public static String getGetLocationTally = "/api3/get_location_tally.php";
        public static String validateProduct = "/api3/validate_product.php" ;
        public static String trackInventory = "/api3/track_inventory.php";
     // public static String listPrinters = "https://api.air-logi.com/service/api3/ap_printer.php";
        public static String listPrinters = "/api3/ap_printer.php";
        public static String sendPrinterRequest = "/api3/ap_print_order_pdf.php";
        public static String stockUpdate = "/api3/stock_update.php";
        public static String productQuantity  = "/api3/product_quantity.php";
        public static String checkOrder = "/api3/check_order.php";
        public static String getGroupPicking= "/api3/get_group_picking.php";
        public static String getPacking = "/api3/get_packing.php";
        public static String getRegiPacking = "/api3/reji_get_packing.php";
        public static String fixedPacking = "/api3/fixed_packing.php";
        public static String batch_boxno = "/api3/batch_boxno.php";
        public static String getTimeBatch = "/api3/get_dtime_batch.php";
        public static String time_DBatch_Picking = "/api3/dbatch_include_picking.php";
     // public static String Dbatch_Truck_Picking = "/api3/dbatch_truck_picking.php";
        public static String Dbatch_Truck_Picking = "/api3/dbatch_truck_picking_new.php";
        public static String rfid_arrival = "/api3/arrival_1671.php";
        public static String rfid_return = "/api3/return_aircloset.php";
        public static String automateScan = "/api3/automate_scan.php";
        public static String check_RF = "/api3/check_rfid.php";
        public static String Rf_order = "/api3/order_aircloset.php";
        public static String barcode_print = "/api3/barcode_print.php";
        public static String ecms_weight = "/api3/ecms_weight.php";
        public static String rf_write ="/api3/write_rfid.php";
        public static String Logout = "/api3/logout.php";
        //total pick
        public static String shop_list = "/api3/shop_list.php";
        public static String Order_picking = "/api3/order_picking.php";
     // public static String NewPrinterlist = "https://api.air-logi.com/service/api3/ap_printer_list.php"
     // public static String NewMachinelist = "https://api.air-logi.com/service/api3/ap_machine_list.php";
        public static String NewMachinelist = "/api3/ap_machine_list.php";
        public static String NewPrinterlist = "/api3/ap_printer_list.php";

        public static String self_put = "/api3/self_put.php";

        public static String daimaruaddPrint = "/api3/daimatsu_add_print.php";
        public static String daimaruautomateScan = "/api3/daimatsu_automate_scan.php";
        public static String daimaruFixedshipping = "/api3/daimatsu_fixed_cat_piking.php";


    }
}