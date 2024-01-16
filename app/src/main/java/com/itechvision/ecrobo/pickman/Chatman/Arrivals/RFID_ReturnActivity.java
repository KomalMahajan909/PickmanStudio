package com.itechvision.ecrobo.pickman.Chatman.Arrivals;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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
import com.gigatms.parameters.Target;
import com.gigatms.parameters.TriggerType;
import com.gigatms.tools.Glog;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetArrivaldetail;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetReturndetail;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.ConnectedDevices;
import com.itechvision.ecrobo.pickman.Chatman.RFDeviceBaseScan;
import com.itechvision.ecrobo.pickman.R;
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
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RFID_ReturnActivity extends BaseActivity implements View.OnClickListener, MainAsynListener, UHFCallback {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.txt1) TextView text1;
    @BindView(R.id.txt2) TextView text2;
    @BindView(R.id.rfid) EditText edtEpc;
    @BindView(R.id.seriallayout) LinearLayout serailLayout;
    @BindView(R.id.qntylayout) LinearLayout qntylayout;
    @BindView(R.id.barcodelayout) LinearLayout barcodelayout;
    @BindView(R.id.rfidLayout) LinearLayout rfIDlayout;
    @BindView(R.id.textlayout)LinearLayout textLayout;
    @BindView(R.id.rfidRg)RadioGroup radio;
    @BindView(R.id.btn_connect) Button connectionbtn;

    boolean confirmClick = false;

    String TAG = RFID_ReturnActivity.class.getSimpleName();

    protected int mProcNo = 0;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_RFID = 2;
    public static final int PROC_QTY = 3;
    public static final int PROC_SKU = 4;

    private TextToSpeak mTextToSpeak;
    public Button sendbtn;

    private boolean showKeyboard;
    private boolean visible = false;
    public Context mcontext = this;

    String adminID = "";

    String rf;
    String rfscan = "";

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    SweetAlertDialog pDialog,pDialog1,pDialog2;
    public static int mRequestStatus = 0;

    public boolean orderRequestSettings = false;

    public static final int REQ_BARCODE = 1;
    public static final int REQ_QTY = 2;

    //ReadWrite process starts

    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    String devMacAddress ="";

    private UHFDevice uhf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid__return);
        ButterKnife.bind(RFID_ReturnActivity.this);

        getIDs();

        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        sendbtn =(Button)findViewById(R.id.btnsend) ;

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

        mTextToSpeak = new TextToSpeak(this);
        Log.e(TAG, "OnCreateeeeee");


        _gt(R.id.serial).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        _gt(R.id.sku).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

        if (mProcNo == 0) nextProcess();

        // READ WRITE Working
        Intent i = getIntent();
        if (i.hasExtra(EXTRAS_DEVICE_ADDRESS)){
            devMacAddress = i.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        }

        initUhfDevice();



    }
    //set title and icons on actionbar
    private void getIDs() {
        actionbarImplement(this, "返品検品", " ",
                0, false, false, false);

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
        }
    }

    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
        if (visible == false) {

            visible = true;
            numbrbtn.setText(R.string.hideKeyboard);
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
                connectionbtn.setText("Connect");
            } else if (uhf.getConnectionState().equals(ConnectionState.CONNECTED)) {
                connectionbtn.setText("Disconnect");
            }
        }
    }

    @OnClick(R.id.btnsend) void Send(){
        showDialog();
    }

    @OnClick(R.id.btnread) void Read(){
        Log.e(TAG,"READING >>   "+uhf);
        String psw = "00000000";
        if (uhf != null)
        uhf.readEpc(psw);
    }

    @OnClick(R.id.btn_connect) void Connect(){
        if(connectionbtn.getText().equals("Connect")){
            uhf.connect();
            connectionbtn.setText("Disconnect");
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
                            Intent i =  new Intent(RFID_ReturnActivity.this, RFDeviceBaseScan.class);
                            i.putExtra("rf_use","return");
                            startActivity(i);
                            connectionbtn.setText("Connect");
                            pDialog1.dismiss();
                        }

                    }, 1000);

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

    @OnClick(R.id.rfClear) void clear(){
        clearEvent();
    }


    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {

            case PROC_RFID:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.rfid).setFocusableInTouchMode(true);
                _gt(R.id.rfid).requestFocus();
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.rfid).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));

                break;

            case PROC_BARCODE:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.barcode).requestFocus();
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.rfid).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;

            case PROC_QTY:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.rfid).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));;
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                if (_gts(R.id.quantity).equals("1"))
                    mTextToSpeak.startSpeaking("1");
                break;
        }
    }

    public void RFIDUse (View view){
        switch (radio.getCheckedRadioButtonId()) {
            case R.id.userf:
                orderRequestSettings = false;
                rfIDlayout.setVisibility(View.VISIBLE);
                barcodelayout.setVisibility(View.GONE);
                qntylayout.setVisibility(View.GONE);
                serailLayout.setVisibility(View.VISIBLE);
                textLayout.setVisibility(View.VISIBLE);
                setProc(PROC_RFID);
                break;
            case R.id.usebarcode:
                orderRequestSettings = true;
                rfIDlayout.setVisibility(View.GONE);
                barcodelayout.setVisibility(View.VISIBLE);
                qntylayout.setVisibility(View.GONE);
                serailLayout.setVisibility(View.GONE);
                textLayout.setVisibility(View.VISIBLE);
                setProc(PROC_BARCODE);
                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {

        switch (mProcNo) {
            case PROC_RFID:
                if(_gts(R.id.rfid).equals("")){
                    Read();
                }

                String rf = _gts(R.id.rfid);
                rf = rf.replaceFirst("^0+(?!$)", "");
                _sts(R.id.rfid,rf);

                if ("".equals(rfscan)) {
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "RFIDは必須です");
                    _gt(R.id.rfid).setFocusableInTouchMode(true);
                    break;
                }
                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("rf_id", rfscan));
                Globals.getterList.add(new ParamsGetter("mode","get_product"));

                mRequestStatus = REQ_BARCODE;

                new MainAsyncTask(this, Globals.Webservice.rfid_return, 1, RFID_ReturnActivity.this, "Form", Globals.getterList, true).execute();

                break;

            case PROC_BARCODE:    // バーコード

                String barcode1 = _gts(R.id.barcode);
                if ("".equals(barcode1)) {
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);

                    break;
                }

                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
                Globals.getterList.add(new ParamsGetter("mode","get_product"));

                mRequestStatus = REQ_BARCODE;

                new MainAsyncTask(this, Globals.Webservice.rfid_return, 1, RFID_ReturnActivity.this, "Form", Globals.getterList, true).execute();

                break;
            case PROC_SKU:    // バーコード

                break;
            case PROC_QTY: // 数量
                String qty = _gts(R.id.quantity);
                if (qty.equals(""))
                    qty = "1";
                String barcode = _gts(R.id.barcode);

                Log.e(TAG, "Qtyyyyy  " + qty);
                Log.e(TAG, "buff " + buff);

                if (isScaned) {

                    Log.e(TAG, "Barcode at present is   " + barcode);
                    if (buff.equals(barcode)) {
                        U.beepSuccess();
                        qty = U.plusTo(qty, "1");
                        _sts(R.id.quantity, qty);
                        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                        break;
                    } else {
                        U.beepError(this, "Barcode dont match");
                        break;
                    }
                }

                if ("".equals(qty)) {
                    U.beepError(this, "数量は必須です");
                    _gt(R.id.quantity).setFocusableInTouchMode(true);
                    break;
                } else if (!U.isNumber(qty)) {
                    U.beepError(this, "数量は数値のみ入力可能です");
                    _gt(R.id.quantity).setFocusableInTouchMode(true);
                    break;
                }

                showDialog();

                break;
        }
    }

    void showDialog(){

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog.setCancelable(true);
        pDialog.setContentText("Do you want to confirm the return ?");
        pDialog.setConfirmText("Yes");
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                if(!confirmClick) {
                    pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    confirmClick = true ;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!orderRequestSettings)
                                sendRequest(rfscan, "1");
                            else
                                sendRequest(_gts(R.id.barcode), "1");
                            pDialog.dismiss();
                        }

                    }, 1000);
                }

                else
                    CommonDialogs.customToast(RFID_ReturnActivity.this, "Wait");
            }
        });
        pDialog.setCancelText("No");
        pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {

            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                pDialog.dismiss();
            }
        });

        pDialog.show();
    }


    public void setSerial(){
         String rfid = rfscan;
         String serial = rfid.substring(24);
         Log.e(TAG,"SERial>>>>>>>>  "+serial);
         serial = serial.replaceFirst("^0+(?!$)", "");
         _sts(R.id.serial, serial);
     }

    void sendRequest(String barcode, String qty) {
        String serial = _gts(R.id.serial);

        Globals.getterList = new ArrayList<>();

        Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("mode","return"));
        if(!orderRequestSettings) {
            rf= rfscan;
            Globals.getterList.add(new ParamsGetter("rf_id", barcode));
        }
        else {
            rf= barcode;
            Globals.getterList.add(new ParamsGetter("barcode", barcode));
        }
        Globals.getterList.add(new ParamsGetter("qty", qty));

        if(BaseActivity.getPrinterSelected()){
           Globals.getterList.add(new ParamsGetter("printer_db", "yes"));
           // Globals.getterList.add(new ParamsGetter("printer_id", BaseActivity.getbarcodeselectedPrinterID()));
        }
           // Globals.getterList.add(new ParamsGetter("printer_id", BaseActivity.getbarcodeselectedPrinterID()));


        mRequestStatus = REQ_QTY;
        new MainAsyncTask(this, Globals.Webservice.rfid_return, 1, RFID_ReturnActivity.this, "Form", Globals.getterList, true).execute();
    }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }


    @Override
    public void nextProcess() {
        Log.e(TAG, "nexttttPRocessssssssssssss");
        _sts(R.id.barcode, "");
        _sts(R.id.serial, "");
        _sts(R.id.sku, "");
        _sts(R.id.rfid, "");
        _sts(R.id.quantity, "");
        rfscan = "";

        if(!orderRequestSettings){
            rfIDlayout.setVisibility(View.VISIBLE);
            barcodelayout.setVisibility(View.GONE);
            qntylayout.setVisibility(View.GONE);
            serailLayout.setVisibility(View.VISIBLE);
            textLayout.setVisibility(View.VISIBLE);
            setProc(PROC_RFID);}
        else {
            rfIDlayout.setVisibility(View.GONE);
            barcodelayout.setVisibility(View.VISIBLE);
            qntylayout.setVisibility(View.GONE);
            serailLayout.setVisibility(View.GONE);
            textLayout.setVisibility(View.VISIBLE);
            setProc(PROC_BARCODE);
        }
    }


    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
        text1.setText("");
        text2.setText("");
        nextProcess();
    }

    @Override
    public void allclearEvent() {
        mTextToSpeak.startSpeaking("clear");
        text1.setText("");
        text2.setText("");
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
            case PROC_RFID: // 数量
                _sts(R.id.rfid, buff);
                break;
        }
    }

    @Override
    protected void onDestroy() {
//        if(uhf != null)
//            uhf.disconnect();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
//        if(uhf != null)
//            uhf.disconnect();
        super.onStop();

    }

    @Override
    public void scanedEvent(String barcode) {
        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEventttttt");

            if (mProcNo == PROC_BARCODE) {
                // check for QR code
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                Log.e(TAG, "Length of barcode is   " + barcode.length());
                if (BaseActivity.getUrl().equals("https://air-logi-st.air-logi.com/service") && BaseActivity.getShopId().equals("1011")) {
                    String result = "";
                    if (barcode.length() == 13) {
                        result = barcode.substring(0, barcode.length() - 1);
                        Log.e(TAG, "BArcode after chopping last character becomes " + result);
                        barcode = result;
                    } else if (barcode.length() == 14) {
                        result = barcode.substring(1, barcode.length() - 1);
                        Log.e(TAG, "BArcode after chopping first and last character becomes " + result);
                        barcode = result;
                    }
                }
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode;

                _sts(R.id.barcode, barcode);
            }
        }

        if (mProcNo == PROC_QTY) {
            Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
            String finalbarcode = CommonFunctions.getBracode(barcode);
            barcode =finalbarcode;
            Log.e(TAG, "barcode111   " + barcode);
        }
        this.inputedEvent(barcode, true);
    }

    @Override
    public void enterEvent() {

    }

    public void setLayout(){

    }



    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {

            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.quantity, barcode);
                break;
        }}


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
        Log.d(TAG, "didGeneralError: " + invokeApi + ": " + errorMessage);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), invokeApi + ": " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void didDiscoverTagInfo(TagInformationFormat tagInformationFormat) {

    }

    @Override
    public void didReadEpc(final byte[] epc) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                edtEpc.setText(Glog.bytesToHexString(epc, ""));
                rfscan = _gts(R.id.rfid);
                String rf = _gts(R.id.rfid);
                 rf = rf.replaceFirst("^0+(?!$)", "");
                _sts(R.id.rfid,rf);

            }
        });
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

    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {
        HashMap<String, String> mHash = new HashMap<>();
        Log.e(TAG, result.toString());
        confirmClick = false;
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

                if (mRequestStatus == REQ_BARCODE) {
                    new GetReturndetail().post(code, msg, result1, mHash, RFID_ReturnActivity.this);
                }
                else if (mRequestStatus == REQ_QTY) {
                    text1.setText(rf);
                    if(!orderRequestSettings)
                    text2.setText("RFID書込みと入荷が完了しました！");
                    else text2.setText("Baarcode 書込みと入荷が完了しました！");
                    U.beepKakutei(this, "検品データを登録しました。");
                    nextProcess();
                }


            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(RFID_ReturnActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else if("10299".equals(code)){
                showErrorPopup(msg);
            }
            else{
                if (mRequestStatus == REQ_BARCODE) {
                    new GetReturndetail().valid(code, msg, result1, mHash, RFID_ReturnActivity.this);
                } else if (mRequestStatus == REQ_QTY) {
                    U.beepError(this, msg);
                }
            }

        } catch (Exception e) {
            confirmClick = false;
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {
        confirmClick = false;
    }

    public void showErrorPopup(String msg){
        pDialog2 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog2.setCancelable(false);

        pDialog2.setContentText(msg);
        pDialog2.setConfirmText("Ok");
        pDialog2.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                pDialog2.dismiss();
            }
        });
        pDialog2.show();
    }
}
