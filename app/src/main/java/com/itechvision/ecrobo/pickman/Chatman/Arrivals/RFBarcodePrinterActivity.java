package com.itechvision.ecrobo.pickman.Chatman.Arrivals;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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

public class RFBarcodePrinterActivity extends BaseActivity implements View.OnClickListener, MainAsynListener, UHFCallback {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.btn_connect) Button connectionbtn;
    @BindView(R.id.barcodelayout) LinearLayout barcodelayout;
    @BindView(R.id.rfidLayout) LinearLayout rfIDlayout;
    @BindView(R.id.rfid) EditText edtEpc;
    @BindView(R.id.rfidRg)RadioGroup radio;
    @BindView(R.id.txt1) TextView text1;
    @BindView(R.id.txt2) TextView text2;

    String TAG = RFBarcodePrinterActivity.class.getSimpleName();

    protected int mProcNo = 0;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_RFID = 2;

    private TextToSpeak mTextToSpeak;

    private boolean showKeyboard;
    private boolean visible = false;
    public Context mcontext = this;

    String adminID = "";

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    public boolean orderRequestSettings = false;

    String txt1 = "";
    //ReadWrite process starts
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    String devMacAddress ="";

    private UHFDevice uhf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfbarcode_printer);
        ButterKnife.bind(RFBarcodePrinterActivity.this);

        getIDs();

        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

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
        actionbarImplement(this, "RFIDラベル", " ",
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

    @OnClick(R.id.btnsend)
    void enter() {
        String s = mBuff.toString();
        Log.e("ScannerbindActivity", "Keyput11222 " + mBuff);
        mBuff.delete(0, mBuff.length());
        Log.e("ScannerbindActivity", "Keyput11222 buff " + mBuff);
        inputedEvent(s);
    }
    @OnClick(R.id.btnread) void Read(){
        Log.e(TAG,"READING >>   "+uhf);
        String psw = "00000000";
        uhf.readEpc(psw);
    }

    @OnClick(R.id.btn_connect) void Connect(){
        if(connectionbtn.getText().equals("Connect")){
            uhf.connect();
            connectionbtn.setText("Disconnect");
        }else {
            uhf.disconnect();
            SharedPreferences.Editor editor = spDomain.edit();
            editor.putString("MacAddress","");
            editor.commit();
            Intent i =  new Intent(this, RFDeviceBaseScan.class);
            i.putExtra("rf_use","printer");
            this.startActivity(i);
            connectionbtn.setText("Connect");
        }
    }

    public void RFIDUse (View view){
        switch (radio.getCheckedRadioButtonId()) {
            case R.id.userf:
                orderRequestSettings = false;
                rfIDlayout.setVisibility(View.VISIBLE);
                barcodelayout.setVisibility(View.GONE);
                setProc(PROC_RFID);
                break;
            case R.id.usebarcode:
                orderRequestSettings = true;
                rfIDlayout.setVisibility(View.GONE);
                barcodelayout.setVisibility(View.VISIBLE);
                setProc(PROC_BARCODE);
                break;
        }
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {

            case PROC_RFID:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.rfid).setFocusableInTouchMode(true);
                _gt(R.id.rfid).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                break;

            case PROC_BARCODE:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.rfid).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
        }
    }

    @Override
    public void inputedEvent(String buff) {
        switch (mProcNo) {
            case PROC_RFID:
                if (_gts(R.id.rfid).equals("")) {
                    Read();
                }

                String rfid = _gts(R.id.rfid);
                Log.e(TAG, "input event   PROC_RFID    "+ rfid);
                if ("".equals(rfid)) {
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "RFIDは必須です");
                    _gt(R.id.rfid).setFocusableInTouchMode(true);
                    break;
                }

                SubmitRequest();
                break;

            case PROC_BARCODE:
                String barcode1 = _gts(R.id.barcode);
                if ("".equals(barcode1)) {
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }
                SubmitRequest();

                break;
        }
    }

    void SubmitRequest(){

        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        if(orderRequestSettings) {
            Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
            txt1 = _gts(R.id.barcode);
            Log.e(TAG,">>>>>>>>>>>>>>>>>>    "+txt1 +"   "+ _gts(R.id.barcode) );
        }else{
        Globals.getterList.add(new ParamsGetter("rf_id", _gts(R.id.rfid)));
        txt1 = _gts(R.id.rfid);
            Log.e(TAG,">>>>>>>>>>>>>>>>>>    "+txt1 +"   "+ _gts(R.id.rfid) );
        }

        Globals.getterList.add(new ParamsGetter("mode", "print"));
        if(BaseActivity.getPrinterSelected())
        {
            Globals.getterList.add(new ParamsGetter("printer_db", "yes"));
        }
        //

        new MainAsyncTask(this, Globals.Webservice.rfid_return, 1, RFBarcodePrinterActivity.this, "Form", Globals.getterList, true).execute();
    }

    @Override
    public void nextProcess() {
        Log.e(TAG, "nexttttPRocessssssssssssss");
        _sts(R.id.barcode, "");
        _sts(R.id.rfid, "");

        if(!orderRequestSettings){
            rfIDlayout.setVisibility(View.VISIBLE);
            barcodelayout.setVisibility(View.GONE);
            setProc(PROC_RFID);}
        else {
            rfIDlayout.setVisibility(View.GONE);
            barcodelayout.setVisibility(View.VISIBLE);
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
            case PROC_RFID: // 数量
                _sts(R.id.rfid, buff);
                break;
        }
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
        this.inputedEvent(barcode);
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, barcode);
                break;

        }
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
    public void didReadEpc(final byte[] bytes) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                edtEpc.setText(Glog.bytesToHexString(bytes, ""));
                Log.e(TAG, "didReadEpc 1111111111111111   "+ edtEpc.getText());

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
                text1.setText(txt1);
                String txt2 ="";
                if(orderRequestSettings)
                    txt2 = txt1+ " Barcode has been printed";
                else
                    txt2 = txt1 + " RFID has been printed";

                text1.setText(txt2);
                nextProcess();

            } else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(RFBarcodePrinterActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }else{
                if(orderRequestSettings)
                    setProc(PROC_BARCODE);
                else
                    setProc(PROC_RFID);

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
