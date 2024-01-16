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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetPrinter;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.AddStock;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.GetStock;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockAdjustmentActivity extends BaseActivity  implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.stockAdd)EditText stockadd;
    @BindView(R.id.stockNeg)EditText stocksub;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.listProduct) ListView lv;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;

    protected int mProcNo = 0;
    public static final int PROC_BARCODE = 1;
    public static final int PROC_QTYADD = 2;
    public static final int PROC_QTYSUB = 3;


    public static int mRequestStatus =0;

    public static final int REQ_INITIAL = 1;
    public static final int REQ_BARCODE = 2;
    public static final int REQ_QTY = 3;

    private TextToSpeak mTextToSpeak;
    String location ="";
    private boolean visible = false;
    protected String mSelectedId = null;
    private boolean showKeyboard;
    public Context mcontext=this;

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    ECRApplication app=new ECRApplication();
    String adminID="";

    String TAG= StockAdjustmentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_adjustment);

        ButterKnife.bind(StockAdjustmentActivity.this);

        getIDs();
        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);

        stockadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProc(PROC_QTYADD);
                _sts(R.id.stockNeg,"");
            }
        });
        stocksub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _sts(R.id.stockAdd,"");
                setProc(PROC_QTYSUB);
            }
        });
        if (mProcNo == 0) nextProcess();
        _glv(R.id.listProduct).setAdapter(null);
        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                // ロケが選択されたら次工程
                location = ((TextView) view.findViewById(R.id.stc_prd_0)).getText().toString();
                String qty = ((TextView) view.findViewById(R.id.stc_prd_1)).getText().toString();
                _sts(R.id.quantity, qty);
                mSelectedId = ((TextView) view.findViewById(R.id.stc_prd_3)).getText().toString();
                U.beepNext();
//                setProc(PROC_LOCATION);
            }
        });
        showKeyboard=BaseActivity.getaddKeyboard();


        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
            visible = true;

			numbrbtn.setText(R.string.hideKeyboard);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);

            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e("MoveActivity","SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }
    }

    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "在庫調整", " ",
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
                _gt(R.id.stockNeg).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.stockAdd).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _glv(R.id.listProduct).setAdapter(null);
                _gt(R.id.barcode).setFocusableInTouchMode(true);

                break;
            case PROC_QTYADD:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.stockNeg).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.stockAdd).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.stockAdd).setFocusableInTouchMode(true);

                break;
            case PROC_QTYSUB:
                _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.stockNeg).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.stockAdd).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.stockNeg).setFocusableInTouchMode(true);

                break;
        }
    }


    @Override
    public void inputedEvent(String buff) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                ListView lv = (ListView) findViewById(R.id.listProduct);
                lv.setAdapter(null);


                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode",_gts(R.id.barcode)));
                Globals.getterList.add(new ParamsGetter("barcode_search","true"));


                mRequestStatus = REQ_BARCODE;

                new MainAsyncTask(this, Globals.Webservice.stockUpdate, 1, StockAdjustmentActivity.this, "Form", Globals.getterList,true).execute();

                break;
            case PROC_QTYADD:
                if(_gts(R.id.quantity).equals(""))
                {
                    U.beepError(this, "数量は必須です");
                    _gt(R.id.stockAdd).setFocusableInTouchMode(true);
                    break;
                }
                if(_gts(R.id.stockAdd).equals(""))
                {
                    U.beepError(this, "数量は必須です");
                    _gt(R.id.stockAdd).setFocusableInTouchMode(true);
                    break;
                }

                String qty= U.plusTo(_gts(R.id.quantity),_gts(R.id.stockAdd));
                stopTimer();
                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode",_gts(R.id.barcode)));
                Globals.getterList.add(new ParamsGetter("quantity",qty));
                Globals.getterList.add(new ParamsGetter("id",mSelectedId));
                Globals.getterList.add(new ParamsGetter("quantity_update","true"));
                Globals.getterList.add(new ParamsGetter("location",location));
                Globals.getterList.add(new ParamsGetter("time_taken",timeTaken().toString()));

                mRequestStatus = REQ_QTY;

                new MainAsyncTask(this, Globals.Webservice.stockUpdate, 1, StockAdjustmentActivity.this, "Form", Globals.getterList,true).execute();
                break;
            case PROC_QTYSUB:
//                String qunatity=_gts(R.id.quantity);
//                String addqnty =
                if(_gts(R.id.quantity).equals(""))
                {
                    U.beepError(this, "数量は必須です");
                    _gt(R.id.stockNeg).setFocusableInTouchMode(true);
                    break;
                }
                if(_gts(R.id.stockNeg).equals(""))
                {
                    U.beepError(this, "数量は必須です");
                    _gt(R.id.stockNeg).setFocusableInTouchMode(true);
                    break;
                }
                if(U.compareNumeric(_gts(R.id.quantity),_gts(R.id.stockNeg))==1)
                {
                    U.beepError(this, "数量は必須です");
                    _gt(R.id.stockAdd).setFocusableInTouchMode(true);
                    break;
                }
                String qty1= U.minusTo(_gts(R.id.quantity),_gts(R.id.stockNeg));
                stopTimer();
                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("barcode",_gts(R.id.barcode)));
                Globals.getterList.add(new ParamsGetter("quantity",qty1));
                Globals.getterList.add(new ParamsGetter("id",mSelectedId));
                Globals.getterList.add(new ParamsGetter("quantity_update","true"));
                Globals.getterList.add(new ParamsGetter("location",location));
                Globals.getterList.add(new ParamsGetter("time_taken",timeTaken().toString()));

                mRequestStatus = REQ_QTY;

                new MainAsyncTask(this, Globals.Webservice.stockUpdate, 1, StockAdjustmentActivity.this, "Form", Globals.getterList,true).execute();
                break;
        }
    }

    @Override
    public void clearEvent() {
        //U.beepSuccess();
        mTextToSpeak.startSpeaking("clear");
        U.beepBigsound(this, null);
        nextProcess();
    }

    @Override
    public void allclearEvent() {
        CommonDialogs.customToast(this,"no action");
    }

    @Override
    public void skipEvent() {
        CommonDialogs.customToast(this,"no action");
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_BARCODE:	// バーコード
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTYADD:
                _sts(R.id.stockAdd, buff);
                break;
            case PROC_QTYSUB: // 数量
                _sts(R.id.stockNeg, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (!barcode.equals("")) {
            if (mProcNo == PROC_BARCODE){
                Log.e(TAG,"Scanned Event url selcted  "+BaseActivity.getUrl()+ "     Shop selected is  "+BaseActivity.getShopId());
                String finalbarcode1 = CommonFunctions.getBracode(barcode);
                barcode =finalbarcode1;
                _sts(R.id.barcode, barcode);}}
        this.inputedEvent(barcode);
    }

    @Override
    public void nextProcess() {
        this._sts(R.id.barcode, "");
        this._sts(R.id.stockAdd, "");
        this._sts(R.id.stockNeg, "");
        this._sts(R.id.quantity,"");
        this._glv(R.id.listProduct).setAdapter(null);
        this.setProc(PROC_BARCODE);

        if(showKeyboard==false) {
            visible = false;
            numbrbtn.setText(R.string.showkeyboard);
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
//			Animation bottomDown = AnimationUtils.loadAnimation(this,
//					R.anim.bottom_down);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
//			hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e("MoveActivity", "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
    }

    @Override
    protected void onDestroy() {
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
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, barcode);
                break;
            case PROC_QTYADD:
                _sts(R.id.stockAdd, barcode);
                break;
            case PROC_QTYSUB:
                _sts(R.id.stockNeg, barcode);
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
                    new GetStock().post(code,msg,result1, mHash,StockAdjustmentActivity.this);
                }

                else if(mRequestStatus == REQ_QTY){
                    new AddStock().post(code,msg,result1,mHash,StockAdjustmentActivity.this);
                }
                else if(mRequestStatus ==REQ_INITIAL)
                {
                    new GetPrinter().post(code,msg,result1,mHash,StockAdjustmentActivity.this);
                }

            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(StockAdjustmentActivity.this,LoginActivity.class);
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
                new GetStock().valid(code,msg,result1, mHash,StockAdjustmentActivity.this);
            }

            else if(mRequestStatus == REQ_QTY){
                new AddStock().valid(code,msg,result1,mHash,StockAdjustmentActivity.this);
            }
            else if(mRequestStatus ==REQ_INITIAL)
            {
                new GetPrinter().valid(code,msg,result1,mHash,StockAdjustmentActivity.this);
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
