package com.itechvision.ecrobo.pickman.Chatman.Ship.InvoiceShipping;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.InvoiceShipping.CheckInvoiceOrder.CheckInvoiceShipOrderResponse;
import com.itechvision.ecrobo.pickman.Models.InvoiceShipping.CheckInvoiceOrder.CheckOrderReq;
import com.itechvision.ecrobo.pickman.Models.InvoiceShipping.CheckInvoiceOrder.MediaCodeData;
import com.itechvision.ecrobo.pickman.Models.InvoiceShipping.SubmitMediacode.SubmitMediaReq;
import com.itechvision.ecrobo.pickman.Models.InvoiceShipping.SubmitMediacode.SubmitMediaResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class InvoiceShippingActivity extends BaseActivity implements View.OnClickListener, DataManager.OrderIDCheckCallback, DataManager.TrackSubmitCallback  {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.add_layout)
    Button keyboard;


    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    ECRApplication app=new ECRApplication();
    String adminID="";

    progresBar progress;
    DataManager manager;

    private TextToSpeak mTextToSpeak;
    String TAG= InvoiceShippingActivity.class.getSimpleName();

    ArrayList<MediaCodeData> arrMedia;

    public String orderID = "";

    protected int mProcNo = 0;

    public static final int PROC_ORDER_ID = 1;
    public static final int PROC_TRACK_ID = 2;

    DataManager.OrderIDCheckCallback orderIDCheckCallback;
    DataManager.TrackSubmitCallback trackSubmitCallback;


    private boolean showKeyboard;
    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_shipping);
        ButterKnife.bind(this);

        getIDs();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id",null);

        mTextToSpeak = new TextToSpeak(this);
        Log.e(TAG, "OnCreateeeeee");

        if (mProcNo == 0) nextProcess();
//        showKeyboard = sharedPreferences.getBoolean("ShowKeyboard", false);
      /*  if (showKeyboard == true) {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
            visible = true;
            keyboard.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            int bottom = convert(200);
            int top = convert(10);
            params.setMargins(0, 0, 0, bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layout.getLayoutParams();
            param.setMargins(0, top, 0, 0);

            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }*/
    }

    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "納品書送り状照合", " ",
                0, false,false,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);


        relLayout1.setOnClickListener(this);

        progress = new progresBar(this);
        manager = new DataManager();
        orderIDCheckCallback = this;
        trackSubmitCallback = this;

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
            case PROC_ORDER_ID:
                _gt(R.id.trackingno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.orderid).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.orderid).setFocusableInTouchMode(true);

                break;
            case PROC_TRACK_ID:
                _gt(R.id.trackingno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.trackingno).setFocusableInTouchMode(true);

                _gt(R.id.orderid).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                break;
        }
    }
    @Override
    public void inputedEvent(String buff) {
        switch (mProcNo) {

            case PROC_ORDER_ID:    // 注文ID
//                 Conditional set next process whether orderId or trackingNo or orderNo.

                String orderId = _gts(R.id.orderid);
                if ("".equals(orderId)) {
                    U.beepError(this, "注文IDは必須です");
                    _gt(R.id.orderid).setFocusableInTouchMode(true);

                    break;
                }

                if (CommonUtilities.getConnectivityStatus(this)) {
                    progress.Show();

                    CheckOrderReq req = new CheckOrderReq(app.getSerial(), adminID, BaseActivity.getShopId(), orderId, getString(R.string.version));
                    manager.OrderIDCheck(req, orderIDCheckCallback);
                } else {
                    CommonUtilities.openInternetDialog(this);
                }

                break;
            case PROC_TRACK_ID:    // バーコード

                String track = _gts(R.id.trackingno);

                if (track.equals("")) {
                    U.beepError(this, "ロット番号が必要");
                    break;
                }

                U.beepRecord(this, null);
                if (CommonUtilities.getConnectivityStatus(this)) {
                    progress.Show();

                    SubmitMediaReq req = new SubmitMediaReq(app.getSerial(), adminID, BaseActivity.getShopId(), _gts(R.id.orderid), track,getString(R.string.version));
                    manager.TrackSubmitAPI(req, trackSubmitCallback);
                } else {
                    CommonUtilities.openInternetDialog(this);
                }
                break;
        }
    }

    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
    }

    @Override
    public void allclearEvent() {
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
    }

    @Override
    public void skipEvent() {

    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_ORDER_ID:    // バーコード
                _sts(R.id.orderid, buff);
                break;
            case PROC_TRACK_ID: // 数量
                _sts(R.id.trackingno, buff);
                break;}
    }

    @Override
    public void scanedEvent(String barcode) {

            if (mProcNo == PROC_ORDER_ID) {

                _sts(R.id.orderid, barcode);
            }
            else if (mProcNo == PROC_TRACK_ID) {

                _sts(R.id.trackingno, barcode);
            }

        this.inputedEvent(barcode);
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_ORDER_ID:    // バーコード
                _sts(R.id.orderid, barcode);
                break;
            case PROC_TRACK_ID: // 数量
                _sts(R.id.trackingno, barcode);
                break;
        }
    }

    @Override
    public void nextProcess() {

        _sts(R.id.trackingno, "");
        _sts(R.id.orderid, "");

        setProc(PROC_ORDER_ID);
        _gt(R.id.orderid).requestFocus();
    }


    @Override
    public void onSucess(int status, CheckInvoiceShipOrderResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){
            arrMedia = message.getResults();
            if (arrMedia.get(0).getMediacode().equalsIgnoreCase("")){
                U.beepError(this,"注文に追跡番号がありません") ;
            }
            else
                setProc(PROC_TRACK_ID);
        }
        else
            U.beepError(this,message.getMessage());
    }

    @Override
    public void onSucess(int status, SubmitMediaResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){
           U.beepKakutei(this, message.getMessage());
           nextProcess();
        }
        else
            U.beepError(this,message.getMessage());
    }

    @Override
    public void onError(int status, ResponseBody error) {
        if (status == 403) {
            progress.Dismiss();
            JSONObject jObjError = null;
            try {
                jObjError = new JSONObject(error.string());
                String message = jObjError.get("message").toString();

                U.beepError(InvoiceShippingActivity.this, message);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onFaliure() {

    }

    @Override
    public void onNetworkFailure() {

    }
}