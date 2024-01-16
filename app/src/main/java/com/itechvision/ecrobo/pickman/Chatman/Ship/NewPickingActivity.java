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
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.AddPacking;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.CheckOrder;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.ClearNewPicking;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.FixedPicking;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetOrderDetail;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetPickingOrdersList;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetPiking;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.ListOrdersCountPicking;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.NewPickingAddPrint;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.R.drawable;
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

public class NewPickingActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(id.actionbar)
    ActionBar actionbar;
    @BindView(id.scrollMain)ScrollView svMain;
    @BindView(id.gridLotNo)LinearLayout lotNo;
    @BindView(id.gridserial)LinearLayout serialgrid;
    @BindView(id.gridPlateNo)LinearLayout plategrid;
    @BindView(id.add_layout)Button numbrbtn;
    @BindView(id.layout_main)RelativeLayout mainlayout;
    @BindView(id.layout_number)RelativeLayout layout;
    @BindView(id.standard1)EditText standard_one;
    @BindView(id.standard2)EditText standard_two;
    @BindView(id.shortBarcode)EditText result_barcode;
    @BindView(id.location)EditText result_loc;
    @BindView(id.productQuantity)EditText result_quantity;
    @BindView(id.productCode)EditText result_pdt;
    @BindView(id.numberOfLines)EditText lineNo;
    @BindView(id.lineNoBtn)Button lineNoBtn;

    protected int mProcNo = 0;
    public TextView productName;

    public static String serialPresent = "0";
    String serialno = "";
    private boolean addpackingList = false;
    private boolean fromqty= false;
    public static boolean isBarcodeChange = false;
    private boolean showKeyboard;
    public Context mcontext=this;
    private TextToSpeak mTextToSpeak;
    public static int mBoxNo = 0;

    public boolean clear = false;
    private List<String> mTrackId = new ArrayList<>();

    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPrefs";

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    public final Handler handlerLoc = new Handler();
    public Runnable runnableCode = null;
    boolean isretrying = true;

    public String groupOrderno ="";
    public static int count = 0;

    public long ORDER_QTY_COUNT = 0;
    String box="";
    public boolean nextbox = false;
    public static final int PACKING_ACTIVITY = 111;

    ECRApplication app=new ECRApplication();
    String adminID="",warehouseID ="";

    public static final int PROC_ORDERID = 1;
    public static final int PROC_ORDER_NO = 2;
    public static final int PROC_TRACKID = 3;
    public static final int PROC_BARCODE = 4;
    public static final int PROC_QTY = 5;
    public static final int PROC_LINE_NO = 6;
    public static final int PROC_LOT_NO =7;
    public static final int PROC_SERIAL_NO = 8;
    public static final int PROC_PLATE_NO = 9;
    public static final int COMPLETE_INSPECT = 1;
    public static final int INCOMPLETE_INSPECT = 2;
    public static final int SKIP_INSPECT = 3;
    public static final int CLEAR_INSPECT = 4;

    public final String LOCATION = "location";
    public final String CODE = "code";
    public final String QTY = "quantity";
    public final String BARCODE = "barcode";

    public final String LOCATIONJAP = "ロケ";
    public final String CODEJAP = "コード";
    public final String QTYJAP = "数量";

    public final String REMARK = "備考欄があります";

    protected Map<String, String> cProductList = null;
    public List<String> serialList = new ArrayList<>();
    public static List<Map<String, String>> packData = new ArrayList<Map<String, String>>();
    public static List<Map<String, String>> sendpackData = new ArrayList<Map<String, String>>();
    List<Map<String, String>> ordersList = new ArrayList<>();

    protected List<Map<String, String>> inspectionList = null;
    protected List<Map<String, String>> mProductList = null;
    public Map<String, String> mTarget = null;
    protected Map<String, String> mPackItem = new HashMap<String, String>();


    private boolean is_scan = false, triplebarcode = false;
    public static boolean isLotChange = false;

    private boolean lotpressed = false;
    private String tranactionId = "";
    private  boolean visible = false;

    private int orderRequestSettings;
    public String _lastUpdateQty = "0";

    public static int mRequestStatus =0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_ORDERID = 2;
    public static final int REQ_ORDERID1 = 3;
    public static final int REQ_ORDERID_GROUP = 4;
    public static final int REQ_CLEAR = 5;
    public static final int REQ_LINENO = 6;
    public static final int FIXED_REQ = 7;
    public static final int REQ_ORDERDETAIL = 8;
    public static final int REQ_ADDPRINT =9;
    public static final int REQ_ADDPACKING = 10;
    public static final int REQ_ALLORDERS_DETAIL = 11;

    public static boolean skipvalue=false;

    String TAG= NewPickingActivity.class.getSimpleName();
    SweetAlertDialog dialog1;
    Dialog dialog;

    public boolean packing =  false,complete=  false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderRequestSettings = BaseActivity.getOrderInfoBy();
        if (orderRequestSettings == SettingActivity.ORDER_ID) {
            setContentView(R.layout.activity_picking);
        }
        else if (orderRequestSettings == SettingActivity.ORDER_NUMBER) {
            setContentView(R.layout.activity_picking_order_no);
        }
        else if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO) {
            setContentView(R.layout.activity_picking_tracking_no);
        }

        ButterKnife.bind(NewPickingActivity.this);

        getIDs();
        ViewPager viewPager = (ViewPager) findViewById(id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        productName = (TextView) findViewById(id.productname);
        lotpressed =BaseActivity.getLotPress();

        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);

        //to check if user is going to Picking screen after home screen
        BaseActivity.setToPickingScreen(false);

        serialgrid.setVisibility(View.VISIBLE);
        plategrid.setVisibility(View.GONE);
        dialog = new Dialog(NewPickingActivity.this);


        if(lotpressed==false)//if lotno. not selected
            lotNo.setVisibility(View.GONE);

        triplebarcode = BaseActivity.getTripleBarcode();
        addpackingList =BaseActivity.getPackingList();
        dialog1 = new SweetAlertDialog(this);


        Log.e(TAG, "packing List status " + addpackingList);

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);

        showKeyboard=sharedPreferences.getBoolean("ShowKeyboard",false);
        Log.e(TAG,"checkValue  "+showKeyboard);

        numbrbtn =(Button) _g(id.add_layout);

        adminID=spDomain.getString("admin_id",null);
        warehouseID = spDomain.getString("warehouse_id",null);

        // display keyboard
        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(id.layout_number);
            visible = true;

            numbrbtn.setText("キーボードを隠す");
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);

            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e(TAG,"SetlayoutMargin");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
        }

        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);

        _gt(id.orderName).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        result_pdt.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        result_quantity.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        result_loc.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        result_barcode.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        standard_one.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        standard_two.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        productName.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

        if (mProcNo == 0) nextProcess();

        lineNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"LineNo_onClick");
                setProc(PROC_LINE_NO);
            }
        });

    }


    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "ピッキング検品", " ",
                0, true,true,true );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);


        relLayout1.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnBlue.setOnClickListener(this);

        btnGreen.setOnClickListener(this);
//        imgRight.setOnClickListener(this);

    }

    private void skipProduct() {
        if (cProductList != null){
            Log.e(TAG,"SkipProductttttttt");
            fixedRequest(SKIP_INSPECT);
        }
    }
    @OnClick (id.lineNoBtn) void lineNoBtn (){
        Log.e(TAG,"LineNoBtn_onClick");
        mBuff.delete(0, mBuff.length());
        if(mProcNo == PROC_LINE_NO) {
            Log.e(TAG,"LineNoBtn_If_PROC_LINE_NO_equal");
            String reqOrderId = "";
            String reqSortBy = "";
            String reqNum = _gts(id.numberOfLines);
            int startProc = 0;
            switch (orderRequestSettings) {
                case SettingActivity.ORDER_ID:
                    reqOrderId = _gts(id.orderId);
                    startProc = PROC_ORDERID;
                    break;
                case SettingActivity.ORDER_NUMBER:
                    reqOrderId = _gts(id.orderNo);
                    reqSortBy = "orderno";
                    startProc = PROC_ORDER_NO;
                    break;
                case SettingActivity.ORDER_TRACKING_NO:
                    reqOrderId = _gts(id.trackingNumber);
                    reqSortBy = "mediacode";
                    startProc = PROC_TRACKID;
                    break;
            }
            if (!reqOrderId.equals("")) {
                getPikingRequest(reqOrderId, reqSortBy, reqNum);
            } else {
                U.beepError(NewPickingActivity.this, "注文IDは必須です");
                setProc(startProc);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case id.relLayout1:
                menu.showMenu();
                break;
            case id.notif_count_red:
                startPackingBoxActivity();
                break;
            case id.notif_count_blue:
                if(getBadge1()!=0)
                    getOrders();
                break;
            case id.notif_count_green:
                if(getBadge3()!=0)
                    showPopup2();
                break;
            default:
                break;
        }
    }
    public void getOrders(){
        Globals.getterList = new ArrayList<>();

        adminID = spDomain.getString("admin_id", null);

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("get_orders", "true"));

        mRequestStatus = REQ_ALLORDERS_DETAIL;

        new MainAsyncTask(this, Globals.Webservice.Getshipping, 1, NewPickingActivity.this, "Form", Globals.getterList, true).execute();
    }

    protected boolean showPopup2() {

        String orderId = "";
        String mode = "not_inspection";
        String reqSortBy = "";
        switch(orderRequestSettings) {
            case SettingActivity.ORDER_ID:
                Log.e(TAG,"showPopup2_ORDER_ID");
                orderId = _gts(id.orderId);
                break;
            case SettingActivity.ORDER_NUMBER:
                Log.e(TAG,"showPopup2_ORDER_NUMBERRRRRRRR");
                orderId = _gts(id.orderNo);
                reqSortBy = "orderNo";
                break;
            case SettingActivity.ORDER_TRACKING_NO:
                Log.e(TAG,"showPopup2_ORDER_TRACKING_NOOOOOOOOOO");
                orderId = _gts(id.trackingNumber);
                reqSortBy = "mediacode";
                break;
        }


        Globals.getterList = new ArrayList<>();
        adminID=spDomain.getString("admin_id",null);

        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());
        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter(        "order_id", orderId));
        Globals.getterList.add(new ParamsGetter("sort_by",reqSortBy));
        Globals.getterList.add(new ParamsGetter("mode", mode));

        if (GetPiking.group == 1) {

            Globals.getterList.add(new ParamsGetter("group_order_no", groupOrderno));
        }

        mRequestStatus = REQ_ORDERDETAIL;
        new MainAsyncTask(this, Globals.Webservice.getPickingOrderDetail, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();

        return false;
    }

    public void AddLayout(View view)
    {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(id.layout_number);
        if(visible==false)
        {

            visible = true;
            numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e(TAG,"SetlayoutMarginn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        else
        {
            visible = false;
            numbrbtn.setText("キーボードを表示する");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG,"SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }

    }
    // convert the demension units
    public int convert(int x) {
        Resources r =mcontext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                x, r.getDisplayMetrics()
        );
        return px;
    }

    public void setProc(int procNo) {
        Log.e(TAG,"setProcccccc");
        mBuff.delete(0, mBuff.length());
        mProcNo = procNo;
        switch (procNo) {
            case PROC_ORDERID:
                Log.e(TAG,"setProc_PROC_ORDERIDDDDDD");
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.numberOfLines).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderId).setFocusableInTouchMode(true);

                if(lotpressed == true)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                scrollToView(svMain, _g(id.orderId));
                break;
            case PROC_ORDER_NO:
                Log.e(TAG,"setProc_PROC_ORDER_NOOOOOOOO");
                _gt(id.orderNo).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.numberOfLines).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderNo).setFocusableInTouchMode(true);

                if(lotpressed == true)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                scrollToView(svMain, _g(id.orderNo));
                break;
            case PROC_TRACKID:
                Log.e(TAG,"setProc_PROC_TRACKIDDDDDDDDD");
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.numberOfLines).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setFocusableInTouchMode(true);

                if(lotpressed == true)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.plateno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                scrollToView(svMain, _g(id.trackingNumber));
                break;
            case PROC_BARCODE:
                skipvalue = false;
                Log.e(TAG,"setProc_PROC_BARCODEEEEEEEEEE");
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.numberOfLines).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                if(lotpressed == true)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.plateno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(id.numberOfLines)); scrollToView(svMain, _g(id.location));
                is_scan = false;
                break;
            case PROC_LOT_NO:
                Log.e(TAG,"setProc_PROC_LOT_NOOOOOOO");
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                _gt(id.numberOfLines).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.lotno).setFocusableInTouchMode(true);

                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                scrollToView(svMain, _g(id.numberOfLines)); scrollToView(svMain, _g(id.lotno));
                break;
            case PROC_QTY:
                Log.e(TAG,"setProc_PROC_QTYYYYYYYYYYYYY");
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.numberOfLines).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                if(lotpressed == true)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                scrollToView(svMain, _g(id.quantity));
                if (_gts(id.quantity).equals("")) _sts(id.quantity, "1");
                _gt(id.quantity).setFocusableInTouchMode(true);

                break;
            case PROC_LINE_NO:
                Log.e(TAG,"setProc_PROC_LINE_NOOOOOOOOOOO");
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.numberOfLines).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));

                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                if(lotpressed == true)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                scrollToView(svMain, _g(id.numberOfLines));
                _gt(id.numberOfLines).setFocusableInTouchMode(true);
                break;

            case PROC_SERIAL_NO:

                Log.e(TAG,"setProc_PROC_LINE_NOOOOOOOOOOO");
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.numberOfLines).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                if(lotpressed == true)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                scrollToView(svMain, _g(id.numberOfLines));
                _gt(id.serialno).setFocusableInTouchMode(true);
                mTextToSpeak.startSpeaking("シリアル番号");
                break;

            case PROC_PLATE_NO:

                Log.e(TAG,"setProc_PROC_LINE_NOOOOOOOOOOO");
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.numberOfLines).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.plateno).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                if(lotpressed == true)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                scrollToView(svMain, _g(id.numberOfLines));
                _gt(id.plateno).setFocusableInTouchMode(true);

                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {
            case PROC_ORDER_NO:	// 注文No
                /* Conditional set next process whether orderId or trackingNo or orderNo. */
                if (orderRequestSettings == SettingActivity.ORDER_NUMBER) {
                    Log.e(TAG,"inputedEvent_PROC_ORDER_NOOOOOOOOO");
                    String orderNo = _gts(id.orderNo);
                    if ("".equals(orderNo)) {
                        U.beepError(this, "注文Noは必須です");
                        _gt(id.orderNo).setFocusableInTouchMode(true);

                        break;
                    }

                    Globals.getterList = new ArrayList<>();

                    adminID=spDomain.getString("admin_id",null);

                    Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                    Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                    Globals.getterList.add(new ParamsGetter("order_id", orderNo));
                    Globals.getterList.add(new ParamsGetter("sort_by", "orderno"));
                    Globals.getterList.add(new ParamsGetter("mode", "piking"));


                    if(BaseActivity.getShopId().equals("1159"))
                        Globals.getterList.add(new ParamsGetter("ip_address", CommonFunctions.NetwordDetect(this)));

                    mRequestStatus = REQ_ORDERID;
                    new MainAsyncTask(this, Globals.Webservice.checkOrder, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();
                }

                break;
            case PROC_ORDERID:    // 注文ID
                /* Conditional set next process whether orderId or trackingNo or orderNo. */
                if (orderRequestSettings == SettingActivity.ORDER_ID ) {
                    String orderId = _gts(id.orderId);

                    if ("".equals(orderId)) {
                        U.beepError(this, "注文IDは必須です");
                        _gt(id.orderId).setFocusableInTouchMode(true);
                        break;
                    }

                    Globals.getterList = new ArrayList<>();
                    adminID=spDomain.getString("admin_id",null);

                    Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                    Globals.getterList.add(new ParamsGetter("order_id", orderId));
                    Globals.getterList.add(new ParamsGetter("sort_by", ""));
                    Globals.getterList.add(new ParamsGetter("mode", "piking"));

                    if(BaseActivity.getShopId().equals("1159"))
                        Globals.getterList.add(new ParamsGetter("ip_address", CommonFunctions.NetwordDetect(this)));

                    mRequestStatus = REQ_ORDERID;

                    new MainAsyncTask(this, Globals.Webservice.checkOrder, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();
                }
                break;
            case PROC_TRACKID:
                /* Conditional set next process whether orderId or trackingNo or orderNo.*/
                if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO) {

                    String trackingNo = _gts(id.trackingNumber);
                    if ("".equals(trackingNo)) {
                        U.beepError(this, "トラック#は必須です");
                        _gt(id.trackingNumber).setFocusableInTouchMode(true);

                        break;
                    }

                    Globals.getterList = new ArrayList<>();
                    adminID=spDomain.getString("admin_id",null);

                    Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                    Globals.getterList.add(new ParamsGetter("order_id", trackingNo));
                    Globals.getterList.add(new ParamsGetter("sort_by", "mediacode"));
                    Globals.getterList.add(new ParamsGetter("mode", "piking"));
                    Globals.getterList.add(new ParamsGetter("get_status","false"));


                    if(BaseActivity.getShopId().equals("1159"))
                        Globals.getterList.add(new ParamsGetter("ip_address", CommonFunctions.NetwordDetect(this)));

                    mRequestStatus = REQ_ORDERID;

                    new MainAsyncTask(this, Globals.Webservice.checkOrder, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();

                } else {
                    if ("".equals(buff)) {
                        U.beepError(this, "トラック#は必須です");
                        _gt(id.trackingNumber).setFocusableInTouchMode(true);

                        break;
                    }
                    boolean match = false;
                    for (String tracking : mTrackId){
                        if (!tracking.equals(buff)) {
                            match= false;
                        }
                        else {
                            match=true;
                            break;
                        }}
                    if(!match){
                        Log.e(TAG,"inputedEvent_PROC_TRACKID_Tracking number not matched");
                        U.beepError(this, "Tracking number not matched to corresponding information");
                        _gt(id.trackingNumber).setFocusableInTouchMode(true);

                        break;
                    }
                    repeatLocation(1000);
                    setProc(PROC_BARCODE);
                }
                break;
            case PROC_BARCODE:    // バーコード
                Log.e(TAG,"Barcode====="+ GetPiking.getbarcode);
                ///            Log.e(TAG,"BarcodeFixed====="+FixedPicking.fixbarcode);
                boolean barcodematch = false;
                if(cProductList.containsKey("multi_barcodes")){
                    String multibarcodes = cProductList.get("multi_barcodes");
                    if(!multibarcodes.equals("")){
                        String barcodes[] = multibarcodes.split(",");
                        Log.e(TAG, ">>>>>>>>>>>>>>>"+barcodes[0]);
                        ArrayList<String> list1 = new ArrayList<>();
                        for(int i =0; i< barcodes.length ; i++){
                            if(buff.equals(barcodes[i])){
                                barcodematch = true;
                                break;
                            }
                            Log.e(TAG, ">>>>>>>>>>>>>>>111111"+list1);
                        }}
                }
                if((cProductList.get("barcode").trim().equals(buff)) || (cProductList.get("code").trim().equals(buff)) || barcodematch )
                {
                    String barcode = _gts(id.barcode);
                    Log.e(TAG,"inputedEvent_PROC_BARCODEEEEEE");
                    if ("".equals(barcode))
                    {
                        handlerLoc.removeCallbacks(runnableCode);
                        mTextToSpeak.resetQueue();
                        U.beepError(this, "バーコードは必須です");
                        _gt(id.barcode).setFocusableInTouchMode(true);

                        break;
                    }
                    isBarcodeChange = false;
                    if(lotpressed ==false){
                        Log.e(TAG,"serial Present   "+serialPresent);
                        if ((cProductList.get("barcode").trim().equals(barcode))|| cProductList.get("code").trim().equals(barcode) || barcodematch){
                            Log.e(TAG,"inputedEvent_PROC_BARCODE_IF_cProductList_equals_Barcode2222222222");
                            handlerLoc.removeCallbacks(runnableCode);
                            mTextToSpeak.resetQueue();

                            // if serial not selected on API
                            if(serialPresent.equals("0")) {
                                String processedCnt = cProductList.get("processedCnt");
                                cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                                _sts(id.quantity, cProductList.get("processedCnt"));
                                Log.e(TAG, "Value of Qunatityyyyy   " + _gts(id.quantity));
                                mTextToSpeak.startSpeaking(_gts(id.quantity));
                                setProc(PROC_QTY);
                                _lastUpdateQty = _gts(id.quantity);

                            }
                            //if serial seelected on API
                            else {
                                if(serialPresent.equals("1"))
                                    setProc(PROC_SERIAL_NO);
                                else {
                                    String processedCnt = cProductList.get("processedCnt");
                                    cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                                    _sts(id.quantity, cProductList.get("processedCnt"));
                                    Log.e(TAG, "Value of Qunatityyyyy   " + _gts(id.quantity));
                                    mTextToSpeak.startSpeaking(_gts(id.quantity));
                                    setProc(PROC_QTY);
                                    _lastUpdateQty = _gts(id.quantity);

                                }
                            }
                            if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                                Log.e(TAG,"inputedEvent_PROC_BARCODE_IF_cProductList_equals_quantity");
                                fixedRequest(COMPLETE_INSPECT);
                            }
                        } else {
                            isBarcodeChange = true;

                            U.beepError(this, "バーコードは必須です");
                            _gt(id.barcode).setFocusableInTouchMode(true);

                        }
                    }
                    else
                    {
                        if ((cProductList.get("barcode").trim().equals(barcode))|| cProductList.get("code").trim().equals(barcode) || barcodematch){
                            Log.e(TAG,"inputedEvent_PROC_BARCODE_IF_cProductList_equals_Barcode33333333333");
                            handlerLoc.removeCallbacks(runnableCode);
                            mTextToSpeak.resetQueue();

                            if(cProductList.get("lot").equals("")) {

                                if( serialPresent.equals("1"))
                                    setProc(PROC_SERIAL_NO);
                                else {
                                    String processedCnt = cProductList.get("processedCnt");
                                    cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                                    _sts(id.quantity, cProductList.get("processedCnt"));
                                    Log.e(TAG, "Value of Qunatityyyyy   " + _gts(id.quantity));

                                    mTextToSpeak.startSpeaking(_gts(id.quantity));
                                    setProc(PROC_QTY);
                                    _lastUpdateQty = _gts(id.quantity);
                                }}else
                                setProc(PROC_LOT_NO);

                            if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                                Log.e(TAG,"inputedEvent_PROC_BARCODE_IF_cProductList_equals_quantity");
                                fixedRequest(COMPLETE_INSPECT);
                            }

                        } else {

                            U.beepError(this, "バーコードは必須です");
                            _gt(id.barcode).setFocusableInTouchMode(true);
                        }
                    }}
                else{
                    handlerLoc.removeCallbacks(runnableCode);
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "BarCode Doesn't Match");
                    _gt(id.barcode).setFocusableInTouchMode(true);
                    break;
                }

                break;
            case PROC_LOT_NO:
                Log.e(TAG,"LOT====="+GetPiking.getlot);
                Log.e(TAG,"LOT====="+FixedPicking.fixlot);
                String lot = _gts(id.lotno);
                if(((buff.equals(GetPiking.getlot))||(buff.equals(FixedPicking.fixlot)))&& (cProductList.get("lot").equals(lot)))
                {
                    Log.e(TAG,"inputedEvent_PROC_LOTNOOOO");
                    if ("".equals(lot)) {
                        U.beepError(this, "バーコードは必須です");
                        _gt(id.lotno).setFocusableInTouchMode(true);
                        break;
                    }
                    isLotChange = false;
                    if (cProductList.get("lot").equals(lot)){
                        Log.e(TAG,"inputedEvent_PROC_LOTNO_IF_cProductList_equals_lotno");
                        handlerLoc.removeCallbacks(runnableCode);
                        mTextToSpeak.resetQueue();
                        if( serialPresent.equals("1"))
                            setProc(PROC_SERIAL_NO);
                        else {
                            String processedCnt = cProductList.get("processedCnt");
                            cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                            _sts(id.quantity, cProductList.get("processedCnt"));
                            Log.e(TAG, "Value of Qunatityyyyy   " + _gts(id.quantity));
                            mTextToSpeak.startSpeaking(_gts(id.quantity));

                            setProc(PROC_QTY);
                            _lastUpdateQty = _gts(id.quantity);
                        }
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            Log.e(TAG,"inputedEvent_PROC_BARCODE_IF_cProductList_equals_quantity");
                            fixedRequest(COMPLETE_INSPECT);
                        }
                    } else {
                        U.beepError(this, "バーコードは必須です");
                        _gt(id.lotno).setFocusableInTouchMode(true);
                    }
                }
                else{
                    U.beepError(this, "Lot no. Doesn't Match");
                    _gt(id.barcode).setFocusableInTouchMode(true);
                    break;
                }

                break;
            case PROC_QTY:

                Log.e(TAG, "inputedEvent_PROC_QTY");
                String qty = _gts(id.quantity);
                String barcode =_gts(id.barcode);

                String selectedbarcode = "";
                if(cProductList.containsKey("multi_barcodes")){
                    String multibarcodes = cProductList.get("multi_barcodes");
                    if(!multibarcodes.equals("")){
                        String barcodes[] = multibarcodes.split(",");
                        Log.e(TAG, ">>>>>>>>>>>>>>>22222"+barcodes[0]);
                        ArrayList<String> list1 = new ArrayList<>();
                        for(int i =0; i< barcodes.length ; i++){
                            if(_gts(id.barcode).equals(barcodes[i])){
                                selectedbarcode = barcodes[i];
                                break;
                            }
                            Log.e(TAG, ">>>>>>>>>>>>>>>111111"+list1);
                        }
                    }
                }

                if( serialPresent.equals("1"))
                    serialno= _gts(id.serialno);

                String lotno= "";
                String code = cProductList.get("code");
                String bar = cProductList.get("barcode");

                if (isScaned) {

                    Log.e(TAG, "Lot pressed  "+ lotpressed);

                    Log.e(TAG, "QTY  "+ qty);

                    if(U.compareNumeric(cProductList.get("processedCnt"),cProductList.get("quantity"))==0)
                    {
                        U.beepError(this,"Qunatity cannot exceed the required quantity");
                        break;
                    }

           /*         if(lotpressed && serialPresent.equals("0")){
                        lotno=_gts(id.lotno);

                        String lots = cProductList.get("lot");
                        if (buff.equals(barcode) || buff.equals(lotno)) {
                            if (buff.equals(barcode) && !lotno.equals("")) { *//*if barcode2 is not empty then store barcode1 and wait till second barcode*//*
                                setBarcodeTemp(buff); *//*store barcode1 in static variable*//*
                                break;

                            }
                            if (buff.equals(lotno) && !barcode.equals("")) {
                                if (!(getBarcodeTemp().concat(buff)).equals((barcode.concat(lotno)))) {
                                    U.beepError(this, "Scanned barcode and lotno not matched");
                                    break;
                                }
                            }
                            if (((getBarcodeTemp().concat(buff)).equals((bar.concat(lots))))||((getBarcodeTemp().concat(buff)).equals((code.concat(lots)))) ||((getBarcodeTemp().concat(buff)).equals((selectedbarcode.concat(lots))))) {
                                setBarcodeTemp("");

                                qty = U.plusTo(qty, "1");
                                String processedCnt = cProductList.get("processedCnt");
                                if(cProductList.get("processedCnt").equals(_gts(id.quantity)))
                                    cProductList.put("processedCnt",_gts(id.quantity));

                                cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                                _sts(id.quantity, qty);
                                mTextToSpeak.startSpeaking(qty);
                                _lastUpdateQty = _gts(id.quantity);

                                *//* check if update in quantity need next action *//*

                                if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                                    fixedRequest(COMPLETE_INSPECT);
                                }
                            }
                            break;
                        }
                        else {
                            U.beepError(this,"Barcode dont match");
                        }
                    }

                    else*/ if(serialPresent.equals("1")){

                        if (buff.equals(barcode) || buff.equals(serialno)) {
                            if (buff.equals(barcode) && !serialno.equals("")) { /*if barcode2 is not empty then store barcode1 and wait till second barcode*/
                                setBarcodeTemp(buff);
                                fromqty =true;
                                if(serialPresent.equals("1"))
                                    setProc(PROC_SERIAL_NO);/*store barcode1 in static variable*/
                                break;
                            }
                            if (buff.equals(serialno) && !barcode.equals("")) {
                                if (!(getBarcodeTemp().concat(buff)).equals((barcode.concat(serialno)))) {
//                                            fromqty =true;
//                                            setProc(PROC_SERIAL_NO);
//                                            U.beepError(this, "Scanned barcode and serialno. not matched");
                                    break;
                                }
                            }
                            if ( ((getBarcodeTemp().concat(buff)).equals(bar.concat(serialno)))||((getBarcodeTemp().concat(buff)).equals(selectedbarcode.concat(serialno))) ||((getBarcodeTemp().concat(buff)).equals(code.concat(serialno))) ) {
                                setBarcodeTemp("");
                                serialList.add(_gts(id.serialno));
                                Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned");
                                qty = U.plusTo(qty, "1");

                                Log.e(TAG, "processed count    "+cProductList.get("processedCnt"));
                                String processedCnt = cProductList.get("processedCnt");
                                cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                                _sts(id.quantity, qty);
                                mTextToSpeak.startSpeaking(qty);
                                _lastUpdateQty = _gts(id.quantity);

                                /* check if update in quantity need next action */
                                if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                                    fixedRequest(COMPLETE_INSPECT);
                                }
                            }
                            break;
                        }
                        else {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned3333");
                            U.beepError(this,"Barcode dont match");
//
                            Toast.makeText(getApplicationContext(),"BarCode Doesn't Match",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        if(buff.equals(barcode)){

                            qty = U.plusTo(qty, "1");
                            String processedCnt = cProductList.get("processedCnt");
                            cProductList.put("processedCnt",qty);
                            _sts(id.quantity, qty);
                            if(Integer.parseInt(qty)>1)
                                mTextToSpeak.startSpeaking(qty);
                            _lastUpdateQty = _gts(id.quantity);

                            /* check if update in quantity need next action */
                            if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                                fixedRequest(COMPLETE_INSPECT);
                            }
                        } else {
                            //U.beepError(this, "You scan wrong barcode");

                            U.beepError(this,"Barcode dont match");
                            Toast.makeText(getApplicationContext(),"BarCode Doesn't Match",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_Scanned");
                    if ("".equals(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                    _lastUpdateQty= qty;
                    String processedCnt = cProductList.get("processedCnt");
                    String qtyUpdate = U.minusTo(qty, _lastUpdateQty);
                    String maxQty_ = U.minusTo(cProductList.get("quantity"), qty);
                    Log.e(TAG, " processed count  "+processedCnt+"      "+" qtyUpdate count  "+qtyUpdate+"      "+" maxQty_  "+maxQty_+"      "+"  lat updatedd    "+_lastUpdateQty);

                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_lastUpdateQTY");
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_qtyUpdate");
                        U.beepError(this, "数量が多すぎます");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt");
//                        cProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        cProductList.put("processedCnt", qty);
                        mTextToSpeak.startSpeaking(_gts(id.quantity));
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            fixedRequest(COMPLETE_INSPECT);
                        } else {
                            if(packing){
                                fixedRequest(INCOMPLETE_INSPECT);
                            }
                            else{
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
                    }}
                break;

            case PROC_SERIAL_NO:
                String barcode1 = _gts(id.barcode);

                if(_gts(id.serialno).equals("")){
                    U.beepError(NewPickingActivity.this,null);
                    break;
                }
                if(_gts(id.serialno).equals(barcode1)){
                    U.beepError(NewPickingActivity.this,null);
                    break;
                }
                else {
                    if (fromqty == false) {
                        serialList.clear();
                        String processedCnt = cProductList.get("processedCnt");
                        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                        _sts(id.quantity, cProductList.get("processedCnt"));

                        mTextToSpeak.startSpeaking(_gts(id.quantity));
                        setProc(PROC_QTY);
                        _lastUpdateQty = _gts(id.quantity);
                        serialList.add(_gts(id.serialno));
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            fixedRequest(COMPLETE_INSPECT);
                        }
                    }
                    else {
                        String qty1 = _gts(id.quantity);
                        serialList.add(_gts(id.serialno));
                        setProc(PROC_QTY);
                        qty1 = U.plusTo(qty1, "1");
                        String processedCnt = cProductList.get("processedCnt");
                        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                        _sts(id.quantity, qty1);
                        fromqty =false;
                        if(Integer.parseInt(qty1)>1)
                            mTextToSpeak.startSpeaking(qty1);
                        _lastUpdateQty = _gts(id.quantity);

                        /* check if update in quantity need next action */
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barc;;;;lllllllllllllllllllllode_Scanned222222");
                            fixedRequest(COMPLETE_INSPECT);
                        }
                    }
                }
                break;

            case PROC_PLATE_NO:
                //if plateno. empty
                if (_gts(id.plateno).equals("")) {
                    U.beepError(this, "ロット番号が必要");
                    break;
                }
                //if quantity complete
                if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                    Log.e(TAG, "inputedEvent_PROC_PLATE_NO_IF_QNTY _ COMPLETE");
                    fixedRequest(COMPLETE_INSPECT);
                }
                //if quantity incomplete
                else{
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
                break;

            case PROC_LINE_NO:
                Log.e(TAG, "inputedEvent_PROC_LINE_NOOOOOOOOO");
                _sts(id.numberOfLines, buff);

                break;
        }
    }

    public void GetRow(){
        Log.e(TAG, ">>>>>>>>>>>>>>>7777777777    GetRow");
        mRequestStatus = REQ_ORDERID1;

        new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();
    }

    public void GetRowGroup(){
        Log.e(TAG, ">>>>>>777777    GetRowGroup");
        Globals.getterList.add(new ParamsGetter("group_order_no", groupOrderno));

        mRequestStatus = REQ_ORDERID_GROUP;

        new MainAsyncTask(this, Globals.Webservice.getGroupPicking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();
    }

    public void setmTrackId(ArrayList<String> mTrackId) {

        Log.e(TAG, "setmTrackIddddd   " + mTrackId);
        this.mTrackId = mTrackId;
    }

    public void getRemarkvoice()
    {
        mTextToSpeak.resetQueue();

        mTextToSpeak.startSpeaking(REMARK);
    }

    public void createNewData() {
        boolean repeat = false;
        for (Map<String, String> map : mProductList) {

//            if ((_gts(id.barcode).equals(map.get("barcode").trim()) || map.get("barcode").matches("(.*)" + _gts(id.barcode)))|| (_gts(id.barcode).equals(map.get("code").trim()))) {
//                Log.e(TAG, "DataAssigned");
            mTarget = map;
            Log.e(TAG, "Target  " + mTarget);

//            }
//             if(clear == true)
//            {
            mTarget = map;
            Log.e(TAG, "Target  " + mTarget);
//            }
        }
        Log.e(TAG, " createNewPackItemmmm ");
        Log.e(TAG, " createNewPackItemmmm Target " + mTarget);
        int target= 0;
        if(!_gts(id.quantity).equals("")) {
            target = Integer.parseInt(_gts(id.quantity));
        }
        int productqnt = Integer.parseInt(cProductList.get("quantity"));
        if (target <= productqnt) {
            if (packData.size() > 0) {

                String _b1 = mTarget.get("barcode");
                String _box1 = String.valueOf(mBoxNo);
                String _loc1 = mTarget.get("location");
                Log.e(TAG, "mtarget  barcode " + _b1 + " box " + _box1 + " loc " + _loc1);
                for (Map<String, String> map : packData) {
                    String _b = map.get("barcode");
                    String _box = map.get("boxNo");
                    String _loc = map.get("location");

                    StringBuffer temp_qty = new StringBuffer();

                    if (_b.equals(_b1) && _loc1.equals(_loc) ) {

                        String qnty = U.plusTo(map.get("quantity"), _gts(id.quantity));
                        Log.e(TAG, "map  quantity " + map.get("quantity"));
                        map.put("quantity", qnty);
                        repeat = true;
                    }
                }
            }

            if (repeat == false) {

                mPackItem = new HashMap<String, String>();
                mPackItem.put("boxNo", String.valueOf(mBoxNo));
                mPackItem.put("code", mTarget.get("code"));
                mPackItem.put("barcode", mTarget.get("barcode"));
                mPackItem.put("location", mTarget.get("location"));
                mPackItem.put("product_stock_history_id",mTarget.get("product_stock_history_id"));
                mPackItem.put("quantity", _gts(id.quantity).toString());
                mPackItem.put("orderSubId", mTarget.get("orderSubId"));
                packData.add(mPackItem);

            }

            is_scan = true;
        }
        updateBadge2("0");

    }

    public void removePackItem() {
        String selectedbarcode = "";

        if(cProductList.containsKey("multi_barcodes")){
            String multibarcodes = cProductList.get("multi_barcodes");
            if(!multibarcodes.equals("")){
                String barcodes[] = multibarcodes.split(",");

                ArrayList<String> list1 = new ArrayList<>();
                for(int i =0; i< barcodes.length ; i++){
                    if(_gts(id.barcode).equals(barcodes[i])){
                        selectedbarcode = barcodes[i];
                        break;
                    }
                    Log.e(TAG, ">>>>>>>>>>>>>>>111111"+list1);
                }}
        }
        for (Map<String, String> map : mProductList) {

            if ((_gts(id.barcode).equals(map.get("barcode").trim()) || map.get("barcode").matches("(.*)" + _gts(id.barcode))) || (_gts(id.barcode).equals(map.get("code").trim())) || (_gts(id.barcode).equals(selectedbarcode.trim()))) {
                Log.e(TAG, "DataAssigned");
                mTarget = map;
            }
        }
        Log.e(TAG, " removePackItemmmm Target " + mTarget);
        int target = Integer.parseInt(_gts(id.quantity));
        int productqnt = Integer.parseInt(cProductList.get("quantity"));

        if (packData.size() > 0) {
            {
                String _b1 = mTarget.get("barcode");
                String _box1 = String.valueOf(mBoxNo);
                Log.e(TAG, "mtarget  barcode " + _b1 + " box " + _box1);
                for (Map<String, String> map : packData) {
                    String _b = map.get("barcode");
                    String _box = map.get("boxNo");
                    Log.e(TAG, "remove pack data  barcode " + _b + " box " + _box);
                    StringBuffer temp_qty = new StringBuffer();
                    if (_b.equals(_b1) && _box.equals(_box1)) {
                        String qnty = U.minusTo(map.get("quantity"), _gts(id.quantity));
                        Log.e(TAG, "map  quantity " + map.get("quantity"));
                        map.put("quantity", qnty);
                    }
                    if (map.get("quantity").equals("0"))
                        packData.remove(map);
                }
            }
        }
    }

    public void

    repeatLocation(int delay) {
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
                if(lang == 1){

                    tts1 = LOCATION;
                    tts3 = BARCODE;
                    tts4 = QTY;
                }
                else {

                    tts1 = LOCATIONJAP;
                    tts3 = BARCODE;
                    tts4 = QTYJAP;

                }
                String code = result_barcode.getText().toString();
                String location = result_loc.getText().toString();
                String quantity = result_quantity.getText().toString();

                String tts2 = location;

                String tts6 = code;

                String tts5 = quantity;
                if(tts2.equalsIgnoreCase("-"))
                {
                    mTextToSpeak.startSpeaking(tts1);
                    mTextToSpeak.startSpeaking(tts2);
                    mTextToSpeak.playsilence();
                }
                else {
                    mTextToSpeak.startSpeaking(tts1, tts2);
                    mTextToSpeak.playsilence();
                }
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

    public void updateBadge1(String orderCount) {
        Log.e(TAG,"updateBa/9o/dge11111111111111"  + orderCount);
        setBadge1(Integer.valueOf(orderCount));
    }

    public void updateBadge2(String qtyCount) {
        Log.e(TAG,"updateBadge222222222222" + qtyCount);
        setBadge2(packData.size());
    }

    public void updateBadge3(String qtyCount) {
        Log.e(TAG,"updateBadge333333333333333333" + qtyCount);
        setBadge3(Integer.valueOf(qtyCount));
    }

    public void inspectionData(List<Map<String, String>> data){
        Log.e(TAG,"inspectionDataaaaaaaaaaa");
        inspectionList = data;
    }

    public void currLineData(Map<String, String> data){
        Log.e(TAG,"currLineDataaaaaaaaaaaa");
        cProductList = data;
    }

    public void setProductList(List<Map<String, String>> data) {
        Log.e(TAG, " setProductListttt ");
        mProductList = data;
        findRepeat();
    }
    public void updateLineNo(String cnt) {
        Log.e(TAG,"updateLineNooooooooooooooo" +cnt);
        _sts(id.numberOfLines, cnt);
    }

    private void findRepeat() {
        Log.e("ShipActivityyyyyy", " findRepeattttt");
        for (Map<String, String> map : mProductList) {
            String _b = map.get("barcode");
            String _l = null;


            StringBuffer temp_qty = new StringBuffer();
            for (Map<String, String> map1 : mProductList) {
                String _b1 = map1.get("barcode");
                String _l1 = null;

                _l1 = map1.get("location");
                if (_b.equals(_b1) && !_l.equals(_l1)) {
                    temp_qty.append(", ").append(map1.get("quantity"));
                    map.put("repeatQties", temp_qty.toString());
//                    }
                }
            }
        }
    }


    public void speakOrderItems(int delay) {
        Log.e(TAG, "speakOrderItemsssssssssss");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "speakOrderItems_handlerrrr");
                int lang =   BaseActivity.getlangpos();
                String tts3 = "";
                String tts5 = "";
                if(lang == 1){
                    tts3 = CODE;
                    tts5 = QTY;
                }
                else {


                    tts3 = CODEJAP;
                    tts5 = QTYJAP;
                }

                String code = result_barcode.getText().toString();
                String quantity = result_quantity.getText().toString().concat(".");

                String tts4 = code;
                String tts6 = quantity;
                /* code have speaking problem in Japanese.
                 * So, read it character by character
                 * */
                String codeChars[] = tts4.split("");
                mTextToSpeak.startSpeaking(tts3);
                for (String chars : codeChars) {
                    mTextToSpeak.startSpeaking(chars);
                }
                mTextToSpeak.playsilence();
                mTextToSpeak.startSpeaking(tts5, tts6);
            }
        }, delay);
    }


    @Override
    public void inputedEvent(String buff) {
        Log.e(TAG," inputedEventttttttttt");
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        if(BaseActivity.getPackingList() && packData.size()>0){
            new AlertDialog.Builder(this)
                    .setTitle("現在のBoxをパッキングリスト作成 ？")
                    .setPositiveButton("する", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            U.beepBigsound(NewPickingActivity.this, null);
                            handlerLoc.removeCallbacks(runnableCode);
                            mTextToSpeak.resetQueue();
                            mTextToSpeak.startSpeaking("clear");
                            printRequest();
                            clear = true;

                        }
                    })
                    .setNegativeButton("しない", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            U.beepBigsound(NewPickingActivity.this, null);
                            handlerLoc.removeCallbacks(runnableCode);
                            mTextToSpeak.resetQueue();
                            mTextToSpeak.startSpeaking("clear");
                            clear = true;
                            if(!"".equals(_gts(id.shortBarcode)))
                                fixedRequest(CLEAR_INSPECT);
                            else{
                                NewPickingActivity.this.nextProcess();
                            }}
                    })
                    .show();
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Clear？")
                    .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            handlerLoc.removeCallbacks(runnableCode);
                            mTextToSpeak.resetQueue();
                            mTextToSpeak.startSpeaking("clear");
                            clear = true;
                            if(!"".equals(_gts(id.shortBarcode)))
                                fixedRequest(CLEAR_INSPECT);
                            else{
                                NewPickingActivity.this.nextProcess();
                            }

                        }
                    })
                    .setNegativeButton("いいえ", null)
                    .show();
        }
    }

    public void clearcall()
    {
        if(!"".equals(_gts(id.shortBarcode)))
            fixedRequest(CLEAR_INSPECT);
        else{
            nextProcess();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (BaseActivity.getOrderInfoBy() == orderRequestSettings && !BaseActivity.getToPickingScreen()) {
            // Just restart from where we left
        } else {
            this.finish();
            Intent i = new Intent(this, NewPickingActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onPause() {
        handlerLoc.removeCallbacks(runnableCode);
        super.onPause();
    }

    @Override
    protected void onStop() {
        mTextToSpeak.resetQueue();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mTextToSpeak.resetQueue();
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();
    }

    public void setOrdersList ( List<Map<String, String>> list){
        ordersList = list;
        showInfo();
    }

    @Override
    @SuppressLint("InflateParams")
    protected boolean showInfo(){
        Log.e(TAG,"showInfooooooo");

        if (ordersList == null) {
            Log.e("PickingActivityyyyyy","showInfoo  mPickingOrderList===000");
            return false;
        }

        if (getPopupWindow2() == null) {
            Log.e("PickingActivityyyyyy","showInfoo  popupwindow");
            final PopupWindow popup2Window = new PopupWindow(this);
            // レイアウト設定
            View popupView = getLayoutInflater().inflate(R.layout.order_list, null);
            popup2Window.setContentView(popupView);
            // 背景設定
            popup2Window.setBackgroundDrawable(getResources().getDrawable(drawable.popup_background));
            // タップ時に他のViewでキャッチされないための設定
            popup2Window.setOutsideTouchable(true);
            popup2Window.setFocusable(true);

            // 表示サイズの設定 今回は幅300dp
            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 324, getResources().getDisplayMetrics());
            popup2Window.setWindowLayoutMode((int) width, WindowManager.LayoutParams.WRAP_CONTENT);
            popup2Window.setWidth((int) width);
            popup2Window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            setPopupWindow2(popup2Window);
            ImageView close =(ImageView)getPopupWindow2().getContentView().findViewById(id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup2Window.dismiss();
                }
            });
        }
        ListView lv = (ListView) getPopupWindow2().getContentView().findViewById(id.orderPicking);
        initList(lv);

        // 画面中央に表示
        getPopupWindow2().showAtLocation(findViewById(id.orderId), Gravity.CENTER, 0, 32);
        return true;
    }

    protected ListViewItems initList(ListView lv) {
        Log.e(TAG,"initListtttttt");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (int i = 0 ;i < ordersList.size(); i++) {
            Map<String, String> row = ordersList.get(i);


            Log.e(TAG,"initListtttttt11111");
            data.add(data.newItem().add(id.odr_pic_0, String.valueOf(i+1))
                    .add(id.odr_pic_1, row.get("order_no"))
                    .add(id.odr_pic_2,row.get("name"))
                    .add(id.odr_pic_3,row.get("batch_name"))

            );
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.order_list_row);
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }



    //to inactive backpress
    @Override
    public void onBackPressed() {
        // TODO not backed from picking activity
        //super.onBackPressed();
    }

    private void fixedRequest(Integer status) {
        /* Stop location repetition */
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();
        if (status == SKIP_INSPECT) {
            cProductList.put("processedCnt", "0");
            skipvalue = true;
        }
        if (is_scan == false && !_gts(id.barcode).equals("") && (!skipvalue || clear ))
            createNewData();

        if (status == CLEAR_INSPECT ) {


            adminID=spDomain.getString("admin_id",null);
            Globals.getterList = new ArrayList<>();

            Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

            Globals.getterList.add(new ParamsGetter("admin_id",adminID));
            Globals.getterList.add(new ParamsGetter("order_id", _gts(id.orderId)));

            Globals.getterList.add(new ParamsGetter("mode", "clear"));
            Globals.getterList.add(new ParamsGetter("inspection_status","clear"));
            Globals.getterList.add(new ParamsGetter("box_no", "1"));


            StringBuffer product_id =new StringBuffer();
            for (Map<String, String> map : packData) {
                product_id.append("\t").append(map.get("product_stock_history_id"));
            }

            Globals.getterList.add(new ParamsGetter("product_stock_history_id",product_id.substring(0).toString()));

            mRequestStatus = REQ_CLEAR;
            new MainAsyncTask(this, Globals.Webservice.fixedPiking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();

//            new ECZaikoClient(this).setListner(new ClearNewPicking()).fixed_pikingclear(this._gts(id.orderId), packData, "clear");
        }
        else
        {
            stopTimer();
            Globals.getterList = new ArrayList<>();


            adminID=spDomain.getString("admin_id",null);


            Log.e("SendLogs","shopidddddd  "+BaseActivity.getShopId());
            Globals.getterList.add(new ParamsGetter("admin_id",adminID));
            Globals.getterList.add(new ParamsGetter("row_no", cProductList.get("no")));
            Globals.getterList.add(new ParamsGetter("product_stock_history_id", cProductList.get("product_stock_history_id")));
            Globals.getterList.add(new ParamsGetter("box_no","1"));
            Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
            Globals.getterList.add(new ParamsGetter("num", cProductList.get("processedCnt")));
            Globals.getterList.add(new ParamsGetter("inspection_status",status+""));

            if(BaseActivity.getShopId().equals("1159"))
                Globals.getterList.add(new ParamsGetter("ip_address", CommonFunctions.NetwordDetect(this)));

            if(cProductList.get("barcode").equals(""))
                Globals.getterList.add(new ParamsGetter("barcode", cProductList.get("code")));
            else
                Globals.getterList.add(new ParamsGetter("barcode", cProductList.get("barcode")));


            if (serialPresent.equals("1") && serialList.size()>0){
                StringBuffer serial_list =new StringBuffer();
                for (String str : serialList) {
                    serial_list.append("\t").append(str);
                }
                Globals.getterList.add(new ParamsGetter("serial_no",serial_list.substring(1).toString()));
            }

            mRequestStatus = FIXED_REQ;

            if (GetPiking.group == 1) {

                Globals.getterList.add(new ParamsGetter("location", cProductList.get("location")));
                Globals.getterList.add(new ParamsGetter("group_order_no", groupOrderno));

                new MainAsyncTask(this, Globals.Webservice.fixedPikingGroup, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();

            } else {
                Globals.getterList.add(new ParamsGetter("mode", "piking"));

                if(BaseActivity.getShippingflag())
                    Globals.getterList.add(new ParamsGetter("shipping_flag","true"));
                // if serial selected
//            if (orderRequestSettings == SettingActivity.ORDER_ID) {
                Globals.getterList.add(new ParamsGetter("order_id", _gts(id.orderId)));
                Globals.getterList.add(new ParamsGetter("sort_by", ""));

                new MainAsyncTask(this, Globals.Webservice.fixedPiking, 1, NewPickingActivity.this, "Form", Globals.getterList, true).execute();
            }
          /*  }
            if(orderRequestSettings == SettingActivity.ORDER_NUMBER) {
                Globals.getterList.add(new ParamsGetter("order_id", _gts(id.orderNo) ));
                Globals.getterList.add(new ParamsGetter("sort_by", "orderno"));

                new MainAsyncTask(this, Globals.Webservice.fixedPiking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();
            }
            if(orderRequestSettings == SettingActivity.ORDER_TRACKING_NO) {
                Globals.getterList.add(new ParamsGetter("order_id", cProductList.get("mediacode")));
//                Globals.getterList.add(new ParamsGetter("order_id", _gts(id.trackingNumber) ));
                Globals.getterList.add(new ParamsGetter("sort_by", "mediacode"));*/

//                new MainAsyncTask(this, Globals.Webservice.fixedPiking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();

//            }
        }
        is_scan = false;
    }

    public void sendPrintRequest() {
        String printBoxNo = getPrintableBox();
        Log.e("ShipActivityyyyyy", " SendPrintRequestttt ");
        for (Map<String, String> map : packData) {
            box = map.get("boxNo");
        }

        String order = cProductList.get("order_id");

        Globals.getterList = new ArrayList<>();

        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("order_id", order));
        Globals.getterList.add(new ParamsGetter("single_box", "true"));
        Globals.getterList.add(new ParamsGetter("getTrack","false"));

        mRequestStatus = REQ_ADDPRINT;
        new MainAsyncTask(this, Globals.Webservice.addPrint, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();


    }

    public String getPrintableBox() {
        String boxes = "";
        Log.e("ShipActivityyyyyy", " getPrintableBoxx ");
        if (packData.size() > 0)
            for (Map<String, String> map : packData) {
                boxes += "," + map.get("boxNo");
            }
        if (boxes.length() > 0)
            boxes = boxes.substring(1);
        return boxes;
    }

    public void printRequest() {
        if (nextbox == true) {
            if (BaseActivity.getPackingList() == true) {
                sendPrintRequest();
                Log.e(TAG, "sending Print request1");
            } else {
                sendFinalRequest();
                Log.e(TAG, "sending NOOOO Print request1");
            }
        } else {

            String Url = spDomain.getString("domain", null);
            if((Url.equals("https://air-logi-st.air-logi.com/service")|| Url.equals("https://api.air-logi.com/service")) && (BaseActivity.getShopId().equals("1101") ||BaseActivity.getShopId().equals("1217")) ){
                if(BaseActivity.getPackingList() == true){
                    Log.e(TAG, "sending Print request222");
                    sendPrintRequest();}
                else{
                    sendFinalRequest();
                    Log.e(TAG, "sending NoOOO  Print request222");
                }
            }
            else {
                if(BaseActivity.getPackingList() == true){
                    sendPrintRequest();
                    Log.e(TAG, "sending Print request333333333333");}
                else{

                    sendFinalRequest();
                    Log.e(TAG, "sending NOOOO Print request3333333");}
            }

        }
    }
    public void sendFinalRequest() {
        Log.e(TAG, " sendFinalRequesttttt");

        String orderid = cProductList.get("order_id");
        Log.e(TAG, " sendFinalRequesttttt  Order id " + orderid);

        StringBuffer orderSubId = new StringBuffer();
        StringBuffer qty = new StringBuffer();
        StringBuffer barcode =new StringBuffer();
        StringBuffer psh_id = new StringBuffer();

        Log.e(TAG, " sendFinalRequesttttt  orderSubId " );
        for (Map<String, String> map : packData){
            if (!map.get("quantity").equals("0")) {
                orderSubId.append("\t").append(map.get("orderSubId"));
                qty.append("\t").append(map.get("quantity"));
                barcode.append("\t").append(map.get("barcode"));
                psh_id.append("\t").append(map.get("product_stock_history_id"));
            }
        }

        Log.e(TAG, " sendFinalRequesttttt  orderSubId   " +orderSubId);

        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Log.e(TAG, " sendFinalRequesttttt  GetPiking.group  " +GetPiking.group);
        if (GetPiking.group == 1) {
            Log.e(TAG, " sendFinalRequesttttt  barcode   " +barcode.substring(1));

            Globals.getterList.add(new ParamsGetter("group_no", groupOrderno));
            Globals.getterList.add(new ParamsGetter("group", "true"));
            Globals.getterList.add(new ParamsGetter("psh_id", psh_id.substring(1)));
            Globals.getterList.add(new ParamsGetter("barcode", barcode.substring(1)));
            Globals.getterList.add(new ParamsGetter("qty", qty.substring(1)));
            Globals.getterList.add(new ParamsGetter("box_no", mBoxNo + ""));
            Globals.getterList.add(new ParamsGetter("single_box", "true"));
        }
        else {
            Globals.getterList.add(new ParamsGetter("order_id", orderid));
            Globals.getterList.add(new ParamsGetter("transaction_id", this.getTranactionId()));
            Globals.getterList.add(new ParamsGetter("getTrack", "false"));
            Globals.getterList.add(new ParamsGetter("single_box", "true"));
            Globals.getterList.add(new ParamsGetter("box_no", mBoxNo + ""));


            Globals.getterList.add(new ParamsGetter("order_sub_id", orderSubId.substring(1)));
            Globals.getterList.add(new ParamsGetter("qty", qty.substring(1)));
            Globals.getterList.add(new ParamsGetter("group_by_barcode", "true"));
        }
        mRequestStatus = REQ_ADDPACKING;
        new MainAsyncTask(this, Globals.Webservice.addPacking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();


    }
    public String getTranactionId() {
        return tranactionId;
    }


    @Override
    public void allclearEvent() {

    }

    @Override
    public void skipEvent() {
        Log.e(TAG,"SkipBtn_OnClick");
        new AlertDialog.Builder(NewPickingActivity.this)
                .setTitle("スキップしてよろしいですか？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTextToSpeak.startSpeaking("Skip");
                        skipProduct();
                    }
                })
                .setNegativeButton("いいえ", null)
                .show();
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_ORDERID:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_ORDERIDDDDD");
                _sts(id.orderId, buff);
                break;
            case PROC_ORDER_NO:
                Log.e(TAG,"ketpressEvent_PROC_ORDERNOOOO");
                _sts(id.orderNo, buff);
                break;
            case PROC_TRACKID:    // Tracking number
                Log.e(TAG,"ketpressEvent_PROC_TRACKIDDDDD");
                _sts(id.trackingNumber, buff);
                break;
            case PROC_BARCODE:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_BARCODEEEEEE");
                _sts(id.barcode, buff);
                break;
            case PROC_SERIAL_NO:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_BARCODEEEEEE");
                _sts(id.serialno, buff);
                break;
            case PROC_QTY: // 数量
                Log.e(TAG,"ketpressEvent_PROC_QTYYYYYYYYY");
                _sts(id.quantity, buff);
                break;
            case PROC_PLATE_NO:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_BARCODEEEEEE");
                _sts(id.plateno, buff);
                break;
            case PROC_LINE_NO: // 数量
                Log.e(TAG,"ketpressEvent_PROC_LINE_NOOOOOOOOO");
                _sts(id.numberOfLines, buff);
                break;
            case PROC_LOT_NO:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_LOT_NOOOO");
                _sts(id.lotno, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {

        if (MainAsyncTask.dialogBox.isShowing()) {
            Toast.makeText(getApplicationContext(), "Please Wait", Toast.LENGTH_SHORT).show();
        }
        else if(dialog1.isShowing()){
            U.beepError(this, null);

        }
        else {
            if (CLEAR_BARCODE.equals(barcode)) {
                Log.e(TAG, "scanedEvent_CLEAR_BARCODE");
                mBuff.delete(0, mBuff.length());
                clearEvent();
            } else if (ENTER_BARCODE.equals(barcode)) {
                Log.e(TAG, "scanedEvent_ENTER_BARCODE");
                enterEvent();
            } else if (!barcode.equals("")) {
                if (isretrying) {
                    U.beepError(this, "Wait");
                } else {
                    if (mProcNo == PROC_BARCODE) {

                        if(warehouseID.equalsIgnoreCase("20722") && barcode.length()>18){
                            String withoutBrackets = barcode.replaceAll("\\)","");
                            withoutBrackets= withoutBrackets.replaceAll("\\(","");
                            String finalbarcode= withoutBrackets.substring(3,16);

                            _sts(R.id.barcode,finalbarcode);
                            barcode = finalbarcode;

                        }
                        else {

                            Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                            String finalbarcode1 = CommonFunctions.getBracode(barcode);
                            barcode = finalbarcode1;

                            if (triplebarcode == triplebarcode) {
                                if ((BaseActivity.getUrl().equals("https://air-logi-st.air-logi.com/service") && BaseActivity.getShopId().equals("1007"))) {
                                    if (barcode.length() == 39) {
                                        String finalbarcode = barcode.substring(26);
                                        Log.e(TAG, "ScannedEventttttt   " + " substring " + finalbarcode);

                                        barcode = finalbarcode;
                                    }
                                }
                            }
                        }
                        _sts(id.barcode, barcode);
                    } else if (mProcNo == PROC_SERIAL_NO) {
                        _sts(id.serialno, barcode);
                    } else if (mProcNo == PROC_PLATE_NO) _sts(id.plateno, barcode);
                    else if (mProcNo == PROC_ORDERID) _sts(id.orderId, barcode);
                    else if (mProcNo == PROC_ORDER_NO) _sts(id.orderNo, barcode);
                    else if (mProcNo == PROC_LINE_NO) _sts(id.numberOfLines, barcode);
                    else if (mProcNo == PROC_TRACKID) {
//                barcode = trim(barcode);
                        _sts(id.trackingNumber, barcode);
                    }

                    if (mProcNo == PROC_QTY) {
                        if (warehouseID.equalsIgnoreCase("20722") && barcode.length() > 18) {
                            String withoutBrackets = barcode.replaceAll("\\)", "");
                            withoutBrackets = withoutBrackets.replaceAll("\\(", "");
                            barcode = withoutBrackets.substring(3, 16);

                        } else {
                            Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                            String finalbarcode1 = CommonFunctions.getBracode(barcode);
                            barcode = finalbarcode1;

                            if (triplebarcode == triplebarcode) {
                                if ((BaseActivity.getUrl().equals("https://air-logi-st.air-logi.com/service") && BaseActivity.getShopId().equals("1007"))) {
                                    if (barcode.length() == 39) {
                                        String finalbarcode = barcode.substring(26);
                                        Log.e(TAG, "ScannedEventttttt   " + " substring " + finalbarcode);

                                        barcode = finalbarcode;
                                    }
                                }
                            }
                        }
                    }
                    if (mProcNo == PROC_LOT_NO) {
                        if(warehouseID.equalsIgnoreCase("20722") && barcode.length()>18){
                            String withoutBrackets = barcode.replaceAll("\\)","");
                            withoutBrackets= withoutBrackets.replaceAll("\\(","");
                            barcode = withoutBrackets.substring(18);

                        }
                        _sts(id.lotno, barcode);
                    }
                    this.inputedEvent(barcode, true);
                }
            }
        }
    }

    @Override
    public void enterEvent() {
        Log.e(TAG, " enterEventtttttttttt");
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();
        if (lotpressed == true) {
            if (mProcNo >= PROC_LOT_NO) {
                Log.e(TAG, " enterEvent_mProcNo >= PROC_BARCODE");
                speakOrderItems(500);
            }
        }
        else{
            if (mProcNo >= PROC_BARCODE){
                Log.e(TAG," enterEvent_mProcNo >= PROC_BARCODE");
                speakOrderItems(500);}
        }
    }
    private void getPikingRequest(String orderId, String sortBy, String lineNo) {
        // stop repeating location
        Log.e(TAG,"getPikingRequesttttttt");
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();

        Globals.getterList = new ArrayList<>();


        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("order_id", orderId));
        Globals.getterList.add(new ParamsGetter("sort_by", sortBy));
        Globals.getterList.add(new ParamsGetter("mode", "piking"));
        Globals.getterList.add(new ParamsGetter("row_no",lineNo));
        if(BaseActivity.getShopId().equals("1159"))
            Globals.getterList.add(new ParamsGetter("ip_address", CommonFunctions.NetwordDetect(this)));


        mRequestStatus = REQ_LINENO;
        new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();

    }


    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                Log.e(TAG,"deleteEvent_PROC_BARCODE");
                _sts(id.barcode, barcode);
                break;
            case PROC_SERIAL_NO:    // バーコード
                Log.e(TAG,"deleteEvent_PROC_BARCODE");
                _sts(id.serialno, barcode);
                break;
            case PROC_PLATE_NO:    // バーコード
                Log.e(TAG,"deleteEvent_PROC_BARCODE");
                _sts(id.plateno, barcode);
                break;
            case PROC_QTY:
                Log.e(TAG,"deleteEvent_PROC_QTY");
                _sts(id.quantity, barcode);
                break;
            case PROC_ORDERID:
                Log.e(TAG,"deleteEvent_PROC_ORDERID");
                _sts(id.orderId, barcode);
                break;
            case PROC_ORDER_NO:
                Log.e(TAG,"deleteEvent_PROC_ORDERNo");
                _sts(id.orderNo, barcode);
                break;
            case PROC_TRACKID:
                Log.e(TAG,"deleteEvent_PROC_TRACKID");
                _sts(id.trackingNumber, barcode);
                break;
            case PROC_LINE_NO:
                Log.e(TAG,"deleteEvent_PROC_LINE_NO");
                _sts(id.numberOfLines, barcode);
                break;
            case PROC_LOT_NO:
                Log.e(TAG,"deleteEvent_PROC_LINE_NO");
                _sts(id.lotno, barcode);
                break;
        }
    }

    @Override
    public void nextProcess() {
        Log.e(TAG," nextProcessssssssss");
        this._sts(id.barcode, "");
        this._sts(id.quantity, "");
        this._sts(id.orderId, "");
        this._sts(id.standard1, "");
        this._sts(id.standard2, "");
        this._stxtv(id.productname, "");
        this._sts(id.orderName, "");
        this._sts(id.trackingNumber, "");
        this._sts(id.location, "");
        this._sts(id.productCode, "");
        this._sts(id.productQuantity, "");
        this._sts(id.shortBarcode, "");
        this._sts(id.numberOfLines, "");
        this._sts(id.serialno, "");
        this._sts(id.plateno, "");

        complete = false;
        packing = false;

        isretrying = false;
        fromqty= false;
        clear = false;

        serialgrid.setVisibility(View.GONE);
        plategrid.setVisibility(View.GONE);

        lotpressed =BaseActivity.getLotPress();
        if(lotpressed ==true){
            lotNo.setVisibility(View.VISIBLE);
            this._sts(id.lotno, "");}
        else
            lotNo.setVisibility(View.GONE);

        serialno= "";

        groupOrderno="";
        GetPiking.group = 0;
        serialPresent = "0";
        serialList.clear();
        this.cProductList = null;
        skipvalue = false;
        isBarcodeChange = false;
        /* list order */
        setBadge3(0);
        setBadge2(0);
        setBadge1(0);

//        this.setTranactionId("");
        mBoxNo = 0;
        mProductList = null;
        resetPackData();
        addpackingList =BaseActivity.getPackingList();
        Log.e("NewShipActivityyyy", "packing List status " + addpackingList);

        switch (orderRequestSettings) {
            case SettingActivity.ORDER_ID:
                Log.e(TAG,"nextProcessssssssss_ORDER_ID");
                this.setProc(PROC_ORDERID);
                break;
            case SettingActivity.ORDER_NUMBER:
                Log.e(TAG,"nextProcessssssssss_ORDER_NUMBER");
                this._sts(id.orderNo, "");
                this.setProc(PROC_ORDER_NO);
                break;
            case SettingActivity.ORDER_TRACKING_NO:
                Log.e(TAG,"nextProcessssssssss_ORDER_TRACKING_NO");
                this.setProc(PROC_TRACKID);
                break;

        }


        //closes the keyboard
        packData.clear();
        if(showKeyboard==false) {
            visible = false;
            numbrbtn.setVisibility(View.VISIBLE);
            numbrbtn.setText("キーボードを表示する");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
//            Animation bottomDown = AnimationUtils.loadAnimation(this,
//                    R.anim.bottom_down);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
//            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
        else
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(id.layout_number);
            visible = true;

            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e(TAG,"SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        Globals.getterList = new ArrayList<>();

        adminID=spDomain.getString("admin_id",null);

        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("initial_call", "yes"));
        mRequestStatus = REQ_INITIAL;
        new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();

    }
    public void setLayout(){
        serialgrid.setVisibility(View.VISIBLE);
        plategrid.setVisibility(View.GONE);
    }

    public void nextProcess1(){
        Log.e("NewPickingActivity","nextProcess22222222222");
        this._sts(id.barcode, "");
        this._sts(id.quantity, "");
        this._sts(id.orderId, "");
        this._sts(id.trackingNumber, "");
        this._sts(id.standard1, "");
        this._sts(id.standard2, "");
        this._stxtv(id.productname, "");
        this._sts(id.orderName, "");
        this._sts(id.location, "");
        this._sts(id.productCode, "");
        this._sts(id.productQuantity, "");
        this._sts(id.shortBarcode, "");
        this._sts(id.numberOfLines, "");
        this._sts(id.serialno, "");
        this._sts(id.plateno, "");
        skipvalue=false;
        clear =false;
        if(lotpressed ==true)
            this._sts(id.lotno, "");

        isBarcodeChange = false;

        serialgrid.setVisibility(View.GONE);
        plategrid.setVisibility(View.GONE);

        serialno= "";
        serialPresent = "0";
        mBoxNo = 0;
//        this.setTranactionId("");
        addpackingList =BaseActivity.getPackingList();

        //closes the keyboard

        if(showKeyboard==false) {
            visible = false;
            numbrbtn.setText("キーボードを表示する");
            numbrbtn.setVisibility(View.VISIBLE);
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);

            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
//            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e("NewPicking", "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
        else
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(id.layout_number);
            visible = true;

//			numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e("NewPicking","SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        //
        switch (orderRequestSettings) {
            case SettingActivity.ORDER_ID:
                Log.e(TAG,"nextProcess22_ORDER_ID");

                this.setProc(PROC_ORDERID);
                break;
            case SettingActivity.ORDER_NUMBER:
                Log.e(TAG,"nextProcess22_ORDER_NUMBER");
//                this._sts(id.trackingNumber, "");
                this._sts(id.orderNo, "");
                this.setProc(PROC_ORDER_NO);
                break;
            case SettingActivity.ORDER_TRACKING_NO:
                Log.e(TAG,"nextProcess22_ORDER_TRACKING_NO");
                this.setProc(PROC_TRACKID);
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        packing =false;
        Log.e(TAG, " onActivityResultttt");
        if (requestCode == PACKING_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.hasExtra(FINISH)) {
                    Log.d("CALLBACK", FINISH);
                    if(!result_barcode.getText().toString().equals(""))
                        repeatLocation(2000);
//                    send = true;
                } else if (data.hasExtra(NEXTBOX)) {
                    if (data.getStringExtra(NEXTBOX).equals(ADDNEXTBOX)) {
                        nextbox = true;
                        printRequest();
                        if(!result_barcode.getText().toString().equals(""))
                            repeatLocation(2000);
                        // Command to add next box
                        if (mProcNo == PROC_QTY) {

                            updateBadge1("0");
                            //                 _sts(id.barcode, "");
                            _sts(id.location, "");
                            _sts(id.quantity, "");
                            _sts(id.productCode, "");
                            _sts(id.productQuantity, "");
                            setProc(PROC_BARCODE);

                        }
                    }
                    Log.d("CALLBACK", "Next box");
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("PACKING_RESPONSE", "Canceled");
                if(!result_barcode.getText().toString().equals(""))
                    repeatLocation(2000);

            }
        }
    }

    public boolean initiatePopup(){
        Log.e(TAG,"initiatePopuppppppppp");
        final PopupWindow popupWindow = new PopupWindow(this);
        if (inspectionList == null) {
            return false;
        }

        if (getPopupWindow() == null) {
            Log.e(TAG,"initiatePopUpp_getPopupWindow");

            // レイアウト設定
            View popupView = getLayoutInflater().inflate(R.layout.inspection_detail, null);
            popupWindow.setContentView(popupView);
            // 背景設定
            popupWindow.setBackgroundDrawable(getResources().getDrawable(drawable.popup_background));
            // タップ時に他のViewでキャッチされないための設定
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);

            // 表示サイズの設定 今回は幅300dp
            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 324, getResources().getDisplayMetrics());
            popupWindow.setWindowLayoutMode((int) width, WindowManager.LayoutParams.WRAP_CONTENT);
            popupWindow.setWidth((int) width);
            popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            setPopupWindow(popupWindow);
            ImageView close =(ImageView)getPopupWindow().getContentView().findViewById(id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }
        ListView lv = (ListView) getPopupWindow().getContentView().findViewById(id.workPicking);
        initWorkList(lv);

        // 画面中央に表示
        getPopupWindow().showAtLocation(findViewById(id.orderId), Gravity.CENTER, 0, 32);
        return false;
    }

    protected ListViewItems initWorkList(ListView lv) {
        Log.e(TAG," initWorkList");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (int i = inspectionList.size() - 1; i >= 0; i--)
        {
            Map<String, String> row = inspectionList.get(i);
            data.add(data.newItem().add(id.wrk_ins_0, row.get("code"))
                    .add(id.wrk_ins_1, String.valueOf(Math.abs(Integer.parseInt(row.get("diff")))))
                    .add(id.wrk_ins_2, row.get("inspection_num")));
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.work_inspection);
        lv.setAdapter(adapter);

        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }

    protected boolean startPackingBoxActivity() {

        if (BaseActivity.getPackingList()== true) {
            if (mProcNo == PROC_QTY) {
                if (is_scan == false)
                    createNewData();

                packing = true;
                is_scan = true;
                inputedEvent("",false);

//                fixedRequest(INCOMPLETE_INSPECT);

//			updateBadge2(""+leftcount);
            }
        }
        if (packData.size() == 0) {
            return true;
        }
        if (BaseActivity.getPackingList() == true) {
            Intent packingIntent = new Intent(this, PackingBoxActivity.class);
            packingIntent.putExtra("orderId", _gts(id.orderId));

            packingIntent.putExtra("orderQtySize", ORDER_QTY_COUNT);
            packingIntent.putExtra("packedQtySize", getBadge1());

            startActivityForResult(packingIntent, PACKING_ACTIVITY);
            return false;
        } else if(BaseActivity.getPackingList()==false) {
            return true;}
        return true;
    }

    public void resetPackData() {
        Log.e(TAG, " ResetPackDataaaa ");
        packData.clear();
        ORDER_QTY_COUNT = 0;
    }

    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {
        HashMap<String, String> mHash=new HashMap<>();
        Log.e(TAG,result.toString());
        GetPiking.getbarcode ="0";
        GetPiking.getlot ="0";


        try {

            String response= result.toString();
            JsonPullParser parser = JsonPullParser.newParser(response);
            JsonHash map1 = JsonHash.fromParser(parser);
            Log.e(TAG," "+map1);

            String msg="";
            JsonArray result1;
            msg=map1.getStringOrNull("message");
            result1= map1.getJsonArrayOrNull("results");
            String code = map1.getStringOrNull("code");

            if (code == null) {
                Log.e(TAG,"CODEeee============Nulllll");
                CommonDialogs.customToast(getApplicationContext(),"Network error occured");
            }
            if ("0".equals(code) == true)
            {
                msg=map1.getStringOrNull("message");
                result1= map1.getJsonArrayOrNull("results");
                Log.e(TAG," result111111    "+result1);
                Log.e(TAG," mRequestStatus    "+mRequestStatus);

                if(mRequestStatus == REQ_INITIAL)
                {
                    new ListOrdersCountPicking().post(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ORDERID)
                {
                    new CheckOrder().post(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ORDERID1)
                {
                    new GetPiking().post(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ORDERID_GROUP)
                {
                    new GetPiking().post(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if (mRequestStatus == REQ_LINENO)
                {
                    new GetPiking().post(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_CLEAR)
                {
                    new ClearNewPicking().post(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ORDERDETAIL){
                    new GetOrderDetail().post(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ADDPRINT)
                {
                    new NewPickingAddPrint().post(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus ==FIXED_REQ)
                {
                    new FixedPicking().post(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ALLORDERS_DETAIL)
                {
                    new GetPickingOrdersList().post(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ADDPACKING)
                {
                    new AddPacking().post(code,msg,result1, mHash,NewPickingActivity.this);
                }
            }    else if (code.equalsIgnoreCase("222222")) {

                showDialog("注文引当前戻ししました。");

            }
            else if(code.equalsIgnoreCase("1020"))
            {
                new AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(NewPickingActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();
                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else if(code.equalsIgnoreCase("420") && mRequestStatus == REQ_ORDERID1){

                new AlertDialog.Builder(this)
                        .setTitle("すでに検品済みです。")
                        .setMessage("もう一度検品しますか？")
                        .setPositiveButton("する", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Globals.getterList.add(new ParamsGetter("picking_clear", "1"));

                                new MainAsyncTask(NewPickingActivity.this, Globals.Webservice.getPiking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();
                            }
                        })
                        .setNegativeButton("しない", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
            else{
                if(mRequestStatus == REQ_INITIAL)
                {
                    new ListOrdersCountPicking().valid(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ORDERID)
                {
                    new GetPiking().valid(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if (mRequestStatus == REQ_LINENO)
                {
                    new GetPiking().valid(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_CLEAR)
                {
                    new ClearNewPicking().valid(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ORDERDETAIL)
                {
                    new GetOrderDetail().valid(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ADDPRINT)
                {
                    new NewPickingAddPrint().valid(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus ==FIXED_REQ)
                {
                    new FixedPicking().valid(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ALLORDERS_DETAIL)
                {
                    new GetPickingOrdersList().valid(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ORDERID1)
                {
                    new GetPiking().valid(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ORDERID_GROUP)
                {
                    new GetPiking().valid(code,msg,result1, mHash,NewPickingActivity.this);
                }
                else if(mRequestStatus == REQ_ADDPACKING)
                    new AddPacking().valid(code,msg,result1, mHash,NewPickingActivity.this);


            }
        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {
        Log.e(TAG, "flagggggg     "+flag);
        if(count>50){
            Log.e(TAG,">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   counttt1111 "+ count);
//          setdialog("ネットワークに接続をできません、\n ネットワーク確認してください。",this,R.layout.remark_dialog_popup);
            count =0;
            isretrying = false;
        }
        else {
            isretrying = true;
            count++;
            Log.e(TAG,">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   counttt "+ count);
            if(mRequestStatus == REQ_INITIAL)
            {
                new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();
            }
            else if(mRequestStatus == REQ_LINENO)
            {
                new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();
            }
            else if(mRequestStatus == REQ_ORDERID)
            {
                new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();
            }
            else if(mRequestStatus == REQ_CLEAR)
            {
                new MainAsyncTask(this, Globals.Webservice.fixedPiking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();
            }
            else if(mRequestStatus == REQ_ORDERDETAIL)
            {
                new MainAsyncTask(this, Globals.Webservice.getPickingOrderDetail, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();
            }
            else if(mRequestStatus == REQ_ADDPRINT)
            {
                new MainAsyncTask(this, Globals.Webservice.addPrint, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();
            }
            else if(mRequestStatus == REQ_ADDPACKING)
            {
                new MainAsyncTask(this, Globals.Webservice.addPacking, 1, NewPickingActivity.this, "Form", Globals.getterList, true).execute();
            }
            else if(mRequestStatus ==FIXED_REQ){
                if (serialPresent.equals("1") )
                    new MainAsyncTask(this, Globals.Webservice.fixedPiking, 1, NewPickingActivity.this, "Form", Globals.getterList,true).execute();
            }
        }
    }
    public void showRemarkDialog(String msg)
    {
        dialog1 = new SweetAlertDialog(this);
        dialog1.setTitle(msg);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);


        dialog1.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog1.dismiss();
            }
        });

        dialog1.show();



/*
        // Set GUI of login screen
        dialog.setContentView(R.layout.remark_dialog);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.setCancelable(false);


        dialog.setCanceledOnTouchOutside(false);

        // Init button of login GUI
        TextView txt=(TextView)dialog.findViewById(R.id.txt) ;
        txt.setText(msg);
        Button btn_ok=(Button)dialog.findViewById(R.id.btn_ok_dialog);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        // Make dialog box visible.
        dialog.show();*/

    }

}

