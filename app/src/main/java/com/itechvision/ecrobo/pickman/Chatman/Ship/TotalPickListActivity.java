package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetPickData;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetPickRow;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.ListShops;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class TotalPickListActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.shopspinner) Spinner shopSpinner;
    @BindView(R.id.spinnerlayout) RelativeLayout spinnerLayout;
    @BindView(R.id.dateselect) EditText dateselect;
    @BindView(R.id.list)ListView lv;
    @BindView(R.id.add_layout)Button numbrbtn;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;


    SweetAlertDialog pDialog;
    protected int mProcNo = 0;

    public static final int PROC_SHOP = 1;
    public static final int PROC_DATE = 2;
    public static final int PROC_BARCODE = 3;
    public static final int PROC_QTY = 4;

    public TextView  productName;

    public static int mRequestStatus = 0;
    SharedPreferences spDomain;
    public Context mcontext = this;
    public static final String DOMAINPREFERENCE = "domain";

    private boolean showKeyboard;
    public String _lastUpdateQty = "0";

    PopupWindow popupWindow;

    ECRApplication app=new ECRApplication();
    public String adminID="",warehouse = "" ;

    public static final int REQ_INITIAL = 1;
    public static final int REQ_DATE = 2;
    public static final int REQ_BARCODE = 3;
    public static final int REQ_QTY = 4;
    public static final int REQ_PRINT = 5;

    List<Map<String, String>> PickList = new ArrayList<>();
    List<Map<String, String>> userList = new ArrayList<>();
    List<Map<String, String>> otheruserList = new ArrayList<>();

    ArrayList<String> codeList = new ArrayList<>();
    public Map<String, String> cProductList = null;

    public String shopselected ="";
    private boolean visible = false;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private TextToSpeak mTextToSpeak;
    String TAG = TotalPickListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_pick_list);
        ButterKnife.bind(TotalPickListActivity.this);

        getIDs();
        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);
        warehouse = spDomain.getString("warehouse_id",null);

        productName = (TextView)findViewById(R.id.productName);

        mTextToSpeak = new TextToSpeak(this);

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

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        dateselect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (dateselect.getRight() - dateselect.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //open calendar on touch icon
                        showTruitonDatePickerDialog(dateselect);
                        return true;
                    }
                }
                return false;
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.e(TAG, "onDateSet: mm/dd/yy: " + month + "/" + dayOfMonth + "/" + year);
                String date = "";
                String _month = "" + month;
                String _day = dayOfMonth + "";
                if (month < 10) {
                    _month = "0" + month;
                }
                if (dayOfMonth < 10) {
                    _day = "0" + dayOfMonth;
                }
                date = year +"-"+ _month +"-"+ _day;
                _sts(R.id.dateselect,date);
                inputedEvent(date);
            }
        };

        shopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                String item = (String) adapterView.getItemAtPosition(position);
                String[] shop_id = item.split(":");

                try {
                    setShopID(shop_id[0]);
                    BaseActivity.setPickshop(Integer.parseInt(shop_id[0]));
                    setProc(PROC_DATE);
                } catch (NumberFormatException e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // 選択されなかった場合
            }
        });

        if(mProcNo == 0) nextProcess();

    }

    //set title and icons on actionbar
    private void getIDs() {
        actionbarImplement(this, "Dolピック検品", " ",
                0, true, true, false);

        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        btnRed.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
//        btnGreen.setOnClickListener(this);
        relLayout1.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relLayout1:
                menu.showMenu();
                break;
            case R.id.notif_count_blue:
                List<Map<String, String>> showlist = new ArrayList<>();
                if(!userList.isEmpty())
                    showlist.addAll(userList);

                showInfo(showlist, "me");
                break;

            case R.id.notif_count_red:
                List<Map<String, String>> list = new ArrayList<>();
                if(!userList.isEmpty())
                    list.addAll(userList);

                if(!otheruserList.isEmpty())
                    list.addAll(otheruserList);

                showInfo(list, "user");
                break;

            default:
                break;
        }
    }

    int findRepeat(String val,List<Map<String, String>> list){
        for (int i =0; i< list.size(); i++){
            Map<String, String> map = new HashMap<>();
            String x = map.get("picking_no");
            if(x.equals(val)){
                return i;
            }
        }
        return -1;
    }

    @OnClick(R.id.enter)
    void enter() {
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());

        inputedEvent(s);
    }
    @OnClick(R.id.clear)
    void Clear() {
        clearEvent();
    }

//    @OnClick(R.id.update) void Update(){
//        Globals.getterList = new ArrayList<>();
//        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
//        Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
//        Globals.getterList.add(new ParamsGetter("shop_id", getShopID()));
//        Globals.getterList.add(new ParamsGetter("date_time",  _gts(R.id.dateselect)));
//        Globals.getterList.add(new ParamsGetter("app_version", getResources().getString(R.string.version)));
//
//        mRequestStatus = REQ_UPDATE;
//        new MainAsyncTask(this, Globals.Webservice.Order_picking, 1, this, "Form", Globals.getterList).execute();
//
//    }

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

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        else {
            visible = false;
            numbrbtn.setText("キーボードを表示する");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
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

    public void showTruitonDatePickerDialog(View v) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void setProc(int procNo) {
        mProcNo = procNo;

        switch (procNo) {

            case PROC_SHOP:
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.dateselect).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;

            case PROC_BARCODE:
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.dateselect).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;

            case PROC_DATE:
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.dateselect).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                _gt(R.id.quantity).setFocusableInTouchMode(true);
                showTruitonDatePickerDialog(dateselect);
                break;

            case PROC_QTY:
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.dateselect).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                if (_gts(R.id.quantity).equals("1"))
                    mTextToSpeak.startSpeaking("1");
                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned)
    {
        switch (mProcNo)
        {
            case PROC_DATE:
                String date= _gts(R.id.dateselect);

                if (date.equals("")){
                    U.beepError(this,"date can't be empty");
                    _gt(R.id.dateselect).setFocusableInTouchMode(true);
                    break;
                }
                Globals.getterList = new ArrayList<>();
                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
                Globals.getterList.add(new ParamsGetter("shop_id", getShopID()));
                Globals.getterList.add(new ParamsGetter("date_time",  _gts(R.id.dateselect)));
                Globals.getterList.add(new ParamsGetter("app_version", getResources().getString(R.string.version)));

                mRequestStatus = REQ_DATE;
                new MainAsyncTask(this, Globals.Webservice.Order_picking, 1, this, "Form", Globals.getterList).execute();

                break;

            case PROC_BARCODE:
                String barcode = _gts(R.id.barcode);
                if (barcode.equals("")){
                    U.beepError(this,"バーコードは必須です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }

                Map<String,String> map = checkList(barcode);

                if(map != null)
                {
                    if(!map.get("status").equalsIgnoreCase("Completed"))
                        sendData(map);

                    else if (map.get("status").equalsIgnoreCase("Working")){
                        if( map.get("picking_user").equalsIgnoreCase(adminID))
                            sendData(map);
                        else {
                            U.beepError(this, "バーコードは別のユーザーによって開始されます");
                            _gt(R.id.barcode).setFocusableInTouchMode(true);
                        }
                    }

                    else{
                        U.beepError(this, "バーコードはすでに完了しています");
                        _gt(R.id.barcode).setFocusableInTouchMode(true);
                    }
                }

                else
                {
                    U.beepError(this, "バーコードが一致しません");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                }
                break;

            case PROC_QTY:
                String qty = _gts(R.id.quantity);
                String barcode1 = _gts(R.id.barcode);

                if (isScaned) {

                    boolean match1 = checkBarcode(buff);
                    if (!match1) {
                        U.beepError(this, "バーコードが一致しません");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                    if(U.compareNumeric(cProductList.get("processedCnt"),cProductList.get("quantity"))==0)
                    {
                        U.beepError(this,"Qunatity cannot exceed the required quantity");
                        break;
                    }

                    if(buff.equals(barcode1)){

                        qty = U.plusTo(qty, "1");
                        String processedCnt = cProductList.get("processedCnt");
                        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                        _sts(R.id.quantity, qty);
                        if(Integer.parseInt(qty)>1)
                            mTextToSpeak.startSpeaking(qty);
                        _lastUpdateQty = _gts(R.id.quantity);

                        /* check if update in quantity need next action */
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            fixedRequest();
                        }
                    } else
                    {
                        U.beepError(this,"Barcode dont match");
                        Toast.makeText(getApplicationContext(),"BarCode Doesn't Match",Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    if ("".equals(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                    String processedCnt = cProductList.get("processedCnt");
                    String qtyUpdate = U.minusTo(qty, _lastUpdateQty);
                    String maxQty_ = U.minusTo(cProductList.get("quantity"), processedCnt);

                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0)
                    {
                        U.beepError(this, "数量が多すぎます");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }
                    else
                    {
                        cProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity")))
                        {
                            fixedRequest();

                        }
                        else
                        {
                            U.beepError(this, "数量が完全ではありません" );
                            _gt(R.id.quantity).setFocusableInTouchMode(true);
                        }
                    }
                }
                break;
        }
    }

    public String getShopID (){
        return shopselected;
    }
    public void setShopID (String shop){
        shopselected = shop;
    }


    @Override
    public void nextProcess() {

        _sts(R.id.barcode, "");
        _sts(R.id.quantity, "");
        _sts(R.id.sku,"");
        _sts(R.id.totalquantity, "");
        _stxtv(R.id.productName, "");
        _sts(R.id.dateselect, "");

        setShopID("");
        updateBadge1("0");
        updateBadge2("0");
        resetPackData();
        lv.setAdapter(null);

        Globals.getterList = new ArrayList<>();
        Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("warehouse_id",warehouse));
        Globals.getterList.add(new ParamsGetter("app_version", getResources().getString(R.string.version)));

        mRequestStatus = REQ_INITIAL;
        new MainAsyncTask(this, Globals.Webservice.shop_list, 1, TotalPickListActivity.this, "Form", Globals.getterList).execute();
    }

    public void resetPackData() {
        userList.clear();

        PickList.clear();
        otheruserList.clear();
    }

    boolean checkBarcode(String barcode){
        boolean match = false;
        for(String b: codeList)
        {
            if(barcode.equals(b))
            {
                match = true;
                break;
            }
        }
        if (!match) {
            if (barcode.equals(cProductList.get("code")))
                match = true;
        }
        return match;
    }

    public void nextWork()
    {
        cProductList.put("processedCnt", "1");
        setProc(PROC_QTY);

        mTextToSpeak.resetQueue();
        _lastUpdateQty = _gts(R.id.quantity);

        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
            fixedRequest();
        }
    }

    private void fixedRequest()
    {
        Globals.getterList = new ArrayList<>();
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
        Globals.getterList.add(new ParamsGetter("shop_id", getShopID()));
        Globals.getterList.add(new ParamsGetter("date_time",  _gts(R.id.dateselect)));
        Globals.getterList.add(new ParamsGetter("mode",  "picked"));
        Globals.getterList.add(new ParamsGetter("product_id",  cProductList.get("product_id")));
        Globals.getterList.add(new ParamsGetter("quantity",   _gts(R.id.quantity)));
        Globals.getterList.add(new ParamsGetter("psh_id",    cProductList.get("psh_id")));
        Globals.getterList.add(new ParamsGetter("app_version", getResources().getString(R.string.version)));

        mRequestStatus = REQ_QTY;
        new MainAsyncTask(this, Globals.Webservice.Order_picking, 1, this, "Form", Globals.getterList).execute();
    }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
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
            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, buff);
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
                // check for QR code

                if (BaseActivity.getUrl().equals("https://air-logi-st.air-logi.com/service") && BaseActivity.getShopId().equals("1011")) {
                    String result = "";
                    if (barcode.length() == 13) {
                        result = barcode.substring(0, barcode.length() - 1);
                        barcode = result;
                    }
                    else if (barcode.length() == 14) {
                        result = barcode.substring(1, barcode.length() - 1);
                        barcode = result;
                    }
                }
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode;

                _sts(R.id.barcode, barcode);
            }


            if (mProcNo == PROC_QTY) {
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode;

            }

        }
        this.inputedEvent(barcode, true);
    }

    public void currLineData(Map<String, String> data,ArrayList<String> list){
        cProductList = data;
        codeList = list;

    }

    public void setListData(List<Map<String, String>> list,List<Map<String, String>> list2,
                            List<Map<String, String>> list3){
        PickList = list;
        userList = list2;
        otheruserList = list3;
        initWorkList();
    }

    protected void initWorkList() {

        lv.setAdapter(null);
        final int statusArray[] = new int[PickList.size()];


        ListViewItems data = new ListViewItems();
        for (int i = 0; i <=PickList.size() - 1; i++) {
            Map<String, String> row = PickList.get(i);
            Log.e(TAG," initWorkList_"+row);

            if(row.get("status").equals("Completed"))
                row.put("text","Done");

            else if(row.get("status").equals("Untouched"))
                row.put("text","Start");

            else if(row.get("status").equals("Working"))
                row.put("text","Working");

            Log.e(TAG," initWorkList1111111111_"+row);


            data.add(data.newItem().add(R.id.row,row.get("picking_no"))
                    .add(R.id.sku_val,row.get("code"))
                    .add(R.id.pname,row.get("name"))
                    .add(R.id.qty,row.get("quantity"))
                    .add(R.id.start_row,row.get("text")));

            if(row.get("text").equalsIgnoreCase("Start"))
                statusArray[i]= R.color.green_color;

            else if(row.get("text").equalsIgnoreCase("Done"))
                statusArray[i]= R.color.light_red;

            else  if(row.get("text").equalsIgnoreCase("Working"))
                statusArray[i]= R.color.yellow;



            Log.e(TAG,"DATA >>>>  "+i+ "     "+statusArray[i]);

        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.total_pick_list_data){

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TextView start = (TextView) v.findViewById(R.id.start_row);
                start.setBackground(getResources().getDrawable(statusArray[position]));

                return v;
            }
        };

        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0,  true);
    }

    public void updateBadge1(String orderCount) {
        setBadge1(Integer.valueOf(orderCount));
    }

    public void updateBadge2(String qtyCount)
    {
        setBadge2(Integer.valueOf(qtyCount));
    }

    public Map<String,String> checkList(String barcode){

        for(Map<String, String> map: PickList){
            if(map.get("barcode").equalsIgnoreCase(barcode)){
                return (map);
            }

            else if(map.get("code").equalsIgnoreCase(barcode))
                return (map);
        }
        return null;
    }


    void sendData(Map<String, String> map)
    {
        String id = map.get("product_id");


        Globals.getterList = new ArrayList<>();
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
        Globals.getterList.add(new ParamsGetter("shop_id", getShopID()));
        Globals.getterList.add(new ParamsGetter("date_time",  _gts(R.id.dateselect)));
        Globals.getterList.add(new ParamsGetter("mode", "start"));
        Globals.getterList.add(new ParamsGetter("product_id", id));
        Globals.getterList.add(new ParamsGetter("app_version", getResources().getString(R.string.version)));

        mRequestStatus = REQ_BARCODE;
        new MainAsyncTask(TotalPickListActivity.this, Globals.Webservice.Order_picking, 1, TotalPickListActivity.this, "Form", Globals.getterList).execute();

    }


    protected boolean showInfo(List<Map<String,String>> list, String status){
        Log.e(TAG,"showInfooooooo");
        List<Map<String,String>> showList =list;
        if (showList.isEmpty()) {
            Log.e(TAG,"showInfoo  mPickingOrderList===000");
            return false;
        }
        else {
            if (getPopupWindow() == null) {
                Log.e(TAG,"showInfoo  popupwindow");
                popupWindow = new PopupWindow(this);
                // レイアウト設定
                View popupView = getLayoutInflater().inflate(R.layout.total_pick_popup_list, null);
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

                ImageView close =(ImageView)getPopupWindow().getContentView().findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
            TextView title =(TextView)getPopupWindow().getContentView().findViewById(R.id.title);
            TextView name_tag =(TextView)getPopupWindow().getContentView().findViewById(R.id.name_tag);

            ListView lv = (ListView) getPopupWindow().getContentView().findViewById(R.id.orderPicking);


            Log.e(TAG,"Statussss   "+status );
            if (status.equals("user")) {
                title.setText("All user working List");
                name_tag.setText("Name");
                initList(lv,status,showList);
            }
            else if(status.equals("me")){
                title.setText("My List");
                name_tag.setText("Status");
                initMyList(lv,status,showList);

            }


            // 画面中央に表示
            getPopupWindow().showAtLocation(findViewById(R.id.barcode), Gravity.CENTER, 0, 32);
            return true;
        }}

    protected ListViewItems initList(ListView lv,String status,List<Map<String,String>> showList) {
        Log.e(TAG,"initListtttttt");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (int i = 0 ;i < showList.size(); i++) {
            Map<String, String> row = showList.get(i);


            Log.e(TAG,"initListtttttt11111");

            data.add(data.newItem().add(R.id.rf_txt_1, row.get("picking_no"))
                    .add(R.id.rf_txt_2, row.get("code"))
                    .add(R.id.rf_txt_3, row.get("picking_user_name"))
            );
        }

        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.total_pick_list_row) ;
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }


    protected ListViewItems initMyList(ListView lv, String status, final List<Map<String,String>> showList) {
        Log.e(TAG,"initListtttttt");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (int i = 0 ;i < showList.size(); i++) {
            Map<String, String> row = showList.get(i);

            Log.e(TAG,"initListtttttt11111");
            if(row.get("quantity").equals("0")) {

                data.add(data.newItem().add(R.id.rf_txt_1, row.get("picking_no"))
                        .add(R.id.rf_txt_2, row.get("code"))
                        .add(R.id.rf_txt_3, "Done"));
            }
            else
                data.add(data.newItem().add(R.id.rf_txt_1, row.get("picking_no"))
                        .add(R.id.rf_txt_2, row.get("code"))
                        .add(R.id.rf_txt_3, "Working"));

        }

        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.total_pick_list_row)
        {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                final TextView reprint = (TextView) v.findViewById(R.id.rf_txt_3);
                if(reprint.getText().equals("Done")){
                    reprint.setBackground(getResources().getDrawable(R.color.yellow));
                }
                else
                    reprint.setBackground(getResources().getDrawable(R.color.white));


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

//    void sendRequest(Map<String, String> map)
//    {
//        Globals.getterList = new ArrayList<>();
//        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
//        Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
//        Globals.getterList.add(new ParamsGetter("shop_id", getShopID()));
//        Globals.getterList.add(new ParamsGetter("date_time",  _gts(R.id.dateselect)));
//        Globals.getterList.add(new ParamsGetter("mode",  "print"));
//        Globals.getterList.add(new ParamsGetter("product_id",  map.get("product_id")));
//        Globals.getterList.add(new ParamsGetter("app_version", getResources().getString(R.string.version)));
//
//        if(BaseActivity.getPrinterSelected())
//            if(BaseActivity.getBarcodeSlipselectedPrinterPOS()>0)
//                Globals.getterList.add(new ParamsGetter("printer_id", BaseActivity.getBarcodeSlipselectedPrinterID()));
//
//        mRequestStatus = REQ_PRINT;
//        new MainAsyncTask(this, Globals.Webservice.Order_picking, 1, this, "Form", Globals.getterList).execute();
//    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.quantity, barcode);
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
            result1 = map1.getJsonArrayOrNull("results");
            if (code == null) {
                Log.e(TAG, "CODEeee============Nulllll");
                CommonDialogs.customToast(getApplicationContext(), "Network error occured");

            }
            if ("0".equals(code) == true) {

                if (mRequestStatus == REQ_DATE ) {
                    new GetPickData().post(code, msg, result1, mHash, this);
                }
                else if (mRequestStatus == REQ_PRINT) {
                    U.beepKakutei(this,null);
                }

                else if (mRequestStatus == REQ_BARCODE) {
                    new GetPickRow().post(code, msg, result1, mHash, this);
                }
                else if (mRequestStatus == REQ_QTY) {
                    new GetPickRow().post(code, msg, result1, mHash, this);
                }
                else if (mRequestStatus == REQ_INITIAL)
                {
                    JsonHash row = (JsonHash) result1.get(0);
                    JsonArray list ;
                    if(row.containsKey("shop_data")){
                        list = row.getJsonArrayOrNull("shop_data");

                        new ListShops().post(code, msg, list,  this);
                    }
                    else U.beepError(this,"No shop data found");
                }

            }else if(code.equalsIgnoreCase("1020")){

                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(TotalPickListActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            } else{
                if (mRequestStatus == REQ_DATE ) {
                    new GetPickData().valid(code, msg, result1, mHash, this);
                }
                else if (mRequestStatus == REQ_BARCODE) {
                    new GetPickRow().valid(code, msg, result1, mHash, this);
                } else if (mRequestStatus == REQ_QTY) {
                    new GetPickRow().valid(code, msg, result1, mHash, this);
                } else if (mRequestStatus == REQ_INITIAL) {
                    new ListShops().valid(code, msg, result1,  this);
                }
                else if (mRequestStatus == REQ_PRINT) {
                    U.beepError(this,msg);
                }
            }

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }
}

