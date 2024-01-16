package com.itechvision.ecrobo.pickman.Chatman.PickWork.W6Batch;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TotalPickListActivity;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.BarcodeData;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BoxBatchPicking.BoxBatchRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BoxBatchPicking.BoxBatchResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BoxBatchSubmisionModel.BoxBatchSubmitReq;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BoxBatchSubmisionModel.BoxBatchSubmitResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class BatchBoxW6Activity extends BaseActivity implements DataManager.GetboxBatchcall, DataManager.BoxBatchSumbitcall {

    @BindView(R.id.relMainAction)
    LinearLayout relMainAction;
    @BindView(R.id.relLayout1)
    RelativeLayout relLayout1;

    @BindView(R.id.relLayout3)
    RelativeLayout relLayout3;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.relLayout2)
    RelativeLayout relLayout2;
    @BindView(R.id.layout_main)
    LinearLayout layoutMain;
    @BindView(R.id.orderIdLabel)
    TextView orderIdLabel;
    @BindView(R.id.orderId)
    EditText orderId;
    @BindView(R.id.orderNo)
    EditText orderNo;
    @BindView(R.id.trackingNumber)
    EditText TrackingNo;
    @BindView(R.id.scanbarcode)
    EditText scanbarcode;
    @BindView(R.id.rl_box)
    LinearLayout rlBox;
    @BindView(R.id.boxtext)
    TextView boxtext;
    @BindView(R.id.layout_number)
    RelativeLayout layoutNumber;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.orderIdLayout)
    LinearLayout orderIdLayout;
    @BindView(R.id.orderNoLayout)
    LinearLayout orderNoLayout;
    @BindView(R.id.trackingNumberLayout)
    LinearLayout trackingNumberLayout;

    private int orderRequestSettings;

    public static int mRequestStatus = 0;
    SharedPreferences spDomain;
    public Context mcontext = this;
    public static final String DOMAINPREFERENCE = "domain";
    public static final int PRODUCT_ACTIVITY = 111;
    String TAG = TotalPickListActivity.class.getSimpleName();
    int Usercount = 0;
    ECRApplication app = new ECRApplication();
    private boolean showKeyboard = false;
    private boolean visible = false;
    private TextToSpeak mTextToSpeak;
    public String _lastUpdateQty = "0";
    protected int mProcNo = 0;
    public static final int PROC_KEY = 2;
    public static final int PROC_NONE = 3;
    public static final int PROC_ORDERID = 4;
    public static final int PROC_ORDERNO = 5;
    public static final int PROC_TRACKID = 6;
    public static final int PROC_QTY = 7;

    ArrayList<BarcodeData> arr;
    DataManager manager;
    progresBar progress;
    String date = "", shipid = "", authid = "", adminid = "", batchid = "", batchno = "",invoice_status = "0", ProductID = "", ID = "", OrderID = "";
    DataManager.GetboxBatchcall getboxBatchcall;
    DataManager.BoxBatchSumbitcall boxBatchSumbitcall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_box_w6);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Log.d(TAG,"On Create ");

        date = intent.getStringExtra("date");
        batchid = intent.getStringExtra("Batch_id");
        batchno = intent.getStringExtra("Batch_no");
        adminid = intent.getStringExtra("admin_id");
        authid = intent.getStringExtra("auth_id");
        invoice_status = intent.getStringExtra("invoice_status");


        orderRequestSettings = BaseActivity.getOrderInfoBy();
        if (orderRequestSettings == SettingActivity.ORDER_ID) {
            orderIdLayout.setVisibility(View.VISIBLE);
            orderNoLayout.setVisibility(View.GONE);
            trackingNumberLayout.setVisibility(View.GONE);
        } else if (orderRequestSettings == SettingActivity.ORDER_NUMBER) {
            orderIdLayout.setVisibility(View.GONE);
            orderNoLayout.setVisibility(View.VISIBLE);
            trackingNumberLayout.setVisibility(View.GONE);
        } else if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO) {
            orderIdLayout.setVisibility(View.GONE);
            orderNoLayout.setVisibility(View.GONE);
            trackingNumberLayout.setVisibility(View.VISIBLE);
        }
//        submitcall = this;

        showKeyboard = BaseActivity.getaddKeyboard();
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));


        mTextToSpeak = new TextToSpeak(this);
        arr = new ArrayList<>();
        manager = new DataManager();
        progress = new progresBar(this);
        getboxBatchcall = this;
        boxBatchSumbitcall = this;

        checkProc();

        if(invoice_status.equalsIgnoreCase("2")){
            Intent i = new Intent(BatchBoxW6Activity.this, BatchPickingW6Activity.class);
            i.putExtra("Batch_id", batchid);
            i.putExtra("Batch_no", batchno);
            i.putExtra("date", date);
            i.putExtra("auth_id", authid);
            i.putExtra("admin_id", adminid);

            startActivityForResult(i, PRODUCT_ACTIVITY);
        }
    }

    void checkProc() {
        if (orderRequestSettings == SettingActivity.ORDER_ID)
            setProc(PROC_ORDERID);
        else if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
            setProc(PROC_ORDERNO);
        else if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO)
            setProc(PROC_TRACKID);

    }

    public int convert(int x) {
        Resources r = mcontext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                x, r.getDisplayMetrics()
        );
        return px;
    }


    @Override
    public void nextProcess() {
    }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        orderId.setText("");
        _sts(R.id.orderNo, "");
        _sts(R.id.trackingNumber, "");
        scanbarcode.setText("");
        boxtext.setText("0");

        if (orderRequestSettings == SettingActivity.ORDER_ID)
            setProc(PROC_ORDERID);
        else if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
            setProc(PROC_ORDERNO);
        else if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO)
            setProc(PROC_TRACKID);
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
            case PROC_ORDERID:    // バーコード
                _sts(R.id.orderId, buff);

                break;
            case PROC_ORDERNO:    // バーコード
                _sts(R.id.orderNo, buff);
                break;
            case PROC_TRACKID:    // バーコード
                _sts(R.id.trackingNumber, buff);

                break;
            case PROC_QTY:    // バーコード
                _sts(R.id.scanbarcode, buff);
                break;
        }
    }

    @Override
    public void enterEvent() {


    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {

            case PROC_ORDERID:    // バーコード
                _sts(R.id.orderId, barcode);
                break;

            case PROC_QTY:    // バーコード
                _sts(R.id.scanbarcode, barcode);
                break;

            case PROC_ORDERNO:
                _sts(R.id.orderNo, barcode);
                break;
            case PROC_TRACKID:
                _sts(R.id.trackingNumber, barcode);

                break;
        }
    }


    @OnClick(R.id.backbtn)
    void backbtn() {
        finish();
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    void onboxsubmitClick() {


        progress.Show();
        BoxBatchSubmitReq req = new BoxBatchSubmitReq(adminid, authid, BaseActivity.getShopId(), batchid, OrderID, getResources().getString(R.string.version), date, _gts(R.id.scanbarcode));
        manager.BoxBatchSubmit(req, boxBatchSumbitcall);
    }


    @OnClick(R.id.add_layout)
    void add_layout() {
        //TODO implement

        if (showKeyboard == false) {
            layoutNumber.setVisibility(View.VISIBLE);
            showKeyboard = true;
        } else {
            layoutNumber.setVisibility(View.GONE);
            showKeyboard = false;
        }

    }


    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {

            case PROC_ORDERID:
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.scanbarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;

            case PROC_ORDERNO:
                _gt(R.id.orderNo).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.scanbarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;

            case PROC_TRACKID:
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.scanbarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;

            case PROC_QTY:
                _gt(R.id.scanbarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;

        }

    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {

            case PROC_ORDERID:
                String order = _gts(R.id.orderId);
                //  Log.e(TAG,"inputedEvent_orderId  "+orderId);

                if (order.equals("")) {
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.orderId).setFocusableInTouchMode(true);
                    break;
                }
                //     boolean match = checkorderId(buff);


                progress.Show();
                BoxBatchRequest req = new BoxBatchRequest(adminid, authid, BaseActivity.getShopId(), batchid, orderId.getText().toString(), getResources().getString(R.string.version), date, "");
                manager.GetboxBatch(req, getboxBatchcall);
                break;

            case PROC_ORDERNO:
                String order1 = _gts(R.id.orderNo);
                //  Log.e(TAG,"inputedEvent_orderId  "+orderId);

                if (order1.equals("")) {
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.orderNo).setFocusableInTouchMode(true);
                    break;
                }
                //     boolean match = checkorderId(buff);


                progress.Show();
                BoxBatchRequest req1 = new BoxBatchRequest(adminid, authid, BaseActivity.getShopId(), batchid, order1, getResources().getString(R.string.version), date, "orderno");
                manager.GetboxBatch(req1, getboxBatchcall);

                break;

            case PROC_TRACKID:
                String track = _gts(R.id.trackingNumber);
                //  Log.e(TAG,"inputedEvent_orderId  "+orderId);

                if (track.equals("")) {
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.trackingNumber).setFocusableInTouchMode(true);
                    break;
                }
                //     boolean match = checkorderId(buff);


                progress.Show();
                BoxBatchRequest req2 = new BoxBatchRequest(adminid, authid, BaseActivity.getShopId(), batchid, track, getResources().getString(R.string.version), date, "mediacode");
                manager.GetboxBatch(req2, getboxBatchcall);

                break;


            case PROC_QTY:
                String boxbar = _gts(R.id.scanbarcode);

                if (boxtext.getText().toString().length() == 1) {
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())) {
                        onboxsubmitClick();
                    } else if (boxbar.equalsIgnoreCase("000" + boxtext.getText().toString())) {
                        onboxsubmitClick();
                        break;
                    } else {
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.scanbarcode).setFocusableInTouchMode(true);
                    }
                } else if (boxtext.getText().toString().length() == 2) {
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())) {
                        onboxsubmitClick();


                    } else if (boxbar.equalsIgnoreCase("00" + boxtext.getText().toString())) {
                        onboxsubmitClick();
                    } else {
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.scanbarcode).setFocusableInTouchMode(true);
                    }
                } else if (boxtext.getText().toString().length() == 3) {
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())) {
                        onboxsubmitClick();
                    } else if (boxbar.equalsIgnoreCase("0" + boxtext.getText().toString())) {
                        onboxsubmitClick();
                    } else {
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.scanbarcode).setFocusableInTouchMode(true);
                    }
                } else {
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())) {
                        onboxsubmitClick();
                    } else {
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.scanbarcode).setFocusableInTouchMode(true);
                    }
                }


                break;

        }
    }


    @Override
    public void scanedEvent(String barcode) {
        Log.e(TAG, "ScannedEventttttt   is " + barcode);


       /* if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEventttttt");*/

        if (mProcNo == PROC_ORDERID) {
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
            _sts(R.id.orderId, barcode);
            //  setProc(PROC_QTY);
        } else if (mProcNo == PROC_QTY) {
            _sts(R.id.scanbarcode, barcode);

        } else if (mProcNo == PROC_ORDERNO) {
            _sts(R.id.orderNo, barcode);
        } else if (mProcNo == PROC_TRACKID) {
            _sts(R.id.trackingNumber, barcode);
        }
        // }
        this.inputedEvent(barcode, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                Intent i = new Intent();
                setResult(Activity.RESULT_OK, i);
                finish();
    }



    /*@Override
    public void onSucess(int status, getTasinvoiceRespose message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("200")){
            boxtext.setText(message.getFontage_no());
            mTextToSpeak.startSpeaking("ボックス"+boxtext.getText().toString());
            // rlBox.setVisibility(View.VISIBLE);
            setProc(PROC_QTY);
        }else if (message.getCode().equalsIgnoreCase("1020")){

            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(NewinvoiceCheckActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        }else {
            // rlBox.setVisibility(View.GONE);
            U.beepError(this,message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, SubmitBatchInvoice message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){
            // mTextToSpeak.startSpeaking("オッケ");
            U.TasNewBeep(this,null);

            if ( message.getStatus().equalsIgnoreCase("complete")){

                Intent in = new Intent(NewinvoiceCheckActivity.this, NewdbatchPickingActivity.class);
                in.putExtra("date",Date);
                in.putExtra("batchid",batchid);
                in.putExtra("adminid",adminid);
                in.putExtra("shopid",shipid);
                in.putExtra("authid", authod);
                startActivity(in);
                finish();

            }
            else{

                orderId.setText("");
                orderNo.setText("");
                TrackingNo.setText("");
                boxtext.setText("0");
                scanbarcode.setText("");
                // U.TasNewBeep(this,null);
                setProc(PROC_ORDERID);
            }
        }else if (message.getCode().erqualsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(BatchBoxW6Activity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })
                    .show();
        }else {
            U.beepError(this,message.getMessage());
            boxtext.setText("");
        }
    }
*/
    @Override
    public void onSucess(int status, BoxBatchResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("200")) {
            if(message.getStatus().equalsIgnoreCase("2")){
                Intent i = new Intent(BatchBoxW6Activity.this, BatchPickingW6Activity.class);
                i.putExtra("Batch_id", batchid);
                i.putExtra("Batch_no", batchno);
                i.putExtra("date", date);
                i.putExtra("auth_id", authid);
                i.putExtra("admin_id", adminid);

                startActivityForResult(i, PRODUCT_ACTIVITY);
            }

            else {
                boxtext.setText(message.getBox_no());
                mTextToSpeak.startSpeaking("ボックス" + boxtext.getText().toString());
                OrderID = message.getOrder_id();
                // rlBox.setVisibility(View.VISIBLE);
                setProc(PROC_QTY);

            }

        } else if (message.getCode().equalsIgnoreCase("1020")) {

            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(BatchBoxW6Activity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        } else {
            // rlBox.setVisibility(View.GONE);
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, BoxBatchSubmitResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("0")) {

            String stats = message.getStatus();

            if (stats.equalsIgnoreCase("1")) {
                clearEvent();
            } else if (stats.equalsIgnoreCase("2")) {
                Intent i = new Intent(BatchBoxW6Activity.this, BatchPickingW6Activity.class);
                i.putExtra("Batch_id", batchid);
                i.putExtra("Batch_no", batchno);
                i.putExtra("date", date);
                i.putExtra("auth_id", authid);
                i.putExtra("admin_id", adminid);
//                i.putExtra("order_id", OrderID);

                startActivityForResult(i, PRODUCT_ACTIVITY);
            }

        } else if (message.getCode().equalsIgnoreCase("1020")) {



            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(BatchBoxW6Activity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        } else {
            // rlBox.setVisibility(View.GONE);
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onError(int status, ResponseBody error) {
        progress.Dismiss();
    }

    @Override
    public void onFaliure() {
        progress.Dismiss();
    }

    @Override
    public void onNetworkFailure() {
        progress.Dismiss();
    }
}

