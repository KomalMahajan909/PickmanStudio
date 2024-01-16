package com.itechvision.ecrobo.pickman.Chatman.Arrivals;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetReturnLot;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetReturnNew;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.NewReturnClassification.GetClassificationResult;
import com.itechvision.ecrobo.pickman.Models.NewReturnClassification.GetReturnClassificationReq;
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
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class ReturnActivity extends BaseActivity implements View.OnClickListener, MainAsynListener, DataManager.GetReturnClassificationCallback {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.returnclassificaion) Spinner returnfromspinner;
    @BindView(R.id.expirationdate) EditText expiration;
    @BindView(R.id.barcode) EditText barcode;
    @BindView(R.id.listArrival)ListView lv;
    @BindView(R.id.layout_returnclassificaion) LinearLayout layoutspinner;
    @BindView(R.id.gridExpiration) LinearLayout layoutexpiration;
    @BindView(R.id.gridLot) LinearLayout layoutlot;
    @BindView(R.id.lotno) EditText lot;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.goodproduct) RadioButton goodproduct;
    @BindView(R.id.badproduct) RadioButton badproduct;
    @BindView(R.id.ll_classification) LinearLayout ll_classification;
    @BindView(R.id.ll_list) LinearLayout ll_list;

    private boolean visible = false;
    public  boolean lotexist=false;
    protected RelativeLayout layout;
    protected RelativeLayout mainlayout;
    public Context mcontext=this;
    private static final String TRUE1 = "TRUE";
    private static final String FALSE1 = "FALSE";
    private String directToStockSetting = "FALSE";
    private boolean showKeyboard;
    public TextToSpeak mTextToSpeak;
    String TAG= DirectarrivalActivity.class.getSimpleName();
    ListViewAdapter adapter;
    public String nyukaId1 = null,TOTALQTY="";
    String attribute = "0";
    private boolean orderRequestSettings;
    public String classificationId  = "";
    public String supplierId ="";
    ECRApplication app=new ECRApplication();
    String adminID="",Clasificationstatus="";
    private boolean lotselect = false;
    protected ArrayList<String> nyukaIdArray = new ArrayList<String>();
    protected ArrayList<String> classificationIdArray = new ArrayList<String>();
    protected ArrayList<String> supplierIdArray = new ArrayList<String>();
    protected List<Map<String, String>> mReturnList =  new ArrayList<Map<String, String>>();
    public static List<Map<String, String>> mArrivalLot = new ArrayList<Map<String, String>>();
    public static int lotpress = 0;
    int mSelectedItem = 0;
    protected int mProcNo = 0;
    private boolean arrivalScheduleSelected = false;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_QTY = 2;
    public static final int PROC_LOT_NO = 3;
    public static final int PROC_EXPIRATION = 4;
    public static int mRequestStatus =0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_BARCODE = 2;
    public static final int REQ_LOT = 3;
    public static final int REQ_QTY = 4;
    public TextView productName;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public String isNextBarcode ="",Classificationtag="0";
    DataManager.GetReturnClassificationCallback getcassification ;
    DataManager manager;
    progresBar progress;
    String lotno = "";
    String expdat = "";

    int ExtraValue =0,valueText2=0;

    DialogInterface.OnDismissListener onCancelListener ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        orderRequestSettings = BaseActivity.getLotPress();

        ButterKnife.bind(ReturnActivity.this);
        getIDs();
        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);

        showKeyboard = BaseActivity.getaddKeyboard();
        arrivalScheduleSelected = BaseActivity.getArrivalNyuka();

        productName = (TextView)findViewById(R.id.productName);
        getcassification = this ;

        manager= new DataManager();
        progress = new progresBar(this);


/*
        expiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTruitonDatePickerDialog(expiration);
            }
        });

        expiration.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showTruitonDatePickerDialog(expiration);
            }
        });*/




        if(showKeyboard==true) {

            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
            visible = true;

            numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(20);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e(TAG,"SetlayoutMargin");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);
        Log.e(TAG,"ONCREATE");

        if (mProcNo == 0) nextProcess();
        Globals.getterList = new ArrayList<>();
        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        mRequestStatus = REQ_INITIAL;
        new MainAsyncTask(this, Globals.Webservice.listPrinter, 1, ReturnActivity.this, "Form", Globals.getterList,true).execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mSelectedItem = position;
                TOTALQTY = mReturnList.get(mSelectedItem).get("qty") ;

                adapter.notifyDataSetChanged();
                if (nyukaIdArray != null && nyukaIdArray.size() > position) {
                    setNyukaId(nyukaIdArray.get(position));
//					setQnty(qntyArray.get(position));
                    Log.e(TAG, "Spinner");
//                  if (arrivalScheduleSelected) {
//                        if (getNyukaId().equals("999")) {
//                          changeColor();
//                        }
//                    }

                    if (getNyukaId().equals("999")) {
                        Log.e(TAG, "Spinner " + getNyukaId() + "  " + attribute);
                        if (BaseActivity.getLotPress()) {
                            if (!attribute.equals("0")) {
                                if (attribute.equals("1")) {
                                    _sts(R.id.lotno, "");
                                    setProc(PROC_LOT_NO);
                                } else if (attribute.equals("2")) {
                                    if (mProcNo != PROC_EXPIRATION) {
                                        setProc(PROC_EXPIRATION);
                                    }
                                    _sts(R.id.expirationdate, "");
                                } else if (attribute.equals("3")) {
                                    setProc(PROC_LOT_NO);
                                    _sts(R.id.expirationdate, "");
                                    _sts(R.id.lotno, "");
                                }
                            }
                        }
                    } else {
                        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_return);
                        layout.setBackgroundResource(R.color.white);
                        if (BaseActivity.getLotPress()) {
                            if (!attribute.equals("0")) {
                                for (Map<String, String> map : mArrivalLot) {
                                    Log.e(TAG, "Spinner       " + getNyukaId() + "     " + map.get("nyuka_id"));
                                    if (map.get("nyuka_id").equals(getNyukaId())) {
                                        Log.e(TAG, "Spinner12       " + map.get("nyuka_id"));
                                        if (attribute.equals("1") && !map.get("lot").equals("")) {
                                            _sts(R.id.lotno, map.get("lot"));
                                        } else if (attribute.equals("2") && !map.get("expiration_date").equals("")) {
                                            _sts(R.id.expirationdate, map.get("expiration_date"));
                                        } else if (attribute.equals("3")) {
                                            if (!map.get("expiration_date").equals(""))
                                                _sts(R.id.expirationdate, map.get("expiration_date"));
                                            if (!map.get("lot").equals(""))
                                                _sts(R.id.lotno, map.get("lot"));
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = "";
                String _month=""+month;
                String _day=day+"";
                if(month < 10){
                    _month = "0" + month;
                }
                if(day < 10){
                    _day  = "0" + day ;
                }
                date = year+_month+_day;
                expiration.setText(date);
                setProc(PROC_QTY);
            }
        };

        directToStockSetting = (this.getDirectToStock())? TRUE1 : FALSE1;

//        classification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                if (classificationIdArray != null && classificationIdArray.size() > position) {
//                    setclassificationId(classificationIdArray.get(position));
//                    Log.e(TAG,"Selected Classification "+classificationIdArray.get(position));
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                setclassificationId("");
//                Log.e(TAG,"Selected Classification"+ getclassificationId() +"null");
//            }
//        });

    }
    //set title and icons on actionbar
    private void getIDs() {

        actionbarImplement(this, "返品入荷", " ", 0, false,false,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);
        relLayout1.setOnClickListener(this);
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

    @OnClick(R.id.enter)void enter(){
        String s = mBuff.toString();
        Log.e("ScannerbindActivity", "Keyput1122 "+mBuff );
        mBuff.delete(0, mBuff.length());
        Log.e("ScannerbindActivity", "Keyput1122 buff "+mBuff );
        inputedEvent(s);
    }

    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
        if(visible==false) {
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
            Log.e(TAG,"SetlayoutMargin");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG,"SetlayoutMargin");
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
        mProcNo = procNo;
        switch (procNo) {
            case PROC_BARCODE:
                Log.e(TAG,"SETPROCCBARCODE");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                lv.setAdapter(null);
                setNyukaId(null);
                if (orderRequestSettings) {
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                }

                break;

            case PROC_QTY:
                Log.e(TAG,"SETPROCQUANTITY");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                if (orderRequestSettings) {
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                }

                break;

            case PROC_LOT_NO:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.lotno).setFocusableInTouchMode(true);
                _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;

            case PROC_EXPIRATION:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.expirationdate).setFocusableInTouchMode(true);

                showTruitonDatePickerDialog(expiration);
                break;
        }
    }

    public void showTruitonDatePickerDialog(View v) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnDismissListener(onCancelListener);



        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (expiration.getText().toString().equalsIgnoreCase("")){
                    setProc(PROC_EXPIRATION);
                }else{
                    setProc(PROC_QTY);
                }

            }
        });


        dialog.show();

    }

    public void inputedEvent(String buff, boolean isScaned) {

        String lot = "";
        switch (mProcNo) {
            case PROC_BARCODE:

                Log.e(TAG,"InputedEventBarcode");

                lv.setAdapter(null);
                setNyukaId(null);
                if (orderRequestSettings) {
                    setKey(1);
                    setProc(PROC_LOT_NO);
                } else {
                    U.beepRecord(this, null);
                }
                Globals.getterList = new ArrayList<>();
                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode",_gts(R.id.barcode)));
                Globals.getterList.add(new ParamsGetter("return_class",Classificationtag));

                mRequestStatus = REQ_BARCODE;
                new MainAsyncTask(this, Globals.Webservice.getStockReturn, 1, ReturnActivity.this, "Form", Globals.getterList,true).execute();

                break;
            case PROC_QTY: // 数量
                Log.e(TAG,"InputedEventQuantity");
                String qty = _gts(R.id.quantity);
                String barcode = _gts(R.id.barcode);

                if (orderRequestSettings){
                    if (attribute.equals("3")) {
                        lotno = _gts(R.id.lotno);
                        expdat = _gts(R.id.expirationdate);
                    } else if (attribute.equals("2"))
                        expdat = _gts(R.id.expirationdate);
                    else if (attribute.equals("1"))
                        lotno = _gts(R.id.lotno);
                 }
                if (isScaned) {

                        if (buff.equals(barcode)) {
                            Log.e(TAG, "IFCONDITION");
                            U.beepSuccess();
                            String val = U.plusTo( _gts(R.id.quantity), "1");
                            _sts(R.id.quantity, val);
                            mTextToSpeak.startSpeaking(val);
                            break;
                        } else {
                            if (Clasificationstatus.equalsIgnoreCase("1")||Clasificationstatus.equalsIgnoreCase("0")){
                                if (Integer.parseInt(qty) > Integer.parseInt(TOTALQTY)) {
                                    ExtraValue =    Integer.parseInt(qty) -Integer.parseInt(TOTALQTY);

                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                                    builder1.setMessage("入荷数量が予定数をオーバーしています。 よろしいですか？");
                                    builder1.setCancelable(false);

                                    builder1.setPositiveButton(
                                            "YES",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();

                                                    new AlertDialog.Builder(ReturnActivity.this, R.style.DialogThemee)
                                                            // .setTitle("Error!")
                                                            .setMessage("入荷予定数より "+ ExtraValue+" 個多く検品しています。")
                                                            .setCancelable(false)
                                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                    Log.e(TAG, "ELSE");
                                                                    stopTimer();
                                                                    isNextBarcode = buff;
                                                                    Log.e(TAG, " isNextBarcode  " + isNextBarcode);
                                                                    Globals.getterList = new ArrayList<>();

                                                                    Log.e(TAG, "shopid  " + BaseActivity.getShopId());
                                                                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                                                                    Globals.getterList.add(new ParamsGetter("barcode", barcode));
                                                                    Globals.getterList.add(new ParamsGetter("qty", qty));
                                                                    Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
                                                                    Globals.getterList.add(new ParamsGetter("lot_no", lotno));
                                                                    Globals.getterList.add(new ParamsGetter("expiration_date", expdat));
                                                                    Globals.getterList.add(new ParamsGetter("move_to_stock", directToStockSetting));
                                                                    Globals.getterList.add(new ParamsGetter("stock_type_id", ""));
                                                                    Globals.getterList.add(new ParamsGetter("nyuka_id", getNyukaId()));
                                                                    mRequestStatus = REQ_QTY;

                                                                    if (BaseActivity.getSupplierList() == true) {
                                                                        Globals.getterList.add(new ParamsGetter("stock_type_id", getsupplierId()));
                                                                        Globals.getterList.add(new ParamsGetter("customer_id", ""));

                                                                    } else if (BaseActivity.getNoSupplierList() == true) {
                                                                        Globals.getterList.add(new ParamsGetter("stock_type_id", ""));
                                                                        Globals.getterList.add(new ParamsGetter("customer_id", getsupplierId()));
                                                                    } else {
                                                                        Globals.getterList.add(new ParamsGetter("stock_type_id", ""));
                                                                        Globals.getterList.add(new ParamsGetter("customer_id", ""));
                                                                    }
                                                                    new MainAsyncTask(ReturnActivity.this, Globals.Webservice.addStockReturn, 1, ReturnActivity.this, "Form", Globals.getterList, true).execute();


                                                                }
                                                            })
                                                            .show();

                                                }
                                            });

                                    builder1.setNegativeButton(
                                            "NO",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();

                                }
                            }else {


                                Log.e(TAG, "ELSE");
                                stopTimer();
                                isNextBarcode = buff;
                                Log.e(TAG, " isNextBarcode  " + isNextBarcode);
                                Globals.getterList = new ArrayList<>();

                                Log.e(TAG, "shopid  " + BaseActivity.getShopId());
                                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                                Globals.getterList.add(new ParamsGetter("barcode", barcode));
                                Globals.getterList.add(new ParamsGetter("qty", qty));
                                Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
                                Globals.getterList.add(new ParamsGetter("lot_no", lotno));
                                Globals.getterList.add(new ParamsGetter("expiration_date", expdat));
                                Globals.getterList.add(new ParamsGetter("move_to_stock", directToStockSetting));
                                Globals.getterList.add(new ParamsGetter("stock_type_id", ""));
                                Globals.getterList.add(new ParamsGetter("nyuka_id", getNyukaId()));
                                mRequestStatus = REQ_QTY;

                                if (BaseActivity.getSupplierList() == true) {
                                    Globals.getterList.add(new ParamsGetter("stock_type_id", getsupplierId()));
                                    Globals.getterList.add(new ParamsGetter("customer_id", ""));

                                } else if (BaseActivity.getNoSupplierList() == true) {
                                    Globals.getterList.add(new ParamsGetter("stock_type_id", ""));
                                    Globals.getterList.add(new ParamsGetter("customer_id", getsupplierId()));
                                } else {
                                    Globals.getterList.add(new ParamsGetter("stock_type_id", ""));
                                    Globals.getterList.add(new ParamsGetter("customer_id", ""));
                                }
                                new MainAsyncTask(this, Globals.Webservice.addStockReturn, 1, ReturnActivity.this, "Form", Globals.getterList, true).execute();

                            }
                            break;

                        }
                    }
//                }
                if ("".equals(qty)||"0".equals(qty)) {
                    Log.e(TAG,"QuantityEmpty");
                    U.beepError(this, "数量は必須です");
                    _gt(R.id.quantity).setFocusableInTouchMode(true);
                    break;
                }

                else if (!U.isNumber(qty)) {
                    Log.e("ReturnNewActivity","NotNumber");
                    U.beepError(this, "数量は数値のみ入力可能です");
                    _gt(R.id.quantity).setFocusableInTouchMode(true);
                    break;
                }else {
                    if (getNyukaId().equalsIgnoreCase("999")) {
                        stopTimer();
                        Globals.getterList = new ArrayList<>();

                        Log.e(TAG, "shopid  " + BaseActivity.getShopId());

                        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                        Globals.getterList.add(new ParamsGetter("barcode", barcode));
                        Globals.getterList.add(new ParamsGetter("qty", qty));
                        Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
                        Globals.getterList.add(new ParamsGetter("lot_no", lotno));
                        Globals.getterList.add(new ParamsGetter("expiration_date", expdat));
                        Globals.getterList.add(new ParamsGetter("move_to_stock", directToStockSetting));
                        Globals.getterList.add(new ParamsGetter("stock_type_id", ""));
                        Globals.getterList.add(new ParamsGetter("nyuka_id", getNyukaId()));
                        Globals.getterList.add(new ParamsGetter("return_class", Classificationtag));

                        mRequestStatus = REQ_QTY;

                        if (BaseActivity.getSupplierList() == true) {
                            Globals.getterList.add(new ParamsGetter("suplier_id", getsupplierId()));
                            Globals.getterList.add(new ParamsGetter("customer_id", ""));
                        } else if (BaseActivity.getNoSupplierList() == true) {
                            Globals.getterList.add(new ParamsGetter("suplier_id", ""));
                            Globals.getterList.add(new ParamsGetter("customer_id", getsupplierId()));
                        } else {
                            Globals.getterList.add(new ParamsGetter("suplier_id", ""));
                            Globals.getterList.add(new ParamsGetter("customer_id", ""));
                        }
                        new MainAsyncTask(this, Globals.Webservice.addStockReturn, 1, ReturnActivity.this, "Form", Globals.getterList, true).execute();
                    } else {

                        if (Clasificationstatus.equalsIgnoreCase("1")||Clasificationstatus.equalsIgnoreCase("0")) {
                            if (TOTALQTY.equalsIgnoreCase("")) {
                                TOTALQTY = "0";
                            }

                            if (Integer.parseInt(qty) > Integer.parseInt(TOTALQTY)) {

                                ExtraValue = Integer.parseInt(qty) - Integer.parseInt(TOTALQTY);

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                                builder1.setMessage("入荷数量が予定数をオーバーしています。 よろしいですか？");
                                builder1.setCancelable(false);

                                builder1.setPositiveButton(
                                        "YES",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();

                                                Alert();
                                                stopTimer();
                                                Globals.getterList = new ArrayList<>();

                                                Log.e(TAG, "shopid  " + BaseActivity.getShopId());

                                                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                                                Globals.getterList.add(new ParamsGetter("barcode", barcode));
                                                Globals.getterList.add(new ParamsGetter("qty", qty));
                                                Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
                                                Globals.getterList.add(new ParamsGetter("lot_no", lotno));
                                                Globals.getterList.add(new ParamsGetter("expiration_date", expdat));
                                                Globals.getterList.add(new ParamsGetter("move_to_stock", directToStockSetting));
                                                Globals.getterList.add(new ParamsGetter("stock_type_id", ""));
                                                Globals.getterList.add(new ParamsGetter("nyuka_id", getNyukaId()));
                                                Globals.getterList.add(new ParamsGetter("return_class", Classificationtag));

                                                mRequestStatus = REQ_QTY;

                                                if (BaseActivity.getSupplierList() == true) {
                                                    Globals.getterList.add(new ParamsGetter("suplier_id", getsupplierId()));
                                                    Globals.getterList.add(new ParamsGetter("customer_id", ""));
                                                } else if (BaseActivity.getNoSupplierList() == true) {
                                                    Globals.getterList.add(new ParamsGetter("suplier_id", ""));
                                                    Globals.getterList.add(new ParamsGetter("customer_id", getsupplierId()));
                                                } else {
                                                    Globals.getterList.add(new ParamsGetter("suplier_id", ""));
                                                    Globals.getterList.add(new ParamsGetter("customer_id", ""));
                                                }
                                                new MainAsyncTask(ReturnActivity.this, Globals.Webservice.addStockReturn, 1, ReturnActivity.this, "Form", Globals.getterList, true).execute();


                                            }
                                        });

                                builder1.setNegativeButton(
                                        "NO",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                            } else {
                                stopTimer();
                                Globals.getterList = new ArrayList<>();

                                Log.e(TAG, "shopid  " + BaseActivity.getShopId());

                                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                                Globals.getterList.add(new ParamsGetter("barcode", barcode));
                                Globals.getterList.add(new ParamsGetter("qty", qty));
                                Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
                                Globals.getterList.add(new ParamsGetter("lot_no", lotno));
                                Globals.getterList.add(new ParamsGetter("expiration_date", expdat));
                                Globals.getterList.add(new ParamsGetter("move_to_stock", directToStockSetting));
                                Globals.getterList.add(new ParamsGetter("stock_type_id", ""));
                                Globals.getterList.add(new ParamsGetter("nyuka_id", getNyukaId()));
                                Globals.getterList.add(new ParamsGetter("return_class", Classificationtag));

                                mRequestStatus = REQ_QTY;

                                if (BaseActivity.getSupplierList() == true) {
                                    Globals.getterList.add(new ParamsGetter("suplier_id", getsupplierId()));
                                    Globals.getterList.add(new ParamsGetter("customer_id", ""));
                                } else if (BaseActivity.getNoSupplierList() == true) {
                                    Globals.getterList.add(new ParamsGetter("suplier_id", ""));
                                    Globals.getterList.add(new ParamsGetter("customer_id", getsupplierId()));
                                } else {
                                    Globals.getterList.add(new ParamsGetter("suplier_id", ""));
                                    Globals.getterList.add(new ParamsGetter("customer_id", ""));
                                }
                                new MainAsyncTask(this, Globals.Webservice.addStockReturn, 1, ReturnActivity.this, "Form", Globals.getterList, true).execute();

                            }
                        } else {
                            stopTimer();
                            Globals.getterList = new ArrayList<>();

                            Log.e(TAG, "shopid  " + BaseActivity.getShopId());

                            Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                            Globals.getterList.add(new ParamsGetter("barcode", barcode));
                            Globals.getterList.add(new ParamsGetter("qty", qty));
                            Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
                            Globals.getterList.add(new ParamsGetter("lot_no", lotno));
                            Globals.getterList.add(new ParamsGetter("expiration_date", expdat));
                            Globals.getterList.add(new ParamsGetter("move_to_stock", directToStockSetting));
                            Globals.getterList.add(new ParamsGetter("stock_type_id", ""));
                            Globals.getterList.add(new ParamsGetter("nyuka_id", getNyukaId()));
                            Globals.getterList.add(new ParamsGetter("return_class", Classificationtag));

                            mRequestStatus = REQ_QTY;

                            if (BaseActivity.getSupplierList() == true) {
                                Globals.getterList.add(new ParamsGetter("suplier_id", getsupplierId()));
                                Globals.getterList.add(new ParamsGetter("customer_id", ""));
                            } else if (BaseActivity.getNoSupplierList() == true) {
                                Globals.getterList.add(new ParamsGetter("suplier_id", ""));
                                Globals.getterList.add(new ParamsGetter("customer_id", getsupplierId()));
                            } else {
                                Globals.getterList.add(new ParamsGetter("suplier_id", ""));
                                Globals.getterList.add(new ParamsGetter("customer_id", ""));
                            }
                            new MainAsyncTask(this, Globals.Webservice.addStockReturn, 1, ReturnActivity.this, "Form", Globals.getterList, true).execute();
                        }
                    }

                }
                break;

            case PROC_LOT_NO:
                lot = _gts(R.id.lotno);



                if (lot.equalsIgnoreCase("") ) {
                U.beepError(this, "ロット番号が必要");
                break;
            }else if (lot.equalsIgnoreCase("0")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else if (lot.equalsIgnoreCase("00")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else if (lot.equalsIgnoreCase("000")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else if (lot.equalsIgnoreCase("0000")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else if (lot.equalsIgnoreCase("00000")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else if (lot.equalsIgnoreCase("000000")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else if (lot.equalsIgnoreCase("0000000")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else if (lot.equalsIgnoreCase("00000000")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else if (lot.equalsIgnoreCase("000000000")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else if (lot.equalsIgnoreCase("0000000000")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else if (lot.equalsIgnoreCase("00000000000")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else if (lot.equalsIgnoreCase("000000000000")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else if (lot.equalsIgnoreCase("0000000000000")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else if (lot.equalsIgnoreCase("00000000000000")){
                U.beepError(this, "ロット番号は0で登録出来ません。");
            }else {


                    setKey(3);
                    U.beepRecord(this, null);
                    if (attribute.equals("3"))
                        if (expiration.getText().toString().equalsIgnoreCase("")) {
                            setProc(PROC_EXPIRATION);
                        } else {
                            setProc(PROC_QTY);
                        }
                    else
                        setProc(PROC_QTY);

                    Globals.getterList = new ArrayList<>();
                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
                    Globals.getterList.add(new ParamsGetter("lot_no", lot));

                    mRequestStatus = REQ_LOT;
//                new MainAsyncTask(this, Globals.Webservice.getStockReturnLotno, 1, ReturnActivity.this, "Form", Globals.getterList,true).execute();

                }
                break;

            case PROC_EXPIRATION:
                lotselect =false;
                String loc = _gts(R.id.expirationdate);
                if ("".equals(loc)) {
                    U.beepError(this, "賞味期限が必要です");
                    _gt(R.id.expirationdate).setFocusableInTouchMode(true);
                    break;
                }
                setProc(PROC_QTY);
                break;
        }
    }
    @Override
    public void inputedEvent(String buff) {
        Log.e(TAG,"inputedEvent");
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        Log.e(TAG,"clearEvent");
        mTextToSpeak.startSpeaking("clear");
        mSelectedItem =0 ;

        nextProcess();
    }

    @Override
    public void allclearEvent() {
        CommonDialogs.customToast(this, "No action");
    }

    @Override
    public void skipEvent() {
        CommonDialogs.customToast(this, "No action");
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_BARCODE:	// バーコード
                Log.e(TAG,"KeyPressEventBarcode");
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                Log.e(TAG,"KeyPressEventQuantity");
                _sts(R.id.quantity, buff);
                break;
            case PROC_LOT_NO: // 数量
                Log.e(TAG,"KeyPressEvenT");
                _sts(R.id.lotno, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        Log.e(TAG,"scannedEvent");

        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEvent");
            if (mProcNo == PROC_BARCODE){
                Log.e(TAG,"Scanned Event url selcted  "+ BaseActivity.getUrl()+ "     Shop selected is  "+ BaseActivity.getShopId());
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode;
                _sts(R.id.barcode, barcode);

            }
        }

        if (mProcNo == PROC_QTY){
            Log.e(TAG,"Scanned Event url selcted  "+ BaseActivity.getUrl()+ "     Shop selected is  "+ BaseActivity.getShopId());
            String finalbarcode = CommonFunctions.getBracode(barcode);
            barcode =finalbarcode;
            Log.e(TAG,"barcode111   "+barcode);
        }

        if (mProcNo == PROC_LOT_NO) _sts(R.id.lotno, barcode);
        this.inputedEvent(barcode, true);


    } public void setAttributeValue(String atr){
        attribute = atr;
        setLayout();
    }

    public void setLayout(){
        if(attribute.equals("0")) {
            layoutlot.setVisibility(View.GONE);
            layoutexpiration.setVisibility(View.GONE);
        } else if(attribute.equals("1")) {
            layoutlot.setVisibility(View.VISIBLE);
            layoutexpiration.setVisibility(View.GONE);
//          addlot = (Button) findViewById(R.id.lot_btn);
        }
        if(attribute.equals("2")) {
            layoutlot.setVisibility(View.GONE);
            layoutexpiration.setVisibility(View.VISIBLE);
            expiration = (EditText) findViewById(R.id.expirationdate);
        }
        if(attribute.equals("3")) {
            layoutlot.setVisibility(View.VISIBLE);
            layoutexpiration.setVisibility(View.VISIBLE);
//          addlot = (Button) findViewById(R.id.lot_btn);
            expiration = (EditText) findViewById(R.id.expirationdate);
        }
    }

    public void ArrivalLotdata(List<Map<String,String>> list){
        mArrivalLot = list;
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        Log.e(TAG,"deleteEvent");
        switch (mProcNo) {
            case PROC_BARCODE:	// バーコード
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.quantity, barcode);
                break;
            case PROC_LOT_NO: // 数量
                _sts(R.id.lotno, barcode);
                break;
        }
    }
    public void getarrivalList( List<Map<String, String>> list){
        mReturnList = list;
        initWorkList();
        ll_list.setVisibility(View.VISIBLE);
     }
    protected void initWorkList() {
        Log.e("NewShippingActivity", "initWorkList");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        ArrayList<Integer> used = new ArrayList<>();

        Log.e(TAG,">>>>>>mBatchList "+ mReturnList.size() + "     "+mReturnList);
        for (int i =0 ; i <= mReturnList.size() - 1; i++) {
            Map<String, String> row = mReturnList.get(i);

             TOTALQTY = mReturnList.get(0).get("qty") ;

            if (row.get("return_class").equals("1")) {
                ll_classification.setVisibility(View.VISIBLE);
                data.add(data.newItem().add(R.id.pck_0, row.get("sup_date"))
                        .add(R.id.pck_1, row.get("order_no"))
                        .add(R.id.pck_2, row.get("comp_name"))
                        .add(R.id.pck_3, row.get("qty"))
                        .add(R.id.pck_4, row.get("lot"))
                        .add(R.id.pck_5, row.get("expiration_date"))
                        .add(R.id.pck_6, row.get("return_class_name"))
                );
                used.add(i);

                adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.arrival_list_rowtwo){
                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {
                        View v = super.getView(position, convertView, parent);
                        if (position == mSelectedItem) {
                            Log.e(TAG,"selected position    "+position);
                            v.setBackgroundColor(Color.YELLOW);
                        }
                        else {
                            if (position % 2 == 1) {
                                Log.e(TAG,"Odd position");
                                v.setBackgroundColor(Color.GRAY);
                            } else {
                                Log.e(TAG,"Even position");
                                v.setBackgroundColor(Color.WHITE);
                            }
                        }


                        return v;
                    }


                };
                lv.setAdapter(adapter);
                // 単一選択モードにする
                lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

            }else{
               TOTALQTY = mReturnList.get(0).get("qty") ;

                ll_classification.setVisibility(View.GONE);


                data.add(data.newItem().add(R.id.pck_0, row.get("sup_date"))
                        .add(R.id.pck_1, row.get("order_no"))
                        .add(R.id.pck_2, row.get("comp_name"))
                        .add(R.id.pck_3, row.get("qty"))
                       /* .add(R.id.pck_4, row.get("lot"))
                        .add(R.id.pck_5, row.get("expiration_date"))
                        .add(R.id.pck_6, row.get("return_class_name"))*/
                );
                used.add(i);

                adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.arrival_list_row){
                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {
                        View v = super.getView(position, convertView, parent);
                        if (position == mSelectedItem) {
                            Log.e(TAG,"selected position  "+position);
                            v.setBackgroundColor(Color.YELLOW);
                        }
                        else {
                            if (position % 2 == 1) {
                                Log.e(TAG,"Odd position");
                                v.setBackgroundColor(Color.GRAY);
                            } else {
                                Log.e(TAG,"Even positionnn");
                                v.setBackgroundColor(Color.WHITE);
                            }
                        }

                        return v;
                    }

                };
                lv.setAdapter(adapter);
                // 単一選択モードにする
                lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            }

        }

        // デフォルト値をセットする
        if (data.getData().size() > 0){
            lv.setItemChecked(0, true);
            lv.setSelection(0);
            setNyukaId(nyukaIdArray.get(0));}
         // return data;

    }

    @Override
    protected void onDestroy() {
        Log.e(TAG,"onDestroy");
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        mTextToSpeak.resetQueue();
        super.onStop();
    }
    @Override
    public void onBackPressed() {
         // super.onBackPressed();
    }
    public static void setKey(int poc) {
        lotpress = poc;
    }

    public String getsupplierId() {
        return this.supplierId;
    }

    public void setsupplierId(String id) {
        this.supplierId = id;
    }

    public void setsupplierIdArray(ArrayList arr) {this.supplierIdArray = arr;}

    public static int getKey() {
        return lotpress;
    }

    public void setNyukaId(String id){
        this.nyukaId1 = id;
    }

    public String getNyukaId(){
        return this.nyukaId1;
    }

    public void setNyukaIdArray(ArrayList arr){ this.nyukaIdArray = arr;}

    public String getclassificationId() {
        return this.classificationId;
    }

    public void setclassificationId(String id) {this.classificationId= id;}

    public void setclassificationIdArray(ArrayList arr) {this.classificationIdArray = arr;}

    public void nextProcess() {
        mSelectedItem =0 ;
        Log.e(TAG,"nextProcess");
        _sts(R.id.barcode, "");
        _sts(R.id.quantity, "");
        _stxtv(R.id.productName, "");
        ll_list.setVisibility(View.GONE);

   //   _sts(R.id.productCode, "");
        /*Classificationtag = "0";
        goodproduct.performClick();*/

        Log.e(TAG, "orderRequestSettings   "+orderRequestSettings);
        if (orderRequestSettings){
            _sts(R.id.expirationdate, "");
            _sts(R.id.lotno, "");
            layoutexpiration.setVisibility(View.VISIBLE);
            layoutlot.setVisibility(View.VISIBLE);

        } else {
            layoutexpiration.setVisibility(View.GONE);
            layoutlot.setVisibility(View.GONE);
        }
        nyukaIdArray = null;
        classificationIdArray = null;
//      classification.setAdapter(null);
        setProc(PROC_BARCODE);
        returnfromspinner.setVisibility(View.GONE);
        layoutspinner.setVisibility(View.GONE);

        if(showKeyboard==false) {
            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
//          Animation bottomDown = AnimationUtils.loadAnimation(this,
//          R.anim.bottom_down);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
//          hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG, "SetlayoutMargin");
            mainlayout.setLayoutParams(params);
        }

        progress.Show();
        GetReturnClassificationReq req = new GetReturnClassificationReq(app.getSerial(), BaseActivity.getShopId(),adminID,"");
        manager.GetReturnClassification(req,getcassification);
    }

     @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {
        HashMap<String, String> mHash=new HashMap<>();
        Log.e(TAG,result.toString());

        try {

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
                Log.e(TAG,"CODE====Null");
                CommonDialogs.customToast(getApplicationContext(),"Network error occured");

            }
            if ("0".equals(code) ) {

                Log.e("SendLogs111",code+"  "+msg+"  "+result1);

                if(mRequestStatus == REQ_BARCODE) {
                    new GetReturnNew().post(code,msg,result1, mHash, ReturnActivity.this);
                } else if(mRequestStatus == REQ_LOT) {
                    new GetReturnLot().post(code,msg,result1, mHash, ReturnActivity.this);
                } else if(mRequestStatus == REQ_QTY){
                    U.beepKakutei(this, "検品データを登録しました。");



                    nextProcess();
                    if(!isNextBarcode.equals(""))
                       scanedEvent(isNextBarcode);
                    isNextBarcode = "";
                } else if(mRequestStatus ==REQ_INITIAL) {

                }
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(ReturnActivity.this, LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })
                         .show();

            } else{
                if(mRequestStatus == REQ_BARCODE) {
                    new GetReturnNew().valid(code,msg,result1, mHash, ReturnActivity.this);
                }
                if(mRequestStatus == REQ_LOT) {
                    new GetReturnLot().valid(code,msg,result1, mHash, ReturnActivity.this);
                } else if(mRequestStatus == REQ_QTY){
                    U.beepError(this, msg);
                }
                else if(mRequestStatus ==REQ_INITIAL) {
                    U.beepError(this,msg);
                    setProc(PROC_BARCODE);
                }
            }

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }

    @OnClick(R.id.goodproduct) void good(){
        Classificationtag = "0";
        badproduct.setChecked(false);
        if (barcode.getText().toString().equals("")){

        }else{
            Globals.getterList = new ArrayList<>();
            Globals.getterList.add(new ParamsGetter("admin_id",adminID));
            Globals.getterList.add(new ParamsGetter("barcode",_gts(R.id.barcode)));
            Globals.getterList.add(new ParamsGetter("return_class",Classificationtag));

            mRequestStatus = REQ_BARCODE;
            new MainAsyncTask(this, Globals.Webservice.getStockReturn, 1, ReturnActivity.this, "Form", Globals.getterList,true).execute();

        }
    }

    @OnClick(R.id.badproduct) void badd(){
        Classificationtag = "1";
        goodproduct.setChecked(false);
        if (barcode.getText().toString().equals("")){

        }else{
            Globals.getterList = new ArrayList<>();
            Globals.getterList.add(new ParamsGetter("admin_id",adminID));
            Globals.getterList.add(new ParamsGetter("barcode",_gts(R.id.barcode)));
            Globals.getterList.add(new ParamsGetter("return_class",Classificationtag));

            mRequestStatus = REQ_BARCODE;
            new MainAsyncTask(this, Globals.Webservice.getStockReturn, 1, ReturnActivity.this, "Form", Globals.getterList,true).execute();

        }
    }

    @Override
    public void onSucess(int status, GetClassificationResult message) throws JsonIOException {
           progress.Dismiss();
        if (message.getCode().equals("0")){
            Clasificationstatus= message.getReturn_class();
            if (message.getReturn_class().equals("1")){
                ll_classification.setVisibility(View.VISIBLE);
            }else{
                ll_classification.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onError(int status, ResponseBody error) { progress.Dismiss(); }

    @Override
    public void onFaliure() { progress.Dismiss();}

    @Override
    public void onNetworkFailure() { progress.Dismiss(); }


    public void Alert(){
        new AlertDialog.Builder(this, R.style.DialogThemee)
                // .setTitle("Error!")
                .setMessage("入荷予定数より "+ ExtraValue+" 個多く検品しています。")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .show();
    }
}
