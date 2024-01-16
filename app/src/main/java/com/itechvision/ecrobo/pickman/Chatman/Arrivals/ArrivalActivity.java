package com.itechvision.ecrobo.pickman.Chatman.Arrivals;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Adapter.Adap_arrivalLoc_list;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.AddArrival;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetArrival;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetPrinter;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.ArrivalWeight.ArrivalWeightRequest;
import com.itechvision.ecrobo.pickman.Models.DirectArrival.DirArrivalListData;
import com.itechvision.ecrobo.pickman.Models.DirectArrival.DirArrivalLoctionResult;
import com.itechvision.ecrobo.pickman.Models.DirectArrival.LocationReq;
import com.itechvision.ecrobo.pickman.Models.NyukaArrival.NyukaSelectReq;
import com.itechvision.ecrobo.pickman.Models.NyukaArrival.NyukaSelectResponse;
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
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;


public class ArrivalActivity extends BaseActivity implements View.OnClickListener, MainAsynListener, DataManager.Nyukacallback ,DataManager.ArrivalWeightcall,DataManager.LocationListcall{
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(id.actionbar) ActionBar actionbar;
    @BindView(id.gridExpiration) LinearLayout layoutexpiration;
    @BindView(id.gridLot) LinearLayout layoutlot;
    @BindView(id.ll_comment) LinearLayout ll_comment;


    @BindView(id.lotno) EditText lot;
    @BindView(id.add_layout) Button numbrbtn;
    @BindView(id.expirationdate) TextView expiration;
    @BindView(id.layout_main) RelativeLayout mainlayout;
    @BindView(id.layout_number) RelativeLayout layout;
    @BindView(id.listArrival) ListView lv;
    @BindView(id.batch_arrival) Button batcharrivalbtn;
    @BindView(id.spinnerlayout) RelativeLayout spinnerLayout;
    @BindView(id.partnoSpinner) Spinner partNospinner;
    @BindView(id.abc) EditText abc;
    @BindView(id.Remarks) EditText remarks;
    public TextView pck0;
    public TextView pck1;
    public TextView pck2;
    public TextView pck3;
    @BindView(id.ll_listdialog) LinearLayout ll_listdialog;
    public LinearLayout partnoLayout;
    int mSelectedItem = 0;
    public boolean lotexist = false;
    public TextView productName;
    protected ArrayList<String> nyukaIdArray = new ArrayList<String>();
    protected ArrayList<String> classificationIdArray = new ArrayList<String>();
    protected ArrayList<String> PartnoArray = new ArrayList<String>();
    protected ArrayList<String> qntyArray = new ArrayList<String>();
    public static List<Map<String, String>> mArrivalLot = new ArrayList<Map<String, String>>();
    public static List<Map<String, String>> mArrivalList = new ArrayList<Map<String, String>>();
    public static List<Map<String, String>> mmultiArrivalList = new ArrayList<Map<String, String>>();
    public static List<Map<String, String>> mBatchList = new ArrayList<Map<String, String>>();
    protected Map<String, String> mStockList = null;
    PopupWindow popupWindow;
    public static int count = 0;
    public Context mcontext = this;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    String TAG = ArrivalActivity.class.getSimpleName();
    public boolean ordernocheckbox = false, triplebarcode = false;
    private TextToSpeak mTextToSpeak;
    private boolean arrivalScheduleSelected = false;
    private boolean showKeyboard;
    private boolean lotselect = false;
    public String nyukaId = null;
    private boolean orderRequestSettings;
    private static final String TRUE = "TRUE";
    private static final String FALSE = "FALSE";
    private String directToStockSetting = "FALSE";
    // public String classificationId = "";
    private boolean submission = false;
    private boolean batcharrival = false, multicode = false;
    public String selectedUrl = null;
    public String quantity = "0";
    public boolean barchnge = false;
    protected int mProcNo = 0;
    public static final int PROC_ORDER_NO = 5;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_QTY = 2;
    public static final int PROC_LOT_NO = 3;
    public static final int PROC_EXPIRATION = 4;
    public static final int PROC_PARTNO = 6;
    public static int mRequestStatus = 0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_BARCODE = 2;
    public static final int REQ_LOT = 3;
    public static final int REQ_QTY = 4;
    String adminID = "";
    public String differ = "0";
    public String qnt = "";
    String attribute = "0";
    private boolean visible = false;
    ListViewAdapter adapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    DialogInterface.OnDismissListener onCancelListener ;
    public static boolean mNextBarcode = false;
    public String isNextBarcode = "", nyukaAdmin = "";
    Map<String, String> row1;
    public ListView listview;
    Dialog dialogList,dialog2;
    View dialogView;
    AlertDialog alertDialog;
    ECRApplication app = new ECRApplication();
    SweetAlertDialog sweetAlertDialog;
    DataManager manager;
    progresBar progress;
    DataManager.Nyukacallback nyukacallback;
    String msg = "",code="",ProductID="",Weightt="",use_cmr_order_support ="";
    JsonArray result1;
    HashMap<String, String> mHash  ;
    DataManager.ArrivalWeightcall  weightcall;
    ImageView close;
    int valueText2 =0;
    @BindView(R.id.listLocations) ListView listLocations;
    @BindView(R.id.loc_layout) LinearLayout loc_layout;
    @BindView(R.id.ll_abc) LinearLayout ll_abc;

    DataManager.LocationListcall locationlist ;
    Adap_arrivalLoc_list adap ;
    ArrayList<DirArrivalListData> arrloc;
    SweetAlertDialog popupdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);

        ButterKnife.bind(ArrivalActivity.this);

        weightcall = this;
        locationlist = this ;

        getIDs();

        Log.d(TAG,"On Create ");
        arrloc= new ArrayList<>();
        dialog2 = new Dialog(ArrivalActivity.this);

        ViewPager viewPager = (ViewPager) findViewById(id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        orderRequestSettings = BaseActivity.getLotPress();
        popupdialog= new SweetAlertDialog(this);


        if (orderRequestSettings){

        }else{
            if (BaseActivity.getUrl().equalsIgnoreCase("https://staging3.air-logi.com/service")||BaseActivity.getUrl().equalsIgnoreCase("https://www3.air-logi.com/service")) {
                LOTDIALOG();
            }
        }

        Log.e(TAG, "orderRequestSettings " + BaseActivity.getLotPress());
        productName = (TextView)findViewById(R.id.productName);
        nyukacallback = this;
        arrivalScheduleSelected = BaseActivity.getArrivalNyuka();
        triplebarcode = BaseActivity.getTripleBarcode();

        partnoLayout = (LinearLayout) findViewById(id.partnoLayout);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);

        directToStockSetting = (this.getDirectToStock()) ? TRUE : FALSE;
        if (directToStockSetting.equals(FALSE)) {
            batcharrivalbtn.setVisibility(View.GONE);
        } else batcharrivalbtn.setVisibility(View.VISIBLE);


        showKeyboard = BaseActivity.getaddKeyboard();
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
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        mTextToSpeak = new TextToSpeak(this);
        Log.e(TAG, "OnCreate");

        if (mProcNo == 0) nextProcess();

        Globals.getterList = new ArrayList<>();
        Log.e(TAG, "shopid  " + BaseActivity.getShopId());
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));

        mRequestStatus = REQ_INITIAL;

        new MainAsyncTask(this, Globals.Webservice.listPrinter, 1, ArrivalActivity.this, "Form", Globals.getterList, true).execute();


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



        batcharrivalbtn.setOnClickListener(this);

        partNospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    setNyukaId(nyukaIdArray.get(0));
                    setAttributeProc();
                    String code = PartnoArray.get(position);
                    _sts(R.id.productCode, code);
                    getList(code);
                    mSelectedItem = 0 ;
                    try {
                        if (mArrivalList.size()!=0) {


                            _sts(R.id.Remarks, mArrivalList.get(0 ).get("comment"));
                            _sts(R.id.Standard1, mArrivalList.get(0 ).get("spec1"));
                            _sts(R.id.Standard2, mArrivalList.get(0).get("spec2"));
                            productName.setText(mArrivalList.get(0).get("name"));
                            setNyukaId(mArrivalList.get(0).get("nyuka_id"));

                            pck0.setText(mArrivalList.get(0).get("sup_date"));
                            pck1.setText(mArrivalList.get(0).get("order_no"));
                            pck2.setText(mArrivalList.get(0).get("comp_name"));
                            pck3.setText(mArrivalList.get(0).get("qty"));
                            setQnty(mArrivalList.get(0).get("qty"));

                            pck2.setSelected(true);
                            progress.Show();
                            LocationReq req = new LocationReq(adminID, app.getSerial(), BaseActivity.getShopId(), getString(R.string.version), _gts(R.id.productCode));
                            manager.LocationList(req, locationlist);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        remarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!remarks.getText().toString().equalsIgnoreCase("")) {

//                    showdialog(remarks.getText().toString());

                    final Dialog dialog = new Dialog(ArrivalActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    // Set GUI of login screen
                    dialog.setContentView(R.layout.new_remark_dialog);
                    dialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    Window window = dialog.getWindow();
                    lp.copyFrom(window.getAttributes());

                    dialog.setCanceledOnTouchOutside(false);

                    // Init button of login GUI
                    TextView txt = (TextView) dialog.findViewById(R.id.txt);
                    txt.setText(remarks.getText().toString());
                    ImageView close = (ImageView) dialog.findViewById(R.id.icon_close);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    // Make dialog box visible.
                    dialog.show();


                   /* SweetAlertDialog dia = new SweetAlertDialog(ArrivalActivity.this);
                    dia.setContentText(remarks.getText().toString());
                    dia.setCancelable(true);
                    dia.show();*/
                }
            }
        });
    }

    //set title and icons on GVBB9BVGN9,NBVGN9,
    private void getIDs() {
        actionbarImplement(this, "入荷検品", " ",
                0, false, true, false);

        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        pck0 = (TextView)findViewById(R.id.pck_0) ;
        pck1 = (TextView)findViewById(R.id.pck_1) ;
        pck2 = (TextView)findViewById(R.id.pck_2) ;
        pck3 = (TextView)findViewById(R.id.pck_3) ;

        btnRed.setOnClickListener(this);
        relLayout1.setOnClickListener(this);

        manager = new DataManager();
        progress = new progresBar(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case id.relLayout1:
                menu.showMenu();
                break;

            case id.batch_arrival:
                if (batcharrival == false) {
                    batcharrivalbtn.setText("シングルに戻す");

                    batcharrivalbtn.setBackgroundColor(getResources().getColor(R.color.darkblue));
                    batcharrival = true;
                } else {
                    if (mBatchList.size() > 0)
                        showPopup("バッチリストあると変更できません。");
                    else {
                        batcharrival = false;
                        batcharrivalbtn.setText("入荷バッチ");
                        batcharrivalbtn.setBackgroundColor(getResources().getColor(R.color.blue));
                    }
                }
                break;

            case id.notif_count_red:
                Log.e(TAG, ">>>>>>>>>1111111" + getBadge2());
                if (getBadge2() != 0) {
                    showOrders();
                }
            default:

                break;
        }
    }

    @OnClick(id.enter)
    void enter() {
        String s = mBuff.toString();
        Log.e("ScannerbindActivity", "Keyput11222 " + mBuff);
        mBuff.delete(0, mBuff.length());
        Log.e("ScannerbindActivity", "Keyput11222 buff " + mBuff);
        inputedEvent(s);
    }

    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
        if (visible == false) {

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
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {

            visible = false;
            numbrbtn.setText("キーボードを表示する");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

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

    public void setNyukaId(String id) {
        this.nyukaId = id;
    }

    public String getNyukaId() {
        return this.nyukaId;
    }

    public void setNyukaIdArray(ArrayList arr) {
        this.nyukaIdArray = arr;
        setNyukaId(nyukaIdArray.get(0));
    }

    public void setQnty(String qnt) {
        this.qnt = qnt;
    }

    public String getQnty() {
        return this.qnt;
    }

    public void setQntyArray(ArrayList arr) {
        this.qntyArray = arr;
    }

    public void ArrivalLotdata(List<Map<String, String>> list) {
        mArrivalLot = list;
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {

            case PROC_BARCODE:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.barcode).setFocusableInTouchMode(true);
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                lv.setAdapter(null);
                setNyukaId(null);
                if (orderRequestSettings == true) {
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                    expiration.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                }
                break;
            case PROC_PARTNO:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                lv.setAdapter(null);
                setNyukaId(null);
                if (orderRequestSettings == true) {
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                    expiration.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                }
                break;
            case PROC_QTY:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setFocusableInTouchMode(true);
                if (orderRequestSettings == true) {
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                    expiration.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                }
                if (_gts(id.quantity).equals("1"))
                    mTextToSpeak.startSpeaking("1");
                break;
            case PROC_LOT_NO:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                expiration.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.lotno).setFocusableInTouchMode(true);
                break;
            case PROC_EXPIRATION:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                expiration.setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                expiration.setFocusableInTouchMode(true);
                showTruitonDatePickerDialog(expiration);
                break;
        }
    }

    public void showTruitonDatePickerDialog(View v) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnDismissListener(onCancelListener);



        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (expiration.getText().toString().equalsIgnoreCase("")){
                    setProc(PROC_EXPIRATION);
                }else{
                    setProc(PROC_QTY);
                }

            }
        });

        dialog.show();
    }

    public void inputedEvent(String buff, boolean isScaned) {
        String lot = "";
        switch (mProcNo) {
            case PROC_BARCODE:
                String barcode1 = _gts(id.barcode);
                if (barcode1.equalsIgnoreCase("")){
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }else

                if (dialog2.isShowing()){
                }else {
                    submission = false;
                    lv.setAdapter(null);
                    setNyukaId(null);
                    String Url = spDomain.getString("domain", null);
                    selectedUrl = Url;

                    if (orderRequestSettings == true) {
                        setProc(PROC_LOT_NO);
                        lotselect = true;
                    } else {
                        U.beepRecord(this, null);
                        setProc(PROC_QTY);
                    }

                    Globals.getterList = new ArrayList<>();
                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    Globals.getterList.add(new ParamsGetter("barcode", barcode1));
                    Globals.getterList.add(new ParamsGetter("check_length", triplebarcode + ""));

                    mRequestStatus = REQ_BARCODE;

                    new MainAsyncTask(this, Globals.Webservice.getArrival, 1, ArrivalActivity.this, "Form", Globals.getterList, true).execute();

                    barchnge = false;
                }
                break;

            case PROC_LOT_NO:

                lot = _gts(R.id.lotno);
        /*        if (lot.equalsIgnoreCase("")){
                    valueText2 =0 ;
                }else{
                    valueText2 = Integer.parseInt(lot);
                }*/
                if (lot.equalsIgnoreCase("") ) {
                    U.beepError(this, "ロット番号が必要");
                    break;
                }else if (lot.equalsIgnoreCase("0")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                }else if (lot.equalsIgnoreCase("00")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                }else if (lot.equalsIgnoreCase("000")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                }else if (lot.equalsIgnoreCase("0000")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                }else if (lot.equalsIgnoreCase("00000")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                }else if (lot.equalsIgnoreCase("000000")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                }else if (lot.equalsIgnoreCase("0000000")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                }else if (lot.equalsIgnoreCase("00000000")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                }else if (lot.equalsIgnoreCase("000000000")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                }else if (lot.equalsIgnoreCase("0000000000")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                }else if (lot.equalsIgnoreCase("00000000000")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                }else if (lot.equalsIgnoreCase("000000000000")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                }else if (lot.equalsIgnoreCase("0000000000000")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                }else if (lot.equalsIgnoreCase("00000000000000")){
                    U.beepError(this, "ロット番号は0で登録出来ません。");
                } else {
                    if (dialog2.isShowing()) {

                    } else {

                        String url = spDomain.getString("domain", null);
                        if ((url.equals("https://air-logi-st.air-logi.com/service") || url.equals("https://api.air-logi.com/service")) && BaseActivity.getShopId().equals("1090"))

                            Globals.getterList = new ArrayList<>();
                        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                        Globals.getterList.add(new ParamsGetter("barcode", _gts(id.barcode)));
                        Globals.getterList.add(new ParamsGetter("lot_no", lot));
                        Globals.getterList.add(new ParamsGetter("check_length", triplebarcode + ""));

                        mRequestStatus = REQ_LOT;

                        U.beepRecord(this, null);
                        if (attribute.equals("3"))
                            if (expiration.getText().toString().equalsIgnoreCase("")) {
                                setProc(PROC_EXPIRATION);
                            } else {
                                setProc(PROC_QTY);
                            }
                        else
                            setProc(PROC_QTY);
                    }

                }
                break;
            case PROC_EXPIRATION:
                if (dialog2.isShowing()){

                }else {
                    lotselect = false;
                    String loc = expiration.getText().toString();
                    if ("".equals(loc)) {
                        U.beepError(this, "賞味期限が必要です");
                        expiration.setFocusableInTouchMode(true);
                        break;
                    }
                    setProc(PROC_QTY);
                }
                break;
            case PROC_QTY: // 数量

                if (dialog2.isShowing()){

                }else {

                    lotselect = false;
                    String qty = _gts(id.quantity);
                    if (qty.equals(""))
                        qty = "1";
                    String barcode = _gts(id.barcode);
                    String lotno = "";
                    String expdate1 = "";

                    if (orderRequestSettings == true) {
                        lotno = _gts(id.lotno);
                        expdate1 = expiration.getText().toString();
                    }

                    if (isScaned) {

                        if (buff.equals(barcode)) {
                            U.beepSuccess();
                            qty = U.plusTo(qty, "1");
                            _sts(id.quantity, qty);
                            mTextToSpeak.startSpeaking(_gts(id.quantity));

                            break;
                        } else {

                            if (batcharrival == true) {
                                if (!getNyukaId().equals("999")) {
                                    checkQnty(_gts(id.barcode), _gts(id.quantity), _gts(id.lotno), expiration.getText().toString());

                                }
                                else {
                                    nextdata();
                                    setProc(PROC_BARCODE);
                                }
                                break;
                            } else {
                                int days = 0;
                                if (orderRequestSettings && (attribute.equals("2") || attribute.equals("3"))) {
                                    if (mArrivalLot.get(0).get("arrival_exp_flag").equals("1")) {

                                        try {
                                            String date = reverseDate(expdate1);
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                            Date expirationdate = sdf.parse(date);
                                            Log.e(TAG, "expirationdateeeeeee     " + expirationdate);

//                            Calendar calendar = Calendar.getInstance();
//                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                            String date = dateFormat.format(calendar.getTime());

                                            String formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                            Log.e(TAG, "currentdateeee      " + formattedDate);

                                            Date arrivaldate = sdf.parse(formattedDate);
                                            Log.e(TAG, "currentdateeee 111111     " + arrivaldate);
                                            days = U.getDaysDifference(arrivaldate, expirationdate);
                                            Log.e(TAG, "difff     " + days);
                                        } catch (Exception e) {
                                            Log.e(TAG, "Exceptionnnnn     " + e);
                                        }
                                        if (!mArrivalLot.get(0).get("arrival_exp_days").equals("")) {
                                            String exp_days = mArrivalLot.get(0).get("arrival_exp_days");
                                            Log.e(TAG, "exp_days     " + exp_days);
                                            if (U.compareNumeric(exp_days, days + "") == -1) {
                                                if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {

                                                } else {

                                                    //DIALOG  WEIGHT
                                                    sweetAlertDialog = new SweetAlertDialog(this);

                                                    sweetAlertDialog.setTitleText("入庫期限が切れています。続行しますか？")
                                                            .setConfirmText("Yes")
                                                            .setCancelText("No")
                                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                    stopTimer();
                                                                    submission = true;
//DIALOG
                                                                    sweetAlertDialog.dismiss();

                                                                    if (count == 0) {
                                                                        if (!getNyukaId().equals("999")) {
                                                                            checkQnty(_gts(id.barcode), _gts(id.quantity), _gts(id.lotno), _gts(id.expirationdate));
                                                                        }
                                                                        else
                                                                            sendRequest(_gts(id.barcode), _gts(id.quantity), _gts(id.lotno), _gts(id.expirationdate));
                                                                    } else {
                                                                        Toast.makeText(getApplicationContext(), "Wait", Toast.LENGTH_SHORT).show();
                                                                    }

                                                                }
                                                            });
                                                    sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            clearEvent();
                                                            sweetAlertDialog.dismiss();
                                                        }
                                                    });

                                                    sweetAlertDialog.show();
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            stopTimer();
                            mNextBarcode = true;
                            isNextBarcode = buff;

                            //DIALOG

                            if (count == 0) {
                                if (!getNyukaId().equals("999")) {
                                    checkQnty(barcode, qty, lotno, expdate1);
                                }
                                else
                                    sendRequest(barcode, qty, lotno, expdate1);
                            } else {
                                Toast.makeText(getApplicationContext(), "Wait", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }

                    if ("".equals(qty) || "0".equals(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                    if (batcharrival == true) {
                        if (!getNyukaId().equals("999")) {
                            checkQnty(barcode, qty, lotno, expdate1);
                        }
                        else{
                            nextdata();
                            setProc(PROC_BARCODE);}
                    } else {

                        int days = 0;
                        if (orderRequestSettings && (attribute.equals("2") || attribute.equals("3"))) {
                            if (mArrivalLot.get(0).get("arrival_exp_flag").equals("1")) {

                                try {
                                    String date = reverseDate(expdate1);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                    Date expirationdate = sdf.parse(date);
                                    Log.e(TAG, "expirationdateeeeeee     " + expirationdate);


                                    String formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                    Log.e(TAG, "currentdateeee      " + formattedDate);

                                    Date arrivaldate = sdf.parse(formattedDate);
                                    Log.e(TAG, "currentdateeee 111111     " + arrivaldate);
                                    days = U.getDaysDifference(arrivaldate, expirationdate);
                                    Log.e(TAG, "difff     " + days);
                                } catch (Exception e) {
                                    Log.e(TAG, "Exceptionnnnn     " + e);
                                }
                                if (!mArrivalLot.get(0).get("arrival_exp_days").equals("")) {
                                    String exp_days = mArrivalLot.get(0).get("arrival_exp_days");
                                    Log.e(TAG, "exp_days     " + exp_days);
                                    if (U.compareNumeric(exp_days, days + "") == -1) {
                                        if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {
                                            // A dialog is already open, wait for it to be dismissed, do nothing
                                        } else {
                                            sweetAlertDialog = new SweetAlertDialog(this);

                                            sweetAlertDialog.setTitleText("入庫期限が切れています。続行しますか？")
                                                    .setConfirmText("Yes")
                                                    .setCancelText("No")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            stopTimer();
                                                            submission = true;
                                                            sweetAlertDialog.dismiss();
                                                            if (count == 0) {
                                                                if (!getNyukaId().equals("999")) {
                                                                    checkQnty(_gts(id.barcode), _gts(id.quantity), _gts(id.lotno), expiration.getText().toString());
                                                                }
                                                                else
                                                                    sendRequest(_gts(id.barcode), _gts(id.quantity), _gts(id.lotno), expiration.getText().toString());
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "Wait", Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    });
                                            sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    clearEvent();
                                                    sweetAlertDialog.dismiss();
                                                }
                                            });

                                            sweetAlertDialog.show();
                                        }
                                        break;
                                    } /*else {
                                    stopTimer();
                                    submission = true;

                                    if (count == 0) {
                                        sendRequest(barcode, qty, lotno, expdate1);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Wait", Toast.L hjjb ENGTH_SHORT).show();
                                    }
                                }*/
                                }
                            }
                        }

                        stopTimer();
                        submission = true;

                        if (count == 0) {
                            if (!getNyukaId().equals("999")) {
                                checkQnty(barcode, qty, lotno, expdate1);
                            }
                            else
                                sendRequest(barcode, qty, lotno, expdate1);
                        } else {
                            Toast.makeText(getApplicationContext(), "Wait", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    void checkQnty(String barcode, String qty, String lotno, String expdate) {
        Log.e(TAG, "Qty is>>>>>>>>" + getQnty());

        int i = U.compareNumeric(_gts(id.quantity), getQnty());
        Log.e(TAG, "Qty is>>>>>>>>" + getQnty() + "  " + i);
        if (i < 0) {
            Log.e(TAG, "Qty is>>>>>>>>Quantity exceeedsss     " + i);
            SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            dialog.setCancelable(true);
            dialog.setTitleText("入荷数量が予定数をオーバーしています。");
            dialog.setContentText("よろしいですか？");
            dialog.setConfirmText("Yes");
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            differ = U.minusTo(_gts(id.quantity), getQnty());
                            dialog.dismiss();
                            if (batcharrival == true) {
                                nextdata();
                                setProc(PROC_BARCODE);
                            } else
                                sendRequest(barcode,qty,lotno,expdate);

                            dialog.dismiss();

                        }

                    }, 1000);

                }
            });
            dialog.setCancelText("No");
            dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    setProc(PROC_QTY);
                    dialog.dismiss();
                }
            });

            dialog.show();

        } else {
            if (batcharrival == true) {
                nextdata();
                setProc(PROC_BARCODE);
            } else
                sendRequest(barcode,qty,lotno,expdate);
        }
    }


    void sendRequest(String barcode, String qty, String lotno, String expdate) {
        ++count;

        if (getNyukaId() != null) {

            Globals.getterList.add(new ParamsGetter("admin_id", adminID));
            if (!multicode)
                Globals.getterList.add(new ParamsGetter("barcode", _gts(id.barcode)));
            else
                Globals.getterList.add(new ParamsGetter("barcode", _gts(id.productCode)));
            // current date nd time
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            String date = dateFormat.format(calendar.getTime());
            Log.e(TAG, "     date  " + date);

            //  Globals.getterList.add(new ParamsGetter("request_time", date));
            Globals.getterList.add(new ParamsGetter("qty", qty));
            Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
            Globals.getterList.add(new ParamsGetter("move_to_stock", directToStockSetting));
            Globals.getterList.add(new ParamsGetter("stock_type_id", ""));
            Globals.getterList.add(new ParamsGetter("lot_no", lotno));
            Globals.getterList.add(new ParamsGetter("expiration_date", expdate));
            if (getNyukaId() != null)
                Globals.getterList.add(new ParamsGetter("nyuka_id", getNyukaId()));
            Globals.getterList.add(new ParamsGetter("check_length", triplebarcode + ""));

            mRequestStatus = REQ_QTY;

            if (ordernocheckbox) {
                new MainAsyncTask(this, Globals.Webservice.addArrival, 1, ArrivalActivity.this, "Form", Globals.getterList, true).execute();
            } else
                new MainAsyncTask(this, Globals.Webservice.addArrival, 1, ArrivalActivity.this, "Form", Globals.getterList, true).execute();

            if (!differ.equals("0")) {
                showdialog("入荷予定数より" + differ + "個多く検品しています。");
            }
        } else
            U.beepError(this, "Select nyuka");
    }

    void showdialog(String msg) {
        // Create Object of Dialog class
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set GUI of login screen
        dialog.setContentView(R.layout.new_picking_dialog);
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
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
                dialog.dismiss();
            }
        });
        // Make dialog box visible.
        dialog.show();
    }

    public void nextdata() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("barcode", _gts(id.barcode));
        map.put("quantity", _gts(id.quantity));
        map.put("code", _gts(id.productCode));

        if (orderRequestSettings) {
            map.put("lotno", _gts(id.lotno));
            map.put("expiration",expiration.getText().toString());
        } else {
            map.put("lotno", "");
            map.put("expiration", "");
        }

        map.put("nyuka", getNyukaId());

        mBatchList.add(map);
        _sts(id.barcode, "");
        _sts(id.quantity, "");
        if (orderRequestSettings) {
            _sts(id.lotno, "");
            expiration.setText("");
        }
        setBadge2(mBatchList.size());
    }

    public void setAttributeProc() {


        if (attribute.equals("0")) {
            setProc(ArrivalActivity.PROC_QTY);
        } else if (attribute.equals("1")) {
            if (_gts(id.lotno).equals(""))
                setProc(ArrivalActivity.PROC_LOT_NO);
            else
                setProc(ArrivalActivity.PROC_QTY);
            lotexist = true;
        } else if (attribute.equals("2")) {
            if (expiration.getText().toString().equals("")) {
                setProc(ArrivalActivity.PROC_EXPIRATION);
            } else {
                showPopup("入荷指示に消費期限が含まれています。");
                setProc(ArrivalActivity.PROC_QTY);
            }
        } else if (attribute.equals("3")) {

            if (_gts(id.lotno).equals(""))
                setProc(ArrivalActivity.PROC_LOT_NO);
            else if (_gts(id.expirationdate).equals(""))
                setProc(ArrivalActivity.PROC_EXPIRATION);
            else {
                showPopup("入荷指示に消費期限が含まれています。");
                setProc(ArrivalActivity.PROC_QTY);
            }
            lotexist = true;
        }

        Log.e(TAG, "multicode    "+multicode);
        Log.e(TAG, "nyukaaa    "+getNyukaId());
        Log.e(TAG, "qty    "+mArrivalList.get(0).get("qty"));
        if(!multicode) {
            if (!getNyukaId().equalsIgnoreCase("999") && mArrivalList.get(0).get("qty").equalsIgnoreCase("0")) {
                _sts(id.quantity, "0");
                quantity = "0";
            }
        }
        else if(multicode){
            if (!getNyukaId().equalsIgnoreCase("999") && mmultiArrivalList.get(0).get("qty").equalsIgnoreCase("0")) {
                _sts(id.quantity, "0");
                quantity = "0";
            }
        }
        else {
            _sts(id.quantity, "1");
            quantity = "1";
        }
        //  setNyukaIdArray(nyukaIdArray);

    }

    // is product using lot no or expiration date
    public void setAttributeValue(String atr) {
        attribute = atr;
        setLayout();
    }

    public void setLayout()
    {
        if (attribute.equals("0"))
        {
            layoutlot.setVisibility(View.GONE);
            layoutexpiration.setVisibility(View.GONE);
        } else if (attribute.equals("1")) {
            layoutlot.setVisibility(View.VISIBLE);
            layoutexpiration.setVisibility(View.GONE);
        } else if (attribute.equals("2")) {
            layoutlot.setVisibility(View.GONE);
            layoutexpiration.setVisibility(View.VISIBLE);
            expiration = (EditText) findViewById(id.expirationdate);
        } else if (attribute.equals("3")) {
            layoutlot.setVisibility(View.VISIBLE);
            layoutexpiration.setVisibility(View.VISIBLE);
            expiration = (EditText) findViewById(id.expirationdate);
        }
    }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
//      _sts(id.orderNo,"");
        nextProcess();
    }

    @Override
    public void allclearEvent() {
    }

    @Override
    public void skipEvent() {
    }

    protected boolean showOrders() {
        Log.e(TAG, " showInfooooo");
//      startPackingBoxActivity();
        Log.e(TAG, "initiatePopuppppppppp");
        popupWindow = new PopupWindow(this);
        if (mBatchList == null) {
            return true;
        }

        // レイアウト設定
        View popupView = getLayoutInflater().inflate(R.layout.activity_batch_arrivals, null);
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

        Button close = (Button) getPopupWindow().getContentView().findViewById(id.packing_close_btn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        Button addlocation = (Button) getPopupWindow().getContentView().findViewById(id.btn_add_location);
        addlocation.setText("確定");
        addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                stopTimer();

                // current date nd time
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                String date = dateFormat.format(calendar.getTime());

                Globals.getterList = new ArrayList<>();
                Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId() + "     date  " + date);
                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
                Globals.getterList.add(new ParamsGetter("move_to_stock", directToStockSetting));
                Globals.getterList.add(new ParamsGetter("request_time", date));

                if (getNyukaId() != null)
                    Globals.getterList.add(new ParamsGetter("nyuka_id", getNyukaId()));
                Globals.getterList.add(new ParamsGetter("check_length", triplebarcode + ""));

                StringBuffer nyuka = new StringBuffer();
                StringBuffer bar = new StringBuffer();
                StringBuffer qty = new StringBuffer();
                StringBuffer lot1 = new StringBuffer();
                StringBuffer exp1 = new StringBuffer();
                for (Map<String, String> map : mBatchList) {
                    if (!map.get("quantity").equals("0")) {
                        bar.append("\t").append(map.get("code"));
                        qty.append("\t").append(map.get("quantity"));
                        nyuka.append("\t").append(map.get("nyuka"));
                        lot1.append("\t").append(map.get("lotno"));
                        exp1.append("\t").append(map.get("expiration"));
                    }
                }

                Globals.getterList.add(new ParamsGetter("nyuka_id", nyuka.substring(1)));
                Globals.getterList.add(new ParamsGetter("barcode", bar.substring(1)));
                Globals.getterList.add(new ParamsGetter("qty", qty.substring(1)));
                Globals.getterList.add(new ParamsGetter("lot_no", lot1.substring(1)));
                Globals.getterList.add(new ParamsGetter("expiration_date", exp1.substring(1)));

                mRequestStatus = REQ_QTY;
                if (count == 0)
                    new MainAsyncTask(ArrivalActivity.this, Globals.Webservice.addArrival, 1, ArrivalActivity.this, "Form", Globals.getterList, true).execute();
                else
                    Toast.makeText(getApplicationContext(), "Wait", Toast.LENGTH_SHORT).show();
            }
        });

        ListView lv = (ListView) getPopupWindow().getContentView().findViewById(id.lvPackingList);
        initWorkList(lv);

        // 画面中央に表示
        getPopupWindow().showAtLocation(findViewById(id.barcode), Gravity.CENTER, 0, 32);
        return false;

    }

    protected ListViewItems initWorkList(final ListView lv) {
        Log.e("NewPickingActivity", " initWorkList");
        lv.setAdapter(null);

        Log.e("NewPickingActivity", " initWorkList");

        ListViewItems data = new ListViewItems();
        for (int i = 0; i <= mBatchList.size() - 1; i++) {
            Map<String, String> row = mBatchList.get(i);
            Log.e("NewPickingActivity", " initWorkList_" + row);

            data.add(data.newItem().add(id.btc_ins_0, i + 1 + "")
                    .add(id.btc_ins_1, row.get("barcode"))
                    .add(id.btc_ins_2, row.get("quantity"))
            );
            Log.e("NewPickingActivity", "DATA >>>>" + data);
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.batch_arrival_list) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Log.e(TAG, "Batchleftt11  " + position);

                ImageView img = (ImageView) v.findViewById(id.delete_batch);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBatchList.remove(position);
                        Log.e(TAG, "Batchleftt  " + mBatchList);
                        initWorkList(lv);
                    }
                });
                return v;
            }
        };
        Log.e("NewPickingActivity", "LIst adapter >>>>");
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }

    public void getList(String part) {
        List<Map<String, String>> data = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        for (Map<String, String> map : mmultiArrivalList) {
            String _c = map.get("code");
            if (_c.equals(part)) {
                data.add(map);
                list.add(map.get("nyuka_id"));
            }
        }
        mArrivalList = data;
        setNyukaIdArray(list);

        initWorkList();
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                _sts(id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                _sts(id.quantity, buff);
                break;
            case PROC_LOT_NO:
                _sts(id.lotno, buff);
                break;

            case PROC_EXPIRATION:
                _sts(id.expirationdate, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {

        Log.e(TAG, "ScannedEvent submission is " + submission);

        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEvent");

            if (mProcNo == PROC_BARCODE) {
                // check for QR code
                if (BaseActivity.getUrl().equals("https://air-logi-st.air-logi.com/service") && BaseActivity.getShopId().equals("1011")) {
                    String result = "";
                    if (barcode.length() == 13) {
                        result = barcode.substring(0, barcode.length() - 1);
                        Log.e(TAG, "BArcode after chopping last character becomes " + result);
                        barcode = result;
                    } else if (barcode.length() == 14) {
                        result = barcode.substring(1, barcode.length() - 1);
                        Log.e(TAG, "BArcode after chopping first and last character becomes " + result);
                        barcode = result;
                    }
                }
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode = finalbarcode;

                _sts(id.barcode, barcode);
            }

            if (mProcNo == PROC_QTY) {
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode = finalbarcode;
                Log.e(TAG, "barcode111   " + barcode);
            }

            if (mProcNo == PROC_LOT_NO)
                _sts(id.lotno, barcode);
        }
        this.inputedEvent(barcode, true);
    }

    @Override
    public void enterEvent() {
        if (mProcNo == PROC_QTY) {
            String qty = _gts(id.quantity);
            String barcode = _gts(id.barcode);
            String lotno = "";
            String expdate = "";
            if (orderRequestSettings == true) {
                lotno = _gts(id.lotno);
                expdate = _gts(id.expirationdate);
            }
            sendRequest(barcode, qty, lotno, expdate);
        }
    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                _sts(id.barcode, barcode);
                break;
            case PROC_QTY: // 数量
                if ("".equals(barcode)) {
                } else
                    _sts(id.quantity, barcode);
                break;
            case PROC_LOT_NO: // 数量
                _sts(id.lotno, barcode);
                break;
            case PROC_EXPIRATION: // 数量
                _sts(id.lotno, barcode);
                break;
        }
    }

    public void nextProcess() {
        Log.e(TAG, "nextProcess");
        _sts(id.barcode, "");
        _sts(id.quantity, "");
        _stxtv(id.productName, "");
        _sts(id.Remarks, "");
        _sts(id.Standard1, "");
        _sts(id.Standard2, "");
        _sts(id.lotno, "");
        _sts(id.expirationdate, "");
        _sts(id.abc, "");
        ll_abc.setVisibility(View.GONE);

        lotexist = false;
        quantity = "0";

        differ = "0";
        batcharrival = false;
        batcharrivalbtn.setText("入荷バッチ");
        batcharrivalbtn.setBackgroundColor(getResources().getColor(R.color.blue));
        mBatchList.clear();
        setBadge2(0);

        pck0.setText("");
        pck1.setText("");
        pck2.setText("");
        pck3.setText("");
        mArrivalList.clear();

        partnoLayout.setVisibility(View.GONE);
        partNospinner.setAdapter(null);

        nyukaIdArray = null;
        classificationIdArray = null;
        multicode = false;
        arrloc.clear();
        loc_layout.setVisibility(View.GONE);
        arrloc.clear();
        // classification.setAdapter(null);
        setProc(PROC_BARCODE);
        barchnge = false;

        _gt(id.barcode).requestFocus();

        RelativeLayout layout = (RelativeLayout) findViewById(id.activity_arrival);
        layout.setBackgroundResource(R.color.white);

        layoutlot.setVisibility(View.GONE);
        layoutexpiration.setVisibility(View.GONE);
        dialog2 = new Dialog(ArrivalActivity.this);
    }


    public void getarrivalList(List<Map<String, String>> list) {

        mArrivalList = list;
        Log.e(TAG, "ae 1111    " + getNyukaId());

        setNyukaId(nyukaIdArray.get(0));
        setQnty(qntyArray.get(0));

        if (arrivalScheduleSelected && mArrivalList.size() > 1)
            mArrivalList.remove(mArrivalList.size() - 1);

        if (getNyukaId().equals("999") && arrivalScheduleSelected && !popupdialog.isShowing()) {
            showPopup("入荷予定がありません。");
            U.beepError(ArrivalActivity.this, null);
            _sts(id.barcode, "");
            setProc(PROC_BARCODE);
        } else {
            _sts(id.Remarks, mArrivalList.get(0).get("comment"));
            _sts(id.Standard1, mArrivalList.get(0).get("spec1"));
            _sts(id.Standard2, mArrivalList.get(0).get("spec2"));

            pck0.setText(mArrivalList.get(0).get("sup_date"));
            pck1.setText(mArrivalList.get(0).get("order_no"));
            pck2.setText(mArrivalList.get(0).get("comp_name"));
            pck3.setText(mArrivalList.get(0).get("qty"));

            mSelectedItem = 0;

            if (getNyukaId().equals("999")) {

                ll_comment.setVisibility(View.GONE);
                if (BaseActivity.getLotPress()) {
                    if (!attribute.equals("0")) {
                        if (attribute.equals("1")) {
                            _sts(R.id.lotno, "");
                            setProc(PROC_LOT_NO);
                        } else if (attribute.equals("2")) {
                            if (mProcNo != PROC_EXPIRATION) {
                                setProc(PROC_EXPIRATION);
                            }
                            _sts(R.id.expirationdate, "");
                        } else if (attribute.equals("3")) {
                            setProc(PROC_LOT_NO);
                            _sts(R.id.expirationdate, "");
                            _sts(R.id.lotno, "");
                        }
                    }
                }
            }else{
                ll_comment.setVisibility(View.VISIBLE);
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_arrival);
                layout.setBackgroundResource(R.color.white);
                if (BaseActivity.getLotPress()) {
                    if (!attribute.equals("0")) {

                        for (Map<String, String> map : mArrivalLot) {
                            if (map.get("nyuka_id").equals(getNyukaId())) {
                                if (attribute.equals("1")) {
                                    _sts(R.id.lotno, map.get("lot"));
                                    if (_gts(R.id.lotno).equals("")) setProc(PROC_LOT_NO);
                                } else if (attribute.equals("2")) {

                                    if (!map.get("expiration_date").equals("")) {
                                        ShowPOP();
                                        _sts(R.id.expirationdate, map.get("expiration_date"));
                                    } else if (mProcNo != PROC_EXPIRATION)
                                        setProc(PROC_EXPIRATION);
                                } else if (attribute.equals("3")) {

                                    if (!map.get("expiration_date").equals(""))
                                        ShowPOP();
                                    _sts(R.id.lotno, map.get("lot"));
                                    _sts(R.id.expirationdate, map.get("expiration_date"));
                                    if (_gts(R.id.lotno).equals(""))
                                        setProc(PROC_LOT_NO);
                                    else if (_gts(R.id.expirationdate).equals("") && mProcNo != PROC_EXPIRATION)
                                        setProc(PROC_EXPIRATION);
                                }
                            }
                        }

                               /* for (int i=0; i<mArrivalLot.size();i++){
                                    if (mArrivalLot.get(i).get("nyuka_id").equalsIgnoreCase(getNyukaId())){

                                     ujnhi.m,.///m,./       if (attribute.equals("1") && !mArrivalLot.get(i).get("lot").equals("")) {
                                                _sts(R.id.lotno, mArrivalLot.get(i).get("lot"));
                                            } else if (attribute.equals("2") && !mArrivalLot.get(i).get("expiration_date").equals("")) {
                                                showPopup("入荷指示に消費期限が含まれています。");
                                                _sts(R.id.expirationdate, mArrivalLot.get(i).get("expiration_date"));
                                            } else if (attribute.equals("3")) {

                                                if (!mArrivalLot.get(i).get("expiration_date").equals("")) {
                                                    showPopup("入荷指示に消費期限が含まれています。");
                                                    _sts(R.id.expirationdate, mArrivalLot.get(i).get("expiration_date"));
                                                }
                                                if (!mArrivalLot.get(i).get("lot").equals(""))
                                                    _sts(R.id.lotno,mArrivalLot.get(i).get("lot"));
                                            }
                                        }

                                    }*/
                    }


                        /*        for (Map<String, String> map : mArrivalLot) {
                                if (map.get("nyuka_id").equals(getNyukaId())) {
                                    if (attribute.equals("1") && !map.get("lot").equals("")) {
                                        _sts(R.id.lotno, map.get("lot"));
                                    } else if (attribute.equals("2") && !map.get("expiration_date").equals("")) {
                                        showPopup("入荷指示に消費期限が含まれています。");
                                        _sts(R.id.expirationdate, map.get("expiration_date"));
                                    } else if (attribute.equals("3")) {

                                        if (!map.get("expiration_date").equals("")) {
                                            showPopup("入荷指示に消費期限が含まれています。");
                                            _sts(R.id.expirationdate, map.get("expiration_date"));
                                        }
                                        if (!map.get("lot").equals(""))
                                            _sts(R.id.lotno, map.get("lot"));
                                    }
                                }
                            }*/

                }
            }


          /*  if (getNyukaId().equals("999")) {

                ll_comment.setVisibility(View.GONE);
                if (BaseActivity.getLotPress()) {
                    if (!attribute.equals("0")) {
                        if (attribute.equals("1")) {
                            _sts(id.lotno, "");
                            setProc(PROC_LOT_NO);
                        } else if (attribute.equals("2")) {
                            if (mProcNo != PROC_EXPIRATION) {
                                setProc(PROC_EXPIRATION);
                            }
                            _sts(id.expirationdate, "");
                        } else if (attribute.equals("3")) {
                            setProc(PROC_LOT_NO);
                            _sts(id.expirationdate, "");
                            _sts(id.lotno, "");
                        }
                    }
                }
            } else {
                ll_comment.setVisibility(View.VISIBLE);
                RelativeLayout layout = (RelativeLayout) findViewById(id.activity_arrival);
                layout.setBackgroundResource(R.color.white);
                if (BaseActivity.getLotPress()) {
                    if (!attribute.equals("0")) {
                        for (Map<String, String> map : mArrivalLot) {
                            if (map.get("nyuka_id").equals(getNyukaId())) {
                                if (attribute.equals("1") && !map.get("lot").equals("")) {
                                    _sts(id.lotno, map.get("lot"));
                                } else if (attribute.equals("2") && !map.get("expiration_date").equals("")) {
                                    if(arrivalScheduleSelected){
                                        if (list.size() == 1)
                                            showPopup("入荷指示に消費期限が含まれています。");}
                                    else{
                                        if (list.size() == 2)
                                            showPopup("入荷指示に消費期限が含まれています。");}

                                    _sts(id.expirationdate, map.get("expiration_date"));
                                } else if (attribute.equals("3")) {

                                    if (!map.get("expiration_date").equals("")) {
                                        if(arrivalScheduleSelected){
                                            if (list.size() == 1)
                                                showPopup("入荷指示に消費期限が含まれています。");}
                                        else{
                                            if (list.size() == 2)
                                                showPopup("入荷指示に消費期限が含まれています。");}
                                        _sts(id.expirationdate, map.get("expiration_date"));
                                    }
                                    if (!map.get("lot").equals(""))
                                        _sts(id.lotno, map.get("lot"));
                                }
                            }
                        }
                    }
                }
            }
*/
        /*    if (arrivalScheduleSelected) {
                if (list.size() > 1)
                    listviewDialog();
            } else {
                if (list.size() > 2)
                    listviewDialog();
            }*/

            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mArrivalList.size() > 2) {
                        click() ;
                    }

                }
            }, 500);





            if (!mArrivalList.get(0).get("admin_id").equals("") && !mArrivalList.get(0).get("admin_id").equals(adminID)) {
      /*       if (attribute.equals("1")) {
                _sts(id.lotno, "");
                setProc(PROC_LOT_NO);
            } else if (attribute.equals("2")) {
                if (mProcNo != PROC_EXPIRATION) {
                    setProc(PROC_EXPIRATION);
                }
                _sts(id.expirationdate, "");
            } else if (attribute.equals("3")) {
                setProc(PROC_LOT_NO);
                _sts(id.expirationdate, "");
                _sts(id.lotno, "");
            }*/

                showPopup("他のユーザーが作業中です。");
            }else{
            /*   if (attribute.equals("1")) {
                _sts(id.lotno, "");
                setProc(PROC_LOT_NO);
            } else if (attribute.equals("2")) {
                if (mProcNo != PROC_EXPIRATION) {
                    setProc(PROC_EXPIRATION);
                }
                _sts(id.expirationdate, "");
            } else if (attribute.equals("3")) {
                setProc(PROC_LOT_NO);
                _sts(id.expirationdate, "");
                _sts(id.lotno, "");
            }*/
            }
        }
    }

    public void getmultiPartarrivalList(List<Map<String, String>> list, boolean multi, List<String> part) {
        Log.e(TAG, "getmultiPartarrivalList    " + mmultiArrivalList);
        mmultiArrivalList = list;
        multicode = multi;
        PartnoArray = (ArrayList<String>) part;

    }

    protected void initWorkList() {

        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        ArrayList<Integer> used = new ArrayList<>();

        if (arrivalScheduleSelected && mArrivalList.size() > 1)
            mArrivalList.remove(mArrivalList.size() - 1);

        for (int i = 0; i <= mArrivalList.size() - 1; i++) {
            row1 = mArrivalList.get(i);

            data.add(data.newItem().add(id.pck_0, row1.get("sup_date"))
                    .add(id.pck_1, row1.get("order_no"))
                    .add(id.pck_2, row1.get("comp_name"))
                    .add(id.pck_3, row1.get("qty"))
            );
            used.add(i);
        }

        adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.arrival_list_row) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                if (position == mSelectedItem)
                {
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
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _sts(R.id.Remarks, mArrivalList.get(position).get("comment"));
                _sts(R.id.Standard1, mArrivalList.get(position).get("spec1"));
                _sts(R.id.Standard2, mArrivalList.get(position).get("spec2"));


                mSelectedItem = position;
                adapter.notifyDataSetChanged();
                if (nyukaIdArray != null && nyukaIdArray.size() > position) {
                    setNyukaId(nyukaIdArray.get(position));
                    setQnty(qntyArray.get(position));


                    if (getNyukaId().equals("999")) {

                        ll_comment.setVisibility(View.GONE);
                        if (BaseActivity.getLotPress()) {
                            if (!attribute.equals("0")) {
                                if (attribute.equals("1")) {
                                    _sts(R.id.lotno, "");
                                    setProc(PROC_LOT_NO);
                                } else if (attribute.equals("2")) {
                                    if (mProcNo != PROC_EXPIRATION) {
                                        setProc(PROC_EXPIRATION);
                                    }
                                    _sts(R.id.expirationdate, "");
                                } else if (attribute.equals("3")) {
                                    setProc(PROC_LOT_NO);
                                    _sts(R.id.expirationdate, "");
                                    _sts(R.id.lotno, "");
                                }
                            }
                        }
                    } else {
                        ll_comment.setVisibility(View.VISIBLE);
                        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_arrival);
                        layout.setBackgroundResource(R.color.white);
                        if (BaseActivity.getLotPress()) {
                            if (!attribute.equals("0")) {
                                for (Map<String, String> map : mArrivalLot) {
                                    if (map.get("nyuka_id").equals(getNyukaId())) {
                                        if (attribute.equals("1") && !map.get("lot").equals("")) {
                                            _sts(R.id.lotno, map.get("lot"));
                                        } else if (attribute.equals("2") && !map.get("expiration_date").equals("")) {
                                            showPopup("入荷指示に消費期限が含まれています。");
                                            _sts(R.id.expirationdate, map.get("expiration_date"));
                                        } else if (attribute.equals("3")) {

                                            if (!map.get("expiration_date").equals("")) {
                                                showPopup("入荷指示に消費期限が含まれています。");
                                                _sts(R.id.expirationdate, map.get("expiration_date"));
                                            }
                                            if (!map.get("lot").equals(""))
                                                _sts(R.id.lotno, map.get("lot"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        Log.e(TAG, "selectedNyukaaadatasize   " + data.getData().size());
        if (data.getData().size() > 0) {
            lv.setItemChecked(0, true);
            lv.setSelection(0);
            Log.e(TAG, "selectedNyukaaa   " + nyukaIdArray.get(0));
            setNyukaId(nyukaIdArray.get(0));
            setQnty(qntyArray.get(0));

            if (getNyukaId().equals("999") && arrivalScheduleSelected && !popupdialog.isShowing()) {

                showPopup("入荷予定がありません。");
                U.beepError(ArrivalActivity.this, null);
                _sts(id.barcode, "");
                setProc(PROC_BARCODE);

            }
        }
    }

    public String reverseDate(String date) {

        String yr = date.substring(0, 4);
        Log.e(TAG, " year    " + yr);
        String mon = date.substring(4, 6);
        Log.e(TAG, " month    " + mon);
        String _d = date.substring(6);
        Log.e(TAG, " _d    " + _d);
        String result = _d + "-" + mon + "-" + yr;

        return result;
    }

    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {
        HashMap<String, String> mHash = new HashMap<>();
        Log.e(TAG, result.toString());
        count = 0;
        try {

            String response = result.toString();
            JsonPullParser parser = JsonPullParser.newParser(response);
            JsonHash map1 = JsonHash.fromParser(parser);
            Log.e(TAG, " " + map1);

            code = map1.getStringOrNull("code");
            msg = map1.getStringOrNull("message");

            if (directToStockSetting.equalsIgnoreCase("TRUE")) {

                if (map1.containsKey("list_stock")) {
                    String listStock = map1.getStringOrNull("list_stock");
                    if (listStock.equalsIgnoreCase("0")) {
                        loc_layout.setVisibility(View.GONE);
                    } else {
                        loc_layout.setVisibility(View.VISIBLE);
                    }
                }
            }

            result1 = map1.getJsonArrayOrNull("results");
            /*if (map1.containsKey("use_cmr_order_support")) {
                if (map1.getStringOrNull("use_cmr_order_support").equalsIgnoreCase("1")) {
                    ll_abc.setVisibility(View.VISIBLE);
                } else {
                    ll_abc.setVisibility(View.GONE);
                }
            }
*/

            if (map1.containsKey("weight")) {
                Weightt = map1.getStringOrNull("weight");
                ProductID = map1.getStringOrNull("product_id");

                if (Weightt.equalsIgnoreCase("")||Weightt.equalsIgnoreCase("0")){
                    dilaogCustomercancel();
                }else{
                    Result();
                }

            }else{
                Result();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {
        if (count > 50) {
            showPopup("ネットワークに接続をできません、\n ネットワーク確認してください。");
            count = 0;
        } else // Repeate the request 10 times if not successful
        {
            count++;

            if (mRequestStatus == REQ_BARCODE) {
                new MainAsyncTask(this, Globals.Webservice.getArrival, 1, ArrivalActivity.this, "Form", Globals.getterList, true).execute();
//            } else if (mRequestStatus == REQ_LOT) {

//                new MainAsyncTask(this, Globals.Webservice.getArrivalLot, 1, ArrivalActivity.this, "Form", Globals.getterList, true).execute();
            } else if (mRequestStatus == REQ_QTY) {
                new MainAsyncTask(this, Globals.Webservice.addArrival, 1, ArrivalActivity.this, "Form", Globals.getterList, true).execute();
            } else if (mRequestStatus == REQ_INITIAL) {
                new MainAsyncTask(this, Globals.Webservice.listPrinter, 1, ArrivalActivity.this, "Form", Globals.getterList, true).execute();
            }
        }
    }

    public void showPopup(String msg) {
        popupdialog= new SweetAlertDialog(this);
        popupdialog.setTitleText(msg);
        popupdialog.show();
    }

    public void listviewDialog() {

        Rect displayRectangle = new Rect();
        Window window = ArrivalActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        AlertDialog.Builder builder = new AlertDialog.Builder(ArrivalActivity.this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.custom, viewGroup, false);
        dialogView.setMinimumWidth((int) (displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int) (displayRectangle.height() * 1f));

        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        listview = (ListView) dialogView.findViewById(id.listview);

        ImageView close = (ImageView) dialogView.findViewById(id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "asdfgyhjvn   1111111  ");

                //    NewinitWorkListDialog();
                alertDialog.dismiss();
            }
        });

        initWorkListDialog();

        alertDialog.show();
    }

    protected void initWorkListDialog() {
        listview.setAdapter(null);
        ListViewItems data = new ListViewItems();
        ArrayList<Integer> used = new ArrayList<>();

        for (int i = 0; i <= mArrivalList.size() - 1; i++) {
            Map<String, String> row = mArrivalList.get(i);

            data.add(data.newItem().add(id.pck_0, row.get("sup_date"))
                    .add(id.pck_1, row.get("order_no"))
                    .add(id.pck_2, row.get("comp_name"))
                    .add(id.pck_3, row.get("qty"))
            );

            used.add(i);

        }

        adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.arrival_list_row_dialog) {
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
        listview.setAdapter(adapter);

        // 単一選択モードにする
        listview.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                _sts(R.id.Remarks, mArrivalList.get(position).get("comment"));
                _sts(R.id.Standard1, mArrivalList.get(position).get("spec1"));
                _sts(R.id.Standard2, mArrivalList.get(position).get("spec2"));

                pck0.setText(mArrivalList.get(position).get("sup_date"));
                pck1.setText(mArrivalList.get(position).get("order_no"));
                pck2.setText(mArrivalList.get(position).get("comp_name"));
                pck3.setText(mArrivalList.get(position).get("qty"));
                productName.setText(mArrivalList.get(position).get("name"));
                mSelectedItem = position;

                if (nyukaIdArray != null && nyukaIdArray.size() > position) {
                    setNyukaId(nyukaIdArray.get(position));
                    setQnty(qntyArray.get(position));

                    if (getNyukaId().equals("999")) {

                        ll_comment.setVisibility(View.GONE);
                        if (BaseActivity.getLotPress()) {
                            if (!attribute.equals("0")) {
                                if (attribute.equals("1")) {
                                    _sts(R.id.lotno, "");
                                    setProc(PROC_LOT_NO);
                                } else if (attribute.equals("2")) {
                                    if (mProcNo != PROC_EXPIRATION) {
                                        setProc(PROC_EXPIRATION);
                                    }
                                    _sts(R.id.expirationdate, "");
                                } else if (attribute.equals("3")) {
                                    setProc(PROC_LOT_NO);
                                    _sts(R.id.expirationdate, "");
                                    _sts(R.id.lotno, "");
                                }
                            }
                        }
                    } else {
                        ll_comment.setVisibility(View.VISIBLE);
                        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_arrival);
                        layout.setBackgroundResource(R.color.white);
                        if (BaseActivity.getLotPress()) {
                            if (!attribute.equals("0")) {

                                for (Map<String, String> map : mArrivalLot) {
                                    if (map.get("nyuka_id").equals(getNyukaId())) {
                                        if (attribute.equals("1")) {
                                            _sts(R.id.lotno, map.get("lot"));
                                            if (_gts(R.id.lotno).equals("")) setProc(PROC_LOT_NO);
                                        } else if (attribute.equals("2")) {

                                            if (!map.get("expiration_date").equals("")) {
                                                ShowPOP();
                                                _sts(R.id.expirationdate, map.get("expiration_date"));
                                            } else if (mProcNo != PROC_EXPIRATION)
                                                setProc(PROC_EXPIRATION);
                                        } else if (attribute.equals("3")) {

                                            if (!map.get("expiration_date").equals(""))
                                                ShowPOP();
                                            _sts(R.id.lotno, map.get("lot"));
                                            _sts(R.id.expirationdate, map.get("expiration_date"));
                                            if (_gts(R.id.lotno).equals(""))
                                                setProc(PROC_LOT_NO);
                                            else if (_gts(R.id.expirationdate).equals("") && mProcNo != PROC_EXPIRATION)
                                                setProc(PROC_EXPIRATION);
                                        }
                                    }
                                }

                               /* for (int i=0; i<mArrivalLot.size();i++){
                                    if (mArrivalLot.get(i).get("nyuka_id").equalsIgnoreCase(getNyukaId())){

                                            if (attribute.equals("1") && !mArrivalLot.get(i).get("lot").equals("")) {
                                                _sts(R.id.lotno, mArrivalLot.get(i).get("lot"));
                                            } else if (attribute.equals("2") && !mArrivalLot.get(i).get("expiration_date").equals("")) {
                                                showPopup("入荷指示に消費期限が含まれています。");
                                                _sts(R.id.expirationdate, mArrivalLot.get(i).get("expiration_date"));
                                            } else if (attribute.equals("3")) {

                                                if (!mArrivalLot.get(i).get("expiration_date").equals("")) {
                                                    showPopup("入荷指示に消費期限が含まれています。");
                                                    _sts(R.id.expirationdate, mArrivalLot.get(i).get("expiration_date"));
                                                }
                                                if (!mArrivalLot.get(i).get("lot").equals(""))
                                                    _sts(R.id.lotno,mArrivalLot.get(i).get("lot"));
                                            }
                                        }

                                    }*/
                            }


                        /*        for (Map<String, String> map : mArrivalLot) {
                                if (map.get("nyuka_id").equals(getNyukaId())) {
                                    if (attribute.equals("1") && !map.get("lot").equals("")) {
                                        _sts(R.id.lotno, map.get("lot"));
                                    } else if (attribute.equals("2") && !map.get("expiration_date").equals("")) {
                                        showPopup("入荷指示に消費期限が含まれています。");
                                        _sts(R.id.expirationdate, map.get("expiration_date"));
                                    } else if (attribute.equals("3")) {

                                        if (!map.get("expiration_date").equals("")) {
                                            showPopup("入荷指示に消費期限が含まれています。");
                                            _sts(R.id.expirationdate, map.get("expiration_date"));
                                        }
                                        if (!map.get("lot").equals(""))
                                            _sts(R.id.lotno, map.get("lot"));
                                    }
                                }
                            }*/

                        }
                    }
                }

                alertDialog.dismiss();
                if (position == 0) {
                    if (!mArrivalList.get(position).get("admin_id").equals("") && !mArrivalList.get(position).get("admin_id").equals(adminID))
                        showPopup("予定外入荷です。");
                } else
                    sendNyukaRequest(nyukaIdArray.get(position));
            }
        });


        if (data.getData().size() > 0) {
            lv.setItemChecked(0, true);
            lv.setSelection(0);
            Log.e(TAG, "selectedNyukaaa   " + nyukaIdArray.get(0));
            setNyukaId(nyukaIdArray.get(0));
            setQnty(qntyArray.get(0));

            if (getNyukaId().equals("999") && arrivalScheduleSelected && !popupdialog.isShowing()) {
                Log.e(TAG,"arrivalScheduleSelected  12111  ");
                showPopup("入荷予定がありません。");
                U.beepError(ArrivalActivity.this, null);
                _sts(id.barcode, "");
                setProc(PROC_BARCODE);
            }
        }
    }


  /*  protected void NewinitWorkListDialog() {

                 Log.e(TAG, "asdfghjknmxcvbn   ");

                _sts(R.id.Remarks, mArrivalList.get(0).get("comment"));
                _sts(R.id.Standard1, mArrivalList.get(0).get("spec1"));
                _sts(R.id.Standard2, mArrivalList.get(0).get("spec2"));

                pck0.setText(mArrivalList.get(0).get("sup_date"));
                pck1.setText(mArrivalList.get(0).get("order_no"));
                pck2.setText(mArrivalList.get(0).get("comp_name"));
                pck3.setText(mArrivalList.get(0).get("qty"));

                mSelectedItem = 0;

                if (nyukaIdArray != null && nyukaIdArray.size() > 0) {
                    setNyukaId(nyukaIdArray.get(0));
                    setQnty(qntyArray.get(0));

                    if (getNyukaId().equals("999")) {

                        ll_comment.setVisibility(View.GONE);
                        if (BaseActivity.getLotPress()) {
                            if (!attribute.equals("0")) {
                                if (attribute.equals("1")) {
                                    _sts(R.id.lotno, "");
                                    setProc(PROC_LOT_NO);
                                } else if (attribute.equals("2")) {
                                    if (mProcNo != PROC_EXPIRATION) {
                                        setProc(PROC_EXPIRATION);
                                    }
                                    _sts(R.id.expirationdate, "");
                                } else if (attribute.equals("3")) {
                                    setProc(PROC_LOT_NO);
                                    _sts(R.id.expirationdate, "");
                                    _sts(R.id.lotno, "");
                                }
                            }
                        }
                    } else {
                        ll_comment.setVisibility(View.VISIBLE);
                        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_arrival);
                        layout.setBackgroundResource(R.color.white);
                        if (BaseActivity.getLotPress()) {
                            if (!attribute.equals("0")) {
                                for (Map<String, String> map : mArrivalLot) {
                                    if (map.get("nyuka_id").equals(getNyukaId())) {
                                        if (attribute.equals("1") && !map.get("lot").equals("")) {
                                            _sts(R.id.lotno, map.get("lot"));
                                        } else if (attribute.equals("2") && !map.get("expiration_date").equals("")) {
                                            showPopup("入荷指示に消費期限が含まれています。");
                                            _sts(R.id.expirationdate, map.get("expiration_date"));
                                        } else if (attribute.equals("3")) {

                                            if (!map.get("expiration_date").equals("")) {
                                                showPopup("入荷指示に消費期限が含まれています。");
                                                _sts(R.id.expirationdate, map.get("expiration_date"));
                                            }
                                            if (!map.get("lot").equals(""))
                                                _sts(R.id.lotno, map.get("lot"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

        sendNyukaRequest(nyukaIdArray.get(0));

    }*/

    @OnClick(id.ll_listdialog) void click() {
        if (mArrivalList.size() != 0) {
            listviewDialog();
        }
    }

    void sendNyukaRequest(String nyuka) {
        progress.Show();
        NyukaSelectReq req = new NyukaSelectReq(app.getSerial(), adminID, BaseActivity.getShopId(), nyuka);
        manager.NyukaSelect(req, nyukacallback);
    }

    @Override
    public void onSucess(int status, NyukaSelectResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("200")) {

        } else if (message.getCode().equalsIgnoreCase("401")) {
            showPopup("予定外入荷です。");
        } else {
            U.beepError(this, message.getMessage());
        }
    }


    public void LocApi(){
        progress.Show();
        LocationReq req = new LocationReq(adminID, app.getSerial(), BaseActivity.getShopId(), getString(R.string.version), _gts(R.id.productCode));
        manager.LocationList(req, locationlist);

    }

    @Override
    public void onSucess(int status, DirArrivalLoctionResult message) throws JsonIOException {
        if (message.getCode().equalsIgnoreCase("0")){
            progress.Dismiss();

            arrloc = message.getResults();

            if (arrloc.size()!=0) {

                adap = new Adap_arrivalLoc_list(this, message.getResults());
                listLocations.setAdapter(adap);
                adap.notifyDataSetChanged();
            }





        }
    }

    @Override
    public void onError(int status, ResponseBody error) {
    }

    @Override
    public void onFaliure() {
    }

    @Override
    public void onNetworkFailure() {
    }

    @Override
    public void onBackPressed() {
//  super.onBackPressed();
    }

    @Override
    public void onSucess(int status, ResponseBody message) throws JsonIOException {
        if (status==200){
            progress.Dismiss();
            Result();
        }

    }

    public void dilaogCustomercancel() {

        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.setCancelable(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setContentView(R.layout.weightsubmit);

        final EditText weight = (EditText) dialog2.findViewById(id.weight);
        close = (ImageView) dialog2.findViewById(id.closee);
        weight.setFocusable(true);
        weight.setFocusableInTouchMode(true);
        weight.requestFocus();

        //  text.setText(remark);
        Button ok = (Button) dialog2.findViewById(id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (weight.getText().toString().equals("")||weight.getText().toString().equals("0")){
                    U.beepError(ArrivalActivity.this,"重さを入力してください ");
                }else {

                    dialog2.dismiss();
                    progress.Show();
                    ArrivalWeightRequest reqq = new ArrivalWeightRequest(adminID, app.getSerial(), BaseActivity.getShopId(), getString(R.string.version), ProductID, weight.getText().toString());
                    manager.ArrivalWeight(reqq, weightcall);

                }
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                nextProcess();

            }
        });
        if (!dialog2.isShowing()) {
            dialog2.show();
        }

    }


    public void Result(){
        if (code == null) {
            Log.e(TAG, "CODE===Null");
            CommonDialogs.customToast(getApplicationContext(), "Network error occured");
        }

        if ("0".equals(code) == true) {

            Log.e("SendLogs", code + "  " + msg + "  " + result1);

            if (mRequestStatus == REQ_BARCODE) {
                new GetArrival().post(code, msg,result1, mHash, ArrivalActivity.this);
            } else if (mRequestStatus == REQ_QTY) {
                new AddArrival().post(code, msg, result1, mHash, ArrivalActivity.this);
            } else if (mRequestStatus == REQ_INITIAL) {
                new GetPrinter().post(code, msg, result1, mHash, ArrivalActivity.this);
            }

        } else if (code.equalsIgnoreCase("1020")) {
            new AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(msg)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent in = new Intent(ArrivalActivity.this, LoginActivity.class);
                            in.putExtra("ACTION", "logout");
                            startActivity(in);
                            finish();

                            dialog.dismiss();
                        }
                    })

                    .show();
        } else {
            if (mRequestStatus == REQ_BARCODE) {
                new GetArrival().valid(code, msg, result1, mHash, ArrivalActivity.this);
            } else if (mRequestStatus == REQ_QTY) {
                new AddArrival().valid(code, msg, result1, mHash, ArrivalActivity.this);
            } else if (mRequestStatus == REQ_INITIAL) {
                new GetPrinter().valid(code, msg, result1, mHash, ArrivalActivity.this);
            }
        }
    }


    public void LOTDIALOG(){
        Dialog  dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogarrival);

        Button dialogButton = (Button) dialog.findViewById(R.id.ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                startActivity(new Intent(ArrivalActivity.this, SettingActivity.class));
                finish();
            }
        });

        dialog.show();
    }

    public void ShowPOP() {
        new SweetAlertDialog(this).setTitleText("入荷指示に消費期限が含まれています。").show();
    }
}

