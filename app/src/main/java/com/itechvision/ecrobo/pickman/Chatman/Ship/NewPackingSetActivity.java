package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.FixedNewPickingSet;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetNewPickingSet;
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
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewPackingSetActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.productQuantity) EditText result_qty;
    @BindView(R.id.parentCode) EditText result_parent;
    @BindView(R.id.location) EditText result_loc;
    @BindView(R.id.productCode) EditText result_pdt;
    @BindView(R.id.scrollMain) ScrollView svMain;

    public final android.os.Handler handlerLoc = new android.os.Handler();
    public Runnable runnableCode = null;

    protected Map<String, String> mPackItem = new HashMap<String, String>();
    public static List<Map<String, String>> packData = new ArrayList<Map<String, String>>();

    protected int mProcNo = 0;
    public static final int PROC_PACKID = 1;
    public static final int PROC_BARCODE = 2;
    public static final int PROC_QTY = 3;

    public static final int COMPLETE_INSPECT = 1;
    public static final int INCOMPLETE_INSPECT = 2;
    public static final int SKIP_INSPECT = 3;

    public static int mRequestStatus =0;

    public static final int REQ_INITIAL = 1;
    public static final int REQ_PACKID = 2;
    public static final int REQ_BARCODE = 3;
    public static final int REQ_QTY = 4;
    public static final int REQ_QTY2 = 5;

    private boolean visible = false;
    public Context mcontext=this;

    public static boolean isBarcodeChange = false;
    public String mBarcode = "";
    public boolean isNextbarcode = false;
    public static boolean skipvalue=false;

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPrefs";

    protected Map<String, String> cProductList = null;

    ECRApplication app=new ECRApplication();
    String adminID="";

    private TextToSpeak mTextToSpeak;

    String TAG= NewPackingSetActivity.class.getSimpleName();
    private boolean showKeyboard;

    public TextView productName;

    public String _lastUpdateQty = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_packing_set);

        ButterKnife.bind(NewPackingSetActivity.this);

        getIDs();

        Log.d(TAG,"On Create ");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id",null);

        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        showKeyboard = sharedPreferences.getBoolean("ShowKeyboard",false);

        productName = (TextView)findViewById(R.id.productname);

        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
            visible = true;

			numbrbtn.setText(R.string.hideKeyboard);
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e("NewPicking","SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);

        result_qty.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        result_pdt.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        result_parent.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        result_loc.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        productName.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

        if (mProcNo == 0) nextProcess();
    }

    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "セット制作指示", " ",
                0, true,true,false);
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

    public void AddLayout(View view)
    {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
        if(visible==false)
        {
            visible = true;
            numbrbtn.setText(R.string.hideKeyboard);
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e("MoveActivity","SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
        }
        else
        {
            visible = false;
            numbrbtn.setText(R.string.showkeyboard);
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG,"SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }

    }
    public int convert(int x) {
        Resources r =mcontext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                x, r.getDisplayMetrics()
        );
        return px;
    }
    @Override
    protected void onStop() {
        Log.e(TAG,"onStop");
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
        Log.e(TAG,"setProcccccc");
        mProcNo = procNo;
        switch (procNo) {
            case PROC_PACKID:
                Log.e(TAG,"setProc_PROC_ORDERIDDDDDD");
                _gt(R.id.packId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.packId).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.packId));
                break;

            case PROC_BARCODE:
                Log.e(TAG,"setProc_PROC_BARCODEEEEEEEEEE");
                _gt(R.id.packId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                _gt(R.id.barcode).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.packId));
                break;

            case PROC_QTY:
                Log.e(TAG,"setProc_PROC_QTYYYYYYYYYYYYY");
                _gt(R.id.packId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));

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

                Log.e(TAG, "inputedEvent_PROC_ORDER_NOOOOOOOOO");
                String packID = _gts(R.id.packId);
                if ("".equals(packID)) {
                    U.beepError(this, "セットIDは必須です");
                    _gt(R.id.packId).setFocusableInTouchMode(true);

                    break;
                }
                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("pack_id",packID));
                Globals.getterList.add(new ParamsGetter("mode","picking"));

                mRequestStatus = REQ_PACKID;
                new MainAsyncTask(this, Globals.Webservice.getPacking, 1, NewPackingSetActivity.this, "Form", Globals.getterList,true).execute();


                break;
            case PROC_BARCODE:    // バーコード
                Log.e(TAG, "inputedEvent_BARCODEEEEEEEEEE    cProductList  "+cProductList);
                if(cProductList.get("barcode").equals(buff) || cProductList.get("code").equals(buff)) {
                    String barcode = _gts(R.id.barcode);

                    Log.e(TAG, "inputedEvent_BARCODEEEEEEEEEE");

                    if ("".equals(barcode)) {
                        U.beepError(this, "バーコードは必須です");
                        Log.e(TAG, "inputedEvent_BARCODE === null");
                        _gt(R.id.barcode).setFocusableInTouchMode(true);

                        break;
                    }
                    String processedCnt = cProductList.get("processedCnt");
                    cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                    _sts(R.id.quantity, cProductList.get("processedCnt"));
                    Log.e(TAG, "Value of Qunatityyyyy   " + _gts(R.id.quantity));
//                    mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                    setProc(PROC_QTY);
                    _lastUpdateQty = _gts(R.id.quantity);

                    if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                        Log.e(TAG,"inputedEvent_PROC_BARCODE_IF_cProductList_equals_quantity");
                        fixedRequest(COMPLETE_INSPECT);
                    }
                }
                else{
                    handlerLoc.removeCallbacks(runnableCode);
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "BarCode Doesn't Match");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }
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
                        } }else {

                        Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty INcomplete");
                        U.beepError(this,"Barcode dont match");
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

                            fixedRequest(COMPLETE_INSPECT);

                        } else {
                            Log.e(TAG, "inputedEvent_QNTYY INcomplete inspect");
                            //increase qunatity in mpackdata

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                            builder1.setMessage("検品数が足りないですがよろしいですか？");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "はい",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            fixedRequest(INCOMPLETE_INSPECT);
                                            dialog.cancel();
                                        }
                                    });
                            builder1.setNegativeButton(
                                    "いいえ",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    }
                }
                break;
        }}

    public void currLineData(Map<String, String> data) {
        Log.e(TAG, "currLineDataaaaaaaaaaa");
        cProductList = data;
        Log.e(TAG, "currLineDataaaaaaaaaaa  " + cProductList);
    }
    public void updateBadge1(String orderCount) {
        Log.e(TAG,"updateBadge11111111111111"  + orderCount);
        setBadge1(Integer.valueOf(orderCount));
    }

    public void updateBadge2(String qtyCount) {
        Log.e(TAG,"updateBadge222222222222" + qtyCount);
        setBadge2(Integer.valueOf(qtyCount));
    }
    public void nextWork() {
        Log.e(TAG, "nextworkkkkkk");
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
        if (status == SKIP_INSPECT) {
            cProductList.put("processedCnt", "0");
            skipvalue = true;
        }
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();

        createNewData();
        stopTimer();



        Globals.getterList = new ArrayList<>();

        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("pack_id",cProductList.get("pack_id")));
        Globals.getterList.add(new ParamsGetter("barcode",cProductList.get("barcode")));
        Globals.getterList.add(new ParamsGetter("location",cProductList.get("location")));
        Globals.getterList.add(new ParamsGetter("num",cProductList.get("processedCnt")));
        Globals.getterList.add(new ParamsGetter("mode","picking"));
        Globals.getterList.add(new ParamsGetter("inspection_status",status+""));
        Globals.getterList.add(new ParamsGetter("time_taken",timeTaken().toString()));

        mRequestStatus = REQ_QTY;

        new MainAsyncTask(this, Globals.Webservice.fixedPacking, 1, NewPackingSetActivity.this, "Form", Globals.getterList,true).execute();

  }
    private void fixedRequest(int status, String nextBarcode) {
        Log.e(TAG, "FixedRequestttttt222222222");
        mBarcode = nextBarcode;
        isNextbarcode = true;

        stopTimer();

        String barc = "";
        if (cProductList.get("barcode").equals(""))
            barc = cProductList.get("code");
        else
            barc = cProductList.get("barcode");

        Globals.getterList = new ArrayList<>();

        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("pack_id",cProductList.get("pack_id")));
        Globals.getterList.add(new ParamsGetter("barcode",barc));
        Globals.getterList.add(new ParamsGetter("location",cProductList.get("location")));
        Globals.getterList.add(new ParamsGetter("num",cProductList.get("processedCnt")));
        Globals.getterList.add(new ParamsGetter("mode","picking"));
        Globals.getterList.add(new ParamsGetter("inspection_status",status+""));
        Globals.getterList.add(new ParamsGetter("time_taken",timeTaken().toString()));

        mRequestStatus = REQ_QTY;

        new MainAsyncTask(this, Globals.Webservice.fixedPacking, 1, NewPackingSetActivity.this, "Form", Globals.getterList,true).execute();

  }

    public void createNewData() {
        boolean repeat = false;

        Log.e(TAG, " createNewPackItemmmm ");
        Log.e(TAG, " createNewPackItemmmm productList " + cProductList);

        mPackItem = new HashMap<String, String>();
        mPackItem.put("pack_id", cProductList.get("pack_id"));
        mPackItem.put("code", cProductList.get("code"));
        mPackItem.put("barcode",cProductList.get("barcode"));
        mPackItem.put("quantity", _gts(R.id.quantity).toString());

        packData.add(mPackItem);

        Log.e(TAG, " mpackItemm " + packData);

    }

    @Override
    public void nextProcess() {
        Log.e(TAG," nextProcessssssssss");
        this._sts(R.id.barcode, "");
        this._sts(R.id.quantity, "");
        this._sts(R.id.packId, "");
        this._sts(R.id.parentCode, "");
        this._stxtv(R.id.productname, "");
        this._sts(R.id.location, "");
        this._sts(R.id.productCode, "");
        this._sts(R.id.productQuantity, "");
        setProc(PROC_PACKID);


        this.cProductList = null;
        skipvalue = false;
        isBarcodeChange = false;
        /* list order */
        setBadge3(0);
        setBadge2(0);
        setBadge1(0);


        //closes the keyboard
        packData.clear();
        if(showKeyboard==false) {
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
            Log.e("NewPicking", "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
        else
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
            visible = true;

            numbrbtn.setText(R.string.hideKeyboard);
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e("NewPicking","SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        Globals.getterList = new ArrayList<>();

        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("initial_call", "yes"));

        mRequestStatus = REQ_INITIAL;

        new MainAsyncTask(this, Globals.Webservice.getPacking, 1, NewPackingSetActivity.this, "Form", Globals.getterList,true).execute();


    }

    @Override
    public void inputedEvent(String buff) {
        Log.e(TAG," inputedEventttttttttt");
        inputedEvent(buff, false);
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
        Log.e(TAG,"SkipBtn_OnClick");
        new AlertDialog.Builder(NewPackingSetActivity.this)
                .setTitle("スキップしてよろしいですか？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cProductList != null){
                            Log.e(TAG,"SkipProductttttttt");
                            fixedRequest(SKIP_INSPECT);
                        }
                    }
                })
                .setNegativeButton("いいえ", null)
                .show();
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {

            case PROC_PACKID:    // Tracking number
                Log.e(TAG,"ketpressEvent_PROC_TRACKIDDDDD");
                _sts(R.id.packId, buff);
                break;
            case PROC_BARCODE:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_BARCODEEEEEE");
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                Log.e(TAG,"ketpressEvent_PROC_QTYYYYYYYYY");
                _sts(R.id.quantity, buff);
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
        } else if (!barcode.equals("")) {
            if (mProcNo == PROC_BARCODE) {
                Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode;
                _sts(R.id.barcode, barcode);
            }
            else if (mProcNo == PROC_PACKID) _sts(R.id.packId, barcode);

            if (mProcNo == PROC_QTY){
                Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode;
            }
            this.inputedEvent(barcode, true);
        }
    }

    @Override
    public void enterEvent() {
        Log.e(TAG, " enterEventtttttttttt");
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();

        if (mProcNo >= PROC_BARCODE){
            Log.e(TAG," enterEvent_mProcNo >= PROC_BARCODE");
//                speakOrderItems(500);
        }
    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                Log.e(TAG,"deleteEvent_PROC_BARCODE");
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QTY:
                Log.e(TAG,"deleteEvent_PROC_QTY");
                _sts(R.id.quantity, barcode);
                break;
            case PROC_PACKID:
                Log.e(TAG,"deleteEvent_PROC_ORDERID");
                _sts(R.id.packId, barcode);
                break;
        }
    }


    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {
        HashMap<String, String> mHash=new HashMap<>();
        Log.e(TAG,result.toString());

        try {

            String response= result.toString();
            JsonPullParser parser = JsonPullParser.newParser(response);
            JsonHash map1 = JsonHash.fromParser(parser);
            Log.e(TAG," "+map1);

            String msg="";
            JsonArray result1;
            String code = map1.getStringOrNull("code");
            msg=map1.getStringOrNull("message");
            result1= map1.getJsonArrayOrNull("results");
            if (code == null) {
                Log.e(TAG,"CODEeee============Nulllll");
                CommonDialogs.customToast(getApplicationContext(),"Network error occured");

            }
            if ("0".equals(code) == true) {
                Log.e("SendLogs111",code+"  "+msg+"  "+result1);

                if(mRequestStatus == REQ_PACKID)
                {
                    new GetNewPickingSet().post(code,msg,result1, mHash,NewPackingSetActivity.this);
                }
                else if(mRequestStatus == REQ_QTY)
                {
                    new FixedNewPickingSet().post(code,msg,result1,mHash,NewPackingSetActivity.this);
                }
                else if(mRequestStatus ==REQ_INITIAL)
                {
                    String all_order_count = "0";
                    if (result1.size() > 0) {
                        JsonHash row = (JsonHash) result1.get(0);
                        all_order_count = row.getStringOrNull("total_count");
                    }
                    updateBadge1(all_order_count);
                }

            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent in = new Intent(NewPackingSetActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
            else
            if(mRequestStatus == REQ_PACKID)
            {
                new GetNewPickingSet().valid(code,msg,result1, mHash,NewPackingSetActivity.this);
            }


            else if(mRequestStatus == REQ_QTY){
                new FixedNewPickingSet().valid(code,msg,result1,mHash,NewPackingSetActivity.this);
            }
            else if(mRequestStatus ==REQ_INITIAL)
            {
                U.beepError(this, msg);
            }

        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }
}
