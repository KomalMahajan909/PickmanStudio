package com.itechvision.ecrobo.pickman.Chatman.PickWork.TasPicking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TotalPickListActivity;
import com.itechvision.ecrobo.pickman.Models.BatchPick.ClearBtotal.BtotalClearResult;
import com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking.TasBarcoderesult;
import com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking.TasPickBarcodeRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking.TasSkipResult;
import com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking.TasSubmitResult;
import com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking.TasbarcodeData;
import com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking.tasSubmitRequest;
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

public class NewtaspickingBarcodeActivity extends BaseActivity implements DataManager.GetTasBarcodecall
    , DataManager.TasSubmitcall , DataManager.SkipTascall , DataManager.CleatBatchcall {

    @BindView(R.id.locationlabel) TextView locationlabel;
    @BindView(R.id.location) TextView location;
    @BindView(R.id.barcodeLabel) TextView barcodeLabel;
    @BindView(R.id.batchno) TextView batchno;
    @BindView(R.id.barcode) EditText barcode;
    @BindView(R.id.qtyLabel) TextView qtyLabel;
    @BindView(R.id.qty) EditText qty;
    @BindView(R.id.partlabel) TextView partlabel;
    @BindView(R.id.partno) TextView partno;
    @BindView(R.id.bardisple) TextView bardisple;
    @BindView(R.id.ordernumber) TextView ordernumber;
    @BindView(R.id.productname) TextView productname;
    @BindView(R.id.totalqtyLabel) TextView totalqtyLabel;
    @BindView(R.id.spec_one) TextView spec_one;
    @BindView(R.id.spec_two) TextView spec_two;
    @BindView(R.id.skucount) Button skucount;
    @BindView(R.id.totalqty) TextView totalqty;
    @BindView(R.id.expridate) TextView expridate;
    @BindView(R.id.lotno) TextView lotno;
    @BindView(R.id.back) ImageView back;
    @BindView(R.id.layout_number) RelativeLayout layoutNumber;
    @BindView(R.id.viewpager) ViewPager viewpager;

    String Date="",shipid="",authod="",adminid="",batchid="",Barcode="" ,BarcodeScanvalue = "0",Pushid="",ROWNO="";
    DataManager manager;
    progresBar progress;
    private boolean showKeyboard = false;
    private boolean visible = false;
    private TextToSpeak mTextToSpeak;
    public String _lastUpdateQty = "0";
    protected int mProcNo = 0;
    public static final int PROC_SHOP = 1;
    public static final int PROC_KEY = 2;
    public static final int PROC_NONE = 3;
    public static final int PROC_BARCODE = 4;
    public static final int PROC_QTY = 5;
    public static final int PROC_PNAME = 6;
    public Context mcontext = this;
    String TAG = NewtaspickingBarcodeActivity.class.getSimpleName();
    ArrayList<String> codeList = new ArrayList<>();
    DataManager.TasSubmitcall submitcall ;
    ArrayList<TasbarcodeData> arr ;
    public final Handler handlerLoc = new Handler();
    public Runnable runnableCode = null;
    public final String LOCATION = "location";
    public final String CODE = "code";
    public final String QTY = "quantity";
    public final String BARCODE = "barcode";
    public final String LOCATIONJAP = "ロケ";
    public final String CODEJAP = "コード";
    public final String QTYJAP = "数量";
    DataManager.GetTasBarcodecall callget;
    DataManager.SkipTascall skip;
    DataManager.CleatBatchcall clearbatch ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtaspicking_barcode);
        ButterKnife.bind(this);
        submitcall = this;
        callget = this;
        skip = this;
        clearbatch = this;

        Log.d(TAG,"On Create ");

        manager = new DataManager();
        progress = new progresBar(this);
        arr = new ArrayList<>();

        showKeyboard = BaseActivity.getaddKeyboard();
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        mTextToSpeak = new TextToSpeak(this);
        Intent intent = getIntent();

        Date=intent.getStringExtra("date");
        shipid=intent.getStringExtra("shopid");
        authod=intent.getStringExtra("authid");
        adminid=intent.getStringExtra("adminid");
        batchid=intent.getStringExtra("batchid");
        progress.Show();

        TasPickBarcodeRequest req = new TasPickBarcodeRequest(authod,adminid,Date,shipid,batchid,"");
        manager.GetTasBarcode(req,this);

    }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        setProc(PROC_BARCODE);
        barcode.setText("");
        qty.setText("");
        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking("Clear");
        BarcodeScanvalue ="0";
    }

    @Override
    public void allclearEvent() {

                new AlertDialog.Builder(NewtaspickingBarcodeActivity.this)
                .setTitle("バッチ内作業が全てクリアされます")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progress.Show();
                        mTextToSpeak.resetQueue();
                        handlerLoc.removeCallbacks(runnableCode);
                        manager.ClearBtotal(adminid,authod, shipid,batchid,Date,clearbatch);

                    }
                })
                .setNegativeButton("NO", null)
                .show();


    }

    @Override
    public void skipEvent() {
                new AlertDialog.Builder(NewtaspickingBarcodeActivity.this)
                .setTitle("スキップしてよろしいですか")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  mTextToSpeak.startSpeaking("Skip");
                        progress.Show();
                        mTextToSpeak.resetQueue();
                        handlerLoc.removeCallbacks(runnableCode);
                        manager.SkipTas(adminid,authod,Pushid,shipid,batchid,ROWNO,skip);
                    }
                })
                .setNegativeButton("いいえ", null)
                .show();
    }

    @Override
    public void nextProcess() {
    }

    public int convert(int x) {
        Resources r = mcontext.getResources();
        int px = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, x, r.getDisplayMetrics() );
        return px;
    }

    @OnClick(R.id.add_layout) void onAddLayoutClick() {

        if (showKeyboard == false) {
            layoutNumber.setVisibility(View.VISIBLE);
            showKeyboard = true;
        } else {
            layoutNumber.setVisibility(View.GONE);
            showKeyboard = false;
        }
     }

    @OnClick(R.id.back) void obackClick() {
         mTextToSpeak.resetQueue();
         finish();
    }

    @OnClick(R.id.enter) void onEnterClick() {
         SubmitApi();
    }

    public void SubmitApi(){
        progress.Show();
        tasSubmitRequest req = new tasSubmitRequest(authod,adminid,Date,shipid,batchid,barcode.getText().toString(),qty.getText().toString(),Pushid,ROWNO,getResources().getString(R.string.version));
        manager.TasBarcodeSubmit(req,submitcall);
    }


    @OnClick(R.id.clear) void onClearClick() {
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.qty, buff);
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
                _sts(R.id.qty, barcode);
                break;
        }
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {

            case PROC_SHOP:
                //  spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                // _gt(R.id.et_key).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.qty).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.productName).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;

            case PROC_BARCODE:
                //   spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                // _gt(R.id.et_key).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.qty).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                //   _gt(R.id.productName).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;

            case PROC_KEY:

             // spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
            //  _gt(R.id.et_key).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.qty).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.productName).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
//              _gt(R.id.et_key).setFocusableInTouchMode(true);
//              _gt(R.id.et_key).setEnabled(true);
            //  showTruitonDatePickerDialog(dateselect);
                break;

            case PROC_PNAME:

            //  spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
             // _gt(R.id.et_key).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.qty).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.productName).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.productName).setFocusableInTouchMode(true);
                _gt(R.id.productName).setEnabled(true);
            //  showTruitonDatePickerDialog(dateselect);
                break;

            case PROC_QTY:
            //  spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
             // _gt(R.id.et_key).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.qty).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
             // _gt(R.id.productName).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.qty).setFocusableInTouchMode(true);

                if (_gts(R.id.qty).equals("1"))
                    mTextToSpeak.startSpeaking("1");
                break;
            case PROC_NONE:
           //   spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
             // _gt(R.id.et_key).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.qty).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.productName).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
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
                boolean match = checkBarcode(buff);
                if (!match) {
                    U.beepError(this, "バーコードが一致しません");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }
                handlerLoc.removeCallbacks(runnableCode);
                _sts(R.id.qty, "1");
                nextWork();

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

                    Log.e("BarcodeScanValue", BarcodeScanvalue);

                    if (U.compareNumeric(BarcodeScanvalue, totalqty.getText().toString()) == 0) {
                        U.beepError(this, "Qunatity cannot exceed the required quantity");
                        break;
                    }

                    if (buff.equals(barcode1)) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned");
                        qty = U.plusTo(BarcodeScanvalue, "1");
                        BarcodeScanvalue = qty;
                        Toast.makeText(this, BarcodeScanvalue, Toast.LENGTH_SHORT).show();

                        mTextToSpeak.resetQueue();
                        handlerLoc.removeCallbacks(runnableCode);

                        _sts(R.id.qty, BarcodeScanvalue);
                        mTextToSpeak.startSpeaking(BarcodeScanvalue);
                        if (Integer.parseInt(BarcodeScanvalue) > 1){
                            //   mTextToSpeak.startSpeaking(BarcodeScanvalue);
                        }

                        if (BarcodeScanvalue.equals(totalqty.getText().toString())) {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned22");
                            handlerLoc.removeCallbacks(runnableCode);
                            //API HIT PICKED
                             try {
                             // progress.Show();
                                Log.e("API 1",">>> API 1 Scan");
                                SubmitApi();
                             } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    } else {
                         Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned33");
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

                    String processedCnt = BarcodeScanvalue;
                   /* String qtyUpdate = U.minusTo(qty, _lastUpdateQty);
                    String maxQty_ = U.minusTo(totalqty.getText().toString(), processedCnt);*/

                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_lastUpdateQTY");
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());
                        _gt(R.id.qty).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(totalqty.getText().toString(), qty) > 0) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_qtyUpdate");
                        U.beepError(this, "数量が多すぎます");
                        _gt(R.id.qty).setFocusableInTouchMode(true);
                        break;
                    } else {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt");
                        // cProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        //   BarcodeScanvalue =  U.plusTo(processedCnt, qtyUpdate);
                        mTextToSpeak.startSpeaking(_gts(R.id.qty));
                        handlerLoc.removeCallbacks(runnableCode);
                        if (BarcodeScanvalue.equals(totalqty.getText().toString())) {
                            //APIHIT PICKED
                            try {

                                Log.e("API 2",">>>>>>>> API 2 Scan");
                                SubmitApi();

                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }
                            //  fixedRequest();

                        } else {
                            try {
                                handlerLoc.removeCallbacks(runnableCode);
                                Log.e("API 3",">>> API 3 Scan");
                                SubmitApi();

                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }

                break;
        }
    }


    @Override
    public void scanedEvent(String barcode) {
        Log.e(TAG, "ScannedEvent  is " + barcode);

        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEvent");

            if (mProcNo == PROC_BARCODE) {
                // check for QR code
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                Log.e(TAG, "Length of barcode is   " + barcode.length());
                if (BaseActivity.getUrl().equals("https://air-logi-st.air-logi.com/service") && BaseActivity.getShopId().equals("1011")) {
                    String result = "";
                    if (barcode.length() == 13) {
                        result = barcode.substring(0, barcode.length() - 1);
                        Log.e(TAG, "Barcode after chopping last character becomes " + result);
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

                if (barcode.equalsIgnoreCase(partno.getText().toString())){
                    setProc(PROC_QTY);
                }else{
                    setProc(PROC_BARCODE);
                }

            }

            if (mProcNo == PROC_QTY) {
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode = finalbarcode;
                Log.e(TAG, "barcode   " + barcode);
            }

        }
        this.inputedEvent(barcode, true);
    }

    boolean checkBarcode(String barcode) {
        boolean match = false;

        if (barcode.equalsIgnoreCase(arr.get(0).getBarcode())){
            match = true ;

        }else if (barcode.equalsIgnoreCase(partno.getText().toString())){
            match = true;
        }

        return match;
    }

    public void nextWork() {

        mTextToSpeak.resetQueue();
        _lastUpdateQty = _gts(R.id.qty);
        BarcodeScanvalue =  _gts(R.id.qty);
        setProc(PROC_QTY);
        if (qty.getText().toString().equals(totalqty.getText().toString())) {
         // fixedRequest();
            handlerLoc.removeCallbacks(runnableCode);
            SubmitApi();
        }
    }

    @Override
    public void onSucess(int status, TasBarcoderesult message) throws JsonIOException {
        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("0")){
            skucount.setText(message.getPending_sku());

            arr = message.getResults();

            location.setText(arr.get(0).getLocation());
            totalqty.setText(arr.get(0).getQunatity());
            partno.setText(arr.get(0).getCode());
            batchno.setText("バッチNo："+arr.get(0).getBatch_name());
            bardisple.setText(arr.get(0).getBarcode());
            ordernumber.setText(arr.get(0).getOrder_no());
            productname.setText(arr.get(0).getName());
            spec_one.setText(arr.get(0).getSpec1());
            spec_two.setText(arr.get(0).getSpec2());
            expridate.setText(arr.get(0).getExpiration_date());
            lotno.setText(arr.get(0).getLot());

            spec_one.setSelected(true);
            spec_two.setSelected(true);
            location.setSelected(true);
            partno.setSelected(true);
            productname.setSelected(true);
            ordernumber.setSelected(true);
            lotno.setSelected(true);
            expridate.setSelected(true);

            ROWNO = arr.get(0).getRow_no();
            // バッチNo：9
            Pushid=arr.get(0).getPsh_id();
            repeatLocation(1000);
            setProc(PROC_BARCODE);
        }else if (message.getCode().equalsIgnoreCase("1020")){
                    new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(NewtaspickingBarcodeActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })
                    .show();
        }else {
            U.beepError(this,message.getMessage());
            Log.e("Error",message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, TasSubmitResult message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){
            ROWNO = message.getResults().get(0).getRow_no();
            if (message.getResults().get(0).getBatch_status().equalsIgnoreCase("2")){
                 Toast.makeText(this,"バッチトータルピックが完了しました",Toast.LENGTH_SHORT).show();
            //   Toast.makeText(this,"バッチトータルピックがかんりょうしました",Toast.LENGTH_SHORT).show();
                 U.TasNewBeep(this,null);
             //  mTextToSpeak.startSpeaking("Batchi gōkei pikku ga kanryō shimashita");
                 mTextToSpeak.startSpeaking("バッチトータルピックがかんりょうしました");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                         finish();
                    }
                }, 3200);

            }else if(message.getResults().get(0).getBatch_status().equalsIgnoreCase("3")){
                U.TasNewBeep(this,null);
                mTextToSpeak.startSpeaking("けっぴんした商品があります");

                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        // .setTitle("アラート!")
                        .setCancelable(false)
                        .setMessage("欠品した商品があります！")
                      //.setMessage("けっぴんした商品があります")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .show();
             }else{

                barcode.setText("");
                qty.setText("");
                BarcodeScanvalue = "0";

                progress.Show();
                U.TasNewBeep(this,null);
                TasPickBarcodeRequest req = new TasPickBarcodeRequest(authod,adminid,Date,shipid,batchid,ROWNO);
                manager.GetTasBarcode(req,this);

            }
        }else if (message.getCode().equalsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(NewtaspickingBarcodeActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        }else {
            U.beepError(this,message.getMessage());
        }

    }

    //SkipTas
    @Override
    public void onSucess(int status, TasSkipResult message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")) {
            ROWNO = message.getResults().get(0).getRow_no();

            if (message.getResults().get(0).getBatch_status().equalsIgnoreCase("2")) {
                //1= working,2 complete,3= skip,0- pendind
                Toast.makeText(this,"バッチトータルピックが完了しました",Toast.LENGTH_SHORT).show();
                //   Toast.makeText(this,"バッチトータルピックがかんりょうしました",Toast.LENGTH_SHORT).show();
                U.TasNewBeep(this,null);
                //  mTextToSpeak.startSpeaking("Batchi gōkei pikku ga kanryō shimashita");
                mTextToSpeak.startSpeaking("バッチトータルピックがかんりょうしました");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        finish();
                    }
                }, 3200);

            }else if(message.getResults().get(0).getBatch_status().equalsIgnoreCase("3")){
                //  mTextToSpeak.startSpeaking("Ketsu-hin shita shōhin ga arimasu!");
                mTextToSpeak.startSpeaking("けっぴんした商品があります");

                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setCancelable(false)
                        .setMessage("欠品した商品があります！")
                        //.setMessage("けっぴんした商品があります")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .show();
            }else{
                barcode.setText("");
                qty.setText("");
                BarcodeScanvalue  = "0";
                progress.Show();
                U.TasNewBeep(this,null);
                TasPickBarcodeRequest req = new TasPickBarcodeRequest(authod,adminid,Date,shipid,batchid,ROWNO);
                manager.GetTasBarcode(req,this);
            }
        }else{
            U.beepError(this,message.getMessage());
        }

    }

    //Clear batch
    @Override
    public void onSucess(int status, BtotalClearResult message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("200")){
            mTextToSpeak.resetQueue();
            finish();
        }else{
            U.beepError(this,message.getMessage());
        }

    }

    @Override
    public void onError(int status, ResponseBody error) {
        progress.Dismiss();
        U.beepError(this,"Sever Error");
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
        Log.e(TAG,"repeatLocation");
//      mTextToSpeak.resetQueue();
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

                    tts1 = LOCATION;
                    tts3 = BARCODE;
                    tts4 = QTY;
                } else {
                    tts1 = LOCATIONJAP;
                    tts3 = BARCODE;
                    tts4 = QTYJAP;
                }

                String input = bardisple.getText().toString();     //input string
                String lastFourDigits = "";     //substring containing last 4 characters

                if (input.length() > 4) {
                    lastFourDigits = input.substring(input.length() - 4);
                }
                else {
                    lastFourDigits = input;
                }

                String code = lastFourDigits;
                String loca = location.getText().toString();
                String quantity = totalqty.getText().toString();

                String tts2 = loca;
                String tts6 = code;
                String tts5 = quantity;
                mTextToSpeak.startSpeaking(tts1, tts2);

                mTextToSpeak.playsilence();
                String codeChars[] = tts6.split("");
                mTextToSpeak.startSpeaking(tts3);
                for (String chars : codeChars) {
                    mTextToSpeak.startSpeaking(chars);
                }
                mTextToSpeak.playsilence();
                mTextToSpeak.startSpeaking(tts4, tts5);
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
        super.onDestroy();
    }

}
