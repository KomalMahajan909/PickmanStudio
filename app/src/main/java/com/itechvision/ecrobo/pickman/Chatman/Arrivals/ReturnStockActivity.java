package com.itechvision.ecrobo.pickman.Chatman.Arrivals;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Adapter.Adap_RetunStokeLot;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetReturnOrder;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetReturnOrderBarcode;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetReturnTrack;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.ReturnStock.ReturnStokeLotExpData;
import com.itechvision.ecrobo.pickman.Models.ReturnStock.ReturnStokeLotExpReq;
import com.itechvision.ecrobo.pickman.Models.ReturnStock.ReturnStokeLotExpResult;
import com.itechvision.ecrobo.pickman.Models.ReturnStock.SubmitReturnStoke.SubmitReturnStokeReq;
import com.itechvision.ecrobo.pickman.Models.ReturnStock.SubmitReturnStoke.SubmitReturnStokeResult;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
//Panelled Mid-Top Sneakers

public class ReturnStockActivity extends BaseActivity implements View.OnClickListener, MainAsynListener , DataManager.GETLOTEXPcall,DataManager.SubmitRetunStokecall {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_number) ViewGroup layout;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.orderReturn) LinearLayout barcodelayout;
    @BindView(R.id.reason_return_spinner) Spinner returnOptionChoose;
    @BindView(R.id.shipping_company_spinner) Spinner returnShippingCompany;
    @BindView(R.id.optionchoose) RadioGroup classification;
    @BindView(R.id.scrollMain) ScrollView svMain;
    @BindView(R.id.gridPrice) LinearLayout pricegrid;
    @BindView(R.id.returning_type_rg) RadioGroup returningType;
    @BindView(R.id.returnlayout)RelativeLayout retrunlayout;
    @BindView(R.id.companylayout)RelativeLayout companylayout;
    @BindView(R.id.orderIdlayout) LinearLayout orderIDLayout;
    @BindView(R.id.trackingNoCheck) CheckBox trackingNoCheck;
    @BindView(R.id.mainlot) LinearLayout mainlot;
    @BindView(R.id.mainexp) LinearLayout mainexp;
    @BindView(R.id.list) ListView list;
    @BindView(R.id.listlayout) LinearLayout listlayout;

    public EditText lotno;
    public EditText expidate;
    public TextToSpeak mTextToSpeak;
    String TAG= ReturnStockActivity.class.getSimpleName();
    public RadioButton orderrb;
    ECRApplication app=new ECRApplication();
    String adminID="";
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public Context mcontext=this;
    public CheckBox returningnum;
    private boolean visible = false ,barcodevisible = false;
    private boolean showKeyboard;
    public int showoption =0;
    public String _lastUpdateQty = "0";
    protected Map<String, String> cProductList = null;
    protected List<Map<String, String>> mReturnList =new ArrayList<>();
    String optionSelected = "",companySelected = "";
    protected int mProcNo = 0;
    public static final int PROC_RETURN_NO = 1;
    public static final int PROC_PRICE = 2;
    public static final int PROC_ORDER_ID = 3;
    public static final int PROC_TRACK_NO = 8;
    public static final int PROC_RETURN_REASON = 4;
    public static final int PROC_SHIPPING_COMPANY = 5;
    public static final int PROC_BARCODE = 6;
    public static final int PROC_QTY = 7;
    public static int mRequestStatus =0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_TRACK_NO = 2;
    public static final int REQ_ORDERID = 3;
    public static final int REQ_BARCODE = 4;
    public static final int FIXED_REQ = 5;
    public static final int REQ_ORDERDETAIL = 6;
    public static final int REQ_ALLORDERS_DETAIL = 7;
    int returnreason = 0,shipcompany =0;
    public String orderID= "";
    CharSequence[] values = {" 良品 "," B品 "," 破損 "};
    String ship_companyArray[] = {"配送会社を選択","ヤマト宅急便","佐川急便","ゆうパック","レターパック","郵便","メール便","ゆうパケット","ネコポス","ゆうメール","その他"};
    String returnoptiom [] = {"返品理由を選択","長期不在","受取拒否","住所不明","配達中止","その他","引取依頼","サイズが合わない","イメージ違い","理由不明","色味相違" , "サイズオーバー","誤発送","商品不良","転居先不明", "素材相違","重量が合わない"};
    AlertDialog alertDialog1;
    String selectedclassification = "";
    String getSelectedclassificationtxt = "";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    DataManager.GETLOTEXPcall getlotexp ;
    DataManager manager ;
    ListViewAdapter adapter;
    ArrayList<ReturnStokeLotExpData> arr ;
    int mSelectedItem = 0,eop=0;
    progresBar progress ;
    DataManager.SubmitRetunStokecall submitreturn;

    SweetAlertDialog dialog;
    //error on getlot
    public boolean loterror = false;

    boolean lotselected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_stock);

        ButterKnife.bind(ReturnStockActivity.this);
        getlotexp = this ;
        submitreturn = this ;

        manager = new DataManager();
        progress = new progresBar(this);
        lotno = (EditText)findViewById(R.id.lotno);
        expidate = (EditText)findViewById(R.id.expidate);
        getIDs();

        Log.d(TAG,"On Create ");
        arr  = new ArrayList<>();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        orderrb  = (RadioButton)findViewById(R.id.orderRb);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID  = spDomain.getString("admin_id",null);

        dialog = new SweetAlertDialog(this);
        lotselected= BaseActivity.getLotPress();
        if(lotselected){
            mainexp.setVisibility(View.VISIBLE);
            mainlot.setVisibility(View.VISIBLE);
            listlayout.setVisibility(View.VISIBLE);
        }
        else {
            mainexp.setVisibility(View.GONE);
            mainlot.setVisibility(View.GONE);
            listlayout.setVisibility(View.GONE);
        }


        mTextToSpeak = new TextToSpeak(this);
        showKeyboard= BaseActivity.getaddKeyboard();
        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
            visible = true;

//			numbrbtn.setText("キーボードを隠す");

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e(TAG,"SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        ArrayAdapter adapter1 = new ArrayAdapter (this, android.R.layout.simple_spinner_item,ship_companyArray){
            @Override
            public boolean isEnabled(int position){
                if(position == 0) {
                    // Disable the second item from Spinner
                    return false;
                } else {
                    return true;
                }
            }};

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        returnShippingCompany.setEnabled(false);
        returnShippingCompany.setClickable(false);
        // Apply the adapter to the spinner
        returnShippingCompany.setAdapter(adapter1);
        returnShippingCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "positionnnnnn    " +position);
                switch (ship_companyArray [position]){
                    case "ヤマト宅急便":
                        shipcompany = 1;
                        break;
                    case "佐川急便":
                        shipcompany = 0;
                        break;
                    case "ゆうパック":
                        shipcompany = 2;
                        break;
                    case "レターパック":
                        shipcompany = 104;
                        break;
                    case "郵便":
                        shipcompany = 101;
                        break;
                    case "メール便":
                        shipcompany = 3;
                        break;
                    case "ゆうパケット":
                        shipcompany = 105;
                        break;
                    case "ネコポス":
                        shipcompany = 205;
                        break;
                    case "ゆうメール":
                        shipcompany = 103;
                        break;
                    case "その他":
                        shipcompany = 404;
                        break;
                }

                setshippingcompanyID(shipcompany+"");

                if(position > 0) {
                    Log.e(TAG,"ShippCompany ID 1111 "+shipcompany);
                    if (showoption == 1) {
                        showdialog();
                    } else{
                        setProc(PROC_BARCODE);}
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter adapter = new ArrayAdapter (this, android.R.layout.simple_spinner_item,returnoptiom){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }};

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        returnOptionChoose.setAdapter(adapter);
        returnOptionChoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "Position" + position);

                switch (returnoptiom [position]){
                    case "受取拒否":
                        returnreason = 1;
                        break;
                    case "引取依頼":
                        returnreason = 2;
                        break;
                    case "誤発送":
                        returnreason = 3;
                        break;
                    case "商品不良":
                        returnreason = 4;
                        break;
                    case "配達中止":
                        returnreason = 5;
                        break;
                    case "サイズが合わない":
                        returnreason = 6;
                        break;
                    case "イメージ違い":
                        returnreason = 7;
                        break;
                    case "長期不在":
                        returnreason = 8;
                        break;
                    case "住所不明":
                        returnreason = 9;
                        break;
                    case "理由不明":
                        returnreason = 10;
                        break;
                    case "色味相違":
                        returnreason = 11;
                        break;
                    case "素材相違":
                        returnreason = 12;
                        break;
                    case "その他":
                        returnreason = 13;
                        break;
                    case "サイズオーバー":
                        returnreason = 14;
                        break;
                    case "重量が合わない":
                        returnreason = 15;
                        break;
                    case "転居先不明":
                        returnreason = 16;
                        break;

                }
                setoptionId(returnreason+"");
                Log.e(TAG,"Returnnn Company ID  "+returnreason);
                if(position>0) {
                    Log.e(TAG,"Returnnn Company ID1111  "+returnreason);

                    if (returnreason ==0 ){

                    }else{
                        returnShippingCompany.setEnabled(true);
                        returnShippingCompany.setClickable(true);
                        setProc(PROC_SHIPPING_COMPANY);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Log.e("Login Activity ","Domain  "+ BaseActivity.getdomainpos());


        returningnum =(CheckBox) _g(R.id.returning_number_checkbox);
        returningnum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(mProcNo == PROC_PRICE && _gts(R.id.orderId).equals("")){
                        setProc(PROC_PRICE);
                        pricegrid.setVisibility(View.VISIBLE);}
                    else if(mProcNo < PROC_PRICE && _gts(R.id.orderId).equals("")){
                        setProc(PROC_RETURN_NO);
                        pricegrid.setVisibility(View.VISIBLE);}
                    else if(mProcNo == PROC_ORDER_ID && _gts(R.id.orderId).equals("")){
                        setProc(PROC_PRICE);
                        pricegrid.setVisibility(View.VISIBLE);}

                    else
                        pricegrid.setVisibility(View.GONE);
                } else
                    pricegrid.setVisibility(View.GONE);
            }});

        classification.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (classification.getCheckedRadioButtonId()){
                    case R.id.good:
                        selectedclassification = "1";
                        getSelectedclassificationtxt ="良品";
                        break;
                    case R.id.used:
                        selectedclassification = "2";
                        getSelectedclassificationtxt ="B品";
                        break;
                    case R.id.damaged:
                        selectedclassification = "3";
                        getSelectedclassificationtxt ="破損";
                        break;
                    default:
                        selectedclassification = "1";
                        getSelectedclassificationtxt ="良品";
                        break;
                }
            }
        });



        if(mProcNo == 0)
            nextProcess();



    }

    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "返品登録", " ", 0, true,false,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        btnBlue.setOnClickListener(this);
        relLayout1.setOnClickListener(this);

//      imgRight.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
            case R.id.notif_count_blue:
                if(getBadge1()>0)
                    showInfo();

                break;

            default:
                break;
        }
    }
    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
        if(visible==false) {

            visible = true;
            numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e(TAG,"SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG,"SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
    }

    public void ReturnType(View view){

        switch (returningType.getCheckedRadioButtonId()) {
            case R.id.orderRb:
                showoption = 1;
                barcodelayout.setVisibility(View.GONE);
                break;
            case R.id.productRb:
                showoption =2;
                barcodelayout.setVisibility(View.VISIBLE);
                barcodevisible =true;
        }
        if(!_gts(R.id.orderId).equals("") ){
            if (showoption == 2){
                U.beepNext();
                setProc(PROC_RETURN_REASON);
            }

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


    public void setoptionId(String id){
        this.optionSelected= id;
    }
    public String getoptionId(){
        return this.optionSelected;
    }

    public void setshippingcompanyID(String id){
        this.companySelected= id;
    }
    public String getshippingcompanyID(){
        return this.companySelected;
    }

    public void setProc(int procNo) {
        Log.e(TAG,"setProcccccc");
        mProcNo = procNo;
        switch (procNo) {
            case PROC_RETURN_NO:
                Log.e(TAG, "setProc_PROC_ReturnNO");
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.price).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                retrunlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                companylayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setFocusableInTouchMode(true);
                returnOptionChoose.setEnabled(false);
                returnShippingCompany.setEnabled(false);
                scrollToView(svMain, _g(R.id.returning_number));
                break;
            case PROC_PRICE:
                Log.e(TAG, "setProc_PROC_PRICE");
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.price).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                retrunlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                companylayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));

                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.price).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.price));
                break;
            case PROC_ORDER_ID:
                Log.e(TAG, "setProc_PROC_ORDERID");
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.price).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                retrunlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                companylayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));

                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.orderId).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.orderId));
                break;
            case PROC_TRACK_NO:
                Log.e(TAG, "setProc_PROC_TRACKNO");
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.price).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                retrunlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                companylayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));

                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.trackingNumber).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.trackingNumber));
                break;
            case PROC_BARCODE:
                Log.e(TAG, "setProc_PROC_BARCODE");

                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.price).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                retrunlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                companylayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.barcode).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.barcode));


                break;


            case PROC_QTY:
                Log.e(TAG, "setProc_PROC_QTY");

                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.price).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                retrunlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                companylayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));

                _gt(R.id.quantity).setFocusableInTouchMode(true);

                if (_gts(R.id.quantity).equals("")) _sts(R.id.quantity, "1");
                scrollToView(svMain, _g(R.id.quantity));
                break;
            case PROC_RETURN_REASON:
                Log.e(TAG, "setProc_PROC_RETURN_REASON");

                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.price).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                companylayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                retrunlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                retrunlayout.setFocusableInTouchMode(true);
                returnOptionChoose.setEnabled(true);
                returnOptionChoose.performClick();
                returnShippingCompany.setEnabled(false);
                scrollToView(svMain, returnOptionChoose);
                break;
            case PROC_SHIPPING_COMPANY:
                Log.e(TAG, "setProc_PROC_SHIPPING_COMPANY");

                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.price).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                retrunlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                companylayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                companylayout.setFocusableInTouchMode(true);

                returnShippingCompany.setEnabled(true);

                returnShippingCompany.performClick();
                scrollToView(svMain, returnShippingCompany);
                break;
        }}

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {
            case PROC_RETURN_NO:

                Log.e(TAG, "inputedEvent_PROC RETURN NO");
                String returnNo = _gts(R.id.returning_number);
                if ("".equals(returnNo)) {
                    U.beepError(this, "注文Noは必須です");
                    _gt(R.id.returning_number).setFocusableInTouchMode(true);
                    break;
                }

                Globals.getterList = new ArrayList<>();
                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("tracking_code", returnNo));
                Globals.getterList.add(new ParamsGetter("mode", "check_order"));

                mRequestStatus = REQ_TRACK_NO;

                new MainAsyncTask(this, Globals.Webservice.sendBack, 1, ReturnStockActivity.this, "Form", Globals.getterList, true).execute();

                break;
            case PROC_PRICE:
                Log.e(TAG, "inputedEvent_PROC_PRICE");
                String price = _gts(R.id.price);
                if ("".equals(price)) {
                    U.beepError(this, "注文Noは必須です");
                    _gt(R.id.price).setFocusableInTouchMode(true);
                    break;
                }
                if(!_gts(R.id.orderId).equals(""))
                    setProc(PROC_RETURN_REASON);
                else{
                    setProc(PROC_ORDER_ID);
                }

                break;
            case PROC_ORDER_ID:
                Log.e(TAG, "inputedEvent_PROC_PRICE");

                String orderid = _gts(R.id.orderId);
                if ("".equals(orderid)) {
                    U.beepError(this, "注文Noは必須です");
                    _gt(R.id.orderId).setFocusableInTouchMode(true);
                    break;
                }
                adminID = spDomain.getString("admin_id", null);


                Globals.getterList = new ArrayList<>();
                Globals.getterList.add(new ParamsGetter("admin_id", adminID));

                if(trackingNoCheck.isChecked())
                {
                    Globals.getterList.add(new ParamsGetter("order_id", orderid));
                    Globals.getterList.add(new ParamsGetter("sort_by","mediacode"));
                    Globals.getterList.add(new ParamsGetter("mode", "check_order"));
                }
                else {
                    Globals.getterList.add(new ParamsGetter("order_id", orderid));
                    Globals.getterList.add(new ParamsGetter("mode", "check_order"));
                }
                mRequestStatus = REQ_ORDERID;

                new MainAsyncTask(this, Globals.Webservice.sendBack, 1, ReturnStockActivity.this, "Form", Globals.getterList, true).execute();

                break;

            case PROC_TRACK_NO:
                Log.e(TAG, "inputedEvent_PROC_TRACK_NO");
                String track = _gts(R.id.trackingNumber);
                if ("".equals(track)) {
                    U.beepError(this, "注文Noは必須です");
                    _gt(R.id.trackingNumber).setFocusableInTouchMode(true);
                    break;
                }
                Globals.getterList = new ArrayList<>();

                adminID = spDomain.getString("admin_id", null);

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("order_id", track));
                Globals.getterList.add(new ParamsGetter("sort_by","mediacode"));
                Globals.getterList.add(new ParamsGetter("mode", "check_order"));

                mRequestStatus = REQ_ORDERID;

                new MainAsyncTask(this, Globals.Webservice.sendBack, 1, ReturnStockActivity.this, "Form", Globals.getterList, true).execute();

                break;

            case PROC_RETURN_REASON:
                returnOptionChoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        switch (returnoptiom[position]) {
                            case "受取拒否":
                                returnreason = 1;
                                break;
                            case "引取依頼":
                                returnreason = 2;
                                break;
                            case "誤発送":
                                returnreason = 3;
                                break;
                            case "商品不良":
                                returnreason = 4;
                                break;
                            case "配達中止":
                                returnreason = 5;
                                break;
                            case "サイズが合わない":
                                returnreason = 6;
                                break;
                            case "イメージ違い":
                                returnreason = 7;
                                break;
                            case "長期不在":
                                returnreason = 8;
                                break;
                            case "住所不明":
                                returnreason = 9;
                                break;
                            case "理由不明":
                                returnreason = 10;
                                break;
                            case "色味相違":
                                returnreason = 11;
                                break;
                            case "その他":
                                returnreason = 99;
                                break;
                            case "サイズオーバー":
                                returnreason = 13;
                                break;
                            case "転居先不明":
                                returnreason = 16;
                        }

                        setoptionId(returnreason + "");
                        Log.e(TAG, "Returnnn Company ID 11111 " + returnreason);

                        if (returnreason == 0) {
                        } else {
                            returnShippingCompany.setEnabled(true);
                            returnShippingCompany.setClickable(true);
                            setProc(PROC_SHIPPING_COMPANY);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;

            case PROC_SHIPPING_COMPANY:
                /*returnShippingCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        switch (ship_companyArray[position]) {
                            case "ヤマト宅急便":
                                shipcompany = 1;
                                break;
                            case "佐川急便":
                                shipcompany = 0;

                                break;
                            case "ゆうパック":
                                shipcompany = 2;
                                break;
                            case "レターパック":
                                shipcompany = 104;
                                break;
                            case "郵便":
                                shipcompany = 101;
                                break;
                            case "メール便":
                                shipcompany = 3;
                                break;
                            case "ゆうパケット":
                                shipcompany = 105;
                                break;
                            case "ネコポス":
                                shipcompany = 205;
                                break;
                            case "ゆうメール":
                                shipcompany = 103;
                                break;
                            case "その他":
                                shipcompany = 404;
                                break;
                        }
                        setshippingcompanyID(shipcompany + "");

                        Log.e(TAG, "shippppp Company ID 111111 " + shipcompany);
                        if(showoption == 1){
                           showdialog();
                        }
                        else setProc(PROC_BARCODE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;*/

            case PROC_BARCODE:
                Log.e(TAG, "inputedEvent_PROC_BARCODEEEE");
                String barcode = _gts(R.id.barcode);
                if ("".equals(barcode)) {
                    U.beepError(this, "バーコードは空にできません");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }
                loterror = false;

                Globals.getterList = new ArrayList<>();

                adminID = spDomain.getString("admin_id", null);

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("order_id",orderID));
                Globals.getterList.add(new ParamsGetter("mode", "check_barcode"));
                Globals.getterList.add(new ParamsGetter("barcode", barcode));


                mRequestStatus = REQ_BARCODE;
                new MainAsyncTask(this, Globals.Webservice.sendBack, 1, ReturnStockActivity.this, "Form", Globals.getterList, true).execute();
                break;

            case PROC_QTY:
                Log.e(TAG, "inputedEvent_PROC_QTY");
                String qty = _gts(R.id.quantity);
                String barcode1 =_gts(R.id.barcode);
                loterror = false;
                Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned "+cProductList.get("processedCnt")+"   qty "+_lastUpdateQty);
                if (isScaned) {
                    if(buff.equals(barcode1)){
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned");
                        qty = U.plusTo(qty, "1");
                        String processedCnt = cProductList.get("processedCnt");
                        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                        _sts(R.id.quantity, qty);
                        if(Integer.parseInt(qty)>1)
                            mTextToSpeak.startSpeaking(qty);
                        _lastUpdateQty = _gts(R.id.quantity);
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned11111111 "+cProductList.get("processedCnt")+"   qty "+_lastUpdateQty);


                        /* check if update in quantity need next action */
                        if (cProductList.get("processedCnt").equals(/*cProductList.get("quantity")*/ arr.get(eop).getNum())) {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned222222");
                            if (getSelectedclassificationtxt.equals("")) {
                                //   setdialog("任意の分類を選択する");
                            }  else {
                                Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned mReturn "+cProductList.get("processedCnt")+"   qty "+_lastUpdateQty);

                                Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned mReturn  "+mReturnList);
                                if(mReturnList.size()>0)
                                    findRepeat();
                                else
                                    addDatatoList();
                            }
                        }
                    } else {
                        U.beepError(this, "数量は空にできません");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty INcomplete");
                    }
                }
                else {
                    Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_Scanned");
                    if ("".equals(qty)) {
                        U.beepError(this, "数量は空にできません");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数字でなければなりません");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                    Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned mReturn111111 "+cProductList.get("processedCnt")+"   qty "+_lastUpdateQty);

                    String processedCnt = cProductList.get("processedCnt");
                    String qtyUpdate = U.minusTo(qty, _lastUpdateQty);
                    String maxQty_ = U.minusTo(cProductList.get("quantity"), processedCnt);

                    Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt   processedCnt" +processedCnt + "  qtyUpdate"+qtyUpdate+"   maxQty_"+maxQty_);
                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_lastUpdateQTY");
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_qtyUpdate");

                        U.beepError(this, "数量オーバーしています。");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if(Integer.parseInt(qty)>Integer.parseInt(arr.get(eop).getNum())){
                        U.beepError(this, "数量オーバーしています。");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);


                        break;
                    }else {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_Scanned11 "+cProductList.get("processedCnt")+"   qty "+_gts(R.id.quantity));

                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt");

                        cProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            if (getSelectedclassificationtxt.equals(""))
                                setdialog("任意の分類を選択する");
                            else {
                                if (mReturnList.size() > 0)
                                    findRepeat();
                                else
                                    addDatatoList();
                            }
                        }
                        else {

                            if (getSelectedclassificationtxt.equals(""))
                                setdialog("任意の分類を選択する");
                            else {
                                if (mReturnList.size() > 0)
                                    findRepeat();
                                else
                                    addDatatoList();
                            }
                        }
                    }}
                break;
        }
    }

    public void sendRequest(){
        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("order_id", orderID));
        Globals.getterList.add(new ParamsGetter("mode", "allreturn"));
        Globals.getterList.add(new ParamsGetter("tracking_code",_gts(R.id.returning_number)));
        Globals.getterList.add(new ParamsGetter("reason",getoptionId()));
        Globals.getterList.add(new ParamsGetter("pickman" ,"true"));
        Globals.getterList.add(new ParamsGetter("shipping_method",getshippingcompanyID()));
        Globals.getterList.add(new ParamsGetter("time_taken",timeTaken().toString()));
        mRequestStatus = FIXED_REQ;

        if(returningnum.isChecked()) {

            Globals.getterList.add(new ParamsGetter("shipping_price", _gts(R.id.price)));
            new MainAsyncTask(ReturnStockActivity.this, Globals.Webservice.sendBack, 1, ReturnStockActivity.this, "Form", Globals.getterList, true).execute();

        } else {
            Globals.getterList.add(new ParamsGetter("shipping_price", ""));
            new MainAsyncTask(ReturnStockActivity.this, Globals.Webservice.sendBack, 1, ReturnStockActivity.this, "Form", Globals.getterList, true).execute();

        }
    }

    @Override
    public void nextProcess() {
        Log.e(TAG," nextProcess");
        this._sts(R.id.barcode, "");
        this._sts(R.id.quantity, "");
        this._sts(R.id.orderId, "");

        this._sts(R.id.returning_number, "");
        this._sts(R.id.price, "");
        this._sts(R.id.lotno, "");
        this._sts(R.id.expidate, "");
        arr.clear();
        list.setAdapter(null);
        mSelectedItem=0;
       /* if (arr.size()>0) {
            arr.clear();
            list.setVisibility(View.GONE);
        }else{
            list.setVisibility(View.VISIBLE);
        }*/

        setoptionId("");
        orderID = "";
        returnreason = 0;
        setshippingcompanyID("");
        barcodevisible= false;

        returnOptionChoose.setEnabled(false);
        returnShippingCompany.setEnabled(false);
        barcodelayout.setVisibility(View.GONE);
        returningnum.setChecked(false);
        mReturnList.clear();
        pricegrid.setVisibility(View.GONE);
        returnOptionChoose.setSelected(false);
        returnOptionChoose.setSelection(0);
        returnShippingCompany.setSelection(0);
        returnShippingCompany.setSelected(false);
        returnShippingCompany.setEnabled(false);
        returnShippingCompany.setClickable(false);
        selectedclassification = "";
        getSelectedclassificationtxt ="";
        updateBadge1("0");

        lotno.setText("");
        expidate.setText("");
        cProductList = null;
        showoption = 1;

        orderrb.setChecked(true);

        if(showKeyboard==false) {
            visible = false;
            numbrbtn.setVisibility(View.VISIBLE);
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            //  Animation bottomDown = AnimationUtils.loadAnimation(this,
            //  R.anim.bottom_down);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
//            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
            Log.e("NewPicking", "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        } else {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
            visible = true;

//			numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e("NewPicking","SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);

            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        setProc(PROC_RETURN_NO);
    }


    @Override
    public void inputedEvent(String buff) {
        Log.e(TAG," inputedEvent");
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        new AlertDialog.Builder(this)
                .setTitle("Clear？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mTextToSpeak.resetQueue();
                        mTextToSpeak.startSpeaking("clear");
                        nextProcess();

                    }
                })
                .setNegativeButton("いいえ", null)
                .show();
    }

    @Override
    public void allclearEvent() {

    }

    @Override
    public void skipEvent() {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_RETURN_NO:
                Log.e(TAG,"ketpressEvent_PROC_RETURN_NO");
                _sts(R.id.returning_number, buff);
                break;
            case PROC_PRICE:
                Log.e(TAG,"ketpressEvent_PROC_PRICE");
                _sts(R.id.price, buff);
                break;
            case PROC_ORDER_ID:
                Log.e(TAG,"ketpressEvent_PROC_ORDERID");
                _sts(R.id.orderId, buff);
                break;
            case PROC_TRACK_NO:
                Log.e(TAG,"ketpressEvent_PROC_ORDERID");
                _sts(R.id.trackingNumber, buff);
                break;
            case PROC_BARCODE:
                Log.e(TAG,"ketpressEvent_PROC_BARCODE");
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY:
                Log.e(TAG,"ketpressEvent_PROC_QTY");
                _sts(R.id.quantity, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (CLEAR_BARCODE.equals(barcode)) {
            Log.e("NewPickingActivity","scanedEvent_CLEAR_BARCODE");
            mBuff.delete(0, mBuff.length());
            clearEvent();
        } else if (ENTER_BARCODE.equals(barcode)) {
            Log.e("NewPickingActivity","scanedEvent_ENTER_BARCODE");
            enterEvent();
        } else if (!barcode.equals("")) {
            if(!dialog.isShowing()) {
                if (mProcNo == PROC_BARCODE) {
                    Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                    String finalbarcode1 = CommonFunctions.getBracode(barcode);
                    barcode = finalbarcode1;

                    if (triplebarcode == triplebarcode) {
                        if ((BaseActivity.getUrl().equals("https://air-logi-st.air-logi.com/service") && BaseActivity.getShopId().equals("1007"))) {

                            if (barcode.length() == 39) {
                                String finalbarcode = barcode.substring(26);
                                Log.e(TAG, "ScannedEvent   " + " substring " + finalbarcode);

                                barcode = finalbarcode;
                            }
                        }
                    }
                    _sts(R.id.barcode, barcode);
                } else if (mProcNo == PROC_ORDER_ID) {
                    _sts(R.id.orderId, barcode);
                } else if (mProcNo == PROC_TRACK_NO) {
                    _sts(R.id.trackingNumber, barcode);
                } else if (mProcNo == PROC_PRICE) _sts(R.id.price, barcode);

                if (mProcNo == PROC_QTY) {
                    Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                    String finalbarcode1 = CommonFunctions.getBracode(barcode);
                    barcode = finalbarcode1;

                    if (triplebarcode == triplebarcode) {
                        if ((BaseActivity.getUrl().equals("https://air-logi-st.air-logi.com/service") && BaseActivity.getShopId().equals("1007"))) {
                            if (barcode.length() == 39) {
                                String finalbarcode = barcode.substring(26);
                                Log.e(TAG, "ScannedEvent   " + " substring " + finalbarcode);

                                barcode = finalbarcode;
                            }
                        }
                    }
                }

                if (mProcNo == PROC_RETURN_NO) _sts(R.id.returning_number, barcode);
                this.inputedEvent(barcode, true);
            }
            else{
                toast("wait");
            }
        }
    }

    @Override
    public void enterEvent() {
    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:
                _sts(R.id.barcode, barcode);
                break;
            case PROC_PRICE:
                _sts(R.id.price, barcode);
                break;
            case PROC_RETURN_NO:
                _sts(R.id.returning_number, barcode);
                break;
            case PROC_QTY:
                _sts(R.id.quantity, barcode);
                break;
            case PROC_ORDER_ID:
                _sts(R.id.orderId, barcode);
                break;
            case PROC_TRACK_NO:
                _sts(R.id.trackingNumber, barcode);
                break;


        }
    }
    public void currLineData(Map<String, String> data) {
        Log.e(TAG, "currLineData");
        cProductList = data;
        Log.e(TAG, "currLineData  " + cProductList);
        if(!loterror)
            nextWork();


    }
    public void nextWork() {
        Log.e(TAG, "nextwork"+cProductList.get("processedCnt"));

        if(mReturnList.size()>0)
        {
            String temp = "0";
            Log.e(TAG, "nextwork11    "+mReturnList);
            for (Map<String, String> map1 : mReturnList) {
                Log.e(TAG, "nextwork22    "+map1);
                String _b = map1.get("barcode");
                String _b1 = cProductList.get("barcode");

                String _c = map1.get("code");
                String _c1 = cProductList.get("code");
                if (_b.equals(_b1) || _c.equals(_c1)) {
                    temp = U.plusTo(map1.get("num"),temp);
                    Log.e(TAG, "nextwork33 "+map1.get("num")+"   temp "+temp);
                }
            }
            cProductList.put("processedCnt",temp);

        }
        if (Integer.parseInt(cProductList.get("processedCnt"))>= Integer.parseInt(cProductList.get("quantity")))
        {
            U.beepError(this, "数量は総量を超えることはできません");
            loterror = true;
            setProc(PROC_BARCODE);
        }
        else {

            _sts(R.id.quantity, "1");
            String processedCnt = cProductList.get("processedCnt");
            cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
            setProc(PROC_QTY);
            mTextToSpeak.resetQueue();
            mTextToSpeak.startSpeaking(_gts(R.id.quantity));
            _lastUpdateQty = _gts(R.id.quantity);
            Log.e(TAG, "nextwork  " + cProductList.get("processedCnt") + "   qty " + _lastUpdateQty);
            if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                if (getSelectedclassificationtxt.equals("")) {
                    //   setdialog("任意の分類を選択する");
                } else {
                    if (mReturnList.size() > 0)
                        findRepeat();
                    else
                        addDatatoList();
                }
            }
        }
    }

    protected boolean showInfo() {

        if (mReturnList == null) {
            return true;
        }
        Log.e(TAG,"initiatePopup");
        final PopupWindow popupWindow = new PopupWindow(this);

        // レイアウト設定
        View popupView = getLayoutInflater().inflate(R.layout.stock_return_batch, null);
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
        addlocation.setText("送信");
        addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (mReturnList.size() > 0) {
                    stopTimer();

                    if(lotselected) {

                        StringBuffer order_sub_id = new StringBuffer();
                        StringBuffer barcode = new StringBuffer();
                        StringBuffer qty = new StringBuffer();
                        StringBuffer classification = new StringBuffer();
                        StringBuffer code = new StringBuffer();
                        StringBuffer lot = new StringBuffer();
                        StringBuffer expri = new StringBuffer();

                        for (Map<String, String> map : mReturnList) {
                            if (!map.get("quantity").equals("0")) {
                                qty.append(",").append(map.get("num"));
                                order_sub_id.append(",").append(map.get("order_sub_id"));
                                classification.append(",").append(map.get("classification"));
                                code.append(",").append(map.get("code"));

                                lot.append(",").append(map.get("lot"));
                                expri.append(",").append(map.get("expri"));

                            }
                        }


                        if (returningnum.isChecked()) {
                            progress.Show();
                            SubmitReturnStokeReq req = new SubmitReturnStokeReq(adminID, orderID, "return", _gts(R.id.returning_number), getoptionId(), getshippingcompanyID(),
                                    timeTaken().toString(), order_sub_id.substring(1).toString(), classification.substring(1).toString(), qty.substring(1).toString(),
                                    code.substring(1).toString(), _gts(R.id.price), lot.substring(1).toString(), expri.substring(1).toString(), app.getSerial(), BaseActivity.getShopId(), getResources().getString(R.string.version));
                            manager.SubmitRetunStoke(req, submitreturn);
                        } else {
                            progress.Show();
                            SubmitReturnStokeReq req = new SubmitReturnStokeReq(adminID, orderID, "return", _gts(R.id.returning_number), getoptionId(), getshippingcompanyID(),
                                    timeTaken().toString(), order_sub_id.substring(1).toString(), classification.substring(1).toString(), qty.substring(1).toString(),
                                    code.substring(1).toString(), "", lot.substring(1).toString(), expri.substring(1).toString(), app.getSerial(), BaseActivity.getShopId(), getResources().getString(R.string.version));
                            manager.SubmitRetunStoke(req, submitreturn);
                        }

                    }
                    else {

                        Globals.getterList = new ArrayList<>();
                        adminID = spDomain.getString("admin_id", null);
                        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                        Globals.getterList.add(new ParamsGetter("order_id", orderID));
                        Globals.getterList.add(new ParamsGetter("mode", "return"));
                        Globals.getterList.add(new ParamsGetter("tracking_code", _gts(R.id.returning_number)));
                        Globals.getterList.add(new ParamsGetter("reason", getoptionId()));
                        Globals.getterList.add(new ParamsGetter("shipping_method", getshippingcompanyID()));
                        Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));


                        StringBuffer order_sub_id = new StringBuffer();
                        StringBuffer barcode = new StringBuffer();
                        StringBuffer qty = new StringBuffer();
                        StringBuffer classification = new StringBuffer();
                        StringBuffer code = new StringBuffer();
                        for (Map<String, String> map : mReturnList) {
                            if (!map.get("quantity").equals("0")) {
                                qty.append("\t").append(map.get("num"));
                                order_sub_id.append("\t").append(map.get("order_sub_id"));
                                classification.append("\t").append(map.get("classification"));
                                code.append("\t").append(map.get("code"));
                            }
                        }

                        Globals.getterList.add(new ParamsGetter("order_sub_id", order_sub_id.substring(1).toString()));
                        Globals.getterList.add(new ParamsGetter("condition_id", classification.substring(1).toString()));
                        Globals.getterList.add(new ParamsGetter("quantity", qty.substring(1).toString()));
                        Globals.getterList.add(new ParamsGetter("code", code.substring(1).toString()));

                        mRequestStatus = FIXED_REQ;

                        if (returningnum.isChecked()) {
                            Globals.getterList.add(new ParamsGetter("shipping_price", _gts(R.id.price)));
                            new MainAsyncTask(ReturnStockActivity.this, Globals.Webservice.sendBack, 1, ReturnStockActivity.this, "Form", Globals.getterList, true).execute();

                        } else {
                            Globals.getterList.add(new ParamsGetter("shipping_price", ""));
                            new MainAsyncTask(ReturnStockActivity.this, Globals.Webservice.sendBack, 1, ReturnStockActivity.this, "Form", Globals.getterList, true).execute();
                        }
                    }
                }
            }
        });

        ListView lv = (ListView) getPopupWindow().getContentView().findViewById(R.id.lvPackingList);
        initWorkList(lv);
        getPopupWindow().showAtLocation(findViewById(R.id.barcode), Gravity.CENTER, 0, 32);
        return false;

    }

    protected ListViewItems initWorkList(final ListView lv) {
        Log.e(TAG," initWorkList" +mReturnList);
        lv.setAdapter(null);

        // GetReturnOrderBarcode.return_class

        ListViewItems data = new ListViewItems();
        for (int i = 0; i <=mReturnList.size() - 1; i++) {
            Map<String, String> row =mReturnList.get(i);
            Log.e(TAG," initWorkList_"+row.get("code"));

            data.add(data.newItem().add(R.id.btc_ins_0,row.get("code"))
                    .add(R.id.btc_ins_1,row.get("num"))
                    .add(R.id.btc_ins_2, row.get("classificationtxt")));

        }
        ListViewAdapter adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.stock_return_batch_list){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Log.e(TAG,"Returnleft1 "+position);

                ImageView img = (ImageView) v.findViewById(R.id.delete_stock);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mReturnList.remove(position);
                        setBadge1(mReturnList.size());
                        Log.e(TAG,"Returnleft  "+mReturnList);
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
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }

    private void findRepeat() {
        boolean match = false;
        Log.e("ShipActivity", " findRepeat");
        for (Map<String, String> map1 : mReturnList) {

            String _l = map1.get("lot");
            String _exp = map1.get("expiration_date");
            String _c = map1.get("code");
            String _cla = map1.get("classification");
            String _l1 = cProductList.get("lot");
            String _exp1 = cProductList.get("expiration_date");
            String _c1 = cProductList.get("code");
            String _cla1 = selectedclassification;


            if (_l.equals(_l1) && _c.equals(_c1) && _cla.equals(_cla1) && _exp.equals(_exp1) ) {
                String qty = cProductList.get("quantity");
                //  String temp = U.plusTo(map1.get("num"), _gts(R.id.quantity));
                String temp = U.plusTo(map1.get("num"), _gts(R.id.quantity));
                match = true;
                if (U.compareNumeric(qty, temp) == 1) {
                    U.beepError(this, "数量は総量を超えることはできません");
                    break;
                } else {
                    map1.put("num", temp);
                    setProc(PROC_BARCODE);
                    _sts(R.id.barcode, "");
                    _sts(R.id.quantity, "");

                    _sts(R.id.lotno, "");
                    _sts(R.id.expidate, "");

                    mSelectedItem=0;
                    arr.clear();
                    list.setVisibility(View.GONE);

                    classification.clearCheck();
                    selectedclassification = "";
                    getSelectedclassificationtxt = "";
                    updateBadge1(mReturnList.size() + "");
                    break;
                }
            }

        }
        if(match == false) {
            addDatatoList();
        }

    }

    void addDatatoList() {
        HashMap<String,String> map =new HashMap<String,String>();
        map.put("barcode",_gts(R.id.barcode));
        map.put("order_sub_id",cProductList.get("order_sub_id"));
        map.put("code",cProductList.get("code"));
        map.put("quantity",cProductList.get("quantity"));
        map.put("classification",selectedclassification);
        map.put("classificationtxt",getSelectedclassificationtxt);
        map.put("num",_gts(R.id.quantity));
        map.put("lot",_gts(R.id.lotno));
        map.put("expri",_gts(R.id.expidate));
        mReturnList.add(map);
        Log.e(TAG, "Mapp Containss  "+map);
        setProc(PROC_BARCODE);
        _sts(R.id.barcode,"");
        _sts(R.id.quantity,"");

        _sts(R.id.lotno, "");
        _sts(R.id.expidate, "");

        mSelectedItem=0;
        arr.clear();
        list.setVisibility(View.GONE);

        classification.clearCheck();
        selectedclassification="";
        getSelectedclassificationtxt ="";
        updateBadge1(mReturnList.size()+"");
    }

    public void updateBadge1(String orderCount) {
        btnBlue.setText(orderCount);
//      totalordercount=Integer.valueOf(orderCount);
        Log.e(TAG, "updateBadge1  " + orderCount);
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
                Log.e(TAG,"CODE======Null");
                CommonDialogs.customToast(getApplicationContext(),"Network error occured");
            }
            if ("0".equals(code) == true) {

                Log.e("SendLogs111 ",code+"  "+msg+"  "+result1);
                Log.e("SendLogs111 ",">>>>>>>>>> "+mRequestStatus);
                if(mRequestStatus == REQ_TRACK_NO)
                    new GetReturnTrack().post(code,msg,result1, mHash, ReturnStockActivity.this);
                else if(mRequestStatus == REQ_ORDERID)
                    new GetReturnOrder().post(code,msg,result1, mHash, ReturnStockActivity.this);
                else if(mRequestStatus == REQ_BARCODE) {
                    /*if (BaseActivity.getLotPress()) {
                        mainexp.setVisibility(View.VISIBLE);
                        mainlot.setVisibility(View.VISIBLE);
                    } else {
                        mainexp.setVisibility(View.GONE);
                        mainlot.setVisibility(View.GONE);
                    }*/

                    getLot();
                    new GetReturnOrderBarcode().post(code, msg, result1, mHash, ReturnStockActivity.this);

                } else if(mRequestStatus == FIXED_REQ) {
                    U.beepKakutei(this, "検品データを登録しました。");
                    nextProcess();
                }
            }else if(code.equalsIgnoreCase("1020")){
                new AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(ReturnStockActivity.this, LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })
                        .show();
            } else{
                if(mRequestStatus == REQ_TRACK_NO) {
                    if(code.equals("14204")) {
                        if (returningnum.isChecked()) {
                            setProc(PROC_PRICE);
                        } else{
                            setProc(PROC_ORDER_ID);
                        }
                    }
                    else U.beepError(this,msg);
                }
                else if(mRequestStatus == REQ_ORDERID)
                    new GetReturnOrder().valid(code,msg,result1, mHash, ReturnStockActivity.this);
                else if(mRequestStatus == REQ_BARCODE)
                    new GetReturnOrderBarcode().valid(code,msg,result1, mHash, ReturnStockActivity.this);

                else if(mRequestStatus == FIXED_REQ){
                    U.beepError(this, msg);
                }
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }

    public void showdialog () {

        final SweetAlertDialog pDialog1 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog1.setCancelable(true);
        pDialog1.setContentText("注文単位で返品登録しますか？");
        pDialog1.setConfirmText("はい。");
        pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sendRequest();
                pDialog1.dismiss();
            }
        });

        pDialog1.setCancelText("いいえ。");
        pDialog1.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                pDialog1.dismiss();
            }
        });

        pDialog1.show();

    }

    public void setdialog (String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(msg);
        builder.setCancelable(false);
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item) {
                    case 0:
                        selectedclassification = "1";
                        getSelectedclassificationtxt ="良品";
                        break;
                    case 1:
                        selectedclassification = "2";
                        getSelectedclassificationtxt ="B品";
                        break;
                    case 2:
                        selectedclassification = "3";
                        getSelectedclassificationtxt ="破損";
                        break;
                }
                if (mReturnList.size() > 0)
                    findRepeat();
                else
                    addDatatoList();
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }

    @Override
    public void onSucess(int status, ReturnStokeLotExpResult message) throws JsonIOException {
        if (message.getCode().equals("0")){
            progress.Dismiss();

            arr = message.getResults();
            eop =0 ;
            list.setAdapter(null);

            if(lotselected){
                if (arr.size()>0) {

                    if (arr.size()==1){
                        if (arr.get(0).getLot().equals("")&& arr.get(0).getExpiration_date().equals("")){
                            list.setVisibility(View.GONE);
                        }else{
                            list.setVisibility(View.VISIBLE);
                            if(mReturnList.size()>0)
                                setList();
                            else
                                Listview();
                        }
                    }else {
                        list.setVisibility(View.VISIBLE);
                        if(mReturnList.size()>0)
                            setList();
                        else
                            Listview();
                    }
                }
            }
            else list.setVisibility(View.GONE);
        }
        else{
            U.beepError(this,message.getMessage());
        }
    }

    private void setList(){



        for(int j = 0;j < mReturnList.size(); j++){
            Map<String, String> map1 = mReturnList.get(j);
            String _l = map1.get("lot");
            String _exp = map1.get("expri");
            String _b = map1.get("barcode");
            int qtyscan = Integer.parseInt(map1.get("num"));
            int q = 0;


            for (int i = 0; i < arr.size(); i++) {
                String _l1 = arr.get(i).getLot();
                String _exp1 = arr.get(i).getExpiration_date();
                String _q1 = arr.get(i).getNum();
                int boxqty = Integer.parseInt(arr.get(i).getNum());


                if(_l.equalsIgnoreCase(_l1) && _exp.equalsIgnoreCase(_exp1)&& _b.equalsIgnoreCase(_gts(R.id.barcode))){
                    if(qtyscan >= boxqty)
                    {
                        q = qtyscan - boxqty;
                        arr.remove(i);
                        if(q == 0)
                            break;
                        else{
                            qtyscan = q;
                            i--;
                        }

                    } else {
                        q = boxqty - qtyscan;
                        arr.get(i).setNum(String.valueOf(q));

                        break;
                    }
                }


            }

        }
        if(arr.size()>0)
            Listview();
        else if(!loterror ){
            loterror = true;
            U.beepError(this, "数量は総量を超えることはできません");
            setProc(PROC_BARCODE);
        }

    }

    private  void Listview(){
        ListViewItems data = new ListViewItems();
        ArrayList<Integer> used = new ArrayList<>();

        for (int i = 0; i <= arr.size() - 1; i++) {

            data.add(data.newItem().add(R.id.lotno, ": "+arr.get(i).getLot())
                    .add(R.id.expri, ": "+arr.get(i).getExpiration_date())
                    .add(R.id.qty, ": "+arr.get(i).getNum())
            );
            used.add(i);

            adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.adap_retunstock) {
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    if (position == mSelectedItem) {
                        Log.e(TAG, "selected position  " + position);
                        v.setBackgroundColor(Color.YELLOW);

                    } else {
                        if (position % 2 == 1) {
                            Log.e(TAG, "Odd position");

                            v.setBackgroundColor(Color.GRAY);
                        } else {
                            Log.e(TAG, "Even positionnn");
                            v.setBackgroundColor(Color.WHITE);
                        }
                    }

                    return v;
                }

            };
            list.setAdapter(adapter);
            // 単一選択モードにする
            list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    lotno.setText(arr.get(position).getLot());
                    expidate.setText(arr.get(position).getExpiration_date());


                    mSelectedItem = position;
                    eop =position ;
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
                }
            });

        }

        if (data.getData().size() > 0){
            lotno.setText(arr.get(0).getLot());
            expidate.setText(arr.get(0).getExpiration_date());
            list.setItemChecked(0, true);

        }

    }

    @Override
    public void onSucess(int status, SubmitReturnStokeResult message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("0")){
            U.beepKakutei(this, "検品データを登録しました。");
            nextProcess();
        }
        else{
            U.beepError(this,message.getMessage());
        }
    }

    @Override
    public void onError(int status, ResponseBody error) {
        progress.Dismiss();
        Log.e("cbjhdsbhvksfbn",">>>>>>>>>>>>>> Eror");
    }

    @Override
    public void onFaliure() {
        progress.Dismiss();
        Log.e("cbjhdsbhvksfbn",">>>>>>>>>>>>>> Faliure");

    }

    @Override
    public void onNetworkFailure() {
        progress.Dismiss();
        Log.e("cbjhdsbhvksfbn",">>>>>>>>>>>>>> Network Failure");
    }

    public void getLot(){
        String barcode= _gts(R.id.barcode);
        progress.Show();
        ReturnStokeLotExpReq req = new ReturnStokeLotExpReq(adminID,orderID,"check_barcode",barcode,app.getSerial(),BaseActivity.getShopId(),getResources().getString(R.string.version));
        manager.GETLOTEXP(req,getlotexp);
    }

    public void showReturndialog(){
        dialog = new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE);
        dialog.setTitle("過去に返品登録が行わた\n" +
                "ことのある注文です。\n" +
                "再度返品登録しますか？");

        dialog.setCancelable(false);
        dialog.setConfirmButton("はい", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if(mProcNo == PROC_RETURN_NO){
                    if (returningnum.isChecked())
                        setProc(PROC_PRICE);
                    else
                        setProc(PROC_RETURN_REASON);
                }
                else {
                    setProc(PROC_RETURN_REASON);
                }
                U.beepNext();
                dialog.dismiss();
            }
        });
        dialog.setCancelButton("キャンセル", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if(mProcNo == PROC_RETURN_NO){
                    setProc(PROC_RETURN_NO);
                }
                else {
                    setProc(PROC_ORDER_ID);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
