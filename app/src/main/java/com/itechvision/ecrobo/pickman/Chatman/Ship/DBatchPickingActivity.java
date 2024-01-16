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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.BatchBarcodeCheck;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.BatchBoxdetail;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.BatchOrderdetail;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
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

public class DBatchPickingActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;

    int[] statusarray;

    private boolean showKeyboard;
    private TextToSpeak mTextToSpeak;

    public TextView qntybtn,boxbtn;

    protected int mProcNo = 0;
    public static final int PROC_BARCODE =1;
    public static final int PROC_QTY= 2;
    public static final int PROC_BOXNO= 3;

    public static int mRequestStatus =0;

    public static final int REQ_INITIAL = 1;
    public static final int REQ_BARCODE = 2;
    public static final int REQ_BOXNO = 3;
    public static final int REQ_BATCHDETAIL = 4;
    public static final int REQ_REMOVEBOX = 5;
    public static final int REQ_RESET = 6;

    protected Map<String, String> cProductList = null;
    protected List<Map<String, String>> statusList = null;

    public String _lastUpdateQty = "0";

    public Context mcontext=this;
    SharedPreferences spDomain;

    String include_left = "0";
    public static final String DOMAINPREFERENCE = "domain";

    ECRApplication app=new ECRApplication();
    String adminID="";
    private boolean visible = false;

    String batchid ="",batchno="",createdate ="";

    String TAG= DBatchPickingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbatch_picking);
        ButterKnife.bind(this);
        getIDs();

        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        showKeyboard=BaseActivity.getaddKeyboard();

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);

        boxbtn = (TextView)findViewById(id.boxbtn);
        qntybtn = (TextView)findViewById(R.id.qtybtn);

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

        }

        Globals.getterList = new ArrayList<>();

        adminID = spDomain.getString("admin_id", null);

        Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("batch_id", batchid));
        Globals.getterList.add(new ParamsGetter("initial_call", "true"));

        mRequestStatus = REQ_INITIAL;
        new MainAsyncTask(this, Globals.Webservice.batch_boxno, 1, DBatchPickingActivity.this, "Form", Globals.getterList, true).execute();

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

    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "Dバッチピッキング", " ",
                0, true,false,true ,true);

        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG,">>>>>>>>>>>>>>"+getBadge3());
        switch (v.getId()) {
            case R.id.relLayout1:
                menu.showMenu();
                break;
            case id.notif_count_blue:
                Log.e(TAG,">>>>>>>>>>>>>>11111111111111111111111"+getBadge1());
                if(getBadge1()!= 0){
                    showInfo();
                }
                break;
            case id.notif_count_green:
                Log.e(TAG,">>>>>>>>>>>>>>111111113333333333333333"+getBadge3());
                if(getBadge3()!= 0){
                    Intent i = new Intent(this, DBatchPickingCategoryActivity.class);
                    i.putExtra("batch_no",batchno );
                    i.putExtra("batch_id",batchid );
                    i.putExtra("create_date", createdate);
                    i.putExtra("badge",include_left);
                    startActivity(i);
//                   startActivity(new Intent(DBatchPickingActivity.this,DBatchPickingCategoryActivity.class));
                }
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.enter)void enter(){
        String s = mBuff.toString();
        Log.e("ScannerbindActivity", "Keyput11222 "+mBuff );
        mBuff.delete(0, mBuff.length());
        Log.e("ScannerbindActivity", "Keyput11222 buff "+mBuff );
        inputedEvent(s);
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {
            case PROC_BARCODE:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.box_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
            case PROC_QTY:
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);
                _gt(R.id.box_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                if (_gts(id.quantity).equals("")) _sts(id.quantity, "1");
                break;
            case PROC_BOXNO:
                _gt(R.id.box_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.box_no).setFocusableInTouchMode(true);
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                String barcode1 = _gts(R.id.barcode);

                if ("".equals(barcode1)) {
                    U.beepError(this, "バーコードが必要です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);

                    break;
                }

                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("batch_id", batchid));
                Globals.getterList.add(new ParamsGetter("barcode", barcode1));

                mRequestStatus = REQ_BARCODE;

                new MainAsyncTask(this, Globals.Webservice.batch_boxno, 1, DBatchPickingActivity.this, "Form", Globals.getterList,true).execute();
                break;
            case PROC_QTY: // 数量
                String qty = _gts(R.id.quantity);
                if(qty.equals(""))
                    qty="1";
                String barcode = _gts(R.id.barcode);

                Log.e(TAG,"Qtyyyyy  "+qty);
                Log.e(TAG, "buff " + buff);

                if (isScaned) {

                    if(U.compareNumeric(cProductList.get("processedCnt"),cProductList.get("quantity"))==0)
                    {
                        U.beepError(this,"Qunatity cannot exceed the required quantity");
                        break;
                    }
                        if(buff.equals(barcode)){

                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned");
                            qty = U.plusTo(qty, "1");
                            String processedCnt = cProductList.get("processedCnt");
                            cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                            _sts(id.quantity, qty);
                            if(Integer.parseInt(qty)>1)
                                mTextToSpeak.startSpeaking(qty);
                            _lastUpdateQty = _gts(id.quantity);

                        /* check if update in quantity need next action */
                            if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                                Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned222222");
                                qntybtn.setBackground(getResources().getDrawable(R.drawable.green_rounded_corner_btn));
                                setProc(PROC_BOXNO);
//                                fixedRequest(COMPLETE_INSPECT);
                            }
                        } else {

                            //U.beepError(this, "You scan wrong barcode");
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_Scanned3333");
                            U.beepError(this,"Barcode dont match");

                            Toast.makeText(getApplicationContext(),"BarCode Doesn't Match", Toast.LENGTH_SHORT).show();
                        }
                    }

                else {
                    Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_Scanned");
                    if ("".equals(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
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
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_qtyUpdate");
                        U.beepError(this, "数量が多すぎます");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else {
                        Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt");
                        cProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        mTextToSpeak.startSpeaking(_gts(id.quantity));
                        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
                            qntybtn.setBackground(getResources().getDrawable(R.drawable.green_rounded_corner_btn));
                            setProc(PROC_BOXNO);
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt222222222222223333333");
//                            fixedRequest(COMPLETE_INSPECT);

                        } else {
                            setProc(PROC_BOXNO);
                            Log.e(TAG, "inputedEvent_PROC_QTY_IF_barcode_NOT_SCANNED_processedCnt333333333322222222");
//                                fixedRequest(INCOMPLETE_INSPECT);
                            }
                        }
                    }

                break;

            case PROC_BOXNO:
                String box = _gts(R.id.box_no);
                String boxtxt = boxbtn.getText().toString();
        ;
                if ("".equals(box)) {
                    U.beepError(this, "箱数は必須です");
                    _gt(R.id.box_no).setFocusableInTouchMode(true);

                    break;
                }

                if(!box.equals(boxtxt)){
                    U.beepError(this, "ボックス番号が正しくありません");
                    _gt(R.id.box_no).setFocusableInTouchMode(true);

                    break;
                }

                stopTimer();

                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode", _gts(R.id.barcode)));
                Globals.getterList.add(new ParamsGetter("qty", _gts(R.id.quantity)));
                Globals.getterList.add(new ParamsGetter("box_no", _gts(id.box_no)));

                Globals.getterList.add(new ParamsGetter("batch_id", batchid));


                mRequestStatus = REQ_BOXNO;

                new MainAsyncTask(this, Globals.Webservice.batch_boxno, 1, DBatchPickingActivity.this, "Form", Globals.getterList,true).execute();

                break;
        }
    }
    public void currLineData(Map<String, String> data){
        Log.e(TAG,"currLineDataaaaaaaaaaaa");
        cProductList = data;
    }
    public void nextWork() {
        Log.e(TAG, "nextworkkkkkk");
        String processedCnt = cProductList.get("processedCnt");
        cProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
        setProc(PROC_QTY);
        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
        _lastUpdateQty = _gts(R.id.quantity);
        Log.e(TAG, "nextworkkkkkk processedCnt" +cProductList.get("processedCnt"));
        Log.e(TAG, "nextworkkkkkk quantity" +cProductList.get("quantity"));

        if (cProductList.get("processedCnt").equals(cProductList.get("quantity"))) {
           qntybtn.setBackground(getResources().getDrawable(R.drawable.green_rounded_corner_btn));
            setProc(PROC_BOXNO);
//            inspectionNo = COMPLETE_INSPECT;
        }
    }

    @Override
    public void nextProcess() {
        Log.e(TAG, "nexttttPRocessssssssssssss");
        _sts(R.id.barcode, "");
        _sts(R.id.quantity, "");
        _sts(R.id.box_no, "");
        boxbtn.setText("0");
        qntybtn.setText("0");

        setProc(PROC_BARCODE);

        _gt(R.id.barcode).requestFocus();
        qntybtn.setBackground(getResources().getDrawable(R.drawable.red_rounded_corner_button));

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

    public void updateBadge1(String orderCount) {
        Log.e(TAG, "updateBadge1111111111  " + orderCount);
        setBadge1(Integer.valueOf(orderCount));
    }
    public void updateBadge4(String boxCount) {
        Log.e(TAG, "updateBadge1111111111  " + boxCount);
        setBadge4(Integer.valueOf(boxCount));
    }
    public void updateBadge3(String boxCount) {
        Log.e(TAG, "updateBadge1111111111  " + boxCount);
        setBadge3(Integer.valueOf(boxCount));
    }

    protected boolean showInfo(){
        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("box_detail", "true"));
        Globals.getterList.add(new ParamsGetter("batch_id", batchid));

        mRequestStatus= REQ_BATCHDETAIL;

        new MainAsyncTask(this, Globals.Webservice.batch_boxno, 1, DBatchPickingActivity.this, "Form", Globals.getterList, true).execute();

        return true;
    }
    public void getProductList( List<Map<String, String>> list){
        statusList = list;
        showlist();
    }
    protected boolean showlist() {
        Log.e(TAG, " showInfooooo");

        if (statusList == null) {
            return true;
        }
        Log.e(TAG,"initiatePopuppppppppp");
        final PopupWindow popupWindow = new PopupWindow(this);

        Log.e(TAG,"initiatePopuppppppppp_getPopupWindow");

        // レイアウト設定
        View popupView = getLayoutInflater().inflate(R.layout.batch_status_list_dialog, null);
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

        ImageView close =(ImageView)getPopupWindow().getContentView().findViewById(R.id.close_btn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        GridView lv = (GridView) getPopupWindow().getContentView().findViewById(R.id.gridList);
        initWorkList(lv);

        // 画面中央に表示
        getPopupWindow().showAtLocation(findViewById(R.id.barcode), Gravity.CENTER, 0, 32);
        Log.e(TAG," initWorkList3333333333" );
        return false;

    }


    protected ListViewItems initWorkList(final GridView lv) {
        Log.e(TAG," initWorkList" +statusList);
        lv.setAdapter(null);
        Log.e(TAG," initWorkList1111111" );
        statusarray = new int[statusList.size()];

        Log.e(TAG," initWorkList2222222222" +statusList.size());
        ListViewItems data = new ListViewItems();
        for (int i = 0; i <=statusList.size() - 1; i++) {
            Map<String, String> row =statusList.get(i);
            Log.e(TAG," initWorkList_>>>>>>>>>>>");

            String status =row.get("box_status");

            if(status.equals("0"))
            {
                statusarray[i]=R.drawable.peach_pending_btn;
            }
            else if(status.equals("1"))
            {
                statusarray[i]=R.drawable.red_rounded_corner_button;
            }
            else if(status.equals("2"))
            {
                statusarray[i]=R.drawable.mastard_rounded_btn;
            }
            else if(status.equals("3"))
            {
                statusarray[i]=R.drawable.grey_rounded_btn;
            }

            data.add(data.newItem().add(R.id.button,row.get("box_no"))
//                    .add(R.id.btc_ins_1,row.get("box_no"))
            );
            Log.e(TAG,"DATA >>>>"+data);
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.list_buttons){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                Button btn = (Button) v.findViewById(R.id.button);
                btn.setBackground(getResources().getDrawable(statusarray[position]));


                return v;
            }
        };
        Log.e(TAG,"LIst adapter >>>>>>>");
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        return data;
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
    void callAllClear(){
        Globals.getterList = new ArrayList<>();

        Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("clear_admin", "true"));
        Globals.getterList.add(new ParamsGetter("batch_id", batchid));

        mRequestStatus = REQ_RESET;

        new MainAsyncTask(this, Globals.Webservice.batch_boxno, 1, DBatchPickingActivity.this, "Form", Globals.getterList, true).execute();

    }

    @Override
    public void skipEvent() {

    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                _sts(id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                _sts(id.quantity, buff);
                break;
            case PROC_BOXNO:
                _sts(id.box_no, buff);
                break;}
    }

    @Override
    public void scanedEvent(String barcode) {

        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEventttttt");

            if (mProcNo == PROC_BARCODE){
                // check for QR code
                Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
                Log.e(TAG,"Length of barcode is   "+barcode.length());
                if(BaseActivity.getUrl().equals("https://air-logi-st.air-logi.com/service") &&  BaseActivity.getShopId().equals("1011")) {
                    String result ="";
                    if(barcode.length() == 13){
                        result = barcode.substring(0, barcode.length()-1);
                        Log.e(TAG,"BArcode after chopping last character becomes "+result);
                        barcode=result;
                    }
                    else if(barcode.length() ==14)
                    {
                        result = barcode.substring(1, barcode.length()-1);
                        Log.e(TAG,"BArcode after chopping first and last character becomes "+result);
                        barcode=result;
                    }
                }
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode;

                _sts(id.barcode, barcode);}
        }


        if (mProcNo == PROC_QTY){
            Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
            String finalbarcode = CommonFunctions.getBracode(barcode);
            barcode =finalbarcode;
            Log.e(TAG,"barcode111   "+barcode);
        }



        if (mProcNo == PROC_BOXNO)
            _sts(id.box_no, barcode);

        this.inputedEvent(barcode, true);

    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                _sts(id.barcode, barcode);
                break;
            case PROC_QTY: // 数量
                _sts(id.quantity, barcode);
                break;
            case PROC_BOXNO: // 数量
                _sts(id.box_no, barcode);
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
                if(mRequestStatus == REQ_BARCODE)
                {
                    new BatchBarcodeCheck().post(code,msg,result1, mHash,DBatchPickingActivity.this);
                }
                else if(mRequestStatus == REQ_BATCHDETAIL)
                {
                    new BatchBoxdetail().post(code,msg,result1, mHash,DBatchPickingActivity.this);
                }
                else if(mRequestStatus == REQ_RESET){
                    nextProcess();
                    startActivity( new Intent(this, DBatchActivity.class));
                    finish();
                }
                else if(mRequestStatus ==REQ_INITIAL ||mRequestStatus == REQ_BOXNO)
                {
                    Log.e(TAG,">>>>>>>>>>>>>>>>>>>>>>"+mRequestStatus);
                    JsonHash row = (JsonHash) result1.get(0);
                    String productcount = row.getStringOrNull("product_count");

                    String ordersCount = row.getStringOrNull("orders_count");

                    String finishCount = row.getStringOrNull("finish_orders");

                    String remaining_rows = row.getStringOrNull("main_product_count");
                    String remaining_include_rows = row.getStringOrNull("pending_include_trays");
                    include_left = "0";
                    include_left= U.minusTo(productcount,remaining_include_rows);


                    updateBadge1(ordersCount);
                    updateBadge4(finishCount);
                    updateBadge3(productcount);

                    // if batch has been complete
                    if(ordersCount.equals(finishCount)){
                        Log.e(TAG, "Completed Batchhhhhhhhhhhhhh");
                    startActivity(new Intent(this,DBatchActivity.class));
                    finish();
                }

                  // if there are include products that dont have tray assigned
                  else   if(Integer.parseInt(include_left)<1 && remaining_rows.equals("0")){
                        if(!ordersCount.equals(finishCount)){
                          callAllClear();
                        }
                    }
                    //move to next barcode
                    else
                    nextProcess();

                }
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(DBatchPickingActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else
            if(mRequestStatus == REQ_BARCODE)
            {
                _gt(id.barcode).requestFocus();
                U.beepError(this,msg);
            }
            else if(mRequestStatus == REQ_BATCHDETAIL)
            {
                new BatchOrderdetail().valid(code,msg,result1, mHash,DBatchPickingActivity.this);
            }
            else if(mRequestStatus == REQ_BOXNO)
            {
                _gt(R.id.box_no).requestFocus();
                U.beepError(this,msg);
            }
            else if(mRequestStatus ==REQ_INITIAL)
            {
                U.beepError(this,msg);
            }

        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {
       U.beepError(this, "network error has occured");
    }
}
