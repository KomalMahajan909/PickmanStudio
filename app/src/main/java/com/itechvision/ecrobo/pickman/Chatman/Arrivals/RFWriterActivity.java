package com.itechvision.ecrobo.pickman.Chatman.Arrivals;

import android.content.Context;
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
import android.widget.RelativeLayout;
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
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.ConnectedDevices;
import com.itechvision.ecrobo.pickman.Chatman.RFDeviceBaseScan;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RFWriterActivity  extends BaseActivity implements View.OnClickListener, UHFCallback {

static SlidingMenu menu;
SlideMenu slidemenu;
@BindView(R.id.add_layout) Button numbrbtn;
@BindView(R.id.layout_main) RelativeLayout mainlayout;
@BindView(R.id.layout_number) RelativeLayout layout;
@BindView(R.id.actionbar) ActionBar actionbar;
@BindView(R.id.write) Button write;
@BindView(R.id.btn_connect) Button connectionbtn;

private TextToSpeak mTextToSpeak;
private boolean showKeyboard;

protected int mProcNo = 0;
public static final int PROC_SKU = 1;
public static final int PROC_SERIAL = 2;

private boolean visible = false;
public Context mcontext = this;

public static final String DOMAINPREFERENCE = "domain";

String TAG = RFWriterActivity.class.getSimpleName();

public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
String devMacAddress ="";


private UHFDevice uhf;
private String action = "";

SweetAlertDialog pDialog,pDialog1;
SharedPreferences spDomain;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfwriter);

       ButterKnife.bind(RFWriterActivity.this);

       getIDs();

       ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
       viewPager.setAdapter(new CustomPagerAdapter(this));

       mTextToSpeak = new TextToSpeak(this);
       Log.e(TAG, "OnCreateeeeee");

       showKeyboard = BaseActivity.getaddKeyboard();
       if (showKeyboard == true)
       {
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

       if (mProcNo == 0) nextProcess();

       // READ WRITE Working
       Intent i = getIntent();
       if (i.hasExtra(EXTRAS_DEVICE_ADDRESS)){
           devMacAddress = i.getStringExtra(EXTRAS_DEVICE_ADDRESS);
       }

       initUhfDevice();
   }

    private void initUhfDevice() {

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

    private void getIDs()
    {
        actionbarImplement(this, "RFID書き込み", " ", 0, false, false, false);

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
        if (visible == false)
        {
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

    @OnClick(R.id.write) void Write(){
        //  set password by default

        String sku = "";
        int skuLength = _gts(R.id.sku).length();
        int n = 21 - skuLength;
        for (int i = 0; i < n; i++) {
            sku = "0" + sku;
        }
        String finalsku = sku + _gts(R.id.sku);
        Log.d(TAG, "onClick: finalsku" + finalsku);

        String serial = "";
        int serialLength = _gts(R.id.serialNo).length();
        int a = 8 - serialLength;
        for (int i = 0; i < a; i++) {
            serial = "0" + serial;
        }

        String finalserial = serial + _gts(R.id.serialNo);

        String epc = "000" + finalsku + finalserial;
        Log.d(TAG, "onClick: finalsku  epc" + epc);
        String psw = "00000000";

        action = "write";
        byte[] e = Glog.hexStringToByteArray(epc);
        Log.e(TAG, "writeEpc   "+e);
        if(uhf != null)
            uhf.writeEpc(psw, e);
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
                            Intent i =  new Intent(RFWriterActivity.this, RFDeviceBaseScan.class);
                            i.putExtra("rf_use","write");
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

    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {
            case PROC_SKU:
                _gt(R.id.sku).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.sku).setFocusableInTouchMode(true);
                _gt(R.id.serialNo).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;

            case PROC_SERIAL:
                _gt(R.id.serialNo).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.serialNo).setFocusableInTouchMode(true);
                _gt(R.id.sku).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
        }
    }


    @Override
    public void inputedEvent(String buff) {
        switch (mProcNo) {
            case PROC_SKU:
                if (_gts(R.id.sku).equals("")) {
                    U.beepError(this, "SKU is empty");
                    _gt(R.id.sku).setFocusableInTouchMode(true);
                    break;
                }
                setProc(PROC_SERIAL);
                break;
            case PROC_SERIAL:
                if (_gts(R.id.serialNo).equals(""))
                {
                    U.beepError(this, "SERIAL is empty");
                    _gt(R.id.serialNo).setFocusableInTouchMode(true);
                    break;
                }
                if (_gts(R.id.serialNo).length()>8)
                {
                    U.beepError(this, "SERIAL is greater than 8 digits");
                    _gt(R.id.serialNo).setFocusableInTouchMode(true);
                    break;
                }

                Write();
                break;
        }
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
            case PROC_SKU:    // バーコード
                _sts(R.id.sku, buff);
                break;

            case PROC_SERIAL:
                _sts(R.id.serialNo,buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEventttttt");

            if (mProcNo == PROC_SKU)
                _sts(R.id.sku, barcode);

            else if (mProcNo==PROC_SERIAL)
                _sts(R.id.serialNo,barcode);

        }
        this.inputedEvent(barcode);
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_SKU:    // バーコード
                _sts(R.id.sku, barcode);
                break;

            case PROC_SERIAL:
                _sts(R.id.serialNo,barcode);
                break;
        }
    }

    @Override
    public void nextProcess() {
        Log.e(TAG, "nexttttPRocessssssssssssss");
        _sts(R.id.sku, "");
        _sts(R.id.serialNo, "");
        setProc(PROC_SKU);

    }

    @Override
    public void didGeneralSuccess(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(action.equals("write")){
                    nextProcess();}
                else
                    Toast.makeText(getApplicationContext(), s + " Success", Toast.LENGTH_SHORT).show();
                action = "";
            }
        });
    }

    @Override
    public void didGeneralError(final String invokeApi, final String errorMessage) {
        Log.d(TAG, "didGeneralError: " + invokeApi + ": " + errorMessage);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(action.equals("write")){
                    showDialog("RFID書き込みされませんでした。");
                }
                else {
                    Toast.makeText(getApplicationContext(), invokeApi + ": " + errorMessage, Toast.LENGTH_SHORT).show();
                }
                action = "";
            }
        });
    }

    @Override
    public void didDiscoverTagInfo(TagInformationFormat tagInformationFormat) {

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
