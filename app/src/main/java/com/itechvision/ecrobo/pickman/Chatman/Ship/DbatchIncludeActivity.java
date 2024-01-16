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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetDbatchIncludeTime;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetIncludeBracode;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetTimelist;
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

public class DbatchIncludeActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.date) EditText datedt;
    @BindView(R.id.timespinner) Spinner timeSpinner;
    @BindView(R.id.spinnerlayout) RelativeLayout spinnerlayout;
    @BindView(R.id.listView) ListView lv;
    @BindView(R.id.btnBack) Button back;

    protected Map<String, String> cProductList = null;

    public String _lastUpdateQty = "0";

    private boolean showKeyboard;
    private TextToSpeak mTextToSpeak;

    protected int mProcNo = 0;
    public static final int PROC_DATE =1;
    public static final int PROC_TIME =2;
    public static final int PROC_BARCODE =3;
    public static final int PROC_QTY= 4;
    public static final int PROC_TRAYNO= 5;


    public static int mRequestStatus =0;

    public static final int REQ_DATE = 1;
    public static final int REQ_TIME = 2;
    public static final int REQ_BARCODE = 3;
    public static final int REQ_TRAY = 4;


    private DatePickerDialog.OnDateSetListener mDateSetListener;
    protected ArrayList<String> timeList = new ArrayList<String>();
    List<Map<String, String>> productsList = new ArrayList<>();

    public Context mcontext=this;
    SharedPreferences spDomain;

    public static final String DOMAINPREFERENCE = "domain";

    ECRApplication app=new ECRApplication();
    String adminID="";
    private boolean visible = false;

    String TAG= DbatchIncludeActivity.class.getSimpleName();

    String batchid ="",batchno="",createdate ="";
    String timeselected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbatch_include);
        ButterKnife.bind(this);
        getIDs();
        Log.d(TAG,"On Create ");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        showKeyboard=BaseActivity.getaddKeyboard();

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);

        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
            visible = true;
            numbrbtn.setText(R.string.hideKeyboard);
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
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        mTextToSpeak = new TextToSpeak(this);
        Log.e(TAG, "OnCreateeeeee");

        Intent i = getIntent();
        if (i.hasExtra("batch_no")){
            batchid = i.getStringExtra("batch_id");
            batchno = i.getStringExtra("batch_no");
            createdate = i.getStringExtra("create_date");
        }
          if(batchid.equals("")){
            back.setVisibility(View.GONE);
          }
          int right = convert(10);
          datedt.setPadding(0,0,right,0);
        datedt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (datedt.getRight() - datedt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //open calendar on touch icon
                        showTruitonDatePickerDialog(datedt);
                        return true;
                    }
                }
                return false;
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = "";
                String _month=""+month;
                String _day=day+"";
                if(month < 10){
                    _month = "0" + month;
                }
                if(day < 10){

                    _day  = "0" + day ;
                }
                date = year+"-"+_month+"-"+_day;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + date);
                datedt.setText(date);
                inputedEvent(date,false);
            }
        };
        _gt(R.id.totalQuantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(mProcNo>0 && position>0){
                   timeselected = timeList.get(position);
                   if(mProcNo == PROC_TIME){
                       enter();
                   }


              }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(mProcNo == 0) nextProcess();
    }


    private void getIDs() {

        actionbarImplement(this, "チラシピック", " ",
                0, false,false,false,false);

        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Log.e(TAG,">>>>>>>>>>>>>>"+getBadge3());
        switch (v.getId()) {
            case R.id.relLayout1:
                menu.showMenu();
                break;
            case R.id.notif_count_blue:
                Log.e(TAG,">>>>>>>>>>>>>>11111111111111111111111"+getBadge1());
                if(getBadge1()!= 0){
                    showInfo();
                }
                break;

            default:
                break;
        }
    }
    public void Back(View view){
        Intent i = new Intent(this, BatchBoxActivity.class);
        i.putExtra("batch_no",batchno );
        i.putExtra("batch_id",batchid );
        i.putExtra("create_date", createdate);
        startActivity(i);
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
            numbrbtn.setText("キーボードを表示する");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG,"SetlayoutMarginnnnn");
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

    @OnClick(R.id.enter)void enter(){
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        int right = convert(10);
        int left = convert(10);
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {
            case PROC_DATE:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                _gt(R.id.date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                datedt.setPadding(left,0,right,0);
                _gt(R.id.date).setFocusableInTouchMode(true);
                spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.trayno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
            case PROC_TIME:
                _gt(R.id.date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                datedt.setPadding(left,0,right,0);
                spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerlayout.setFocusableInTouchMode(true);
                _gt(R.id.trayno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
            case PROC_BARCODE:
                _gt(R.id.date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                datedt.setPadding(left,0,right,0);
                spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);

                _gt(R.id.trayno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
            case PROC_QTY:
                _gt(R.id.date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                datedt.setPadding(left,0,right,0);
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.trayno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                if (_gts(R.id.quantity).equals("")) _sts(R.id.quantity, "1");
                break;
            case PROC_TRAYNO:
                _gt(R.id.date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                datedt.setPadding(left,0,right,0);
                _gt(R.id.trayno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.trayno).setFocusableInTouchMode(true);
                spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {
            case PROC_DATE:
                String date = _gts(R.id.date);

                if ("".equals(date)) {
                    U.beepError(this, "任意の日付を入力してください");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }

                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("create_date", date));

                mRequestStatus = REQ_DATE;

                new MainAsyncTask(this, Globals.Webservice.getTimeBatch, 1, DbatchIncludeActivity.this, "Form", Globals.getterList,true).execute();
                break;
            case PROC_TIME:
                timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(mProcNo>0 && position>0){
                            timeselected = timeList.get(position);
                            enter();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("create_date", _gts(R.id.date)));
                Globals.getterList.add(new ParamsGetter("create_time", timeselected));

                mRequestStatus = REQ_TIME;

                new MainAsyncTask(this, Globals.Webservice.getTimeBatch, 1, DbatchIncludeActivity.this, "Form", Globals.getterList,true).execute();
                break;

            case PROC_BARCODE:    // バーコード
                String barcode1 = _gts(R.id.barcode);

                if ("".equals(barcode1)) {
                    U.beepError(this, "バーコードが必要です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);

                    break;
                }

                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("create_date", _gts(R.id.date)));
                Globals.getterList.add(new ParamsGetter("create_time", timeselected));
                Globals.getterList.add(new ParamsGetter("barcode", barcode1));

                mRequestStatus = REQ_BARCODE;

                new MainAsyncTask(this, Globals.Webservice.getTimeBatch, 1, DbatchIncludeActivity.this, "Form", Globals.getterList,true).execute();
                break;
            case PROC_QTY: // 数量
                String qty = _gts(R.id.quantity);
                if(qty.equals(""))
                    qty="1";
                String barcode = _gts(R.id.barcode);

                Log.e(TAG,"Qtyyyyy  "+qty);
                Log.e(TAG, "buff " + buff);

                if (isScaned) {

                    if(U.compareNumeric(cProductList.get("processedCnt"),cProductList.get("quantity"))==0)
                    {
                        U.beepError(this,"Qunatity cannot exceed the required quantity");
                        break;
                    }
                    if(buff.equals(barcode)){

                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned");
                        qty = U.plusTo(qty, "1");
                        String processedCnt = cProductList.get("processedCnt");
                        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                        _sts(R.id.quantity, qty);
                        if(Integer.parseInt(qty)>1)
                            mTextToSpeak.startSpeaking(qty);
                        _lastUpdateQty = _gts(R.id.quantity);

                        /* check if update in quantity need next action */
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned222222");
                            setProc(PROC_TRAYNO);
//                                fixedRequest(COMPLETE_INSPECT);
                        }
                    } else {

                        //U.beepError(this, "You scan wrong barcode");
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned3333");
                        U.beepError(this,"Barcode dont match");

                        Toast.makeText(getApplicationContext(),"BarCode Doesn't Match", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_Scanned");
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
                    Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_Scanned111   "+processedCnt+"     "+_lastUpdateQty);


                    String qtyUpdate = U.minusTo(qty, _lastUpdateQty);
                    Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_Scanned222   "+qtyUpdate+"   "+cProductList.get("quantity"));


                    String maxQty_ = U.minusTo(cProductList.get("quantity"), processedCnt);
                    Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_Scanned333   "+maxQty_);


                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_lastUpdateQTY");
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_qtyUpdate");
                        U.beepError(this, "数量が多すぎます");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt");
                        cProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            setProc(PROC_TRAYNO);
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt222222222222223333333");


                        } else {
                            setProc(PROC_TRAYNO);
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt333333333322222222");

                        }
                    }
                }

                break;

            case PROC_TRAYNO:
                String tray = _gts(R.id.trayno);

                if ("".equals(tray)) {
                    U.beepError(this, "箱数は必須です");
                    _gt(R.id.trayno).setFocusableInTouchMode(true);

                    break;
                }
                stopTimer();

                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
                Globals.getterList.add(new ParamsGetter("tray_quantity", _gts(R.id.quantity)));
                Globals.getterList.add(new ParamsGetter("tray_no", _gts(R.id.trayno)));
                Globals.getterList.add(new ParamsGetter("create_date", _gts(R.id.date)));
                Globals.getterList.add(new ParamsGetter("create_time", timeselected));
                Globals.getterList.add(new ParamsGetter("location", cProductList.get("location")));


                mRequestStatus = REQ_TRAY;

                new MainAsyncTask(this, Globals.Webservice.time_DBatch_Picking, 1, DbatchIncludeActivity.this, "Form", Globals.getterList,true).execute();

                break;
        }
    }

    public void setTimeArray(ArrayList arr) {
        this.timeList = arr;
    }
    public void setProductsArray(List<Map<String, String>> arr) {
        productsList = arr;
        initWorkList();
    }

    protected void initWorkList() {
        Log.e("NewPickingActivity"," initWorkList");
        lv.setAdapter(null);

        Log.e("NewPickingActivity"," initWorkList");

        ListViewItems data = new ListViewItems();
        for (int i = 0; i <=productsList.size() - 1; i++) {
            Map<String, String> row = productsList.get(i);
            Log.e("NewPickingActivity"," initWorkList_"+row);

            data.add(data.newItem().add(R.id.dbtc_inc_0,row.get("location"))
                    .add(R.id.dbtc_inc_1,row.get("code"))
                    .add(R.id.dbtc_inc_2, row.get("quantity"))
                    .add(R.id.dbtc_inc_3, row.get("tray_quantity"))
                    .add(R.id.dbtc_inc_4, row.get("tray"))


            );
            Log.e("NewPickingActivity","DATA >>>>"+data);
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.dbatch_include_list_row);
        Log.e("NewPickingActivity","LIst adapter >>>>>>>");
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
    }

    @Override
    public void nextProcess()
    {
        timeSpinner.setAdapter(null);
        _sts(R.id.date,"");
        _sts(R.id.barcode,"");
        _sts(R.id.quantity,"");
        _sts(R.id.totalQuantity,"");
        _sts(R.id.trayno,"");

        lv.setAdapter(null);
        setProc(PROC_DATE);
    }

    public void currLineData(Map<String, String> data){
        Log.e(TAG,"currLineDataaaaaaaaaaaa");
        cProductList = data;
    }
    public void nextWork() {
        Log.e(TAG, "nextworkkkkkk");
        String processedCnt = cProductList.get("processedCnt");
        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
        setProc(PROC_QTY);
        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
        _lastUpdateQty = _gts(R.id.quantity);
        Log.e(TAG, "nextworkkkkkk processedCnt" +cProductList.get("processedCnt"));
        Log.e(TAG, "nextworkkkkkk quantity" +cProductList.get("quantity"));

        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
            Log.e(TAG, ">>>>>>>>>>>>");
            setProc(PROC_TRAYNO);
//            inspectionNo = COMPLETE_INSPECT;
        }
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
            case PROC_TRAYNO:
                _sts(R.id.trayno, buff);
                break;}
    }

    @Override
    public void scanedEvent(String barcode) {
        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEventttttt");

            if (mProcNo == PROC_BARCODE){
                // check for QR code
                Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());

                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode;

                _sts(R.id.barcode, barcode);}
        }


        if (mProcNo == PROC_QTY){
            Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
            String finalbarcode = CommonFunctions.getBracode(barcode);
            barcode =finalbarcode;

            Log.e(TAG,"barcode111   "+barcode);
        }



        if (mProcNo == PROC_TRAYNO)
            _sts(R.id.trayno, barcode);

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
            case PROC_QTY: // 数量
                _sts(R.id.quantity, barcode);
                break;
            case PROC_TRAYNO: // 数量
                _sts(R.id.trayno, barcode);
                break;
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
                Log.e(TAG,"CODEeee============Nulllll");
                CommonDialogs.customToast(getApplicationContext(),"Network error occured");

            }
            if ("0".equals(code) == true) {

                Log.e("SendLogs111",code+"  "+msg+"  "+result1);
                if(mRequestStatus == REQ_DATE)
                {
                    new GetTimelist().post(code,msg,result1, mHash,DbatchIncludeActivity.this);
                }
                else if(mRequestStatus == REQ_TIME)
                {
                    new GetDbatchIncludeTime().post(code,msg,result1, mHash,DbatchIncludeActivity.this);
                }
                else if(mRequestStatus == REQ_BARCODE){

                    new GetIncludeBracode().post(code,msg,result1, mHash,DbatchIncludeActivity.this);
                }
                else if(mRequestStatus == REQ_TRAY )
                {
                    new GetDbatchIncludeTime().post(code,msg,result1, mHash,DbatchIncludeActivity.this);
                }
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(DbatchIncludeActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else
            if(mRequestStatus == REQ_DATE)
            {
                new GetTimelist().valid(code,msg,result1, mHash,DbatchIncludeActivity.this);
            }
            else if(mRequestStatus == REQ_TIME )
            {
                new GetDbatchIncludeTime().valid(code,msg,result1, mHash,DbatchIncludeActivity.this);
            }
            else if(mRequestStatus == REQ_BARCODE)
            {
                _gt(R.id.barcode).requestFocus();
                new GetIncludeBracode().valid(code,msg,result1, mHash,DbatchIncludeActivity.this);
            }
            else if(mRequestStatus == REQ_TRAY )
            {
                _gt(R.id.trayno).requestFocus();
                new GetDbatchIncludeTime().valid(code,msg,result1, mHash,DbatchIncludeActivity.this);
            }

        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {
           U.beepError(this, "Network problem occured");
    }
}
