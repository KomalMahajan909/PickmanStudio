package com.itechvision.ecrobo.pickman.Chatman.Stock;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.AddStockProduct;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.GetStockProduct;
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

public class StockChangeActivity  extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.listProduct) ListView lv;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.spinnerlayout) RelativeLayout spinnerLayout;
    @BindView(R.id.partnoSpinner) Spinner partNospinner;

    protected List<Map<String, String>> stockList = new ArrayList<Map<String, String>>();
    protected List<Map<String, String>> multistockList = new ArrayList<Map<String, String>>();
    protected ArrayList<String> PartnoArray = new ArrayList<String>();

    public LinearLayout partnoLayout;

    private boolean multicode = false;
    public static String mSelectedId = null;	//selected stock classification
    public static String processedCount = "0"; //scanned qunatity
    public static String totalqnty = null;
    public static String productID = null;

    protected int mProcNo = 0;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_PARTNO = 2;
    public static final int PROC_QTY = 3;
    public static final int PROC_STOCK_ID =4;

    public static int mRequestStatus =0;

    public static final int REQ_INITIAL = 1;
    public static final int REQ_BARCODE = 2;
    public static final int REQ_QTY = 3;

    private boolean showKeyboard;
    private boolean visible = false;
    public Context mcontext=this;

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    ECRApplication app=new ECRApplication();
    String adminID="";
    String productCode = "";

    private TextToSpeak mTextToSpeak;

    String TAG= StockChangeActivity.class.getSimpleName();
    ListViewItems classificationData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_change);

        ButterKnife.bind(StockChangeActivity.this);

        getIDs();
        Log.d(TAG,"On Create ");
        partnoLayout = (LinearLayout)findViewById(R.id.partnoLayout);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);


        showKeyboard = BaseActivity.getaddKeyboard();
        if (showKeyboard == true) {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
            visible = true;

            numbrbtn.setText(R.string.hideKeyboard);
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
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }


        if (mProcNo == 0) nextProcess();
        _glv(R.id.listProduct).setAdapter(null);
        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);
        _gt(R.id.totalQuantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                // ロケが選択されたら次工程
                Map<String,String> map = new HashMap<>();
                map = stockList.get(position);
                Log.e(TAG, "MAp data got is  "+map);

                productID = map.get("product_id");
                mSelectedId = map.get("stock_type_id");
                String qty = ((TextView) view.findViewById(R.id.stc_cls_1)).getText().toString();
                _sts(R.id.totalQuantity, qty);
                totalqnty = qty;
                String stock = ((TextView) view.findViewById(R.id.stc_cls_2)).getText().toString();
                _sts(R.id.stockClassification,stock);

                U.beepNext();
                processedCount="1";
                setProc(PROC_QTY);
            }
        });

        partNospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position >0 ){

                    String code = PartnoArray.get(position);
                    productCode = code ;
                    getList(code);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "在庫マイナス", " ",
                0, false,false,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relLayout1:
                menu.showMenu();
                break;

            default:
                break;
        }
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
            Log.e("MoveActivity","SetlayoutMarginnnnn");
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
            Log.e("MoveActivity","SetlayoutMarginnnnn");
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
        switch (procNo) {
            case PROC_BARCODE:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.stockClassification).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _glv(R.id.listProduct).setAdapter(null);
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;
            case PROC_PARTNO:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.stockClassification).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                break;
            case PROC_QTY:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.stockClassification).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setFocusableInTouchMode(true);
                _sts(R.id.quantity,"1");
                mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                break;
            case PROC_STOCK_ID:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.stockClassification).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.stockClassification).setFocusableInTouchMode(true);

                break;

        }
    }
    public void inputedEvent(String buff, boolean isScaned) {

        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード

                String barcode1=_gts(R.id.barcode);
                if ("".equals(barcode1)) {
                    U.beepError(this, "バーコードが必要です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);
                    break;
                }
                Log.e(TAG,"InputedEventBArcodeeeeeeeeee");
                U.beepRecord(this, null);

                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode",_gts(R.id.barcode)));
                Globals.getterList.add(new ParamsGetter("barcode_search","true"));


                mRequestStatus = REQ_BARCODE;

                new MainAsyncTask(this, Globals.Webservice.productQuantity, 1, StockChangeActivity.this, "Form", Globals.getterList,true).execute();


                break;
            case PROC_STOCK_ID:
                if(!_gts(R.id.stockClassification).equals("")){
                    setProc(PROC_QTY);
                    processedCount="1";
                }
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                        // ロケが選択されたら次工程
                        Map<String,String> map = new HashMap<>();
                        map = stockList.get(position);
                        Log.e(TAG, "MAp data got is  "+map);

                        productID = map.get("product_id");
                        mSelectedId = map.get("stock_type_id");
                        String qty = ((TextView) view.findViewById(R.id.stc_cls_1)).getText().toString();
                        _sts(R.id.totalQuantity, qty);
                        totalqnty = qty;
                        String stock = ((TextView) view.findViewById(R.id.stc_cls_2)).getText().toString();
                        _sts(R.id.stockClassification,stock);
                        U.beepNext();
                        setProc(PROC_QTY);
//                _sts(R.id.quantity,"1");
                        processedCount="1";
                    }
                });
            case PROC_QTY: // 数量
                String qty = _gts(R.id.quantity);
                if(qty.equals(""))
                    qty="1";
                String barcode = _gts(R.id.barcode);
                Log.e(TAG,"Qtyyyyy  "+qty);
                Log.e(TAG, "buff " + buff);

                if (isScaned) {

                    Log.e(TAG,"Barcode at present is   "+barcode);
                    if (buff.equals(barcode)) {
                        U.beepSuccess();

                        qty = U.plusTo(qty, "1");
                        _sts(R.id.quantity, qty);
                        mTextToSpeak.startSpeaking(_gts(R.id.quantity));

                        if (!processedCount.equals(_gts(R.id.quantity)))
                            processedCount = _gts(R.id.quantity);
                        processedCount = U.plusTo(processedCount,"1");
                        //increase qunatity in mpackdata
                        if (Integer.parseInt(processedCount) <= Integer.parseInt(totalqnty)) {

                            _sts(R.id.quantity, qty);

                            if (Integer.parseInt(processedCount) == Integer.parseInt(totalqnty)) {
                                Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty complete");
                                fixedRequest();
                                break;
                            }
                        }
                    }
                    else {

                        Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty INcomplete");
                        U.beepError(this,"バーコードが一致しません");
//                            fixedRequest();
                        break;
                    }
                }
                else {
                    if ("".equals(qty)) {
                        U.beepError(this, "数量は必須です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }
                    else if(Integer.parseInt(qty)<=0){
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }
                    if (Integer.parseInt(processedCount) > Integer.parseInt(totalqnty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else {
                        stopTimer();
                        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                        fixedRequest();
                    }
                }
                break;


        }}
    void fixedRequest() {
        Log.e(TAG, "FixedRequestttttt");
        stopTimer();
        Globals.getterList = new ArrayList<>();

        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        if(!multicode)
        Globals.getterList.add(new ParamsGetter("barcode",_gts(R.id.barcode)));
        else
            Globals.getterList.add(new ParamsGetter("barcode",productCode));
        Globals.getterList.add(new ParamsGetter("stock_type_id",mSelectedId));
        Globals.getterList.add(new ParamsGetter("product_id",productID));
        Globals.getterList.add(new ParamsGetter("qty",_gts(R.id.quantity)));
        Globals.getterList.add(new ParamsGetter("time_taken",timeTaken().toString()));

        mRequestStatus = REQ_QTY;

        new MainAsyncTask(this, Globals.Webservice.productQuantity, 1, StockChangeActivity.this, "Form", Globals.getterList,true).execute();

    }

    public void getList(String part)
    {

        ListViewItems listdata = new ListViewItems();
        List<Map<String, String>> data = new ArrayList<>();
        for(Map<String,String> map : multistockList){
            String _c = map.get("code");
            if(_c.equals(part)){

                listdata.add(listdata.newItem()
                        .add(R.id.stc_cls_1, map.get("quantity"))
                        .add(R.id.stc_cls_2, map.get("st_name")));
                 data.add(map);
                 Log.e(TAG, "dataaaaaaaaaaaaaa  "+data);

                if(data.size()==1)
                {
                    _sts(R.id.totalQuantity,map.get("quantity"));
                    _sts(R.id.stockClassification,map.get("st_name"));
                    mSelectedId = map.get("stock_type_id");
                    totalqnty =map.get("quantity");
                    productID =map.get("product_id");
                }
            }
        }

        stockList = data;

        if (listdata.getData().size() == 0) {
            U.beepError(this, "該当商品は在庫がありません");
            return;
        }

        ListViewAdapter adapter = new ListViewAdapter(
               getApplicationContext()
                , listdata
                , R.layout.list_stock_classifications);
        lv.setAdapter(adapter);
        ListView lv = (ListView) findViewById(R.id.listProduct);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        startTimer();
        if (classificationData.getData().size() == 1)
            setProc(PROC_QTY);
        else
            setProc(PROC_STOCK_ID);

        U.beepNext();
    }


    public void getMultiPartList(List<Map<String, String>> stock, ListViewItems data, boolean multi, List<String> part)
    {
        multistockList =stock;
        classificationData = data;
        multicode = multi;
        PartnoArray = (ArrayList<String>) part;
    }

    public void getStockData(List<Map<String, String>> stock)
    {
        stockList =stock;
    }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        Log.e(TAG,"clearEventtttt");
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
    }

    @Override
    public void allclearEvent() {

    }

    @Override
    public void skipEvent() {

    }

    @Override
    public void nextProcess() {
        _sts(R.id.barcode, "");
        _sts(R.id.quantity, "");

        partnoLayout.setVisibility(View.GONE);
        partNospinner.setAdapter(null);

        productCode ="";
        multicode = false;

        this._glv(R.id.listProduct).setAdapter(null);
        _sts(R.id.stockClassification, "");
        _sts(R.id.totalQuantity, "");
        mSelectedId= null;
        productID =null;
        totalqnty = null;
        processedCount = "0";
        setProc(PROC_BARCODE);
        _gt(R.id.barcode).requestFocus();
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
        Log.e(TAG,"scanneddddddEventtttt   "+ barcode);


        if (!barcode.equals("")) {
            if (mProcNo == PROC_BARCODE){
                Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
                String finalbarcode1 = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode1;
                _sts(R.id.barcode, barcode);}

        }

        else if (mProcNo == PROC_QTY){
            Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
            String finalbarcode1 = CommonFunctions.getBracode(barcode);
            barcode =finalbarcode1;
            Log.e(TAG,"barcode111   "+barcode);
        }

        this.inputedEvent(barcode, true);


    }

    @Override
    public void enterEvent() {
        Log.e("PickingActivityyyyyy","enterEventttttt");
        toast("Enter Event");
        if( mProcNo == PROC_QTY)
            fixedRequest();
    }

    @Override
    public void deleteEvent(String barcode) {
        Log.e(TAG,"deleteEventtttttttt");
        switch (mProcNo) {
            case PROC_BARCODE:	// バーコード
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.quantity, barcode);
                break;
            case PROC_STOCK_ID: //
                _sts(R.id.stockClassification, barcode);
                break;

        }
    }

    protected void onDestroy() {
        Log.e(TAG,"onDestroyyyyyyyyyyyyyy");
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        mTextToSpeak.resetQueue();
        super.onStop();
    }
    @Override
    public void onBackPressed() {
        // TODO not backed from picking activity
        //super.onBackPressed();
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
                    new GetStockProduct().post(code,msg,result1, mHash,StockChangeActivity.this);
                }

                else if(mRequestStatus == REQ_QTY){
                    new AddStockProduct().post(code,msg,result1,mHash,StockChangeActivity.this);
                }


            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(StockChangeActivity.this,LoginActivity.class);
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
                new GetStockProduct().valid(code,msg,result1, mHash,StockChangeActivity.this);
            }

            else if(mRequestStatus == REQ_QTY){
                new AddStockProduct().valid(code,msg,result1,mHash,StockChangeActivity.this);
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
}
