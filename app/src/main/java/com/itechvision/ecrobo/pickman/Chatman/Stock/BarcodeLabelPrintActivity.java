package com.itechvision.ecrobo.pickman.Chatman.Stock;

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
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.AddArrival;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetArrival;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetPrinter;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
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

public class BarcodeLabelPrintActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.radiogroup) RadioGroup radiogroup;
    @BindView(R.id.qtyLayout) LinearLayout qtyLayout;

    String TAG = BarcodeLabelPrintActivity.class.getSimpleName();

    String adminID = "";

    private boolean showKeyboard;
    private TextToSpeak mTextToSpeak;
    private boolean visible = false;
    public Context mcontext = this;

    protected int mProcNo = 0;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_QTY = 2;


    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    public static boolean mNextBarcode = false;
    public String isNextBarcode = "";
    public int showoption =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_label_print);

        ButterKnife.bind(BarcodeLabelPrintActivity.this);

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
        Log.e(TAG, "OnCreateeeeee");

        if (mProcNo == 0) nextProcess();

        showoption = 1;
        qtyLayout.setVisibility(View.GONE);
    }

    //set title and icons on actionbar
    private void getIDs() {
        actionbarImplement(this, "ラベル印刷", " ",
                0, true, true, false);

        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        btnRed.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
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


    public void SelectionType(View view){

        switch (radiogroup.getCheckedRadioButtonId())
        {
            case R.id.rb1:
                showoption = 1;
                qtyLayout.setVisibility(View.GONE);
                break;

            case R.id.rb2:
                showoption =2;
                qtyLayout.setVisibility(View.VISIBLE);
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
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        else {
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
                x, r.getDisplayMetrics()
        );
        return px;
    }

    @OnClick(R.id.enter) void enter() {
        String s = mBuff.toString();
        Log.e("ScannerbindActivity", "Keyput11222 " + mBuff);
        mBuff.delete(0, mBuff.length());
        Log.e("ScannerbindActivity", "Keyput11222 buff " + mBuff);
        inputedEvent(s);
    }

    public void setProc(int procNo) {
        Log.e(TAG,"setProcccccc");
        mProcNo = procNo;
        switch (procNo)
        {

            case PROC_BARCODE:
                Log.e(TAG,"setProc_PROC_BARCODEEEEEEEEEE");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                break;
            case PROC_QTY:
                Log.e(TAG,"setProc_PROC_QTYYYYYYYY");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                break;

        }
    }


    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                String barcode = _gts(R.id.barcode);

                if (barcode.equals("") || barcode.equals("0")) {
                    U.beepError(this, "バーコードがスキャンされていません");
                    break;
                }

                if(showoption == 1)
                    sendRequest(barcode,"1");
                else
                    setProc(PROC_QTY);

                break;
            case PROC_QTY: // 数量

                String qty = _gts(R.id.quantity);
                if (qty.equals(""))
                    qty = "1";
                String barcode1 = _gts(R.id.barcode);

                if (isScaned) {

                    Log.e(TAG, "Barcode at present is   " + barcode1);
                    if (buff.equals(barcode1)) {
                        U.beepSuccess();
                        qty = U.plusTo(qty, "1");
                        _sts(R.id.quantity, qty);
                        mTextToSpeak.startSpeaking(_gts(R.id.quantity));

                        break;
                    } else {
                        stopTimer();
                        mNextBarcode = true;
                        isNextBarcode = buff;
                        Log.e(TAG, "NextBarcodeeeee 11111  " + mNextBarcode + " isNextBarcode  " + isNextBarcode);

                        sendRequest(barcode1, qty);

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

                stopTimer();
                sendRequest(barcode1, qty);

                break;

        }
    }

    void sendRequest(String barcode, String qty) {

        Globals.getterList = new ArrayList<>();

        Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
        Globals.getterList.add(new ParamsGetter("quantity", qty));

        if(BaseActivity.getPrinterSelected() && !checkPrinterSelect())
            Globals.getterList.add(new ParamsGetter("barcode_printer_id", BaseActivity.getBarcodeSlipselectedPrinterID()));
        else {
            Globals.getterList.add(new ParamsGetter("ap_printer_db","1"));

        }

        new MainAsyncTask(this, Globals.Webservice.barcode_print, 1, BarcodeLabelPrintActivity.this, "Form", Globals.getterList, true).execute();

    }
    @Override
    public void nextProcess()
    {
        _sts(R.id.barcode, "");
        _sts(R.id.quantity, "");

        setBadge2(0);

        setProc(PROC_BARCODE);
        _gt(R.id.barcode).requestFocus();
    }

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
                break;}
    }

    @Override
    public void scanedEvent(String barcode) {

        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEventttttt");

            if (mProcNo == PROC_BARCODE) {

                String finalbarcode1 = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode1;

                _sts(R.id.barcode, barcode);
            }

        }


        if (mProcNo == PROC_QTY) {
            Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
            String finalbarcode1 = CommonFunctions.getBracode(barcode);
            barcode =finalbarcode1;

            Log.e(TAG, "barcode111   " + barcode);
        }


        this.inputedEvent(barcode, true);

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
            case PROC_QTY: // 数量
                _sts(R.id.quantity, barcode);
                break;
        }
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
                JsonHash row = (JsonHash) result1.get(0);

                if (row.containsKey("data")) {
                    nextProcess();
                }

            } else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(BarcodeLabelPrintActivity.this,LoginActivity.class);
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
        U.beepError(this,null);
        CommonDialogs.customToast(getApplicationContext(), "Network error occured");
    }
}
