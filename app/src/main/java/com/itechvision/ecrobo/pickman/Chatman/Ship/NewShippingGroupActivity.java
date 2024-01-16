package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.app.Activity;
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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.AddPackingGroup;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.FixedPikingNSGroup;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetPickingOrderGroup;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetPikingNSGroup;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetShipingTrackGroup;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetShippingOrderGroup;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.ListOrdersCountNS;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.ShippingOrderDetailGroup;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.drawable;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
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

import static com.itechvision.ecrobo.pickman.Util.CommonFunctions.matchString;

public class NewShippingGroupActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.scrollMain)ScrollView svMain;

    @BindView(R.id.add_layout)Button numbrbtn;
    @BindView(R.id.layout_main)RelativeLayout mainlayout;
    @BindView(R.id.layout_number)RelativeLayout layout;

    public static boolean isBarcodeChange = false;

    String TAG= NewShippingGroupActivity.class.getSimpleName();

    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPrefs";

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    private List<String> mTrackId = new ArrayList<>();
    public Map<String, String> mTarget = null;
    protected Map<String, String> cProductList = null;
    public static List<Map<String, String>> packData = new ArrayList<Map<String, String>>();
    public static List<Map<String, String>> packBoxData = new ArrayList<Map<String, String>>();
    protected List<Map<String, String>> mOrderList = null;
    protected List<Map<String, String>> mProductList = null;
    public List<String> serialList = new ArrayList<>();
    protected Map<String, String> mPackItem = new HashMap<String, String>();
    public static List<String> order = new ArrayList<String>();
    public static List<String> orderSubid = new ArrayList<String>();

    ECRApplication app=new ECRApplication();
    String adminID="";

    private boolean visible = false;
    public static int leftcount = 0;
    public String _lastUpdateQty = "0";
    public String groupOrderno ="";
    public static int mBoxNo = 0;
    public static boolean skipvalue = false;
    public boolean errorDialog = false;

    public final String REMARK = "備考欄があります";

    private boolean showKeyboard;
    private int orderRequestSettings;
    private boolean addpackingList = false;
    public boolean clear = false;

    public Context mcontext=this;
    private TextToSpeak mTextToSpeak;

    public boolean nextbox = false;
    String box="";

    public static boolean ordergot=false;
    public  String orderi = "";
    public static int inspection = 0;
    private boolean fromqty= false;

    SweetAlertDialog pDialog1;

    public static String serialPresent = "0";
    String serialno = "";

    int badge=0;

    protected int mProcNo = 0;
    public static final int PACKING_ACTIVITY = 111;
    public static final int PROC_ORDERID = 1;
    public static final int PROC_ORDER_NO = 2;
    public static final int PROC_TRACKID = 3;
    public static final int PROC_BARCODE = 4;
    public static final int PROC_QTY = 5;
    public static final int PROC_SERIAL_NO = 6;

    public static final int COMPLETE_INSPECT = 1;
    public static final int INCOMPLETE_INSPECT = 2;
    public static final int ALL_CLEAR = 3;

    public static int mRequestStatus =0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_ORDERID = 2;
    public static final int REQ_CLEAR = 3;
    public static final int FIXED_REQ = 5;
    public static final int REQ_ORDERDETAIL = 6;
    public static final int REQ_ADDPRINT =7;
    public static final int REQ_FIXED_ORDER = 8;
    public static final int REQ_BARCODE = 9;
    public static final int REQ_ALLORDERS_DETAIL = 10;
    public static final int REQ_ADDPACKING = 4;

    public EditText orderId, location;
    public TextView orderlabel, locationlabel;

    private int totalordercount=0;

    private boolean is_scan = false;
    public boolean mNextBarcode = false;
    public String isNextBarcode ="";

    public long ORDER_QTY_COUNT = 0;

    public String tranactionId = "", orderID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderRequestSettings = BaseActivity.getOrderInfoBy();
        if (orderRequestSettings == SettingActivity.ORDER_ID)
            setContentView(R.layout.activity_new_shipping_group);
        else if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
            setContentView(R.layout.activity_new_shipping_group_order_no);
        else if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO)
            setContentView(R.layout.activity_new_shipping_group_tracking_no);

        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);

        ButterKnife.bind(NewShippingGroupActivity.this);

        Log.d(TAG,"On Create ");
        getIDs();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        orderId = (EditText) _gt(id.orderId);
        orderlabel = (TextView) _gtxtv(id.orderIdLabel);
        location = (EditText) _gt(id.location);
        locationlabel = (TextView) _gtxtv(id.locationLabel);

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);

        showKeyboard=sharedPreferences.getBoolean("ShowKeyboard",false);
        adminID=spDomain.getString("admin_id",null);

        if (showKeyboard == true) {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
            visible = true;

//			numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            layout = (RelativeLayout) findViewById(id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layout.getLayoutParams();
            param.setMargins(0, top, 0, 0);
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        addpackingList =BaseActivity.getPackingList();

        _gt(id.orderName).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        _gt(id.totalQuantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        _gt(id.quantityDetail).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        _gt(id.productCode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        _gt(id.productQuantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        _gt(id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        if (mProcNo == 0) nextProcess();

    }

    //set title and icons on actionbar
    private void getIDs() {

        actionbarImplement(this, "レジ検品(G)", " ",
                0, true,true,true ,false);
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnBlue.setOnClickListener(this);

        btnGreen.setOnClickListener(this);
//        imgRight.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
            case id.notif_count_blue:
                showInfo();
                break;
            case id.notif_count_green:
                startPackingBoxActivity();
                break;
            case id.notif_count_red:
                showPopup3();
                break;
        }
    }
    @OnClick(id.enter)
    void enter() {
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
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
            Log.e(TAG,"SetlayoutMarginnnnn");
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
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

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
        mProcNo = procNo;
        switch (procNo) {
            case PROC_ORDERID:
                Log.e(TAG, "setProc_ORDERIDDDD");
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderId).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(id.orderId));
                break;
            case PROC_ORDER_NO:
                Log.e(TAG, "setProc_ORDERNOOOOO");
                _gt(id.orderNo).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderId).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(id.orderNo));
                break;
            case PROC_TRACKID:
                Log.e(TAG, "setProc_TRACKIDDDDDDDDDDDD");
                if (this.getOrderInfoBy() == SettingActivity.ORDER_NUMBER)
                    _gt(id.orderNo).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
//				_gt(id.numberOfLines).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(id.trackingNumber));
                break;
            case PROC_BARCODE:
                Log.e(TAG, "setProc_BARCODEEEEEEEEE");
                if (this.getOrderInfoBy() == SettingActivity.ORDER_NUMBER)
                    _gt(id.orderNo).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setFocusableInTouchMode(true);
                mTextToSpeak.startSpeaking("barkodo");
                scrollToView(svMain, _g(id.barcode));
                is_scan = false;
                break;
            case PROC_QTY:
                Log.e(TAG, "setProc_QNTYYYYYYYYYYYY");
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                if (_gts(id.quantity).equals("")) _sts(id.quantity, "1");
                _gt(id.quantity).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(id.quantity));
                break;
            case PROC_SERIAL_NO:
                Log.e("NewPickingActivity","setProc_PROC_LINE_NOOOOOOOOOOO");
                Log.e(TAG, "setProc_QNTYYYYYYYYYYYY");
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.serialno).setFocusableInTouchMode(true);
                mTextToSpeak.startSpeaking("Shiriaru bangō");
                scrollToView(svMain, _g(id.quantity));
                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {
            case PROC_ORDER_NO:    // 注文No
                /* Conditional set next process whether orderId or trackingNo or orderNo. */
                if (orderRequestSettings == SettingActivity.ORDER_NUMBER) {
                    Log.e(TAG, "inputedEvent_ORDERNOOOOO");
                    String orderNo = _gts(id.orderNo);

                    if ("".equals(orderNo)) {
                        U.beepError(this, "注文Noは必須です");
                        _gt(id.orderNo).setFocusableInTouchMode(true);
                        break;
                    }

                    Globals.getterList = new ArrayList<>();


                    Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    Globals.getterList.add(new ParamsGetter("order_id", orderNo));
                    Globals.getterList.add(new ParamsGetter("sort_by", "orderno"));
                    Globals.getterList.add(new ParamsGetter("mode", "shipping"));
                    Globals.getterList.add(new ParamsGetter("getTrack", "false"));
                    Globals.getterList.add(new ParamsGetter("get_status", "false"));
                    mRequestStatus = REQ_ORDERID;

                    new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList, true).execute();

                }
                break;
            case PROC_ORDERID:    // 注文ID
                /* Conditional set next process whether orderId or trackingNo or orderNo. */
                if (orderRequestSettings == SettingActivity.ORDER_ID) {
                    Log.e("NewPickingActivity", "inputedEvent_PROC_ORDER_IDDDDDDDD");
                    String orderId = _gts(id.orderId);
                    if ("".equals(orderId)) {
                        U.beepError(this, "注文IDは必須です");
                        _gt(id.orderId).setFocusableInTouchMode(true);
                        break;
                    }

                    Globals.getterList = new ArrayList<>();


                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    Globals.getterList.add(new ParamsGetter("order_id", orderId));
                    Globals.getterList.add(new ParamsGetter("sort_by", ""));
                    Globals.getterList.add(new ParamsGetter("mode", "shipping"));
                    Globals.getterList.add(new ParamsGetter("getTrack", "false"));
                    Globals.getterList.add(new ParamsGetter("get_status", "false"));
                    mRequestStatus = REQ_ORDERID;

                    new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList, true).execute();

                }
                break;
            case PROC_TRACKID:    // tracking number
                /* Conditional set next process whether orderId or trackingNo or orderNo.*/
                if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO) {
                    Log.e("NewPickingActivity", "inputedEvent_PROC_TRACKIDDDDD");
                    String trackingNo = _gts(id.trackingNumber);
                    if ("".equals(trackingNo)) {
                        U.beepError(this, "トラック#は必須です");
                        _gt(id.trackingNumber).setFocusableInTouchMode(true);
                        break;
                    }

                    Globals.getterList = new ArrayList<>();

                    Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());
                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    Globals.getterList.add(new ParamsGetter("order_id", trackingNo));
                    Globals.getterList.add(new ParamsGetter("sort_by", "mediacode"));
                    Globals.getterList.add(new ParamsGetter("mode", "shipping"));
                    Globals.getterList.add(new ParamsGetter("getTrack", "true"));
                    Globals.getterList.add(new ParamsGetter("get_status", "false"));
                    mRequestStatus = REQ_ORDERID;

                    new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList, true).execute();
                } else {
                    if ("".equals(buff)) {
                        U.beepError(this, "トラック#は必須です");
                        _gt(id.trackingNumber).setFocusableInTouchMode(true);
                        break;

                    }
                    boolean match = false;
                    String track = buff.replaceFirst("^0+(?!$)", "");
                    for (String tracking : mTrackId){
                        String mtrack = tracking.replaceFirst("^0+(?!$)", "");
                        if (!track.equals(mtrack)) {
                            match= false;
                        }
                        else {
                            match=true;
                            break;
                        }}
                    if(!match){
                        Log.e(TAG, "inputedEvent_PROC_TRACKID_Tracking number not matched");
                        U.beepError(this, "Tracking number not matched to corresponding information");
                        _gt(id.trackingNumber).setFocusableInTouchMode(true);
                        break;
                    }
                    setProc(PROC_BARCODE);
                }
                break;
            case PROC_BARCODE:    // バーコード
                String barcode = _gts(id.barcode);
                mTarget = null;
                Log.e(TAG, "inputedEvent_BARCODEEEEEEEEEE");
//                setKey(PROC_BARCODE);

                if(errorDialog){
                    U.beepError(this,null);
                    break;
                }
                if ("".equals(barcode)) {
                    U.beepError(this, "バーコードは必須です");
                    Log.e(TAG, "inputedEvent_BARCODE === null");
                    _gt(id.barcode).setFocusableInTouchMode(true);
                    break;
                }

                Globals.getterList = new ArrayList<>();

                Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());
                Globals.getterList.add(new ParamsGetter("admin_id", adminID));

                Globals.getterList.add(new ParamsGetter("mode", "shipping"));

                Globals.getterList.add(new ParamsGetter("get_status", "true"));
                Globals.getterList.add(new ParamsGetter("barcode", barcode));
                mRequestStatus = REQ_BARCODE;

                switch (orderRequestSettings) {
                    case SettingActivity.ORDER_NUMBER:
                        Log.e(TAG, "inputedEvent_BARCODEEEEEEEEEE sending Request orderno");
                        Globals.getterList.add(new ParamsGetter("order_id", _gts(id.orderNo)));
                        Globals.getterList.add(new ParamsGetter("sort_by", "orderno"));
                        new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList, true).execute();

                        break;
                    case SettingActivity.ORDER_ID:
                        Log.e(TAG, "inputedEvent_BARCODEEEEEEEEEE sending Request orderid");
                        if (GetPickingOrderGroup.group == 1) {
                            Globals.getterList.add(new ParamsGetter("order_id", groupOrderno));
                            Globals.getterList.add(new ParamsGetter("sort_by", "group"));
                            Globals.getterList.add(new ParamsGetter("getProduct","true"));
                            new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList, true).execute();

                        } else {
                            Globals.getterList.add(new ParamsGetter("order_id", _gts(id.orderId)));
                            Globals.getterList.add(new ParamsGetter("sort_by", ""));
                            new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList, true).execute();


                        }
                        break;
                    case SettingActivity.ORDER_TRACKING_NO:
                        Log.e(TAG, "inputedEvent_BARCODEEEEEEEEEE sending Request trackingno");
                        if (GetPickingOrderGroup.track == 1) {
                            Globals.getterList.add(new ParamsGetter("order_id", _gts(id.trackingNumber)));
                            Globals.getterList.add(new ParamsGetter("sort_by", "mediacode"));
                            Globals.getterList.add(new ParamsGetter("getTrackProducts","true"));

                            new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList, true).execute();

                        } else
                        {
                            Globals.getterList.add(new ParamsGetter("order_id", _gts(id.trackingNumber)));
                            Globals.getterList.add(new ParamsGetter("sort_by", "mediacode"));
                            new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList, true).execute();

                        }
                        break;
                }

                break;

            case PROC_QTY:
                String qty = _gts(id.quantity);
                String barcode1 = _gts(id.barcode);
                serialno = _gts(id.serialno);
                Log.e(TAG, "inputedEvent_QNTYY Barcode11111    "+qty);
                if (isScaned) {
                    Log.e(TAG, "inputedEvent_QNTYY Barcode Scanned");

                    if (serialPresent.equals("1")) {
                        String bar = cProductList.get("barcode");
                        String code = cProductList.get("code");
                        if (buff.equals(barcode1) || buff.equals(serialno)) {
                            if (buff.equals(barcode1) && !serialno.equals("")) { /*if barcode2 is not empty then store barcode1 and wait till second barcode*/
                                setBarcodeTemp(buff);
                                fromqty = true;
                                setProc(PROC_SERIAL_NO);/*store barcode1 in static variable*/
                                break;
                            }
                            if (buff.equals(serialno) && !barcode1.equals("")) {
                                if (!(getBarcodeTemp().concat(buff)).equals((barcode1.concat(serialno)))) {
                                    U.beepError(this, "Scanned barcode and serialno. not matched");
                                    break;
                                }
                            }
                            if (((getBarcodeTemp().concat(buff)).equals((bar.concat(serialno))))||((getBarcodeTemp().concat(buff)).equals((code.concat(serialno)))) ) {
                                setBarcodeTemp("");
                                Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned");
                                qty = U.plusTo(qty, "1");
                                String processedCnt = cProductList.get("processedCnt");
                                if (!processedCnt.equals(_gts(id.quantity)))
                                    processedCnt = _gts(id.quantity);
                                cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                                if (Integer.parseInt(cProductList.get("processedCnt")) <= Integer.parseInt(cProductList.get("quantity"))) {
                                    _sts(id.quantity, qty);
                                    if (Integer.parseInt(qty) > 1)
                                        mTextToSpeak.startSpeaking(qty);
                                    updateBadge4();
                                    _lastUpdateQty = _gts(id.quantity);
                                    /* check if update in quantity need next action */
                                    if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                                        Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty complete");
                                        fixedRequest(COMPLETE_INSPECT);
                                    }
                                }
                            }
                            break;
                        }

                    } else {
                        if (buff.equals(barcode1)) {
                            Log.e(TAG, "inputedEvent_QNTYY Barcode match");
                            qty = U.plusTo(qty, "1");
                            String processedCnt = cProductList.get("processedCnt");
                            if (!processedCnt.equals(_gts(id.quantity)))
                                processedCnt = _gts(id.quantity);
                            cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                            //increase qunatity in mpackdata
                            if (Integer.parseInt(cProductList.get("processedCnt")) <= Integer.parseInt(cProductList.get("quantity"))) {
                                String _loc1 = cProductList.get("location");

                                _sts(id.quantity, qty);
                                if (Integer.parseInt(qty) > 1)
                                    mTextToSpeak.startSpeaking(qty);
                                updateBadge4();
                                _lastUpdateQty = _gts(id.quantity);

                                /* check if update in quantity need next action */
                                if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                                    Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty complete");
                                    fixedRequest(COMPLETE_INSPECT);
                                }
                            }
                        } else {
                            Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty INcomplete");
                            fixedRequest(INCOMPLETE_INSPECT, buff);
                        }
                    }
                } else {
                    if ("".equals(qty)) {
                        Log.e(TAG, "inputedEvent_QNTYY empty ");
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
                    Log.e(TAG, "inputedEvent_QNTYY maxqnty " + maxQty_);
                    Log.e(TAG, "inputedEvent_QNTYY qtyUpdate " + qtyUpdate);
                    Log.e(TAG, "inputedEvent_QNTYY processedCnt " + processedCnt);
                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());
                        Log.e(TAG, "inputedEvent_QNTYY is less");
                        _sts(id.quantity, "1");
                        String _box1 = String.valueOf(mBoxNo);
                        String _loc1 = cProductList.get("location");

                        cProductList.put("processedCnt", "1");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        Log.e(TAG, "inputedEvent_QNTYY is exceeds");
                        U.beepError(this, "数量が多すぎます");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        _sts(id.quantity, "1");
                        String _box1 = String.valueOf(mBoxNo);
                        String _loc1 = cProductList.get("location");

                        cProductList.put("processedCnt", "1");
                        break;
                    } else {
                        if (Integer.parseInt(qty) > 1)
                            mTextToSpeak.startSpeaking(_gts(id.quantity));
                        cProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            Log.e(TAG, "inputedEvent_QNTYY complete inspect");
                            //increase qunatity in mpackdata
                            if (Integer.parseInt(cProductList.get("processedCnt")) <= Integer.parseInt(cProductList.get("quantity"))) {
                                String _box1 = String.valueOf(mBoxNo);
                                String _loc1 = cProductList.get("location");

                            }
                            fixedRequest(COMPLETE_INSPECT);

                        } else {
                            Log.e(TAG, "inputedEvent_QNTYY INcomplete inspect");
                            //increase qunatity in mpackdata
                            if (Integer.parseInt(cProductList.get("processedCnt")) <= Integer.parseInt(cProductList.get("quantity"))) {

                                String _box1 = String.valueOf(mBoxNo);
                                String _loc1 = cProductList.get("location");

                            }
                            fixedRequest(INCOMPLETE_INSPECT);
                        }
                    }

                }
                break;
            case PROC_SERIAL_NO:

                String barcode2 = _gts(id.barcode);

                if(_gts(id.serialno).equals("")){
                    U.beepError(this,null);
                    break;
                }
                if(_gts(id.serialno).equals(barcode2)){
                    U.beepError(this,null);
                    break;
                } else {
                    if (fromqty == false) {
                        serialList.clear();
                        serialList.add(_gts(id.serialno));
                        serialno = _gts(id.serialno);
                        nextWork();
                    } else {
                        fromqty=false;
                        setProc(PROC_QTY);
                        String qty1 = _gts(id.quantity);
                        serialList.add(_gts(id.serialno));
                        setBarcodeTemp("");
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned");
                        qty1 = U.plusTo(qty1, "1");
                        String processedCnt = cProductList.get("processedCnt");
                        if (!processedCnt.equals(_gts(id.quantity)))
                            processedCnt = _gts(id.quantity);
                        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                        if (Integer.parseInt(cProductList.get("processedCnt")) <= Integer.parseInt(cProductList.get("quantity"))) {
                            _sts(id.quantity, qty1);
                            if (Integer.parseInt(qty1) > 1)
                                mTextToSpeak.startSpeaking(qty1);
                            updateBadge4();
                            _lastUpdateQty = _gts(id.quantity);
                            /* check if update in quantity need next action */
                            if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                                Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty complete");
                                fixedRequest(COMPLETE_INSPECT);
                            }
                        }
                    }
                }
                break;
        }
    }

    protected boolean startPackingBoxActivity() {
        Log.e(TAG, " startPackingBoxActivityyyy");
        Log.e(TAG, "PackData  " + packBoxData);

        if (BaseActivity.getPackingList()== true) {
            if (mProcNo == PROC_QTY) {
                if (is_scan == false)
                    createNewPackItem();
                fixedRequest(INCOMPLETE_INSPECT);

            }
        }
        if (packBoxData.size() == 0) {
            return true;
        }
        if (BaseActivity.getPackingList() == true) {
            Intent packingIntent = new Intent(this, NewPackingBoxGroupActivity.class);
            packingIntent.putExtra("orderId", _gts(id.orderId));
            if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO && GetPickingOrderGroup.track == 1)
                packingIntent.putExtra("tracking_no", _gts(id.trackingNumber));

            packingIntent.putExtra("orderQtySize", ORDER_QTY_COUNT);
            packingIntent.putExtra("packedQtySize", getBadge3());
            Log.e("NewShippingActivity","showPack");
            startActivityForResult(packingIntent, PACKING_ACTIVITY);
            return false;
        } else if(BaseActivity.getPackingList()==false){
            return true;}
        return true;
    }

    protected boolean showPopup3() {
        Log.e("NewShippingActivity","showPopup3333");
        if ( mProcNo == PROC_ORDERID) {
            return true;
        }
        else if (mProcNo == PROC_TRACKID) {
            return true;
        }

        else if (mProcNo == PROC_ORDER_NO) {
            return true;
        }
        else {

            Globals.getterList = new ArrayList<>();


            Log.e("SendLogs","shopidddddd  "+BaseActivity.getShopId());
            Globals.getterList.add(new ParamsGetter("admin_id",adminID));

            Globals.getterList.add(new ParamsGetter("mode", "shipping"));

            Globals.getterList.add(new ParamsGetter("get_order_detail", "true"));

            mRequestStatus = REQ_ALLORDERS_DETAIL;


            if(orderRequestSettings==SettingActivity.ORDER_ID) {
                if(GetPickingOrderGroup.group==1) {
                    Globals.getterList.add(new ParamsGetter("order_id", groupOrderno));
                    Globals.getterList.add(new ParamsGetter("sort_by", "group"));

                }else{
                    Globals.getterList.add(new ParamsGetter("order_id", _gts(id.orderId)));
                    Globals.getterList.add(new ParamsGetter("sort_by", ""));

                }}else if(orderRequestSettings==SettingActivity.ORDER_NUMBER) {
                Globals.getterList.add(new ParamsGetter("order_id", _gts(id.orderNo)));
                Globals.getterList.add(new ParamsGetter("sort_by", "orderno"));

            }else  {
                Globals.getterList.add(new ParamsGetter("order_id", _gts(id.trackingNumber)));
                Globals.getterList.add(new ParamsGetter("sort_by", "mediacode"));

            }
            new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList, true).execute();


            return false;
        }}


    void fixedRequest(int status) {
        Log.e(TAG, "FixedRequestttttt");
        if (status == ALL_CLEAR) {
            cProductList.put("processedCnt", "0");
            skipvalue = true;
            Globals.getterList = new ArrayList<>();


            Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());
            Globals.getterList.add(new ParamsGetter("admin_id", adminID));
            Globals.getterList.add(new ParamsGetter("order_id", _gts(id.orderId)));
            Globals.getterList.add(new ParamsGetter("resetAll", "true"));
            Globals.getterList.add(new ParamsGetter("mode", "shipping"));


            mRequestStatus = REQ_CLEAR;
            new MainAsyncTask(this, Globals.Webservice.fixedPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList, true).execute();

        }
        else {
            mNextBarcode = false;
            isNextBarcode ="";
            Log.e("SendRequestttttt","SEnddddddddddddddRRRRRRRRRRRRRRRRr");
            sendRequest(status);
        }
    }

    public void createNewPackItem() {
        boolean repeat = false;
        boolean repeatpack = false;

      /*  for (Map<String, String> map : mProductList) {
            Log.e(TAG, "DataAssigned    "+_gts(id.barcode));
            Log.e(TAG, "DataAssigned111111    "+map.get("barcode"));
            Log.e(TAG, "DataAssigned22222222    "+map.get("code"));


            if ((_gts(id.barcode).equals(map.get("barcode").trim()) || map.get("barcode").matches("(.*)" + _gts(id.barcode)))|| (_gts(id.barcode).equals(map.get("code").trim()))) {
//			if (_gts(id.barcode).equals(map.get("barcode"))){
                Log.e(TAG, "DataAssigned");
                mTarget = map;
                Log.e(TAG, "Target  " + mTarget);

            }
        }*/

        mTarget = cProductList;
        Log.e("NewShipActivityyyyyy", " createNewPackItemmmm ");
        Log.e("NewShipActivityyyyyy", " createNewPackItemmmm Target " + mTarget);
        int target = Integer.parseInt(_gts(id.quantity));
        int productqnt = Integer.parseInt(cProductList.get("quantity"));
        if (target <= productqnt) {
            if (packData.size() > 0) {
                String _b1 = mTarget.get("barcode");
                String _box1 = String.valueOf(mBoxNo);
                String _loc1 = mTarget.get("location");
                if ( GetPickingOrderGroup.track == 1 || GetPickingOrderGroup.group==1) {

                    Log.e(TAG, "mtarget  barcode " + _b1 + " box " + _box1);
                    for (Map<String, String> map : packData) {
                        String _b = map.get("barcode");
                        String _box = map.get("boxNo");
                        Log.e(TAG, "pack data  barcode " + _b + " box " + _box);
                        StringBuffer temp_qty = new StringBuffer();
                        if (_b.equals(_b1) && _box.equals(_box1)) {
                            String qnty = U.plusTo(map.get("quantity"), _gts(id.quantity));
                            Log.e(TAG, "map  quantity " + map.get("quantity"));
                            map.put("quantity", qnty);
                            repeat = true;
                        }
                    }
                }
                else {
                    Log.e(TAG, "mtarget  barcode " + _b1 + " box " + _box1 + " loc " + _loc1);
                    for (Map<String, String> map : packData) {
                        String _b = map.get("barcode");
                        String _box = map.get("boxNo");
                        String _loc = map.get("location");
                        Log.e(TAG, "pack data  barcode " + _b + " box " + _box + " loc " + _loc);
                        StringBuffer temp_qty = new StringBuffer();

                        if (_b.equals(_b1) && _loc.equals(_loc1) && _box.equals(_box1)) {
                            String qnty = U.plusTo(map.get("quantity"), _gts(id.quantity));
                            Log.e(TAG, "map  quantity " + map.get("quantity"));
                            map.put("quantity", qnty);
                            repeat = true;
                        }
                    }
                }
                for (Map<String, String> map : packBoxData) {
                    String _b = map.get("barcode");
                    String _box = map.get("boxNo");

                    Log.e(TAG, "pack data  barcode " + _b + " box " + _box );
                    StringBuffer temp_qty = new StringBuffer();

                    if (_b.equals(_b1) &&  _box.equals(_box1)) {

                        String qnty = U.plusTo(map.get("quantity"), _gts(id.quantity));
                        Log.e(TAG, "map  quantity " + map.get("quantity"));
                        map.put("quantity", qnty);
                        repeatpack = true;
                    }
                }
            }
            if (repeat == false) {
                if (GetPickingOrderGroup.track == 1 ||GetPickingOrderGroup.group==1) {
                    mPackItem = new HashMap<String, String>();
                    mPackItem.put("boxNo", String.valueOf(mBoxNo));
                    mPackItem.put("code", mTarget.get("code"));
                    mPackItem.put("barcode", mTarget.get("barcode"));
                    mPackItem.put("quantity", _gts(id.quantity).toString());
                    packData.add(mPackItem);
                } else {
                    mPackItem = new HashMap<String, String>();
                    mPackItem.put("boxNo", String.valueOf(mBoxNo));
                    mPackItem.put("code", mTarget.get("code"));
                    mPackItem.put("barcode", mTarget.get("barcode"));
                    mPackItem.put("location", mTarget.get("location"));
                    mPackItem.put("quantity", _gts(id.quantity).toString());
                    mPackItem.put("orderSubId", mTarget.get("orderSubId"));
                    packData.add(mPackItem);
                }
                Log.e("NewShipActivityyyyyy", " mpackItemm " + packData);

                if(!repeatpack){
                    mPackItem = new HashMap<String, String>();
                    mPackItem.put("boxNo", String.valueOf(mBoxNo));
                    mPackItem.put("code", mTarget.get("code"));
                    mPackItem.put("barcode", mTarget.get("barcode"));
                    mPackItem.put("quantity", _gts(id.quantity).toString());
                    packBoxData.add(mPackItem);
                }
            }
            is_scan = true;
        }

    }
    public void removePackItem() {
        for (Map<String, String> map : mProductList) {

            if ((_gts(id.barcode).equals(map.get("barcode").trim()) || map.get("barcode").matches("(.*)" + _gts(id.barcode))) || (_gts(id.barcode).equals(map.get("code").trim()))) {

                Log.e(TAG, "DataAssigned");
                mTarget = map;
                Log.e(TAG, "Target  " + mTarget);
            }
        }

        Log.e("NewShipActivityyyyyy", " removePackItemmmm Target " + mTarget);
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

    private void fixedRequest(int status, String nextBarcode) {

        mNextBarcode = true;
        isNextBarcode =nextBarcode;
        sendRequest(status);

    }
    public void sendRequest(int status)
    {

        cProductList.put("processedCnt",_gts(id.quantity));
        stopTimer();
        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));

        Globals.getterList.add(new ParamsGetter("order_id", _gts(R.id.orderId) ));
        Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));


        Globals.getterList.add(new ParamsGetter("mode", "shipping"));
        Globals.getterList.add(new ParamsGetter("getTrack","false"));
        Globals.getterList.add(new ParamsGetter("box_no","1"));
        Globals.getterList.add(new ParamsGetter("product_stock_history_id", cProductList.get("product_stock_history_id")));
        Log.e("SendRequestttttt","SEnddddddddddddddRRRRRRRRRr11111111122222222222");

        if(cProductList.get("barcode").equals(""))
            Globals.getterList.add(new ParamsGetter("barcode", cProductList.get("code")));
        else
            Globals.getterList.add(new ParamsGetter("barcode", cProductList.get("barcode")));

        Globals.getterList.add(new ParamsGetter("num", cProductList.get("processedCnt")));
        Globals.getterList.add(new ParamsGetter("inspection_status",status+""));
        mRequestStatus =FIXED_REQ;


        if (is_scan == false && !_gts(R.id.barcode).equals(""))
            createNewPackItem();

        if (GetPickingOrderGroup.group == 1) {
            Globals.getterList.add(new ParamsGetter("group_no",groupOrderno));
            Globals.getterList.add(new ParamsGetter("sort_by", "group"));

            if(serialPresent.equals("1")) {
                StringBuffer serial =new StringBuffer();
                for (String str: serialList) {
                    serial.append("\t").append(str);

                }

                Globals.getterList.add(new ParamsGetter("serial_no",serial.substring(1).toString()));
            }
//            Globals.getterList.add(new ParamsGetter("serial_no",serialno));
            new MainAsyncTask(this, Globals.Webservice.fixedPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList,true).execute();
        } else {
            Globals.getterList.add(new ParamsGetter("sort_by", ""));
            Globals.getterList.add(new ParamsGetter("row_no", cProductList.get("no")));
            if(serialPresent.equals("1")) {
                StringBuffer serial =new StringBuffer();
                for (String str: serialList) {
                    serial.append("\t").append(str);

                }

                Globals.getterList.add(new ParamsGetter("serial_no",serial.substring(1).toString()));
            }
            new MainAsyncTask(this, Globals.Webservice.fixedPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList,true).execute();
        }
    }
    public void nextWork() {
        Log.e(TAG, "nextworkkkkkk");

        cProductList.put("processedCnt","1");

        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking(_gts(id.quantity));
        _lastUpdateQty = _gts(id.quantity);

        setProc(PROC_QTY);

        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
            createNewPackItem();
            fixedRequest(COMPLETE_INSPECT);
        }
    }


    protected boolean showInfo() {
        Log.e("ShipActivityyyyyy"," showInfooooo");
        if(totalordercount==0)
            return false;

        Globals.getterList = new ArrayList<>();


        Log.e("SendLogs","shopidddddd  "+BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("mode", "shipping"));
        Globals.getterList.add(new ParamsGetter("order_details", "true"));

        mRequestStatus = REQ_ORDERDETAIL;


        if(orderRequestSettings==SettingActivity.ORDER_ID) {

            Globals.getterList.add(new ParamsGetter("order_id", _gts(R.id.orderId) ));
            Globals.getterList.add(new ParamsGetter("sort_by", "" ));

        }else if(orderRequestSettings==SettingActivity.ORDER_NUMBER) {
            Globals.getterList.add(new ParamsGetter("order_id", _gts(id.orderNo )));
            Globals.getterList.add(new ParamsGetter("sort_by", "orderno" ));

        }else  {
            Globals.getterList.add(new ParamsGetter("order_id", _gts(id.trackingNumber)));
            Globals.getterList.add(new ParamsGetter("sort_by", "mediacode" ));

        }
        new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList,true).execute();

        return true;

    }

    public void currLineData(Map<String, String> data) {
        Log.e(TAG, "currLineDataaaaaaaaaaa");
        cProductList = data;
        Log.e(TAG, "currLineDataaaaaaaaaaa  " + cProductList);
    }
    public String getTranactionId() {
        return tranactionId;
    }

    public void setTranactionId(String t) {
        tranactionId = t;
    }

    public void updateBadge1(String orderCount) {
        setBadge1(Integer.parseInt(orderCount));
        totalordercount=Integer.parseInt(orderCount);
    }

    public void updateBadge2(String qtyCount) {

        setBadge2(Integer.parseInt(qtyCount));
        inspection=Integer.valueOf(qtyCount);    }

    public void updateBadge3(String qtyCount) {

        leftcount = Integer.parseInt(qtyCount);
        if (packBoxData!= null)
            setBadge3(packBoxData.size());

    }

    private void updateBadge4() {
        int qtyBadge = 0;
        for (Map<String, String> map : packData) {
            String qtyCount = map.get("quantity");
            qtyBadge += Integer.valueOf(qtyCount);
        }
        Log.e(TAG, " UpdatePackBadgeeeee "+ qtyBadge +"  "+ ORDER_QTY_COUNT);
        if (qtyBadge <= ORDER_QTY_COUNT)
            setBadge4(qtyBadge);
    }

    public void setmTrackId(ArrayList<String> mTrackId) {
        Log.e(TAG, "setmTrackIddddd   " + mTrackId);
        this.mTrackId = mTrackId;
    }
    public void setPackBadge(long cnt) {
        setBadge3(cnt);
    }

    public void nextProcess1(){
        this._sts(id.trackingNumber, "");
        this._sts(id.barcode, "");
        this._sts(id.quantity, "");
        this._sts(id.totalQuantity, "");
        this._sts(id.quantityDetail, "");
        this._sts(id.orderId, "");
        this._sts(id.orderName, "");
        this._sts(id.location, "");
        this._sts(id.productCode, "");
        this._sts(id.productQuantity, "");
        this._sts(id.serialno ,"");

        /* Switch between orderId, orderNo, trackingNo */
        switch (orderRequestSettings) {
            case SettingActivity.ORDER_ID:
                this.setProc(PROC_ORDERID);
                break;
            case SettingActivity.ORDER_NUMBER:
                this._sts(id.orderNo, "");
                this.setProc(PROC_ORDER_NO);
                break;
            case SettingActivity.ORDER_TRACKING_NO:
                this.setProc(PROC_TRACKID);
                break;
        }
    }
    @Override
    public void nextProcess() {

        nextProcess1();
        this.setTranactionId("");
        orderID = "";
        this.setBadge1(0);
        this.setBadge2(0);
        this.setBadge3(0);
        orderId.setVisibility(View.VISIBLE);
        orderlabel.setVisibility(View.VISIBLE);
        GetPickingOrderGroup.track = 0;
        orderi="";
        nextbox=false;
        ordergot=false;
        groupOrderno="";
        mNextBarcode = false;
        isNextBarcode ="";
        serialno= "";
        serialPresent = "0";
        serialList.clear();

        clear = false;

        GetPickingOrderGroup.group=0;

        if (showKeyboard == false)
        {
            visible = false;
            numbrbtn.setText("キーボードを表示する");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
            hiddenPanel.setVisibility(View.INVISIBLE);
            mainlayout.setLayoutParams(params);
        }
        mTarget = null;
        FixedPikingNSGroup.empty = 0;
        GetPickingOrderGroup.gotbox = false;
        mBoxNo = 0;
        mProductList = null;
        resetPackData();
        addpackingList =BaseActivity.getPackingList();


        order.clear();
        orderSubid.clear();
        orderi = null;

        Globals.getterList = new ArrayList<>();
        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("initial_call", "yes"));

        mRequestStatus = REQ_INITIAL;
        new MainAsyncTask(this, Globals.Webservice.getPiking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList,true).execute();
    }

    public void resetPackData() {
        Log.e("ShipActivityyyyyy", " ResetPackDataaaa ");
        packData.clear();
        packBoxData.clear();
        ORDER_QTY_COUNT = 0;
    }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PACKING_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.hasExtra(FINISH)) {

                    if(BaseActivity.getRemarkPress()==true && !GetPickingOrderGroup.remark.equals(""))
                        showDialog(GetPickingOrderGroup.remark);

                    if(BaseActivity.getPackingList()==true){
                        printRequest();
                    }

                    else {
                        U.beepFinish(this, "終了");
                        nextProcess();
                    }
                } else if (data.hasExtra(NEXTBOX)) {
                    if (data.getStringExtra(NEXTBOX).equals(ADDNEXTBOX)) {
                        nextbox = true;
                        printRequest();
                        // Command to add next box
                        if (mProcNo == PROC_QTY) {
                            updateBadge4();
                            _sts(id.barcode, "");

                            if (GetPickingOrderGroup.track != 1)
                                _sts(id.location, "");

                            _sts(id.quantity, "");
                            _sts(id.productCode, "");
                            _sts(id.productQuantity, "");
                            setProc(PROC_BARCODE);
                        }
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("PACKING_RESPONSE", "Canceled");
            }
        }
    }

    @Override
    public void clearEvent() {
        if(BaseActivity.getPackingList() && packData.size()>0){
            new AlertDialog.Builder(this)
                    .setTitle("現在のBoxをパッキングリスト作成 ？")
                    .setPositiveButton("する", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clear =true;
                            printRequest();
                        }
                    })
                    .setNegativeButton("しない", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO cancel clear
                            packData.remove(mPackItem);
                            mTextToSpeak.startSpeaking("clear");
                            U.beepBigsound(NewShippingGroupActivity.this, null);
                            _sts(id.trackingNumber, "");
                            NewShippingGroupActivity.this.nextProcess();
                        }
                    })
                    .show();
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Clear？")
                    .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            packData.remove(mPackItem);
                            mTextToSpeak.startSpeaking("clear");
                            U.beepBigsound(NewShippingGroupActivity.this, null);
                            _sts(id.trackingNumber, "");
                            NewShippingGroupActivity.this.nextProcess();
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
    }

    public void printRequest() {
        if (nextbox == true) {
            if (BaseActivity.getPackingList() == true) {
                sendPrintRequest();
                Log.e("NewShipActivity", "sending Print request1");
            } else {
                sendFinalRequest();
                Log.e("NewShipActivity", "sending NOOOO Print request1");
            }
        } else {

            String Url = spDomain.getString("domain", null);
            if(Url.equals("https://air-logi-st.air-logi.com/service")|| Url.equals("https://api.air-logi.com/service") && (BaseActivity.getShopId().equals("1101") ||BaseActivity.getShopId().equals("1217")) ){
                if(BaseActivity.getPackingList() == true)
                    sendPrintRequest();
                else
                    sendFinalRequest();
                Log.e("NewShipActivity", "sending Print request1");
            }
            else {
                if(BaseActivity.getPackingList() == true)
                    sendPrintRequest();
                else
                    sendFinalRequest();
                Log.e("NewShipActivity", "sending NOOOO Print request1");
            }
        }
    }

    public void sendFinalRequest() {
        String orderid = cProductList.get("order_id");
        Log.e(TAG, " sendFinalRequesttttt  Order id " + orderid);

        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("order_id", orderid));
        Globals.getterList.add(new ParamsGetter("transaction_id", getTranactionId()));
        Globals.getterList.add(new ParamsGetter("getTrack", "false"));
        Globals.getterList.add(new ParamsGetter("single_box", "true"));
        Globals.getterList.add(new ParamsGetter("box_no", mBoxNo + ""));

        StringBuffer orderSubId = new StringBuffer();
        StringBuffer qty = new StringBuffer();
        StringBuffer barcode = new StringBuffer();
        for (Map<String, String> map : packData) {
            if (!map.get("quantity").equals("0")) {
                if(!map.get("barcode").equalsIgnoreCase(""))
                    barcode.append("\t").append(map.get("barcode"));
                else
                    barcode.append("\t").append(map.get("code"));
                orderSubId.append("\t").append(map.get("orderSubId"));
                qty.append("\t").append(map.get("quantity"));
            }
        }

        if (GetPickingOrderGroup.group == 1) {

            Globals.getterList.add(new ParamsGetter("group_no", groupOrderno));
            Globals.getterList.add(new ParamsGetter("group", "true"));
            Globals.getterList.add(new ParamsGetter("barcode", barcode.substring(1)));
            Globals.getterList.add(new ParamsGetter("qty", qty.substring(1)));
        }
        else {
            Globals.getterList.add(new ParamsGetter("order_sub_id", orderSubId.substring(1)));
            Globals.getterList.add(new ParamsGetter("qty", qty.substring(1)));
            Globals.getterList.add(new ParamsGetter("group_by_barcode", "true"));
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        mRequestStatus = REQ_ADDPACKING;
        new MainAsyncTask(this, Globals.Webservice.addPacking, 1, NewShippingGroupActivity.this, "Form", Globals.getterList, true).execute();
    }

    public void sendPrintRequest() {

        Log.e(TAG, " SendPrintRequestttt ");
        for (Map<String, String> map : packData) {
            box = map.get("boxNo");
        }

        Globals.getterList = new ArrayList<>();


        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());


        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("order_id", orderID));
        Globals.getterList.add(new ParamsGetter("single_box", "true"));
        Globals.getterList.add(new ParamsGetter("getTrack","false"));

        mRequestStatus = REQ_ADDPRINT;

        new MainAsyncTask(this, Globals.Webservice.addPrint, 1, NewShippingGroupActivity.this, "Form", Globals.getterList,true).execute();
    }


    @Override
    public void allclearEvent()
    {
        new AlertDialog.Builder(NewShippingGroupActivity.this)
                .setTitle("All Clear?")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e(TAG, "onCreateeeeeeeeeeeeeeee");
                        clearProduct();
                    }
                })
                .setNegativeButton("いいえ", null)
                .show();
    }

    private void clearProduct() {
        Log.e(TAG, "clearProduct");
        if (cProductList != null) {



            fixedRequest(ALL_CLEAR);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestarttttttttt");
        if (BaseActivity.getOrderInfoBy() == orderRequestSettings) {
            // Just restart from where we left
        } else {
            this.finish();
            Intent i = new Intent(this, NewShippingGroupActivity.class);
            startActivity(i);
        }
    }
    public void setProductList(List<Map<String, String>> data) {
        Log.e("ShipActivityyyyyy", " setProductListttt ");
        mProductList = data;
        findRepeat();
    }

    public void setOrderList(List<Map<String, String>> data) {
        Log.e("NewShipingActivityyyyyy", " setProductListttt ");
        mOrderList=null;
        mOrderList = data;}

    private void findRepeat() {
        Log.e("NewShippingActivity", " findRepeattttt");
        for (Map<String, String> map : mProductList) {
            String _b = map.get("barcode");
            String _l = null;
            if (GetPickingOrderGroup.track != 1 ||GetPickingOrderGroup.group!=1) {
                _l = map.get("location");

            }
            StringBuffer temp_qty = new StringBuffer();
            for (Map<String, String> map1 : mProductList) {
                String _b1 = map1.get("barcode");
                String _l1 = null;
                if (GetPickingOrderGroup.track == 1 ||GetPickingOrderGroup.group==1) {
                    if (_b.equals(_b1)) {
                        temp_qty.append(", ").append(map1.get("quantity"));
                        map.put("repeatQties", temp_qty.toString());
                    }
                } else {
                    _l1 = map1.get("location");
                    if (_b.equals(_b1) && !_l.equals(_l1)) {
                        temp_qty.append(", ").append(map1.get("quantity"));
                        map.put("repeatQties", temp_qty.toString());
                    }
                }
            }
        }
    }

    public boolean initiatePopup(List<Map<String, String>> data) {
        Log.e(TAG, " setOrdertListttt ");
        mOrderList = data;
        setPopupWindow(null);
        if (getPopupWindow() == null) {
            Log.e(TAG, " setOrdertListttt 11111111111");
            final PopupWindow popupWindow = new PopupWindow(this);
            // レイアウト設定
            View popupView=null;
            Log.e(TAG, " setOrdertListttt 222222222222");
            popupView = getLayoutInflater().inflate(R.layout.new_inspection_detail, null);

            popupWindow.setContentView(popupView);
            // 背景設定
            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));
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
        badge=3;
        initWorkList1(lv);


        // 画面中央に表示
        getPopupWindow().showAtLocation(findViewById(R.id.orderId), Gravity.CENTER, 0, 32);
        return false;
    }
    protected ListViewItems initWorkList1(ListView lv) {
        Log.e("NewShippingActivity", "initWorkList11111");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (int i = mOrderList.size() - 1; i >= 0; i--) {
            Map<String, String> row = mOrderList.get(i);

            if(badge==3)
            {
                data.add(data.newItem().add(id.shipping_wrk_ins_0, row.get("code")).add(R.id.shipping_wrk_ins_1, row.get("barcode"))
                        .add(R.id.shipping_wrk_ins_2, row.get("quantity"))
                        .add(R.id.shipping_wrk_ins_3, row.get("squantity"))
                );
            }
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.new_shipping_inspection);
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }

    public void setOrderbadge(List<Map<String, String>> data)
    {

        Log.e(TAG, " setOrdertListttt ");
        mOrderList = data;
        setPopupWindow(null);
        if (getPopupWindow() == null) {
            Log.e(TAG, " setOrdertListttt >>>>>>>>>>>");
            final PopupWindow popupWindow1 = new PopupWindow(this);
            // レイアウト設定
            View popupView=null;

            popupView = getLayoutInflater().inflate(R.layout.order_details, null);
            Log.e(TAG, " setOrdertListttt 1111111111");
            popupWindow1.setContentView(popupView);
            // 背景設定
            popupWindow1.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));
            // タップ時に他のViewでキャッチされないための設定
            popupWindow1.setOutsideTouchable(true);
            popupWindow1.setFocusable(true);

            // 表示サイズの設定 今回は幅300dp
            Log.e(TAG, " setOrdertListttt 2222222");
            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 324, getResources().getDisplayMetrics());
            popupWindow1.setWindowLayoutMode((int) width, WindowManager.LayoutParams.WRAP_CONTENT);
            popupWindow1.setWidth((int) width);
            popupWindow1.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            setPopupWindow(popupWindow1);
            Log.e(TAG, " setOrdertListttt 33333");
            ImageView close =(ImageView)getPopupWindow().getContentView().findViewById(id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow1.dismiss();
                }
            });

        }
        ListView lv = (ListView) getPopupWindow().getContentView().findViewById(id.workPicking);
        badge=2;
        initWorkList(lv);

        // 画面中央に表示
        getPopupWindow().showAtLocation(findViewById(R.id.orderId), Gravity.CENTER, 0, 32);
    }

    protected ListViewItems initWorkList(ListView lv) {
        Log.e("NewShippingActivity", "initWorkList     "+mOrderList);
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (int i = mOrderList.size() - 1; i >= 0; i--) {
            Map<String, String> row = mOrderList.get(i);


            if (badge == 2) {
                if(row.containsKey("barcode"))
                    data.add(data.newItem().add(R.id.wrk_ins_0, row.get("name2"))
                            .add(R.id.wrk_ins_1, row.get("barcode"))
                            .add(R.id.wrk_ins_2, row.get("order_no"))
                    );

                else
                    data.add(data.newItem().add(R.id.wrk_ins_0, row.get("name2"))
                            .add(R.id.wrk_ins_2, row.get("order_no"))
                    );

            }

        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.work_inspection){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TextView txt0 = (TextView)v.findViewById(R.id.txtlable_0);
                TextView txt1 = (TextView)v.findViewById(R.id.txtlable_1);
                TextView txt2 = (TextView)v.findViewById(R.id.txtlable_2);

                txt0.setText("送付先");
                txt1.setText("オーダー番号");
                txt2.setText("引当日時");

                return v;
            }
        };
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }

    @Override
    protected void onDestroy() {
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();
    }

    @Override
    public void skipEvent() {

    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_ORDERID:    // バーコード
                _sts(id.orderId, buff);
                break;
            case PROC_ORDER_NO:    // バーコード
                _sts(id.orderNo, buff);
                break;
            case PROC_TRACKID:    // tracking number
                _sts(id.trackingNumber, buff);
                break;
            case PROC_BARCODE:    // バーコード
                _sts(id.barcode, buff);
                break;
            case PROC_SERIAL_NO:    // バーコード
                _sts(id.serialno, buff);
                break;
            case PROC_QTY: // 数量
                _sts(id.quantity, buff);
                break;
        }
    }

    public void getRemarkvoice()
    {
        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking(REMARK);
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
                if (mProcNo == PROC_BARCODE ){

                    if ((BaseActivity.getUrl().equals("http://165.100.112.154/service") || BaseActivity.getUrl().equals("http://52.198.136.69/service") || BaseActivity.getUrl().equals("https://165.100.112.154/service")) && (BaseActivity.getShopId().equals("1139")) || BaseActivity.getShopId().equals("1242") ) {
                        if(barcode.length()>18)
                        {
                            String[] splitdata =barcode.split(",");
                            char first = splitdata[splitdata.length-1].charAt(0);

                            boolean hasUpperCase = matchString(first+"",".*[A-Z].*");

                            String finalbarcode=null;
                            if (hasUpperCase)
                            {
                                finalbarcode=splitdata[0]+"-"+first;
                            }
                            else {
                                boolean hasUpperCase1 = matchString(first + "",".*[0-9].*");

                                if (hasUpperCase1) {
                                    finalbarcode = splitdata[0];
                                }
                                else{
                                    char first1 = splitdata[splitdata.length-1].charAt(3);

                                    boolean hasUpperCase2 = matchString(first1+"",".*[A-Z].*");

                                    if (hasUpperCase2)
                                    {
                                        finalbarcode=splitdata[0]+"-"+first1;
                                    }
                                    else
                                    {
                                        first1 = splitdata[splitdata.length-1].charAt(4);

                                        hasUpperCase2 = matchString(first1+"",".*[A-Z].*");
                                        if(hasUpperCase2)
                                        {
                                            finalbarcode=splitdata[0]+"-"+first1;
                                        }
                                        else
                                            finalbarcode = splitdata[0];
                                    }
                                }
                            }
                            barcode= finalbarcode;
                        }}

                    _sts(id.barcode, barcode);
                }
                else if (mProcNo == PROC_ORDERID)
                    _sts(id.orderId, barcode);
                else if(mProcNo == PROC_SERIAL_NO)
                    _sts(id.serialno,barcode);

                else if (mProcNo == PROC_ORDER_NO)
                    _sts(id.orderNo, barcode);
                else if (mProcNo == PROC_TRACKID)
                {
                    _sts(id.trackingNumber, barcode);
                }
                if (mProcNo == PROC_QTY){
                    if ((BaseActivity.getUrl().equals("http://165.100.112.154/service") || BaseActivity.getUrl().equals("http://52.198.136.69/service") || BaseActivity.getUrl().equals("https://165.100.112.154/service")) && (BaseActivity.getShopId().equals("1139")) || BaseActivity.getShopId().equals("1242") ) {
                        if(barcode.length()>18){
                            String[] splitdata =barcode.split(",");
                            char first = splitdata[splitdata.length-1].charAt(0);

                            boolean hasUpperCase = matchString(first+"",".*[A-Z].*");

                            String finalbarcode=null;
                            if (hasUpperCase)
                            {
                                finalbarcode=splitdata[0]+"-"+first;
                            }
                            else {
                                boolean hasUpperCase1 = matchString(first + "",".*[0-9].*");

                                if (hasUpperCase1) {
                                    finalbarcode = splitdata[0];
                                    Log.e(TAG, " digit data becomes 11111" + finalbarcode);
                                }
                                else{
                                    char first1 = splitdata[splitdata.length-1].charAt(3);
                                    Log.e(TAG," first character1111  "+first1);

                                    boolean hasUpperCase2 = matchString(first1+"",".*[A-Z].*");

                                    if (hasUpperCase2)
                                    {
                                        finalbarcode=splitdata[0]+"-"+first1;
                                        Log.e(TAG," character  data becomes1111 "+finalbarcode);
                                    }
                                    else
                                    {
                                        first1 = splitdata[splitdata.length-1].charAt(4);
                                        Log.e(TAG," first character1111  "+first1);

                                        hasUpperCase2 = matchString(first1+"",".*[A-Z].*");
                                        if(hasUpperCase2)
                                        {
                                            finalbarcode=splitdata[0]+"-"+first1;
                                            Log.e(TAG," character  data becomes1111 "+finalbarcode);
                                        }
                                        else
                                            finalbarcode = splitdata[0];
                                    }
                                }
                            }
                            barcode= finalbarcode;
                        }
                        Log.e(TAG,"barcode111   "+barcode);
                    }}

                this.inputedEvent(barcode, true);
            } else{
                Toast.makeText(getApplicationContext(), "Please Wait", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void clearcall(){
        packData.remove(mPackItem);
        mTextToSpeak.startSpeaking("clear");
        U.beepBigsound(this, null);
        _sts(id.trackingNumber, "");
        nextProcess();
    }


    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                _sts(id.barcode, barcode);
                break;
            case PROC_QTY:
                if ("".equals(barcode)) {
                } else
                    _sts(id.quantity, barcode);
                break;
            case PROC_ORDERID:
                _sts(id.orderId, barcode);
                break;
            case PROC_SERIAL_NO:
                _sts(id.serialno, barcode);
                break;
            case PROC_ORDER_NO:
                _sts(id.orderNo, barcode);
                break;
            case PROC_TRACKID:
                _sts(id.trackingNumber, barcode);
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
            msg=map1.getStringOrNull("message");
            result1= map1.getJsonArrayOrNull("results");
            String code = map1.getStringOrNull("code");
            if (code == null) {
                Log.e(TAG,"CODEeee============Nulllll");
                CommonDialogs.customToast(getApplicationContext(),"Network error occured");

            }
            if ("0".equals(code) == true) {

                Log.e("SendLogs111",code+"  "+msg+"  "+result1);

                if(mRequestStatus == REQ_INITIAL)
                {
                    new ListOrdersCountNS().post(code,msg,result1, mHash,NewShippingGroupActivity.this);
                }
                else if(mRequestStatus == REQ_ORDERID)
                {
                    new GetPickingOrderGroup().post(code,msg,result1, mHash,NewShippingGroupActivity.this);
                }
                else if (mRequestStatus == REQ_BARCODE){
                    switch (orderRequestSettings) {
                        case SettingActivity.ORDER_NUMBER:
                            new GetPikingNSGroup().post(code, msg, result1, mHash, NewShippingGroupActivity.this);
                            break;
                        case SettingActivity.ORDER_ID:
                            if (GetPickingOrderGroup.group == 1)
                                new GetShipingTrackGroup().post(code, msg, result1, mHash, NewShippingGroupActivity.this);
                            else
                                new GetPikingNSGroup().post(code, msg, result1, mHash, NewShippingGroupActivity.this);
                            break;
                        case SettingActivity.ORDER_TRACKING_NO:
                            if (GetPickingOrderGroup.track == 1)
                                new GetShipingTrackGroup().post(code, msg, result1, mHash, NewShippingGroupActivity.this);
                            else
                                new GetPikingNSGroup().post(code, msg, result1, mHash, NewShippingGroupActivity.this);
                            break;
                    }
                }

                else if(mRequestStatus == REQ_CLEAR){
                    nextProcess();
                }
                else if(mRequestStatus == REQ_ORDERDETAIL){
                    new GetShippingOrderGroup().post(code,msg,result1, mHash,NewShippingGroupActivity.this);
                }
                else if(mRequestStatus == REQ_ADDPRINT){
                    if (result1.size() > 0) {
                        // collect all data from response
                        JsonHash row = (JsonHash) result1.get(0);
                        String box_no = row.getStringOrNull("box_no");
                        mBoxNo=Integer.parseInt(box_no);

                        Log.d("DEBUG", "Success");
                        packingRequest();
//                        count=0;
                    }
                }
                else if(mRequestStatus ==REQ_ALLORDERS_DETAIL)
                    new ShippingOrderDetailGroup().post(code,msg,result1, mHash,NewShippingGroupActivity.this);
                else if(mRequestStatus ==FIXED_REQ){
                    new FixedPikingNSGroup().post(code,msg,result1, mHash,NewShippingGroupActivity.this);
                }
                else if(mRequestStatus == REQ_ADDPACKING){
                    new AddPackingGroup().post(code,msg,result1, mHash,NewShippingGroupActivity.this);

                }

            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(NewShippingGroupActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
            else{
                if (mRequestStatus == REQ_BARCODE){
                    Log.e(TAG,"REQ_BARCODE >>>>>>> "+mRequestStatus);
                    errorDialog = true;
                    showErrorPopup();
                    U.beepError(this, null);
                }
                else
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

    public void showErrorPopup(){
        pDialog1 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog1.setCancelable(false);

        pDialog1.setTitleText("正しい商品を検品して下さい。");
        pDialog1.setConfirmText("Ok");
        pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Log.e(TAG, "<>>>>>>>><<<>>>");
                errorDialog = false;
                pDialog1.dismiss();
            }
        });
        pDialog1.show();
    }


    public void packingRequest(){
        sendFinalRequest();
    }
}
