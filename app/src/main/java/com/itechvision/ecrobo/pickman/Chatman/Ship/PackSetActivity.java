package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.FixedPacking;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetPacking;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetPackingNS;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetPackingParent;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PackSetActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.layout_main)
    RelativeLayout mainlayout;
    @BindView(R.id.add_layout)
    Button numbrbtn;
    @BindView(R.id.layout_number)
    RelativeLayout layout;
    @BindView(R.id.productQuantity)
    EditText result_qty;
    @BindView(R.id.parentCode)
    EditText result_parent;
    @BindView(R.id.location)
    EditText result_loc;
    @BindView(R.id.productCode)
    EditText result_pdt;
    @BindView(R.id.scrollMain)
    ScrollView svMain;
    @BindView(R.id.gridparBarcode)
    LinearLayout gridparentBarcode;
    @BindView(R.id.parent_scan_rg)
    RadioGroup parent_scan_rg;

    @BindView(R.id.yes_rb)
    RadioButton yes_rb;
    @BindView(R.id.no_rb)
    RadioButton no_rb;


    protected int mProcNo = 0;
    public static final int PROC_PACKID = 1;
    public static final int PROC_BARCODE = 3;
    public static final int PROC_PARENTBARCODE = 2;
    public static final int PROC_QTY = 4;
    public static int mRequestStatus = 0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_PACKID = 2;
    public static final int REQ_BARCODE = 3;
    public static final int REQ_PARENTBARCODE = 4;

    public static final int REQ_QTY1 = 5;
    public static final int REQ_QTY2 = 6;
    public static final int COMPLETE_INSPECT = 1;
    public static final int INCOMPLETE_INSPECT = 2;
    protected Map<String, String> cProductList = null;
    public static boolean isBarcodeChange = false;
    public String mBarcode = "";
    public boolean isNextbarcode = false;
    private boolean visible = false;
    public Context mcontext = this;
    public String _lastUpdateQty = "0";
    public TextView prod_name;

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPrefs";

    ECRApplication app = new ECRApplication();
    String adminID = "";

    private TextToSpeak mTextToSpeak;
    String TAG = PackSetActivity.class.getSimpleName();
    private boolean showKeyboard;

    public final android.os.Handler handlerLoc = new android.os.Handler();
    public Runnable runnableCode = null;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_set);
        ButterKnife.bind(PackSetActivity.this);

        getIDs();

        Log.d(TAG, "On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);

        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);

        showKeyboard = sharedPreferences.getBoolean("ShowKeyboard", false);
        Log.e("TAGyyyy", "chaeckValue  " + showKeyboard);
        numbrbtn = (Button) _g(R.id.add_layout);

        prod_name = (TextView) findViewById(R.id.productname);
        dialog = new Dialog(this);

        if (showKeyboard == true) {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
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
            Log.e("NewPicking", "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);

        result_qty.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        result_pdt.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        result_parent.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        result_loc.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        prod_name.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        if (mProcNo == 0) nextProcess();

        if (BaseActivity.getParentScanSelected()) {
            yes_rb.setChecked(true);
            gridparentBarcode.setVisibility(View.VISIBLE);
        } else {
            no_rb.setChecked(true);
            gridparentBarcode.setVisibility(View.GONE);
        }
    }

    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "セット制作指示", " ", 0, true, true, false);
        //menubarr
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
            default:
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
            Log.e("MoveActivity", "SetlayoutMarginnnnn");
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
            Log.e("MoveActivity", "SetlayoutMarginnnnn");
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

    public void ScanParent(View view) {
        if (mProcNo <= PROC_BARCODE) {
            switch (parent_scan_rg.getCheckedRadioButtonId()) {
                case R.id.yes_rb:
                    BaseActivity.setParentScanSelected(true);
                    gridparentBarcode.setVisibility(View.VISIBLE);
                    break;
                case R.id.no_rb:
                    BaseActivity.setParentScanSelected(false);
                    gridparentBarcode.setVisibility(View.GONE);
            }

        } else {
            U.beepError(this, null);
            if (BaseActivity.getParentScanSelected())
                yes_rb.setChecked(true);
            else
                no_rb.setChecked(true
                );
        }

    }


    @Override
    protected void onStop() {
        Log.e(TAG, "onStop");
        mTextToSpeak.resetQueue();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mTextToSpeak.resetQueue();
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }

    public void setProc(int procNo) {
        Log.e(TAG, "setProcc");
        mProcNo = procNo;
        switch (procNo) {
            case PROC_PACKID:
                Log.e(TAG, "setProc_PROC_ORDERIDDD");
                _gt(R.id.packId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.parbarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.packId).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(R.id.packId));
                break;

            case PROC_BARCODE:
                Log.e(TAG, "setProc_PROC_BARCODEEEE");
                _gt(R.id.packId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.parbarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.packId));
                break;

            case PROC_PARENTBARCODE:
                Log.e(TAG, "setProc_PROC_BARCODEEEE");
                _gt(R.id.packId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.parbarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.parbarcode).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.packId));
                break;

            case PROC_QTY:
                Log.e(TAG, "setProc_PROC_QYYYYYYY");
                _gt(R.id.packId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.parbarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                scrollToView(svMain, _g(R.id.quantity));

                if (_gts(R.id.quantity).equals("")) _sts(R.id.quantity, "1");
                mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {
            case PROC_PACKID:    // 注文No
                /* Conditional set next process whether orderId or trackingNo or orderNo. */

                Log.e(TAG, "inputedEvent_PROC_ORDER_NOOO");
                String packID = _gts(R.id.packId);
                if ("".equals(packID)) {
                    U.beepError(this, "セットIDは必須です");
                    _gt(R.id.packId).setFocusableInTouchMode(true);

                    break;
                }

                Globals.getterList = new ArrayList<>();

                Log.e(TAG, "shopidd  " + BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("pack_id", packID));

                mRequestStatus = REQ_PACKID;

                new MainAsyncTask(this, Globals.Webservice.getPacking, 1, PackSetActivity.this, "Form", Globals.getterList, true).execute();

                break;
            case PROC_BARCODE:    // バーコード

                String barcode = _gts(R.id.barcode);

                Log.e(TAG, "inputedEvent_BARCODEEEE");
//                setKey(PROC_BARCODE);
                if ("".equals(barcode)) {
                    U.beepError(this, "バーコードは必須です");
                    Log.e(TAG, "inputedEvent_BARCODE === null");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);

                    break;
                }


                    Globals.getterList = new ArrayList<>();

                    Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    Globals.getterList.add(new ParamsGetter("pack_id", _gts(R.id.packId)));
                    Globals.getterList.add(new ParamsGetter("barcode", barcode));

                    mRequestStatus = REQ_BARCODE;

                    new MainAsyncTask(this, Globals.Webservice.getPacking, 1, PackSetActivity.this, "Form", Globals.getterList, true).execute();

              /*  else{
                    if(cProductList.get("barcode").equalsIgnoreCase(barcode)|| cProductList.get("code").equalsIgnoreCase(barcode) ) {
                        nextWork();
                        break;
                    }
                    else {
                        U.beepError(this, "バーコードが子バーコードと一致しません。");
                    }
                    }
*/
                break;

            case PROC_PARENTBARCODE:    // バーコード

                String parbarcode = _gts(R.id.parbarcode);

                Log.e(TAG, "inputedEvent_BARCODEEEE");
//                setKey(PROC_BARCODE);
                if ("".equals(parbarcode)) {
                    U.beepError(this, "バーコードは必須です");
                    Log.e(TAG, "inputedEvent_BARCODE === null");
                    _gt(R.id.parbarcode).setFocusableInTouchMode(true);
                    break;
                }

                Globals.getterList = new ArrayList<>();

                Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("pack_id", _gts(R.id.packId)));
                Globals.getterList.add(new ParamsGetter("barcode", parbarcode));

                mRequestStatus = REQ_PARENTBARCODE;

                new MainAsyncTask(this, Globals.Webservice.getRegiPacking, 1, PackSetActivity.this, "Form", Globals.getterList, true).execute();

                break;

            case PROC_QTY:
                String qty = _gts(R.id.quantity);


                if (isScaned) {
                    Log.e(TAG, "inputedEvent_QNTYY Barcode Scanned");
                    if (buff.equals(_gts(R.id.barcode))) {
                        Log.e(TAG, "inputedEvent_QNTYY Barcode match");
                        qty = U.plusTo(qty, "1");
                        String processedCnt = cProductList.get("processedCnt");
                        if (!processedCnt.equals(_gts(R.id.quantity)))
                            processedCnt = _gts(R.id.quantity);
                        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                        //increase qunatity in mpackdata
                        if (Integer.parseInt(cProductList.get("processedCnt")) <= Integer.parseInt(cProductList.get("quantity"))) {


                            _sts(R.id.quantity, qty);

                            if (Integer.parseInt(qty) > 1)
                                mTextToSpeak.startSpeaking(qty);

                            _lastUpdateQty = _gts(R.id.quantity);

                            /* check if update in quantity need next action */
                            if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                                Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty complete");
                                fixedRequest(COMPLETE_INSPECT);
                                break;

                            }
                        }
                    } else {
                        Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty INcomplete");
                        fixedRequest(INCOMPLETE_INSPECT, buff);
                        break;
                    }

                } else {
                    if ("".equals(qty)) {
                        Log.e(TAG, "inputedEvent_QNTYY empty ");
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
                    String maxQty_ = U.minusTo(cProductList.get("quantity"), processedCnt);
                    Log.e(TAG, "inputedEvent_QNTYY maxqnty " + maxQty_);
                    Log.e(TAG, "inputedEvent_QNTYY qtyUpdate " + qtyUpdate);
                    Log.e(TAG, "inputedEvent_QNTYY processedCnt " + processedCnt);
                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());
                        Log.e(TAG, "inputedEvent_QNTYY is less");
                        _sts(R.id.quantity, "1");


                        cProductList.put("processedCnt", "1");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        Log.e(TAG, "inputedEvent_QNTYY is exceeds");
                        U.beepError(this, "数量が多すぎます");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        _sts(R.id.quantity, "1");

                        cProductList.put("processedCnt", "1");
                        break;
                    } else {
                        if (Integer.parseInt(qty) > 1)
                            mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                        cProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            Log.e(TAG, "inputedEvent_QNTYY complete inspect");
                            //increase qunatity in mpackdata
                            fixedRequest(COMPLETE_INSPECT);

                        } else {
                            Log.e(TAG, "inputedEvent_QNTYY INcomplete inspect");
                            //increase qunatity in mpackdata
                            fixedRequest(INCOMPLETE_INSPECT);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void inputedEvent(String buff) {
        Log.e(TAG, " inputedEventtt");
        inputedEvent(buff, false);
    }

    public void currLineData(Map<String, String> data) {
        Log.e(TAG, "currLineDataaaaaa");
        cProductList = data;
        Log.e(TAG, "currLineDataaaaa  " + cProductList);
    }

    public void updateBadge1(String orderCount) {
        Log.e(TAG, "updateBadge1111111" + orderCount);
        setBadge1(Integer.valueOf(orderCount));
    }

    public void updateBadge2(String qtyCount) {
        Log.e(TAG, "updateBadge222222" + qtyCount);
        setBadge2(Integer.valueOf(qtyCount));
    }

    public void nextWork() {
        Log.e(TAG, "nextworkkk");
        String processedCnt = cProductList.get("processedCnt");
        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
        setProc(PROC_QTY);
        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
        _lastUpdateQty = _gts(R.id.quantity);

        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
            fixedRequest(COMPLETE_INSPECT);

        }

    }

    private void fixedRequest(Integer status) {
        /* Stop location repetition */
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();
        stopTimer();


        Globals.getterList = new ArrayList<>();

        Log.e(TAG, "shopid  " + BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("pack_id", cProductList.get("pack_id")));
        Globals.getterList.add(new ParamsGetter("barcode", cProductList.get("barcode")));
        Globals.getterList.add(new ParamsGetter("location", cProductList.get("location")));
        Globals.getterList.add(new ParamsGetter("num", cProductList.get("processedCnt")));
        Globals.getterList.add(new ParamsGetter("inspection_status", status + ""));
        Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));


        mRequestStatus = REQ_QTY1;

        new MainAsyncTask(this, Globals.Webservice.fixedPacking, 1, PackSetActivity.this, "Form", Globals.getterList, true).execute();


    }

    private void fixedRequest(int status, String nextBarcode) {
        Log.e(TAG, "FixedRequesttt22");
        mBarcode = nextBarcode;
        isNextbarcode = true;
        stopTimer();

        String barc = "";
        if (cProductList.get("barcode").equals(""))
            barc = cProductList.get("code");
        else
            barc = cProductList.get("barcode");

        Globals.getterList = new ArrayList<>();

        Log.e(TAG, "shopid  " + BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("pack_id", cProductList.get("pack_id")));
        Globals.getterList.add(new ParamsGetter("barcode", barc));
        Globals.getterList.add(new ParamsGetter("location", cProductList.get("location")));
        Globals.getterList.add(new ParamsGetter("num", cProductList.get("processedCnt")));
        Globals.getterList.add(new ParamsGetter("inspection_status", status + ""));
        Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));

        mRequestStatus = REQ_QTY1;
        new MainAsyncTask(this, Globals.Webservice.fixedPacking, 1, PackSetActivity.this, "Form", Globals.getterList, true).execute();

    }

    @Override
    public void clearEvent() {
        new AlertDialog.Builder(this)
                .setTitle("Clear？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mTextToSpeak.startSpeaking("clear");
                        nextProcess();
                    }
                })
                .setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO cancel clear
                    }
                })
                .show();
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

            case PROC_PACKID:    // Tracking number
                Log.e(TAG, "ketpressEvent_PROC_TRACKIDD");
                _sts(R.id.packId, buff);
                break;
            case PROC_BARCODE:    // バーコード
                Log.e(TAG, "ketpressEvent_PROC_BARCODEE");
                _sts(R.id.barcode, buff);
                break;
            case PROC_PARENTBARCODE:    // バーコード
                Log.e(TAG, "ketpressEvent_PROC_BARCODEE");
                _sts(R.id.parbarcode, buff);
                break;
            case PROC_QTY: // 数量
                Log.e(TAG, "ketpressEvent_PROC_QTYY");
                _sts(R.id.quantity, buff);
                break;

        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (CLEAR_BARCODE.equals(barcode)) {
            Log.e(TAG, "scanedEvent_CLEAR_BARCODE");
            mBuff.delete(0, mBuff.length());
            clearEvent();
        } else if (ENTER_BARCODE.equals(barcode)) {
            Log.e(TAG, "scanedEvent_ENTER_BARCODE");
            enterEvent();
        } else if (!barcode.equals("")) {
            if (mProcNo == PROC_BARCODE) {
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                String finalbarcode1 = CommonFunctions.getBracode(barcode);
                barcode = finalbarcode1;

                _sts(R.id.barcode, barcode);
            } else if (mProcNo == PROC_PARENTBARCODE) _sts(R.id.parbarcode, barcode);
            else if (mProcNo == PROC_PACKID) _sts(R.id.packId, barcode);


            if (mProcNo == PROC_QTY) {
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                String finalbarcode1 = CommonFunctions.getBracode(barcode);
                barcode = finalbarcode1;
            }

            this.inputedEvent(barcode, true);
        }
    }

    @Override
    public void enterEvent() {
        Log.e(TAG, " enterEventt");
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();

        if (mProcNo >= PROC_BARCODE) {
            Log.e(TAG, " enterEvent_mProcNo >= PROC_BARCODE");
//                speakOrderItems(500);
        }
    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                Log.e(TAG, "deleteEvent_PROC_BARCODE");
                _sts(R.id.barcode, barcode);
                break;
            case PROC_PARENTBARCODE:
                Log.e(TAG, "deleteEvent_PROC_PARENT");
                _sts(R.id.parbarcode, barcode);
                break;
            case PROC_QTY:
                Log.e(TAG, "deleteEvent_PROC_QTY");
                _sts(R.id.quantity, barcode);
                break;
            case PROC_PACKID:
                Log.e(TAG, "deleteEvent_PROC_ORDERID");
                _sts(R.id.packId, barcode);
                break;
        }
    }

    @Override
    public void nextProcess() {
        Log.e("NewPickingActivity", " nextProcess");
        this._sts(R.id.barcode, "");
        this._sts(R.id.quantity, "");
        this._sts(R.id.packId, "");
        this._sts(R.id.parentCode, "");
        this._stxtv(R.id.productname, "");
        this._sts(R.id.location, "");
        this._sts(R.id.productCode, "");
        this._sts(R.id.productQuantity, "");
        this._sts(R.id.parbarcode, "");
        setProc(PROC_PACKID);


        this.cProductList = null;

        isBarcodeChange = false;
        /* list order */
        setBadge3(0);
        setBadge2(0);
        setBadge1(0);


        if (no_rb.isChecked())
            gridparentBarcode.setVisibility(View.GONE);
        else
            gridparentBarcode.setVisibility(View.VISIBLE);
        //closes the keyboard

        if (showKeyboard == false) {
            visible = false;
            numbrbtn.setVisibility(View.VISIBLE);

            numbrbtn.setText(R.string.showkeyboard);
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
//            Animation bottomDown = AnimationUtils.loadAnimation(this,
//                    R.anim.bottom_down);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
//            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
            Log.e(TAG, "SetlayoutMarginn");
            mainlayout.setLayoutParams(params);
        } else {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
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
            Log.e(TAG, "SetlayoutMarginn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }

        Globals.getterList = new ArrayList<>();

        Log.e(TAG, "shopid  " + BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("initial_call", "yes"));

        mRequestStatus = REQ_INITIAL;

        new MainAsyncTask(this, Globals.Webservice.getPacking, 1, PackSetActivity.this, "Form", Globals.getterList, true).execute();


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
                Log.e(TAG, "CODEe=======Null");
                CommonDialogs.customToast(getApplicationContext(), "Network error occured");

            }
            if ("0".equals(code) == true) {


                Log.e("SendLogs111", code + "  " + msg + "  " + result1);

                if (mRequestStatus == REQ_PACKID) {
                    new GetPacking().post(code, msg, result1, mHash, PackSetActivity.this);
                } else if (mRequestStatus == REQ_BARCODE) {
                    new GetPackingNS().post(code, msg, result1, mHash, PackSetActivity.this);
                } else if (mRequestStatus == REQ_PARENTBARCODE) {
                    new GetPackingParent().post(code, msg, result1, mHash, PackSetActivity.this);
                } else if (mRequestStatus == REQ_QTY1) {
                    new FixedPacking().post(code, msg, result1, mHash, PackSetActivity.this);
                } else if (mRequestStatus == REQ_INITIAL) {
                    String all_order_count = "0";
                    if (result1.size() > 0) {
                        JsonHash row = (JsonHash) result1.get(0);
                        all_order_count = row.getStringOrNull("total_count");
                    }
                    updateBadge1(all_order_count);
                }
            } else if (code.equalsIgnoreCase("1020")) {
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(PackSetActivity.this, LoginActivity.class);
                                in.putExtra("ACTION", "logout");
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            } else if (code.equalsIgnoreCase("10202") && mRequestStatus == REQ_PARENTBARCODE) {
                U.beepError(this, null);
                if (!dialog.isShowing()) {
                    dialog = new Dialog(this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    // Set GUI of login screen
                    dialog.setContentView(R.layout.dialog_popup);
                    dialog.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    Window window = dialog.getWindow();
                    lp.copyFrom(window.getAttributes());


                    dialog.setCanceledOnTouchOutside(false);

                    // Init button of login GUI
                    TextView txt = (TextView) dialog.findViewById(R.id.txt);
                    txt.setText("作成予定のセットとバーコード" +
                            "\nが一致しません。");
                    ImageView close = (ImageView) dialog.findViewById(R.id.icon_close);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            _sts(R.id.parbarcode, "");
                            _gt(R.id.parbarcode).setFocusableInTouchMode(true);
                            dialog.dismiss();
                        }
                    });
                    // Make dialog box visible.
                    dialog.show();

                }
            } else if (mRequestStatus == REQ_PACKID) {
                setProc(PROC_PACKID);
                new GetPacking().valid(code, msg, result1, mHash, PackSetActivity.this);
            } else if (mRequestStatus == REQ_BARCODE) {
                new GetPackingNS().valid(code, msg, result1, mHash, PackSetActivity.this);
            } else if (mRequestStatus == REQ_PARENTBARCODE) {
                U.beepError(this, msg);
                setProc(PROC_PARENTBARCODE);
            } else if (mRequestStatus == REQ_QTY1) {
                new FixedPacking().valid(code, msg, result1, mHash, PackSetActivity.this);
            } else if (mRequestStatus == REQ_INITIAL) {
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
