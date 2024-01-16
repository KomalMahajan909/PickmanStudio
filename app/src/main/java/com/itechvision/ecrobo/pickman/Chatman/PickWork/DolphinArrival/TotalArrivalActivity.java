package com.itechvision.ecrobo.pickman.Chatman.PickWork.DolphinArrival;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TotalPickListActivity;
import com.itechvision.ecrobo.pickman.Models.SubmitTime.TimeRequest;
import com.itechvision.ecrobo.pickman.Models.SubmitTime.TimeResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.PickedResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.ReprintRequest;
import com.itechvision.ecrobo.pickman.Models.TotalArival.ReprintResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.StartArivalResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.TotalArivalReq;
import com.itechvision.ecrobo.pickman.Models.TotalArival.TotalArivalResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.TotalArrivalListReq;
import com.itechvision.ecrobo.pickman.Models.TotalArival.Update.MylistData;
import com.itechvision.ecrobo.pickman.Models.TotalArival.Update.MylistResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.Update.OtherListResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.Update.UpdateProductRequest;
import com.itechvision.ecrobo.pickman.Models.TotalArival.nyuka_data;
import com.itechvision.ecrobo.pickman.Models.TotalArival.nyuka_startdata;
import com.itechvision.ecrobo.pickman.Models.TotalArival.package_data;
import com.itechvision.ecrobo.pickman.Models.TotalList.TotalListResponse;
import com.itechvision.ecrobo.pickman.Models.TotalList.shopdata;
import com.itechvision.ecrobo.pickman.Models.TotalShopListRequest;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.Util.UtilsMethods;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;


public class TotalArrivalActivity extends BaseActivity implements DataManager.TotalListcallback,
        View.OnClickListener, DataManager.TotalArrivalcallback, DataManager.Startcallback,
        DataManager.Updatecallback, DataManager.Reprintcallback, DataManager.PickedProductcallback, DataManager.Mylistcallback,
        DataManager.OtherListcallback, DataManager.Timecallback {

    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.layout_main)
    RelativeLayout layoutMain;
    @BindView(R.id.scrollMain)
    ScrollView scrollMain;
  // @BindView(R.id.rbarrival)
//   RadioButton rbarrival;
//   @BindView(R.id.radiogroup)
//   RadioGroup radiogroup;
//   @BindView(R.id.rbreturn)
//   RadioButton rbreturn;
    @BindView(R.id.spinnerlayout)
    RelativeLayout spinnerlayout;
    @BindView(R.id.shopspinner)
    Spinner shopspinner;
    @BindView(R.id.et_date)
    EditText etdate;

    @BindView(R.id.show_allCheck)
    CheckBox show_allCheck;

    @BindView(R.id.barcode)
    EditText barcode;

    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.layout_number)
    RelativeLayout layoutNumber;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.add_layout)
    Button add_layout;

    SweetAlertDialog pDialog;
    int eop, barcodepostion;
    public static int mRequestStatus = 0;
    SharedPreferences spDomain;
    public Context mcontext = this;
    public static final String DOMAINPREFERENCE = "domain";

    String show_all = "0";

    int Usercount = 0;
    ECRApplication app = new ECRApplication();
    int typePOS = 0,DisposalPOS=0;
    public String adminID = "", warehouse = "", domainname = "", ID = "", typeSelect = "",
            Key = "", BarcodeScanvalue = "0", PName = "", ProductId = "", ReturnNyokaID = "", ReturnOrderNo = "", getIncrease = "",CaseQuantity="",Disposal="" ,Disposaltexts="";
    DataManager manager;
    progresBar progress;
    ArrayList<shopdata> spinnerArray1;
    ArrayList<nyuka_data> datta;
    ArrayList<package_data> packagedatta;
    ArrayList<String> typeList;
    ArrayList<MylistData> mydata;
    ArrayList<nyuka_startdata> startdatta;

    ArrayList<String> a;
    String Statusreturn = "0";
    private boolean showKeyboard;
    private boolean visible = false;
    private TextToSpeak mTextToSpeak;
    public String _lastUpdateQty = "0";
    protected int mProcNo = 0;
    public static final int PROC_SHOP = 1;
    public static final int PROC_DATE = 2;
    public static final int PROC_NONE = 3;
    public static final int PROC_BARCODE = 4;
    public static final int PROC_QTY = 5;

    public static final int REQ_INITIAL = 1;
    public static final int REQ_DATE = 2;
    public static final int REQ_BARCODE = 3;
    public static final int REQ_QTY = 4;
    public static final int REQ_PRINT = 5;
    public static final int REQ_UPDATE = 6;
    String TAG = TotalPickListActivity.class.getSimpleName();

    ArrayList<String> codeList = new ArrayList<>();
    public Map<String, String> cProductList = null;

    DataManager.TotalArrivalcallback totalarrivecall;
    DataManager.Updatecallback updaterequest;
    DataManager.PickedProductcallback pickedcall;
    DataManager.Mylistcallback mylistcall;
    DataManager.Timecallback timesubmit;
    DataManager.Reprintcallback reprintcall;
    DataManager.OtherListcallback otherlist;
    DataManager.TotalListcallback totallist;

    Dialog dialogg, dialogcheck;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    String depth = "", heigh = "", weight = "", width = "", depth1 = "", heigh1 = "", weight1 = "", width1 = "", NyukaId = "", productID = "", track_package = "";
    DataManager.Startcallback call;
    EditText Widthh, Heightt, Depthh, Weightt, Widthh1, Heightt1, Depthh1, Weightt1,CaseQuantityEd;

    int selectedPosition = 0;
    long mLastClickTime;

    PopupWindow popupWindow;
    Calendar myCalendar;
    private int CalendarHour, CalendarMinute;
    TimePickerDialog timepickerdialog;
    TextView timee;
     ListViewAdapter adapter;

    String barc = "", date_select = "", shop_select = "";
    int OtherCount = 0;
String disposal[] = {"選択なし","不燃","リサイクル","その他" };
 boolean SubmitDisposal = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_arrival);
        ButterKnife.bind(this);
        totalarrivecall = this;
        updaterequest = this;
        pickedcall = this;
        mylistcall = this;
        otherlist = this;
        call = this;
        timesubmit = this;
        totallist = this;
        getIDs();
        Log.d(TAG,"On Create ");
        showKeyboard = BaseActivity.getaddKeyboard();
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        manager = new DataManager();
        progress = new progresBar(this);
        a = new ArrayList<>();


        etdate.setText(BaseActivity.getDolphinDate());

        etdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etdate.getRight() - etdate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //open calendar on touch icon

                        showTruitonDatePickerDialog(etdate);
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
                date = year + "-" + _month + "-" + _day;

                _sts(R.id.et_date, date);
            }
        };

        if (showKeyboard == true) {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
            visible = true;
            add_layout.setText("キーボードを隠す");
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layoutMain.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layoutNumber.getLayoutParams();
            param.setMargins(0, top, 0, 0);
            //     Log.e(TAG, "SetlayoutMarginnnnn");
            layoutMain.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            add_layout.setVisibility(View.GONE);
        }

        mTextToSpeak = new TextToSpeak(this);
        //Log.e(TAG, "OnCreateeeeee");

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);
        warehouse = spDomain.getString("warehouse_id", null);

        Intent intent = getIntent();
        if (intent.hasExtra("barcode")) {
            shop_select = intent.getStringExtra("shop");
            barc = intent.getStringExtra("barcode");
            date_select = intent.getStringExtra("date");
        }

        show_allCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    show_all = "1";
                } else show_all = "0";

                if (!_gts(R.id.barcode).equals("")) {
                    sendBarcodeRequest();

                }
            }
        });

        GetallShopAPI();
    }

    public void sendBarcodeRequest() {
        progress.Show();
        BaseActivity.setDolphinDate(_gts(R.id.et_date));
        TotalArrivalListReq req = new TotalArrivalListReq(app.getSerial(), ID, adminID, getResources().getString(R.string.version), _gts(R.id.barcode), _gts(R.id.et_date), show_all);
        manager.TotalArrival(req, totalarrivecall);
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
        dialog.show();

    }

    private void GetallShopAPI() {
        progress.Show();
        TotalShopListRequest req = new TotalShopListRequest(app.getSerial(), adminID, warehouse);
        manager.TotalList(req, totallist);
    }

    private void TotalArrivalAPI() {
        progress.Show();
        TotalArivalReq req = new TotalArivalReq(app.getSerial(), adminID, ID, Key,
                PName, "", "", "", "", "", "", "", "", getResources().getString(R.string.version));
//        manager.TotalArrival(req, totalarrivecall);
    }

    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
        if (visible == false) {

            visible = true;
            add_layout.setText("キーボードを隠す");
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layoutMain.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layoutNumber.getLayoutParams();
            param.setMargins(0, top, 0, 0);

            layoutMain.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;
            add_layout.setText("キーボードを表示する");
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layoutMain.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            //  Log.e(TAG, "SetlayoutMarginnnnn");
            layoutMain.setLayoutParams(params);
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

    private void getIDs() {

        actionbarImplement(this, "D入荷検品", " ",
                0, true, true, false);
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);

        btnRed.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
    }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    @OnClick(R.id.enter)
    void Enter() {
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
    }

    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
    }

    @Override
    public void allclearEvent() {
      /*  etdate.setText("");
        barcode.setText("");
//        quantity.setText("");
//        productName.setText("");

        mRequestStatus = REQ_INITIAL;
        GetallShopAPI();*/
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

    @OnClick(R.id.enter)
    void onEnterClick() {
        //TODO implement
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);

    }

/*    @OnClick(R.id.clear)
    void onclearClick() {
        //TODO implement
        clearEvent();
    }*/

    @Override
    public void nextProcess() {

        etdate.setText("");
        barcode.setText("");
        list.setAdapter(null);
        setProc(PROC_DATE);

        setBadge1(0);
        setBadge2(0);
        setDolfinArrivalshop(0);

      /*
        resetPackData();
      */

        mRequestStatus = REQ_INITIAL;
        GetallShopAPI();
    }

    @Override
    public void onSucess(int status, TotalListResponse message) throws JsonIOException {

        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")) {
            ArrayList<String> arrayList = new ArrayList<String>();
            spinnerArray1 = message.getResult().get(0).getShop_data();
            for (int i = 0; i < spinnerArray1.size(); i++) {
                spinnerArray1.get(i).getShop_id();
                spinnerArray1.get(i).getShop_name();

                String a = spinnerArray1.get(i).getShop_id() + ": " + spinnerArray1.get(i).getShop_name();
                arrayList.add(a);
            }


            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),
                    _singleItemRes, arrayList);
            adapter1.setDropDownViewResource(_dropdownRes);

            shopspinner.setAdapter(adapter1);
            shopspinner.setSelection(getDolfinArrivalshop());

            shopspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ID = spinnerArray1.get(position).getShop_id();
                    BaseActivity.setDolfinArrivalshop(position);
                    setProc(PROC_BARCODE);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } else if (message.getCode().equalsIgnoreCase("1020")) {
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(TotalArrivalActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })
                    .show();
        } else {
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, TotalArivalResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")) {
            list.setVisibility(View.VISIBLE);
            datta = message.getNyuka_list();

            setBadge2(Integer.parseInt(message.getNyuka_complete_count()));
            OtherCount = Integer.parseInt(message.getNyuka_other_count());

            if (OtherCount == 0 && datta.size() == 0) {
                //no data found
                U.beepError(this, "結果が見つかりません");
                list.setAdapter(null);
                setProc(PROC_BARCODE);
            } else if (OtherCount != 0 && datta.size() == 0) {
                //other user working
                U.beepError(this, "他の働くユーザー");
                list.setAdapter(null);
                setProc(PROC_BARCODE);
            } else {
                packagedatta = message.getPackages();

                depth = datta.get(0).getDepth();
                heigh = datta.get(0).getHeight();
                weight = datta.get(0).getWeight();
                width = datta.get(0).getWidth();

                Log.e(TAG, "depth  " + depth + "height   " + heigh + "weight  " + weight + "width  " + width);

                depth1 = datta.get(0).getPiece_depth();
                heigh1 = datta.get(0).getPiece_height();
                weight1 = datta.get(0).getPiece_weight();
                width1 = datta.get(0).getPiece_width();

                CaseQuantity= datta.get(0).getCase_qty();
                Disposal=datta.get(0).getDisposal_flag();


                setList();

                checkforDimensions();

            }

        } else if (message.getCode().equalsIgnoreCase("1020")) {
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(TotalArrivalActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })
                    .show();
        }
    }

    void setList() {
        int printcount = 0, reprintCount = 0;
        ListViewItems data = new ListViewItems();
        for (int i = 0; i < datta.size(); i++) {

            data.add(data.newItem()
                    .add(R.id.sku_name, datta.get(i).getProduct_name())
                    .add(R.id.sku_val, datta.get(i).getCode())
                    .add(R.id.sku_date, datta.get(i).getDate())
                    .add(R.id.sku_qty, datta.get(i).getQuantity()));


            String print = datta.get(i).getPrint();

            if (print.equals("0"))
                printcount++;
            else if (print.equals("1"))
                reprintCount++;


        }
        //   adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.total_arrival_list_data)
        adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.adap_newarrival) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                final TextView start = (TextView) v.findViewById(R.id.operation);
                final TextView sku_name = (TextView) v.findViewById(R.id.sku_name);

                sku_name.setSelected(true);

                String print = datta.get(position).getPrint();

                if (print.equals("0")) {
                    start.setText("入荷検品");
                    start.setBackground(getResources().getDrawable(R.drawable.keyboard_btn_rounded));
                } else if (print.equals("1")) {
                    start.setText("再印刷");
                    start.setBackground(getResources().getDrawable(R.drawable.peach_pending_btn));
                }
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (start.getText().toString().equals("入荷検品")) {
                            selectedPosition = position;
                            Log.e(TAG, " Positionnnnn     " + selectedPosition);

                            progress.Show();
                            TotalArivalReq req = new TotalArivalReq(app.getSerial(), adminID, ID, "",
                                    "", "start", datta.get(position).getNyuka_id(), "", "", "", "", "", "", getResources().getString(R.string.version));
                            manager.StartArival(req, call);
                        } else {
                        }
                    }
                });

                return v;
            }
        };

        list.setAdapter(adapter);
        // 単一選択モードにする
        list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
          /*  // デフォルト値をセットする
            if (data.getData().size() > 0)
                list.setItemChecked(0, true);*/

        setBadge1(printcount);

    }

    public void checkforDimensions() {

        if (depth.equalsIgnoreCase("0") || depth.equalsIgnoreCase("")) {
            UpdateArrival();
        } else if (heigh.equalsIgnoreCase("0") || heigh.equalsIgnoreCase("")) {
            UpdateArrival();
        } else if (weight.equalsIgnoreCase("0") || weight.equalsIgnoreCase("")) {
            UpdateArrival();
        } else if (width.equalsIgnoreCase("0") || width.equalsIgnoreCase("")) {
            UpdateArrival();
        } else if (depth1.equalsIgnoreCase("0") || depth1.equalsIgnoreCase("")) {
            UpdateArrival();
        } else if (heigh1.equalsIgnoreCase("0") || heigh1.equalsIgnoreCase("")) {
            UpdateArrival();
        } else if (weight1.equalsIgnoreCase("0") || weight1.equalsIgnoreCase("")) {
            UpdateArrival();
        } else if (width1.equalsIgnoreCase("0") || width1.equalsIgnoreCase("")) {
            UpdateArrival();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            sendBarcodeRequest();
        }
    }

    void showdialog(final int postion) {

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog.setCancelable(true);
        pDialog.setContentText("Do you want to start pick for line no. " + datta.get(postion).getPicking_no() + "?");
        pDialog.setConfirmText("Yes");
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        NyukaId = datta.get(postion).getNyuka_id();
                        ProductId = datta.get(postion).getProduct_id();


                        pDialog.dismissWithAnimation();
                    }

                }, 500);
            }
        });
        pDialog.setCancelText("No");
        pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                pDialog.dismissWithAnimation();
            }
        });

        pDialog.show();
    }

    @Override
    public void onSucess(int status, StartArivalResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")) {
            startdatta = message.getNyuka_start();

            Intent c_qty = new Intent(TotalArrivalActivity.this, CaseQuantityActivity.class);
            c_qty.putExtra("barcode", _gts(R.id.barcode));
            c_qty.putExtra("date", _gts(R.id.et_date));
            c_qty.putExtra("caseQty", startdatta.get(0).getCase_qty());
            c_qty.putExtra("quantity", startdatta.get(0).getQuantity());
            c_qty.putExtra("attribute_type", startdatta.get(0).getAttribute_type());
            c_qty.putExtra("arrival_date", datta.get(selectedPosition).getDate());
            c_qty.putExtra("code", startdatta.get(0).getCode());
            c_qty.putExtra("product_name", startdatta.get(0).getProduct_name());
            c_qty.putExtra("lot_no", datta.get(selectedPosition).getLot());
            c_qty.putExtra("nyuka_id", startdatta.get(0).getNyuka_id());
            c_qty.putExtra("product_id", startdatta.get(0).getProduct_id());
            c_qty.putExtra("comment", startdatta.get(0).getComment());
            c_qty.putExtra("shopID", ID);

            Log.e(TAG, "Dateeee    " + startdatta.get(0).getRsv_date() + "  arrival_date  " + datta.get(selectedPosition).getDate());


            startActivityForResult(c_qty, 0);


        } else if (message.getCode().equalsIgnoreCase("1020")) {
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(TotalArrivalActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        } else {
            U.beepError(this, message.getMessage());
        }
    }

    public void UpdateArrival() {
        // custom dialog
        Log.e(TAG, "updatee  9876543");
        dialogg = new Dialog(TotalArrivalActivity.this);
        dialogg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogg.setContentView(R.layout.widthdialog_1);
        dialogg.setCanceledOnTouchOutside(false);
        dialogg.setCancelable(false);
        Log.e(TAG, "updatee  98765431111");

        final Button check = (Button) dialogg.findViewById(R.id.check);


        Widthh = (EditText) dialogg.findViewById(R.id.Width);
        Heightt = (EditText) dialogg.findViewById(R.id.Height);
        Depthh = (EditText) dialogg.findViewById(R.id.Depth);
        Weightt = (EditText) dialogg.findViewById(R.id.Weight);
        Log.e(TAG, "updatee  987654322222");

        Widthh1 = (EditText) dialogg.findViewById(R.id.Width1);
        Heightt1 = (EditText) dialogg.findViewById(R.id.Height1);
        Depthh1 = (EditText) dialogg.findViewById(R.id.Depth1);
        Weightt1 = (EditText) dialogg.findViewById(R.id.Weight1);
        Log.e(TAG, "updatee  98765433333");

        TextView product_code = (TextView) dialogg.findViewById(R.id.productcode);
        TextView product_name = (TextView) dialogg.findViewById(R.id.productname);
        CaseQuantityEd = (EditText) dialogg.findViewById(R.id.case_quantity);

        final Spinner typeSpinner = (Spinner) dialogg.findViewById(R.id.typespinner);
        final Spinner disposalspinner = (Spinner) dialogg.findViewById(R.id.disposalspinner);

        product_code.setText(datta.get(0).getCode());



        product_name.setText(datta.get(0).getProduct_name());
        product_name.setSelected(true);

        String a = "";
        typeList = new ArrayList<>();
        for (int i = 0; i < packagedatta.size(); i++) {
            a = packagedatta.get(i).getName();
            typeList.add(a);
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),_singleItemRes, typeList);
        adapter1.setDropDownViewResource(_dropdownRes);
        typeSpinner.setAdapter(adapter1);
        typeSpinner.setSelection(typePOS);


        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeSelect = typeList.get(position);
                typePOS = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //disposalspinner
        ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(getApplicationContext(),_singleItemRes, disposal);
        adapter11.setDropDownViewResource(_dropdownRes);
        disposalspinner.setAdapter(adapter11);


     /* String abc = "2";

        if (abc.equalsIgnoreCase("1")){
            disposalspinner.setSelection(1);
            disposalspinner.setEnabled(false);
        }   else if(abc.equalsIgnoreCase("2")){
            disposalspinner.setSelection(2);
            disposalspinner.setEnabled(false);
        }   else if(abc.equalsIgnoreCase("3")){
            disposalspinner.setSelection(3);
            disposalspinner.setEnabled(false);
        }  else{
            disposalspinner.setEnabled(true);
            disposalspinner.setSelection(0);
        }*/


        disposalspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               //typeSelect = disposal.gep;
                DisposalPOS = position;
                Disposaltexts = disposalspinner.getSelectedItem().toString();
              //  Toast.makeText(TotalArrivalActivity.this,textss,Toast.LENGTH_SHORT).show();;
             }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (datta.get(0).getWidth().equalsIgnoreCase("0") || datta.get(0).getWidth().equalsIgnoreCase("")) {

            if (!width.equals("") && !width.equals("0"))
                Widthh.setText(width);
            else
                Widthh.setText("");
            Widthh.setEnabled(true);
        } else {
            Widthh.setEnabled(false);
            Widthh.setText(datta.get(0).getWidth());
        }

        if (datta.get(0).getHeight().equalsIgnoreCase("0") || datta.get(0).getHeight().equalsIgnoreCase("")) {
            if (!heigh.equals("") && !heigh.equals("0"))
                Heightt.setText(heigh);
            else
                Heightt.setText("");

            Heightt.setEnabled(true);
        } else {
            Heightt.setText(datta.get(0).getHeight());
            Heightt.setEnabled(false);
        }

        if (datta.get(0).getDepth().equalsIgnoreCase("0") || datta.get(0).getDepth().equalsIgnoreCase("")) {
            if (!depth.equals("") && !depth.equals("0"))
                Depthh.setText(depth);
            else
                Depthh.setText("");
            Depthh.setEnabled(true);
        } else {
            Depthh.setText(datta.get(0).getDepth());
            Depthh.setEnabled(false);
        }

        if (datta.get(0).getWeight().equalsIgnoreCase("0") || datta.get(0).getWeight().equalsIgnoreCase("")) {
            if (!weight.equals("") && !weight.equals("0"))
                Weightt.setText(weight);
            else
                Weightt.setText("");
            Weightt.setEnabled(true);
        } else {
            Weightt.setText(datta.get(0).getWeight());
            Weightt.setEnabled(false);
        }


        if (datta.get(0).getPiece_width().equalsIgnoreCase("0") || datta.get(0).getPiece_width().equalsIgnoreCase("")) {
            if (!width1.equals("") && !width1.equals("0"))
                Widthh1.setText(width1);
            else
                Widthh1.setText("");
                Widthh1.setEnabled(true);
        } else {
            Widthh1.setEnabled(false);
            Widthh1.setText(datta.get(0).getPiece_width());
        }

        if (datta.get(0).getPiece_height().equalsIgnoreCase("0") || datta.get(0).getPiece_height().equalsIgnoreCase("")) {
            if (!heigh1.equals("") && !heigh1.equals("0"))
                Heightt1.setText(heigh1);
            else
                Heightt1.setText("");
                Heightt1.setEnabled(true);
        } else {
                Heightt1.setText(datta.get(0).getPiece_height());
                Heightt1.setEnabled(false);
        }

        if (datta.get(0).getPiece_depth().equalsIgnoreCase("0") || datta.get(0).getPiece_depth().equalsIgnoreCase("")) {
            if (!depth1.equals("") && !depth1.equals("0"))
                Depthh1.setText(depth1);
            else
                Depthh1.setText("");
                Depthh1.setEnabled(true);
        } else {
                Depthh1.setText(datta.get(0).getPiece_depth());
                Depthh1.setEnabled(false);
        }

        if (datta.get(0).getPiece_weight().equalsIgnoreCase("0") || datta.get(0).getPiece_weight().equalsIgnoreCase("")) {
            if (!weight1.equals("") && !weight1.equals("0"))
                Weightt1.setText(weight1);
            else
                Weightt1.setText("");
            Weightt1.setEnabled(true);
        } else {
            Weightt1.setText(datta.get(0).getPiece_weight());
            Weightt1.setEnabled(false);
        }

        Weightt1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus&&CaseQuantityEd.getText().toString().equalsIgnoreCase("0")) {

                }else if (!hasFocus&&CaseQuantityEd.getText().toString().equalsIgnoreCase("1")){
                    UtilsMethods.hideKeyboard(TotalArrivalActivity.this,Weightt1);
                }else if (!hasFocus&&CaseQuantityEd.getText().toString().equalsIgnoreCase("2")){
                    UtilsMethods.hideKeyboard(TotalArrivalActivity.this,Weightt1);
                }else if (!hasFocus&&CaseQuantityEd.getText().toString().equalsIgnoreCase("3")){
                    UtilsMethods.hideKeyboard(TotalArrivalActivity.this,Weightt1);
                }
            }
        });

        if (datta.get(0).getCase_qty().equalsIgnoreCase("0")|| datta.get(0).getCase_qty().equalsIgnoreCase("")){
            if (!CaseQuantity.equals("") && !CaseQuantity.equals("0"))
                CaseQuantityEd.setText(CaseQuantity);
            else
                CaseQuantityEd.setText("");
            CaseQuantityEd.setEnabled(true);
        } else {
            CaseQuantityEd.setText(datta.get(0).getCase_qty());
            CaseQuantityEd.setEnabled(false);
        }


        if (SubmitDisposal){
            disposalspinner.setSelection(DisposalPOS);
            disposalspinner.setEnabled(true);
        }else {

            if (datta.get(0).getDisposal_flag().equalsIgnoreCase("1")) {
                disposalspinner.setSelection(1);
                DisposalPOS = 1;
                disposalspinner.setEnabled(false);
            } else if (datta.get(0).getDisposal_flag().equalsIgnoreCase("2")) {
                disposalspinner.setSelection(2);
                disposalspinner.setEnabled(false);
                DisposalPOS = 2;
            } else if (datta.get(0).getDisposal_flag().equalsIgnoreCase("3")) {
                disposalspinner.setSelection(3);
                disposalspinner.setEnabled(false);
                DisposalPOS = 3;
            } else {
                disposalspinner.setEnabled(true);
                disposalspinner.setSelection(0);
                DisposalPOS = 0;
                UtilsMethods.hideKeyboard(TotalArrivalActivity.this,CaseQuantityEd);
            }

        }

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Widthh.getText().toString().trim().equalsIgnoreCase("") || Widthh.getText().toString().trim().equalsIgnoreCase("0")) {
                    U.beepError(TotalArrivalActivity.this, "幅を記入してください");
                    UtilsMethods.ShakeEditText(Widthh);
                } else if (Heightt.getText().toString().trim().equalsIgnoreCase("") || Heightt.getText().toString().trim().equalsIgnoreCase("0")) {
                    UtilsMethods.ShakeEditText(Heightt);
                    U.beepError(TotalArrivalActivity.this, "高さを入力してください");
                } else if (Depthh.getText().toString().trim().equalsIgnoreCase("") || Depthh.getText().toString().trim().equalsIgnoreCase("0")) {
                    UtilsMethods.ShakeEditText(Depthh);
                    U.beepError(TotalArrivalActivity.this, "深さを入力してください");
                } else if (Weightt.getText().toString().trim().equalsIgnoreCase("") || Weightt.getText().toString().trim().equalsIgnoreCase("0")) {
                    UtilsMethods.ShakeEditText(Weightt);
                    U.beepError(TotalArrivalActivity.this, "重量を入力してください");
                } else if (Widthh1.getText().toString().trim().equalsIgnoreCase("") || Widthh1.getText().toString().trim().equalsIgnoreCase("0")) {
                    U.beepError(TotalArrivalActivity.this, "幅を記入してください");
                    UtilsMethods.ShakeEditText(Widthh1);
                } else if (Heightt1.getText().toString().trim().equalsIgnoreCase("") || Heightt1.getText().toString().trim().equalsIgnoreCase("0")) {
                    UtilsMethods.ShakeEditText(Heightt1);
                    U.beepError(TotalArrivalActivity.this, "高さを入力してください");
                } else if (Depthh1.getText().toString().trim().equalsIgnoreCase("") || Depthh1.getText().toString().trim().equalsIgnoreCase("0")) {
                    UtilsMethods.ShakeEditText(Depthh1);
                    U.beepError(TotalArrivalActivity.this, "深さを入力してください");
                } else if (Weightt1.getText().toString().trim().equalsIgnoreCase("") || Weightt1.getText().toString().trim().equalsIgnoreCase("0")) {
                    UtilsMethods.ShakeEditText(Weightt1);
                    U.beepError(TotalArrivalActivity.this, "重量を入力してください");
                } else if(CaseQuantityEd.getText().toString().trim() .equalsIgnoreCase("0")||CaseQuantityEd.getText().toString().trim().equalsIgnoreCase("")){
                    U.beepError(TotalArrivalActivity.this, "入数を入力してください");
                }else if(Disposaltexts.equalsIgnoreCase("選択なし")){
                    U.beepError(TotalArrivalActivity.this, "選択してください 廃棄区分");
                }else {

                    width = Widthh.getText().toString();
                    heigh = Heightt.getText().toString();
                    depth = Depthh.getText().toString();
                    weight = Weightt.getText().toString();

                    width1 = Widthh1.getText().toString();
                    heigh1 = Heightt1.getText().toString();
                    depth1 = Depthh1.getText().toString();
                    weight1 = Weightt1.getText().toString();
                    Disposaltexts = disposalspinner.getSelectedItem().toString();
                    CaseQuantity = CaseQuantityEd.getText().toString();
                    Log.e(TAG, "qwertyjhgfd   width" + width + " height" + heigh + " depth" + depth + " weight" + weight);
                    SubmitDisposal =true;
                    dialogg.dismiss();
                    CheckArrival();
                }
            }
        });

        dialogg.show();
    }

    public void CheckArrival() {
        // custom dialog

        dialogcheck = new Dialog(TotalArrivalActivity.this);
        dialogcheck.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogcheck.setContentView(R.layout.widthdialog);
        dialogcheck.setCanceledOnTouchOutside(false);
        dialogcheck.setCancelable(false);

        Button update = (Button) dialogcheck.findViewById(R.id.enter);
        Button back = (Button) dialogcheck.findViewById(R.id.back);

        Widthh = (EditText) dialogcheck.findViewById(R.id.Width);
        Heightt = (EditText) dialogcheck.findViewById(R.id.Height);
        Depthh = (EditText) dialogcheck.findViewById(R.id.Depth);
        Weightt = (EditText) dialogcheck.findViewById(R.id.Weight);

        Widthh1 = (EditText) dialogcheck.findViewById(R.id.Width1);
        Heightt1 = (EditText) dialogcheck.findViewById(R.id.Height1);
        Depthh1 = (EditText) dialogcheck.findViewById(R.id.Depth1);
        Weightt1 = (EditText) dialogcheck.findViewById(R.id.Weight1);

        TextView product_code = (TextView) dialogcheck.findViewById(R.id.productcode);
        TextView product_name = (TextView) dialogcheck.findViewById(R.id.productname);
        final TextView disposalcheck = (TextView) dialogcheck.findViewById(R.id.disposalcheck);
       final TextView case_quantity1 = (TextView) dialogcheck.findViewById(R.id.case_quantity1);

      //  Toast.makeText(TotalArrivalActivity.this,Disposaltexts,Toast.LENGTH_SHORT).show();

        disposalcheck.setText(Disposaltexts);
        case_quantity1.setText(CaseQuantity);
        product_code.setText(datta.get(0).getCode());
         product_name.setText(datta.get(0).getProduct_name());
        product_name.setSelected(true);


        TextView type = (TextView) dialogcheck.findViewById(R.id.package_text);
        type.setText(typeSelect);

        Widthh.setText(width);
        Heightt.setText(heigh);
        Depthh.setText(depth);
        Weightt.setText(weight);

        Widthh1.setText(width1);
        Heightt1.setText(heigh1);
        Depthh1.setText(depth1);
        Weightt1.setText(weight1);



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.Show();

                if (disposalcheck.getText().toString().equalsIgnoreCase("不燃")){
                Disposal = "1";
                }else if(disposalcheck.getText().toString().equalsIgnoreCase("リサイクル")){
                    Disposal = "2";
                }else{
                    Disposal = "3";
                }

                UpdateProductRequest req = new UpdateProductRequest(app.getSerial(), adminID, ID, datta.get(0).getProduct_id(), Depthh.getText().toString().trim(), Widthh.getText().toString().trim()
                        , Heightt.getText().toString().trim(), Weightt.getText().toString().trim(), Depthh1.getText().toString().trim(), Widthh1.getText().toString().trim()
                        , Heightt1.getText().toString().trim(), Weightt1.getText().toString().trim(), typePOS + 1 + "", getResources().getString(R.string.version),Disposal,case_quantity1.getText().toString());
                manager.UpdateProduct(req, updaterequest);

                dialogcheck.dismiss();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogcheck.dismiss();
                SubmitDisposal =true;
                UpdateArrival();
            }
        });
        dialogcheck.show();
    }

    @Override
    public void onSucess(int status, ResponseBody message) throws JsonIOException {
        progress.Dismiss();
        Disposaltexts = "";
        Disposal = "";
        DisposalPOS = 0;

    }

    @Override
    public void onSucess(int status, PickedResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")) {
            if (Statusreturn.equalsIgnoreCase("1")) {
                ReturnNyokaID = message.getNyuka_id();
                ReturnOrderNo = message.getOrder_no();
                if (message.getReturn_time() == true) {
                    //Dialog open
                    SubmitTime();

                } else {

                    barcode.setText("");
                    BarcodeScanvalue = "0";
                    startdatta.clear();

                   /* if (cbKey.isChecked()) {
                        Key = "";
                        etKey.setText("");
                        setProc(PROC_PNAME);
                    } else {
                        PName = "";
                        productName.setText("");
                        setProc(PROC_KEY);
                    }*/

                    TotalArrivalAPI();
                }

            } else {
                barcode.setText("");
                BarcodeScanvalue = "0";
                startdatta.clear();

              /*  if (cbKey.isChecked()) {
                    Key = "";
                    etKey.setText("");
                    setProc(PROC_PNAME);
                } else {
                    PName = "";
                    productName.setText("");
                    setProc(PROC_KEY);
                }*/

                TotalArrivalAPI();

            }

        } else if (message.getCode().equalsIgnoreCase("1020")) {
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                        .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(TotalArrivalActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        } else {
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, MylistResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")) {
            //   MylistData
            mydata = message.getNyuka_list();
            showInfo("me", mydata);

        } else if (message.getCode().equalsIgnoreCase("1020")) {
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(TotalArrivalActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        } else {
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, OtherListResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")) {
            //   MylistData
            mydata = message.getNyuka_list();
            showInfo("user", message.getNyuka_list());
        } else if (message.getCode().equalsIgnoreCase("1020")) {
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(TotalArrivalActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        } else {
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, TimeResponse message) throws JsonIOException {
        //werty
        if (message.getCode().equalsIgnoreCase("0")) {
            barcode.setText("");
            BarcodeScanvalue = "0";
            startdatta.clear();

           /* if (cbKey.isChecked()) {
                Key = "";
                etKey.setText("");
                setProc(PROC_PNAME);
            } else {
                PName = "";
                productName.setText("");
                setProc(PROC_KEY);
            }
*/
            TotalArrivalAPI();

        } else if (message.getCode().equalsIgnoreCase("1020")) {
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(TotalArrivalActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        } else {
            barcode.setText("");
            BarcodeScanvalue = "0";
            startdatta.clear();

           /* if (cbKey.isChecked()) {
                Key = "";
                etKey.setText("");
                setProc(PROC_PNAME);
            } else {
                PName = "";
                productName.setText("");
                setProc(PROC_KEY);
            }*/

            TotalArrivalAPI();
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, ReprintResponse message) throws JsonIOException {
        if (message.getCode().equalsIgnoreCase("0")) {
            U.beepKakutei(this, null);
        }
    }

    @Override
    public void onError(int status, ResponseBody error) {
        progress.Dismiss();
        U.beepError(this,error+"");
    }

    @Override
    public void onFaliure() {
        progress.Dismiss();
    }

    @Override
    public void onNetworkFailure() {
        progress.Dismiss();
        Toast.makeText(this, "Intenet not working", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.relLayout1:
                menu.showMenu();
                break;

            case R.id.notif_count_blue:

              /*  if (btnBlue.getText().toString().equalsIgnoreCase("0")) {
                } else {
                    progress.Show();
                    TotalArivalReq req = new TotalArivalReq(app.getSerial(), adminID, ID, Key,
                            PName, "", "", "", "", "", "", "", "", getResources().getString(R.string.version));
                    manager.MyList(req, mylistcall);
                }

                break;

            case R.id.notif_count_red:
                if (btnRed.getText().toString().equalsIgnoreCase("0")) {

                } else {
                    progress.Show();
                    TotalArivalReq reqe = new TotalArivalReq(app.getSerial(), adminID, ID, Key,
                            PName, "", "", "", "", "", "", "", "", getResources().getString(R.string.version));
                    manager.OtherList(reqe, otherlist);
                }
*/
                break;

            default:
                break;
        }
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {

            case PROC_SHOP:
                spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.et_date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;

            case PROC_BARCODE:
                spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.et_date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);

                break;

//            case PROC_DATE:
//
//                spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
//                _gt(R.id.et_date).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
//                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
//                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
//                _gt(R.id.productName).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
//                _gt(R.id.et_date).setFocusableInTouchMode(true);
//                _gt(R.id.et_date).setEnabled(true);
//                //  showTruitonDatePickerDialog(dateselect);
//                break;

        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {

            case PROC_BARCODE:
                String barcode = _gts(R.id.barcode);
                if (barcode.equals("")) {
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }

                if (disableButtonTwoSecs() == false)
                    sendBarcodeRequest();
                else
                    //       Toast.makeText(getApplicationContext(), "Wait...",Toast.LENGTH_SHORT).show();

                    break;

        }
    }


    @Override
    public void scanedEvent(String barcode) {
        Log.e(TAG, "ScannedEventttttt   is " + barcode);

        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEventttttt");

            if (mProcNo == PROC_BARCODE) {
                // check for QR code
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                Log.e(TAG, "Length of barcode is   " + barcode.length());
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
                _sts(R.id.barcode, barcode);

            }

        }
        this.inputedEvent(barcode, true);
    }


    protected boolean showInfo(String status, ArrayList<MylistData> list) {

        if (getPopupWindow2() == null) {

            popupWindow = new PopupWindow(this);

            View popupView = getLayoutInflater().inflate(R.layout.total_pick_popup_list, null);
            popupWindow.setContentView(popupView);

            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));

            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);


            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 324, getResources().getDisplayMetrics());
            popupWindow.setWindowLayoutMode((int) width, WindowManager.LayoutParams.WRAP_CONTENT);
            popupWindow.setWidth((int) width);
            popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            setPopupWindow2(popupWindow);

            ImageView close = (ImageView) getPopupWindow2().getContentView().findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }
        TextView title = (TextView) getPopupWindow2().getContentView().findViewById(R.id.title);
        TextView name_tag = (TextView) getPopupWindow2().getContentView().findViewById(R.id.name_tag);
        ListView lv = (ListView) getPopupWindow2().getContentView().findViewById(R.id.orderPicking);

        Log.e(TAG, "Statussss   " + status);
        if (status.equals("user")) {
            title.setText("All user working List");
            name_tag.setText("Name");
            OtherList(lv, status, mydata);

        } else if (status.equals("me")) {
            title.setText("My List");
            name_tag.setText("Status");
            initMyList(lv, status, mydata);
        }
        // 画面中央に表示
        getPopupWindow2().showAtLocation(findViewById(R.id.barcode), Gravity.CENTER, 0, 32);
        return true;
    }


    protected ListViewItems initMyList(ListView lv, String status, final ArrayList<MylistData> showList) {
        Log.e(TAG, "initListtt");
        lv.setAdapter(null);

        ListViewItems data = new ListViewItems();
        for (int i = 0; i < showList.size(); i++) {
            //  Map<String, String> row = showList.get(i);

            Log.e(TAG, "initListtttttt11111");
            if (showList.get(i).getQuantity().equals("0")) {

                data.add(data.newItem().add(R.id.rf_txt_1, showList.get(i).getPicking_no())
                        .add(R.id.rf_txt_2, showList.get(i).getCode())
                        .add(R.id.rf_txt_3, "Reprint"));
            } else
                data.add(data.newItem().add(R.id.rf_txt_1, showList.get(i).getPicking_no())
                        .add(R.id.rf_txt_2, showList.get(i).getCode())
                        .add(R.id.rf_txt_3, "Working"));

        }

        ListViewAdapter adapterq = new ListViewAdapter(
                getApplicationContext(), data
                , R.layout.total_pick_list_row) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                final TextView reprint = (TextView) v.findViewById(R.id.rf_txt_3);
                if (reprint.getText().equals("Reprint")) {
                    reprint.setBackground(getResources().getDrawable(R.color.yellow));
                } else
                    reprint.setBackground(getResources().getDrawable(R.color.white));

                reprint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (reprint.getText().equals("Reprint")) {
                            sendRequest(position);
                        }
                    }
                });

                return v;
            }
        };

        lv.setAdapter(adapterq);

        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }


    protected ListViewItems OtherList(ListView lv, String status, final ArrayList<MylistData> showList) {
        Log.e(TAG, "initListttt");
        lv.setAdapter(null);

        ListViewItems data = new ListViewItems();
        for (int i = 0; i < showList.size(); i++) {
            //  Map<String, String> row = showList.get(i);
            Log.e(TAG, "initListtt111");

            data.add(data.newItem().add(R.id.rf_txt_1, showList.get(i).getPicking_no())
                    .add(R.id.rf_txt_2, showList.get(i).getCode())
                    .add(R.id.rf_txt_3, showList.get(i).getPicking_user_name()));

        }

        ListViewAdapter adapterqq = new ListViewAdapter(
                getApplicationContext(), data, R.layout.total_pick_list_row) {

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                final TextView reprint = (TextView) v.findViewById(R.id.rf_txt_3);
                if (reprint.getText().equals("Reprint")) {
                    reprint.setBackground(getResources().getDrawable(R.color.yellow));
                } else
                    reprint.setBackground(getResources().getDrawable(R.color.white));

                reprint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (reprint.getText().equals("Reprint")) {
                            //  Map<String, String> map = showList.get(position);
                            //   sendRequest(map);
                        }
                    }
                });

                return v;
            }
        };

        lv.setAdapter(adapterqq);
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }

    //API HIT FOR REPRINT REQUEST
    void sendRequest(int position) {

        String nyuka = mydata.get(position).getNyuka_id();
        String product = mydata.get(position).getProduct_id();

        progress.Show();
        ReprintRequest req = new ReprintRequest(app.getSerial(), adminID, ID, "print", nyuka, product, getResources().getString(R.string.version));
        manager.Reprint(req, reprintcall);
    }

    //timer
    private void Timeset() {

        myCalendar = Calendar.getInstance();
        CalendarHour = myCalendar.get(Calendar.HOUR_OF_DAY);
        CalendarMinute = myCalendar.get(Calendar.MINUTE);

        timepickerdialog = new TimePickerDialog(TotalArrivalActivity.this,
        new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (hourOfDay == 0) {
                            hourOfDay += 12;
                        } else if (hourOfDay == 12) {

                        } else if (hourOfDay > 12) {
                            hourOfDay -= 12;
                        } else {

                        }

                        String q = hourOfDay + ":" + minute;
                        Toast.makeText(TotalArrivalActivity.this, q, Toast.LENGTH_SHORT).show();
                        timee.setText(hourOfDay + ":" + minute);
                        // TimeValue = txttimeecg.getText().toString();

                    }
                }, CalendarHour, CalendarMinute, true);
        timepickerdialog.show();

    }

    public void SubmitTime() {

        final Dialog timedia = new Dialog(TotalArrivalActivity.this);
        timedia.requestWindowFeature(Window.FEATURE_NO_TITLE);
        timedia.setContentView(R.layout.dialogtime);
        timedia.setCancelable(false);
        timedia.setCanceledOnTouchOutside(false);

        ImageView cancel = (ImageView) timedia.findViewById(R.id.cancel);
        Button submit = (Button) timedia.findViewById(R.id.submit);
        timee = (TextView) timedia.findViewById(R.id.time);

        timee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timeset();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress.Show();
                TimeRequest req = new TimeRequest(app.getSerial(), adminID, ID, ReturnNyokaID, ReturnOrderNo,
                timee.getText().toString());
                manager.ReturnTime(req, timesubmit);

                timedia.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timedia.dismiss();
            }
        });

        timedia.show();
    }

    @Override
    public void onBackPressed() {
    }

    public boolean disableButtonTwoSecs() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
            return true;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        return false;
    }
}
