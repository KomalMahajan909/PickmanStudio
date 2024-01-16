package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.FixedBatchPickingNS;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetBatchPicking;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetBatchPickingNS;
import com.itechvision.ecrobo.pickman.R;
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

public class BatchPickingActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.gridMain) LinearLayout gridbarcode;
    @BindView(R.id.add_layout) Button keyboard;
    @BindView(R.id.enter) Button enter;

    public static boolean fixed = true;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public static final int PROC_BARCODE = 1;
    public static final int PROC_QTY = 2;
    protected int mProcNo = 0;
    private ScrollView svMain;
    private Button numbrbtn;
    private boolean visible = false;
    protected RelativeLayout layout;
    protected RelativeLayout mainlayout;
    public Context mcontext = this;
    private TextToSpeak mTextToSpeak;
    protected List<Map<String, String>> mBatchList = null;
    ArrayList<String> inspectionstatusList= new ArrayList<>();
    public Map<String, String> mTarget = null;
    int[] statusarray;
    private String TAG = "BatchPickingActivity";
    ListView lv;
    boolean includegrid= false;
    ECRApplication app=new ECRApplication();
    public String adminID="";
    String batchid = "";
    String batchno ="";
    String createdate = "";
    public String _lastUpdateQty = "0";
    protected Map<String, String> ProductList = null;
    public boolean mNextBarcode = false;
    public String isNextBarcode ="";
    public static int mRequestStatus =0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_BARCODE = 2;
    public static final int REQ_QTY = 3;
    int position1 = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_picking);
        ButterKnife.bind(BatchPickingActivity.this);
        Log.d(TAG,"On Create ");
        getIDs();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        lv = (ListView)findViewById(R.id.listView);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);
        numbrbtn = (Button) _g(R.id.add_layout);
        mTextToSpeak = new TextToSpeak(this);
        svMain = (ScrollView) _g(R.id.scrollMain);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        if(BaseActivity.getbatchScreen())
        {
            gridbarcode.setVisibility(View.VISIBLE);
            keyboard.setVisibility(View.VISIBLE);
            enter.setVisibility(View.VISIBLE);
        }
        else {
            gridbarcode.setVisibility(View.GONE);
            keyboard.setVisibility(View.GONE);
            enter.setVisibility(View.GONE);
        }



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
        Globals.getterList.add(new ParamsGetter("create_date", createdate));


            mRequestStatus = REQ_INITIAL;
        new MainAsyncTask(this, Globals.Webservice.BatchPicking, 1, BatchPickingActivity.this, "Form", Globals.getterList, true).execute();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Map<String, String> row = mBatchList.get(position);
                Log.e(TAG,">>>>>>>>>>>>>>>"+position);
                String status = row.get("batch_status");
                Log.e(TAG,">>>>>>>>>>>>>>><<<<<<<<<<<<<<"+status);

                    if (!row.get("ope").equals("済")) {
                        setdialog(row, BatchPickingActivity.this, R.layout.batch_picking_dialog);
                        position1 = position;
                    }

            }
//                _sts(R.id.stock1, qty);
////                mSelectedId = ((TextView) view.findViewById(R.id.stc_prd_3)).getText().toString();
//                U.beepSuccess();
//                setProc(PROC_LOCATION);

        });


    }
    @OnClick(R.id.back)void back(){
        goback();
    }
    @OnClick(R.id.enter)void enter(){
        String s = mBuff.toString();
        Log.e("ScannerbindActivity", "Keyput11222 "+mBuff );
        mBuff.delete(0, mBuff.length());
        Log.e("ScannerbindActivity", "Keyput11222 buff "+mBuff );
        inputedEvent(s);
    }
    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
        if (visible == false) {

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
    public int convert(int x) {
        Resources r = mcontext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                x, r.getDisplayMetrics()
        );
        return px;
    }
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "バッチ商品ピック", " ",
                0, true,true,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
            case R.id.notif_count_red:
//                     setBadge2(0);
                break;
            case R.id.notif_count_blue:
//                setBadge1(0);
                break;

            default:
                break;
        }}
    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {

            case PROC_BARCODE:
                Log.e(TAG, "setProc_BARCODEEEEEEEEE");

                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.barcode).setFocusableInTouchMode(true);

                mTextToSpeak.startSpeaking("barcode");
                scrollToView(svMain, _g(R.id.barcode));
                _lastUpdateQty = "0";
                break;
            case PROC_QTY:
                Log.e(TAG, "setProc_QNTYYYYYYYYYYYY");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                if (_gts(R.id.quantity).equals("")) _sts(R.id.quantity, "1");
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(R.id.quantity));
                break;

        }
    }
    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                String barcode = _gts(R.id.barcode);
                boolean match= false;

                Log.e(TAG, "inputedEvent_BARCODEEEEEEEEEE   "+barcode);
                if(barcode.equals("")){
                U.beepError(this, "barcode cannot be empty");
                Log.e(TAG, "inputedEvent_BARCODE === null"+barcode  );
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                break;
                }
                Log.e(TAG, "inputedEvent_BARCODE >>>>>>>>>>>>>>   "+mBatchList);

                            Globals.getterList = new ArrayList<>();

                            adminID = spDomain.getString("admin_id", null);

                            Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                            Globals.getterList.add(new ParamsGetter("admin_id", adminID));

                            Globals.getterList.add(new ParamsGetter("barcode", barcode));
                            Globals.getterList.add(new ParamsGetter("batch_id", batchid));
                            Globals.getterList.add(new ParamsGetter("create_date", createdate));
                            mRequestStatus = REQ_BARCODE;
                            new MainAsyncTask(this, Globals.Webservice.BatchPicking, 1, BatchPickingActivity.this, "Form", Globals.getterList, true).execute();
                            break;


            case PROC_QTY:
                String qty = _gts(R.id.quantity);
                String barcode1 = _gts(R.id.barcode);

                if (isScaned) {
                    Log.e(TAG, "inputedEvent_QNTYY Barcode Scanned");


                    if (buff.equals(barcode1)) {
                        Log.e(TAG, "inputedEvent_QNTYY Barcode match");
                        qty = U.plusTo(qty, "1");
                        String processedCnt = ProductList.get("processedCnt");
                        if (!processedCnt.equals(_gts(R.id.quantity)))
                            processedCnt = _gts(R.id.quantity);
                        ProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                        //increase qunatity in mpackdata
                        if (Integer.parseInt(ProductList.get("processedCnt")) <= Integer.parseInt(ProductList.get("quantity"))) {
                            String _loc1 = ProductList.get("location");

                            _sts(R.id.quantity, qty);
                            if (Integer.parseInt(qty) > 1)
                                mTextToSpeak.startSpeaking(qty);
//
                            _lastUpdateQty = _gts(R.id.quantity);

                        /* check if update in quantity need next action */
                            if (ProductList.get("processedCnt").equals(ProductList.get("quantity"))) {
                                Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty complete");
                                fixedRequest();
                            }
                        }
                    } else {
                        Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty INcomplete");
                        fixedRequest(buff);
                    }

                } else {
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

                    String processedCnt = ProductList.get("processedCnt");
                    String qtyUpdate = U.minusTo(qty, _lastUpdateQty);
                    String maxQty_ = U.minusTo(ProductList.get("quantity"), processedCnt);
                    Log.e(TAG, "inputedEvent_QNTYY maxqnty " + maxQty_);
                    Log.e(TAG, "inputedEvent_QNTYY qtyUpdate " + qtyUpdate);
                    Log.e(TAG, "inputedEvent_QNTYY processedCnt " + processedCnt);
                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());
                        Log.e(TAG, "inputedEvent_QNTYY is less");
                        _sts(R.id.quantity, "1");

                        ProductList.put("processedCnt", "1");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {
                        Log.e(TAG, "inputedEvent_QNTYY is exceeds");
                        U.beepError(this, "数量が多すぎます");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        _sts(R.id.quantity, "1");

                        ProductList.put("processedCnt", "1");
                        break;
                    } else {
                        if (Integer.parseInt(qty) > 1)
                            mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                        ProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        if (ProductList.get("processedCnt").equals(ProductList.get("quantity"))) {
                            Log.e(TAG, "inputedEvent_QNTYY complete inspect");
                            //increase qunatity in mpackdata

                            fixedRequest();

                        } else {
                            Log.e(TAG, "inputedEvent_QNTYY INcomplete inspect");
                            //increase qunatity in mpackdata

                            fixedRequest();
                        }
//
                    }

                }
                break;


        }
    }
    public void currLineData(Map<String, String> data) {
        Log.e(TAG, "currLineDataaaaaaaaaaa");
        ProductList = data;
        Log.e(TAG, "currLineDataaaaaaaaaaa  " + ProductList);
    }

    void fixedRequest() {
        Log.e(TAG, "FixedRequestttttt");
        sendRequest();
    }

    private void fixedRequest( String nextBarcode) {
        Log.e(TAG, "FixedRequestttttt222222222");

        mNextBarcode = true;
        isNextBarcode =nextBarcode;
        sendRequest();

    }
    public void sendRequest()
    {
//        fixed = false;
        mTarget= null;

        mTarget = ProductList;

//        updateBadge2(mList.size()+"");
        Globals.getterList = new ArrayList<>();


        Globals.getterList.add(new ParamsGetter("admin_id",adminID));

        if(ProductList.get("barcode").equals(""))
            Globals.getterList.add(new ParamsGetter("barcode", ProductList.get("code")));
        else
            Globals.getterList.add(new ParamsGetter("barcode", ProductList.get("barcode")));
        Globals.getterList.add(new ParamsGetter("batch_id", batchid));
        Globals.getterList.add(new ParamsGetter("create_date", createdate));
        Globals.getterList.add(new ParamsGetter("pick_qty", ProductList.get("processedCnt")));

        mRequestStatus =REQ_QTY;



        new MainAsyncTask(this, Globals.Webservice.BatchPicking, 1, BatchPickingActivity.this, "Form", Globals.getterList,true).execute();




    }
    @Override
    public void allclearEvent() {
        CommonDialogs.customToast(this, "No action");
    }

    @Override
    public void skipEvent() {
        CommonDialogs.customToast(this, "No action");
    }
    @Override
    public void inputedEvent(String buff) {
        Log.e(TAG, "inputedEventtttttt");
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        new AlertDialog.Builder(this)
                .setTitle("Clear？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//
                        mTextToSpeak.startSpeaking("clear");
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
        if (CLEAR_BARCODE.equals(barcode)) {
            mBuff.delete(0, mBuff.length());
            clearEvent();
        } else if (ENTER_BARCODE.equals(barcode)) {
            enterEvent();
        } else {
            if(!MainAsyncTask.dialogBox.isShowing()) {
            if (mProcNo == PROC_BARCODE){
                 if(fixed){
                Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
                     String finalbarcode = CommonFunctions.getBracode(barcode);
                     barcode =finalbarcode;

                _sts(R.id.barcode, barcode);}
            else   CommonDialogs.customToast(this,"wait");
            }


            if (mProcNo == PROC_QTY){
                Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode;
            }



            this.inputedEvent(barcode, true);

        }
        else
            CommonDialogs.customToast(this,"wait");
    }}
    public void nextWork() {
        Log.e(TAG, "nextworkkkkkk");
        String processedCnt = ProductList.get("processedCnt");
        Log.e(TAG, "nextworkkkkkk111111111111111111");
        ProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
        Log.e(TAG, "nextworkkkkkk22222222222222222222");
        setProc(PROC_QTY);
        mTextToSpeak.resetQueue();
        Log.e(TAG, "nextworkkkkkkuytrewjhgfdshgfdsgfd");
        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
        _lastUpdateQty = _gts(R.id.quantity);
        Log.e(TAG, "nextworkkkkkk processedCnt" +ProductList.get("processedCnt"));
        Log.e(TAG, "nextworkkkkkk quantity" +ProductList.get("quantity"));

        if (ProductList.get("processedCnt").equals(ProductList.get("quantity"))) {
            fixedRequest();

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

        }
    }
    public void updateBadge2(String qtyCount) {
        Log.e(TAG, "updateBadge222222  " + qtyCount);
        setBadge2(Integer.valueOf(qtyCount));

    }


    public void updateBadge1(String qtyCount) {
        Log.e(TAG,"updateBadge1111111111  "+qtyCount);
        setBadge1(Integer.valueOf(qtyCount));
    }

    public void getBatchList( List<Map<String, String>> list){
        mBatchList = list;
        initWorkList();
    }
    public void setStatusList(ArrayList<String> data)
    {
        inspectionstatusList =data;
        Log.e(TAG,">>>>>>>>>>>>>>>>>>>>    "+inspectionstatusList);
        Log.e(TAG,">>>>>>>>>>>>>>>>>>>>    "+inspectionstatusList.size());
    }
    protected void initWorkList() {
        Log.e(TAG, "initWorkList");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        ArrayList<Integer> used = new ArrayList<>();
        statusarray = new int[inspectionstatusList.size()];
        for(int i=0;i< inspectionstatusList.size();i++){
            Map<String, String> row = mBatchList.get(i);
            Log.e(TAG,">>>>>>>>>>>>>>>"+i);

            if(inspectionstatusList.get(i).equals("0"))
            {
                statusarray[i]=R.drawable.grey_rounded_btn;
            }
            else if(inspectionstatusList.get(i).equals("2"))
            {
                statusarray[i]=R.drawable.red_rounded_corner_button;
            }
            else if(inspectionstatusList.get(i).equals("1"))
            {
                statusarray[i]=R.drawable.green_rounded_corner_btn;
            }
        }
        Log.e(TAG,">>>>>>>>>>>>>>>statusarray     "+ statusarray.length);
        Log.e(TAG,">>>>>>>>>>>>>>>mBatchList     "+ mBatchList.size() + "     "+mBatchList);
        for (int i =0 ; i <= mBatchList.size() - 1; i++) {
            Map<String, String> row = mBatchList.get(i);


            Log.e(TAG,">>>>>>>>>>>>>>>rowwwww     "+ row);

            data.add(data.newItem().add(R.id.btc_pick_txt_1, row.get("ope"))
                    .add(R.id.btc_pick_txt_00, row.get("location"))
                    .add(R.id.btc_pick_txt_01, row.get("code"))
                    .add(R.id.btc_pick_txt_02, row.get("product_name"))
                    .add(R.id.btc_pick_txt_03, row.get("barcode"))
                    .add(R.id.btc_pick_txt_04, row.get("lot"))
                    .add(R.id.btc_pick_txt_2, row.get("category"))
                    .add(R.id.btc_pick_txt_3, row.get("quantity"))

            );

            used.add(i);

        }

        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.batch_picking_activity_list_row) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                TextView img = (TextView) v.findViewById(R.id.btc_pick_txt_1);
                img.setBackground(getResources().getDrawable(statusarray[position]));
                return v;
            }
        };
        lv.setAdapter(adapter);
//        lv.getChildAt(1).setEnabled(false);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
//        return data;
    }
    public void nextProcess() {
        _sts(R.id.barcode, "");
        _sts(R.id.quantity, "");

        setProc(PROC_BARCODE);



    }
    public void scroll(){
        lv.setSelection(position1+1);
    }
    public void goback( ) {
        Log.e(TAG, " gobackkkkkkkkkkkkkkk ");
        startActivity(new Intent(this, BatchPickingOrder.class));
    }

    public void setBadge1(long cnt) {
       btnBlue.setText(cnt+"");
    }
    public void setBadge2(long cnt) {
      btnRed.setText(cnt+"");
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
                Log.e("SendLogs111",code+"  "+msg+"  "+mRequestStatus);


                if(mRequestStatus == REQ_INITIAL)
                {
                    Log.e("SendLogs111",code+"  "+msg+"  >>>>>>>>>>>>>>>>>>>>>>>>>");
                    new GetBatchPicking().post(code,msg,result1, mHash,BatchPickingActivity.this);
                }
                else if (mRequestStatus == REQ_BARCODE){
                    Log.e("SendLogs11333",code+"  "+msg+"  >>>>>>>>>>>>>>>>>>>>>>>>>");
                    new GetBatchPickingNS().post(code, msg, result1, mHash, BatchPickingActivity.this);

                }
                else if(mRequestStatus ==REQ_QTY){
                    Log.e("SendLogs111",code+"  "+msg+"  >>>>>>>>>>>>>>>>>>>>>>>>>"+mRequestStatus);
                    new FixedBatchPickingNS().post(code,msg,result1, mHash,BatchPickingActivity.this);
                }


            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(BatchPickingActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else {  Log.e("SendLogs111",code+"  "+msg+"  >>>>>>>>>>elseeeeee   "+mRequestStatus);
                if (mRequestStatus == REQ_INITIAL) {
                    new GetBatchPicking().valid(code, msg, result1, mHash, BatchPickingActivity.this);
                }
                else if (mRequestStatus == REQ_BARCODE) {
                    new GetBatchPickingNS().valid(code, msg, result1, mHash, BatchPickingActivity.this);
                }
                else if(mRequestStatus ==REQ_QTY){
                    new FixedBatchPickingNS().valid(code,msg,result1, mHash,BatchPickingActivity.this);
                }

            }
        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }
    public void setdialog (final Map<String,String> map, Activity activity , int layout)
    {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set GUI of login screen

        dialog.setContentView(layout);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());


        dialog.setCanceledOnTouchOutside(true);

        // Init button of login GUI
        TextView txtloc=(TextView)dialog.findViewById(R.id.dialog_txt_01) ;
        TextView txtpart=(TextView)dialog.findViewById(R.id.dialog_txt_02) ;
        TextView txtname=(TextView)dialog.findViewById(R.id.dialog_txt_03) ;
        TextView txtbar=(TextView)dialog.findViewById(R.id.dialog_txt_04) ;
        TextView txtlot=(TextView)dialog.findViewById(R.id.dialog_txt_05) ;
        TextView textQty =(TextView)dialog.findViewById(R.id.dialog_txt_06);
        txtloc.setText(map.get("location"));
        txtpart.setText(map.get("code"));
        txtname.setText(map.get("product_name"));
        txtbar.setText(map.get("barcode"));
        txtlot.setText(map.get("lot"));
        textQty.setText(map.get("quantity"));


        Button close=(Button)dialog.findViewById(R.id.done);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mTarget = map;
                Globals.getterList = new ArrayList<>();
                ECRApplication app=new ECRApplication();
                String adminID="";
                adminID=spDomain.getString("admin_id",null);


                Log.e("SendLogs","shopidddddd  "+BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));

                Globals.getterList.add(new ParamsGetter("batch_id", batchid));
                Globals.getterList.add(new ParamsGetter("create_date", createdate));
                if(map.get("barcode").equals(""))
                    Globals.getterList.add(new ParamsGetter("barcode", map.get("code")));
                else
                    Globals.getterList.add(new ParamsGetter("barcode", map.get("barcode")));

                Globals.getterList.add(new ParamsGetter("pick_qty", map.get("quantity")));
                Globals.getterList.add(new ParamsGetter("pick_all", "yes"));
                mRequestStatus =REQ_QTY;

                new MainAsyncTask(BatchPickingActivity.this, Globals.Webservice.BatchPicking, 1, BatchPickingActivity.this, "Form", Globals.getterList,true).execute();


                dialog.dismiss();
            }
        });
        // Make dialog box visible.
        dialog.show();
    }
    public void setdialog (String msg,Activity activity ,int layout)
    {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set GUI of login screen

        dialog.setContentView(layout);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());


        dialog.setCanceledOnTouchOutside(false);

        // Init button of login GUI
        TextView txt=(TextView)dialog.findViewById(R.id.txt) ;
        txt.setText(msg);
        ImageView close=(ImageView)dialog.findViewById(R.id.icon_close);



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // Make dialog box visible.
        dialog.show();
    }
}
