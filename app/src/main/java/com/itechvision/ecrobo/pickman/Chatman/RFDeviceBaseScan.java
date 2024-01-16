package com.itechvision.ecrobo.pickman.Chatman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFBarcodePrinterActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFIDArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFID_ReturnActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFReadWriteActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFTagCheckActivity;
import com.itechvision.ecrobo.pickman.Chatman.RFID.RFWriterActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.RFPickingActivity;
import com.itechvision.ecrobo.pickman.Fragments.DeviceScanFragment;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class RFDeviceBaseScan extends FragmentActivity implements View.OnClickListener {

    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.nextscreen) Button next;

    private final int REQUEST_COARSE_LOCATION = 99;
    public static String rftype = "";



    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";


    private static final String TAG = RFDeviceBaseScan.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfdevice_base_scan);
        ButterKnife.bind(this);

        Intent i = getIntent();
        if (i.hasExtra("rf_use")){
            rftype = i.getStringExtra("rf_use");
            Log.e(TAG,"RfIDDDDD1111    "+rftype);
        }
        getIDs();

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        String mac = spDomain.getString("MacAddress", "");
        if(!mac.equals("")){
            Log.e(TAG,"MACCCCCCCCCCCC   "+mac);
            if(rftype.equals("arrival")){
                Intent inti =new Intent(this, RFIDArrivalActivity.class);
                inti.putExtra(RFIDArrivalActivity.EXTRAS_DEVICE_ADDRESS,mac);
                startActivity(inti);
            }
            else if(rftype.equals("return")){
                Intent inti = new Intent(this, RFID_ReturnActivity.class);
                inti.putExtra(RFID_ReturnActivity.EXTRAS_DEVICE_ADDRESS,mac);
                startActivity(inti);
            }
            else if(rftype.equals("printer"))
            {
                Intent inti = new Intent(this, RFBarcodePrinterActivity.class);
                inti.putExtra(RFBarcodePrinterActivity.EXTRAS_DEVICE_ADDRESS,mac);
                startActivity(inti);
            }
            else if(rftype.equals("check"))
            {
                Intent inti = new Intent(this, RFTagCheckActivity.class);
                inti.putExtra(RFTagCheckActivity.EXTRAS_DEVICE_ADDRESS,mac);
                startActivity(inti);
            }
            else if(rftype.equals("write"))
            {
                Intent inti = new Intent(this, RFWriterActivity.class);
                inti.putExtra(RFWriterActivity.EXTRAS_DEVICE_ADDRESS,mac);
                startActivity(inti);
            }
            else if(rftype.equals("readWrite"))
            {
                Intent inti = new Intent(this, RFReadWriteActivity.class);
                inti.putExtra(RFReadWriteActivity.EXTRAS_DEVICE_ADDRESS,mac);
                startActivity(inti);
            }
            else if (rftype.equals("picking"))
            {
                Intent inti = new Intent(this, RFPickingActivity.class);
                inti.putExtra(RFPickingActivity.EXTRAS_DEVICE_ADDRESS,mac);
                startActivity(inti);
            }
//
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new DeviceScanFragment())
                .commit();
        next.setOnClickListener(this);
    }

    //set title and icons on actionbar
    private void getIDs() {
        String title ="";
        Log.e(TAG,"RfIDDDDD     "+rftype);
        if(rftype.equals("arrival"))
          title = "入荷検品";
        else if(rftype.equals("return"))
            title  = "返品検品";
        else if(rftype.equals("printer"))
            title  = "BarcodePrinter";
        else if(rftype.equals("check"))
            title  = "RFTagCheck";
        else if(rftype.equals("readWrite"))
            title  = "RF ReadWrite";
        else if (rftype.equals("picking"))
            title  = "接続画面";
        else if (rftype.equals("write"))
            title  = "RFID書き込み";


        CommonUtilities.actionbarImplement(this, title, " ",
                0, false, false, false);

        menu =CommonUtilities.setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        CommonUtilities.btnRed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relLayout1:
                menu.showMenu();
                break;
            case R.id.nextscreen:
                if(rftype.equals("arrival"))
                startActivity(new Intent(this, RFIDArrivalActivity.class));
                else if(rftype.equals("return"))
                    startActivity(new Intent(this, RFID_ReturnActivity.class));
                else if(rftype.equals("printer"))
                    startActivity(new Intent(this, RFBarcodePrinterActivity.class));
                else if(rftype.equals("check"))
                    startActivity(new Intent(this, RFTagCheckActivity.class));
                else if(rftype.equals("readWrite"))
                    startActivity(new Intent(this, RFReadWriteActivity.class));
                else if(rftype.equals("picking"))
                    startActivity(new Intent(this, RFPickingActivity.class));
                else if(rftype.equals("write"))
                    startActivity(new Intent(this, RFWriterActivity.class));
                break;


        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        requestNeededPermissions();
    }

    private void getAppVersion() {
        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void requestNeededPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION,WRITE_EXTERNAL_STORAGE}, REQUEST_COARSE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_COARSE_LOCATION) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
