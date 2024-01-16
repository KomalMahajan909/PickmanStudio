package com.itechvision.ecrobo.pickman.Chatman.PickWork.NewReturnStock;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetReturnOrderBarcodenew;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetReturnOrdernew;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetReturnTracka;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
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
//Panelled Mid-Top Sneakers

public class NewReturnStockActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
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


    public TextToSpeak mTextToSpeak;
    String TAG= NewReturnStockActivity.class.getSimpleName();

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
    CharSequence[] values = {" 良品 "," B品 "," 破損 "};
    String ship_companyArray[] = {"配送会社を選択","ヤマト宅急便","佐川急便","ゆうパック","レターパック","郵便","メール便","ゆうパケット","ネコポス","ゆうメール","その他"};
    String returnoptiom [] = {"返品理由を選択","長期不在","受取拒否","住所不明","配達中止","その他","引取依頼","サイズが合わない","イメージ違い","理由不明","色味相違" , "サイズオーバー"};
    AlertDialog alertDialog1;
    String selectedclassification = "";
    String getSelectedclassificationtxt = "";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newreturn_stock);

        ButterKnife.bind(NewReturnStockActivity.this);

        getIDs();

        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        orderrb= (RadioButton)findViewById(R.id.orderRb);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);


        mTextToSpeak = new TextToSpeak(this);
        showKeyboard=BaseActivity.getaddKeyboard();
        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
            visible = true;

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
            numbrbtn.setVisibility(View.GONE);
        }


        ArrayAdapter adapter1 = new ArrayAdapter (this, android.R.layout.simple_spinner_item,ship_companyArray){
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

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
        returnShippingCompany.setAdapter(adapter1);
        returnShippingCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                Log.e(TAG, "Positionnnn 11113333333" + position);

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
                    case "その他":
                        returnreason = 99;
                        break;
                    case "サイズオーバー":
                        returnreason = 13;
                        break;

                }
                setoptionId(returnreason+"");
                if(position>0) {

                    setProc(PROC_SHIPPING_COMPANY);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

        actionbarImplement(this, "戻品登録", " ",
                0, true,false,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        btnBlue.setOnClickListener(this);
        relLayout1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
            case R.id.notif_count_blue:
                showInfo();
                break;

            default:
                break;
        }
    }
    public void AddLayout(View view)
    {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
        if(visible==false)
        {

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

        }
        else
        {
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

      /*  switch (returningType.getCheckedRadioButtonId())
        {
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

        }*/
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

        mProcNo = procNo;
        switch (procNo) {
            case PROC_RETURN_NO:

                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.price).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                retrunlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                companylayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.returning_number));
                break;
            case PROC_PRICE:

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
            case PROC_BARCODE:

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

                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.price).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                companylayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                retrunlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                retrunlayout.setFocusableInTouchMode(true);

                scrollToView(svMain, returnOptionChoose);
                break;
            case PROC_SHIPPING_COMPANY:

                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.price).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.returning_number).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));

                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                retrunlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                companylayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                companylayout.setFocusableInTouchMode(true);
                scrollToView(svMain, returnShippingCompany);
                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {
            case PROC_RETURN_NO:    // 注文No

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

                new MainAsyncTask(this, Globals.Webservice.sendBack, 1, NewReturnStockActivity.this, "Form", Globals.getterList, true).execute();
                setProc(PROC_PRICE);
                break;
            case PROC_PRICE:

                String price = _gts(R.id.price);
                if ("".equals(price)) {
                    U.beepError(this, "注文Noは必須です");
                    _gt(R.id.price).setFocusableInTouchMode(true);
                    break;
                }
                if(!_gts(R.id.orderId).equals(""))
                    setProc(PROC_BARCODE);
                else
                    setProc(PROC_ORDER_ID);
                break;
            case PROC_ORDER_ID:

                String orderid = _gts(R.id.orderId);
                if ("".equals(orderid)) {
                    U.beepError(this, "注文Noは必須です");
                    _gt(R.id.orderId).setFocusableInTouchMode(true);
                    break;
                }
                Globals.getterList = new ArrayList<>();

                adminID = spDomain.getString("admin_id", null);

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("order_no", orderid));
                Globals.getterList.add(new ParamsGetter("mode", "check_order"));

                mRequestStatus = REQ_ORDERID;

                new MainAsyncTask(this, Globals.Webservice.sendBack, 1, NewReturnStockActivity.this, "Form", Globals.getterList, true).execute();

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
                            case "サイズオーバー" :
                                returnreason = 13;
                        }

                        setoptionId(returnreason + "");
                        Log.e(TAG, "Returnnn Company ID 11111 " + returnreason);
                        setProc(PROC_SHIPPING_COMPANY);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;

            case PROC_SHIPPING_COMPANY:
                returnShippingCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                break;

            case PROC_BARCODE:
                Log.e(TAG, "inputedEvent_PROC_BARCODEEEE");
                String barcode = _gts(R.id.barcode);
                if ("".equals(barcode)) {
                    U.beepError(this, "バーコードは空にできません");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }
                Globals.getterList = new ArrayList<>();

                adminID = spDomain.getString("admin_id", null);

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("order_no", _gts(R.id.orderId)));
                Globals.getterList.add(new ParamsGetter("mode", "check_barcode"));
                Globals.getterList.add(new ParamsGetter("barcode", barcode));

                mRequestStatus = REQ_BARCODE;
                new MainAsyncTask(this, Globals.Webservice.sendBack, 1, NewReturnStockActivity.this, "Form", Globals.getterList, true).execute();
                break;

            case PROC_QTY:
                Log.e(TAG, "inputedEvent_PROC_QTY");
                String qty = _gts(R.id.quantity);
                String barcode1 =_gts(R.id.barcode);
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
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned222222");
                            if (getSelectedclassificationtxt.equals(""))
                                setdialog("任意の分類を選択する");
                            else {
                                Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned mReturn "+cProductList.get("processedCnt")+"   qty "+_lastUpdateQty);

                                Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned mReturn  "+mReturnList);
                                if(mReturnList.size()>0)
                                    findRepeat();
                                else
                                    addDatatoList();
                            }
                        }
                    } else
                        Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty INcomplete");

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
                    } else {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_Scanned11111111 "+cProductList.get("processedCnt")+"   qty "+_gts(R.id.quantity));

                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt");
                        cProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity")))
                            if (getSelectedclassificationtxt.equals(""))
                                setdialog("任意の分類を選択する");
                            else {
                                if (mReturnList.size() > 0)
                                    findRepeat();
                                else
                                    addDatatoList();
                            }
                        else
                        if (getSelectedclassificationtxt.equals(""))
                            setdialog("任意の分類を選択する");
                        else {
                            if (mReturnList.size() > 0)
                                findRepeat();
                            else
                                addDatatoList();
                        }
                    }}
                break;
        }
    }

    public void sendRequest(){
        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("order_no", _gts(R.id.orderId)));
        Globals.getterList.add(new ParamsGetter("mode", "allreturn"));
        Globals.getterList.add(new ParamsGetter("tracking_code",_gts(R.id.returning_number)));
        Globals.getterList.add(new ParamsGetter("reason","その他"));
        Globals.getterList.add(new ParamsGetter("pickman" ,"true"));
        Globals.getterList.add(new ParamsGetter("shipping_method","その他"));
        Globals.getterList.add(new ParamsGetter("time_taken",timeTaken().toString()));
        mRequestStatus = FIXED_REQ;

        if(returningnum.isChecked()) {
            Globals.getterList.add(new ParamsGetter("shipping_price", _gts(R.id.price)));
            new MainAsyncTask(NewReturnStockActivity.this, Globals.Webservice.sendBack, 1, NewReturnStockActivity.this, "Form", Globals.getterList, true).execute();

        } else {
            Globals.getterList.add(new ParamsGetter("shipping_price", ""));
            new MainAsyncTask(NewReturnStockActivity.this, Globals.Webservice.sendBack, 1, NewReturnStockActivity.this, "Form", Globals.getterList, true).execute();

        }
    }
    @Override
    public void nextProcess() {
        Log.e(TAG," nextProcessssssssss");
        this._sts(R.id.barcode, "");
        this._sts(R.id.quantity, "");
        this._sts(R.id.orderId, "");
        this._sts(R.id.returning_number, "");
        this._sts(R.id.price, "");

         setoptionId("");
         returnreason = 0;
         setshippingcompanyID("");

         barcodevisible= false;
         barcodelayout.setVisibility(View.VISIBLE);
         returningnum.setChecked(false);
         mReturnList.clear();
//         pricegrid.setVisibility(View.VISIBLE);
         returnOptionChoose.setSelected(false);
         returnOptionChoose.setSelection(0);
         returnShippingCompany.setSelection(0);
         returnShippingCompany.setSelected(false);
         selectedclassification = "";
         getSelectedclassificationtxt ="";
         updateBadge1("0");
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
//            Animation bottomDown = AnimationUtils.loadAnimation(this,
//                    R.anim.bottom_down);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
//            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
            Log.e("NewPicking", "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
        else
        {
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

            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        setProc(PROC_ORDER_ID);
    }


    @Override
    public void inputedEvent(String buff) {
        Log.e(TAG," inputedEventttttttttt");
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
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_RETURN_NO:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_RETURN_NOOO");
                _sts(R.id.returning_number, buff);
                break;
            case PROC_PRICE:
                Log.e(TAG,"ketpressEvent_PROC_PRICEEEEEE");
                _sts(R.id.price, buff);
                break;
            case PROC_ORDER_ID:
                Log.e(TAG,"ketpressEvent_PROC_ORDERRRIDDDDD");
                _sts(R.id.orderId, buff);
                break;
            case PROC_BARCODE:    // バーコード
                Log.e(TAG,"ketpressEvent_PROC_BARCODEEEEEE");
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                Log.e(TAG,"ketpressEvent_PROC_QTYYYYYYYYY");
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
            if (mProcNo == PROC_BARCODE) {
                Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
                String finalbarcode1 = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode1;

                if (triplebarcode == triplebarcode){
                    if((BaseActivity.getUrl().equals("https://air-logi-st.air-logi.com/service")&&  BaseActivity.getShopId().equals("1007"))){
                        if(barcode.length()== 39){
                            String finalbarcode =barcode.substring(26);
                            Log.e(TAG, "ScannedEventttttt   " + " substring "  + finalbarcode );

                            barcode= finalbarcode;
                        }
                    }}
                _sts(R.id.barcode, barcode);
            }
            else if(mProcNo == PROC_ORDER_ID){
                _sts(R.id.orderId, barcode);
            }

            else if(mProcNo == PROC_PRICE) _sts(R.id.price, barcode);



            if (mProcNo == PROC_QTY){
                Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
                String finalbarcode1 = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode1;

                if (triplebarcode == triplebarcode){
                    if((BaseActivity.getUrl().equals("https://air-logi-st.air-logi.com/service")&&  BaseActivity.getShopId().equals("1007"))){
                        if(barcode.length()== 39){
                            String finalbarcode =barcode.substring(26);
                            Log.e(TAG, "ScannedEventttttt   " + " substring "  + finalbarcode );

                            barcode= finalbarcode;
                        }
                    }}
            }

            if(mProcNo == PROC_RETURN_NO) _sts(R.id.returning_number, barcode);
            this.inputedEvent(barcode, true);
        }
    }

    @Override
    public void enterEvent() {
    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                Log.e(TAG,"deleteEvent_PROC_BARCODE");
                _sts(R.id.barcode, barcode);
                break;
            case PROC_PRICE:    // バーコード
                Log.e(TAG,"deleteEvent_PROC_BARCODE");
                _sts(R.id.price, barcode);
                break;
            case PROC_RETURN_NO:    // バーコード
                Log.e(TAG,"deleteEvent_PROC_BARCODE");
                _sts(R.id.returning_number, barcode);
                break;
            case PROC_QTY:
                Log.e(TAG,"deleteEvent_PROC_QTY");
                _sts(R.id.quantity, barcode);
                break;
            case PROC_ORDER_ID:
                Log.e(TAG,"deleteEvent_PROC_ORDERID");
                _sts(R.id.orderId, barcode);
                break;

        }
    }
    public void currLineData(Map<String, String> data) {
        Log.e(TAG, "currLineDataaaaaaaaaaa");
        cProductList = data;
        Log.e(TAG, "currLineDataaaaaaaaaaa  " + cProductList);
    }
    public void nextWork() {
        Log.e(TAG, "nextworkkkkkk"+cProductList.get("processedCnt"));

        if(mReturnList.size()>0)
        {
            String temp = "0";
            Log.e(TAG, "nextworkkkkkk 111111111111    "+mReturnList);
            for (Map<String, String> map1 : mReturnList) {
                Log.e(TAG, "nextworkkkkkk  22222    "+map1);
                String _b = map1.get("order_sub_id");
                String _b1 = cProductList.get("order_sub_id");
                if (_b.equals(_b1)) {
                    temp = U.plusTo(map1.get("num"),temp);
                    Log.e(TAG, "nextworkkkkkk 33333333 "+map1.get("num")+"   temp "+temp);
                }

            }
            cProductList.put("processedCnt",temp);

        }
        _sts(R.id.quantity,"1");
        String processedCnt = cProductList.get("processedCnt");
        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
        setProc(PROC_QTY);
        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
        _lastUpdateQty = _gts(R.id.quantity);
        Log.e(TAG, "nextworkkkkkk  "+cProductList.get("processedCnt")+"   qty "+_lastUpdateQty);
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
    }

    protected boolean showInfo() {
        Log.e(TAG, " showInfooooo");

        if (mReturnList == null) {
            return true;
        }
        Log.e(TAG,"initiatePopuppppppppp");
        final PopupWindow popupWindow = new PopupWindow(this);

        Log.e(TAG,"initiatePopuppppppppp_getPopupWindow");

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
                stopTimer();

                Globals.getterList = new ArrayList<>();

                adminID = spDomain.getString("admin_id", null);

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("order_no", _gts(R.id.orderId)));
                Globals.getterList.add(new ParamsGetter("mode", "return"));
                Globals.getterList.add(new ParamsGetter("tracking_code",_gts(R.id.returning_number)));
                Globals.getterList.add(new ParamsGetter("reason",getoptionId()));
                Globals.getterList.add(new ParamsGetter("shipping_method","99"));
                Globals.getterList.add(new ParamsGetter("time_taken",timeTaken().toString()));

                StringBuffer order_sub_id = new StringBuffer();
                StringBuffer barcode =new StringBuffer();

                StringBuffer qty = new StringBuffer();
                StringBuffer classification =new StringBuffer();
                StringBuffer code =new StringBuffer();
                for (Map<String, String> map : mReturnList) {
                    if (!map.get("quantity").equals("0")) {
                        qty.append("\t").append(map.get("num"));
                        order_sub_id.append("\t").append(map.get("order_sub_id"));
                        classification.append("\t").append(map.get("classification"));
                        code.append("\t").append(map.get("code"));

                    }
                }

                Globals.getterList.add(new ParamsGetter("order_sub_id",order_sub_id.substring(1).toString()));
                Globals.getterList.add(new ParamsGetter("condition_id",classification.substring(1).toString()));
                Globals.getterList.add(new ParamsGetter("quantity",qty.substring(1).toString()));
                Globals.getterList.add(new ParamsGetter("code",code.substring(1).toString()));

                mRequestStatus = FIXED_REQ;

                 if(returningnum.isChecked()) {
                    Globals.getterList.add(new ParamsGetter("shipping_price", _gts(R.id.price)));
                    new MainAsyncTask(NewReturnStockActivity.this, Globals.Webservice.sendBack, 1, NewReturnStockActivity.this, "Form", Globals.getterList, true).execute();

                } else {
                    Globals.getterList.add(new ParamsGetter("shipping_price", ""));
                    new MainAsyncTask(NewReturnStockActivity.this, Globals.Webservice.sendBack, 1, NewReturnStockActivity.this, "Form", Globals.getterList, true).execute();
                }
            }
        });

        ListView lv = (ListView) getPopupWindow().getContentView().findViewById(R.id.lvPackingList);
        initWorkList(lv);
         // 画面中央に表示
        getPopupWindow().showAtLocation(findViewById(R.id.barcode), Gravity.CENTER, 0, 32);
        return false;

    }

    protected ListViewItems initWorkList(final ListView lv) {
        Log.e(TAG," initWorkList" +mReturnList);
        lv.setAdapter(null);

        ListViewItems data = new ListViewItems();
        for (int i = 0; i <=mReturnList.size() - 1; i++) {
            Map<String, String> row =mReturnList.get(i);
            Log.e(TAG," initWorkList_"+row.get("code"));

            data.add(data.newItem().add(R.id.btc_ins_0,row.get("code"))
                    .add(R.id.btc_ins_1,row.get("num"))
                    .add(R.id.btc_ins_2, row.get("classificationtxt"))
            );
            Log.e(TAG,"DATA >>>>"+data);
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.stock_return_batch_list){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Log.e(TAG,"Returnleftttttttttt1111  "+position);

                ImageView img = (ImageView) v.findViewById(R.id.delete_stock);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mReturnList.remove(position);
                        Log.e(TAG,"Returnleftttttttttt  "+mReturnList);
                        initWorkList(lv);
                    }
                });
                return v;
            }
        };
        Log.e(TAG,"LIst adapter >>>>>>>");
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }


    private void findRepeat()
    {
        boolean match = false;
        Log.e("ShipActivityyyyyy", " findRepeattttt");
        for (Map<String, String> map1 : mReturnList) {
            String _b = map1.get("order_sub_id");
            String _c = map1.get("code");
            String _cla = map1.get("classification");
            String _b1 = cProductList.get("order_sub_id");
            String _c1 = cProductList.get("code");
            String _cla1 = selectedclassification;

            if (_b.equals(_b1) && _c.equals(_c1) &&  _cla.equals(_cla1)) {
                String qty = cProductList.get("quantity");
                String temp = U.plusTo(map1.get("num"),_gts(R.id.quantity));
                match = true;
                if(U.compareNumeric(qty,temp) == 1){
                    U.beepError(this,"数量は総量を超えることはできません");
                    break;
                }
                else {
                    map1.put("num",temp);
                    setProc(PROC_BARCODE);
                    _sts(R.id.barcode,"");
                    _sts(R.id.quantity,"");

                    classification.clearCheck();
                    selectedclassification="";
                    getSelectedclassificationtxt ="";
                    updateBadge1(mReturnList.size()+"");
                    break;
                }
            }
        }
        if(match == false) {
            addDatatoList();
        }
    }
    void addDatatoList()
    {
        HashMap<String,String> map =new HashMap<String,String>();
        map.put("barcode",cProductList.get("barcode"));
        map.put("order_sub_id",cProductList.get("order_sub_id"));
        map.put("code",cProductList.get("code"));
        map.put("quantity",cProductList.get("quantity"));
        map.put("classification",selectedclassification);
        map.put("classificationtxt",getSelectedclassificationtxt);

        map.put("num",_gts(R.id.quantity));
        mReturnList.add(map);
        Log.e(TAG, "Mappppp Containssss  "+map);
        setProc(PROC_BARCODE);
        _sts(R.id.barcode,"");
        _sts(R.id.quantity,"");

        classification.clearCheck();
        selectedclassification="";
        getSelectedclassificationtxt ="";
        updateBadge1(mReturnList.size()+"");
    }
    public void updateBadge1(String orderCount) {
        btnBlue.setText(orderCount);
//        totalordercount=Integer.valueOf(orderCount);
        Log.e(TAG, "updateBadge1111111111  " + orderCount);
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
                Log.e(TAG,"CODEeee============Nulllll");
                CommonDialogs.customToast(getApplicationContext(),"Network error occured");
            }
            if ("0".equals(code) == true)
            {
                Log.e("SendLogs111",code+"  "+msg+"  "+result1);
                Log.e("SendLogs111",">>>>>>>>>>>>>>>>>>>>>"+mRequestStatus);
                if(mRequestStatus == REQ_TRACK_NO) {

                   new GetReturnTracka().post(code, msg, result1, mHash, NewReturnStockActivity.this);

                } else if(mRequestStatus == REQ_ORDERID) {
                    new GetReturnOrdernew().post(code, msg, result1, mHash, NewReturnStockActivity.this);
                 } else if(mRequestStatus == REQ_BARCODE)
                    new GetReturnOrderBarcodenew().post(code,msg,result1, mHash, NewReturnStockActivity.this);

                else if(mRequestStatus == FIXED_REQ)
                {
                    U.beepFinish(this, "検品データを登録しました。");
                    nextProcess();
                }
            }else if(code.equalsIgnoreCase("1020")){
                new AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(NewReturnStockActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else{
                if(mRequestStatus == REQ_TRACK_NO) {
                    if(code.equals("14204"))
                    {

                            setProc(PROC_PRICE);

                    }
                    else U.beepError(this,msg);
                }
                else if(mRequestStatus == REQ_ORDERID) {
                     new GetReturnOrdernew().valid(code, msg, result1, mHash, NewReturnStockActivity.this);
                }else if(mRequestStatus == REQ_BARCODE)
                    new GetReturnOrderBarcodenew().valid(code,msg,result1, mHash, NewReturnStockActivity.this);

                else if(mRequestStatus == FIXED_REQ){
                    U.beepError(this, msg);
                }
            }}
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }
    public void showdialog ()
    {
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

    public void setdialog (String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(msg);
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
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
}
