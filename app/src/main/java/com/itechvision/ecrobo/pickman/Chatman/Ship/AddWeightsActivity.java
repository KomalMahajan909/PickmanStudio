package com.itechvision.ecrobo.pickman.Chatman.Ship;

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
import android.widget.RelativeLayout;

import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetRfOrder;
import com.itechvision.ecrobo.pickman.Chatman.Stock.BarcodeLabelPrintActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.SlipPrinterActivity;
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

public class AddWeightsActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;

    String TAG = AddWeightsActivity.class.getSimpleName();

    String adminID = "";

    private boolean showKeyboard;
    private TextToSpeak mTextToSpeak;
    private boolean visible = false;
    public Context mcontext = this;

    protected int mProcNo = 0;
    public static final int PROC_TRACK = 1;
    public static final int PROC_WEIGHT = 2;

    public static int mRequestStatus =0;

    public static final int REQ_TRACK = 1;


    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weights);
        ButterKnife.bind(AddWeightsActivity.this);
        Log.d(TAG,"On Create ");
        getIDs();

        mTextToSpeak = new TextToSpeak(this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);

        if(mProcNo == 0)
            nextProcess();
    }



    //set title and icons on actionbar
    private void getIDs() {
        actionbarImplement(this, getString(R.string.weight), " ",
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
                break;}
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

    public void setProc(int procNo) {
        Log.e(TAG,"setProcccccc");
        mProcNo = procNo;
        switch (procNo)
        {

            case PROC_TRACK:
                Log.e(TAG,"setProc_PROC_TRACK");
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.weight).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.trackingNumber).setFocusableInTouchMode(true);


                break;
            case PROC_WEIGHT:
                Log.e(TAG,"setProc_PROC_WEIGHT");
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.weight).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.weight).setFocusableInTouchMode(true);

                break;
        }
    }

    @Override
    public void inputedEvent(String buff) {
        switch (mProcNo) {
            case PROC_TRACK:    // バーコード
                String track = _gts(R.id.trackingNumber);

                if (track.equals("") || track.equals("0")) {
                    U.beepError(this, "バーコードがスキャンされていません");
                    _gt(R.id.trackingNumber).requestFocus();
                    break;
                }

                setProc(PROC_WEIGHT);

                break;
            case PROC_WEIGHT:
                String weight = _gts(R.id.weight);

                if (weight.equals("") || weight.equals("0")) {
                    U.beepError(this, "バーコードがスキャンされていません");
                    _gt(R.id.weight).requestFocus();
                    break;
                }


                Globals.getterList = new ArrayList<>();
                Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("mediacode", _gts(R.id.trackingNumber)));
                Globals.getterList.add(new ParamsGetter("weight", _gts(R.id.weight)));

                mRequestStatus = REQ_TRACK;

                new MainAsyncTask(this, Globals.Webservice.ecms_weight, 1, AddWeightsActivity.this, "Form", Globals.getterList, true).execute();

        }}

    @Override
    public void nextProcess() {
        this._sts(R.id.trackingNumber, "");
        this._sts(R.id.weight, "");
        setProc(PROC_TRACK);
    }

    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
        U.beepBigsound(this, null);
        nextProcess();
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

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_TRACK:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_BARCODEEEEEE");
                _sts(R.id.trackingNumber, buff);
                break;
            case PROC_WEIGHT:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_BARCODEEEEEE");
                _sts(R.id.weight, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        Log.e(TAG, "barcode1111111  " + barcode);
        if (mProcNo == PROC_TRACK) {
            // check for QR code
            _sts(R.id.trackingNumber, barcode);
        }

        else if (mProcNo == PROC_WEIGHT) {
            _sts(R.id.weight, barcode);
        }
        this.inputedEvent(barcode);

    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_TRACK:
                _sts(R.id.trackingNumber, barcode);
                break;
            case PROC_WEIGHT:
                _sts(R.id.weight, barcode);
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

                if (row.containsKey("results")) {
                    U.beepKakutei(this, "検品データを登録しました。");
                    nextProcess();
                }
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(AddWeightsActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else {
                _gt(R.id.weight).requestFocus();
                U.beepError(this,msg);
            }

        }catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }
}
