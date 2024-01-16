
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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetArrivalNew;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetNewArrivalAllocation;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.MoveStock;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.ArrivalWeight.ArrivalWeightRequest;
import com.itechvision.ecrobo.pickman.Models.DirectArrival.DirArrivalListData;
import com.itechvision.ecrobo.pickman.Models.DirectArrival.DirArrivalLoctionResult;
import com.itechvision.ecrobo.pickman.Models.DirectArrival.LocationReq;
import com.itechvision.ecrobo.pickman.Models.NyukaArrival.NyukaSelectReq;
import com.itechvision.ecrobo.pickman.Models.NyukaArrival.NyukaSelectResponse;
import com.itechvision.ecrobo.pickman.R;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

import android.view.LayoutInflater;

public class DirectarrivalActivity extends BaseActivity implements View.OnClickListener, MainAsynListener, DataManager.Nyukacallback ,DataManager.ArrivalWeightcall,DataManager.LocationListcall{
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.expirationdate) EditText expiration;
    @BindView(R.id.gridLot) LinearLayout layoutlot;
    @BindView(R.id.gridExpiration) LinearLayout layoutexpiration;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.add_location) EditText add_location;
    @BindView(R.id.listArrival) ListView lv;
    @BindView(R.id.Remarks) EditText remarks;
    @BindView(R.id.classificationspinner) Spinner classification;
    @BindView(R.id.batch_arrival) Button batcharrivalbtn;
    @BindView(R.id.spinnerlayout) RelativeLayout spinnerLayout;
    @BindView(R.id.partnoSpinner) Spinner partNospinner;
    @BindView(R.id.ll_comment) LinearLayout ll_comment;
    @BindView(R.id.ll_listdialog) LinearLayout ll_listdialog;


    public TextView pck0;
    public TextView pck1;
    public TextView pck2;
    public TextView pck3;
    @BindView(R.id.listLocations) ListView listLocations;
    @BindView(R.id.loc_layout) LinearLayout loc_layout;
    @BindView(R.id.abc) EditText abc;
    @BindView(R.id.ll_abc) LinearLayout ll_abc;

    int eop , POSTION = 0;
    public LinearLayout partnoLayout;
    View dialogView;
    AlertDialog alertDialog;
//    @BindView(R.id.layout_number) RelativeLayout layout;

//    private Button batcharrivalbtn;

    public TextView productName;
    int valueText2 =0 ;
    public static boolean addlotno = false;
    public String qnt = "";
    public String differ = "0";
    SharedPreferences spDomain;
    public Context mcontext = this;
    public static final String DOMAINPREFERENCE = "domain";
    SweetAlertDialog sweetAlertDialog;

    private static final String TRUE2 = "TRUE";
    private static final String FALSE2 = "FALSE";
    private String directToStockSetting = "FALSE";
    ListViewAdapter adapter;
    ListView listview;
    Dialog dialogList;
    // for fixed location barcodes
    public static String fixed_location_flag = "";  // to get flag that location fixed or not
    public static String location_fixed = "";  // to store location

    String TAG = DirectarrivalActivity.class.getSimpleName();
    protected int mProcNo = 0;

    public static final int PROC_ORDER_NO = 6;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_QTY = 2;
    public static final int PROC_LOCATION = 3;
    public static final int PROC_LOT_NO = 4;
    public static final int PROC_EXPIRATION = 5;
    public static final int PROC_PARTNO = 7;
    public static int mRequestStatus = 0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_BARCODE1 = 2;
    public static final int REQ_BARCODE2 = 3;
    public static final int REQ_LOT1 = 4;
    public static final int REQ_LOT2 = 5;
    public static final int REQ_QTY = 6;
    public static final int REQ_AUTOMATE_ORDER = 7;
    int mSelectedItem = 0;
    private TextToSpeak mTextToSpeak;
    public String nyukaId = "";
    public String classificationId = "", defaultclassificationID = "";
    public String location = null;
    private boolean orderRequestSettings;
    String attribute = "0" ,  Weightt="" ,QQQQ="";
    public boolean lotexist = false;
    public boolean ordernocheckbox = false, triplebarcode = false;
    private boolean showKeyboard;

    ECRApplication app = new ECRApplication();
    String adminID = "";

    public boolean batcharrival = false, multicode = false;
    private boolean arrivalScheduleSelected = false;
    private boolean visible = false;
    private boolean lotselect = false;
    public String selectedUrl = null;
    protected Map<String, String> mStockList = null;
    protected ArrayList<String> PartnoArray = new ArrayList<String>();
    public static List<Map<String, String>> mBatchList = new ArrayList<Map<String, String>>();
    public static List<Map<String, String>> mArrivalList = new ArrayList<Map<String, String>>();
    public static List<Map<String, String>> mArrivalLot = new ArrayList<Map<String, String>>();
    public static List<Map<String, String>> mmultiArrivalList = new ArrayList<>();
    public static List<Map<String, String>> mmultiLocationList = new ArrayList<>();
    protected ArrayList<String> nyukaIdArray = new ArrayList<String>();
    protected ArrayList<String> orderArray = new ArrayList<String>();
    protected ArrayList<String> qntyArray = new ArrayList<String>();
    protected ArrayList<String> classificationIdArray = new ArrayList<String>();
    protected ArrayList<String> defaultStockArray = new ArrayList<String>();
    protected ArrayList<String> locationArray = new ArrayList<String>();

    public ArrayList<String> defaultclassificationIdArray = new ArrayList<String>();
    Dialog  dialog2;
    SweetAlertDialog dialog;
    public static int count = 0;
    PopupWindow popupWindow;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    DialogInterface.OnDismissListener onCancelListener ;
    InputMethodManager imm;

    DataManager manager;
    progresBar progress;
    DataManager.Nyukacallback nyukacallback;

    String msg = "",code="",ProductID="";
    JsonArray result1;
    HashMap<String, String> mHash;
    DataManager.ArrivalWeightcall  weightcall;
    DataManager.LocationListcall locationlist ;
    ImageView close ;
    Adap_arrivalLoc_list adap ;
    ArrayList<DirArrivalListData> arrloc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directarrival);

        ButterKnife.bind(DirectarrivalActivity.this);
        getIDs();

        Log.d(TAG,"On Create ");

        nyukacallback = this;
        weightcall = this;
        locationlist = this;

        productName = (TextView) findViewById(R.id.productName);
        pck0 = (TextView) findViewById(R.id.pck_0);
        pck1 = (TextView) findViewById(R.id.pck_1);
        pck2 = (TextView) findViewById(R.id.pck_2);
        pck3 = (TextView) findViewById(R.id.pck_3);


        Log.e(TAG, "Lotno    " + getLotPress());
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        orderRequestSettings = BaseActivity.getLotPress();

        if (orderRequestSettings == true) {
            layoutlot.setVisibility(View.VISIBLE);
            layoutexpiration.setVisibility(View.VISIBLE);
        }

        if (orderRequestSettings){

        }else{
            if (BaseActivity.getUrl().equalsIgnoreCase("https://staging3.air-logi.com/service")||BaseActivity.getUrl().equalsIgnoreCase("https://www3.air-logi.com/service")) {
                LOTDIALOG();
            }
        }

        arrivalScheduleSelected = BaseActivity.getArrivalNyuka();
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);

        mTextToSpeak = new TextToSpeak(this);
        triplebarcode = BaseActivity.getTripleBarcode();
        classification = (Spinner) _gsv(R.id.classificationspinner);
        partnoLayout = (LinearLayout) findViewById(R.id.partnoLayout);

        arrloc = new ArrayList<>();

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
        Log.e(TAG, "ONCREATEE");

        if (mProcNo == 0) nextProcess();


        Globals.getterList = new ArrayList<>();
        Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));

        mRequestStatus = REQ_INITIAL;

        new MainAsyncTask(this, Globals.Webservice.listPrinter, 1, DirectarrivalActivity.this, "Form", Globals.getterList, true).execute();

        classification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (classificationIdArray != null && classificationIdArray.size() > position) {
                    setclassificationId(classificationIdArray.get(position));
                    Log.e(TAG, "Selected Classification " + classificationIdArray.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setclassificationId("");
                Log.e(TAG, "Selected Classification" + getclassificationId() + "null");
            }
        });


        partNospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    setAttributeProc();
                    String code = PartnoArray.get(position);
                    _sts(R.id.productCode, code);
                    mSelectedItem = 0 ;
                    getList(code);
                    try {



                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Write whatever to want to do after delay specified (1 sec)
                                Data();
                            }
                        }, 800);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


//                    getList(code);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



/*

        expiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTruitonDatePickerDialog(expiration);
            }
        });

        expiration.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showTruitonDatePickerDialog(expiration);
            }
        });

*/

        final Spinner location = (Spinner) findViewById(R.id.listAllocation);
        setLocation("");
        add_location.setText("");
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (locationArray != null && locationArray.size() > i) {
                    setLocation(locationArray.get(i));
                    Object item;
                    Log.e(TAG, "SpinnereeerrrLocation" + getLocation());
                    Log.e(TAG, "SpinnereeerrrLocationSize" + locationArray.size());
                    if (i == 0) {
                        if (mProcNo == PROC_LOCATION) {
                            setProc(PROC_LOCATION);
                            add_location.setText("");
                        }
                        item = adapterView.getItemAtPosition(i);
                    } else {
                        add_location.setText(getLocation());
                        item = adapterView.getItemAtPosition(i);
                    }
                    Log.e(TAG, "SpinnereeerrrLocation" + getLocation());
                    Log.e(TAG, "SpinnereeerrrObject" + item);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                add_location.setText("");
            }
        });

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

        directToStockSetting = (this.getDirectToStock()) ? TRUE2 : FALSE2;
        batcharrivalbtn.setOnClickListener(this);

        remarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!remarks.getText().toString().equalsIgnoreCase("")) {
//                    showdialog(remarks.getText().toString());


                    final Dialog dialog = new Dialog(DirectarrivalActivity.this);
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





                   /* SweetAlertDialog dia = new SweetAlertDialog(DirectarrivalActivity.this);
                    dia.setContentText(remarks.getText().toString());
                    dia.setCancelable(true);
                    dia.show();*/
                }
            }
        });
    }


    public void LocApi(){
        progress.Show();
        LocationReq req = new LocationReq(adminID, app.getSerial(), BaseActivity.getShopId(), getString(R.string.version), _gts(R.id.barcode));
        manager.LocationList(req, locationlist);

    }
    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "入荷棚入れ", " ",
                0, true, true, false);
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);


        relLayout1.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnBlue.setOnClickListener(this);

        manager = new DataManager();
        progress = new progresBar(this);

        mHash = new HashMap<>();

        dialog2 = new Dialog(DirectarrivalActivity.this);

    }


    public void Data(){
        if (mArrivalList.size()!=0) {


            _sts(R.id.Remarks, mArrivalList.get(0).get("comment"));
            _sts(R.id.Standard1, mArrivalList.get(0).get("spec1"));
            _sts(R.id.Standard2, mArrivalList.get(0).get("spec2"));
            productName.setText(mArrivalList.get(0).get("name"));

            setNyukaId(mArrivalList.get(0).get("nyuka_id"));

            pck0.setText(mArrivalList.get(0).get("sup_date"));
            pck1.setText(mArrivalList.get(0).get("order_no"));
            pck2.setText(mArrivalList.get(0).get("comp_name"));
            pck3.setText(mArrivalList.get(0).get("qty"));
            abc.setText(mArrivalList.get(0).get("abc"));
            int val = findIndex(mArrivalList.get(0).get("stock_type_id"));
            if(val>=0){
                classification.setSelection(val);
            }
            else classification.setSelection(0);

            QQQQ =mArrivalList.get(0).get("qty");

            POSTION = 0 ;

            setQnty(qntyArray.get(POSTION));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
            case R.id.batch_arrival:
                if (batcharrival == false) {
                    batcharrivalbtn.setText("シングルに戻す");
                    batcharrivalbtn.setBackgroundColor(getResources().getColor(R.color.darkblue));
                    batcharrival = true;
                } else {
                    if (mBatchList.size() > 0)
                        showdialog("バッチリストあると変更できません。");
                    else {
                        batcharrival = false;
                        batcharrivalbtn.setText("入荷バッチ");
                        batcharrivalbtn.setBackgroundColor(getResources().getColor(R.color.blue));

                    }
                }
                break;
            case R.id.notif_count_red:
                Log.e(TAG, ">>>>>>>>>>>111111111111111" + getBadge2());
                if (getBadge2() != 0) {
                    showOrders();
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
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

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

    public void ArrivalLotdata(List<Map<String, String>> list) {
        mArrivalLot = list;
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {

            case PROC_BARCODE:
                Log.e(TAG, "SETPROCCBARCODEE");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.barcode).requestFocus();
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                lv.setAdapter(null);
                _gt(R.id.add_location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gsv(R.id.listAllocation).setAdapter(null);
                if (orderRequestSettings) {
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                }
                setNyukaId(null);
                setLocation(null);
                //_glv(id.listArrival).setAdapter(null);
                break;
            case PROC_PARTNO:
                Log.e(TAG, "SETPROCCPARTNOO");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.add_location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                lv.setAdapter(null);
                _gt(R.id.add_location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gsv(R.id.listAllocation).setAdapter(null);
                if (orderRequestSettings) {
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                }
                setNyukaId(null);
                setLocation(null);

                break;
            case PROC_QTY:
                Log.e(TAG, "SETPROCCQUANTITYY");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.add_location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                if (attribute.equals("1"))
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                else if (attribute.equals("2"))
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                else if (attribute.equals("3")) {
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                }


                if (_gts(R.id.quantity).equals("1"))
                    mTextToSpeak.startSpeaking("1");

                break;

            case PROC_LOCATION:
                Log.e(TAG, "setProc Locationn");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.add_location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.add_location).setFocusableInTouchMode(true);
                _gt(R.id.add_location).requestFocus();
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                if (attribute.equals("1"))
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                else if (attribute.equals("2"))
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                else if (attribute.equals("3")) {
                    _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                }
                break;
            case PROC_LOT_NO:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.add_location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.lotno).setFocusableInTouchMode(true);
                break;
            case PROC_EXPIRATION:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.add_location).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.expirationdate).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
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
//            case PROC_ORDER_NO:
//                String orderno = _gts(R.id.orderNo);
//                if (orderno.equals("") || orderno.equals("0")) {
//                    U.beepError(this, "ロット番号が必要");
//                    break;
//                }
//                setProc(PROC_BARCODE);
//                break;

            case PROC_BARCODE:

                String barcode1 = _gts(R.id.barcode);
                if (barcode1.equalsIgnoreCase("")){
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }else

                if (dialog2.isShowing()){

                }else {
                    // バーコード
                    Log.e(   TAG, "InputedEventBarcodee");

                    lv.setAdapter(null);

                    Spinner lv = (Spinner) findViewById(R.id.listAllocation);
                    lv.setAdapter(null);
                    setNyukaId(null);
                    setLocation(null);
                    String Url = spDomain.getString("domain", null);
                    selectedUrl = Url;

                    Globals.getterList = new ArrayList<>();
                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    Globals.getterList.add(new ParamsGetter("barcode", barcode1));

                    if (orderRequestSettings) {
                        U.beepNext();

                        Globals.getterList.add(new ParamsGetter("check_length", triplebarcode + ""));
                        mRequestStatus = REQ_BARCODE1;
                        new MainAsyncTask(this, Globals.Webservice.getArrival, 1, DirectarrivalActivity.this, "Form", Globals.getterList, true).execute();

                        lotselect = true;
                    } else {
                        U.beepRecord(this, null);

                        Globals.getterList.add(new ParamsGetter("check_length", triplebarcode + ""));
                        mRequestStatus = REQ_BARCODE1;
                        new MainAsyncTask(this, Globals.Webservice.getArrival, 1, DirectarrivalActivity.this, "Form", Globals.getterList, true).execute();

                    }
                }
                break;
            case PROC_LOT_NO:

                lot = _gts(R.id.lotno);

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
                }else {
                    if (dialog2.isShowing()) {

                    }
                  /*  else if(BaseActivity.getLotnoScanCheck()){
                        if(!lot.equalsIgnoreCase(mArrivalLot.get(mSelectedItem).get("lot"))){
                            U.beepError(this, "ロットが一致しない");
                        }
                        else
                        if (attribute.equals("3") || addlotno)
                            if (expiration.getText().toString().equalsIgnoreCase("")) {
                                setProc(PROC_EXPIRATION);
                            } else {
                                setProc(PROC_QTY);
                            }
                        else
                            setProc(PROC_QTY);
                    }*/
                    else {


                        String url = spDomain.getString("domain", null);

                        Globals.getterList = new ArrayList<>();
                        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                        Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
                        Globals.getterList.add(new ParamsGetter("lot_no", lot));
                        Globals.getterList.add(new ParamsGetter("check_length", triplebarcode + ""));

                        if (addlotno) {
                            if (expiration.getText().toString().equalsIgnoreCase("")) {
                                setProc(PROC_EXPIRATION);
                            } else {
                                setProc(PROC_QTY);
                            }
                            U.beepNext();
                        } else {
                            U.beepRecord(this, null);
                            if (attribute.equals("3"))
                                if (expiration.getText().toString().equalsIgnoreCase("")) {
                                    setProc(PROC_EXPIRATION);
                                } else {
                                    setProc(PROC_QTY);
                                }
                            else
                                setProc(PROC_QTY);

                            Globals.getterList.add(new ParamsGetter("new", "0"));
                            Globals.getterList.add(new ParamsGetter("move_stock", "true"));

                        }
                    }
                }
                break;

            case PROC_QTY: // 数量
                if(dialog2.isShowing()){

                }else{

                    lotselect = false;

                    String quantity = _gts(R.id.quantity);
                    String barcode = _gts(R.id.barcode);
                    String lotno = "";

                    if (orderRequestSettings) {
                        lotno = _gts(R.id.lotno);
                    }

                    if (isScaned) {

                        if (buff.equals(barcode)) {

                            U.beepSuccess();
                            String val = U.plusTo(_gts(R.id.quantity), "1");
                            _sts(R.id.quantity, val);
                            mTextToSpeak.startSpeaking(val);
                            break;
                        } else {
                            U.beepError(this, "Barcode dont match");
                            break;
                        }
                    }
//             }

                    if ("".equals(quantity) || "0".equals(quantity)) {
                        U.beepError(this, "数量は必須です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(quantity)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else {
                        U.beepSuccess();
                        if (!getNyukaId().equals("999")) {
                            checkQnty();
                        } else {

                            if (batcharrival == true) {
                                nextdata();
                                setProc(PROC_BARCODE);
                            } else


                                setProc(PROC_LOCATION);

                            //Dialog

                        }
                    }
                }
                break;
            case PROC_EXPIRATION:
                if (dialog2.isShowing()){

                }else {
                    lotselect = false;
                    String exp = _gts(R.id.expirationdate);
                    if ("".equals(exp)) {
                        U.beepError(this, "賞味期限が必要です");
                        _gt(R.id.expirationdate).setFocusableInTouchMode(true);
                        break;
                    }
                    setProc(PROC_QTY);
                }
                break;
            case PROC_LOCATION: // ロケーション
                try {
                    if (dialog2.isShowing()){

                    }else {
                        lotselect = false;
                        String loc = _gts(R.id.add_location);
                        String lotn = "";
                        String expdat = "";
                        if (attribute.equals("3")) {
                            lotn = _gts(R.id.lotno);
                            expdat = _gts(R.id.expirationdate);
                        } else if (attribute.equals("2"))
                            expdat = _gts(R.id.expirationdate);
                        else if (attribute.equals("1"))
                            lotn = _gts(R.id.lotno);


                        if ("".equals(loc)) {
                            U.beepError(this, "ロケーションは必須です");
                            _gt(R.id.add_location).setFocusableInTouchMode(true);
                            break;
                        }

                        if (fixed_location_flag.equals("1") && !loc.equals(location_fixed) && !location_fixed.equals("")) {
                            U.beepError(this, "固定ロケーションの品番に複数のロケーションを登録することは出来ません");
                            _gt(R.id.add_location).setFocusableInTouchMode(true);
                            break;
                        }


                        String classification = "";
                        if (getclassificationId().equals("")) {
                            classification = getdefaultclassificationId();
                        } else
                            classification = getclassificationId();

                        stopTimer();

                        Globals.getterList = new ArrayList<>();

                        Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                        Globals.getterList.add(new ParamsGetter("source", "z"));
                        Globals.getterList.add(new ParamsGetter("destination", loc));
                        Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
                        Globals.getterList.add(new ParamsGetter("check_length", triplebarcode + ""));

                        if (batcharrival) {
                            StringBuffer nyuka = new StringBuffer();
                            StringBuffer bar = new StringBuffer();
                            StringBuffer classif = new StringBuffer();
                            StringBuffer qty = new StringBuffer();
                            StringBuffer lot1 = new StringBuffer();
                            StringBuffer exp1 = new StringBuffer();
                            for (Map<String, String> map : mBatchList) {
                                if (!map.get("quantity").equals("0")) {
                                    bar.append("\t").append(map.get("code"));
                                    qty.append("\t").append(map.get("quantity"));
                                    classif.append("\t").append(map.get("classification"));
                                    nyuka.append("\t").append(map.get("nyuka"));
                                    lot1.append("\t").append(map.get("lotno"));
                                    exp1.append("\t").append(map.get("expiration"));
                                }
                            }

                            Globals.getterList.add(new ParamsGetter("nyuka_id", nyuka.substring(1)));
                            Globals.getterList.add(new ParamsGetter("stock_type_id", classif.substring(1)));
                            Globals.getterList.add(new ParamsGetter("barcode", bar.substring(1)));
                            Globals.getterList.add(new ParamsGetter("qty", qty.substring(1)));
                            Globals.getterList.add(new ParamsGetter("lot_no", lot1.substring(1)));
                            Globals.getterList.add(new ParamsGetter("expiration_date", exp1.substring(1)));

                            mRequestStatus = REQ_QTY;
                            if (count == 0)
                                new MainAsyncTask(this, Globals.Webservice.moveStockArrival, 1, DirectarrivalActivity.this, "Form", Globals.getterList, true).execute();
                            else
                                Toast.makeText(getApplicationContext(), "Wait", Toast.LENGTH_SHORT).show();
                            //                    new ECZaikoClient(this).setListner(new MoveStock()).moveStockArrival(mBatchList, "z", loc,timeTaken().toString(),triplebarcode+"");
                        } else {
                            if (multicode)
                                Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.productCode)));
                            else
                                Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.productCode)));
                            //  Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
                            Globals.getterList.add(new ParamsGetter("lot_no", lotn));
                            Globals.getterList.add(new ParamsGetter("qty", _gts(R.id.quantity)));
                            Globals.getterList.add(new ParamsGetter("nyuka_id", getNyukaId()));

                            Globals.getterList.add(new ParamsGetter("stock_type_id", classification));
                            Globals.getterList.add(new ParamsGetter("expiration_date", expdat));
                            mRequestStatus = REQ_QTY;

                            int days = 0;
                            if (orderRequestSettings && (attribute.equals("3") || attribute.equals("2"))) {
                                if (mArrivalLot.get(0).get("arrival_exp_flag").equals("1")) {
                                    try {
                                        // reversing the string expiration date
                                        String date = reverseDate(expdat);
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                        Date expirationdate = sdf.parse(date);

                                        String formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                                        Date arrivaldate = sdf.parse(formattedDate);
                                        days = U.getDaysDifference(arrivaldate, expirationdate);

                                    } catch (Exception e) {
                                        Log.e(TAG, "Exceptionnnnn     " + e);
                                    }
                                    if (!mArrivalLot.get(0).get("arrival_exp_days").equals("")) {
                                        String exp_days = mArrivalLot.get(0).get("arrival_exp_days");

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
                                                                if (count == 0)
                                                                    new MainAsyncTask(DirectarrivalActivity.this, Globals.Webservice.moveStockArrival, 1, DirectarrivalActivity.this, "Form", Globals.getterList, true).execute();
                                                                else
                                                                    Toast.makeText(getApplicationContext(), "Wait", Toast.LENGTH_SHORT).show();
                                                                sweetAlertDialog.dismiss();
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
                                                break;
                                            }
                                        }
                                    }
                                }
                            }

                            if (count == 0)
                                new MainAsyncTask(DirectarrivalActivity.this, Globals.Webservice.moveStockArrival, 1, DirectarrivalActivity.this, "Form", Globals.getterList, true).execute();
                            else
                                Toast.makeText(getApplicationContext(), "Wait", Toast.LENGTH_SHORT).show();
                        }
                        if (!differ.equals("0")) {
                            showdialog("入荷予定数より" + differ + "個多く検品しています。");

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
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

    public void setlocationList() {
        Globals.getterList = new ArrayList<>();

        Log.e(TAG, "setlocationList    shopidddddd  " + BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
        Globals.getterList.add(new ParamsGetter("lot", ""));
        Globals.getterList.add(new ParamsGetter("new", "0"));
        Globals.getterList.add(new ParamsGetter("move_stock", "true"));
        Globals.getterList.add(new ParamsGetter("check_length", triplebarcode + ""));

        if(!_gts(R.id.barcode).equals("")) {
            mRequestStatus = REQ_BARCODE2;
            new MainAsyncTask(this, Globals.Webservice.getAllocationArrival, 1, DirectarrivalActivity.this, "Form", Globals.getterList, true).execute();
        }
    }

    void checkQnty() {

        Log.e(TAG, "Qty is>>>>>>>>" + getQnty());

        int i = U.compareNumeric(_gts(R.id.quantity), getQnty());

        Log.e(TAG, "Qty is>>>>>>>>" + getQnty() + "  " + i);
        if (i < 0) {
            Log.e(TAG, "Qty is>>>>>>>>Quantity exceeedsss     " + i);
            dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
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
                            // differ = U.minusTo(_gts(R.id.quantity), getQnty());
                            differ = U.minusTo(_gts(R.id.quantity), getQnty());
                            dialog.dismiss();
                            if (batcharrival == true) {
                                nextdata();
                                setProc(PROC_BARCODE);
                            } else


                                setProc(PROC_LOCATION);


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

                setProc(PROC_LOCATION);



        }
    }


    public String getclassificationId() {
        return this.classificationId;
    }

    public void setclassificationId(String id) {
        this.classificationId = id;
    }

    public void setclassificationIdArray(ArrayList arr) {
        this.classificationIdArray = arr;
    }


    public void setNyukaId(String id) {
        Log.e(TAG, "nyukaaaaa  setNyukaId   " + nyukaId);
        this.nyukaId = id;
    }

    public String getNyukaId() {
        return this.nyukaId;
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

    public void setLocation(String id) {
        this.location = id;
    }

    public String getLocation() {
        return this.location;
    }

    public void setNyukaIdArray(ArrayList arr) {

        this.nyukaIdArray = arr;
    }

    public void setLocationArray(ArrayList arr) {
        this.locationArray = arr;
    }

    public void setMulticodeLocations(List<Map<String, String>> arr) {
        mmultiLocationList = arr;
        Log.e(TAG, "mmultiLocationList  setMulticodeLocations   " + mmultiLocationList);
    }

    // is product using lot no or expiration date
    public void setAttributeValue(String atr) {
        attribute = atr;
        setLayout();
    }

    public void setLayout() {
        if (attribute.equals("0")) {
            layoutlot.setVisibility(View.GONE);
            layoutexpiration.setVisibility(View.GONE);
        } else if (attribute.equals("1")) {
            layoutlot.setVisibility(View.VISIBLE);
            layoutexpiration.setVisibility(View.GONE);
        }
        if (attribute.equals("2")) {

            layoutlot.setVisibility(View.GONE);
            layoutexpiration.setVisibility(View.VISIBLE);
            expiration = (EditText) findViewById(R.id.expirationdate);
        }
        if (attribute.equals("3")) {
            layoutlot.setVisibility(View.VISIBLE);
            layoutexpiration.setVisibility(View.VISIBLE);
            expiration = (EditText) findViewById(R.id.expirationdate);
        }
    }

    public void setdefaultclassificationIdArray(ArrayList arr) {
        this.defaultclassificationIdArray = arr;
    }

    // for the default set Stock classification
    public String getdefaultclassificationId() {
        return this.defaultclassificationID;
    }

    public void setdefaultclassificationId(String id) {
        this.defaultclassificationID = id;
    }

    public void setdefaultclassificationArray(ArrayList arr) {
        this.defaultStockArray = arr;
    }

    public void setStockList(Map<String, String> data) {
        mStockList = data;
    }

    public void nextdata() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("barcode", _gts(R.id.barcode));
        map.put("quantity", _gts(R.id.quantity));
        map.put("code", _gts(R.id.productCode));

        if (orderRequestSettings) {
            map.put("lotno", _gts(R.id.lotno));
            map.put("expiration", _gts(R.id.expirationdate));
        } else {
            map.put("lotno", "");
            map.put("expiration", "");
        }
        String classification = "";
        if (getclassificationId().equals("")) {
            classification = getdefaultclassificationId();
        } else
            classification = getclassificationId();

        map.put("classification", classification);

        map.put("nyuka", getNyukaId());

        mBatchList.add(map);
        _sts(R.id.barcode, "");
        _sts(R.id.quantity, "");
        if (orderRequestSettings) {
            _sts(R.id.lotno, "");
            _sts(R.id.expirationdate, "");

        }
        setBadge2(mBatchList.size());
    }

    public void setAttributeProc() {
        if (attribute.equals("0")) {
            setProc(DirectarrivalActivity.PROC_QTY);
        } else if (attribute.equals("1")) {
            if (_gts(R.id.lotno).equals("") )
                setProc(PROC_LOT_NO);
            else
                setProc(PROC_QTY);
            lotexist = true;
        } else if (attribute.equals("2")) {
            if (_gts(R.id.expirationdate).equals("")) {
                setProc(PROC_EXPIRATION);
            } else {
                showPopup();
                setProc(PROC_QTY);
            }
        } else if (attribute.equals("3")) {
            if (_gts(R.id.lotno).equals(""))
                setProc(PROC_LOT_NO);
            else if (_gts(R.id.expirationdate).equals(""))
                setProc(PROC_EXPIRATION);
            else {
                showPopup();
                setProc(PROC_QTY);
            }
            lotexist = true;
        }

        if(!nyukaIdArray.get(0).equalsIgnoreCase("999")&& qntyArray.get(0).equalsIgnoreCase("0")) {
            _sts(R.id.quantity, "0");

        }
        else
        {
            _sts(R.id.quantity, "1");
        }

    }


    public void getList(String part) {
        Spinner spinner = (Spinner) findViewById(R.id.listAllocation);
        List<Map<String, String>> data = new ArrayList<>();
        ArrayList<String> locationlist = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
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
        String location_info = null;
        locationlist.add("   ロケ登録");
        list2.add("   ロケ登録");
        for (Map<String, String> map : mmultiLocationList) {
            String _c = map.get("code");
            if (_c.equals(part)) {
                String quantity = map.get("quantity");
                String loc = map.get("location");
                list2.add(loc);

                location_info = "      " + loc + "           :          " + quantity;
                locationlist.add(location_info);
            }
        }

        if (locationlist.size() > 0) {
            Log.e(TAG, "Size greater than 0");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    _singleItemRes, locationlist);
            adapter.setDropDownViewResource(_dropdownRes);

            spinner.setAdapter(adapter);

        } else spinner.setAdapter(null);

        setLocationArray(list2);
        initWorkList();

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mArrivalList.size() > 2) {
                    click() ;
                }

            }
        }, 500);

    }

    public void getmultiPartarrivalList(List<Map<String, String>> list, boolean multi, List<String> part) {
        mmultiArrivalList = list;
        multicode = multi;
        PartnoArray = (ArrayList<String>) part;
    }

    public void getarrivalList(List<Map<String, String>> list) {
        mArrivalList = list;
//        initWorkList();

        setNyukaId(nyukaIdArray.get(0));
        if (arrivalScheduleSelected && mArrivalList.size() > 1)
            mArrivalList.remove(mArrivalList.size() - 1);

        Log.e(TAG, " arrivalScheduleSelected   "+arrivalScheduleSelected);
        if (getNyukaId().equals("999") && arrivalScheduleSelected)
        {
            Log.e(TAG, " aaaaaaaaaaaaaaaaa   ");
            showPopup("入荷予定がありません。");
            U.beepError(DirectarrivalActivity.this, null);
            _sts(R.id.barcode, "");
            setProc(PROC_BARCODE);
        } else {
            Log.e(TAG, "bvcaBVFSVVFDBFBFDVD   ");

            if(arrivalScheduleSelected){
                if (list.size() > 1)
                    listviewDialog();
            }
            else {
                if (list.size() > 2)
                    listviewDialog();
            }

            eop = 0;

            Log.e(TAG, "eop    " + eop);

            _sts(R.id.Remarks, mArrivalList.get(0).get("comment"));
            _sts(R.id.Standard1, mArrivalList.get(0).get("spec1"));
            _sts(R.id.Standard2, mArrivalList.get(0).get("spec2"));


            pck0.setText(mArrivalList.get(0).get("sup_date"));
            pck1.setText(mArrivalList.get(0).get("order_no"));
            pck2.setText(mArrivalList.get(0).get("comp_name"));
            pck3.setText(mArrivalList.get(0).get("qty"));
            abc.setText(mArrivalList.get(0).get("abc"));

            int val = findIndex(mArrivalList.get(0).get("stock_type_id"));
            if(val>=0){
                classification.setSelection(val);
            }
            else
                classification.setSelection(0);

            mSelectedItem = 0;

            Log.e(TAG, "mSelectedItem    " + mSelectedItem);

            if (nyukaIdArray != null && nyukaIdArray.size() > 0) {
                Log.e(TAG, "nyukaIdArray    " + nyukaIdArray);
                setNyukaId(nyukaIdArray.get(0));
                setQnty(qntyArray.get(POSTION));
                //  QQQQ =qntyArray.get(0);

                if (getNyukaId().equals("999")) {
                    ll_comment.setVisibility(View.GONE);
                    Log.e(TAG, "Spinneeeerrrrrrrrrrrrrrrr       " + getNyukaId() + "       " + attribute);
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
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_new_arrival);
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
                                            if (arrivalScheduleSelected) {
                                                if (list.size() == 1)
                                                    showPopup();
                                            } else {
                                                if (list.size() == 2)
                                                    showPopup();
                                            }
                                            _sts(R.id.expirationdate, map.get("expiration_date"));
                                        } else if (mProcNo != PROC_EXPIRATION)
                                            setProc(PROC_EXPIRATION);
                                    } else if (attribute.equals("3")) {

                                        if (!map.get("expiration_date").equals("")) {
                                            if (arrivalScheduleSelected) {
                                                if (list.size() == 1)
                                                    showPopup();
                                            } else {
                                                if (list.size() == 2)
                                                    showPopup();
                                            }
                                        }

                                        _sts(R.id.lotno, map.get("lot"));
                                        _sts(R.id.expirationdate, map.get("expiration_date"));
                                        if (_gts(R.id.lotno).equals(""))
                                            setProc(PROC_LOT_NO);
                                        else if (_gts(R.id.expirationdate).equals("") && mProcNo != PROC_EXPIRATION)
                                            setProc(PROC_EXPIRATION);
                                    }
                                }
                            }
                        }
                    }
                }


                if (!mArrivalList.get(0).get("admin_id").equals("") && !mArrivalList.get(0).get("admin_id").equals(adminID))
                    showPopup("他のユーザーが作業中です。");

            }
        }



    }
    protected void initWorkList() {
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        ArrayList<Integer> used = new ArrayList<>();
        if (arrivalScheduleSelected && mArrivalList.size() > 1)
            mArrivalList.remove(mArrivalList.size() - 1);

        for (int i = 0; i <= mArrivalList.size() - 1; i++) {
            Map<String, String> row = mArrivalList.get(i);

            data.add(data.newItem().add(R.id.pck_0, row.get("sup_date"))
                    .add(R.id.pck_1, row.get("order_no"))
                    .add(R.id.pck_2, row.get("comp_name"))
                    .add(R.id.pck_3, row.get("qty"))
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
                if (position == mSelectedItem) {
                    Log.e(TAG, "selecteddddd positionnn43434354    " + position);
                    v.setBackgroundColor(Color.YELLOW);
                } else {
                    Log.e(TAG, "    positionnn 54325654325432  ");
                    if (position % 2 == 1) {
                        Log.e(TAG, "Odd    positionnn 54325432432   ");
                        v.setBackgroundColor(Color.GRAY);
                    } else {
                        Log.e(TAG, "Even     positionnn    ");

                        v.setBackgroundColor(Color.WHITE);
                    }
                }

                return v;
            }
        };
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする

        if (data.getData().size() > 0) {
            lv.setItemChecked(0, true);
            lv.setSelection(0);
            setNyukaId(nyukaIdArray.get(0));
            setQnty(qntyArray.get(POSTION));
            //      QQQQ=qntyArray.get(0);

            if (getNyukaId().equals("999") && arrivalScheduleSelected) {
                showPopup("入荷予定がありません。");
                U.beepError(this, null);
                _sts(R.id.barcode, "");
                setProc(PROC_BARCODE);
            }
        }

        if (!mArrivalList.get(0).get("admin_id").equals("") && !mArrivalList.get(0).get("admin_id").equals(adminID))
            showPopup("他のユーザーが作業中です。");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                _sts(R.id.Remarks, mArrivalList.get(position).get("comment"));
                _sts(R.id.Standard1, mArrivalList.get(position).get("spec1"));
                _sts(R.id.Standard2, mArrivalList.get(position).get("spec2"));
                abc.setText(mArrivalList.get(position).get("abc"));

                mSelectedItem = position;
                adapter.notifyDataSetChanged();

                if (nyukaIdArray != null && nyukaIdArray.size() > position) {
                    setNyukaId(nyukaIdArray.get(position));
                    setQnty(qntyArray.get(position));
                    //     QQQQ=qntyArray.get(position);

                    /*if (arrivalScheduleSelected && getNyukaId().equals("999"))
                    {
                        showPopup("入荷予定がありません。");
                        U.beepError(DirectarrivalActivity.this,null);
                        setProc(PROC_BARCODE);
                        mSelectedItem = 0;

                    }
                    else */
                    if (getNyukaId().equals("999")) {
                        ll_comment.setVisibility(View.GONE);
                        Log.e(TAG, "Spinnereeerrrrrrrrrrrrrrrr1232343454545       " + getNyukaId() + "       " + attribute);
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
                        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_new_arrival);
                        layout.setBackgroundResource(R.color.white);
                        if (BaseActivity.getLotPress()) {
                            if (!attribute.equals("0")) {
                                for (Map<String, String> map : mArrivalLot) {
                                    Log.e(TAG, "Spinnereerr 7655      " + getNyukaId() + "     " + map.get("nyuka_id"));
                                    if (map.get("nyuka_id").equals(getNyukaId())){
                                        Log.e(TAG, "Spinnereeerrrrrrrrrrrrrrrr12221212  5555555555555     " + map.get("nyuka_id"));
                                        if (attribute.equals("1")) {

                                            _sts(R.id.lotno, map.get("lot"));
                                            if (_gts(R.id.lotno).equals("")) setProc(PROC_LOT_NO);
                                        } else if (attribute.equals("2")) {

                                            if (!map.get("expiration_date").equals("")) {
                                                showPopup();
                                                _sts(R.id.expirationdate, map.get("expiration_date"));
                                            } else if (mProcNo != PROC_EXPIRATION)
                                                setProc(PROC_EXPIRATION);
                                        } else if (attribute.equals("3")) {
                                            showPopup();

                                            _sts(R.id.lotno, map.get("lot"));
                                            _sts(R.id.expirationdate, map.get("expiration_date"));
                                            if (_gts(R.id.lotno).equals(""))
                                                setProc(PROC_LOT_NO);
                                            else if (_gts(R.id.expirationdate).equals("") && mProcNo != PROC_EXPIRATION)
                                                setProc(PROC_EXPIRATION);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (position == 0) {
                        if (!mArrivalList.get(position).get("admin_id").equals("") && !mArrivalList.get(position).get("admin_id").equals(adminID))
                            showPopup("他のユーザーが作業中です。");
                    } else
                        sendNyukaRequest(nyukaIdArray.get(position));
                }
            }
        });
    }
    //?
    @Override
    public void inputedEvent(String buff) {
        Log.e(TAG, "inputedEvent");
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        Log.e(TAG, "clearEventtttt");
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
    }

    @Override
    public void allclearEvent() {
        CommonDialogs.customToast(this, "No action");
    }

    @Override
    public void skipEvent() {
        CommonDialogs.customToast(this, "No action");
    }

    public void nextProcess() {
        Log.e(TAG, "nextProcess");
        _sts(R.id.barcode, "");
        _sts(R.id.quantity, "");
        _sts(R.id.add_location, "");
        _stxtv(R.id.productName, "");
        _sts(R.id.Remarks, "");
        _sts(R.id.Standard1, "");
        _sts(R.id.Standard2, "");
        _sts(R.id.productCode, "");
        _sts(R.id.abc, "");
        pck0.setText("");
        pck1.setText("");
        pck2.setText("");
        pck3.setText("");
        mArrivalList.clear();

        ll_abc.setVisibility(View.GONE);
        partnoLayout.setVisibility(View.GONE);
        partNospinner.setAdapter(null);
        arrloc.clear();
        listLocations.setAdapter(null);
        loc_layout.setVisibility(View.GONE);

        multicode = false;

        lotexist = false;
        if (orderRequestSettings) {
            _sts(R.id.lotno, "");
            _sts(R.id.expirationdate, "");

            addlotno = false;
            lotselect = false;
        }

        // lv.setAdapter(null);
        //  listview.setAdapter(null);
        mSelectedItem = 0;
        setNyukaId("");
        setQnty("");
        POSTION = 0 ;
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_new_arrival);
        layout.setBackgroundResource(R.color.white);

        mBatchList.clear();
        fixed_location_flag = "";
        location_fixed = "";
        qnt = null;

        batcharrival = false;
        batcharrivalbtn.setText("入荷バッチ");
        batcharrivalbtn.setBackgroundColor(getResources().getColor(R.color.blue));
        mBatchList.clear();
        setBadge2(0);

        qntyArray = null;
        nyukaIdArray = null;
        orderArray = null;
        locationArray = null;
        classificationIdArray = null;
        classification.setAdapter(null);
        differ = "0";
        setProc(PROC_BARCODE);
        setBadge1(0);
        if (showKeyboard == false) {
            visible = false;
            numbrbtn.setText(R.string.showkeyboard);

            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);

            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);

            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG, "SetlayoutMargin");
            mainlayout.setLayoutParams(params);
        }
        layoutexpiration.setVisibility(View.GONE);
        layoutlot.setVisibility(View.GONE);

        dialog2 = new Dialog(DirectarrivalActivity.this);

    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_BARCODE:
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.quantity, buff);
                break;
            case PROC_LOCATION: //
                _sts(R.id.add_location, buff);
                break;
            case PROC_LOT_NO: //
                _sts(R.id.lotno, buff);
                break;
            case PROC_EXPIRATION: //
                _sts(R.id.expirationdate, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        Log.e(TAG, "scannedEvent   " + barcode);

       /* String data = barcode;
        String[] items = data.split(",");
        for (String item : items){
            System.out.println("item = " + item);
        }*/

        if (MoveStock.dialogdismissed == true) {
            MoveStock.dialog.dismiss();
            MoveStock.dialogdismissed = false;
        }

        if (dialog2.isShowing()) {
        } else {

            if (!MainAsyncTask.dialogBox.isShowing()) {
                if (!barcode.equals("")) {
                    if (mProcNo == PROC_BARCODE) {
                        Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                        String finalbarcode = CommonFunctions.getBracode(barcode);
                        barcode = finalbarcode;

                        _sts(R.id.barcode, barcode);
                    }

                    if (mProcNo == PROC_QTY) {
                        Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                        String finalbarcode = CommonFunctions.getBracode(barcode);
                        barcode = finalbarcode;


                    }

                    if (mProcNo == PROC_LOCATION) _sts(R.id.add_location, barcode);

                    if (mProcNo == PROC_LOT_NO)
                        _sts(R.id.lotno, barcode);
                }
                this.inputedEvent(barcode, true);

            } else
                Toast.makeText(getApplicationContext(), "Please Wait", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean matchString(String str, String match) {
        Pattern p = Pattern.compile(match);
        Matcher m = p.matcher(str);

        if (m.find()) {
            return true;
        }
        return false;
    }


    protected boolean showOrders() {
        //  startPackingBoxActivity();
        popupWindow = new PopupWindow(this);
        if (mBatchList == null) {
            return true;
        }

        // レイアウト設定
        View popupView = getLayoutInflater().inflate(R.layout.activity_batch_arrivals, null);
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

        Button close = (Button) getPopupWindow().getContentView().findViewById(R.id.packing_close_btn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        Button addlocation = (Button) getPopupWindow().getContentView().findViewById(R.id.btn_add_location);
        addlocation.setText("ロケを確定");
        addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProc(PROC_LOCATION);
                popupWindow.dismiss();
            }
        });

        ListView lv = (ListView) getPopupWindow().getContentView().findViewById(R.id.lvPackingList);
        initWorkList(lv);

        // 画面中央に表示
        getPopupWindow().showAtLocation(findViewById(R.id.barcode), Gravity.CENTER, 0, 32);
        return false;
    }

    protected ListViewItems initWorkList(final ListView lv) {
        Log.e("NewPickingActivity", " initWorkList");
        lv.setAdapter(null);

        ListViewItems data = new ListViewItems();
        for (int i = 0; i <= mBatchList.size() - 1; i++) {
            Map<String, String> row = mBatchList.get(i);

            data.add(data.newItem().add(R.id.btc_ins_0, i + 1 + "")
                    .add(R.id.btc_ins_1, row.get("barcode"))
                    .add(R.id.btc_ins_2, row.get("quantity"))
            );
        }
        ListViewAdapter adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.batch_arrival_list) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ImageView img = (ImageView) v.findViewById(R.id.delete_batch);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBatchList.remove(position);

                        initWorkList(lv);
                    }
                });
                return v;
            }
        };

        lv.setAdapter(adapter);
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }

    @OnClick(R.id.enter) void enter() {
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
    }

    @Override
    public void enterEvent() {
        if (mProcNo == PROC_QTY) {
            inputedEvent("", false);
        } else if (mProcNo == PROC_LOCATION) {
            inputedEvent("", false);
        }
    }

    @Override
    public void deleteEvent(String barcode) {
        Log.e(TAG, "deleteEvent");
        switch (mProcNo) {

            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.quantity, barcode);
                break;
            case PROC_LOT_NO: //
                _sts(R.id.lotno, barcode);
                break;
            case PROC_EXPIRATION: //
                _sts(R.id.expirationdate, barcode);
                break;
            case PROC_LOCATION: //
                _sts(R.id.add_location, barcode);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();
    }
//9953470751


    @Override
    protected void onStop() {
        mTextToSpeak.resetQueue();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {

        Log.e(TAG, result.toString());
        count = 0;
        try {

            String response = result.toString();
            JsonPullParser parser = JsonPullParser.newParser(response);
            JsonHash map1 = JsonHash.fromParser(parser);
            Log.e(TAG, " " + map1);

            code = map1.getStringOrNull("code");
            msg = map1.getStringOrNull("message");


            result1 = map1.getJsonArrayOrNull("results");


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
            System.out.print(e);
            e.printStackTrace();
        }
    }

    @Override
    public void onPostError(int flag) {
        if (count > 50) {
            Log.e(TAG, ">>>>>>   counttt1" + count);
            showdialog("ネットワークに接続をできません、\n ネットワーク確認してください。");
            count = 0;
        } else // Repeate the request 10 times if not successful
        {
            count++;
            Log.e(TAG, ">>>>>>>>   count " + count);
            if (mRequestStatus == REQ_BARCODE1) {
                new MainAsyncTask(this, Globals.Webservice.getArrival, 1, DirectarrivalActivity.this, "Form", Globals.getterList, true).execute();
            }
            if (mRequestStatus == REQ_BARCODE2) {
                new MainAsyncTask(this, Globals.Webservice.getAllocationArrival, 1, DirectarrivalActivity.this, "Form", Globals.getterList, true).execute();
            } else if (mRequestStatus == REQ_QTY) {
                new MainAsyncTask(this, Globals.Webservice.moveStockArrival, 1, DirectarrivalActivity.this, "Form", Globals.getterList, true).execute();
            } else if (mRequestStatus == REQ_INITIAL) {
                new MainAsyncTask(this, Globals.Webservice.listPrinter, 1, DirectarrivalActivity.this, "Form", Globals.getterList, true).execute();
            }

        }
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
        TextView txt = (TextView) dialog.findViewById(R.id.txt);
        txt.setText(msg);
        ImageView close = (ImageView) dialog.findViewById(R.id.icon_close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // Make dialog box visible.
        dialog.show();
    }

    public void showPopup() {
        new SweetAlertDialog(this).setTitleText("入荷指示に消費期限が含まれています。").show();
    }

    public void listviewDialog() {

        Rect displayRectangle = new Rect();
        Window window = DirectarrivalActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        AlertDialog.Builder builder = new AlertDialog.Builder(DirectarrivalActivity.this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.custom, viewGroup, false);
        dialogView.setMinimumWidth((int) (displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int) (displayRectangle.height() * 1f));

        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        listview = (ListView) dialogView.findViewById(R.id.listview);

        ImageView close = (ImageView) dialogView.findViewById(R.id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        Log.e(TAG, "Size of ArrivalList " + mArrivalList.size() + "       " + mArrivalList);

        for (int i = 0; i < mArrivalList.size(); i++) {
            Map<String, String> row = mArrivalList.get(i);

            data.add(data.newItem().add(R.id.pck_0, row.get("sup_date"))
                    .add(R.id.pck_1, row.get("order_no"))
                    .add(R.id.pck_2, row.get("comp_name"))
                    .add(R.id.pck_3, row.get("qty"))
            );
            used.add(i);
        }

        adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.arrival_list_row_dialog) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == mSelectedItem) {
                    Log.e(TAG, "selected position " + position);
                    v.setBackgroundColor(Color.YELLOW);
                } else {
                    if (position % 2 == 1) {
                        Log.e(TAG, "Odd  position");
                        v.setBackgroundColor(Color.GRAY);
                    } else {
                        Log.e(TAG, "Even   position");
                        v.setBackgroundColor(Color.WHITE);
                    }
                }

                return v;
            }
        };
        listview.setAdapter(adapter);
        // 単一選択モードにする
        listview.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        // デフォルト値をセットする
        if (data.getData().size() > 0) {
            lv.setItemChecked(0, true);
            lv.setSelection(0);
            setNyukaId(nyukaIdArray.get(0));
            setQnty(qntyArray.get(POSTION));
            //QQQQ=qntyArray.get(0);

            if (getNyukaId().equals("999") && arrivalScheduleSelected) {
                showPopup("入荷予定がありません。");
                U.beepError(this, null);
                _sts(R.id.barcode, "");
                setProc(PROC_BARCODE);
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialog.dismiss();
                alertDialog.cancel();


                eop = position;

                _sts(R.id.Remarks, mArrivalList.get(position).get("comment"));
                _sts(R.id.Standard1, mArrivalList.get(position).get("spec1"));
                _sts(R.id.Standard2, mArrivalList.get(position).get("spec2"));
                productName.setText(mArrivalList.get(position).get("name"));
                int val = findIndex(mArrivalList.get(position).get("stock_type_id"));
                if(val>=0){
                    classification.setSelection(val);
                }
                else
                    classification.setSelection(0);


                pck0.setText(mArrivalList.get(position).get("sup_date"));
                pck1.setText(mArrivalList.get(position).get("order_no"));
                pck2.setText(mArrivalList.get(position).get("comp_name"));
                pck3.setText(mArrivalList.get(position).get("qty"));


                mSelectedItem = position;
                adapter.notifyDataSetChanged();

                if (nyukaIdArray != null && nyukaIdArray.size() > position) {
                    setNyukaId(nyukaIdArray.get(position));
                    setQnty(qntyArray.get(position));
                    //QQQQ=qntyArray.get(position);

                    if (getNyukaId().equals("999")) {
                        ll_comment.setVisibility(View.GONE);
                        Log.e(TAG, "Spinner 5533      " + getNyukaId() + "       " + attribute);
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
                        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_new_arrival);
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
                                                showPopup();
                                                _sts(R.id.expirationdate, map.get("expiration_date"));
                                            } else if (mProcNo != PROC_EXPIRATION)
                                                setProc(PROC_EXPIRATION);
                                        } else if (attribute.equals("3")) {

                                            if (!map.get("expiration_date").equals(""))
                                                showPopup();

                                            _sts(R.id.lotno, map.get("lot"));
                                            _sts(R.id.expirationdate, map.get("expiration_date"));
                                            if (_gts(R.id.lotno).equals(""))
                                                setProc(PROC_LOT_NO);
                                            else if (_gts(R.id.expirationdate).equals("") && mProcNo != PROC_EXPIRATION)
                                                setProc(PROC_EXPIRATION);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                alertDialog.dismiss();
                // dialogList.dismiss();
                if (position == 0) {
                    if (!mArrivalList.get(position).get("admin_id").equals("") && !mArrivalList.get(position).get("admin_id").equals(adminID))
                        showPopup("他のユーザーが作業中です。");
                } else
                    sendNyukaRequest(nyukaIdArray.get(position));
            }
        });
    }

    public void showPopup(String msg) {
        new SweetAlertDialog(this).setTitleText(msg).show();
    }

    void sendNyukaRequest(String nyuka) {
        progress.Show();
        NyukaSelectReq req = new NyukaSelectReq(app.getSerial(), adminID, BaseActivity.getShopId(), nyuka);
        manager.NyukaSelect(req, nyukacallback);
    }

    @OnClick(R.id.ll_listdialog)
    void click() {
        if (mArrivalList.size() != 0) {
            listviewDialog();
        }
    }

    public void LISTOPEN(){
        if (mArrivalList.size() != 0) {
            if (mArrivalList.size()>1){
                listviewDialog();}
        }
    }


    @Override
    public void onSucess(int status, NyukaSelectResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("200")) {

        } else if (message.getCode().equalsIgnoreCase("401")) {
            showPopup("他のユーザーが作業中です。");

        } else {
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, ResponseBody message) throws JsonIOException {
        if (status==200){
            progress.Dismiss();
            Result();
        }

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
            }else{
                adap = new Adap_arrivalLoc_list(this, message.getResults());
                listLocations.setAdapter(adap);
                adap.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onError(int status, ResponseBody error) {
        progress.Dismiss();
    }

    @Override
    public void onFaliure() {
        progress.Dismiss();
    }

    @Override
    public void onNetworkFailure() {
        progress.Dismiss();
    }

    public void dilaogCustomercancel() {

        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.setCancelable(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setContentView(R.layout.weightsubmit);

        final EditText weight = (EditText) dialog2.findViewById(R.id.weight);
        close = (ImageView) dialog2.findViewById(R.id.closee);
        weight.setFocusable(true);
        weight.setFocusableInTouchMode(true);
        weight.requestFocus();
        //  text.setText(remark);
        Button ok = (Button) dialog2.findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weight.getText().toString().equals("")||weight.getText().toString().equals("0")){
                    U.beepError(DirectarrivalActivity.this,"重さを入力してください");
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
            Log.e(TAG, "CODE====Null");
            CommonDialogs.customToast(getApplicationContext(), "Network error occured");
        }

        if ("0".equals(code)) {

            Log.e("SendLogs111", code + "  " + msg + "  " + result1);

            if (mRequestStatus == REQ_BARCODE1) {
                new GetArrivalNew().post(code, msg, result1, mHash, DirectarrivalActivity.this);
            } else if (mRequestStatus == REQ_BARCODE2) {


                new GetNewArrivalAllocation().post(code, msg, result1, mHash, DirectarrivalActivity.this);
            } else if (mRequestStatus == REQ_QTY) {
                new MoveStock().post(code, msg, result1, mHash, DirectarrivalActivity.this);
            } else if (mRequestStatus == REQ_INITIAL) {
                count = 0;
            }

        } else if (code.equalsIgnoreCase("1020")) {
            new AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(msg)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent in = new Intent(DirectarrivalActivity.this, LoginActivity.class);
                            in.putExtra("ACTION", "logout");
                            startActivity(in);
                            finish();

                            dialog.dismiss();
                        }
                    })

                    .show();
        } else {
            if (mRequestStatus == REQ_BARCODE1) {
                new GetArrivalNew().valid(code, msg, result1, mHash, DirectarrivalActivity.this);
            }
            if (mRequestStatus == REQ_BARCODE2) {
                new GetNewArrivalAllocation().valid(code, msg, result1, mHash, DirectarrivalActivity.this);
            } else if (mRequestStatus == REQ_QTY) {
                new MoveStock().valid(code, msg, result1, mHash, DirectarrivalActivity.this);

            } else if (mRequestStatus == REQ_INITIAL) {
                U.beepError(this, msg);

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
                startActivity(new Intent(DirectarrivalActivity.this, SettingActivity.class));
                finish();
            }
        });

        dialog.show();
    }

    int findIndex(String val){
        for(int i = 0; i<classificationIdArray.size();i++){
            if(val.equals(classificationIdArray.get(i))){
                return i;
            }
        }
        return -1;
    }
}
