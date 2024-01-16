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
import android.widget.EditText;
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
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
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

public class RFReadWriteActivity extends BaseActivity implements View.OnClickListener, UHFCallback {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.btn_connect) Button connectionbtn;
    @BindView(R.id.read_rf) EditText edtEpc;

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    private TextToSpeak mTextToSpeak;
    private boolean showKeyboard;
    private boolean visible = false;
    public Context mcontext = this;

    public Button writebt,sendbtn;

    protected int mProcNo = 0;
    public static final int PROC_READRF = 1;
    public static final int PROC_WRITERF = 2;

    String rfscan = "";
    SweetAlertDialog pDialog,pDialog1;
    String adminID = "";

    String TAG = RFReadWriteActivity.class.getSimpleName();

    //ReadWrite process starts

    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    String devMacAddress ="";

    private UHFDevice uhf;
    private String action = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfread_write);

        ButterKnife.bind(RFReadWriteActivity.this);

        getIDs();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        writebt =(Button)findViewById(R.id.btnwrite) ;
        sendbtn =(Button)findViewById(R.id.btnsend) ;

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

    //set title and icons on actionbar
    private void getIDs() {
        actionbarImplement(this, "RFReadWrite", " ",
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
    @Override
    public void onResume() {
        super.onResume();
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
    @OnClick(R.id.rfClear) void clear(){
        clearEvent();
    }

    @OnClick(R.id.btnread) void Read(){
        if(uhf!= null) {
            Log.e(TAG, "READING >>   " + uhf);
            String psw = "00000000";
            action = "read";
            uhf.readEpc(psw);
        }
    }

    @OnClick(R.id.btnwrite) void Write(){
        //  set password by default
        String psw = "00000000";
        String epc = _gts(R.id.write_rf);
        action = "write";
        byte[] e = Glog.hexStringToByteArray(epc);
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
                            Intent i =  new Intent(RFReadWriteActivity.this, RFDeviceBaseScan.class);
                            i.putExtra("rf_use","readWrite");
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

            case PROC_READRF:
                _gt(R.id.write_rf).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.read_rf).setFocusableInTouchMode(true);
                _gt(R.id.read_rf).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                Read();
                break;

            case PROC_WRITERF:
                _gt(R.id.write_rf).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.write_rf).setFocusableInTouchMode(true);
                _gt(R.id.read_rf).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
        }
    }


    @Override
    public void inputedEvent(String buff) {
        switch (mProcNo) {
            case PROC_READRF:
                if(_gts(R.id.read_rf).equals("")){
                    Read();
                }

                String rf = _gts(R.id.read_rf);
                rf = rf.replaceFirst("^0+(?!$)", "");
                _sts(R.id.read_rf,rf);

                if ("".equals(rfscan)) {
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "RFIDは必須です");
                    _gt(R.id.read_rf).setFocusableInTouchMode(true);
                    break;
                }
                setProc(PROC_WRITERF);
                break;

            case PROC_WRITERF:    // バーコード
                String writerf = _gts(R.id.write_rf);
                if ("".equals(writerf)) {
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.write_rf).setFocusableInTouchMode(true);
                    break;
                }
                Write();
                break;
        }
    }

    @Override
    public void nextProcess() {
        Log.e(TAG, "nexttttPRocessssssssssssss");
        _sts(R.id.write_rf, "");
        _sts(R.id.read_rf, "");
        setProc(PROC_READRF);
        _gt(R.id.write_rf).requestFocus();
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
            case PROC_WRITERF:    // バーコード
                _sts(R.id.write_rf, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEventttttt");

            if (mProcNo == PROC_WRITERF)
                _sts(R.id.write_rf, barcode);

        }
        this.inputedEvent(barcode);
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_WRITERF:    // バーコード
                _sts(R.id.write_rf, barcode);
                break;
        }
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
    public void didReadEpc(final byte[] epc) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                edtEpc.setText(Glog.bytesToHexString(epc, ""));
                rfscan = _gts(R.id.read_rf);
                String rf = _gts(R.id.read_rf);
                rf = rf.replaceFirst("^0+(?!$)", "");
                _sts(R.id.read_rf,rf);
                setProc(PROC_WRITERF);
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

}
