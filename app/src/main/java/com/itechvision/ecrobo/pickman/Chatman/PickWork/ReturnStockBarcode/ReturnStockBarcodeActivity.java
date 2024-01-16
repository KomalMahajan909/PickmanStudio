package com.itechvision.ecrobo.pickman.Chatman.PickWork.ReturnStockBarcode;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnBarcode.ReturnProductReq;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnBarcode.ReturnProductResponse;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnBarcode.ReturnProductResponseData;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnBarcode.ReturnProductResponseDataProducts;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnLocationSubmit.ReturnLocationSubmitReq;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnLocationSubmit.ReturnLocationSubmitResponse;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnLocationSubmit.ReturnLocationsubmitData;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnLocationSubmit.ReturnLocationsubmitPendingData;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnOrderId.ReturnOrderIDReq;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnOrderId.ReturnOrderResponseData;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnOrderId.ReturnOrderRespose;
import com.itechvision.ecrobo.pickman.Models.SyakkiID.SakiData;
import com.itechvision.ecrobo.pickman.Models.SyakkiID.SakiResponse;
import com.itechvision.ecrobo.pickman.Models.SyakkiID.SyakkiRequest;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;


public class ReturnStockBarcodeActivity extends BaseActivity implements View.OnClickListener , MainAsynListener, DataManager.GetReturnOrderIDcall, DataManager.GetReturnProductcall, DataManager.GetReturnLocationSubmitcall ,DataManager.GetSakiIDcall{
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.add_layout)
    Button numbrbtn;
    @BindView(R.id.layout_number)
    ViewGroup layout;
    @BindView(R.id.layout_main)
    RelativeLayout mainlayout;
    @BindView(R.id.orderReturn)
    LinearLayout barcodelayout;
    @BindView(R.id.scrollMain)
    ScrollView svMain;
    @BindView(R.id.trackingNoCheck)
    CheckBox trackingNoCheck;
    @BindView(R.id.stockspinnerLayout)
    RelativeLayout stockspinnerLayout;
    @BindView(R.id.classificationspinner)
    Spinner classificationspinner;
    @BindView(R.id.return1)
    EditText return1;
    @BindView(R.id.return2)
    EditText return2;
    @BindView(R.id.skipButton)
    Button skipButton;

    public TextToSpeak mTextToSpeak;
    String TAG= ReturnStockBarcodeActivity.class.getSimpleName();

    ArrayList<ReturnOrderResponseData> arr ;
    ArrayList<ReturnProductResponseData> data ;
    ArrayList<ReturnLocationsubmitData> Locdata ;
    ArrayList<ReturnLocationsubmitPendingData> Pendingdata ;
    ArrayList<String> classificationdata;
    ArrayList<String> classificationArray;
    ArrayList<ReturnProductResponseDataProducts> cProductList ;
    ArrayList<SakiData> idsdata ;

    ECRApplication app=new ECRApplication();
    public String adminID = "", ID = "";
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public Context mcontext=this;
    private boolean showKeyboard;

    protected int mProcNo = 0;

    public static final int PROC_RETURN_NO = 1;
    public static final int PROC_ORDER_ID = 3;
    public static final int PROC_BARCODE = 4;
    public static final int PROC_QTY = 6;
    public static final int PROC_RETURN1 = 5;
    public static final int PROC_RETURN2 = 7;
    public static final int PROC_STOCK_CLASSIFICATION = 8;
    public static final int PROC_LOCATION = 9;

    public static int mRequestStatus =0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_RETURN_NO = 2;
    public static final int REQ_CLASSIFICATION = 3;

    DataManager manager;
    progresBar progress;
    DataManager.GetReturnOrderIDcall getReturnOrderIDcall;
    DataManager.GetReturnProductcall getReturnProductcall;
    DataManager.GetReturnLocationSubmitcall getReturnLocationSubmitcall;
    DataManager.GetSakiIDcall  getids;
    private boolean visible = false, skip = false;

    String Selectedqty = "0", ScanedQty = "0" , selectedCondition = "", LocationQty="0";
    private String classificationId = "";
    String ConDition1="",Condition2="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_stock_barcode);

        ButterKnife.bind(ReturnStockBarcodeActivity.this);

        getIDs();

        Log.d(TAG,"On Create ");

        getids =this;
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);

        mTextToSpeak = new TextToSpeak(this);
        return1.setOnClickListener(this);
        return2.setOnClickListener(this);

        showKeyboard=BaseActivity.getaddKeyboard();
        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
            visible = true;

//			numbrbtn.setText("キーボードを隠す");

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e(TAG,"SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        classificationspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (classificationArray != null && classificationArray.size() > position && position>0) {
                    setclassificationId(classificationArray.get(position));
                    Log.e(TAG, "Selected Classification " + classificationArray.get(position));
                    if (ScanedQty.equals(Selectedqty)) {
                        setProc(PROC_LOCATION);                        //  NEWIDS();
                    }else if(LocationQty.equalsIgnoreCase("1") && Integer.parseInt(ScanedQty)!=Integer.parseInt(Selectedqty)){
                        setProc(PROC_LOCATION);
                    } else{
                        setProc(PROC_QTY);
                    }

                }
                else {
                    setclassificationId("");
                    Log.e(TAG, "Selected Classification" + getclassificationId() + "null");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setclassificationId("");
                Log.e(TAG,"Selected Classification"+ getclassificationId() +"null");
            }
        });

        skipButton.setOnClickListener(this);

        if(mProcNo == 0)
            nextProcess();





    }

    public void GetID(){
        progress.Show();
        SyakkiRequest reqq= new SyakkiRequest(app.getSerial(), BaseActivity.getShopId(), getResources().getString(R.string.version),adminID);
        manager.SakiyakiIds(reqq,getids);
    }

    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "Ｓ専用返品", " ",
                0, false,false,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

//        btnBlue.setOnClickListener(this);
        relLayout1.setOnClickListener(this);
        skipButton.setOnClickListener(this);

        progress = new progresBar(this);
        manager = new DataManager();
        getReturnOrderIDcall = this;
        getReturnProductcall = this;
        getReturnLocationSubmitcall = this;

        arr = new ArrayList<>();
        data = new ArrayList<>();
        cProductList = new ArrayList<>();
        classificationdata = new ArrayList<>();
        classificationArray = new ArrayList<>();
        idsdata = new ArrayList<>();

        Locdata = new ArrayList<>();
        Pendingdata = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
            case R.id.return1:
                if (return1.getText().toString().equals("0")){

                }else {
                    if (mProcNo >= PROC_RETURN1 && cProductList.size() > 0) {
                        int val = Integer.parseInt(_gts(R.id.return1));
                        if (val > 0) {
                            _gt(R.id.return1).setBackground(getResources().getDrawable(R.drawable.basic_edittext_selected));
                            _gt(R.id.return2).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));


                            selectedCondition = "1";
                            Selectedqty = _gts(R.id.return1);
                            _sts(R.id.quantity, "1");
                            nextWork();
                        }
                    }

                    if (ConDition1.equalsIgnoreCase("")) {
                        setSpinner();
                        classificationspinner.performClick();
                    } else {

                        for (int i = 0; i < idsdata.size(); i++) {

                            if (idsdata.get(i).getId().equalsIgnoreCase(ConDition1)) {
                                setSpinner();
                                classificationspinner.setSelection(i + 1);

                                break;
                            }
                        }
                    }
                }



                break;
            case R.id.return2:
                if (return2.getText().toString().equals("0")){

                }else {

                    if (mProcNo >= PROC_RETURN1 && cProductList.size() > 0) {
                        int val1 = Integer.parseInt(_gts(R.id.return2));
                        if (val1 > 0) {
                            return2.setBackground(getResources().getDrawable(R.drawable.basic_edittext_selected));
                            _gt(R.id.return1).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                            selectedCondition = "2";
                            Selectedqty = _gts(R.id.return2);
                            _sts(R.id.quantity, "1");
                            nextWork();

                         }

                    }

                    if (Condition2.equalsIgnoreCase("")) {
                        setSpinner();
                        classificationspinner.performClick();
                    } else {

                        for (int i = 0; i < idsdata.size(); i++) {

                            if (idsdata.get(i).getId().equalsIgnoreCase(Condition2)) {
                                setSpinner();
                                classificationspinner.setSelection(i + 1);

                                break;
                            }
                        }
                    }
                }

                break;
            case R.id.skipButton:
                if(mProcNo== PROC_RETURN_NO ){
                    skip= true;
                    setProc(PROC_ORDER_ID);
                    _sts(R.id.returning_number, "");
                }
                else {
                    skip = false;

                }
                Log.e(TAG, " Skipp     "+skip);
                break;
            default:
                break;
        }
    }
    public void AddLayout(View view)
    {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
        if(visible==false)
        {

            visible = true;
            numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e(TAG,"SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        else
        {
            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG,"SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
    }

    public int convert(int x) {
        Resources r =mcontext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                x, r.getDisplayMetrics()
        );
        return px;
    }

    public void setProc(int procNo) {
        Log.e(TAG,"setProcccccc");
        mProcNo = procNo;
        switch (procNo) {
            case PROC_RETURN_NO:
                LocationQty ="0";
                Log.e(TAG, "setProc_PROC_ReturnNO");
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));

                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.return1).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.return2).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));

                _gt(R.id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                stockspinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setFocusableInTouchMode(true);

                classificationspinner.setAdapter(null);
                scrollToView(svMain, _g(R.id.returning_number));
                break;

            case PROC_ORDER_ID:
                LocationQty ="0";
                Log.e(TAG, "setProc_PROC_ORDER_ID");
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));

                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.return1).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.return2).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));

                _gt(R.id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                stockspinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.orderId).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.orderId));
                break;

            case PROC_BARCODE:
                LocationQty ="0";
                Log.e(TAG, "setProc_PROC_BARCODE");
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.return1).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.return2).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));

                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                stockspinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.barcode));
                break;

            case PROC_RETURN1:
                LocationQty ="0";
                Log.e(TAG, "setProc_PROC_BARCODE");
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                stockspinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.barcode));
                break;


            case PROC_QTY:

                LocationQty ="0";
                Log.e(TAG, "setProc_PROC_QTYY");
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));

                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                stockspinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.quantity));
                break;

            case PROC_STOCK_CLASSIFICATION:
                Log.e(TAG, "setProc_PROC_STOCK_CLASSIFICATION");

                setSpinner();

                LocationQty ="0";
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                stockspinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
//                 classificationspinner.performClick();
                scrollToView(svMain, _g(R.id.stockspinnerLayout));
                break;

            case PROC_LOCATION:
                LocationQty ="1";
                Log.e(TAG, "setProc_PROC_LOCATION");
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));

                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                stockspinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.location).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.location));
                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {
            case PROC_RETURN_NO:    // 注文No
                LocationQty ="0";
                Log.e(TAG, "inputedEvent_PROC RETURN NO");
                String returnNo = _gts(R.id.returning_number);
                if ("".equals(returnNo)) {
                    U.beepError(this, "注文Noは必須です");
                    _gt(R.id.returning_number).setFocusableInTouchMode(true);
                    break;
                }

               /* Globals.getterList = new ArrayList<>();
                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("tracking_code", returnNo));
                Globals.getterList.add(new ParamsGetter("mode", "check_order"));

                mRequestStatus = REQ_RETURN_NO;*/

                progress.Show();
                ReturnOrderIDReq req = new ReturnOrderIDReq(adminID, app.getSerial(), BaseActivity.getShopId(), getResources().getString(R.string.version), "","", returnNo);
                manager.GetReturnOrder(req, getReturnOrderIDcall);

                break;

            case PROC_ORDER_ID:    // 注文No
                LocationQty ="0";
                Log.e(TAG, "inputedEvent_PROC RETURN NO");
                String orderid = _gts(R.id.orderId);
                if ("".equals(orderid)) {
                    U.beepError(this, "注文Noは必須です");
                    _gt(R.id.orderId).setFocusableInTouchMode(true);
                    break;
                }
                String sortby="";
                if(trackingNoCheck.isChecked())
                    sortby ="mediacode";

                else  sortby = "";

                progress.Show();
                ReturnOrderIDReq req11 = new ReturnOrderIDReq(adminID, app.getSerial(), BaseActivity.getShopId(), getResources().getString(R.string.version), orderid,sortby, "");
                manager.GetReturnOrder(req11, getReturnOrderIDcall);

                break;

            case PROC_BARCODE:    // 注文No
                LocationQty ="0";
                Log.e(TAG, "inputedEvent_PROC RETURN NO");
                String barcode = _gts(R.id.barcode);
                if ("".equals(barcode)) {
                    U.beepError(this, "バーコードは空にできません");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }

                progress.Show();
                ReturnProductReq req1 = new ReturnProductReq(adminID, app.getSerial(), BaseActivity.getShopId(), getResources().getString(R.string.version), arr.get(0).getOrder_id(),arr.get(0).getSendback_id(),barcode);
                manager.GetReturnProduct(req1, getReturnProductcall);

                break;

            case PROC_QTY:
                LocationQty ="0";

                String qty = _gts(R.id.quantity);
                String barcode1 = _gts(R.id.barcode);

                if (isScaned) {

                    if (buff.equals(barcode1)) {
                        qty = U.plusTo(qty, "1");

                        if(!ScanedQty.equals(_gts(R.id.quantity)))
                            ScanedQty = _gts(R.id.quantity);

                        ScanedQty = U.plusTo(ScanedQty,"1");
                        Log.e(TAG, "Scannedd   "+ScanedQty);

                        //increase qunatity in mpackdata
                        if (Integer.parseInt(ScanedQty) <= Integer.parseInt(Selectedqty)) {


                            _sts(R.id.quantity, qty);

                            if (Integer.parseInt(qty) > 1)
                                mTextToSpeak.startSpeaking(qty);


                            /* check if update in quantity need next action */
                            if (ScanedQty.equals(Selectedqty)) {

                                if (getclassificationId().equalsIgnoreCase("")){
                                    setProc(PROC_STOCK_CLASSIFICATION);
                                }else{
                                    setProc(PROC_LOCATION);
                                }


                                //   NEWIDS();
                            }
                        }
                    } else {
                        U.beepError(this, "Barcode doesnot match");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                } else {
                    if ("".equals(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                    String processedCnt = ScanedQty;
                    String qtyUpdate = U.minusTo(qty, ScanedQty);
                    String maxQty_ = U.minusTo(Selectedqty, processedCnt);

                    if (U.compareNumeric(ScanedQty, qty) == -1) {
                        U.beepError(this, "Quantity entered should exceed " + ScanedQty.toString());

                        _sts(R.id.quantity, "1");

                        ScanedQty = "1";

                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        U.beepError(this, "数量が多すぎます");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        _sts(R.id.quantity, "1");

                        ScanedQty = "1";
                        break;
                    } else {
                        if (Integer.parseInt(qty) > 1)
                            mTextToSpeak.startSpeaking(_gts(R.id.quantity));

                        ScanedQty =  U.plusTo(processedCnt, qtyUpdate);

                        if (ScanedQty.equals(Selectedqty)) {

                            if (getclassificationId().equalsIgnoreCase("")){
                                setProc(PROC_STOCK_CLASSIFICATION);
                            }else{
                                setProc(PROC_LOCATION);
                            }

                            //  setProc(PROC_STOCK_CLASSIFICATION);;
                            //    NEWIDS();
                        } else {
                            if (getclassificationId().equalsIgnoreCase("")){
                                setProc(PROC_STOCK_CLASSIFICATION);
                            }else{
                                setProc(PROC_LOCATION);
                            }

                            //setProc(PROC_STOCK_CLASSIFICATION);
                            // NEWIDS();
                        }
                    }
                }
                break;

            case PROC_LOCATION:    // 注文No
                LocationQty ="1";
                Log.e(TAG, "inputedEvent_PROC LOCATION");
                String loc = _gts(R.id.location);
                if ("".equals(loc)) {
                    U.beepError(this, "バーコードは空にできません");
                    _gt(R.id.location).setFocusableInTouchMode(true);
                    break;
                }

                progress.Show();
                ReturnLocationSubmitReq req2 = new ReturnLocationSubmitReq(adminID, app.getSerial(), BaseActivity.getShopId(), getResources().getString(R.string.version), arr.get(0).getOrder_id(),arr.get(0).getSendback_id(),cProductList.get(0).getBarcode(),cProductList.get(0).getProduct_id(),selectedCondition, ScanedQty,loc,getclassificationId());
                manager.GetReturnLocationSubmit(req2, getReturnLocationSubmitcall);

                break;


        }
    }

    public String getclassificationId() {
        return this.classificationId;
    }

    public void setclassificationId(String id) {
        this.classificationId= id;
    }

    public void nextWork() {
        ScanedQty = "1";
        setProc(PROC_QTY);
        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
        Log.e(TAG, " Sacneddd QTY  "+ScanedQty+ "    SelectedQTY    "+Selectedqty);
        if (ScanedQty.equals(Selectedqty)) {
            setProc(PROC_STOCK_CLASSIFICATION);
            //  NEWIDS();
        } else{
            setProc(PROC_QTY);
        }

        //
    }

    private void setSpinner()
    {
        if (classificationdata.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    _singleItemRes, classificationdata);
            adapter.setDropDownViewResource(_dropdownRes);
            classificationspinner.setVisibility(View.VISIBLE);
            classificationspinner.setAdapter(adapter);
        }
    }


    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        new AlertDialog.Builder(this)
                .setTitle("Clear？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mTextToSpeak.resetQueue();
                        mTextToSpeak.startSpeaking("clear");
                        nextProcess();
                        /*final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 100ms
                                SyakkiRequest reqq= new SyakkiRequest(app.getSerial(), BaseActivity.getShopId(), getResources().getString(R.string.version),adminID);
                                manager.SakiyakiIds(reqq,getids);
                            }
                        }, 1500);*/



                    }
                })
                .setNegativeButton("いいえ", null)
                .show();
    }

    @Override
    public void allclearEvent() {

    }

    @Override
    public void skipEvent() {

    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_RETURN_NO:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_RETURN_NOOO");
                _sts(R.id.returning_number, buff);
                break;

            case PROC_ORDER_ID:
                Log.e(TAG,"ketpressEvent_PROC_ORDERRRIDDDDD");
                _sts(R.id.orderId, buff);
                break;

            case PROC_BARCODE:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_BARCODEEEEEE");
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                Log.e(TAG,"ketpressEvent_PROC_QTYYYYYYYYY");
                _sts(R.id.quantity, buff);
                break;
            case PROC_LOCATION: // 数量
                Log.e(TAG,"ketpressEvent_PROC_QTYYYYYYYYY");
                _sts(R.id.location, buff);
                break;

         /*   case PROC_QTY: // 数量
                Log.e(TAG,"ketpressEvent_PROC_QTYYYYYYYYY");
                _sts(R.id.quantity, buff);
                break;*/
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (CLEAR_BARCODE.equals(barcode)) {

            mBuff.delete(0, mBuff.length());
            clearEvent();
        } else if (ENTER_BARCODE.equals(barcode)) {
            enterEvent();
        } else if (!barcode.equals("")) {
            if (mProcNo == PROC_BARCODE) {
                _sts(R.id.barcode, barcode);
            } else if (mProcNo == PROC_ORDER_ID) {
                _sts(R.id.orderId, barcode);
            } else if (mProcNo == PROC_RETURN_NO) {
                _sts(R.id.returning_number, barcode);
            } else if (mProcNo == PROC_LOCATION) _sts(R.id.location, barcode);

            if (mProcNo == PROC_QTY) {
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                String finalbarcode1 = CommonFunctions.getBracode(barcode);
                barcode = finalbarcode1;

            }
            this.inputedEvent(barcode, true);
        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_RETURN_NO:    // バーコード
                Log.e(TAG, "ketpressEvent_PROC_RETURN_NOOO");
                _sts(R.id.returning_number, barcode);
                break;

            case PROC_ORDER_ID:
                Log.e(TAG, "ketpressEvent_PROC_ORDERRRIDDDDD");
                _sts(R.id.orderId, barcode);
                break;
            case PROC_RETURN1:
                Log.e(TAG, "ketpressEvent_PROC_RETURN1");
                _sts(R.id.return1, barcode);
                break;
            case PROC_RETURN2:
                Log.e(TAG, "ketpressEvent_PROC_RETURN222");
                _sts(R.id.return2, barcode);
                break;

            case PROC_BARCODE:    // バーコード
                Log.e(TAG, "ketpressEvent_PROC_BARCODEEEEEE");
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QTY: // 数量
                Log.e(TAG, "ketpressEvent_PROC_QTYYYYYYYYY");
                _sts(R.id.quantity, barcode);
                break;
            case PROC_LOCATION: // 数量
                Log.e(TAG, "ketpressEvent_PROC_QTYYYYYYYYY");
                _sts(R.id.location, barcode);
                break;
        }
    }

    @Override
    public void nextProcess() {
        Log.e(TAG," nextProcessssssssss");
        this._sts(R.id.barcode, "");
        this._sts(R.id.quantity, "");
        this._sts(R.id.orderId, "");
        this._sts(R.id.product_code,"");
        this._sts(R.id.product_name,"");
        this._sts(R.id.return1, "");
        this._sts(R.id.return2, "");
        this._sts(R.id.returning_number, "");
        this._sts(R.id.location, "");
        classificationspinner.setAdapter(null);
        if(!classificationdata.isEmpty()){
            classificationdata.clear();
            classificationArray.clear();
        }


        if(showKeyboard==false) {
            visible = false;
            numbrbtn.setVisibility(View.VISIBLE);
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
//            Animation bottomDown = AnimationUtils.loadAnimation(this,
//                    R.anim.bottom_down);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
//            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
            Log.e("NewPicking", "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
        else
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
            visible = true;

//			numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e("NewPicking","SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);

            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }

     //   getClassificationList();

        GetID();

/*
        if(skip)
        setProc(PROC_ORDER_ID);
        else*/
        setProc(PROC_RETURN_NO);

    }

    public void getClassificationList(){
        Globals.getterList = new ArrayList<>();

        Log.e(TAG,"shopidddddd111111111  "+BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));

        mRequestStatus = REQ_CLASSIFICATION;

        new MainAsyncTask(this, Globals.Webservice.listStockIDs, 1, this, "Form", Globals.getterList,true).execute();

    }
    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {
        HashMap<String, String> mHash=new HashMap<>();
        Log.e(TAG,result.toString());
        if(!classificationdata.isEmpty()){
            classificationdata.clear();
            classificationArray.clear();
        }
        try {

            String response = result.toString();
            JsonPullParser parser = JsonPullParser.newParser(response);
            JsonHash map1 = JsonHash.fromParser(parser);
            Log.e(TAG, " " + map1);

            String msg = "";
            JsonArray result1;
            String code = map1.getStringOrNull("code");
            msg = map1.getStringOrNull("message");
            result1 = map1.getJsonArrayOrNull("results");


            if (map1.containsValue("condition1")||map1.containsValue("condition2")){
                ConDition1=   map1.getStringOrNull("condition1");
                Condition2=  map1.getStringOrNull("condition2");

            }


            if (code == null) {
                Log.e(TAG, "CODEeee============Nulllll");
                CommonDialogs.customToast(getApplicationContext(), "Network error occured");
            }
            if ("0".equals(code) == true) {

                Log.e("SendLogs111", code + "  " + msg + "  " + result1);
                Log.e("SendLogs111", ">>>>>>>>>>>>>>>>>>>>>" + mRequestStatus);
                if(mRequestStatus == REQ_CLASSIFICATION)
                {
                    for (int i = 0; i < result1.size(); i++) {
                        JsonHash row = (JsonHash) result1.get(i);

                        if (row.containsKey("stock_ids")) {
                            JsonArray row2 = row.getJsonArrayOrNull("stock_ids");
                            Log.e("GetStockReturn", "Stock Ids List  " + row2 + row2.size());
                            classificationdata.add("在庫区分を選択");
                            classificationArray.add("");
                            for (int j = 0; j < row2.size(); j++) {
                                JsonHash row1 = (JsonHash) row2.get(j);
                                String id = row1.getStringOrNull("id");
                                String name = row1.getStringOrNull("name");
                                String classification = id + "  :  " + name;
                                classificationdata.add(classification);
                                classificationArray.add(row1.getStringOrNull("id"));
                            }
                        }

                    }
                }
                GetID();
            }
            else {
                U.beepError(this, msg);
            }
        }
        catch (Exception e){
            Log.e(TAG, "Exceptionnnn   "+e);

        }
    }

    @Override
    public void onPostError(int flag) {

    }

    @Override
    public void onSucess(int status, ReturnOrderRespose message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){
            arr= message.getResults();
            if(mProcNo== PROC_RETURN_NO){
                _sts(R.id.orderId, arr.get(0).getOrder_id());
            }
            setProc(PROC_BARCODE);

        }else if(message.getCode().equalsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent in = new Intent(ReturnStockBarcodeActivity.this, LoginActivity.class);
                            in.putExtra("ACTION", "logout" );
                            startActivity(in);
                            finish();

                            dialog.dismiss();
                        }
                    })

                    .show();
        }else{
            U.beepError(this,message.getMessage());

            Log.e("khdfhkdf",message.getMessage()); }
    }

    @Override
    public void onSucess(int status, ReturnProductResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){
            data= message.getResults();
            cProductList = data.get(0).getProducts();

            Toast.makeText(this, " 在庫区分を選択してください。", Toast.LENGTH_LONG).show();

            _sts(R.id.product_code, cProductList.get(0).getCode());
            _sts(R.id.product_name , cProductList.get(0).getPname());
            for(int i =0 ; i< cProductList.size(); i++){
                if (cProductList.get(i).getCondition_id().equalsIgnoreCase("1")) {
                    _sts(R.id.return1, cProductList.get(i).getRest_qty());
                }
                else if (cProductList.get(i).getCondition_id().equalsIgnoreCase("2")){
                    _sts(R.id.return2, cProductList.get(i).getRest_qty());
                }}

            if(return1.getText().equals("")|| return1.getText().toString().isEmpty()){
                _sts(R.id.return1, "0");
            }
            if(return2.getText().equals("") || return2.getText().toString().isEmpty()){
                _sts(R.id.return2, "0");
            }

            setProc(PROC_RETURN1);

        }else if(message.getCode().equalsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent in = new Intent(ReturnStockBarcodeActivity.this, LoginActivity.class);
                            in.putExtra("ACTION", "logout" );
                            startActivity(in);
                            finish();

                            dialog.dismiss();
                        }
                    })

                    .show();
        }else{
            U.beepError(this,message.getMessage());

            Log.e("khdfhkdf",message.getMessage()); }
    }



    @Override
    public void onSucess(int status, ReturnLocationSubmitResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){
            Locdata= message.getResults();
            Pendingdata = Locdata.get(0).getPending();

            if(Pendingdata.size()>0){
                this._sts(R.id.barcode, "");
                this._sts(R.id.quantity, "");
                this._sts(R.id.product_code,"");
                this._sts(R.id.product_name,"");
                this._sts(R.id.return1, "");
                this._sts(R.id.return2, "");
                this._sts(R.id.location, "");
                classificationspinner.setAdapter(null);
                setProc(PROC_BARCODE);
                U.beepNext();
            }
            else{
                U.beepKakutei(this,null);
                nextProcess();}

        }else if(message.getCode().equalsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent in = new Intent(ReturnStockBarcodeActivity.this, LoginActivity.class);
                            in.putExtra("ACTION", "logout" );
                            startActivity(in);
                            finish();

                            dialog.dismiss();
                        }
                    })

                    .show();
        }else{
            U.beepError(this,message.getMessage());

            Log.e("khdfhkdf",message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, SakiResponse message) throws JsonIOException {
progress.Dismiss();

         if(message.getCode().equalsIgnoreCase("0")){
          /*  if(!classificationdata.isEmpty()){

                classificationArray.clear();
            }*/

            ConDition1 =message.getCondition1();
            Condition2 =message.getCondition2();

            idsdata = message.getResults().get(0).getStock_ids();
             classificationdata.add("在庫区分を選択");
             classificationArray.add("");
            for (int q=0;q<idsdata.size();q++){
                classificationdata.add(idsdata.get(q).getId()+ " : "+idsdata.get(q).getName());

                classificationArray.add(idsdata.get(q).getId());
            }
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

    public void NEWIDS(){
        if (selectedCondition.equalsIgnoreCase("1")){
            if (ConDition1.equalsIgnoreCase("")){
                setSpinner();
                classificationspinner.performClick();
            }else{

                for (int i=0;i<idsdata.size();i++) {

                    if (idsdata.get(i).getId().equalsIgnoreCase(ConDition1)) {
                        setSpinner();
                        classificationspinner.setSelection(i+1);

                        break;
                    }
                }
            }
        }else{
            if (Condition2.equalsIgnoreCase("")){
                setSpinner();
                classificationspinner.performClick();
            }else{

                for (int i=0;i<idsdata.size();i++) {

                    if (idsdata.get(i).getId().equalsIgnoreCase(Condition2)) {
                        setSpinner();
                        classificationspinner.setSelection(i+1);

                        break;
                    }
                }
            }
        }
    }
}