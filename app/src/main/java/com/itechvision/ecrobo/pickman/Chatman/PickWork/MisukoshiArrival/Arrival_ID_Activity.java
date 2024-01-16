package com.itechvision.ecrobo.pickman.Chatman.PickWork.MisukoshiArrival;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.SystemClock;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Adapter.Adap_arrivalID_list;
import com.itechvision.ecrobo.pickman.Adapter.Adap_newArrival;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Arrival_ID_Check.ArrivalIDCheckRequest;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Arrival_ID_Check.ArrivalIDCheckResponse;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Arrival_ID_Check.Arrival_ID_list_data;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Barcode_Check.BarcodeCheckRequest;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Barcode_Check.BarcodeCheckResponse;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Barcode_Check.Product_List_data;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.ClearEventSubmissionRequest;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.DirectArrival.DirectArrivalSubmissionRequest;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.NormalArrivalSubmisson.ArrivalsubmissionRequest;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.NormalArrivalSubmisson.ArrivalsubmissionResponse;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.StockClassification.StockClassificationListData;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.StockClassification.StockClassificationRequest;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.StockClassification.StockClassificationResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.rey.material.app.BottomSheetDialog;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

public class Arrival_ID_Activity extends BaseActivity implements View.OnClickListener, DataManager.ArrivalIDcheckcall ,DataManager.BarcodeCheckcall, DataManager.Arrivalsubmissioncall, DataManager.DirectArrivalsubmissioncall, DataManager.ClearEventcall, DataManager.StockClassificationcall{

    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.gridExpiration) LinearLayout layoutexpiration;
    @BindView(R.id.gridLot) LinearLayout layoutlot;
    @BindView(R.id.ll_comment) LinearLayout ll_comment;
    @BindView(R.id.location_layout) LinearLayout location_layout;
    @BindView(R.id.lotno) EditText lot;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.expirationdate) EditText expiration;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.productName) TextView productName;
    @BindView(R.id.stockClassificationLayout) LinearLayout stockClassificationLayout;
    @BindView(R.id.ll_listdialog) LinearLayout ll_listdialog;
    @BindView(R.id.classificationspinnerLayout) RelativeLayout classificationspinnerLayout;
    @BindView(R.id.classificationspinner) Spinner classificationspinner;
    @BindView(R.id.pck_0) TextView pck_0;
    @BindView(R.id.pck_1) TextView pck_1;
    @BindView(R.id.pck_2) TextView pck_2;
    @BindView(R.id.pck_3) TextView pck_3;
    @BindView(R.id.pck_4) TextView pck_4;
    @BindView(R.id.pck_5) TextView pck_5;

    public boolean lotexist = false;
    public Context mcontext = this;
    String TAG = Arrival_ID_Activity.class.getSimpleName();
    protected int mProcNo = 0;
    public static final int PROC_ORDER_ID = 1;
    public static final int PROC_BARCODE = 2;
    public static final int PROC_LOT_NO = 3;
    public static final int PROC_EXPIRATION = 4;
    public static final int PROC_QTY = 5;
    public static final int PROC_LOCATION = 6;
    SweetAlertDialog  dialog1;
    private boolean visible = false;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public static boolean mNextBarcode = false;
    ECRApplication app = new ECRApplication();
    String adminID = "";
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    private TextToSpeak mTextToSpeak;
    private boolean showKeyboard;
    private boolean orderRequestSettings;
    private static final String TRUE = "TRUE";
    private static final String FALSE = "FALSE";
    private String directToStockSetting = "";
    private boolean directArrival = false;
    DataManager manager;
    progresBar progress;
    //clear for clear event, delete for menu change
    Boolean clear = false, delete = false, userclear= false;
    String totalquantity = "0", attributetype = "0", BarcodeScanned = "0";
    ArrayList<Arrival_ID_list_data> scheduleList;
    ArrayList<Product_List_data> productList;
    ArrayList<StockClassificationListData> stockList;
    ArrayList<String> classificationdata = new ArrayList<String>();
    private DataManager.ArrivalIDcheckcall arrivalIDcheckcall;
    private DataManager.BarcodeCheckcall barcodeCheckcall;
    private DataManager.Arrivalsubmissioncall arrivalsubmissioncall;
    private DataManager.DirectArrivalsubmissioncall directArrivalsubmissioncall;
    private DataManager.ClearEventcall clearEventcall;
    private DataManager.StockClassificationcall stockClassificationcall;
    private String classificationId = "",NAUKAID="",PRODUCTID="";
    Dialog dialoglist;
    private  Dialog dialogLoc;
    Adap_arrivalID_list adapterlist;
    BottomSheetDialog bottomSheetDialog ;
    Adap_newArrival adaplist ;
    int mSelectedItem =0, eop=0;
    ListView listvv;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival_id);
        ButterKnife.bind(Arrival_ID_Activity.this);
        getIDs();

        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        orderRequestSettings = BaseActivity.getLotPress();
        Log.e(TAG, "orderRequestSettings " + BaseActivity.getLotPress());

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);
        progress = new progresBar(this);
        manager = new DataManager();

        arrivalIDcheckcall = this;
        barcodeCheckcall = this;
        arrivalsubmissioncall = this;
        directArrivalsubmissioncall = this;
        clearEventcall = this;
        stockClassificationcall = this;


        if(BaseActivity.getOptShelf()) {
            directArrival = true;
            location_layout.setVisibility(View.VISIBLE);
            stockClassificationLayout.setVisibility(View.VISIBLE);
        }
        else{
            directArrival = false;
            location_layout.setVisibility(View.GONE);
            stockClassificationLayout.setVisibility(View.GONE);
            directToStockSetting = (this.getDirectToStock()) ? TRUE : FALSE;
        }

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
            Log.e(TAG, "SetlayoutMargin");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        mTextToSpeak = new TextToSpeak(this);
        Log.e(TAG, "OnCreate");

        if (mProcNo == 0) nextProcess();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = "";
                String _month = "" + month;
                String _day = day + "";
                if (month < 10) {
                    _month = "0" + month;
                }
                if (day < 10) {
                    _day = "0" + day;
                }
                date = year + _month + _day;

                expiration.setText(date);
                setProc(PROC_QTY);
            }
        };
    }

    //set title and icons on actionbar
    private void getIDs() {
        actionbarImplement(this, "箱ID入荷検品", " ",
                0, true, true, false);

        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        btnRed.setOnClickListener(this);
        relLayout1.setOnClickListener(this);

        manager = new DataManager();
        progress = new progresBar(this);
        scheduleList = new ArrayList<>();
        productList = new ArrayList<>();

        bottomSheetDialog = new BottomSheetDialog(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                if(mProcNo>PROC_ORDER_ID)
                {
                    delete = true;
                    clearDialog("箱ID入荷検品が未完ですが、別メニューに遷移しますか？");
                }
                else
                    menu.showMenu();
                break;
            case R.id.notif_count_red:

                if (getBadge2() != 0) {
//                    showInfo();
                    dilaogprintqty();
                }
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
            Log.e(TAG, "SetlayoutMargin");
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
            Log.e(TAG, "SetlayoutMargin");
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
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {
            case PROC_ORDER_ID:
                _gt(R.id.arrivalID).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.arrivalID).setFocusableInTouchMode(true);
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                _gt(R.id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                if (orderRequestSettings == true) {
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                }
                break;
            case PROC_BARCODE:
                _gt(R.id.arrivalID).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                if (orderRequestSettings == true) {
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                }
                mTextToSpeak.startSpeaking("barcode");
                break;

            case PROC_QTY:
                _gt(R.id.arrivalID).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);
                if (orderRequestSettings == true) {
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                }
                if (_gts(R.id.quantity).equals("1"))
                    mTextToSpeak.startSpeaking("1");
                break;
            case PROC_LOT_NO:
                _gt(R.id.arrivalID).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.lotno).setFocusableInTouchMode(true);
                break;
            case PROC_EXPIRATION:
                _gt(R.id.arrivalID).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.expirationdate).setFocusableInTouchMode(true);
                showTruitonDatePickerDialog(expiration);
                break;

            case PROC_LOCATION:
                _gt(R.id.arrivalID).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.location).setFocusableInTouchMode(true);
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));

                if (orderRequestSettings == true) {
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                }
                break;
        }
    }

    public void showTruitonDatePickerDialog(View v) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void inputedEvent(String buff,boolean isScaned) {
        String lot = "";
        switch (mProcNo) {
            case PROC_ORDER_ID:
                String orderId = _gts(R.id.arrivalID);

                if (orderId.equals("") || orderId.equals("0")) {
                    U.beepError(this, "箱IDは必須です。");
                    break;
                }

                if (!CommonUtilities.getConnectivityStatus(this))
                    CommonUtilities.openInternetDialog(this);
                else {
                    progress.Show();
                    ArrivalIDCheckRequest req = new ArrivalIDCheckRequest(adminID, app.getSerial(), BaseActivity.getShopId(),orderId);
                    manager.ArrivalIDCheck(req, arrivalIDcheckcall);
                }

                break;

            case PROC_BARCODE:

                if (bottomSheetDialog.isShowing()){

                }else {

                    String barcode1 = _gts(R.id.barcode);


            /*    if (orderRequestSettings == true) {
                    setProc(PROC_LOT_NO);
                 } else {
                    U.beepRecord(this, null);
                    setProc(PROC_QTY);
                }*/

                    if (barcode1.equals("") || barcode1.equals("0")) {
                        U.beepError(this, "バーコードが必要です");
                        break;
                    }

                    if (!CommonUtilities.getConnectivityStatus(this))
                        CommonUtilities.openInternetDialog(this);
                    else {

                        progress.Show();
                        BarcodeCheckRequest req = new BarcodeCheckRequest(adminID, app.getSerial(), BaseActivity.getShopId(), _gts(R.id.arrivalID), barcode1);
                        manager.BarcodeCheckAPI(req, barcodeCheckcall);
                    }

                }
                break;
            case PROC_LOT_NO:

                lot = _gts(R.id.lotno);
                if (lot.equals("") || lot.equals("0")) {
                    U.beepError(this, "ロット番号が必要");
                    break;
                }

                U.beepRecord(this, null);
                if (attributetype.equals("3")) {
                    if (_gts(R.id.expirationdate).equalsIgnoreCase("")) {
                        setProc(PROC_EXPIRATION);
                    } else {
                        setProc(PROC_QTY);
                    }

                } else
                    setProc(PROC_QTY);

                break;
            case PROC_EXPIRATION:

                String loc = _gts(R.id.expirationdate);
                if ("".equals(loc)) {
                    U.beepError(this, "賞味期限が必要です");
                    _gt(R.id.expirationdate).setFocusableInTouchMode(true);
                    break;
                }
                setProc(PROC_QTY);

                break;
            case PROC_QTY:


//                    lotselect = false;
                String qty = _gts(R.id.quantity);
                if (qty.equals(""))
                    qty = "1";
                String barcode = _gts(R.id.barcode);
                String lotno = "";
                String expdate1 = "";

                if (orderRequestSettings == true) {
                    lotno = _gts(R.id.lotno);
                    expdate1 = _gts(R.id.expirationdate);
                }

                if (isScaned) {

                    if (buff.equals(barcode)) {
                        U.beepSuccess();
                        qty = U.plusTo(qty, "1");
                        _sts(R.id.quantity, qty);
                        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                        break;
                    } else {


                        if (directArrival) {

                            U.beepError(this, "Barcode dont match");
                            break;

                        } else {
                            stopTimer();
                           /* mNextBarcode = true;
                            isNextBarcode = buff;*/

                            //DIALOG

                            if (!CommonUtilities.getConnectivityStatus(this))
                                CommonUtilities.openInternetDialog(this);
                            else {
                                progress.Show();
                                ArrivalsubmissionRequest req = new ArrivalsubmissionRequest(adminID, app.getSerial(), BaseActivity.getShopId(), _gts(R.id.arrivalID), PRODUCTID /*productList.get(0).getProduct_id()*/, NAUKAID /*productList.get(0).getNyuka_id()*/, _gts(R.id.quantity), lotno, expdate1, directToStockSetting, getResources().getString(R.string.version), timeTaken().toString());
                                manager.ArrivalSubmissionAPI(req, arrivalsubmissioncall);
                            }

                            break;
                        }
                    }
                }

                if ("".equals(qty) || "0".equals(qty)) {
                    U.beepError(this, "数量は必須です");
                    _gt(R.id.quantity).setFocusableInTouchMode(true);
                    break;
                } else if (!U.isNumber(qty)) {
                    U.beepError(this, "数量は数値のみ入力可能です");
                    _gt(R.id.quantity).setFocusableInTouchMode(true);
                    break;
                }

                if (Integer.parseInt(pck_3.getText().toString()) < (Integer.parseInt(_gts(R.id.quantity))))
                    checkQnty();
                else {
                    if (directArrival) {
                        setProc(PROC_LOCATION);
                    } else {
                        stopTimer();

                        if (!CommonUtilities.getConnectivityStatus(this))
                            CommonUtilities.openInternetDialog(this);
                        else {
                            progress.Show();
                            ArrivalsubmissionRequest req = new ArrivalsubmissionRequest(adminID, app.getSerial(), BaseActivity.getShopId(), _gts(R.id.arrivalID), PRODUCTID /*productList.get(0).getProduct_id()*/, NAUKAID /*productList.get(0).getNyuka_id()*/, _gts(R.id.quantity), lotno, expdate1, directToStockSetting, getResources().getString(R.string.version), timeTaken().toString());
                            manager.ArrivalSubmissionAPI(req, arrivalsubmissioncall);
                        }
                    }
                }


                break;
            case PROC_LOCATION:

                String loc1 = _gts(R.id.location);
                String lotn = "";
                String expdat = "";
                if (orderRequestSettings) {
                    if (attributetype.equals("3")) {
                        lotn = _gts(R.id.lotno);
                        expdat = _gts(R.id.expirationdate);
                    } else if (attributetype.equals("2"))
                        expdat = _gts(R.id.expirationdate);
                    else if (attributetype.equals("1"))
                        lotn = _gts(R.id.lotno);
                }

                if ("".equals(loc1)) {
                    U.beepError(this, "ロケーションは必須です");
                    _gt(R.id.location).setFocusableInTouchMode(true);
                    break;
                }
                    /* String classification = "";
                    if (getclassificationId().equals("")) {
                        classification = getdefaultclassificationId();
                    } else
                        classification = getclassificationId();
*/
                stopTimer();
                if (!CommonUtilities.getConnectivityStatus(this))
                    CommonUtilities.openInternetDialog(this);
                else {
                    progress.Show();
                    DirectArrivalSubmissionRequest req = new DirectArrivalSubmissionRequest(adminID, app.getSerial(), BaseActivity.getShopId(), _gts(R.id.arrivalID), PRODUCTID /*productList.get(0).getProduct_id()*/, NAUKAID /*productList.get(0).getNyuka_id()*/, _gts(R.id.quantity), lotn, expdat, loc1, getclassificationId(), getResources().getString(R.string.version), timeTaken().toString());
                    manager.DirectArrivalSubmissionAPI(req, directArrivalsubmissioncall);
                }
                /* if (!differ.equals("0")) {
                        showdialog("数を予定より" + differ + "個多いです。");
                 }*/

                break;
        }
    }

    void checkQnty() {
        U.beepError(this, null);
        dialog1 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        if(dialog1.isShowing()){

        }
        else {

            dialog1.setCancelable(true);
            dialog1.setTitleText("予定数より多く入荷はできません。");
            dialog1.setConfirmText("OK");
            dialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            _gt(R.id.quantity).setFocusableInTouchMode(true);
                            dialog1.dismiss();
                        }
                    }, 1000);

                }
            });

            dialog1.show();
        }
    }


    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff,false);
    }

    @Override
    public void clearEvent() {
        if (mProcNo > PROC_ORDER_ID) {
            clear = true;
            clearDialog("箱ID入荷検品が未完ですが、クリアしますか？");
            scheduleList.clear();
            NAUKAID="";
            PRODUCTID="";
        } else {
            scheduleList.clear();
            mTextToSpeak.startSpeaking("Clear");
            NAUKAID="";
            PRODUCTID="";
            nextProcess();
        }
    }

    void clearDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!CommonUtilities.getConnectivityStatus(Arrival_ID_Activity.this))
                            CommonUtilities.openInternetDialog(Arrival_ID_Activity.this);
                        else {
                            progress.Show();
                            ClearEventSubmissionRequest req = new ClearEventSubmissionRequest(adminID, app.getSerial(), BaseActivity.getShopId(), _gts(R.id.arrivalID));
                            manager.ClearEventAPI(req, clearEventcall);
                        }

                    }
                })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
            case PROC_ORDER_ID:
                _sts(R.id.arrivalID, buff);
                break;
            case PROC_BARCODE:

                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY:
                _sts(R.id.quantity, buff);
                break;
            case PROC_LOCATION:
                _sts(R.id.location, buff);
                break;
            case PROC_LOT_NO:
                _sts(R.id.lotno, buff);
                break;
            case PROC_EXPIRATION:
                _sts(R.id.expirationdate, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if(dialogLoc !=null){
            if(dialogLoc.isShowing())
                dialogLoc.dismiss();
        }
        if (!barcode.equals("")) {
            if (mProcNo == PROC_BARCODE) {
                if (bottomSheetDialog.isShowing()){

                }else
                    _sts(R.id.barcode, barcode);
            }

            else if (mProcNo == PROC_QTY) {
               /* Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode = finalbarcode;*/
            }

            else if (mProcNo == PROC_LOCATION) _sts(R.id.location, barcode);
            else if (mProcNo == PROC_ORDER_ID) _sts(R.id.arrivalID, barcode);

            else if (mProcNo == PROC_LOT_NO)
                _sts(R.id.lotno, barcode);
            this.inputedEvent(barcode, true);
        }

     /*else
            Toast.makeText(getApplicationContext(), "Please Wait", Toast.LENGTH_SHORT).show();
}*/
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        Log.e(TAG, "deleteEvent");
        switch (mProcNo) {
            case PROC_ORDER_ID:
                _sts(R.id.arrivalID, barcode);
                break;
            case PROC_BARCODE:
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QTY:
                _sts(R.id.quantity, barcode);
                break;
            case PROC_LOT_NO:
                _sts(R.id.lotno, barcode);
                break;
            case PROC_EXPIRATION:
                _sts(R.id.expirationdate, barcode);
                break;
            case PROC_LOCATION:
                _sts(R.id.location, barcode);
                break;
        }
    }

    @Override
    public void nextProcess() {
        Log.e(TAG, "nextProcess");
        _sts(R.id.arrivalID, "");
        _sts(R.id.barcode, "");
        _sts(R.id.quantity, "");
        _sts(R.id.location, "");
        _stxtv(R.id.productName, "");
        _sts(R.id.Remarks, "");
        _sts(R.id.Standard1, "");
        _sts(R.id.Standard2, "");
        _sts(R.id.productCode, "");
        lot.setText("");
        expiration.setText("");
        pck_0.setText("");
        pck_1.setText("");
        pck_2.setText("");
        pck_3.setText("");
        pck_4.setText("");
        pck_5.setText("");
        mSelectedItem = 0;
        productList.clear();
        classificationspinner.setAdapter(null);
        lotexist = false;
        delete = false;
        clear = false;
        if (orderRequestSettings) {
            _sts(R.id.lotno, "");
            _sts(R.id.expirationdate, "");
        /*  addlotno = false;
            lotselect = false;*/
        }

        setBadge1(0);
        setBadge2(0);

        setProc(PROC_ORDER_ID);
        if (showKeyboard == false) {
            visible = false;
            numbrbtn.setText(R.string.showkeyboard);

            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);

            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
        layoutexpiration.setVisibility(View.GONE);
        layoutlot.setVisibility(View.GONE);

    }

    @Override
    public void onSucess(int status, ArrivalIDCheckResponse message) throws JsonIOException {
        progress.Dismiss();
        if(message.getCode().equalsIgnoreCase("0")) {
            setBadge1(Integer.parseInt(message.getPending_arrivals()));
            setBadge2(Integer.parseInt(message.getPending_orders()));

            scheduleList = message.getResults();

      /*      if (scheduleList.size()>1){
                pck_0.setText(scheduleList.get(0).getRsv_date());
                pck_1.setText(scheduleList.get(0).getCode());
                pck_2.setText(scheduleList.get(0).getComp_name());
                pck_3.setText(scheduleList.get(0).getQuantity());
                pck_4.setText(scheduleList.get(0).getLot());
                pck_5.setText(scheduleList.get(0).getExpiration_date());
                _stxtv(R.id.productName, scheduleList.get(0).getProduct_name());
                _sts(R.id.Remarks, scheduleList.get(0).getComment());
                _sts(R.id.Standard1, scheduleList.get(0).getSpec1());
                _sts(R.id.Standard2, scheduleList.get(0).getSpec2());
                _sts(R.id.productCode, scheduleList.get(0).getCode());
                showBottomSheetDialog();
            }else{
                pck_0.setText(scheduleList.get(0).getRsv_date());
                pck_1.setText(scheduleList.get(0).getCode());
                pck_2.setText(scheduleList.get(0).getComp_name());
                pck_3.setText(scheduleList.get(0).getQuantity());
                pck_4.setText(scheduleList.get(0).getLot());
                pck_5.setText(scheduleList.get(0).getExpiration_date());
                _stxtv(R.id.productName, scheduleList.get(0).getProduct_name());
                _sts(R.id.Remarks, scheduleList.get(0).getComment());
                _sts(R.id.Standard1, scheduleList.get(0).getSpec1());
                _sts(R.id.Standard2, scheduleList.get(0).getSpec2());
                _sts(R.id.productCode, scheduleList.get(0).getCode());
            }*/

            setProc(PROC_BARCODE);

            if(BaseActivity.getOptShelf()){
                if (!CommonUtilities.getConnectivityStatus(Arrival_ID_Activity.this))
                    CommonUtilities.openInternetDialog(Arrival_ID_Activity.this);
                else {
                    progress.Show();
                    StockClassificationRequest req = new StockClassificationRequest(adminID, app.getSerial(), BaseActivity.getShopId(), getResources().getString(R.string.version));
                    manager.StockClassificationAPI(req, stockClassificationcall);
                }
            }
        }
        else if(message.getCode().equalsIgnoreCase("14404")){
            U.beepError(this, null);
            showPopup(message.getMessage());
            setProc(PROC_ORDER_ID);
        }
        else if (message.getCode().equalsIgnoreCase("14405")){
            userpopup(message.getMessage());
        }
        else {
            U.beepError(this, message.getMessage());
            setProc(PROC_ORDER_ID);
        }
    }

    @Override
    public void onSucess(int status, BarcodeCheckResponse message) throws JsonIOException {
        progress.Dismiss();
        if(message.getCode().equalsIgnoreCase("0")){

            productList = message.getResults();

            if (productList.size()>1){
                pck_0.setText(productList.get(0).getRsv_date());
                pck_1.setText(productList.get(0).getCode());
                pck_2.setText(productList.get(0).getComp_name());
                pck_3.setText(productList.get(0).getQuantity());
                pck_4.setText(productList.get(0).getLot());
                pck_5.setText(productList.get(0).getExpiration_date());
                _stxtv(R.id.productName, productList.get(0).getProduct_name());
                _sts(R.id.Remarks, productList.get(0).getComment());
                _sts(R.id.Standard1, productList.get(0).getSpec1());
                _sts(R.id.Standard2, productList.get(0).getSpec2());
                _sts(R.id.productCode, productList.get(0).getCode());
                _sts(R.id.lotno, productList.get(0).getLot());

                NAUKAID = productList.get(0).getNyuka_id();
                PRODUCTID = productList.get(0).getProduct_id();

                _sts(R.id.quantity, "1");
                totalquantity = productList.get(0).getQuantity();
                attributetype = productList.get(0).getAttribute_type();
                bottomSheetDialog= new BottomSheetDialog(this);
                showBottomSheetDialog();
            }else{
                pck_0.setText(productList.get(0).getRsv_date());
                pck_1.setText(productList.get(0).getCode());
                pck_2.setText(productList.get(0).getComp_name());
                pck_3.setText(productList.get(0).getQuantity());
                pck_4.setText(productList.get(0).getLot());
                pck_5.setText(productList.get(0).getExpiration_date());
                _stxtv(R.id.productName, productList.get(0).getProduct_name());
                _sts(R.id.Remarks, productList.get(0).getComment());
                _sts(R.id.Standard1, productList.get(0).getSpec1());
                _sts(R.id.Standard2, productList.get(0).getSpec2());
                _sts(R.id.productCode, productList.get(0).getCode());
                _sts(R.id.lotno, productList.get(0).getLot());
                NAUKAID = productList.get(0).getNyuka_id();
                PRODUCTID = productList.get(0).getProduct_id();

                _sts(R.id.quantity, "1");
                totalquantity = productList.get(0).getQuantity();
                attributetype = productList.get(0).getAttribute_type();
                if(orderRequestSettings) {
                    if (attributetype.equals("0")) {
                        layoutlot.setVisibility(View.GONE);
                        layoutexpiration.setVisibility(View.GONE);
                    }
                    if (attributetype.equals("1")) {
                        layoutlot.setVisibility(View.VISIBLE);
                        layoutexpiration.setVisibility(View.GONE);
                        _sts(R.id.lotno, message.getResults().get(0).getLot());
                    } else if (attributetype.equals("2")) {
                        layoutlot.setVisibility(View.GONE);
                        layoutexpiration.setVisibility(View.VISIBLE);
                        _sts(R.id.expirationdate, message.getResults().get(0).getExpiration_date());
                    } else if (attributetype.equals("3")) {
                        layoutlot.setVisibility(View.VISIBLE);
                        layoutexpiration.setVisibility(View.VISIBLE);
                        _sts(R.id.expirationdate, message.getResults().get(0).getExpiration_date());
                        _sts(R.id.lotno, message.getResults().get(0).getLot());
                    }
                    setAttributeProc();
                }
                else{

                    setProc(PROC_QTY);
                }
            }

        }
        else if(message.getCode().equalsIgnoreCase("14404")){
            U.beepError(this, null);
            showPopup(message.getMessage());
            setProc(PROC_BARCODE);
        }
        else if (message.getCode().equalsIgnoreCase("14405")){
            userpopup(message.getMessage());
        }
        else {
            U.beepError(this, message.getMessage());
            setProc(PROC_BARCODE);
        }
    }


    public void setAttributeProc() {

        if (attributetype.equals("0")) {
            U.beepRecord(this, null);
            setProc(PROC_QTY);
        } else if (attributetype.equals("1")) {
            U.beepNext();
            if (_gts(R.id.lotno).equals(""))
                setProc(PROC_LOT_NO);
            else
                setProc(PROC_QTY);
            lotexist = true;
        } else if (attributetype.equals("2")) {
            U.beepNext();
            if (_gts(R.id.expirationdate).equals("")) {
                setProc(PROC_EXPIRATION);
            } else {
                //  showPopup("入荷指示に消費期限が含まれています。");
                setProc(PROC_QTY);
            }
        } else if (attributetype.equals("3")) {
            U.beepNext();
            if (_gts(R.id.lotno).equals(""))
                setProc(PROC_LOT_NO);
            else if (_gts(R.id.expirationdate).equals(""))
                setProc(PROC_EXPIRATION);
            else {
                setProc(PROC_QTY);
            }
            lotexist = true;
        }

        _sts(R.id.quantity, "1");
        BarcodeScanned = "1";
        //  setNyukaIdArray(nyukaIdArray);

    }

    @Override
    public void onSucess(int status, ArrivalsubmissionResponse message) throws JsonIOException {
        progress.Dismiss();
        if(message.getCode().equalsIgnoreCase("0")){

            if(directArrival){
                dialogLoc = new Dialog(this);
                dialogLoc.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // Set GUI of login screen
                dialogLoc.setContentView(R.layout.status_submit);
                dialogLoc.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialogLoc.getWindow();
                lp.copyFrom(window.getAttributes());
                dialogLoc.setCanceledOnTouchOutside(false);

                // Init button of login GUI
                TextView msg = (TextView) dialogLoc.findViewById(R.id.txt);
                msg.setText("品番" + _gts(R.id.productCode) + "をロケ" + _gts(R.id.location) + "に" +_gts(R.id.quantity)+"個棚入れしました");
                Button ok = (Button) dialogLoc.findViewById(R.id.btn_ok_dialog);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        U.beepNext();
                        dialogLoc.dismiss();
                    }
                });
                // Make dialog box visible.
                dialogLoc.show();
            }
            setBadge1(Integer.parseInt(message.getPending_arrivals()));
            setBadge2(Integer.parseInt(message.getPending_orders()));

            if(Integer.parseInt(message.getPending_arrivals() )== 0) {
                showPopup("箱ID入荷検品が完了しました。");
                U.beepFinish(this,null);
                nextProcess();
            } else {
                _sts(R.id.barcode, "");
                _sts(R.id.quantity, "");
                _sts(R.id.location, "");
                _stxtv(R.id.productName, "");
                lot.setText("");
                expiration.setText("");
                _sts(R.id.Remarks, "");
                _sts(R.id.Standard1, "");
                _sts(R.id.Standard2, "");
                _sts(R.id.productCode, "");

                pck_0.setText("");
                pck_1.setText("");
                pck_2.setText("");
                pck_3.setText("");
                pck_4.setText("");
                pck_5.setText("");
                productList.clear();
                NAUKAID = "";
                PRODUCTID = "";
                mSelectedItem=0;
                classificationspinner.setSelection(0);

                if(directArrival) {
                    U.beepKakutei(this, null);

                    //   U.beepFinish(this, null);
                } else {
                    // U.beepFinish(this, "検品データを登録しました。");
                    U.beepKakutei(this, "検品データを登録しました。");
                }

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Write whatever to want to do after delay specified (1 sec)
                        setProc(PROC_BARCODE);
                    }
                }, 250);

            }
            scheduleList = message.getResults();



        }
        else if (message.getCode().equalsIgnoreCase("14405")){
            userpopup(message.getMessage());
        }
        else {
            U.beepError(this, message.getMessage());
        }
    }


    @Override
    public void onSucess(int status, ResponseBody message) throws JsonIOException {
        progress.Dismiss();
        if(status == 200){
            if(clear) {
                mTextToSpeak.startSpeaking("Clear");
                nextProcess();
            } else if(delete){
                menu.showMenu();
            } else if (userclear){

                if (!CommonUtilities.getConnectivityStatus(Arrival_ID_Activity.this))
                    CommonUtilities.openInternetDialog(Arrival_ID_Activity.this);
                else {
                    progress.Show();
                    ArrivalIDCheckRequest req = new ArrivalIDCheckRequest(adminID, app.getSerial(), BaseActivity.getShopId(),_gts(R.id.arrivalID));
                    manager.ArrivalIDCheck(req, arrivalIDcheckcall);
                }

            }
            delete =false;
            clear = false;
            userclear = false;
        }

    }

    public String getclassificationId() {
        return this.classificationId;
    }

    public void setclassificationId(String id) {
        this.classificationId = id;
    }

    void userpopup(String msg){
        final SweetAlertDialog  dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        dialog.setCancelable(true);
        dialog.setTitleText(msg);
        dialog.setConfirmText("Yes");
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        _sts(R.id.barcode, "");
                        _sts(R.id.quantity, "");
                        _sts(R.id.location, "");
                        _stxtv(R.id.productName, "");
                        _sts(R.id.Remarks, "");
                        _sts(R.id.Standard1, "");
                        _sts(R.id.Standard2, "");
                        _sts(R.id.productCode, "");

                        if (!CommonUtilities.getConnectivityStatus(Arrival_ID_Activity.this))
                            CommonUtilities.openInternetDialog(Arrival_ID_Activity.this);
                        else {
                            userclear = true;
                            progress.Show();
                            ClearEventSubmissionRequest req = new ClearEventSubmissionRequest(adminID, app.getSerial(), BaseActivity.getShopId(), _gts(R.id.arrivalID));
                            manager.ClearEventAPI(req, clearEventcall);
                        }
                        dialog.dismiss();

                    }
                }, 1000);

            }
        });
        dialog.setCancelText("No");
        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nextProcess();
                        dialog.dismiss();
                    }
                }, 1000);

            }
        });

        dialog.show();
    }

    @Override
    public void onSucess(int status, StockClassificationResponse message) throws JsonIOException {
        progress.Dismiss();
        if(status == 200){
            classificationdata.clear();
            stockList = message.getResults();
            classificationdata.add("在庫区分を選択");
            for(StockClassificationListData val: stockList){
                String classification = val.getId() + "  :  " + val.getName();
                classificationdata.add(classification);
            }

            if (classificationdata.size() > 0) {
                ArrayAdapter adapter = new ArrayAdapter(this,_singleItemRes ,  classificationdata) {
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0) {
                            // Disable the first item from Spinner
                            // First item will be use for hint
                            return false;
                        } else {
                            return true;
                        }
                    }
                };

                adapter.setDropDownViewResource(_dropdownRes);
                classificationspinner.setAdapter(adapter);
            }

            U.beepNext();
            classificationspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    if (position>0) {
                        setclassificationId(stockList.get(position-1).getId());
                        Log.e(TAG, "Selected Classification " + getclassificationId());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    setclassificationId("");
                    Log.e(TAG, "Selected Classification" + getclassificationId() + "null");
                }
            });

        }
    }

    @Override
    public void onError(int status, ResponseBody error) {
        progress.Dismiss();

        if(status == 14405){
            final SweetAlertDialog  dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            dialog.setCancelable(true);
            dialog.setTitleText("予定数より多く入荷はできません。");
            dialog.setConfirmText("Yes");
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    }, 1000);
                }
            });
            dialog.show();
        } else {
            JSONObject jObjError = null;
            try {
                jObjError = new JSONObject(error.string());
                String message = jObjError.get("error").toString();

                U.beepError(this, message);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onFaliure() {
        progress.Dismiss();
    }

    @Override
    public void onNetworkFailure() {
        progress.Dismiss();
    }

    public void showPopup(String msg) {
        new SweetAlertDialog(this)
                .setTitleText(msg)
                .show();
    }

    public void dilaogprintqty() {
        dialoglist = new Dialog(Arrival_ID_Activity.this);
        dialoglist.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialoglist.setCanceledOnTouchOutside(false);
        dialoglist.setCancelable(false);
        dialoglist.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialoglist.setContentView(R.layout.arrival_list_popup);

        ListView list = (ListView) dialoglist.findViewById(R.id.orderPicking);
        ImageView close = (ImageView) dialoglist.findViewById(R.id.close);

        adapterlist = new Adap_arrivalID_list(this,scheduleList);
        list.setAdapter(adapterlist);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialoglist.dismiss();
            }
        });

        if (!dialoglist.isShowing()) {
            dialoglist.show();
        }

    }

    protected boolean showInfo() {
        Log.e(TAG, "showInfo");
        if (scheduleList == null) {
            return false;
        }

        if (getPopupWindow2() == null) {

            final PopupWindow popup2Window = new PopupWindow(this);
            // レイアウト設定
            View popupView = getLayoutInflater().inflate(R.layout.arrival_list_popup, null);
            popup2Window.setContentView(popupView);
            // 背景設定
            popup2Window.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));
            // タップ時に他のViewでキャッチされないための設定
            popup2Window.setOutsideTouchable(false);
            popup2Window.setFocusable(false);
            // 表示サイズの設定 今回は幅300dp
            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 324, getResources().getDisplayMetrics());
            popup2Window.setWindowLayoutMode((int) width, WindowManager.LayoutParams.WRAP_CONTENT);
            popup2Window.setWidth((int) width);
            popup2Window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            setPopupWindow2(popup2Window);
            ImageView close = (ImageView) getPopupWindow2().getContentView().findViewById(R.id.close);
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
        getPopupWindow2().showAtLocation(findViewById(R.id.arrivalID), Gravity.CENTER, 0, 32);
        return true;
    }

    protected ListViewItems initList(ListView lv) {

        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (Arrival_ID_list_data val:scheduleList) {
            data.add(data.newItem().add(R.id.arrivalID_txt, val.getNyuka_id())
                    .add(R.id.code_txt, val.getCode())
                    .add(R.id.total_txt, val.getRsv_cnt())
                    .add(R.id.comp_txt, val.getComplete_qty()));
        }
        ListViewAdapter adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.arrival_list_popup_row);
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }

    @OnClick(R.id.ll_listdialog) void listclick(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        if (productList==null){

        }else
        if (productList.size()!=0){
            bottomSheetDialog = new BottomSheetDialog(this);
            showBottomSheetDialog();
        }
    }

    private void showBottomSheetDialog() {

        bottomSheetDialog.setContentView(R.layout.newarrival_list);
        bottomSheetDialog.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);
        bottomSheetDialog.cancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        listvv = (ListView) bottomSheetDialog.findViewById(R.id.listview);
        ImageView back = (ImageView) bottomSheetDialog.findViewById(R.id.back);

        adaplist = new Adap_newArrival(this, productList) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == mSelectedItem) {
                    v.setBackgroundColor(Color.YELLOW);
                } else {
                    if (position % 2 == 1) {
                        v.setBackgroundColor(Color.GRAY);
                    } else {
                        v.setBackgroundColor(Color.WHITE);
                    }
                }
                return v;
            }
        };
        listvv.setAdapter(adaplist);
        adaplist.notifyDataSetChanged();

        listvv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pck_0.setText(productList.get(i).getRsv_date());
                pck_1.setText(productList.get(i).getCode());
                pck_2.setText(productList.get(i).getComp_name());
                pck_3.setText(productList.get(i).getQuantity());
                pck_4.setText(productList.get(i).getLot());
                pck_5.setText(productList.get(i).getExpiration_date());
                _stxtv(R.id.productName, productList.get(i).getProduct_name());
                _sts(R.id.Remarks, productList.get(i).getComment());
                _sts(R.id.Standard1, productList.get(i).getSpec1());
                _sts(R.id.Standard2, productList.get(i).getSpec2());
                _sts(R.id.productCode, productList.get(i).getCode());
                NAUKAID = productList.get(i).getNyuka_id();
                PRODUCTID = productList.get(i).getProduct_id();
          /*      if ( productList.get(i).getLot().equalsIgnoreCase("")){
                    _sts(R.id.lotno, productList.get(i).getLot());
                    setProc(PROC_EXPIRATION);

                }else{

                }*/

                attributetype = productList.get(i).getAttribute_type();

                if(orderRequestSettings) {
                    if (attributetype.equals("0")) {
                        layoutlot.setVisibility(View.GONE);
                        layoutexpiration.setVisibility(View.GONE);
                    }
                    if (attributetype.equals("1")) {
                        layoutlot.setVisibility(View.VISIBLE);
                        layoutexpiration.setVisibility(View.GONE);
                        _sts(R.id.lotno, productList.get(i).getLot());
                    } else if (attributetype.equals("2")) {
                        layoutlot.setVisibility(View.GONE);
                        layoutexpiration.setVisibility(View.VISIBLE);
                        _sts(R.id.expirationdate, productList.get(i).getExpiration_date());
                    } else if (attributetype.equals("3")) {
                        layoutlot.setVisibility(View.VISIBLE);
                        layoutexpiration.setVisibility(View.VISIBLE);
                        _sts(R.id.expirationdate, productList.get(i).getExpiration_date());
                        _sts(R.id.lotno, productList.get(i).getLot());
                    }
                    setAttributeProc();
                }
                else{
                    setProc(PROC_QTY);
                }

                mSelectedItem = i;
                bottomSheetDialog.cancel();
                bottomSheetDialog.dismiss();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                bottomSheetDialog.cancel();

                attributetype = productList.get(0).getAttribute_type();

                if(orderRequestSettings) {
                    if (attributetype.equals("0")) {
                        layoutlot.setVisibility(View.GONE);
                        layoutexpiration.setVisibility(View.GONE);
                    }
                    if (attributetype.equals("1")) {
                        layoutlot.setVisibility(View.VISIBLE);
                        layoutexpiration.setVisibility(View.GONE);
                        _sts(R.id.lotno, productList.get(0).getLot());
                    } else if (attributetype.equals("2")) {
                        layoutlot.setVisibility(View.GONE);
                        layoutexpiration.setVisibility(View.VISIBLE);
                        _sts(R.id.expirationdate, productList.get(0).getExpiration_date());
                    } else if (attributetype.equals("3")) {
                        layoutlot.setVisibility(View.VISIBLE);
                        layoutexpiration.setVisibility(View.VISIBLE);
                        _sts(R.id.expirationdate, productList.get(0).getExpiration_date());
                        _sts(R.id.lotno, productList.get(0).getLot());
                    }
                    setAttributeProc();
                }
                else{
                    setProc(PROC_QTY);
                }
            }
        });

        bottomSheetDialog.show();
    }

    @Override
    public void onBackPressed() {
        //    super.onBackPressed();
    }
}