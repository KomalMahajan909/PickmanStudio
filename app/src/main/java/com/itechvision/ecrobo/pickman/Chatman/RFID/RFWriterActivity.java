package com.itechvision.ecrobo.pickman.Chatman.RFID;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.view.ViewPager;
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
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.ConnectedDevices;
import com.itechvision.ecrobo.pickman.Chatman.RFDeviceBaseScan;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
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

public class RFWriterActivity  extends BaseActivity implements View.OnClickListener, UHFCallback , MainAsynListener {

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
    ECRApplication app=new ECRApplication();
    String adminID="";
    protected int mProcNo = 0;
    public static final int PROC_SKU = 1;
    public static final int PROC_SERIAL = 2;
    private boolean visible = false;
    public Context mcontext = this;
    public static int mRequestStatus =0;
    public static final int REQ_CHECK = 1;
    public static final int REQ_WRITE = 2;
    String TAG = RFWriterActivity.class.getSimpleName();
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    String devMacAddress ="";
    private UHFDevice uhf;
    private String action = "",readEPc = "";
    SweetAlertDialog pDialog,pDialog1;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfwriter);

       ButterKnife.bind(RFWriterActivity.this);
       Log.d(TAG,"On Create ");
       getIDs();

       spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);

       adminID = spDomain.getString("admin_id", null);

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
       Log.e(TAG,"Createeeeeeeeeeee   "+devMacAddress);
       if (i.hasExtra(EXTRAS_DEVICE_ADDRESS)){
           Log.e(TAG,"Createeeeeeeeeeee11111111   ");
           devMacAddress = i.getStringExtra(EXTRAS_DEVICE_ADDRESS);
           Log.e(TAG,"MACCCCCCCCCCCC   "+devMacAddress);
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


    void Enter(){
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
    }

    @OnClick(R.id.write) void Write(){
      Enter();

    }
    void Read()
    {
        // read rfid TAG first
        if(uhf!= null) {
            Log.e(TAG, "READING >>   " + uhf);
            String psw = "00000000";
            action = "read";
            uhf.readEpc(psw);
        }

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
                startTimer();
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

                String rf =  CreateRFID();

                stopTimer();
                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("serial", _gts(R.id.serialNo)));
                Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.sku)));
                Globals.getterList.add(new ParamsGetter("mode", "check"));
                Globals.getterList.add(new ParamsGetter("rfid", rf));
                Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
                mRequestStatus = REQ_CHECK;

                new MainAsyncTask(this, Globals.Webservice.rf_write, 1, RFWriterActivity.this, "Form", Globals.getterList, true).execute();

                break;
        }
    }


    String CreateRFID()
    {
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
        return epc;
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

    public void callWrite()
    {
        String epc = CreateRFID();
        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("serial", _gts(R.id.serialNo)));
        Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.sku)));
        Globals.getterList.add(new ParamsGetter("rfid", epc));
        Globals.getterList.add(new ParamsGetter("old_rfid", readEPc));
        Globals.getterList.add(new ParamsGetter("mode", "write"));
        Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
        mRequestStatus = REQ_CHECK;

        new MainAsyncTask(this, Globals.Webservice.rf_write, 1, RFWriterActivity.this, "Form", Globals.getterList, true).execute();

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
                Log.e(TAG,"didGeneralSuccess   "+s);

                if(action.equals("write")){
                    action = "";
                    callWrite();
                }

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
                else if(action.equals("read"))
                    Toast.makeText(getApplicationContext(), "Error reading  "+invokeApi + ": " + errorMessage, Toast.LENGTH_SHORT).show();

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
                readEPc = Glog.bytesToHexString(epc, "");
              // call write EPc function
                String epc =  CreateRFID();
                String psw = "00000000";

                action = "write";
                byte[] e = Glog.hexStringToByteArray(epc);
                Log.e(TAG, "writeEpc   "+e);
                if(uhf != null)
                    uhf.writeEpc(psw, e);
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

                if (mRequestStatus == REQ_CHECK) {
                    JsonHash row = (JsonHash) result1.get(0);
                    Log.e(TAG,">>>>>>>>>>>"+row);

                    String rfexists = "0";
                    if(row.containsKey("rfid_exists"))
                    {
                        rfexists = row.getStringOrNull("rfid_exists");
                    }
                    if(rfexists.equals("0")){
                        final SweetAlertDialog pDialog1 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
                        pDialog1.setCancelable(true);
                        pDialog1.setTitleText("入荷されてないRFIDです。");
                        pDialog1.setContentText("書き込みしますか？");
                        pDialog1.setConfirmText("Yes");
                        pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Read();
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
                    }
                    else Read();
                } else if (mRequestStatus == REQ_WRITE) {
                    U.beepFinish(this, null);
                    nextProcess();


                }} else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(RFWriterActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }else {
                    U.beepError(this, msg);
            }

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }
}
