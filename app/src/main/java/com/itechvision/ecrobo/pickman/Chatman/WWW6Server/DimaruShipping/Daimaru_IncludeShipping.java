package com.itechvision.ecrobo.pickman.Chatman.WWW6Server.DimaruShipping;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.ShippingPackingBoxActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.ShippingSpecificationActivity;
 import com.itechvision.ecrobo.pickman.Models.DaimaruIncludeShipping.DaimaruGetPickCategory2;
import com.itechvision.ecrobo.pickman.Models.DaimaruIncludeShipping.DaimaruIncludeAddPacking;
import com.itechvision.ecrobo.pickman.Models.DaimaruIncludeShipping.DaimaruIncludeAddPrint;
import com.itechvision.ecrobo.pickman.Models.DaimaruIncludeShipping.DimaruFixedPickCategory2;
import com.itechvision.ecrobo.pickman.Models.DaimaruShipping.DaimaruShippingRequest;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.KoguchiShipCompany.NKoguchiShipData;
import com.itechvision.ecrobo.pickman.Models.NewShipping.Boxsize.SetBoxSizeResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.CheckBarcode.CheckBarcodeResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.FixOrder.FixOrderResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
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
import com.reginald.editspinner.EditSpinner;

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
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

public class Daimaru_IncludeShipping extends BaseActivity implements View.OnClickListener,DataManager.DaimaruSetboxSizeCallback,DataManager.DaimaruCheckBarcodeCallback,DataManager.DaimaruFixOrderCallback,MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(id.actionbar) ActionBar actionbar;
    @BindView(id.gridMain) LinearLayout maingrid;

    String orderId ="",track_no = "";
    private ListView lv;
    public Map<String, String> mTarget = null;
    protected int mProcNo = 0;
    public static int inspectionNo = 0;
    public static final int PACKING_ACTIVITY = 111;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_QTY = 2;
    public static final int PROC_BOX = 3;
    public static final int PROC_KAGUCHI = 4;
    public static final int PROC_SHIP = 5;
    public static final int COMPLETE_INSPECT = 1;
    public static final int INCOMPLETE_INSPECT = 2;


    private ScrollView svMain;
    private Button numbrbtn;
    private boolean visible = false;
    protected RelativeLayout layout;
    protected RelativeLayout mainlayout;
    public Context mcontext = this;
    public boolean clear = false;
    private TextToSpeak mTextToSpeak;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public static int mRequestStatus =0;
    public static final int FIXED_REQ = 1;
    public static final int REQ_BARCODE = 2;
    public static final int REQ_ALL_PICK = 3;
    public static final int REQ_ADDPRINT =7;
    public static final int REQ_ADDPACKING = 8;
    public static final int REQ_SENDPRINTER =10;
    public static final int REQ_BOX =11;

    progresBar progress;
    ECRApplication app=new ECRApplication();
    String adminID="",ID = "";
    String box="";
    public String boxSize = "";
    private boolean is_scan = false;
    public String _lastUpdateQty = "0";
    public long ORDER_QTY_COUNT = 0;
    public boolean send = false;
    public boolean nextbox = false;
    protected Map<String, String> ProductList = null;
    protected List<Map<String, String>> mProductList = null;
    protected Map<String, String> mPackItem = new HashMap<String, String>();
    public String koguchi ="",shipping_company ="";
    private SweetAlertDialog dialog;
    String TAG = Daimaru_IncludeShipping.class.getSimpleName();
    protected List<Map<String, String>> mList = new ArrayList<Map<String,String>>();
    public boolean mNextBarcode = false;
    public String isNextBarcode ="";
    public boolean packing =  false,complete=  false;
    DataManager manager;
    DataManager.DaimaruCheckBarcodeCallback checkobarcode;
    DataManager.DaimaruFixOrderCallback fixOrder;
    DataManager.DaimaruSetboxSizeCallback setboxSize;


    @BindView(R.id.koguchi) EditSpinner koguchii;
    @BindView(R.id.changeshippingcmnpy) EditSpinner changeshippingcmnpy;
    @BindView(R.id.id_c_koguchi) Button id_c_koguchi;
    @BindView(R.id.id_c_company) Button id_c_company;
    @BindView(R.id.ll_shipkoguchi) LinearLayout ll_shipkoguchi;
    public String ChangedShipId="";
    private String koguchi_count = "";
    DataManager.OneToOneGetCompanycall getshipcompany;
    DataManager.DaimaruUpdateShipcall updateship;
    ArrayList<NKoguchiShipData> shipcompdata;


    @BindView(R.id.spinnerlayout1) RelativeLayout spinnerlayout1;
    @BindView(R.id.spinnershippinglayout) RelativeLayout spinnershippinglayout;
    int  eop = 0;
    String Kog = "";
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_include_shipping);
        ButterKnife.bind(Daimaru_IncludeShipping.this);
        getIDs();
        Log.d(TAG,"On Create ");

        ViewPager viewPager = (ViewPager) findViewById(id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        numbrbtn = (Button) _g(id.add_layout);
        mTextToSpeak = new TextToSpeak(this);
        svMain = (ScrollView) _g(id.scrollMain);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);

        ID = BaseActivity.getShopId();
        adminID = spDomain.getString("admin_id", null);

        checkobarcode = this;
        fixOrder =this;
        setboxSize = this;

        Intent i = getIntent();
        if (i.hasExtra("orderId")){
            orderId = i.getStringExtra("orderId");
            koguchi = i.getStringExtra("koguchi");
            shipping_company = i.getStringExtra("shipping_method");
            setBadge4(Daimaru_Shipping.packBoxData.size());
        }

        if(BaseActivity.getincludeScreen()) {
            maingrid.setVisibility(View.VISIBLE);
        }
        else
            maingrid.setVisibility(View.GONE);
        mList = Daimaru_Shipping.category1List;
        Log.e(TAG, "category1List     "+Daimaru_Shipping.category1List);
        //selectAll = (CheckBox) this.findViewById(R.id.selectAllLVItems);
        lv = ((ListView) this.findViewById(id.listinclude));
        updateBadge2(mList.size()+"");
        Log.e(TAG, "updateBadge2     "+mList);
        setlist();
        if(mProcNo == 0 && BaseActivity.getincludeScreen())
            setProc(PROC_BARCODE);
    }

    @Override
    public void nextProcess() {
        mTextToSpeak.startSpeaking("finish");

        startActivity(new Intent(this,Daimaru_Shipping.class));
        finish();
    }

    public void nextProcess1(){
        Intent intent=new Intent();
        intent.putExtra(FINISH, "finish");
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    //set title and icons on actionbar
    private void getIDs() {

        actionbarImplement(this, "同梱物", " ",
       0, false,true,false, false );

        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnYellow.setOnClickListener(this);

        progress = new progresBar(this);
        manager = new DataManager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case id.relLayout1:
//                menu.showMenu();
                break;
            case id.notif_count_red:
                showPopup2();
                break;
            case id.notif_count_yellow:
                Log.e(TAG,"packing List   "+ BaseActivity.getPackingList());
                if (BaseActivity.getPackingList())
                    startPackingBoxActivity();
                break;
            default:
                break;
        }
    }

    @OnClick (id.allpick)void allpick(){
        dialog=  new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText("Pick All?");
        dialog.setConfirmText("Yes").setCancelText("No")
                .showCancelButton(false);
        dialog .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                createAllPackset();
                Globals.getterList = new ArrayList<>();

                adminID = spDomain.getString("admin_id", null);

                Log.e(TAG, "shopid  " + BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("submit", "all"));
                Globals.getterList.add(new ParamsGetter("order_id", orderId));
                Globals.getterList.add(new ParamsGetter("category", "1"));
                if(BaseActivity.getShippingflag())
                    Globals.getterList.add(new ParamsGetter("shipping_flag","true"));
                //if boxsize selected the update the packed and shipped status after box size is send
                if(BaseActivity.getBoxNo()){
                    Globals.getterList.add(new ParamsGetter("box_flag","true"));
                }
                else   Globals.getterList.add(new ParamsGetter("box_flag",""));

                mRequestStatus = REQ_ALL_PICK;
                dialog.dismiss();
                new MainAsyncTask(Daimaru_IncludeShipping.this, Globals.Webservice.daimaruFixedshipping, 1, Daimaru_IncludeShipping.this, "Form", Globals.getterList, true).execute();

            }
        });

        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
                 dialog.dismiss();
         }
        });
        dialog.show();

    }

    @OnClick (id.enter)void enter(){
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
    }

    @OnClick (id.back)void back(){
        Intent intent = new Intent();
        intent.putExtra(NEXTBOX, ADDNEXTBOX);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }


    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
        if (visible == false) {

            visible = true;
            numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            layout = (RelativeLayout) findViewById(id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layout.getLayoutParams();
            param.setMargins(0, top, 0, 0);

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);
            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            mainlayout.setLayoutParams(params);
        }
    }

    public int convert(int x) {
        Resources r = mcontext.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, r.getDisplayMetrics());
        return px;
    }


    void setlist(){
        Log.e(TAG,"setlist   "+mList);
        lv.setAdapter(null);
        if(mList.size() > 0) {
            ListViewItems data = new ListViewItems();
            for (Map<String, String> map : mList){
                data.add(data.newItem()
                .add(id.pck_0, map.get("barcode"))
                .add(id.pck_1, map.get("product_name"))
                .add(id.pck_2, map.get("quantity"))
                .add(id.pck_3, map.get("code"))
                .add(id.pck_4, map.get("location")));
            }
            if (data.getData().size() > 0) {
                ListViewAdapter adapter = new  ListViewAdapter (this.getApplicationContext(), data
                        , R.layout.list_packing_row);
                lv.setAdapter(adapter);
            }
        }
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {

            case PROC_BARCODE:
                Log.e(TAG, "setProc_BARCODE");
                isNextBarcode ="";
                mNextBarcode=false;
                is_scan = false;
                _gt(id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(id.box_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(id.barcode).setFocusableInTouchMode(true);

                mTextToSpeak.startSpeaking("barcode");
                scrollToView(svMain, _g(id.barcode));
                break;
            case PROC_QTY:
                Log.e(TAG, "setProc_QNTY");
                _gt(id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(id.box_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                if (_gts(id.quantity).equals("")) _sts(id.quantity, "1");
                _gt(id.quantity).setFocusableInTouchMode(true);

                scrollToView(svMain, _g(id.quantity));
                break;

            case PROC_BOX:
                lv.setAdapter(null);
                mTextToSpeak.startSpeaking("box size");
                Log.e(TAG, "setProc_BOX");
                _gt(id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(id.box_no).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(id.box_no).setFocusableInTouchMode(true);
                scrollToView(svMain, _g(id.box_no));
                break;


        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                String barcode = _gts(id.barcode);
                 Log.e(TAG, "inputedEvent_BARCODE");
                 if ("".equals(barcode)) {

                    U.beepError(this, "バーコードは必須です");
                    Log.e(TAG, "inputedEvent_BARCODE === null");
                    _gt(id.barcode).setFocusableInTouchMode(true);
                    _gt(id.barcode).requestFocus();
                    break;
                }
                String category="";
                boolean match = false;
                for(Map<String,String> map:mList ){

                    String _b = map.get("barcode");
                    String _c = map.get("code");
                    if(_b.equals(barcode)|| _c.equals(barcode)){
                        category= map.get("category");
                        match = true;
                        break;
                    }
                }
                if(!match){
                    U.beepError(this, "バーコードは必須です");
                    _gt(id.barcode).setFocusableInTouchMode(true);
                    break;
                }

                progress.Show();
                DaimaruShippingRequest req = new DaimaruShippingRequest(app.getSerial(), adminID, ID, orderId, barcode, category, "", "", "", "", "",   "", "", "", "", "", getResources().getString(R.string.version));
                manager.DaimaruCheckBarcode (req, checkobarcode);


           /*     Globals.getterList = new ArrayList<>();

                adminID = spDomain.getString("admin_id", null);

                Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));

                Globals.getterList.add(new ParamsGetter("category", category));
                Globals.getterList.add(new ParamsGetter("barcode", barcode));
                Globals.getterList.add(new ParamsGetter("order_id",orderId));
                mRequestStatus = REQ_BARCODE;
                new MainAsyncTask(this, Globals.Webservice.Getshipping, 1, IncludeShippingActivity.this, "Form", Globals.getterList, true).execute();
               */
                _lastUpdateQty = _gts(id.quantity);
                break;
            case PROC_QTY:
                String qty = _gts(id.quantity);
                String barcode1 = _gts(id.barcode);

                if (isScaned) {
                    Log.e(TAG, "inputedEvent_QNTYY Barcode Scanned");

                    if (buff.equals(barcode1)) {
                        Log.e(TAG, "inputedEvent_QNTYY Barcode match");
                        qty = U.plusTo(qty, "1");
                        String processedCnt = ProductList.get("processedCnt");
                        if (!processedCnt.equals(_gts(id.quantity)))
                            processedCnt = _gts(id.quantity);
                        ProductList.put("processedCnt", U.plusTo(processedCnt, "1"));
                        //increase qunatity in mpackdata
                        if (Integer.parseInt(ProductList.get("processedCnt")) <= Integer.parseInt(ProductList.get("quantity"))) {
                            String _loc1 = ProductList.get("location");

                            _sts(id.quantity, qty);
                            if (Integer.parseInt(qty) > 1)
                                mTextToSpeak.startSpeaking(qty);

                            _lastUpdateQty = _gts(id.quantity);

                            /* check if update in quantity need next action */
                            if (ProductList.get("processedCnt").equals(ProductList.get("quantity"))) {
                                Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty complete");

                                fixedRequest(COMPLETE_INSPECT);

                            }
                        }
                    } else {
                        Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty INcomplete");

                        fixedRequest( INCOMPLETE_INSPECT,buff);

                    }

                } else {
                    if ("".equals(qty)) {
                        Log.e(TAG, "inputedEvent_QNTYY empty ");
                        U.beepError(this, "数量は必須です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    }

                    String processedCnt = ProductList.get("processedCnt");
                    String qtyUpdate = U.minusTo(qty, _lastUpdateQty);
                    String maxQty_ = U.minusTo(ProductList.get("quantity"), processedCnt);

                    if (U.compareNumeric(_lastUpdateQty, qty) == -1) {
                        U.beepError(this, "Quantity entered should exceed " + _lastUpdateQty.toString());
                        _sts(id.quantity, "1");

                        ProductList.put("processedCnt", "1");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (U.compareNumeric(maxQty_, qtyUpdate) > 0) {

                        U.beepError(this, "数量が多すぎます");
                        _gt(id.quantity).setFocusableInTouchMode(true);
                        _sts(id.quantity, "1");

                        ProductList.put("processedCnt", "1");
                        break;
                    } else {
                        if (Integer.parseInt(qty) > 1)
                            mTextToSpeak.startSpeaking(_gts(id.quantity));
                        ProductList.put("processedCnt", U.plusTo(processedCnt, qtyUpdate));
                        if (ProductList.get("processedCnt").equals(ProductList.get("quantity"))) {

                            //increase qunatity in mpackdata

                            fixedRequest(COMPLETE_INSPECT);


                        } else {
                            Log.e(TAG, "inputedEvent_QNTYY INcomplete inspect");
                            //increase qunatity in mpackdata

                            fixedRequest(INCOMPLETE_INSPECT);

                        }
//						mPackItem.put("quantity", (mPackItem.get("quantity")));
                    }

                }
                break;
            case PROC_BOX:
                String box = _gts(id.box_no);


//                if (isScaned) {
                if ("".equals(box)) {
                    Log.e(TAG, "inputedEvent_QNTYY empty ");
                    U.beepError(this, "数量は必須です");
                    _gt(id.box_no).setFocusableInTouchMode(true);

                    break;
                }

                if(boxSize.equals(""))
                    boxSize = box;
                else boxSize = boxSize+","+box;

                _sts(id.box_no,boxSize);

                Log.e(TAG, "inputedEvent_QNTYY Barcode Scanned");

                String processedCnt = mTarget.get("Koguchi_processed_count");
                Log.e(TAG,"proceesed  "+ processedCnt);
                Log.e(TAG,"Koguchi " + koguchi);

                mTarget.put("Koguchi_processed_count", U.plusTo(processedCnt, "1"));
                //increase qunatity in mpackdata
                if (Integer.parseInt(mTarget.get("Koguchi_processed_count")) <= Integer.parseInt(koguchi)) {

                    if (Integer.parseInt(mTarget.get("Koguchi_processed_count")) > 1)
                        mTextToSpeak.startSpeaking(mTarget.get("Koguchi_processed_count"));

                    /* check if update in quantity need next action */
                    if (mTarget.get("Koguchi_processed_count").equals(koguchi)) {
                        Log.e(TAG, "inputedEvent_Boxxxxx complete");
                        sendBoxSize();
                    }
                }

                break;
        }
    }

    void sendBoxSize(){

        String airprint_printer = "";
        String selectedPrinter = "";
        String shipflag = "";
        String checkdb ="0";


        if (BaseActivity.getPrinterSelected() && !checkPrinterSelect()) {
            airprint_printer = BaseActivity.getintegratedselectedPrinterID();
            selectedPrinter = BaseActivity.getCsvselectedPrinterID();
        }
        else checkdb = "1";

        if (BaseActivity.getShippingflag())
            shipflag = "true";

        progress.Show();


        DaimaruShippingRequest req = new DaimaruShippingRequest(app.getSerial(), adminID, ID, orderId, "", "",
                "", "","", "", "",  "","",shipflag,"true",boxSize,"true",checkdb,airprint_printer,selectedPrinter , getResources().getString(R.string.version));

        manager.DaimaruSetboxSize(req, setboxSize);

   /*     Globals.getterList = new ArrayList<>();

        Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("update_box", "true"));
        Globals.getterList.add(new ParamsGetter("boxsize", boxSize));
        Globals.getterList.add(new ParamsGetter("order_id", orderId));
        Globals.getterList.add(new ParamsGetter("update_status","true"));
        if(BaseActivity.getShippingflag())
            Globals.getterList.add(new ParamsGetter("shipping_flag","true"));


        mRequestStatus = REQ_BOX;
        new MainAsyncTask(this, Globals.Webservice.Fixedshipping, 1, IncludeShippingActivity.this, "Form", Globals.getterList,true).execute();
*/
    }

    public void call(){
        Log.e(TAG, "DataRemoved>>>>>>>> " + mTarget);
        removeData(1);
    }
    protected boolean startPackingBoxActivity() {
        Log.e(TAG, " startPackingBoxActivity");
        Log.e(TAG, "PackData  " + Daimaru_Shipping.packBoxData);

        if (BaseActivity.getPackingList()== true) {
            if (mProcNo == PROC_QTY) {
                if (is_scan == false)
                    createNewPackItem();
                fixedRequest(INCOMPLETE_INSPECT);
            }
        }
        if (Daimaru_Shipping.packBoxData.size() == 0) {
            return true;
        }
        if (BaseActivity.getPackingList() == true) {
            Log.e(TAG, " startPackingBoxActivityyy13");
            Intent packingIntent = new Intent(this, ShippingPackingBoxActivity.class);
            packingIntent.putExtra("orderId", orderId);

            packingIntent.putExtra("orderQtySize", ORDER_QTY_COUNT);
            packingIntent.putExtra("packedQtySize", getBadge4());
            Log.e(TAG,"showPack");
            startActivityForResult(packingIntent, PACKING_ACTIVITY);
            return false;
        } else if(BaseActivity.getPackingList()==false){
            return true;}
        return true;
    }
    public void removeData(int status)
    {
        for (Map<String, String> map : mList) {
            String _b = mTarget.get("barcode");
            String _l = mTarget.get("location");
            String _b1 = map.get("barcode");
            String _l1 = map.get("location");
            if ((_b.equals(_b1.trim()) || _b1.matches("(.*)" + _b)) || (_b.equals(map.get("code").trim()))) {
                if(_l.equals(_l1)){
                    Log.e(TAG, "DataAssigned " +_b +"    "+_b1  );
                    if(status == COMPLETE_INSPECT){
                        mList.remove(map);
                        Log.e(TAG, "DataRemoved>>>>>>>> " +map );
                        break;
                    }
                    else if(status == INCOMPLETE_INSPECT){
                        String qnty = U.minusTo(mTarget.get("quantity"),mTarget.get("processedCnt"));
                        Log.e(TAG, "Quantitiesssss  " +mTarget.get("quantity") +"    "+mTarget.get("processedCnt")  +"<<<<<<<<<<<"+ qnty);

                        map.put("quantity", qnty);
                        Log.e(TAG, "map  quantity " + map);
                        break;
                    }
                }
                Log.e(TAG, " removePackItem");
            }
        }
        Log.e(TAG, "category1List    "+mList);

        if(mList.size()== 0 ){
            goback();
        }
        updateBadge2(mList.size()+"");
        setlist();
    }

    public void goback() {
        Intent intent=new Intent();
        intent.putExtra(FINISH, "finish");
        setResult(Activity.RESULT_OK, intent);
        finish();
     }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    void fixedRequest(int status) {
         sendRequest(status);
    }

    private void fixedRequest(int status, String nextBarcode) {

        mNextBarcode = true;
        isNextBarcode =nextBarcode;
        sendRequest(status);

    }
    public void sendRequest(int status) {
        Log.e(TAG,"sendRequest  "+is_scan);
        if (is_scan == false && !_gts(id.barcode).equals(""))
            createNewPackItem();

        mTarget= null;
        inspectionNo = status;
        mTarget = ProductList;

        updateBadge2(mList.size()+"");

        String barc = "";
        if (ProductList.get("barcode").equals(""))
            barc = ProductList.get("code");
        else
            barc = ProductList.get("barcode");

        String qty = ProductList.get("processedCnt");
        String shipping_flag = "";
        String box_flag = "";

        if (BaseActivity.getShippingflag())
            shipping_flag = "true";


        //if boxsize selected the update the packed and shipped status after box size is send
        if (BaseActivity.getBoxNo())
            box_flag = "true";


        String airprint_printer = "";
        String selectedPrinter = "";
        String auto_complete = "";

        if (BaseActivity.getPrinterSelected()) {
            airprint_printer = BaseActivity.getintegratedselectedPrinterID();
            selectedPrinter = BaseActivity.getCsvselectedPrinterID();
        }

        if (BaseActivity.getsinclude())
            auto_complete = "";
        else
            auto_complete = "true";


        progress.Show();
        DaimaruShippingRequest req = new DaimaruShippingRequest(app.getSerial(), adminID, ID, orderId, barc, ProductList.get("category"), "","", ProductList.get("product_stock_history_id"), qty, timeTaken().toString(), auto_complete,box_flag,shipping_flag, airprint_printer, selectedPrinter, getResources().getString(R.string.version));
        manager.DaimaruFixOrder(req, fixOrder);



       /* Globals.getterList = new ArrayList<>();
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));


        Globals.getterList.add(new ParamsGetter("order_id", orderId));

        Globals.getterList.add(new ParamsGetter("product_stock_history_id", ProductList.get("product_stock_history_id")));
        Globals.getterList.add(new ParamsGetter("category",  ProductList.get("category")));
        if(ProductList.get("barcode").equals(""))
            Globals.getterList.add(new ParamsGetter("barcode", ProductList.get("code")));
        else
            Globals.getterList.add(new ParamsGetter("barcode", ProductList.get("barcode")));

        Globals.getterList.add(new ParamsGetter("num", ProductList.get("processedCnt")));
        Globals.getterList.add(new ParamsGetter("inspection_status",status+""));

        if(BaseActivity.getShippingflag())
        Globals.getterList.add(new ParamsGetter("shipping_flag","true"));

        //if boxsize selected the update the packed and shipped status after box size is send
        if(BaseActivity.getBoxNo()){
            Globals.getterList.add(new ParamsGetter("box_flag","true"));
        }
        else   Globals.getterList.add(new ParamsGetter("box_flag",""));

        mRequestStatus =FIXED_REQ;

        new MainAsyncTask(this, Globals.Webservice.Fixedshipping, 1, IncludeShippingActivity.this, "Form", Globals.getterList,true).execute();
*/
//      new ECZaikoClient(this).setListner(new FixedPikingNS()).fixed_shipping(ProductList, status, "false");


    }

    public void createNewPackItem() {
        boolean repeat = false;
        boolean repeatpackBox = false;

        mTarget = ProductList;

        Log.e(TAG, " createNewPackItem ");
        Log.e(TAG, " createNewPackItem Target " + mTarget);
        int target = Integer.parseInt(_gts(id.quantity));
        int productqnt = Integer.parseInt( ProductList.get("quantity"));
        if (target <= productqnt) {
            if (Daimaru_Shipping.packData.size() > 0) {
                String _b1 = mTarget.get("barcode");
                String _box1 = String.valueOf(Daimaru_Shipping.mBoxNo);
                String _loc1 = mTarget.get("location");

                for (Map<String, String> map : Daimaru_Shipping.packData) {
                    String _b = map.get("barcode");
                    String _box = map.get("boxNo");
                    String _loc = map.get("location");

                    StringBuffer temp_qty = new StringBuffer();

                    if (_b.equals(_b1) && _loc.equals(_loc1) && _box.equals(_box1)) {

                        String qnty = U.plusTo(map.get("quantity"), _gts(id.quantity));
                        map.put("quantity", qnty);
                        repeat = true;

                    }
                }

                // for packing Box Activity List

                for (Map<String, String> map : Daimaru_Shipping.packBoxData) {
                    String _b = map.get("barcode");
                    Log.e(TAG, "pack data  barcode " + _b);
                    StringBuffer temp_qty = new StringBuffer();

                    if (_b.equals(_b1)) {

                        String qnty = U.plusTo(map.get("quantity"), _gts(id.quantity));
                        Log.e(TAG, "map  quantity " + map.get("quantity"));
                        map.put("quantity", qnty);
                        repeatpackBox = true;

                    }
                }
            }
        }

        if (repeat == false) {

            mPackItem = new HashMap<String, String>();
            mPackItem.put("boxNo", String.valueOf(Daimaru_Shipping.mBoxNo));
            mPackItem.put("code", mTarget.get("code"));
            mPackItem.put("barcode", mTarget.get("barcode"));
            mPackItem.put("location", mTarget.get("location"));
            mPackItem.put("quantity", _gts(id.quantity).toString());
            mPackItem.put("order_sub_id", mTarget.get("order_sub_id"));
            Daimaru_Shipping.packData.add(mPackItem);

            Log.e(TAG, " mpackItemm " + Daimaru_Shipping.packData);
        }
        if(repeatpackBox == false){
            mPackItem = new HashMap<String, String>();
            mPackItem.put("boxNo", String.valueOf(Daimaru_Shipping.mBoxNo));
            mPackItem.put("code", mTarget.get("code"));
            mPackItem.put("barcode", mTarget.get("barcode"));
            mPackItem.put("quantity", _gts(id.quantity).toString());
            Daimaru_Shipping.packBoxData.add(mPackItem);

        }

        is_scan = true;
        updatePackBadge();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("ShipActivity", " onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == PACKING_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.hasExtra(FINISH)) {
                    Log.d("CALLBACK", FINISH);
                    send = true;
//                    fixedRequest();
                } else if (data.hasExtra(NEXTBOX)) {
                    if (data.getStringExtra(NEXTBOX).equals(ADDNEXTBOX)) {
                        nextbox = true;
                        printRequest();
                        // Command to add next box
                        if (mProcNo == PROC_QTY) {

                            updatePackBadge();
                            _sts(id.barcode, "");

                            _sts(id.location, "");
                            _sts(id.quantity, "");
                            _sts(id.productCode, "");
                            _sts(id.productQuantity, "");
                            setProc(PROC_BARCODE);

                        }
                    }
                    Log.d("CALLBACK", "Next box");
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("PACKING_RESPONSE", "Canceled");
            }
        }
    }
    public void updatePackBadge() {
        Log.e(TAG, " UpdatePackBadge ");
        int qtyBadge = 0;

        if (Daimaru_Shipping.packBoxData != null)
            setBadge4(Daimaru_Shipping.packBoxData.size());
        else
            setBadge4(0);
    }
    public void updateBadge2(String qtyCount) {
        Log.e(TAG, "updateBadge2  " + qtyCount);
        setBadge2(Integer.valueOf(qtyCount));

    }
    public void currLineData(Map<String, String> data) {
        Log.e(TAG, "currLineData");
        ProductList = data;
        Log.e(TAG, "currLineData  " + ProductList);
    }
    @Override
    public void inputedEvent(String buff) {
        Log.e(TAG, "inputedEvent");
        inputedEvent(buff, false);
    }

    public void printRequest() {
        if (nextbox == true) {
            if (BaseActivity.getPackingList() == true) {
                sendPrintRequest();
                Log.e(TAG, "sending Print request1");
            } else {
              //  sendFinalRequest();
                Log.e(TAG, "sending NOOOO Print request1");
            }
        } else {

            String Url = spDomain.getString("domain", null);
            if(Url.equals("https://air-logi-st.air-logi.com/service")|| Url.equals("https://api.air-logi.com/service") && (BaseActivity.getShopId().equals("1101") ||BaseActivity.getShopId().equals("1217")) ){
                if(BaseActivity.getPackingList() == true)
                    sendPrintRequest();
                else
                   // sendFinalRequest();
                Log.e(TAG, "sending Print request1");
            }
            else {
                if(BaseActivity.getPackingList() == true)
                    sendPrintRequest();
                else
                   // sendFinalRequest();
                Log.e(TAG, "sending NO Print request1");
            }
        }
    }
    public void createAllPackset() {

        for (Map<String, String> map : mList) {
            boolean repeat = false;
            boolean repeatPackBox = false;
            mTarget = map;
            Log.e(TAG, "Target  " + mTarget);

            Log.e(TAG, " createAllPackset ");
            Log.e(TAG, " createAllPackset Target " + mTarget);

            if (Daimaru_Shipping.packData.size() > 0) {
                String _b1 = mTarget.get("barcode");
                String _box1 = String.valueOf(Daimaru_Shipping.mBoxNo);
                String _loc1 = mTarget.get("location");
                Log.e(TAG, "mtarget  barcode " + _b1 + " box " + _box1 + " loc " + _loc1);
                for (Map<String, String> map1 : Daimaru_Shipping.packData) {
                    String _b = map1.get("barcode");
                    String _box = map1.get("boxNo");
                    String _loc = map1.get("location");
                    Log.e(TAG, "pack data  barcode " + _b + " box " + _box + " loc " + _loc);
                    StringBuffer temp_qty = new StringBuffer();

                    if (_b.equals(_b1) && _loc.equals(_loc1) && _box.equals(_box1)) {

                        String qnty = U.plusTo(map1.get("quantity"),mTarget.get("quantity"));
                        Log.e(TAG, "map  quantity " + map1.get("quantity"));
                        map1.put("quantity", qnty);
                        repeat = true;

                    }
                }
                for (Map<String, String> map1 : Daimaru_Shipping.packBoxData) {
                    String _b = map1.get("barcode");
                    String _box = map1.get("boxNo");
                    Log.e(TAG, "pack data  barcode " + _b + " box " + _box );
                    StringBuffer temp_qty = new StringBuffer();

                    if (_b.equals(_b1) ) {

                        String qnty = U.plusTo(map1.get("quantity"),mTarget.get("quantity"));
                        Log.e(TAG, "map  quantity " + map1.get("quantity"));
                        map1.put("quantity", qnty);
                        repeatPackBox = true;

                    }
                }


            }
            if (repeat == false) {
                mPackItem = new HashMap<String, String>();
                mPackItem.put("boxNo", String.valueOf(Daimaru_Shipping.mBoxNo));
                mPackItem.put("code", mTarget.get("code"));
                mPackItem.put("barcode", mTarget.get("barcode"));
                mPackItem.put("location", mTarget.get("location"));
                mPackItem.put("quantity",mTarget.get("quantity"));
                mPackItem.put("order_sub_id", mTarget.get("order_sub_id"));
                Daimaru_Shipping.packData.add(mPackItem);
            }
            if (!repeatPackBox ){
                mPackItem = new HashMap<String, String>();
                mPackItem.put("boxNo", String.valueOf(Daimaru_Shipping.mBoxNo));
                mPackItem.put("code", mTarget.get("code"));
                mPackItem.put("barcode", mTarget.get("barcode"));
                mPackItem.put("quantity",mTarget.get("quantity"));
                Daimaru_Shipping.packBoxData.add(mPackItem);
            }
            Log.e(TAG, " mpackItemm " + Daimaru_Shipping.packData);
            Log.e(TAG, " mpackItemm Pack boxxxxxx" + Daimaru_Shipping.packBoxData);


        }

    }

    public String getPrintableBox() {
        String boxes = "";
        Log.e(TAG, " getPrintableBoxx ");
        if (Daimaru_Shipping.packData.size() > 0)
            for (Map<String, String> map : Daimaru_Shipping.packData) {
                boxes += "," + map.get("boxNo");
            }
        if (boxes.length() > 0)
            boxes = boxes.substring(1);
        return boxes;
    }
    public void sendPrintRequest() {
        String printBoxNo = getPrintableBox();
        Log.e(TAG, " SendPrintRequestttt ");
        for (Map<String, String> map : Daimaru_Shipping.packData) {
            box = map.get("boxNo");
        }

        String order = orderId;

        Globals.getterList = new ArrayList<>();


        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("order_id", order));
        Globals.getterList.add(new ParamsGetter("single_box", "true"));
        Globals.getterList.add(new ParamsGetter("getTrack","false"));

        mRequestStatus = REQ_ADDPRINT;
        new MainAsyncTask(this, Globals.Webservice.daimaruaddPrint, 1, Daimaru_IncludeShipping.this, "Form", Globals.getterList,true).execute();


//        new ECZaikoClient(this).setListner(new NewPickingAddPrint()).addPrintNext(order, box, "order_id", "true","false");

    }
    public void sendFinalRequest() {
        Log.e(TAG, " sendFinalRequesttttt");


        Log.e(TAG, " sendFinalRequesttttt  Order id " + orderId);

        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Log.e(TAG, " sendFinalRequesttttt  admin_id " + adminID);
        Globals.getterList.add(new ParamsGetter("order_id", orderId));
        Log.e(TAG, " sendFinalRequesttttt  order_id " + orderId);
        Globals.getterList.add(new ParamsGetter("transaction_id", ""));
        Log.e(TAG, " sendFinalRequesttttt  getTrack ");
        Globals.getterList.add(new ParamsGetter("getTrack","false"));
        Log.e(TAG, " sendFinalRequesttttt  getTrack 1111");
        Globals.getterList.add(new ParamsGetter("single_box","true"));
        Log.e(TAG, " sendFinalRequesttttt  getTrack ");
        Globals.getterList.add(new ParamsGetter("box_no",Daimaru_Shipping.mBoxNo+""));

        StringBuffer orderSubId = new StringBuffer();
        StringBuffer qty = new StringBuffer();
        StringBuffer barcode =new StringBuffer();
        Log.e(TAG, " sendFinalRequesttttt  packData "+Daimaru_Shipping.packData);
        for (Map<String, String> map : Daimaru_Shipping.packData){
            if (!map.get("quantity").equals("0")) {
                orderSubId.append("\t").append(map.get("order_sub_id"));
                qty.append("\t").append(map.get("quantity"));
            }
        }
        Log.e(TAG, " sendFinalRequesttttt  order_sub_id "+orderSubId.substring(1));
        Globals.getterList.add(new ParamsGetter("order_sub_id", orderSubId.substring(1)));
        Log.e(TAG, " sendFinalRequesttttt  order_sub_id "+qty.substring(1));
        Globals.getterList.add(new ParamsGetter("qty", qty.substring(1)));
        Globals.getterList.add(new ParamsGetter( "group_by_barcode" ,"true"));

        mRequestStatus = REQ_ADDPACKING;
        new MainAsyncTask(this, Globals.Webservice.addPacking, 1, Daimaru_IncludeShipping.this, "Form", Globals.getterList,true).execute();


    }


    @Override
    public void clearEvent() {
        if(BaseActivity.getPackingList() && Daimaru_Shipping.packData.size()>0)
        {
            new AlertDialog.Builder(this)
                    .setTitle("現在のBoxをパッキングリスト作成 ？")
                    .setPositiveButton("する", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clear = true;
                            printRequest();
                        }
                    })
                    .setNegativeButton("しない", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO cancel clear
                            mTextToSpeak.startSpeaking("clear");
                            nextProcess();
                        }
                    })
                    .show();
        }
    }


    public void clearcall()
    {
        mTextToSpeak.startSpeaking("clear");
        startActivity(new Intent(this,Daimaru_Shipping.class));
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
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {

            case PROC_BARCODE:    // バーコード
                _sts(id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                _sts(id.quantity, buff);
                break;
            case PROC_BOX: // 数量
                _sts(id.box_no, buff);
                break;
        }
    }

    public void sendSelectedPrinter(){
        Log.e(TAG,"sendSelectedPrinter");
        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("order_id",orderId));


        if(!checkPrinterSelect()) {
            Globals.getterList.add(new ParamsGetter("airprint_printer", BaseActivity.getintegratedselectedPrinterID()));
            Globals.getterList.add(new ParamsGetter("csv_printer_id", BaseActivity.getCsvselectedPrinterID()));
        }
        else
            Globals.getterList.add(new ParamsGetter("ap_printer_db","1"));
        mRequestStatus = REQ_SENDPRINTER;


        new MainAsyncTask(this, Globals.Webservice.sendPrinterRequest, 1, Daimaru_IncludeShipping.this, "Form", Globals.getterList, true).execute();

    }

    @Override
    public void scanedEvent(String barcode) {


        if (CLEAR_BARCODE.equals(barcode)) {
            mBuff.delete(0, mBuff.length());
            clearEvent();
        } else if (ENTER_BARCODE.equals(barcode)) {
            enterEvent();
        } else {
            if (!MainAsyncTask.dialogBox.isShowing()) {
                if (mProcNo == PROC_BARCODE) {

                    Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                    String finalbarcode = CommonFunctions.getBracode(barcode);
                    barcode =finalbarcode;
                    _sts(id.barcode, barcode);
                }

                if (mProcNo == PROC_QTY) {
                    Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                    String finalbarcode = CommonFunctions.getBracode(barcode);
                    barcode =finalbarcode;
                }
                else if (mProcNo == PROC_BOX) _sts(id.box_no, barcode);

                this.inputedEvent(barcode, true);

            } else
                CommonDialogs.customToast(this, "wait");
        }
    }

    public void resetPackData() {
        Log.e("ShipActivityyyyyy", " ResetPackData ");
        ORDER_QTY_COUNT = 0;
        Daimaru_Shipping.packData.clear();
        Daimaru_Shipping.packBoxData.clear();

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
                if ("".equals(barcode)) {
                } else
                    _sts(id.quantity, barcode);
                break;
            case PROC_BOX:    // バーコード
                _sts(id.box_no, barcode);
                break;

        }
    }
    public void setProductList(List<Map<String, String>> data) {
        Log.e("ShipActivity", " setProductList");
        mProductList = data;
        findRepeat();
    }
    private void findRepeat() {
        Log.e(TAG, " findRepeat");
        for (Map<String, String> map : mProductList) {
            String _b = map.get("barcode");
            String _l = null;

            StringBuffer temp_qty = new StringBuffer();
            for (Map<String, String> map1 : mProductList) {
                String _b1 = map1.get("barcode");
                String _l1 = null;

                _l1 = map1.get("location");
                if (_b.equals(_b1) && !_l.equals(_l1)) {
                    temp_qty.append(", ").append(map1.get("quantity"));
                    map.put("repeatQties", temp_qty.toString());
                }

            }
        }
    }

    public  void ToBoxsize() {
        Intent i = new Intent (this, ShippingSpecificationActivity.class);
        i.putExtra("order_id",orderId);
        i.putExtra("company",shipping_company.trim());
        i.putExtra("action","shipping");
        i.putExtra("batch_id", "");
        i.putExtra("slip_printer", "");
        startActivity(i);
    }

    public void nextWork() {

        String processedCnt = ProductList.get("processedCnt");
        ProductList.put("processedCnt", U.plusTo(processedCnt, "1"));

        setProc(PROC_QTY);
        mTextToSpeak.resetQueue();

        mTextToSpeak.startSpeaking(_gts(id.quantity));
        _lastUpdateQty = _gts(id.quantity);

        if (ProductList.get("processedCnt").equals(ProductList.get("quantity"))) {
            fixedRequest(COMPLETE_INSPECT);
         }
    }
    @Override
    protected void onDestroy() {
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();
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
            msg =map1.getStringOrNull("message");
            result1= map1.getJsonArrayOrNull("results");
            if (code == null) {
                Log.e(TAG,"CODEeee============Nulllll");
                CommonDialogs.customToast(getApplicationContext(),"Network error occured");

            }
            if ("0".equals(code) == true) {

                Log.e("SendLogs111",code+"  "+msg+"  "+result1);
                Log.e("SendLogs111",code+"  "+msg+"  "+mRequestStatus);

                if (mRequestStatus == REQ_BARCODE){
                    Log.e("SendLogs111",code+"  "+msg+"  >>>>>>>>>>>>>>>>>>>>>>>>>");
                    new DaimaruGetPickCategory2().post(code, msg, result1, mHash, Daimaru_IncludeShipping.this);

                }

                else if(mRequestStatus ==FIXED_REQ){
                    Log.e("SendLogs111",code+"  "+msg+"  >>>>>>>>>>>>>>>>>>>>>>>>>"+mRequestStatus);
                    new DimaruFixedPickCategory2().post(code,msg,result1, mHash, Daimaru_IncludeShipping.this);
                }
                else if(mRequestStatus == REQ_ADDPRINT){
                    new DaimaruIncludeAddPrint().post(code,msg,result1, mHash, Daimaru_IncludeShipping.this);
                }
                else if(mRequestStatus == REQ_ADDPACKING){
                    new DaimaruIncludeAddPacking().post(code,msg,result1, mHash, Daimaru_IncludeShipping.this);
                }
                else if(mRequestStatus == REQ_SENDPRINTER){
                    /*if(BaseActivity.getPackingList()== true){
                        complete = true;
                        if(!packing) {
                            printRequest();
                        }
                        packing = false;}
                    else*/
                    if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag())
                       // ToBoxsize();
                        nextProcess1();
                    else
                        nextProcess1();}
                else if(mRequestStatus == REQ_BOX){
                   /* if(BaseActivity.printerPress){
                        sendSelectedPrinter();
                    }
                    else {*/
                       /* if(BaseActivity.getPackingList()== true)
                        {
                            complete = true;
                            if(!packing) {
                                printRequest();
                            }
                            packing = false;}
                        else*/
                        if (BaseActivity.getBoxSelected() == true&& !BaseActivity.getShippingflag())
                            ToBoxsize();
                        else
                            nextProcess();
                   // }
                }
                else if(mRequestStatus ==REQ_ALL_PICK){
                    if (result1.size() > 0) {
                        // collect all data from response
                        Log.e(TAG,"allrowConttttt222222222222222222 ");
                        JsonHash row = (JsonHash) result1.get(0);
                        Log.e(TAG,"allrowConttttt11111111111112222222222222 ");

                        String not_inspected = row.getStringOrNull("not_inspection_row_count");
                        String short_inspected = row.getStringOrNull("shortage_row_count");
                        Log.e(TAG,"allrowConttttt654321123456 ");
                        if(not_inspected.equals(""))
                            not_inspected="0";
                        if(short_inspected.equals(""))
                            short_inspected="0";
                        Log.e(TAG,"allrowConttttt1212121221313 ");
                        String all_row_count = U.plusTo(not_inspected, short_inspected);
                        Daimaru_Shipping.includenotinspected = all_row_count;
                        Daimaru_Shipping.category1List.clear();

                        if(Integer.parseInt(Daimaru_Shipping.includenotinspected)==0){
                            if(BaseActivity.getBoxNo()){
                                boxSize = "";
                                _sts(id.barcode, "");
                                _sts(id.quantity, "");

                                mTarget.put("Koguchi_processed_count","0");
                                setProc(Daimaru_IncludeShipping.PROC_BOX);
                                Log.e("FixedPickingNS","11111111111133333333");
                            }
                            else {
                               /* if (BaseActivity.getPrinterSelected())
                                 //   sendSelectedPrinter();
                                else*/ {
                                   /* if (BaseActivity.getPackingList() == true) {
                                        if ((Integer.parseInt(not_inspected) == 0 && Integer.parseInt(short_inspected) != 0) || Integer.parseInt(all_row_count) == 0)
                                            complete = true;
                                        if (!packing) {
                                            printRequest();
                                        }
//                                    nextProcess1();

                                        packing = false;
                                    }*/
                                    /*else*/ if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag()) {
                                       // ToBoxsize();
                                        nextProcess1();
                                    }
                                    else

                                        nextProcess1();
                                }
                            }
                        }else{
                            goback();
                            if (inspectionNo == 1)
                                call();
                            else
                                removeData(inspectionNo);

                        }}
                }
            }else if(code.equalsIgnoreCase("1020")){
                new AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(Daimaru_IncludeShipping.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }else if(code.equals("10204")){
                U.beepError(this,msg);
            }
            else {

                if (mRequestStatus == REQ_BARCODE) {
                    new DaimaruGetPickCategory2().valid(code, msg, result1, mHash, Daimaru_IncludeShipping.this);
                }
                else if(mRequestStatus ==FIXED_REQ){
                    new DimaruFixedPickCategory2().valid(code,msg,result1, mHash, Daimaru_IncludeShipping.this);
                }
                else if(mRequestStatus == REQ_ADDPRINT){
                    new DaimaruIncludeAddPrint().valid(code,msg,result1, mHash, Daimaru_IncludeShipping.this);
                }
                else if(mRequestStatus == REQ_ADDPACKING){
                    new DaimaruIncludeAddPacking().valid(code,msg,result1, mHash, Daimaru_IncludeShipping.this);
                }
                else if (mRequestStatus == REQ_BOX){
                    setProc(PROC_BOX);
                    U.beepError(this,msg);
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

    @Override
    public void onSucess(int status, CheckBarcodeResponse message) throws JsonIOException {
        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("200")) {

            startTimer();
            Map<String, String> map =new HashMap<>();
            if (message.getData() != null && message.getData().size()>0) {
                map = message.getData().get(0);
                Log.e(TAG, ">>>>>>>>><<<<" + map);


                if (map != null && !map.isEmpty()) {

                    map.put("processedCnt", "0");
                    map.put("Koguchi_processed_count", "0");

                    _sts(id.quantity, "");


                    ProductList = map;
                    nextWork();


                } else {
                    _sts(id.quantity, "");
                    U.beepError(this, "No data found!");
                }
            }
            else {
                _sts(id.quantity, "");
                U.beepError(this, "No data found!");
            }
        } else {
            U.beepError(this,message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, FixOrderResponse message) throws JsonIOException {
        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("200")) {

            String all_row_count = "";
            Map<String, String> map = new HashMap<>();
            if(message.getData()!= null&&message.getData().size()!=0)
                map = message.getData().get(0);

            String not_inspected = message.getNot_inspection_row_count();
            String short_inspected = message.getShortage_row_count();

            all_row_count = U.plusTo(not_inspected, short_inspected);

            Daimaru_Shipping.includenotinspected = all_row_count;

            if (mNextBarcode) {
                _sts(id.barcode, "");

                _sts(id.barcode, isNextBarcode);
                setProc(PROC_BARCODE);
                inputedEvent(isNextBarcode, true);
                if(inspectionNo == 1)
                    call();
                else
                    removeData(inspectionNo);

            }

            else if ( map.isEmpty()){

                if(Integer.parseInt(Daimaru_Shipping.includenotinspected)==0){

                  //  VisibilityON Ship
                    nextProcess1();
/*
                    if(BaseActivity.getBoxNo()){
                        boxSize = "";
                        _sts(id.barcode, "");
                        _sts(id.quantity, "");

                        mTarget.put("Koguchi_processed_count","0");
                        setProc(PROC_BOX);

                    }
                    else {
                        if(BaseActivity.printerPress){
                            sendSelectedPrinter();
                        }
                        else {
                          *//*  if(BaseActivity.getPackingList()== true){
                                if( (Integer.parseInt(not_inspected)==0 && Integer.parseInt(short_inspected)!=0) || Integer.parseInt(all_row_count)==0)
                                    complete = true;
                                if(!packing)
                                    printRequest();

//                        act.nextProcess1();

                                packing = false;}*//*
                              if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag())
                                ToBoxsize();

                            else
                                nextProcess1();
                        }
                    }*/
                }else {
                    goback();

                    if (inspectionNo == 1)
                        call();
                    else
                        removeData(inspectionNo);
                }


            } else {
                 _sts(id.barcode, "");
                _sts(id.quantity, "");
                setProc(PROC_BARCODE);
                if(inspectionNo == 1)
                    call();
                else
                    removeData(inspectionNo);
                Daimaru_Shipping.includenotinspected = all_row_count;
                mNextBarcode=false;
                isNextBarcode = "";

            }
        }
    }

    @Override
    public void onSucess(int status, SetBoxSizeResponse message) throws JsonIOException {
        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("0")) {

           /* if (BaseActivity.getPrinterSelected()) {
                sendSelectedPrinter();
            }else if (BaseActivity.getPackingList() == true) {
                complete = true;

                if (!packing)
                    printRequest();
                    nextProcess1();
                    packing = false;
            } else*/ if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag()) {
                ToBoxsize();
             } else
                nextProcess1();

        } else {
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



}
