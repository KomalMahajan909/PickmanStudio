package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.AddKoguchi;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetKoguchi;
import com.itechvision.ecrobo.pickman.R;
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

public class KoguchiActivity  extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;


    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    ECRApplication app=new ECRApplication();
    String adminID="";

    private TextToSpeak mTextToSpeak;
    String TAG= KoguchiActivity.class.getSimpleName();

    public String orderID = "";

    protected int mProcNo = 0;
    public static final int PROC_TRACK_ID = 1;
    public static final int PROC_QTY = 2;

    public static int mRequestStatus =0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_TRACKING = 2;
    public static final int REQ_QTY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koguchi);
        ButterKnife.bind(KoguchiActivity.this);

        getIDs();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id",null);

        mTextToSpeak = new TextToSpeak(this);
        Log.e(TAG, "OnCreateeeeee");

        if (mProcNo == 0) nextProcess();
    }

    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "個口変更", " ",
                0, false,false,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);


        relLayout1.setOnClickListener(this);

//        btnBlue.setOnClickListener(this);

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

    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {
            case PROC_TRACK_ID:
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.trackingNumber).setFocusableInTouchMode(true);

                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                break;
            case PROC_QTY:
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                if (_gts(R.id.quantity).equals("1"))
                    mTextToSpeak.startSpeaking("1");
                break;
        }
    }

    public void inputedEvent(String buff, boolean isScaned) {

        switch (mProcNo) {
            case PROC_TRACK_ID:    // バーコード

                String track = _gts(R.id.trackingNumber);

                if (track.equals("") ) {
                    U.beepError(this, "ロット番号が必要");
                    break;
                }

                U.beepRecord(this, null);
                setProc(PROC_QTY);
                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("mediacode",_gts(R.id.trackingNumber)));
                Globals.getterList.add(new ParamsGetter("mediacode_check","true"));
                mRequestStatus = REQ_TRACKING;
                new MainAsyncTask(KoguchiActivity.this, Globals.Webservice.koguchi, 1, KoguchiActivity.this, "Form", Globals.getterList,true).execute();

//                new ECZaikoClient(this).setListner(new GetKoguchi()).getKoguchi(track, "mediacode_check","true");

                break;

            case PROC_QTY: // 数量

                String qty = _gts(R.id.quantity);
                if(qty.equals(""))
                    qty="1";
                String track1 = _gts(R.id.trackingNumber);

                Log.e(TAG,"Qtyyyyy  "+qty);
                Log.e(TAG, "buff " + buff);

                if (isScaned) {
                    if (buff.equals(track1)) {
                        U.beepSuccess();
                        qty = U.plusTo(qty, "1");
                        _sts(R.id.quantity, qty);
                        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                        break;
                    } else {

                        stopTimer();

                        Globals.getterList = new ArrayList<>();

                        Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                        Globals.getterList.add(new ParamsGetter("order_id",orderID));
                        Globals.getterList.add(new ParamsGetter("koguchi",_gts(R.id.quantity)));
                        Globals.getterList.add(new ParamsGetter("time_taken",timeTaken().toString()));
                        mRequestStatus = REQ_QTY;
                        new MainAsyncTask(KoguchiActivity.this, Globals.Webservice.koguchi, 1, KoguchiActivity.this, "Form", Globals.getterList,true).execute();
//                       .add("order_id", order).add(tag,value).add("time_taken", span);
//                        new ECZaikoClient(this).setListner(new AddKoguchi()).addKoguchi(orderID, "koguchi",_gts(R.id.quantity),timeTaken().toString());
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

                stopTimer();
                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("order_id",orderID));
                Globals.getterList.add(new ParamsGetter("koguchi",_gts(R.id.quantity)));
                Globals.getterList.add(new ParamsGetter("time_taken",timeTaken().toString()));
                mRequestStatus = REQ_QTY;
                new MainAsyncTask(KoguchiActivity.this, Globals.Webservice.koguchi, 1, KoguchiActivity.this, "Form", Globals.getterList,true).execute();
//                new ECZaikoClient(this).setListner(new AddKoguchi()).addKoguchi(orderID, "koguchi",_gts(R.id.quantity),timeTaken().toString());
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
//        U.beepBigsound(this, null);
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
            case PROC_TRACK_ID:    // バーコード
                _sts(R.id.trackingNumber, buff);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.quantity, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEventttttt");

            if (mProcNo == PROC_TRACK_ID){
                _sts(R.id.trackingNumber, barcode);}
        }

        this.inputedEvent(barcode, true);


    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_TRACK_ID:    // バーコード
                _sts(R.id.trackingNumber, barcode);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.quantity, barcode);
                break;
        }
    }
    public void getOrderId(String order)
    {
        orderID =order;
    }
    public void nextProcess() {
        _sts(R.id.trackingNumber, "");
        _sts(R.id.quantity, "");
        orderID ="";
//        quantity="0";

        setProc(PROC_TRACK_ID);
        _gt(R.id.trackingNumber).requestFocus();

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

//                if(mRequestStatus == REQ_INITIAL)
//                {
//                    new GetSpecification().post(code,msg,result1, mHash,ShippingSpecificationActivity.this);
//                }
                 if(mRequestStatus == REQ_TRACKING)
                {
                    new GetKoguchi().post(code,msg,result1, mHash,KoguchiActivity.this);
                }
                else if(mRequestStatus == REQ_QTY) {
                    new AddKoguchi().post(code,msg,result1, mHash,KoguchiActivity.this);
                }

            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(KoguchiActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else{
//                if(mRequestStatus == REQ_INITIAL)
//                {
//                    new GetSpecification().valid(code,msg,result1, mHash,ShippingSpecificationActivity.this);
//                }
                if(mRequestStatus == REQ_TRACKING)
                {
                    new GetKoguchi().valid(code,msg,result1, mHash,KoguchiActivity.this);
                }


                else if(mRequestStatus == REQ_QTY){
                    new AddKoguchi().valid(code,msg,result1, mHash,KoguchiActivity.this);
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
