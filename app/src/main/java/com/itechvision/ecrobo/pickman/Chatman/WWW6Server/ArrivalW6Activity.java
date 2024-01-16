package com.itechvision.ecrobo.pickman.Chatman.WWW6Server;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.AddArrival;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetArrival;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.StockClassification.StockClassificationListData;
import com.itechvision.ecrobo.pickman.Models.NyukaArrival.NyukaSelectReq;
import com.itechvision.ecrobo.pickman.Models.NyukaArrival.NyukaSelectResponse;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.AddArrival.AddArrivalReq;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.AddArrival.AddArrivalResponse;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetArrival.ArrivalData;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetArrival.GetArrivalReq;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetArrival.GetArrivalResponse;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetArrivalOrderNo.GetArrivalOrderReq;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetArrivalOrderNo.GetArrivalOrderResponse;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetOrderArrival.GetArrivalBarcodeOrderNoReq;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetOrderArrival.GetArrivalBarcodeOrderNoResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.vvakame.util.jsonpullparser.util.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

public class ArrivalW6Activity extends BaseActivity implements View.OnClickListener,DataManager.GetArrivalOrderNocall, DataManager.GetArrivalcall, DataManager.Nyukacallback,  DataManager.AddArrivalcall , DataManager.GetArrivalBarcodeOrderNocall {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.gridExpiration)
    LinearLayout layoutexpiration;
    @BindView(R.id.gridLot)
    LinearLayout layoutlot;
    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;
    @BindView(R.id.lotno)
    EditText lot;
    @BindView(R.id.add_layout)
    Button numbrbtn;
    @BindView(R.id.expirationdate)
    EditText expiration;
    @BindView(R.id.layout_main)
    RelativeLayout mainlayout;
    @BindView(R.id.layout_number)
    RelativeLayout layout;
//    @BindView(R.id.listArrival)
//    ListView lv;

//    @BindView(R.id.spinnerlayout)
//    RelativeLayout spinnerLayout;

    @BindView(R.id.pck_0)
    TextView pck0;
    @BindView(R.id.pck_1)
    TextView pck1;
    @BindView(R.id.pck_2)
    TextView pck2;
    @BindView(R.id.pck_3)
    TextView pck3;
    @BindView(R.id.ll_listdialog)
    LinearLayout ll_listdialog;
    @BindView(R.id.arrivalID)
    EditText arrivalID;
    @BindView(R.id.orderNoChkbox)
    CheckBox orderNoChkbox;


    public static int nyukacount = 0;


    int mSelectedItem = 0;
    public boolean lotexist = false;
    public TextView productName;
    protected ArrayList<String> nyukaIdArray = new ArrayList<String>();
    protected ArrayList<String> classificationIdArray = new ArrayList<String>();


    public static List<Map<String, String>> mmultiArrivalList = new ArrayList<Map<String, String>>();

    protected Map<String, String> mStockList = null;
    PopupWindow popupWindow;
    public static int count = 0;
    public Context mcontext = this;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    String TAG = ArrivalW6Activity.class.getSimpleName();

    private TextToSpeak mTextToSpeak;
    private boolean arrivalScheduleSelected = false;
    private boolean showKeyboard;

    public String nyukaId = null;
    private boolean orderRequestSettings;
    private static final String TRUE = "TRUE";
    private static final String FALSE = "FALSE";
    private String directToStockSetting = "FALSE";
    // public String classificationId = "";
    private boolean submission = false;
    public String selectedUrl = null;
    public String quantity = "0";
    public boolean barchnge = false;

    protected int mProcNo = 0;
    public static final int PROC_ORDER_NO = 1;
    public static final int PROC_BARCODE = 2;
    public static final int PROC_QTY = 3;
    public static final int PROC_LOT_NO = 4;
    public static final int PROC_EXPIRATION = 5;


    public static int mRequestStatus = 0;

    public static final int REQ_BARCODE = 2;
    public static final int REQ_LOT = 3;
    public static final int REQ_QTY = 4;
    String adminID = "";
    String attribute = "0";
    private boolean visible = false;
    ListViewAdapter adapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public static boolean mNextBarcode = false;
    public String isNextBarcode = "", nyukaAdmin = "";
    Map<String, String> row1;
    public ListView listview;
    Dialog dialogList, dialog2;
    View dialogView;
    AlertDialog alertDialog;
    ECRApplication app = new ECRApplication();
    SweetAlertDialog sweetAlertDialog;

    ArrayList<ArrivalData> productList = new ArrayList<>();
    ArrayList<StockClassificationListData> stockList;
    ArrayList<String> classificationdata = new ArrayList<String>();


    DataManager manager;
    progresBar progress;

    DataManager.Nyukacallback nyukacallback;
    String msg = "", code = "", ProductID = "", Weightt = "";
    JsonArray result1;
    HashMap<String, String> mHash;
    DataManager.ArrivalWeightcall weightcall;
    ImageView close;

    private DataManager.GetArrivalOrderNocall getArrivalOrderNocall;
    private DataManager.GetArrivalcall getArrivalcall;
    private DataManager.AddArrivalcall addArrivalcall;
    private DataManager.GetArrivalBarcodeOrderNocall getArrivalBarcodeOrderNocall;
    private DataManager.StockClassificationcall stockClassificationcall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival_w6);

        ButterKnife.bind(ArrivalW6Activity.this);

        getIDs();
        dialog2 = new Dialog(ArrivalW6Activity.this);

        Log.d(TAG,"On Create ");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        orderRequestSettings = BaseActivity.getLotPress();

        Log.e(TAG, "orderRequestSettings " + BaseActivity.getLotPress());
        productName = (TextView) findViewById(R.id.productName);
        nyukacallback = this;
        getArrivalOrderNocall = this;
        getArrivalcall = this;
        addArrivalcall = this;
        getArrivalBarcodeOrderNocall = this;

        arrivalScheduleSelected = BaseActivity.getArrivalNyuka();



        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);

        directToStockSetting = TRUE;

        showKeyboard = BaseActivity.getaddKeyboard();
        if (showKeyboard == true) {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
            visible = true;
            numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layout.getLayoutParams();
            param.setMargins(0, top, 0, 0);
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        mTextToSpeak = new TextToSpeak(this);
        Log.e(TAG, "OnCreate");

        if (mProcNo == 0) nextProcess();



        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = "";
                String _month = "" + month;
                String _day = day + "";
                if (month < 10) {
                    _month = "0" + month;
                }
                if (day < 10) {
                    _day = "0" + day;
                }
                date = year + _month + _day;

                expiration.setText(date);
                setProc(PROC_QTY);
            }
        };

        orderNoChkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mProcNo<= PROC_BARCODE){
                if(isChecked)
                {
                   _sts(R.id.arrivalID,"");
                   setProc(PROC_BARCODE);
                }
                else {
                    _sts(R.id.barcode,"");
                    setProc(PROC_ORDER_NO);
                }
            }}
        });

    }

    //set title and icons on actionbar
    private void getIDs() {
        actionbarImplement(this, "入荷検品DM", " ",
                0, false, false, false);

        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);

        manager = new DataManager();
        progress = new progresBar(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;

            case R.id.notif_count_red:
                if (getBadge2() != 0) {
//                        showOrders();
                }
            default:
                break;
        }
    }

    @OnClick(R.id.enter)
    void enter() {
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
    }

    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
        if (visible == false) {

            visible = true;
            numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layout.getLayoutParams();
            param.setMargins(0, top, 0, 0);
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;
            numbrbtn.setText("キーボードを表示する");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
    }

    public int convert(int x) {
        Resources r = mcontext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                x, r.getDisplayMetrics());
        return px;
    }

    public void setNyukaId(String id) {
        this.nyukaId = id;
    }

    public String getNyukaId() {
        return this.nyukaId;
    }

    public void setNyukaIdArray(ArrayList arr) {
        this.nyukaIdArray = arr;
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {

            case PROC_ORDER_NO:
                _gt(R.id.arrivalID).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.arrivalID).setFocusableInTouchMode(true);
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));


                if (orderRequestSettings == true) {
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                }
                break;

            case PROC_BARCODE:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.arrivalID).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                /*spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                lv.setAdapter(null);*/
                setNyukaId(null);
                if (orderRequestSettings == true) {
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                }
                break;

            case PROC_QTY:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);
                if (orderRequestSettings == true) {
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                }
                if (_gts(R.id.quantity).equals("1"))
                    mTextToSpeak.startSpeaking("1");
                break;
            case PROC_LOT_NO:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.lotno).setFocusableInTouchMode(true);
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

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    public void inputedEvent(String buff, boolean isScaned) {
        String lot = "";
        switch (mProcNo) {
            case PROC_ORDER_NO:
                String orderId = _gts(R.id.arrivalID);

                if (orderId.equals("") || orderId.equals("0")) {
                    U.beepError(this, "箱IDは必須です。");
                    break;
                }


                if (!CommonUtilities.getConnectivityStatus(this))
                    CommonUtilities.openInternetDialog(this);
                else {
                    progress.Show();
                    GetArrivalOrderReq req = new GetArrivalOrderReq(adminID, app.getSerial(), BaseActivity.getShopId(),orderId);
                    manager.GetArrivalOrderNoAPI(req, getArrivalOrderNocall);
                }

                break;
            case PROC_BARCODE:    // バーコード
                if (dialog2.isShowing()) {
                } else {
                    String barcode1 = _gts(R.id.barcode);
                    submission = false;
                    setNyukaId(null);
                    U.beepRecord(this, null);


                    if(orderNoChkbox.isChecked()){
                        if (!CommonUtilities.getConnectivityStatus(ArrivalW6Activity.this))
                            CommonUtilities.openInternetDialog(ArrivalW6Activity.this);
                        else {
                            progress.Show();
                            GetArrivalReq req = new GetArrivalReq(adminID, app.getSerial(), BaseActivity.getShopId(), barcode1, _gts(R.id.arrivalID));
                            manager.GetArrivalAPI(req, getArrivalcall);

                        }
                    }

                    else{
                        if (!CommonUtilities.getConnectivityStatus(ArrivalW6Activity.this))
                            CommonUtilities.openInternetDialog(ArrivalW6Activity.this);
                        else {
                            progress.Show();

                            GetArrivalBarcodeOrderNoReq req = new GetArrivalBarcodeOrderNoReq(adminID, app.getSerial(), BaseActivity.getShopId(), barcode1, _gts(R.id.arrivalID));
                            manager.GetArrivalBarcodeOrderNoAPI(req, getArrivalBarcodeOrderNocall);

                        }
                    }

                  /*  if (orderRequestSettings == true) {
                        setProc(PROC_LOT_NO);
                        lotselect = true;
                    } else {
                        U.beepRecord(this, null);
                        setProc(PROC_QTY);
                    }*/



                    barchnge = false;
                }
                break;
            case PROC_LOT_NO:    // バーコード
                if (dialog2.isShowing()) {

                } else {
                    lot = _gts(R.id.lotno);
                    if (lot.equals("") || lot.equals("0")) {
                        U.beepError(this, "ロット番号が必要");
                        break;
                    }

                    U.beepRecord(this, null);
                    if (attribute.equals("3"))
                        setProc(PROC_EXPIRATION);
                    else
                        setProc(PROC_QTY);
                }
                break;
            case PROC_EXPIRATION:
                if (dialog2.isShowing()) {

                } else {

                    String loc = _gts(R.id.expirationdate);
                    if ("".equals(loc)) {
                        U.beepError(this, "賞味期限が必要です");
                        _gt(R.id.expirationdate).setFocusableInTouchMode(true);
                        break;
                    }
                    setProc(PROC_QTY);
                }
                break;
            case PROC_QTY: // 数量

                if (dialog2.isShowing()) {

                } else {

                    String qty = _gts(R.id.quantity);
                    if (qty.equals(""))
                        qty = "1";
                    String barcode = _gts(R.id.barcode);
                    String lotno = "";
                    String expdate1 = "";

                    if (orderRequestSettings == true) {
                        lotno = _gts(R.id.lotno);
                        expdate1 = _gts(R.id.expirationdate);
                    }

                    if (isScaned) {

                        if (buff.equals(barcode)) {
                            U.beepSuccess();
                            qty = U.plusTo(qty, "1");
                            _sts(R.id.quantity, qty);
                            mTextToSpeak.startSpeaking(_gts(R.id.quantity));

                            break;
                        } else {

                            int days = 0;
                            if (orderRequestSettings && (attribute.equals("2") || attribute.equals("3"))) {
                                if (productList.get(0).getArrival_exp_flag().equals("1")) {

                                    try {
                                        String date = reverseDate(expdate1);
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                        Date expirationdate = sdf.parse(date);
                                        Log.e(TAG, "expirationdateeeeeee     " + expirationdate);

                                        String formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                        Log.e(TAG, "currentdateeee      " + formattedDate);

                                        Date arrivaldate = sdf.parse(formattedDate);
                                        Log.e(TAG, "currentdateeee 111111     " + arrivaldate);
                                        days = U.getDaysDifference(arrivaldate, expirationdate);
                                        Log.e(TAG, "difff     " + days);
                                    } catch (Exception e) {
                                        Log.e(TAG, "Exceptionnnnn     " + e);
                                    }
                                    if (!productList.get(0).getArrival_exp_days().equals("")) {
                                        String exp_days = productList.get(0).getArrival_exp_days();
                                        Log.e(TAG, "exp_days     " + exp_days);
                                        if (U.compareNumeric(exp_days, days + "") == -1) {
                                            if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {

                                            } else {

                                                //DIALOG  WEIGHT
                                                sweetAlertDialog = new SweetAlertDialog(this);

                                                sweetAlertDialog.setTitleText("入庫期限が切れています。続行しますか？")
                                                        .setConfirmText("Yes")
                                                        .setCancelText("No")
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                stopTimer();
                                                                submission = true;
//DIALOG


                                                                if (count == 0) {
                                                                    sendRequest(_gts(R.id.barcode), _gts(R.id.quantity), _gts(R.id.lotno), _gts(R.id.expirationdate));
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Wait", Toast.LENGTH_SHORT).show();
                                                                }
                                                                sweetAlertDialog.dismiss();
                                                            }
                                                        });
                                                sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        clearEvent();
                                                        sweetAlertDialog.dismiss();
                                                    }
                                                });

                                                sweetAlertDialog.show();
                                            }
                                            break;
                                        }
                                    }
                                }
                            }

                            stopTimer();
                            mNextBarcode = true;
                            isNextBarcode = buff;

                            //DIALOG

                            if (count == 0) {
                                sendRequest(barcode, qty, lotno, expdate1);
                            } else {
                                Toast.makeText(getApplicationContext(), "Wait", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }

                    if ("".equals(qty) || "0".equals(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }


                    int days = 0;
                    if (orderRequestSettings && (attribute.equals("2") || attribute.equals("3"))) {
                        if (productList.get(0).getArrival_exp_flag().equals("1")) {

                            try {
                                String date = reverseDate(expdate1);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                Date expirationdate = sdf.parse(date);
                                String formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                                Date arrivaldate = sdf.parse(formattedDate);

                                days = U.getDaysDifference(arrivaldate, expirationdate);

                            } catch (Exception e) {
                                Log.e(TAG, "Exceptionnnnn     " + e);
                            }
                            if (!productList.get(0).getArrival_exp_days().equals("")) {
                                String exp_days = productList.get(0).getArrival_exp_days();
                                Log.e(TAG, "exp_days     " + exp_days);
                                if (U.compareNumeric(exp_days, days + "") == -1) {
                                    if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {
                                        // A dialog is already open, wait for it to be dismissed, do nothing
                                    } else {
                                        sweetAlertDialog = new SweetAlertDialog(this);

                                        sweetAlertDialog.setTitleText("入庫期限が切れています。続行しますか？")
                                                .setConfirmText("Yes")
                                                .setCancelText("No")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        stopTimer();
                                                        submission = true;

                                                        if (count == 0) {
                                                            sendRequest(_gts(R.id.barcode), _gts(R.id.quantity), _gts(R.id.lotno), _gts(R.id.expirationdate));
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Wait", Toast.LENGTH_SHORT).show();
                                                        }
                                                        sweetAlertDialog.dismiss();
                                                    }
                                                });
                                        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                clearEvent();
                                                sweetAlertDialog.dismiss();
                                            }
                                        });

                                        sweetAlertDialog.show();
                                    }
                                    break;
                                }
                            }
                        }
                    }

                    stopTimer();
                    submission = true;

                    if (count == 0) {
                        sendRequest(barcode, qty, lotno, expdate1);
                    } else {
                        Toast.makeText(getApplicationContext(), "Wait", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }
    }


    void sendRequest(String barcode, String qty, String lotno, String expdate) {
        ++count;

        if (getNyukaId() != null) {

            if (!CommonUtilities.getConnectivityStatus(this))
                CommonUtilities.openInternetDialog(this);
            else {
                progress.Show();
                 AddArrivalReq req = new AddArrivalReq(adminID, app.getSerial(), BaseActivity.getShopId(), _gts(R.id.arrivalID), productList.get(mSelectedItem).getProduct_id(), getNyukaId() , _gts(R.id.quantity), lotno, expdate, getResources().getString(R.string.version), timeTaken().toString());
                manager.AddArrivalAPI(req, addArrivalcall);
            }

            } else
            U.beepError(this, "Select nyuka");
    }


    public void setAttributeProc() {

        if (attribute.equals("0")) {
            setProc(PROC_QTY);
        } else if (attribute.equals("1")) {
            if (_gts(R.id.lotno).equals(""))
                setProc(PROC_LOT_NO);
            else
                setProc(PROC_QTY);
            lotexist = true;
        } else if (attribute.equals("2")) {
            if (_gts(R.id.expirationdate).equals("")) {
                setProc(PROC_EXPIRATION);
            } else {
                showPopup("入荷指示に消費期限が含まれています。");
                setProc(PROC_QTY);
            }
        } else if (attribute.equals("3")) {

            if (_gts(R.id.lotno).equals(""))
                setProc(PROC_LOT_NO);
            else if (_gts(R.id.expirationdate).equals(""))
                setProc(PROC_EXPIRATION);
            else {
                showPopup("入荷指示に消費期限が含まれています。");
                setProc(PROC_QTY);
            }
            lotexist = true;
        }

        _sts(R.id.quantity, "1");
        quantity = "1";
        //  setNyukaIdArray(nyukaIdArray);

    }

    // is product using lot no or expiration date
    public void setAttributeValue(String atr) {
        attribute = atr;
        setLayout();
    }

    public void setLayout() {
        if (attribute.equals("0")) {
            layoutlot.setVisibility(View.GONE);
            layoutexpiration.setVisibility(View.GONE);
        } else if (attribute.equals("1")) {
            layoutlot.setVisibility(View.VISIBLE);
            layoutexpiration.setVisibility(View.GONE);
        } else if (attribute.equals("2")) {
            layoutlot.setVisibility(View.GONE);
            layoutexpiration.setVisibility(View.VISIBLE);
            expiration = (EditText) findViewById(R.id.expirationdate);
        } else if (attribute.equals("3")) {
            layoutlot.setVisibility(View.VISIBLE);
            layoutexpiration.setVisibility(View.VISIBLE);
            expiration = (EditText) findViewById(R.id.expirationdate);
        }
    }

    @Override
    public void inputedEvent(String buff) {

        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
//      _sts(id.orderNo,"");
        nextProcess();
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
            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.quantity, buff);
                break;
            case PROC_ORDER_NO: // 数量
                arrivalID.setText(buff);
                break;
            case PROC_LOT_NO:
                _sts(R.id.lotno, buff);
                break;
            case PROC_EXPIRATION:
                _sts(R.id.expirationdate, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {

        Log.e(TAG, "ScannedEvent   submission is " + submission);

        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEvent");

            if (mProcNo == PROC_BARCODE) {
                // check for QR code
                _sts(R.id.barcode, barcode);
            }

            if (mProcNo == PROC_ORDER_NO) {
                arrivalID.setText(barcode);
            }

            if (mProcNo == PROC_LOT_NO)
                _sts(R.id.lotno, barcode);
        }
        this.inputedEvent(barcode, true);

    }

    @Override
    public void enterEvent() {
        Log.e(TAG, "Enterrererere event.......");
        if (mProcNo == PROC_QTY) {
            String qty = _gts(R.id.quantity);
            String barcode = _gts(R.id.barcode);
            String lotno = "";
            String expdate = "";
            if (orderRequestSettings == true) {
                lotno = _gts(R.id.lotno);
                expdate = _gts(R.id.expirationdate);
            }

            sendRequest(barcode, qty, lotno, expdate);

        }
    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {

            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, barcode);
                break;
            case PROC_ORDER_NO:    // バーコード
                arrivalID.setText(barcode);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.quantity, barcode);
                break;
            case PROC_LOT_NO: // 数量
                _sts(R.id.lotno, barcode);
                break;
            case PROC_EXPIRATION: // 数量
                _sts(R.id.lotno, barcode);
                break;
        }
    }

    public void nextProcess() {
        Log.e(TAG, "nexttttPRocess");
        _sts(R.id.arrivalID,"");
        _sts(R.id.barcode, "");
        _sts(R.id.quantity, "");
        _stxtv(R.id.productName, "");
        _sts(R.id.Remarks, "");
        _sts(R.id.Standard1, "");
        _sts(R.id.Standard2, "");
        _sts(R.id.lotno, "");
        _sts(R.id.expirationdate, "");

        lotexist = false;
        quantity = "0";

        if(orderNoChkbox.isChecked())
            setProc(PROC_BARCODE);
        else
            setProc(PROC_ORDER_NO);

        setBadge2(0);

        pck0.setText("");
        pck1.setText("");
        pck2.setText("");
        pck3.setText("");

        nyukaIdArray = null;

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_arrival);
        layout.setBackgroundResource(R.color.white);

        layoutlot.setVisibility(View.GONE);
        layoutexpiration.setVisibility(View.GONE);
        dialog2 = new Dialog(ArrivalW6Activity.this);
    }

    public void getarrivalList() {

        Log.e(TAG, "aaaaaaaaaaaaeeeeeeeeeeee 1111      " + getNyukaId());

        setNyukaId(nyukaIdArray.get(0));

        if (arrivalScheduleSelected && productList.size() > 1)
            productList.remove(productList.size() - 1);


        if (getNyukaId().equals("999") && arrivalScheduleSelected) {
            showPopup("入荷予定がありません。");
            U.beepError(ArrivalW6Activity.this, null);
            _sts(R.id.barcode, "");
            setProc(PROC_BARCODE);
        } else {
            _sts(R.id.Remarks, productList.get(0).getComment());
            _sts(R.id.Standard1, productList.get(0).getSpec1());
            _sts(R.id.Standard2, productList.get(0).getSpec2());

            pck0.setText(productList.get(0).getRsv_date());
            pck1.setText(productList.get(0).getOrder_no());
            pck2.setText(productList.get(0).getComp_name());
            pck3.setText(U.minusTo(productList.get(0).getRsv_cnt(), productList.get(0).getAct_cnt()));

            mSelectedItem = 0;


            if (getNyukaId().equals("999")) {

                ll_comment.setVisibility(View.GONE);
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
                ll_comment.setVisibility(View.VISIBLE);
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_arrival);
                layout.setBackgroundResource(R.color.white);
                if (BaseActivity.getLotPress()) {
                    if (!attribute.equals("0")) {
                        for (int i = 0; i > productList.size(); i++) {
                            if (productList.get(i).getNyuka_id().equals(getNyukaId())) {
                                if (attribute.equals("1") && !productList.get(i).getLot().equals("")) {
                                    _sts(R.id.lotno, productList.get(i).getLot());
                                } else if (attribute.equals("2") && !productList.get(i).getExpiration_date().equals("")) {
                                    if (arrivalScheduleSelected) {
                                        if (productList.size() == 1)
                                            showPopup("入荷指示に消費期限が含まれています。");
                                    } else {
                                        if (productList.size() == 2)
                                            showPopup("入荷指示に消費期限が含まれています。");
                                    }

                                    _sts(R.id.expirationdate, productList.get(i).getExpiration_date());
                                } else if (attribute.equals("3")) {

                                    if (!productList.get(i).getExpiration_date().equals("")) {
                                        if (arrivalScheduleSelected) {
                                            if (productList.size() == 1)
                                                showPopup("入荷指示に消費期限が含まれています。");
                                        } else {
                                            if (productList.size() == 2)
                                                showPopup("入荷指示に消費期限が含まれています。");
                                        }
                                        _sts(R.id.expirationdate, productList.get(i).getExpiration_date());
                                    }
                                    if (!productList.get(i).getLot().equals(""))
                                        _sts(R.id.lotno, productList.get(i).getLot());
                                }
                            }
                        }
                    }
                }
            }

            if(orderNoChkbox.isChecked()) {
                if (arrivalScheduleSelected) {
                    if (productList.size() > 1)
                        listviewDialog();
                } else {
                    if (productList.size() > 2)
                        listviewDialog();
                }
            }

            else{
                if (productList.size() > 1)
                    listviewDialog();
            }

            if (!productList.get(0).getAdmin_id().equals("") && !productList.get(0).getAdmin_id().equals(adminID))
                showPopup("他のユーザーが作業中です。");
        }
    }

      /*  public void getmultiPartarrivalList(List<Map<String, String>> list, boolean multi, List<String> part) {
            Log.e(TAG, "getmultiPartarrivalList    " + mmultiArrivalList);
            mmultiArrivalList = list;
            multicode = multi;
            PartnoArray = (ArrayList<String>) part;
        }*/


    public String reverseDate(String date) {

        String yr = date.substring(0, 4);
        Log.e(TAG, " year    " + yr);
        String mon = date.substring(4, 6);
        Log.e(TAG, " month    " + mon);
        String _d = date.substring(6);
        Log.e(TAG, " _d    " + _d);
        String result = _d + "-" + mon + "-" + yr;

        return result;
    }

    public void showPopup(String msg) {
        new SweetAlertDialog(this)
                .setTitleText(msg)
                .show();
    }

    public void listviewDialog() {

        Rect displayRectangle = new Rect();
        Window window = ArrivalW6Activity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        AlertDialog.Builder builder = new AlertDialog.Builder(ArrivalW6Activity.this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.custom, viewGroup, false);
        dialogView.setMinimumWidth((int) (displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int) (displayRectangle.height() * 1f));

        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        listview = (ListView) dialogView.findViewById(R.id.listview);

        ImageView close = (ImageView) dialogView.findViewById(R.id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "asdfghjrtgvyfyhjvn   1111111  ");
                alertDialog.dismiss();
            }
        });

        initWorkListDialog();

        alertDialog.show();
    }

    protected void initWorkListDialog() {
        listview.setAdapter(null);
        ListViewItems data = new ListViewItems();
        ArrayList<Integer> used = new ArrayList<>();


        for (int i = 0; i <= productList.size() - 1; i++) {

            data.add(data.newItem().add(R.id.pck_0, productList.get(i).getRsv_date())
                    .add(R.id.pck_1, productList.get(i).getOrder_no())
                    .add(R.id.pck_2, productList.get(i).getComp_name())
                    .add(R.id.pck_3, U.minusTo(productList.get(i).getRsv_cnt(), productList.get(i).getAct_cnt()))
            );

            used.add(i);
        }

        adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.arrival_list_row_dialog) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == mSelectedItem) {
                    v.setBackgroundColor(Color.YELLOW);
                } else {
                    if (position % 2 == 1) {
                        v.setBackgroundColor(Color.GRAY);
                    } else {
                        v.setBackgroundColor(Color.WHITE);
                    }
                }
                return v;
            }
        };
        listview.setAdapter(adapter);

        // 単一選択モードにする
        listview.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "asdfghjknmxcvbn   ");

                _sts(R.id.Remarks, productList.get(position).getComment());
                _sts(R.id.Standard1, productList.get(position).getSpec1());
                _sts(R.id.Standard2, productList.get(position).getSpec2());

                pck0.setText(productList.get(position).getRsv_date());
                pck1.setText(productList.get(position).getOrder_no());
                pck2.setText(productList.get(position).getComp_name());
                pck3.setText(U.minusTo(productList.get(position).getRsv_cnt(), productList.get(position).getAct_cnt()));

                mSelectedItem = position;

                if (nyukaIdArray != null && nyukaIdArray.size() > position) {
                    setNyukaId(nyukaIdArray.get(position));

                    if (getNyukaId().equals("999")) {

                        ll_comment.setVisibility(View.GONE);
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
                        ll_comment.setVisibility(View.VISIBLE);
                        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_arrival);
                        layout.setBackgroundResource(R.color.white);
                        if (BaseActivity.getLotPress()) {
                            if (!attribute.equals("0")) {
                                for (int i = 0; i > productList.size(); i++) {
                                    if (productList.get(i).getNyuka_id().equals(getNyukaId())) {
                                        if (attribute.equals("1") && !productList.get(i).getLot().equals("")) {
                                            _sts(R.id.lotno, productList.get(i).getLot());
                                        } else if (attribute.equals("2") && !productList.get(i).getExpiration_date().equals("")) {
                                            showPopup("入荷指示に消費期限が含まれています。");
                                            _sts(R.id.expirationdate, productList.get(i).getExpiration_date());
                                        } else if (attribute.equals("3")) {

                                            if (!productList.get(i).getExpiration_date().equals("")) {
                                                showPopup("入荷指示に消費期限が含まれています。");
                                                _sts(R.id.expirationdate, productList.get(i).getExpiration_date());
                                            }
                                            if (!productList.get(i).getLot().equals(""))
                                                _sts(R.id.lotno, productList.get(i).getLot());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                alertDialog.dismiss();
                if (position == 0) {
                    if (!productList.get(position).getAdmin_id().equals("") && !productList.get(position).getAdmin_id().equals(adminID))
                        showPopup("予定外入荷です。");
                } else
                    sendNyukaRequest(nyukaIdArray.get(position));
            }
        });


        if (data.getData().size() > 0) {
            Log.e(TAG, "selectedNyukaaa   " + nyukaIdArray.get(0));
            setNyukaId(nyukaIdArray.get(0));

            if (getNyukaId().equals("999") && arrivalScheduleSelected) {
                Log.e(TAG, "arrivalScheduleSelected  1213111111111111111  ");
                showPopup("入荷予定がありません。");
                U.beepError(ArrivalW6Activity.this, null);
                _sts(R.id.barcode, "");
                setProc(PROC_BARCODE);
            }
        }
    }

    @OnClick(R.id.ll_listdialog)
    void click() {
        if (productList.size() != 0) {
            listviewDialog();
        }
    }

    void sendNyukaRequest(String nyuka) {
        progress.Show();
        NyukaSelectReq req = new NyukaSelectReq(app.getSerial(), adminID, BaseActivity.getShopId(), nyuka);
        manager.NyukaSelect(req, nyukacallback);
    }

    @Override
    public void onSucess(int status, NyukaSelectResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("200")) {

        } else if (message.getCode().equalsIgnoreCase("401")) {
            showPopup("予定外入荷です。");
        } else {
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, GetArrivalResponse message) throws JsonIOException {
        progress.Dismiss();

        count = 0;
        if (message.getCode().equalsIgnoreCase("0")) {
            nyukaIdArray = new ArrayList<>();

            productList = message.getResults();
            nyukacount = 0;
            String product_code = "", name = "", comment = "", SpecOne = "", Spectwo = "", Naukaid = "";
            for (int i = 0; i < productList.size(); i++) {

                if (i == 0) {
                    product_code = productList.get(i).getCode();
                    name = productList.get(i).getProduct_name();
                    comment = productList.get(i).getComment();
                    SpecOne = productList.get(i).getSpec1();
                    Spectwo = productList.get(i).getSpec2();
                    Naukaid = productList.get(i).getNyuka_id();
                }

                if (productList.get(i).getRsv_cnt() != null && productList.get(i).getRsv_cnt().equals("") == false) {

                    String cnt = U.minusTo(productList.get(i).getRsv_cnt(), productList.get(i).getAct_cnt());
                    if (Integer.parseInt(cnt) > 0 || (productList.get(i).getNyuka_id().equals("999"))) {

                        nyukaIdArray.add(productList.get(i).getNyuka_id());
                        if (!productList.get(i).getNyuka_id().equals("999"))
                            ++nyukacount;
                    }

//                    String lotno = productList.get(i).getLot();

                   /* if (!lotno.equals("")) {
                        count++;
                    }*/
                }
                Log.e(TAG, "BaseActivity.getLotPress     " + BaseActivity.getLotPress());


                if (productList.get(i).getAttribute_type() != null && BaseActivity.getLotPress()) {
                    attribute = productList.get(i).getAttribute_type();

                }
            }

            if (nyukacount > 1)
                CommonDialogs.customToast(this, "複数の入荷予定が登録されています。");

            if (!nyukaIdArray.get(0).equals("999") && BaseActivity.getLotPress()) {

                String nyukaa = nyukaIdArray.get(0);
                if (productList.get(0).getNyuka_id().equals(nyukaa)) {

                    if (attribute.equals("1")) {
                        _sts(R.id.lotno, productList.get(0).getLot());
                    } else if (attribute.equals("2")) {

                        Log.e(TAG, "33333333333   " + productList.get(0).getExpiration_date());
                        _sts(R.id.expirationdate, productList.get(0).getExpiration_date());
                    } else if (attribute.equals("3")) {
                        Log.e(TAG, "333333333332221111   expirationnnnn" + productList.get(0).getExpiration_date() + "     lottt  " + productList.get(0).getLot());
                        _sts(R.id.expirationdate, productList.get(0).getExpiration_date());
                        _sts(R.id.lotno, productList.get(0).getLot());
                    }
                }

            }

            mTextToSpeak.startSpeaking("scanning");
            if (BaseActivity.getLotPress()) {
                setAttributeValue(attribute);
            }

            startTimer();


            if (attribute.equals("0")) {
                setProc(PROC_QTY);
            } else if (attribute.equals("1")) {
                if (_gts(R.id.lotno).equals(""))
                    setProc(PROC_LOT_NO);
                else
                    setProc(PROC_QTY);
                lotexist = true;
            } else if (attribute.equals("2")) {

                if (_gts(R.id.expirationdate).equals("")) {
                    setProc(PROC_EXPIRATION);
                } else {
                    setProc(PROC_QTY);
                }
            } else if (attribute.equals("3")) {

                if (_gts(R.id.lotno).equals(""))
                    setProc(PROC_LOT_NO);
                else if (_gts(R.id.expirationdate).equals(""))
                    setProc(PROC_EXPIRATION);
                else {
                    setProc(PROC_QTY);
                }
                lotexist = true;
            }

            _sts(R.id.quantity, "1");

            if (Naukaid.equalsIgnoreCase("999")) {
                _g(R.id.ll_comment).setVisibility(View.GONE);

            } else {
                _g(R.id.ll_comment).setVisibility(View.VISIBLE);
            }
            _sts(R.id.productCode, product_code);
            _stxtv(R.id.productName, name);
            productName.setSelected(true);

            _sts(R.id.Remarks, comment);
            _sts(R.id.Standard1, SpecOne);
            _sts(R.id.Standard2, Spectwo);

            quantity = "1";

            setNyukaIdArray(nyukaIdArray);
            getarrivalList();

        } else if (message.getCode().equalsIgnoreCase("401")) {
            showPopup("予定外入荷です。");

        } else {
            U.beepError(this, message.getMessage());
        }


    }

    @Override
    public void onSucess(int status, GetArrivalOrderResponse message) throws JsonIOException {
        progress.Dismiss();
        count = 0;
        if(message.getCode().equalsIgnoreCase("0"))
        {
            setProc(PROC_BARCODE);


        }
        else if(message.getCode().equalsIgnoreCase("14404")){
            U.beepError(this, message.getMessage());
            setProc(PROC_ORDER_NO);
        }
        else if (message.getCode().equalsIgnoreCase("14405")){
            U.beepError(this, message.getMessage());
            setProc(PROC_ORDER_NO);
        }
        else {
            U.beepError(this, message.getMessage());
            setProc(PROC_ORDER_NO);
        }
    }


    @Override
    public void onSucess(int status, AddArrivalResponse message) throws JsonIOException {
        progress.Dismiss();
        count = 0;

        if(message.getCode().equalsIgnoreCase("0")){
           String nextBarcode = "";
            if (mNextBarcode)
                nextBarcode = isNextBarcode;
            if(Integer.parseInt(message.getPending_arrivals() )== 0)
            {
                U.beepKakutei(this, "検品データを登録しました。");
                nextProcess();
            }
            else {
                _sts(R.id.barcode, "");
                _sts(R.id.quantity, "");
                _stxtv(R.id.productName, "");
                lot.setText("");
                expiration.setText("");
                _sts(R.id.Remarks, "");
                _sts(R.id.Standard1, "");

                _sts(R.id.Standard2, "");
                _sts(R.id.productCode, "");

                pck0.setText("");
                pck1.setText("");
                pck2.setText("");
                pck3.setText("");

                nyukaIdArray.clear();
                productList.clear();

                setProc(PROC_BARCODE);

                U.beepKakutei(this, "検品データを登録しました。");
            }
            if(mNextBarcode){
             scanedEvent(nextBarcode);
            }

            mNextBarcode = false;
            isNextBarcode = "";
            nextBarcode = "";
        }
        else if (message.getCode().equalsIgnoreCase("14405")){
            U.beepError(this,message.getMessage());
        }
        else {
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, GetArrivalBarcodeOrderNoResponse message) throws JsonIOException
    {
        progress.Dismiss();
        count = 0;
        if (message.getCode().equalsIgnoreCase("0")) {
            nyukaIdArray = new ArrayList<>();

            productList = message.getResults();
            nyukacount = 0;
            String product_code = "", name = "", comment = "", SpecOne = "", Spectwo = "", Naukaid = "";
            for (int i = 0; i < productList.size(); i++) {

                if (i == 0) {
                    product_code = productList.get(i).getCode();
                    name = productList.get(i).getProduct_name();
                    comment = productList.get(i).getComment();
                    SpecOne = productList.get(i).getSpec1();
                    Spectwo = productList.get(i).getSpec2();
                    Naukaid = productList.get(i).getNyuka_id();
                }
                productList.get(i).setOrder_no(_gts(R.id.arrivalID));

                if (productList.get(i).getRsv_cnt() != null && productList.get(i).getRsv_cnt().equals("") == false) {

                    String cnt = U.minusTo(productList.get(i).getRsv_cnt(), productList.get(i).getAct_cnt());
                    if (Integer.parseInt(cnt) > 0 || (productList.get(i).getNyuka_id().equals("999"))) {

                        nyukaIdArray.add(productList.get(i).getNyuka_id());
                        if (!productList.get(i).getNyuka_id().equals("999"))
                            ++nyukacount;
                    }

//                    String lotno = productList.get(i).getLot();

                   /* if (!lotno.equals("")) {
                        count++;
                    }*/
                }
                Log.e(TAG, "BaseActivity.getLotPress     " + BaseActivity.getLotPress());


                if (productList.get(i).getAttribute_type() != null && BaseActivity.getLotPress()) {
                    attribute = productList.get(i).getAttribute_type();
                }
            }

            if (nyukacount > 1)
                CommonDialogs.customToast(this, "複数の入荷予定が登録されています。");

            if (!nyukaIdArray.get(0).equals("999") && BaseActivity.getLotPress()) {

                String nyukaa = nyukaIdArray.get(0);
                if (productList.get(0).getNyuka_id().equals(nyukaa)) {

                    if (attribute.equals("1")) {
                        _sts(R.id.lotno, productList.get(0).getLot());
                    } else if (attribute.equals("2")) {

                        Log.e(TAG, "33333333333   " + productList.get(0).getExpiration_date());
                        _sts(R.id.expirationdate, productList.get(0).getExpiration_date());
                    } else if (attribute.equals("3")) {
                        Log.e(TAG, "333333333332221111   expirationnnnn" + productList.get(0).getExpiration_date() + "     lottt  " + productList.get(0).getLot());
                        _sts(R.id.expirationdate, productList.get(0).getExpiration_date());
                        _sts(R.id.lotno, productList.get(0).getLot());
                    }
                }

            }

            mTextToSpeak.startSpeaking("scanning");
            if (BaseActivity.getLotPress()) {
                setAttributeValue(attribute);
            }

            startTimer();


            if (attribute.equals("0")) {
                setProc(PROC_QTY);
            } else if (attribute.equals("1")) {
                if (_gts(R.id.lotno).equals(""))
                    setProc(PROC_LOT_NO);
                else
                    setProc(PROC_QTY);
                lotexist = true;
            } else if (attribute.equals("2")) {

                if (_gts(R.id.expirationdate).equals("")) {
                    setProc(PROC_EXPIRATION);
                } else {
                    setProc(PROC_QTY);
                }
            } else if (attribute.equals("3")) {

                if (_gts(R.id.lotno).equals(""))
                    setProc(PROC_LOT_NO);
                else if (_gts(R.id.expirationdate).equals(""))
                    setProc(PROC_EXPIRATION);
                else {
                    setProc(PROC_QTY);
                }
                lotexist = true;
            }

            _sts(R.id.quantity, "1");

            if (Naukaid.equalsIgnoreCase("999")) {
                _g(R.id.ll_comment).setVisibility(View.GONE);

            } else {
                _g(R.id.ll_comment).setVisibility(View.VISIBLE);
            }
            _sts(R.id.productCode, product_code);
            _stxtv(R.id.productName, name);
            productName.setSelected(true);

            _sts(R.id.Remarks, comment);
            _sts(R.id.Standard1, SpecOne);
            _sts(R.id.Standard2, Spectwo);

            quantity = "1";

            setNyukaIdArray(nyukaIdArray);
            getarrivalList();

        } else if (message.getCode().equalsIgnoreCase("401")) {
            showPopup("予定外入荷です。");

        } else {
            U.beepError(this, message.getMessage());
        }

    }

    @Override
    public void onError(int status, ResponseBody error)
    {
     progress.Dismiss();


        JSONObject jObjError = null;
        try {
            jObjError = new JSONObject(error.string());
            String message = jObjError.get("error").toString();

            U.beepError(this, message);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onFaliure() {
        progress.Dismiss();
    }

    @Override
    public void onNetworkFailure() {
        progress.Dismiss();
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }


       /* @Override
        public void onSucess(int status, ResponseBody message) throws JsonIOException {
            if (status==200){
                progress.Dismiss();
                Result();
            }
        }*/

       /* public void dilaogCustomercancel() {

            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog2.setCanceledOnTouchOutside(false);
            dialog2.setCancelable(false);
            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog2.setContentView(R.layout.weightsubmit);

            final EditText weight = (EditText) dialog2.findViewById(R.id.weight);
            close = (ImageView) dialog2.findViewById(R.id.closee);
            weight.setFocusable(true);
            weight.setFocusableInTouchMode(true);
            weight.requestFocus();

            //  text.setText(remark);
            Button ok = (Button) dialog2.findViewById(R.id.ok);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (weight.getText().toString().equals("")||weight.getText().toString().equals("0")){
                        U.beepError(ArrivalW6Activity.this,"重さを入力してください ");
                    }else {
                        dialog2.dismiss();
                        progress.Show();
                        ArrivalWeightRequest reqq = new ArrivalWeightRequest(adminID, app.getSerial(), BaseActivity.getShopId(), getString(R.string.version), ProductID, weight.getText().toString());
                        manager.ArrivalWeight(reqq, weightcall);
                    }
                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog2.dismiss();
                    nextProcess();
                }
            });
            if (!dialog2.isShowing()) {
                dialog2.show();
            }
        }*/

    public void Result() {
        if (code == null) {
            Log.e(TAG, "CODEeee===Null");
            CommonDialogs.customToast(getApplicationContext(), "Network error occured");
        }

        if ("0".equals(code) == true) {

            Log.e("SendLogs", code + "  " + msg + "  " + result1);

            if (mRequestStatus == REQ_BARCODE) {


                new GetArrival().post(code, msg, result1, mHash, ArrivalW6Activity.this);
            } else if (mRequestStatus == REQ_QTY) {


                new AddArrival().post(code, msg, result1, mHash, ArrivalW6Activity.this);
            }
        } else if (code.equalsIgnoreCase("1020")) {
            new AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(msg)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent in = new Intent(ArrivalW6Activity.this, LoginActivity.class);
                            in.putExtra("ACTION", "logout");
                            startActivity(in);
                            finish();

                            dialog.dismiss();
                        }
                    })

                    .show();
        } else {
            if (mRequestStatus == REQ_BARCODE) {
                new GetArrival().valid(code, msg, result1, mHash, ArrivalW6Activity.this);
            } else if (mRequestStatus == REQ_QTY) {
                new AddArrival().valid(code, msg, result1, mHash, ArrivalW6Activity.this);
            }
        }
    }
}
