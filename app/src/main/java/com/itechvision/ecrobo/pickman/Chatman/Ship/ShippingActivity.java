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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.AllOrderList;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.NewAddPacking;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.NewAddPrint;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.Orderdetail;
import com.itechvision.ecrobo.pickman.Models.NewShipping.Boxsize.SetBoxSizeResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.CheckBarcode.CheckBarcodeResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.CheckOrder.CheckOrderNoProductData;
import com.itechvision.ecrobo.pickman.Models.NewShipping.CheckOrder.CheckOrderNoProductResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.CheckOrder.CheckOrderResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.FixOrder.FixOrderResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.GetTotalOrders.GetOrderData;
import com.itechvision.ecrobo.pickman.Models.NewShipping.GetTotalOrders.GetOrderResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.NotScanProductShippingRequest;
import com.itechvision.ecrobo.pickman.Models.NewShipping.ShippingRequest;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.R.drawable;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.SharedPrefrences;
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
import okhttp3.ResponseBody;

public class ShippingActivity extends BaseActivity implements View.OnClickListener, DataManager.SetboxSizeCallback, DataManager.GetOrderNocallback, DataManager.CheckBarcodeCallback, DataManager.GetOrderCallback, DataManager.GetOrderNoProductCallback, DataManager.FixOrderCallback, MainAsynListener {

    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(id.actionbar)
    ActionBar actionbar;
    @BindView(id.box_no)
    EditText box_no;
    Dialog dialog1;
    protected int mProcNo = 0;
    public static int inspectionNo = 0;
    public static int mBoxNo = 0;
    public boolean clear = false;
    private List<String> mTrackId = new ArrayList<>();
    private Button skipBtn, allclear;
    private ScrollView svMain;
    public TextToSpeak mTextToSpeak;
    private boolean showKeyboard;
    public TextView productName;
    private Button numbrbtn;
    private boolean visible = false;
    protected RelativeLayout layout;
    protected RelativeLayout mainlayout;
    public Context mcontext = this;
    public boolean getOrderDetail = false;
    private int orderRequestSettings;
    ECRApplication app = new ECRApplication();
    public String adminID = "", warehouse = "", domainname = "", ID = "",NPID="",NP_Shop_ID="",
            Key = "", BarcodeScanvalue = "0", PName = "", ProductId = "", getIncrease = "", PayStatus = "";
    public static Map<String, String> mTarget = null;
    List<Map<String, String>> productsList = new ArrayList<>();
    List<Map<String, String>> ordersList = new ArrayList<>();
    public static List<Map<String, String>> category0List = new ArrayList<>();
    public static List<Map<String, String>> category1List = new ArrayList<>();
    protected Map<String, String> mPackItem = new HashMap<String, String>();
    public static List<Map<String, String>> packData = new ArrayList<Map<String, String>>();
    public static List<Map<String, String>> packBoxData = new ArrayList<Map<String, String>>();
    public static String includenotinspected = "0";
    public static final int PROC_ORDERID = 1;
    public static final int PROC_ORDER_NO = 2;
    public static final int PROC_TRACKID = 3;
    public static final int PROC_BARCODE = 4;
    public static final int PROC_QTY = 5;
    public static final int PROC_BOX = 6;
    public static final int PROC_ORDER_NOSCAN = 7;
    public static final int PROC_SERIAL_NO = 8;
    public static final int COMPLETE_INSPECT = 1;
    public static final int INCOMPLETE_INSPECT = 2;

    public long ORDER_QTY_COUNT = 0;
    public String boxSize = "";
    public Map<String, String> cProductList = null;
    //  protected List<Map<String, String>> mProductList = null;
    private boolean is_scan = false;
    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPrefs";
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public static final int PACKING_ACTIVITY = 111;
    private static final int CLEAR = 1;
    SweetAlertDialog pDialog1;
    public boolean errorDialog = false;
    progresBar progress;
    String box = "";
    public boolean send = false;
    PopupWindow popup2Window, popupWindow;
    public boolean mNextBarcode = false;
    public String isNextBarcode = "";
    private String TAG = ShippingActivity.class.getSimpleName();
    public static int mRequestStatus = 0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_ORDERID = 2;
    public static final int REQ_BARCODE = 3;
    public static final int FIXED_REQ = 4;
    public static final int REQ_ORDERDETAIL = 5;
    public static final int REQ_ALLORDERS_DETAIL = 6;
    public static final int REQ_ALLCLEAR = 7;
    public static final int REQ_ADDPRINT = 8;
    public static final int REQ_ADDPACKING = 9;
    public static final int REQ_SENDPRINTER = 10;
    public static final int REQ_BOX = 11;

    public String _lastUpdateQty = "0";
    public boolean nextbox = false;
    public boolean packing = false, complete = false;
    String barcode = "";
    DataManager manager;
    DataManager.GetOrderNocallback getorder;
    DataManager.GetOrderCallback checkorder;
    DataManager.GetOrderNoProductCallback checkordernoProduct;
    DataManager.CheckBarcodeCallback checkobarcode;
    DataManager.FixOrderCallback fixOrder;
    DataManager.SetboxSizeCallback setboxSize;
    ArrayList<GetOrderData> arr;
    ArrayList<CheckOrderNoProductData> arrayData;
    static ShippingActivity shippingActivity;

    @BindView(id.gridserial)
    LinearLayout serialgrid;
    public String serialPresent = "0", SerialNO = "";
    String serialNo = "";
    public List<String> serialList = new ArrayList<>();
    private boolean fromqty = false;

    String temporderId="", tempsortby = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderRequestSettings = BaseActivity.getOrderInfoBy();
        if (orderRequestSettings == SettingActivity.ORDER_ID)
            setContentView(R.layout.activity_shipping);
        else if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
            setContentView(R.layout.activity_shipping_order);
        else if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO)
            setContentView(R.layout.activity_shipping_trackingno);

        ButterKnife.bind(ShippingActivity.this);
        getIDs();
        ViewPager viewPager = (ViewPager) findViewById(id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        productName = (TextView) findViewById(id.productName);

        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);

        adminID = spDomain.getString("admin_id", null);
        ID = BaseActivity.getShopId();

        getorder = this;
        checkorder = this;
        checkobarcode = this;
        fixOrder = this;
        setboxSize = this;
        checkordernoProduct = this;

        svMain = (ScrollView) _g(id.scrollMain);
        dialog1 = new Dialog(ShippingActivity.this);

        mTextToSpeak = new TextToSpeak(this);
        showKeyboard = sharedPreferences.getBoolean("ShowKeyboard", false);

        numbrbtn = (Button) _g(id.add_layout);
        if (showKeyboard == true) {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
            visible = true;
            numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            layout = (RelativeLayout) findViewById(id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layout.getLayoutParams();
            param.setMargins(0, top, 0, 0);

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }
        _gt(id.totalQuantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        _gt(id.productCode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

        productName.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

        if (!packData.isEmpty()) {
            packData.clear();
            packBoxData.clear();
        }

        progress.Show();
        ShippingRequest req = new ShippingRequest(app.getSerial(), adminID, ID, "", "", "", "", "", "", "", "", "", "", "", "", "", "", getResources().getString(R.string.version), "");
        manager.GetOrderNo(req, getorder);

        if (mProcNo == 0) {
            switch (orderRequestSettings) {
                case SettingActivity.ORDER_ID:

                    this.setProc(PROC_ORDERID);
                    break;
                case SettingActivity.ORDER_NUMBER:

                    this.setProc(PROC_ORDER_NO);
                    break;
                case SettingActivity.ORDER_TRACKING_NO:

                    this.setProc(PROC_TRACKID);
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPrefrences.get_ShipOrderNumber(ShippingActivity.this).equalsIgnoreCase("1")) {
            _g(id.ll_orderno_settings).setVisibility(View.VISIBLE);
            // _g(id.ll_orderno_settings).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        } else {
            _g(id.ll_orderno_settings).setVisibility(View.GONE);
        }
    }

    public void restart() {
        this.finish();
        Intent i = new Intent(this, ShippingActivity.class);
        startActivity(i);
    }

    @OnClick(id.enter)
    void enter() {
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
    }

    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub
        actionbarImplement(this, "レジ検品", " ",
                0, true, true, true, true);
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnYellow.setOnClickListener(this);

        progress = new progresBar(this);
        manager = new DataManager();
        arr = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case id.relLayout1:
                menu.showMenu();
                break;
            case id.notif_count_yellow:
                if (BaseActivity.getPackingList())
                    startPackingBoxActivity();
                break;
            case id.notif_count_red:
                if (BaseActivity.getsinclude())
                    showPopup2();
                break;
            case id.notif_count_blue:
                if (mProcNo >= PROC_BARCODE) {
                    if (mProcNo == PROC_QTY) {
                        cProductList.put("processedCnt", _gts(id.quantity));
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity")))
                            fixedRequest(COMPLETE_INSPECT);
                        else fixedRequest(INCOMPLETE_INSPECT);
                        getOrderDetail = true;
                    } else
                        getDetail();
                }
                break;
            case id.notif_count_green:
                getOrders();
                break;

            default:
                break;
        }
    }

    public void printRequest() {
        if (nextbox == true) {
            if (BaseActivity.getPackingList() == true) {
                sendPrintRequest();
            } else {
                sendFinalRequest();
            }
        } else {

            String Url = spDomain.getString("domain", null);
            if (Url.equals("https://air-logi-st.air-logi.com/service") || Url.equals("https://api.air-logi.com/service") && (BaseActivity.getShopId().equals("1101") || BaseActivity.getShopId().equals("1217"))) {
                if (BaseActivity.getPackingList() == true)
                    sendPrintRequest();
                else
                    sendFinalRequest();
            } else {
                if (BaseActivity.getPackingList() == true)
                    sendPrintRequest();
                else
                    sendFinalRequest();
            }
        }
    }

    public String getPrintableBox() {
        String boxes = "";

        if (packData.size() > 0)
            for (Map<String, String> map : packData) {
                boxes = "," + map.get("boxNo");
            }
        if (boxes.length() > 0)
            boxes = boxes.substring(1);
        return boxes;
    }

    public static ShippingActivity getInstance() {
        return shippingActivity;
    }

    public void sendPrintRequest() {
        String printBoxNo = getPrintableBox();

        for (Map<String, String> map : packData) {
            box = map.get("boxNo");
        }

        String order = cProductList.get("order_id");

        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("order_id", order));
        Globals.getterList.add(new ParamsGetter("single_box", "true"));
        Globals.getterList.add(new ParamsGetter("getTrack", "false"));

        mRequestStatus = REQ_ADDPRINT;
        new MainAsyncTask(this, Globals.Webservice.addPrint, 1, ShippingActivity.this, "Form", Globals.getterList, true).execute();

    }

    public void sendFinalRequest() {

        String orderid = cProductList.get("order_id");

        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("order_id", orderid));
        Globals.getterList.add(new ParamsGetter("transaction_id", ""));
        Globals.getterList.add(new ParamsGetter("getTrack", "false"));
        Globals.getterList.add(new ParamsGetter("single_box", "true"));
        Globals.getterList.add(new ParamsGetter("box_no", mBoxNo + ""));

        StringBuffer orderSubId = new StringBuffer();
        StringBuffer qty = new StringBuffer();
        StringBuffer barcode = new StringBuffer();
        for (Map<String, String> map : packData) {
            if (!map.get("quantity").equals("0")) {
                orderSubId.append("\t").append(map.get("order_sub_id"));
                qty.append("\t").append(map.get("quantity"));
            }
        }

        Globals.getterList.add(new ParamsGetter("order_sub_id", orderSubId.substring(1)));
        Globals.getterList.add(new ParamsGetter("qty", qty.substring(1)));
        Globals.getterList.add(new ParamsGetter("group_by_barcode", "true"));

        mRequestStatus = REQ_ADDPACKING;
        new MainAsyncTask(this, Globals.Webservice.addPacking, 1, ShippingActivity.this, "Form", Globals.getterList, true).execute();


    }

    public void getOrders() {

        Globals.getterList = new ArrayList<>();
        adminID = spDomain.getString("admin_id", null);
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("get_orders", "true"));

        mRequestStatus = REQ_ALLORDERS_DETAIL;
        new MainAsyncTask(this, Globals.Webservice.Getshipping, 1, ShippingActivity.this, "Form", Globals.getterList, true).execute();

    }

    public void setOrdersList(List<Map<String, String>> list) {
        ordersList = list;
        showInfo();
    }

    @Override
    protected boolean showPopup2() {
        if (category1List.size() < 1)
            return true;
        else {

            if (mProcNo == PROC_QTY) {
                cProductList.put("processedCnt", _gts(id.quantity));
                if (cProductList.get("processedCnt").equals(cProductList.get("quantity")))
                    fixedRequest(COMPLETE_INSPECT);
                else fixedRequest(INCOMPLETE_INSPECT);
            }
            gotoIncludeScreen();

            return false;
        }

    }

    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
        if (visible == false) {

            visible = true;
            numbrbtn.setText(R.string.hideKeyboard);
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            layout = (RelativeLayout) findViewById(id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layout.getLayoutParams();
            param.setMargins(0, top, 0, 0);

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;
            numbrbtn.setText(R.string.showkeyboard);
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);

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

    @Override
    protected void onDestroy() {
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (BaseActivity.getOrderInfoBy() == orderRequestSettings) {
            // Just restart from where we left
        } else {
            this.finish();
            Intent i = new Intent(this, ShippingActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        // TODO not backed from picking activity
        //super.onBackPressed();
    }


    public void setProc(int procNo) {
        mBuff.delete(0, mBuff.length());
        mProcNo = procNo;
        switch (procNo) {
            case PROC_ORDERID:
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.box_no).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderno_settings).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderId).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(id.orderId));
                break;
            case PROC_TRACKID:
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
                    _gt(id.orderNo).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.box_no).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderno_settings).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(id.trackingNumber));
                break;

            case PROC_ORDER_NO:
                _gt(id.orderNo).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.box_no).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderno_settings).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderNo).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(id.orderNo));
                break;

            case PROC_BARCODE:
                isNextBarcode = "";
                mNextBarcode = false;
                is_scan = false;
                if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
                    _gt(id.orderNo).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.box_no).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderno_settings).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setFocusableInTouchMode(true);

                mTextToSpeak.startSpeaking("barcode");
                scrollToView(svMain, _g(id.barcode));
                break;

            case PROC_QTY:
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.box_no).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderno_settings).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
                    _gt(id.orderNo).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                if (_gts(id.quantity).equals("")) _sts(id.quantity, "1");
                _gt(id.quantity).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(id.quantity));
                break;

            case PROC_ORDER_NOSCAN:
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.box_no).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderno_settings).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderno_settings).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(id.orderno_settings));
                break;


            case PROC_BOX:
                mTextToSpeak.startSpeaking("box size");
                if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
                    _gt(id.orderNo).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.box_no).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.orderno_settings).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.box_no).setFocusableInTouchMode(true);
                break;

            case PROC_SERIAL_NO:

                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.serialno).setFocusableInTouchMode(true);
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.box_no).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.orderno_settings).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                mTextToSpeak.startSpeaking("シリアル番号");
                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {

        switch (mProcNo) {

            case PROC_ORDERID:    // 注文ID
                /* Conditional set next process whether orderId or trackingNo or orderNo. */

                String orderId = _gts(id.orderId);
                if ("".equals(orderId)) {
                    U.beepError(this, "注文IDは必須です");
                    _gt(id.orderId).setFocusableInTouchMode(true);
                    _gt(id.orderId).setFocusableInTouchMode(true);
                    break;
                }

                GetOrderRequest(orderId, "","");

                break;

            case PROC_ORDER_NO:    // 注文ID
                /* Conditional set next process whether orderId or trackingNo or orderNo. */
                if (orderRequestSettings == SettingActivity.ORDER_NUMBER) {

                    String orderNo = _gts(id.orderNo);
                    if ("".equals(orderNo)) {
                        U.beepError(this, "注文IDは必須です");
                        _gt(
                                id.orderNo).setFocusableInTouchMode(true);
                        break;
                    }

                    GetOrderRequest(orderNo, "orderno","");

                }
                break;
            case PROC_TRACKID:    // tracking number
                /* Conditional set next process whether orderId or trackingNo or orderNo.*/
                if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO) {

                    String trackingNo = _gts(id.trackingNumber);
                    if ("".equals(trackingNo)) {
                        U.beepError(this, "トラック#は必須です");
                        _gt(id.trackingNumber).setFocusableInTouchMode(true);
                        break;
                    }

                    GetOrderRequest(trackingNo, "mediacode","");

                } else {

                    String trackingNo = _gts(id.trackingNumber);
                    if ("".equals(trackingNo)) {
                        U.beepError(this, "トラック#は必須です");
                        _gt(id.trackingNumber).setFocusableInTouchMode(true);
                        break;
                    }
                    String track = trackingNo.replaceFirst("^0+(?!$)", "");
                    boolean match = false;
                    for (String tracking : mTrackId) {
                        String mtrack = tracking.replaceFirst("^0+(?!$)", "");
                        if (!mtrack.equals(track)) {
                            match = false;
                        } else {
                            match = true;
                            break;
                        }
                    }
                    if (!match) {
                        U.beepError(this, "Tracking number not matched to corresponding information");
                        _gt(id.trackingNumber).setFocusableInTouchMode(true);
                        break;
                    }

                    if (!BaseActivity.getscanProduct()) {
                        RequestfornoScan(trackingNo, "mediacode");
                    } else {
                        if (category0List.isEmpty() && BaseActivity.getsinclude())
                            gotoIncludeScreen();
                        else
                            setProc(PROC_BARCODE);
                    }
                }
                break;
            case PROC_BARCODE:    // バーコード
                String barcode = _gts(id.barcode);
                if (dialog1.isShowing()) {
                } else {

                    if (errorDialog) {
                        U.beepError(this, null);
                        break;
                    }

                    if ("".equals(barcode)) {
                        U.beepError(this, "バーコードは必須です");
                        _gt(id.barcode).setFocusableInTouchMode(true);
                        break;
                    }
                    else if(barcode.length()>14 && ID.equalsIgnoreCase("1646")){
                        U.beepError(this,null);
                        break;
                    }

                    progress.Show();
                    ShippingRequest req = new ShippingRequest(app.getSerial(), adminID, ID, _gts(id.orderId), barcode, "0", "", "", "", "", "", "", "", "", "", "", "", getResources().getString(R.string.version), "");
                    manager.CheckBarcode(req, checkobarcode);

                    _lastUpdateQty = _gts(id.quantity);
                }
                break;

            case PROC_ORDER_NOSCAN:    // バーコード
                String Ordernumber = _gts(id.orderno_settings);

                if ("".equals(Ordernumber)) {
                    U.beepError(this, "バーコードは必須です");
                    _gt(id.orderno_settings).setFocusableInTouchMode(true);

                    break;
                }

                if (Ordernumber.equalsIgnoreCase(cProductList.get("order_no"))||Ordernumber.equalsIgnoreCase(NPID)||Ordernumber.equalsIgnoreCase(NP_Shop_ID)) {
                    checkProc();
                } else {
                    _gt(id.orderno_settings).setFocusableInTouchMode(true);
                    U.beepError(this, "正しい番号をスキャンしてください。");
                }

                break;
            case PROC_QTY:
                String qty = _gts(id.quantity);
                String barcode1 = _gts(id.barcode);

                if (serialPresent.equals("1"))
                    serialNo = _gts(id.serialno);

                if (isScaned) {

                   /* if (buff.equals(barcode1)) {
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
                                _lastUpdateQty = _gts(id.quantity);

                            *//* check if update in quantity need next action *//*
                            if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                                fixedRequest(COMPLETE_INSPECT);
                             }
                        }
                    } else {
                        fixedRequest(INCOMPLETE_INSPECT, buff);
                    }*/


                    if (serialPresent.equals("1")) {

                        if (buff.equals(barcode1) || buff.equals(serialNo)) {
                            if (buff.equals(barcode1) && !serialNo.equals("")) { /*if barcode2 is not empty then store barcode1 and wait till second barcode*/
                                setBarcodeTemp(buff);
                                fromqty = true;
                                if (serialPresent.equals("1"))
                                    setProc(PROC_SERIAL_NO);/*store barcode1 in static variable*/
                                break;
                            }
                            if (buff.equals(serialNo) && !barcode1.equals("")) {
                                if (!(getBarcodeTemp().concat(buff)).equals((barcode1.concat(serialNo)))) {
//                                            fromqty =true;
//                                            setProc(PROC_SERIAL_NO);
//                                            U.beepError(this, "Scanned barcode and serialno. not matched");
                                    break;
                                }
                            }
                            if (((getBarcodeTemp().concat(buff)).equals(barcode1.concat(serialNo)))) {
                                setBarcodeTemp("");
                                serialList.add(_gts(id.serialno));
                                Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned");
                                qty = U.plusTo(qty, "1");

                                Log.e(TAG, "processed count    " + cProductList.get("processedCnt"));
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
                        } else {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned3333");
                            //  U.beepError(this, "Barcode dont match");
                            fixedRequest(INCOMPLETE_INSPECT,buff);
                        }

                    } else {
                        if (buff.equals(barcode1)) {

                            qty = U.plusTo(qty, "1");
                            String processedCnt = cProductList.get("processedCnt");
                            cProductList.put("processedCnt", qty);
                            _sts(id.quantity, qty);
                            if (Integer.parseInt(qty) > 1)
                                mTextToSpeak.startSpeaking(qty);
                            _lastUpdateQty = _gts(id.quantity);

                            /* check if update in quantity need next action */
                            if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                                fixedRequest(COMPLETE_INSPECT);
                            }
                        } else {
                            fixedRequest(INCOMPLETE_INSPECT,buff);
                            //    U.beepError(this, "Barcode dont match");
                            //   Toast.makeText(getApplicationContext(), "BarCode Doesn't Match", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    if ("".equals(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                    String processedCnt = cProductList.get("processedCnt");
                    String qtyUpdate = U.minusTo(qty, _lastUpdateQty);
                    String maxQty_ = U.minusTo(cProductList.get("quantity"), processedCnt);

                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());

                        _sts(id.quantity, "1");

                        cProductList.put("processedCnt", "1");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        U.beepError(this, "数量が多すぎます");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        _sts(id.quantity, "1");

                        cProductList.put("processedCnt", "1");
                        break;
                    } else {
                        if (Integer.parseInt(qty) > 1)
                            mTextToSpeak.startSpeaking(_gts(id.quantity));

                        cProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));

                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {

                            fixedRequest(COMPLETE_INSPECT);
                        } else {
                            fixedRequest(INCOMPLETE_INSPECT);
                        }
                    }
                }
                break;
            case PROC_BOX:
                if (box_no.getText().toString().length() > 3) {
                    U.beepError(this, "桁数は3桁までです。");
                    _gt(id.box_no).setFocusableInTouchMode(true);
                    break;
                } else {

                    String box = _gts(id.box_no);


                    if ("".equals(box)) {
                        U.beepError(this, "数量は必須です");
                        _gt(id.box_no).setFocusableInTouchMode(true);
                        break;
                    }

                    if (boxSize.equals(""))
                        boxSize = box;
                    else boxSize = boxSize + "," + box;

                    _sts(id.box_no, boxSize);

                    String processedCnt = cProductList.get("Koguchi_processed_count");

                    cProductList.put("Koguchi_processed_count", U.plusTo(processedCnt, "1"));
                    //increase qunatity in mpackdata
                    if (Integer.parseInt(cProductList.get("Koguchi_processed_count")) <= Integer.parseInt(cProductList.get("koguchi"))) {

                        if (Integer.parseInt(cProductList.get("Koguchi_processed_count")) > 1)
                            mTextToSpeak.startSpeaking(cProductList.get("Koguchi_processed_count"));

                        /* check if update in quantity need next action */
                        if (cProductList.get("Koguchi_processed_count").equals(cProductList.get("koguchi"))) {
                            sendBoxSize();
                        }
                    }
                }
                break;

            case PROC_SERIAL_NO:
                String barcodee = _gts(id.barcode);

                if (_gts(id.serialno).equals("")) {
                    U.beepError(ShippingActivity.this, null);
                    break;
                }
                if (_gts(id.serialno).equals(barcodee)) {
                    U.beepError(ShippingActivity.this, null);
                    break;
                } else {
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
                    } else {
                        String qty1 = _gts(id.quantity);
                        serialList.add(_gts(id.serialno));
                        setProc(PROC_QTY);
                        qty1 = U.plusTo(qty1, "1");
                        String processedCnt = cProductList.get("processedCnt");
                        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                        _sts(id.quantity, qty1);
                        fromqty = false;
                        if (Integer.parseInt(qty1) > 1)
                            mTextToSpeak.startSpeaking(qty1);
                        _lastUpdateQty = _gts(id.quantity);

                        /* check if update in quantity need next action */
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned222222");
                            fixedRequest(COMPLETE_INSPECT);
                        }
                    }
                }
                break;
        }
    }

    public void call() {
        Log.e(TAG, "DataRemoved>>>>>>>> " + mTarget);
        removeData(1);
    }

    public void removeData(int status) {
        for (Map<String, String> map : category0List) {
            String _b = mTarget.get("barcode");
            String _l = mTarget.get("location");
            String _b1 = map.get("barcode");
            String _l1 = map.get("location");
            if ((_b.equals(_b1.trim()) || _b1.matches("(.*)" + _b)) || (_b.equals(map.get("code").trim()))) {
                if (_l.equals(_l1)) {
                    Log.e(TAG, "DataAssigned " + _b + "    " + _b1);
                    if (status == COMPLETE_INSPECT) {
                        category0List.remove(map);
                        Log.e(TAG, "DataRemoved>>>>>>>> " + map);
                        break;
                    } else if (status == INCOMPLETE_INSPECT) {
                        String qnty = U.minusTo(mTarget.get("quantity"), mTarget.get("processedCnt"));
                        Log.e(TAG, "Quantitiess  " + mTarget.get("quantity") + "    " + mTarget.get("processedCnt") + "<<<<" + qnty);
                        if (qnty.equals("0"))
                            category0List.remove(map);
                        else {
                            map.put("quantity", qnty);
                            Log.e(TAG, "map  quantity " + map);
                        }
                        break;
                    }

                }

            }
        }

        if (category0List.size() == 0 && BaseActivity.getsinclude() && category1List.size() != 0) {
            gotoIncludeScreen();

        } else if (category0List.size() == 0 && !BaseActivity.getsinclude() && category1List.size() == 0) {
            U.beepKakutei(this, null);
//           mTextToSpeak.startSpeaking("finish");
            nextProcess();
        }

    }

    public void gotoIncludeScreen() {
        U.beepKakutei(this, null);

        Intent packingIntent = new Intent(this, IncludeShippingActivity.class);
        packingIntent.putExtra("orderId", _gts(id.orderId));
        packingIntent.putExtra("koguchi", cProductList.get("koguchi"));
        packingIntent.putExtra("shipping_method", cProductList.get("shipping_method").trim().toString());

        startActivityForResult(packingIntent, 2);
    }

    void sendBoxSize() {

        String airprint_printer = "";
        String selectedPrinter = "";
        String shipflag = "";
        String checkdb = "0";

        if (BaseActivity.getPrinterSelected() && !checkPrinterSelect()) {
            airprint_printer = BaseActivity.getintegratedselectedPrinterID();
            selectedPrinter = BaseActivity.getCsvselectedPrinterID();
        } else
            checkdb = "1";

        if (BaseActivity.getShippingflag())
            shipflag = "true";

        progress.Show();
        ShippingRequest req = new ShippingRequest(app.getSerial(), adminID, ID, cProductList.get("order_id"), "", "",
                "", "", "", "", "", "", "", "", shipflag, "true", boxSize, "true", checkdb, airprint_printer, selectedPrinter, getResources().getString(R.string.version));
        manager.SetboxSize(req, setboxSize);

    }

    void fixedRequest(int status) {

        sendRequest(status);
    }

    private void fixedRequest(int status, String nextBarcode) {

        mNextBarcode = true;
        isNextBarcode = nextBarcode;
        sendRequest(status);

    }

    public void sendRequest(int status) {

        stopTimer();
        if (is_scan == false && !_gts(id.barcode).equals(""))
            createNewPackItem();
        mTarget = null;
        inspectionNo = status;
        mTarget = cProductList;

        String barc = "";
        if (cProductList.get("barcode").equals(""))
            barc = cProductList.get("code");
        else
            barc = cProductList.get("barcode");

        String qty = cProductList.get("processedCnt");
        String shipping_flag = "";
        String box_flag = "";

        if (BaseActivity.getShippingflag())
            shipping_flag = "true";

        //if boxsize selected the update the packed and shipped status after box size is send
        if (BaseActivity.getBoxNo())
            box_flag = "true";


        String airprint_printer = "";
        String selectedPrinter = "";
        String auto_complete = "";


        if (BaseActivity.getPrinterSelected()) {
            airprint_printer = BaseActivity.getintegratedselectedPrinterID();
            selectedPrinter = BaseActivity.getCsvselectedPrinterID();
        }

        if (BaseActivity.getsinclude())
            auto_complete = "";
        else
            auto_complete = "true";

        //   serial_no

        if (serialPresent.equals("1") && serialList.size() > 0) {
            StringBuffer serial_list = new StringBuffer();
            for (String str : serialList) {
                serial_list.append(",").append(str);
            }

            SerialNO = serial_list.substring(1).toString();
            //Globals.getterList.add(new ParamsGetter("serial_no",serial_list.substring(1).toString()));
        } else {
            SerialNO = "";
        }


        progress.Show();
        ShippingRequest req = new ShippingRequest(app.getSerial(), adminID, ID, _gts(id.orderId), barc, "0", "", "", cProductList.get("product_stock_history_id"), qty, timeTaken().toString(), status + "", auto_complete, box_flag, shipping_flag, airprint_printer, selectedPrinter, getResources().getString(R.string.version), SerialNO);
        manager.FixOrder(req, fixOrder);

    }


    public void setmTrackId(ArrayList<String> mTrackId) {
        this.mTrackId = mTrackId;
    }

    public void nextWork() {

        if (serialPresent.equals("0")) {
            String processedCnt = cProductList.get("processedCnt");
            cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
            setProc(PROC_QTY);
            mTextToSpeak.resetQueue();
            mTextToSpeak.startSpeaking(_gts(id.quantity));
            _lastUpdateQty = _gts(id.quantity);

            if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {

                createNewPackItem();
                fixedRequest(COMPLETE_INSPECT);
                mTarget = null;
                mTarget = cProductList;
                inspectionNo = COMPLETE_INSPECT;

            }

        }
        //if serial seelected on API
        else {
            if (serialPresent.equals("1"))
                setProc(PROC_SERIAL_NO);
            else {
                String processedCnt = cProductList.get("processedCnt");
                cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                setProc(PROC_QTY);
                mTextToSpeak.resetQueue();
                mTextToSpeak.startSpeaking(_gts(id.quantity));
                _lastUpdateQty = _gts(id.quantity);

            }
           /* if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                if (serialPresent.equals("1")){

                }else {
                    createNewPackItem();
                    fixedRequest(COMPLETE_INSPECT);
                    mTarget = null;
                    mTarget = cProductList;
                    inspectionNo = COMPLETE_INSPECT;
                }
            }*/
        }


    }

    public void GetOrderRequest(String orderId, String sortby, String reship) {
        String airprint_printer = "";
        String selectedPrinter = "";


        if (BaseActivity.getPrinterSelected()) {
            airprint_printer = BaseActivity.getintegratedselectedPrinterID();
            selectedPrinter = BaseActivity.getCsvselectedPrinterID();
        }

        temporderId = orderId;
        tempsortby = sortby;

        progress.Show();
        ShippingRequest req = new ShippingRequest(app.getSerial(), adminID, ID, orderId, "", "", sortby, "", "", "", "", "", "", "", "", airprint_printer, selectedPrinter, getResources().getString(R.string.version), "", reship);
        manager.GetOrder(req, checkorder);

    }


    public void RequestfornoScan(String orderId, String sortby) {

        progress.Show();
        String shipping_flag = "";

        if (BaseActivity.getShippingflag())
            shipping_flag = "true";

        NotScanProductShippingRequest req = new NotScanProductShippingRequest(app.getSerial(), adminID, ID, orderId, sortby, shipping_flag, getResources().getString(R.string.version));
        manager.GetOrderNoProduct(req, checkordernoProduct);
    }

    protected boolean startPackingBoxActivity() {
        Log.e("ShipActivity", " startPackingBoxActivity");
        Log.e("NewShippingActivity ", "PackData  " + packBoxData);

        if (BaseActivity.getPackingList() == true) {
            if (mProcNo == PROC_QTY) {
                cProductList.put("processedCnt", _gts(id.quantity));
                if (is_scan == false)
                    createNewPackItem();
                if (cProductList.get("processedCnt").equals(cProductList.get("quantity")))
                    fixedRequest(COMPLETE_INSPECT);
                else fixedRequest(INCOMPLETE_INSPECT);




         /*   if (mProcNo == PROC_QTY) {
                if (is_scan == false)
                    createNewPackItem();

                fixedRequest(INCOMPLETE_INSPECT);
//                packing = true;
//                is_scan = true;
//                inputedEvent("",false);
*/
            }
        }
        if (packBoxData.size() == 0) {
            return true;
        }
        if (BaseActivity.getPackingList() == true) {
            Log.e(TAG, " startPackingBoxActivityyy11113333    ");
            Intent packingIntent = new Intent(this, ShippingPackingBoxActivity.class);
            packingIntent.putExtra("orderId", _gts(id.orderId));
            packingIntent.putExtra("orderQtySize", ORDER_QTY_COUNT);
            packingIntent.putExtra("packedQtySize", getBadge4());
            Log.e(TAG, "showPack");
            startActivityForResult(packingIntent, PACKING_ACTIVITY);
            return false;
        } else if (BaseActivity.getPackingList() == false) {
            return true;
        }
        return true;
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                if (data.hasExtra(FINISH)) {
                    if (includenotinspected.equals("0")) {
                        U.beepKakutei(this, null);
                        if (!ShippingActivity.this.isFinishing()) {
                            showDialog1("作成されたボックス番号は" + mBoxNo + "です");
                            nextProcess1();
                        }
                    } else {
                        _sts(id.barcode, "");
                        _sts(id.quantity, "");
                        _sts(id.productCode, "");

                        _stxtv(id.productName, "");
                        _sts(id.totalQuantity, "");
                        setProc(PROC_BARCODE);

                        setBadge2(0);
                        setBadge3(Integer.parseInt(ShippingActivity.includenotinspected));
                    }
                } else if (data.hasExtra(NEXTBOX)) {

                    if (mProcNo == PROC_QTY) {

                        Log.e("ShipActivity", " onActivityResult3");
                        updatePackBadge();
                        _sts(id.barcode, "");
                        _sts(id.quantity, "");
                        _sts(id.productCode, "");
                        _stxtv(id.productName, "");
                        _sts(id.totalQuantity, "");
                        setProc(PROC_BARCODE);

                    }

                    Log.d("CALLBACK", "Next box");
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("PACKING_RESPONSE", "Cancelled");
            }
        } else if (requestCode == PACKING_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.hasExtra(FINISH)) {
                    Log.d("CALLBACK", FINISH);
                    send = true;
//                    fixedRequest();
                } else if (data.hasExtra(NEXTBOX)) {
                    if (data.getStringExtra(NEXTBOX).equals(ADDNEXTBOX)) {
                        nextbox = true;
                        printRequest();
                        // Command to add next box
                        if (mProcNo == PROC_QTY) {

                            updatePackBadge();
                            _sts(id.barcode, "");
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
            }
        }
    }


    @Override
    public void inputedEvent(String buff) {
        Log.e(TAG, "inputedEvent");
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        if (BaseActivity.getPackingList() && packData.size() > 0) {
            new AlertDialog.Builder(this)
                    .setTitle("現在のBoxをパッキングリスト作成 ？")
                    .setPositiveButton("する", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clear = true;
                            printRequest();
                        }
                    })
                    .setNegativeButton("しない", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO cancel clear
                            mTextToSpeak.startSpeaking("clear");
                            _sts(id.trackingNumber, "");
                            nextProcess();
                        }
                    })
                    .show();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Clear？")
                    .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mTextToSpeak.startSpeaking("clear");
                            _sts(id.trackingNumber, "");
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
    }

    public void clearcall() {
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
    }

    @Override
    public void allclearEvent() {
        Log.e("Log>>>>>>>", "allClear");
        new AlertDialog.Builder(this)
                .setTitle("AllClear？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Globals.getterList = new ArrayList<>();
                        adminID = spDomain.getString("admin_id", null);
                        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                        Globals.getterList.add(new ParamsGetter("order_id", _gts(id.orderId)));
                        Globals.getterList.add(new ParamsGetter("resetAll", "true"));

                        mRequestStatus = REQ_ALLCLEAR;
                        new MainAsyncTask(ShippingActivity.this, Globals.Webservice.Fixedshipping, 1, ShippingActivity.this, "Form", Globals.getterList, true).execute();
                        nextProcess();
                    }
                })

                .setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }


    @Override
    public void skipEvent() {
        CommonDialogs.customToast(this, "No action");
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_ORDERID:    // バーコード
                _sts(id.orderId, buff);
                break;
            case PROC_TRACKID:    // tracking number
                _sts(id.trackingNumber, buff);
                break;
            case PROC_BARCODE:    // バーコード
                _sts(id.barcode, buff);
                break;
            case PROC_ORDER_NO:
                _sts(id.orderNo, buff);
                break;

            case PROC_ORDER_NOSCAN:
                _sts(id.orderno_settings, buff);
                break;

            case PROC_QTY: // 数量
                _sts(id.quantity, buff);
                break;
            case PROC_BOX:
                _sts(id.box_no, buff);
                break;
            case PROC_SERIAL_NO:
                _sts(id.serialno, buff);
                break;
        }
    }

    public void createNewPackItem() {
        boolean repeat = false;
        boolean repeatpackBox = false;

        mTarget = cProductList;

        int target = Integer.parseInt(_gts(id.quantity));
        int productqnt = Integer.parseInt(cProductList.get("quantity"));
        if (target <= productqnt) {
            if (packData.size() > 0) {
                String _b1 = mTarget.get("barcode");
                String _box1 = String.valueOf(mBoxNo);
                String _loc1 = mTarget.get("location");

                for (Map<String, String> map : packData) {
                    String _b = map.get("barcode");
                    String _loc = map.get("location");

                    StringBuffer temp_qty = new StringBuffer();
                    if (_b.equals(_b1) && _loc.equals(_loc1)) {

                        String qnty = U.plusTo(map.get("quantity"), _gts(id.quantity));

                        map.put("quantity", qnty);
                        repeat = true;

                    }
                }

                // for packing Box Activity List

                for (Map<String, String> map : packBoxData) {
                    String _b = map.get("barcode");
                    Log.e(TAG, "pack data  barcode " + _b);
                    StringBuffer temp_qty = new StringBuffer();

                    if (_b.equals(_b1)) {

                        String qnty = U.plusTo(map.get("quantity"), _gts(id.quantity));
                        map.put("quantity", qnty);
                        repeatpackBox = true;

                    }
                }
            }
            if (repeat == false) {

                mPackItem = new HashMap<String, String>();
                mPackItem.put("boxNo", String.valueOf(mBoxNo));
                mPackItem.put("code", mTarget.get("code"));
                mPackItem.put("barcode", mTarget.get("barcode"));
                mPackItem.put("location", mTarget.get("location"));
                mPackItem.put("quantity", _gts(id.quantity).toString());
                mPackItem.put("order_sub_id", mTarget.get("order_sub_id"));
                packData.add(mPackItem);

            }
            if (repeatpackBox == false) {
                mPackItem = new HashMap<String, String>();
                mPackItem.put("boxNo", String.valueOf(mBoxNo));
                mPackItem.put("code", mTarget.get("code"));
                mPackItem.put("barcode", mTarget.get("barcode"));
                mPackItem.put("quantity", _gts(id.quantity).toString());
                packBoxData.add(mPackItem);
            }
            is_scan = true;
            updatePackBadge();
        }

    }

    public void getProductList(List<Map<String, String>> list) {
        productsList = list;
        showPopup3();
    }

    public void updatePackBadge() {
        int qtyBadge = 0;

        if (BaseActivity.getPackingList() == true) {
            if (packBoxData != null)
                setBadge4(packBoxData.size());
            else
                setBadge4(0);
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
            if (dialog1.isShowing()) {

            } else {
                if (!MainAsyncTask.dialogBox.isShowing()) {
                    if (!barcode.equals("")) {
                        if (mProcNo == PROC_BARCODE) {
                            Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                            String finalbarcode1 = CommonFunctions.getBracode(barcode);
                            barcode = finalbarcode1;

                            _sts(id.barcode, barcode);
                        } else if (mProcNo == PROC_ORDERID) _sts(id.orderId, barcode);

                        else if (mProcNo == PROC_ORDER_NOSCAN) _sts(id.orderno_settings, barcode);

                        else if (mProcNo == PROC_ORDER_NO) _sts(id.orderNo, barcode);

                        else if (mProcNo == PROC_BOX) _sts(id.box_no, barcode);
                        else if (mProcNo == PROC_SERIAL_NO)
                            _sts(id.serialno, barcode);

                        else if (mProcNo == PROC_TRACKID) {

                            _sts(id.trackingNumber, barcode);
                        } else if (mProcNo == PROC_QTY) {
                            Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                            String finalbarcode1 = CommonFunctions.getBracode(barcode);
                            barcode = finalbarcode1;
                        }

                    }
                    this.inputedEvent(barcode, true);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Wait", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    public void enterEvent() {
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
    }


    public void updateBadge1(String orderCount) {
        Log.e(TAG, "updateBadge111  " + orderCount);
        setBadge1(Integer.valueOf(orderCount));
    }

    public void updateBadge2(String qtyCount) {
        Log.e(TAG, "updateBadge222  " + qtyCount);
        setBadge2(Integer.valueOf(qtyCount));
    }

    public void updateBadge3(String qtyCount) {
        Log.e(TAG, "updateBadge333  " + qtyCount);
        setBadge3(Integer.valueOf(qtyCount));
    }

    public void getDetail() {

        getOrderDetail = false;
        Globals.getterList = new ArrayList<>();

        adminID = spDomain.getString("admin_id", null);

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("order_id", _gts(id.orderId)));
        mRequestStatus = REQ_ORDERDETAIL;

        new MainAsyncTask(this, Globals.Webservice.Getshipping, 1, ShippingActivity.this, "Form", Globals.getterList, true).execute();
    }


    @Override
    @SuppressLint("InflateParams")
    protected boolean showPopup3() {
        Log.e(TAG, "showPopup333");
        if (mProcNo == PROC_ORDERID) {
            Log.e("PickingActivity", "showInfoo  ORDER_IDDD");
            U.beepError(this, "注文IDをスキャンして下さい");
            return false;
        }
        if (productsList == null) {
            Log.e("PickingActivity", "showInfoo  mPickingOrderList===000");
            return false;
        }
        if (getPopupWindow() == null) {
            Log.e("PickingActivity", "showInfoo  popupwindow");
            final PopupWindow popupWindow = new PopupWindow(this);
            // レイアウト設定
            View popupView = getLayoutInflater().inflate(R.layout.work_pickings, null);
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
            ImageView close = (ImageView) getPopupWindow().getContentView().findViewById(id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }
        ListView lv1 = (ListView) getPopupWindow().getContentView().findViewById(id.workPicking);
        initWorkList(lv1);

        // 画面中央に表示
        getPopupWindow().showAtLocation(findViewById(id.orderId), Gravity.CENTER, 0, 32);
        return true;
    }

    protected ListViewItems initWorkList(ListView lv1) {

        lv1.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (int i = productsList.size() - 1; i >= 0; i--) {
            Map<String, String> row = productsList.get(i);

            data.add(data.newItem().add(id.wrk_pic_0, row.get("code"))
                            .add(id.wrk_pic_1, row.get("product_qty"))
                            .add(id.wrk_pic_2, row.get("quantity"))
                            .add(id.wrk_pic_3, row.get("category"))
//                    .add(R.id.wrk_pic_2, String.valueOf(i))
            );
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.work_picking_row);
        lv1.setAdapter(adapter);
        // 単一選択モードにする
        lv1.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv1.setItemChecked(0, true);
        return data;
    }


    @Override
    @SuppressLint("InflateParams")
    protected boolean showInfo() {
        Log.e(TAG, "showInfoo");

        if (ordersList == null) {
            Log.e("PickingActivityy", "showInfoo  mPickingOrderList===000");
            return false;
        }

        if (getPopupWindow2() == null) {
            Log.e("PickingActivity", "showInfoo  popupwindow");
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
            ImageView close = (ImageView) getPopupWindow2().getContentView().findViewById(id.close);
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
        Log.e(TAG, "initList");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (int i = 0; i < ordersList.size(); i++) {
            Map<String, String> row = ordersList.get(i);

            data.add(data.newItem().add(id.odr_pic_0, String.valueOf(i + 1))
                    .add(id.odr_pic_1, row.get("order_no"))
                    .add(id.odr_pic_2, row.get("name"))
                    .add(id.odr_pic_3, row.get("batch_name"))
            );
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.order_list_row);
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト?値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }


    public void nextProcess() {
        Log.e(TAG, "nextPRocess");

        this._sts(id.trackingNumber, "");
        this._sts(id.barcode, "");
        this._sts(id.quantity, "");
        this._sts(id.totalQuantity, "");
        this._sts(id.lotno, "");
        this._sts(id.orderId, "");
        this._sts(id.productCode, "");
        _sts(id.orderName, "");
        this._stxtv(id.productName, "");

        Log.e(TAG, "nextPRocess");
        resetPackData();
        setBadge1(0);
        setBadge2(0);
        setBadge3(0);
        setBadge4(0);

        serialgrid.setVisibility(View.GONE);

        mNextBarcode = false;
        isNextBarcode = "";
        setProc(PROC_ORDERID);

        if (showKeyboard == false) {
            visible = false;
            numbrbtn.setText(R.string.showkeyboard);
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e("MoveActivity", "SetlayoutMargin");
            mainlayout.setLayoutParams(params);
        }
        mTarget = null;
        clear = false;
        resetPackData();
        if (!category0List.isEmpty())
            category0List.clear();
        if (!category1List.isEmpty())
            category1List.clear();


        Globals.getterList = new ArrayList<>();
        adminID = spDomain.getString("admin_id", null);
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("initial_call", "yes"));
        mRequestStatus = REQ_INITIAL;
        new MainAsyncTask(this, Globals.Webservice.Getshipping, 1, ShippingActivity.this, "Form", Globals.getterList, true).execute();

        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }


    public void nextProcess1() {
        this._sts(id.trackingNumber, "");
        this._sts(id.barcode, "");
        this._sts(id.quantity, "");
        this._sts(id.totalQuantity, "");
        this._sts(id.lotno, "");
        this._sts(id.productCode, "");
        this._sts(id.orderName, "");
        this._sts(id.box_no, "");
        _stxtv(id.serialno, "");
        serialgrid.setVisibility(View.GONE);

        this._stxtv(id.productName, "");
        setBadge1(0);
        setBadge2(0);
        setBadge3(0);
        setBadge4(0);
        if (!category0List.isEmpty())
            category0List.clear();
        if (!category1List.isEmpty())
            category1List.clear();
        if (showKeyboard == false) {
            visible = false;
            numbrbtn.setText(R.string.showkeyboard);
            numbrbtn.setVisibility(View.VISIBLE);
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
//            Animation bottomDown = AnimationUtils.loadAnimation(this,
//                    R.anim.bottom_down);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);

            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e("NewPicking", "SetlayoutMargin");
            mainlayout.setLayoutParams(params);
        } else {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
            visible = true;

            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            layout = (RelativeLayout) findViewById(id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layout.getLayoutParams();
            param.setMargins(0, top, 0, 0);

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }

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
        mBoxNo = 0;
    }

    public void ToBoxsize() {
        Log.e(TAG, "Tooo Boxsize Call");
        Intent i = new Intent(this, ShippingSpecificationActivity.class);
        i.putExtra("order_id", cProductList.get("order_id"));
        i.putExtra("company", cProductList.get("shipping_method").trim().toString());
        i.putExtra("action", "shipping");
        i.putExtra("batch_id", "");
        i.putExtra("slip_printer", "");
        startActivity(i);
    }

    public void resetPackData() {
        Log.e(TAG, " ResetPackData ");
        ORDER_QTY_COUNT = 0;
        if (!packData.isEmpty()) {
            packData.clear();
            packBoxData.clear();
        }
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
            case PROC_ORDER_NO:
                _sts(id.orderNo, barcode);
                break;
            case PROC_ORDER_NOSCAN:
                _sts(id.orderno_settings, barcode);
                break;
            case PROC_TRACKID:
                _sts(id.trackingNumber, barcode);
                break;
            case PROC_BOX:
                _sts(id.box_no, barcode);
                break;
            case PROC_SERIAL_NO:
                _sts(id.serialno, barcode);
                break;
        }
    }

    public void sendSelectedPrinter() {
        Log.e(TAG, "sendSelectedPrinter   " + BaseActivity.getPrinterSelected());
        Globals.getterList = new ArrayList<>();
        String order = cProductList.get("order_id");
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("order_id", order));

        if (!checkPrinterSelect()) {
            Globals.getterList.add(new ParamsGetter("airprint_printer", BaseActivity.getintegratedselectedPrinterID()));
            Globals.getterList.add(new ParamsGetter("csv_printer_id", BaseActivity.getCsvselectedPrinterID()));
        } else
            Globals.getterList.add(new ParamsGetter("ap_printer_db", "1"));
        mRequestStatus = REQ_SENDPRINTER;

        new MainAsyncTask(this, Globals.Webservice.sendPrinterRequest, 1, ShippingActivity.this, "Form", Globals.getterList, true).execute();
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
            if (code == null)
            {
                CommonDialogs.customToast(getApplicationContext(), "Network error occured");
            }
            if ("0".equals(code) == true)
            {
                if (mRequestStatus == REQ_INITIAL)
                {
                    // collect all data from response
                    JsonHash row = (JsonHash) result1.get(0);
                    String all_order_count = row.getStringOrNull("all_order_count");
                    updateBadge3(all_order_count);
                }

                if (mRequestStatus == REQ_ORDERDETAIL)
                {
                    new Orderdetail().post(code, msg, result1, mHash, ShippingActivity.this);
                } else if (mRequestStatus == REQ_ALLORDERS_DETAIL)
                {
                    new AllOrderList().post(code, msg, result1, mHash, ShippingActivity.this);
                } else if (mRequestStatus == REQ_ALLCLEAR)
                {
                    nextProcess();
                }
                else if (mRequestStatus == REQ_BOX) {

                    U.beepKakutei(this, null);
                    if (BaseActivity.getPrinterSelected()) {
                        nextProcess1();
                        sendSelectedPrinter();
                    } else if (BaseActivity.getPackingList() == true) {
                        complete = true;

                        if (!packing)
                            printRequest();

                        nextProcess1();
                        packing = false;
                    } else if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag()) {
                        ToBoxsize();
                    } else
                        nextProcess();
                } else if (mRequestStatus == REQ_ADDPRINT) {
                    new NewAddPrint().post(code, msg, result1, mHash, ShippingActivity.this);
                } else if (mRequestStatus == REQ_ADDPACKING) {
                    new NewAddPacking().post(code, msg, result1, mHash, ShippingActivity.this);
                } else if (mRequestStatus == REQ_SENDPRINTER) {

                    if (BaseActivity.getPackingList() == true) {
                        complete = true;

                        if (!packing)
                            printRequest();
                        nextProcess1();
                        packing = false;
                    } else if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag()) {
                        ToBoxsize();
                    } else
                        nextProcess();
                }

            } else if (code.equalsIgnoreCase("1020")) {
                new AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(ShippingActivity.this, LoginActivity.class);
                                in.putExtra("ACTION", "logout");
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })
                        .show();
            }
            else if (code.equalsIgnoreCase("222222"))
            {
                CommonDialogs.setdialog("注文引当前戻ししました。",this,R.layout.new_picking_dialog);
            }
            else {
                if (mRequestStatus == REQ_ORDERDETAIL) {
                    new Orderdetail().valid(code, msg, result1, mHash, ShippingActivity.this);
                } else if (mRequestStatus == REQ_ALLORDERS_DETAIL) {
                    new AllOrderList().valid(code, msg, result1, mHash, ShippingActivity.this);
                } else if (mRequestStatus == REQ_ADDPRINT) {
                    new NewAddPrint().valid(code, msg, result1, mHash, ShippingActivity.this);
                } else if (mRequestStatus == REQ_SENDPRINTER) {
                    U.beepError(this, msg);
                } else if (mRequestStatus == REQ_ADDPACKING) {
                    new NewAddPacking().valid(code, msg, result1, mHash, ShippingActivity.this);
                } else if (mRequestStatus == REQ_ALLCLEAR)
                    U.beepError(this, msg);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }

    public void showDialog1(String msg) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set GUI of login screen
        dialog.setContentView(R.layout.new_picking_dialog);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());

        dialog.setCanceledOnTouchOutside(false);

        // Init button of login GUI
        TextView txt = (TextView) dialog.findViewById(id.txt);
        txt.setText(msg);
        ImageView close = (ImageView) dialog.findViewById(id.icon_close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextProcess();
                dialog.dismiss();
            }
        });

        // Make dialog box visible
        dialog.show();
    }

    public void showErrorPopup() {
        pDialog1 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog1.setCancelable(true);
        pDialog1.setTitleText("正しい商品を検品して下さい。");
        pDialog1.setConfirmText("Ok");
        pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                errorDialog = false;
                pDialog1.dismiss();
            }
        });
        pDialog1.show();
    }

    @Override
    public void onSucess(int status, GetOrderResponse message) throws JsonIOException {
        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("0")) {
            arr = message.getResults();
            updateBadge3(arr.get(0).getAll_order_count());

        }  else if (message.getCode().equalsIgnoreCase("222222")) {
            CommonDialogs.setdialog("注文引当前戻ししました。",this,R.layout.new_picking_dialog);

        } else {
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, CheckOrderResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("200")) {
//            orderData = message.getData();
            Log.e(TAG, ">>>>>>" + message.getData());
            String remark = "", name = "";

            PayStatus = message.getAfter_pay();
            String all_row_count = U.plusTo(message.getNot_inspection_row_count(), message.getShortage_row_count());

            updateBadge3(message.getAll_order_count());

            ArrayList<Map<String, String>> list = message.getData();
            Log.e(TAG, ">>>><<<<" + list);
            ArrayList<String> tracklist = new ArrayList<>();
            Map<String, String> map = new HashMap<>();

            category0List = new ArrayList<>();
            category1List = new ArrayList<>();
            if (list != null && list.size() > 0) {
                Log.e(TAG, ">>>><<<<" + list.size());
                if (!BaseActivity.getscanProduct()) {
                    cProductList = list.get(0);
                    if (orderRequestSettings == SettingActivity.ORDER_ID)

                        RequestfornoScan(_gts(id.orderId), "");
                    else if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
                        RequestfornoScan(_gts(id.orderNo), "orderno");
                    else if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO)
                        RequestfornoScan(_gts(id.trackingNumber), "mediacode");
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        Log.e(TAG, ">>><<<" + list.get(i));
                        map = list.get(i);
                        Log.e(TAG, ">>><<<1111" + map);

                        if (list.get(0).containsKey("serial_no_flg")) {
                            String SerialNO = list.get(0).get("serial_no_flg");

                            if (SerialNO.equals("1")) {
                                serialPresent = SerialNO;
                                serialgrid.setVisibility(View.VISIBLE);
                            } else {
                                serialPresent = SerialNO;
                                serialgrid.setVisibility(View.GONE);
                            }
                        }

                        if (list.get(0).containsKey("np_id")) {
                            NPID = list.get(0).get("np_id");
                        }

                        if (list.get(0).containsKey("np_shop_id")) {
                            NP_Shop_ID = list.get(0).get("np_shop_id");
                        }

                        if (!map.get("quantity").equals("0")) {
                            map.put("processedCnt", "0");

                            if (map.containsKey("pikking_method")) {
                                remark = map.get("pikking_method");
                            }

                            String category = map.get("category");
                            if (category.equals("0")) {
                                category0List.add(map);
                            } else if ((category.equals("1") || category.equals("3") || category.equals("4")) && BaseActivity.getsinclude()) {
                                category1List.add(map);
                            }
                            Log.e(TAG, ">>><<<222" + category1List);
                        }

                        String tracking = map.get("mediacode");
                        Log.e(TAG, ">>><<<333" + remark);

                        Log.e(TAG, ">>>><<<<  mediacode" + tracking);
                        name = map.get("name");
                        barcode = map.get("barcode");

                    }
                    if (!remark.equals("") && BaseActivity.getRemarkPress()) {

                        dilaogCustomercancel(remark);
                        //  CommonDialogs.setdialog(remark, this, R.layout.dialog_popup);
                    }

                    updateBadge2(category1List.size() + "");
                    updateBadge1(all_row_count);

                    if (!message.getAll_order_count().equals("0") && category0List.size() == 0 && category1List.size() == 0)
                        U.beepError(this, "order complete");

                    else if (!BaseActivity.getsinclude() && category0List.size() == 0)
                        U.beepError(this, "order complete");

                    else {
                        cProductList = map;

                        _sts(id.orderName, name);
                        _sts(id.barcode, "");
                        _sts(id.quantity, "");

                        if (PayStatus != null) {
                            if (PayStatus.equalsIgnoreCase("1") && SharedPrefrences.get_ShipOrderNumber(ShippingActivity.this).equalsIgnoreCase("1")) {
                                setProc(PROC_ORDER_NOSCAN);
                            } else {
                                checkProc();
                            }
                        } else {
                            checkProc();
                        }

                        String track[] = map.get("mediacode").split(",");

                        for (String t : track) {
                            tracklist.add(t);
                        }
                        mTrackId = tracklist;
                    }

                }
            } else {
                U.beepError(this, "No record found!");
            }

        }
        else if (message.getCode().equalsIgnoreCase("420") && BaseActivity.getReshipment()){
            new AlertDialog.Builder(this)
                    .setTitle("すでに検品済みです。")
                    .setMessage("もう一度検品しますか？")
                    .setPositiveButton("する", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GetOrderRequest(temporderId,tempsortby,"1");
                        }
                    })
                    .setNegativeButton("しない", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }else if (message.getCode().equalsIgnoreCase("222222")) {
            CommonDialogs.setdialog("注文引当前戻ししました。",this,R.layout.new_picking_dialog);

        }
        else {
            U.beepError(this, message.getMessage());
        }
    }

    void checkProc() {
        switch (BaseActivity.getOrderInfoBy()) {
            case SettingActivity.ORDER_ID:

                if (cProductList.get("mediacode").equals("") || BaseActivity.getTrackCheck()) {
                    if (category0List.isEmpty() && BaseActivity.getsinclude())
                        gotoIncludeScreen();
                    else
                        setProc(PROC_BARCODE);
                } else {
                    setProc(PROC_TRACKID);
                }
                break;

            case SettingActivity.ORDER_NUMBER:
                _sts(id.orderId, cProductList.get("order_id"));
                if (cProductList.get("mediacode").equals("") || BaseActivity.getTrackCheck()) {

                    if (category0List.isEmpty() && BaseActivity.getsinclude())
                        gotoIncludeScreen();
                    else
                        setProc(PROC_BARCODE);
                } else {
                    setProc(PROC_TRACKID);
                }
                break;
            case SettingActivity.ORDER_TRACKING_NO:
                Log.e(TAG, "SWITCH__ORDER_TRACKING_NO");
                _sts(id.orderId, cProductList.get("order_id"));
                if (category0List.isEmpty() && BaseActivity.getsinclude())
                    gotoIncludeScreen();
                else
                    setProc(PROC_BARCODE);
                break;
        }
    }


    @Override
    public void onSucess(int status, CheckBarcodeResponse message) throws JsonIOException {
        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("200")) {

            startTimer();
            updateBadge3(message.getAll_order_count());

            String all_row_count = U.plusTo(message.getNot_inspection_row_count(), message.getShortage_row_count());

            Map<String, String> map = new HashMap<>();
            if (message.getData() != null && message.getData().size() > 0) {
                map = message.getData().get(0);

                if (map != null && !map.isEmpty()) {

                    map.put("processedCnt", "0");
                    map.put("Koguchi_processed_count", "0");

                    _stxtv(id.productName, map.get("product_name"));
                    productName.setSelected(true);

                    _sts(id.quantity, "");
                    Log.e(TAG, ">>>>> lottt" + map.get("lot"));
                    _sts(id.lotno, map.get("lot"));
                    _sts(id.totalQuantity, map.get("quantity"));

                    //  serial_no_flg
                    if (map.containsKey("serial_no_flg")) {
                        String SerialNO = map.get("serial_no_flg");

                        if (SerialNO.equals("1")) {
                            serialPresent = SerialNO;
                            serialgrid.setVisibility(View.VISIBLE);
                        } else {
                            serialPresent = SerialNO;
                            serialgrid.setVisibility(View.GONE);
                        }
                    }

                    cProductList = map;
                    nextWork();

                    updateBadge1(all_row_count);

                    _sts(id.productCode, map.get("code"));

                } else {
                    _sts(id.quantity, "");
                    _stxtv(id.productName, "");
                    _sts(id.productCode, "");
                    _sts(id.totalQuantity, "");
                    U.beepError(this, "No data found!");
                }
            } else {
                U.beepError(this, "No data found!");
            }
        }
        else if (message.getCode().equalsIgnoreCase("222222")) {
            CommonDialogs.setdialog("注文引当前戻ししました。",this,R.layout.new_picking_dialog);

        }
        else if (message.getCode().equalsIgnoreCase("20162")) {
            U.beepError(this, null);
            Lockdialog();

        }

        else {
            U.beepError(this, message.getMessage());
        }

    }

    @Override
    public void onSucess(int status, FixOrderResponse message) throws JsonIOException {
        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("200")) {

            startTimer();
            updateBadge3(message.getAll_order_count());

            String all_row_count = U.plusTo(message.getNot_inspection_row_count(), message.getShortage_row_count());
            updateBadge1(all_row_count);

            Map<String, String> map = new HashMap<>();
            if (message.getData() != null)
                map = message.getData().get(0);

            if (mNextBarcode) {
                _sts(id.barcode, "");
                _sts(id.barcode, isNextBarcode);
                setProc(PROC_BARCODE);
                inputedEvent(isNextBarcode, true);
                removeData(inspectionNo);

                if (ShippingActivity.inspectionNo == 1)
                    call();
                else
                    removeData(ShippingActivity.inspectionNo);

            } else if (all_row_count.equals("0")) {

                //if Box selected on setting screen
                if (BaseActivity.getBoxNo()) {
                    boxSize = "";
                    _sts(id.trackingNumber, "");
                    _sts(id.barcode, "");
                    _sts(id.quantity, "");
                    _sts(id.totalQuantity, "");
                    _sts(id.lotno, "");
                    _sts(id.productCode, "");
                    _sts(id.orderName, "");
                    _stxtv(id.productName, "");
                    _stxtv(id.serialno, "");

                    cProductList.put("Koguchi_processed_count", "0");
                    setProc(PROC_BOX);

                } else {
                    U.beepKakutei(this, null);
                    if (BaseActivity.getPrinterSelected()) {
                        nextProcess1();
                        sendSelectedPrinter();
                    } else if (BaseActivity.getPackingList() == true) {

                        if ((Integer.parseInt(message.getNot_inspection_row_count()) == 0 && Integer.parseInt(message.getShortage_row_count()) != 0) || Integer.parseInt(all_row_count) == 0)
                            complete = true;
                        if (!packing)
                            printRequest();
                        nextProcess1();
                        packing = false;
                    } else if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag()) {
                        ToBoxsize();
                    } else
                        nextProcess();
                }
            } else if (!all_row_count.equals("0") && (map.isEmpty() || map == null) && BaseActivity.getsinclude()) {

                gotoIncludeScreen();

            } else if (!all_row_count.equals("0") && (map.isEmpty() || map == null) && !BaseActivity.getsinclude()) {



                //if Box selected on setting screen
                if (BaseActivity.getBoxNo()) {
                    boxSize = "";
                    _sts(id.trackingNumber, "");
                    _sts(id.barcode, "");
                    _sts(id.quantity, "");
                    _sts(id.totalQuantity, "");
                    _sts(id.lotno, "");
                    _sts(id.orderId, "");
                    _sts(id.productCode, "");
                    _sts(id.orderName, "");
                    _stxtv(id.productName, "");

                    cProductList.put("Koguchi_processed_count", "0");
                    setProc(PROC_BOX);

                } else {//if printer selected
                    U.beepKakutei(this, null);

                    if (BaseActivity.getPrinterSelected()) {
                        nextProcess1();
                        sendSelectedPrinter();
                    } else if (BaseActivity.getPackingList() == true) {

                        Log.e("FixedPickingNS ", "Running next process1122");
                        if ((Integer.parseInt(message.getNot_inspection_row_count()) == 0 && Integer.parseInt(message.getShortage_row_count()) != 0) || Integer.parseInt(all_row_count) == 0)
                            complete = true;

                        if (!packing)
                            printRequest();

                        nextProcess1();

                        packing = false;
                    } else if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag())
                        ToBoxsize();
                    else nextProcess();
                }
            } else {
                Log.e("FixedPickingNS", "222222    " + ShippingActivity.inspectionNo);
                _sts(id.barcode, "");
                _sts(id.quantity, "");
                _stxtv(id.productName, "");
                _sts(id.productCode, "");
                _sts(id.totalQuantity, "");
                setProc(PROC_BARCODE);
                if (inspectionNo == 1)
                    call();
                else
                    removeData(ShippingActivity.inspectionNo);
                Log.e("FixedPickingNS", "22223333");

            }

            if (getOrderDetail)
                getDetail();


        }else if (message.getCode().equalsIgnoreCase("222222")) {
            CommonDialogs.setdialog("注文引当前戻ししました。",this,R.layout.new_picking_dialog);

        }
        else {
            U.beepError(this, message.getMessage());
        }

    }

    @Override
    public void onSucess(int status, SetBoxSizeResponse message) throws JsonIOException {
        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("0")) {
//            Map<String, String> map = new HashMap<>();
//            if(message.getResults()!= null)
//                map = message.getResults().get(0);
//
//            updateBadge3(map.get("all_order_count"));

            U.beepKakutei(this, null);
            if (BaseActivity.getPrinterSelected()) {
                nextProcess1();
                sendSelectedPrinter();
            } else if (BaseActivity.getPackingList() == true) {
                complete = true;

                if (!packing)
                    printRequest();

                nextProcess1();
                packing = false;
            } else if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag()) {
                ToBoxsize();
            } else
                nextProcess();

        } else {
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, CheckOrderNoProductResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("200")) {
            if (message.getData() != null) {
                cProductList = message.getData().get(0);
                if (!cProductList.get("mediacode").equals("") && !BaseActivity.getTrackCheck()) {
                    String remark = "";
                    if (cProductList.containsKey("pikking_method")) {
                        remark = cProductList.get("pikking_method");
                    }
                    if (!remark.equals("") && BaseActivity.getRemarkPress()) {
                        dilaogCustomercancel(remark);

                        //  CommonDialogs.setdialog(remark, this, R.layout.dialog_popup);
                        //  CommonDialogs.setdialog(remark, this, R.layout.dialog_popup);
                    }
                    String track[] = cProductList.get("mediacode").split(",");
                    ArrayList<String> tracklist = new ArrayList<>();
                    for (String t : track) {
                        tracklist.add(t);
                    }
                    mTrackId = tracklist;

                    setProc(PROC_TRACKID);
                } else if (BaseActivity.getTrackCheck()) {
                    RequestfornoScan(cProductList.get("mediacode"), "mediacode");
                } else {
                    setNextStep();
                }
            } else setNextStep();
        } else {
            U.beepError(this, message.getMessage());
        }
    }

    public void setNextStep() {
        if (BaseActivity.getBoxNo()) {
            nextProcess1();

            Log.e(TAG, "nextProcess1   " + cProductList);
            cProductList.put("Koguchi_processed_count", "0");
            Log.e(TAG, "nextProcess22222  " + cProductList);
            setProc(PROC_BOX);
        } else {
            U.beepKakutei(this, null);
            if (BaseActivity.getPrinterSelected()) {
                Log.e(TAG, "getPrinterSelected   " + BaseActivity.getPrinterSelected());
                sendSelectedPrinter();
                Log.e(TAG, "getPrinterSelected   " + BaseActivity.getPrinterSelected());
                nextProcess1();
            } else if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag()) {
                Log.e(TAG, "getBoxSelected   " + BaseActivity.getBoxSelected() + !BaseActivity.getShippingflag());
                ToBoxsize();
            } else
                nextProcess();
        }

    }

    @Override
    public void onError(int status, ResponseBody error) {
        progress.Dismiss();
        U.beepError(this, error.toString());
    }

    @Override
    public void onFaliure() {
        progress.Dismiss();
        U.beepError(this, "Network failure");
    }

    @Override
    public void onNetworkFailure() {
        progress.Dismiss();
        U.beepError(this, "Network failure");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }


    public void dilaogCustomercancel(String remark) {

        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setCancelable(false);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setContentView(R.layout.shipping_remark);

        TextView text = (TextView) dialog1.findViewById(id.remark);
        text.setText(remark);
        Button ok = (Button) dialog1.findViewById(id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        if (!dialog1.isShowing()) {
            dialog1.show();
        }
    }

    public void Lockdialog ()
    {

        Dialog dialog = new Dialog(ShippingActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set GUI of login screen

        dialog.setContentView(R.layout.lock_dialog);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        lp.copyFrom(window.getAttributes());



        // Init button of login GUI
        TextView txt=(TextView)dialog.findViewById(R.id.txt) ;
        txt.setText("解除コードを\n入力してください。");
        EditText edt = (EditText)dialog.findViewById(R.id.edittxt);
        Button ok=(Button)dialog.findViewById(R.id.btn_ok_dialog);
        edt.setFocusable(true);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt.getText().toString().equalsIgnoreCase("")){
                    U.beepError(ShippingActivity.this, "Enter passcode");
                }
                else if (edt.getText().toString().equalsIgnoreCase("20162")) {
                    Log.e("commomndialog", ">>>>>111111111");
                    dialog.dismiss();
                } else {
                    U.beepError(ShippingActivity.this, " 解除コードが違います");
                }
            }  });
        // Make dialog box visible.
        dialog.show();
    }

}

