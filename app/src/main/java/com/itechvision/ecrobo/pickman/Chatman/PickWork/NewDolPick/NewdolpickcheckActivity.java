package com.itechvision.ecrobo.pickman.Chatman.PickWork.NewDolPick;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TotalPickListActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewdolpickcheckActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.layout_main) RelativeLayout layoutMain;
    @BindView(R.id.barcode) EditText barcode;
    @BindView(R.id.checkbarcode) EditText checkbarcode;
    static SlidingMenu menu;
    SlideMenu slidemenu;
    protected int mProcNo = 0;

    public static final int PROC_BARCODE = 4;
    public static final int PROC_QTY = 5;
    String TAG = TotalPickListActivity.class.getSimpleName();
    String a="",b="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newdolpickcheck);
        ButterKnife.bind(this);
        getIDs();
        Log.d(TAG,"On Create ");

    }
    private void getIDs() {

        actionbarImplement(this, "Dトータルピック", " ",
       0, false,false,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        setProc(PROC_BARCODE);

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
        switch (mProcNo) {
            case PROC_BARCODE:    // バーコード
                _sts(R.id.barcode, buff);
                break;
            case PROC_QTY: // 数量
                _sts(R.id.checkbarcode, buff);
                break;

        }
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
            case PROC_QTY: // 数量
                _sts(R.id.checkbarcode, barcode);
                break;
        }
    }

    @Override
    public void nextProcess() {

    }


    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {

            case PROC_BARCODE:
                 _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.barcode).setFocusableInTouchMode(true);
                _gt(R.id.checkbarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                 break;

            case PROC_QTY:
                 _gt(R.id.barcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.checkbarcode).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                 _gt(R.id.checkbarcode).setFocusableInTouchMode(true);

            break;

        }
    }

    public void inputedEvent(String buff, boolean isScaned) {

    }


    @Override
    public void scanedEvent(String barcode) {
        Log.e(TAG, "ScannedEventttttt   is " + barcode);

        if (!barcode.equals("")) {
            Log.e(TAG, "ScannedEventttttt");

            if (mProcNo == PROC_BARCODE) {
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
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode = finalbarcode;
                _sts(R.id.barcode, barcode);

                if (barcode.startsWith("SC")){
                String currentString = finalbarcode;
                String[] separated = currentString.split("SC");
              a=    separated[0]; // this will contain "Fruit"
              b=    separated[1];

                    setProc(PROC_QTY);
            }else if(barcode.startsWith("sc")){
                    String currentString = finalbarcode;
                    String[] separated = currentString.split("sc");
                    a=    separated[0]; // this will contain "Fruit"
                    b=       separated[1];

                    setProc(PROC_QTY);
            }  else if(barcode.startsWith("Sc")){
                    String currentString = finalbarcode;
                    String[] separated = currentString.split("Sc");
                    a=    separated[0]; // this will contain "Fruit"
                    b=       separated[1];

                    setProc(PROC_QTY);
            }else if(barcode.startsWith("sC")){
                    String currentString = finalbarcode;
                    String[] separated = currentString.split("sC");
                    a=    separated[0]; // this will contain "Fruit"
                    b=       separated[1];

                    setProc(PROC_QTY);
            } else{
                    new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                           // .setTitle("Error!")
                            .setMessage("商品が違います！")
                            .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                 }
                            })
                             .show();
                    U.beepError(this, null);
                    setProc(PROC_BARCODE);
            }

            }

          else  if (mProcNo == PROC_QTY) {
                Log.e(TAG, "Scanned Event url selcted  " + BaseActivity.getUrl() + "   Shop selected is  " + BaseActivity.getShopId());
                String finalbarcode = CommonFunctions.getBracode(barcode);
             //   barcode = finalbarcode;
                Log.e(TAG, "barcode111   " + finalbarcode);
                _sts(R.id.checkbarcode, finalbarcode);

                if (finalbarcode.equals(b)) {
                    U.boxOK(this,null);
                    new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                            // .setTitle("Error!")
                            .setMessage("OK！")
                            .setCancelable(false)
                            .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    next();

                                 }
                            })

                            .show();
                } else {

                    new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                            // .setTitle("Error!")
                            .setMessage("商品が違います！")
                            .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                 }
                            })

                            .show();
                    U.beepError(this, null);
                    
                }

            }

        }
     }


     private void next(){

        barcode.setText("");
        checkbarcode.setText("");
        setProc(PROC_BARCODE);

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, SettingActivity.class));
        finish();

    }

}
