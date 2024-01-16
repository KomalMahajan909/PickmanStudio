package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gigatms.ConnectionState;
import com.gigatms.TagInformationFormat;
import com.gigatms.UHFCallback;
import com.gigatms.UHFDevice;
import com.gigatms.parameters.BuzzerOperationMode;
import com.gigatms.parameters.RfSensitivityLevel;
import com.gigatms.parameters.Session;
import com.gigatms.parameters.TagPresentedType;
import com.gigatms.parameters.Target;
import com.gigatms.parameters.TriggerType;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFIDArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFID_ReturnActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.AddArrival;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetArrival;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetPrinter;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.ConnectedDevices;
import com.itechvision.ecrobo.pickman.Chatman.RFDeviceBaseScan;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetRfOrder;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.id;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RFPickingActivity extends BaseActivity implements View.OnClickListener, MainAsynListener, UHFCallback {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.btn_connect) Button connectionbtn;
    @BindView(R.id.list) ListView lv;
    @BindView(id.listinclude) ListView lvInclude;
    @BindView(R.id.dateselect) EditText datedt;
    @BindView(R.id.btnread) Button btnRead;


    public LinearLayout  includeLayout;

    String TAG = RFPickingActivity.class.getSimpleName();

    Dialog dialog;
    int pending = 0, include =0,scanned = 0;
    protected int mProcNo = 0;
    public static final int PROC_FRONTAGE = 1;
    public static final int PROC_RFID = 2;
    public static final int PROC_BARCODE = 3;
    public static final int PROC_QTY = 4;
    public static final int PROC_INCLUDE_BARCODE = 5;
    public static final int PROC_INCLUDE_QTY = 6;

    public static int mRequestStatus = 0;

    public static final int REQ_FRONTAGE = 1;
    public static final int REQ_QTY = 3;

    List<Map<String, String>> orderList = new ArrayList<>();
    List<Map<String, String>> rfList = new ArrayList<>();
    List<String> gotList = new ArrayList<>();
    List<Map<String, String>> barcodeList = new ArrayList<>();
    List<Map<String, String>> includeList = new ArrayList<>();
    List<Map<String, String>> packList = new ArrayList<>();
    protected Map<String, String> mTarget = null;
    protected Map<String, String> mPackItem = new HashMap<String, String>();
    protected List<Map<String, String>>showList = new ArrayList<>();

    SweetAlertDialog pDialog,pDialog1;
    public String orderID;

    private TextToSpeak mTextToSpeak;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    PopupWindow popup2Window;

    String adminID = "";
    private boolean showKeyboard;
    private boolean visible = false;
    public Context mcontext = this;

    public String _lastUpdateQty = "0";

    boolean sendconfirm = false;

    private DatePickerDialog.OnDateSetListener mDateSetListener;


    //ReadWrite process starts
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    String devMacAddress ="";
    private UHFDevice uhf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfpicking);

        ButterKnife.bind(RFPickingActivity.this);

        getIDs();
        Log.d(TAG,"On Create ");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        includeLayout = (LinearLayout)findViewById(id.includeLayout);

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);

        showKeyboard = BaseActivity.getaddKeyboard();
        if (showKeyboard == true) {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
            visible = true;

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
                BaseActivity.setRFdate(date);
                setProc(PROC_FRONTAGE);
            }
        };

        mTextToSpeak = new TextToSpeak(this);
        Log.e(TAG, "OnCreateeeeee");

        if (mProcNo == 0) nextProcess();

        // READ WRITE Working
        Intent i = getIntent();
        if (i.hasExtra(EXTRAS_DEVICE_ADDRESS)){
            devMacAddress = i.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        }

        initUhfDevice();

        _sts(R.id.dateselect,BaseActivity.getRfdate());
        if(getRfdate().equals("")){
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            Log.e(TAG, ">>>>>>>>>>>>>>>   " + date);
            _sts(R.id.dateselect,date);
        }
    }

    //set title and icons on actionbar
    private void getIDs() {
        actionbarImplement(this, "RFID出荷検品", " ",
                0, true, true, true);

        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relLayout1:
                menu.showMenu();
                break;
            case id.notif_count_blue:
                showList = new ArrayList<>();
                showList= includeList;
                showInfo("include");
                break;
            case id.notif_count_red:
                showList = new ArrayList<>();
                if(gotList.size()>0){
                for(Map<String,String> map :orderList){
                    if(map.get("scan_tag").equals("0")){
                        showList.add(map);
                    }
                }}
                for(Map<String,String> map :barcodeList){
                    if(!map.get("processedCnt").equals(map.get("num"))){
                        String _temp = U.minusTo(map.get("num"),map.get("processedCnt"));
                        map.put("num",_temp);
                        showList.add(map);
                }}
                if(mProcNo>4 && BaseActivity.getsinclude()){
                    for(Map<String,String> map :includeList){
                        if(!map.get("processedCnt").equals(map.get("num"))){
                            String _temp = U.minusTo(map.get("num"),map.get("processedCnt"));
                            map.put("num",_temp);
                            showList.add(map);
                        }}
                }
                Log .e(TAG, "ShowListtt beomessss   "+showList);
                showInfo("pending");
                break;
            case id.notif_count_green:
                showList = new ArrayList<>();
                for(Map<String,String> map :orderList){
                    if(map.get("scan_tag").equals("1")){
                        showList.add(map);
                    }
                }
                for(Map<String,String> map :barcodeList){
                    if(!map.get("processedCnt").equals("0")) {
                        map.put("num", map.get("processedCnt"));
                        showList.add(map);
                    }}
                Log .e(TAG, "ShowListtt beomessss   "+showList);
                showInfo("scanned");
                break;
        }
    }

    @OnClick(R.id.btnread) void Read(){

        Log.e(TAG,"READING >>   "+uhf);
        if (uhf != null) {
            if(!rfList.isEmpty())
            rfList.clear();
            lv.setAdapter(null);
            uhf.startInventory(TagPresentedType.PC_EPC);
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
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    @OnClick(R.id.btn_connect) void Connect(){
        if(connectionbtn.getText().equals("Back")){
            pDialog1 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            pDialog1.setCancelable(true);
            pDialog1.setContentText("Do you want to go back to previous screen？");
            pDialog1.setConfirmText("Yes");
            pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    Intent i =  new Intent(RFPickingActivity.this, RFDeviceBaseScan.class);
                    i.putExtra("rf_use","picking");
                    startActivity(i);
                    finish();
                    pDialog1.dismiss();
                }
            });

            pDialog1.setCancelText("No");
            pDialog1.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {

                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    pDialog1.dismiss();
                }
            });

            pDialog1.show();

        }else {
            pDialog1 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            pDialog1.setCancelable(true);
            pDialog1.setContentText("本当にRFIDデバイスを切断しますか？");
            pDialog1.setConfirmText("Yes");
            pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {


                    pDialog1.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            uhf.disconnect();
                            SharedPreferences.Editor editor = spDomain.edit();
                            editor.putString("MacAddress","");
                            editor.commit();
                            Intent i =  new Intent(RFPickingActivity.this, RFDeviceBaseScan.class);
                            i.putExtra("rf_use","picking");
                            startActivity(i);
                            connectionbtn.setText("Back");
                            pDialog1.dismiss();
                        }

                    },
                            1000);
                }
            });

            pDialog1.setCancelText("No");
            pDialog1.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {

                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    pDialog1.dismiss();
                }
            });

            pDialog1.show();
        }
    }

    @OnClick(R.id.btnstop) void Stop(){
            if (uhf != null) {
                uhf.stopInventory();
                if(rfList.size()>0)
                matchList();
            }
    }

    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
        if (visible == false) {

            visible = true;
            numbrbtn.setText(R.string.hideKeyboard);
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(150);
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

        }
        else {
            visible = false;
            numbrbtn.setText(R.string.showkeyboard);
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
                x, r.getDisplayMetrics()
        );
        return px;
    }
    private void initUhfDevice()
    {
        UHFDevice uhf = (UHFDevice) ConnectedDevices.getInstance().get(devMacAddress);
        if (uhf != null) {
            Log.d(TAG, "initUhfDevice: found uhf");
            this.uhf = uhf;
            uhf.setUHFCallback(this);


            if (uhf.getConnectionState().equals(ConnectionState.DISCONNECTED)) {
                connectionbtn.setText("Back");
            } else if (uhf.getConnectionState().equals(ConnectionState.CONNECTED)) {
                connectionbtn.setText("Disconnect");
            }
        }
    }

    public void Enter (View view){
        String s = mBuff.toString();
        Log.e("ScannerbindActivity", "Keyput11222 " + mBuff);
        mBuff.delete(0, mBuff.length());
        Log.e("ScannerbindActivity", "Keyput11222 buff " + mBuff);
        inputedEvent(s);

    }
    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {
            case PROC_FRONTAGE:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.frontage_no).setFocusableInTouchMode(true);
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.frontage_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                lv.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                lv.setAdapter(null);

                break;
            case PROC_RFID:
                Read();
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                lv.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.frontage_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;
            case PROC_BARCODE:
                mTextToSpeak.startSpeaking("Barcode");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(id.barcode).setTextIsSelectable(true);
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                lv.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.frontage_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
            case PROC_QTY:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.frontage_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                lv.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));;
                _gt(R.id.quantity).setFocusableInTouchMode(true);
                break;
            case PROC_INCLUDE_BARCODE:
                mTextToSpeak.startSpeaking("Include Barcode");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(id.includebarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.includebarcode).setFocusableInTouchMode(true);
                _gt(id.includequantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                lv.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.frontage_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
            case PROC_INCLUDE_QTY:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.includebarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.includequantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.frontage_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                lv.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));;
                _gt(R.id.includequantity).setFocusableInTouchMode(true);
                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {

        switch (mProcNo) {
            case PROC_FRONTAGE:    // 注文ID
				/* Conditional set next process whether frontage_no or trackingNo or orderNo. */

                Log.e("NewPickingActivity", "inputedEvent_PROC_FRONTAGEDDDDDDD");
                String frontage_no = _gts(R.id.frontage_no);
                if ("".equals(frontage_no)) {
                    U.beepError(this, "注文IDは必須です");
                    _gt(R.id.frontage_no).setFocusableInTouchMode(true);
                    break;
                }

                Globals.getterList = new ArrayList<>();
                adminID = spDomain.getString("admin_id", null);

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("create_date", _gts(R.id.dateselect)));
                Globals.getterList.add(new ParamsGetter("frontage_no", frontage_no));

                mRequestStatus = REQ_FRONTAGE;

                new MainAsyncTask(this, Globals.Webservice.Rf_order, 1, RFPickingActivity.this, "Form", Globals.getterList, true).execute();

                break;
            case PROC_RFID:

                break;

            case PROC_BARCODE:    // バーコード

                String barcode1 = _gts(R.id.barcode);
                if ("".equals(barcode1)) {
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }
                mTarget = null;
                for (Map<String, String> map : barcodeList) {
                    Log.e(TAG," inputedEventt  BARCODEEE1111  " +map);

                    if (barcode1.equals(map.get("barcode").trim()) || barcode1.equals(map.get("code").trim())) {
                        Log.e(TAG," inputedEventt  BARCODEEE1111  " + barcode1);
                        mTarget = map;
                        mTarget.put("processedCnt","0");
                        break;
                    }
                }
                if (mTarget != null) {
                    _sts(id.quantity, "1");
                    String processedCnt = mTarget.get("processedCnt");
                    mTarget.put("processedCnt", U.plusTo(processedCnt, "1"));


					/* Already scanned this barcode ,
					* try to find if this barcode has current entry in packData
					* */
                        boolean createNewEntry = true;
                        for (Map<String, String> map : packList) {
                            if (mTarget.get("order_sub_id").equals(map.get("order_sub_id"))) {
                                mPackItem = map;
                                createNewEntry = false;
                                Log.e(TAG, " inputedEventt  BARCODEEE333333");
                            }
                        }

                        if (createNewEntry) {
                            createNewPackItem();
                            Log.e(TAG, " inputedEventt  BARCODEEE444444");
                        } else {
                            Log.e(TAG, " inputedEventt  BARCODEEE5555");
                            mPackItem.put("quantity", U.plusTo(mPackItem.get("quantity"), "1"));
                        }


                    Log.e(TAG, " inputedEventt  BARCODEEE7777");
                    _sts(id.quantity, "1");

                    mTextToSpeak.startSpeaking(_gts(id.quantity));
                    Log.e(TAG, " inputedEventt  BARCODEEE8888");
                    _lastUpdateQty = _gts(id.quantity);


                    setProc(PROC_QTY);

					/* check if update in quantity need next action */
                    if (mTarget.get("processedCnt").equals(mTarget.get("num"))){
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadNextBarcode();
                            }
                        }, 1000);
                    }

                }
                else {
                        U.beepError(this, "注文情報に該当商品は見つかりません");
                        _gt(R.id.barcode).setFocusableInTouchMode(true);
                }


                break;
            case PROC_QTY: // 数量
                Log.e(TAG," inputedEventt  Qtyyy");

                String qty = _gts(id.quantity);
                if ("".equals(qty)) {
                    qty = "1";
                    _sts(id.quantity, "1");
                    mTextToSpeak.startSpeaking(_gts(id.quantity));
                }
                if (isScaned) {
                        if (buff.equals(_gts(id.barcode))) {
                            if (mPackItem.get("quantity").equals("0")){
                                // this means new box created and need to set qty to zero
                                qty = "0";
                            }
                            qty = U.plusTo(qty, "1");
						/* update mProductlist and packData */
                            String processedCnt = mTarget.get("processedCnt");
                            mTarget.put("processedCnt", U.plusTo(processedCnt, "1"));

                            mPackItem.put("quantity", U.plusTo(mPackItem.get("quantity"), "1"));

                            _sts(id.quantity, qty);
                            _lastUpdateQty = qty;

                            mTextToSpeak.startSpeaking(_gts(id.quantity));


						/* check if update in quantity need next action */
                            if(mTarget.get("processedCnt").equals(mTarget.get("num"))){
                                loadNextBarcode();
                            }
                        } else {
                            setCounts();
                            setQty();
                            initIncludeList(barcodeList);
                            this._sts(id.barcode, "");
                            this._sts(id.quantity, "");
                            this.setProc(PROC_BARCODE);
                            this.scanedEvent(buff);
                        }
                    } else {
                    if ("".equals(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(id.quantity).setFocusableInTouchMode(true);


                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(id.quantity).setFocusableInTouchMode(true);

                        break;
                    }
                    String processedCnt = mTarget.get("processedCnt");
                    String qtyUpdate = U.minusTo(qty, _lastUpdateQty);
                    String maxQty_ = U.minusTo(mTarget.get("num"), processedCnt);


                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        Log.e(TAG," inputedEventt  Qtyyy5555   ");
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString() );
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    }else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        Log.e(TAG," inputedEventt  Qtyyy66666   ");
                        U.beepError(this, "数量が多すぎます");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else {

					/* update mProductlist and packData */
                        mTarget.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));

                        mPackItem.put("quantity", U.plusTo(mPackItem.get("quantity"), qtyUpdate));


                        mTextToSpeak.startSpeaking(_gts(id.quantity));

						/* check if update in quantity need next action */
                        if(mTarget.get("processedCnt").equals(mTarget.get("num"))){
                            loadNextBarcode();
                        } else {
							/*get next barcode*/
							setCounts();
							setQty();
                            initIncludeList(barcodeList);
                            this._sts(id.barcode, "");
                            this._sts(id.quantity, "");
                            U.beepNext();
                            this.setProc(PROC_BARCODE);
                        }
                    }
                }
                break;

            case PROC_INCLUDE_BARCODE:    // バーコード
                    mTarget= null;
                String barcode2 = _gts(id.includebarcode);
                if ("".equals(barcode2)) {
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.includebarcode).setFocusableInTouchMode(true);
                    break;
                }

                for (Map<String, String> map : includeList) {
                    Log.e(TAG," inputedEventt  BARCODEEEINCLUDEEE111  " +map);

                    if (barcode2.equals(map.get("barcode").trim()) || barcode2.equals(map.get("code").trim())) {
                        Log.e(TAG," inputedEventt  BARCODEEEINCLUDEEE1111  " + barcode2);
                        mTarget = map;
                        mTarget.put("processedCnt","0");
                        break;
                    }
                }
                if (mTarget != null) {
                    _sts(id.includequantity, "1");
                    String processedCnt = mTarget.get("processedCnt");
                    mTarget.put("processedCnt", U.plusTo(processedCnt, "1"));


					/* Already scanned this barcode ,
					* try to find if this barcode has current entry in packData
					* */
                    boolean createNewEntry = true;
                    for (Map<String, String> map : packList) {
                        if (mTarget.get("order_sub_id").equals(map.get("order_sub_id"))) {
                            mPackItem = map;
                            createNewEntry = false;
                            Log.e(TAG, " inputedEventt  BARCODEEEinclude333333");
                        }
                    }

                    if (createNewEntry) {
                        createNewPackItem();
                        Log.e(TAG, " inputedEventt  BARCODEEEinclude444444");
                    } else {
                        Log.e(TAG, " inputedEventt  BARCODEEEinclude5555");
                        mPackItem.put("quantity", U.plusTo(mPackItem.get("quantity"), "1"));
                    }

                    Log.e(TAG, " inputedEventt  BARCODEEEinclude7777");
                    _sts(id.includequantity, "1");

                    mTextToSpeak.startSpeaking(_gts(id.includequantity));
                    Log.e(TAG, " inputedEventt  BARCODEEEinclude8888");
                    _lastUpdateQty = _gts(id.includequantity);

                    setProc(PROC_INCLUDE_QTY);

					/* check if update in quantity need next action */
                    if (mTarget.get("processedCnt").equals(mTarget.get("num"))){
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadNextIncludeBarcode();
                            }
                        },1000);
                    }
                }
                else {
                    U.beepError(this, "注文情報に該当商品は見つかりません");
                    _gt(R.id.includebarcode).setFocusableInTouchMode(true);
                }
                break;

            case PROC_INCLUDE_QTY: // 数量
                Log.e(TAG," inputedEventt  Qtyyy");

                String qty2 = _gts(id.includequantity);
                if ("".equals(qty2)) {
                    qty2 = "1";
                    _sts(id.includequantity, "1");
                    mTextToSpeak.startSpeaking(_gts(id.includequantity));
                }
                if (isScaned) {
                    if (buff.equals(_gts(id.includebarcode))) {
                        if (mPackItem.get("quantity").equals("0")){
                            // this means new box created and need to set qty to zero
                            qty2 = "0";
                        }
                        qty2 = U.plusTo(qty2, "1");
						/* update mProductlist and packData */
                        String processedCnt = mTarget.get("processedCnt");
                        mTarget.put("processedCnt", U.plusTo(processedCnt, "1"));

                        mPackItem.put("quantity", U.plusTo(mPackItem.get("quantity"), "1"));

                        _sts(id.includequantity, qty2);
                        _lastUpdateQty = qty2;

                        mTextToSpeak.startSpeaking(_gts(id.includequantity));

						/* check if update in quantity need next action */
                        if(mTarget.get("processedCnt").equals(mTarget.get("num"))){
                            loadNextIncludeBarcode();
                        }
                    } else {
                        setCounts();
                        setQtyInclude();
                        initIncludeList(includeList);
                        this._sts(id.includebarcode, "");
                        this._sts(id.includequantity, "");
                        this.setProc(PROC_INCLUDE_BARCODE);
                        this.scanedEvent(buff);
                    }
                } else {
                    if ("".equals(qty2)) {
                        U.beepError(this, "数量は必須です");
                        _gt(id.includequantity).setFocusableInTouchMode(true);
                        break;
                    }
                    else if (!U.isNumber(qty2)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(id.includequantity).setFocusableInTouchMode(true);
                        break;
                    }
                    String processedCnt = mTarget.get("processedCnt");
                    String qtyUpdate = U.minusTo(qty2, _lastUpdateQty);
                    String maxQty_ = U.minusTo(mTarget.get("num"), processedCnt);

                    if (U.compareNumeric(_lastUpdateQty, qty2) == -1) {
                        Log.e(TAG," inputedEventt  Qtyyy5555   ");
                        U.beepError(this, "INCLUDEEEE   Quantity entered should exceed " + _lastUpdateQty.toString() );
                        _gt(id.includequantity).setFocusableInTouchMode(true);
                        break;
                    }else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        U.beepError(this, "数量が多すぎます");
                        _gt(id.includequantity).setFocusableInTouchMode(true);
                        break;
                    } else {

					/* update mProductlist and packData */
                        mTarget.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        mPackItem.put("quantity", U.plusTo(mPackItem.get("quantity"), qtyUpdate));
                        mTextToSpeak.startSpeaking(_gts(id.includequantity));

						/* check if update in quantity need next action */
                        if(mTarget.get("processedCnt").equals(mTarget.get("num"))){
                            loadNextIncludeBarcode();
                        } else {
							/*get next barcode*/
                            setCounts();
                            setQtyInclude();
                            initIncludeList(includeList);
                            this._sts(id.includebarcode, "");
                            this._sts(id.includequantity, "");
                            U.beepNext();
                            this.setProc(PROC_INCLUDE_BARCODE);
                        }
                    }
                }
                break;
        }
    }

    public void setQty(){
        for (Map<String, String> map : barcodeList) {

            String _qty = mTarget.get("processedCnt");
            if (mTarget.get("order_sub_id").equals(map.get("order_sub_id"))) {

                String _t = map.get("num");
                String temp = U.minusTo(_t,_qty);
                Log.e(TAG," inputedEventt  QTYYYY222  " + temp);
                map.put("num",temp);
                Log.e(TAG," inputedEventt  QTYYYY3333  " + map);
                break;
            }
        }
        mTarget = null;
    }

    public void setQtyInclude(){
        for (Map<String, String> map : includeList) {

            String _qty = mTarget.get("processedCnt");
            if (mTarget.get("order_sub_id").equals(map.get("order_sub_id"))) {

                String _t = map.get("num");
                String temp = U.minusTo(_t,_qty);
                Log.e(TAG," inputedEventt  QTYYYY222  " + temp);
                map.put("num",temp);
                Log.e(TAG," inputedEventt  QTYYYY3333  " + map);
                break;
            }
        }
        mTarget = null;
    }

    @Override
    public void nextProcess() {
        Log.e(TAG, "nexttttPRocessssssssssssss");
        _sts(R.id.barcode, "");
        _sts(R.id.frontage_no, "");
        _sts(R.id.order_no, "");
        lv.setAdapter(null);
        rfList.clear();
        lvInclude.setAdapter(null);
        _sts(R.id.quantity, "");
        setProc(PROC_FRONTAGE);
        _lastUpdateQty = "";
        orderID = "";
        _gt(R.id.frontage_no).requestFocus();
        resetlist();
        setBadge1(0);
        setBadge2(0);
        setBadge3(0);
        sendconfirm =false;
        mTarget = null;
        includeLayout.setVisibility(View.GONE);
    }

    private void resetlist()
    {
        Log.e(TAG, " ResetPackDataaaa ");
        packList.clear();
        rfList.clear();
        orderList.clear();
        gotList.clear();
        barcodeList.clear();
        if(!includeList.isEmpty())
        includeList.clear();
    }

    private void createNewPackItem()
    {

        Log.e(TAG," createNewPackItemmmm ");
        mPackItem = new HashMap<String, String>();
        mPackItem.put("code", mTarget.get("code"));
        mPackItem.put("barcode", mTarget.get("barcode"));
        mPackItem.put("quantity", "1");
        mPackItem.put("order_sub_id", mTarget.get("order_sub_id"));
        packList.add(mPackItem);
        Log.e(TAG," createNewPackItemmmm "+packList);
    }

    public void setRFIDs (List<Map<String, String>> orderlist,List<String> list){
        orderList = orderlist;
        gotList =  list;
    }
    public void setincludeList (List<Map<String, String>> barList){
        if(includeList.size()>0)
            includeList.clear();
        includeList = barList;
    }

    public void setbarcodeList (List<Map<String, String>> barList){
        this.barcodeList = barList;
    }

    public void setOrderID (String order){ orderID = order; }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
    }

    @Override
    public void allclearEvent() {
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
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
            case PROC_FRONTAGE: // 数量
                _sts(R.id.frontage_no, buff);
                break;
            case PROC_INCLUDE_BARCODE:    // バーコード
                _sts(id.includebarcode, buff);
                break;
            case PROC_INCLUDE_QTY: // 数量
                _sts(id.includequantity, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (!barcode.equals("")) {
                if (mProcNo == PROC_BARCODE ) {
                    // check for QR code
                    _sts(R.id.barcode, barcode);
                }

                   if (mProcNo == PROC_QTY) {
                        Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                    String finalbarcode = CommonFunctions.getBracode(barcode);
                    barcode =finalbarcode;
                    Log.e(TAG, "barcode111   " + barcode);
                }
            if (mProcNo == PROC_FRONTAGE)
                _sts(id.frontage_no, barcode);
            if (mProcNo == PROC_INCLUDE_BARCODE) {
                // check for QR code
                _sts(id.includebarcode, barcode);
            }
            if (mProcNo == PROC_INCLUDE_QTY) {
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode;
                Log.e(TAG, "barcode111   " + barcode);
            }

        }
                this.inputedEvent(barcode, true);

    }

    @Override
    public void enterEvent() {

    }
    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_FRONTAGE: // 数量
                _sts(R.id.frontage_no, barcode);
                break;
            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.quantity, barcode);
                break;
            case PROC_INCLUDE_BARCODE:    // バーコード
                _sts(id.includebarcode, barcode);
                break;
            case PROC_INCLUDE_QTY: // 数量
                _sts(R.id.includequantity, barcode);
                break;
        }
    }

    private void loadNextBarcode() {
        setCounts();
        Log.e("ShipActivityyyyyy"," loadNextBarcodeeeeeee ");
        if(mTarget != null){
            Log.e("ShipActivityyyyyy"," loadNextBarcodeeeeeee11111 ");
            barcodeList.remove(mTarget);}
        if (barcodeList.size() > 0) {
            Map<String, String> nextMap = barcodeList.get(0);
            this._sts(id.barcode, "");
            this._sts(id.quantity, "");

            mTarget = null;
            U.beepNext();
            initIncludeList(barcodeList);
            setProc(PROC_BARCODE);

        } else {
            if(!includeList.isEmpty()) {
                if (!BaseActivity.getsinclude())
                    setIncludePop();
                else {

                    includeLayout.setVisibility(View.VISIBLE);
                    initIncludeList(includeList);
                    setProc(PROC_INCLUDE_BARCODE);
                }
          }
          else fixedRequest();
    }
    }

    private void loadNextIncludeBarcode() {
        setCounts();
        Log.e(TAG," loadNextIncludeBarcodeeeeeee ");
        if(mTarget != null){
            Log.e(TAG," loadNextIncludeBarcodeeeeeee11111 ");
            includeList.remove(mTarget);}
        if (includeList.size() > 0) {

            this._sts(id.includebarcode, "");
            this._sts(id.includequantity, "");

            mTarget = null;
            U.beepNext();
            initIncludeList(includeList);
            this.setProc(PROC_INCLUDE_BARCODE);
        } else {
           fixedRequest();
        }
    }


    public void setIncludePop(){
        if(!includeList.isEmpty()){
            showPop("以下の同梱物商品をピックしてください。",includeList,"include");
        }
        else  fixedRequest();
    }


    public void fixedRequest(){

        Log.e(TAG,"fixedddddd   Requesttttt");
        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("order_id", orderID));
        Globals.getterList.add(new ParamsGetter("create_date", _gts(R.id.dateselect)));
        Globals.getterList.add(new ParamsGetter("mode", "complete_status"));

        if(!orderList.isEmpty()) {
            StringBuffer order_sub =new StringBuffer();
            StringBuffer serial =new StringBuffer();
            StringBuffer code =new StringBuffer();
            StringBuffer rf =new StringBuffer();

            for (Map<String, String> map : orderList) {
                order_sub.append(",").append(map.get("order_sub_id"));
                rf.append(",").append(map.get("scanned_rf"));
                code.append(",").append(map.get("code"));

            }

            Globals.getterList.add(new ParamsGetter("code", code.substring(1).toString()));
            Globals.getterList.add(new ParamsGetter("order_sub_id", order_sub.substring(1).toString()));
            Globals.getterList.add(new ParamsGetter("rfid", rf.substring(1).toString()));
        }


            StringBuffer order_sub1 =new StringBuffer();
            StringBuffer code1 =new StringBuffer();
            StringBuffer qty1 = new StringBuffer();

        if(!packList.isEmpty() || !includeList.isEmpty()) {
            if (!packList.isEmpty()) {
                for (Map<String, String> map : packList) {
                    order_sub1.append(",").append(map.get("order_sub_id"));
                    code1.append(",").append(map.get("code"));
                    qty1.append(",").append(map.get("quantity"));
                }
            }

            if (!includeList.isEmpty()) {
                for (Map<String, String> map : includeList) {
                    order_sub1.append(",").append(map.get("order_sub_id"));
                    code1.append(",").append(map.get("code"));
                    qty1.append(",").append(map.get("num"));
                }

            }
            Globals.getterList.add(new ParamsGetter("b_order_sub_id", order_sub1.substring(1).toString()));
            Globals.getterList.add(new ParamsGetter("b_code", code1.substring(1).toString()));
            Globals.getterList.add(new ParamsGetter("b_quantity", qty1.substring(1).toString()));
        }
        Log.e(TAG,"fixedddddd   Requesttttt   getPrinterSelected  " +BaseActivity.getPrinterSelected());

        if(BaseActivity.getPrinterSelected()){
//            Globals.getterList.add(new ParamsGetter("printer_db", "yes"));
            if(BaseActivity.getFileselectedPrinterPOS()>0)
            Globals.getterList.add(new ParamsGetter("attached_printer", BaseActivity.getFileselectedPrinterID()));
            if(BaseActivity.getintegratedselectedPrinterPOS()>0)
                Globals.getterList.add(new ParamsGetter("airprint_printer", BaseActivity.getintegratedselectedPrinterID()));
        }
        mRequestStatus = REQ_QTY;


        new MainAsyncTask(this, Globals.Webservice.Rf_order, 1, RFPickingActivity.this, "Form", Globals.getterList, true).execute();
    }


    public void add(String val) {
        Log.e(TAG,"Valueeeee  "+val);
        int equalIndex = -1;
        int size = rfList.size();
        val = val.substring(4);
        Map <String,String> map = new HashMap<>();
        Log.e(TAG,"Valueeeee becomessss "+val);
        if (size == 0) {
            Log.e(TAG,"Valueeeee becomessss 111  "+val.length());
            if(val.length()> 30)
            {
              map.put("rf",val);
              map.put("match","0");
            rfList.add(map);
            }
            else{
                U.beepError(this, null);
                CommonDialogs.customToast(this, "RFID形式エラーです。再書き込みしてください "+val);}

        } else {
            for (int i = 0; i < size; i++) {
                Map<String, String> map1 = rfList.get(i);
                String v =map1.get("rf");
                if (val.equals(v)) {
                    equalIndex = i;
                    break;
                } else if (i == size - 1) {
                    Log.e(TAG,"Valueeeee becomessss 2222    "+val.length());
                    if(val.length()>  30) {
                        map.put("rf",val);
                        map.put("match","0");
                        rfList.add(map);
                    }else {
                        U.beepError(this, null);
                        CommonDialogs.customToast(this, "RFID形式エラーです。再書き込みしてください "+val);}
                }
                Log.e(TAG,"listttt becomesss  "+rfList);
            }
        }
        initWorkList();

    }


    // now match the scanned RFID list with the list in order
    void matchList()
    {
        //copy rfList to another to keep track of values
      List<Map<String, String>> list = rfList;
      // removelist to remove items from list
      List<Map<String, String>>  removelist = new ArrayList<>();

      for(Map<String, String> map1:list)
      {
          String val = map1.get("rf");
          if(val.length()< 32){
              int n = 32 - val.length();
              for (int i = 0; i < n; i++) {
                  val = "0" + val;
              }
          }

          String str = val.substring(4, 24);
          Log.e(TAG, "Substringgggg   " + str);
          str = str.replaceFirst("^0+(?!$)", "");
          Log.e(TAG, "Substringgggg 111111  " + str);

          for(Map<String, String> map:orderList)
          {
              String sku = map.get("code");
              String qty = map.get("num");
              String scan =  map.get("scan_tag");
              String id = map.get("id");


              if (str.equals(sku))
              {
                  Log.e(TAG, "MAtchListtttt4444444444  " + str +"  "+sku);
                  String _temp = U.plusTo(map.get("processedCnt"),"1");
                  if(scan.equals("0"))
                  map.put("processedCnt",_temp);

                  map1.put("match","1");
                  removelist.add(map1);
                  if(_temp.equals(qty) && scan.equals("0")) {

                      map.put("scan_tag", "1");
                      map.put("scanned_rf", map1.get("rf"));
                      gotList.remove(id);
                      break;
                  }
                  Log.e(TAG, "MAtchListtttt22222   " + "  "+map.get("scan_tag"));
                  Log.e(TAG, "MAtchListtttt55555   " + orderList +"        "+gotList);

              }
              Log.e(TAG, "MAtchListtttt666666   " + orderList +"        "+gotList );
              }
      }

      initRFList(orderList);
      initWorkList();
      if(!removelist.isEmpty()){
          list.removeAll(removelist);
      }
      // if scanned more no. of rfIds than in order
      if(!list.isEmpty()){
          for (Map<String, String> map : list) {
              String sku = map.get("rf");
              String str = sku.substring(4, 24);
              str = str.replaceFirst("^0+(?!$)", "");
              map.put("code",str);
              }
          showPop("以下の商品外してください。",list , "remove");
          U.beepError(this,null);
      }
      else if(gotList.isEmpty()){
          if(!barcodeList.isEmpty()){
              initIncludeList(barcodeList);
          setProc(PROC_BARCODE);
          includeLayout.setVisibility(View.GONE);

          }
          else if(!includeList.isEmpty()) {
              if (!BaseActivity.getsinclude())
                  setIncludePop();
              else {

                  includeLayout.setVisibility(View.VISIBLE);
                  initIncludeList(includeList);
                  setProc(PROC_INCLUDE_BARCODE);
              }
          }
          else fixedRequest();
      }
      else {
          // if there are some pending RFIDs to scan
          List<Map<String, String>>  list1 = new ArrayList<>();
          for (Map<String, String> map : orderList) {
              if( map.get("scan_tag").equals("0")){
            list1.add(map);
          }}
          showPop("未検品商品" , list1, "put");
          U.beepError(this,null);
      }
        setCounts();
    }


    protected ListViewItems initWorkList() {

        Log.e("NewPickingActivity"," initWorkList");
        lv.setAdapter(null);
        final int matchList[] = new int[rfList.size()];
        Log.e("NewPickingActivity"," initWorkList");

        ListViewItems data = new ListViewItems();
        for (int i = 0; i <=rfList.size() - 1; i++) {
            Map<String, String> row = rfList.get(i);
            Log.e("NewPickingActivity"," initWorkList_"+row);

            data.add(data.newItem().add(R.id.txtNO,i+1+"")
                    .add(id.txtEPC,row.get("rf"))
            );
            Log.e("NewPickingActivity","DATA >>>>"+data);
            if (row.get("match").equals("0"))
                matchList[i] = 0;
            else  matchList[i] = 1;

        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.item_epc){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TextView no = (TextView) v.findViewById(R.id.txtNO);
                TextView epc = (TextView) v.findViewById(R.id.txtEPC);

                if (matchList[position] == 0) {
                    Log.e(TAG, "not match positionnn    " + position);
                    no.setTextColor(getResources().getColor(R.color.primary_dark));
                    epc.setTextColor(getResources().getColor(R.color.primary_dark));
                } else {
                    Log.e(TAG, "match positionnn    " + position);
                    no.setTextColor(getResources().getColor(R.color.green_color));
                    epc.setTextColor(getResources().getColor(R.color.green_color));
                }

                return v;
            }
        };
        Log.e("NewPickingActivity","LIst adapter >>>>>>>");
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }

    public ListViewItems initRFList(List<Map<String, String>> list) {

        lvInclude.setAdapter(null);

        ListViewItems data = new ListViewItems();

        for (int i = 0; i <=list.size() - 1; i++) {
            Map<String, String> row = list.get(i);

            if(row.get("scan_tag").equals("0"))
            data.add(data.newItem().add(id.rf_txt_1, row.get("code"))
                    .add(id.rf_txt_2, row.get("num")));
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.dialog_list_row);

        lvInclude.setAdapter(adapter);
        // 単一選択モードにする
        lvInclude.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        return data;
    }

    public ListViewItems initIncludeList(List<Map<String, String>> list) {

        lvInclude.setAdapter(null);

        ListViewItems data = new ListViewItems();
        for (int i = 0; i <=list.size() - 1; i++) {
            Map<String, String> row = list.get(i);

            data.add(data.newItem().add(id.rf_txt_1, row.get("code"))
                    .add(id.rf_txt_2, row.get("num")));

        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.dialog_list_row);

        lvInclude.setAdapter(adapter);
        // 単一選択モードにする
        lvInclude.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        return data;
    }

    void setCounts(){
        //pending rows
      pending =0;
      pending = gotList.size();

      for(Map<String,String> map :barcodeList){
          if(!map.get("processedCnt").equals(map.get("num"))){
            ++pending;
          }}

          //set include ccount
      include = includeList.size();

      //set scanned counts
      scanned =0;
      for(Map<String,String> map :orderList){
          if(map.get("scan_tag").equals("1")){
              ++scanned;
          }
      }
      for(Map<String,String> map :barcodeList){
          if(!map.get("processedCnt").equals("0")) {
              ++scanned;
          }}

      updateBadge1(include+"");
      updateBadge2(pending+"");
      updateBadge3(scanned+"");
  }
    protected boolean showInfo(String status){
        Log.e(TAG,"showInfooooooo");
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
            TextView title =(TextView)getPopupWindow2().getContentView().findViewById(R.id.title);
            TextView rf_tag =(TextView)getPopupWindow2().getContentView().findViewById(R.id.rf_tag);

            Log.e(TAG,"Staussss   "+status );
            if (status.equals("scanned")) {
                title.setText("検品済み商品");
                rf_tag.setVisibility(View.VISIBLE);
            }
            else if(status.equals("pending")){
                title.setText("未検品商品");
                rf_tag.setVisibility(View.VISIBLE);
            }
            else if(status.equals("include")){
                title.setText("同梱物商品");
                rf_tag.setVisibility(View.GONE);
            }
            ListView lv = (ListView) getPopupWindow2().getContentView().findViewById(R.id.orderPicking);
            initList(lv,status);

            // 画面中央に表示
            getPopupWindow2().showAtLocation(findViewById(R.id.barcode), Gravity.CENTER, 0, 32);
            return true;
        }}

    protected ListViewItems initList(ListView lv,String status) {
        Log.e(TAG,"initListtttttt");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (int i = 0 ;i < showList.size(); i++) {
            Map<String, String> row = showList.get(i);


            Log.e(TAG,"initListtttttt11111");
            if(status.equals("include"))
                data.add(data.newItem().add(id.rf_txt_1, row.get("code"))
                        .add(id.rf_txt_3, row.get("num")));
             else
            data.add(data.newItem().add(id.rf_txt_1, row.get("code"))
                    .add(id.rf_txt_2, row.get("rfid_flag"))
                    .add(id.rf_txt_3, row.get("num"))
            );
        }

        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.rf_product_list_row) ;
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }


    public void updateBadge1(String orderCount) {
        Log.e(TAG,"updateBadge11111111111111"  + orderCount);
        setBadge1(Integer.valueOf(orderCount));
    }

    public void updateBadge2(String qtyCount) {
        Log.e(TAG,"updateBadge222222222222" + qtyCount);
        setBadge2(Integer.valueOf(qtyCount));
    }

    public void updateBadge3(String qtyCount) {
        Log.e(TAG,"updateBadge333333333333333333" + qtyCount);
        setBadge3(Integer.valueOf(qtyCount));
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
                Log.e(TAG, "CODEeee============Nulllll");
                CommonDialogs.customToast(getApplicationContext(), "Network error occured");

            }
            if ("0".equals(code) == true) {

                Log.e("SendLogs111", code + "  " + msg + "  " + result1);

                if (mRequestStatus == REQ_FRONTAGE) {
                    new GetRfOrder().post(code, msg, result1, mHash, RFPickingActivity.this);
                } else if (mRequestStatus == REQ_QTY) {
                    sendconfirm =false;
                    nextProcess();
                    U.beepFinish(this, null);

                }
            } else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(RFPickingActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }else {
                    if (mRequestStatus == REQ_FRONTAGE) {
                        new GetRfOrder().valid(code, msg, result1, mHash, RFPickingActivity.this);
                    } else if (mRequestStatus == REQ_QTY) {
                        sendconfirm =false;
                       U.beepError(this, msg);
                    }
                }

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }


    void showPop(String title, List <Map<String,String>> list , final String status){
        Log .e(TAG, "List issss   "+list);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set GUI of login screen
        dialog.setContentView(R.layout.dialog_with_list);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());


        dialog.setCanceledOnTouchOutside(false);

        // Init button of login GUI
        TextView txt=(TextView)dialog.findViewById(R.id.text1);
        TextView txt2=(TextView)dialog.findViewById(R.id.text3);
        ListView listView=(ListView) dialog.findViewById(id.listview) ;
        Button close=(Button)dialog.findViewById(R.id.close_btn);
        txt.setText(title);
        if(!status.equals("include")){
            txt2.setVisibility(View.GONE);
             close.setText("OK");}
        else {   txt2.setVisibility(View.VISIBLE);
            close.setText("ピック");}

        listView.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (int i = 0 ;i < list.size(); i++) {
            Map<String, String> row = list.get(i);


            Log.e(TAG,"initListtttttt11111");
            if(!status.equals("include"))
                data.add(data.newItem().add(id.rf_txt_1, row.get("code")));
            else
                data.add(data.newItem().add(id.rf_txt_1, row.get("code"))
                        .add(id.rf_txt_2, row.get("num"))
                );
        }

        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.dialog_list_row) ;
        listView.setAdapter(adapter);



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.equals("include")){
                    if(!sendconfirm){
                    fixedRequest();
                    sendconfirm = true;
                    }
                }

                dialog.dismiss();

            }
        });
        // Make dialog box visible.
        dialog.show();

    }


    @Override
    public void didGeneralSuccess(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), s + " Success", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void didGeneralError(final String invokeApi, final String errorMessage) {
        Toast.makeText(getApplicationContext(), invokeApi + ": " + errorMessage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void didDiscoverTagInfo(final TagInformationFormat tagInformationFormat) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                add(tagInformationFormat.getEPCHexString());

//                    adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void didReadEpc(byte[] bytes) {

    }

    @Override
    public void didGetFirmwareVersion(String s) {

    }

    @Override
    public void didGetRfPower(byte b) {

    }

    @Override
    public void didGetRfSensitivity(RfSensitivityLevel rfSensitivityLevel) {

    }

    @Override
    public void didGetFrequencyList(ArrayList<Double> arrayList) {

    }

    @Override
    public void didGetTriggerType(TriggerType triggerType) {

    }

    @Override
    public void didGetSessionAndTarget(Session session, Target target) {

    }

    @Override
    public void didGetQValue(byte b) {

    }

    @Override
    public void didGetBuzzerOperationMode(BuzzerOperationMode buzzerOperationMode) {

    }

}
