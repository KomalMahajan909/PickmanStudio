package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetItemList;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetShipmentList;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetZoneList;
import com.itechvision.ecrobo.pickman.Models.TruckPicking.GetShippingCompanyRequest;
import com.itechvision.ecrobo.pickman.Models.TruckPicking.ShippingCompanyData;
import com.itechvision.ecrobo.pickman.Models.TruckPicking.TruckpickingResult;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
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
import okhttp3.ResponseBody;

import static com.itechvision.ecrobo.pickman.Util.CommonDialogs.customToast;

public class TruckBatchActivity extends BaseActivity implements MainAsynListener, View.OnClickListener, DataManager.GetShippingCompanycall {

    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.date) EditText datedt;
    @BindView(R.id.add_layout) Button keyboard;
    @BindView(R.id.ZoneSpinnerlayout) RelativeLayout ZonespinnerLayout;
    @BindView(R.id.shipmentSpinnerlayout)  RelativeLayout shipmentSpinnerlayout;
    @BindView(R.id.itemSpinnerlayout)  RelativeLayout itemSpinnerlayout;
    @BindView(R.id.itemshippincompany)  RelativeLayout itemshippincompany;
    @BindView(R.id.zoneSpinner) Spinner zoneSpinner;
    @BindView(R.id.shipmentSpinner)  Spinner shipmentSpinner;
    @BindView(R.id.ItemSpinner)  Spinner ItemSpinner;
    @BindView(R.id.shippingcompanyspinner)  Spinner shippingcompanyspinner;

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    ECRApplication app=new ECRApplication();
    public String adminID="",version="",ID="";
    private Button numbrbtn;
    private TextToSpeak mTextToSpeak;
    protected int mProcNo = 0;
    public static final int PROC_DATE = 1;
    public static final int PROC_TRUCK = 2;
    public static final int PROC_ZONE = 3;
    public static final int PROC_SHIPMENT = 4;
    public static final int PROC_ITEM = 5;
    public static final int PROC_ShipCompany = 6;
    public static int mRequestStatus =0;
    public static final int REQ_DATE = 1;
    public static final int REQ_TRUCK = 2;
    public static final int REQ_ZONE = 3;
    public static final int REQ_SHIPMENT = 4;

    private String TAG = "TruckBatchActivity";
    private boolean visible = false;
    protected RelativeLayout mainlayout;
    protected RelativeLayout layout;
    public Context mcontext = this;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    String zoneSelected ="", shippmentselected = "", itemselected = "";
    List<Map<String, String>> zoneList = new ArrayList<>();
    List<Map<String, String>> shipmentList = new ArrayList<>();
    List<Map<String, String>> itemList = new ArrayList<>();

    public static ArrayList<Map<String, String>> cancelList = new ArrayList<>();

    DataManager.GetShippingCompanycall call;
    DataManager manger;
    ArrayList<ShippingCompanyData> data;
    ArrayList<String> spinnerArray1,SpinnerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_batch);
        ButterKnife.bind(this);
        call =this;
        getIDs();

        Log.d(TAG,"On Create ");
        manger = new DataManager();
        data = new  ArrayList<>();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        Log.e(TAG,"onCreatettttt "+BaseActivity.truckComplete);

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);
        numbrbtn = (Button) _g(R.id.add_layout);
        mTextToSpeak = new TextToSpeak(this);
        try {
            PackageInfo pInfo =  getPackageManager().getPackageInfo(getPackageName(), 0);
            version = String.valueOf( pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(ContentValues.TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = "";

                if (month < 10) {
                    if (day < 10)
                        date = year + "-0" + month + "-0" + day;
                    else
                        date = year + "-0" + month + "-" + day;
                } else {
                    if (day < 10)
                        date = year + "-" + month + "-0" + day;
                    else
                        date = year + "-" + month + "-" + day;
                }
                datedt.setText(date);
                BaseActivity.settruckdate(date);
                _sts(R.id.truck,"");
                setProc(PROC_DATE);
                 zoneSpinner.setAdapter(null);
                shipmentSpinner.setAdapter(null);
                ItemSpinner.setAdapter(null);
                shippingcompanyspinner.setAdapter(null);


                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        Globals.getterList = new ArrayList<>();
                        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                        Globals.getterList.add(new ParamsGetter("shipping_schedule", _gts(R.id.date)));
                        Globals.getterList.add(new ParamsGetter("mode", "get_zone"));

                        mRequestStatus = REQ_DATE;
                        new MainAsyncTask(TruckBatchActivity.this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchActivity.this, "Form", Globals.getterList, true).execute();

                    }
                }, 200);


            }
        };

        datedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonDatePickerDialog(datedt);
            }
        });


 /*       datedt.setOnTouchListener(new View.OnTouchListener() {
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
                _sts(R.id.date,date);
                BaseActivity.settruckdate(date);
                inputedEvent(date);
            }
        };
*/
        zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0 ){
                    Map<String,String> map = zoneList.get(position-1);
                    zoneSelected = map.get("key");

                    _sts(R.id.truck,"");
                   // setProc(PROC_DATE);
                  //  zoneSpinner.setAdapter(null);
                    shipmentSpinner.setAdapter(null);
                    ItemSpinner.setAdapter(null);
                    shippingcompanyspinner.setAdapter(null);


                    Globals.getterList = new ArrayList<>();
                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    Globals.getterList.add(new ParamsGetter("shipping_schedule", _gts(R.id.date)));
                    Globals.getterList.add(new ParamsGetter("mode", "get_order"));
                    Globals.getterList.add(new ParamsGetter("zone_no", zoneSelected));

                    mRequestStatus = REQ_ZONE;
                    new MainAsyncTask(TruckBatchActivity.this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchActivity.this, "Form", Globals.getterList, true).execute();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/*
 zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0 && mProcNo == PROC_ZONE ){
                    Map<String,String> map = zoneList.get(position-1);
                    zoneSelected = map.get("key");




                    Globals.getterList = new ArrayList<>();
                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    Globals.getterList.add(new ParamsGetter("shipping_schedule", _gts(R.id.date)));
                    Globals.getterList.add(new ParamsGetter("mode", "get_order"));
                    Globals.getterList.add(new ParamsGetter("zone_no", zoneSelected));

                    mRequestStatus = REQ_ZONE;
                    new MainAsyncTask(TruckBatchActivity.this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchActivity.this, "Form", Globals.getterList, true).execute();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        shipmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0   ){
                    Map<String,String> map = shipmentList.get(position-1);
                    shippmentselected = map.get("key");

                    _sts(R.id.truck,"");
                    // setProc(PROC_DATE);
                    //  zoneSpinner.setAdapter(null);
                     ItemSpinner.setAdapter(null);
                    shippingcompanyspinner.setAdapter(null);

                    Globals.getterList = new ArrayList<>();
                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    Globals.getterList.add(new ParamsGetter("shipping_schedule", _gts(R.id.date)));
                    Globals.getterList.add(new ParamsGetter("mode", "get_items"));
                    Globals.getterList.add(new ParamsGetter("zone_no", zoneSelected));
                    Globals.getterList.add(new ParamsGetter("order_group", shippmentselected));

                    mRequestStatus = REQ_SHIPMENT;
                    new MainAsyncTask(TruckBatchActivity.this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchActivity.this, "Form", Globals.getterList, true).execute();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ItemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0  ){
                    Map<String,String> map = itemList.get(position-1);
                    itemselected = map.get("key");


                    _sts(R.id.truck,"");
                    // setProc(PROC_DATE);
                    //  zoneSpinner.setAdapter(null);
                  //   ItemSpinner.setAdapter(null);
                    shippingcompanyspinner.setAdapter(null);

                    setProc(PROC_ShipCompany);

                    GetShippingCompanyRequest req = new GetShippingCompanyRequest(adminID,_gts(R.id.date),"get_batch",zoneSelected,shippmentselected,itemselected,app.getSerial(),BaseActivity.getShopId(),version);
                    manger.GetShippingCompany(req,call);



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(mProcNo == 0) nextProcess();
        int right = convert(10);
        int left = convert(10);
        datedt.setPadding(left,0,right,0);

        setBadge2(cancelList.size());
        _sts(R.id.date,BaseActivity.gettruckdate());

        shippingcompanyspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    ID = data.get(position-1).getBatch_id();
                    Log.e("Xhgydfukjvhfsuvl", " >>>>" +ID);
                    setProc(PROC_TRUCK);
                }else{
                    ID="";
                    Log.e("Xhgydfukjvhfsuvl", " >>>>" +ID);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void showTruitonDatePickerDialog(View v) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(TruckBatchActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


/*    public void showTruitonDatePickerDialog(View v) {
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

    }*/

    private void getIDs() {

        actionbarImplement(this, "バッチ商品ピック", " ",
                0, false,true,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnRed.setOnClickListener(this);
//        btnBlue.setOnClickListener(this);
    }

    @OnClick(R.id.clear) void clear(){
        if(getBadge2()> 0){
            final SweetAlertDialog pDialog1 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            pDialog1.setCancelable(true);
            pDialog1.setContentText("Do you want to clear all the cancel list ?");
            pDialog1.setConfirmText("Yes");
            pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    pDialog1.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            cancelList.clear();
                            setBadge2(cancelList.size());
                            pDialog1.dismiss();
                        }

                    }, 1000);
                }
            });
            pDialog1.setCancelText("No");
            pDialog1.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {

                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    pDialog1.dismiss();
                }
            });

            pDialog1.show();
        }
    }

    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
        if (visible == false) {

            visible = true;

            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layout.getLayoutParams();
            param.setMargins(0, top, 0, 0);
            Log.e("MoveActivity", "SetlayoutMarginn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e("MoveActivity", "SetlayoutMarginn");
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
        int right = convert(10);
        int left = convert(10);
        switch (procNo) {

            case PROC_TRUCK:
                Log.e(TAG, "setProc_BARCODEEEEE");
                _gt(R.id.truck).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                ZonespinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                shipmentSpinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                itemshippincompany.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                itemSpinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                datedt.setPadding(left,0,right,0);
                _gt(R.id.truck).setFocusableInTouchMode(true);

                break;

            case PROC_DATE:
                Log.e(TAG,"setPROC_DATEEEEEEE");
                _gt(R.id.date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.truck).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                ZonespinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                shipmentSpinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                itemSpinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                itemshippincompany.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                datedt.setPadding(left,0,right,0);
                _gt(R.id.truck).setPadding(left,0,0,0);
                _gt(R.id.date).setFocusableInTouchMode(true);
                break;
            case PROC_ZONE:
                _gt(R.id.date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.truck).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                ZonespinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                shipmentSpinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                itemSpinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                itemshippincompany.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;

            case PROC_SHIPMENT:
                _gt(R.id.date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.truck).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                ZonespinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                shipmentSpinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                itemSpinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                itemshippincompany.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
            case PROC_ShipCompany:
                _gt(R.id.date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.truck).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                ZonespinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                shipmentSpinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                itemshippincompany.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                itemSpinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;

            case PROC_ITEM:
                _gt(R.id.date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.truck).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                ZonespinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                shipmentSpinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                itemshippincompany.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                itemSpinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                break;
        }
    }
    @Override
    public void inputedEvent(String buff) {
        switch (mProcNo) {
            case PROC_TRUCK:    // バーコード
                String truck = _gts(R.id.truck);

                Log.e(TAG, "inputedEvent_TRUCK   "+truck);
                if(truck.equals("")) {
                    U.beepError(this, "truck cannot be empty");
                    _gt(R.id.truck).setFocusableInTouchMode(true);
                    break;
                }

                if (ID.equalsIgnoreCase("")){
                    U.beepError(this, "配送会社を選択してください。");
                }else {
                    Globals.getterList = new ArrayList<>();
                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                    Globals.getterList.add(new ParamsGetter("shipping_schedule", _gts(R.id.date)));
                    Globals.getterList.add(new ParamsGetter("truck_barcode", truck));
                    Globals.getterList.add(new ParamsGetter("zone_no", zoneSelected));
                    Globals.getterList.add(new ParamsGetter("order_group", shippmentselected));
                    Globals.getterList.add(new ParamsGetter("items_group", itemselected));
                    Globals.getterList.add(new ParamsGetter("batch_id", ID));
                    Globals.getterList.add(new ParamsGetter("mode", "batch_truck"));

                    mRequestStatus = REQ_TRUCK;
                    new MainAsyncTask(this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchActivity.this, "Form", Globals.getterList, true).execute();
                }
                break;

            case PROC_DATE:
                String date= _gts(R.id.date);

                Log.e(TAG,"inputedEvent_DATE "+date);

                if (date.equals("")){
                    U.beepError(this,"date can't be empty");
                    _gt(R.id.date).setFocusableInTouchMode(true);
                    break;
                }
                Globals.getterList = new ArrayList<>();
                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("shipping_schedule", _gts(R.id.date)));
                Globals.getterList.add(new ParamsGetter("mode", "get_zone"));

                mRequestStatus = REQ_DATE;
                new MainAsyncTask(this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckBatchActivity.this, "Form", Globals.getterList, true).execute();

                break;
        }
    }

    @Override
    public void clearEvent() {
        new AlertDialog.Builder(this)
                .setTitle("Clear？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mTextToSpeak.startSpeaking("clear");
                        nextProcess();
                    }
                })
                .setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO cancel clear
                    }
                })
                .show();
    }

    @Override
    public void allclearEvent()
    {
        customToast(this, "No action");
    }

    @Override
    public void skipEvent() {
        customToast(this, "No action");

    }
    @Override
    public void nextProcess() {
        _sts(R.id.truck,"");
        _sts(R.id.date,"");
        setProc(PROC_DATE);
        zoneSpinner.setAdapter(null);
        shipmentSpinner.setAdapter(null);
        ItemSpinner.setAdapter(null);
        shippingcompanyspinner.setAdapter(null);
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_TRUCK:    // バーコード
                _sts(R.id.truck, buff);
                break;
        }
    }

    protected boolean showPopup(){
        Log.e(TAG,"showPopup");
        if (cancelList == null || cancelList.isEmpty()) {
            Log.e("PickingActivityy","showInfoo  mPickingOrderList===000");
            return false;
        } else {
            if (getPopupWindow2() == null) {
                Log.e("PickingActivityy","showInfoo  popupwindow");
                final PopupWindow popupWindow = new PopupWindow(this);
                // レイアウト設定
                View popupView = getLayoutInflater().inflate(R.layout.order_list, null);
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
                setPopupWindow2(popupWindow);
                ImageView close =(ImageView)getPopupWindow2().getContentView().findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
            ListView lv = (ListView) getPopupWindow2().getContentView().findViewById(R.id.orderPicking);
            initList1(lv);

            // 画面中央に表示
            getPopupWindow2().showAtLocation(findViewById(R.id.truck), Gravity.CENTER, 0, 32);
            return true;
        }
    }

    protected ListViewItems initList1(ListView lv) {
        Log.e(TAG,"initList1");
        lv.setAdapter(null);
        Log.e(TAG,"initListt12222");
        ListViewItems data = new ListViewItems();
        Log.e(TAG,"initListt13333");
        for (int i = 0 ;i < cancelList.size(); i++) {

            Map<String, String> row = cancelList.get(i);

            Log.e(TAG,"initListtt"+i+i);
            data.add(data.newItem().add(R.id.odr_pic_1, row.get("order_no"))
                    .add(R.id.odr_pic_2, row.get("code")).add(R.id.odr_pic_3, row.get("location"))
                    .add(R.id.odr_pic_4, row.get("quantity"))
                    .add(R.id.odr_pic_5, row.get("pick_quantity"))
            );
        }

        ListViewAdapter adapter = new ListViewAdapter(getApplicationContext() , data
                , R.layout.cancel_orders_product_row) ;
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }

    @Override
    public void scanedEvent(String barcode) {
        if (!barcode.equals("")) {
            if (mProcNo==PROC_TRUCK){
                _sts(R.id.truck,barcode);
            }
        }

        this.inputedEvent(barcode);
    }

    @Override
    public void enterEvent() {

    }

    @OnClick(R.id.enter)void enter(){
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
    }

    @Override
    public void deleteEvent(String barcode) {

        switch (mProcNo) {
            case PROC_TRUCK:    // バーコード
                _sts(R.id.truck, barcode);
                break;
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
            case R.id.notif_count_red:
                showPopup();
                break;
        }
    }

    public void setzoneList(List<Map<String, String>> list){
        zoneList = list;
    }
    public void setShipmentList(List<Map<String, String>> list){
        shipmentList = list;
    }
    public void setItemList(List<Map<String, String>> list){
        itemList = list;
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
                customToast(getApplicationContext(),"Network error occured");

            }
            if ("0".equals(code) == true) {
                Log.e(TAG ,"mRequestStatus   " +mRequestStatus);
                JsonHash row = result1.getJsonHashOrNull(0);
                if(mRequestStatus == REQ_TRUCK) {
                    String count ="";
                    if(row.containsKey("pending_row_counts")){
                        count = row.getStringOrNull("pending_row_counts");
                    }
                    if(!count.equals("0") && row.containsKey("product_row")){

                        JsonHash row1 = row.getJsonHashOrNull("product_row");

                        BaseActivity.truckComplete = false;
                        Intent i = new Intent(this,TruckBatchPickingActivity.class);
                        i.putExtra("truck",_gts(R.id.truck));
                        i.putExtra("shipping_schedule",_gts(R.id.date));
                        i.putExtra("batch_id",row1.getStringOrNull("batch_id"));
                        startActivity(i);
                    }
                    else U.beepError(this,"No results found");
                }
                else if(mRequestStatus == REQ_DATE) {
                    Log.e(TAG ,"mRequestStatus11   " +mRequestStatus);
                    new GetZoneList().post(code,msg,result1, mHash,TruckBatchActivity.this);
                }
                else if(mRequestStatus == REQ_ZONE) {
                    Log.e(TAG ,"mRequestStatus11   " +mRequestStatus);
                    new GetShipmentList().post(code,msg,result1, mHash,TruckBatchActivity.this);
                }
                else if(mRequestStatus == REQ_SHIPMENT) {

                    Log.e(TAG ,"mRequestStatus11   " +mRequestStatus);
                    new GetItemList().post(code,msg,result1, mHash,TruckBatchActivity.this);
                }

            }else if(code.equalsIgnoreCase("1020")){
                        new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(TruckBatchActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })
                        .show();
            } else {

                U.beepError(this,msg);
            }
        }
        catch (Exception e) {
            System.out.print(e);
        }

    }

    @Override
    public void onPostError(int flag) {
        U.beepError(this,"Network Error");
    }

    //GetShipping Company
    @Override
    public void onSucess(int status, TruckpickingResult message) throws JsonIOException {
        if (message.getCode().equalsIgnoreCase("0")){

            data = message.getResults();

            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("選択する");

            for (int i = 0; i < data.size(); i++) {
                data.get(i).getShipping_method();
                data.get(i).getBatch_no();
                data.get(i).getBatch_id();

                String a =/* data.get(i).getBatch_id() + ": " +*/ data.get(i).getShipping_method()+ " / "+data.get(i).getBatch_no();
                arrayList.add(a);
            }


            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),  _singleItemRes, arrayList);
            adapter1.setDropDownViewResource(_dropdownRes);
            shippingcompanyspinner.setAdapter(adapter1);


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
}
