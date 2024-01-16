
package com.itechvision.ecrobo.pickman.Chatman.PickWork.W6Batch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.FixBatchPicking.FixBatchPickingRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.FixBatchPicking.FixBatchPickingResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.GetBatchPicking.BatchPickingRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.GetBatchPicking.BatchPickingResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.GetBatchPicking.ProductListData;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

import static com.itechvision.ecrobo.pickman.Util.CommonDialogs.customToast;

public class BatchPickingW6Activity extends BaseActivity implements DataManager.GetBatchPickingcall, DataManager.FixBatchPickingcall {


    @BindView(R.id.add_layout) Button keyboard;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.scrollMain) ScrollView svMain;
    @BindView(R.id.skipList) Button skippedOnes;
    @BindView(R.id.barcode) EditText barcode;
    @BindView(R.id.boxtext) TextView boxtext;
    @BindView(R.id.location) EditText location;
    @BindView(R.id.product_code) EditText product_code;
    @BindView(R.id.quantity) EditText quantity;
    @BindView(R.id.standard1) EditText standard1;
    @BindView(R.id.standard2) EditText standard2;
    @BindView(R.id.productname) TextView productname;
    @BindView(R.id.productQuantity) EditText productQuantity;
    @BindView(R.id.shortBarcode) EditText shortBarcode;
    @BindView(R.id.order_no) EditText order_no;
    @BindView(R.id.orderer_name) EditText orderer_name;
    @BindView(R.id.box_no) EditText box_no;
    @BindView(R.id.notif_count_green) Button btnGreen;
    @BindView(R.id.notif_count_red) Button btnRed;
    @BindView(R.id.notif_count_blue) Button btnBlue;



    private boolean visible = false;
    public Context mcontext = this;

    public String TAG = BatchPickingW6Activity.class.getSimpleName();

    String date="",authid="",adminid="",batchid="",batchno="";

    protected int mProcNo = 0;

    public static final int PROC_BARCODE = 1;
    public static final int PROC_QTY = 2;
    public static final int PROC_BOXNO = 3;

    public final Handler handlerLoc = new Handler();
    public Runnable runnableCode = null;

    public String _lastUpdateQty = "0";
    public String BarcodeScaned = "0";
    private String rowno = "0";
    private TextToSpeak mTextToSpeak;

    public final String LOCATION = "location";
    public final String BARCODE = "barcode";
    public final String QTY = "quantity";
    public final String BOX = "box";


    public final String LOCATIONJAP = "Roke";
    public final String BARCODEJAP = "Bakodo";
    public final String QTYJAP = "Suryo";
    public final String BOXJAP = "Bokkusu";

    protected ArrayList<ProductListData> cProductList = new ArrayList<>();



    DataManager manager;
    progresBar progress;

    private DataManager.GetBatchPickingcall batchPickingcall;
    private DataManager.FixBatchPickingcall fixbatchPickingcall;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_picking_w6);

        ButterKnife.bind(this);
//        getIDs();

        Log.d(TAG,"On Create ");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        mTextToSpeak = new TextToSpeak(this);

        manager = new DataManager();
        progress = new progresBar(this);
        batchPickingcall =  this;
        fixbatchPickingcall = this;

        Intent intent = getIntent();

        date = intent.getStringExtra("date");
        batchid = intent.getStringExtra("Batch_id");
        batchno = intent.getStringExtra("Batch_no");
        adminid = intent.getStringExtra("admin_id");
        authid = intent.getStringExtra("auth_id");
//        order = intent.getStringExtra("order_id");
//        getby = intent.getStringExtra("getby");


        if (mProcNo == 0)
            nextProcess();
    }

/*    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "バッチ商品ピック", " ",
                0, true,true,true,true );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnYellow.setOnClickListener(this);
    }*/


    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
        if (visible == false)
        {
            visible = true;


            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layout.getLayoutParams();
            param.setMargins(0, top, 0, 0);
            Log.e("MoveActivity", "SetlayoutMarginnnnn");
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
            Log.e("MoveActivity", "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
    }

  @OnClick (R.id.img1ActionBar)
          void back(){
                Intent i = new Intent();
                setResult(Activity.RESULT_OK, i);
                finish();
    }

    public int convert(int x)
    {
        Resources r = mcontext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                x, r.getDisplayMetrics()
        );
        return px;
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {

            case PROC_BARCODE:
                Log.e(TAG, "setProc_BARCODEEEEEEEEE");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.box_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(R.id.barcode));
                break;

            case PROC_QTY:
                Log.e(TAG,"setPROC_QTYYYYYYYYYY");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.box_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.quantity));
                break;

            case PROC_BOXNO:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.box_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.box_no).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(R.id.box_no));
                break;
        }
    }

    public void inputedEvent(String buff,boolean scan){
        switch (mProcNo) {
            case PROC_BARCODE:
                String barcode = _gts(R.id.barcode);
                Log.e(TAG,"inputedEvent_PROC_BARCODEEEEEE");
                if ("".equals(barcode)) {
                    handlerLoc.removeCallbacks(runnableCode);
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "バーコードは必須です");
                    break;
                }

                if((cProductList.get(0).getBarcode().trim().equalsIgnoreCase(barcode)) || (cProductList.get(0).getCode().trim().equalsIgnoreCase(barcode))){
                    handlerLoc.removeCallbacks(runnableCode);
                    mTextToSpeak.resetQueue();

                    _sts(R.id.quantity,"1");

                    nextWork();
                }

                else
                {
                    handlerLoc.removeCallbacks(runnableCode);
                    mTextToSpeak.resetQueue();
                    U.beepError(this, "BarCode Doesn't Match");
                    break;
                }
                break;

            case PROC_QTY :

                String qty = _gts(R.id.quantity);

                if(scan){
                    if(U.compareNumeric(BarcodeScaned,cProductList.get(0).getQuantity())==0)
                    {
                        U.beepError(this,"Qunatity cannot exceed the required quantity");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                    if((cProductList.get(0).getBarcode().trim().equalsIgnoreCase(buff)) || (cProductList.get(0).getCode().trim().equalsIgnoreCase(buff))){
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned");
                        qty = U.plusTo(qty, "1");

                        BarcodeScaned =  U.plusTo(BarcodeScaned, "1");
                        _sts(R.id.quantity, qty);

                        if(Integer.parseInt(qty)>1)
                            mTextToSpeak.startSpeaking(qty);
                        _lastUpdateQty = _gts(R.id.quantity);

                        /* check if update in quantity need next action */
                        if (BarcodeScaned.equals(cProductList.get(0).getQuantity())) {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned222222");
                            setProc(PROC_BOXNO);

                        }
                    }
                    else {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned3333");
                        U.beepError(this,"Barcode dont match");
                        Toast.makeText(getApplicationContext(),"BarCode Doesn't Match",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if ("".equals(qty)) {
                        Log.e(TAG, "inputedEvent_QNTYY empty ");
                        U.beepError(this, "数量は必須です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                  else  if ("".equals(qty) || "0".equalsIgnoreCase(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }
                    int q = Integer.parseInt(qty);
                    int tq = Integer.parseInt(productQuantity.getText().toString());

                    if (q>tq) {
                        U.beepError(this, "数量が多すぎます");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        _sts(R.id.quantity, "1");

                        BarcodeScaned = "1";
                        break;
                    } else {
                        if (Integer.parseInt(qty) > 1)
                            mTextToSpeak.startSpeaking(_gts(R.id.quantity));

                        BarcodeScaned = _gts(R.id.quantity);

                        if (BarcodeScaned.equals(cProductList.get(0).getQuantity())) {
                            Log.e(TAG, "inputedEvent_QNTYY complete inspect");
                            setProc(PROC_BOXNO);


                        } else {
                            Log.e(TAG, "inputedEvent_QNTYY INcomplete inspect");
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                            builder1.setMessage("検品数が足りないですがよろしいですか？");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "はい",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            setProc(PROC_BOXNO);


                                            dialog.cancel();
                                        }
                                    });

                            builder1.setNegativeButton(
                                    "いいえ",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    }
                }

                break;
            case PROC_BOXNO:
                String boxno = _gts(R.id.box_no);
                String boxnotxt = boxtext.getText().toString();


                if (boxtext.getText().toString().length()==1){
                    if (boxno.equalsIgnoreCase(boxtext.getText().toString())){
                        fixRequest();
                    }else if(boxno.equalsIgnoreCase("000"+boxtext.getText().toString())){
                        fixRequest();
                        break;
                    }else{
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.box_no).setFocusableInTouchMode(true);
                    }
                }else if(boxtext.getText().toString().length()==2){
                    if (boxno.equalsIgnoreCase(boxtext.getText().toString())){
                        fixRequest();


                    }else if(boxno.equalsIgnoreCase("00"+boxtext.getText().toString())){
                        fixRequest();
                    }else{
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.box_no).setFocusableInTouchMode(true);
                    }
                }else if(boxtext.getText().toString().length()==3){
                    if (boxno.equalsIgnoreCase(boxtext.getText().toString())){
                        fixRequest();
                    }else if(boxno.equalsIgnoreCase("0"+boxtext.getText().toString())){
                        fixRequest();
                    }else{
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.box_no).setFocusableInTouchMode(true);
                    }
                }else{
                    if (boxno.equalsIgnoreCase(boxtext.getText().toString())){
                        fixRequest();
                    }else{
                        U.beepNGbox(this, "箱番号が違います。");
                        _gt(R.id.box_no).setFocusableInTouchMode(true);
                    }
                }
                break;
        }
    }

    void fixRequest(){
        stopTimer();
        handlerLoc.removeCallbacks(runnableCode);
        mTextToSpeak.resetQueue();


        if(CommonUtilities.getConnectivityStatus(this)) {
            progress.Show();
            FixBatchPickingRequest request = new FixBatchPickingRequest(adminid, authid, BaseActivity.getShopId(),getResources().getString(R.string.version),batchid,cProductList.get(0).getBarcode(),_gts(R.id.quantity),cProductList.get(0).getPsh_id(), rowno);
            manager.FixBatchPicking(request, fixbatchPickingcall);
        } else {
            CommonUtilities.openInternetDialog(this);
        }
    }

    public void nextWork() {

        BarcodeScaned = "1";
        setProc(PROC_QTY);

        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
        _lastUpdateQty = _gts(R.id.quantity);


        if (BarcodeScaned.equals(cProductList.get(0).getQuantity())) {
            setProc(PROC_BOXNO);

        }
    }

    public void repeatBarcode(int delay) {
        Log.e(TAG,"repeatBarcodeeeee");
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
                String tts8 = "";
                
                if(lang == 1){
                    tts1 = LOCATION;
                    tts3 = BARCODE;
                    tts4 = QTY;
                    tts8 = BOX;
                }
                else {

                    tts1 = LOCATIONJAP;
                    tts3 = BARCODEJAP;
                    tts4 = QTYJAP;
                    tts8 = BOXJAP;
                }
                String code = _gts(R.id.shortBarcode);
                String location = cProductList.get(0).getLocation();
                String quantity = cProductList.get(0).getQuantity();
                String box= cProductList.get(0).getFrontage_number();


                String tts2 = location;
                String tts6 = code;
                String tts5 = quantity;
                String tts9 = box;
                
                String locChars[] = tts2.split("");
                mTextToSpeak.startSpeaking(tts1);
                for (String chars : locChars) {
                    mTextToSpeak.startSpeaking(chars);
                }

                mTextToSpeak.playsilence();
                String codeChars[] = tts6.split("");
                mTextToSpeak.startSpeaking(tts3);
                for (String chars : codeChars) {
                    mTextToSpeak.startSpeaking(chars);
                }
                mTextToSpeak.playsilence();
                mTextToSpeak.startSpeaking(tts4, tts5);

                mTextToSpeak.playsilence();
                mTextToSpeak.startSpeaking(tts8, tts9);

                mTextToSpeak.playsilence();
                handlerLoc.postDelayed(runnableCode, INTERVAL);
            }
        };
        handlerLoc.post(runnableCode);
    }

    @Override
    public void inputedEvent(String buff) {
       inputedEvent(buff,false);
    }

    @Override
    public void clearEvent() {
        customToast(this,"Action not Valid for this menu");

    }

    @Override
    public void allclearEvent() {
        customToast(this,"Action not Valid for this menu");

    }

    @Override
    public void skipEvent() {

        SweetAlertDialog pDialog1 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog1.setCancelable(true);
        pDialog1.setContentText("Do you want to skip this product?");
        pDialog1.setConfirmText("Yes");

        pDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                pDialog1.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                            handlerLoc.removeCallbacks(runnableCode);
                            mTextToSpeak.resetQueue();
                            skipCall();
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

    void skipCall(){
        if(CommonUtilities.getConnectivityStatus(this)) {
            progress.Show();
            FixBatchPickingRequest request = new FixBatchPickingRequest(adminid, authid, BaseActivity.getShopId(),getResources().getString(R.string.version),batchid,cProductList.get(0).getBarcode(),"0",cProductList.get(0).getPsh_id(), rowno);
            manager.FixBatchPicking(request, fixbatchPickingcall);
        } else {
            CommonUtilities.openInternetDialog(this);
        }
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY:
                _sts(R.id.quantity, buff);
                break;
            case PROC_BOXNO:
                _sts(R.id.box_no, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (CLEAR_BARCODE.equals(barcode)) {
            mBuff.delete(0, mBuff.length());
            clearEvent();
        } else if (ENTER_BARCODE.equals(barcode)) {
            enterEvent();
        } else {

                if (!barcode.equals("")) {
                    if (mProcNo == PROC_BARCODE) {
                        String finalbarcode1 = CommonFunctions.getBracode(barcode);
                        barcode =finalbarcode1;

                        _sts(R.id.barcode, barcode);

                    } else if (mProcNo == PROC_QTY) {
                        String finalbarcode1 = CommonFunctions.getBracode(barcode);
                        barcode =finalbarcode1;
                    }

                    if (mProcNo == PROC_BOXNO){
                        _sts(R.id.box_no,"");
                        _sts(R.id.box_no, barcode);
                    }
                }

                this.inputedEvent(barcode,true);
                mBuff.delete(0, mBuff.length());
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
            case PROC_QTY:
                if ("".equals(barcode)) {
                } else
                    _sts(R.id.quantity, barcode);
                break;
            case PROC_BOXNO:
                _sts(R.id.box_no, barcode);
                break;
        }
    }

    @Override
    public void nextProcess() {
        _sts(R.id.barcode,"");
        _sts(R.id.quantity,"");
        _sts(R.id.box_no,"");
        _sts(R.id.location,"");
        _sts(R.id.product_code,"");
        _sts(R.id.standard1,"");
        _sts(R.id.standard2,"");
        productname.setText("");
        _sts(R.id.productQuantity,"");
        _sts(R.id.shortBarcode,"");

        setProc(PROC_BARCODE);

       /* cancelcount = 0;
        isstop= false;*/

        if(CommonUtilities.getConnectivityStatus(this)) {
            progress.Show();
            BatchPickingRequest request = new BatchPickingRequest(adminid, authid, BaseActivity.getShopId(),getResources().getString(R.string.version),batchid);
            manager.GetBatchPicking(request, batchPickingcall);
        } else {
            CommonUtilities.openInternetDialog(this);
        }
    }



    @Override
    public void onSucess(int status, BatchPickingResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("0")) {

            rowno = message.getRow();

            cProductList = message.getOrder_data();
            btnBlue.setText(message.getBatch_orders_count());
            btnRed.setText(message.getBatch_orders_remaining_count());
            btnGreen.setText(message.getPending_row_counts());
//            btnBlue.setText(message.getTotal_product());
            if(!cProductList.isEmpty() && cProductList.size()>0){
            location.setText(cProductList.get(0).getLocation());
            product_code.setText(cProductList.get(0).getCode());
                standard1.setText(cProductList.get(0).getSpec1());
                standard2.setText(cProductList.get(0).getSpec2());
                productQuantity.setText(cProductList.get(0).getQuantity());
                productname.setText(cProductList.get(0).getName());
                order_no.setText(cProductList.get(0).getOrder_no());
                orderer_name.setText(cProductList.get(0).getOrderer());
                boxtext.setText(cProductList.get(0).getFrontage_number());
                String barc = cProductList.get(0).getBarcode();

                if (barc.length() >= 4)
                    _sts(R.id.shortBarcode, barc.substring(barc.length() - 4));
                else
                    _sts(R.id.shortBarcode, barc);

                setProc(PROC_BARCODE);
                repeatBarcode(1000);
        }
            else{
                U.beepError(this,"Order already inspected");
                Intent i = new Intent();
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        }
        else{
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, FixBatchPickingResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("0")) {
            rowno = message.getRow();
//            boxtext.setText(message.getBox_number());
            cProductList = new ArrayList<>();
            cProductList = message.getOrder_data();

            String notInspected = message.getPending_row_counts();

            String totalorders = message.getBatch_orders_count();
            String remainingOrders = message.getBatch_orders_remaining_count();

            btnBlue.setText(totalorders);
            btnRed.setText(message.getBatch_orders_remaining_count());
            btnGreen.setText(notInspected);


                if (Integer.parseInt(notInspected) == 0 && Integer.parseInt(remainingOrders) != 0) {
                    U.beepKakutei(this, null);
                    Intent i = new Intent();
                    i.putExtra("batch_status", "1");
                    setResult(Activity.RESULT_OK, i);
                    finish();

                } else if (Integer.parseInt(notInspected) == 0 && Integer.parseInt(remainingOrders) == 0) {
                    U.beepFinish(this, null);
                    Intent i = new Intent();
                    i.putExtra("batch_status", "2");
                    setResult(Activity.RESULT_OK, i);
                    finish();

                } else if (Integer.parseInt(notInspected) > 0 && cProductList.size()==0 ) {
                    U.beepKakutei(this, null);
                    Intent i = new Intent();
                    i.putExtra("batch_status", "1");
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }


            else if ( cProductList.size() > 0 && Integer.parseInt(notInspected)!=0) {
//                rowno.
                location.setText(cProductList.get(0).getLocation());
                product_code.setText(cProductList.get(0).getCode());
                standard1.setText(cProductList.get(0).getSpec1());
                standard2.setText(cProductList.get(0).getSpec2());
                productQuantity.setText(cProductList.get(0).getQuantity());
                productname.setText(cProductList.get(0).getName());
                order_no.setText(cProductList.get(0).getOrder_no());
                orderer_name.setText(cProductList.get(0).getOrderer());
                boxtext.setText(cProductList.get(0).getFrontage_number());

                box_no.setText("");
                barcode.setText("");
                quantity.setText("");

                String barc = cProductList.get(0).getBarcode();

                if (barc.length() >= 4)
                    _sts(R.id.shortBarcode, barc.substring(barc.length() - 4));
                else
                    _sts(R.id.shortBarcode, barc);

                setProc(PROC_BARCODE);
                repeatBarcode(1000);
            }
            else {
                U.beepError(this,"Error in API response");
                }
        }
        else{
            U.beepError(this, message.getMessage());
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

    @Override
    protected void onPause() {
        mTextToSpeak.resetQueue();
        mTextToSpeak.onStopSpeaking();

        super.onPause();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mTextToSpeak.resetQueue();
        mTextToSpeak.onStopSpeaking();

        super.onDestroy();
    }
}
