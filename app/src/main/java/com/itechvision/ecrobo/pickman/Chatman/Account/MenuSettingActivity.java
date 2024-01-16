package com.itechvision.ecrobo.pickman.Chatman.Account;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.Settings.SettingData;
import com.itechvision.ecrobo.pickman.Models.Settings.SettingRequest;
import com.itechvision.ecrobo.pickman.Models.Settings.SettingResult;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.SharedPrefrences;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

import static com.itechvision.ecrobo.pickman.Chatman.Account.UpdateActivity.DOMAINPREFERENCE;

public class MenuSettingActivity extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener , DataManager.GetSettingscall,DataManager.Postsettingcall{

    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.sdBatch)CheckBox sdBatch;
    @BindView(R.id.dBatch)CheckBox dBatch;
    @BindView(R.id.temporaryLocation)CheckBox temporaryLocation;
    @BindView(R.id.truckBatch)CheckBox truckBatch;
    @BindView(R.id.shipping)CheckBox shipping;
    @BindView(R.id.koguchi)CheckBox koguchi;
    @BindView(R.id.boxSize)CheckBox boxSize;
    @BindView(R.id.barcodeprinter)CheckBox printer;
    @BindView(R.id.stockChange)CheckBox StockChange;
    @BindView(R.id.packSet)CheckBox packSet;
    @BindView(R.id.newPackSet)CheckBox newPackSet;
    @BindView(R.id.rfarrivalchk)CheckBox Rfarrival;
    @BindView(R.id.rfreturnchk)CheckBox Rfreturn;
    @BindView(R.id.slipPrint)CheckBox SlipPrinter;
    @BindView(R.id.rfPicking)CheckBox rfPicking;
    @BindView(R.id.machineprint)CheckBox machine;
    @BindView(R.id.barcodeSlipPrinter)CheckBox barcodeSlipPrinter;
    @BindView(R.id.rfWriter)CheckBox rfWriter;
    @BindView(R.id.ecms)CheckBox ecms;
    @BindView(R.id.pdBoxSize)CheckBox pdBoxSize;
    @BindView(R.id.pickList)CheckBox picklist;
    @BindView(R.id.totalarrival)CheckBox totalarrival;
    @BindView(R.id.newBatchPick)CheckBox newBatchpick;
    @BindView(R.id.newTasPick)CheckBox newTasPick;
    @BindView(R.id.newPrinterSelect)CheckBox newPrinterSelect;
    @BindView(R.id.newreturnStock)CheckBox newreturnStock;
    @BindView(R.id.newdolarrival)CheckBox newdolarrival;
    @BindView(R.id.returnStockBarcode)CheckBox returnStockBarcode;
    @BindView(R.id.mitsukoshi_arrival_chk)CheckBox mitsukoshi_arrival_chk;
    @BindView(R.id.koguchiPrint_chk)CheckBox koguchiPrint;
    @BindView(R.id.invoice_printing)CheckBox invoice_printing;
    @BindView(R.id.diamaru_batch_picking)CheckBox diamaru_batch_picking;
    @BindView(R.id.simple_shipping_chk)CheckBox simple_shipping;
    @BindView(R.id.tshipping) CheckBox tshipping;
    @BindView(R.id.customer_arrival) CheckBox customer_arrival;
    @BindView(R.id.new_move_stock_check)CheckBox new_move_stock_check;
    @BindView(R.id.invoice_shipping_check)CheckBox invoice_shipping_check;
    @BindView(R.id.multiuser_shipping_check)CheckBox multiuser_shipping_check;
    @BindView(R.id.dm_batch_picking)CheckBox dm_batch_pick_check;
    @BindView(R.id.new_invoice_print)CheckBox new_invoice_print;
    @BindView(R.id.iris_arrival)CheckBox iris_arrival_check;
    @BindView(R.id.iris_picking)CheckBox iris_picking_check;

    @BindView(R.id.save) Button save;

    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPrefs";
    String SdBatchCheck,DBatchCheck,TemporaryLocationCheck,TruckBatchCheck,ShippingCheck ,KoguchiCheck  ,BoxSizeCheck   , PrinterCheck, tshippingCheck, invoice_print
            ,StockChangeCheck , PackSetCheck  ,NewPackSetCheck , RfNewArrivalCheck , RfNewReturnCheck , SlipPrinterCheck ,RfPickingCheck  , MachinePrinterCheck, customerArrival, multiuser_shipping,newMoveStock, invoice_shipping
            ,BarcodeSlipPrinter  ,RfWriter, Ecms ,PdBoxSize  ,Picklist,  Totalarrival , Newbatchpick,NewTasPick, NewreturnStock , NewPrinterSelect,adminid,NewDolArrival, ReturnStockBarcode, koguchi_Print, mitsukoshi_arrival,invoiceprinting, diamaruBatch,simpleShip, dmBatch,iris_arrival, iris_picking;

    DataManager manager ;
    progresBar progress ;
    DataManager.GetSettingscall getcall;
    ECRApplication app=new ECRApplication();
    SharedPreferences spDomain;

    final Handler handler = new Handler();
    String TAG = MenuSettingActivity.class.getSimpleName();
    SettingData data ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_setting);

        ButterKnife.bind(this);
        getcall= this ;

        Log.d(TAG,"On Create");

        manager = new  DataManager();
        progress = new progresBar(this);

        getIDs();
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminid = spDomain.getString("admin_id", null);

        if (SharedPrefrences.get_sdBatchCheck(this).equalsIgnoreCase("1")){
            sdBatch.setChecked(true);
        }else{
            sdBatch.setChecked(false);
        }

        if (SharedPrefrences.getW6BatchPicking(this).equalsIgnoreCase("1")){
            diamaru_batch_picking.setChecked(true);
        }else{
            diamaru_batch_picking.setChecked(false);
        }

        if (SharedPrefrences.getSimpleShipping(this).equalsIgnoreCase("1")){
            simple_shipping.setChecked(true);
        }else{
            simple_shipping.setChecked(false);
        }


        if (SharedPrefrences.get_sdBatchCheck(this).equalsIgnoreCase("1")){
            sdBatch.setChecked(true);
        }else{
            sdBatch.setChecked(false);
        }

        if (SharedPrefrences.get_dBatchCheck(this).equalsIgnoreCase("1")){
            dBatch.setChecked(true);
        }else{
            dBatch.setChecked(false);
        }

        if (SharedPrefrences.get_temporaryLocationCheck(this).equalsIgnoreCase("1")){
            temporaryLocation.setChecked(true);
        }else{
            temporaryLocation.setChecked(false);
        }

        if (SharedPrefrences.get_tshippingCheck(this).equalsIgnoreCase("1")){
            tshipping.setChecked(true);
        }else{
            tshipping.setChecked(false);
        }

        if (SharedPrefrences.getCustomerArrival(this).equalsIgnoreCase("1")){
            customer_arrival.setChecked(true);
        }else{
            customer_arrival.setChecked(false);
        }

        if (SharedPrefrences.getIrisArrival(this).equalsIgnoreCase("1")){
            iris_arrival_check.setChecked(true);
        }else{
            iris_arrival_check.setChecked(false);
        }

        if (SharedPrefrences.getIrisPicking(this).equalsIgnoreCase("1")){
            iris_picking_check.setChecked(true);
        }else{
            iris_picking_check.setChecked(false);
        }

        if (SharedPrefrences.get_truckBatchCheck(this).equalsIgnoreCase("1")){
            truckBatch.setChecked(true);
        }else{
            truckBatch.setChecked(false);
        }


        if (SharedPrefrences.get_shippingCheck(this).equalsIgnoreCase("1")){
            shipping.setChecked(true);
        }else{
            shipping.setChecked(false);
        }

        if (SharedPrefrences.get_koguchiCheck(this).equalsIgnoreCase("1")){
            koguchi.setChecked(true);
        }else{
            koguchi.setChecked(false);
        }

        if (SharedPrefrences.get_boxSizeCheck(this).equalsIgnoreCase("1")){
            boxSize.setChecked(true);
        }else{
            boxSize.setChecked(false);
        }


        if (SharedPrefrences.get_printerCheck(this).equalsIgnoreCase("1")){
            printer.setChecked(true);
        }else{
            printer.setChecked(false);
        }

        if (SharedPrefrences.get_StockChangeCheck(this).equalsIgnoreCase("1")){
            StockChange.setChecked(true);
        }else{
            StockChange.setChecked(false);
        }

        if (SharedPrefrences.get_packSetCheck(this).equalsIgnoreCase("1")){
            packSet.setChecked(true);
        }else{
            packSet.setChecked(false);
        }


        if (SharedPrefrences.get_newPackSetCheck(this).equalsIgnoreCase("1")){
            newPackSet.setChecked(true);
        }else{
            newPackSet.setChecked(false);
        }


        if (SharedPrefrences.get_rfNewArrivalCheck(this).equalsIgnoreCase("1")){
            Rfarrival.setChecked(true);
        }else{
            Rfarrival.setChecked(false);
        }


        if (SharedPrefrences.get_rfNewReturnCheck(this).equalsIgnoreCase("1")){
            Rfreturn.setChecked(true);
        }else{
            Rfreturn.setChecked(false);
        }

        if (SharedPrefrences.get_slipPrinterCheck(this).equalsIgnoreCase("1")){
            SlipPrinter.setChecked(true);
        }else{
            SlipPrinter.setChecked(false);
        }


        if (SharedPrefrences.get_rfPickingCheck(this).equalsIgnoreCase("1")){
            rfPicking.setChecked(true);
        }else{
            rfPicking.setChecked(false);
        }



        if (SharedPrefrences.get_machinePrinterCheck(this).equalsIgnoreCase("1")){
            machine.setChecked(true);
        }else{
            machine.setChecked(false);
        }



        if (SharedPrefrences.get_barcodeSlipPrinter(this).equalsIgnoreCase("1")){
            barcodeSlipPrinter.setChecked(true);
        }else{
            barcodeSlipPrinter.setChecked(false);
        }



        if (SharedPrefrences.get_rfWriter(this).equalsIgnoreCase("1")){
            rfWriter.setChecked(true);
        }else{
            rfWriter.setChecked(false);
        }

        if (SharedPrefrences.get_ecms(this).equalsIgnoreCase("1")){
            ecms.setChecked(true);
        }else{
            ecms.setChecked(false);
        }


        if (SharedPrefrences.get_pdBoxSize(this).equalsIgnoreCase("1")){
            pdBoxSize.setChecked(true);
        }else{
            pdBoxSize.setChecked(false);
        }


        if (SharedPrefrences.get_picklist(this).equalsIgnoreCase("1")){
            picklist.setChecked(true);
        }else{
            picklist.setChecked(false);
        }

        if (SharedPrefrences.get_totalarrival(this).equalsIgnoreCase("1")){
            totalarrival.setChecked(true);
        }else{
            totalarrival.setChecked(false);
        }


        if (SharedPrefrences.get_newbatchpick(this).equalsIgnoreCase("1")){
            newBatchpick.setChecked(true);
        }else{
            newBatchpick.setChecked(false);
        }


        if (SharedPrefrences.get_newTasPick(this).equalsIgnoreCase("1")){
            newTasPick.setChecked(true);
        }else{
            newTasPick.setChecked(false);
        }

        if (SharedPrefrences.get_newdolArrival(this).equalsIgnoreCase("1")){
            newdolarrival.setChecked(true);
        }else{
            newdolarrival.setChecked(false);
        }



        if (SharedPrefrences.get_newreturnStock(this).equalsIgnoreCase("1")){
            newreturnStock.setChecked(true);
        }else{
            newreturnStock.setChecked(false);
        }

        if (SharedPrefrences.get_newPrinterSelect(this).equalsIgnoreCase("1")){
            newPrinterSelect.setChecked(true);
        }else{
            newPrinterSelect.setChecked(false);
        }

        if (SharedPrefrences.get_returnStockBarcode(this).equalsIgnoreCase("1")){
            returnStockBarcode.setChecked(true);
        }else{
            returnStockBarcode.setChecked(false);
        }

        if (SharedPrefrences.get_Mitsukoshi_arrival(this).equalsIgnoreCase("1")){
            mitsukoshi_arrival_chk.setChecked(true);
        }else{
            mitsukoshi_arrival_chk.setChecked(false);
        }

        if (SharedPrefrences.get_koguchiPrint(this).equalsIgnoreCase("1")) {
            koguchiPrint.setChecked(true);
        }
        else {
            koguchiPrint.setChecked(false);
        }

        if (SharedPrefrences.get_invoice_printing(this).equalsIgnoreCase("1")) {
            invoice_printing.setChecked(true);
        }
        else {
            invoice_printing.setChecked(false);
        }


        if (SharedPrefrences.get_new_move_stock(this).equalsIgnoreCase("1")) {
            new_move_stock_check.setChecked(true);
        }
        else {
            new_move_stock_check.setChecked(false);
        }

        if (SharedPrefrences.get_invoice_shipping(this).equalsIgnoreCase("1")) {
            invoice_shipping_check.setChecked(true);
        }
        else {
            invoice_shipping_check.setChecked(false);
        }

        if (SharedPrefrences.get_multiuser_shipping(this).equalsIgnoreCase("1")) {
            multiuser_shipping_check.setChecked(true);
        }
        else {
            multiuser_shipping_check.setChecked(false);
        }

        if (SharedPrefrences.getDmBatchPicking(this).equalsIgnoreCase("1")) {
            dm_batch_pick_check.setChecked(true);
        }
        else {
            dm_batch_pick_check.setChecked(false);
        }

        if (SharedPrefrences.getNewInvoicePrint(this).equalsIgnoreCase("1")) {
            new_invoice_print.setChecked(true);
        }
        else {
            new_invoice_print.setChecked(false);
        }



        sdBatch.setOnCheckedChangeListener(this);
        dBatch.setOnCheckedChangeListener(this);
        temporaryLocation.setOnCheckedChangeListener(this);
        truckBatch.setOnCheckedChangeListener(this);
        shipping.setOnCheckedChangeListener(this);
        koguchi.setOnCheckedChangeListener(this);
        boxSize.setOnCheckedChangeListener(this);
        printer.setOnCheckedChangeListener(this);
        StockChange.setOnCheckedChangeListener(this);
        packSet.setOnCheckedChangeListener(this);
        newPackSet.setOnCheckedChangeListener(this);
        machine.setOnCheckedChangeListener(this);
        SlipPrinter.setOnCheckedChangeListener(this);
        rfPicking.setOnCheckedChangeListener(this);
        Rfreturn.setOnCheckedChangeListener(this);
        Rfarrival.setOnCheckedChangeListener(this);
        barcodeSlipPrinter.setOnCheckedChangeListener(this);
        rfWriter.setOnCheckedChangeListener(this);
        ecms.setOnCheckedChangeListener(this);
        pdBoxSize.setOnCheckedChangeListener(this);
        picklist.setOnCheckedChangeListener(this);
        totalarrival.setOnCheckedChangeListener(this);
        newBatchpick.setOnCheckedChangeListener(this);
        newTasPick.setOnCheckedChangeListener(this);
        newPrinterSelect.setOnCheckedChangeListener(this);
        newreturnStock.setOnCheckedChangeListener(this);
        newdolarrival.setOnCheckedChangeListener(this);
        returnStockBarcode.setOnCheckedChangeListener(this);
        koguchiPrint.setOnCheckedChangeListener(this);
        mitsukoshi_arrival_chk.setOnCheckedChangeListener(this);
        invoice_printing.setOnCheckedChangeListener(this);
        diamaru_batch_picking.setOnCheckedChangeListener(this);
        simple_shipping.setOnCheckedChangeListener(this);
        tshipping.setOnCheckedChangeListener(this);
        customer_arrival.setOnCheckedChangeListener(this);
        iris_arrival_check.setOnCheckedChangeListener(this);
        iris_picking_check.setOnCheckedChangeListener(this);

        new_move_stock_check.setOnCheckedChangeListener(this);
        invoice_shipping_check.setOnCheckedChangeListener(this);
        multiuser_shipping_check.setOnCheckedChangeListener(this);
        dm_batch_pick_check.setOnCheckedChangeListener(this);
        new_invoice_print.setOnCheckedChangeListener(this);

        //   progress.Show();
        //   manager.GetSettings(adminid,app.getSerial(),"",getcall);


        if (BaseActivity.getUrl().equals("https://www6.air-logi.com/service")) {
            diamaru_batch_picking.setVisibility(View.VISIBLE);
        } else{
            diamaru_batch_picking.setVisibility(View.INVISIBLE);
        }

    }

    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "メニュー設定", " ",
                0, false,false,false );

        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnBlue.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
            default:
                break;
        }
    }


    @OnClick(R.id.save) void setclick(){
        progress.Show();
        SettingRequest req = new SettingRequest(adminid, app.getSerial(), "", "", "", "", ""
                , "", "", "", "", "", "", "", "", "",
                "", "", "", SdBatchCheck, DBatchCheck, TemporaryLocationCheck, TruckBatchCheck, ShippingCheck, KoguchiCheck, BoxSizeCheck, PrinterCheck
                , StockChangeCheck, PackSetCheck, NewPackSetCheck, RfNewArrivalCheck, RfNewReturnCheck, SlipPrinterCheck, RfPickingCheck, MachinePrinterCheck
                , BarcodeSlipPrinter, RfWriter, Ecms, PdBoxSize, Picklist, Totalarrival, Newbatchpick, NewTasPick, NewreturnStock, NewPrinterSelect, "", "", NewDolArrival, "", "", "", "", ReturnStockBarcode, koguchi_Print, mitsukoshi_arrival, invoiceprinting, diamaruBatch, simpleShip, tshippingCheck, customerArrival, newMoveStock, multiuser_shipping, invoice_shipping, dmBatch, invoice_print, "", "", iris_arrival, iris_picking, "");
        manager.PostSettingStatus(req,this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (buttonView.getId()) {

            case R.id.sdBatch:
                if (sdBatch.isChecked()){
                    editor.putBoolean("sdBatchCheck",true);
                    SdBatchCheck ="1";
                }
                else{
                    editor.putBoolean("sdBatchCheck",false);
                    SdBatchCheck ="0";
                }
                break;

            case R.id.dBatch:
                if (dBatch.isChecked())
                {
                    editor.putBoolean("dBatchCheck",true);
                    DBatchCheck ="1";
                }
                else
                {
                    editor.putBoolean("dBatchCheck",false);
                    DBatchCheck ="0";
                }
                break;

            case R.id.temporaryLocation:
                if (temporaryLocation.isChecked()){
                    editor.putBoolean("temporaryLocationCheck",true);
                    TemporaryLocationCheck ="1";
                }
                else{
                    editor.putBoolean("temporaryLocationCheck",false);
                    TemporaryLocationCheck ="0";
                }
                break;

            case R.id.truckBatch:
                if (truckBatch.isChecked()){
                    editor.putBoolean("truckBatchCheck",true);
                    TruckBatchCheck = "1";
                }
                else
                {
                    editor.putBoolean("truckBatchCheck",false);
                    TruckBatchCheck = "0";
                }
                break;

            case R.id.shipping:
                if (shipping.isChecked()){
                    editor.putBoolean("shippingCheck",true);
                    ShippingCheck = "1";
                }
                else
                {
                    editor.putBoolean("shippingCheck",false);
                    ShippingCheck = "0";
                }
                break;

            case R.id.koguchi:
                if (koguchi.isChecked()){
                    editor.putBoolean("koguchiCheck",true);
                    KoguchiCheck = "1";
                }
                else {
                    editor.putBoolean("koguchiCheck", false);
                    KoguchiCheck = "0";
                }
                break;

            case R.id.boxSize:
                if (boxSize.isChecked())
                {
                    editor.putBoolean("boxSizeCheck",true);
                    BoxSizeCheck = "1";
                }
                else
                {
                    editor.putBoolean("boxSizeCheck",false);
                    BoxSizeCheck = "0";
                }

                break;

            case R.id.barcodeprinter:
                if (printer.isChecked())
                {
                    editor.putBoolean("printerCheck",true);
                    PrinterCheck = "1";
                }
                else{
                    editor.putBoolean("printerCheck",false);
                    PrinterCheck = "0";
                }
                break;

            case R.id.stockChange:
                if (StockChange.isChecked()){
                    editor.putBoolean("StockChangeCheck",true);
                    StockChangeCheck = "1";
                }
                else
                {
                    editor.putBoolean("StockChangeCheck",false);
                    StockChangeCheck = "0";
                }
                break;

            case R.id.packSet:
                if (packSet.isChecked()){
                    editor.putBoolean("packSetCheck",true);
                    PackSetCheck = "1";
                }
                else
                {
                    editor.putBoolean("packSetCheck",false);
                    PackSetCheck = "0";
                }
                break;

            case R.id.newPackSet:
                if (newPackSet.isChecked()){
                    editor.putBoolean("newPackSetCheck",true);
                    NewPackSetCheck = "1";
                }
                else
                {
                    editor.putBoolean("newPackSetCheck",false);
                    NewPackSetCheck = "0";
                }
                break;
            case R.id.rfarrivalchk:
                if (Rfarrival.isChecked()){
                    editor.putBoolean("rfNewArrivalCheck",true);
                    RfNewArrivalCheck ="1";
                }
                else
                {
                    editor.putBoolean("rfNewArrivalCheck",false);
                    RfNewArrivalCheck ="0";
                }
                break;
            case R.id.rfreturnchk:
                if (Rfreturn.isChecked()){
                    editor.putBoolean("rfNewReturnCheck",true);
                    RfNewReturnCheck = "1";
                }
                else
                {
                    editor.putBoolean("rfNewReturnCheck",false);
                    RfNewReturnCheck = "0";
                }
                break;
            case R.id.slipPrint:
                if (SlipPrinter.isChecked()){
                    editor.putBoolean("slipPrinterCheck",true);
                    SlipPrinterCheck="1";
                }
                else
                {
                    editor.putBoolean("slipPrinterCheck",false);
                    SlipPrinterCheck="0";
                }
                break;

            case R.id.machineprint:
                if (machine.isChecked()){
                    editor.putBoolean("machinePrinterCheck",true);
                    MachinePrinterCheck = "1";
                }
                else
                {
                    editor.putBoolean("machinePrinterCheck",false);
                    MachinePrinterCheck = "0";
                }
                break;

            case R.id.rfPicking:
                if (rfPicking.isChecked()){
                    editor.putBoolean("rfPickingCheck",true);
                    RfPickingCheck="1";
                }
                else
                {
                    editor.putBoolean("rfPickingCheck",false);
                    RfPickingCheck="0";
                }
                break;

            case R.id.barcodeSlipPrinter:
                if (barcodeSlipPrinter.isChecked()){
                    editor.putBoolean("barcodeSlipPrinter",true);
                    BarcodeSlipPrinter="1";
                }
                else
                {
                    editor.putBoolean("barcodeSlipPrinter",false);
                    BarcodeSlipPrinter="0";
                }
                break;

            case R.id.rfWriter:
                if (rfWriter.isChecked()){
                    editor.putBoolean("rfWriter",true);
                    RfWriter="1";
                }
                else
                {
                    editor.putBoolean("rfWriter",false);
                    RfWriter="0";
                }
                break;
            case R.id.ecms:
                if (ecms.isChecked()){
                    editor.putBoolean("ecms",true);
                    Ecms="1";
                }
                else
                {
                    editor.putBoolean("ecms",false);
                    Ecms="0";
                }
                break;

            case R.id.pdBoxSize:
                if(pdBoxSize.isChecked()){
                    editor.putBoolean("pdBoxSize",true);
                    PdBoxSize="1";
                }
                else {
                    editor.putBoolean("pdBoxSize",false);
                    PdBoxSize="0";
                }
                break;
            case R.id.pickList:
                if (picklist.isChecked()){
                    editor.putBoolean("picklist",true);
                    Picklist="1";
                }

                else
                {
                    editor.putBoolean("picklist",false);
                    Picklist="0";
                }
                break;

            case R.id.totalarrival:
                if (totalarrival.isChecked()){
                    editor.putBoolean("totalarrival",true);
                    Totalarrival="1";
                }

                else
                {
                    editor.putBoolean("totalarrival",false);
                    Totalarrival="0";
                }
                break;

            case R.id.mitsukoshi_arrival_chk:
                if (mitsukoshi_arrival_chk.isChecked()){
                    editor.putBoolean("mitsukoshi_arrival",true);
                    mitsukoshi_arrival="1";
                }

                else
                {
                    editor.putBoolean("mitsukoshi_arrival",false);
                    mitsukoshi_arrival="0";
                }
                break;

            case R.id.newBatchPick:
                if (newBatchpick.isChecked()){
                    editor.putBoolean("newbatchpick",true);
                    Newbatchpick="1";
                }

                else
                {
                    editor.putBoolean("newbatchpick",false);
                    Newbatchpick="0";
                }
                break;

            case R.id.newTasPick:
                if (newTasPick.isChecked()){
                    editor.putBoolean("newTasPick",true);
                    NewTasPick="1";
                }

                else
                {
                    editor.putBoolean("newTasPick",false);
                    NewTasPick="0";
                }
                break;
            case R.id.newdolarrival:
                if (newdolarrival.isChecked()){
                    editor.putBoolean("newDolarrival",true);
                    NewDolArrival="1";
                }

                else
                {
                    editor.putBoolean("newDolarrival",false);
                    NewDolArrival="0";
                }
                break;


            case R.id.newPrinterSelect:
                if (newPrinterSelect.isChecked()){
                    editor.putBoolean("newPrinterSelect",true);
                    NewPrinterSelect="1";
                }

                else
                {
                    editor.putBoolean("newPrinterSelect",false);
                    NewPrinterSelect="0";
                }
                break;

            case R.id.tshipping:
                if (tshipping.isChecked()){
                    editor.putBoolean("tshippingCheck",true);
                    tshippingCheck="1";
                }

                else
                {
                    editor.putBoolean("tshippingCheck",false);
                    tshippingCheck="0";
                }
                break;
            case R.id.newreturnStock:
                if (newreturnStock.isChecked()){
                    editor.putBoolean("newreturnStock",true);
                    NewreturnStock="1";
                }

                else
                {
                    editor.putBoolean("newreturnStock",false);
                    NewreturnStock="0";
                }
                break;

            case R.id.returnStockBarcode:
                if (returnStockBarcode.isChecked()){
                    editor.putBoolean("returnStockBarcode",true);
                    ReturnStockBarcode="1";
                }

                else
                {
                    editor.putBoolean("returnStockBarcode",false);
                    ReturnStockBarcode="0";
                }
                break;

            case R.id.koguchiPrint_chk:
                if (koguchiPrint.isChecked()){
                    SharedPrefrences.set_koguchiPrint(this,"1");
                    koguchi_Print="1";
                } else {
                    SharedPrefrences.set_koguchiPrint(this,"0");
                    koguchi_Print="0";
                }
                break;

            case R.id.invoice_printing:
                if (invoice_printing.isChecked()){
                    SharedPrefrences.set_invoice_printing(this,"1");
                    invoiceprinting="1";
                } else {
                    SharedPrefrences.set_invoice_printing(this,"0");
                    invoiceprinting="0";
                }
                break;

            case R.id.diamaru_batch_picking:
                if (diamaru_batch_picking.isChecked())
                {
                    SharedPrefrences.setW6BatchPicking(this,"1");
                    diamaruBatch = "1";
                }
                else{
                    SharedPrefrences.setW6BatchPicking(this,"0");
                    diamaruBatch = "0";
                }
                break;

            case R.id.simple_shipping_chk:
                if (simple_shipping.isChecked())
                {
                    SharedPrefrences.setSimpleShipping(this,"1");
                    simpleShip = "1";
                }
                else{
                    SharedPrefrences.setSimpleShipping(this,"0");
                    simpleShip = "0";
                }
                break;
            case R.id.iris_arrival:
                if (iris_arrival_check.isChecked())
                {
                    SharedPrefrences.setIrisArrival(this,"1");
                    iris_arrival = "1";
                }
                else{
                    SharedPrefrences.setIrisArrival(this,"0");
                    iris_arrival = "0";
                }
                break;

            case R.id.iris_picking:
                if (iris_picking_check.isChecked())
                {
                    SharedPrefrences.setIrisPicking(this,"1");
                    iris_picking = "1";
                }
                else{
                    SharedPrefrences.setIrisPicking(this,"0");
                    iris_picking = "0";
                }
                break;
            case R.id.customer_arrival:
                if (customer_arrival.isChecked())
                {
                    editor.putBoolean("CustomerArrival",true);
                    customerArrival = "1";
                }
                else{
                    editor.putBoolean("CustomerArrival",false);
                    customerArrival = "0";
                }
                break;


            case R.id.new_move_stock_check:
                if (new_move_stock_check.isChecked())
                {
                    editor.putBoolean("new_move_stock",true);
                    newMoveStock = "1";
                }
                else{
                    editor.putBoolean("new_move_stock",false);
                    newMoveStock = "0";
                }
                break;

            case R.id.invoice_shipping_check:
                if (invoice_shipping_check.isChecked())
                {
                    editor.putBoolean("invoice_shipping",true);
                    invoice_shipping = "1";
                }
                else{
                    editor.putBoolean("invoice_shipping",false);
                    invoice_shipping = "0";
                }
                break;
            case R.id.multiuser_shipping_check:
                if (multiuser_shipping_check.isChecked())
                {
                    editor.putBoolean("new_move_stock",true);
                    multiuser_shipping = "1";
                }
                else{
                    editor.putBoolean("new_move_stock",false);
                    multiuser_shipping = "0";
                }
                break;

            case R.id.dm_batch_picking:
                if (dm_batch_pick_check.isChecked())
                {
                    editor.putBoolean("DmBatchPicking",true);
                    dmBatch = "1";
                }
                else{
                    editor.putBoolean("DmBatchPicking",false);
                    dmBatch = "0";
                }
                break;
            case R.id.new_invoice_print:
                if (new_invoice_print.isChecked())
                {
                    editor.putBoolean("NewInvoicePrint",true);
                    invoice_print = "1";
                }
                else{
                    editor.putBoolean("NewInvoicePrint",false);
                    invoice_print = "0";
                }
                break;
        }
        editor.commit();
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);
    }
    @Override
    public void nextProcess() {

    }

    @Override
    public void inputedEvent(String buff) {

    }

    @Override
    public void clearEvent() {

    }

    @Override
    public void allclearEvent() {

    }

    @Override
    public void skipEvent() {

    }

    @Override
    public void keypressEvent(String key, String buff) {

    }

    @Override
    public void scanedEvent(String barcode) {

    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {

    }

    private boolean _getboolean (String s,boolean b)
    {
        boolean rt = sharedPreferences.getBoolean(s, b);
        return rt;
    }

    @Override
    public void onSucess(int status, SettingResult message) throws JsonIOException {

        if (message.getCode().equalsIgnoreCase("200")) {
            progress.Dismiss();
            try {
                //Menu Settings
                if (message.getResult().getSdBatchCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("sdBatchCheck",true);
                    SlideMenu.sdBatch.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_sdBatchCheck(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("sdBatchCheck",false);
                    SlideMenu.sdBatch.setVisibility(View.GONE);
                    SharedPrefrences.set_sdBatchCheck(this,"0");
                }

                // dBatch
                if (message.getResult().getdBatchCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("dBatchCheck",true);
                    SlideMenu.dBatch.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_dBatchCheck(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("dBatchCheck",false);
                    SlideMenu.dBatch.setVisibility(View.GONE);
                    SharedPrefrences.set_dBatchCheck(this,"0");
                }


                // temporaryLocation
                if (message.getResult().getTemporaryLocationCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("temporaryLocationCheck",true);
                    slidemenu.temporaryLocation.setVisibility(View.VISIBLE);

                    SharedPrefrences.set_temporaryLocationCheck(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("temporaryLocationCheck",false);
                    slidemenu.temporaryLocation.setVisibility(View.GONE);
                    SharedPrefrences.set_temporaryLocationCheck(this,"0");
                }

                // truckBatchLL
                if (message.getResult().getTruckBatchCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("truckBatchCheck",true);
                    slidemenu.truckBatchLL.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_truckBatchCheck(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("truckBatchCheck",false);
                    slidemenu.truckBatchLL.setVisibility(View.GONE);

                    SharedPrefrences.set_truckBatchCheck(this,"0");
                }

                //  newShipGroupLL
                if (message.getResult().getShippingCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("shippingCheck",true);
                    slidemenu.newShipGroupLL.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_shippingCheck(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("shippingCheck",false);
                    slidemenu.newShipGroupLL.setVisibility(View.GONE);
                    SharedPrefrences.set_shippingCheck(this,"0");
                }

                // koguchi
                if (message.getResult().getKoguchiCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("koguchiCheck",true);
                    slidemenu.koguchi.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_koguchiCheck(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("koguchiCheck",false);
                    slidemenu.koguchi.setVisibility(View.GONE);
                    SharedPrefrences.set_koguchiCheck(this,"0");
                }

                // boxSize
                if (message.getResult().getBoxSizeCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("boxSizeCheck",true);
                    slidemenu.boxSize.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_boxSizeCheck(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("boxSizeCheck",false);
                    slidemenu.boxSize.setVisibility(View.GONE);
                    SharedPrefrences.set_boxSizeCheck(this,"0");
                }


                //  printerCheck
                if (message.getResult().getPrinterCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("printerCheck",true);
                    slidemenu.barcodelabelprintLL.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_printerCheck(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("printerCheck",false);
                    slidemenu.barcodelabelprintLL.setVisibility(View.GONE);
                    SharedPrefrences.set_printerCheck(this,"0");
                }

                //    stockChange
                if (message.getResult().getStockChangeCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("StockChangeCheck",true);
                    slidemenu.stockChange.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_StockChangeCheck(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("StockChangeCheck",false);
                    slidemenu.stockChange.setVisibility(View.GONE);
                    SharedPrefrences.set_StockChangeCheck(this,"0");
                }

                //   packSetLL
                if (message.getResult().getPackSetCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("packSetCheck",true);
                    slidemenu.packSetLL.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_packSetCheck(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("packSetCheck",false);
                    slidemenu.packSetLL.setVisibility(View.GONE);
                    SharedPrefrences.set_packSetCheck(this,"0");
                }


                // newPackSetCheck
                if (message.getResult().getNewPackSetCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("newPackSetCheck",true);
                    slidemenu.newPackSetLL.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_newPackSetCheck(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("newPackSetCheck",false);
                    slidemenu.newPackSetLL.setVisibility(View.GONE);
                    SharedPrefrences.set_newPackSetCheck(this,"0");
                }

                //   NewArrivalCheck
                if (message.getResult().getRfNewArrivalCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("rfNewArrivalCheck",true);
                    slidemenu.rfarrivalLL.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_rfNewArrivalCheck(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("rfNewArrivalCheck",false);
                    slidemenu.rfarrivalLL.setVisibility(View.GONE);
                    SharedPrefrences.set_rfNewArrivalCheck(this,"0"); }

                // NewReturnCheck
                if (message.getResult().getRfNewReturnCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("rfNewReturnCheck",true);
                    slidemenu.rfReturnLL.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_rfNewReturnCheck(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("rfNewReturnCheck",false);
                    slidemenu.rfReturnLL.setVisibility(View.GONE);
                    SharedPrefrences.set_rfNewReturnCheck(this,"0");   }

                // slipPrinterCheck
                if (message.getResult().getSlipPrinterCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("slipPrinterCheck",true);
                    slidemenu.slipPRinterll.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_slipPrinterCheck(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("slipPrinterCheck",false);
                    slidemenu.slipPRinterll.setVisibility(View.GONE);
                    SharedPrefrences.set_slipPrinterCheck(this,"0");   }

                // machinePrinterCheck
                if (message.getResult().getMachinePrinterCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("machinePrinterCheck",true);
                    slidemenu.machineprinterLL.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_machinePrinterCheck(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("machinePrinterCheck",false);
                    slidemenu.machineprinterLL.setVisibility(View.GONE);
                    SharedPrefrences.set_machinePrinterCheck(this,"0");  }

                //  PickingCheck
                if (message.getResult().getRfPickingCheck().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("rfPickingCheck",true);
                    slidemenu.rfPickingLL.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_rfPickingCheck(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("rfPickingCheck",false);
                    slidemenu.rfPickingLL.setVisibility(View.GONE);
                    SharedPrefrences.set_rfPickingCheck(this,"0"); }

                //   barcodeSlipPrinter
                if (message.getResult().getBarcodeSlipPrinter().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("barcodeSlipPrinter",true);
                    slidemenu.printer.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_barcodeSlipPrinter(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("barcodeSlipPrinter",false);
                    slidemenu.printer.setVisibility(View.GONE);
                    SharedPrefrences.set_barcodeSlipPrinter(this,"0");    }

                // rfWriter
                if (message.getResult().getRfWriter().equalsIgnoreCase("1"))
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("rfWriter",true);
                    slidemenu.rfWriterLL.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_rfWriter(this,"1");
                }
                else
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("rfWriter",false);
                    slidemenu.rfWriterLL.setVisibility(View.GONE);
                    SharedPrefrences.set_rfWriter(this,"0");
                }

                //   ecms
                if (message.getResult().getEcms().equalsIgnoreCase("1"))
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("ecms",true);
                    slidemenu.ecmsWeightLL.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_ecms(this,"1");
                }
                else
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("ecms",false);
                    slidemenu.ecmsWeightLL.setVisibility(View.GONE);
                    SharedPrefrences.set_ecms(this,"0");
                }

                //   BoxSize
                if (message.getResult().getPdBoxSize().equalsIgnoreCase("1"))
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("pdBoxSize",true);
                    slidemenu.newboxsizeLL.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_pdBoxSize(this,"1");
                }
                else
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("pdBoxSize",false);
                    slidemenu.newboxsizeLL.setVisibility(View.GONE);
                    SharedPrefrences.set_pdBoxSize(this,"0");
                }

                // picklist
                if (message.getResult().getPicklist().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("picklist",true);
                    slidemenu.totalPickLL.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_picklist(this,"1"); }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("picklist",false);
                    slidemenu.totalPickLL.setVisibility(View.GONE);
                    SharedPrefrences.set_picklist(this,"0");
                }

                // totalarrival
                if (message.getResult().getTotalarrival().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("totalarrival",true);
                    slidemenu.totalarrivallist.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_totalarrival(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("totalarrival",false);
                    slidemenu.totalarrivallist.setVisibility(View.GONE);
                    SharedPrefrences.set_totalarrival(this,"0");
                }

                // mitsukoshi_arrival
                if (message.getResult().getUse_mitsukoshi().equalsIgnoreCase("1")){
                    slidemenu.llmitsukoshi_arrival.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_Mitsukoshi_arrival(this,"1");
                }else{
                    slidemenu.llmitsukoshi_arrival.setVisibility(View.GONE);
                    SharedPrefrences.set_Mitsukoshi_arrival(this,"0");
                }

                //  newbatchpick
                if (message.getResult().getNewbatchpick().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("newbatchpick",true);
                    slidemenu.llnewbatchpick.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_newbatchpick(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("newbatchpick",false);
                    slidemenu.llnewbatchpick.setVisibility(View.GONE);
                    SharedPrefrences.set_newbatchpick(this,"0");  }

                //  newTasPick
                if (message.getResult().getNewTasPick().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("newTasPick",true);
                    SlideMenu.llnewtaspick.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_newTasPick(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("newTasPick",false);
                    SlideMenu.llnewtaspick.setVisibility(View.GONE);
                    SharedPrefrences.set_newTasPick(this,"0");
                }

                //  newPrinterSelect
                if (message.getResult().getNewPrinterSelect().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("newPrinterSelect",true);
                    slidemenu.llnewMachinePrinter.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_newPrinterSelect(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("newPrinterSelect",false);
                    slidemenu.llnewMachinePrinter.setVisibility(View.GONE);
                    SharedPrefrences.set_newPrinterSelect(this,"0");
                }

                //   newreturnStock
                if (message.getResult().getNewreturnStock().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("newreturnStock",true);
                    slidemenu.llnewreturn_stock.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_newreturnStock(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("newreturnStock",false);
                    slidemenu.llnewreturn_stock.setVisibility(View.GONE);
                    SharedPrefrences.set_newreturnStock(this,"0");
                }

                //   returnStockBarcode
                if (message.getResult().getReturn_stock_barcode().equalsIgnoreCase("1") ){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("returnStockBarcode",true);
                    slidemenu.llreturnstock_barcode.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_returnStockBarcode(this,"1");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("returnStockBarcode",false);
                    slidemenu.llreturnstock_barcode.setVisibility(View.GONE);
                    SharedPrefrences.set_returnStockBarcode(this,"0");
                }

                //KoguchiPrintCSV
                if (message.getResult().getPrint_csv().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    SlideMenu.llkoguchi_print.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_koguchiPrint(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    SlideMenu.llkoguchi_print.setVisibility(View.GONE);
                    SharedPrefrences.set_koguchiPrint(this,"0");
                }


                //InvoiceShipping
                if (message.getResult().getMedia_shipping().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    SlideMenu.llinvoice_shipping.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_invoice_shipping(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    SlideMenu.llinvoice_shipping.setVisibility(View.GONE);
                    SharedPrefrences.set_invoice_shipping(this,"0");
                }


              /*  //NewDolArrival
                if (message.getResult().getUse_dolphin_arrival().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("newDolarrival",true);
                    SlideMenu.lldoltotalpick.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_newdolArrival(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("newDolarrival",false);
                    SlideMenu.lldoltotalpick.setVisibility(View.GONE);
                    SharedPrefrences.set_newdolArrival(this,"0");
                }

*/
                //invoice_printing
                if (message.getResult().getinvoice_printing().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("invoice_printing",true);
                    SlideMenu.llorderdetails.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_invoice_printing(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("invoice_printing",false);
                    SlideMenu.llorderdetails.setVisibility(View.GONE);
                    SharedPrefrences.set_invoice_printing(this,"0");
                }


                //mitsukoshi_arrival
                if (message.getResult().getUse_mitsukoshi().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("mitsukoshi_arrival",true);
                    SlideMenu.llmitsukoshi_arrival.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_Mitsukoshi_arrival(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("mitsukoshi_arrival",false);
                    SlideMenu.llmitsukoshi_arrival.setVisibility(View.GONE);
                    SharedPrefrences.set_Mitsukoshi_arrival(this,"0");
                }


                //tshipping
                if (message.getResult().getTshipping().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("tshippingCheck",true);
                    SlideMenu.llbox_cash_register.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_tshippingCheck(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("tshippingCheck",false);
                    SlideMenu.llbox_cash_register.setVisibility(View.GONE);
                    SharedPrefrences.set_tshippingCheck(this,"0");
                }

                //CustomerArrival
                if (message.getResult().getCustomer_arrival().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("CustomerArrival",true);
                    SlideMenu.llcustomerArrival.setVisibility(View.VISIBLE);
                    SharedPrefrences.setCustomerArrival(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("CustomerArrival",false);
                    SlideMenu.llcustomerArrival.setVisibility(View.GONE);
                    SharedPrefrences.setCustomerArrival(this,"0");
                }


                //New Move Stock
                if (message.getResult().getNew_move_stock_screen().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("new_move_stock",true);
                    SlideMenu.llnew_move_stock.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_new_move_stock(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("new_move_stock",false);
                    SlideMenu.llnew_move_stock.setVisibility(View.GONE);
                    SharedPrefrences.set_new_move_stock(this,"0");
                }
                //New Invoice Print
                if (message.getResult().getNew_invoice_print().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("NewInvoicePrint",true);
                    SlideMenu.llnew_invoice_print.setVisibility(View.VISIBLE);
                    SharedPrefrences.setNewInvoicePrint(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("NewInvoicePrint",false);
                    SlideMenu.llnew_invoice_print.setVisibility(View.GONE);
                    SharedPrefrences.setNewInvoicePrint(this,"0");
                }

                //Dm Batchpicking
                if (message.getResult().getPickman_batch().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("DmBatchPicking",true);
                    SlideMenu.lldm_batch_picking.setVisibility(View.VISIBLE);
                    SharedPrefrences.setDmBatchPicking(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("DmBatchPicking",false);
                    SlideMenu.lldm_batch_picking.setVisibility(View.GONE);
                    SharedPrefrences.setDmBatchPicking(this,"0");
                }

        /*        //Multiuser Shipping
                if (message.getResult().getMultiuser_shipping().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("multiuser_shipping",true);
                    SlideMenu.ll.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_multiuser_shipping(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("multiuser_shipping",false);
                    SlideMenu.llcustomerArrival.setVisibility(View.GONE);
                    SharedPrefrences.set_multiuser_shipping(this,"0");
                }*/


                //SimpleShipping
                if (message.getResult().getMenu_simple_shipping().equalsIgnoreCase("1")){
                    SlideMenu.llsimple_shipping.setVisibility(View.VISIBLE);
                    SharedPrefrences.setSimpleShipping(this,"1");

                }else{
                    SlideMenu.llsimple_shipping.setVisibility(View.GONE);
                    SharedPrefrences.setSimpleShipping(this,"0");
                }

                //IRIS ARRIVAL SCREEN
                if (message.getResult().getIris_arrival_screen().equalsIgnoreCase("1")){
                    SlideMenu.ll_iris_arrival.setVisibility(View.VISIBLE);
                    SharedPrefrences.setIrisArrival(this,"1");

                }else{
                    SlideMenu.ll_iris_arrival.setVisibility(View.GONE);
                    SharedPrefrences.setIrisArrival(this,"0");
                }

             /*   //Diamaru Batch
                if (*//*BaseActivity.getUrl().equals("https://www6.air-logi.com/service") &&*//* message.getResult().getMultipick_dm().equalsIgnoreCase("1")){
                    SlideMenu.lldaimarubatch.setVisibility(View.VISIBLE);
                    SharedPrefrences.setW6BatchPicking(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    SlideMenu.lldaimarubatch.setVisibility(View.GONE);
                    SharedPrefrences.setW6BatchPicking(this,"0");
                }*/

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSucess(int status, ResponseBody messshippiage) throws JsonIOException {
        progress.Dismiss();

        if (status==200){
            //  progress.Show();
            manager.GetSettings(adminid,app.getSerial(),"",getcall);

            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("成功!")
                    .setMessage("設定を変更しました。")
                    .setPositiveButton("閉じる", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })

                    .show();
        }
    }

    @Override
    public void onError(int status, ResponseBody error) {
        progress.Dismiss();
    }

    @Override
    public void onFaliure() {
        progress.Dismiss();
    }

    @Override
    public void onNetworkFailure() {
        progress.Dismiss();
    }
}