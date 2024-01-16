package com.itechvision.ecrobo.pickman.Chatman.LoopShipping;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Adapter.LoopShip.Adap_loopShipOrderPendinglist;
import com.itechvision.ecrobo.pickman.Adapter.LoopShip.Adap_loopShipOrderlist;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.PickWork.TasPicking.NewtaspickingBarcodeActivity;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.Barcode.LoopBarcodeReq;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.Barcode.LoopBarcodeResult;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder.GetorderData;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder.GetorderRequest;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder.GetorderResult;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder.GetpendingorderResult;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder.PendingData;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.LoopOrderReq;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.LoopOrderResult;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.Submit.LoopSubmitResult;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.Submit.SubmitReqLoop;
import com.itechvision.ecrobo.pickman.Models.NewShipping.CheckOrder.CheckOrderNoProductData;
import com.itechvision.ecrobo.pickman.Models.NewShipping.GetTotalOrders.GetOrderData;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.drawable;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.rey.material.app.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

public class LoopShipping extends BaseActivity implements View.OnClickListener , DataManager.LoopShipingOrdercall ,DataManager.LoopShipingBarcodeAPIcall,DataManager.SubmitLoopShipingAPIcall
        ,DataManager.GETLoopOrderAPIcall,DataManager.GETLoopPendingOrdercall{

    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(id.actionbar) ActionBar actionbar;
    @BindView(id.box_no) EditText box_no;
    @BindView(id.orderId) EditText orderId;
    @BindView(id.serialno) EditText SerialNo;

    Dialog dialog1;
    protected int mProcNo = 0;
    public static int inspectionNo = 0;
    public static int mBoxNo = 0;
    public boolean clear = false,skip=false;
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
            Key = "", BarcodeScanvalue = "0", BARCODE = "", ProductId = "", PSHID = "", TOTALQTY = "";
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
    private String TAG = LoopShipping.class.getSimpleName();
    public static int mRequestStatus = 0;

    BottomSheetDialog Imagebottom, PendingOrderBottom;
    public String _lastUpdateQty = "0",all_barcode="";
    public boolean nextbox = false;
    public boolean packing = false, complete = false;
    String barcode = "";
    DataManager manager;

    ArrayList<GetOrderData> arr;
    ArrayList<CheckOrderNoProductData> arrayData;
    ArrayList<PendingData> arrPending;
    ArrayList<GetorderData> orderlist;
    @BindView(id.gridserial) LinearLayout serialgrid;
    public String serialPresent = "0", SerialNO = "",SHippingFlag="";
    String serialNo = "";
    public List<String> serialList = new ArrayList<>();
    private boolean fromqty = false;
    DataManager.LoopShipingOrdercall getOrder ;
    DataManager.LoopShipingBarcodeAPIcall getbarocde ;
    DataManager.SubmitLoopShipingAPIcall submit ;
    DataManager.GETLoopOrderAPIcall getorderlist;
    DataManager.GETLoopPendingOrdercall getpendinglist;
    Adap_loopShipOrderlist adap ;
    Adap_loopShipOrderPendinglist adap2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderRequestSettings = BaseActivity.getOrderInfoBy();
        setContentView(R.layout.shipping_loop);

        ButterKnife.bind(LoopShipping.this);
        getIDs();
        Log.e(TAG , "On  Create");
        getOrder = this;
        getbarocde = this;
        submit = this;
        getorderlist = this;
        getpendinglist = this;

        ViewPager viewPager = (ViewPager) findViewById(id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        productName = (TextView) findViewById(id.productName);

        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);

        adminID = spDomain.getString("admin_id", null);
        ID = BaseActivity.getShopId();
        orderlist = new ArrayList<>();
        arrPending = new ArrayList<>();


        PendingOrderBottom = new BottomSheetDialog(this);
        Imagebottom = new BottomSheetDialog(this);

        svMain = (ScrollView) _g(id.scrollMain);
        dialog1 = new Dialog(LoopShipping.this);

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

        if (mProcNo == 0) nextProcess();

     /*   progress.Show();
          ShippingRequest req = new ShippingRequest(app.getSerial(), adminID, ID, "", "", "", "", "", "", "", "", "", "", "", "", "", "", getResources().getString(R.string.version), "");
          manager.GetOrderNo(req, getorder);
*/


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (BaseActivity.getShippingflag() == true){
            SHippingFlag = "true";
        }else{
            SHippingFlag = "false";
        }

    }


    @OnClick(id.enter) void enter() {
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
    }

    //set title and icons on actionbar
    private void getIDs() {

        actionbarImplement(this, "Looopレジ検品", " ",
                0, true, false, true, false);

        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        // btnYellow.setOnClickListener(this);

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
          /*  case id.notif_count_yellow:

                if (BaseActivity.getPackingList())
                 break;
            case id.notif_count_red:
                if (BaseActivity.getincludeScreen())
                    showPopup2();
                break;*/
            case id.notif_count_blue:
                if (btnBlue.getText().toString().equalsIgnoreCase("0")){

                }else {
                    progress.Show();
                    LoopOrderReq req = new LoopOrderReq(adminID, app.getSerial(), ID,orderId.getText().toString());
                    manager.GETLoopPendingOrder(req, getpendinglist);
                }
                break;

            case id.notif_count_green:
                if (btnGreen.getText().toString().equalsIgnoreCase("0")){

                }else {
                    progress.Show();
                    GetorderRequest req = new GetorderRequest(adminID, app.getSerial(), ID);
                    manager.GETLoopOrder(req, getorderlist);
                }
                break;

            default:
                break;
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
    public void onBackPressed() {
        // TODO not backed from picking activity
        //super.onBackPressed();
    }


    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {
            case PROC_ORDERID:
                _gt(id.orderId).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.trackingNumber).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.box_no).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.serialno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

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
                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {

        switch (mProcNo) {

            case PROC_ORDERID:    // 注文ID

                String orderId = _gts(id.orderId);
                if ("".equals(orderId)) {
                    U.beepError(this, "注文IDは必須です");
                    _gt(id.orderId).setFocusableInTouchMode(true);
                    break;
                }else{
                    progress.Show();
                    LoopOrderReq req = new LoopOrderReq(adminID,app.getSerial(), ID ,orderId);
                    manager.LoopShipingOrderAPI(req,getOrder);
                }

                break;


            case PROC_TRACKID:
                /* Conditional set next process whether orderId or trackingNo or orderNo.*/

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


                setProc(PROC_BARCODE);


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

                    progress.Show();
                    LoopBarcodeReq req = new LoopBarcodeReq(adminID,app.getSerial(), ID ,_gts(id.orderId),barcode,all_barcode);
                    manager.LoopShipingBarcodeAPI(req,getbarocde);

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

                if (isScaned) {

                    boolean match1 = checkBarcode(buff);
                    if (!match1) {
                        U.beepError(this, "バーコードが一致しません");
                        _gt(R.id.barcode).setFocusableInTouchMode(true);
                        break;
                    }

                    Log.e("BarcodeScanValue", BarcodeScanvalue);

                    if (U.compareNumeric(BarcodeScanvalue, TOTALQTY) == 0) {
                        U.beepError(this, "Qunatity cannot exceed the required quantity");
                        break;
                    }

                    if (buff.equals(barcode1)) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned");
                        qty = U.plusTo(BarcodeScanvalue, "1");
                        BarcodeScanvalue = qty;
                        //  Toast.makeText(this, BarcodeScanvalue, Toast.LENGTH_SHORT).show();

                        _sts(id.quantity, BarcodeScanvalue);
                        mTextToSpeak.startSpeaking(BarcodeScanvalue);
                        if (Integer.parseInt(BarcodeScanvalue) > 1){
                            //   mTextToSpeak.startSpeaking(BarcodeScanvalue);

                        }

                        if (BarcodeScanvalue.equals(TOTALQTY)) {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned22");
                            //API HIT PICKED
                            try {
                                // progress.Show();
                                Log.e("API 1",">>> API 1 Scan");
                                SubmitAPI();
                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    } else {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned33");
                        U.beepError(this, "Barcode dont match");
                        Toast.makeText(getApplicationContext(), "BarCode Doesn't Match", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_Scanned");
                    if ("".equals(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }else if (qty.equalsIgnoreCase("0")){
                        U.beepError(this, "数量番号は0であり、登録できません。");
                    }else if (qty.equalsIgnoreCase("00")){
                        U.beepError(this, "数量番号は0であり、登録できません。");
                    }else if (qty.equalsIgnoreCase("000")){
                        U.beepError(this, "数量番号は0であり、登録できません。");
                    }else if (qty.equalsIgnoreCase("0000")){
                        U.beepError(this, "数量番号は0であり、登録できません。");
                    }else if (qty.equalsIgnoreCase("00000")){
                        U.beepError(this, "数量番号は0であり、登録できません。");
                    }else if (qty.equalsIgnoreCase("000000")){
                        U.beepError(this, "数量番号は0であり、登録できません。");
                    }else if (qty.equalsIgnoreCase("0000000")){
                        U.beepError(this, "数量番号は0であり、登録できません。");
                    }else if (qty.equalsIgnoreCase("00000000")){
                        U.beepError(this, "数量番号は0であり、登録できません。");
                    }else if (qty.equalsIgnoreCase("000000000")){
                        U.beepError(this, "数量番号は0であり、登録できません。");
                    }else if (qty.equalsIgnoreCase("0000000000")){
                        U.beepError(this, "数量番号は0であり、登録できません。");
                    }else if (qty.equalsIgnoreCase("00000000000")){
                        U.beepError(this, "数量番号は0であり、登録できません。");
                    }  else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                    String processedCnt = BarcodeScanvalue;
                   /* String qtyUpdate = U.minusTo(qty, _lastUpdateQty);
                    String maxQty_ = U.minusTo(totalqty.getText().toString(), processedCnt);*/

                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_lastUpdateQTY");
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(TOTALQTY, qty) > 0) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_qtyUpdate");
                        U.beepError(this, "数量が多すぎます");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt");
                        // cProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        //   BarcodeScanvalue =  U.plusTo(processedCnt, qtyUpdate);
                        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                        if (BarcodeScanvalue.equals(TOTALQTY)) {
                            //APIHIT PICKED
                            try {

                                Log.e("API 2",">>>>>>>> API 2 Scan");
                                SubmitAPI();

                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }
                            //  fixedRequest();

                        } else {
                            try {
                                Log.e("API 3",">>> API 3 Scan");
                                SubmitAPI();

                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }


                break;

            case PROC_SERIAL_NO:
                String Serial = _gts(id.serialno);

                if (_gts(id.serialno).equals("")) {
                    U.beepError(LoopShipping.this, null);
                    break;
                }else if(_gts(id.serialno).equals("0")){
                    U.beepError(LoopShipping.this, null);
                    break;
                }else if (Serial.equalsIgnoreCase("00")){
                    U.beepError(this, "シリアル番号は0で登録できません。");
                }else if (Serial.equalsIgnoreCase("000")){
                    U.beepError(this, "シリアル番号は0で登録できません。");
                }else if (Serial.equalsIgnoreCase("0000")){
                    U.beepError(this, "シリアル番号は0で登録できません。");
                }else if (Serial.equalsIgnoreCase("00000")){
                    U.beepError(this, "シリアル番号は0で登録できません。");
                }else if (Serial.equalsIgnoreCase("000000")){
                    U.beepError(this, "シリアル番号は0で登録できません。");
                }else if (Serial.equalsIgnoreCase("0000000")){
                    U.beepError(this, "シリアル番号は0で登録できません。");
                }else if (Serial.equalsIgnoreCase("00000000")){
                    U.beepError(this, "シリアル番号は0で登録できません。");
                }else if (Serial.equalsIgnoreCase("000000000")){
                    U.beepError(this, "シリアル番号は0で登録できません。");
                }else if (Serial.equalsIgnoreCase("0000000000")){
                    U.beepError(this, "シリアル番号は0で登録できません。");
                }else if (Serial.equalsIgnoreCase("00000000000")){
                    U.beepError(this, "シリアル番号は0で登録できません。");
                } else {
                    if (_gts(id.quantity).equals("")) _sts(id.quantity, "1");
                    SubmitAPI();

                }
                break;
        }
    }

    boolean checkBarcode(String barcode) {
        boolean match = false;

        if (barcode.equalsIgnoreCase(BARCODE)){
            match = true ;
        }
        return match;
    }


    public void setmTrackId(ArrayList<String> mTrackId) {
        this.mTrackId = mTrackId;
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
                            btnGreen.setText("0");

                            btnBlue.setText("0");
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
      /*  Log.e("Log>>>>", "allClear");
        new AlertDialog.Builder(this)
                .setTitle("AllClear？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                      nextProcess();
                    }
                })

                .setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();*/
    }


    @Override
    public void skipEvent() {
/*
        if (_gts(id.barcode).equalsIgnoreCase("")||_gts(id.barcode).equalsIgnoreCase("0")){
            U.beepError(this,"バーコードは必須です");

        }else {

            new AlertDialog.Builder(LoopShipping.this)
                    .setTitle("スキップしますか？")
                    .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //  mTextToSpeak.startSpeaking("Skip");
                            skip = true;
                            progress.Show();
                            SubmitReqLoop req = new SubmitReqLoop(adminID, app.getSerial(), ID, _gts(id.orderId), ProductId, PSHID, SerialNo.getText().toString(), "0", "1",all_barcode);
                            manager.SubmitLoopShipingAPI(req, submit);

                        }
                    })
                    .setNegativeButton("いいえ", null)
                    .show();

        }*/
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



    @Override
    public void scanedEvent(String barcode) {
        if (ENTER_BARCODE.equals(barcode)) {
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
                        } else if (mProcNo == PROC_ORDERID) {
                            // _sts(id.orderId, barcode);
                            orderId.setText(barcode);
                        } else if (mProcNo == PROC_ORDER_NOSCAN) _sts(id.orderno_settings, barcode);

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
        Log.e(TAG, "updateBadge22  " + qtyCount);
        setBadge2(Integer.valueOf(qtyCount));
    }

    public void updateBadge3(String qtyCount) {
        Log.e(TAG, "updateBadge333  " + qtyCount);
        setBadge3(Integer.valueOf(qtyCount));
    }

    public void nextProcess() {

        this._sts(id.trackingNumber, "");
        this._sts(id.barcode, "");
        this._sts(id.quantity, "");
        this._sts(id.totalQuantity, "");
        this._sts(id.lotno, "");
        this._sts(id.orderId, "");
        this._sts(id.productCode, "");
        this._sts(id.serialno, "");
        _sts(id.orderName, "");
        this._stxtv(id.productName, "");
        all_barcode ="";
        skip = false;
        setBadge1(0);
        setBadge2(0);
        setBadge3(0);
        setBadge4(0);

        // serialgrid.setVisibility(View.GONE);

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
            mainlayout.setLayoutParams(params);
        }
        mTarget = null;
        clear = false;

    }


    public void nextProcess1() {
        this._sts(id.trackingNumber, "");
        this._sts(id.barcode, "");
        this._sts(id.quantity, "");
        this._sts(id.totalQuantity, "");
        this._sts(id.lotno, "");
        this._sts(id.orderId, "");
        this._sts(id.productCode, "");
        this._sts(id.orderName, "");
        this._sts(id.box_no, "");
        _stxtv(id.serialno, "");
        // serialgrid.setVisibility(View.GONE);

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
//          Animation bottomDown = AnimationUtils.loadAnimation(this,
//          R.anim.bottom_down);
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
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

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


    public void showDialog1(String msg) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Set GUI of login screen
        dialog.setContentView(R.layout.new_picking_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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


    void checkProc() {
        switch (BaseActivity.getOrderInfoBy()) {
            case SettingActivity.ORDER_ID:

                if (cProductList.get("mediacode").equals("") || BaseActivity.getTrackCheck()) {
                    if (category0List.isEmpty() && BaseActivity.getincludeScreen()) {

                    } else
                        setProc(PROC_BARCODE);
                } else {
                    setProc(PROC_TRACKID);
                }
                break;

            case SettingActivity.ORDER_NUMBER:
                _sts(id.orderId, cProductList.get("order_id"));
                if (cProductList.get("mediacode").equals("") || BaseActivity.getTrackCheck()) {

                    if (category0List.isEmpty() && BaseActivity.getincludeScreen()) {
                    } else
                        setProc(PROC_BARCODE);
                } else {
                    setProc(PROC_TRACKID);
                }
                break;
            case SettingActivity.ORDER_TRACKING_NO:
                Log.e(TAG, "SWITCH__ORDER_TRACKING_NO");
                _sts(id.orderId, cProductList.get("order_id"));
                if (category0List.isEmpty() && BaseActivity.getincludeScreen()){
                } else
                    setProc(PROC_BARCODE);
                break;
        }
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

    @Override
    public void onSucess(int status, LoopOrderResult message) throws JsonIOException {

        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("0")){
            ArrayList<String> tracklist = new ArrayList<>();

            _sts(R.id.orderName, message.getOrder_data().getName2());
            btnGreen.setText(message.getAll_order_count());

            int total = Integer.parseInt(message.getPending_row_count()) + Integer.parseInt(message.getSkip_row_count());
            btnBlue.setText(String.valueOf(total));

            if (message.getOrder_data().getMediacode().equalsIgnoreCase("") || BaseActivity.getTrackCheck()) {
                setProc(PROC_BARCODE);
            } else {
                setProc(PROC_TRACKID);
            }

            String track[] = message.getOrder_data().getMediacode().split(",");

            for (String t : track) {
                tracklist.add(t);
            }
            mTrackId = tracklist;

        }else{
            U.beepError(this,message.getMessage());
        }
    }


    //Barcode
    @Override
    public void onSucess(int status, LoopBarcodeResult message) throws JsonIOException {

        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){

            btnGreen.setText(message.getAll_order_count());
            all_barcode = message.getAll_barcode();

            int total = Integer.parseInt(message.getPending_row_count()) + Integer.parseInt(message.getSkip_row_count());
            btnBlue.setText(String.valueOf(total));

            ProductId = message.getProduct_data().get(0).getProduct_id();
            PSHID = message.getProduct_data().get(0).getPsh_id();
            BARCODE = message.getProduct_data().get(0).getBarcode();
            TOTALQTY = message.getProduct_data().get(0).getQuantity();
            SerialNO = message.getProduct_data().get(0).getSerial_no_flg();

            _sts(id.totalQuantity,TOTALQTY);
            _sts(id.productCode,message.getProduct_data().get(0).getProductcode());
            _stxtv(id.productName,message.getProduct_data().get(0).getProductname());
            _sts(id.lotno,message.getProduct_data().get(0).getLotno());

            if (message.getProduct_data().get(0).getSerial_no_flg().equalsIgnoreCase("0")){
                setProc(PROC_QTY);
                BarcodeScanvalue =  _gts(R.id.quantity);
                if (_gts(id.quantity).equalsIgnoreCase(TOTALQTY)){
                    SubmitAPI();
                }
            }else{
                setProc(PROC_SERIAL_NO);
                if (_gts(id.quantity).equals("")) _sts(id.quantity, "1");
            }

        }else{
            U.beepError(this, message.getMessage());
        }

    }

    @Override
    public void onSucess(int status, LoopSubmitResult message) throws JsonIOException {
        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("0")){
            btnGreen.setText(message.getAll_order_count());
            int total = Integer.parseInt(message.getPending_row_count()) + Integer.parseInt(message.getSkip_row_count());
            btnBlue.setText(String.valueOf(total));
            all_barcode = message.getAll_barcode();
            if (message.getPending_barcode().equalsIgnoreCase("0")  && message.getProduct_data().size()==0){
                nextProcess();
                U.beepFinish(this,null);
            }else if(Integer.parseInt(message.getPending_barcode())> 0 && message.getProduct_data().size()==0  ){
                U.beepKakutei(this,null);
                setProc(PROC_BARCODE);
                _sts(id.barcode,"");
                _sts(id.totalQuantity,"");
                _sts(id.quantity,"");
                _sts(id.serialno,"");
                BarcodeScanvalue = "0";
                ProductId = "";
                PSHID = "";
                _sts(id.productCode,"");
                _stxtv(id.productName,"");
                _sts(id.lotno,"");
                skip= false;
            }else if(Integer.parseInt(message.getPending_barcode())> 0 && message.getProduct_data().size()!=0){
                SerialNO =  message.getProduct_data().get(0).getSerial_no_flg();
                //
                if (SerialNO.equalsIgnoreCase("0")) {
                    U.beepKakutei(this,null);
                    setProc(PROC_BARCODE);
                    _sts(id.barcode, "");
                    _sts(id.totalQuantity, "");
                    _sts(id.quantity, "");
                    _sts(id.serialno, "");
                    BarcodeScanvalue = "0";

                    _sts(id.productCode, "");
                    _stxtv(id.productName, "");
                    _sts(id.lotno, "");
                } else {
                    SerialNO =  message.getProduct_data().get(0).getSerial_no_flg();
                    if (skip){
                        U.beepKakutei(this,null);
                        skip = false;
                        setProc(PROC_BARCODE);
                        _sts(id.barcode, "");
                        _sts(id.totalQuantity, "");
                        _sts(id.quantity, "");
                        _sts(id.serialno, "");
                        BarcodeScanvalue = "0";

                        _sts(id.productCode, "");
                        _stxtv(id.productName, "");
                        _sts(id.lotno, "");
                    }else{
                        U.beepKakutei(this,null);
                        BarcodeScanvalue = "0";
                        _sts(R.id.quantity, "");
                        _sts(id.serialno, "");

                        ProductId = message.getProduct_data().get(0).getProduct_id();
                        PSHID = message.getProduct_data().get(0).getPsh_id();
                        BARCODE = message.getProduct_data().get(0).getBarcode();
                        TOTALQTY = message.getProduct_data().get(0).getQuantity();
                        _sts(id.productCode, message.getProduct_data().get(0).getProductcode());
                        _stxtv(id.productName, message.getProduct_data().get(0).getProductname());
                        _sts(id.lotno, message.getProduct_data().get(0).getLotno());
                        _sts(id.totalQuantity, TOTALQTY);

                        if (SerialNO.equalsIgnoreCase("0")) {
                            setProc(PROC_QTY);
                            BarcodeScanvalue = _gts(R.id.quantity);
                        } else {
                            setProc(PROC_SERIAL_NO);
                        }

                        if (_gts(id.quantity).equalsIgnoreCase(TOTALQTY)) {
                            SubmitAPI();
                        }
                    }

                }
            }
        }else{
            U.beepError(this, message.getMessage());
        }
    }


    @Override
    public void onSucess(int status, GetorderResult message) throws JsonIOException {

        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){
            orderlist = message.getOrders();
            if (orderlist.size()!=0){
                showCustomDialog() ;
            }
        }
    }

    @Override
    public void onSucess(int status, GetpendingorderResult message) throws JsonIOException {

        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){
            arrPending = message.getProduct_data();
            if (arrPending.size()!=0){
                PendingBottomDialog()   ;
            }
        }
    }

    @Override
    public void onError(int status, ResponseBody error) {

        if (status == 403) {
            progress.Dismiss();
            JSONObject jObjError = null;
            try {
                jObjError = new JSONObject(error.string());
                String message = jObjError.get("message").toString();

                U.beepError(LoopShipping.this, message);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onFaliure() {
    }

    @Override
    public void onNetworkFailure() {
    }

    public void SubmitAPI(){
        progress.Show();
        SubmitReqLoop req = new SubmitReqLoop(adminID,app.getSerial(), ID ,_gts(id.orderId),ProductId,PSHID,SerialNo.getText().toString(),_gts(id.quantity),"0",all_barcode,SHippingFlag);
        manager.SubmitLoopShipingAPI(req,submit);
    }


    private void showCustomDialog() {

        Imagebottom = new BottomSheetDialog(this);
        Imagebottom.setContentView(R.layout.order_list);
        Imagebottom.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);
        Imagebottom.cancelable(false);
        Imagebottom.setCanceledOnTouchOutside(false);

        ImageView back = (ImageView) Imagebottom.findViewById(R.id.close);
        ListView list =(ListView) Imagebottom.findViewById(R.id.orderPicking) ;

        adap = new Adap_loopShipOrderlist(LoopShipping.this,orderlist);
        list.setAdapter(adap);
        adap.notifyDataSetChanged();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imagebottom.dismiss();
            }
        });

        Imagebottom.show();
    }

    private void PendingBottomDialog() {

        PendingOrderBottom = new BottomSheetDialog(this);
        PendingOrderBottom.setContentView(R.layout.pendinglooplist);
        PendingOrderBottom.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);
        PendingOrderBottom.cancelable(false);
        PendingOrderBottom.setCanceledOnTouchOutside(false);

        ImageView back = (ImageView) PendingOrderBottom.findViewById(R.id.close);
        ListView list =(ListView) PendingOrderBottom.findViewById(R.id.orderPicking) ;

        adap2 = new Adap_loopShipOrderPendinglist(LoopShipping.this,arrPending);
        list.setAdapter(adap2);
        adap2.notifyDataSetChanged();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PendingOrderBottom.dismiss();
            }
        });

        PendingOrderBottom.show();
    }


}

