package com.itechvision.ecrobo.pickman.Chatman.Tshipping;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxCashList;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxCashOrderIDRequest;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxCashOrderIDResp;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxList.BoxListData;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxList.BoxListRequest;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxList.BoxListResp;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.FixReq.PickProductReq;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.FixReq.PickProductRes;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.GetBoxNoResponse;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.NextBoxReq;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Packing.GetPackingListResponse;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Packing.PackListData;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.WeightConnect.BtSocketService;
import com.itechvision.ecrobo.pickman.WeightConnect.ConfigActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class BoxCashRegisterActivity extends BaseActivity implements DataManager.GetOrderIDCallback, DataManager.GetBoxListCallback, DataManager.SetPickProductCallback, DataManager.GetBoxNoCallback , DataManager.GetPackingListcallback {
    static SlidingMenu menu;
    SlideMenu slidemenu;

 /*   @BindView(R.id.actionbar)
    LinearLayout actionbar;*/
    @BindView(R.id.menu_drawer)
    ImageView menuDrawer;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.orderId)
    EditText orderId;
    @BindView(R.id.boxsizespiner)
    EditSpinner boxsizespiner;
    @BindView(R.id.Cbox) EditText cbox;
//    @BindView(R.id.koguchi) EditSpinner koguchi;

    @BindView(R.id.barcode) EditText barcode;
    @BindView(R.id.quantity) EditText quantity;
    @BindView(R.id.productName) TextView productName;
    @BindView(R.id.nextBox)
    Button nextboxButton;
    @BindView(R.id.totalQuantity) TextView totalQuantity;
    @BindView(R.id.btnblue) Button btnBlue;
    @BindView(R.id.btnred) Button btnRed;
    @BindView(R.id.notif_count_yellow) Button btnyellow;
    @BindView(R.id.btnGry) Button btnGrey;
    @BindView(R.id.keyboard) Button keyboard;

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.boxspinnerlayout) RelativeLayout boxspinnerlayout;

    @BindView(R.id.weightLayout) LinearLayout weightLayout;
    @BindView(R.id.weight) EditText weight;
    @BindView(R.id.realweight) EditText realweight;
    @BindView(R.id.boxRadiogroup)
    RadioGroup radioGroup;
    @BindView(R.id.box_rb)
    RadioButton boxRadioButton;
    @BindView(R.id.poly_rb) RadioButton polyRadioButton;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
//    @BindView(R.id.kochienter) Button kochienter;
//    @BindView(R.id.clear_kogu) TextView clear_kogu;
    @BindView(R.id.layout_main)
    RelativeLayout mainlayout;
    @BindView(R.id.layout_number)
    RelativeLayout layoutnumber;
    @BindView(R.id.connect) Button mButton01;
    @BindView(R.id.Spinner01) Spinner mSpinner01;

    protected int mProcNo = 0;
    public final int PROC_ORDERID = 1;
    public final int PROC_BOXSIZE = 2;
    public final int PROC_CBOX = 3;
    public final int PROC_KOGUCHI = 4;
    public final int PROC_BARCODE = 5;
    public final int PROC_QUANTITY = 6;
    public final int PROC_WEIGHT = 7;
    public static final int PACKING_ACTIVITY = 111;

    String is_polythene = "0";

    private String TAG = BoxCashRegisterActivity.class.getSimpleName();

    ECRApplication app = new ECRApplication();

    DataManager manager;
    progresBar progress;

    private DataManager.GetOrderIDCallback getOrderIDCallback;
    private DataManager.GetBoxListCallback getBoxListCallback;
    private DataManager.SetPickProductCallback setPickProductCallback;
    private DataManager.GetPackingListcallback getPackingListcallback;
    private DataManager.GetBoxNoCallback getBoxNoCallback;

    public TextToSpeak mTextToSpeak;
    private boolean showKeyboard;

    public Context mcontext = this;
    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPrefs";
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    public String adminID = "", ID = "";
    private boolean visible = false;

    public static final String ADDNEXTBOX = "add_next_box_action";
    public static final String NEXTBOX = "next_button";
    public static final String BACK = "back_button";

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_CONNECTION_LOST = 5;
    public static final int MESSAGE_TOAST = 6;
    public static final int MESSAGE_TIMEOUT = 7;
    public static final int MESSAGE_SETTING = 8;
    public static final int MESSAGE_RECONNECT = 9;
    // BtSocketService のハンドラから受け取るキー名
    public static final String DEVICE_NAME = "device_name";
    public static final String DEFAULT_NAME = "default_name";
    public static final String TOAST = "toast";
    public static final String SETTING = "setting";
    // インテントリクエストコード
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_CONFIG_BT = 2;
    // 接続デバイス名
    private String mConnectedDeviceName = null;
    // 接続デバイスのデフォルト名(工場出荷時)
    private String mConnectedDefaultName = null;
    // 送信データバッファ文字列
    private StringBuffer mOutStringBuffer;
    // 受信データバッファ文字列
    private StringBuffer mInStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBtAdapter;
    // Local Bluetooth devices
    private BluetoothDevice[] mBtDevices;
    // ペアリングされているデバイスのための Array adapter
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    // ソケットサービス
    private BtSocketService mSocketService = null;
    private boolean mNeedPairing = false;
    private static final boolean D = true;

    int res;
    String qnt = "0", rounded = "",voice="";
    public String _lastUpdateQty = "0";
    private String koguchi_count= "";
    String selectedboxsize = "";
    String selectedboxid = "";
    String BarcodeScaned = "",Realbarcode="";


    private final int _singleItemRes = R.layout.spinner_layout_space;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;

    ArrayList<String> sizes = new ArrayList<>();
    public String size = "", mBoxNo = "0";
    int post = 0 ,eop;
    String shipFlag = "";
    boolean nextPress = false;
    public String isNextBarcode = "";
    private boolean is_scan = false;

    boolean nextBox= false, complete =  false, clear=false , menuOpen =  false;


    ViewGroup hiddenPanel;

    //    public static final int PACKING_ACTIVITY = 111;
    ArrayList<BoxCashList> productList = new ArrayList<>();
    public static ArrayList<PackListData> packingList = new ArrayList<>();
    ArrayList<BoxListData> boxList = new ArrayList<>();
    public List<BoxCashList> packData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_cash_register);

        ButterKnife.bind(this);
        mTextToSpeak = new TextToSpeak(this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        progress = new progresBar(this);
        manager = new DataManager();
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);

        getOrderIDCallback = this;
        getBoxListCallback = this;
        getBoxNoCallback =this;
        setPickProductCallback = this;
        getPackingListcallback = this;

        adminID = spDomain.getString("admin_id", null);
        ID = BaseActivity.getShopId();
        Log.d(TAG,"On Create ");
        hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);


        showKeyboard = sharedPreferences.getBoolean("ShowKeyboard", false);

        if (showKeyboard == true) {
            visible = true;
            keyboard.setText("キーボードを隠す");

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);

            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layoutnumber.getLayoutParams();
            param.setMargins(0, top, 0, 0);

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        }
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        // 取得できない場合は Bluetoothがサポートされていないためアプリを終了する。
        if (mBtAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        mButton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mSpinner01.getSelectedItemPosition();

                if (mBtDevices.length == 0) {
                    Toast.makeText(BoxCashRegisterActivity.this, R.string.error_msg01, Toast.LENGTH_SHORT).show();
                    return;
                }
                // デバイスを接続しようとしている場合は何もしない
                if (mSocketService.getState() == BtSocketService.STATE_CONNECTING) {
                    return;
                }
                // 接続済みで接続済みのデバイスを接続しようとしている場合は何もしない
                if (mSocketService.getState() == BtSocketService.STATE_CONNECTED &&
                        mConnectedDeviceName.equals(mBtDevices[index].getName())) {
                    Toast.makeText(BoxCashRegisterActivity.this, R.string.already_connected, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (D) Log.d(TAG, "addrss:" + mBtDevices[index].getAddress());
                // デバイスへ接続する
                mSocketService.connect(mBtDevices[index], true);

            }
        });

        // Bluetoothが無効になっている場合 有効になるようリクエストする
        if (!mBtAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            // mSocketServiceを取得していない場合 setupMain()を実行する
            if (mSocketService == null) setupMain();
        }

        if (mProcNo == 0) {
            nextProcess();
        }
    }
    @OnClick(R.id.menu_drawer)
    void menu() {
        menu.showMenu();
    }

    @OnClick(R.id.cbox_clear)void CBoxClear(){
        cbox.setText("");
        setProc(PROC_CBOX);
    }

    public void AddLayout(View view) {

        if (visible == false) {

            visible = true;
            keyboard.setText(R.string.hideKeyboard);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layoutnumber.getLayoutParams();
            param.setMargins(0, top, 0, 0);

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;
            keyboard.setText(R.string.showkeyboard);
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

    public void BoxRadio(View view){
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.box_rb:
                is_polythene = "0";
                break;
            case R.id.poly_rb:
                is_polythene = "1";
                break;
        }
    }


    @OnClick(R.id.btnblue)
    void BlueBtn(View view) {
     /*   if (!btnBlue.getText().toString().equalsIgnoreCase("0")) {
            if (!CommonUtilities.getConnectivityStatus(this))
                CommonUtilities.openInternetDialog(this);
            else {
                progressBar.Show();
                manager.OrderDetailAPI(PrefsManager.getToken(this), orderDetail);
            }
        }*/
    }


    @OnClick(R.id.btnGry)
    void GreyBtn(View view) {
        if (!btnGrey.getText().toString().equalsIgnoreCase("0")) {
            if (!CommonUtilities.getConnectivityStatus(this))
                CommonUtilities.openInternetDialog(this);
            else {
                progress.Show();
                BoxCashOrderIDRequest req =  new BoxCashOrderIDRequest(adminID,app.getSerial(), ID, _gts(R.id.orderId),getResources().getString(R.string.version));


                manager.GetPackingListAPI(req, getPackingListcallback);
            }
        }
    }


//    @OnClick(R.id.clear_kogu)
//    void clear_kogu() {
//        setProc(PROC_KOGUCHI);
//        koguchi.setText("Select Koguchi");
//    }

    @OnClick(R.id.nextBox)
    void nextBox() {
        if(orderId.getText().toString().equalsIgnoreCase(""))
        {

        }
        else if (barcode.getText().toString().equalsIgnoreCase("")) {
//              U.beepError(this,"バーコードが必要です。");
            nextPress = true;
            U.TasNewBeep(this, null);

            setOnWeight();
        } else if (barcode.getText().toString().equalsIgnoreCase(productList.get(post).getBarcode()) || barcode.getText().toString().equalsIgnoreCase(productList.get(post).getCode())) {
            U.TasNewBeep(this, null);
            nextPress = true;
            fixedRequest("");

        } else {
            nextPress = true;
            setOnWeight();
        }
    }

    void setOnWeight(){
        if(complete)
            U.beepError(this,"バーコードが必要です。");
        if (shipFlag.equalsIgnoreCase("1")) {
            nextPress = true;
            setProc(PROC_WEIGHT);
            nextboxButton.setEnabled(false);
        } else
            callNextBox();

    }




    public void callNextBox(){
        mTextToSpeak.onStopSpeaking();
        if (!CommonUtilities.getConnectivityStatus(this))
            CommonUtilities.openInternetDialog(this);

        else {
            String we = "";
            if(shipFlag.equalsIgnoreCase("1") )
                we = weight.getText().toString();

            progress.Show();
            NextBoxReq req  = new NextBoxReq(adminID,app.getSerial(), ID, orderId.getText().toString(),we);
            manager.GetBoxNo(req, getBoxNoCallback);
        }
    }


    public void setProc(int procNo) {
        mProcNo = procNo;
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());

        switch (procNo) {
            case PROC_ORDERID:
                orderId.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                orderId.setFocusableInTouchMode(true);
                boxspinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                cbox.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                barcode.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                quantity.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                weight.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                CommonUtilities.scrollToView(scrollView, orderId);
                break;

            case PROC_BOXSIZE:
                boxspinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                boxspinnerlayout.setFocusableInTouchMode(true);
                orderId.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                cbox.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                barcode.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                quantity.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                weight.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                CommonUtilities.scrollToView(scrollView, boxspinnerlayout);
                break;

            case PROC_CBOX:
                cbox.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                cbox.setFocusableInTouchMode(true);
                orderId.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                barcode.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                quantity.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                boxspinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                weight.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                CommonUtilities.scrollToView(scrollView, cbox);
                break;


            case PROC_BARCODE:
                barcode.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                barcode.setFocusableInTouchMode(true);
                orderId.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                cbox.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                quantity.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                boxspinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                weight.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                CommonUtilities.scrollToView(scrollView, barcode);
                break;

            case PROC_QUANTITY:
                quantity.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                quantity.setFocusableInTouchMode(true);
                orderId.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                cbox.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                barcode.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                boxspinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                weight.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                CommonUtilities.scrollToView(scrollView, quantity);
                if(quantity.getText().toString().equalsIgnoreCase(""))
                    quantity.setText("1");
                break;
            case PROC_WEIGHT:
                barcode.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                orderId.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                cbox.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                quantity.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                boxspinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                CommonUtilities.scrollToView(scrollView, weight);

                weight.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                weight.setFocusableInTouchMode(true);
                mInStringBuffer = new StringBuffer("");
                mBuff.delete(0, mBuff.length());
        }
    }

    public void inputedEvent(String buff,boolean isScaned) {
        switch (mProcNo) {
            case PROC_ORDERID:    // 注文ID
                String order = orderId.getText().toString();
                if ("".equals(order)) {
                    U.beepError(this, "注文IDは必須です");
                    orderId.setFocusableInTouchMode(true);
                    break;
                }
                if (!CommonUtilities.getConnectivityStatus(this))
                    CommonUtilities.openInternetDialog(this);
                else {
                    progress.Show();
                    BoxCashOrderIDRequest req =  new BoxCashOrderIDRequest(adminID,app.getSerial(), ID, order,getResources().getString(R.string.version));
                    manager.GetBoxOrderID(req, getOrderIDCallback);
                }
                break;
        /*    case PROC_KOGUCHI:
                String val = koguchi.getText().toString();
                if ("".equals(val) || "0".equalsIgnoreCase(val)) {
                    U.beepError(this, "個口を入力してください。");
                    koguchi.setFocusableInTouchMode(true);
                    break;
                }
                if (!U.isNumber(val)) {
                    U.beepError(this, "個口を数字のみで入力してください。");
                    koguchi.setFocusableInTouchMode(true);
                    break;
                }
                if(koguchi_count.equalsIgnoreCase(val)){

                }
                else{
                    *//*if (!CommonUtilities.getConnectivityStatus(BoxCashRegisterActivity.this))
                        CommonUtilities.openInternetDialog(BoxCashRegisterActivity.this);
                    else{
                        progressBar.Show();
                        koguchi_count = val;
                        KoguchiReq req = new KoguchiReq(orderId.getText().toString(), koguchi_count);
                        manager.SetKoguchi(PrefsManager.getToken(BoxCashRegisterActivity.this), req,setKoguchiCallback);
                    }*//*
                }
                // setProc(PROC_BARCODE);
                break;*/
            case PROC_CBOX:
                String cbox1 = cbox.getText().toString();
                if ("".equals(cbox1)) {
                    U.beepError(this, "箱番号を入力してください。");
                    cbox.setFocusableInTouchMode(true);
                    break;
                }
//                setKoguchiSpinner();
                // setProc(PROC_KOGUCHI);
                setProc(PROC_BARCODE);
                break;
            case PROC_BARCODE:
                String BaRcode = _gts(R.id.barcode);
                Realbarcode = _gts(R.id.barcode);

                String Barcode = CommonUtilities.RemovebarcodeAl(BaRcode);
                barcode.setText(Barcode);

                if ("".equals(Barcode)) {
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }

                boolean match = false;

                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getBarcode().equalsIgnoreCase(Barcode) || productList.get(i).getCode().equalsIgnoreCase(Barcode)) {
                      /*   if (productList.get(i).getQuantity().equals(productList.get(i).getScanned_qty())){
                           match = false;
                           continue;
                       }*/

                        startTimer();
                        match = true;
                        post = i;
                        break;
                    }
                }
                if (match) {
                    productName.setText(productList.get(post).getProduct_name());
                    totalQuantity.setText(productList.get(post).getQuantity());

                    nextWork();
                } else {
                    U.beepError(this, "データが見つかりません");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }
                break;

            case PROC_QUANTITY:
                String qty = _gts(R.id.quantity);
                if(qty.equalsIgnoreCase("")) {
                    qty = "0";
                }

                if (isScaned) {

                    if (buff.equals(_gts(R.id.barcode))) {

                        if (!BarcodeScaned.equals(qty))
                            BarcodeScaned = qty;

                        BarcodeScaned = U.plusTo(BarcodeScaned, "1");
                        qty = U.plusTo(qty, "1");

                        Log.e(TAG,"BarcodeScannedd   "+ BarcodeScaned);
                        //increase qunatity in mpackdata
                        if (Integer.parseInt(BarcodeScaned) <= Integer.parseInt(totalQuantity.getText().toString())) {

                            _sts(R.id.quantity, qty);
                            if (Integer.parseInt(qty) > 1)
                                mTextToSpeak.startSpeaking(qty);

                            _lastUpdateQty = _gts(R.id.quantity);
                            //   check if update in quantity need next action
                            if (BarcodeScaned.equals(totalQuantity.getText().toString())) {
                                //  productList.get(post).setScanned_qty(BarcodeScaned);
                                fixedRequest( "");

                            }
                        }
                    } else {
                        //  productList.get(post).setScanned_qty(BarcodeScaned);
                        if ("".equals(qty) || "0".equalsIgnoreCase(qty)) {
                            U.beepError(this, "数量は必須です");
                            _gt(R.id.quantity).setFocusableInTouchMode(true);
                            break;
                        }
                        fixedRequest(buff);
                    }
                } else {
                    if ("".equals(qty) || "0".equalsIgnoreCase(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                    String processedCnt = BarcodeScaned;
                    String qtyUpdate = U.minusTo(qty, _lastUpdateQty);
                    String maxQty_ = U.minusTo(productList.get(post).getQuantity(), processedCnt);

                /*  if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());
                        _sts(R.id.quantity, "1");
                        BarcodeScaned = productList.get(post).getScanned_qty();
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else

                */
                    if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        U.beepError(this, "数量が多すぎます");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        _sts(R.id.quantity, "1");

                        BarcodeScaned = "1";
                        break;
                    } else {
                        if (Integer.parseInt(qty) > 1)
                            mTextToSpeak.startSpeaking(_gts(R.id.quantity));

                        BarcodeScaned = _gts(R.id.quantity);

                        if (BarcodeScaned.equals(totalQuantity.getText())) {
                            fixedRequest( "");
                        } else {
                            fixedRequest( "");
                        }
                    }
                }
                break;
            case PROC_WEIGHT:
                String Weight1 = _gts(R.id.weight);
                if (isScaned) {
                    if ("".equals(Weight1)) {
                        U.beepError(this, "重量を入力");
                        _gt(R.id.weight).setFocusableInTouchMode(true);
                        break;
                    } else {
                        //Api
                    }
                } else {
                    if ("".equals(Weight1)) {
                        U.beepError(this, "重量を入力");
                        _gt(R.id.weight).setFocusableInTouchMode(true);
                        break;
                    } else {

                        try {
                            String a ="";
                            double b ;
                            int weight12  ;
                            a=   Weight1 ;
                            //  a= mInStringBuffer.toString() ;
                            b = Double.parseDouble(a);
                            rounded = String.format("%.0f", b);
                            weight12 = Integer.parseInt(rounded);
                            res = weight12+ 6;

                            weight.setText(String.valueOf(res));
                            realweight.setText(String.valueOf(weight12));

                            voice = String.valueOf(weight12);
                            mTextToSpeak.startSpeaking(voice);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                        if(nextPress)
                            callNextBox();
                        else
                            callNextBox();
                    }
                }
                break;
        }
    }



    private void fixedRequest( String nextBarcode) {
        /*productList.get(post).setScanned_qty(BarcodeScaned);*/
        isNextBarcode = nextBarcode;
        complete = false;

        stopTimer();
        if (is_scan == false )
            createNewPackItem();

        String barc = "";

        if (!CommonUtilities.getConnectivityStatus(this))
            CommonUtilities.openInternetDialog(this);

        else{
            stopTimer();
            progress.Show();
            PickProductReq req = new PickProductReq(adminID, app.getSerial(),  ID,_gts(R.id.orderId), productList.get(post).getProduct_id(),_gts(R.id.quantity),  productList.get(post).getProduct_stock_history_id(),timeTaken().toString(),cbox.getText().toString(), "" ,is_polythene,mBoxNo,getResources().getString(R.string.version));
            manager.SetPickProduct(req, setPickProductCallback);
        }
    }

    private void setSpinner() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Select Box Size");
        int pos = 0;
        for (int i = 0; i< boxList.size(); i++){
            list.add(boxList.get(i).getSize()+"   "+ boxList.get(i).getEms_box_code());
            if(!getBoxid().equals("") && getBoxid().equals(boxList.get(i).getEms_box_code())){
                pos = i+1;
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this,_singleItemRes ,  list) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };

        adapter.setDropDownViewResource(_dropdownRes);
        boxsizespiner.setAdapter(adapter);

        boxsizespiner.setText(list.get(pos));
        if(pos>0){
            cbox.setText(getBoxid());

        }

        boxsizespiner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    setBoxSize(list.get(position));
                    setBoxId(boxList.get(position-1).getEms_box_code());
                    size = "";
                    cbox.setText(getBoxid());
//                    setKoguchiSpinner();
                    //setProc(PROC_KOGUCHI);
                    setProc(PROC_BARCODE);
                }
            }
        });
    }


    public void createNewPackItem() {
        boolean repeat = false;
        int target =0;
        int productqnt =0;
        String QTY ="";
        if ( quantity.getText().toString().equals("")){
            QTY= "0";
        }else{
            QTY =quantity.getText().toString() ;
        }
        target = Integer.parseInt(QTY);
        productqnt = Integer.parseInt( productList.get(post).getQuantity());
        if (target <= productqnt) {
            if (packData.size() > 0) {
                String _b1 =  productList.get(post).getCode();
                String _loc1 =  productList.get(post).getLocation();
                String box =  cbox.getText().toString();

                for ( int i =0; i<packData.size();i++) {
                    String _b = packData.get(i).getCode();
                    String _loc = packData.get(i).getLocation();

                    String _box = packData.get(i).getBoxCode();
                    StringBuffer temp_qty = new StringBuffer();

                    if (_b.equals(_b1) &&  box.equalsIgnoreCase(_box)) {

                        try {
                            if (packData.get(i).getPacked_qty().equals("")){
                                packData.get(i).setPacked_qty("0");
                            }
                            String qnty = U.plusTo(packData.get(i).getPacked_qty(), QTY);

                            packData.get(i).setPacked_qty(qnty);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        repeat = true;
                    }
                }

            }

            if (repeat == false) {

                BoxCashList mPackItem =  productList.get(post);

                mPackItem.setBoxNo(String.valueOf(mBoxNo));
                mPackItem.setBoxCode(cbox.getText().toString());
                mPackItem.setPacked_qty(_gts(R.id.quantity));

                packData.add(mPackItem);
            }

            is_scan = true;
        }
        Log.e(TAG, "packData" + packData);

    }

    public void remove(){
        if (packData.size() > 0) {
            String _c1 =  productList.get(post).getCode();
            String _b1 = barcode.getText().toString();
            String box =  cbox.getText().toString();

            for ( int i =0; i<packData.size();i++) {
                String _c = packData.get(i).getCode();
                String _b = packData.get(i).getBarcode();
                String _box = packData.get(i).getBoxCode();
                if(_b1.equalsIgnoreCase(_b) || _b1.equalsIgnoreCase(_c)){
                    if (_c.equals(_c1) && box.equalsIgnoreCase(_box)) {
                        packData.remove(i);
                        break;
                    }
                }
            }
            is_scan = false;
        }
    }

    public void setBoxSize(String size) {
        this.selectedboxsize = size;
    }

    public String getBoxSize() {
        return this.selectedboxsize;
    }

    public void setBoxId(String id) {
        this.selectedboxid = id;
    }

    public String getBoxid() {
        return this.selectedboxid;
    }

    public void nextWork () {

        BarcodeScaned= productList.get(post).getScanned_qty();
        BarcodeScaned = U.plusTo(productList.get(post).getScanned_qty(),"1");
        _sts(R.id.quantity,"1");


        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking(_gts(R.id.quantity));

        _lastUpdateQty = _gts(R.id.quantity);
        setProc(PROC_QUANTITY);

        if (BarcodeScaned.equals(productList.get(post).getQuantity())) {
            if(!is_scan)
                createNewPackItem();
            fixedRequest("");

        }
    }

    @Override
    public void inputedEvent(String buff) {
      inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        if ( packData.size() > 0) {
            new AlertDialog.Builder(this)
                    .setTitle("現在のBoxをパッキングリスト作成 ？")
                    .setPositiveButton("する", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clear = true;
                            callNextBox();
                        }
                    })
                    .setNegativeButton("しない", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO cancel clear
                            mTextToSpeak.startSpeaking("clear");
                            setBoxSize("");
                            setBoxId("");
                            boxsizespiner.setText("");
                            boxsizespiner.setAdapter(null);
                            nextProcess();
                        }
                    })
                    .show();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Clear？")
                    .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mTextToSpeak.startSpeaking("clear");
                            setBoxSize("");
                            setBoxId("");
                            boxsizespiner.setText("");
                            boxsizespiner.setAdapter(null);
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
            case PROC_ORDERID:
                orderId.setText(buff);
                break;
            case PROC_BOXSIZE:
                boxsizespiner.setText(buff);
                break;
            case PROC_CBOX:
                setBoxId("");
                setBoxSize("");
                cbox.setText(buff);
                break;
            case PROC_BARCODE: // 数量
                barcode.setText(buff);
                break;
        /*    case PROC_KOGUCHI: // 数量
                koguchi.setText(buff);
                break;*/
            case PROC_QUANTITY:
                quantity.setText(buff);
                break;
            case PROC_WEIGHT:
                weight.setText(buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
      if(!progress.isShowing()){
        if (!barcode.equals("")) {
            if (mProcNo == PROC_ORDERID) {
                orderId.setText(barcode);
            }
            else if (mProcNo == PROC_CBOX) {
                cbox.setText(barcode);
            }
            else if (mProcNo == PROC_BARCODE) {
                this.barcode.setText(barcode);
            }
            else if (mProcNo == PROC_QUANTITY) {
                String Barcode = CommonUtilities.RemovebarcodeAl(barcode);
                barcode= Barcode;
            }
            else if (mProcNo == PROC_WEIGHT) {
                this.weight.setText(barcode);
            }
            this.inputedEvent(barcode, true);

        }} else {

            Toast.makeText(getApplicationContext(), "Please Wait", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextProcess() {

        orderId.setText("");
        barcode.setText("");
        quantity.setText("");
        totalQuantity.setText("");

        cbox.setText("");
        productName.setText("");
        weight.setText("");
        realweight.setText("");
        weightLayout.setVisibility(View.GONE);
        nextboxButton.setEnabled(true);


        boxsizespiner.setAdapter(null);


        size = "" ;
        koguchi_count= "";
        post = 0;
        isNextBarcode = "";
        is_scan = false;
        nextBox =  false;

        complete = false;
        clear = false;

        btnRed.setText("0");
        btnGrey.setText("0");
        btnyellow.setText("0");
        btnBlue.setText("0");

        if(showKeyboard){
            if (visible == false) {

                visible = true;
                keyboard.setText(R.string.hideKeyboard);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
                int bottom = convert(200);
                int top = convert(10);
                params.setMargins(0, 0, 0, bottom);
                RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layoutnumber.getLayoutParams();
                param.setMargins(0, top, 0, 0);

                mainlayout.setLayoutParams(params);
                Animation bottomUp = AnimationUtils.loadAnimation(this,
                        R.anim.bottom_up);

                hiddenPanel.startAnimation(bottomUp);
                hiddenPanel.setVisibility(View.VISIBLE);

            } else {
                visible = false;
                keyboard.setText(R.string.showkeyboard);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
                params.setMargins(0, 0, 0, 10);
                Animation bottomDown = AnimationUtils.loadAnimation(this,
                        R.anim.bottom_down);

                hiddenPanel.startAnimation(bottomDown);
                hiddenPanel.setVisibility(View.INVISIBLE);

                mainlayout.setLayoutParams(params);
            }
        }

        if (!packData.isEmpty())
            packData.clear();
        productList.clear();

        setProc(PROC_ORDERID);

//            progressBar.Show();
//            manager.OrderCountAPI(PrefsManager.getToken(this), orderCount);

    }

    void nextProcess1(){
        barcode.setText("");
        quantity.setText("");
        totalQuantity.setText("");

        if(getBoxid().equalsIgnoreCase(""))
            cbox.setText("");
        productName.setText("");

        boxsizespiner.setAdapter(null);

        if (!packData.isEmpty())
            packData.clear();

        nextBox =  false;

    }


    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        if(barcode.equalsIgnoreCase(""))
        {
            mBuff.delete(0, mBuff.length());
        }
        switch (mProcNo) {
            case PROC_ORDERID:    // バーコード
                _sts(R.id.orderId, barcode);
                break;
            case PROC_BOXSIZE:
                _sts(R.id.boxsizespiner, barcode);
                break;
            case PROC_CBOX:
                _sts(R.id.Cbox, barcode);
                break;
          /*  case PROC_KOGUCHI:
                _sts(R.id.koguchi, barcode);
                break;*/
            case PROC_BARCODE:
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QUANTITY:
                _sts(R.id.quantity, barcode);
                break;
            case PROC_WEIGHT:
                _sts(R.id.weight, barcode);
                break;
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        if (D) Log.e(TAG, "++ ON START ++");
        // Bluetoothが無効になっている場合 有効になるようリクエストする
        if (!mBtAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            // mSocketServiceを取得していない場合 setupMain()を実行する
            if (mSocketService == null) setupMain();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if (D) Log.e(TAG, "+ ON RESUME +");
        if (mSocketService != null) {
            // stateがSTATE_NONEの場合 mSocketService.start() を実行する
            if (mSocketService.getState() == BtSocketService.STATE_NONE) {
                mSocketService.start();
            }
        }
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if (D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mSocketService != null) mSocketService.stop();
        mTextToSpeak.resetQueue();

        if (D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mSocketService != null) mSocketService.stop();

        mTextToSpeak.resetQueue();
        mTextToSpeak.onStopSpeaking();
    }

    private void setupMain() {
        // レイアウトビュー
        mButton01 = (Button) findViewById(R.id.connect);
        mSpinner01 = (Spinner) findViewById(R.id.Spinner01);

        // 現在のペアリングデバイスの set コレクションを取得

        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.arrival_spinner_item);
        mPairedDevicesArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        //コレクションからBluetoothDeviceの配列に変換
        mBtDevices = (BluetoothDevice[]) pairedDevices.toArray(new BluetoothDevice[0]);
        // ペアリングデバイスをArrayAdapterへ追加
        if (pairedDevices.size() > 0) {
            for (int i = 0; i < mBtDevices.length; i++) {
                mPairedDevicesArrayAdapter.add(mBtDevices[i].getName());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }

        mSpinner01.setAdapter(mPairedDevicesArrayAdapter);
        mSpinner01.setSelection(0);
        //mButton01.setOnClickListener(this);
        // bluetooth接続のためにBtSocketServiceを取得
        mSocketService = new BtSocketService(this, mHandler);

        // データ送受信バッファの初期化
        mOutStringBuffer = new StringBuffer("");
        mInStringBuffer = new StringBuffer("");
    }


    private void initConfig() {
        // 接続状態であるかどうかを確認
        if (mSocketService.getState() != BtSocketService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        // デバイス電源をOFFにする前にいったんソケットを切断する
        mSocketService.start();
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(getText(R.string.alert_before) + "\n" + getText(R.string.alert_poweron))
                .setPositiveButton(R.string.alert_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int index = mSpinner01.getSelectedItemPosition();
                                // コマンドモードへ移行するための処理を実行
                                mSocketService.entercmd(mBtDevices[index]);
                            }
                        }
                )
                .setNegativeButton(R.string.alert_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int index = mSpinner01.getSelectedItemPosition();
                                // キャンセルされたためソケットを再接続する
                                mSocketService.connect(mBtDevices[index], true);
                            }
                        }
                )
                .show();
    }

    /**設定完了手続きのためにアラートダイアログ表示*/
    private void exitConfig() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(getText(R.string.alert_after) + "\n" + getText(R.string.alert_poweron))
                .setPositiveButton(R.string.alert_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int index = mSpinner01.getSelectedItemPosition();
                                if (mNeedPairing) {
                                    pairDevice(mBtDevices[index]);
                                }
                                // 設定が完了したためソケットを再接続する
                                mSocketService.connect(mBtDevices[index], true);
                            }
                        }
                )
                .show();
    }

    /**
     * 接続エラーのためにアラートダイアログ表示
     */
    private void alertFailedConnection() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(getText(R.string.error_msg02) + "\n" + getText(R.string.error_msg03))
                .setPositiveButton(R.string.alert_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                )
                .show();
    }

    /**
     * PAIRING_REQUEST アクティビティーの開始
     *
     * @param device The BluetoothDevice to connect.
     */
    public void pairDevice(BluetoothDevice device) {
        String ACTION_PAIRING_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST";
        Intent intent = new Intent(ACTION_PAIRING_REQUEST);
        String EXTRA_DEVICE = "android.bluetooth.device.extra.DEVICE";
        intent.putExtra(EXTRA_DEVICE, device);
        String EXTRA_PAIRING_VARIANT = "android.bluetooth.device.extra.PAIRING_VARIANT";
        int PAIRING_VARIANT_PIN = 0;
        intent.putExtra(EXTRA_PAIRING_VARIANT, PAIRING_VARIANT_PIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void sendMessage(String message) {
        // 接続状態であるかどうかを確認
        if (mSocketService.getState() != BtSocketService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // 送信データがあるかどうかを確認
        if (message.length() > 0) {
            // バイトデータ取得、mSocketService.write() を実行
            byte[] send = message.getBytes();
            mSocketService.write(send);

            // 送信データバッファをリセット、送信データ用UIの更新
            mOutStringBuffer.setLength(0);
            //    mEditText01.setText(mOutStringBuffer);
        }
    }


    private void startConfigActivity(Message msg) {
        Intent intent = new Intent(this, ConfigActivity.class);
        // msg.objにはデバイスの設定データが格納されている
        byte[] readBuf = (byte[]) msg.obj;
        // msg.arg2にはデータのサイズが格納されている
        String setting = new String(readBuf, 0, msg.arg2);
        intent.putExtra(SETTING, setting);
        intent.putExtra(DEFAULT_NAME, mConnectedDefaultName);
        startActivityForResult(intent, REQUEST_CONFIG_BT);
    }


    private final void setStatus(int resId) {
        //final ActionBar actionBar = getActionBar();
        //actionBar.setSubtitle(resId);
    }


    private final void setStatus(CharSequence subTitle) {
        String tmpString = new String();
        tmpString = getString(R.string.app_name) + " [ " + subTitle + " ]";
        setTitle(tmpString);
        // アクションバーにサブタイトルを表示する SdkVersion11以降で使用可能
        //final ActionBar actionBar = getActionBar();
        //actionBar.setSubtitle(subTitle);
    }

    // BtSocketServiceからの情報を取得するためのハンドラ
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int index = mSpinner01.getSelectedItemPosition();

            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    // 以前のActionBar設定のための関数呼び出し処理はコメントにする
                    switch (msg.arg1) {
                        case BtSocketService.STATE_CONNECTED:
                            //setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            //     setStatus(getString(R.string.title_connected));
                            mInStringBuffer.setLength(0);
                            break;
                        case BtSocketService.STATE_CONNECTING:
                            //setStatus(R.string.title_connecting);
                            //      setStatus(getString(R.string.title_connecting));
                            break;
                        case BtSocketService.STATE_LISTEN:
                        case BtSocketService.STATE_NONE:
                            //setStatus(R.string.title_not_connected);
                            //     setStatus(getString(R.string.title_not_connected));
                            //   showPreferences(false);
                            break;
                    }
                    //if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    break;
                case MESSAGE_WRITE:
                    //byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    //String writeMessage = new String(writeBuf);
                    //mTextView01.setText("Me:  " + writeMessage);
                    break;
                case MESSAGE_READ:
                    String a = "";
                    double b;
                    int weight1;
                    // mInStringBuffer.delete(0,mInStringBuffer.length());
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg2);
                    mInStringBuffer.append(readMessage);
//                   if (mProcNo == PROC_WEIGHT){

                    try {

                        a = mInStringBuffer.toString();
//                        datamachine.setText("Data From machine :- "+a);


                        b = Double.parseDouble(a);
//                        valuepoint.setText("Value in point :- "+String.valueOf(b));


                        rounded = String.format("%.0f", b);
//                        removepoint.setText("Value remove Point :- "+rounded);

                        weight1 = Integer.parseInt(rounded);
                        res = weight1 + 6;

                        weight.setText(String.valueOf(res));

                        // realweight.setText(String.valueOf(weight1));
//                        realweight.setText(String.valueOf(weight1));
                        mTextToSpeak.startSpeaking(String.valueOf(weight1));

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    //     }
                    break;
                case MESSAGE_DEVICE_NAME:
                    // 接続デバイス名を保存する
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    mConnectedDefaultName = msg.getData().getString(DEFAULT_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    if (D) Log.d(TAG, "DEFAULT_NAME:" + mConnectedDefaultName);
                    // デバイス名が変更された場合、スピナーに表示する名前も更新する。
                    if (!mConnectedDeviceName.equals(mPairedDevicesArrayAdapter.getItem(index))) {
                        if (mBtDevices.length != 0) {
                            if (D) Log.d(TAG, "mSpinner01 update to " + mConnectedDefaultName);
                            mPairedDevicesArrayAdapter.clear();
                            for (int i = 0; i < mBtDevices.length; i++) {
                                mPairedDevicesArrayAdapter.add(mBtDevices[i].getName());
                            }
                            mSpinner01.setAdapter(mPairedDevicesArrayAdapter);
                            mSpinner01.setSelection(index);
                        }
                    }
                    //   showPreferences(true);
                    break;
                case MESSAGE_CONNECTION_LOST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
                    alertFailedConnection();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TIMEOUT:
                    Toast.makeText(getApplicationContext(), R.string.error_msg04, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_SETTING:
                    //if(D) Log.d(TAG, "MESSAGE_SETTING");
                    startConfigActivity(msg);
                    break;
                case MESSAGE_RECONNECT:
                    //if(D) Log.d(TAG, "MESSAGE_RECONNECT");
                    // (Boolean)msg.obj: 設定を変更した場合は true
                    // 設定を変更した場合はダイアログを表示する
                    Boolean isExitConfig = (Boolean) msg.obj;
                    if (isExitConfig.booleanValue()) {
                        exitConfig();
                    } else {
                        // キャンセルされたためソケットを再接続する
                        mSocketService.connect(mBtDevices[index], true);
                    }
                    break;
            }
        }
    };

    // startActivityForResult で起動させたアクティビティが
    // finish() により破棄されたときにコールされる
    // requestCode : startActivityForResult の第二引数で指定した値
    // resultCode : 起動先のActivity.setResult の第一引数で指定した値
    // Intent data : 起動先Activityから送られてくる Intent

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {

            case REQUEST_ENABLE_BT:
                // Bluetooth設定を有効化できた場合
                if (resultCode == Activity.RESULT_OK) {
                    // メインアクティビティの初期化開始
                    setupMain();
                } else {
                    // Bluetooth設定を有効化できなかった場合
                    if (D) Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case REQUEST_CONFIG_BT:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        mNeedPairing = extras.getBoolean(ConfigActivity.EXTRA_NEED_PAIRING);
                    }
                    mSocketService.exitcmd(data);
                }
                break;
            case PACKING_ACTIVITY:
                if (data.hasExtra(BACK)) {

                }
                else if (data.hasExtra(NEXTBOX)) {
                    btnGrey.setText(PackingBoxListActivity.packed_count);
                    productList = PackingBoxListActivity.productList;
                    for (int i=0; i< productList.size(); i++)
                        productList.get(i).setScanned_qty("0");
                }
                break;
        }
    }

    @Override
    public void onSucess(int status, BoxCashOrderIDResp message) throws JsonIOException {
        progress.Dismiss();
        String result = message.getResult();
        if(result!= null)
        {

        }
        else{
            result = "";
        }
        if (result .equalsIgnoreCase("OK")) {

            startTimer();
            koguchi_count = message.getKoguchi();
            mBoxNo = message.getBox_no();

            btnyellow.setText(koguchi_count);

            String packed_count = message.getPacked_count();
            btnGrey.setText(packed_count);
            String pending_orders = message.getPending_count();
            btnBlue.setText(pending_orders);
            String finish_orders = message.getFinished_count();
            btnRed.setText(finish_orders);


            shipFlag = message.getShipco_method();
            if (shipFlag != null) {

            } else
                shipFlag = "";

            if (shipFlag.equalsIgnoreCase("1")) {
                weightLayout.setVisibility(View.VISIBLE);
            } else
                weightLayout.setVisibility(View.GONE);


            String all_row_count = U.plusTo(message.getNot_inspection_row_count(), message.getShortage_row_count());
            productList = message.getData();
            for (int i = 0; i < productList.size(); i++)
                productList.get(i).setScanned_qty("0");

            if (!CommonUtilities.getConnectivityStatus(this))
                CommonUtilities.openInternetDialog(this);
            else {
                progress.Show();
                BoxListRequest req = new BoxListRequest(adminID, app.getSerial(), ID);
                manager.GetBoxList(req, getBoxListCallback);
            }
        }
        else{
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, BoxListResp message) throws JsonIOException {
        progress.Dismiss();
        String result = message.getResult();
        if(result!= null)
        {

        }
        else{
            result = "";
        }
        if (result .equalsIgnoreCase("OK")) {


            boxList = message.getBoxes();
            setSpinner();
            setProc(PROC_CBOX);
        }
        else
            U.beepError(this, message.getMessage() );
    }

    @Override
    public void onSucess(int status, PickProductRes message) throws JsonIOException {
        progress.Dismiss();
        String result = message.getResult();
        if(result!= null)
        {

        }
        else{
            result = "";
        }
        if (result .equalsIgnoreCase("OK")) {

            String pending_orders = message.getAll_order_count();
            btnBlue.setText(pending_orders);
            is_scan = false;
            btnGrey.setText(message.getPacked_count());
            if (message.getKoguchi_mismatch().equalsIgnoreCase("1")) {
                CommonDialogs.dialogAlert("選択した個口は事前設定された個口と一致しません。ポータルから個口を変更してください", this);
                nextProcess();
                if (!getBoxid().equalsIgnoreCase("")) {
                    cbox.setText(getBoxid());
                }

            } else {
                if (complete) {
                    nextProcess();
                    if (!getBoxid().equalsIgnoreCase("")) {
                        cbox.setText(getBoxid());
                    }
                } else if (clear) {
                    nextProcess();
                    setBoxSize("");
                    setBoxId("");
                    boxsizespiner.setText("");
                    boxsizespiner.setAdapter(null);
                } else {
                    barcode.setText("");
                    quantity.setText("");
                    totalQuantity.setText("");
                    productName.setText("");
                    setProc(PROC_BARCODE);
                }
            }
            String all_row_count = U.plusTo(message.getNot_inspection_row_count(), message.getShortage_row_count());
            if (message.getData().size()!=0){

                if (all_row_count.equalsIgnoreCase("0")) {
                    barcode.setText("");
                    quantity.setText("");
                    totalQuantity.setText("");
                    productName.setText("");
                    setProc(PROC_BARCODE);
                    complete = true;
                    if (shipFlag.equalsIgnoreCase("1") ) {
                        setProc(PROC_WEIGHT);
                    } else
                        callNextBox();
                } else if (nextBox || nextPress) {
                    barcode.setText("");
                    quantity.setText("");
                    totalQuantity.setText("");
                    productName.setText("");
                    setProc(PROC_BARCODE);
                    productList = new ArrayList<>();
                    productList = message.getData();

                    for (int i=0; i< productList.size(); i++)
                        productList.get(i).setScanned_qty("0");

                    setOnWeight();
                } else {
                    barcode.setText("");
                    quantity.setText("");
                    totalQuantity.setText("");
                    productName.setText("");
                    setProc(PROC_BARCODE);

                    productList = new ArrayList<>();
                    productList = message.getData();

                    for (int i=0; i< productList.size(); i++)
                        productList.get(i).setScanned_qty("0");

                    if (!isNextBarcode.equals(""))
                        scanedEvent(isNextBarcode);
                }

                Log.e(TAG, "productListtttt    "+productList);
            }else{


                Toast.makeText(this,"検品が完了しました。",Toast.LENGTH_SHORT).show();
                //    TasMusic.TasNewBeep(this,"検品が完了しました。");
                //    U.beepSuccess(this,"検品が完了しました。");
//                nextProcess();
                if(all_row_count.equalsIgnoreCase("0"))
                    complete = true;
                if (shipFlag.equalsIgnoreCase("1") ) {
                    setProc(PROC_WEIGHT);

                } else
                    callNextBox();

            }
        }
        else
        {
            U.beepError(this, message.getMessage() );
        }
    }

    @Override
    public void onSucess(int status, GetBoxNoResponse message) throws JsonIOException {
        progress.Dismiss();
        String result = message.getResult();
        if(result!= null)
        {

        }
        else{
            result = "";
        }
        if (result .equalsIgnoreCase("OK")) {
            mBoxNo = message.getBox_no();
            nextboxButton.setEnabled(true);
//            U.beepKakutei(this, null);

            if(complete){
                mTextToSpeak.startSpeaking("検品が完了しました。");
                nextProcess();}
            else  {
                setProc(PROC_BARCODE);
                U.beepKakutei(this, null);
                weight.setText("" );
                realweight.setText("");
            }

            nextPress = false;
        }
        else{
            nextboxButton.setEnabled(true);
            U.beepError(this, message.getMessage() );
        }
    }

    @Override
    public void onSucess(int status, GetPackingListResponse message) throws JsonIOException {
        progress.Dismiss();
        String result = message.getResult();
        if(result!= null)
        {

        }
        else{
            result = "";
        }
        if (result .equalsIgnoreCase("OK")) {

            packingList = message.getPacked_boxes();

            startPackingBoxActivity();
        }
        else
            U.beepError(this, message.getMessage() );
    }


    protected boolean startPackingBoxActivity() {

        if (mProcNo == PROC_QUANTITY) {
            if (is_scan == false)
                createNewPackItem();
            fixedRequest( "");
        }
        Intent packingIntent = new Intent(this, PackingBoxListActivity.class);
        packingIntent.putExtra("orderId", orderId.getText().toString());
        Log.e(TAG, "showPack");
        startActivityForResult(packingIntent, PACKING_ACTIVITY);
        return false;
    }

    @Override
    public void onError(int status, ResponseBody error) {
        progress.Dismiss();
        U.beepError(this, error.toString());
    }

    @Override
    public void onFaliure() {
        progress.Dismiss();
        U.beepError(this, "Network failure");
    }

    @Override
    public void onNetworkFailure() {
        progress.Dismiss();
        U.beepError(this, "Network failure");
    }

}