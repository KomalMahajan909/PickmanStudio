
package com.itechvision.ecrobo.pickman.Chatman.Arrivals.CustomerArrival;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewPickingActivity;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckCustomer.CustomerCheckReq;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckCustomer.CustomerCheckResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class CustomerIDActivity extends BaseActivity implements View.OnClickListener, DataManager.CheckCustomercallback {
    static SlidingMenu menu;
    SlideMenu slidemenu;
  /*  @BindView(R.id.actionbar)
    ActionBar actionbar;*/
  @BindView(R.id.layout_main)
  RelativeLayout mainlayout;
    @BindView(R.id.layout_number) RelativeLayout layout;
    @BindView(R.id.keyboard)
    Button numbrbtn;
    @BindView(R.id.customerId)
    EditText customerID;
//    @BindView(R.id.menu_drawer)
//    ImageView menu_drawer;

    ECRApplication app = new ECRApplication();
    public String adminID = "", ID = "";
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    progresBar progress;
    DataManager manager;
    String TAG = CustomerIDActivity.class.getSimpleName();
    DataManager.CheckCustomercallback checkCustomercallback;
    private boolean visible = false;
    public Context mcontext = this;
    private boolean showKeyboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_id);
        ButterKnife.bind(this);
        getIDs();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        Log.d(TAG,"On Create ");
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);

        adminID = spDomain.getString("admin_id", null);
        ID = BaseActivity.getShopId();

        showKeyboard = BaseActivity.getaddKeyboard();
        if (showKeyboard == true) {
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
            visible = true;
            numbrbtn.setText("キーボードを隠す");
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
        }

    }

    @Override
    public void inputedEvent(String buff) {
        String customer = _gts(R.id.customerId);
        if ("".equals(customer)) {
            U.beepError(this, "お客様ID必須");
            _gt(R.id.customerId).setFocusableInTouchMode(true);

        }
        else{
           progress.Show();
            CustomerCheckReq req = new CustomerCheckReq( adminID,app.getSerial(), ID, getResources().getString(R.string.version), customer);
            manager.CheckCustomerID(req, checkCustomercallback);
        }


    }

    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
        if (visible == false) {

            visible = true;
            numbrbtn.setText("キーボードを隠す");
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

        } else {

            visible = false;
            numbrbtn.setText("キーボードを表示する");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
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
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                x, r.getDisplayMetrics()
        );
        return px;
    }
    @Override
    public void clearEvent() {
      _sts(R.id.customerId,"");
    }

    @Override
    public void allclearEvent() {
        Toast.makeText(getApplicationContext(),"this action is not allowed",Toast.LENGTH_LONG).show();
    }

    @Override
    public void skipEvent() {
        Toast.makeText(getApplicationContext(),"this action is not allowed",Toast.LENGTH_LONG).show();
    }

    @Override
    public void keypressEvent(String key, String buff) {
        _sts(R.id.customerId, buff);
    }

    @Override
    public void scanedEvent(String barcode) {

            if (progress.isShowing()) {
                Toast.makeText(getApplicationContext(), "Please Wait", Toast.LENGTH_SHORT).show();
            } else {
                if (!barcode.equals("")) {

                    _sts(R.id.customerId,barcode);
                    this.inputedEvent(barcode);
                }}

    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        _sts(R.id.customerId, barcode);
    }

    @Override
    public void nextProcess() {

    }


    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub
        actionbarImplement(this, "入荷検品ECMS", " ",
                0, false, false, false, false);


        //menubarr


        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);
        relLayout1.setOnClickListener(this);


        progress = new progresBar(this);
        manager = new DataManager();

        checkCustomercallback= this;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
        }
    }

    @Override
    public void onSucess(int status, CustomerCheckResponse message) throws JsonIOException {
        progress.Dismiss();
     if(message.getCode().equalsIgnoreCase("0")){

         Intent i = new Intent(CustomerIDActivity.this,CustomerArrivalActivity.class);
         i.putExtra("ID",_gts(R.id.customerId));
         startActivity(i);
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
}