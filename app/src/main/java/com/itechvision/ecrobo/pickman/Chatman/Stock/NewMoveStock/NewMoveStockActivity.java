package com.itechvision.ecrobo.pickman.Chatman.Stock.NewMoveStock;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.BarcodeCheck.BarcodeCheckReq;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.BarcodeCheck.BarcodeResponse;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.BarcodeCheck.ProductList;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.BarcodeCheck.StockList;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.LocationCheck.LocationCheckReq;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.LocationCheck.LocationCheckResponse;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.SubmitStock.SubmitStockReq;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.SubmitStock.SubmitStockResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class NewMoveStockActivity extends BaseActivity implements View.OnClickListener, DataManager.CheckLocationcallback,  DataManager.CheckBarcodeStockcallback, DataManager.SubmitStockcallback {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.add_layout)
    Button numbrbtn;
    @BindView(R.id.layout_number)
    ViewGroup layout;
    @BindView(R.id.layout_main)
    RelativeLayout mainlayout;

    @BindView(R.id.scrollMain)
    ScrollView svMain;

    @BindView(R.id.list)
    ListView listview;
    public LinearLayout partnoLayout;
    String EXPRDAATE ="",APISTOCKID="";
    SharedPreferences sharedPreferences;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public static final String MYPREFERENCE = "MyPrefs";

    ECRApplication app=new ECRApplication();
    String adminID="";


    private ArrayList<StockList> stock = new ArrayList<>();
    protected ArrayList<ProductList> mProductList = new ArrayList<>();


    private boolean showKeyboard;
    private boolean visible = false;
    public TextToSpeak mTextToSpeak;
    public Context mcontext=this;
    private boolean orderRequestSettings ;

    protected int mProcNo = 0;
    public static final int PROC_SOURCE = 1;
    public static final int PROC_BARCODE = 2;
    public static final int PROC_QTY = 4;
    public static final int PROC_DESTINATION = 5;

    public static int count = 0;
    String lastupdatedQty = "0";
    public TextView spec1, spec2;

    String TAG= NewMoveStockActivity.class.getSimpleName();
    ListViewAdapter adapter ;

    progresBar progress;
    DataManager manager;
    DataManager.CheckLocationcallback checkLocationcallback;
    DataManager.CheckBarcodeStockcallback checkBarcodecallback;
    DataManager.SubmitStockcallback submitStockcallback;

    Dialog dialog1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_move_stock);
        ButterKnife.bind(this);

        getIDs();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));


        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);

        adminID = spDomain.getString("admin_id",null);

        spec1 = (TextView)findViewById(R.id.standard1);
        spec2 = (TextView)findViewById(R.id.standard2);

        partnoLayout = (LinearLayout)findViewById(R.id.partnoLayout);

        showKeyboard= BaseActivity.getaddKeyboard();
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
            Log.e("MoveActivity","SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);


        spec1.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        spec2.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        _gt(R.id.product_code).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        _gt(R.id.currentStock).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
        dialog1= new Dialog(this);
        if (mProcNo == 0) nextProcess();


    }

    public void AddLayout(View view)
    {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
        if(visible==false)
        {
            visible = true;

            numbrbtn.setText("キーボードを隠す");
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
        }
        else
        {
            visible = false;

            numbrbtn.setText("キーボードを表示");
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);

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

    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "IL在庫移動", " ",
                0, true,false ,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnBlue.setOnClickListener(this);

        progress = new progresBar(this);
        manager = new DataManager();
        checkLocationcallback = this;
        submitStockcallback = this;
        checkBarcodecallback = this;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.relLayout1:
                menu.showMenu();
                break;
            default:
                break;
        }
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {

            case PROC_BARCODE:
                Log.e(TAG, "SETPROCCCCC  PROC_BARCODE ");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.source).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.destination).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                _gt(R.id.barcode).setFocusableInTouchMode(true);


                scrollToView(svMain, _g(R.id.barcode));
                break;

            case PROC_QTY:
                Log.e(TAG, "SETPROCCCCC  PROC_QTY ");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.source).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.destination).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                scrollToView(svMain, _g(R.id.quantity));

                _gt(R.id.quantity).setFocusableInTouchMode(true);
                break;

            case PROC_SOURCE:
                Log.e(TAG, "SETPROCCCCC  PROC_SOURCE ");
                mBuff.delete(0, mBuff.length());
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.source).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.destination).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                _gt(R.id.source).setFocusableInTouchMode(true);
                hideKeyboard(this);

                scrollToView(svMain, _g(R.id.source));
                break;

            case PROC_DESTINATION:
                Log.e(TAG, "SETPROCCCCC  PROC_DESTINATION ");
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.source).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.destination).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));

                _gt(R.id.destination).setFocusableInTouchMode(true);
                _gt(R.id.destination).requestFocus();
                scrollToView(svMain, _g(R.id.destination));
                break;

        }
    }

    public void inputedEvent(String buff, boolean isScaned) {

        switch (mProcNo) {
            case PROC_SOURCE:
                String source = _gts(R.id.source);
                if ("".equals(source)) {
                    U.beepError(this, "移動元ロケーションは必須です");
                    _gt(R.id.source).setFocusableInTouchMode(true);

                    break;
                } else {
                    if(!CommonUtilities.getConnectivityStatus(this))
                    {
                        CommonUtilities.openInternetDialog(this);
                    }
                    else {
                        progress.Show();
                    LocationCheckReq req = new LocationCheckReq(adminID,app.getSerial(), BaseActivity.getShopId(), getResources().getString(R.string.version),source);
                    manager.LocationCheck(req, checkLocationcallback);}
                }
                break;

            case PROC_BARCODE:	// バーコード
                String barcode1 = _gts(R.id.barcode);
                if ("".equals(barcode1)) {
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.barcode).setFocusableInTouchMode(true);

                    break;
                } else {
                    if(!CommonUtilities.getConnectivityStatus(this))
                    {
                        CommonUtilities.openInternetDialog(this);
                    }
                    else {
                        progress.Show();
                        BarcodeCheckReq req = new BarcodeCheckReq(adminID,app.getSerial(), BaseActivity.getShopId(),getResources().getString(R.string.version),_gts(R.id.source),_gts(R.id.barcode),triplebarcode+"");
                        manager.BarcodeCheck(req, checkBarcodecallback);
                    }
                }
                break;

            case PROC_QTY:
                String qty = _gts(R.id.quantity);
                String barcode = _gts(R.id.barcode);
                String maxqty = _gts(R.id.currentStock);
                if (isScaned) {

                        if (buff.equals(barcode)) {
                            U.beepSuccess();
                            String val = U.plusTo(_gts(R.id.quantity), "1");
                            _sts(R.id.quantity, val);
                            mTextToSpeak.startSpeaking(val);
                            if (maxqty.equals(_gts(R.id.quantity))) {
                                setProc(PROC_DESTINATION);
                            }
                            break;
                        }

                        else {
                            U.beepError(this, "1度に移動出来る商品は1商品のみです");
                            _gt(R.id.quantity).setFocusableInTouchMode(true);
                            break;
                        }
                }
                if ("".equals(qty)) {
                    U.beepError(this, "数量は必須です");
                    _gt(R.id.quantity).setFocusableInTouchMode(true);
                    break;
                } else if (!U.isNumber(qty)) {
                    U.beepError(this, "数量は数値のみ入力可能です");
                    _gt(R.id.quantity).setFocusableInTouchMode(true);
                    break;
                }
                else if (U.compareNumeric(maxqty, qty) > 0) {
                    U.beepError(this, "数量が多すぎます");
                    _gt(R.id.quantity).setFocusableInTouchMode(true);
                    break;
                }else {
                    U.beepSuccess();
                        setProc(PROC_DESTINATION);
                }
                break;

            case PROC_DESTINATION:
                String destination = _gts(R.id.destination);

                if ("".equals(destination)) {
                    U.beepError(this, "移動先ロケーションは必須です");
                    _gt(R.id.destination).setFocusableInTouchMode(true);
                    break;
                }

                else {
                    stopTimer();
                    if(!CommonUtilities.getConnectivityStatus(this))
                    {
                        CommonUtilities.openInternetDialog(this);
                    }
                    else {

                        progress.Show();
                        SubmitStockReq req = new SubmitStockReq(adminID, app.getSerial(), BaseActivity.getShopId(), getResources().getString(R.string.version), _gts(R.id.source), _gts(R.id.destination), _gts(R.id.barcode), _gts(R.id.quantity), mProductList.get(0).getProduct_id(), timeTaken().toString(), triplebarcode + "");
                        manager.SubmitStockAPI(req, submitStockcallback);
                    }
                }
                break;
        }
    }



    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);
    }


    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
    }


    @Override
    public void allclearEvent() {

    }

    @Override
    public void skipEvent() {

    }

    public void nextWork() {
        initWorkList();
        _sts(R.id.quantity,"1");
        lastupdatedQty = U.plusTo(lastupdatedQty,"1");
        mTextToSpeak.startSpeaking("1");
        setProc(PROC_QTY);
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_BARCODE:	// バーコード
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.quantity, buff);
                break;
            case PROC_SOURCE:	// 移動元
                _sts(R.id.source, buff);
                break;
            case PROC_DESTINATION:	// 移動先
                _sts(R.id.destination, buff);
                break;

        }
    }

    @Override
    public void scanedEvent(String barcode)
    {
        if(dialog1.isShowing())
        {
            dialog1.dismiss();
        }
        if(!progress.isShowing())
        {
            if (!barcode.equals("")) {
                if (mProcNo == PROC_BARCODE){
                    String finalbarcode1 = CommonFunctions.getBracode(barcode);
                    barcode =finalbarcode1;

                    _sts(R.id.barcode, barcode);}

                if (mProcNo == PROC_QTY){
                    String finalbarcode1 = CommonFunctions.getBracode(barcode);
                    barcode =finalbarcode1;
                }

                else if (mProcNo == PROC_SOURCE) _sts(R.id.source, barcode);
                else if (mProcNo == PROC_DESTINATION) _sts(R.id.destination, barcode);}
            this.inputedEvent(barcode, true);
        }
        else
            Toast.makeText(getApplicationContext(),"Please Wait", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:	// バーコード
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QTY:	// ロケ選択
                _sts(R.id.quantity, barcode);
                break;
            case PROC_SOURCE: // 数量
                _sts(R.id.source, barcode);
                break;
            case PROC_DESTINATION: // 数量
                _sts(R.id.destination, barcode);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();
    }


    public ListViewItems initWorkList() {
        listview.setAdapter(null);

        ListViewItems data = new ListViewItems();
        for (int i = 0; i <=stock.size() - 1; i++) {

            data.add(data.newItem().add(R.id.txt1,stock.get(i).getStock_type_label())
                    .add(R.id.txt2, stock.get(i).getLot())
                    .add(R.id.txt3,stock.get(i).getExpiration_date())
                    .add(R.id.txt4, stock.get(i).getQuantity())
            );
        }
        adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.move_stock_list_row) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                    if (position % 2 == 1) {
                        Log.e(TAG, "Odd    positionnn");
                        v.setBackgroundColor(Color.WHITE);
                    } else {
                        v.setBackgroundColor(Color.WHITE);
                    }
                return v;
            }
        };


        listview.setAdapter(adapter);
        listview.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        return data;
    }

    @Override
    public void nextProcess() {
        this._sts(R.id.barcode, "");
        this._sts(R.id.quantity, "");
        this._sts(R.id.source, "");
        this._sts(R.id.destination, "");
        this._sts(R.id.currentStock,"");
        this._sts(R.id.product_code,"");
        this._stxtv(R.id.standard1,"");
        this._stxtv(R.id.standard2,"");

        this.setProc(PROC_SOURCE);
        setBadge2(0);

        listview.setAdapter(null);
        lastupdatedQty ="0";


        if(showKeyboard==false) {
            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);

            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);

            hiddenPanel.setVisibility(View.INVISIBLE);

            mainlayout.setLayoutParams(params);
        }
    }


    @Override
    public void onSucess(int status, LocationCheckResponse message) throws JsonIOException {
      progress.Dismiss();
      if(message.getCode().equals("0")) {
          setProc(PROC_BARCODE);
          U.beepNext();
      }
       else{
          U.beepError(this, message.getMessage());
      }
      }

    @Override
    public void onSucess(int status, BarcodeResponse message) throws JsonIOException {
        progress.Dismiss();
        if(message.getCode().equals("0")) {
            mProductList = message.getResults();
            stock = new ArrayList<>();
            for (int j = 0; j < mProductList.get(0).getStocks().size(); j++) {
                String loc = mProductList.get(0).getStocks().get(j).getLocation();
                //  stock_type_id = row2.getStringOrNull("stock_type_id");

                String loctxt = _gts(R.id.source);

                if ("z".equals(loc) == false) {

                    if (loc.equalsIgnoreCase(loctxt)) {
                        stock.add(mProductList.get(0).getStocks().get(j));

                    }

                }
            }
            if (stock.size() == 0) {
                U.beepError(this, "該当商品は在庫がありません");
                return;
            }
            startTimer();
            if (Integer.parseInt(mProductList.get(0).getTotal_quantity()) > 0) {
                _stxtv(R.id.standard1, mProductList.get(0).getSpec1());
                spec1.setSelected(true);
                _stxtv(R.id.standard2, mProductList.get(0).getSpec2());
                spec2.setSelected(true);
                _sts(R.id.product_code, mProductList.get(0).getCode());
                _sts(R.id.currentStock, mProductList.get(0).getTotal_quantity());

                nextWork();
            }
        }
        else{
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, SubmitStockResponse message) throws JsonIOException {
        progress.Dismiss();
        if(message.getCode().equals("0")) {
            U.beepKakutei(this, "設定を登録しました");
            nextProcess();

            String source= message.getResults().get(0).getSource();
            String destination =  message.getResults().get(0).getDestination();
            String qnty= message.getResults().get(0).getQty();
            String product = message.getResults().get(0).getCode();
            dialog1 = new Dialog(this);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // Set GUI of login screen
            dialog1.setContentView(R.layout.status_submit);
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = dialog1.getWindow();
            lp.copyFrom(window.getAttributes());


            dialog1.setCanceledOnTouchOutside(false);

            // Init button of login GUI
            TextView msg = (TextView) dialog1.findViewById(R.id.txt);
            msg.setText("品番" + product + "をロケ" + source + "から" + destination + "に" + qnty + "個移動しました。");
//			    品番0787-02222をロケAB1-03-03-04-03からsinghに4個移動しました。
            Button ok = (Button) dialog1.findViewById(R.id.btn_ok_dialog);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    U.beepNext();
                    dialog1.dismiss();

                }
            });
            // Make dialog box visible.
            dialog1.show();




        }
        else{
            U.beepError(this, message.getMessage());
        }
    }

    @Override
        public void onError(int status, ResponseBody error) {
            progress.Dismiss();
            U.beepError(this, error.toString());
        }

        @Override
        public void onFaliure() {
            progress.Dismiss();
            U.beepError(this, "Network failure");
        }

        @Override
        public void onNetworkFailure() {
            progress.Dismiss();
            U.beepError(this, "Network failure");
        }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}