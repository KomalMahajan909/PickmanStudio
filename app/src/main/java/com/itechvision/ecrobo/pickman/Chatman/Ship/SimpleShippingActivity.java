package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.CheckBarcode.CheckBarcodeShipReq;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.CheckBarcode.CheckBarcodeShipResponse;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.CheckBarcode.ShipProductList;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.CheckOrder.CheckOrderReq;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.CheckOrder.CheckShipOrderResponse;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.GetShippingSettings.GetShippingSettingResponse;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.GetShippingSettings.GetShippingSettingsReq;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.PickBarcode.PickBarcodeReq;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.PickBarcode.PickBarcodeResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class SimpleShippingActivity extends BaseActivity implements DataManager.GetSettingsAPIcall, DataManager.CheckOrderCallback, DataManager.CheckBarcodeShipCallback, DataManager.PickBarcodeCallback {
    static SlidingMenu menu;
    SlideMenu slidemenu;


    @BindView(R.id.menu_drawer)
    ImageView menu_drawer;
    @BindView(R.id.orderId)
    EditText orderId;
    @BindView(R.id.orderName)
    EditText orderName;
    @BindView(R.id.trackingNumber)
    EditText trackingNumber;
    @BindView(R.id.barcode)
    EditText barcode;
    @BindView(R.id.quantity)
    EditText quantity;

    @BindView(R.id.productCode)
    EditText productCode;
    @BindView(R.id.productName)
    TextView productName;
    @BindView(R.id.totalQuantity)
    EditText totalQuantity;
    @BindView(R.id.location)
    EditText location;

    @BindView(R.id.scrollMain)
    ScrollView svMain;
    @BindView(R.id.keyboard)
    Button keyboard;
    @BindView(R.id.btnblue)
    Button btnBlue;
    @BindView(R.id.btnred)
    Button btnRed;

    @BindView(R.id.layout_main)
    RelativeLayout mainlayout;
    @BindView(R.id.layout_number)
    RelativeLayout layoutnumber;


    protected int mProcNo = 0;
    public static final int PROC_ORDERID = 1;
    public static final int PROC_ORDER_NO = 2;
    public static final int PROC_TRACKID = 3;
    public static final int PROC_BARCODE = 4;
    public static final int PROC_QTY = 5;

    public boolean mNextBarcode = false;
    public String isNextBarcode = "";

    public Context mcontext = this;
    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPrefs";
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    public String adminID = "", domainname = "", ID = "", BarcodeScanvalue = "0", productCheck = "0", days_Check = "0";
    public String order_id = "", mediacode = "", BarcodeScaned = "0";
    private boolean visible = false;

    ECRApplication app = new ECRApplication();

    DataManager manager;
    progresBar progress;

    private DataManager.GetSettingsAPIcall getSettingsAPIcall;
    private DataManager.CheckOrderCallback checkOrderCallback;
    private DataManager.CheckBarcodeShipCallback checkBarcodeShipCallback;
    private DataManager.PickBarcodeCallback pickBarcodeCallback;

    private List<String> mTrackId = new ArrayList<>();
    private ArrayList<ShipProductList> mProductList = new ArrayList<>();

    private int orderRequestSettings;
    public TextToSpeak mTextToSpeak;
    private boolean showKeyboard;

    private String TAG = SimpleShippingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderRequestSettings = BaseActivity.getOrderInfoBy();
        if (orderRequestSettings == SettingActivity.ORDER_ID)
            setContentView(R.layout.activity_simple_shipping);
        else if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
            setContentView(R.layout.activity_simple_shipping_orderno);
        else if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO)
            setContentView(R.layout.activity_simple_shipping_trackingno);

        Log.d(TAG,"On Create ");
        ButterKnife.bind(SimpleShippingActivity.this);

        mTextToSpeak = new TextToSpeak(this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        productName = (TextView) findViewById(R.id.productName);

        progress = new progresBar(this);
        manager = new DataManager();
        getSettingsAPIcall = this;
        checkOrderCallback = this;
        checkBarcodeShipCallback = this;
        pickBarcodeCallback = this;


        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);

        adminID = spDomain.getString("admin_id", null);
        ID = BaseActivity.getShopId();

        showKeyboard = sharedPreferences.getBoolean("ShowKeyboard", false);

        if (showKeyboard == true) {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
            visible = true;
            keyboard.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);

            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layoutnumber.getLayoutParams();
            param.setMargins(0, top, 0, 0);

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        if (mProcNo == 0) {
            nextProcess();
        }
    }

    @OnClick(R.id.menu_drawer)
    void menu() {
        menu.showMenu();
    }

    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
        if (visible == false) {

            visible = true;
            keyboard.setText(R.string.hideKeyboard);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layoutnumber.getLayoutParams();
            param.setMargins(0, top, 0, 0);

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;
            keyboard.setText(R.string.showkeyboard);
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
    protected void onResume() {
        super.onResume();
        if (BaseActivity.getOrderInfoBy() == orderRequestSettings ) {
            // Just restart from where we left
        } else {
            this.finish();
            Intent i = new Intent(this, SimpleShippingActivity.class);
            startActivity(i);
        }
    }
    public void restart() {
        this.finish();
        Intent i = new Intent(this, SimpleShippingActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.enter)
    void enter() {
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {
            case PROC_ORDERID:
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.orderId).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(R.id.orderId));
                break;
            case PROC_TRACKID:
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
                    _gt(R.id.orderNo).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.trackingNumber).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.trackingNumber));
                break;

            case PROC_ORDER_NO:
                _gt(R.id.orderNo).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.orderNo).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.orderNo));
                break;

            case PROC_BARCODE:
                isNextBarcode = "";
                mNextBarcode = false;

                if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
                    _gt(R.id.orderNo).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);

                mTextToSpeak.startSpeaking("barcode");
                scrollToView(svMain, _g(R.id.barcode));
                break;

            case PROC_QTY:
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));

                if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
                    _gt(R.id.orderNo).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                if (_gts(R.id.quantity).equals("")) _sts(R.id.quantity, "1");
                _gt(R.id.quantity).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(R.id.quantity));
                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {

        switch (mProcNo) {

            case PROC_ORDERID:    // 注文ID
                /* Conditional set next process whether orderId or trackingNo or orderNo. */

                String orderId = _gts(R.id.orderId);
                if ("".equals(orderId)) {
                    U.beepError(this, "注文IDは必須です");
                    _gt(R.id.orderId).setFocusableInTouchMode(true);

                    break;
                }

                progress.Show();
                CheckOrderReq req = new CheckOrderReq(app.getSerial(), adminID, ID, orderId, getResources().getString(R.string.version), days_Check, productCheck, "");
                manager.CheckOrderShipping(req, checkOrderCallback);

                break;

            case PROC_ORDER_NO:    // 注文ID
                /* Conditional set next process whether orderId or trackingNo or orderNo. */
                if (orderRequestSettings == SettingActivity.ORDER_NUMBER) {

                    String orderNo = _gts(R.id.orderNo);
                    if ("".equals(orderNo)) {
                        U.beepError(this, "注文IDは必須です");
                        _gt(R.id.orderNo).setFocusableInTouchMode(true);
                        break;
                    }
                    progress.Show();
                    CheckOrderReq req1 = new CheckOrderReq(app.getSerial(), adminID, ID, orderNo, getResources().getString(R.string.version), days_Check, productCheck, "orderno");
                    manager.CheckOrderShipping(req1, checkOrderCallback);

                }
                break;
            case PROC_TRACKID:    // tracking number
                /* Conditional set next process whether orderId or trackingNo or orderNo.*/
                if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO) {

                    String trackingNo = _gts(R.id.trackingNumber);
                    if ("".equals(trackingNo)) {
                        U.beepError(this, "トラック#は必須です");
                        _gt(R.id.trackingNumber).setFocusableInTouchMode(true);
                        break;
                    }

                    progress.Show();
                    CheckOrderReq req1 = new CheckOrderReq(app.getSerial(), adminID, ID, trackingNo, getResources().getString(R.string.version), days_Check, productCheck, "mediacode");
                    manager.CheckOrderShipping(req1, checkOrderCallback);

                } else {

                    String trackingNo = _gts(R.id.trackingNumber);
                    if ("".equals(trackingNo)) {
                        U.beepError(this, "トラック#は必須です");
                        _gt(R.id.trackingNumber).setFocusableInTouchMode(true);
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
                        _gt(R.id.trackingNumber).setFocusableInTouchMode(true);
                        break;
                    }
                    setProc(PROC_BARCODE);
                }

                break;
            case PROC_BARCODE:    // バーコード
                String barcode = _gts(R.id.barcode);
                if ("".equals(barcode)) {
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }

                progress.Show();

                CheckBarcodeShipReq req1 = new CheckBarcodeShipReq(app.getSerial(), adminID, ID, order_id, getResources().getString(R.string.version), days_Check, productCheck, barcode);
                manager.CheckBarcodeShipping(req1, checkBarcodeShipCallback);

                break;
            case PROC_QTY:
                String qty = _gts(R.id.quantity);
                String barcode1 = _gts(R.id.barcode);

                if (isScaned) {

                    if (buff.equals(barcode1)) {

                        qty = U.plusTo(qty, "1");
                        BarcodeScaned = U.plusTo(BarcodeScaned, qty);

                        _sts(R.id.quantity, qty);
                        if (Integer.parseInt(qty) > 1)
                            mTextToSpeak.startSpeaking(qty);

                        BarcodeScaned = _gts(R.id.quantity);

                        /* check if update in quantity need next action */
                        if (BarcodeScaned.equals(mProductList.get(0).getQuantity())) {
                            fixedRequest();
                        }
                    } else {
                     /*   U.beepError(this, "Barcode dont match");
                        Toast.makeText(getApplicationContext(), "BarCode Doesn't Match", Toast.LENGTH_SHORT).show();*/
                        fixedRequest(buff);
                    }

                } else {
                    if ("".equals(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                    String qtyUpdate = U.minusTo(qty, BarcodeScaned);
                    String maxQty_ = U.minusTo(mProductList.get(0).getQuantity(), BarcodeScaned);

                    if (U.compareNumeric(BarcodeScaned, qty) == -1) {
                        U.beepError(this, "Quantity entered should exceed " + BarcodeScaned.toString());

                        _sts(R.id.quantity, "1");
                        BarcodeScaned = "1";

                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        U.beepError(this, "数量が多すぎます");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        _sts(R.id.quantity, "1");

                        BarcodeScaned = "1";
                        break;
                    } else {
                        if (Integer.parseInt(qty) > 1)
                            mTextToSpeak.startSpeaking(_gts(R.id.quantity));

                        BarcodeScaned = U.plusTo(BarcodeScaned, qtyUpdate);

                        if (BarcodeScaned.equals(mProductList.get(0).getQuantity())) {
                            fixedRequest();
                        } else {
                            fixedRequest();
                        }
                    }
                }
                break;
        }
    }

    private void fixedRequest() {
        sendRequest();
    }

    private void fixedRequest(String nextBarcode) {
        mNextBarcode = true;
        isNextBarcode = nextBarcode;
        sendRequest();
    }

    public void sendRequest() {

        stopTimer();

        String barc = "";
        if (!mProductList.get(0).getBarcode().equals(""))
            barc = mProductList.get(0).getBarcode();
        else
            barc = mProductList.get(0).getCode();

        progress.Show();

        PickBarcodeReq req = new PickBarcodeReq(app.getSerial(), adminID, ID, order_id, getResources().getString(R.string.version), days_Check, productCheck, barc, mProductList.get(0).getProduct_stock_history_id(), BarcodeScaned);
        manager.PickBarcodeAPI(req, pickBarcodeCallback);

    }


    @Override
    public void inputedEvent(String buff) {
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
                        _sts(R.id.trackingNumber, "");
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
        CommonDialogs.customToast(this, "No action");
    }

    @Override
    public void skipEvent() {
        CommonDialogs.customToast(this, "No action");
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_ORDERID:    // バーコード
                _sts(R.id.orderId, buff);
                break;
            case PROC_TRACKID:    // tracking number
                _sts(R.id.trackingNumber, buff);
                break;
            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, buff);
                break;
            case PROC_ORDER_NO:
                _sts(R.id.orderNo, buff);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.quantity, buff);
                break;
        }
    }


    @Override
    public void scanedEvent(String barcode) {

        if (!barcode.equals("")) {
            if (mProcNo == PROC_BARCODE) {
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                String finalbarcode1 = CommonFunctions.getBracode(barcode);
                barcode = finalbarcode1;

                _sts(R.id.barcode, barcode);
            } else if (mProcNo == PROC_ORDERID) _sts(R.id.orderId, barcode);

            else if (mProcNo == PROC_ORDER_NO) _sts(R.id.orderNo, barcode);

            else if (mProcNo == PROC_TRACKID) {

                _sts(R.id.trackingNumber, barcode);
            } else if (mProcNo == PROC_QTY) {
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                String finalbarcode1 = CommonFunctions.getBracode(barcode);
                barcode = finalbarcode1;
            }

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
            case PROC_QTY:
                if ("".equals(barcode)) {
                } else
                    _sts(R.id.quantity, barcode);
                break;
            case PROC_ORDERID:
                _sts(R.id.orderId, barcode);
                break;
            case PROC_ORDER_NO:
                _sts(R.id.orderNo, barcode);
                break;
            case PROC_TRACKID:
                _sts(R.id.trackingNumber, barcode);
                break;

        }
    }


    @Override
    public void nextProcess() {
        Log.e("NewPickingActivity", "nextProcess22");
        this._sts(R.id.trackingNumber, "");
        this._sts(R.id.barcode, "");
        this._sts(R.id.quantity, "");
        this._sts(R.id.totalQuantity, "");
        this._sts(R.id.location, "");
        this._sts(R.id.orderId, "");
        this._sts(R.id.productCode, "");
        this._sts(R.id.orderName, "");


        this._stxtv(R.id.productName, "");
        btnRed.setText("0");
        btnBlue.setText("0");

        if (showKeyboard == true) {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
            visible = true;
            keyboard.setText("キーボードを隠す");

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);

            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layoutnumber.getLayoutParams();
            param.setMargins(0, top, 0, 0);

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
            visible = true;

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);

            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layoutnumber.getLayoutParams();
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
                this._sts(R.id.orderNo, "");
                this.setProc(PROC_ORDER_NO);
                break;
            case SettingActivity.ORDER_TRACKING_NO:
                this.setProc(PROC_TRACKID);
                break;
        }

        progress.Show();
        GetShippingSettingsReq req = new GetShippingSettingsReq(adminID, app.getSerial(), ID, getResources().getString(R.string.version));
        manager.GetSettingsAPI(req, getSettingsAPIcall);
    }

    @Override
    public void onSucess(int status, GetShippingSettingResponse message) throws JsonIOException {
        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("0")) {
            productCheck = message.getProduct_category();
            days_Check = message.getOrder_days_check();

        } else {
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, CheckShipOrderResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("200")) {
            orderName.setText(message.getReceiver_name());
            orderId.setText(message.getOrder_id());
//            trackingNumber.setText(message.getMediacode());

            btnBlue.setText(message.getAll_order_count());
            btnRed.setText(U.plusTo(message.getNot_inspection_row_count(), message.getShortage_row_count()));

            order_id = message.getOrder_id();
            mediacode = message.getMediacode();
            checkProc();
            ArrayList<String> tracklist = new ArrayList<>();
            String track[] = mediacode.split(",");

            for (String t : track) {
                tracklist.add(t);
            }
            mTrackId = tracklist;
        } else {
            U.beepError(this, message.getMessage());
        }
    }


    void checkProc() {
        switch (BaseActivity.getOrderInfoBy()) {
            case SettingActivity.ORDER_ID:

                if (mediacode.equals("") || BaseActivity.getTrackCheck()) {
                    setProc(PROC_BARCODE);
                } else {
                    setProc(PROC_TRACKID);
                }
                break;

            case SettingActivity.ORDER_NUMBER:

                if (mediacode.equals("") || BaseActivity.getTrackCheck()) {
                    setProc(PROC_BARCODE);
                } else {
                    setProc(PROC_TRACKID);
                }
                break;
            case SettingActivity.ORDER_TRACKING_NO:
                setProc(PROC_BARCODE);
                break;
        }
    }


    @Override
    public void onSucess(int status, CheckBarcodeShipResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("200")) {
            mProductList = message.getData();
            btnBlue.setText(message.getAll_order_count());
            btnRed.setText(U.plusTo(message.getNot_inspection_row_count(), message.getShortage_row_count()));

            quantity.setText("1");
            productCode.setText(mProductList.get(0).getCode());
            productName.setText(mProductList.get(0).getProduct_name());
            totalQuantity.setText(mProductList.get(0).getQuantity());
            location.setText(mProductList.get(0).getLocation());

            nextWork();
        } else {
            U.beepError(this, message.getMessage());
        }
    }

    public void nextWork() {

        BarcodeScaned = "1";
        setProc(PROC_QTY);
        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking(_gts(R.id.quantity));


        if (BarcodeScaned.equals(mProductList.get(0).getQuantity())) {

            fixedRequest();

        }

    }

    @Override
    public void onSucess(int status, PickBarcodeResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("200")) {
            startTimer();
            btnBlue.setText(message.getAll_order_count());

            String all_row_count = U.plusTo(message.getNot_inspection_row_count(), message.getShortage_row_count());
            btnRed.setText(all_row_count);

            if (mNextBarcode) {
                _sts(R.id.barcode, "");
                _sts(R.id.barcode, isNextBarcode);
                setProc(PROC_BARCODE);
                inputedEvent(isNextBarcode, true);
            } else if (all_row_count.equals("0")) {

                U.beepKakutei(this, null);
                nextProcess();
            } else {

                _sts(R.id.barcode, "");
                _sts(R.id.quantity, "");
                _stxtv(R.id.productName, "");
                _sts(R.id.productCode, "");
                _sts(R.id.totalQuantity, "");
                setProc(PROC_BARCODE);
                Log.e(TAG, "222222222333333");

            }
        } else {
            U.beepError(this, message.getMessage());
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
    public void onBackPressed() {
//        super.onBackPressed();
    }
}