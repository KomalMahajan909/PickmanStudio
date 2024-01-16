package com.itechvision.ecrobo.pickman.Chatman.Arrivals;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetAllocation;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetPartNumberAllocation;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.ListStockId;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.MoveStock;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
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

public class AllocationActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {

    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(id.actionbar) ActionBar actionbar;
    @BindView(id.add_layout) Button numbrbtn;
    @BindView(id.layout_number) ViewGroup layout;
    @BindView(id.layout_main) RelativeLayout mainlayout;
    @BindView(id.classificationspinner) Spinner classification;
    @BindView(id.check_caseqty) CheckBox checkCaseQty;

    //@BindView(R.id.listAllocation) ListView allocationlistView;
    @BindView(id.listLocations) ListView locationlistView;
    @BindView(id.lotno) EditText lot;
    @BindView(id.expiration) EditText exp;
    @BindView(id.spinnerlayout) RelativeLayout spinnerLayout;
    @BindView(id.partnoSpinner) Spinner partNospinner;
    @BindView(id.pck_0) TextView pck0;
    @BindView(id.pck_1) TextView pck1;
    @BindView(id.pck_2) TextView pck2;
    @BindView(id.pck_3) TextView pck3;
    @BindView(id.abc) EditText abc;
    @BindView(id.ll_abc) LinearLayout ll_abc;

    public LinearLayout partnoLayout;
    public TextView productName;
    public LinearLayout lotlayout, explayout;
    protected int mProcNo = 0;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_QTY = 2;
    public static final int PROC_LOCATION = 3;
    public static final int PROC_LOT_NO = 4;
    private boolean showKeyboard;
    private boolean visible = false, multicode = false;
    private TextToSpeak mTextToSpeak;
    public Context mcontext = this;
    private boolean orderRequestSettings;
    public String classificationId = "";
    public static int lotpress = 0;
    public static int mRequestStatus = 0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_BARCODE = 2;
    public static final int REQ_CODE = 7;
    public static final int REQ_CLASSIFICATION = 5;
    public static final int REQ_LOT = 3;
    public static final int REQ_QTY = 4;
    public static final int PROC_PARTNO = 6;
    View dialogView;
    AlertDialog alertDialog;
    public ListView listview;
    ListViewAdapter adapter;
    public boolean lotexist = false;
    private ArrayList<HashMap<String, String>> allocationlist = new ArrayList<>();
    private ArrayList<HashMap<String, String>> zloclist = new ArrayList<>();
    private ArrayList<HashMap<String, String>> loclist = new ArrayList<>();
    private ArrayList<HashMap<String, String>> jsonlist = new ArrayList<>();
    protected ArrayList<String> PartnoArray = new ArrayList<String>();
    protected ArrayList<String> defaultStockArray = new ArrayList<String>();
    protected ArrayList<String> classificationIdArray = new ArrayList<String>();
    public ArrayList<String> defaultclassificationIdArray = new ArrayList<String>();
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    ECRApplication app = new ECRApplication();
    String adminID = "",warehouseID = "";
    int mSelectedItem = 0;
    // for fixed location barcodes
    public static String fixed_location_flag = "";
    public static String location_fixed = "";
    String TAG = AllocationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocation);
        ButterKnife.bind(AllocationActivity.this);

        getIDs();

        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        lotlayout = (LinearLayout) findViewById(id.gridlot);
        explayout = (LinearLayout) findViewById(id.gridExpiration);
        partnoLayout = (LinearLayout) findViewById(id.partnoLayout);
        productName = (TextView) findViewById(id.product_name);

        orderRequestSettings = false;
        if (BaseActivity.getLotPress()) {
            lotlayout.setVisibility(View.VISIBLE);
            explayout.setVisibility(View.VISIBLE);
        } else {
            lotlayout.setVisibility(View.GONE);
            explayout.setVisibility(View.GONE);
        }

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);
        warehouseID = spDomain.getString("warehouse_id",null);

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
            Log.e(TAG, "SetlayoutMargin");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }


        if(BaseActivity.get_CaseQtyCheckSelfPut()){
            checkCaseQty.setChecked(true);
        }
        else checkCaseQty.setChecked(false);


/*        allocationlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            HashMap<String, String> map = null;
            if (mProcNo == PROC_LOT_NO) {
                map = allocationlist.get(position);
                Log.e(TAG, "data selected is   " + map);
                String selectedlot = map.get("lot");
                if (!selectedlot.equals("")) {
                    lot.setText(map.get("lot"));
                    exp.setText(map.get("expiration"));
                    _sts(R.id.totalquantity, map.get("quantity"));
                    setProc(PROC_QTY);
                }
            }
        }
    });*/

        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);
        setBarcodeTemp(null);
        _gt(id.product_code).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        if (mProcNo == 0) nextProcess();

        classification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (classificationIdArray != null && classificationIdArray.size() > position) {
                    setclassificationId(classificationIdArray.get(position));
                    Log.e(TAG, "Selected Classification " + classificationIdArray.get(position));
                } else {
                    setclassificationId("");
                    Log.e(TAG, "Selected Classification" + getclassificationId() + "null");
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
                    Log.e(TAG, "Selected partNospinner" + position);
                    String code = PartnoArray.get(position);
                    Log.e(TAG, "Selected partNospinner" + code);
                    _sts(R.id.product_code, code);

                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
                    Globals.getterList.add(new ParamsGetter("lot_no", ""));
                    Globals.getterList.add(new ParamsGetter("code", code));
                    Globals.getterList.add(new ParamsGetter("source", "z"));

                    mRequestStatus = REQ_CODE;

                    new MainAsyncTask(AllocationActivity.this, Globals.Webservice.getAllocation, 1, AllocationActivity.this, "Form", Globals.getterList, true).execute();

//                    setAttributeProc(code);
//                    getList(code);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkCaseQty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkCaseQty.isChecked()){
                    BaseActivity.set_CaseQtyCheckSelfPut(true);
                }
                else{
                    BaseActivity.set_CaseQtyCheckSelfPut(false);
                }
            }
        });
    }

    //set title and icons on actionbar
    private void getIDs() {

        actionbarImplement(this, "棚入れ", " ",
                0, true, false, false);
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnBlue.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case id.relLayout1:
                menu.showMenu();
                break;

            default:
                break;
        }
    }

    @OnClick(id.ll_listdialog) void click() {
        if (zloclist.size() != 0) {
            listviewDialog();
        }
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
            Log.e(TAG, "SetlayoutMargin");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
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

    @Override
    protected void onDestroy() {
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();
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

    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {
            case PROC_BARCODE:
                Log.e(TAG, "PROCCCCCCCC BARCODE");
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.location).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
//                _glv(id.listAllocation).setAdapter(null);
                _gt(id.barcode).setFocusableInTouchMode(true);
                if (orderRequestSettings == true)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                break;
            case PROC_PARTNO:
                Log.e(TAG, "PROCCCCCCCC PARTNO");
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.location).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                spinnerLayout.setFocusableInTouchMode(true);
                if (orderRequestSettings == true)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                break;
            case PROC_QTY:
                Log.e(TAG, "PROCCCCCCCC QUANTITY");
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.location).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setFocusableInTouchMode(true);

                if (orderRequestSettings == true)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                break;
            case PROC_LOCATION:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.location).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.location).setFocusableInTouchMode(true);
                if (orderRequestSettings == true)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                break;
            case PROC_LOT_NO:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.location).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.lotno).setFocusableInTouchMode(true);
                _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        String lot = "";
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
//                allocationlistView.setAdapter(null);
                locationlistView.setAdapter(null);
                setKey(1);
                Globals.getterList = new ArrayList<>();

                Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("barcode", _gts(id.barcode)));
                Globals.getterList.add(new ParamsGetter("lot_no", lot));
                Globals.getterList.add(new ParamsGetter("source", "z"));

                mRequestStatus = REQ_BARCODE;

                new MainAsyncTask(this, Globals.Webservice.getAllocation, 1, AllocationActivity.this, "Form", Globals.getterList, true).execute();


                break;
            case PROC_LOT_NO:    // バーコード
                lot = _gts(id.lotno);
                if (lot.equals("") || lot.equals("0")) {
                    U.beepError(this, "ロット番号が必要");
                    break;
                }
                setKey(4);
                Globals.getterList = new ArrayList<>();

                Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("barcode", _gts(id.barcode)));
                Globals.getterList.add(new ParamsGetter("lot_no", lot));

                mRequestStatus = REQ_BARCODE;

                new MainAsyncTask(this, Globals.Webservice.getAllocation, 1, AllocationActivity.this, "Form", Globals.getterList, true).execute();

                break;

            case PROC_QTY: // 数量

                String quantity = _gts(id.quantity);
                String barcode = _gts(id.barcode);
                String lotn = "";
                Log.e(TAG, " inputedEventt  Qtyyy");
                if (isScaned) {
                    if (orderRequestSettings == true && lotexist == true) {
                        Log.e(TAG, " inputedEventt  Qtyyy1111");
                        lotn = _gts(id.lotno);
                        if (buff.equals(barcode) || buff.equals(lotn)) {
                            if (buff.equals(barcode) && !lotn.equals("")) {
                                Log.e(TAG, " inputedEventt  Qtyyy2222");
                                /*if barcode2 is not empty then store barcode1 and wait till second barcode*/
                                setBarcodeTemp(buff); /*store barcode1 in static variable*/
                                break;
                            }

                            if (buff.equals(lotn) && !barcode.equals("")) {
                                if (!(getBarcodeTemp().concat(buff)).equals((barcode.concat(lotn)))) {
                                    Log.e(TAG, " inputedEventt  Qtyyy3333333");
                                    U.beepError(this, "Scanned barcode and lotno not matched");
                                    break;
                                }
                            }
                            if ((getBarcodeTemp().concat(buff)).equals((barcode.concat(lotn)))) {
                                Log.e(TAG, " inputedEventt  Qtyyy444444444444");
                                U.beepSuccess();
                                String val = U.plusTo(_gts(id.quantity), "1");
                                _sts(id.quantity, val);
                                mTextToSpeak.startSpeaking(val);
                            }
                            break;
                        } else {
                            U.beepError(this, "Barcode dont match");
                            _gt(id.quantity).setFocusableInTouchMode(true);
                            break;
                        }
                    } else {
                        if (buff.equals(barcode)) {
                            U.beepSuccess();
                            String val = U.plusTo(_gts(id.quantity), "1");
                            _sts(id.quantity, val);
                            mTextToSpeak.startSpeaking(val);
                            break;
                        } else {
                            U.beepError(this, "Barcode dont match");

                            _gt(id.quantity).setFocusableInTouchMode(true);
                            break;
                        }
                    }
                } else {
                    if ("".equals(quantity)) {
                        U.beepError(this, "数量は必須です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(quantity)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;

                    }
                    else if (U.compareNumeric(quantity,_gts(id.totalquantity)) == -1) {
                        U.beepError(this, "Quantity should not exceed total quantity");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else {
                        U.beepSuccess();
                        setProc(PROC_LOCATION);
                    }
                }
                break;
            case PROC_LOCATION:
                String loc = _gts(id.location);
                String lotno = "";
                String expiration = "";

                try {
                    if (BaseActivity.getLotPress()) {
                        lotno = zloclist.get(mSelectedItem).get("lot");
                        expiration = zloclist.get(mSelectedItem).get("expiration");
                    }
                    if ("".equals(loc)) {
                        U.beepError(this, "ロケーションは必須です");
                        _gt(id.location).setFocusableInTouchMode(true);
                        break;
                    }

                    if (fixed_location_flag.equals("1") && !loc.equals(location_fixed) && !location_fixed.equals("")) {
                        U.beepError(this, "固定ロケーションの品番に複数のロケーションを登録することは出来ません");
                        _gt(id.location).setFocusableInTouchMode(true);
                        break;
                    }

                    String classification = "";

                    classification = getclassificationId();
                    stopTimer();
                    Globals.getterList = new ArrayList<>();

                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    if (!multicode)
                        //  Globals.getterList.add(new ParamsGetter("barcode", _gts(id.barcode)));
                        Globals.getterList.add(new ParamsGetter("barcode", _gts(id.product_code)));
                    else

                        Globals.getterList.add(new ParamsGetter("barcode", _gts(id.product_code)));
                    Globals.getterList.add(new ParamsGetter("qty", _gts(id.quantity)));
                    Globals.getterList.add(new ParamsGetter("stock_type_id", classification));
                    Globals.getterList.add(new ParamsGetter("source", "z"));
                    Globals.getterList.add(new ParamsGetter("destination", loc));
                    Globals.getterList.add(new ParamsGetter("product_id", zloclist.get(mSelectedItem).get("product_id")));
                    Globals.getterList.add(new ParamsGetter("product_stock_id", zloclist.get(mSelectedItem).get("id")));
                    Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));


                    mRequestStatus = REQ_QTY;

                    new MainAsyncTask(this, Globals.Webservice.self_put, 1, AllocationActivity.this, "Form", Globals.getterList, true).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    public void setAttributeProc(String code) {
        ListViewItems data = new ListViewItems();
        HashMap<String, String> map = new HashMap<>();
        String selectedlot = null;
        String selectedexp = null;
        String selectedqty = null;

        String standard1 = "";
        String standard2 = "";
        int count = 0;
        String qty = "";
        String name = "";

        for (Map<String, String> map1 : loclist) {
            String c = map1.get("code");
            String loc = map1.get("location");

            if (!"".equals(loc) && c.equals(code)) {
                if (BaseActivity.getLotPress()) {
                    data.add(data.newItem().add(id.arr_all_0, map1.get("location"))
                            .add(id.arr_all_2, map1.get("lot"))
                            .add(id.arr_all_1, map1.get("expiration"))
                            .add(id.arr_all_3, map1.get("quantity")));

                    map.put("location", map1.get("location"));
                    map.put("quantity", map1.get("quantity"));
                    String lot = map1.get("lot");
                    String expiration = map1.get("expiration");
                    String qnty = map1.get("quantity");
                    String location = map1.get("location");

                    map.put("lot", lot);
                    map.put("expiration", expiration);

                    if (location.equals("z") && !lot.equals("") && Integer.parseInt(qnty) > 0) {
                        selectedlot = lot;
                        selectedqty = qnty;
                        selectedexp = expiration;

                        count++;
                    }
                } else {
                    data.add(data.newItem().add(id.arr_all_0, map1.get("location"))
                            .add(id.arr_all_3, map1.get("quantity")));
                    map.put("location", map1.get("location"));
                    map.put("quantity", map1.get("quantity"));
                }

                if (map1.containsKey("fix_location_flag")) {
                    AllocationActivity.fixed_location_flag = map1.get("fix_location_flag");
                    if (!loc.equals("z") && !loc.equals("y"))
                        AllocationActivity.location_fixed = loc;
                    Log.e("Get aloocation  ", "fixedLocation " + AllocationActivity.location_fixed);
                }
                allocationlist.add(map);

            }

            if (c.equals(code)) {
                name = map1.get("name");
                standard1 = map1.get("spec1");
                standard2 = map1.get("spec2");
                if (qty.equals("")) {
                    Log.e(TAG, "QTYYYY  " + qty);
                    qty = map1.get("rest");
                    Log.e(TAG, "QTYYYY  rest" + qty);
                }
            }
        }

        if (data.getData().size() > 0) {
            ListViewAdapter adapter = new ListViewAdapter(
                    this
                    , data
                    , R.layout.list_allocation_row) {
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    LinearLayout lot = (LinearLayout) v.findViewById(id.layoutlot);
                    LinearLayout exp = (LinearLayout) v.findViewById(id.layoutexp);
                    if (BaseActivity.getLotPress()) {
                        lot.setVisibility(View.VISIBLE);
                        exp.setVisibility(View.VISIBLE);
                    } else {
                        lot.setVisibility(View.GONE);
                        exp.setVisibility(View.GONE);
                    }
                    return v;
                }
            };
            ListView lv = (ListView) findViewById(id.listLocations);
            lv.setAdapter(adapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_NONE);
        }

        if (orderRequestSettings) {
            if (mProcNo == AllocationActivity.PROC_LOT_NO) {
                Log.e("GetAllocation", "Lot no condition 1111");
                lotexist = true;
                setProc(AllocationActivity.PROC_QTY);
            } else {
                if (count == 0) {
                    Log.e("GetAllocation", "Lot no condition 2222");
                    lotexist = false;
                    lotlayout.setVisibility(View.GONE);
                    explayout.setVisibility(View.GONE);
                    setProc(PROC_QTY);
                } else {
                    Log.e("GetAllocation", "Lot no condition 333");
                    lotexist = false;
                    _sts(id.lotno, selectedlot);
                    _sts(id.expiration, selectedexp);
                    _sts(id.totalquantity, selectedqty);
                    setProc(PROC_QTY);
                }
            }
        } else
        {
            setProc(PROC_QTY);
        }
        startTimer();
        _sts(id.quantity, "1");
        _sts(id.totalquantity, qty);
        _sts(id.product_code, code);
        _stxtv(id.product_name, name);
        _sts(id.standard, standard1 + "  " + standard2);
        setAllocationlist(allocationlist);

    }

    public void setAllocationlist(ArrayList<HashMap<String, String>> data) {
        allocationlist = data;
        Log.e(TAG, "List becomess " + allocationlist);
    }

    public void setZlocationlist(ArrayList<HashMap<String, String>> data) {
        zloclist = data;
        Log.e(TAG, "List becomess zLocListtt " + zloclist);

        _sts(id.lotno, zloclist.get(0).get("lot"));
        _sts(id.expiration, zloclist.get(0).get("expiration"));
        _sts(id.totalquantity, zloclist.get(0).get("quantity"));

        pck0.setText(zloclist.get(0).get("location"));
        pck1.setText(zloclist.get(0).get("lot"));
        pck2.setText(zloclist.get(0).get("expiration"));
        pck3.setText(zloclist.get(0).get("quantity"));

        LinearLayout lot = (LinearLayout) findViewById(id.lay_lot);
        LinearLayout exp = (LinearLayout) findViewById(id.lay_exp);
        if (BaseActivity.getLotPress()) {
            lot.setVisibility(View.VISIBLE);
            exp.setVisibility(View.VISIBLE);
        } else {
            lot.setVisibility(View.GONE);
            exp.setVisibility(View.GONE);
        }

        mSelectedItem = 0;

        if (zloclist.size() > 1)
            listviewDialog();
    }

    public void setlocationlist(ArrayList<HashMap<String, String>> data) {
        loclist = data;
        Log.e(TAG, "List becomes  LocList " + loclist);
    }

    public void getJsonData(ArrayList<HashMap<String, String>> data, boolean multi, List<String> part) {
        jsonlist = data;
        multicode = multi;
        PartnoArray = (ArrayList<String>) part;
        Log.e(TAG, "List becomes jsonlist " + jsonlist);

    }

    public void getClassificationList() {
        Globals.getterList = new ArrayList<>();

        Log.e(TAG, "shopid  " + BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));

        mRequestStatus = REQ_CLASSIFICATION;

        new MainAsyncTask(this, Globals.Webservice.listStockIDs, 1, AllocationActivity.this, "Form", Globals.getterList, true).execute();

    }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    public static void setKey(int poc) {
        lotpress = poc;
    }

    public static int getKey() {
        return lotpress;
    }

    @Override
    public void clearEvent() {
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

    public void listviewDialog() {

        Rect displayRectangle = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
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
                Log.e(TAG, "1111111 ");
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

        for (int i = 0; i <= zloclist.size() - 1; i++) {
            Map<String, String> row = zloclist.get(i);

            if (BaseActivity.getLotPress()) {
                data.add(data.newItem().add(id.arr_all_0, row.get("location"))
                        .add(id.arr_all_2, row.get("lot"))
                        .add(id.arr_all_1, row.get("expiration"))
                        .add(id.arr_all_3, row.get("quantity")));
            } else {
                data.add(data.newItem().add(id.arr_all_0, row.get("location"))
                        .add(id.arr_all_3, row.get("quantity")));
            }

            used.add(i);

        }

        adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.list_allocation_row) {
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

                LinearLayout lot = (LinearLayout)v.findViewById(id.layoutlot);
                LinearLayout exp = (LinearLayout)v.findViewById(id.layoutexp);
                if (BaseActivity.getLotPress()) {
                    lot.setVisibility(View.VISIBLE);
                    exp.setVisibility(View.VISIBLE);
                } else {
                    lot.setVisibility(View.GONE);
                    exp.setVisibility(View.GONE);
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

                _sts(R.id.lotno, zloclist.get(position).get("lot"));
                _sts(R.id.expiration, zloclist.get(position).get("expiration"));
                _sts(R.id.totalquantity, zloclist.get(position).get("quantity"));

                pck0.setText(zloclist.get(position).get("location"));
                pck1.setText(zloclist.get(position).get("lot"));
                pck2.setText(zloclist.get(position).get("expiration"));
                pck3.setText(zloclist.get(position).get("quantity"));

                LinearLayout lot = (LinearLayout) findViewById(R.id.lay_lot);
                LinearLayout exp = (LinearLayout) findViewById(R.id.lay_exp);
                if (BaseActivity.getLotPress()) {
                    lot.setVisibility(View.VISIBLE);
                    exp.setVisibility(View.VISIBLE);
                } else {
                    lot.setVisibility(View.GONE);
                    exp.setVisibility(View.GONE);
                }

                mSelectedItem = position;

                alertDialog.dismiss();
            }
        });


        if (data.getData().size() > 0) {
        /*    allocationlistView.setItemChecked(0, true);
            allocationlistView.setSelection(0);*/
//            Log.e(TAG, "selectedNyukaaa   " + nyukaIdArray.get(0));
//            setNyukaId(nyukaIdArray.get(0));

        }
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
            case PROC_LOCATION:
                _sts(id.location, buff);
                break;
            case PROC_LOT_NO:
                _sts(id.lotno, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (MoveStock.dialogdismissed == true) {
            MoveStock.dialog.dismiss();
            MoveStock.dialogdismissed = false;
        }
        if (!MainAsyncTask.dialogBox.isShowing()) {
            if (!barcode.equals("")) {
                if (mProcNo == PROC_BARCODE) {
                    if(warehouseID.equalsIgnoreCase("20722") && barcode.length()>18){
                        String withoutBrackets = barcode.replaceAll("\\)","");
                        withoutBrackets= withoutBrackets.replaceAll("\\(","");
                        String finalbarcode= withoutBrackets.substring(3,16);
                        _sts(R.id.barcode,finalbarcode);
                    }
                    else {
                        Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                        String finalbarcode = CommonFunctions.getBracode(barcode);
                        barcode = finalbarcode;

                        _sts(id.barcode, barcode);
                    }
                } else if (mProcNo == PROC_QTY) {
                    String finalbarcode = CommonFunctions.getBracode(barcode);
                    barcode = finalbarcode;
                    Log.e(TAG, "barcode111   " + barcode);
                } else if (mProcNo == PROC_LOCATION) _sts(id.location, barcode);
                else if (mProcNo == PROC_LOT_NO) _sts(id.lotno, barcode);
            }
            this.inputedEvent(barcode, true);
        } else
            Toast.makeText(getApplicationContext(), "Please Wait", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void nextProcess() {
        _sts(id.barcode, "");
        _sts(id.quantity, "");
        _sts(id.location, "");
        _sts(id.product_code, "");
        _sts(id.totalquantity, "");
        _stxtv(id.product_name, "");
        _sts(id.standard, "");
        _sts(id.abc, "");
        //   ll_abc.setVisibility(View.VISIBLE);

        classification.setAdapter(null);
        lotexist = false;
        classificationIdArray = null;
        classification.setAdapter(null);
        _sts(id.totalquantity, "");

        partnoLayout.setVisibility(View.GONE);
        partNospinner.setAdapter(null);
        multicode = false;

        if (BaseActivity.getLotPress() == true) {
            _sts(id.lotno, "");
            _sts(id.expiration, "");
            explayout.setVisibility(View.VISIBLE);
            lotlayout.setVisibility(View.VISIBLE);
        } else {
            lotlayout.setVisibility(View.GONE);
            explayout.setVisibility(View.GONE);
        }
//		listView.setEnabled(true);
        setProc(PROC_BARCODE);
        fixed_location_flag = "";
        location_fixed = "";

        locationlistView.setAdapter(null);

        if (!loclist.isEmpty()) {
            loclist.clear();
            zloclist.clear();
        }

        pck0.setText("");
        pck1.setText("");
        pck2.setText("");
        pck3.setText("");

        if (showKeyboard == false) {
            visible = false;
            numbrbtn.setText(R.string.showkeyboard);
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);

            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
            hiddenPanel.setVisibility(View.GONE);
            Log.e(TAG, "SetlayoutMargin");
            mainlayout.setLayoutParams(params);
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
            case PROC_LOCATION: //
                _sts(id.location, barcode);
                break;
            case PROC_LOT_NO: //
                _sts(id.lotno, barcode);
                break;
        }
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

          /*  if (map1.containsKey("use_cmr_order_support")) {
                if (map1.getStringOrNull("use_cmr_order_support").equalsIgnoreCase("1")) {
                    ll_abc.setVisibility(View.VISIBLE);
                } else {
                    ll_abc.setVisibility(View.GONE);
                }
            }  */


            result1 = map1.getJsonArrayOrNull("results");

            if (code == null) {
                Log.e(TAG, "CODE======Null");
                CommonDialogs.customToast(getApplicationContext(), "Network error occured");
            }
            if ("0".equalsIgnoreCase(code)) {

                Log.e("SendLogs", code + "  " + msg + "  " + result1);

                if (mRequestStatus == REQ_BARCODE) {
                    JsonArray zloc = new JsonArray();
                    JsonArray loc = new JsonArray();
                    if (map1.containsKey("z_location")) {
                        zloc = map1.getJsonArrayOrNull("z_location");
                        loc = map1.getJsonArrayOrNull("without_z_location");
                    }
                    new GetAllocation().post(code, msg, result1, zloc, loc, mHash, AllocationActivity.this);
                }
                else if (mRequestStatus == REQ_CODE) {
                    JsonArray zloc = new JsonArray();
                    JsonArray loc = new JsonArray();
                    if (map1.containsKey("z_location")) {
                        zloc = map1.getJsonArrayOrNull("z_location");
                        loc = map1.getJsonArrayOrNull("without_z_location");
                    }
                    new GetPartNumberAllocation().post(code, msg, result1, zloc, loc, mHash, AllocationActivity.this);
                }
                else if (mRequestStatus == REQ_CLASSIFICATION) {
                    new ListStockId().post(code, msg, result1, mHash, AllocationActivity.this);
                } else if (mRequestStatus == REQ_QTY) {
                    new MoveStock().post(code, msg, result1, mHash, AllocationActivity.this);
                }

            }

            else if (code.equalsIgnoreCase("1020")) {
                new AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(AllocationActivity.this, LoginActivity.class);
                                in.putExtra("ACTION", "logout");
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }

            else if (mRequestStatus == REQ_BARCODE) {
                new GetAllocation().valid(code, msg, result1, mHash, AllocationActivity.this);
            } else if (mRequestStatus == REQ_CLASSIFICATION) {
                new ListStockId().valid(code, msg, result1, mHash, AllocationActivity.this);
            } else if (mRequestStatus == REQ_QTY) {
                new MoveStock().valid(code, msg, result1, mHash, AllocationActivity.this);
            } else if (mRequestStatus == REQ_CODE){
                new GetPartNumberAllocation().valid(code, msg, result1, mHash, AllocationActivity.this);
                _sts(id.quantity, "");
                _sts(id.location, "");
                _sts(id.product_code, "");
                _sts(id.totalquantity, "");
                _stxtv(id.product_name, "");
                _sts(id.standard, "");
                locationlistView.setAdapter(null);
                pck0.setText("");
                pck1.setText("");
                pck2.setText("");
                pck3.setText("");

                if (!loclist.isEmpty()) {
                    loclist.clear();
                    zloclist.clear();
                }
            }
        } catch (Exception e) {
            System.out.print(e);
        }

    }

    @Override
    public void onPostError(int flag) {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}



