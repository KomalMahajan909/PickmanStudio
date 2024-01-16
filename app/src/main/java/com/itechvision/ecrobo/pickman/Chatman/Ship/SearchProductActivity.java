package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetProduct;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchProductActivity  extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(id.add_layout)Button numbrbtn;
    @BindView(id.scrollMain) ScrollView svMain;

    @BindView(R.id.listLocation) ListView lv;
    @BindView(id.layout_main) RelativeLayout mainlayout;
    @BindView(id.layout_number) RelativeLayout layout;
    @BindView(id.searchProduct) CheckBox searchCount;
    @BindView(id.btnBack) Button backbtn;


    protected int mProcNo = 0;
    public static int lotpress=0;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_SELECT = 2;
    public static final int PROC_QTY = 3;
    public static final int PROC_LOT_NO = 4;

    ECRApplication app=new ECRApplication();
    String adminID="";

    SharedPreferences sharedPreferences;

    private boolean orderRequestSettings;
    protected String mSelectedId = null;
    public static List<Map<String, String>> mProductList =  new ArrayList<Map<String, String>>();
    protected ArrayList<String> productIdArray = new ArrayList<String>();
    private boolean visible = false,triplebarcode = false;
    private TextToSpeak mTextToSpeak;

    public TextView productname, spec1, spec2;

    public Context mcontext=this;
    private boolean showKeyboard;
    ListViewAdapter adapter;


    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public static int count = 0;
    String TAG= SearchProductActivity.class.getSimpleName();

    boolean back = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        ButterKnife.bind(SearchProductActivity.this);
        Log.d(TAG,"On Create ");
        getIDs();
        Log.e(TAG,"Lotnooooooo    "+getLotPress());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        productname =(TextView)findViewById(R.id.productname);

        spec1 =(TextView)findViewById(R.id.standard1);
        spec2 =(TextView)findViewById(R.id.standard2);

        Intent i = getIntent();
        if (i.hasExtra("back"))
            back = i.getBooleanExtra("back",true);

        if(back)
            backbtn.setVisibility(View.VISIBLE);

        else
            backbtn.setVisibility(View.GONE);

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);

        triplebarcode = BaseActivity.getTripleBarcode();
        productname.setBackground(getResources().getDrawable(drawable.basic_edittext_off));
        _gt(id.productCode).setBackground(getResources().getDrawable(drawable.basic_edittext_off));
        spec1.setBackground(getResources().getDrawable(drawable.basic_edittext_off));
        spec2.setBackground(getResources().getDrawable(drawable.basic_edittext_off));
        _gt(id.totalQuantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off));
        _gt(id.orderQuantity).setBackground(getResources().getDrawable(drawable.basic_edittext_off));

        showKeyboard=BaseActivity.getaddKeyboard();
        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(id.layout_number);
            visible = true;

            numbrbtn.setText(R.string.hideKeyboard);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);

            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e("SearchProductActivity","SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);


        lv.setAdapter(null);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> map = mProductList.get(position);
                Log.e("Clicked", "SelectedMappppp     "+map);

                if(productIdArray.size() > position)
                    mSelectedId = productIdArray.get(position);
//                }
                U.beepSuccess();
                if (orderRequestSettings) {
                    if (getKey() == PROC_LOT_NO) {
                        setProc(PROC_BARCODE);
                    }
                    else if (getKey() == PROC_BARCODE)
                        setProc(PROC_LOT_NO);
                }
                else
                    setProc(PROC_BARCODE);
            }
        });

        if (mProcNo == 0) nextProcess();
    }
    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        if(BaseActivity.getSearchProduct()==true)

            actionbarImplement(this, "商品検索", " ",
                    0, false,true,false );
        else

            actionbarImplement(this, "商品検索", " ",
                    0, false,false,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);


        relLayout1.setOnClickListener(this);
        btnRed.setOnClickListener(this);


    }
    public void AddLayout(View view)
    {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
        if(visible==false)
        {
            visible = true;

            numbrbtn.setText(R.string.hideKeyboard);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
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

            numbrbtn.setText(R.string.showkeyboard);
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

    @OnClick(R.id.btnBack)void back(){
        Intent i = new Intent();
        SearchProductActivity.this.setResult(Activity.RESULT_OK, i);
        finish();

    }

    public static void setKey(int poc)
    {
        lotpress=poc;
    }
    public static int getKey()
    {
        return lotpress;
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
            case PROC_BARCODE:
                Log.e(TAG,"SetPRocccccc Barcodeeeeee");
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_on));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                if (orderRequestSettings)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off));
                break;
            case PROC_SELECT:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off));

                if (orderRequestSettings)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off));
                break;
            case PROC_QTY:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off));
                if (orderRequestSettings)
                    _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_off));
                break;
            case PROC_LOT_NO:
                _gt(id.barcode).setBackground(getResources().getDrawable(drawable.basic_edittext_off));
                _gt(id.lotno).setBackground(getResources().getDrawable(drawable.basic_edittext_on));
        }
    }

    @Override
    public void inputedEvent(String buff) {
        String lot="";
        switch (mProcNo) {
            case PROC_BARCODE:	// ?????
                lv.setAdapter(null);
                setKey(1);

                Globals.getterList = new ArrayList<>();
                Toast.makeText(this, "inputedEvent   " +buff, Toast.LENGTH_SHORT).show();
                Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode",_gts(id.barcode)));
                Globals.getterList.add(new ParamsGetter("lot_no",lot));
                Globals.getterList.add(new ParamsGetter("check_length",triplebarcode+""));

                if(BaseActivity.getSearchProduct())
                    Globals.getterList.add(new ParamsGetter("get_untracked","true"));

                new MainAsyncTask(this, Globals.Webservice.getProduct, 1, SearchProductActivity.this, "Form", Globals.getterList,true).execute();

                break;
            case PROC_SELECT:	// ????
                setProc(PROC_BARCODE);
                break;
            case PROC_QTY:		// ??
//				String stock2 = _gts(id.stock2);
//				if ("".equals(stock2)) {
//					U.beepError(this, "???????");
//					_gt(id.stock2).setFocusableInTouchMode(true);
//					break;
//				} else {
//					new ECZaikoClient(this).setListner(new AdjustStock()).adjustStockByProduct(this._gts(id.barcode), this._gts(id.stock1), mSelectedId, this._gts(id.stock2));
//				}
                break;
            case PROC_LOT_NO:
                lot = _gts(id.lotno);
                if (lot.equals("") || lot.equals("0")) {
                    U.beepError(this, "????????");
                    break;
                }
                setKey(4);
                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode",_gts(id.barcode)));
                Globals.getterList.add(new ParamsGetter("lot_no",lot));
                Globals.getterList.add(new ParamsGetter("check_length",triplebarcode+""));

                new MainAsyncTask(this, Globals.Webservice.getProduct, 1, SearchProductActivity.this, "Form", Globals.getterList,true).execute();

                break;
        }
    }

    public void searchProduct(View view){
        if (getSearchProduct()==false){
            setSearchproduct(true);
            ((CheckBox)_g(id.searchProduct)).setChecked(true);
            getIDs();
        }
        else
        {
            setSearchproduct(false);
            ((CheckBox)_g(id.searchProduct)).setChecked(false);
            getIDs();
        }
    }

    @Override
    public void clearEvent() {
        //U.beepSuccess();
        mTextToSpeak.startSpeaking("clear");
//        U.beepBigsound(this, null);
        nextProcess();
    }

    @Override
    public void allclearEvent() {

    }
    @Override
    protected void onStop() {
        mTextToSpeak.resetQueue();
        super.onStop();
    }
    @Override
    public void skipEvent() {

    }
    public void updateBadge2(String totalCount) {
        setBadge2(Integer.valueOf(totalCount));
        Log.e(TAG, "updateBadge1111111111  " + totalCount);
    }

    @Override
    public void onBackPressed() {
        // TODO not backed from picking activity
        //super.onBackPressed();
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_BARCODE:	// ?????
                _sts(id.barcode, buff);
                break;
            case PROC_SELECT:	// ????
                break;
            case PROC_QTY: // ??
//				_sts(id.stock2, buff);
                break;
            case PROC_LOT_NO:
                _sts(id.lotno, buff);
                break;
        }
    }

    public void nextProcess() {
        Log.e(TAG, "nextProcesssss  ");
        this._sts(id.barcode, "");
        this._stxtv(id.standard1, "");
        this._stxtv(id.standard2, "");
        this._sts(id.productCode, "");
        this._stxtv(id.productname, "");
        this._sts(id.totalQuantity, "");
        this._sts(id.orderQuantity, "");
        if (orderRequestSettings)
            this._sts(id.lotno, "");
        lv.setAdapter(null);
        this.setProc(PROC_BARCODE);
        _gt(id.barcode).requestFocus();
        Log.e(TAG, "nextProcesssss 11111 ");
        this.setBadge3(0);
        if (showKeyboard == false) {
            visible = false;
            numbrbtn.setText(R.string.showkeyboard);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
//			Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
//			hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e("NewPicking", "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);

        }
    }

    @Override
    public void scanedEvent(String barcode) {
        Toast.makeText(this, "scanned Event  " +barcode, Toast.LENGTH_SHORT).show();
        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEventttttt");

            if (mProcNo == PROC_BARCODE) {
                Toast.makeText(this, "scanned Event 11111 " +barcode, Toast.LENGTH_SHORT).show();
                // check for QR code
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "     Shop selected is  " + BaseActivity.getShopId());
                Log.e(TAG, "Length of barcode is   " + barcode.length());
                if (BaseActivity.getUrl().equals("https://air-logi-st.air-logi.com/service") && BaseActivity.getShopId().equals("1011")) {
                    String result = "";
                    if (barcode.length() == 13) {
                        result = barcode.substring(0, barcode.length() - 1);
                        Log.e(TAG, "BArcode after chopping last character becomes " + result);
                        barcode = result;
                    } else if (barcode.length() == 14) {
                        result = barcode.substring(1, barcode.length() - 1);
                        Log.e(TAG, "BArcode after chopping first and last character becomes " + result);
                        barcode = result;
                    }
                }
                String finalbarcode1 = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode1;

                _sts(id.barcode, barcode);
            }
        }


        if(mProcNo== PROC_LOT_NO) _sts(id.lotno,barcode);
        this.inputedEvent(barcode);
    }
    public void setproductIdArray(ArrayList arr){ this.productIdArray = arr;}
    public boolean matchString (String str,String match)
    {
        Pattern p = Pattern.compile(match);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public void getProductList( List<Map<String, String>> list){
        mProductList = list;
        initWorkList();
    }
    protected void initWorkList() {
        Log.e("NewShippingActivity", "initWorkList");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        ArrayList<Integer> used = new ArrayList<>();

        Log.e(TAG,">>>>>>>>>>>>>>>mBatchList     "+ mProductList.size() + "     "+mProductList);
        for (int i =0 ; i <= mProductList.size() - 1; i++) {
            Map<String, String> row = mProductList.get(i);

            data.add(data.newItem().add(R.id.prd_0, row.get("location"))
                    .add(R.id.prd_1, row.get("quantity"))
                    .add(R.id.prd_2, row.get("stock_type_label"))

            );

            used.add(i);
        }

        adapter = new ListViewAdapter(
                getApplicationContext()
                , data
                , R.layout.product_list_row){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                if (position % 2 == 1) {
                    Log.e(TAG,"Odd    positionnn    ");
                    v.setBackgroundColor(Color.GRAY);
                } else {
                    Log.e(TAG,"Even     positionnn    ");
                    v.setBackgroundColor(Color.WHITE);
                }
//            }


                return v;
            }
        };
        lv.setAdapter(adapter);
//        lv.getChildAt(1).setEnabled(false);
        // ??????????
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        // ????????????
        if (data.getData().size() > 0){
            lv.setItemChecked(0, true);
            lv.setSelection(0);
        }
//        return data;
    }


    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:	// ?????
                _sts(id.barcode, barcode);
                break;
            case PROC_SELECT:	// ????
                break;
            case PROC_QTY: // ??
//				_sts(id.stock2, barcode);
                break;
            case PROC_LOT_NO: // ??
                _sts(id.lotno, barcode);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;

            case id.notif_count_red:
                menu.showMenu();
                break;


            default:
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
            if ("0".equals(code) ) {


                Log.e("SendLogs111",code+"  "+msg+"  "+result1);

                new GetProduct().post(code,msg,result1, mHash,SearchProductActivity.this);

            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(SearchProductActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else{
                new GetProduct().valid(code,msg,result1, mHash,SearchProductActivity.this);
            }}
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {
        if (count > 50) {
            Log.e(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   counttt1111 " + count);
            showdialog("????????????????\n ???????????????");
            count = 0;
        } else // Repeate the request 10 times if not successful
        {
            count++;
            Log.e(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   counttt " + count);
            new MainAsyncTask(this, Globals.Webservice.getArrival, 1, SearchProductActivity.this, "Form", Globals.getterList,true).execute();



        }
    }

    void showdialog (String msg){
        // Create Object of Dialog class
        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set GUI of login screen
        dialog.setContentView(R.layout.new_picking_dialog);
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
