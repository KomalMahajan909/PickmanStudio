package com.itechvision.ecrobo.pickman.Chatman.Stock;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetDirectReturn;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.GetLocationStock;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.GetProductLotno;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.GetProductStock;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.MoveStock2;
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

public class MoveStockActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_number) ViewGroup layout;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.classificationspinner) Spinner classification;
    @BindView(R.id.scrollMain) ScrollView svMain;
    @BindView(id.gridLotNo) LinearLayout layoutLot;
    @BindView(id.batchRg) RadioGroup batchRg;
    @BindView(id.usebatch) RadioButton usebatch;
    @BindView(id.list)ListView listview;

    @BindView(id.notuse) RadioButton notusebatch;
    @BindView(id.spinnerlayout) RelativeLayout spinnerLayout;
    @BindView(id.spinnerpartlayout) RelativeLayout spinnerPartLayout;
    @BindView(R.id.partnoSpinner) Spinner partNospinner;
    @BindView(id.qtyfixedCheck) CheckBox qtyFixedcheck;
    @BindView(id.locfixedCheck) CheckBox locCheck;
    @BindView(id.locDelete)Button locationBtn;
    @BindView(id.locfixLayout) LinearLayout locLayout;
    @BindView(id.qtyfixLayout) LinearLayout qtyLayout;
    @BindView(id.locationdigitLayout) LinearLayout locationEditLayout;
    @BindView(id.lotno) TextView lotno;
    @BindView(id.expirationdate)
    EditText expirationdate;

    public LinearLayout partnoLayout;
    String  EXPRDAATE ="",APISTOCKID="";
    SharedPreferences sharedPreferences;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public static final String MYPREFERENCE = "MyPrefs";

    ECRApplication app=new ECRApplication();
    String adminID="";

    protected ArrayList<String> classificationIdArray = new ArrayList<String>();
    protected List<Map<String, String>> mProductList = null;
    public static List<Map<String, String>> mBatchList =  new ArrayList<Map<String, String>>();
    private ArrayList<HashMap<String, String>> jsonlist = new ArrayList<>();
    protected ArrayList<String> PartnoArray = new ArrayList<String>();

    public String classificationId = "", lotselected = "", expiration = "",productID ="", pshID = "";
    PopupWindow popupWindow;

    private boolean showKeyboard;
    private boolean visible = false,triplebarcode =false ,multicode = false;
    public TextToSpeak mTextToSpeak;
    public Context mcontext=this;
    private boolean orderRequestSettings ,  batchUse = false, fixQty = true, locbtnpress = false;

    protected int mProcNo = 0;
    public static final int PROC_LOC_FIX = 1;
    public static final int PROC_SOURCE = 2;
    public static final int PROC_BARCODE = 3;
    public static final int PROC_LOT =4;
    public static final int PROC_QTY = 5;
    public static final int PROC_DESTINATION = 6;
    public static final int PROC_CLASSIFICATION = 7;
    public static final int PROC_PARTNO = 8;

    public static int mRequestStatus =0;
    public static final int REQ_BARCODE = 1;
    public static final int REQ_SOURCE = 2;
    public static final int REQ_DESTINATION = 3;
    public static final int REQ_LOT = 5;
    public static int count = 0;
     String lastupdatedQty = "0";
    int mSelectedItem = 0;
    public TextView spec1, spec2;

    String TAG= MoveStockActivity.class.getSimpleName();
    ListViewAdapter     adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_stock);

        ButterKnife.bind(MoveStockActivity.this);

        getIDs();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        orderRequestSettings = BaseActivity.getLotPress();
        if(orderRequestSettings){
            layoutLot.setVisibility(View.VISIBLE);
        }

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);

        adminID = spDomain.getString("admin_id",null);

        spec1 = (TextView)findViewById(id.standard1);
        spec2 = (TextView)findViewById(id.standard2);

        partnoLayout = (LinearLayout)findViewById(R.id.partnoLayout);

        showKeyboard=BaseActivity.getaddKeyboard();
        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(id.layout_number);
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
            Log.e("MoveActivity","SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);
        triplebarcode = BaseActivity.getTripleBarcode();

        spec1.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        spec2.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        _gt(id.product_code).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        _gt(id.currentStock).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        if (mProcNo == 0) nextProcess();

        if(BaseActivity.getBatchSelected()){
            usebatch.setChecked(true);
            notusebatch.setChecked(false);
            batchUse = true;
            locLayout.setVisibility(View.VISIBLE);
            qtyLayout.setVisibility(View.VISIBLE);
        }
        else {
            usebatch.setChecked(false);
            notusebatch.setChecked(true);
            batchUse = false;
        }

        setcheckbox();

        classification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position>0){
                    if (classificationIdArray != null && classificationIdArray.size() > position) {
                        setclassificationId(classificationIdArray.get(position));
                        Log.e("Arrival Activity","Selected Classification "+classificationIdArray.get(position));

                        if(mProcNo == PROC_CLASSIFICATION){
                            setProc(PROC_DESTINATION);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setclassificationId("");
                Log.e("Arrival Activity","Selected Classification"+ getclassificationId() +"null");
            }
        });

        partNospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0 ){

                    String code = PartnoArray.get(position);
                    _sts(R.id.product_code, code);
                    setAttributeProc(code);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        locCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mProcNo < PROC_BARCODE && batchUse) {
                    if (locCheck.isChecked()) {
                        locationEditLayout.setVisibility(View.VISIBLE);
                        setProc(PROC_LOC_FIX);
                    } else {
                        if(locbtnpress){
                            locCheck.setChecked(true);
                            U.beepError(MoveStockActivity.this, null);
                            CommonDialogs.customToast(MoveStockActivity.this, "Please delete the location digits");
                        }
                        else
                        {
                            locationEditLayout.setVisibility(View.GONE);
                            setProc(PROC_SOURCE);
                        }
                    }} else {
                    CommonDialogs.customToast(MoveStockActivity.this, "This action is not allowed after source locaion");
                }
            }
        });

        qtyFixedcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mProcNo < PROC_BARCODE && batchUse) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    if (qtyFixedcheck.isChecked()) {

                        editor.putBoolean("QuantityFix",true);
                        fixQty = true;
                    } else {
                        editor.putBoolean("QuantityFix",false);
                        fixQty = false;
                    }
                    setProc(PROC_SOURCE);
                    editor.commit();
                }

                else {
                    if (qtyFixedcheck.isChecked()) {
                        qtyFixedcheck.setChecked(false);
                    } else {
                        qtyFixedcheck.setChecked(true);
                    }
                    U.beepError(MoveStockActivity.this, null);
                    CommonDialogs.customToast(MoveStockActivity.this, "This action is not allowed after source locaion");
                }
            }
        });
    }

    public void setcheckbox()
    {
        String locfix =sharedPreferences.getString("LocationDigits","0");
        if(batchUse){
            if(locfix.equals("0")){
                locCheck.setChecked(false);
                locationEditLayout.setVisibility(View.GONE);
            }
            else {
                locCheck.setChecked(true);
                locationEditLayout.setVisibility(View.VISIBLE);
                _sts(id.locFixDigits, locfix);
                LocDelete();
            }
        }

        boolean qty = sharedPreferences.getBoolean("QuantityFix", false);
        if(batchUse){
            if(qty){
                qtyFixedcheck.setChecked(true);
                fixQty = true;
            }
            else {
                qtyFixedcheck.setChecked(false);
                fixQty = false;
            }}

    }

    public void AddLayout(View view)
    {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
        if(visible==false)
        {
            visible = true;

            numbrbtn.setText("キーボードを隠す");
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
        }
        else
        {
            visible = false;

            numbrbtn.setText("キーボードを表示");
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);

            mainlayout.setLayoutParams(params);
        }
    }
    public int convert(int x) {
        Resources r =mcontext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                x, r.getDisplayMetrics()
        );
        return px;
    }

    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "在庫移動", " ",
                0, true,true ,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
        btnRed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.relLayout1:
                menu.showMenu();
                break;

            case R.id.notif_count_red:
                if(getBadge2()!= 0){
                    showOrders();
                }

            default:
                break;
        }
    }

    public void BatchUse (View view){
        switch (batchRg.getCheckedRadioButtonId()) {
            case R.id.usebatch:
                batchUse = true;
                Log.e(TAG, "radio selected   "+batchUse);
                BaseActivity.setBatchSelected(true);
                locLayout.setVisibility(View.VISIBLE);
                qtyLayout.setVisibility(View.VISIBLE);
                setcheckbox();
                nextProcess();
                break;
            case R.id.notuse:
                batchUse = false;
                Log.e(TAG, "radio selected   "+batchUse);
                BaseActivity.setBatchSelected(false);
                locLayout.setVisibility(View.GONE);
                qtyLayout.setVisibility(View.GONE);
                fixQty = false;
                locbtnpress = false;
                locationBtn.setText("SAVE");
                setProc(PROC_SOURCE);
                _sts(id.locFixDigits,"");
                locationEditLayout.setVisibility(View.GONE);
                setcheckbox();
                nextProcess();
                break;
        }
    }

    @OnClick(R.id.locDelete) void LocDelete()
    {
        String loc = _gts(id.locFixDigits);
        if (loc.equals("") || loc.equals("0")) {
            U.beepError(this, "ロット番号が必要");
        }

        else {

            SharedPreferences.Editor editor = sharedPreferences.edit();

            if(locationBtn.getText().toString().equals("SAVE")){
                locbtnpress = true;
                hideKeyboard(this);
                locationBtn.setText("DELETE");
                locationBtn.setBackground(getResources().getDrawable(drawable.red_rounded_corner_button));
                editor.putString("LocationDigits",loc);
                setProc(PROC_SOURCE);
            }
            else if(locationBtn.getText().toString().equals("DELETE")){
                locbtnpress = false;
                locationBtn.setText("SAVE");
                locationBtn.setBackground(getResources().getDrawable(drawable.green_rounded_corner_btn));
                editor.putString("LocationDigits","0");
                _sts(id.locFixDigits,"");
            }
            editor.commit();
        }
    }

    public String getclassificationId() {
        return this.classificationId;
    }

    public void setclassificationId(String id) {
        this.classificationId= id;
    }

    public void setclassificationIdArray(ArrayList arr) {
        this.classificationIdArray = arr;

        if (classificationIdArray.size()>0){
            dataspinner(APISTOCKID);
        }

    }


    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {
            case PROC_LOC_FIX:
                Log.e(TAG, "SETPROCCCCC  PROC_LOC_FIX ");
                _gt(id.locFixDigits).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.source).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.destination).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                spinnerPartLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.locFixDigits).setFocusableInTouchMode(true);

                if (orderRequestSettings==true)
                    _gtxtv(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                scrollToView(svMain, _g(id.barcode));
                break;
            case PROC_BARCODE:
                Log.e(TAG, "SETPROCCCCC  PROC_BARCODE ");
                _gt(id.locFixDigits).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.source).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.destination).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                spinnerPartLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setFocusableInTouchMode(true);

                if (orderRequestSettings==true)
                    _gtxtv(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                scrollToView(svMain, _g(id.barcode));
                break;
            case PROC_PARTNO:
                Log.e(TAG, "PROCCCCCCCC PARTNO");
                _gt(id.locFixDigits).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.source).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.destination).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                spinnerPartLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));

                if (orderRequestSettings == true)
                    _gtxtv(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                break;
            case PROC_LOT:
                Log.e(TAG, "SETPROCCCCC  PROC_LOT ");
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.source).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.destination).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                spinnerPartLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

                _gtxtv(id.lotno).setFocusableInTouchMode(true);

                _gtxtv(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                scrollToView(svMain, _g(id.barcode));
                break;
            case PROC_QTY:
                Log.e(TAG, "SETPROCCCCC  PROC_QTY ");
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.source).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.destination).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                spinnerPartLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                scrollToView(svMain, _g(id.quantity));
                if (orderRequestSettings==true)
                    _gtxtv(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setFocusableInTouchMode(true);
                break;

            case PROC_SOURCE:
                Log.e(TAG, "SETPROCCCCC  PROC_SOURCE ");
                mBuff.delete(0, mBuff.length());
                _gt(id.locFixDigits).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.source).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.destination).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                if (orderRequestSettings==true)
                    _gtxtv(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.source).setFocusableInTouchMode(true);
                hideKeyboard(this);

                scrollToView(svMain, _g(id.source));
                break;

            case PROC_DESTINATION:
                Log.e(TAG, "SETPROCCCCC  PROC_DESTINATION ");
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.source).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.destination).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                if (orderRequestSettings==true)
                    _gtxtv(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.destination).setFocusableInTouchMode(true);
                _gt(id.destination).requestFocus();
                scrollToView(svMain, _g(id.destination));
                break;

            case PROC_CLASSIFICATION:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.source).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.destination).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                classification.performClick();

                if (orderRequestSettings==true)
                    _gtxtv(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.destination).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(id.destination));
                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        String lot="";
        switch (mProcNo) {
            case PROC_BARCODE:	// バーコード
                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());
                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode", _gts(id.barcode)));
                Globals.getterList.add(new ParamsGetter("location", _gts(id.source)));
                Globals.getterList.add(new ParamsGetter("lot_no", lot));
                Globals.getterList.add(new ParamsGetter("check_length", triplebarcode+""));

                mRequestStatus = REQ_BARCODE;

                new MainAsyncTask(this, Globals.Webservice.getProduct, 1, MoveStockActivity.this, "Form", Globals.getterList,true).execute();

                break;

            case PROC_LOT:
                lot =  lotno.getText().toString();
                if (lot.equals("") || lot.equals("0")) {
                    U.beepError(this, "ロット番号が必要");
                    break;
                }
                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
                Globals.getterList.add(new ParamsGetter("shop_id",BaseActivity.getShopId()));
                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode", _gts(id.barcode)));
                Globals.getterList.add(new ParamsGetter("lot_no", lot));
                Globals.getterList.add(new ParamsGetter("check_length", triplebarcode+""));

                mRequestStatus = REQ_LOT;

                new MainAsyncTask(this, Globals.Webservice.getProduct, 1, MoveStockActivity.this, "Form", Globals.getterList,true).execute();

                break;
            case PROC_QTY:
                String qty = _gts(id.quantity);
                String barcode = _gts(id.barcode);
                String maxqty = _gts(id.currentStock);
                if (isScaned) {
                    if (orderRequestSettings == true) {
                        String lotnoq =  lotno.getText().toString();
                        if (buff.equals(barcode) || buff.equals(lotnoq)) {
                            if (buff.equals(barcode) && !lotnoq.equals("")) { /*if barcode2 is not empty then store barcode1 and wait till second barcode*/
                                setBarcodeTemp(buff); /*store barcode1 in static variable*/
                                break;
                            }
                            if (buff.equals(lotnoq) && !barcode.equals("")) {
                                if (!(getBarcodeTemp().concat(buff)).equals((barcode.concat(lotnoq)))) {
                                    U.beepError(this, "Scanned barcode and lotno not matched");
                                    break;
                                }
                            }
                            if ((getBarcodeTemp().concat(buff)).equals((barcode.concat(lotnoq)))) {
                                setBarcodeTemp("");
                                U.beepSuccess();
                                String val = U.plusTo(_gts(id.quantity), "1");
                                _sts(id.quantity, val);
                                mTextToSpeak.startSpeaking(val);
                                if (maxqty.equals(_gts(id.quantity))) {
                                    if(batchUse == true) {
                                            if ( fixQty == true && !_gts(id.quantity).equalsIgnoreCase("1")){
                                                U.beepError(this, "数量は必須です");
                                                _gt(id.quantity).setFocusableInTouchMode(true);
                                                break;
                                            }

                                        nextdata();
                                        setProc(PROC_BARCODE);
                                    }
                                    else
                                        setProc(PROC_DESTINATION);
                                }
                                break;
                            }
                            else {
                                U.beepError(this, "Scanned barcode and lotno not matched");
                                break;
                            }
                        }
                    }
                    else {
                        if (buff.equals(barcode)) {
                            U.beepSuccess();
                            String val = U.plusTo(_gts(id.quantity), "1");
                            _sts(id.quantity, val);
                            mTextToSpeak.startSpeaking(val);
                            if (maxqty.equals(_gts(id.quantity))) {

                                if(batchUse == true) {
                                    if ( fixQty == true && !_gts(id.quantity).equalsIgnoreCase("1")){
                                        U.beepError(this, "数量は必須です");
                                        _gt(id.quantity).setFocusableInTouchMode(true);
                                        break;
                                    }
                                    nextdata();
                                    setProc(PROC_BARCODE);
                                }
                                else
                                    setProc(PROC_DESTINATION);
                            }
                            break;
                        }

                        else {
                            U.beepError(this, "1度に移動出来る商品は1商品のみです");
                            _gt(id.quantity).setFocusableInTouchMode(true);
                            break;
                        }}
                }
                if ("".equals(qty)) {
                    U.beepError(this, "数量は必須です");
                    _gt(id.quantity).setFocusableInTouchMode(true);
                    break;
                } else if (!U.isNumber(qty)) {
                    U.beepError(this, "数量は数値のみ入力可能です");
                    _gt(id.quantity).setFocusableInTouchMode(true);
                    break;
                }
                else if(Integer.parseInt(qty)<=0){
                    U.beepError(this, "数量は数値のみ入力可能です");
                    _gt(id.quantity).setFocusableInTouchMode(true);
                    break;
                }
                else if (U.compareNumeric(maxqty, qty) > 0) {
                    U.beepError(this, "数量が多すぎます");
                    _gt(id.quantity).setFocusableInTouchMode(true);
                    break;
                }else {
                    U.beepSuccess();
                    if(batchUse == true) {
                        if ( fixQty == true && !_gts(id.quantity).equalsIgnoreCase("1")){
                            U.beepError(this, "数量は必須です");
                            _gt(id.quantity).setFocusableInTouchMode(true);
                            break;
                        }
                        nextdata();
                        setProc(PROC_BARCODE);
                    }
                    else
                        setProc(PROC_DESTINATION);
                }
                break;
            case PROC_SOURCE:
                String source = _gts(id.source);
                if ("".equals(source)) {
                    U.beepError(this, "移動元ロケーションは必須です");
                    _gt(id.source).setFocusableInTouchMode(true);

                    break;
                } else {
                    Globals.getterList = new ArrayList<>();

                    Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
                    Globals.getterList.add(new ParamsGetter("shop_id",BaseActivity.getShopId()));
                    Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                    Globals.getterList.add(new ParamsGetter("location", source));
                    mRequestStatus = REQ_SOURCE;

                    new MainAsyncTask(this, Globals.Webservice.getLocationTally, 1, MoveStockActivity.this, "Form", Globals.getterList,true).execute();
                }
                break;
            case PROC_DESTINATION:
                String destination = _gts(id.destination);
                String lotnoq = "";
                String locfix = "";
                int loclength = 0;
                if (locbtnpress) {
                    locfix = _gts(id.locFixDigits);
                    loclength = Integer.parseInt(locfix);
                }

                if (orderRequestSettings==true)
                    lotnoq= lotno.getText().toString();

                if ("".equals(destination)) {
                    U.beepError(this, "移動先ロケーションは必須です");
                    _gt(id.destination).setFocusableInTouchMode(true);
                    break;
                }
                else if(locbtnpress && destination.length()!= loclength){
                    U.beepError(this, "移動先ロケーションは必須です");
                    _gt(id.destination).setFocusableInTouchMode(true);
                    break;
                }
                else {
                    stopTimer();
                    Globals.getterList = new ArrayList<>();

                    Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                    Globals.getterList.add(new ParamsGetter("source", _gts(id.source)));
                    Globals.getterList.add(new ParamsGetter("destination", _gts(id.destination)));
                    Globals.getterList.add(new ParamsGetter("stock_type_id", getclassificationId()));

                    if(batchUse){
                        StringBuffer bar =new StringBuffer();
                        StringBuffer id = new StringBuffer();
                        StringBuffer qnty = new StringBuffer();
                        StringBuffer lotno1 = new StringBuffer();
                        StringBuffer productid = new StringBuffer();
                        for (Map<String, String> map : mBatchList){
                            if (!map.get("quantity").equals("0")) {
                                bar.append("\t").append(map.get("code"));
                                qnty.append("\t").append(map.get("quantity"));
                                lotno1.append("\t").append(map.get("lotno"));
                                id.append("\t").append(map.get("id"));
                                productid.append("\t").append(map.get("product_id"));
                            }
                        }

                        Globals.getterList.add(new ParamsGetter("barcode",bar.substring(1)));
                        Globals.getterList.add(new ParamsGetter("qty", qnty.substring(1)));
                        Globals.getterList.add(new ParamsGetter("lot_no", lotno1.substring(1)));
                        Globals.getterList.add(new ParamsGetter("product_id", productid.substring(1)));

                        Globals.getterList.add(new ParamsGetter("mode", "bulk_move"));
                        Globals.getterList.add(new ParamsGetter("product_stock_id", id.substring(1)));
                    }
                    else {
                        if(!multicode)
                            Globals.getterList.add(new ParamsGetter("barcode", _gts(id.barcode)));
                        else
                            Globals.getterList.add(new ParamsGetter("barcode", _gts(id.product_code)));

                        Globals.getterList.add(new ParamsGetter("qty", _gts(id.quantity)));
                        Globals.getterList.add(new ParamsGetter("lot_no", lotselected));
                        Globals.getterList.add(new ParamsGetter("product_stock_id", pshID));
                        Globals.getterList.add(new ParamsGetter("product_id", productID));
                    }
                    Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
                    Globals.getterList.add(new ParamsGetter("check_length", triplebarcode+""));



                    mRequestStatus = REQ_DESTINATION;
//                  new MainAsyncTask(this, Globals.Webservice.movestock, 1, MoveStockActivity.this, "Form", Globals.getterList,true).execute();
                    new MainAsyncTask(this, Globals.Webservice.productMoveStock, 1, MoveStockActivity.this, "Form", Globals.getterList,true).execute();
                }
                break;
        }
    }


    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    public void setProductList(List<Map<String, String>> data) {
        mProductList = data;
    }

  /*  public ListViewItems initWorkList() {
        listview.setAdapter(null);

        ListViewItems data = new ListViewItems();
        for (int i = 0; i <=mProductList.size() - 1; i++)
        {
            Map<String, String> row = mProductList.get(i);

            data.add(data.newItem().add(id.txt1, row.get("stock_type_label"))
                    .add(R.id.txt2, row.get("lot"))
                    .add(R.id.txt3, row.get("expiration_date"))
                    .add(R.id.txt4, row.get("quantity"))
            );
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.move_stock_list_row);

        listview.setAdapter(adapter);
        // 単一選択モードにする
        listview.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "KJHGFDNBVCHGFD  position  "+position);
                Map<String,String> map= new HashMap<>();
                map = mProductList.get(0);
                Log.e(TAG, "KJHGFDNBVCHGFD  mapp "+map);
                lotselected = map.get("lot");
                expiration = map.get("expiration_date");
                pshID = map.get("id");
                productID = map.get("product_id");
                Log.e(TAG, "KJHGFDNBVCHGFD  mapp "+productID);
               _sts(R.id.lotno,lotselected);

                for(int i = 0; i<classificationIdArray.size();i++){
                    String stock = map.get("stock_type_id");
                    Log.e(TAG, "KJHGFDNBVCHGFD  stock "+stock);
                    if(classificationIdArray.get(i).equalsIgnoreCase(stock)){
                        classification.setSelection(i);
                        break;
                    }
                }
                Log.e(TAG, "KJHGFDNBVCHGFD  classification "+classification);
                _sts(R.id.currentStock, map.get("quantity"));

                Log.e(TAG, "KJHGFDNBVCHGFD  quantity "+map.get("quantity"));
            }
        });
        // デフォルト値をセットする
        if (data.getData().size() > 0) {
            Log.e(TAG, "KJHGFDNBVCHGFD  ");
//            setSelection(0);
            listview.setItemChecked(0,true);
        }
     return data;
    }
*/


    public ListViewItems initWorkList() {
        listview.setAdapter(null);

        ListViewItems data = new ListViewItems();
        for (int i = 0; i <=mProductList.size() - 1; i++) {
            Map<String, String> row = mProductList.get(i);

            data.add(data.newItem().add(id.txt1, row.get("stock_type_label"))
                    .add(R.id.txt2, row.get("lot"))
                    .add(R.id.txt3, row.get("expiration_date"))
                    .add(R.id.txt4, row.get("quantity"))
            );
        }
        adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.move_stock_list_row) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == mSelectedItem) {
                    Log.e(TAG, "selecteddddd positionnn    " + position);
                    v.setBackgroundColor(Color.YELLOW);

                } else {
                    if (position % 2 == 1) {
                        Log.e(TAG, "Odd    positionnn");
                        v.setBackgroundColor(Color.WHITE);
                    } else {
                         v.setBackgroundColor(Color.WHITE);
                    }
                }

                return v;
            }
        };


        listview.setAdapter(adapter);
         listview.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
         listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "JHGFDSMNBFD   pos "+position);
                 mSelectedItem = position;
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Map<String,String> map= new HashMap<>();
                map = mProductList.get(position);
                lotselected = map.get("lot");
                 expiration = map.get("expiration_date");
                pshID = map.get("id");
                productID = map.get("product_id");
                Log.e(TAG, "KJHGFDNBVCHGFD  mapp "+productID);
                EXPRDAATE =expiration;
                lotno.setText(lotselected);
                expirationdate.setText(EXPRDAATE);
 //                classification.setSelected();

                for(int i = 0; i<classificationIdArray.size();i++){
                  String    stock = map.get("stock_type_id");
                    if(classificationIdArray.get(i).equalsIgnoreCase(stock)){
                        classification.setSelection(i);
                        break;
                    }
                }
                Log.e(TAG, "JHGFDSMNBFD   "+map.get("quantity"));
                _sts(R.id.currentStock, map.get("quantity"));

            }
        });


        // デフォルト値をセットする
        if (data.getData().size() > 0)
            setSelection(0);
        return data;
    }

    public void setSelection(int pos){
        Map<String,String> map= new HashMap<>();
        map = mProductList.get(0);
        lotselected = map.get("lot");

        expiration = map.get("expiration_date");
        pshID = map.get("id");
        productID = map.get("product_id");
        lotno.setText(lotselected);
         EXPRDAATE = expiration ;
         expirationdate.setText(EXPRDAATE);
         APISTOCKID = map.get("stock_type_id");
/*
        for(int i = 0; i<classificationIdArray.size();i++){
          String  stock = map.get("stock_type_id");
            if(classificationIdArray.get(i).equalsIgnoreCase(stock)){
                classification.setSelection(i);
                break;
            }
        }*/
        _sts(R.id.currentStock, map.get("quantity"));


    }
    public void getJsonData(ArrayList<HashMap<String, String>> data, boolean multi, List<String> part) {
        jsonlist = data;
        multicode = multi;
        PartnoArray = (ArrayList<String>) part;
    }

    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
    }

    public void nextdata()
    {
        HashMap<String,String> map =new HashMap<String,String>();
        map.put("barcode",_gts(R.id.barcode));
        map.put("code",_gts(R.id.product_code));
        map.put("quantity",_gts(R.id.quantity));
        map.put("stock",getclassificationId());
        map.put("id",pshID);
        map.put("product_id",productID);

        map.put("lotno",lotselected);
        map.put("expiration", expiration);


        /*

        if(orderRequestSettings){
            map.put("lotno",_gts(R.id.lotno));
        }
        else
        {
            map.put("lotno","");
        }
*/

        mBatchList.add(map);
        _sts(R.id.barcode,"");
        _sts(R.id.quantity,"");
    classification.setSelection(0);

        setBadge2(mBatchList.size());

        lotselected = "";
        expiration = "";
        pshID = "";
        productID = "";
        EXPRDAATE="";

    }
    public void setAttributeProc(String code)
    {
        String stndrd_two = null;
        String stndrd_one = null;
        String currentstock = null;

        List<Map<String,String>> list = new ArrayList<>();

        for(Map<String,String> map1: jsonlist)
        {
            String c = map1.get("code");

            if (c.equals(code))
            {
                list.add(map1);

                stndrd_one = map1.get("spec1");
                stndrd_two = map1.get("spec2");
                currentstock = map1.get("quantity");

                if (mBatchList.size() > 0) {
                    currentstock = findRepeat( code, currentstock);
                }
            }
        }
        if (Integer.parseInt(currentstock) > 0)
        {
            _stxtv(R.id.standard1, stndrd_one);
            spec1.setSelected(true);
            _stxtv(R.id.standard2, stndrd_two);
            spec2.setSelected(true);
            _sts(R.id.currentStock, currentstock);

            nextWork();
        }
        else {
            U.beepError(this, "No stocks left");
            setProc(MoveStockActivity.PROC_BARCODE);
        }
    }

    public String findRepeat( String code, String qty)
    {
        String temp = qty;
        for (Map<String, String> map : mBatchList){
            Log.e(TAG, "fineRepeat");
            String _c = map.get("code");
            String _q = map.get("quantity");
            if(_c.equals(code)){
                temp = U.minusTo(temp,_q);
            }
        }
        return temp;
    }


    protected boolean showOrders()
    {
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

        Button close =(Button)getPopupWindow().getContentView().findViewById(R.id.packing_close_btn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        Button addlocation =(Button)getPopupWindow().getContentView().findViewById(R.id.btn_add_location);
        addlocation.setText("確定");
        addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBatchList.size()<1){
                    U.beepError(MoveStockActivity.this,"Empty batch");
                }
                else {
                    if (getShopId().equals("1671") || getShopId().equals("1760") || getShopId().equals("1775")) {
                        setProc(PROC_DESTINATION);
                    } else
                        setProc(PROC_CLASSIFICATION);
                    popupWindow.dismiss();
                } }
        });

        ListView lv = (ListView) getPopupWindow().getContentView().findViewById(R.id.lvPackingList);
        initWorkList(lv);

        // 画面中央に表示
        getPopupWindow().showAtLocation(findViewById(R.id.barcode), Gravity.CENTER, 0, 32);
        return false;
    }

    protected ListViewItems initWorkList(final ListView lv)
    {
        lv.setAdapter(null);

        ListViewItems data = new ListViewItems();
        for (int i = 0; i <=mBatchList.size() - 1; i++) {
            Map<String, String> row = mBatchList.get(i);

            if(!multicode)
                data.add(data.newItem().add(R.id.btc_ins_0,i+1+"")
                        .add(R.id.btc_ins_1,row.get("barcode"))
                        .add(R.id.btc_ins_2, row.get("quantity"))
                );
            else data.add(data.newItem().add(R.id.btc_ins_0,i+1+"")
                    .add(R.id.btc_ins_1,row.get("code"))
                    .add(R.id.btc_ins_2, row.get("quantity"))
            );

        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.batch_arrival_list){

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Log.e(TAG,"Batchleftttttttttt1111  "+position);

                ImageView img = (ImageView) v.findViewById(R.id.delete_batch);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBatchList.remove(position);
                        setBadge2(mBatchList.size());
                        initWorkList(lv);
                    }
                });
                return v;
            }
        };

        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0){
            lv.setItemChecked(0, true);

        }
        return data;
    }


    @Override
    public void allclearEvent() {

    }

    @Override
    public void skipEvent() {

    }

    public void nextWork() {
        initWorkList();

        _sts(id.quantity,"1");

        Log.e(TAG, "batchUse......  "+batchUse  +"    "+fixQty);

        lastupdatedQty = U.plusTo(lastupdatedQty,"1");
        if(batchUse == true && fixQty == true)
            mTextToSpeak.startSpeaking(lastupdatedQty);
        else
            mTextToSpeak.startSpeaking("1");

        setProc(PROC_QTY);



        if (batchUse == true && fixQty == true )
        {

            String currentStock = findRepeat(mProductList.get(0).get("code"), mProductList.get(0).get("quantity"));
            Log.e(TAG, "current stock......  "+currentStock);
            if(Integer.parseInt(currentStock)== 0 ){
                U.beepError(this,"No current stocks");
                setProc(PROC_BARCODE);
            }
            else{
            listview.setItemChecked(0,true);
            nextdata();
            setProc(PROC_BARCODE);
        }}
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_BARCODE:	// バーコード
                _sts(id.barcode, buff);
                break;
            case PROC_LOT:	// バーコード
                _stxtv(id.lotno, buff);
                break;
            case PROC_QTY: // 数量
                _sts(id.quantity, buff);
                break;
            case PROC_SOURCE:	// 移動元
                _sts(id.source, buff);
                break;
            case PROC_DESTINATION:	// 移動先
                _sts(id.destination, buff);
                break;
            case PROC_LOC_FIX:	// 移動先
                _sts(id.locFixDigits, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode)
    {
        if(MoveStock2.dialogdismissed1 ==true)
        {
            MoveStock2.dialog1.dismiss();
            MoveStock2.dialogdismissed1= false;
        }
        if(!MainAsyncTask.dialogBox.isShowing())
        {
            if (!barcode.equals("")) {
                if (mProcNo == PROC_BARCODE){
                    String finalbarcode1 = CommonFunctions.getBracode(barcode);
                    barcode =finalbarcode1;

                    _sts(id.barcode, barcode);}

                if (mProcNo == PROC_QTY){
                    String finalbarcode1 = CommonFunctions.getBracode(barcode);
                    barcode =finalbarcode1;
                }

                else if (mProcNo == PROC_LOT) _stxtv(id.lotno, barcode);
                else if (mProcNo == PROC_SOURCE) _sts(id.source, barcode);
                else if (mProcNo == PROC_DESTINATION) _sts(id.destination, barcode);}
            this.inputedEvent(barcode, true);
        }
        else
            Toast.makeText(getApplicationContext(),"Please Wait",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:	// バーコード
                _sts(id.barcode, barcode);
                break;
            case PROC_LOT:	// バーコード
                _stxtv(id.lotno, barcode);
                break;
            case PROC_QTY:	// ロケ選択
                _sts(id.quantity, barcode);
                break;
            case PROC_SOURCE: // 数量
                _sts(id.source, barcode);
                break;
            case PROC_DESTINATION: // 数量
                _sts(id.destination, barcode);
                break;
            case PROC_LOC_FIX:
                _sts(id.locFixDigits, barcode);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();
    }

    @Override
    public void nextProcess() {
        this._sts(id.barcode, "");
        this._sts(id.barcode, "");
        this._sts(id.quantity, "");
        this._sts(id.source, "");
        this._sts(id.destination, "");
        this._sts(id.currentStock,"");
        this._sts(id.product_code,"");
        this._sts(id.expirationdate,"");
        this._stxtv(id.standard1,"");
        this._stxtv(id.standard2,"");
        this._stxtv(id.lotno,"");
        this.setProc(PROC_SOURCE);
        mSelectedItem= 0;
        if (!batchUse){
            locLayout.setVisibility(View.GONE);
            qtyLayout.setVisibility(View.GONE);
        }

        classificationIdArray.clear();
        mBatchList.clear();
        setBadge2(0);

        listview.setAdapter(null);
        lastupdatedQty ="0";

        lotselected = "";
        expiration = "";
        pshID = "";
        productID = "";

        partnoLayout.setVisibility(View.GONE);
        partNospinner.setAdapter(null);
        multicode = false;

        classification.setAdapter(null);
        if(orderRequestSettings==true)
            this._stxtv(id.lotno,"");
        if(showKeyboard==false) {
            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);

            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);

            hiddenPanel.setVisibility(View.INVISIBLE);

            mainlayout.setLayoutParams(params);
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
            String code = map1.getStringOrNull("code");
            msg=map1.getStringOrNull("message");
            result1= map1.getJsonArrayOrNull("results");
            if (code == null) {
                Log.e(TAG,"CODE=====Null");
                CommonDialogs.customToast(getApplicationContext(),"Network error occured");
            }
            if ("0".equals(code) == true) {

                if(mRequestStatus == REQ_BARCODE)
                {
                    new GetProductStock().post(code,msg,result1, mHash,MoveStockActivity.this);
                }
                else if(mRequestStatus == REQ_LOT)
                {
                    new GetProductLotno().post(code,msg,result1, mHash,MoveStockActivity.this);
                }
                else if(mRequestStatus == REQ_SOURCE)
                {
                    new GetLocationStock().post(code,msg,result1,mHash,MoveStockActivity.this);
                }
                else if(mRequestStatus == REQ_DESTINATION)
                {
                    if(batchUse){
                        U.beepKakutei(this, "設定を登録しました");
                        toast("在庫を移動しました。");
                        nextProcess();
                    }
                    else
                        new MoveStock2().post(code,msg,result1,mHash,MoveStockActivity.this);
                }
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(MoveStockActivity.this,LoginActivity.class);
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
                if(mRequestStatus == REQ_BARCODE)
                {
                    new GetProductStock().valid(code,msg,result1, mHash,MoveStockActivity.this);
                }
                else if(mRequestStatus == REQ_LOT)
                {
                    new GetProductLotno().valid(code,msg,result1, mHash,MoveStockActivity.this);
                }
                else if(mRequestStatus == REQ_SOURCE)
                {
                    new GetLocationStock().valid(code,msg,result1,mHash,MoveStockActivity.this);
                }
                else if(mRequestStatus == REQ_DESTINATION)
                {
                    new MoveStock2().valid(code,msg,result1,mHash,MoveStockActivity.this);
                }
            }
        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {
        if(count>50){
            Log.e(TAG,">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   counttt1111 "+ count);
            setDialog();
            count = 0;
        }
        else // Repeate the request 10 times if not successful
        {
            count++;
            Log.e(TAG,">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   counttt "+ count);
            if(mRequestStatus == REQ_BARCODE)
            {
                new MainAsyncTask(this, Globals.Webservice.getProduct, 1, MoveStockActivity.this, "Form", Globals.getterList,true).execute();
            }
            else if(mRequestStatus == REQ_LOT)
            {
                new MainAsyncTask(this, Globals.Webservice.getProduct, 1, MoveStockActivity.this, "Form", Globals.getterList,true).execute();
            }
            else if(mRequestStatus == REQ_SOURCE)
            {
                new MainAsyncTask(this, Globals.Webservice.getLocationTally, 1, MoveStockActivity.this, "Form", Globals.getterList,true).execute();
            }
            else if(mRequestStatus == REQ_DESTINATION)
            {
                new MainAsyncTask(this, Globals.Webservice.movestock, 1, MoveStockActivity.this, "Form", Globals.getterList,true).execute();

            }
        }
    }


    void setDialog(){

        final SweetAlertDialog pDialog_logout = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        pDialog_logout.setCancelable(true);
        pDialog_logout.setTitleText("No network connection");
        pDialog_logout.setContentText("Please connect");
        pDialog_logout.setConfirmText("Cancel");
        pDialog_logout.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                pDialog_logout.dismissWithAnimation();
            }});
        pDialog_logout.show();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    public int ClasificationPostion(String val){
        for(int i=0;i<=classificationIdArray.size();i++){

            if (classificationIdArray.get(i).equals(val)){
                return  i;
            }

        }
        return 0;
    }

    public void  dataspinner(String clasification){
        int pos =0;
        if (clasification.equals(" ") ){

            // spinner.setSelection(pos);
        } else{
            pos = ClasificationPostion(clasification);
        }
        GetProductStock.spinner.setSelection(pos);
    }
}
