package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.FixTruckPicking;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetLocationList;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetTruckPicking;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.TruckBatchOrderDetail;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.TruckBatchSkipDetails;
import com.itechvision.ecrobo.pickman.Chatman.Stock.TruckSearchActivity;
import com.itechvision.ecrobo.pickman.R;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.itechvision.ecrobo.pickman.Util.CommonDialogs.customToast;

public class TruckBatchPickingActivity extends BaseActivity implements MainAsynListener, View.OnClickListener{

    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout) Button keyboard;
//    @BindView(R.id.enter) Button enter;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.scrollMain)ScrollView svMain;
    @BindView(R.id.skipList) Button skippedOnes;
    @BindView(R.id.barcode)EditText barcodeedt;
    @BindView(R.id.Spinnerlayout)RelativeLayout Spinnerlayout;
    @BindView(R.id.LocationSpinner)Spinner locationSpinner;

    public static final int SEARCH_ACTIVITY = 111;

    public Button cancelOrder;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    ECRApplication app=new ECRApplication();
    public String adminID="";
    private Button numbrbtn;
    private TextToSpeak mTextToSpeak;

    public final Handler handlerLoc = new Handler();
    public Runnable runnableCode = null;

    public String _lastUpdateQty = "0";

    protected Map<String, String> cProductList = null;
    ArrayList<Map<String, String>> orderList = new ArrayList<>();
    ArrayList<Map<String, String>> cancelList = new ArrayList<>();

    private boolean visible = false;
    public Context mcontext = this;

    protected int mProcNo = 0;

    private String TAG = "TruckBatchPickingActivity";

    public static final int PROC_LOCATION_SPINNER = 4;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_QTY = 2;
    public static final int PROC_FRONTAGE = 3;

    public static int mRequestStatus =0;

    public static final int REQ_INITIAL = 1;
    public static final int REQ_BARCODE = 2;
    public static final int REQ_FRONTAGE = 3;
    public static final int REQ_STOP = 4;
    public static final int REQ_ORDER_DETAIL = 5;
    public static final int REQ_SKIP = 6;
    public static final int REQ_LOC = 7;

    public static final int COMPLETE_INSPECT = 1;
    public static final int INCOMPLETE_INSPECT = 2;
    public static final int SKIP_INSPECT = 3;
    public static final int CLEAR_INSPECT = 4;

    public final String LOCATION = "Roke";
    public final String BARCODE = "code";
    public final String QTY = "Suryo";
    public final String FONTAGE = "Maguchi";

    public final String LOCATIONJAP = "Roke";
    public final String BARCODEJAP = "code";
    public final String QTYJAP = "Suryo";
    public final String FONTAGEJAP = "Maguchi";


    public int loc_reset = 0;
//    public final String LOCATIONJAP = "ロケ";
//    public final String BARCODEJAP = "バーコード";
//    public final String QTYJAP = "数量";
//    public final String FONTAGEJAP = "間口";

    SweetAlertDialog pDialog1;

    int inspection = 0;

    public String action = "", selectedLoc = "";
    String truck= "",date="", batch = "";
    public static String getAction = "";
    boolean isstop = false,callsearch = false , closepage = false;

    List<String> locationList = new ArrayList<>();
    // to keep track that batch is complete or not
    public  boolean complete  = false;
    public int cancelcount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_batch_picking);

        ButterKnife.bind(this);

        getIDs();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        Log.d(TAG,"On Create ");

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);
        numbrbtn = (Button) _g(R.id.add_layout);
        mTextToSpeak = new TextToSpeak(this);

        cancelOrder = (Button)findViewById(R.id.cancel);
        Intent i = getIntent();
        if (i.hasExtra("truck")){
            truck = i.getStringExtra("truck");
            date = i.getStringExtra("shipping_schedule");
            batch = i.getStringExtra("batch_id");
        }

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0 && mProcNo == PROC_LOCATION_SPINNER){

                    selectedLoc = locationList.get(position);

                    Globals.getterList = new ArrayList<>();
                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    Globals.getterList.add(new ParamsGetter("shipping_schedule", date));
                    Globals.getterList.add(new ParamsGetter("truck_barcode", truck));
                    Globals.getterList.add(new ParamsGetter("batch_id",batch));
                    Globals.getterList.add(new ParamsGetter("mode","start_batch"));
                    Globals.getterList.add(new ParamsGetter("location_start",selectedLoc));

                    mRequestStatus = REQ_LOC;

                    new MainAsyncTask(TruckBatchPickingActivity.this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchPickingActivity.this, "Form", Globals.getterList, true).execute();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(mProcNo == 0) nextProcess();

        skippedOnes.setOnClickListener(this);

    }

    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "バッチ商品ピック", " ",
                0, true,true,true,true );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnYellow.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
            case R.id.notif_count_blue:
                if(action.equals("")){
                getAction = "allBatch";
                action = "detail";
                OrderDetail();}
                else CommonDialogs.customToast(this,"Wait");
                break;
            case R.id.notif_count_green:
                if(action.equals("")){
                getAction = "completeOrders";
                action = "detail";
                OrderDetail();
                }
                else CommonDialogs.customToast(this,"Wait");
                 break;
            case R.id.notif_count_red:
                if(action.equals("")){
                getAction = "currentBatch";
                action = "detail";
                OrderDetail();
                }
                else CommonDialogs.customToast(this,"Wait");
                  break;
            case R.id.notif_count_yellow:
                break;
        }
    }

    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
        if (visible == false)
        {
            visible = true;

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
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e("MoveActivity", "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
    }

    public int convert(int x)
    {
        Resources r = mcontext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                x, r.getDisplayMetrics()
        );
        return px;
    }

    @OnClick(R.id.search_screen) void Search()
    {
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();

        callsearch = true;

        Intent i = new Intent(this,TruckSearchActivity.class);
        i.putExtra("barcode",cProductList.get("barcode"));
        i.putExtra("code",cProductList.get("code"));
        i.putExtra("location",cProductList.get("location"));
        i.putExtra("quantity",cProductList.get("quantity"));
        i.putExtra("batch_id",cProductList.get("batch_id"));
        i.putExtra("order_id",cProductList.get("order_id"));
        i.putExtra("psh_id",cProductList.get("psh_id"));
        i.putExtra("truck",truck);
        i.putExtra("shipping_schedule",date);

        startActivityForResult(i, SEARCH_ACTIVITY);

    }
    @OnClick(R.id.cancel) void Cancelorder(){
        pDialog1 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog1.setCancelable(true);
        pDialog1.setContentText("Do you Want to cancel this order ?");
        pDialog1.setConfirmText("Yes");
        pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {


                pDialog1.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(action.equals("")) {
                        handlerLoc.removeCallbacks(runnableCode);
                        mTextToSpeak.resetQueue();
                        action = "cancel";
                        ++cancelcount;
                        cancelOrder.setVisibility(View.GONE);
                        SendRequest("cancel_order");
                        } else CommonDialogs.customToast(TruckBatchPickingActivity.this,"Wait");
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

    @OnClick (R.id.stop) void stop (){
        pDialog1 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog1.setCancelable(true);
        pDialog1.setContentText("Do you want to stop this batch ?");
        pDialog1.setConfirmText("Yes");
        pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                pDialog1.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(action.equals("")) {
                        handlerLoc.removeCallbacks(runnableCode);
                        mTextToSpeak.resetQueue();
                        action = "stop";
                        isstop = true;
                        Globals.getterList = new ArrayList<>();

                        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                        Globals.getterList.add(new ParamsGetter("shipping_schedule", date));
                        Globals.getterList.add(new ParamsGetter("mode", "stop_batch"));
                        Globals.getterList.add(new ParamsGetter("batch_id", cProductList.get("batch_id")));

                        mRequestStatus =REQ_STOP;

                        new MainAsyncTask(TruckBatchPickingActivity.this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchPickingActivity.this, "Form", Globals.getterList, true).execute();
                        } else CommonDialogs.customToast(TruckBatchPickingActivity.this,"Wait");
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

    @OnClick (R.id.skipbtn) void Skip(){

        pDialog1 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog1.setCancelable(true);
        pDialog1.setContentText("Do you want to skip this product?");
        pDialog1.setConfirmText("Yes");
        Log.e(TAG, "actionnnnnnn  "+action);
        pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                pDialog1.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(action.equals("")) {
                        action = "skip";
                        handlerLoc.removeCallbacks(runnableCode);
                        mTextToSpeak.resetQueue();
                        inspection = SKIP_INSPECT;
                        SkipRequest();
                        }
                        else CommonDialogs.customToast(TruckBatchPickingActivity.this,"Wait");

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

    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {
            case PROC_LOCATION_SPINNER:
                Log.e(TAG, "setProc_LOCSPINNERRR");
                Spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.frontage).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(R.id.barcode));
                break;

            case PROC_BARCODE:
                Log.e(TAG, "setProc_BARCODEEEEEEEEE");
                Spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.frontage).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(R.id.barcode));
                break;

            case PROC_QTY:
                Log.e(TAG,"setPROC_QTYYYYYYYYYY");
                Spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.frontage).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.quantity));
                break;

            case PROC_FRONTAGE:
                Log.e(TAG,"setPROC_FRONTAGEEEEEEEEEEEEE");
                Spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.frontage).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.frontage).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(R.id.frontage));
                break;
        }
    }

    public void inputedEvent(String buff,boolean scan){
        switch (mProcNo) {
            case PROC_BARCODE:
                String barcode = _gts(R.id.barcode);
                Log.e(TAG,"inputedEvent_PROC_BARCODEEEEEE");
                if ("".equals(barcode)) {
                    handlerLoc.removeCallbacks(runnableCode);
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "バーコードは必須です");
                    break;
                }

                if((cProductList.get("barcode").trim().equals(barcode)) || (cProductList.get("code").trim().equals(barcode))){
                   _sts(R.id.quantity,"1");


                   mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                   if(cProductList.get("cancel_flag").equals("1")){
                       U.beepError(this,null);
                       showMessage();}
                   else
                   nextWork();

                }

                else
                {
                    handlerLoc.removeCallbacks(runnableCode);
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "BarCode Doesn't Match");
                    break;
            }
           break;

            case PROC_QTY :

                if(cProductList.get("cancel_flag").equals("1")){
                    U.beepError(this,null);
                    _gt(R.id.quantity).setFocusableInTouchMode(true);

                    showMessage();}

                String qty = _gts(R.id.quantity);

                if(scan){
                    if(U.compareNumeric(cProductList.get("processedCnt"),cProductList.get("quantity"))==0)
                    {
                        U.beepError(this,"Qunatity cannot exceed the required quantity");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);

                        break;
                    }

                    if((cProductList.get("barcode").trim().equals(buff)) || (cProductList.get("code").trim().equals(buff))){
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned");
                        qty = U.plusTo(qty, "1");
                        String processedCnt = cProductList.get("processedCnt");
                        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                        _sts(R.id.quantity, qty);


                        if(Integer.parseInt(qty)>1)
                            mTextToSpeak.startSpeaking(qty);
                          _lastUpdateQty = _gts(R.id.quantity);

                        /* check if update in quantity need next action */
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned222222");
                            setProc(PROC_FRONTAGE);
                            repeatFrontage(1000);
                            inspection = COMPLETE_INSPECT;
                        }

                    }
                    else {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned3333");
                        U.beepError(this,"Barcode dont match");
                        Toast.makeText(getApplicationContext(),"BarCode Doesn't Match",Toast.LENGTH_SHORT).show();
                    }

                }
                else {
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
                            setProc(PROC_FRONTAGE);
                            repeatFrontage(1000);
                            inspection = COMPLETE_INSPECT;
                        } else {
                            Log.e(TAG, "inputedEvent_QNTYY INcomplete inspect");
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                            builder1.setMessage("検品数が足りないですがよろしいですか？");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "はい",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            setProc(PROC_FRONTAGE);
                                            repeatFrontage(1000);
                                            inspection = INCOMPLETE_INSPECT;

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
            case PROC_FRONTAGE:
                String frontage = _gts(R.id.frontage);
                String frontagetxt = _gts(R.id.showfrontage);

                Log.e(TAG, "inputedEvent_frontage   "+frontage);
                if(frontage.equals("")) {
                    U.beepError(this, "frontage cannot be empty");
                    _gt(R.id.frontage).setFocusableInTouchMode(true);

                    break;

                }
                if(!frontage.equals(frontagetxt)){
                    U.beepError(this, "値が一致しません");
                    _gt(R.id.frontage).setFocusableInTouchMode(true);

                    break;
                }

                stopTimer();
                handlerLoc.removeCallbacks(runnableCode);
                mTextToSpeak.resetQueue();

                Globals.getterList = new ArrayList<>();
                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("shipping_schedule", date));
                Globals.getterList.add(new ParamsGetter("truck_id", cProductList.get("truck_id")));
                Globals.getterList.add(new ParamsGetter("batch_id", cProductList.get("batch_id")));
                Globals.getterList.add(new ParamsGetter("order_sub_id", cProductList.get("order_sub_id")));
                Globals.getterList.add(new ParamsGetter("order_id", cProductList.get("order_id")));
                Globals.getterList.add(new ParamsGetter("location",_gts(R.id.location)));
                Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
                Globals.getterList.add(new ParamsGetter("quantity", _gts(R.id.quantity)));
                Globals.getterList.add(new ParamsGetter("mode", "picking"));
                if(loc_reset==0)
                Globals.getterList.add(new ParamsGetter("location_start",selectedLoc));

                Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
                Globals.getterList.add(new ParamsGetter("batch_detail_no", _gts(R.id.frontage)));
                Globals.getterList.add(new ParamsGetter("inspection_status",inspection+""));
                Globals.getterList.add(new ParamsGetter("psh_id", cProductList.get("psh_id")));

                mRequestStatus = REQ_FRONTAGE;

                new MainAsyncTask(this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchPickingActivity.this, "Form", Globals.getterList, true).execute();
                break;
        }
    }


    public void OrderDetail ()
    {
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();

        Globals.getterList = new ArrayList<>();
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("shipping_schedule", date));
        Globals.getterList.add(new ParamsGetter("truck_barcode", cProductList.get("truck_id")));
        Globals.getterList.add(new ParamsGetter("mode", "list_orders"));
        Globals.getterList.add(new ParamsGetter("batch_id",  cProductList.get("batch_id")));
        mRequestStatus = REQ_ORDER_DETAIL;

        new MainAsyncTask(this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchPickingActivity.this, "Form", Globals.getterList, true).execute();
    }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff,false);
    }


    @Override
    public void nextProcess() {
        _sts(R.id.barcode,"");
        _sts(R.id.quantity,"");
        _sts(R.id.frontage,"");
        _sts(R.id.location,"");
        _sts(R.id.showbarcode,"");
        _sts(R.id.color,"");
        _sts(R.id.size,"");
        _sts(R.id.shipping_company, "");
        _sts(R.id.showfrontage,"");
        cancelOrder.setVisibility(View.GONE);
        setProc(PROC_BARCODE);

        cancelcount = 0;
        isstop= false;
        Globals.getterList = new ArrayList<>();
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("shipping_schedule", date));
        Globals.getterList.add(new ParamsGetter("truck_barcode", truck));
        Globals.getterList.add(new ParamsGetter("batch_id",batch));
        Globals.getterList.add(new ParamsGetter("mode","list_location"));

        mRequestStatus = REQ_INITIAL;

        new MainAsyncTask(this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchPickingActivity.this, "Form", Globals.getterList, true).execute();
    }


    public void updateBadge1(String batchcount)
    {
        Log.e(TAG, "updateBadge1111111111  " + batchcount);
        setBadge11(batchcount);
    }

    public void updateBadge2(String orderleft)
    {
        Log.e(TAG, "updateBadge1111111111  " + orderleft);
        setBadge12(orderleft);
    }

    public void updateBadge3(String finishedorders)
    {
        Log.e(TAG, "updateBadge22222  " + finishedorders);
        setBadge13(finishedorders);
    }

    public void updateBadge4(String batchno)
    {
        Log.e(TAG, "updateBadge1222222  " + batchno);
        setBadge14(batchno);
    }

    @Override
    public void clearEvent() {
        customToast(this,"Action not Valid for this menu");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callsearch = false;
        action = "";
        Log.e(TAG, " onActivityResultttt");
        if (requestCode == SEARCH_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.hasExtra("location")) {
                    Map<String,String> map = TruckSearchActivity.orderList;

                    currLineData(map);
                    setProc(TruckBatchPickingActivity.PROC_BARCODE);
                    _sts(R.id.location,map.get("location"));
                    _sts(R.id.showfrontage,map.get("frontage"));
                    _sts(R.id.showbarcode,map.get("code"));
                    _sts(R.id.color,map.get("color"));
                    _sts(R.id.size,map.get("size"));

                    setProc(PROC_BARCODE);
                }

                else{
                    if (mProcNo == PROC_BARCODE) {
                    setProc(PROC_BARCODE);
                } else if (mProcNo == PROC_QTY) {
                    setProc(PROC_QTY);
                } else setProc(PROC_FRONTAGE);
            }
            }
            }
    }

    @Override
    public void allclearEvent() {
        CommonDialogs.customToast(this, "No action");
    }

    @Override
    public void skipEvent() {

     Skip();
    }


    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY:
                _sts(R.id.quantity, buff);
                break;
            case PROC_FRONTAGE:
                _sts(R.id.frontage, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (CLEAR_BARCODE.equals(barcode)) {
            mBuff.delete(0, mBuff.length());
            clearEvent();
        } else if (ENTER_BARCODE.equals(barcode)) {
            enterEvent();
        } else {
            if(!MainAsyncTask.dialogBox.isShowing()) {
                if (!barcode.equals("")) {
                    if (mProcNo == PROC_BARCODE) {
                        String finalbarcode1 = CommonFunctions.getBracode(barcode);
                        barcode =finalbarcode1;

                        _sts(R.id.barcode, barcode);

                    } else if (mProcNo == PROC_QTY) {
                        String finalbarcode1 = CommonFunctions.getBracode(barcode);
                        barcode =finalbarcode1;
                    }

                    if (mProcNo == PROC_FRONTAGE){
                        _sts(R.id.frontage,"");
                        _sts(R.id.frontage, barcode);
                    }
                }

                this.inputedEvent(barcode,true);
                mBuff.delete(0, mBuff.length());
            }
            else
                CommonDialogs.customToast(this,"wait");
        }}

    public void returnback(){
        complete = true;
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();
        startActivity(new Intent(this,TruckBatchActivity.class));
        finish();
    }


    public void currLineData(Map<String, String> data){
        cProductList = data;
    }

    public void nextWork() {

        cProductList.put("processedCnt", "1");
        setProc(PROC_QTY);

        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();

        _lastUpdateQty = _gts(R.id.quantity);


        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
            setProc(PROC_FRONTAGE);
            repeatFrontage(1000);
            inspection = COMPLETE_INSPECT;
        }
    }

    void SendRequest(String mode)
    {
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();
        Globals.getterList.add(new ParamsGetter("order_id", cProductList.get("order_id")));

        mRequestStatus =REQ_FRONTAGE;

        new MainAsyncTask(TruckBatchPickingActivity.this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchPickingActivity.this, "Form", Globals.getterList, true).execute();
    }


    void SkipRequest(){
        inspection = SKIP_INSPECT;
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();

        Globals.getterList = new ArrayList<>();
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("shipping_schedule", date));
        Globals.getterList.add(new ParamsGetter("truck_id", cProductList.get("truck_id")));
        Globals.getterList.add(new ParamsGetter("batch_id", cProductList.get("batch_id")));
        Globals.getterList.add(new ParamsGetter("order_sub_id", cProductList.get("order_sub_id")));
        Globals.getterList.add(new ParamsGetter("order_id", cProductList.get("order_id")));

        Globals.getterList.add(new ParamsGetter("location", _gts(R.id.location)));
        if(!cProductList.get("barcode").equals(""))
            Globals.getterList.add(new ParamsGetter("barcode", cProductList.get("barcode")));
        else
            Globals.getterList.add(new ParamsGetter("barcode", cProductList.get("code")));

        if(loc_reset==0)
            Globals.getterList.add(new ParamsGetter("location_start",selectedLoc));

        Globals.getterList.add(new ParamsGetter("quantity", "0"));
        Globals.getterList.add(new ParamsGetter("mode", "picking"));
        Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
        Globals.getterList.add(new ParamsGetter("batch_detail_no", _gts(R.id.frontage)));
        Globals.getterList.add(new ParamsGetter("inspection_status",inspection+""));
        Globals.getterList.add(new ParamsGetter("psh_id", cProductList.get("psh_id")));

        mRequestStatus = REQ_FRONTAGE;

        new MainAsyncTask(this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchPickingActivity.this, "Form", Globals.getterList, true).execute();
   }

    @Override
    public void enterEvent() {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QTY:
                if ("".equals(barcode)) {
                } else
                    _sts(R.id.quantity, barcode);
                break;
            case PROC_FRONTAGE:
                    _sts(R.id.frontage, barcode);
                break;

        }
    }

    public void setLocationList(List<String> list){
        locationList = list;
    }
    public void repeatLocation(int delay) {
        Log.e(TAG,"repeatLocationnnnnnnnnnnn");
//        mTextToSpeak.resetQueue();
        final int INTERVAL = delay;
        runnableCode = new Runnable() {
            @Override
            public void run() {
                Log.e(TAG,"repeatLocation_handlereee");
                int lang =  BaseActivity.getlangpos();
                String tts1 = "";
                String tts3 = "";
                String tts4 = "";
                String tts7 = "";
                if(lang == 1){
                    tts1 = LOCATION;
                    tts3 = BARCODE;
                    tts4 = QTY;
                }
                else {

                    tts1 = LOCATIONJAP;
                    tts3 = BARCODEJAP;
                    tts4 = QTYJAP;

                }
                String code = cProductList.get("code");
                String location = cProductList.get("location");
                String quantity = cProductList.get("quantity");


                String tts2 = location;

                String tts6 = code;

                String tts5 = quantity;
                String locChars[] = tts2.split("");
                mTextToSpeak.startSpeaking(tts1);
                for (String chars : locChars) {
                    mTextToSpeak.startSpeaking(chars);
                }


                mTextToSpeak.playsilence();
                String codeChars[] = tts6.split("");
                mTextToSpeak.startSpeaking(tts3);
                for (String chars : codeChars) {
                    mTextToSpeak.startSpeaking(chars);
                }
                mTextToSpeak.playsilence();
                mTextToSpeak.startSpeaking(tts4, tts5);

                handlerLoc.postDelayed(runnableCode, INTERVAL);
            }
        };
        handlerLoc.post(runnableCode);
    }

    public void repeatFrontage(int delay) {
        Log.e(TAG,"repeatFrontage");
//        mTextToSpeak.resetQueue();
        final int INTERVAL = delay;
        runnableCode = new Runnable() {
            @Override
            public void run() {
                Log.e(TAG,"repeatFrontage_handlereee");
                int lang =  BaseActivity.getlangpos();

                String tts7 = "";
                if(lang == 1){
                    tts7 = FONTAGE;
                }
                else {

                    tts7 = FONTAGEJAP;

                }

                String fontage = cProductList.get("frontage");

                mTextToSpeak.playsilence();
                String codeChars[] = fontage.split("");
                mTextToSpeak.startSpeaking(tts7);
                for (String chars : codeChars) {
                    mTextToSpeak.startSpeaking(chars);
                }
                handlerLoc.postDelayed(runnableCode, INTERVAL);
            }
        };
        handlerLoc.post(runnableCode);
    }

    public void setInfo( ArrayList<Map<String,String>> arr){
        orderList = arr;
        showInfo();
    }
    public void setDialog( ArrayList<Map<String,String>> arr){
        cancelList = arr;
        showPop(cancelList);
    }

    @Override
    protected void onStop() {
        closepage = true;
        action = "onStop";
        Log.e(TAG,"onstopppppppp   "+complete);
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();
        if(!isstop &&  !complete && !callsearch)
        callstop();

        if (!complete)
        BaseActivity.truckComplete =false;
        else
            BaseActivity.truckComplete =true;
        super.onStop();
    }

    @Override
    protected void onPause() {
        closepage = true;
        action = "onPause";
        Log.e(TAG,"onPauseeeeeeeeeee "+complete);
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();
        if(!isstop &&  !complete && !callsearch)
        callstop();

        if (!complete)
            BaseActivity.truckComplete =false;
        else
            BaseActivity.truckComplete =true;
        Log.e(TAG,"onPauseeeeeeeeeee "+BaseActivity.truckComplete );
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        closepage = true;
        action = "onDestroy";
        Log.e(TAG,"onDestroyyyyyyy  "+complete);
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();
        if(!isstop &&  !complete && !callsearch)
        callstop();

        if (!complete)
            BaseActivity.truckComplete =false;
        else
            BaseActivity.truckComplete =true;
        Log.e(TAG,"onDestroyyyyyyy  "+BaseActivity.truckComplete );
        super.onDestroy();
    }

    void callstop(){
        isstop = true;
        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("shipping_schedule", date));
        Globals.getterList.add(new ParamsGetter("mode", "stop_batch"));
        Globals.getterList.add(new ParamsGetter("batch_id", cProductList.get("batch_id")));

        mRequestStatus =REQ_STOP;
        new MainAsyncTask(TruckBatchPickingActivity.this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchPickingActivity.this, "Form", Globals.getterList, true).execute();

    }


    public void getCancelledlist(){

            handlerLoc.removeCallbacks(runnableCode);
            mTextToSpeak.resetQueue();
            action = "skip_list";
            Globals.getterList = new ArrayList<>();
            Globals.getterList.add(new ParamsGetter("admin_id", adminID));
            Globals.getterList.add(new ParamsGetter("shipping_schedule", date));
             Globals.getterList.add(new ParamsGetter("mode", "list_skip_cancelled_orders"));
            Globals.getterList.add(new ParamsGetter("batch_id", cProductList.get("batch_id")));
            mRequestStatus = REQ_SKIP;

            new MainAsyncTask(this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchPickingActivity.this, "Form", Globals.getterList, true).execute();

    }
    @Override
    @SuppressLint("InflateParams")
    protected boolean showInfo(){
        Log.e(TAG,"showInfooooooo");
        if (orderList == null) {
            Log.e("PickingActivityyyyyy","showInfoo  mPickingOrderList===000");
            return false;
        }
        else {
        if (getPopupWindow2() == null) {
            Log.e("PickingActivityyyyyy","showInfoo  popupwindow");
            final PopupWindow popup2Window = new PopupWindow(this);
            // レイアウト設定
            View popupView = getLayoutInflater().inflate(R.layout.order_list, null);
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
        ListView lv = (ListView) getPopupWindow2().getContentView().findViewById(R.id.orderPicking);
        initList(lv);

        // 画面中央に表示
        getPopupWindow2().showAtLocation(findViewById(R.id.barcode), Gravity.CENTER, 0, 32);
        return true;
    }}

    protected ListViewItems initList(ListView lv) {
        Log.e(TAG,"initListtttttt");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (int i = 0 ;i < orderList.size(); i++) {
            Map<String, String> row = orderList.get(i);


            Log.e(TAG,"initListtttttt11111");
            data.add(data.newItem().add(R.id.odr_pic_0, String.valueOf(i+1))
                    .add(R.id.odr_pic_1, row.get("order_no"))
            );
        }

        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.truck_batch_order_details) ;
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }


    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {
        HashMap<String, String> mHash=new HashMap<>();
        Log.e(TAG,result.toString());
        action = "";
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
                customToast(getApplicationContext(),"Network error occured");

            }
            if ("0".equals(code) == true) {
                Log.e("SendLogs111",code+"  "+msg+"  "+result1);
                if(mRequestStatus == REQ_INITIAL){
                    new GetLocationList().post(code,msg,result1, mHash,TruckBatchPickingActivity.this);
                }
                else if(mRequestStatus == REQ_LOC)
                    new GetTruckPicking().post(code,msg,result1, mHash,TruckBatchPickingActivity.this);
                else if(mRequestStatus == REQ_FRONTAGE)
                    new FixTruckPicking().post(code,msg,result1, mHash,TruckBatchPickingActivity.this);
                else if(mRequestStatus == REQ_STOP) {
                    handlerLoc.removeCallbacks(runnableCode);
                    mTextToSpeak.resetQueue();
                    if(closepage){
                        finish();
                    }
                    else if(isstop)
                      returnback();
                }else if (mRequestStatus == REQ_ORDER_DETAIL)
                    new TruckBatchOrderDetail().post(code,msg,result1, mHash,TruckBatchPickingActivity.this);
                else if (mRequestStatus == REQ_SKIP){
                    if (result1.size()>0)
                    new TruckBatchSkipDetails().post(code,msg,result1, mHash,TruckBatchPickingActivity.this);
                    else {
                        U.beepFinish(this, null);
                    complete = true;
                    returnback();
                    }
                }
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(TruckBatchPickingActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else
            {
                if(mRequestStatus == REQ_INITIAL)
                    new GetLocationList().valid(code,msg,result1, mHash,TruckBatchPickingActivity.this);
                else if(mRequestStatus == REQ_LOC)
                    new GetTruckPicking().valid(code,msg,result1, mHash,TruckBatchPickingActivity.this);
                else if(mRequestStatus == REQ_FRONTAGE)
                    new FixTruckPicking().valid(code,msg,result1, mHash,TruckBatchPickingActivity.this);
                else if(mRequestStatus == REQ_STOP)
                    U.beepError(this,msg);
                else if (mRequestStatus == REQ_ORDER_DETAIL)
                    new TruckBatchOrderDetail().valid(code,msg,result1, mHash,TruckBatchPickingActivity.this);
                else if (mRequestStatus == REQ_SKIP)
                    new TruckBatchSkipDetails().valid(code,msg,result1, mHash,TruckBatchPickingActivity.this);

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

    public void showMessage(){
        pDialog1 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog1.setCancelable(true);
        pDialog1.setContentText("注文キャンセルになりました、【注文キャンセル】のボータンを押してください。");
        pDialog1.setConfirmText("注文キャンセル");
        pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
        pDialog1.dismiss();

            }
        });
        pDialog1.show();
    }

    void showPop( List<Map<String,String>> list){

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_with_list);
        dialog.getWindow().setBackgroundDrawable(
        new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());

        dialog.setCanceledOnTouchOutside(false);

        // Init button of login GUI
        TextView title = (TextView)dialog.findViewById(R.id.text1);
        TextView txt=(TextView)dialog.findViewById(R.id.text2);
        TextView txt2=(TextView)dialog.findViewById(R.id.text3);

        ListView listView=(ListView) dialog.findViewById(R.id.listview) ;
        Button close=(Button)dialog.findViewById(R.id.close_btn);

        title.setText("キャンセルされた注文のピック済みの商品を戻してください。");

        txt.setVisibility(View.GONE);
        txt2.setVisibility(View.GONE);
        close.setText("OK");

        listView.setAdapter(null);
        ListViewItems data = new ListViewItems();
        Log.e(TAG,"initListtttttt13333");
        for (int i = 0 ;i < cancelList.size(); i++) {

            Map<String, String> row = cancelList.get(i);


            Log.e(TAG,"initListtttttt"+i+i);
            data.add(data.newItem().add(R.id.odr_pic_1, row.get("order_no"))
                    .add(R.id.odr_pic_2, row.get("code")).add(R.id.odr_pic_3, row.get("location"))
                    .add(R.id.odr_pic_4, row.get("quantity"))
                    .add(R.id.odr_pic_5, row.get("pick_quantity"))
            );
        }

        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.cancel_orders_product_row) ;
        listView.setAdapter(adapter);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                U.beepFinish(TruckBatchPickingActivity.this, null);
                complete = true;
                returnback();
                dialog.dismiss();

            }
        });
        // Make dialog box visible.
        dialog.show();

    }
}
