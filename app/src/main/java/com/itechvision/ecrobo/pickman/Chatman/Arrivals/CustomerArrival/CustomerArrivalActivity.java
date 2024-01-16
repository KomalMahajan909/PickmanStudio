package com.itechvision.ecrobo.pickman.Chatman.Arrivals.CustomerArrival;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckBarcode.BatchList;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckBarcode.CustomerBarcodeReq;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckBarcode.CustomerBarcodeResponse;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckBarcode.NyukaData;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckCustomer.CustomerCheckReq;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.SubmitArrival.SubmitArrivalReq;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.SubmitArrival.SubmitArrivalResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

public class CustomerArrivalActivity extends BaseActivity implements DataManager.CheckCustomerBarcodecallback ,DataManager.submitArrivalcallback{

    @BindView(R.id.layout_main)
    RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.batch_arrival)
    Button batcharrivalbtn;
    @BindView(R.id.productName)
    TextView productName;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.customerId)
    EditText customer;
    @BindView(R.id.sendButton)Button sendBtn;



    protected int mProcNo = 0;
    public static final int PROC_ORDER_NO = 5;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_QTY = 2;
    public static final int PROC_LOT_NO = 3;
    public static final int PROC_EXPIRATION = 4;
    public static final int PROC_PARTNO = 6;

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    String TAG = CustomerArrivalActivity.class.getSimpleName();

    ECRApplication app = new ECRApplication();

    private boolean showKeyboard;

    private ArrayList<NyukaData> arr = new ArrayList<>();

    private ArrayList<BatchList> batchList = new ArrayList<>();

    private boolean visible = false;
    String adminID = "",customerID = "", ID = "";
    private TextToSpeak mTextToSpeak;

    private boolean batcharrival = false;
    public Context mcontext = this;

    public static boolean mNextBarcode = false;
    public String isNextBarcode = "";

    DataManager manager;
    progresBar progress;

    DataManager.CheckCustomerBarcodecallback checkBarcodecallback;
    DataManager.submitArrivalcallback submitArrivalcallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_arrival);

        ButterKnife.bind(CustomerArrivalActivity.this);

        getIDs();

        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        productName = (TextView)findViewById(R.id.productName);

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);
        ID = BaseActivity.getShopId();

        Intent i = getIntent();
        if (i.hasExtra("ID"))
            customerID = i.getStringExtra("ID");

        if (!customerID.equalsIgnoreCase(""))
           customer.setText(customerID);

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
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        mTextToSpeak = new TextToSpeak(this);
        Log.e(TAG, "OnCreate");

        if (mProcNo == 0) nextProcess();

    }

    @OnClick(R.id.menu_drawer) void backbtn(){
        Intent i = new Intent(CustomerArrivalActivity.this, CustomerIDActivity.class);
        startActivity(i);
        finish();
    }
    //set title and icons on actionbar
    private void getIDs() {


        manager = new DataManager();
        progress = new progresBar(this);

        checkBarcodecallback = this;
        submitArrivalcallback = this;

    }

    @OnClick(R.id.enter)
    void enter() {
        String s = mBuff.toString();
        Log.e("ScannerbindActivity", "Keyput11222 " + mBuff);
        mBuff.delete(0, mBuff.length());
        Log.e("ScannerbindActivity", "Keyput11222 buff " + mBuff);
        inputedEvent(s);
    }

    @OnClick(R.id.batch_arrival)
    void batchSelect(){
        if(!_gts(R.id.quantity).equalsIgnoreCase("")){
            BatchList val = new BatchList(_gts(R.id.barcode),_gts(R.id.quantity));
            batchList.add(val);
            _sts(R.id.barcode, "");
            _sts(R.id.quantity, "");
            _stxtv(R.id.productName, "");
            _sts(R.id.productCode,"");
            _sts(R.id.Standard1, "");
            _sts(R.id.Standard2, "");

            setProc(PROC_BARCODE);
            batcharrival = true;
        }
    }

    @OnClick(R.id.sendButton)
    void Send(){
        if(!_gts(R.id.quantity).equalsIgnoreCase("")) {
            BatchList val = new BatchList(_gts(R.id.barcode), _gts(R.id.quantity));
            batchList.add(val);
        }
      showMessage();

    }

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
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {

            visible = false;
            numbrbtn.setText("キーボードを表示する");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG, "SetlayoutMarginnnnn");
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
    

        mBuff.delete(0, mBuff.length());
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {

            case PROC_BARCODE:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;
            case PROC_PARTNO:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;
            case PROC_QTY:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                    _gt(R.id.lotno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;

        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        String lot = "";
        switch (mProcNo) {
            case PROC_BARCODE:
                String barcode1 = _gts(R.id.barcode);
                if (barcode1.equalsIgnoreCase("")) {
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }

                U.beepRecord(this, null);
                setProc(PROC_QTY);

                progress.Show();
                CustomerBarcodeReq req = new CustomerBarcodeReq(adminID, app.getSerial(), ID, getResources().getString(R.string.version), barcode1);
                manager.CheckBarcodeArrival(req, checkBarcodecallback);

                break;
            case PROC_QTY: // 数量

                    String qty = _gts(R.id.quantity);
                    if (qty.equals(""))
                        qty = "1";
                    String barcode = _gts(R.id.barcode);


                    if (isScaned) {

                        if (buff.equals(barcode)) {
                            U.beepSuccess();
                            qty = U.plusTo(qty, "1");
                            _sts(R.id.quantity, qty);
                            mTextToSpeak.startSpeaking(_gts(R.id.quantity));

                            break;
                        } else {


                            if (batcharrival == true) {

                                break;
                            } else {
                                showMessage();
                            }


                            mNextBarcode = true;
                            isNextBarcode = buff;

                            break;
                        }
                    }
                    else {

                        if ("".equals(qty) || "0".equals(qty)) {
                            U.beepError(this, "数量は必須です");
                            _gt(R.id.quantity).setFocusableInTouchMode(true);
                            break;
                        } else if (!U.isNumber(qty)) {
                            U.beepError(this, "数量は数値のみ入力可能です");
                            _gt(R.id.quantity).setFocusableInTouchMode(true);
                            break;
                        }

                        if (batcharrival == true) {

                            break;
                        } else {
                            showMessage();
                        }
                    }

                break;
        }
    }
    void sendRequest(String barcode1, String qty) {

        progress.Show();

        SubmitArrivalReq req = new SubmitArrivalReq(adminID, app.getSerial(), ID, getResources().getString(R.string.version), barcode1,customerID,qty);
        manager.SubmitArrival(req, submitArrivalcallback);

    }

    void showMessage()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("入荷検品を完了します。\n" +
                "顧客番号：" +customerID+
                "\nの出荷指示データを生成しますか？\n");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (batcharrival == true) {
                            StringBuffer bar = new StringBuffer();
                            StringBuffer qty1 = new StringBuffer();

                            for (int i= 0;i< batchList.size();i++){
                                if (!batchList.get(i).getQuantity().equals("0")) {
                                    bar.append(",").append(batchList.get(i).getBarcode());
                                    qty1.append(",").append(batchList.get(i).getQuantity());
                                }
                            }


                            sendRequest(bar.substring(1),qty1.substring(1));
                        } else
                            sendRequest(_gts(R.id.barcode), _gts(R.id.quantity));
                        dialog.cancel();
                    }
                });



        builder1.setNegativeButton(
                "いいえ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        _sts(R.id.barcode, "");
                        _sts(R.id.quantity, "");
                        _stxtv(R.id.productName, "");
                        _sts(R.id.productCode,"");
                        _sts(R.id.Standard1, "");
                        _sts(R.id.Standard2, "");
                        setProc(PROC_BARCODE);
                      /*  Intent i = new Intent(CustomerArrivalActivity.this, CustomerIDActivity.class);
                        startActivity(i);
                        finish();*/
                        dialog.cancel();                  }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }

    @Override
    public void inputedEvent(String buff) {
      inputedEvent(buff,false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTextToSpeak.onStopSpeaking();
        mTextToSpeak.resetQueue();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTextToSpeak.onStopSpeaking();
        mTextToSpeak.resetQueue();
    }

    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
    }

    @Override
    public void allclearEvent() {
       Intent i = new Intent(CustomerArrivalActivity.this, CustomerIDActivity.class);
       startActivity(i);
       finish();
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
            Log.e(TAG, "ScannedEvent");

            if (mProcNo == PROC_BARCODE) {
                // check for QR code
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

            if (mProcNo == PROC_QTY) {
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode = finalbarcode;
                Log.e(TAG, "barcode111   " + barcode);
            }

        }
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
                if ("".equals(barcode)) {
                } else
                    _sts(R.id.quantity, barcode);
                break;
        }
    }

    @Override
    public void nextProcess() {
        Log.e(TAG, "nextProcess");
        _sts(R.id.barcode, "");
        _sts(R.id.quantity, "");
        _stxtv(R.id.productName, "");
        _sts(R.id.productCode,"");
        _sts(R.id.Standard1, "");
        _sts(R.id.Standard2, "");
        _sts(R.id.lotno, "");

        setProc(PROC_BARCODE);
    }

    @Override
    public void onSucess(int status, CustomerBarcodeResponse message) throws JsonIOException {
      progress.Dismiss();
      if(message.getCode().equalsIgnoreCase("0")){
           arr = message.getResults();

          mTextToSpeak.startSpeaking("Scanning");

          _sts(R.id.Standard1, arr.get(0).getSpec1());
          _sts(R.id.Standard2, arr.get(0).getSpec2());
          _sts(R.id.productCode,arr.get(0).getCode());
          productName.setText( arr.get(0).getName());
          _sts(R.id.lotno, customerID);
          _sts(R.id.quantity, "1");
            mTextToSpeak.startSpeaking("1");
            setProc(PROC_QTY);

      }
      else {
          U.beepError(this, message.getMessage());
          _sts(R.id.barcode,"");
          setProc(PROC_BARCODE);
      }
    }

    @Override
    public void onSucess(int status, SubmitArrivalResponse message) throws JsonIOException {
     progress.Dismiss();
        if(message.getCode().equalsIgnoreCase("0")){
            Intent i = new Intent(CustomerArrivalActivity.this, CustomerIDActivity.class);
            startActivity(i);
            finish();
        }
        else {
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onError(int status, ResponseBody error) {
     progress.Dismiss();
        JSONObject jObjError = null;
        try {
            jObjError = new JSONObject(error.toString());
            String message = jObjError.get("message").toString();
            U.beepError(this,message);
        }
        catch (Exception e){
            e.printStackTrace();
        }
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