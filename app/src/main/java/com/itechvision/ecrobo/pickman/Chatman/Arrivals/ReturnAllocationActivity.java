package com.itechvision.ecrobo.pickman.Chatman.Arrivals;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetReturn;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetStockReturn;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.NewReturnClassification.GetClassificationResult;
import com.itechvision.ecrobo.pickman.Models.NewReturnClassification.GetReturnClassificationReq;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class ReturnAllocationActivity extends BaseActivity implements View.OnClickListener, MainAsynListener, DataManager.GetReturnClassificationCallback{
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(id.actionbar)
    ActionBar actionbar;
    @BindView(id.add_layout) Button numbrbtn;
    @BindView(id.classificationspinner) Spinner classification;
    @BindView(id.layout_number) ViewGroup layout;
    @BindView(id.layout_main) RelativeLayout mainlayout;
    @BindView(id.lotno) EditText lot;
    @BindView(id.expiration) EditText exp;
    @BindView(id.barcode) EditText barcode;
    @BindView(id.listLocations) ListView locationlistView;
    @BindView(id.pck_0) TextView pck0;
    @BindView(id.pck_1) TextView pck1;
    @BindView(id.pck_2) TextView pck2;
    @BindView(id.pck_3) TextView pck3;

    @BindView(id.goodproduct)
    RadioButton goodproduct;
    @BindView(id.badproduct) RadioButton badproduct;
    @BindView(id.ll_classification) LinearLayout ll_classification;

    public LinearLayout lotlayout,explayout;
    public TextToSpeak mTextToSpeak;
    ECRApplication app=new ECRApplication();
    String adminID="",Clasificationstatus="";
    public static String Classificationtag="0";
    public static String Good="",Bad="" ;

    protected ArrayList<String> classificationIdArray = new ArrayList<String>();
    private ArrayList<HashMap<String, String>> allocationlist = new ArrayList<>();
    private ArrayList<HashMap<String, String>> yloclist = new ArrayList<>();
    private ArrayList<HashMap<String, String>> loclist = new ArrayList<>();

    public String classificationId = null;

    public boolean lotexist = false;
    public static int lotpress = 0;

    public TextView productName;

    private boolean orderRequestSettings;

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    public Context mcontext=this;

    private boolean visible = false;
    private boolean showKeyboard;


    int mSelectedItem = 0;

    protected int mProcNo = 0;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_QTY = 2;
    public static final int PROC_LOCATION = 3;

    public static int mRequestStatus =0;

    public static final int REQ_INITIAL = 1;
    public static final int REQ_BARCODE = 2;
    public static final int REQ_QTY = 3;
    public static final int PROC_LOT_NO = 4;
    public static final int REQ_CLASSIFICATION = 5;


    View dialogView;
    AlertDialog alertDialog;
    public ListView listview;
    ListViewAdapter adapter;

    // for fixed location barcodes
    public static String fixed_location = "";  // to get flag that location fixed or not
    public static String location_fixed = "";  // to store location

    String TAG= ReturnAllocationActivity.class.getSimpleName();
    DataManager.GetReturnClassificationCallback getcassification ;
    DataManager manager;
    progresBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_allocation);

        ButterKnife.bind(ReturnAllocationActivity.this);
        getcassification = this;

        getIDs();

        Log.d(TAG,"On Create ");
        manager= new DataManager();
        progress = new progresBar(this);



        ViewPager viewPager = (ViewPager) findViewById(id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        productName = (TextView)findViewById(id.product_name) ;

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);

        lotlayout = (LinearLayout) findViewById(id.gridlot);
        explayout = (LinearLayout) findViewById(id.gridExpiration);

        orderRequestSettings = BaseActivity.getLotPress();
        if (orderRequestSettings == true) {
            lotlayout.setVisibility(View.VISIBLE);
            explayout.setVisibility(View.VISIBLE);
        }
        else  {
            lotlayout.setVisibility(View.GONE);
            explayout.setVisibility(View.GONE);
        }

        showKeyboard= BaseActivity.getaddKeyboard();



        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(id.layout_number);
            visible = true;

            numbrbtn.setText(R.string.hideKeyboard);
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(270);
            int top = convert(10);
            int btm= convert(20);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,btm);
            Log.e("MoveActivity","SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);

        if (mProcNo == 0) nextProcess();


        classification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (classificationIdArray != null && classificationIdArray.size() > position) {
                    setclassificationId(classificationIdArray.get(position));
                    Log.e(TAG, "Selected Classification " + classificationIdArray.get(position));
                }
                else {
                    setclassificationId("");
                    Log.e(TAG, "Selected Classification" + getclassificationId() + "null");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setclassificationId("");
                Log.e("Arrival Activity","Selected Classification"+ getclassificationId() +"null");
            }
        });

     /*   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


    }


    @Override
    protected void onResume() {
        super.onResume();
        Good="";
        Bad="";
        Classificationtag ="0";
        progress.Show();
        GetReturnClassificationReq req = new GetReturnClassificationReq(app.getSerial(), BaseActivity.getShopId(), adminID, "");
        manager.GetReturnClassification(req, getcassification);

    }

    //set title and icons on actionbar
    private void getIDs() {

        actionbarImplement(this, "返品棚入れ", " ",
                0, false,false,false );

        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
    }

    @OnClick(id.ll_listdialog)
    void click() {
        if (yloclist.size() != 0) {
            listviewDialog();
        }
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

    public void AddLayout(View view)
    {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(id.layout_number);
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
            Log.e("MoveActivity","SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        else
        {
            visible = false;
            numbrbtn.setText("キーボードを表示する");
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e("MoveActivity","SetlayoutMarginnnnn");
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
    public String getclassificationId() {
        return this.classificationId;
    }

    public void setclassificationId(String id) {
        this.classificationId= id;
    }

    public void setclassificationIdArray(ArrayList arr) {
        this.classificationIdArray = arr;
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {
            case PROC_BARCODE:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.location).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                locationlistView.setAdapter(null);
                _gt(id.barcode).setFocusableInTouchMode(true);

                break;
            case PROC_QTY:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.location).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setFocusableInTouchMode(true);

                break;
            case PROC_LOCATION:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.location).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.location).setFocusableInTouchMode(true);

                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        String lot = "";
        switch (mProcNo) {
            case PROC_BARCODE:	// バーコード

                locationlistView.setAdapter(null);
                setKey(1);
                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+ BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode",_gts(id.barcode)));
                Globals.getterList.add(new ParamsGetter("return_class",Classificationtag));

                mRequestStatus = REQ_BARCODE;
                new MainAsyncTask(this, Globals.Webservice.getReturn, 1, ReturnAllocationActivity.this, "Form", Globals.getterList,true).execute();


                break;

            case PROC_LOT_NO:    // バーコード
                lot = _gts(id.lotno);
                if (lot.equals("") || lot.equals("0")) {
                    U.beepError(this, "ロット番号が必要");
                    break;
                }
                setKey(4);
                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+ BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode",_gts(id.barcode)));
                Globals.getterList.add(new ParamsGetter("lot_no",lot));

                mRequestStatus = REQ_BARCODE;

                new MainAsyncTask(this, Globals.Webservice.getAllocation, 1, ReturnAllocationActivity.this, "Form", Globals.getterList,true).execute();
                break;
            case PROC_QTY: // 数量
                String quantity = _gts(id.quantity);
                String barcode = _gts(id.barcode);
                if(quantity.equalsIgnoreCase(""))
                {
                    quantity= "0";
                }
                if (isScaned) {
                    if (buff.equals(barcode)) {
                        U.beepSuccess();
                        String val = U.plusTo(quantity, "1");
                        _sts(id.quantity, val);
                        mTextToSpeak.startSpeaking(val);
                        break;
                    } else {
                        U.beepError(this, "Barcode dont match");
                        Log.e("NewArrivalActivity", "InputedEventQuantityyyyyyyyyyyyySUCCESS");
                        break;
                    }
                }
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
                }

                else {
                    U.beepSuccess();
                    setProc(PROC_LOCATION);
                }
                break;
            case PROC_LOCATION: // ロケーション
                String loc = _gts(id.location);
                String lotno = "";
                String expiration ="";
                if (orderRequestSettings == true) {
                    lotno = _gts(id.lotno);
                    expiration = _gts(id.expiration);
                }

                if ("".equals(loc)) {
                    U.beepError(this, "ロケーションは必須です");
                    _gt(id.location).setFocusableInTouchMode(true);
                    break;
                }
                Log.e(TAG, "PROC location>>>>>>>>>>>>>");
                if(fixed_location.equals("1") && !loc.equals(location_fixed) && !location_fixed.equals("")){
                    U.beepError(this, "固定ロケーションの品番に複数のロケーションを登録することは出来ません");
                    _gt(id.location).setFocusableInTouchMode(true);
                    break;
                }
                stopTimer();

                String classification = "";
                classification  =getclassificationId();
                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+ BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode",_gts(id.barcode)));
                Globals.getterList.add(new ParamsGetter("qty", _gts(id.quantity)));
                Globals.getterList.add(new ParamsGetter("stock_type_id",classification));
                Globals.getterList.add(new ParamsGetter("source", "y"));
                Globals.getterList.add(new ParamsGetter("destination", loc));
                Globals.getterList.add(new ParamsGetter("product_id", yloclist.get(mSelectedItem).get("product_id")));
                Globals.getterList.add(new ParamsGetter("product_stock_id", yloclist.get(mSelectedItem).get("id")));
                Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));

                mRequestStatus = REQ_QTY;

                new MainAsyncTask(this, Globals.Webservice.self_put, 1, this, "Form", Globals.getterList, true).execute();

                break;
        }
    }

    @Override
    protected void onDestroy() {
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();
        Classificationtag="0";
    }



    @Override
    public void inputedEvent(String buff) {
        Log.e("ReturnAllocationAct","inputedEventtttt");
        inputedEvent(buff, false);
    }

    public static void setKey(int poc) {
        lotpress = poc;
    }

    public static int getKey() {
        return lotpress;
    }
    public void getClassificationList(){

        if (GetReturn.Returnclass.equals("1")){
            ll_classification.setVisibility(View.VISIBLE);
        }else{
            ll_classification.setVisibility(View.GONE);
        }

        Globals.getterList = new ArrayList<>();
        Globals.getterList.add(new ParamsGetter("admin_id",adminID));

        mRequestStatus = REQ_CLASSIFICATION;

        new MainAsyncTask(this, Globals.Webservice.listStockIDs, 1, ReturnAllocationActivity.this, "Form", Globals.getterList,true).execute();

    }

    @Override
    public void nextProcess() {
        _sts(id.barcode, "");
        _sts(id.quantity, "");
        _sts(id.location, "");
        _sts(id.product_code,"");
        _stxtv(id.product_name, "");
        _sts(id.totalquantity,"");
        _sts(id.standard,"");
        classification.setAdapter(null);
        setProc(PROC_BARCODE);
        classificationIdArray.clear();
        Good ="0";
        Bad="0";

      /*Classificationtag = "0";
        goodproduct.performClick();*/

        if (orderRequestSettings == true){
            _sts(id.lotno, "");
            _sts(id.expiration, "");
            explayout.setVisibility(View.VISIBLE);
            lotlayout.setVisibility(View.VISIBLE);}
        else { lotlayout.setVisibility(View.GONE);
            explayout.setVisibility(View.GONE);}

        locationlistView.setAdapter(null);
        if (!loclist.isEmpty()) {
            loclist.clear();
            yloclist.clear();
        }

        pck0.setText("");
        pck1.setText("");
        pck2.setText("");
        pck3.setText("");

        if(showKeyboard==false) {
            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
//			Animation bottomDown = AnimationUtils.loadAnimation(this,
//					R.anim.bottom_down);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
//			hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
            Log.e("MoveActivity", "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
        fixed_location = "";
        location_fixed= "";

        progress.Show();
        GetReturnClassificationReq req = new GetReturnClassificationReq(app.getSerial(), BaseActivity.getShopId(), adminID, "");
        manager.GetReturnClassification(req, getcassification);
    }

    @Override
    public void clearEvent() {
        //U.beepSuccess();
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
    }
    public void setAllocationlist(ArrayList<HashMap<String, String>> data) {
        allocationlist = data;
        Log.e(TAG, "List becomess " + allocationlist);
    }

    public void setYAllocationlist(ArrayList<HashMap<String, String>> data) {
        yloclist = data;
        Log.e(TAG, "List becomess " + yloclist);

        _sts(id.lotno, yloclist.get(0).get("lot"));
        _sts(id.expiration, yloclist.get(0).get("expiration"));
        _sts(id.totalquantity, yloclist.get(0).get("quantity"));

        pck0.setText(yloclist.get(0).get("location"));
        pck1.setText(yloclist.get(0).get("lot"));
        pck2.setText(yloclist.get(0).get("expiration"));
        pck3.setText(yloclist.get(0).get("quantity"));

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

        if (yloclist.size() > 1)
            listviewDialog();
    }

    public void setlocationlist(ArrayList<HashMap<String, String>> data) {
        loclist = data;
        Log.e(TAG, "List becomess  LocList " + loclist);
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
            case PROC_BARCODE:	// バーコード
                _sts(id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                _sts(id.quantity, buff);
                break;
            case PROC_LOCATION: //
                _sts(id.location, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (!MainAsyncTask.dialogBox.isShowing()) {
            if (!barcode.equals(""))
            {
                if (mProcNo == PROC_BARCODE)
                {
                    Log.e(TAG,"Scanned Event url selcted  "+ BaseActivity.getUrl()+ "     Shop selected is  "+ BaseActivity.getShopId());
                    String finalbarcode = CommonFunctions.getBracode(barcode);
                    barcode =finalbarcode;
                    _sts(id.barcode, barcode);
                }
                if (mProcNo == PROC_QTY)
                {
                    Log.e(TAG,"Scanned Event url selcted  "+ BaseActivity.getUrl()+ "     Shop selected is  "+ BaseActivity.getShopId());
                    String finalbarcode = CommonFunctions.getBracode(barcode);
                    barcode =finalbarcode;
                    Log.e(TAG,"barcode111   "+barcode);
                }
                else if (mProcNo == PROC_LOCATION) _sts(id.location, barcode);
            }
            this.inputedEvent(barcode, true);
        }
        else
            Toast.makeText(getApplicationContext(), "Please Wait", Toast.LENGTH_SHORT).show();
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
            case PROC_QTY: // 数量
                if ("".equals(barcode)) {
                } else
                    _sts(id.quantity, barcode);
                break;
            case PROC_LOCATION: //
                _sts(id.location, barcode);
                break;
        }
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
            public void onClick(View v)
            {
                Log.e(TAG, "asdjvn   1111111  ");
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

        for (int i = 0; i <= yloclist.size() - 1; i++) {
            Map<String, String> row = yloclist.get(i);

            if (BaseActivity.getLotPress()) {
                data.add(data.newItem().add(id.arr_all_0, row.get("location"))
                        .add(id.arr_all_2, row.get("lot"))
                        .add(id.arr_all_1, row.get("expiration"))
                        .add(id.arr_all_3, row.get("quantity"))
                        .add(id.arr_all_4, row.get("return_class_name")));
            } else {
                data.add(data.newItem().add(id.arr_all_0, row.get("location"))
                        .add(id.arr_all_3, row.get("quantity")));
            }
            used.add(i);
        }

        adapter = new ListViewAdapter(
                getApplicationContext(), data, R.layout.list_allocation_row_one) {
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

                _sts(R.id.lotno, yloclist.get(position).get("lot"));
                _sts(R.id.expiration, yloclist.get(position).get("expiration"));
                _sts(R.id.totalquantity, yloclist.get(position).get("quantity"));

                pck0.setText(yloclist.get(position).get("location"));
                pck1.setText(yloclist.get(position).get("lot"));
                pck2.setText(yloclist.get(position).get("expiration"));
                pck3.setText(yloclist.get(position).get("quantity"));

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
                Log.e(TAG,"CODEeee====Null");
                CommonDialogs.customToast(getApplicationContext(),"Network error occured");
            }
            if ("0".equals(code) == true) {
                Log.e("SendLogs111",code+"  "+msg+"  "+result1);

                if(mRequestStatus == REQ_BARCODE) {
                    JsonArray yloc = new JsonArray();
                    JsonArray loc = new JsonArray();
                    if (map1.containsKey("y_location")) {
                        yloc = map1.getJsonArrayOrNull("y_location");
                        loc = map1.getJsonArrayOrNull("without_y_location");
                    }

                    new GetReturn().post(code, msg, result1, yloc, loc, mHash, ReturnAllocationActivity.this);
                }
                else if(mRequestStatus == REQ_QTY) {
                    U.beepKakutei(this, "設定を登録しました");
                    nextProcess();
                } else if(mRequestStatus == REQ_CLASSIFICATION) {
                    new GetStockReturn().post(code,msg,result1, mHash, ReturnAllocationActivity.this);
                }
            }else if(code.equalsIgnoreCase("1020")){
                new AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(ReturnAllocationActivity.this, LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();
                                dialog.dismiss();
                            }
                        })
                        .show();
            } else
            if(mRequestStatus == REQ_BARCODE)
                new GetReturn().valid(code,msg,result1, mHash, ReturnAllocationActivity.this);

            else if(mRequestStatus == REQ_QTY){
                U.beepError(this, msg);
            }
            else if(mRequestStatus == REQ_CLASSIFICATION) {
                new GetStockReturn().valid(code,msg,result1, mHash, ReturnAllocationActivity.this);
            }
        }
        catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }


    @OnClick(R.id.goodproduct) void good(){
        Classificationtag = "0";
        badproduct.setChecked(false);
        goodproduct.setChecked(true);
        if (barcode.getText().toString().equals("")) {

        } else{
            Globals.getterList = new ArrayList<>();
            Globals.getterList.add(new ParamsGetter("admin_id",adminID));
            Globals.getterList.add(new ParamsGetter("barcode",_gts(id.barcode)));
            Globals.getterList.add(new ParamsGetter("return_class",Classificationtag));

            mRequestStatus = REQ_BARCODE;
            new MainAsyncTask(this, Globals.Webservice.getReturn, 1, ReturnAllocationActivity.this, "Form", Globals.getterList,true).execute();
        }

        if (classificationIdArray.size()>0){
            if (!Good.equals("0")) {
                int pos = ClasificationPostion(Good);
                classification.setSelection(pos);
            }
        }

    }

    @OnClick(R.id.badproduct) void badd(){
        Classificationtag = "1";
        goodproduct.setChecked(false);
        if (barcode.getText().toString().equals("")){

        }else{
            Globals.getterList = new ArrayList<>();
            Globals.getterList.add(new ParamsGetter("admin_id",adminID));
            Globals.getterList.add(new ParamsGetter("barcode",_gts(id.barcode)));
            Globals.getterList.add(new ParamsGetter("return_class",Classificationtag));

            mRequestStatus = REQ_BARCODE;
            new MainAsyncTask(this, Globals.Webservice.getReturn, 1, ReturnAllocationActivity.this, "Form", Globals.getterList,true).execute();

        }

        if (classificationIdArray.size()>0){
            if (!Bad.equals("0")) {
                int pos = ClasificationPostion(Bad);
                classification.setSelection(pos);
            }
        }
    }

    @Override
    public void onSucess(int status, GetClassificationResult message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("0")){
            Clasificationstatus= message.getReturn_class();
            if (message.getReturn_class().equals("1")){
                ll_classification.setVisibility(View.VISIBLE);
                Good= message.getClassification_g();
                Bad= message.getClassification_b();
            }else{
                ll_classification.setVisibility(View.GONE);
                Good = "0";
                Bad="0";
            }

        }
    }

    @Override
    public void onError(int status, ResponseBody error) { progress.Dismiss(); }

    @Override
    public void onFaliure() { progress.Dismiss();}

    @Override
    public void onNetworkFailure() { progress.Dismiss(); }


    public int ClasificationPostion(String val){
        for(int i=0;i<=classificationIdArray.size();i++){

            if (classificationIdArray.get(i).equals(val)){
                return  i;
            }

        }
        return 0;
    }


    @Override
    protected void onStop() {
        super.onStop();
        classification.setAdapter(null);
        classificationIdArray.clear();
    }
}
