package com.itechvision.ecrobo.pickman.Chatman.WWW6Server.OnetoONeSlip;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.WWW6Server.DimaruShipping.Daimaru_Shipping;
import com.itechvision.ecrobo.pickman.Chatman.WWW6Server.OnetoONeSlip.onetoone_slipprint.GetAllocateDetails_one;
import com.itechvision.ecrobo.pickman.Chatman.WWW6Server.OnetoONeSlip.onetoone_slipprint.GetBatchList_one;
import com.itechvision.ecrobo.pickman.Chatman.WWW6Server.OnetoONeSlip.onetoone_slipprint.SlipPrinterOrderDetails_one;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.KoguchiShipCompany.NKoguchiShipCompResult;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.KoguchiShipCompany.NKoguchiShipData;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.KoguchiShipCompany.NKoguchi_ShipCompanyReq;
import com.itechvision.ecrobo.pickman.Models.OnetoOneSlip_printer.Daimaru_OnetoOne_OrderIDReq;
import com.itechvision.ecrobo.pickman.Models.OnetoOneSlip_printer.Daimaru_OnetoOne_OrderID_Res;
import com.itechvision.ecrobo.pickman.Models.OnetoOneSlip_printer.UpdateShip_request;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.SharedPrefrences;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.reginald.editspinner.EditSpinner;

import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class One_to_One_SlipPrinter extends BaseActivity implements View.OnClickListener, MainAsynListener , DataManager.OneToOneGetCompanycall ,DataManager.One_to_One_UpdateShipcall ,DataManager.GetORderIDcall{
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.dateselect) EditText datedt;
    @BindView(R.id.add_qty) LinearLayout qtylayout;
    @BindView(R.id.batchspinner) Spinner spinner;
    @BindView(R.id.spinnerlayout)RelativeLayout spinnerLayout;
    @BindView(R.id.spinnerlayout1)RelativeLayout spinnerlayout1;
    @BindView(R.id.spinnershippinglayout)RelativeLayout spinnershippinglayout;
    @BindView(R.id.koguchi) EditSpinner koguchi;
    @BindView(R.id.changeshippingcmnpy) EditSpinner changeshippingcmnpy;
    @BindView(R.id.id_c_koguchi) Button id_c_koguchi;
    @BindView(R.id.id_c_company) Button id_c_company;

    String TAG = One_to_One_SlipPrinter.class.getSimpleName();
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public Map<String, String> cProductList = null;
    public Map<String, String> submitList = null;
    protected List<Map<String, String>> showList = new ArrayList<>();
    protected List<Map<String, String>> batchList = new ArrayList<>();
    String adminID = "";
    Dialog dialog1;
    String batchID ="";
    int batchPOS = 0;
    PopupWindow popup2Window;
    private boolean showKeyboard;
    public TextToSpeak mTextToSpeak;
    private boolean visible = false;
    public Context mcontext = this;
    protected int mProcNo = 0;
    public static final int PROC_DATE = 1;
    public static final int PROC_BATCH = 2;
    public static final int PROC_BARCODE = 3;
    public static final int PROC_QTY = 4;
    public static final int PROC_KAGUCHI = 5;
    public static final int PROC_SHIP = 6;
    public static String getAction = "";
    public static int mRequestStatus =0;
    public static final int REQ_DATE = 1;
    public static final int REQ_BARCODE = 2;
    public static final int REQ_QTY = 3;
    public static final int REQ_ORDER_DETAIL=4;
    public static final int REQ_INITIAL=5;
    String batchselected = "";
    String action = "";
    public String _lastUpdateQty = "0",ChangedShipId="";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String koguchi_count = "";
    DataManager.OneToOneGetCompanycall getshipcompany;
    DataManager.One_to_One_UpdateShipcall updateship;
    ArrayList<NKoguchiShipData> shipcompdata;
    DataManager.GetORderIDcall GetOrderID ;
    ArrayList<String> arrayList;
    ArrayList<String> arr1;
    DataManager manager;
    progresBar progress;
    ArrayAdapter<String> adapter1;
    ArrayList<String> sizes = new ArrayList<>();
    public String size = "", mBoxNo = "0";
    int post = 0, eop;
    ECRApplication app = new ECRApplication();
    String Kog = "",version="",ORDERID="",datell = "";


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_to_one_slip_printer);

        ButterKnife.bind(One_to_One_SlipPrinter.this);
        getshipcompany = this;
        updateship = this;
        GetOrderID = this;

        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);
        dialog1 = new Dialog(One_to_One_SlipPrinter.this);
        manager = new DataManager();
        progress = new progresBar(this);
        shipcompdata = new ArrayList<>();
        arrayList = new ArrayList<String>();
        arr1 = new ArrayList<String>();
        getIDs();
        Intent intent = getIntent();
        if(intent.hasExtra("action")){
            action = intent.getStringExtra("action");
            datell = intent.getStringExtra("shipping_date");



            Globals.getterList = new ArrayList<>();
            Globals.getterList.add(new ParamsGetter("admin_id", adminID));
            Globals.getterList.add(new ParamsGetter("shipping_date", datell));
            Globals.getterList.add(new ParamsGetter("mode","batch_list"));

            mRequestStatus = REQ_DATE;
            new MainAsyncTask(this, Globals.Webservice.daimaruautomateScan, 1, One_to_One_SlipPrinter.this, "Form", Globals.getterList, true).execute();

        }

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

        datedt.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (datedt.getRight() - datedt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //open calendar on touch icon
                        showTruitonDatePickerDialog(datedt);
                        return true;
                    }
                }
                return false;
            }
        });

        mTextToSpeak = new TextToSpeak(this);
        Log.e(TAG, "OnCreatee");

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
                date = year+"-"+_month+"-"+_day;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + date);
                datedt.setText(date);
                inputedEvent("");
            }
        };

        datedt.setText(BaseActivity.getshippingDate());
        if(BaseActivity.getshippingDate().equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            Log.e(TAG, ">>>>>>>>>>>>>>>   " + date);

            datedt.setText(date);
        }

        if (mProcNo == 0) nextProcess();

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mProcNo>0 && position>0){
                    Map<String, String> map1 = new HashMap<>();
                    map1 =  batchList.get(position-1);
                    setBatchID(map1.get("batch_id"));
                    setBadge2(Integer.parseInt(map1.get("remaining_orders")));
                    setBatchPOS(position);
                    if(map1.get("remaining_orders").equals("0")){
                        U.beepError(One_to_One_SlipPrinter.this, "this batch is complete");
                        spinner.setSelection(0);
                        setProc(PROC_BATCH);
                    }
                    else {
                        if (mProcNo == PROC_BATCH) {
                            setProc(PROC_BARCODE);
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //set title and icons on actionbar
    private void getIDs() {

        actionbarImplement(this, "1-1検品DM", " ", 0, true, true, false);
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        btnRed.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
        relLayout1.setOnClickListener(this);

        try {
            PackageInfo pInfo =  getPackageManager().getPackageInfo(getPackageName(), 0);
            version = String.valueOf( pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        progress.Show();
        NKoguchi_ShipCompanyReq req = new NKoguchi_ShipCompanyReq(adminID, app.getSerial(), BaseActivity.getShopId());
        manager.One_to_One_SlipPrintCompanies(req, getshipcompany);
        setKoguchiSpinner();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;

            case R.id.notif_count_blue:
//              if(getBadge1()> 0){
                getAction = "total_list";
                OrderDetail();
//        }
                 break;

            case R.id.notif_count_red:
//                if(getBadge2()> 0){
                getAction = "remaining_list";
                OrderDetail();
//                }
                break;

        }
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
            Log.e(TAG, "SetlayoutMargin");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;
            numbrbtn.setText("キーボードを表示する");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG, "SetlayoutMarginn");
            mainlayout.setLayoutParams(params);
        }
    }

    public int convert(int x) {
        Resources r = mcontext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                x, r.getDisplayMetrics()
        );
        return px;
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
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    @OnClick(R.id.enter) void enter() {

        if (mProcNo==1){
            String s = mBuff.toString();
            Log.e("ScannerbindActivity", "Keyput11222 " + mBuff);
            mBuff.delete(0, mBuff.length());
            Log.e("ScannerbindActivity", "Keyput11222 buff " + mBuff);
            inputedEvent(s);
        }else {
            BaseActivity.setshippingDate(_gts(R.id.dateselect));

            Globals.getterList = new ArrayList<>();
            Log.e(TAG, "shopid  " + BaseActivity.getShopId());

            Globals.getterList.add(new ParamsGetter("admin_id", adminID));
            Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
            Globals.getterList.add(new ParamsGetter("shipping_date", _gts(R.id.dateselect)));
            Globals.getterList.add(new ParamsGetter("batch_id", getBatchID()));
            Globals.getterList.add(new ParamsGetter("order_id", ORDERID));

            Log.e(TAG,"123456789"+checkPrinterSelect());

            if(BaseActivity.getPrinterSelected() && !checkPrinterSelect()) {

                Globals.getterList.add(new ParamsGetter("invoice_printer",BaseActivity.getinvoiceselectedPrinterID()));
                Globals.getterList.add(new ParamsGetter("airprint_printer",BaseActivity.getintegratedselectedPrinterID() ));
                Globals.getterList.add(new ParamsGetter("csv_printer_id",BaseActivity.getCsvselectedPrinterID()));
            }
            else {
                Globals.getterList.add(new ParamsGetter("ap_printer_db","1"));
            }

            Globals.getterList.add(new ParamsGetter("mode","check"));

            mRequestStatus = REQ_BARCODE;

            new MainAsyncTask(this, Globals.Webservice.daimaruautomateScan, 1, One_to_One_SlipPrinter.this, "Form", Globals.getterList, true).execute();
          //Toast.makeText(this,"qwertyu",Toast.LENGTH_SHORT).show();

        }/*else{
            String s = mBuff.toString();
            Log.e("ScannerbindActivity", "Keyput11222 " + mBuff);
            mBuff.delete(0, mBuff.length());
            Log.e("ScannerbindActivity", "Keyput11222 buff " + mBuff);
            inputedEvent(s);
        }
*/
    }

    public void setProc(int procNo) {
        Log.e(TAG,"setProcc");
        mProcNo = procNo;
        mBuff.delete(0, mBuff.length());
        switch (procNo) {
            case PROC_DATE:
                Log.e(TAG,"setProc_PROC_BARCODEEEE");
                spinner.setAdapter(null);
                _gt(R.id.dateselect).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnershippinglayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerlayout1.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;
            case PROC_BATCH:
                Log.e(TAG,"setProc_PROC_BARCODEEEE");
                _gt(R.id.dateselect).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnershippinglayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerlayout1.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;

            case PROC_BARCODE:
                Log.e(TAG,"setProc_PROC_BARCODEEEEEE");
                _gt(R.id.dateselect).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnershippinglayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerlayout1.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);

                break;
            case PROC_QTY:
                Log.e(TAG,"setProc_PROC_BARCODEEEEEE");
                _gt(R.id.dateselect).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnershippinglayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerlayout1.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                break;
            case PROC_SHIP:
                Log.e(TAG,"setProc_PROC_BARCODEEEEE");
                _gt(R.id.dateselect).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerlayout1.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnershippinglayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                spinnershippinglayout.setFocusableInTouchMode(true);

                break;
            case PROC_KAGUCHI:
                Log.e(TAG,"setProc_PROC_BARCODEEEEE");
                _gt(R.id.dateselect).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnershippinglayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                spinnerlayout1.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                spinnerlayout1.setFocusableInTouchMode(true);

                break;
        }
    }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {
            case PROC_DATE:
                String date1= _gts(R.id.dateselect);

                if (date1.equals("")){
                    U.beepError(this,"date can't be empty");
                    _gt(R.id.dateselect).setFocusableInTouchMode(true);
                    break;
                }
                Globals.getterList = new ArrayList<>();
                Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("shipping_date", _gts(R.id.dateselect)));
                Globals.getterList.add(new ParamsGetter("mode","batch_list"));

                mRequestStatus = REQ_DATE;

                new MainAsyncTask(this, Globals.Webservice.daimaruautomateScan, 1, One_to_One_SlipPrinter.this, "Form", Globals.getterList, true).execute();

                break;
            case PROC_BARCODE:    // バーコード
                String barcode = _gts(R.id.barcode);
                String date = _gts(R.id.dateselect);

                if (date.equals("") || date.equals("0")) {
                    U.beepError(this, "出荷日を選択されていません。");
                    break;
                }
                if (barcode.equals("") || barcode.equals("0")) {
                    U.beepError(this, "バーコードがスキャンされていません");
                    break;
                }

                progress.Show();
                Daimaru_OnetoOne_OrderIDReq req = new Daimaru_OnetoOne_OrderIDReq(adminID,version,app.getSerial(),getBatchID(),BaseActivity.getShopId(),barcode, _gts(R.id.dateselect));
                manager.GetORderID(req,GetOrderID)   ;
                break;
       /*         BaseActivity.setshippingDate(date);

                Globals.getterList = new ArrayList<>();
                Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
                Globals.getterList.add(new ParamsGetter("shipping_date", _gts(R.id.dateselect)));
                Globals.getterList.add(new ParamsGetter("batch_id", getBatchID()));

                Log.e(TAG,"1234567892345678"+checkPrinterSelect());

                if(BaseActivity.getPrinterSelected() && !checkPrinterSelect()) {

                    Globals.getterList.add(new ParamsGetter("invoice_printer",BaseActivity.getinvoiceselectedPrinterID()));
                    Globals.getterList.add(new ParamsGetter("airprint_printer",BaseActivity.getintegratedselectedPrinterID() ));
                    Globals.getterList.add(new ParamsGetter("csv_printer_id",BaseActivity.getCsvselectedPrinterID()));
                }
                else {
                    Globals.getterList.add(new ParamsGetter("ap_printer_db","1"));
                }

                Globals.getterList.add(new ParamsGetter("mode","check"));

                mRequestStatus = REQ_BARCODE;

                new MainAsyncTask(this, Globals.Webservice.daimaruautomateScan, 1, One_to_One_SlipPrinter.this, "Form", Globals.getterList, true).execute();
*/
             case PROC_QTY: // 数量

                String qty = _gts(R.id.quantity);
                String barcode1 = _gts(R.id.barcode);
                if(isScaned){
                    if(U.compareNumeric(cProductList.get("processedCnt"),cProductList.get("num_count"))==0)
                    {
                        U.beepError(this,"Qunatity cannot exceed the required quantity");
                        break;
                    }

                    if((barcode1.trim().equals(buff))){
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned");
                        qty = U.plusTo(qty, "1");
                        String processedCnt = cProductList.get("processedCnt");
                        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                        _sts(R.id.quantity, qty);
                        if(Integer.parseInt(qty)>1)
                        mTextToSpeak.startSpeaking(qty);
                        _lastUpdateQty = _gts(R.id.quantity);

                        /* check if update in quantity need next action */
                        if (cProductList.get("processedCnt").equals(cProductList.get("num_count"))) {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned22");

                            fixedRequest();
                        }
                    }
                    else {
                        Log.e(TAG, "inputedEvent_QNTYY INcomplete inspect");
                        U.beepError(this, "Incomplete Quantity");
                        _sts(R.id.quantity, "1");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                    }

                }
                else {
                    Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_Scanned");
                    if ("".equals(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                    String processedCnt = cProductList.get("processedCnt");
                    String qtyUpdate = U.minusTo(qty, _lastUpdateQty);
                    String maxQty_ = U.minusTo(cProductList.get("num_count"), processedCnt);

                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_lastUpdateQTY");
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_qtyUpdate");
                        U.beepError(this, "数量が多すぎます");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt");
                        cProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                        if (cProductList.get("processedCnt").equals(cProductList.get("num_count"))) {
                            fixedRequest();
                        }
                        else
                        {
                            Log.e(TAG, "inputedEvent_QNTYY INcomplete inspect");
                            U.beepError(this, "Incomplete Quantity");
                            _sts(R.id.quantity, "1");
                            _gt(R.id.quantity).setFocusableInTouchMode(true);
                        }
                    }
                }
                break;

            case PROC_KAGUCHI:
                String val = koguchi.getText().toString();
                if ("".equals(val) || "0".equalsIgnoreCase(val)) {
                    U.beepError(this, "個口を入力してください。");
                    koguchi.setFocusableInTouchMode(true);
                    break;
                }
                if (!U.isNumber(val)) {
                    U.beepError(this, "個口を数字のみで入力してください。");
                    koguchi.setFocusableInTouchMode(true);
                    break;
                }
                if (koguchi_count.equalsIgnoreCase(val)) {

                } else {
                    /*if (!CommonUtilities.getConnectivityStatus(BoxCashRegisterActivity.this))
                        CommonUtilities.openInternetDialog(BoxCashRegisterActivity.this);
                    else{
                        progressBar.Show();
                        koguchi_count = val;
                        KoguchiReq req = new KoguchiReq(orderId.getText().toString(), koguchi_count);
                        manager.SetKoguchi(PrefsManager.getToken(BoxCashRegisterActivity.this), req,setKoguchiCallback);
                    }*/
                }
                break;
        }
    }

    private void fixedRequest(){
        Globals.getterList = new ArrayList<>();
        Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
        Globals.getterList.add(new ParamsGetter("shipping_date", _gts(R.id.dateselect)));
        Globals.getterList.add(new ParamsGetter("order_id", ORDERID));
        Globals.getterList.add(new ParamsGetter("batch_id", getBatchID()));

        if(BaseActivity.getPrinterSelected() && !checkPrinterSelect()) {
            Globals.getterList.add(new ParamsGetter("invoice_printer",BaseActivity.getinvoiceselectedPrinterID()));
            Globals.getterList.add(new ParamsGetter("airprint_printer",BaseActivity.getintegratedselectedPrinterID() ));
            Globals.getterList.add(new ParamsGetter("csv_printer_id",BaseActivity.getCsvselectedPrinterID()));
        }
        else
            Globals.getterList.add(new ParamsGetter("ap_printer_db","1"));
            Globals.getterList.add(new ParamsGetter("status","check"));

            mRequestStatus = REQ_QTY;

        new MainAsyncTask(this, Globals.Webservice.daimaruautomateScan, 1, One_to_One_SlipPrinter.this, "Form", Globals.getterList, true).execute();
    }


    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
        U.beepBigsound(this, null);
        nextProcess();
    }

    public  void resetspinner(){
        spinner.setSelection(0);
    }

    public void setlayout(){
        this._sts(R.id.barcode, "");
        this._sts(R.id.quantity, "");
        this._sts(R.id.totalQuantity, "");
        this._sts(R.id.productCode, "");
        this._sts(R.id.productName, "");
        this._sts(R.id.orderNo, "");
        this._sts(R.id.receiver_name, "");
        changeshippingcmnpy.setText("配送会社を選択");
        koguchi.setText("個口を選択");
        koguchi_count = "";
        qtylayout.setVisibility(View.GONE);
        ORDERID="";
    }

    @Override
    public void nextProcess() {
        Log.e(TAG," nextProcess");
        this.setProc(PROC_DATE);
        this._sts(R.id.barcode, "");
        this._sts(R.id.quantity, "");
        this._sts(R.id.totalQuantity, "");
        this._sts(R.id.productCode, "");
        this._sts(R.id.productName, "");
        this._sts(R.id.orderNo, "");
        this._sts(R.id.receiver_name, "");
        changeshippingcmnpy.setText("配送会社を選択");
        koguchi.setText("個口を選択");
        koguchi_count = "";
//        this.setBadge3(0);

        qtylayout.setVisibility(View.GONE);

        updateBadge1("0");
        updateBadge2("0");
ORDERID="";
    }

    @Override
    public void allclearEvent() {
        mTextToSpeak.startSpeaking("clear");
        U.beepBigsound(this, null);
        nextProcess();
    }

    @Override
    public void skipEvent() {

    }

    public void OrderDetail () {
        Globals.getterList = new ArrayList<>();
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("shipping_date", _gts(R.id.dateselect)));
        Globals.getterList.add(new ParamsGetter("mode", "list_orders"));

        if(getAction.equals("remaining_list")){
            Globals.getterList.add(new ParamsGetter("batch_id",getBatchID()));
        }

        mRequestStatus = REQ_ORDER_DETAIL;

        new MainAsyncTask(this, Globals.Webservice.daimaruautomateScan, 1, One_to_One_SlipPrinter.this, "Form", Globals.getterList, true).execute();
    }

    public void setInfo( ArrayList<Map<String,String>> arr){
        showList = arr;
        showInfo();
    }

    protected boolean showInfo(){
        Log.e(TAG,"showInfoo");
        if (showList.isEmpty()) {
            Log.e(TAG,"showInfoo  mPickingOrderList===000");
            return false;
        }
        else {
            if (getPopupWindow2() == null) {
                Log.e(TAG,"showInfoo  popupwindow");
                popup2Window = new PopupWindow(this);
                // レイアウト設定
                View popupView = getLayoutInflater().inflate(R.layout.rf_product_list, null);
                popup2Window.setContentView(popupView);
                // 背景設定
                popup2Window.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));
                // タップ時に他のViewでキャッチされないための設定
                popup2Window.setOutsideTouchable(true);
                popup2Window.setFocusable(true);

                // 表示サイズの設定 今回は幅300dp
                float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 324, getResources().getDisplayMetrics());
                popup2Window.setWindowLayoutMode((int) width, WindowManager.LayoutParams.WRAP_CONTENT);
                popup2Window.setWidth((int) width);
                popup2Window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                setPopupWindow2(popup2Window);

                ImageView close =(ImageView)getPopupWindow2().getContentView().findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup2Window.dismiss();
                    }
                });
            }
            TextView sku_tag =(TextView)getPopupWindow2().getContentView().findViewById(R.id.sku_tag);
            TextView qty_tag =(TextView)getPopupWindow2().getContentView().findViewById(R.id.rf_qty);
            TextView rf_tag =(TextView)getPopupWindow2().getContentView().findViewById(R.id.rf_tag);

            sku_tag.setText(R.string.orderNo);
            qty_tag.setText(R.string.productCode);
            rf_tag.setVisibility(View.GONE);

            ListView lv = (ListView) getPopupWindow2().getContentView().findViewById(R.id.orderPicking);
            initList(lv);

            // 画面中央に表示
            getPopupWindow2().showAtLocation(findViewById(R.id.barcode), Gravity.CENTER, 0, 32);
            return true;
        }}

    protected ListViewItems initList(ListView lv) {
        Log.e(TAG,"initListt");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (int i = 0 ;i < showList.size(); i++) {
            Map<String, String> row = showList.get(i);

            Log.e(TAG,"initLit11");
            data.add(data.newItem().add(R.id.rf_txt_1, row.get("order_no")).add(R.id.rf_txt_2, row.get("code")));
         }

        ListViewAdapter adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.dialog_list_row) ;
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_BARCODEEEEEE");
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_BARCODEEEEEE");
                _sts(R.id.quantity, buff);
                break;

             case PROC_SHIP: // 数量
                changeshippingcmnpy.setText(buff);
                break;

             case PROC_KAGUCHI:    // バーコード
                koguchi.setText(buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (CLEAR_BARCODE.equals(barcode)) {
            Log.e(TAG,"scanedEvent_CLEAR_BARCODE");
            mBuff.delete(0, mBuff.length());
            clearEvent();
        } else if (ENTER_BARCODE.equals(barcode)) {
            Log.e(TAG,"scanedEvent_ENTER_BARCODE");
            enterEvent();
        }  if (dialog1.isShowing()) {

        } else {
            if (!MainAsyncTask.dialogBox.isShowing()) {
                if (!barcode.equals("")) {
                    if (mProcNo == PROC_BARCODE) {
                        Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                        String finalbarcode1 = CommonFunctions.getBracode(barcode);
                        barcode = finalbarcode1;

                        _sts(R.id.barcode, barcode);
                    } else if (mProcNo == PROC_QTY) {
                        Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                        String finalbarcode1 = CommonFunctions.getBracode(barcode);
                        barcode = finalbarcode1;
                    }

                    this.inputedEvent(barcode, true);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please Wait", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void enterEvent() {
        String s = mBuff.toString();
        Log.e("ScannerbindActivity", "Keyput11222 " + mBuff);
        mBuff.delete(0, mBuff.length());
        Log.e("ScannerbindActivity", "Keyput11222 buff " + mBuff);
        inputedEvent(s);
    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                Log.e(TAG,"deleteEvent_PROC_BARCODEEEE");
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QTY:
                Log.e(TAG,"deleteEvent_PROC_BARCODEEE");
                _sts(R.id.quantity, barcode);
                break;
        }
    }

    public void setProductsArray(List<Map<String, String>> arr) {
        batchList = arr;

    }
    public  void setBatchPosition(){
        if(action.equals("back")){
            spinner.setSelection(getBatchPOS());
            action = "";
         }
    }

    public void setBatchID(String id){
        this.batchID = id;
    }
    public String getBatchID(){
        return this.batchID;
    }
    public void setBatchSelectedPOS(int id){
        this.batchPOS = id;
    }
    public int getBatchSelectedPOS(){
        return this.batchPOS;
    }

    public void getsubmitList(Map<String,String> map){
        submitList = map;
    }

    public void updateBadge1(String orderCount) {
        Log.e(TAG,"updateBadge1111"  + orderCount);
        setBadge1(Integer.valueOf(orderCount));
    }

    public void updateBadge2(String qtyCount) {
        Log.e(TAG,"updateBadge2" + qtyCount);
        setBadge2(Integer.valueOf(qtyCount));
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
         //super.onBackPressed();
    }

    public  void nextWork(Map<String, String> map){
        cProductList = map;
        mTextToSpeak.resetQueue();
        _sts(R.id.quantity,"1");
        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
        _lastUpdateQty = _gts(R.id.quantity);
        qtylayout.setVisibility(View.VISIBLE);

        koguchi.setText("個口を選択");
        changeshippingcmnpy.setText("配送会社を選択");
        ChangedShipId = "";
        Kog = "";

        U.beepNext();
        setProc(PROC_QTY);
     }

    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {
        HashMap<String, String> mHash = new HashMap<>();
        Log.e(TAG, result.toString());


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
            if (code == null) {
                Log.e(TAG, "CODEeee==Null");
                CommonDialogs.customToast(getApplicationContext(), "Network error occured");

            }
            if ("0".equals(code) == true) {

                Log.e("SendLogs111", code + "  " + msg + "  " + result1);
                JsonHash row1 = (JsonHash) result1.get(0);
                if(mRequestStatus == REQ_INITIAL){
                    String remaining_orders = row1.getStringOrNull("remaining_orders");
                    String total = row1.getStringOrNull("total_orders");
                    updateBadge1(total);
                    updateBadge2(remaining_orders);
                    if(action.equals("back"))
                        inputedEvent(datedt.getText().toString());
                }
                if(mRequestStatus == REQ_DATE){
                    new GetBatchList_one().post(result1, One_to_One_SlipPrinter.this);
                }
                else if(mRequestStatus == REQ_BARCODE){
                    new GetAllocateDetails_one().post(result1, One_to_One_SlipPrinter.this);
                }
                else if(mRequestStatus == REQ_QTY) {
                    new GetAllocateDetails_one().post(result1, One_to_One_SlipPrinter.this);
                }
                else if(mRequestStatus == REQ_ORDER_DETAIL){
                    new SlipPrinterOrderDetails_one().post(code,msg,result1, mHash, One_to_One_SlipPrinter.this);

                }
            } else if(code.equalsIgnoreCase("1020")){
                        new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(One_to_One_SlipPrinter.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();

                            }
                        })

                        .show();
            }else{
                U.beepError(this,msg);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {
        U.beepError(this,"network error has occured");
    }

    public  void CallBoxSizeScreen (){
        if(getBadge2()==0 && getBadge1()!=0){
            setBatchPOS(0);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
        @Override
        public void run() {
                //Do something after 100ms
                Intent i = new Intent (One_to_One_SlipPrinter.this, ONetoOneShippingSpecification.class);
                i.putExtra("action","slip_printer");
                i.putExtra("company",submitList.get("shipping_method"));
                i.putExtra("order_id",submitList.get("order_id"));
                i.putExtra("batch_id",getBatchID());
                i.putExtra("slip_printer","SlipPrinter");
                i.putExtra("shipping_date",_gts(R.id.dateselect));
                startActivity(i);
                finish();

            }
        }, 1000);

    }

/*    public void setdialog (String msg, int layout) {

        final Dialog dialog = new Dialog(SlipPrinterActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Set GUI of login screen

        dialog.setContentView(layout);
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.setCanceledOnTouchOutside(true);

        // Init button of login GUI
        TextView txt=(TextView)dialog.findViewById(R.id.txt) ;
        txt.setText(msg);
        ImageView close=(ImageView)dialog.findViewById(R.id.icon_close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(BaseActivity.getBoxSelected() == true){
                    CallBoxSizeScreen();
                } else {
                    if(getBadge2()==0 && getBadge1()==0) {
                        nextProcess();
                    } else if(getBadge2()==0 && getBadge1()!=0) {
                        setProc(S/lipPrinterActivity.PROC_BATCH);
                        resetspinner();
                        setlayout();
                    } else {
                        setProc(PROC_BARCODE);
                        setlayout();
                    }
                }

                dialog.cancel();
                dialog.dismiss();
            }
        });
        dialog.show();
    }*/

    public void dilaogCustomercancel( String remark) {

        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setCancelable(false);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setContentView(R.layout.shipping_remark);

        TextView text = (TextView) dialog1.findViewById(R.id.remark);
        text.setText(remark);
        Button ok = (Button) dialog1.findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(BaseActivity.getBoxSelected() == true){
           //         CallBoxSizeScreen();
                } else {*/
                    if(getBadge2()==0 && getBadge1()==0) {
                        nextProcess();
                    } else if(getBadge2()==0 && getBadge1()!=0) {
                        setProc(One_to_One_SlipPrinter.PROC_BATCH);
                        resetspinner();
                        setlayout();
                    } else {
                        setProc(PROC_BARCODE);
                        setlayout();
                  //  }
                }
                dialog1.dismiss();
            }
        });

        if (!dialog1.isShowing()) {
            dialog1.show();
        }
    }

    private void setKoguchiSpinner() {
        sizes = new ArrayList<>();
        // sizes.add("Select Koguchi");
        for (int i = 1; i <= 10; i++) {
            sizes.add(i + "");
        }

        ArrayAdapter adapter = new ArrayAdapter(this,  R.layout.spinner, sizes) {
        @Override
        public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                } else {
                    return true;
                }
            }
        };

        adapter.setDropDownViewResource(_singleItemRes);
        koguchi.setAdapter(adapter);
     // koguchi.setText(koguchi_count);
        koguchi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    eop = position;
                    if (koguchi_count.equalsIgnoreCase(sizes.get(position))) {

                    } else {

                    }
                }
            }
        });
    }

    @Override
    public void onSucess(int status, NKoguchiShipCompResult message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("0")) {
            arrayList.clear();
            shipcompdata.clear();
            shipcompdata = message.getShipping_methods();
            if (shipcompdata.size() != 0) {
                for (int i = 0; i < shipcompdata.size(); i++) {
                    shipcompdata.get(i).getId();
                    shipcompdata.get(i).getNamed();
                    String a = shipcompdata.get(i).getNamed();
                    arrayList.add(a);
                }

                adapter1 = new ArrayAdapter<String>(One_to_One_SlipPrinter.this, R.layout.spinner, arrayList);
                adapter1.setDropDownViewResource(_singleItemRes);
                changeshippingcmnpy.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
                changeshippingcmnpy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String name = shipcompdata.get(i).getNamed();
                        String id = shipcompdata.get(i).getId();
                        changeshippingcmnpy.setText(name);
                        Log.e("selected shopID", id);
                        ChangedShipId = id;
                        changeshippingcmnpy.setText(shipcompdata.get(i).getNamed());
                    }
                });
            } else {
               // print = false;
                U.beepError(this, "運送会社が見つかりません。");
            }
        } else if (message.getCode().equals("403")) {
            U.beepError(this, message.getMessage());
           // print = false;
        }
    }

    @Override
    public void onSucess(int status, ResponseBody message) throws JsonIOException {
        if (status==200){
            progress.Dismiss();
            JSONObject jObjError = null;
            try {
                jObjError = new JSONObject(message.string());
                String code = jObjError.get("code").toString();
                String messages = jObjError.get("message").toString();

                if (code.equals("0")){
                    koguchi.setText("個口を選択");
                    changeshippingcmnpy.setText("配送会社を選択");
                    ChangedShipId = "";
                    Kog = "";
                    setProc(PROC_BARCODE);
                    U.beepKakutei(this,"変更しました。");
                }else if(code.equals("14421")) {
                    koguchi.setText("個口を選択");
                    changeshippingcmnpy.setText("配送会社を選択");
                    ChangedShipId = "";
                    Kog = "";
                    U.beepKakutei(this,messages);
                  }else{
                    U.beepError(this, messages);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

     }

    @Override
    public void onSucess(int status, Daimaru_OnetoOne_OrderID_Res message) throws JsonIOException {
        if (status==200) {
            progress.Dismiss();
            if (message.getCode().equals("0")) {
                ORDERID = message.getOrder_id();

                String comment = "0";
                if (message.getComment() != null)
                    comment = message.getComment();

                if (comment.equalsIgnoreCase("1")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("航空便搭載\n"+
                            "禁止商品を含みます\n");
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            "Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    U.beepError(this, message.getMessage());
                }
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

    @OnClick(R.id.id_c_koguchi) void id_c_koguchi() {
        koguchi.setText("個口を選択");
        setProc(PROC_KAGUCHI);
    }

    @OnClick(R.id.id_c_company) void id_c_company() {
        changeshippingcmnpy.setText("配送会社を選択");
        setProc(PROC_SHIP);
    }

    @OnClick(R.id.submit) void submit() {
        if (changeshippingcmnpy.getText().toString().equals("配送会社を選択") && koguchi.getText().toString().equals("個口を選択")) {
            U.beepError(this, "個口や運送会社を更新してください。");
        } else {
            if (koguchi.getText().toString().equals("個口を選択")) {
                Kog = "";
            } else {
                Kog = koguchi.getText().toString();
            }

            progress.Show();
            UpdateShip_request req = new UpdateShip_request(adminID, app.getSerial(), BaseActivity.getShopId(),  getResources().getString(R.string.version),ORDERID, ChangedShipId, Kog);
            manager.One_to_One_UpdateShip(req, updateship);
            ChangedShipId = "";
            Kog = "";
        }

    }

}