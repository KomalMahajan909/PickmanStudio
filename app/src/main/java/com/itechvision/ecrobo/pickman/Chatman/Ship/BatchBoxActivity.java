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
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.BatchOrderdetail;
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

public class BatchBoxActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;

    protected int mProcNo = 0;
    public static  final  int PROC_ORDER_NO =1;
    public static final int PROC_BOXNO= 2;
    public static int mRequestStatus =0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_ORDER_NO = 2;
    public static final int REQ_BOXNO = 3;
    public static final int REQ_BATCHDETAIL = 4;
    public static final int REQ_REMOVEBOX = 5;
    public static final int REQ_RESET = 6;
    private boolean showKeyboard;
    private TextToSpeak mTextToSpeak;
    public Context mcontext=this;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    List<Map<String, String>> productsList = new ArrayList<>();
    ECRApplication app=new ECRApplication();
    String adminID="";
    String batchid ="",batchno="",createdate ="";
    private boolean visible = false;
    String TAG= BatchBoxActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_box);

        ButterKnife.bind(this);
        Log.d(TAG,"On Create ");
        getIDs();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        showKeyboard=BaseActivity.getaddKeyboard();

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);

        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
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

        _sts(R.id.batchid,batchno);

        Globals.getterList = new ArrayList<>();

        adminID = spDomain.getString("admin_id", null);

        Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("batch_id", batchid));
        Globals.getterList.add(new ParamsGetter("initial_call", "true"));


        mRequestStatus = REQ_INITIAL;
        new MainAsyncTask(this, Globals.Webservice.batch_boxno, 1, BatchBoxActivity.this, "Form", Globals.getterList, true).execute();

        if(mProcNo ==0)
            setProc(PROC_ORDER_NO);

    }

    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "間口と注文の紐付け", " ",
                0, true,true,true );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);


        relLayout1.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnRed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG,">>>>>>>>>>>>>>"+getBadge3());
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;

            case R.id.notif_count_green:
                Log.e(TAG,">>>>>>>>>>>>>>11111111111111111111111"+getBadge3());
                if(getBadge3()!= 0){
                    showInfo();
                }
                break;
            case R.id.notif_count_red:
                Log.e(TAG,">>>>>>>>>>>>>>11111111111111111111111"+getBadge2());
                Intent i = new Intent(this, DbatchIncludeActivity.class);
                i.putExtra("batch_no",batchno );
                i.putExtra("batch_id",batchid );
                i.putExtra("create_date", createdate);
                startActivity(i);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.nextscreen) void Next (){
        Intent i = new Intent(this, DBatchPickingActivity.class);
        i.putExtra("batch_no",batchno );
        i.putExtra("batch_id",batchid );
        i.putExtra("create_date", createdate);
        startActivity(i);
    }

    @OnClick(R.id.enter)void enter(){
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
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

    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {
            case PROC_ORDER_NO:
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.orderId).setFocusableInTouchMode(true);
                _gt(R.id.box_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                break;
            case PROC_BOXNO:
                _gt(R.id.orderId).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.box_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.box_no).setFocusableInTouchMode(true);
                break;
        }}


    @Override
    public void inputedEvent(String buff) {

        switch (mProcNo) {
            case PROC_ORDER_NO:
                String order = _gts(R.id.orderId);

                if ("".equals(order)) {
                    U.beepError(this, "賞味期限が必要です");
                    _gt(R.id.orderId).setFocusableInTouchMode(true);

                    break;
                }
                Globals.getterList = new ArrayList<>();

                Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("order_id", order));
                Globals.getterList.add(new ParamsGetter("batch_id", batchid));
                Globals.getterList.add(new ParamsGetter("check_order", "true"));

                mRequestStatus = REQ_ORDER_NO;

                new MainAsyncTask(this, Globals.Webservice.batch_boxno, 1, BatchBoxActivity.this, "Form", Globals.getterList, true).execute();
                break;
            case PROC_BOXNO:
                String box = _gts(R.id.box_no);
                box = box.replaceFirst("^0+(?!$)", "");
                _sts(R.id.box_no,box);

                if ("".equals(box) || "0".equals(box)) {
                    U.beepError(this, "賞味期限が必要です");
                    _gt(R.id.box_no).setFocusableInTouchMode(true);

                    break;
                }

                Globals.getterList = new ArrayList<>();

                Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("order_id", _gts(R.id.orderId)));
                Globals.getterList.add(new ParamsGetter("box_no", _gts(R.id.box_no)));
                Globals.getterList.add(new ParamsGetter("batch_id", batchid));

                mRequestStatus = REQ_BOXNO;

                new MainAsyncTask(this, Globals.Webservice.batch_boxno, 1, BatchBoxActivity.this, "Form", Globals.getterList, true).execute();
                break;
        }
    }

    @Override
    public void nextProcess() {
        Log.e(TAG, "nexttttPRocessssssssssssss");
        _sts(R.id.orderId, "");
        _sts(R.id.box_no, "");
  ;
        setProc(PROC_ORDER_NO);

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

    @Override
    public void clearEvent() {
        Log.e(TAG,"clearEventtttt");
        mTextToSpeak.startSpeaking("clear");
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

        new MainAsyncTask(this, Globals.Webservice.batch_boxno, 1, BatchBoxActivity.this, "Form", Globals.getterList, true).execute();

    }

    @Override
    public void skipEvent() {
        CommonDialogs.customToast(this,"No action to perform");
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_ORDER_NO:
                _sts(R.id.orderId, buff);
                break;
            case PROC_BOXNO:
                _sts(R.id.box_no, buff);
                break;
    }}

    @Override
    public void scanedEvent(String barcode) {
        if (mProcNo == PROC_ORDER_NO)
            _sts(R.id.orderId, barcode);
        else if(mProcNo == PROC_BOXNO) {
            barcode =barcode.replaceFirst("^0+(?!$)", "");
            _sts(R.id.box_no, barcode);
        }
        this.inputedEvent(barcode);
    }

    @Override
    public void enterEvent() {

    }

    public void updateBadge1(String orderCount) {
        Log.e(TAG, "updateBadge1111111111  " + orderCount);
        setBadge1(Integer.valueOf(orderCount));
    }
    public void updateBadge3(String boxCount) {
        Log.e(TAG, "updateBadge1111111111  " + boxCount);
        setBadge3(Integer.valueOf(boxCount));
    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_ORDER_NO:
                _sts(R.id.orderId, barcode);
                break;
            case PROC_BOXNO:    // バーコード
                _sts(R.id.box_no, barcode);
                break;
        }
    }

    protected boolean showInfo(){
        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("order_detail", "true"));
        Globals.getterList.add(new ParamsGetter("batch_id", batchid));
        mRequestStatus= REQ_BATCHDETAIL;
        new MainAsyncTask(this, Globals.Webservice.batch_boxno, 1, BatchBoxActivity.this, "Form", Globals.getterList, true).execute();

        return true;
    }
    public void getProductList( List<Map<String, String>> list){
        productsList = list;
        showlist();
    }
    protected boolean showlist() {
        Log.e(TAG, " showInfooooo");

        if (productsList == null) {
            return true;
        }
        Log.e(TAG,"initiatePopuppppppppp");
        final PopupWindow popupWindow = new PopupWindow(this);

        Log.e(TAG,"initiatePopuppppppppp_getPopupWindow");

        // レイアウト設定
        View popupView = getLayoutInflater().inflate(R.layout.batch_detail_popup, null);
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

        Button close =(Button)getPopupWindow().getContentView().findViewById(R.id.batch_close_btn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        ListView lv = (ListView) getPopupWindow().getContentView().findViewById(R.id.lvPackingList);
        initWorkList(lv);

        // 画面中央に表示
        getPopupWindow().showAtLocation(findViewById(R.id.orderId), Gravity.CENTER, 0, 32);
        return false;

    }


    protected ListViewItems initWorkList(final ListView lv) {
        Log.e(TAG," initWorkList" +productsList);
        lv.setAdapter(null);

        ListViewItems data = new ListViewItems();
        for (int i = 0; i <=productsList.size() - 1; i++) {
            Map<String, String> row =productsList.get(i);
            Log.e(TAG," initWorkList_"+row.get("order_id"));

            data.add(data.newItem().add(R.id.btc_ins_0,row.get("order_id"))
                    .add(R.id.btc_ins_1,row.get("box_no"))
            );
            Log.e(TAG,"DATA >>>>"+data);
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.batch_order_list){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Log.e(TAG,"Returnleftttttttttt1111  "+position);

                ImageView img = (ImageView) v.findViewById(R.id.delete_box);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,String> map = new HashMap<>();
                        map= productsList.get(position);
                        Globals.getterList = new ArrayList<>();

                        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                        Globals.getterList.add(new ParamsGetter("remove_boxno","true"));
                        Globals.getterList.add(new ParamsGetter("batch_id", batchid));
                        Globals.getterList.add(new ParamsGetter("order_id", map.get("order_id")));
                        mRequestStatus= REQ_REMOVEBOX;
                        new MainAsyncTask(BatchBoxActivity.this, Globals.Webservice.batch_boxno, 1, BatchBoxActivity.this, "Form", Globals.getterList, true).execute();

                        productsList.remove(position);

                        Log.e(TAG,"Returnleftttttttttt  "+productsList);
                        initWorkList(lv);
                        updateBadge3(productsList.size()+"");
                    }
                });
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
                Log.e("SendLogs111",">>>>>>>>>>>>>>>>>>  "+mRequestStatus);
                if(mRequestStatus == REQ_ORDER_NO)
                {Log.e("SendLogs111",">>>>>>>>>>>>>>>>>>111111111111  ");
                    setProc(PROC_BOXNO);
                }
                else if(mRequestStatus == REQ_BATCHDETAIL)
                {
                    new BatchOrderdetail().post(code,msg,result1, mHash,BatchBoxActivity.this);
                }
                else if(mRequestStatus == REQ_RESET){
                    nextProcess();
                    _sts(R.id.batchid,"");
                    startActivity( new Intent(this, DBatchActivity.class));
                    finish();
                }

                else if(mRequestStatus ==REQ_INITIAL ||mRequestStatus == REQ_BOXNO)
                {
                   JsonHash row = (JsonHash) result1.get(0);

                   String ordersCount = row.getStringOrNull("orders_count");
                   String boxCount = row.getStringOrNull("boxes_count");
                   updateBadge1(ordersCount);
                   updateBadge3(boxCount);

                   if(ordersCount.equals(boxCount)){
                       Intent i = new Intent(this, DBatchPickingActivity.class);
                       i.putExtra("batch_no",batchno );
                       i.putExtra("batch_id",batchid );
                       i.putExtra("create_date", createdate);
                       startActivity(i);
                   }
                   else   nextProcess();
                }
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(BatchBoxActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else
            if(mRequestStatus == REQ_ORDER_NO)
            {
                U.beepError(this,msg);
            }
            else if(mRequestStatus == REQ_BATCHDETAIL)
            {
                new BatchOrderdetail().valid(code,msg,result1, mHash,BatchBoxActivity.this);
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
            else if(mRequestStatus == REQ_RESET){
                nextProcess();
                _sts(R.id.batchid,"");
                startActivity( new Intent(this, DBatchActivity.class));
                finish();
            }

        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {
         U.beepError(this,"Network error occured");


    }
}
