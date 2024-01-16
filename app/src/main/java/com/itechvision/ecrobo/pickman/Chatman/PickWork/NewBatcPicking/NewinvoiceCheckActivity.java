package com.itechvision.ecrobo.pickman.Chatman.PickWork.NewBatcPicking;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TotalPickListActivity;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.BarcodeData;
import com.itechvision.ecrobo.pickman.Models.TasBatchInvoice.SubmitBatchInvoice;
import com.itechvision.ecrobo.pickman.Models.TasBatchInvoice.getTasinvoiceRespose;
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
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

public class NewinvoiceCheckActivity extends BaseActivity implements DataManager.GetIvoicetascall , DataManager.SubmitInvoiceTascall {

    @BindView(R.id.relMainAction) LinearLayout relMainAction;
    @BindView(R.id.relLayout1) RelativeLayout relLayout1;
    @BindView(R.id.img1ActionBar) ImageView img1ActionBar;
    @BindView(R.id.relLayout3) RelativeLayout relLayout3;
    @BindView(R.id.txtTitle) TextView txtTitle;
    @BindView(R.id.relLayout2) RelativeLayout relLayout2;
    @BindView(R.id.layout_main) LinearLayout layoutMain;
    @BindView(R.id.orderIdLabel) TextView orderIdLabel;
    @BindView(R.id.orderId) EditText orderId;
    @BindView(R.id.orderNo) EditText orderNo;
    @BindView(R.id.trackingNumber) EditText TrackingNo;
    @BindView(R.id.scanbarcode) EditText scanbarcode;
    @BindView(R.id.rl_box) LinearLayout rlBox;
    @BindView(R.id.boxtext) TextView boxtext;
    @BindView(R.id.layout_number) RelativeLayout layoutNumber;
    @BindView(R.id.viewpager) ViewPager viewpager;
    @BindView(R.id.orderIdLayout) LinearLayout orderIdLayout;
    @BindView(R.id.orderNoLayout) LinearLayout orderNoLayout;
    @BindView(R.id.trackingNumberLayout) LinearLayout trackingNumberLayout;

    private int orderRequestSettings;

    PopupWindow popupWindow;
    SweetAlertDialog pDialog;
    int eop, barcodepostion;
    public static int mRequestStatus = 0;
    SharedPreferences spDomain;
    public Context mcontext = this;
    public static final String DOMAINPREFERENCE = "domain";
    String TAG = TotalPickListActivity.class.getSimpleName();
    int Usercount = 0;
    ECRApplication app = new ECRApplication();
    private boolean showKeyboard = false;
    private boolean visible = false;
    private TextToSpeak mTextToSpeak;
    public String _lastUpdateQty = "0";
    protected int mProcNo = 0;
    public static final int PROC_SHOP = 1;
    public static final int PROC_KEY = 2;
    public static final int PROC_NONE = 3;
    public static final int PROC_ORDERID = 4;
    public static final int PROC_ORDERNO = 5;
    public static final int PROC_TRACKID = 6;
    public static final int PROC_QTY = 7;
    public static final int PROC_PNAME = 8;
    ArrayList<BarcodeData> arr;
    DataManager manager;
    progresBar progress;
    String Date="",shipid="",authod="",adminid="",batchid="",ProductID="",ID="",OrderID="";
    DataManager.SubmitInvoiceTascall submitcall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newinvoice_check);
        ButterKnife.bind(this);

        Log.d(TAG,"On Create ");
        Log.d(TAG,"orderRequestSettings "+orderRequestSettings);

        orderRequestSettings = BaseActivity.getOrderInfoBy();
        if (orderRequestSettings == SettingActivity.ORDER_ID) {
            orderIdLayout.setVisibility(View.VISIBLE);
            orderNoLayout.setVisibility(View.GONE);
            trackingNumberLayout.setVisibility(View.GONE);
        } else if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
        {
            orderIdLayout.setVisibility(View.GONE);
            orderNoLayout.setVisibility(View.VISIBLE);
            trackingNumberLayout.setVisibility(View.GONE);
        }
        else if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO)
            {
                orderIdLayout.setVisibility(View.GONE);
                orderNoLayout.setVisibility(View.GONE);
                trackingNumberLayout.setVisibility(View.VISIBLE);
            }
        submitcall = this;

        Intent intent = getIntent();

        Date=intent.getStringExtra("date");
        shipid=intent.getStringExtra("shopid");
        authod=intent.getStringExtra("authid");
        adminid=intent.getStringExtra("adminid");
        batchid=intent.getStringExtra("batchid");

        showKeyboard = BaseActivity.getaddKeyboard();
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        if (orderRequestSettings == SettingActivity.ORDER_ID)
            setProc(PROC_ORDERID);
        else if (orderRequestSettings == SettingActivity.ORDER_NUMBER)
            setProc(PROC_ORDERNO);
        else if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO)
            setProc(PROC_TRACKID);

        mTextToSpeak = new TextToSpeak(this);
        arr = new ArrayList<>();
        manager = new DataManager();
        progress= new progresBar(this);

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
        _sts(R.id.orderNo,"");
        _sts(R.id.trackingNumber,"");
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
      /*  String s = mBuff.toString();
          mBuff.delete(0, mBuff.length());
         inputedEvent(s);
         qtybtn.performClick();
     */

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

    @OnClick(R.id.relLayout1)
    void onAddLayoutClick() {
        //TODO implement
        finish();
    }


    @OnClick(R.id.boxsubmit)
    void onboxsubmitClick() {
        String order = "";
        String getby = "";

        if (orderRequestSettings == SettingActivity.ORDER_ID){
            order = _gts(R.id.orderId);
            getby = "";
        }
        else if (orderRequestSettings == SettingActivity.ORDER_NUMBER) {
            order = _gts(R.id.orderNo);
            getby = "orderno";
        }
        else if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO){
            order = _gts(R.id.trackingNumber);
            getby = "mediacode";
        }


        // submitcall

        progress.Show();
        if (BaseActivity.getsinclude()) {
            manager.SubmitInvoiceTas(adminid, authod, shipid, batchid, "0", order,getby, submitcall);
        }else{
            manager.SubmitInvoiceTas(adminid, authod, shipid, batchid, "1", order,getby, submitcall);
        }
    }

    public void SubmitAPI(){
        String order = "";
        String getby = "";

        if (orderRequestSettings == SettingActivity.ORDER_ID){
            order = _gts(R.id.orderId);
            getby = "";
        }
        else if (orderRequestSettings == SettingActivity.ORDER_NUMBER) {
            order = _gts(R.id.orderNo);
            getby = "orderno";
        }
        else if (orderRequestSettings == SettingActivity.ORDER_TRACKING_NO){
            order = _gts(R.id.trackingNumber);
            getby = "mediacode";
        }


        progress.Show();
        if (BaseActivity.getsinclude()) {
            manager.SubmitInvoiceTas(adminid, authod, shipid, batchid, "0", order,getby, submitcall);
        }else{
            manager.SubmitInvoiceTas(adminid, authod, shipid, batchid, "1", order, getby, submitcall);
        }
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
                manager.GetIvoicetas(adminid,authod,shipid,batchid,orderId.getText().toString(),"",this);
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
                manager.GetIvoicetas(adminid,authod,shipid,batchid,order1,"orderno",this);
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
                manager.GetIvoicetas(adminid,authod,shipid,batchid,track,  "mediacode",this);
                break;

            case PROC_QTY:
                String boxbar =  _gts(R.id.scanbarcode);

            /*    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())){
                    SubmitAPI();
                }else if(boxbar.equalsIgnoreCase("000"+boxtext.getText().toString())){
                    SubmitAPI();
                }else{
                    U.beepError(this, "箱番号が違います。");
                    _gt(R.id.scanbarcode).setFocusableInTouchMode(true);


                }*/
                if (boxtext.getText().toString().length()==1){
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())){
                        SubmitAPI();
                    }else if(boxbar.equalsIgnoreCase("000"+boxtext.getText().toString())){
                        SubmitAPI();
                        break;
                    }else{
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.scanbarcode).setFocusableInTouchMode(true);
                    }
                }else if(boxtext.getText().toString().length()==2){
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())){
                        SubmitAPI();


                    }else if(boxbar.equalsIgnoreCase("00"+boxtext.getText().toString())){
                        SubmitAPI();
                    }else{
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.scanbarcode).setFocusableInTouchMode(true);
                    }
                }else if(boxtext.getText().toString().length()==3){
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())){
                        SubmitAPI();
                    }else if(boxbar.equalsIgnoreCase("0"+boxtext.getText().toString())){
                        SubmitAPI();
                    }else{
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.scanbarcode).setFocusableInTouchMode(true);
                    }
                }else{
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())){
                        SubmitAPI();
                    }else{
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
        }else if (mProcNo == PROC_QTY){
            _sts(R.id.scanbarcode, barcode);
            //       String boxbar =  _gts(R.id.scanbarcode);

               /* if (boxtext.getText().toString().length()==1){
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())){
                        SubmitAPI();
                    }else if(boxbar.equalsIgnoreCase("000"+boxtext.getText().toString())){
                        SubmitAPI();

                    }else{
                        U.beepError(this, "箱番号が違います。");
                        _gt(R.id.scanbarcode).setFocusableInTouchMode(true);
                    }
                }else if(boxtext.getText().toString().length()==2){
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())){
                        SubmitAPI();
                    }else if(boxbar.equalsIgnoreCase("00"+boxtext.getText().toString())){
                        SubmitAPI();
                    }else{
                        U.beepError(this, "箱番号が違います。");
                        _gt(R.id.scanbarcode).setFocusableInTouchMode(true);
                    }
                }else if(boxtext.getText().toString().length()==3){
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())){
                        SubmitAPI();
                    }else if(boxbar.equalsIgnoreCase("0"+boxtext.getText().toString())){
                        SubmitAPI();
                    }else{
                        U.beepError(this, "箱番号が違います。");
                        _gt(R.id.scanbarcode).setFocusableInTouchMode(true);
                    }
                }else{
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())){
                        SubmitAPI();
                    }else{
                        U.beepError(this, "箱番号が違います。");
                        _gt(R.id.scanbarcode).setFocusableInTouchMode(true);
                    }
                }*/
        }
        else if(mProcNo == PROC_ORDERNO) {
            _sts(R.id.orderNo, barcode);
        }        else if(mProcNo == PROC_TRACKID ){
            _sts(R.id.trackingNumber, barcode);
        }
        // }
        this.inputedEvent(barcode, true);
    }


    @Override
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
            U.beepError(this,message.getMessage());
            boxtext.setText("");
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
