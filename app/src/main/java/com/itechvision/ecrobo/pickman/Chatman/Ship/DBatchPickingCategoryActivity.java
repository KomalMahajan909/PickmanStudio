package com.itechvision.ecrobo.pickman.Chatman.Ship;

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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.BatchTrayDetail;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetIncludeDbatchList;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetIncludeTray;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.PutTrayQnty;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DBatchPickingCategoryActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.lv)ListView lv;


    int[] statusarray;

    private boolean showKeyboard;
    private TextToSpeak mTextToSpeak;


    protected int mProcNo = 0;
    public static final int PROC_ORDERNO =1;
    public static final int PROC_QTY= 3;
    public static final int PROC_TRAYNO= 2;

    public static int mRequestStatus =0;

    public static final int REQ_INITIAL = 1;
    public static final int REQ_ORDERNO = 2;
    public static final int REQ_TRAY = 3;
    public static final int REQ_QTY = 4;
    public static final int REQ_BATCHDETAIL = 6;
    public static final int REQ_RESET = 7;

    protected Map<String, String> cProductList = null;
    protected List<Map<String, String>> statusList = null;
    protected List<Map<String, String>> inspectionList = null;

    ListViewAdapter adapter;

    public String _lastUpdateQty = "0";

    public Context mcontext=this;
    SharedPreferences spDomain;

    public static final String DOMAINPREFERENCE = "domain";

    public boolean mNextBarcode = false;
    public String isNextBarcode ="";

    ECRApplication app=new ECRApplication();
    String adminID="";
    private boolean visible = false;

    String batchid ="",batchno="",createdate ="",badge = "";

    String TAG= DBatchPickingCategoryActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbatch_picking_category);
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
           badge = i.getStringExtra("badge");

        }
        updateBadge1(badge);
        _sts(R.id.batchid,batchno);

        if(mProcNo ==0)
            nextProcess();
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

    @OnClick(R.id.btnBack)void back(){
        Intent i = new Intent(this, DBatchPickingActivity.class);
        i.putExtra("batch_no",batchno );
        i.putExtra("batch_id",batchid );
        i.putExtra("create_date", createdate);
        startActivity(i);
    }

    @OnClick(R.id.btnfinish)void Finish(){

        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("batch_id", batchid));
        Globals.getterList.add(new ParamsGetter("order_id", _gts(R.id.orderNo)));
        Globals.getterList.add(new ParamsGetter("mode","finish_order"));

        mRequestStatus = REQ_QTY;

        new MainAsyncTask(this, Globals.Webservice.time_DBatch_Picking, 1, DBatchPickingCategoryActivity.this, "Form", Globals.getterList,true).execute();


    }

    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "チラシピック", " ",
                0, true,false,false,false);

        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
//        btnGreen.setOnClickListener(this);
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
//            case R.id.notif_count_green:
//                Log.e(TAG,">>>>>>>>>>>>>>11111111111111111111111"+getBadge1());
//                if(getBadge3()!= 0){
//                    showInfo();
//                }
//                break;
            default:
                break;
        }
    }

    @OnClick(R.id.enter)void enter(){
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {
            case PROC_ORDERNO:
                _gt(R.id.orderNo).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.orderNo).setFocusableInTouchMode(true);
                _gt(R.id.trayno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
            case PROC_TRAYNO:
                _gt(R.id.orderNo).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.trayno).setFocusableInTouchMode(true);
                _gt(R.id.trayno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
            case PROC_QTY:
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);
                _gt(R.id.orderNo).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.trayno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                if (_gts(R.id.quantity).equals("")) _sts(R.id.quantity, "1");
                break;

        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {
            case PROC_ORDERNO:    // バーコード
                String order = _gts(R.id.orderNo);

                if ("".equals(order)) {
                    U.beepError(this, "バーコードが必要です");
                    _gt(R.id.orderNo).setFocusableInTouchMode(true);

                    break;
                }

                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("batch_id", batchid));
                Globals.getterList.add(new ParamsGetter("order_id", order));

                mRequestStatus = REQ_ORDERNO;

                new MainAsyncTask(this, Globals.Webservice.time_DBatch_Picking, 1, DBatchPickingCategoryActivity.this, "Form", Globals.getterList,true).execute();
                break;
            case PROC_QTY: // 数量
                String qty = _gts(R.id.quantity);
                if(qty.equals(""))
                    qty="1";
                String tray = _gts(R.id.trayno);

                Log.e(TAG,"Qtyyyyy  "+qty);
                Log.e(TAG, "buff " + buff);

                if (isScaned) {

                    if(U.compareNumeric(cProductList.get("processedCnt"),cProductList.get("quantity"))==0)
                    {
                        U.beepError(this,"Qunatity cannot exceed the required quantity");
                        break;
                    }
                    if(buff.equals(tray)){

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
                                fixedRequest();
                        }
                    } else {

                        //U.beepError(this, "You scan wrong barcode");
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned3333");
                       fixedRequest(buff);
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

                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt222222222222223333333");
                            fixedRequest();

                        } else {
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt333333333322222222");
                            fixedRequest();
                        }
                    }
                }

                break;

            case PROC_TRAYNO:
                String tray1 = _gts(R.id.trayno);

                if ("".equals(tray1)) {
                    U.beepError(this, "箱数は必須です");
                    _gt(R.id.trayno).setFocusableInTouchMode(true);

                    break;
                }

                stopTimer();

                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("order_id", _gts(R.id.orderNo)));
                Globals.getterList.add(new ParamsGetter("tray_no", tray1));
                Globals.getterList.add(new ParamsGetter("batch_id", batchid));


                mRequestStatus = REQ_TRAY;

                new MainAsyncTask(this, Globals.Webservice.time_DBatch_Picking, 1, DBatchPickingCategoryActivity.this, "Form", Globals.getterList,true).execute();

                break;
        }
    }
    public void currLineData(Map<String, String> data){
        Log.e(TAG,"currLineDataaaaaaaaaaaa");
        cProductList = data;

        nextWork();
    }
    public void nextWork() {

        Log.e(TAG, "nextworkkkkkk");
        String processedCnt = cProductList.get("processedCnt");
        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
        setProc(PROC_QTY);
        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
        _lastUpdateQty = _gts(R.id.quantity);


        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {

            fixedRequest();
        }
    }
    void Removedata()
    {
        String trayno = _gts(R.id.trayno);
        String qty =  _gts(R.id.quantity);

        for(Map<String,String> map : statusList){
            String _t = map.get("tray");
            String _q = map.get("tray_quantity");

            if(trayno.equals(_t)){
                Log.e(TAG, "DataAssigned " +trayno +"    "+_t);

                            String qnty = U.minusTo(_q,qty);
                            Log.e(TAG, "Quantitiesssss  " +_q +"    "+qty  +"<<<<<<<<<<<"+ qty);
                            if(qnty.equals("0")) {
                                statusList.remove(map);
                            }
                            else{
                            map.put("quantity", qnty);
                            Log.e(TAG, "map  quantity " + map);}
                            break;
                        }

                    }
                    Log.e(TAG, " removePackItemmmm " +statusList);
                     initWorkList(lv);

                }


    void fixedRequest() {
        Log.e(TAG, "FixedRequestttttt");
//        Removedata();
        sendRequest();
    }

    private void fixedRequest( String nextBarcode) {
        Log.e(TAG, "FixedRequestttttt222222222");
//        Removedata();
        mNextBarcode = true;
        isNextBarcode =nextBarcode;
        sendRequest();

    }

    public void sendRequest()
    {

        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("order_id", _gts(R.id.orderNo)));
        Globals.getterList.add(new ParamsGetter("tray_no", _gts(R.id.trayno)));
        Globals.getterList.add(new ParamsGetter("batch_id", batchid));
        Globals.getterList.add(new ParamsGetter("track_quantity", _gts(R.id.quantity)));

        mRequestStatus =REQ_QTY;

        new MainAsyncTask(this, Globals.Webservice.time_DBatch_Picking, 1, DBatchPickingCategoryActivity.this, "Form", Globals.getterList,true).execute();
    }
    @Override
    public void nextProcess() {
        Log.e(TAG, "nexttttPRocessssssssssssss");
        _sts(R.id.orderNo, "");
        _sts(R.id.quantity, "");
        _sts(R.id.trayno, "");

        lv.setAdapter(null);
        setProc(PROC_ORDERNO);

        _gt(R.id.orderNo).requestFocus();


        if (showKeyboard == false) {
            visible = false;
            numbrbtn.setText(R.string.showkeyboard);
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);

            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);

            hiddenPanel.setVisibility(View.GONE);
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
    }

    public void updateBadge1(String boxCount) {
        Log.e(TAG, "updateBadge1111111111  " + boxCount);
        setBadge1(Integer.valueOf(boxCount));
    }

    protected boolean showInfo(){
        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("batch_id", batchid));
        Globals.getterList.add(new ParamsGetter("mode", "list_trays"));

        mRequestStatus= REQ_BATCHDETAIL;

        new MainAsyncTask(this, Globals.Webservice.time_DBatch_Picking, 1, DBatchPickingCategoryActivity.this, "Form", Globals.getterList, true).execute();

        return true;
    }
    public void getProductList( List<Map<String, String>> list){
        statusList = list;
        initWorkList(lv);
    }

    public void getBatchList( List<Map<String, String>> list){
        inspectionList = list;

    }
    public boolean initiatePopup(){
        Log.e(TAG,"initiatePopuppppppppp");
        final PopupWindow popupWindow = new PopupWindow(this);
        if (inspectionList == null) {
            return false;
        }

        if (getPopupWindow() == null) {
            Log.e(TAG,"initiatePopuppppppppp_getPopupWindow");

            // レイアウト設定
            View popupView = getLayoutInflater().inflate(R.layout.batch_inspection_list, null);
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
            setPopupWindow(popupWindow);
            ImageView close =(ImageView)getPopupWindow().getContentView().findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }
        ListView lv1 = (ListView) getPopupWindow().getContentView().findViewById(R.id.workPicking);
        initWorkListPopop(lv1);

        // 画面中央に表示
        getPopupWindow().showAtLocation(findViewById(R.id.batchid), Gravity.CENTER, 0, 32);
        return true;
    }

    protected ListViewItems initWorkListPopop(ListView lv1) {
        Log.e(TAG," initWorkList111111111111");
        lv1.setAdapter(null);
        ListViewItems data = new ListViewItems();
        for (int i = inspectionList.size() - 1; i >= 0; i--) {
            Map<String, String> row = inspectionList.get(i);
            Log.e(TAG," initWorkList_222222222222"+row);

            data.add(data.newItem().add(R.id.pdt_0, row.get("tray"))
                    .add(R.id.pdt_1, row.get("quantity"))

            );
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.include_batch_product_list_product);
        lv1.setAdapter(adapter);
        // 単一選択モードにする
        lv1.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv1.setItemChecked(0, true);
        return data;
    }



    protected void initWorkList(ListView lv) {
        Log.e("NewShippingActivity", "initWorkList");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();


        Log.e(TAG,">>>>>>>>>>>>>>>mBatchList     "+ statusList.size() + "     "+statusList);
        for (int i =0 ; i <statusList.size() ; i++) {
            Map<String, String> row = statusList.get(i);

            data.add(data.newItem().add(R.id.pdt_0, row.get("tray"))
                    .add(R.id.pdt_1, row.get("tray_quantity"))

            );
            Log.e(TAG,">>>>>>>>>>>>>>>mBatchList     "+ statusList.size() + "     "+statusList);



        }

        adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.include_batch_product_list_product);
        lv.setAdapter(adapter);

        Log.e(TAG,"   "+data.getData().size());

    }


    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
//        _sts(id.orderNo,"");
        nextProcess();
    }

    @Override
    public void allclearEvent() {
        new AlertDialog.Builder(this)
                .setTitle("Clear？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTextToSpeak.resetQueue();
                        mTextToSpeak.startSpeaking("clear");
                        callAllClear();

                    }
                })
                .setNegativeButton("いいえ", null)
                .show();


    }
    void callAllClear()
    {
        Globals.getterList = new ArrayList<>();

        Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("clear_admin", "true"));
        Globals.getterList.add(new ParamsGetter("batch_id", batchid));

        mRequestStatus = REQ_RESET;

        new MainAsyncTask(this, Globals.Webservice.batch_boxno, 1, DBatchPickingCategoryActivity.this, "Form", Globals.getterList, true).execute();
    }

    @Override
    public void skipEvent() {

    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_ORDERNO:    // バーコード
                _sts(R.id.orderNo, buff);
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

            if (mProcNo == PROC_ORDERNO)
                _sts(R.id.orderNo, barcode);

            if (mProcNo == PROC_TRAYNO)
                _sts(R.id.trayno, barcode);

            this.inputedEvent(barcode, true);
        }
    }

    public void getback(){
        Intent i = new Intent(this, DBatchPickingActivity.class);
        i.putExtra("batch_no",batchno );
        i.putExtra("batch_id",batchid );
        i.putExtra("create_date", createdate);
        startActivity(i);
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_ORDERNO:
                _sts(R.id.orderNo, barcode);
                break;
            case PROC_QTY:
                _sts(R.id.quantity, barcode);
                break;
            case PROC_TRAYNO:
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
                if(mRequestStatus == REQ_ORDERNO)
                {
                    new GetIncludeDbatchList().post(code,msg,result1, mHash,DBatchPickingCategoryActivity.this);
                }
               else if(mRequestStatus == REQ_TRAY)
                {
                    new GetIncludeTray().post(code,msg,result1, mHash,DBatchPickingCategoryActivity.this);
                }
               else if(mRequestStatus == REQ_QTY)
                {
                    new PutTrayQnty().post(code,msg,result1, mHash,DBatchPickingCategoryActivity.this);
                }
                else if(mRequestStatus == REQ_BATCHDETAIL)
                {
                    new BatchTrayDetail().post(code,msg,result1, mHash,DBatchPickingCategoryActivity.this);
                }
                else if(mRequestStatus == REQ_RESET)
                {
                    nextProcess();
                    _sts(R.id.batchid,"");
                    startActivity( new Intent(this, DBatchActivity.class));
                    finish();
                }

                if(mRequestStatus ==REQ_INITIAL)
                {
                    new GetIncludeDbatchList().post(code,msg,result1, mHash,DBatchPickingCategoryActivity.this);
                }
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(DBatchPickingCategoryActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else {
                if (mRequestStatus == REQ_ORDERNO) {
                    new GetIncludeDbatchList().valid(code,msg,result1, mHash,DBatchPickingCategoryActivity.this);
                }
               else if (mRequestStatus == REQ_TRAY) {
                    new GetIncludeTray().valid(code, msg, result1, mHash, DBatchPickingCategoryActivity.this);

                }
               else if (mRequestStatus == REQ_QTY) {
                    new PutTrayQnty().valid(code, msg, result1, mHash, DBatchPickingCategoryActivity.this);

                } else if (mRequestStatus == REQ_BATCHDETAIL)
                {
                    new BatchTrayDetail().valid(code,msg,result1, mHash,DBatchPickingCategoryActivity.this);
                 }
                 else if (mRequestStatus == REQ_INITIAL)
                 {
                    new GetIncludeDbatchList().post(code,msg,result1, mHash,DBatchPickingCategoryActivity.this);
                }
                else if(mRequestStatus == REQ_RESET)
                    U.beepError(this, msg);
            }
        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {
     U.beepError(this,"Some error has occured");
    }
}

