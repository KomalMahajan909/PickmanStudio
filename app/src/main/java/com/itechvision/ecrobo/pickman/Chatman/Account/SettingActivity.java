package com.itechvision.ecrobo.pickman.Chatman.Account;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.api.ListShopAll;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.Settings.SettingRequest;
import com.itechvision.ecrobo.pickman.Models.Settings.SettingResult;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.itechvision.ecrobo.pickman.Util.SharedPrefrences;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;
import java.util.ArrayList;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class SettingActivity extends BaseActivity implements View.OnClickListener, MainAsynListener, DataManager.Postsettingcall , DataManager.GetSettingscall {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    /*  @BindView(R.id.actionbar)ActionBar actionbar;*/
    @BindView(id.spinnershopID)Spinner shopname;
    @BindView(id.sIncludeRg) RadioGroup includeradiogroup;
    @BindView(id.sincludeyesRb) RadioButton includeradioyes;
    @BindView(id.sincludenoRb) RadioButton includeradiono;
    @BindView(id.scanProductRb) RadioGroup scanProductradiogroup;
    @BindView(id.scanProductyesRb) RadioButton scanProductradioyes;
    @BindView(id.scanProductnoRb) RadioButton scanProductradiono;
    @BindView(id.includeScreenRg) RadioGroup includeScreenradiogroup;
    @BindView(id.includeScreenyesRb) RadioButton includeScreenyesradio;
    @BindView(id.includeScreennoRb) RadioButton includeScreennoradio;
    @BindView(id.batchScreenRg) RadioGroup batchScreenradiogroup;
    @BindView(id.keyboardRg) RadioGroup keyboardradiogroup;
    @BindView(id.batchScreenyesRb) RadioButton batchScreenyesradio;
    @BindView(id.batchScreennoRb) RadioButton batchScreennoradio;
    @BindView(id.checklotno)CheckBox lotno;
    @BindView(id.checkshipping)CheckBox shippingflag;
    @BindView(id.directStockSpinner) Spinner rgdirect;
    @BindView(id.pickingRequest) Spinner pickingRequest;
    @BindView(id.checksupplierList)CheckBox supplier;
    @BindView(id.checkNosupplierList)CheckBox nosupplier;
    @BindView(id.checkremark)CheckBox remark;
    @BindView(id.checkpackingList)CheckBox packing;
    @BindView(id.checkprinter)CheckBox printer;
    @BindView(id.checkBoxSize) CheckBox checkBoxsize;
    @BindView(id.checkBoxNo) CheckBox checkBoxNo;
    @BindView(id.checkTrackingNo) CheckBox checkTrackingNo;
    @BindView(id.checkarrivalNyuka) CheckBox checkarrivalNyuka;
    @BindView(id.checkrepicking) CheckBox checkrepicking;
    @BindView(id.checkShippingorderno) CheckBox checkShippingorderno;
    @BindView(id.checkTshipping_flag)CheckBox checkTshipping_flag;
    @BindView(id.checkBoxChange)CheckBox checkBoxSizeChange;
    @BindView(id.checkTracking_date)CheckBox checkTracking_date;
    @BindView(id.save) Button save;
    @BindView(id.menubar) ImageView menubar;
    @BindView(id.keyboardLayout) LinearLayout keyboardLayout;
    @BindView(id.sIncludeLayout) LinearLayout sIncludeLayout;
    @BindView(id.scanProductLayout) LinearLayout scanProductLayout;
    @BindView(id.includeScreenLayout) LinearLayout includeScreenLayout;
    @BindView(id.batchScreenLayout) LinearLayout batchScreenLayout;
    @BindView(R.id.checkReship) CheckBox checkReship;

    public static String   version ="";
    SharedPreferences sharedPreferences;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public static final String MYPREFERENCE = "MyPrefs";
    public static final int ORDER_ID = 2;
    public static final int ORDER_NUMBER = 3;
    public static final int ORDER_TRACKING_NO = 4;
    ECRApplication app=new ECRApplication();
    String TAG = SettingActivity.class.getSimpleName();
    boolean settingsChanged  = false;
    boolean sincludecheck =  true;
    boolean includescreen = false;
    boolean batchscreen = false, track =false, stockchange= false, orderinfo_change = false;
    String stock[] = {"通常", "ダイレクト", "ダイレクト棚入れ"};
    String order[] = {"注文ID", "注文番号", "発送番号"};
    String selectedstock = "",selected_orderinfo ="";
    String Printer="", CheckTrackingNo="", Remark="", checkTshipping = "", CheckboxArrival="", Lotno="", CheckboxNO="", CheckboxSize="",ShippingFlag="",SupplierList="",secondPickingCheckbox = "", NoSupplierList="", PackingLists="", Keyboard="", IncludeScreenstatus="",BatchScreenstatus ="",ShopId="",adminid="",Sinclude="",scanProduct = "",Tas_Re_ship="" ,ShipOrderNumber="",trackingDateCheck = "",checkBoxChange = "", checkReshipment = "";
    DataManager manager ;
    progresBar progress ;
    DataManager.GetSettingscall getcall;
    protected ArrayList<String> changeList = new ArrayList<String>();
    boolean shippingInspection = true, sIncludeFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_home_layout);

        ButterKnife.bind(SettingActivity.this);
        getcall= this ;
        getIDs();

        Log.d(TAG,"On Create ");
        //to check if user is going to Picking screen after home screen
        BaseActivity.setToPickingScreen(false);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);

        adminid = spDomain.getString("admin_id", null);
        String url = spDomain.getString("domain",null);
        int urlpos = spDomain.getInt("domainPosition",0);
        String role = spDomain.getString("role",null);
        BaseActivity.setrole(role);

        BaseActivity.setdomainpos(urlpos);
        BaseActivity.setUrl(url);
        CommonFunctions.setAccessPoint(url);
        Log.e(TAG, "URL selected is set as " + url);
        Log.e(TAG, "Admin ID is " + adminid);



        shopname.setSelection(BaseActivity.getshoppos());
        orderinfo_change = false;
        stockchange = false;

        manager = new  DataManager();
        progress = new progresBar(this);

        if (CommonUtilities.getConnectivityStatus(this)){
            Globals.getterList = new ArrayList<>();
            Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
            Globals.getterList.add(new ParamsGetter("admin_id",adminid));
            //   Globals.getterList.add(new ParamsGetter("app_code",version));

            new MainAsyncTask(this, Globals.Webservice.ListShopAll, 1, SettingActivity.this, "Form", Globals.getterList).execute();

        }
        else {
            CommonUtilities.openInternetDialog(this);
        }

        ArrayAdapter adapter = new ArrayAdapter (this,R.layout.spinner_layout,order);
        Log.e("SettingsActivityyyy","Orderrrrrr  "+BaseActivity.getorderpos());

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        pickingRequest.setAdapter(adapter);
        pickingRequest.setSelection(SharedPrefrences.getorderPos(SettingActivity.this));
        pickingRequest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setorderpos(position);
                SharedPrefrences.setorderPos(SettingActivity.this,position);
                String selection = parent.getItemAtPosition(position).toString();
                Log.e("settingActivityyyy","Orderrrrr Selectionnn  "+ selection);
                setOrderInfoBySettings(selection);
                orderinfo_change =  true;
                selected_orderinfo = selection;
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                setOrderInfoBy(ORDER_ID);
                setorderpos(0);
                editor.putInt("OrderInfoBy",getOrderInfoBy());
                editor.commit();
            }
        });

        batchscreen=sharedPreferences.getBoolean("BatchScreen",BaseActivity.getbatchScreen());
        if(batchscreen == true)
            batchScreenyesradio.setChecked(true);
        else batchScreennoradio.setChecked(true);

        batchscreen=sharedPreferences.getBoolean("TrackCheck",BaseActivity.getbatchScreen());

        if(batchscreen == true)
            batchScreenyesradio.setChecked(true);
        else batchScreennoradio.setChecked(true);


        if(BaseActivity.getSupplierList() == true) {
            SupplierList ="";
            NoSupplierList ="";
            supplier.setChecked(true);
            nosupplier.setChecked(false);
        }
        else if(BaseActivity.getNoSupplierList() ==true) {
            NoSupplierList ="";
            SupplierList ="";
            supplier.setChecked(false);
            nosupplier.setChecked(true);
        } else {
            NoSupplierList ="";
            SupplierList ="";
            supplier.setChecked(false);
            nosupplier.setChecked(false);
        }

        // Get shop_id from shop list
        Intent i = getIntent();
        if (i != null) {
            String shop_id = i.getStringExtra("SHOPID");
            String shop_name = i.getStringExtra("SHOPNAME");
            String admin_id = i.getStringExtra("ADMINID");
            ShopId =shop_id;

            if (shop_id != null && shop_name != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("shopID", shop_id);
                editor.putString("shopName", shop_name);
                editor.commit();
            }
            if (!TextUtils.isEmpty(admin_id)) {
                ((ECRApplication) getApplication()).setAdminId(admin_id);
            }
            if (!TextUtils.isEmpty(shop_id)) {
                ((ECRApplication) getApplication()).setShopId(shop_id);
                ((ECRApplication) getApplication()).setShopName(shop_name);

            } else {
                shop_id = sharedPreferences.getString("shopID", "563");
                shop_name = sharedPreferences.getString("shopName", "SUGURKITCHEN（シュガーキッチン）");
                ((ECRApplication) getApplication()).setShopId(shop_id);
                ((ECRApplication) getApplication()).setShopName(shop_name);
            }
        }
        ArrayAdapter adapter1 = new ArrayAdapter (this,R.layout.spinner_layout,stock);

        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        rgdirect.setAdapter(adapter1);
        rgdirect.setSelection(SharedPrefrences.getdirectStockPos(SettingActivity.this
        ));
        rgdirect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setdirectStockPos(position);
                SharedPrefrences.setdirectStockPos(SettingActivity.this,position );
                String selection = parent.getItemAtPosition(position).toString();
                Setstock(selection);
                stockchange = true;
                selectedstock = selection;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                BaseActivity.setOptShelf(false);
                BaseActivity.setDirectToStock(false);
                setorderpos(0);
            }
        });

        if(BaseActivity.stockAdjust==true) {
            stockAdjust=false;
            showDialog("権限ありません。");
        }
    }

    @OnClick(id.menubar) void menubar(){
        if(settingsChanged){
            new AlertDialog.Builder(this)
                    .setTitle("設定が保存されていません。\n 移動しますか?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            menu.showMenu();
                        }
                    })
                    .setNegativeButton("キャンセル", null)
                    .show();
        }
        else{
            menu.showMenu();
        }
    }

    @OnClick(id.voiceSettings) void settings()
    {
        Intent i = new Intent(SettingActivity.this,SoundSettingsActivity.class);
        startActivity(i);
    }


    @OnClick(id.save) void Save(){
        createChangeList();
    }

    @OnClick(id.allSetting) void AllSetting()
    {

        sIncludeLayout.setVisibility(View.VISIBLE);
        scanProductLayout.setVisibility(View.VISIBLE);
        includeScreenLayout.setVisibility(View.VISIBLE);
        batchScreenLayout.setVisibility(View.VISIBLE);
        lotno.setVisibility(View.VISIBLE);

        checkShippingorderno.setVisibility(View.VISIBLE);
        checkrepicking.setVisibility(View.VISIBLE);
        checkarrivalNyuka.setVisibility(View.VISIBLE);
        checkTrackingNo.setVisibility(View.VISIBLE);
        checkBoxNo.setVisibility(View.VISIBLE);
        checkBoxsize.setVisibility(View.VISIBLE);
        packing.setVisibility(View.VISIBLE);
        printer.setVisibility(View.VISIBLE);

        remark.setVisibility(View.VISIBLE);
        nosupplier.setVisibility(View.VISIBLE);
        supplier.setVisibility(View.VISIBLE);
        shippingflag.setVisibility(View.VISIBLE);
        checkTshipping_flag.setVisibility(View.VISIBLE);
        checkTracking_date.setVisibility(View.VISIBLE);
        checkBoxSizeChange.setVisibility(View.VISIBLE);
        checkReship.setVisibility(View.VISIBLE);
    }


    @Override
    public void nextProcess() {

    }

    public void AddKeyboard(View view)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (keyboardradiogroup.getCheckedRadioButtonId())
        {
            case id.showkeyboardRb:
                this.setaddKeyboard(true);

                editor.putBoolean("ShowKeyboard", getaddKeyboard());
                Keyboard ="1";
                break;
            case id.hidekeyboardRb:

                this.setaddKeyboard(false);
                Keyboard ="0";
                editor.putBoolean("ShowKeyboard", getaddKeyboard());
        }
        settingsChanged = true;
        editor.commit();
    }
    public void Setstock(String opt) {
        switch (opt)
        {
            case "ダイレクト":
                this.setDirectToStock(true);
                this.setOptShelf(false);
                break;
            case "ダイレクト棚入れ":
                this.setOptShelf(true);
                this.setDirectToStock(false);
                break;
            case "通常":
                this.setOptShelf(false);
                this.setDirectToStock(false);
                break;
            default:
                this.setOptShelf(false);
                this.setDirectToStock(false);
        }
    }

    public void PrinterSelected( View view)
    {
        if(getPrinterSelected()==false) {
            setPrinterSelected(true);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("PrinterSeleced", getPrinterSelected());
            editor.commit();
            ((CheckBox) _g(id.checkprinter)).setChecked(true);
            Printer = "1";
        }
        else
        {
            Printer = "0";
            setPrinterSelected(false);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("PrinterSeleced", getPrinterSelected());
            editor.commit();
            ((CheckBox) _g(id.checkprinter)).setChecked(false);
        }
        settingsChanged = true;
    }

    private void setOrderInfoBySettings(String op) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (op) {
            case "注文ID":
                setOrderInfoBy(ORDER_ID);
                editor.putInt("OrderInfoBy",getOrderInfoBy());
                editor.commit();
                break;
            case "注文番号":
                setOrderInfoBy(ORDER_NUMBER);
                editor.putInt("OrderInfoBy",getOrderInfoBy());
                editor.commit();
                break;
            case "発送番号":
                setOrderInfoBy(ORDER_TRACKING_NO);
                editor.putInt("OrderInfoBy",getOrderInfoBy());
                editor.commit();
                break;
            default:
                setOrderInfoBy(ORDER_ID);
                editor.putInt("OrderInfoBy",getOrderInfoBy());
                editor.commit();
        }

    }

    @Override
    public void inputedEvent(String buff) {

    }

    @Override
    public void clearEvent() {

    }

    @Override
    public void allclearEvent() {

    }

    @Override
    public void skipEvent() {

    }

    @Override
    public void keypressEvent(String key, String buff) {

    }

    @Override
    public void scanedEvent(String barcode) {

    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (BaseActivity.getUrl().equals("https://www6.air-logi.com/service")){
            SlideMenu.shiptxt.setText("レジ検品DM");
            SlideMenu.slipprinttxt38.setText("1-1検品DM");
            SlideMenu.arrivaltxt5.setText("入荷検品DM");
            SlideMenu.llDaimaruSlipPrinter.setVisibility(View.VISIBLE);
            //slipPRinterll.setVisibility(View.VISIBLE);
        }else{
            SlideMenu.shiptxt.setText("レジ検品");
            SlideMenu.slipprinttxt38.setText("1-1検品");
            SlideMenu.arrivaltxt5.setText("入荷検品");
            SlideMenu.slipPRinterll.setVisibility(View.GONE);
            SlideMenu.llDaimaruSlipPrinter.setVisibility(View.GONE);
        }

        if (BaseActivity.getUrl().equalsIgnoreCase("https://api.air-logi.com/service") && BaseActivity.getShopId().equalsIgnoreCase("3366")||BaseActivity.getUrl().equalsIgnoreCase("https://staging.air-logi.com/service") && BaseActivity.getShopId().equalsIgnoreCase("3366") ){
            SlideMenu.loopshipping.setVisibility(View.VISIBLE);
        }else{
            SlideMenu.loopshipping.setVisibility(View.GONE);
        }

    }

    //set title and icons on actionbar
    private void getIDs() {
        //   CommonUtilities.imgRight.setOnClickListener(this);
        menu = CommonUtilities.setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        try {
            PackageInfo pInfo =  getPackageManager().getPackageInfo(getPackageName(), 0);
            version = String.valueOf( pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

         /*   case R.id.relLayout1:

                break;*/
            default:
                break;
        }
    }

    public void SInclude(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (includeradiogroup.getCheckedRadioButtonId()) {
            case id.sincludeyesRb:
                if(shippingInspection){
                    BaseActivity.setsinclude(true);
                    sIncludeFlag = true;
                    includeradioyes.setChecked(true);
                    editor.putBoolean("SInclude",  BaseActivity.getsinclude());
                    Sinclude = "1";}
                else {
                    includeradiono.setChecked(true);
                }
                break;
            case id.sincludenoRb:
                sIncludeFlag = false;
                includeScreennoradio.setChecked(true);
                BaseActivity.setsinclude(false);
                editor.putBoolean("SInclude", BaseActivity.getsinclude());
                Sinclude ="0";
                IncludeScreenstatus = "0";
                IncludeScreen(includeScreennoradio);
        }
        editor.commit();
        settingsChanged = true;
    }

    public void scanProduct(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (scanProductradiogroup.getCheckedRadioButtonId()) {
            case id.scanProductyesRb:
                shippingInspection = true;
                BaseActivity.setscanProduct(true);
                editor.putBoolean("scanProduct",  BaseActivity.getscanProduct());
                scanProduct = "0";
                break;
            case id.scanProductnoRb:
                shippingInspection = false;
                includeradiono.setChecked(true);
                includeScreennoradio.setChecked(true);
                BaseActivity.setscanProduct(false);
                editor.putBoolean("scanProduct", BaseActivity.getscanProduct());
                scanProduct ="1";
                Sinclude = "0";
                IncludeScreenstatus = "0";

                SInclude(includeradiono);
                IncludeScreen(includeScreennoradio);
                break;
        }
        editor.commit();
        settingsChanged = true;
    }

    public void LotnoClick( View view) {
        if(getLotPress()==false) {
            setLotPress(true);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("LotPress", getLotPress());
            editor.commit();
            ((CheckBox) _g(id.checklotno)).setChecked(true);
            Lotno ="1";
        } else {
            Lotno ="0";
            setLotPress(false);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("LotPress", getLotPress());
            editor.commit();
            ((CheckBox) _g(id.checklotno)).setChecked(false);
        }
        settingsChanged = true;
    }



    public void BoxNo(View view) {
        if(getBoxNo()==false) {
            setBoxNo(true);
            CheckboxNO ="1";
            ((CheckBox) _g(id.checkBoxNo)).setChecked(true);
        } else {
            CheckboxNO ="0";
            setBoxNo(false);
            ((CheckBox) _g(id.checkBoxNo)).setChecked(false);
        }
        settingsChanged = true;
    }
    public void TAS_ReShip(View view){

        if(get_Tas_ReShip()==false) {
            set_Tas_ReShip(true);
            Tas_Re_ship ="1";
            ((CheckBox) _g(id.checkrepicking)).setChecked(true);
        } else {
            set_Tas_ReShip(false);
            Tas_Re_ship ="0";
            ((CheckBox) _g(id.checkrepicking)).setChecked(false);
        }
        settingsChanged = true;
    }

    public void ArrivalNyuka(View view) {
        if(getArrivalNyuka()==false) {
            setArrivalNyuka(true);
            CheckboxArrival ="1";
            ((CheckBox) _g(id.checkarrivalNyuka)).setChecked(true);
        } else {
            CheckboxArrival ="0";
            setArrivalNyuka(false);
            ((CheckBox) _g(id.checkarrivalNyuka)).setChecked(false);
        }
        settingsChanged = true;
    }

    public void Trackcheck(View view){
        if(getTrackCheck()==false) {
            setTrackCheck(true);
            ((CheckBox) _g(id.checkTrackingNo)).setChecked(true);
            CheckTrackingNo ="1";
        } else {
            CheckTrackingNo ="0";
            setTrackCheck(false);
            ((CheckBox) _g(id.checkTrackingNo)).setChecked(false);
        }
        settingsChanged = true;
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("TrackCheck", getTrackCheck());
//        editor.commit();
    }

    public void BoxSize(View view){
        if(getBoxSelected()==false) {
            setBoxSelected(true);
            ((CheckBox) _g(id.checkBoxSize)).setChecked(true);
            CheckboxSize = "1";
        } else {
            CheckboxSize = "0";
            setBoxSelected(false);
            ((CheckBox) _g(id.checkBoxSize)).setChecked(false);
        }
        settingsChanged = true;
    }

    public void Tracking_date_check(View view) {
        if(getTracking_date_check()==false) {
            setTracking_date_check(true);
            trackingDateCheck ="1";
            ((CheckBox) _g(id.checkTracking_date)).setChecked(true);
        } else {
            trackingDateCheck ="0";
            setTracking_date_check(false);
            ((CheckBox) _g(id.checkTracking_date)).setChecked(false);
        }
        settingsChanged = true;
    }
    public void BoxChangeCheck(View view) {

        if(getBoxsizeChange()==false) {
            setBoxsizeChange(true);
            ((CheckBox) _g(id.checkBoxChange)).setChecked(true);
            checkBoxChange = "1";
        } else {
            checkBoxChange = "0";
            setBoxsizeChange(false);
            ((CheckBox) _g(id.checkBoxChange)).setChecked(false);
        }
        settingsChanged = true;
    }



    public void ShipOrderNumber(View view){
        if(getShipppicked()==false) {
            setShipppicked(true);
            ((CheckBox) _g(id.checkShippingorderno)).setChecked(true);
            ShipOrderNumber = "1";
        } else {
            setShipppicked(false);
            ShipOrderNumber = "0";
            ((CheckBox) _g(id.checkShippingorderno)).setChecked(false);
        }
        settingsChanged = true;
    }

    public void Shippingflag(View view) {
        if(getShippingflag()==false) {
            setShippingflag(true);
            ((CheckBox) _g(id.checkshipping)).setChecked(true);
            ShippingFlag = "1";
        } else {
            ShippingFlag = "0";
            setShippingflag(false);
            ((CheckBox) _g(id.checkshipping)).setChecked(false);
        }
        settingsChanged = true;
    }
    public void TShipping_flag(View view) {
        if(getTshipping_flag()==false) {
            setTshipping_flag(true);
            ((CheckBox) _g(id.checkTshipping_flag)).setChecked(true);
            checkTshipping = "1";
        } else {
            checkTshipping = "0";
            setTshipping_flag(false);
            ((CheckBox) _g(id.checkTshipping_flag)).setChecked(false);
        }
        settingsChanged = true;
    }

    public void CheckReship(View view) {

        if(getReshipment()==false) {
            setReshipment(true);
            ((CheckBox) _g(id.checkReship)).setChecked(true);
            checkReshipment = "1";
        } else {
            checkReshipment = "0";
            setReshipment(false);
            ((CheckBox) _g(id.checkReship)).setChecked(false);
        }
        settingsChanged = true;
    }

    public void SupplierList( View view) {
        if(getSupplierList()==false && ((CheckBox) _g(id.checksupplierList)).isChecked()) {
            setSupplierList(true);
            setNoSupplierList(false);
            ((CheckBox) _g(id.checksupplierList)).setChecked(true);
            ((CheckBox) _g(id.checkNosupplierList)).setChecked(false);
            settingsChanged = true;
            SupplierList ="1";
            NoSupplierList ="0";
        } else if(getNoSupplierList()== false && ((CheckBox) _g(id.checkNosupplierList)).isChecked()) {
            setSupplierList(false);
            setNoSupplierList(true);
            ((CheckBox) _g(id.checksupplierList)).setChecked(false);
            ((CheckBox) _g(id.checkNosupplierList)).setChecked(true);
            settingsChanged = true;
            SupplierList ="0";
            NoSupplierList ="1";
        }
        else {
            SupplierList ="0";
            NoSupplierList ="0";
            setSupplierList(false);
            setNoSupplierList(false);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("SupplierList", getSupplierList());
            editor.commit();
            ((CheckBox) _g(id.checksupplierList)).setChecked(false);
            ((CheckBox) _g(id.checkNosupplierList)).setChecked(false);

        }
    }

    public void IncludeScreen(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (includeScreenradiogroup.getCheckedRadioButtonId())
        {
            case id.includeScreenyesRb:
                if(shippingInspection && sIncludeFlag){
                    BaseActivity.setincludeScreen(true);
                    editor.putBoolean("IncludeScreen",  BaseActivity.getincludeScreen());
                    IncludeScreenstatus= "1";
                }
                else{
                    includeScreennoradio.setChecked(true);
                    IncludeScreenstatus= "0";
                }
                break;

            case id.includeScreennoRb:
                IncludeScreenstatus= "0";
                BaseActivity.setincludeScreen(false);
                editor.putBoolean("IncludeScreen", BaseActivity.getincludeScreen());
        }
        editor.commit();
    }

    public void AddRemark(View view) {
        if(getRemarkPress()==false) {
            setRemarkPress(true);
            ((CheckBox) _g(id.checkremark)).setChecked(true);
            Remark="1";
        }
        else
        {
            Remark="0";
            setRemarkPress(false);
            ((CheckBox) _g(id.checkremark)).setChecked(false);
        }
    }

    public void PackingList( View view) {
        if(getPackingList()==false) {
            setPackingList(true);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("PackingList", getPackingList());
            editor.commit();
            ((CheckBox) _g(id.checkpackingList)).setChecked(true);
            PackingLists = "1";
        }  else {
            PackingLists ="0";
            setPackingList(false);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("PackingList", getPackingList());
            editor.commit();
            ((CheckBox) _g(id.checkpackingList)).setChecked(false);
        }
    }

    public void BatchScreen(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (batchScreenradiogroup.getCheckedRadioButtonId()) {
            case id.batchScreenyesRb:
                BaseActivity.setbatchScreen(true);
                editor.putBoolean("BatchScreen",  BaseActivity.getbatchScreen());
                BatchScreenstatus ="1";
                break;
            case id.batchScreennoRb:
                BaseActivity.setbatchScreen(false);
                BatchScreenstatus ="0";
                editor.putBoolean("BatchScreen", BaseActivity.getbatchScreen());
        }
        editor.commit();
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
                Log.e(TAG,"CODE====Nulllll");
                U.beepError(this, "ネットワーク接続でエラーが発生しました。インターネットに接続できるか確認して下さい。");

            }
            if ("0".equals(code) ) {

                Log.e("SendLogs111", code + "  " + msg + "  " + result1);

                new ListShopAll().post(code, msg, result1,  this);

                //Get Setting
                progress.Show();
                manager.GetSettings(adminid,app.getSerial(),"",getcall);
            }else if(code.equalsIgnoreCase("1020")){


                new AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(SettingActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })
                        .show();
            }
            else
                new ListShopAll().valid(code,msg,result1, this);
        }
        catch (Exception e)
        {
            U.beepError(this, "ネットワーク接続でエラーが発生しました。インターネットに接続できるか確認して下さい。");
            System.out.print("FatalException ListShopAllll  "+e);
        }
    }

    @Override
    public void onPostError(int flag) {
        U.beepError(this, "ネットワーク接続でエラーが発生しました。インターネットに接続できるか確認して下さい。");
        System.out.print("FatalException ListShopAllll  ");
    }


    public void AlertUpdate(){
        // custom dialog
        final Dialog dialog = new Dialog(SettingActivity.this);
        dialog.setContentView(R.layout.dialogupdate);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(id.alert_txt);
        Button update = (Button) dialog.findViewById(id.update);
        Button cancel = (Button) dialog.findViewById(id.cancel);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onSucess(int status, ResponseBody message) throws JsonIOException {
        progress.Dismiss();

        if (status==200){
            Toast.makeText(getApplicationContext(),"設定を保存しました。",Toast.LENGTH_SHORT).show();
            progress.Show();
            manager.GetSettings(adminid,app.getSerial(),"",getcall);
        }

    }

    @Override
    public void onSucess(int status, SettingResult message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("200")){

            Log.e(TAG,">>>><<<<<<<<<<batchScreenRg"+ message.getResult().getSetsinclude());
            // Error Sound
            if (message.getResult().getNg_tune() == null || message.getResult().getNg_tune().equalsIgnoreCase("")) {
                BaseActivity.setErrorSound(0);
            } else {
                String errorVoice = message.getResult().getNg_tune();
                BaseActivity.setErrorSound(Integer.parseInt(errorVoice));
            }
            // OK Sound
            if (message.getResult().getOk_tune() ==  null || message.getResult().getOk_tune().equalsIgnoreCase("")) {
                BaseActivity.setOkSound(0);
            } else {
                String okvoice = message.getResult().getOk_tune();
                BaseActivity.setOkSound(Integer.parseInt(okvoice));
            }
            Log.e(TAG, "setErrorSound1111   "+BaseActivity.getErrorSound());
            Log.e(TAG, "setokSound1111   "+BaseActivity.getOkSound());

            //SInclude
            if (message.getResult().getSetsinclude().equalsIgnoreCase("1")){
                Log.e(TAG,">>>>>>>>>>>>>>>>>"+ message.getResult().getSetsinclude());
                includeradioyes.setChecked(true);
                sIncludeLayout.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                BaseActivity.setsinclude(true);
                Sinclude = "1";
                sIncludeFlag = true;
                editor.putBoolean("SInclude",  BaseActivity.getsinclude());
                editor.commit();
            }else{
                Log.e(TAG,">>>>>>>>>>>>>>>>>2222222222222"+ message.getResult().getSetsinclude());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                includeradiono.setChecked(true);
                sIncludeLayout.setVisibility(View.GONE);
                BaseActivity.setsinclude(false);
                sIncludeFlag = false;
                editor.putBoolean("SInclude",  BaseActivity.getsinclude());
                editor.commit();

            }

            //ScanProduct for shipping screen
            if (message.getResult().getProductscanflag().equalsIgnoreCase("0")){
                Log.e(TAG,">>>>>>>>>>>>>>>>11111111>  "+ message.getResult().getProductscanflag());
                scanProductradioyes.setChecked(true);
                scanProductLayout.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                BaseActivity.setscanProduct(true);
                scanProduct = "0";
                shippingInspection = true;
                editor.putBoolean("scanProduct",  BaseActivity.getscanProduct());
            }else{
                Log.e(TAG,">>>>>>>>>>>>>>>>11111111>2222222222222   "+ message.getResult().getProductscanflag());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                scanProductradiono.setChecked(true);
                scanProductLayout.setVisibility(View.GONE);
                BaseActivity.setscanProduct(false);
                shippingInspection = false;
                editor.putBoolean("scanProduct",  BaseActivity.getscanProduct());

            }

            if (message.getResult().getSetaddKeyboard().equalsIgnoreCase("1")){
                ((RadioButton) _g(id.showkeyboardRb)).setChecked(true);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                this.setaddKeyboard(true);
                Keyboard = "1";
                editor.putBoolean("ShowKeyboard", getaddKeyboard());
            } else {
                Keyboard ="0";


                ((RadioButton) _g(id.hidekeyboardRb)).setChecked(true);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                this.setaddKeyboard(false);
                editor.putBoolean("ShowKeyboard", getaddKeyboard());
            }

            //Batch Screen
            if (message.getResult().getSetbatchScreen().equalsIgnoreCase("1")) {
                batchScreenyesradio.setChecked(true);
                batchScreennoradio.setChecked(false);
                batchScreenLayout.setVisibility(View.VISIBLE);
                BatchScreenstatus = "1";
                SharedPreferences.Editor editor = sharedPreferences.edit();
                BaseActivity.setbatchScreen(true);
                editor.putBoolean("BatchScreen",  BaseActivity.getbatchScreen());
            }else{
                batchScreenyesradio.setChecked(false);
                batchScreennoradio.setChecked(true);
                batchScreenLayout.setVisibility(View.GONE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                BaseActivity.setbatchScreen(false);
                editor.putBoolean("BatchScreen",  BaseActivity.getbatchScreen());
            }

            //BoxNumber
            if (message.getResult().getSetBoxNo().equalsIgnoreCase("1")){
                checkBoxNo.setChecked(true);
                checkBoxNo.setVisibility(View.VISIBLE);
                setBoxNo(true);

                CheckboxNO = "1";
            }else{
                checkBoxNo.setChecked(false);
                checkBoxNo.setVisibility(View.GONE);
                setBoxNo(false);
            }

            //Box Selected
            if( message.getResult().getSetBoxSelected().equalsIgnoreCase("1")){
                checkBoxsize.setChecked(true);
                CheckboxSize = "1";
                checkBoxsize.setVisibility(View.VISIBLE);
                setBoxSelected(true);
            } else {
                setBoxSelected(false);
                checkBoxsize.setVisibility(View.GONE);
                checkBoxsize.setChecked(false);
            }

            //Include Screen
            //Include Screen
            if (message.getResult().getSetincludeScreen().equalsIgnoreCase("1")) {
                includeScreenyesradio.setChecked(true);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                BaseActivity.setincludeScreen(true);
                IncludeScreenstatus = "1";
                includeScreenLayout.setVisibility(View.VISIBLE);
                editor.putBoolean("IncludeScreen",  BaseActivity.getincludeScreen());

            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                includeScreennoradio.setChecked(true);
                BaseActivity.setincludeScreen(false);
                includeScreenLayout.setVisibility(View.GONE);
                IncludeScreenstatus ="0";
                editor.putBoolean("IncludeScreen",  BaseActivity.getincludeScreen());
            }

            //Lot Number
            if (message.getResult().getSetLotPress().equalsIgnoreCase("1")){
                lotno.setChecked(true);
                Lotno = "1";
                setLotPress(true);
                lotno.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("LotPress", getLotPress());
                editor.commit();
            }else{
                lotno.setChecked(false);
                setLotPress(false);
                lotno.setVisibility(View.GONE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("LotPress", getLotPress());
                editor.commit();
            }

            //Packing List
            if (message.getResult().getSetPackingList().equalsIgnoreCase("1")){
                packing.setChecked(true);
                setPackingList(true);
                PackingLists = "1";
                packing.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("PackingList", getPackingList());
                editor.commit();
            }else{
                packing.setChecked(false);
                setPackingList(false);
                packing.setVisibility(View.GONE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("PackingList", getPackingList());
                editor.commit();
            }

            //Printer Select
            if (message.getResult().getSetPrinterSelected().equalsIgnoreCase("1")){
                printer.setChecked(true);
                setPrinterSelected(true);
                Printer = "1";
                printer.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("PrinterSeleced", getPrinterSelected());
                editor.commit();
            }else{
                printer.setChecked(false);
                setPrinterSelected(false);
                printer.setVisibility(View.GONE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("PrinterSeleced", getPrinterSelected());
                editor.commit();
            }

            //Remarks
            if (message.getResult().getSetRemarkPress().equalsIgnoreCase("1")){
                remark.setChecked(true);
                Remark = "1";
                remark.setVisibility(View.VISIBLE);
                setRemarkPress(true);
            }else{
                remark.setChecked(false);
                remark.setVisibility(View.GONE);
                setRemarkPress(false);
            }

            //Shipping Flag
            if (message.getResult().getSetShippingflag().equalsIgnoreCase("1")){
                setShippingflag(true);
                ShippingFlag = "1";
                shippingflag.setVisibility(View.VISIBLE);
                shippingflag.setChecked(true);
            }else{
                setShippingflag(false);
                shippingflag.setChecked(false);
                shippingflag.setVisibility(View.GONE);
            }

            //Track Screen
            if (message.getResult().getSetTrackCheck().equalsIgnoreCase("1")){
                setTrackCheck(true);
                CheckTrackingNo = "1";
                checkTrackingNo.setVisibility(View.VISIBLE);
                ((CheckBox) _g(id.checkTrackingNo)).setChecked(true);
            } else{
                setTrackCheck(false);
                checkTrackingNo.setVisibility(View.GONE);
                ((CheckBox) _g(id.checkTrackingNo)).setChecked(false);
            }

            //Arrival Nyuka
            if (message.getResult().getSetArrivalNyuka().equalsIgnoreCase("1")){
                setArrivalNyuka(true);
                CheckboxArrival = "1";
                checkarrivalNyuka.setVisibility(View.VISIBLE);
                ((CheckBox) _g(id.checkarrivalNyuka)).setChecked(true);
            } else{
                setArrivalNyuka(false);
                Log.e(TAG, ">>>>>>>>>>>><<<<<<<<<<<<<<<<<<<"+getArrivalNyuka());
                ((CheckBox) _g(id.checkarrivalNyuka)).setChecked(false);
                checkarrivalNyuka.setVisibility(View.GONE);

            }

            //SupplierList
            if (message.getResult().getSetSupplierList().equalsIgnoreCase("1")){
                setSupplierList(true);
                setNoSupplierList(false);
                SupplierList = "1";
                NoSupplierList = "";
                supplier.setChecked(true);
                nosupplier.setChecked(false);
                nosupplier.setVisibility(View.GONE);
                supplier.setVisibility(View.VISIBLE);
            }else{
                setSupplierList(false);
                setNoSupplierList(false);
                supplier.setChecked(false);
                supplier.setVisibility(View.GONE);

            }

            //NoSupplierlist
            if (message.getResult().getSetNoSupplierList().equalsIgnoreCase("1")){
                setSupplierList(false);
                setNoSupplierList(true);
                NoSupplierList = "1";
                SupplierList = "";
                supplier.setChecked(false);
                nosupplier.setChecked(true);
                nosupplier.setVisibility(View.VISIBLE);
                supplier.setVisibility(View.GONE);
            }else{
                setSupplierList(false);
                setNoSupplierList(false);
                nosupplier.setChecked(false);
                nosupplier.setVisibility(View.GONE);

            }
            //TasFlag
            if (message.getResult().getTas_flag().equalsIgnoreCase("1")){
                SharedPrefrences.set_Tas_ReShip(this,"1");
                set_Tas_ReShip(true);
                Tas_Re_ship = "1";
                checkrepicking.setChecked(true);
                checkrepicking.setVisibility(View.VISIBLE);
            }else{
                SharedPrefrences.set_Tas_ReShip(this,"0");
                set_Tas_ReShip(false);
                checkrepicking.setChecked(false);
                checkrepicking.setVisibility(View.GONE);
            }

            //Tshipping_change
            if (message.getResult().getTshipping_change().equalsIgnoreCase("1")){
                SharedPrefrences.set_Tshipping_change(this,"1");
                setTshipping_flag(true);
                checkTshipping = "1";
                checkTshipping_flag.setChecked(true);
                checkTshipping_flag.setVisibility(View.VISIBLE);
            }else{
                SharedPrefrences.set_Tshipping_change(this,"0");
                setTshipping_flag(false);
                checkTshipping = "0";
                checkTshipping_flag.setChecked(false);
                checkTshipping_flag.setVisibility(View.GONE);
            }

            //ShipOrderNumber
            if (message.getResult().getAfter_pay_check().equalsIgnoreCase("1")){
                ShipOrderNumber = "1";
                SharedPrefrences.set_ShipOrderNumber(this,"1");
                setShipppicked(true);
                checkShippingorderno.setChecked(true);
                checkShippingorderno.setVisibility(View.VISIBLE);
            }else{
                SharedPrefrences.set_ShipOrderNumber(this,"0");
                setShipppicked(false);
                checkShippingorderno.setChecked(false);
                checkShippingorderno.setVisibility(View.GONE);

            }

            //BoxSizeChange
            if (message.getResult().getCheck_box_size().equalsIgnoreCase("1"))
            {
                checkBoxChange = "1";
                SharedPrefrences.setBoxChangeCheck(this,"1");
                setBoxsizeChange(true);
                checkBoxSizeChange.setChecked(true);
                checkBoxSizeChange.setVisibility(View.VISIBLE);
            }else{
                SharedPrefrences.setBoxChangeCheck(this,"0");
                setBoxsizeChange(false);
                checkBoxSizeChange.setChecked(false);
                checkBoxSizeChange.setVisibility(View.GONE);

            }

            // Tracking Date Check
            if (message.getResult().getTracking_date_check().equalsIgnoreCase("1")){
                SharedPrefrences.setTracking_date_check(this,"1");
                setTracking_date_check(true);
                trackingDateCheck = "1";
                checkTracking_date.setChecked(true);
                checkTracking_date.setVisibility(View.VISIBLE);
            }else{
                SharedPrefrences.setTracking_date_check(this,"0");
                setTracking_date_check(false);
                trackingDateCheck = "0";
                checkTracking_date.setChecked(false);
                checkTracking_date.setVisibility(View.GONE);
            }

            //Shipping Reship
            if (message.getResult().getUse_reship().equalsIgnoreCase("1"))
            {
                checkReshipment = "1";
                SharedPrefrences.setReshipment(this,"1");
                setReshipment(true);
                checkReship.setChecked(true);
                checkReship.setVisibility(View.VISIBLE);
            }else{
                SharedPrefrences.setReshipment(this,"0");
                setReshipment(false);
                checkReship.setChecked(false);
                checkReship.setVisibility(View.GONE);
            }
            //Menu Settings

            // sdBatch

            if (message.getResult().getSdBatchCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("sdBatchCheck",true);
                SlideMenu.sdBatch.setVisibility(View.VISIBLE);
                SharedPrefrences.set_sdBatchCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("sdBatchCheck",false);
                SlideMenu.sdBatch.setVisibility(View.GONE);
                SharedPrefrences.set_sdBatchCheck(this,"0");
            }

            // dBatch
            if (message.getResult().getdBatchCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("dBatchCheck",true);
                SlideMenu.dBatch.setVisibility(View.VISIBLE);
                SharedPrefrences.set_dBatchCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("dBatchCheck",false);
                SlideMenu.dBatch.setVisibility(View.GONE);
                SharedPrefrences.set_dBatchCheck(this,"0");
            }

            // temporaryLocation
            if (message.getResult().getTemporaryLocationCheck().equalsIgnoreCase("1")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("temporaryLocationCheck",true);
                slidemenu.temporaryLocation.setVisibility(View.VISIBLE);
                SharedPrefrences.set_temporaryLocationCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("temporaryLocationCheck",false);
                slidemenu.temporaryLocation.setVisibility(View.GONE);
                SharedPrefrences.set_temporaryLocationCheck(this,"0");
            }

            // truckBatchLL
            if (message.getResult().getTruckBatchCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("truckBatchCheck",true);
                slidemenu.truckBatchLL.setVisibility(View.VISIBLE);
                SharedPrefrences.set_truckBatchCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("truckBatchCheck",false);
                slidemenu.truckBatchLL.setVisibility(View.GONE);

                SharedPrefrences.set_truckBatchCheck(this,"0");
            }

            //  newShipGroupLL
            if (message.getResult().getShippingCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("shippingCheck",true);
                slidemenu.newShipGroupLL.setVisibility(View.VISIBLE);
                SharedPrefrences.set_shippingCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("shippingCheck",false);
                slidemenu.newShipGroupLL.setVisibility(View.GONE);
                SharedPrefrences.set_shippingCheck(this,"0");
            }

            // koguchi
            if (message.getResult().getKoguchiCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("koguchiCheck",true);
                slidemenu.koguchi.setVisibility(View.VISIBLE);
                SharedPrefrences.set_koguchiCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("koguchiCheck",false);
                slidemenu.koguchi.setVisibility(View.GONE);
                SharedPrefrences.set_koguchiCheck(this,"0");
            }

            // boxSize
            if (message.getResult().getBoxSizeCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("boxSizeCheck",true);
                slidemenu.boxSize.setVisibility(View.VISIBLE);
                SharedPrefrences.set_boxSizeCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("boxSizeCheck",false);
                slidemenu.boxSize.setVisibility(View.GONE);
                SharedPrefrences.set_boxSizeCheck(this,"0");
            }

            //  printerCheck
            if (message.getResult().getPrinterCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("printerCheck",true);
                slidemenu.barcodelabelprintLL.setVisibility(View.VISIBLE);
                SharedPrefrences.set_printerCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("printerCheck",false);
                slidemenu.barcodelabelprintLL.setVisibility(View.GONE);
                SharedPrefrences.set_printerCheck(this,"0");
            }

            //    stockChange
            if (message.getResult().getStockChangeCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("StockChangeCheck",true);
                slidemenu.stockChange.setVisibility(View.VISIBLE);
                SharedPrefrences.set_StockChangeCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("StockChangeCheck",false);
                slidemenu.stockChange.setVisibility(View.GONE);
                SharedPrefrences.set_StockChangeCheck(this,"0");
            }

            //   packSetLL
            if (message.getResult().getPackSetCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("packSetCheck",true);
                slidemenu.packSetLL.setVisibility(View.VISIBLE);
                SharedPrefrences.set_packSetCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("packSetCheck",false);
                slidemenu.packSetLL.setVisibility(View.GONE);
                SharedPrefrences.set_packSetCheck(this,"0");
            }

            // newPackSetCheck
            if (message.getResult().getNewPackSetCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("newPackSetCheck",true);
                slidemenu.newPackSetLL.setVisibility(View.VISIBLE);
                SharedPrefrences.set_newPackSetCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("newPackSetCheck",false);
                slidemenu.newPackSetLL.setVisibility(View.GONE);
                SharedPrefrences.set_newPackSetCheck(this,"0");
            }

            //   NewArrivalCheck
            if (message.getResult().getRfNewArrivalCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("rfNewArrivalCheck",true);
                slidemenu.rfarrivalLL.setVisibility(View.VISIBLE);
                SharedPrefrences.set_rfNewArrivalCheck(this,"1");

            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("rfNewArrivalCheck",false);
                slidemenu.rfarrivalLL.setVisibility(View.GONE);
                SharedPrefrences.set_rfNewArrivalCheck(this,"0");
            }

            // NewReturnCheck
            if (message.getResult().getRfNewReturnCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("rfNewReturnCheck",true);
                slidemenu.rfReturnLL.setVisibility(View.VISIBLE);
                SharedPrefrences.set_rfNewReturnCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("rfNewReturnCheck",false);
                slidemenu.rfReturnLL.setVisibility(View.GONE);
                SharedPrefrences.set_rfNewReturnCheck(this,"0");   }

            // slipPrinterCheck
            if (message.getResult().getSlipPrinterCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("slipPrinterCheck",true);
                slidemenu.slipPRinterll.setVisibility(View.VISIBLE);
                SharedPrefrences.set_slipPrinterCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("slipPrinterCheck",false);
                slidemenu.slipPRinterll.setVisibility(View.GONE);
                SharedPrefrences.set_slipPrinterCheck(this,"0");   }

            // machinePrinterCheck
            if (message.getResult().getMachinePrinterCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("machinePrinterCheck",true);
                slidemenu.machineprinterLL.setVisibility(View.VISIBLE);
                SharedPrefrences.set_machinePrinterCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("machinePrinterCheck",false);
                slidemenu.machineprinterLL.setVisibility(View.GONE);
                SharedPrefrences.set_machinePrinterCheck(this,"0");  }

            //  PickingCheck
            if (message.getResult().getRfPickingCheck().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("rfPickingCheck",true);
                slidemenu.rfPickingLL.setVisibility(View.VISIBLE);
                SharedPrefrences.set_rfPickingCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("rfPickingCheck",false);
                slidemenu.rfPickingLL.setVisibility(View.GONE);
                SharedPrefrences.set_rfPickingCheck(this,"0"); }

            //   barcodeSlipPrinter
            if (message.getResult().getBarcodeSlipPrinter().equalsIgnoreCase("1")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("barcodeSlipPrinter",true);
                slidemenu.printer.setVisibility(View.VISIBLE);
                SharedPrefrences.set_barcodeSlipPrinter(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("barcodeSlipPrinter",false);
                slidemenu.printer.setVisibility(View.GONE);
                SharedPrefrences.set_barcodeSlipPrinter(this,"0");
            }

            // rfWriter
            if (message.getResult().getRfWriter().equalsIgnoreCase("1")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("rfWriter",true);
                slidemenu.rfWriterLL.setVisibility(View.VISIBLE);
                SharedPrefrences.set_rfWriter(this,"1");
            } else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("rfWriter",false);
                slidemenu.rfWriterLL.setVisibility(View.GONE);
                SharedPrefrences.set_rfWriter(this,"0");     }

            //   ecms
            if (message.getResult().getEcms().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ecms",true);
                slidemenu.ecmsWeightLL.setVisibility(View.VISIBLE);
                SharedPrefrences.set_ecms(this,"1"); }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ecms",false);
                slidemenu.ecmsWeightLL.setVisibility(View.GONE);
                SharedPrefrences.set_ecms(this,"0"); }
            //   BoxSize
            if (message.getResult().getPdBoxSize().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("pdBoxSize",true);
                slidemenu.newboxsizeLL.setVisibility(View.VISIBLE);
                SharedPrefrences.set_pdBoxSize(this,"1"); }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("pdBoxSize",false);
                slidemenu.newboxsizeLL.setVisibility(View.GONE);
                SharedPrefrences.set_pdBoxSize(this,"0"); }

            // picklist
            if (message.getResult().getPicklist().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("picklist",true);
                slidemenu.totalPickLL.setVisibility(View.VISIBLE);
                SharedPrefrences.set_picklist(this,"1"); }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("picklist",false);
                slidemenu.totalPickLL.setVisibility(View.GONE);
                SharedPrefrences.set_picklist(this,"0");
            }

            // totalarrival
            if (message.getResult().getTotalarrival().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("totalarrival",true);
                slidemenu.totalarrivallist.setVisibility(View.VISIBLE);
                SharedPrefrences.set_totalarrival(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("totalarrival",false);
                slidemenu.totalarrivallist.setVisibility(View.GONE);
                SharedPrefrences.set_totalarrival(this,"0");
            }

            //  newbatchpick
            if (message.getResult().getNewbatchpick().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("newbatchpick",true);
                slidemenu.llnewbatchpick.setVisibility(View.VISIBLE);
                SharedPrefrences.set_newbatchpick(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("newbatchpick",false);
                slidemenu.llnewbatchpick.setVisibility(View.GONE);
                SharedPrefrences.set_newbatchpick(this,"0");  }

            //  newTasPick
            if (message.getResult().getNewTasPick().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("newTasPick",true);
                SlideMenu.llnewtaspick.setVisibility(View.VISIBLE);
                SharedPrefrences.set_newTasPick(this,"1");

            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("newTasPick",false);
                SlideMenu.llnewtaspick.setVisibility(View.GONE);
                SharedPrefrences.set_newTasPick(this,"0");
            }

            //  newPrinterSelect
            if (message.getResult().getNewPrinterSelect().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("newPrinterSelect",true);
                slidemenu.llnewMachinePrinter.setVisibility(View.VISIBLE);
                SharedPrefrences.set_newPrinterSelect(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("newPrinterSelect",false);
                slidemenu.llnewMachinePrinter.setVisibility(View.GONE);
                SharedPrefrences.set_newPrinterSelect(this,"0");
            }

            //  tshipping
            if (message.getResult().getTshipping().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("tshippingCheck",true);
                slidemenu.llbox_cash_register.setVisibility(View.VISIBLE);
                SharedPrefrences.set_tshippingCheck(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("tshippingCheck",false);
                slidemenu.llbox_cash_register.setVisibility(View.GONE);
                SharedPrefrences.set_tshippingCheck(this,"0");
            }

            //   newreturnStock
            if (message.getResult().getNewreturnStock().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("newreturnStock",true);
                slidemenu.llnewreturn_stock.setVisibility(View.VISIBLE);
                SharedPrefrences.set_newreturnStock(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("newreturnStock",false);
                slidemenu.llnewreturn_stock.setVisibility(View.GONE);
                SharedPrefrences.set_newreturnStock(this,"0");
            }

            //   returnStockBarcode
            if (message.getResult().getReturn_stock_barcode().equalsIgnoreCase("1") ){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("returnStockBarcode",true);
                slidemenu.llreturnstock_barcode.setVisibility(View.VISIBLE);
                SharedPrefrences.set_returnStockBarcode(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("returnStockBarcode",false);
                slidemenu.llreturnstock_barcode.setVisibility(View.GONE);
                SharedPrefrences.set_returnStockBarcode(this,"0");
            }

            //KoguchiPrintCSV
            if (message.getResult().getPrint_csv().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                SlideMenu.llkoguchi_print.setVisibility(View.VISIBLE);
                SharedPrefrences.set_koguchiPrint(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                SlideMenu.llkoguchi_print.setVisibility(View.GONE);
                SharedPrefrences.set_koguchiPrint(this,"0");
            }

            //invoice_printing
            if (message.getResult().getinvoice_printing().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("invoice_printing",true);
                SlideMenu.llorderdetails.setVisibility(View.VISIBLE);
                SharedPrefrences.set_invoice_printing(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("invoice_printing",false);
                SlideMenu.llorderdetails.setVisibility(View.GONE);
                SharedPrefrences.set_invoice_printing(this,"0");
            }

            //New Move Stock
            if (message.getResult().getNew_move_stock_screen().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("new_move_stock",true);
                SlideMenu.llnew_move_stock.setVisibility(View.VISIBLE);
                SharedPrefrences.set_new_move_stock(this,"1");

            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("new_move_stock",false);
                SlideMenu.llnew_move_stock.setVisibility(View.GONE);
                SharedPrefrences.set_new_move_stock(this,"0");
            }

            //New Invoice Print
            if (message.getResult().getNew_invoice_print().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("NewInvoicePrint",true);
                SlideMenu.llnew_invoice_print.setVisibility(View.VISIBLE);
                SharedPrefrences.setNewInvoicePrint(this,"1");

            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("NewInvoicePrint",false);
                SlideMenu.llnew_invoice_print.setVisibility(View.GONE);
                SharedPrefrences.setNewInvoicePrint(this,"0");
            }

            //InvoiceShipping
            if (message.getResult().getMedia_shipping().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                SlideMenu.llinvoice_shipping.setVisibility(View.VISIBLE);
                SharedPrefrences.set_invoice_shipping(this,"1");

            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                SlideMenu.llinvoice_shipping.setVisibility(View.GONE);
                SharedPrefrences.set_invoice_shipping(this,"0");
            }

            //Dm Batchpicking
            if (message.getResult().getPickman_batch().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("DmBatchPicking",true);
                SlideMenu.lldm_batch_picking.setVisibility(View.VISIBLE);
                SharedPrefrences.setDmBatchPicking(this,"1");

            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("DmBatchPicking",false);
                SlideMenu.lldm_batch_picking.setVisibility(View.GONE);
                SharedPrefrences.setDmBatchPicking(this,"0");
            }
        /*        //Multiuser Shipping
                if (message.getResult().getMultiuser_shipping().equalsIgnoreCase("1")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("multiuser_shipping",true);
                    SlideMenu.ll.setVisibility(View.VISIBLE);
                    SharedPrefrences.set_multiuser_shipping(this,"1");

                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("multiuser_shipping",false);
                    SlideMenu.llcustomerArrival.setVisibility(View.GONE);
                    SharedPrefrences.set_multiuser_shipping(this,"0");
                }*/


        /*    //NewDolArrival
            if (message.getResult().getUse_dolphin_arrival().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("newDolarrival",true);
                SlideMenu.lldoltotalpick.setVisibility(View.VISIBLE);
                SharedPrefrences.set_newdolArrival(this,"1");
             }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("newDolarrival",false);
                SlideMenu.lldoltotalpick.setVisibility(View.GONE);
                SharedPrefrences.set_newdolArrival(this,"0");
            }*/

            //customer_arrival
            if (message.getResult().getCustomer_arrival().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("CustomerArrival",true);
                SlideMenu.llcustomerArrival.setVisibility(View.VISIBLE);
                SharedPrefrences.setCustomerArrival(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("CustomerArrival",false);
                SlideMenu.llcustomerArrival.setVisibility(View.GONE);
                SharedPrefrences.setCustomerArrival(this,"0");
            }

            //mitsukoshi_arrival
            if (message.getResult().getUse_mitsukoshi().equalsIgnoreCase("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("mitsukoshi_arrival",true);
                SlideMenu.llmitsukoshi_arrival.setVisibility(View.VISIBLE);
                SharedPrefrences.set_Mitsukoshi_arrival(this,"1");
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("mitsukoshi_arrival",false);
                SlideMenu.llmitsukoshi_arrival.setVisibility(View.GONE);
                SharedPrefrences.set_Mitsukoshi_arrival(this,"0");
            }

            settingsChanged = false;

            //if Shipping Inspection Radio false then include shipping and sInclude will also be false
            if(!shippingInspection){

                SharedPreferences.Editor editor = sharedPreferences.edit();
                //includescreen
                includeScreennoradio.setChecked(true);
                BaseActivity.setincludeScreen(false);
                IncludeScreenstatus = "0";
                includeScreenLayout.setVisibility(View.VISIBLE);
                editor.putBoolean("IncludeScreen",  BaseActivity.getincludeScreen());

                //sInclude
                includeradiono.setChecked(true);
                sIncludeLayout.setVisibility(View.GONE);
                BaseActivity.setsinclude(false);
                editor.putBoolean("SInclude",  BaseActivity.getsinclude());
                editor.commit();

            }
            //if sInclude Radio false then include shipping  will also be false
            if(!sIncludeFlag){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //includescreen
                BaseActivity.setincludeScreen(false);
                includeScreennoradio.setChecked(true);
                IncludeScreenstatus = "0";
                includeScreenLayout.setVisibility(View.VISIBLE);
                editor.putBoolean("IncludeScreen",  BaseActivity.getincludeScreen());
                editor.commit();
            }
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

    public void createChangeList(){

        changeList = new ArrayList<>();
        if(Lotno.equalsIgnoreCase("1"))
            changeList.add("Lot No./Exp.");
        if(Remark.equalsIgnoreCase("1"))
            changeList.add("備考欄");
        if(SupplierList.equalsIgnoreCase("1"))
            changeList.add("催事の返品機能");
        if(NoSupplierList.equalsIgnoreCase("1"))
            changeList.add("仕入先");
        if(PackingLists.equalsIgnoreCase("1"))
            changeList.add("ﾊﾟｯｷﾝｸﾞﾘｽﾄ");
        if(Printer.equalsIgnoreCase("1"))
            changeList.add("自動印刷");
        if(ShippingFlag.equalsIgnoreCase("1"))
            changeList.add("出荷済");
        if(checkTshipping.equalsIgnoreCase("1"))
            changeList.add("T出荷済");
        if(CheckboxNO.equalsIgnoreCase("1"))
            changeList.add("箱サイズ");
        if(CheckboxSize.equalsIgnoreCase("1"))
            changeList.add("検品後出荷工程");
        if(CheckTrackingNo.equalsIgnoreCase("1"))
            changeList.add("問番号検品");
        if(CheckboxArrival.equalsIgnoreCase("1"))
            changeList.add("予定外入荷不可");
        if(Tas_Re_ship.equalsIgnoreCase("1"))
            changeList.add("TAS後レジ検しない");
        if(ShipOrderNumber.equalsIgnoreCase("1"))
            changeList.add("後払い検品");
        if(checkReshipment.equalsIgnoreCase("1"))
            changeList.add("再レジ検品");
        if(Keyboard.equalsIgnoreCase("1"))
            changeList.add("キーボードを表示");
        if(Sinclude.equalsIgnoreCase("1"))
            changeList.add("同梱物検品");
        if(scanProductradioyes.isChecked())
            changeList.add("検品");
        if(IncludeScreenstatus.equalsIgnoreCase("1"))
            changeList.add("同梱物スキャン");
        if(BatchScreenstatus.equalsIgnoreCase("1"))
            changeList.add("バッチ検品");
        if(checkBoxChange.equalsIgnoreCase("1"))
            changeList.add("出荷工程確認");
        if(trackingDateCheck.equalsIgnoreCase("1"))
            changeList.add("先日付出工警告");
        if(stockchange){
            changeList.add(selectedstock);
        }
        if(orderinfo_change){
            changeList.add(selected_orderinfo);
        }
        Log.e(TAG, "ChangeList  "+changeList);
        SetDialog();

    }

    private void SetDialog(){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//      dialog = new Dialog(LocationDetailsActivity.this);
        dialog.setContentView(R.layout.home_screen_save_dialog);
//      dialog.setTitle("Edit Location");
        TextView title= (TextView)dialog.findViewById(id.title) ;
        TextView title2= (TextView)dialog.findViewById(id.title2) ;
        title.setText("設定を保存しますか？");
        title2.setVisibility(View.GONE);

        ListView change = (ListView)dialog.findViewById(id.list);
        // Initializing an ArrayAdapter
        change.setAdapter(null);
        ListViewItems data = new ListViewItems();
        ArrayList<Integer> used = new ArrayList<>();

        for (int i = 0; i <= changeList.size() - 1; i++) {
            data.add(data.newItem().add(id.textView, changeList.get(i)));
        }

        ListViewAdapter  adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.setting_dialog_row);
        change.setAdapter(adapter);

        Button dialogcancel = (Button) dialog.findViewById(id.dialog_cancel);
//      if button is clicked, close the custom dialog
        dialogcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button dialogSave =(Button)dialog.findViewById(id.dialog_save);
        dialogSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,"OnSave Click");

                progress.Show();
                SettingRequest req = new SettingRequest(adminid,app.getSerial(),"",checkTshipping,Lotno,Remark,SupplierList,NoSupplierList,PackingLists,Printer,ShippingFlag,CheckboxNO,CheckboxSize,CheckTrackingNo,Keyboard,Sinclude,
                        IncludeScreenstatus,CheckboxArrival,BatchScreenstatus,"","","","","","","","","","","","","","","","","","","","","","","","","","",Tas_Re_ship ,ShipOrderNumber,"", secondPickingCheckbox, "","", scanProduct,"","","","","","","","","","","","","", checkBoxChange, trackingDateCheck,"","",checkReshipment);
                manager.PostSettingStatus(req,SettingActivity.this);
                settingsChanged = false;
                BaseActivity.setToPickingScreen(true);
                dialog.dismiss();

            }
        });
        dialog.show();

        //the exorcism of god

    }
}
