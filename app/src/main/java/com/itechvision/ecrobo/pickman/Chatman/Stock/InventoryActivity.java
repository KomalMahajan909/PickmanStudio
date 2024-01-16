package com.itechvision.ecrobo.pickman.Chatman.Stock;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.GetLocationTally;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.TallyInventories;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.ValidateBarcode;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.ValidateLotno;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.R.drawable;
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

public class InventoryActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout)Button numbrbtn;
    @BindView(R.id.layout_main)RelativeLayout mainlayout;
    @BindView(R.id.layout_number)RelativeLayout layout;
    @BindView(R.id.gridLot) LinearLayout layoutlot;
    @BindView(R.id.spinnerlayout) RelativeLayout spinnerLayout;
    @BindView(R.id.quantity)
    EditText quantity;
    @BindView(R.id.partnoSpinner)
    Spinner partNospinner;

    public Context mcontext=this;
    private TextToSpeak mTextToSpeak;
    private boolean showKeyboard;
    private boolean visible = false,multicode = false;;
    private boolean orderRequestSettings;

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    ECRApplication app=new ECRApplication();
    String adminID="";

    int itemscanned=0;
    String lotprsent= "0";


    protected List<Map<String, String>> mStockList = null;
    protected ArrayList<String> PartnoArray = new ArrayList<String>();
    protected Map<String,String> mTarget = null;

    protected int mProcNo = 0;
    public static final int PROC_LOCATION = 1;
    public static final int PROC_BARCODE = 2;
    public static final int PROC_QTY = 3;
    public static final int PROC_LOT_NO = 4;
    public static final int PROC_PARTNO = 5;

    public static int mRequestStatus =0;

    public static final int REQ_LOCATION = 1;
    public static final int REQ_BARCODE = 2;
    public static final int REQ_LOT = 3;
    public static final int REQ_QTY = 4;

    public LinearLayout partnoLayout;

    String TAG= InventoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        ButterKnife.bind(InventoryActivity.this);
        Log.d(TAG,"On Create ");
        getIDs();

        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        partnoLayout = (LinearLayout)findViewById(R.id.partnoLayout);

        orderRequestSettings = BaseActivity.getLotPress();
        if(orderRequestSettings)
            layoutlot.setVisibility(View.VISIBLE);
        else
            layoutlot.setVisibility(View.GONE);

        mTextToSpeak = new TextToSpeak(this);

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);

        showKeyboard=BaseActivity.getaddKeyboard();
        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(id.layout_number);
            visible = true;

            numbrbtn.setText(R.string.hideKeyboard);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(300);
            int top = convert(10);
            int btm= convert(100);
            params.setMargins(0,0,0,bottom);

            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,btm);
            Log.e(TAG,"SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        _gt(id.productCode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
        if(mProcNo == 0) nextProcess();
        Log.e(TAG,"AfterNextProcess");

        partNospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0 ){
                    String code = PartnoArray.get(position);
                    _sts(R.id.productCode, code);
                    setDataProc(code,_gts(R.id.barcode));

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

        actionbarImplement(this,"棚卸", " ",
                0, false,true,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);


        relLayout1.setOnClickListener(this);
        btnRed.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
            case id.notif_count_red:
                showInventory();

            default:
                break;
        }
    }
    public void AddLayout(View view)
    {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(id.layout_number);
        if(visible==false)
        {
            visible = true;
            numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(300);
            int top = convert(10);
            int btm= convert(100);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,btm);
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
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

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
    @Override
    protected void onDestroy() {
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();

    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {
            case PROC_LOCATION:
                Log.e(TAG,"SETPROC_Proc_Locationnnnnn");
                _gt(id.location).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.location).setFocusableInTouchMode(true);
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                if (orderRequestSettings)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                break;
            case PROC_BARCODE:
                Log.e(TAG,"SETPROC_Proc_BarCodeeeeeeee");
                _gt(id.location).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setFocusableInTouchMode(true);
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                if (orderRequestSettings)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                break;
            case PROC_PARTNO:
                break;
            case PROC_QTY:
                Log.e(TAG,"SETPROC_Proc_Quantityyyyyyy");
                _gt(id.location).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                if (_gts(id.quantity).equals("")){
                    _sts(id.quantity, "1");
                    //  mTextToSpeak.startSpeaking(quantity.getText().toString());
                }
                _gt(id.quantity).setFocusableInTouchMode(true);
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                if (orderRequestSettings)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                break;
            case PROC_LOT_NO:
                Log.e(TAG,"SETPROC_Proc_LOT_NOOOOOOOOOOOOOO");
                _gt(id.location).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.quantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.lotno).setFocusableInTouchMode(true);
                spinnerLayout.setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));

        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        Log.e(TAG,"InputedEventttttttttt");
        String location = _gts(id.location);
        String barcode = _gts(id.barcode);
        String qty = _gts(id.quantity);
        switch (mProcNo) {
            case PROC_LOCATION:    // ロケ
                Log.e(TAG, "InputedEvent_PROC_LOC");
                if ("".equals(location)) {
                    U.beepError(this, "在庫数情報に該当ロケは見つかりせん");
                    _gt(id.location).setFocusableInTouchMode(true);
                    break;
                }

                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("location", location));

                mRequestStatus = REQ_LOCATION;

                new MainAsyncTask(this, Globals.Webservice.getLocationTally, 1, InventoryActivity.this, "Form", Globals.getterList,true).execute();

                break;
            case PROC_BARCODE:    // バーコード
                Log.e(TAG, "InputedEvent_PROC_BARCODE");
                setBarcodeTemp(buff);
                if ("".equals(barcode)) {
                    U.beepError(this, "Barcode could not be empty");
                    _gt(id.barcode).setFocusableInTouchMode(true);

                    break;
                }
                Boolean isNewItem = true;

                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());
                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode", barcode));
                Globals.getterList.add(new ParamsGetter("lot_no", ""));


                if (orderRequestSettings )
                {
                    setProc(PROC_LOT_NO);

                    mRequestStatus = REQ_BARCODE;
                    new MainAsyncTask(this, Globals.Webservice.validateProduct, 1, InventoryActivity.this, "Form", Globals.getterList,true).execute();

                }
                else{

                    mRequestStatus = REQ_BARCODE;
                    new MainAsyncTask(this, Globals.Webservice.validateProduct, 1, InventoryActivity.this, "Form", Globals.getterList,true).execute();

                }
                break;
            case PROC_LOT_NO:
                String lot= _gts(id.lotno);
                Log.e(TAG,"InputedEvent_PROC_LOTNO");
                if ("".equals(lot)){
                    U.beepError(this, "Barcode could not be empty");
                    _gt(id.lotno).setFocusableInTouchMode(true);
                    break;
                }
                setProc(PROC_QTY);
                Boolean isNewItem1 = true;
                for (Map<String, String> map : mStockList){
                    Log.e(TAG,"InputedEvent_PROC_LOTNO_FORLOOP");
                    if ((map.get("lot").equals(buff))&& map.get("barcode").equals(barcode)){
                        _sts(id.productCode, map.get("code"));
                        mTarget = map;
                        isNewItem1 = false;
                    }
                }
                if(isNewItem1) {
                    Log.e(TAG, "InputedEvent_PROC_LOTNO_IF");

                    Globals.getterList = new ArrayList<>();

                    Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());
                    Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                    Globals.getterList.add(new ParamsGetter("barcode", barcode));
                    Globals.getterList.add(new ParamsGetter("lot_no", lot));

                    mRequestStatus = REQ_BARCODE;

                    new MainAsyncTask(this, Globals.Webservice.validateProduct, 1, InventoryActivity.this, "Form", Globals.getterList,true).execute();
                }
                else {

                    if (buff.equals(_gts(id.lotno))) {
                        if ((getBarcodeTemp().concat(buff)).equals(_gts(id.barcode).concat(_gts(id.lotno)))) {

                            Log.e(TAG, "InputedEvent_PROC_LOTNO_ELSE");
                            Log.e(TAG,"Quantity record "+mTarget.get("qtyScanned"));
                            String qnty = U.plusTo(mTarget.get("qtyScanned"), "1");
                            _sts(id.quantity, qnty);
                            mTarget.put("qtyScanned", qnty);
                            mTextToSpeak.startSpeaking(qnty);
                            updateBadge1();
                        }
                    }
                }

                break;
            case PROC_QTY:
                if(qty.equals("") ||qty.equals("0")) {
                    qty = "1";
                }
                Log.e(TAG,"Quantity record Proc qnty"+qty);
                if (isScaned) {

                    if(orderRequestSettings)
                    {
                        if (buff.equals(_gts(id.barcode)) || buff.equals(_gts(id.lotno))) {
                            if (buff.equals(_gts(id.barcode)) && !_gts(id.lotno).equals("")) {
                                setBarcodeTemp(buff);
                                break;
                            }
                            if (buff.equals(_gts(id.lotno))) {
                                if ((getBarcodeTemp().concat(buff)).equals(_gts(id.barcode).concat(_gts(id.lotno)))) {

                                    qty = U.plusTo(qty, "1");
                                    Log.e(TAG,"Quantity entered "+qty);
                                    _sts(id.quantity, qty);
                                    mTarget.put("qtyScanned", qty);
                                    if(Integer.parseInt(qty)>1)
                                        mTextToSpeak.startSpeaking(qty);
                                    updateBadge1();
                                }
                            }
                        }
                        else {
                            Log.e(TAG,"InputedEvent_PROC_QTY_ELSE");
                            _sts(id.barcode, buff);
                            _sts(id.quantity, "");
                            _sts(id.lotno, "");
                            setProc(PROC_BARCODE);
                            inputedEvent(buff, true);
                        }
                    }
                    else {
                        if (buff.equals(barcode)){

                            qty = U.plusTo(qty, "1");
                            String quant=qty;
                            Log.e(TAG,"Quantity set "+qty);
                            Log.e(TAG,"Quantity taken "+quant);
                            _sts(id.quantity, qty);
                            Log.e(TAG,"Quantity set "+mTarget);
                            mTarget.put("qtyScanned", qty);

                            if(Integer.parseInt(qty)>1)
                                mTextToSpeak.startSpeaking(qty);

                        } else {
                            Log.e(TAG,"InputedEvent_PROC_QTY_ELSE  222222");
                            _sts(id.barcode, buff);
                            _sts(id.quantity, "");
                            setProc(PROC_BARCODE);
                            inputedEvent(buff, true);
                        }
                    }} else {
                    Log.e(TAG,"InputedEvent_PROC_QTY_NotSCANNED ");
                    if ("".equals(qty)) {
                        Log.e(TAG,"InputedEvent_PROC_QTY_QTYequal");
                        U.beepError(this, "数量は必須です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        Log.e(TAG,"InputedEvent_PROC_QTY_isNumber");
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    }
                    Log.e(TAG,"InputedEvent_PROC_QTY_QtyScanned");
                    _sts(id.quantity, qty);
                    mTarget.put("qtyScanned", qty);
                    updateBadge1();
                    findRepeat();
                    _sts(id.barcode, "");
                    _sts(id.quantity, "");
                    _sts(id.productCode, "");
                    if(orderRequestSettings )
                        _sts(id.lotno, "");
                    setProc(PROC_BARCODE);
                }
                break;
        }
    }

    public void updateBadge1() {
        int count = 0;
        itemscanned=0;

        for (Map<String, String> map : mStockList){
            Log.e(TAG,"UpdateTallyBadgeeeeeeeeeeee" + count+"   >>>>  " +map.get("qtyScanned"));
            count += Integer.parseInt(map.get("qtyScanned"));
        }
        setBadge2(count);
        itemscanned=count;
    }

    public void getmultiPartarrivalList( boolean multi, List<String> part) {
        Log.e(TAG, "getmultiPartarrivalList    "+part);

        multicode = multi;
        PartnoArray = (ArrayList<String>) part;

    }

    public void createNewEntry(String product_code, String barcode){
        String qty = _gts(id.quantity);
        String lot ="";
        if(orderRequestSettings )
            lot= _gts(id.lotno);
        _sts(id.productCode, product_code);
        mTarget = new HashMap<String, String>();
        mTarget.put("barcode", barcode);
        mTarget.put("code", _gts(id.productCode));
        mTarget.put("quantity", "0");
        mTarget.put("qtyScanned", qty);
        mTarget.put("lot",lot);
        mStockList.add(mTarget);

    }

    private void findRepeat()
    {
        List<Map<String,String>> list = new ArrayList<>();
        List<Map<String,String>> duplicate = new ArrayList<>();


        for (int i=0; i< mStockList.size();i++) {
            String temp = "";
            boolean match = false;
            Map<String, String> map1 = mStockList.get(i);
            String _b = map1.get("barcode");
            String _c = map1.get("code");
            String _q = map1.get("quantity");
            Log.e(TAG, "mapp1   "+map1);

            if(list.isEmpty())
                list.add(map1);

            else {
                for (int j = 0; j<list.size();j++) {
                    Map<String, String> map2 = list.get(j);
                    String _b1 = map2.get("barcode");
                    String _c1 = map2.get("code");
                    String _q1 = map2.get("quantity");
                    Log.e(TAG, "mapp2   "+map2);
                    Log.e(TAG, "iiiii  ="+i+"  jjjjj="+j);

                    if (_b.equals(_b1) && _c.equals(_c1)) {

                        temp = U.plusTo(map1.get("qtyScanned"), map2.get("qtyScanned"));
                        match =  true;

                        if(!_q.equals("0")) {
                            map2.put("quantity", _q);
                        }
                        else if(!_q1.equals("0")) {
                            map2.put("quantity", _q1);
                        }

                        map2.put("qtyScanned", temp);


                        Log.e(TAG, "listttttt   "+list);

                    }
                }
                if(!match)
                    list.add(map1);
            }}
        mStockList = list;
        Log.e(TAG, "mStockListttttt   "+mStockList);

    }
    public void closePopup(){
        PopupWindow pop = getPopupWindow();
        if (pop.isShowing()){
            pop.dismiss();
        }
    }

    public void setStockList(List<Map<String, String>> data)
    {
        mStockList = data;
    }

    @Override
    public void nextProcess() {
        Log.e(TAG, "nextProcessssssss");
        this._sts(id.location, "");
        this._sts(id.barcode, "");
        this._sts(id.productCode, "");
        this._sts(id.quantity, "");
        if (orderRequestSettings)
            this._sts(id.lotno, "");
        mTarget = null;
        mStockList = null;
        setBadge2(0);

        multicode = false;
        partNospinner.setAdapter(null);
        this.setProc(PROC_LOCATION);
        itemscanned = 0;
        GetLocationTally.listsize = 0;
        if (showKeyboard == false) {
            visible = false;
            numbrbtn.setText("キーボードを表示する");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
//			Animation bottomDown = AnimationUtils.loadAnimation(this,
//					R.anim.bottom_down);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
//			hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
    }

    protected boolean showInventory(){
        if (mStockList == null){
            Log.e(TAG,"showInventoryyyyyyyyyyyyyy");
            return false;
        }

        if (getPopupWindow() == null) {

            if(orderRequestSettings)
                lotprsent= "1";

            Log.e(TAG,"showInventory_getPopupWindow");
            final PopupWindow popupWindow = new PopupWindow(this);

            View popupView = getLayoutInflater().inflate(R.layout.work_inventories, null);


            popupView.findViewById(id.sendToServer).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG,"itemscanned "+itemscanned);
                    Log.e(TAG,"listsize "+GetLocationTally.listsize);
                    if (itemscanned >0 || GetLocationTally.listsize > 0)
                    {

                        if(orderRequestSettings)
                            lotprsent= "1";

                        new AlertDialog.Builder(InventoryActivity.this)
                                .setTitle("サーバへの送信")
                                .setMessage("サーバへデータを送信します。よろしいですか？")
                                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        stopTimer();

                                        Globals.getterList = new ArrayList<>();

                                        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());
                                        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                                        Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));
                                        Globals.getterList.add(new ParamsGetter("location", _gts(id.location)));
                                        Globals.getterList.add(new ParamsGetter("lot_flag", lotprsent));

                                        StringBuffer barcode = new StringBuffer();
                                        StringBuffer lot = new StringBuffer();
                                        StringBuffer quantity = new StringBuffer();
                                        for (Map<String, String> map : mStockList){

                                            barcode.append("\t").append(map.get("code"));
                                            lot.append("\t").append(map.get("lot"));
                                            quantity.append("\t").append(map.get("qtyScanned"));
                                        }
                                        if(barcode.substring(1) !=null) {

                                            Globals.getterList.add(new ParamsGetter("barcode", barcode.substring(1)));
                                            Globals.getterList.add(new ParamsGetter("stock_count", quantity.substring(1)));
                                            Globals.getterList.add(new ParamsGetter("lot_no", lot.substring(1)));
                                        }

                                        mRequestStatus = REQ_QTY;

                                        new MainAsyncTask(InventoryActivity.this, Globals.Webservice.trackInventory, 1, InventoryActivity.this, "Form", Globals.getterList,true).execute();



                                    }
                                })
                                .setNegativeButton("いいえ", null)
                                .show();

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"在庫がありませんので送信する必要はありません",Toast.LENGTH_SHORT).show();
                    }
                }
            });

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

            ImageView close =(ImageView)getPopupWindow().getContentView().findViewById(id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }
        ListView lv = (ListView) getPopupWindow().getContentView().findViewById(R.id.work_inventory);
        initWorkList(lv);

        // 画面中央に表示
        getPopupWindow().showAtLocation(findViewById(R.id.location), Gravity.CENTER, 0, 42);
        return false;
    }

    private ListViewItems initWorkList(ListView lv) {
        lv.setAdapter(null);

        ListViewItems data = new ListViewItems();
        for (int i = mStockList.size() - 1; i >= 0; i--) {
            Map<String, String> row = mStockList.get(i);

            if(!multicode)
                data.add(data.newItem().add(R.id.wrk_1, row.get("barcode"))
                        .add(R.id.wrk_2, row.get("quantity"))
                        .add(R.id.wrk_3, row.get("qtyScanned"))
                );
            else
                data.add(data.newItem().add(R.id.wrk_1, row.get("code"))
                        .add(R.id.wrk_2, row.get("quantity"))
                        .add(R.id.wrk_3, row.get("qtyScanned"))
                );
        }
        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.list_inventory_tally_row);
        lv.setAdapter(adapter);
        // 単一選択モードにする
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // デフォルト値をセットする
        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;

    }

    public void setDataProc(String code,String barcode){
        Boolean isNewItem = true;

        for (Map<String, String> map : mStockList) {
            Log.e(TAG, "InputedEvent_PROC_BARCODE_FORLOOP");
            if(multicode){
                if (map.get("code").equals(code)) {
                    _sts(id.productCode, map.get("code"));
                    mTarget = map;
                    isNewItem = false;
                }}
            else{
                if(map.get("barcode").equals(barcode) && map.get("code").equals(code)){
                    _sts(id.productCode, map.get("code"));
                    mTarget = map;
                    isNewItem = false;
                }
            }
        }
        setProc(PROC_QTY);
        if (!isNewItem) {
            Log.e(TAG, "InputedEvent_PROC_BARCODE_ELSE   1111");
            Log.e(TAG,"Quantity record "+mTarget.get("qtyScanned"));
            String qnty = U.plusTo(mTarget.get("qtyScanned"), "1");
            _sts(id.quantity, qnty);
            mTarget.put("qtyScanned", qnty);
            mTextToSpeak.startSpeaking(qnty);
            Log.e(TAG, "InputedEvent_PROC_BARCODE_ELSE   1111  "+mTarget);
            updateBadge1();
        }
        else{
            createNewEntry(code,barcode);
            updateBadge1();
        }


    }
    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        new AlertDialog.Builder(this)
                .setTitle("クリアしますか？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("InventoryTallyActivity","clearEventPositiveButton");
                        confirmClear();
                        dialog.cancel();
                    }
                })

                .setNegativeButton("いいえ", null)
                .show();
    }

    public void confirmClear()
    {
        new AlertDialog.Builder(this)
                .setTitle("本当にクリアしますか？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("InventoryTallyActivity","clearEventPositiveButton");
                        mTextToSpeak.startSpeaking("clear");
                        U.beepBigsound(InventoryActivity.this, null);
                        InventoryActivity.this.nextProcess();
                    }
                })

                .setNegativeButton("いいえ", null)
                .show();
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
            case PROC_LOCATION:	// バーコード
                Log.e(TAG,"keypressEvent_PROC_Location");
                _sts(id.location, buff);
                break;
            case PROC_BARCODE:	// バーコード
                Log.e(TAG,"keypressEvent_PROC_BARCODE");
                _sts(id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                Log.e(TAG,"keypressEvent_PROC_QTY");
                _sts(id.quantity, buff);
                break;
            case PROC_LOT_NO: // 数量
                Log.e(TAG,"keypressEvent_PROC_LOT");
                _sts(id.lotno, buff);
                break;

        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (CLEAR_BARCODE.equals(barcode)){
            Log.e("InventoryTallyActivity","scanedEvent_CLEAR_BARCODE");
            mBuff.delete(0, mBuff.length());
            clearEvent();
        }
        else if (ENTER_BARCODE.equals(barcode)){
            Log.e("InventoryTallyActivity","scanedEvent_ENTER_BARCODE");
            enterEvent();
        }
        else {
            Log.e("InventoryTallyActivity","scanedEvent_BARCODE_notEqual");
            if((!MainAsyncTask.dialogshow)) {
                if (!barcode.equals("")) {
                    if (mProcNo == PROC_BARCODE){
                        Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
                        String finalbarcode1 = CommonFunctions.getBracode(barcode);
                        barcode =finalbarcode1;

                        _sts(id.barcode, barcode);}

                    if (mProcNo == PROC_QTY){


                        Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
                        String finalbarcode1 = CommonFunctions.getBracode(barcode);
                        barcode =finalbarcode1;
                    }

                    if (mProcNo == PROC_LOCATION) _sts(id.location, barcode);
                    if (mProcNo == PROC_LOT_NO) _sts(id.lotno, barcode);}
                this.inputedEvent(barcode, true);
            }
            else
                Toast.makeText(getApplicationContext(),"Please Wait",Toast.LENGTH_SHORT).show();
        }
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
            case PROC_QTY:
                _sts(id.quantity, barcode);
                break;
            case PROC_LOCATION:
                _sts(id.location, barcode);
                break;
            case PROC_LOT_NO:
                _sts(id.lotno, barcode);
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
            if ("0".equals(code) == true)
            {
                Log.e("SendLogs111",code+"  "+msg+"  "+result1);

                if(mRequestStatus == REQ_BARCODE)
                {
                    new ValidateBarcode().post(code,msg,result1, mHash,InventoryActivity.this);
                }
                else if(mRequestStatus == REQ_LOT)
                {
                    new ValidateLotno().post(code,msg,result1, mHash,InventoryActivity.this);
                }
                else if(mRequestStatus == REQ_QTY)
                {
                    new TallyInventories().post(code,msg,result1,mHash,InventoryActivity.this);
                }
                else if(mRequestStatus ==REQ_LOCATION)
                {
                    new GetLocationTally().post(code,msg,result1,mHash,InventoryActivity.this);
                }
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(InventoryActivity.this,LoginActivity.class);
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
                new ValidateBarcode().valid(code,msg,result1, mHash,InventoryActivity.this);
            }
            else if(mRequestStatus == REQ_LOT)
            {
                new ValidateLotno().valid(code,msg,result1, mHash,InventoryActivity.this);
            }
            else if(mRequestStatus == REQ_QTY){
                new TallyInventories().valid(code,msg,result1,mHash,InventoryActivity.this);
            }
            else if(mRequestStatus ==REQ_LOCATION)
            {
                new GetLocationTally().valid(code,msg,result1,mHash,InventoryActivity.this);
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
