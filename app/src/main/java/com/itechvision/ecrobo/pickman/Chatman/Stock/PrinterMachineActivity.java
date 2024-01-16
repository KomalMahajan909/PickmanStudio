package com.itechvision.ecrobo.pickman.Chatman.Stock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.ListMachinePrinters;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class  PrinterMachineActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.barcode_printer) Spinner barcodeprinter;
    @BindView(R.id.barcodeSlipPrinter) Spinner barcodeslipprinter;
    @BindView(R.id.barcodeSlipmachine) Spinner barcodeslippmachine;
    @BindView(R.id.barcode_machine) Spinner barcodemachine;
    @BindView(R.id.integrated_printer) Spinner integratedprinter;
    @BindView(R.id.integrated_machine) Spinner integratedmachine;
    @BindView(R.id.invoice_printer) Spinner invoiceprinter;
    @BindView(R.id.invoice_machine) Spinner invoicemachine;
    @BindView(R.id.postpay_printer) Spinner postpayprinter;
    @BindView(R.id.postpay_machine) Spinner postpaymachine;
    @BindView(R.id.csv_machine) Spinner csvmachine;
    @BindView(R.id.csv_printer) Spinner csvprinter;
    @BindView(R.id.file_machine) Spinner filemachine;
    @BindView(R.id.file_printer) Spinner fileprinter;

    public boolean barcodecount , barcodeslipcount ,integratecount, invoicecount , postpaycount , csvcount , filecount ;



    String TAG= PrinterMachineActivity.class.getSimpleName();

    protected ArrayList<String> printerArray = new ArrayList<String>();
    protected ArrayList<Map<String,String>> data = new ArrayList<>();
    JsonHash machineData ;

    ArrayList<String> list;
    ArrayList<String> printerID;

    protected ArrayList<String> BarcodeprinterList = new ArrayList<String>();
    protected ArrayList<String> BarcodeprinterIDList = new ArrayList<String>();

    protected ArrayList<String> BarcodeSlipprinterList = new ArrayList<String>();
    protected ArrayList<String> BarcodeSlipprinterIDList = new ArrayList<String>();

    protected ArrayList<String> IntegratedprinterList = new ArrayList<String>();
    protected ArrayList<String> IntegratedprinterIDList = new ArrayList<String>();

    protected ArrayList<String> InvoiceprinterList = new ArrayList<String>();
    protected ArrayList<String> InvoiceprinterIDList = new ArrayList<String>();

    protected ArrayList<String> PostpayprinterList = new ArrayList<String>();
    protected ArrayList<String> PostpayprinterIDList = new ArrayList<String>();

    protected ArrayList<String> CsvprinterList = new ArrayList<String>();
    protected ArrayList<String> CsvprinterIDList = new ArrayList<String>();

    protected ArrayList<String> FileprinterList = new ArrayList<String>();
    protected ArrayList<String> FileprinterIDList = new ArrayList<String>();

    ECRApplication app=new ECRApplication();
    String adminID="";
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    private final int _singleItemRes = R.layout.printer_list_spinner_item;
    private final int _dropdownRes = R.layout.simple_spinner_dropdown_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer_machine);

        ButterKnife.bind(this);
        Log.d(TAG,"On Create ");
        getIDs();

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);

        Globals.getterList = new ArrayList<>();

        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));

        new MainAsyncTask(this, Globals.Webservice.listPrinters, 1, PrinterMachineActivity.this, "Form", Globals.getterList).execute();

     //  new MainAsyncTask(this, Globals.Webservice.listPrinters,true, 1, PrinterMachineActivity.this, "Form", Globals.getterList,false).execute();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @OnItemSelected (R.id.barcode_machine) void barcodeMachine(int position){
        if (printerArray != null && printerArray.size() > position) {
            if (position != 0) {

                BaseActivity.setbarcodeMachinePos(position);
                BarcodeprinterList = getPrintersList(printerArray.get(position));
                BarcodeprinterIDList = printerID;
                setspinner(R.id.barcode_printer,BarcodeprinterList);
                if(!barcodecount) {
                    barcodeprinter.setSelection(BaseActivity.getbarcodeselectedPrinterPOS());
                   barcodecount = true;
                }
                Log.e(TAG,"printer selected 1111111111 "+ BaseActivity.getbarcodeMachinePos());
            } else {
                BaseActivity.setbarcodeMachinePos(0);
                BaseActivity.setbarcodeselectedPrinterPOS(0);
                BaseActivity.setbarcodeselectedPrinterID("");
                barcodecount = true;
            }
        }
    }

    @OnItemSelected (R.id.barcode_printer) void barcodePrinter(int position){
        Log.e(TAG,"printer array >>>>>>>11111 "+BarcodeprinterList   +"      "+getbarcodeselectedPrinterPOS() +"      "+position);
        if (BarcodeprinterList != null && BarcodeprinterList.size() > position) {
            if (position != 0) {
                BaseActivity.setbarcodeselectedPrinterPOS(position);
                BaseActivity.setbarcodeselectedPrinterID(BarcodeprinterIDList.get(position));
                Log.e(TAG,"printer selected 2222222 "+BaseActivity.getbarcodeselectedPrinterPOS() +"   "+ getbarcodeselectedPrinterID());

            } else {
                BaseActivity.setbarcodeselectedPrinterPOS(0);
                BaseActivity.setbarcodeselectedPrinterID("");
            }}
    }

    @OnItemSelected (R.id.barcodeSlipmachine) void barcodeSlipMachine(int position){
        Log.e(TAG,"printer array >>>>>>>> "+printerArray);
        if (printerArray != null && printerArray.size() > position) {
            if (position != 0) {
                BaseActivity.setBarcodeSlipMachinePos(position);
                BarcodeSlipprinterList = getPrintersList(printerArray.get(position));
                BarcodeSlipprinterIDList = printerID;
                setspinner(R.id.barcodeSlipPrinter,BarcodeSlipprinterList);
                if(!barcodeslipcount) {
                    barcodeslipprinter.setSelection(BaseActivity.getBarcodeSlipselectedPrinterPOS());
                    barcodeslipcount = true;
                }
                Log.e(TAG,"printer selected 333333 "+ BaseActivity.getBarcodeSlipMachinePos());
            } else {
                BaseActivity.setBarcodeSlipMachinePos(0);
                BaseActivity.setBarcodeSlipselectedPrinterPOS(0);
                BaseActivity.setBarcodeSlipselectedPrinterID("");
                barcodeslipcount = true;
            }
        }
    }

    @OnItemSelected (R.id.barcodeSlipPrinter) void barcodeSlipPrinter(int position){
        Log.e(TAG,"printer array >>>>>>>>2222 "+BarcodeSlipprinterList);
        if (BarcodeSlipprinterList != null && BarcodeSlipprinterList.size() > position) {
            if (position != 0) {
                BaseActivity.setBarcodeSlipselectedPrinterPOS(position);
                BaseActivity.setBarcodeSlipselectedPrinterID(BarcodeSlipprinterIDList.get(position));
                Log.e(TAG,"printer selected 444444 "+BaseActivity.getBarcodeSlipselectedPrinterPOS() +"   "+ getBarcodeSlipselectedPrinterID());

            } else {
                BaseActivity.setBarcodeSlipselectedPrinterPOS(0);
                BaseActivity.setBarcodeSlipselectedPrinterID("");
            }}
    }


    @OnItemSelected (R.id.invoice_machine) void invoiceMachine(int position){
        Log.e(TAG,"printer array >>>>>>>> "+printerArray);
        if (printerArray != null && printerArray.size() > position) {
            if (position != 0) {
                BaseActivity.setinvoiceMachinePos(position);
                InvoiceprinterList = getPrintersList(printerArray.get(position));
                InvoiceprinterIDList = printerID;
                setspinner(R.id.invoice_printer,InvoiceprinterList);
                if(!invoicecount) {
                    invoiceprinter.setSelection(BaseActivity.getinvoiceselectedPrinterPOS());
                    invoicecount = true;
                }
                Log.e(TAG,"printer selected 555555 "+ BaseActivity.getinvoiceMachinePos());
            } else {
                BaseActivity.setinvoiceMachinePos(0);
                BaseActivity.setinvoiceselectedPrinterPOS(0);
                BaseActivity.setinvoiceselectedPrinterID("");
                invoicecount = true;
            }
        }
    }

    @OnItemSelected (R.id.invoice_printer) void invoicePrinter(int position){
        Log.e(TAG,"printer array >>>>>>>>33333 "+InvoiceprinterList );
        if (InvoiceprinterList != null && InvoiceprinterList.size() > position) {
            if (position != 0) {
                BaseActivity.setinvoiceselectedPrinterPOS(position);
                BaseActivity.setetinvoiceselectedPrinterName(InvoiceprinterList.get(position));
                BaseActivity.setinvoiceselectedPrinterID(InvoiceprinterIDList.get(position));
                Log.e(TAG,"printer selected 66666 "+BaseActivity.getinvoiceselectedPrinterPOS() +"   "+ getinvoiceselectedPrinterID());

            } else {
                BaseActivity.setinvoiceselectedPrinterPOS(0);
                BaseActivity.setinvoiceselectedPrinterID("");
            }}
    }

    @OnItemSelected (R.id.integrated_machine) void integratedMachine(int position){
        Log.e(TAG,"printer array >>>>>>>> "+printerArray);
        if (printerArray != null && printerArray.size() > position) {
            if (position > 0) {
                BaseActivity.setintegratedMachinePos(position);
                IntegratedprinterList = getPrintersList(printerArray.get(position));
                IntegratedprinterIDList = printerID;
                setspinner(R.id.integrated_printer,IntegratedprinterList);
                if(!integratecount) {
                    integratedprinter.setSelection(BaseActivity.getintegratedselectedPrinterPOS());
                    integratecount = true;
                    Log.e(TAG,"printer array >>>>>>>>1111111integrated_machine "+getintegratedselectedPrinterPOS());
                }

                Log.e(TAG,"printer selected 99999 "+getintegratedMachinePos() );

            } else {
                Log.e(TAG,"printer selected 9191919191 "+getintegratedselectedPrinterPOS() );
                BaseActivity.setintegratedMachinePos(0);
                BaseActivity.setintegratedselectedPrinterPOS(0);
                BaseActivity.setintegratedselectedPrinterID("");
                integratecount = true;
            }}
    }

    @OnItemSelected (R.id.integrated_printer) void integratedPrinter(int position){
        Log.e(TAG,"printer array >>>>>>>>66666 "+IntegratedprinterList +"     "+getintegratedselectedPrinterPOS()+"   "+position);
        if (IntegratedprinterList != null && IntegratedprinterList.size() > position) {
            if (position > 0) {
                BaseActivity.setintegratedselectedPrinterPOS(position);
                BaseActivity.setintegratedselectedPrinterName(IntegratedprinterList.get(position));
                BaseActivity.setintegratedselectedPrinterID(IntegratedprinterIDList.get(position));
                Log.e(TAG,"printer selected 13131313 "+BaseActivity.getintegratedselectedPrinterPOS() +"   "+ getintegratedselectedPrinterID());

            } else {
                BaseActivity.setintegratedselectedPrinterPOS(0);
                BaseActivity.setintegratedselectedPrinterID("");
            }
        }
    }

    @OnItemSelected (R.id.postpay_machine) void postpayMachine(int position){
        Log.e(TAG,"printer array >>>>>>>> "+printerArray);
        if (printerArray != null && printerArray.size() > position) {
            if (position != 0) {
                BaseActivity.setPostPayMachinePos(position);
                PostpayprinterList = getPrintersList(printerArray.get(position));
                PostpayprinterIDList = printerID;
                setspinner(R.id.postpay_printer,PostpayprinterList);
                if(!postpaycount) {
                    postpayprinter.setSelection(BaseActivity.getPostPayselectedPrinterPOS());
                    postpaycount = true;
                }

                Log.e(TAG,"printer selected 99999 "+getPostPayMachinePos() );

            } else {
                BaseActivity.setPostPayMachinePos(0);
                BaseActivity.setPostPayselectedPrinterPOS(0);
                BaseActivity.setPostPayselectedPrinterID("");
                postpaycount = true;
            }
        }
    }

    @OnItemSelected (R.id.postpay_printer) void postpayPrinter(int position){
        Log.e(TAG,"printer array >>>>>>>>55555 "+PostpayprinterList);
        if (PostpayprinterList != null && PostpayprinterList.size() > position) {
            if (position != 0) {
                BaseActivity.setPostPayselectedPrinterPOS(position);
                BaseActivity.setPostPayselectedPrinterID(PostpayprinterIDList.get(position));
                Log.e(TAG,"printer selected 00000 "+BaseActivity.getPostPayselectedPrinterPOS() +"   "+ getPostPayselectedPrinterID());

            } else {
                BaseActivity.setPostPayselectedPrinterPOS(0);
                BaseActivity.setPostPayselectedPrinterID("");
            }}
    }

    @OnItemSelected (R.id.csv_machine) void csvMachine(int position){
        Log.e(TAG,"printer array >>>>>>>> "+printerArray);
        if (printerArray != null && printerArray.size() > position) {
            if (position != 0) {
                BaseActivity.setCsvMachinePos(position);
                CsvprinterList = getPrintersList(printerArray.get(position));
                CsvprinterIDList = printerID;
                setspinner(R.id.csv_printer,CsvprinterList);
                if(!csvcount) {
                    csvprinter.setSelection(BaseActivity.getCsvselectedPrinterPOS());
                    csvcount = true;
                }
                Log.e(TAG,"printer selected 12121212 "+getCsvMachinePos() );

            } else {
                BaseActivity.setCsvMachinePos(0);
                BaseActivity.setCsvselectedPrinterPOS(0);
                BaseActivity.setCsvselectedPrinterID("");
                csvcount = true;
            }}
    }

    @OnItemSelected (R.id.csv_printer) void csvPrinter(int position){
        Log.e(TAG,"printer array >>>>>>>>611116666 "+CsvprinterList);
        if (CsvprinterList != null && CsvprinterList.size() > position) {
            if (position != 0) {
                BaseActivity.setCsvselectedPrinterPOS(position);
                BaseActivity.setCsvselectedPrinterName(CsvprinterList.get(position));
                BaseActivity.setCsvselectedPrinterID(CsvprinterIDList.get(position));
                Log.e(TAG,"printer selected 13131313 "+BaseActivity.getCsvselectedPrinterPOS() +"   "+ getCsvselectedPrinterID());

            } else {
                BaseActivity.setCsvselectedPrinterPOS(0);
                BaseActivity.setCsvselectedPrinterID("");
            }
        }
    }

    @OnItemSelected (R.id.file_machine) void fileMachine(int position){
        Log.e(TAG,"printer array >>>>>>>> "+printerArray);
        if (printerArray != null && printerArray.size() > position) {
            if (position != 0) {
                BaseActivity.setFileMachinePos(position);
                FileprinterList = getPrintersList(printerArray.get(position));
                FileprinterIDList = printerID;
                setspinner(R.id.file_printer,FileprinterList);
                if(!filecount) {
                    fileprinter.setSelection(BaseActivity.getFileselectedPrinterPOS());
                    filecount = true;
                }
                Log.e(TAG,"printer selected 12121212 "+getFileMachinePos() );

            } else {
                BaseActivity.setFileMachinePos(0);
                BaseActivity.setFileselectedPrinterPOS(0);
                BaseActivity.setFileselectedPrinterID("");
                filecount = true;
            }}
    }

    @OnItemSelected (R.id.file_printer) void filePrinter(int position){
        Log.e(TAG,"printer array >>>>>>>>66666 "+FileprinterList);
        if (FileprinterList != null && FileprinterList.size() > position) {
            if (position != 0) {
                BaseActivity.setFileselectedPrinterPOS(position);
//                BaseActivity.setCsvselectedPrinterName(FileprinterList.get(position));
                BaseActivity.setFileselectedPrinterID(FileprinterIDList.get(position));
                Log.e(TAG,"printer selected 13131313 "+BaseActivity.getFileselectedPrinterPOS() +"   "+ getFileselectedPrinterID());

            } else {
                BaseActivity.setFileselectedPrinterPOS(0);
                BaseActivity.setFileselectedPrinterID("");
            }
        }
    }


    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "プリンター選択", " ",
                0, false,false,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);


        relLayout1.setOnClickListener(this);

//        imgRight.setOnClickListener(this);
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

    public boolean setSelectedPRinters(){

                Log.e(TAG, "Baseeeee  barcode11111111111111barcodemachine  " + BaseActivity.getbarcodeMachinePos());
               try {
                   barcodemachine.setSelection(BaseActivity.getbarcodeMachinePos());
                   barcodeMachine(BaseActivity.getbarcodeMachinePos());
                   if (BaseActivity.getbarcodeMachinePos() > 0)
                       barcodecount = false;


                   Log.e(TAG, "Baseeeee   barcode11111111111111 barcodeslippmachine" + BaseActivity.getBarcodeSlipMachinePos());
                   barcodeslippmachine.setSelection(BaseActivity.getBarcodeSlipMachinePos());
                   barcodeSlipMachine(BaseActivity.getBarcodeSlipMachinePos());
                   if (BaseActivity.getBarcodeSlipMachinePos() > 0)
                       barcodeslipcount = false;

                   Log.e(TAG, "Baseeeee  integrated11111111111111integratedmachine  " + BaseActivity.getintegratedMachinePos());
                   integratedmachine.setSelection(BaseActivity.getintegratedMachinePos());
                   integratedMachine(BaseActivity.getintegratedMachinePos());
                   if (BaseActivity.getintegratedMachinePos() > 0)
                       integratecount = false;

                   Log.e(TAG, "Baseeeee  invoice11111111111111  " + BaseActivity.getinvoiceMachinePos());
                   invoicemachine.setSelection(BaseActivity.getinvoiceMachinePos());
                   invoiceMachine(BaseActivity.getinvoiceMachinePos());
                   if (BaseActivity.getinvoiceMachinePos() > 0)
                       invoicecount = false;


                   Log.e(TAG, "Baseeeee  postpayyyyy11111111111111  " + BaseActivity.getPostPayMachinePos());
                   postpaymachine.setSelection(BaseActivity.getPostPayMachinePos());
                   postpayMachine(BaseActivity.getPostPayMachinePos());
                   if (BaseActivity.getPostPayMachinePos() > 0)
                       postpaycount = false;


                   Log.e(TAG, "Baseeeee  csvselect11111111111111  " + BaseActivity.getCsvMachinePos());
                   csvmachine.setSelection(BaseActivity.getCsvMachinePos());
                   csvMachine(BaseActivity.getCsvMachinePos());
                   if (BaseActivity.getCsvMachinePos() > 0)
                       csvcount = false;

                   Log.e(TAG, "Baseeeee  fileselect11111111111111  " + BaseActivity.getFileMachinePos());
                   filemachine.setSelection(BaseActivity.getFileMachinePos());
                   fileMachine(BaseActivity.getFileMachinePos());
                   if (BaseActivity.getFileMachinePos() > 0)
                       filecount = false;
               }
               catch (Exception e){
                   resetAll();
                   return false;
               }

       return true;
    }

    public void setPrintersSpinners()
    {
        // selected printers
        Log.e(TAG, "Baseeeee  barcode11111111111111  "+BaseActivity.getbarcodeselectedPrinterPOS());
        barcodeprinter.setSelection(BaseActivity.getbarcodeselectedPrinterPOS());

        Log.e(TAG, "Baseeeee  barcode11111111111111  "+BaseActivity.getBarcodeSlipselectedPrinterPOS());
        barcodeslipprinter.setSelection(BaseActivity.getBarcodeSlipselectedPrinterPOS());

        Log.e(TAG, "Baseeeee  integrated11111111111111integratedprinter  "+BaseActivity.getintegratedselectedPrinterPOS());
        integratedprinter.setSelection(BaseActivity.getintegratedselectedPrinterPOS());
        Log.e(TAG, "Baseeeee  integrated1122222222222222222integratedprinter  "+BaseActivity.getintegratedselectedPrinterPOS());

        Log.e(TAG, "Baseeeee  invoice11111111111111  "+BaseActivity.getinvoiceselectedPrinterPOS());
        invoiceprinter.setSelection(BaseActivity.getinvoiceselectedPrinterPOS());

        Log.e(TAG, "Baseeeee  postpayyyyy11111111111111  "+BaseActivity.getPostPayselectedPrinterPOS());
        postpayprinter.setSelection(BaseActivity.getPostPayselectedPrinterPOS());

        Log.e(TAG, "Baseeeee  csvselect11111111111111  "+BaseActivity.getCsvselectedPrinterPOS());
        csvprinter.setSelection(BaseActivity.getCsvselectedPrinterPOS());

        Log.e(TAG, "Baseeeee  fileselect11111111111111  "+BaseActivity.getFileselectedPrinterPOS());
        fileprinter.setSelection(BaseActivity.getFileselectedPrinterPOS());

    }

    void resetAll(){
        BaseActivity.setbarcodeMachinePos(0);
        BaseActivity.setbarcodeselectedPrinterPOS(0);
        BaseActivity.setbarcodeselectedPrinterID("");
        BaseActivity.setBarcodeSlipMachinePos(0);
        BaseActivity.setBarcodeSlipselectedPrinterPOS(0);
        BaseActivity.setBarcodeSlipselectedPrinterID("");
        BaseActivity.setinvoiceMachinePos(0);
        BaseActivity.setinvoiceselectedPrinterPOS(0);
        BaseActivity.setinvoiceselectedPrinterID("");
        BaseActivity.setintegratedMachinePos(0);
        BaseActivity.setintegratedselectedPrinterPOS(0);
        BaseActivity.setintegratedselectedPrinterID("");
        BaseActivity.setPostPayMachinePos(0);
        BaseActivity.setPostPayselectedPrinterPOS(0);
        BaseActivity.setPostPayselectedPrinterID("");
        BaseActivity.setCsvMachinePos(0);
        BaseActivity.setCsvselectedPrinterPOS(0);
        BaseActivity.setCsvselectedPrinterID("");
        BaseActivity.setFileMachinePos(0);
        BaseActivity.setFileselectedPrinterPOS(0);
        BaseActivity.setFileselectedPrinterID("");
        barcodecount= true ;
        barcodeslipcount= true ;
        integratecount= true;
        invoicecount= true ;
        postpaycount = true;
        csvcount= true;
        filecount= true;
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
    public void setPrinterArray(ArrayList arr) {
        this.printerArray = arr;
    }
    public void setPrinterData(ArrayList<Map<String,String>> arr) {
        data = arr;
//        setPrintersSpinners();
    }

    public void setMachineData(JsonHash arr) {
        machineData = arr;
    }



    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {
        HashMap<String, String> mHash=new HashMap<>();
        Log.e(TAG,result.toString());

        try
        {
            String response= result.toString();
            JsonPullParser parser = JsonPullParser.newParser(response);
            JsonHash map1 = JsonHash.fromParser(parser);
            Log.e(TAG," "+map1);

            String msg="";
            JsonArray result1;
            String code = map1.getStringOrNull("code");
            msg=map1.getStringOrNull("message");
            result1= map1.getJsonArrayOrNull("results");
            if (code == null) {
                Log.e(TAG,"CODEeee============Nulllll");
                CommonDialogs.customToast(getApplicationContext(),"Network error occured");
            }

            if ("0".equals(code) )
            {
                Log.e("SendLogs111",code+"  "+msg+"  "+result1);

                new ListMachinePrinters().post(code,msg,result1, mHash,PrinterMachineActivity.this);
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(PrinterMachineActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else{
                U.beepError(this, msg);

            }}
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }
    public  ArrayList<String> getPrintersList (String value){

        list = new ArrayList<>();
        printerID = new ArrayList<>();
        printerID.add("");
        list.add("プリンター名選択");
         Log.e (TAG,"listt gettinggggggg ");
        if (machineData.containsKey(value)) {
            JsonArray list2 = machineData.getJsonArrayOrNull(value);
            for (int i = 0; i < list2.size(); i++) {
                JsonHash row = (JsonHash) list2.get(i);

                    list.add(row.getStringOrNull("printer_name"));
                    printerID.add(row.getStringOrNull("printer_id"));
                }

            }
        return list;
    }
     void setspinner(int id, ArrayList<String> data){
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                 _singleItemRes, data);
         adapter.setDropDownViewResource(_dropdownRes);
         Spinner spinner = (Spinner) findViewById(id);
         spinner.setAdapter(adapter);
     }



}
