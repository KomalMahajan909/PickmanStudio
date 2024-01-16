


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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.GetLocation;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.MoveLocation;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.R.drawable;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
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

public class MoveLocationActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.add_layout)Button numbrbtn;
    @BindView(R.id.scrollMain)
    ScrollView svMain;
    @BindView(id.layout_main )RelativeLayout mainlayout;
    @BindView(id.layout_number )RelativeLayout layout;

    protected int mProcNo = 0;	//

    public static final int PROC_SOURCE = 1;
    public static final int PROC_DESTINATION = 2;

    ECRApplication app=new ECRApplication();
    String adminID="";

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    private TextToSpeak mTextToSpeak;
    private boolean showKeyboard;
    public Context mcontext=this;

    private boolean visible = false;

    public static int mRequestStatus =0;

    public static final int REQ_SOURCE= 1;
    public static final int REQ_DESTINATION= 2;

    String TAG= MoveLocationActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_location);

        ButterKnife.bind(MoveLocationActivity.this);
        Log.d(TAG,"On Create ");
        getIDs();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);

        showKeyboard=BaseActivity.getaddKeyboard();
        numbrbtn =(Button) _g(id.add_layout);
        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(id.layout_number);
            visible = true;

            numbrbtn.setText("キーボードを隠す");

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(250);
            int top = convert(10);
            int btm =convert(20);
            params.setMargins(0,0,0,bottom);

            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,btm);
            Log.e("MoveActivity","SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }
        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);

        if (mProcNo == 0) nextProcess();

    }

    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "ロケ移動", " ",
                0, false,false,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);


        relLayout1.setOnClickListener(this);

//        imgRight.setOnClickListener(this);

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
        ViewGroup hiddenPanel = (ViewGroup)findViewById(id.layout_number);
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
            numbrbtn.setText("キーボードを表示");
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
            case PROC_SOURCE:
                _gt(id.source).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.destination).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _glv(id.listProduct).setAdapter(null);
                _gt(id.source).requestFocus();
                _gt(id.source).setFocusableInTouchMode(true);
                break;
            case PROC_DESTINATION:
                _gt(id.source).setBackground(getResources().getDrawable(drawable.basic_edittext_off_nopadding));
                _gt(id.destination).setBackground(getResources().getDrawable(drawable.basic_edittext_on_nopadding));
                _gt(id.destination).setFocusableInTouchMode(true);
                _gt(id.destination).requestFocus();

                break;
        }
    }


    @Override
    public void inputedEvent(String buff) {
        this.keypressEvent("", buff);
        switch (mProcNo) {
            case PROC_SOURCE:
                String source = _gts(id.source);
                if ("".equals(source)) {
                    U.beepError(this, "移動元ロケーションは必須です");
                    _gt(id.source).setFocusableInTouchMode(true);

                } else {
                    _glv(id.listProduct).setAdapter(null);
                    Globals.getterList = new ArrayList<>();

                    Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                    Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                    Globals.getterList.add(new ParamsGetter("location",_gts(id.source)));


                    mRequestStatus = REQ_SOURCE;

                    new MainAsyncTask(this, Globals.Webservice.getLocation, 1, MoveLocationActivity.this, "Form", Globals.getterList,true).execute();


                }
                break;
            case PROC_DESTINATION:
                String destination = _gts(id.destination);


                if ("".equals(destination)) {
                    U.beepError(this, "移動先ロケーションは必須です");
                    _gt(id.destination).setFocusableInTouchMode(true);

                } else {
                    stopTimer();


                    Globals.getterList = new ArrayList<>();

                    Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                    Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                    Globals.getterList.add(new ParamsGetter("source",_gts(id.source)));
                    Globals.getterList.add(new ParamsGetter("destination",_gts(id.destination)));
                    Globals.getterList.add(new ParamsGetter("time_taken",timeTaken().toString()));


                    mRequestStatus = REQ_DESTINATION;

                    new MainAsyncTask(this, Globals.Webservice.moveLocation, 1, MoveLocationActivity.this, "Form", Globals.getterList,true).execute();

                }
                break;
        }

    }

    @Override
    public void clearEvent() {
        //U.beepSuccess();
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
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_SOURCE:	// 移動元
                _sts(id.source, buff);
                break;
            case PROC_DESTINATION:	// 移動先
                _sts(id.destination, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if(MoveLocation.dialogdismissed ==true)
        {
            MoveLocation.dialog.dismiss();
            MoveLocation.dialogdismissed= false;
        }
        if (!barcode.equals("")) {
        if (mProcNo == PROC_SOURCE) _sts(id.source, barcode);
        else if (mProcNo == PROC_DESTINATION) _sts(id.destination, barcode);}
        this.inputedEvent(barcode);
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_SOURCE: // 数量
                _sts(id.source, barcode);
                break;
            case PROC_DESTINATION: // 数量
                _sts(id.destination, barcode);
                break;
        }
    }


    @Override
    public void nextProcess() {
        this._sts(id.source, "");
        this._sts(id.destination, "");
        this.setProc(PROC_SOURCE);
        if(showKeyboard==false) {
            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);

            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);
//			hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e("MoveActivity", "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
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
            if ("0".equals(code) )
            {

                Log.e("SendLogs111",code+"  "+msg+"  "+result1);

                if(mRequestStatus == REQ_SOURCE)
                {
                    new GetLocation().post(code,msg,result1, mHash,MoveLocationActivity.this);
                }
                else if(mRequestStatus == REQ_DESTINATION)
                {
                    new MoveLocation().post(code,msg,result1, mHash,MoveLocationActivity.this);
                }

            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(MoveLocationActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else{
                if(mRequestStatus == REQ_SOURCE)
                {
                    new GetLocation().valid(code,msg,result1, mHash,MoveLocationActivity.this);
                }
                if(mRequestStatus == REQ_DESTINATION)
                {
                    new MoveLocation().valid(code,msg,result1, mHash,MoveLocationActivity.this);
                }

            }}
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }
}
