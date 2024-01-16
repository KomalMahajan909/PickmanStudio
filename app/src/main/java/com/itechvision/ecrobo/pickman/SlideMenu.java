package com.itechvision.ecrobo.pickman;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.Chatman.Account.DemoLoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Account.MenuSettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Account.SendLogActivity;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.AllocationActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.CustomerArrival.CustomerIDActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.DirectReturnActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.DirectarrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.IrisScreens.IrisDirectArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.IrisScreens.IrisPickingActivity;
import com.itechvision.ecrobo.pickman.Chatman.IrisScreens.Iris_Arrival_Activity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ReturnStockActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.DmBatch.DmBatchListActivity;
import com.itechvision.ecrobo.pickman.Chatman.InvoicePrint.InvoicePrintActivity;
import com.itechvision.ecrobo.pickman.Chatman.LoopShipping.LoopShipping;
import com.itechvision.ecrobo.pickman.Chatman.PickWork.W6Batch.BatchListW6Activity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.InvoiceShipping.InvoiceShippingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.ShippingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.SimpleShippingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.NewMoveStock.NewMoveStockActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.SlipPrinterActivity;
import com.itechvision.ecrobo.pickman.Chatman.Tshipping.BoxCashRegisterActivity;
import com.itechvision.ecrobo.pickman.Chatman.WWW6Server.DimaruShipping.Daimaru_Shipping;
import com.itechvision.ecrobo.pickman.Chatman.NewKoguchi.NewKoguchiActivity;
import com.itechvision.ecrobo.pickman.Chatman.PickWork.MisukoshiArrival.Arrival_ID_Activity;
import com.itechvision.ecrobo.pickman.Chatman.PickWork.PrintKoguchi.PrintKoguchiActivity;
import com.itechvision.ecrobo.pickman.Chatman.PickWork.ReturnStockBarcode.ReturnStockBarcodeActivity;
import com.itechvision.ecrobo.pickman.Chatman.RFDeviceBaseScan;
import com.itechvision.ecrobo.pickman.Chatman.PickWork.NewBatcPicking.NewdbatchPickingActivity;
import com.itechvision.ecrobo.pickman.Chatman.PickWork.NewDolPick.NewdolpickcheckActivity;
import com.itechvision.ecrobo.pickman.Chatman.PickWork.NewReturnStock.NewReturnStockActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ReturnActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ReturnAllocationActivity;
import com.itechvision.ecrobo.pickman.Chatman.PickWork.DolphinArrival.TotalArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.PickWork.NewPrinter.NewprinterScreenActivity;
import com.itechvision.ecrobo.pickman.Chatman.PickWork.TasPicking.Newtaspicking;
import com.itechvision.ecrobo.pickman.Chatman.Ship.AddWeightsActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.BatchPickingOrder;
import com.itechvision.ecrobo.pickman.Chatman.Ship.BoxSizeActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.DBatchActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.DbatchIncludeActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.KoguchiActivity;
import com.itechvision.ecrobo.pickman.Chatman.PickWork.NewBatcPicking.NewBatchPickingOrder;
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewBoxSizeActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewPackingSetActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewPickingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewShippingGroupActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.PackSetActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.SearchLocationActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.SearchProductActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.ShippingSpecificationActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TotalPickListActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TruckBatchActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.BarcodeLabelPrintActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.InventoryActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.MoveLocationActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.MoveStockActivity;
import com.itechvision.ecrobo.pickman.Chatman.WWW6Server.OnetoONeSlip.One_to_One_SlipPrinter;
import com.itechvision.ecrobo.pickman.Chatman.Stock.PrinterMachineActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.StockAdjustmentActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.StockChangeActivity;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.Util.SharedPrefrences;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SlideMenu implements View.OnClickListener, MainAsynListener<String> {
    Dialog dialog_Forgot;
    SlidingMenu menu;
    Context c;
    public static String mboxId = "";
    public static TextView userName, userId,shiptxt,slipprinttxt38,arrivaltxt5;
    RelativeLayout main, update, batchpicking, searchLocation, searchproduct, newshipping, newPicking, shippingspecification, movelocation, itemshipped, arrival, allocation, newreturn, movestock, stockchnge, returnAllocation, settings, logout, sndLogs, returnStock, inventory, boxCash,
            printerSelect, stockAdjust, packSet, newPackSet, newBoxSize, dbatchpicking, tempLocation, truckBatch, menuSetting, rfCheck, rfArrival, rfReturn, slipPRinter, machineprinter, rfReadWrite, rfPicking, barcodeLabel, newboxsize, ecmsWeight, rfWriter, newShipGroup,customerArrival, new_invoice_print,
            totalPick, totalarrival, action_batch_picking_new, action_tas_picking_new, action_newreturn_stock, action_newmachine_printer, action_dol_picking, action_returnstock_barcode, koguchi_print, action_order, mitsukoshi_arrival,action_daimaru_slip_printer, action_daimaru_batch_picking, action_simple_shipping,action_new_loopshipping, new_move_stock, iinvoice_shipping , dm_batch,iris_arrival,iris_picking;
    public static LinearLayout sdBatch, dBatch, temporaryLocation, truckBatchLL, koguchi, boxSize, newboxsizeLL, rfWriterLL, barcodelabelprintLL, llnewtaspick,llbox_cash_register, llnew_move_stock,
            printer, stockChange, packSetLL, newPackSetLL, slipPRinterll, rfCheckll, rfarrivalLL, rfReturnLL, machineprinterLL, rfReadWriteLL,llsimple_shipping,llcustomerArrival,
            rfPickingLL, ecmsWeightLL, newShipGroupLL, totalPickLL, totalarrivallist, llnewbatchpick, llnewreturn_stock, llnewMachinePrinter, lldoltotalpick, llreturnstock_barcode, llkoguchi_print, llorderdetails, llmitsukoshi_arrival,llDaimaruSlipPrinter, lldaimarubatch ,loopshipping, llinvoice_shipping, lldm_batch_picking, llnew_invoice_print,ll_iris_arrival, ll_iris_picking;

    Handler handler;
    private SweetAlertDialog pDialog_logout;
    SharedPreferences sharedPreferences;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public static final String MYPREFERENCE = "MyPrefs";
    public static final String ISUSERLOGIN = "isUserLogin";
    DataManager manger;
    DataManager.Logoutcallback call;
    ECRApplication app = new ECRApplication();

    public SlideMenu(SlidingMenu menu, Context c) {

        this.menu = menu;
        this.c = c;
        this.call = call;

        userName = menu.findViewById(R.id.txt_user);
        userId = menu.findViewById(R.id.txt_userID);
        shiptxt = menu.findViewById(R.id.shiptxt);
        arrivaltxt5 = menu.findViewById(R.id.arrivaltxt5);
        slipprinttxt38 = menu.findViewById(R.id.slipprinttxt38);

        spDomain = c.getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        sharedPreferences = c.getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);

        sdBatch = menu.findViewById(R.id.llbatchpick);
        dBatch = menu.findViewById(R.id.lld_batch);
        temporaryLocation = menu.findViewById(R.id.llTempLocation);
        truckBatchLL = menu.findViewById(R.id.lltruckBatch);
        koguchi = menu.findViewById(R.id.llbox_number);
        boxSize = menu.findViewById(R.id.llnew_box_size);
        printer = menu.findViewById(R.id.llPrinter);
        stockChange = menu.findViewById(R.id.llStockChange);
        packSetLL = menu.findViewById(R.id.llPackSet);
        newPackSetLL = menu.findViewById(R.id.llNewPackSet);
        machineprinterLL = menu.findViewById(R.id.llMachinePrinter);
        barcodelabelprintLL = menu.findViewById(R.id.llBarcodeLabelPrint);
        slipPRinterll = menu.findViewById(R.id.llSlipPrinter);
        action_daimaru_slip_printer = menu.findViewById(R.id.action_daimaru_slip_printer);
        llDaimaruSlipPrinter = menu.findViewById(R.id.llDaimaruSlipPrinter);
        rfarrivalLL = menu.findViewById(R.id.llNewRFArrival);
        rfReturnLL = menu.findViewById(R.id.llNewRFReturn);
        rfCheckll = menu.findViewById(R.id.llRFCheck);
        rfReadWriteLL = menu.findViewById(R.id.llRFReadWrite);
        rfPickingLL = menu.findViewById(R.id.llRFPick);
        newboxsizeLL = menu.findViewById(R.id.llnew_box_size2);
        ecmsWeightLL = menu.findViewById(R.id.llecmsWeight);
        newShipGroupLL = menu.findViewById(R.id.shipping_groupLL);
        rfWriterLL = menu.findViewById(R.id.llNewWrite);
        totalPickLL = menu.findViewById(R.id.lltotal_pick_list);
        llbox_cash_register = menu.findViewById(R.id.llbox_cash_register_toreba);
        totalarrivallist = menu.findViewById(R.id.lltotal_arrival_list);
        llnewbatchpick = menu.findViewById(R.id.llnewbatchpick);
        llnewtaspick = menu.findViewById(R.id.llnewtaspick);
        llnewreturn_stock = menu.findViewById(R.id.llnewreturn_stock);
        llnewMachinePrinter = menu.findViewById(R.id.llnewMachinePrinter);
        lldoltotalpick = menu.findViewById(R.id.lldoltotalpick);
        llreturnstock_barcode = menu.findViewById(R.id.llreturnstock_barcode);
        llkoguchi_print = menu.findViewById(R.id.llkoguchi_print);
        llorderdetails = menu.findViewById(R.id.llorderdetails);
        llmitsukoshi_arrival = menu.findViewById(R.id.llmitsukoshi_arrivals);
        lldaimarubatch = menu .findViewById(R.id.lldaimarubatch);
        lldm_batch_picking = menu.findViewById(R.id.llDmbatch);
        llsimple_shipping = menu.findViewById(R.id.llSimple_shipping);
        loopshipping = menu.findViewById(R.id.loopshipping);
        llcustomerArrival = menu.findViewById(R.id.llcustomerArrival);
        llnew_move_stock = menu.findViewById(R.id.new_move_stock);
        llinvoice_shipping = menu.findViewById(R.id.ll_invoice_shipping);
        llnew_invoice_print = menu.findViewById(R.id.llnew_invoice_print);
        ll_iris_arrival = menu.findViewById(R.id.ll_iris_arrivals);
        ll_iris_picking = menu.findViewById(R.id.llirispicking);



        NewBatchPickingOrder.click = false;
        NewdbatchPickingActivity.back = "0";

        if (BaseActivity.getUrl().equalsIgnoreCase("https://api.air-logi.com/service") && BaseActivity.getShopId().equalsIgnoreCase("3366")||BaseActivity.getUrl().equalsIgnoreCase("https://staging.air-logi.com/service") && BaseActivity.getShopId().equalsIgnoreCase("3366") ){
            SlideMenu.loopshipping.setVisibility(View.VISIBLE);
        }else{
            SlideMenu.loopshipping.setVisibility(View.GONE);
        }


        if (sharedPreferences.getBoolean("sdBatchCheck", false)) {
            sdBatch.setVisibility(View.VISIBLE);
        } else
            sdBatch.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("dBatchCheck", false)) {
            dBatch.setVisibility(View.VISIBLE);
        } else
            dBatch.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("temporaryLocationCheck", false)) {
            temporaryLocation.setVisibility(View.VISIBLE);
        } else
            temporaryLocation.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("truckBatchCheck", false)) {
            truckBatchLL.setVisibility(View.VISIBLE);
        } else
            truckBatchLL.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("koguchiCheck", false)) {
            koguchi.setVisibility(View.VISIBLE);
        } else
            koguchi.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("boxSizeCheck", false)) {
            boxSize.setVisibility(View.VISIBLE);
        } else
            boxSize.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("barcodeSlipPrinter", false)) {
            printer.setVisibility(View.VISIBLE);
        } else
            printer.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("StockChangeCheck", false)) {
            stockChange.setVisibility(View.VISIBLE);
        } else
            stockChange.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("packSetCheck", false)) {
            packSetLL.setVisibility(View.VISIBLE);
        } else
            packSetLL.setVisibility(View.GONE);


        if (sharedPreferences.getBoolean("newPackSetCheck", false)) {
            newPackSetLL.setVisibility(View.VISIBLE);
        } else
            newPackSetLL.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("rfNewArrivalCheck", false)) {
            rfarrivalLL.setVisibility(View.VISIBLE);
        } else
            rfarrivalLL.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("rfNewReturnCheck", false)) {
            rfReturnLL.setVisibility(View.VISIBLE);
        } else
            rfReturnLL.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("slipPrinterCheck", false)) {
            slipPRinterll.setVisibility(View.VISIBLE);
        } else
            slipPRinterll.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("machinePrinterCheck", false))
            machineprinterLL.setVisibility(View.VISIBLE);
        else
            machineprinterLL.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("rfPickingCheck", false))
            rfPickingLL.setVisibility(View.VISIBLE);
        else
            rfPickingLL.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("shippingCheck", false))
            newShipGroupLL.setVisibility(View.VISIBLE);
        else
            newShipGroupLL.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("ecms", false))
            ecmsWeightLL.setVisibility(View.VISIBLE);
        else
            ecmsWeightLL.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("rfWriter", false))
            rfWriterLL.setVisibility(View.VISIBLE);

        else
            rfWriterLL.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("pdBoxSize", false))
            newboxsizeLL.setVisibility(View.VISIBLE);
        else
            newboxsizeLL.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("printerCheck", false)) {
            barcodelabelprintLL.setVisibility(View.VISIBLE);
        } else
            barcodelabelprintLL.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("picklist", false)) {
            totalPickLL.setVisibility(View.VISIBLE);
        } else
            totalPickLL.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("totalarrival", false)) {
            totalarrivallist.setVisibility(View.VISIBLE);
        } else
            totalarrivallist.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("returnStockBarcode", false)) {
            llreturnstock_barcode.setVisibility(View.VISIBLE);
        } else
            llreturnstock_barcode.setVisibility(View.GONE);

        if (sharedPreferences.getBoolean("newbatchpick", false)) {
            llnewbatchpick.setVisibility(View.VISIBLE);
        } else
            llnewbatchpick.setVisibility(View.GONE);


        if (sharedPreferences.getBoolean("newTasPick", false)) {
            llnewtaspick.setVisibility(View.VISIBLE);
        } else
            llnewtaspick.setVisibility(View.GONE);


        if (sharedPreferences.getBoolean("newreturnStock", false)) {
            llnewreturn_stock.setVisibility(View.VISIBLE);
        } else
            llnewreturn_stock.setVisibility(View.GONE);


        if (sharedPreferences.getBoolean("newPrinterSelect", false)) {
            llnewMachinePrinter.setVisibility(View.VISIBLE);
        } else
            llnewMachinePrinter.setVisibility(View.GONE);

///Rav Share
        if (SharedPrefrences.get_sdBatchCheck(c).equalsIgnoreCase("1")) {
            sdBatch.setVisibility(View.VISIBLE);
        } else {
            sdBatch.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_dBatchCheck(c).equalsIgnoreCase("1")) {
            dBatch.setVisibility(View.VISIBLE);
        } else {
            dBatch.setVisibility(View.GONE);
        }


        if (SharedPrefrences.get_temporaryLocationCheck(c).equalsIgnoreCase("1")) {
            temporaryLocation.setVisibility(View.VISIBLE);
        } else {
            temporaryLocation.setVisibility(View.GONE);
        }


        if (SharedPrefrences.get_truckBatchCheck(c).equalsIgnoreCase("1")) {
            truckBatchLL.setVisibility(View.VISIBLE);
        } else {
            truckBatchLL.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_shippingCheck(c).equalsIgnoreCase("1")) {
            newShipGroupLL.setVisibility(View.VISIBLE);
        } else {
            newShipGroupLL.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_koguchiCheck(c).equalsIgnoreCase("1")) {
            koguchi.setVisibility(View.VISIBLE);
        } else {
            koguchi.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_boxSizeCheck(c).equalsIgnoreCase("1")) {
            boxSize.setVisibility(View.VISIBLE);
        } else {
            boxSize.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_printerCheck(c).equalsIgnoreCase("1")) {
            barcodelabelprintLL.setVisibility(View.VISIBLE);
        } else {
            barcodelabelprintLL.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_StockChangeCheck(c).equalsIgnoreCase("1")) {
            stockChange.setVisibility(View.VISIBLE);
        } else {
            stockChange.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_packSetCheck(c).equalsIgnoreCase("1")) {
            packSetLL.setVisibility(View.VISIBLE);
        } else {
            packSetLL.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_newPackSetCheck(c).equalsIgnoreCase("1")) {
            newPackSetLL.setVisibility(View.VISIBLE);
        } else {
            newPackSetLL.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_rfNewArrivalCheck(c).equalsIgnoreCase("1")) {
            rfarrivalLL.setVisibility(View.VISIBLE);
        } else {
            rfarrivalLL.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_rfNewReturnCheck(c).equalsIgnoreCase("1")) {
            rfReturnLL.setVisibility(View.VISIBLE);
        } else {
            rfReturnLL.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_slipPrinterCheck(c).equalsIgnoreCase("1")) {
            slipPRinterll.setVisibility(View.VISIBLE);
        } else {
            slipPRinterll.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_rfPickingCheck(c).equalsIgnoreCase("1")) {
            rfPickingLL.setVisibility(View.VISIBLE);
        } else {
            rfPickingLL.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_machinePrinterCheck(c).equalsIgnoreCase("1")) {
            machineprinterLL.setVisibility(View.VISIBLE);
        } else {
            machineprinterLL.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_barcodeSlipPrinter(c).equalsIgnoreCase("1")) {
            printer.setVisibility(View.VISIBLE);
        } else {
            printer.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_rfWriter(c).equalsIgnoreCase("1")) {
            rfWriterLL.setVisibility(View.VISIBLE);
        } else {
            rfWriterLL.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_ecms(c).equalsIgnoreCase("1")) {
            ecmsWeightLL.setVisibility(View.VISIBLE);
        } else {
            ecmsWeightLL.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_pdBoxSize(c).equalsIgnoreCase("1")) {
            newboxsizeLL.setVisibility(View.VISIBLE);
        } else {
            newboxsizeLL.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_picklist(c).equalsIgnoreCase("1")) {
            totalPickLL.setVisibility(View.VISIBLE);
        } else {
            totalPickLL.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_totalarrival(c).equalsIgnoreCase("1")) {
            totalarrivallist.setVisibility(View.VISIBLE);
        } else {
            totalarrivallist.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_Mitsukoshi_arrival(c).equalsIgnoreCase("1")) {
            llmitsukoshi_arrival.setVisibility(View.VISIBLE);
        } else {
            llmitsukoshi_arrival.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_newbatchpick(c).equalsIgnoreCase("1")) {
            llnewbatchpick.setVisibility(View.VISIBLE);
        } else {
            llnewbatchpick.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_newTasPick(c).equalsIgnoreCase("1")) {
            llnewtaspick.setVisibility(View.VISIBLE);
        } else {
            llnewtaspick.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_newreturnStock(c).equalsIgnoreCase("1")) {
            llnewreturn_stock.setVisibility(View.VISIBLE);
        } else {
            llnewreturn_stock.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_newPrinterSelect(c).equalsIgnoreCase("1")) {
            llnewMachinePrinter.setVisibility(View.VISIBLE);
        } else {
            llnewMachinePrinter.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_newdolArrival(c).equalsIgnoreCase("1")) {
            lldoltotalpick.setVisibility(View.VISIBLE);
        } else {
            lldoltotalpick.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_returnStockBarcode(c).equalsIgnoreCase("1")) {
            llreturnstock_barcode.setVisibility(View.VISIBLE);
        } else {
            llreturnstock_barcode.setVisibility(View.GONE);
        }

        if (SharedPrefrences.get_koguchiPrint(c).equalsIgnoreCase("1")) {
            llkoguchi_print.setVisibility(View.VISIBLE);
        } else {
            llkoguchi_print.setVisibility(View.GONE);
        }
        if (SharedPrefrences.get_invoice_printing(c).equalsIgnoreCase("1")) {
            llorderdetails.setVisibility(View.VISIBLE);
        } else {
            llorderdetails.setVisibility(View.GONE);
        }
        if (SharedPrefrences.getSimpleShipping(c).equalsIgnoreCase("1")) {
            llsimple_shipping.setVisibility(View.VISIBLE);
        } else {
            llsimple_shipping.setVisibility(View.GONE);
        }
        if (SharedPrefrences.getW6BatchPicking(c).equalsIgnoreCase("1")) {
            lldaimarubatch.setVisibility(View.VISIBLE);
        } else {
            lldaimarubatch.setVisibility(View.GONE);
        }

        if(SharedPrefrences.getCustomerArrival(c).equals("1"))
            llcustomerArrival.setVisibility(View.VISIBLE);
        else
            llcustomerArrival.setVisibility(View.GONE);

        if(SharedPrefrences.get_tshippingCheck(c).equals("1"))
            llbox_cash_register.setVisibility(View.VISIBLE);
        else
            llbox_cash_register.setVisibility(View.GONE);


        if(SharedPrefrences.get_new_move_stock(c).equals("1"))
            llnew_move_stock.setVisibility(View.VISIBLE);
        else
            llnew_move_stock.setVisibility(View.GONE);

        if(SharedPrefrences.get_invoice_shipping(c).equals("1"))
            llinvoice_shipping.setVisibility(View.VISIBLE);
        else
            llinvoice_shipping.setVisibility(View.GONE);

        if (SharedPrefrences.getDmBatchPicking(c).equalsIgnoreCase("1")) {
            lldm_batch_picking.setVisibility(View.VISIBLE);
        } else {
            lldm_batch_picking.setVisibility(View.GONE);
        }

        if (SharedPrefrences.getNewInvoicePrint(c).equalsIgnoreCase("1")) {
            llnew_invoice_print.setVisibility(View.VISIBLE);
        } else {
            llnew_invoice_print.setVisibility(View.GONE);
        }

        if (SharedPrefrences.getIrisArrival(c).equalsIgnoreCase("1")) {
            ll_iris_arrival.setVisibility(View.VISIBLE);
        } else {
            ll_iris_arrival.setVisibility(View.GONE);
        }


        if (SharedPrefrences.getIrisPicking(c).equalsIgnoreCase("1")) {
            ll_iris_picking.setVisibility(View.VISIBLE);
        } else {
            ll_iris_picking.setVisibility(View.GONE);
        }


        if (BaseActivity.getUrl().equals("https://www6.air-logi.com/service")){
            shiptxt.setText("レジ検品DM");
            slipprinttxt38.setText("1-1検品DM");
            arrivaltxt5.setText("入荷検品DM");
            llDaimaruSlipPrinter.setVisibility(View.VISIBLE);
            lldaimarubatch.setVisibility(View.VISIBLE);
            //slipPRinterll.setVisibility(View.VISIBLE);
        }else{
            shiptxt.setText("レジ検品");
            slipprinttxt38.setText("1-1検品");
            arrivaltxt5.setText("入荷検品");
//            slipPRinterll.setVisibility(View.GONE);
            llDaimaruSlipPrinter.setVisibility(View.GONE);
            lldaimarubatch.setVisibility(View.GONE);
        }



        main = menu.findViewById(R.id.menu_main);
        searchproduct = menu.findViewById(R.id.action_searchProduct);
        searchLocation = menu.findViewById(R.id.action_searchLocation);
        newshipping = menu.findViewById(R.id.action_new_shipping);
        movestock = menu.findViewById(R.id.action_move_stock);
        newPicking = menu.findViewById(R.id.action_new_picking);
        sndLogs = menu.findViewById(R.id.action_sendLogs);
        shippingspecification = menu.findViewById(R.id.action_shipping_specification);
        movelocation = menu.findViewById(R.id.action_move_location);
        itemshipped = menu.findViewById(R.id.action_koguchi);
        returnAllocation = menu.findViewById(R.id.action_return_allocation);
        stockchnge = menu.findViewById(R.id.action_stock_change);
        newreturn = menu.findViewById(R.id.action_return);
        arrival = menu.findViewById(R.id.action_arrival);
        allocation = menu.findViewById(R.id.action_allocation);
        batchpicking = menu.findViewById(R.id.action_batch_picking);
        update = menu.findViewById(R.id.action_update);
        logout = menu.findViewById(R.id.action_logout);
        settings = menu.findViewById(R.id.action_setting);
        returnStock = menu.findViewById(R.id.action_return_stock);
        inventory = menu.findViewById(R.id.action_inventory);
        stockAdjust = menu.findViewById(R.id.action_stock_adjustment);
        packSet = menu.findViewById(R.id.action_pack_set);
        newPackSet = menu.findViewById(R.id.action_new_pack_set);
        newBoxSize = menu.findViewById(R.id.action_box_size);
        dbatchpicking = menu.findViewById(R.id.action_new_batch_picking);
        tempLocation = menu.findViewById(R.id.action_temp_location);
        truckBatch = menu.findViewById(R.id.action_truck);
        menuSetting = menu.findViewById(R.id.action_menu_setting);
        rfArrival = menu.findViewById(R.id.action_new_rf_arrival);
        rfReturn = menu.findViewById(R.id.action_new_rf_return);
        rfCheck = menu.findViewById(R.id.action_rf_check);
        slipPRinter = menu.findViewById(R.id.action_slip_printer);
        rfReadWrite = menu.findViewById(R.id.action_read_write);
        rfPicking = menu.findViewById(R.id.action_rf_picking);
        barcodeLabel = menu.findViewById(R.id.action_barcode_label_print);
        printerSelect = menu.findViewById(R.id.action_printer_select);
        machineprinter = menu.findViewById(R.id.action_machine_printer);
        newboxsize = menu.findViewById(R.id.action_box_size2);
        ecmsWeight = menu.findViewById(R.id.action_ecms_weight);
        rfWriter = menu.findViewById(R.id.action_rf_writer);
        newShipGroup = menu.findViewById(R.id.action_new_shipping_group);
        totalPick = menu.findViewById(R.id.action_total_pick);
        totalarrival = menu.findViewById(R.id.action_total_arrival);
        action_batch_picking_new = menu.findViewById(R.id.action_batch_picking_new);
        action_tas_picking_new = menu.findViewById(R.id.action_tas_picking_new);
        action_newreturn_stock = menu.findViewById(R.id.action_newreturn_stock);
        action_newmachine_printer = menu.findViewById(R.id.action_newmachine_printer);
        action_dol_picking = menu.findViewById(R.id.action_dol_picking);
        action_returnstock_barcode = menu.findViewById(R.id.action_returnstock_barcode);
        koguchi_print = menu.findViewById(R.id.action_koguchi_print);
        action_order = menu.findViewById(R.id.action_order);
        mitsukoshi_arrival = menu.findViewById(R.id.action_mitsukoshi_arrival);
        action_daimaru_batch_picking = menu.findViewById(R.id.action_daimaru_batch_picking);
        dm_batch = menu.findViewById(R.id.action_dm_batch_picking);
        action_simple_shipping = menu.findViewById(R.id.action_simple_shipping);
        action_new_loopshipping = menu.findViewById(R.id.action_new_loopshipping);
        boxCash = menu.findViewById(R.id.action_box_cash_register_toreba);
        customerArrival = menu.findViewById(R.id.action_customer_arrival);
        new_move_stock = menu.findViewById(R.id.action_new_move_stock);
        iinvoice_shipping = menu.findViewById(R.id.action_invoice_shipping);
        new_invoice_print = menu.findViewById(R.id.action_new_invoice_print);
        iris_arrival = menu.findViewById(R.id.action_iris_arrival);
        iris_picking = menu.findViewById(R.id.action_iris_picking);

        spDomain = c.getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        searchproduct.setOnClickListener(this);


        searchLocation.setOnClickListener(this);
        newshipping.setOnClickListener(this);
        newShipGroup.setOnClickListener(this);
        newPicking.setOnClickListener(this);
        sndLogs.setOnClickListener(this);
        shippingspecification.setOnClickListener(this);
        movestock.setOnClickListener(this);
        movelocation.setOnClickListener(this);
        itemshipped.setOnClickListener(this);
        newreturn.setOnClickListener(this);
        returnAllocation.setOnClickListener(this);
        inventory.setOnClickListener(this);
        stockchnge.setOnClickListener(this);
        printerSelect.setOnClickListener(this);
        stockAdjust.setOnClickListener(this);
        packSet.setOnClickListener(this);
        newPackSet.setOnClickListener(this);
        tempLocation.setOnClickListener(this);
        truckBatch.setOnClickListener(this);
        menuSetting.setOnClickListener(this);
        rfArrival.setOnClickListener(this);
        rfReturn.setOnClickListener(this);
        rfCheck.setOnClickListener(this);
        rfReadWrite.setOnClickListener(this);
        slipPRinter.setOnClickListener(this);
        machineprinter.setOnClickListener(this);
        rfPicking.setOnClickListener(this);
        newBoxSize.setOnClickListener(this);
        arrival.setOnClickListener(this);
        allocation.setOnClickListener(this);
        batchpicking.setOnClickListener(this);
        dbatchpicking.setOnClickListener(this);
        returnStock.setOnClickListener(this);
        update.setOnClickListener(this);
        settings.setOnClickListener(this);
        logout.setOnClickListener(this);
        barcodeLabel.setOnClickListener(this);
        newboxsize.setOnClickListener(this);
        totalPick.setOnClickListener(this);
        ecmsWeight.setOnClickListener(this);
        rfWriter.setOnClickListener(this);
        totalarrival.setOnClickListener(this);
        customerArrival.setOnClickListener(this);
        action_batch_picking_new.setOnClickListener(this);
        action_tas_picking_new.setOnClickListener(this);
        action_newreturn_stock.setOnClickListener(this);
        action_newmachine_printer.setOnClickListener(this);
        action_dol_picking.setOnClickListener(this);
        action_returnstock_barcode.setOnClickListener(this);
        koguchi_print.setOnClickListener(this);
        action_order.setOnClickListener(this);
        mitsukoshi_arrival.setOnClickListener(this);
        action_daimaru_slip_printer.setOnClickListener(this);
        action_daimaru_batch_picking.setOnClickListener(this);
        action_simple_shipping.setOnClickListener(this);
        action_new_loopshipping.setOnClickListener(this);
        boxCash.setOnClickListener(this);
        new_move_stock.setOnClickListener(this);
        iinvoice_shipping.setOnClickListener(this);
        dm_batch.setOnClickListener(this);
        new_invoice_print.setOnClickListener(this);
        iris_arrival.setOnClickListener(this);
        iris_picking.setOnClickListener(this);

        String user = spDomain.getString("user_name", "");
        userName.setText("ユーザ : " + user);

        String adminID = spDomain.getString("admin_id", null);
        userId.setText("ID表示  : " + adminID);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.action_update:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //  c.startcActivity(new Intent(c, UpdateActivity.class));
                        final String appPackageName = c.getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                }, 10);

                break;

            case R.id.action_allocation:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, AllocationActivity.class));
                    }
                }, 10);

                break;

            case R.id.action_order:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, NewKoguchiActivity.class));
                    }
                }, 10);

                break;

            case R.id.action_dol_picking:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, NewdolpickcheckActivity.class));
                    }
                }, 10);

                break;

            case R.id.action_printer_select:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent i = new Intent(c, RFDeviceBaseScan.class);
                        //  Intent i =  new Intent(c, RFBarcodePrinterActivity.class);
                        i.putExtra("rf_use", "printer");
                        c.startActivity(i);

                    }
                }, 10);

                break;

            case R.id.action_read_write:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent i = new Intent(c, RFDeviceBaseScan.class);
                        i.putExtra("rf_use", "readWrite");
                        c.startActivity(i);

                    }
                }, 10);

                break;

            case R.id.action_truck:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, TruckBatchActivity.class));

                    }
                }, 10);

                break;

            case R.id.action_move_location:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, MoveLocationActivity.class));
                    }
                }, 10);

                break;

            case R.id.action_invoice_shipping:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, InvoiceShippingActivity.class));
                    }
                }, 10);

                break;

            case R.id.action_new_move_stock:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, NewMoveStockActivity.class));
                    }
                }, 10);

                break;

            case R.id.action_inventory:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, InventoryActivity.class));
                    }
                }, 10);

                break;

            case R.id.action_box_cash_register_toreba:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, BoxCashRegisterActivity.class));
                    }
                }, 10);

                break;
            case R.id.action_customer_arrival:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, CustomerIDActivity.class));
                    }
                }, 10);

                break;
            case R.id.action_move_stock:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, MoveStockActivity.class));
                    }
                }, 10);

                break;

            case R.id.action_batch_picking:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, BatchPickingOrder.class));
                        //  c.startActivity(new Intent(c, NewBatchPickingOrder.class));

                    }
                }, 10);

                break;

            case R.id.action_mitsukoshi_arrival:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, Arrival_ID_Activity.class));
                    }
                }, 10);


                break;
            case R.id.action_daimaru_batch_picking:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, BatchListW6Activity.class));
                    }
                }, 10);


                break;


            case R.id.action_rf_writer:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent i = new Intent(c, RFDeviceBaseScan.class);
                        i.putExtra("rf_use", "write");
                        c.startActivity(i);

                    }
                }, 10);

                break;

            case R.id.action_new_batch_picking:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, DBatchActivity.class));

                    }
                }, 10);

                break;
            case R.id.action_new_invoice_print:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, InvoicePrintActivity.class));

                    }
                }, 10);

                break;

            case R.id.action_total_pick:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, TotalPickListActivity.class));

                    }
                }, 10);

                break;
            case R.id.action_menu_setting:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        c.startActivity(new Intent(c, MenuSettingActivity.class));

                    }
                }, 10);

                break;


            case R.id.action_returnstock_barcode:
                menu.showContent();

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        c.startActivity(new Intent(c, ReturnStockBarcodeActivity.class));

                    }
                }, 10);

                break;
            case R.id.action_rf_check:

                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(c, RFDeviceBaseScan.class);
                        i.putExtra("rf_use", "check");
                        c.startActivity(i);
                        //  c.startActivity(new Intent(c, RFIDArrivalActivity.class));
                    }
                }, 10);

                break;
            case R.id.action_new_rf_arrival:

                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent i = new Intent(c, RFDeviceBaseScan.class);
                        i.putExtra("rf_use", "arrival");
                        c.startActivity(i);
//                        c.startActivity(new Intent(c, RFIDArrivalActivity.class));
                    }
                }, 10);

                break;
            case R.id.action_new_rf_return:

                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent i = new Intent(c, RFDeviceBaseScan.class);
                        i.putExtra("rf_use", "return");
                        c.startActivity(i);
                    }
                }, 10);

                break;
            case R.id.action_koguchi:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, KoguchiActivity.class));

                    }
                }, 10);


                break;
            case R.id.action_barcode_label_print:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, BarcodeLabelPrintActivity.class));

                    }
                }, 10);

                break;

            case R.id.action_searchProduct:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, SearchProductActivity.class));
                    }
                }, 10);
                break;
            case R.id.action_pack_set:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, PackSetActivity.class));
                    }
                }, 10);
                break;
            case R.id.action_new_pack_set:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        c.startActivity(new Intent(c, NewPackingSetActivity.class));
                    }
                }, 10);
                break;
            case R.id.action_setting:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        c.startActivity(new Intent(c, SettingActivity.class));
                    }
                }, 10);
                break;

            case R.id.action_ecms_weight:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, AddWeightsActivity.class));

                    }
                }, 10);


                break;

            case R.id.action_new_picking:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent(c, NewPickingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        c.startActivity(intent);

                    }
                }, 200);
                break;
            case R.id.action_iris_picking:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent(c, IrisPickingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        c.startActivity(intent);

                    }
                }, 200);
                break;


            case R.id.action_new_shipping:

                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (BaseActivity.getUrl().equals("https://www6.air-logi.com/service")){
                            Intent intent = new Intent(c, Daimaru_Shipping.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            c.startActivity(intent);
                        }else{
                            Intent intent = new Intent(c, ShippingActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            c.startActivity(intent);
                        }
                    }
                }, 200);
                break;


            case R.id.action_new_loopshipping:

                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        Intent intent = new Intent(c, LoopShipping.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        c.startActivity(intent);
                    }
                }, 200);
                break;

            case R.id.action_simple_shipping:

                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        Intent intent = new Intent(c, SimpleShippingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        c.startActivity(intent);
                    }
                }, 200);
                break;
            case R.id.action_new_shipping_group:

                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent(c, NewShippingGroupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        c.startActivity(intent);
                    }
                }, 200);
                break;
            case R.id.action_sendLogs:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        c.startActivity(new Intent(c, SendLogActivity.class));
                    }
                }, 200);
                break;
            case R.id.action_arrival:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //c.startActivity(new Intent(c, Permotors.class));
                        if(BaseActivity.getOptShelf()) {
                            c.startActivity(new Intent(c, DirectarrivalActivity.class));
                        }
                        else{
                           /* if(BaseActivity.getDirectToStock())
                                c.startActivity(new Intent(c, ArrivalW6Activity.class));
                            else*/
                            c.startActivity(new Intent(c, ArrivalActivity.class));
                        }


                    }
                }, 200);

                break;
            case R.id.action_return:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //c.startActivity(new Intent(c, Permotors.class));
                        if (BaseActivity.getOptShelf()) {
                            c.startActivity(new Intent(c, DirectReturnActivity.class));
                        } else {
                            c.startActivity(new Intent(c, ReturnActivity.class));
                        }

                    }
                }, 200);

                break;

            case R.id.action_return_allocation:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, ReturnAllocationActivity.class));
                    }
                }, 200);

                break;
            case R.id.action_temp_location:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, DbatchIncludeActivity.class));
                    }
                }, 200);

                break;

            case R.id.action_return_stock:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, ReturnStockActivity.class));
                    }
                }, 200);

                break;
            case R.id.action_box_size2:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, NewBoxSizeActivity.class));
                    }
                }, 200);

                break;
            case R.id.action_shipping_specification:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, ShippingSpecificationActivity.class));
                    }
                }, 200);

                break;
            case R.id.action_box_size:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, BoxSizeActivity.class));
                    }
                }, 200);

                break;

            case R.id.action_stock_adjustment:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (BaseActivity.getrole().equals("2") || BaseActivity.getrole().equals("8"))
                            c.startActivity(new Intent(c, StockAdjustmentActivity.class));
                        else c.startActivity(new Intent(c, SettingActivity.class));
                    }
                }, 200);

                break;
            case R.id.action_iris_arrival:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if(BaseActivity.getOptShelf()) {
                            c.startActivity(new Intent(c, IrisDirectArrivalActivity.class));
                        }
                        else{
                            c.startActivity(new Intent(c, Iris_Arrival_Activity.class));
                        }}
                }, 200);

                break;
            case R.id.action_stock_change:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, StockChangeActivity.class));
                    }
                }, 200);

                break;
            case R.id.action_searchLocation:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, SearchLocationActivity.class));
                    }
                }, 200);
                break;

            case R.id.action_rf_picking:

                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent i = new Intent(c, RFDeviceBaseScan.class);
                        i.putExtra("rf_use", "picking");
                        c.startActivity(i);
                    }
                }, 10);

                break;

            case R.id.action_koguchi_print:

                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent i = new Intent(c, PrintKoguchiActivity.class);
                        c.startActivity(i);
                    }
                }, 10);

                break;

            case R.id.action_slip_printer:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                    /*    if (BaseActivity.getUrl().equals("https://www6.air-logi.com/service")) {
                            c.startActivity(new Intent(c, One_to_One_SlipPrinter.class));
                        }else{*/
                        c.startActivity(new Intent(c, SlipPrinterActivity.class));
                        // }

                    }
                }, 200);
                break;

            case R.id.action_daimaru_slip_printer:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, One_to_One_SlipPrinter.class));
                    }
                }, 200);
                break;


            case R.id.action_machine_printer:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, PrinterMachineActivity.class));
                    }
                }, 200);
                break;

            case R.id.action_total_arrival:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, TotalArrivalActivity.class));
                    }
                }, 200);

                break;

            case R.id.action_batch_picking_new:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, NewBatchPickingOrder.class));
                        // c.startActivity(new Intent(c, NewtaspickDeliverynotesActivity.class));
                    }
                }, 200);

                break;

            case R.id.action_dm_batch_picking:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, DmBatchListActivity.class));
                    }
                }, 200);

                break;

            case R.id.action_tas_picking_new:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, Newtaspicking.class));
                    }
                }, 200);

                break;

            case R.id.action_newreturn_stock:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, NewReturnStockActivity.class));
                    }
                }, 200);

                break;

            case R.id.action_newmachine_printer:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        c.startActivity(new Intent(c, NewprinterScreenActivity.class));
                    }
                }, 200);

                break;

            case R.id.action_logout:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        pDialog_logout = new SweetAlertDialog(c, SweetAlertDialog.WARNING_TYPE);
                        pDialog_logout.setCancelable(true);
                        pDialog_logout.setTitleText("Logout?");
                        pDialog_logout.setContentText("Do you want to logout?");
                        pDialog_logout.setConfirmText("Yes");
                        pDialog_logout.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                pDialog_logout.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent = new Intent(c, DemoLoginActivity.class);
                                        intent.putExtra("ACTION", "logout");
                                        c.startActivity(intent);
                                        ((Activity) c).finish();

                       /* Globals.getterList = new ArrayList<>();
                        Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
                        Globals.getterList.add(new ParamsGetter("admin_id",spDomain.getString("admin_id", null)));

                        new MainAsyncTask(c, Globals.Webservice.Logout, 1, SlideMenu.this, "Form", Globals.getterList,true).execute();
*/
                                        pDialog_logout.dismissWithAnimation();

                                    }

                                }, 1000);

                            }
                        });
                        pDialog_logout.setCancelText("No");
                        pDialog_logout.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {

                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                pDialog_logout.dismissWithAnimation();
                            }
                        });

                        pDialog_logout.show();

                    }
                }, 200);

                break;

            default:
                menu.showContent();
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //c.startActivity(new Intent(c, SettingsActivity.class));
                    }
                }, 200);
                break;
        }
    }

    public static String getMboxId() {
        return mboxId;
    }

    public static void setMboxId(String mbox) {
        mboxId = mbox;
    }

    @Override
    public void onPostSuccess(String result, int flag, boolean isSucess) {

    }

    @Override
    public void onPostError(int flag) {

    }
}

