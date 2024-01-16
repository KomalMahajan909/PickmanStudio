package com.itechvision.ecrobo.pickman.Chatman.Stock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.ListPrintersAll;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrinterSelectActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.spinner_barcode_arrival) Spinner barcodeselect;
    @BindView(R.id.spinner_barcode_slip) Spinner barcodeslip;
    @BindView(R.id.spinner_integrated_printer) Spinner integratedselect;
    @BindView(R.id.spinner_invoice_printer) Spinner invoiceselect;
    @BindView(R.id.spinner_postpay_printer) Spinner postpayselect;
    @BindView(R.id.spinner_csvfix) Spinner csvselect;



    String TAG= PrinterSelectActivity.class.getSimpleName();

    protected ArrayList<String> printerArray = new ArrayList<String>();
    protected ArrayList<String> printerIds = new ArrayList<String>();

    String barcodepr="",invoicepr = "", integratepr ="",integrateprID ="",postpaypr = "",csvpr = "",barcodeslippr;

    int barcodepos=0,invoicepos = 0, integratepos = 0,postpaypos = 0 ,csvpos = 0, barcodeSlippos= 0;
    ECRApplication app=new ECRApplication();
    String adminID="";
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    protected int mProcNo = 0;
    public static final int PROC_BARCODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer_select);

        ButterKnife.bind(PrinterSelectActivity.this);


        getIDs();
        Log.d(TAG,"On Create ");

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);

        Globals.getterList = new ArrayList<>();

        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));


        new MainAsyncTask(this, Globals.Webservice.listPrinters,true, 1, PrinterSelectActivity.this, "Form", Globals.getterList,false).execute();


        barcodeselect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.e(TAG,"printer array >>>>>>>> "+printerIds);
                if (printerIds != null && printerIds.size() > position) {
                    if (position != 0) {
                        setBarcodePrinter(printerIds.get(position));
                        setBarcodePrinterpos(position);
                        Log.e(TAG,"printer selected 1111111111 "+getBarcodePrinter()+"    "+getBarcodePrinterpos());
                    } else {
                        setBarcodePrinter("");
                        setBarcodePrinterpos(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        barcodeslip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.e(TAG,"printer array >>>>>>>> "+printerIds);
                if (printerIds != null && printerIds.size() > position) {
                    if (position != 0) {
                        setBarcodeSlipPrinter(printerIds.get(position));
                        setBarcodeSlipPrinterpos(position);
                        Log.e(TAG,"printer selected 1111111111 "+getBarcodeSlipPrinter()+"    "+getBarcodeSlipPrinterpos());
                    } else {
                        setBarcodeSlipPrinter("");
                        setBarcodeSlipPrinterpos(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        invoiceselect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.e(TAG,"printer array >>>>>>>> "+printerIds);
                if (printerIds != null && printerIds.size() > position) {
                    if (position != 0) {
                        setInvoicePrinter(printerIds.get(position));
                        setInvoicePrinterPos(position);
                        Log.e(TAG,"printer selected 222222 "+getInvoicePrinter() + "        "+getInvoicePrinterPos());

                    } else {
                        setInvoicePrinter("");
                        setInvoicePrinterPos(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        integratedselect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.e(TAG,"printer array >>>>>>>> "+printerIds);
                if (printerIds != null && printerIds.size() > position) {
                    if (position != 0) {
                        setIntegratePrinter(printerIds.get(position));
                        setIntegratePrinterPos(position);
                        setIntegratePrinterID1(printerArray.get(position));
                        Log.e(TAG,"printer selected 33333 "+getIntegratePrinter() +"          "+getIntegratePrinterPos());

                    } else {
                        setIntegratePrinter("");
                        setIntegratePrinterPos(0);
                        setIntegratePrinterID1("");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        postpayselect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.e(TAG,"printer array >>>>>>>> "+printerIds);
                if (printerIds != null && printerIds.size() > position) {
                    if (position != 0) {
                        setPostpayPrinter(printerIds.get(position));
                        setPostpayPrinterPos(position);
                        Log.e(TAG,"printer selected  444444"+getPostpayPrinter()+"          "+getPostpayPrinterPos());

                    } else {
                        setPostpayPrinter("");
                        setPostpayPrinterPos(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        csvselect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.e(TAG,"printer array >>>>>>>> "+printerIds);
                if (printerIds != null && printerIds.size() > position) {
                    if (position != 0) {
                        setCsvPrinter(printerIds.get(position));
                        setCsvPrinterPos(position);
                        Log.e(TAG,"printer selected  444444"+getCsvPrinter()+"          "+getCsvPrinterPos());

                    } else {
                        setCsvPrinter("");
                        setPostpayPrinterPos(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick(R.id.enter)void enter(){


    }

    public void setSelectedPRinters(){


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

    public void setPrinterArray(ArrayList arr) {
        this.printerArray = arr;
    }
    public void setPrinterIds(ArrayList arr) {
        this.printerIds = arr;
    }
    public  void setBarcodePrinter(String printer) {
        this.barcodepr = printer;
    }

    public  String getBarcodePrinter() {
        return this.barcodepr;
    }
    public  void setBarcodePrinterpos(int printer) {
        this.barcodepos = printer;
    }

    public  int getBarcodePrinterpos() {
        return this.barcodepos;
    }

    public  void setBarcodeSlipPrinter(String printer) {
        this.barcodeslippr = printer;
    }

    public  String getBarcodeSlipPrinter() {
        return this.barcodeslippr;
    }
    public  void setBarcodeSlipPrinterpos(int printer) {
        this.barcodeSlippos = printer;
    }

    public  int getBarcodeSlipPrinterpos() {
        return this.barcodeSlippos;
    }
    public  void setInvoicePrinterPos(int printer) {
        this.invoicepos = printer;
    }

    public  int getInvoicePrinterPos() {
        return this.invoicepos;
    }
    public  void setIntegratePrinterPos(int printer) {
        this.integratepos = printer;
    }
    public  int getIntegratePrinterPos() {
        return this.integratepos;
    }

    public  void setInvoicePrinter(String printer) {
        this.invoicepr = printer;
    }
    public  String getInvoicePrinter() {
        return this.invoicepr;
    }

    public  void setIntegratePrinter(String printer) {
        this.integratepr = printer;
    }
    public  String getIntegratePrinter() {
        return this.integratepr;
    }


    public  void setIntegratePrinterID1(String printer) {
        this.integrateprID = printer;
    }
    public  String getIntegratePrinterID1() {
        return this.integrateprID;
    }


    public  void setPostpayPrinter(String printer) {
        this.postpaypr = printer;
    }
    public  String getPostpayPrinter() {
        return this.postpaypr;
    }

    public  void setPostpayPrinterPos(int printer) {
        this.postpaypos = printer;
    }
    public  int getPostpayPrinterPos() {
        return this.postpaypos;
    }

    public  void setCsvPrinter(String printer) {
        this.csvpr = printer;
    }
    public  String getCsvPrinter() {
        return this.csvpr;
    }
    public  void setCsvPrinterPos(int printer) {
        this.csvpos = printer;
    }

    public  int getCsvPrinterPos() {
        return this.csvpos;
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

                    new ListPrintersAll().post(code,msg,result1, mHash,PrinterSelectActivity.this);

            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(PrinterSelectActivity.this,LoginActivity.class);
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
}
