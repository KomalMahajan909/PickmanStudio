package com.itechvision.ecrobo.pickman.Chatman.PickWork.NewBatcPicking;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TotalPickListActivity;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.BarcodeData;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.BarcodePickRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.BarcodePickResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.GetBatchStatus.GetBatchStatusResult;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.GetBatchStatus.GetBatchStatus_Req;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.SubmitBarcodeRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.SubmitBarcodeResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.SharedPrefrences;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

public class NewdbatchPickingActivity extends BaseActivity implements View.OnClickListener, DataManager.barcodeBatchcall , DataManager.SubmitBarcodecall , DataManager.GetBAtchStatuscall {

    @BindView(R.id.barcodeLabel) TextView barcodeLabel;
    @BindView(R.id.barcode) EditText Barcode;
    @BindView(R.id.quantitytext) TextView quantitytext;
    @BindView(R.id.boxtext) TextView boxtext;
    @BindView(R.id.layout_number) RelativeLayout layoutNumber;
    @BindView(R.id.viewpager) ViewPager viewpager;
    @BindView(R.id.rl_box) LinearLayout rl_box;
    @BindView(R.id.layout_main) LinearLayout layoutMain;
    @BindView(R.id.add_layout) Button add_layout;
    @BindView(R.id.relMainAction) LinearLayout relMainAction;
    @BindView(R.id.relLayout1) RelativeLayout relLayout1;
    @BindView(R.id.img1ActionBar) ImageView img1ActionBar;
    @BindView(R.id.relLayout3) RelativeLayout relLayout3;
    @BindView(R.id.txtTitle) TextView txtTitle;
    @BindView(R.id.relLayout2) RelativeLayout relLayout2;
    @BindView(R.id.notif_count_blue) Button totalcount;
    @BindView(R.id.qtybtn) Button qtybtn;
    @BindView(R.id.notif_count_red) Button finishedbox;
    @BindView(R.id.notif_count_green) Button pendingbox;
    @BindView(R.id.ll_boxbarcode) LinearLayout llBoxbarcode;
    @BindView(R.id.boxbarcodeLabel) TextView boxbarcodeLabel;
    @BindView(R.id.boxbarcode) EditText boxbarcode;
    @BindView(R.id.qty) EditText qty;

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

    public static final int PROC_BARCODE = 1;
    public static final int PROC_QTY = 2;
    public static final int PROC_BOX = 3;
    ArrayList<BarcodeData> arr;
    String Date="",shipid="",authod="",adminid="",batchid="",ProductID="",ID="",OrderID="",totalquantity = "",code= "",c="",
    barcodeScanned ="0";
    DataManager manager;
    progresBar progress;
    DataManager.SubmitBarcodecall callsub;
    public  static  String back="0",Datte ="";
    public final String LOCATION = "Box";
    public final String QTY = "quantity";
    public final String LOCATIONJAP = "ボックス";
    public final String QTYJAP = "数量";
    public final Handler handlerLoc = new Handler();
    public Runnable runnableCode = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newdbatch_picking);
        ButterKnife.bind(this);
        callsub = this ;

        Intent intent = getIntent();
        Log.d(TAG,"On Create ");

        Date=intent.getStringExtra("date");
        shipid=intent.getStringExtra("shopid");
        authod=intent.getStringExtra("authid");
        adminid=intent.getStringExtra("adminid");
        batchid=intent.getStringExtra("batchid");

        showKeyboard = BaseActivity.getaddKeyboard();
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        setProc(PROC_BARCODE);
        mTextToSpeak = new TextToSpeak(this);
        arr = new ArrayList<>();
        manager = new DataManager();
        progress= new progresBar(this);
        progress.Show();
        GetBatchStatus_Req req =new GetBatchStatus_Req(authod,adminid,shipid,batchid);
        manager.GetBatchStatus(req,this);
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
        setProc(PROC_BARCODE);
        Barcode.setText("");
        boxbarcode.setText("");
        qtybtn.setVisibility(View.GONE);
        rl_box.setVisibility(View.GONE);
        llBoxbarcode.setVisibility(View.GONE);
        quantitytext.setText("0");
        boxtext.setText("0");

    }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);

    }

    @Override
    public void clearEvent() {
        Barcode.setText("");
        boxbarcode.setText("");
        qty.setText("");

        qtybtn.setVisibility(View.GONE);
        rl_box.setVisibility(View.GONE);
        llBoxbarcode.setVisibility(View.GONE);
        quantitytext.setText("0");
        boxtext.setText("0");
        setProc(PROC_BARCODE);
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking("Clear");

    }

    @Override
    public void allclearEvent() {
        setProc(PROC_BARCODE);
        Barcode.setText("");
        boxbarcode.setText("");
        qtybtn.setVisibility(View.GONE);
        rl_box.setVisibility(View.GONE);
        llBoxbarcode.setVisibility(View.GONE);
        quantitytext.setText("0");
        boxtext.setText("0");

        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking("Clear");
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
            case PROC_QTY:    // バーコード
                _sts(R.id.qty, buff);

                break;
            case PROC_BOX:    // バーコード
                _sts(R.id.boxbarcode, buff);

                break;

        }

    }

    @OnClick(R.id.notif_count_blue)void click(){
        if (totalcount.getText().toString().equalsIgnoreCase("0")){

        }else{
            Intent in = new Intent(NewdbatchPickingActivity.this, BoxstatusBatchActivity.class);
            in.putExtra("date",Date);
            in.putExtra("batchid",batchid);
            in.putExtra("adminid",adminid);
            in.putExtra("shopid",shipid);
            in.putExtra("authid", authod);
            in.putExtra("barcode", Barcode.getText().toString());
            startActivity(in);
        }

    }

    @OnClick(R.id.notif_count_red)void click1(){

        if (finishedbox.getText().toString().equalsIgnoreCase("0")){

        }else{
            Intent in = new Intent(NewdbatchPickingActivity.this, BoxstatusBatchActivity.class);
            in.putExtra("date",Date);
            in.putExtra("batchid",batchid);
            in.putExtra("adminid",adminid);
            in.putExtra("shopid",shipid);
            in.putExtra("authid", authod);
            in.putExtra("barcode", Barcode.getText().toString());
            startActivity(in);
        }

    }

    @OnClick(R.id.notif_count_green)void click2(){

        if (pendingbox.getText().toString().equalsIgnoreCase("0")){

        }else{
            Intent in = new Intent(NewdbatchPickingActivity.this, BoxstatusBatchActivity.class);
            in.putExtra("date",Date);
            in.putExtra("batchid",batchid);
            in.putExtra("adminid",adminid);
            in.putExtra("shopid",shipid);
            in.putExtra("authid", authod);
            in.putExtra("barcode", Barcode.getText().toString());
            startActivity(in);
        }

    }

    @Override
    public void enterEvent() {



        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
//       qtybtn.performClick();

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {

            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, barcode);
                break;

            case PROC_QTY:    // バーコード
                _sts(R.id.qty, barcode);
                break;
            case PROC_BOX:    // バーコード
                _sts(R.id.boxbarcode, barcode);
                break;

        }
    }

    @OnClick(R.id.relLayout1)
    void onAddLayoutClick() {
        //TODO implement

        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();
        finish();
    }


    @OnClick(R.id.productcheck)
    void onProductcheckClick() {
        //TODO implement
        if (Barcode.getText().toString().length()==0){
            U.beepError(this,"Please Enter barcode");
        }else{
            Intent in = new Intent(NewdbatchPickingActivity.this, BatchProductlistActivity.class);
            in.putExtra("date",Date);
            in.putExtra("batchid",batchid);
            in.putExtra("adminid",adminid);
            in.putExtra("shopid",shipid);
            in.putExtra("authid", authod);
            in.putExtra("barcode", Barcode.getText().toString());
            startActivity(in);

        }

    }

    @OnClick(R.id.boxsubmit)
    void onboxsubmitClick() {
        //TODO implement
         SubmitAPI();
//245151


    }

    public void SubmitAPI(){
        mTextToSpeak.resetQueue();
        handlerLoc.removeCallbacks(runnableCode);
        progress.Show();
        if (BaseActivity.getsinclude()){
            //"0"
            SubmitBarcodeRequest req = new SubmitBarcodeRequest(authod,adminid,shipid,Date,batchid,ID,qty.getText().toString()
                    ,OrderID,ProductID ,"0", SharedPrefrences.get_Tas_ReShip(NewdbatchPickingActivity.this),getResources().getString(R.string.version));
            manager.SubmitBarcodeBatch(req,callsub);
        }else{
            SubmitBarcodeRequest req = new SubmitBarcodeRequest(authod,adminid,shipid,Date,batchid,ID,qty.getText().toString()
                    ,OrderID,ProductID ,"1",SharedPrefrences.get_Tas_ReShip(NewdbatchPickingActivity.this),getResources().getString(R.string.version));
            manager.SubmitBarcodeBatch(req,callsub);
        }
    }

    @OnClick(R.id.qtybtn)
    void onqtybtnClick() {
        //TODO implement
        progress.Show();
        BarcodePickRequest req = new BarcodePickRequest(authod,adminid,shipid,Date,batchid, Barcode.getText().toString());
        manager.BatchPickBarcode(req,this);

    }

    @OnClick(R.id.add_layout)
    void add_layout() {

        if (showKeyboard == false) {
            layoutNumber.setVisibility(View.VISIBLE);
            showKeyboard = true;
        } else {
            layoutNumber.setVisibility(View.GONE);
            showKeyboard = false;
        }
    }

    @Override
    public void onClick(View v) {

    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {

            case PROC_BARCODE:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.boxbarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.qty).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;

            case PROC_QTY:
                _gt(R.id.boxbarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.qty).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
            case PROC_BOX:
                _gt(R.id.qty).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.boxbarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;

        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {

            case PROC_BARCODE:
                String barcode = _gts(R.id.barcode);
                //  Log.e(TAG,"inputedEvent_BARCODE  "+barcode);

                if (barcode.equals("")) {
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }
                //   boolean match = checkBarcode(buff);
                progress.Show();
                BarcodePickRequest req = new BarcodePickRequest(authod,adminid,shipid,Date,batchid, Barcode.getText().toString());
                manager.BatchPickBarcode(req,this);

                break;

            case PROC_QTY:
                Log.e(TAG, "inputedEvent_PROC_QTY");
                String qty = _gts(R.id.qty);
                String barcode1 = _gts(R.id.barcode);

                if (isScaned) {

                    boolean match1 = checkBarcode(buff);
                    if (!match1) {
                        U.beepError(this, "バーコードが一致しません");
                        _gt(R.id.barcode).setFocusableInTouchMode(true);
                        break;
                    }

                    Log.e("BarcodeScanValue", barcodeScanned);

                    if (U.compareNumeric(barcodeScanned,quantitytext .getText().toString()) == 0) {
                        U.beepError(this, "Qunatity cannot exceed the required quantity");
                        break;
                    }

                    if (buff.equals(barcode1)) {

                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned" +qty);

                        qty = U.plusTo(barcodeScanned, "1");
                        barcodeScanned = qty;
                        Toast.makeText(this, barcodeScanned, Toast.LENGTH_SHORT).show();
                        mTextToSpeak.resetQueue();
                        handlerLoc.removeCallbacks(runnableCode);
                        _sts(R.id.qty, barcodeScanned);
                        if (Integer.parseInt(barcodeScanned) > 1)
                            mTextToSpeak.startSpeaking(barcodeScanned);

                        //   _lastUpdateQty = _gts(R.id.qty);
                        /* check if update in quantity need next action */
                        if (barcodeScanned.equals(quantitytext.getText().toString())) {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned222222");

                            //API HIT PICKED

                            try {
                                // progress.Show();
                                Log.e("API 1",">>>>>>>> API 1 Scan");
                                setProc(PROC_BOX);

                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    } else {
                        //U.beepError(this, "You scan wrong barcode");
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned3333");
                        U.beepError(this, "Barcode dont match");
                        Toast.makeText(getApplicationContext(), "BarCode Doesn't Match", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_Scanned");
                    if ("".equals(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(R.id.qty).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.qty).setFocusableInTouchMode(true);
                        break;
                    }

                    String processedCnt = barcodeScanned;
                    String qtyUpdate = U.minusTo(qty, _lastUpdateQty);
                    String maxQty_ = U.minusTo(quantitytext.getText().toString(), processedCnt);

                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_lastUpdateQTY");
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());
                        _gt(R.id.qty).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_qtyUpdate");
                        U.beepError(this, "数量が多すぎます");
                        _gt(R.id.qty).setFocusableInTouchMode(true);
                        break;
                    } else {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt");
                   //   cProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                   //   barcodeScanned =  U.plusTo(processedCnt, qtyUpdate);
                        mTextToSpeak.startSpeaking(_gts(R.id.qty));
                        handlerLoc.removeCallbacks(runnableCode);

                        if (barcodeScanned.equals(quantitytext.getText().toString())) {
                            //API HIT PICKED
                            try {

                                setProc(PROC_BOX);
                             } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }
                            //  fixedRequest();

                        } else {
                            //incomplete
                            try {

                                setProc(PROC_BOX);


                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                }

                break;
            case PROC_BOX:

                String boxbar =  _gts(R.id.boxbarcode);

                if (boxtext.getText().toString().length()==1){
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())){
                         SubmitAPI();
                    }else if(boxbar.equalsIgnoreCase("000"+boxtext.getText().toString())){
                        SubmitAPI();
                    }else{
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.boxbarcode).setFocusableInTouchMode(true);
                    }
                }else if(boxtext.getText().toString().length()==2){
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())){
                         SubmitAPI();
                    }else if(boxbar.equalsIgnoreCase("00"+boxtext.getText().toString())){
                         SubmitAPI();
                    }else{
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.boxbarcode).setFocusableInTouchMode(true);
                    }
                }else if(boxtext.getText().toString().length()==3){
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())){
                         SubmitAPI();
                    }else if(boxbar.equalsIgnoreCase("0"+boxtext.getText().toString())){
                         SubmitAPI();
                    }else{
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.boxbarcode).setFocusableInTouchMode(true);
                    }
                }else{
                    if (boxbar.equalsIgnoreCase(boxtext.getText().toString())){
                         SubmitAPI();
                    }else{
                        U.beepNGbox(this, "箱番号が違います。");

                        _gt(R.id.boxbarcode).setFocusableInTouchMode(true);
                    }
                }
                break;

        }
    }

    private boolean checkBarcode(String buff) {
        boolean match = false;

        if (buff.equals(_gts(R.id.barcode))){
            match = true ;

        }else if (buff.equals(code)){
            match = true;
        }

        return match;
    }


    @Override
    public void scanedEvent(String barcode) {
        Log.e(TAG, "ScannedEventttttt   is " + barcode);

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
                    Log.e(TAG, "Barcode after chopping first and last character becomes " + result);
                    barcode = result;
                }
            }
            String finalbarcode = CommonFunctions.getBracode(barcode);
            barcode = finalbarcode;
            _sts(R.id.barcode, barcode);
            //  setProc(PROC_QTY);
        }else if(mProcNo == PROC_BOX){
            _sts(R.id.boxbarcode, barcode);
        }
        else if(mProcNo == PROC_QTY){
            String finalbarcode = CommonFunctions.getBracode(barcode);
            barcode = finalbarcode;
            Log.e(TAG, "barcode111   " + barcode);
        }

        this.inputedEvent(barcode, true);
    }

    public void   nextWork() {
        Log.e(TAG, "nextworkkkkkk");
        // cProductList.put("processedCnt", "1");
        _sts(R.id.qty,"1");

        barcodeScanned = _gts(R.id.qty);
      //  mTextToSpeak.startSpeaking("1");

        _lastUpdateQty = _gts(R.id.qty);
        setProc(PROC_QTY);
        if (qty.getText().toString().equals(quantitytext.getText().toString())) {
            setProc(PROC_BOX);
         }
    }

    @Override
    public void onSucess(int status, BarcodePickResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("0")){
             totalcount.setText(message.getTotal_rows());
             finishedbox.setText(message.getFinished());
             pendingbox.setText(message.getPending());

            arr = message.getBarcode_data() ;

            if (arr.size() != 0) {
                quantitytext.setText(arr.get(0).getQuantity());
                boxtext.setText(arr.get(0).getBatchno());
                ProductID=    arr.get(0).getProduct_id();
                ID=    arr.get(0).getId();
                OrderID=    arr.get(0).getOrder_id();
            //  code = arr.get(0).getCode();

                c =   "BOX No"+arr.get(0).getBatchno()+"がかんりょうしました。";

                barcodeScanned ="0";
                rl_box.setVisibility(View.VISIBLE);
                qtybtn.setVisibility(View.GONE);
                llBoxbarcode.setVisibility(View.VISIBLE);
                repeatLocation(1000);
//                setProc(PROC_BOX);
                nextWork();
            }else{
                U.beepError(this,message.getMessage());
                Log.e("Error",":-   "+ message.getMessage()   );
            }

        }else if (message.getCode().equalsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(NewdbatchPickingActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        }else {
            U.beepError(this,message.getMessage());
            Log.e("Error",":-   "+ message.getMessage()   );
        }
    }

    @Override
    public void onSucess(int status, SubmitBarcodeResponse message) throws JsonIOException {

        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("0")){
            U.TasNewBeep(this,null);
            if (message.getStatus().equalsIgnoreCase("pending")){
                rl_box.setVisibility(View.GONE);
                llBoxbarcode.setVisibility(View.GONE);
                qtybtn.setVisibility(View.GONE);
                quantitytext.setText("0");
                boxtext.setText("0");
                Barcode.setText("");
                qty.setText("");
                boxbarcode.setText("");
                totalcount.setText(message.getTotal_rows());
                finishedbox.setText(message.getFinished());
                pendingbox.setText(message.getPending());
                setProc(PROC_BARCODE);
                handlerLoc.removeCallbacks(runnableCode);
                mTextToSpeak.resetQueue();
                if (message.getBox_status().equalsIgnoreCase("pending")){
                 // mTextToSpeak.startSpeaking("オッケ");

                 }else{

                   Toast.makeText(this, "ボックスが完了しました", Toast.LENGTH_LONG).show();
                    //U.beepOK(this,"オッケ");
                 //   U.boxOK(this,"オッケ");
                     mTextToSpeak.startSpeaking("ボックスがかんりょうしました");
                }

            }else{
                /*handlerLoc.removeCallbacks(runnableCode);
                mTextToSpeak.resetQueue();*/
                mTextToSpeak.startSpeaking("ぶんぱいがかんりょうしました");
           //  String a=   "BOX No"+boxtext.getText().toString()+"BOX No1がかんりょうしました。";
           //  String a=   "BOX No"+boxtext.getText().toString()+"が完了しました。";
             String a=   "分配が完了しました。";
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        // .setTitle("アラート!")
                        .setCancelable(false)
                        .setMessage(a)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                back = "1";
                                Datte = Date ;
                                finish();
                            }
                        })
                        .show();

            }
         }else if (message.getCode().equalsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(NewdbatchPickingActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })
                     .show();
        }else {
            U.beepError(this,message.getMessage());
            Log.e("Error",":-   "+ message.getMessage()   );
        }


    }

    @Override
    public void onSucess(int status, GetBatchStatusResult message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){

            totalcount.setText(message.getResult().get(0).getWorking());
            finishedbox.setText(message.getResult().get(0).getCompleted());
            pendingbox.setText( message.getResult().get(0).getPending());
        }
         else if (message.getCode().equalsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(NewdbatchPickingActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        }else {
            U.beepError(this,message.getMessage());
            Log.e("Error",":-   "+ message.getMessage()   );
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



    public void repeatLocation(int delay) {
        Log.e(TAG,"repeatLocationnnnnnnnnnnn");
//        mTextToSpeak.resetQueue();
        final int INTERVAL = delay;
        runnableCode = new Runnable() {
            @Override
            public void run() {
                Log.e(TAG,"repeatLocation_handlereee");
                int lang =  BaseActivity.getlangpos();
                String tts1 = "";
                String tts3 = "";
                String tts4 = "";
                if(lang == 1){

                  //  tts3 = BARCODE;
                    tts4 = QTYJAP;
                    tts1 = LOCATIONJAP;

                    tts4 = QTY;
                    tts1 = LOCATION;
                }
                else {
                    tts4 = QTYJAP;
                    tts1 = LOCATIONJAP;

                    //   tts3 = BARCODE;

                }

              //String code = lastFourDigits;
                String quantity = quantitytext.getText().toString();
                String loca = boxtext.getText().toString();
                String tts5 = quantity;
                String tts2 = loca;

             //   String tts6 = code;

                mTextToSpeak.startSpeaking(tts4, tts5);

                mTextToSpeak.playsilence();
               /* String codeChars[] = tts6.split("");
                mTextToSpeak.startSpeaking(tts3);
                for (String chars : codeChars) {
                    mTextToSpeak.startSpeaking(chars);
                }*/
                 mTextToSpeak.startSpeaking(tts1, tts2);
                handlerLoc.postDelayed(runnableCode, INTERVAL);
            }
        };
        handlerLoc.post(runnableCode);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        mTextToSpeak.resetQueue();

    }


    @Override
    protected void onPause() {
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mTextToSpeak.resetQueue();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mTextToSpeak.resetQueue();
        mTextToSpeak.onStopSpeaking();
        Log.d(TAG,"On onDestroy ");
        super.onDestroy();
    }

}

