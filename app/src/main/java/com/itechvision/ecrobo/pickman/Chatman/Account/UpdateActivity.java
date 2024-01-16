package com.itechvision.ecrobo.pickman.Chatman.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import butterknife.BindView;
import butterknife.ButterKnife;

public  class UpdateActivity extends BaseActivity implements View.OnClickListener {

    static SlidingMenu menu;
    SlideMenu slidemenu;

    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.updatebtn)  Button updatebtn;

    final int CONTEXT_MENU_TEST = 3;
    final int CONTEXT_MENU_DOWNLOAD = 1;
    final int CONTEXT_MENU_ACTUAL = 2;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    String TAG = UpdateActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(UpdateActivity.this);
        getIDs();

        Log.d(TAG,"On Create ");
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        updatebtn.setOnClickListener(this);
    }

    @Override
    public void nextProcess() {
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

    //set title and icons on actionbar
    private void getIDs() {

        CommonUtilities.actionbarImplement(this, "アップデイト", " ",
       0, false,false,false );

        //menubarr
        menu = CommonUtilities.setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);
        CommonUtilities.relLayout1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
            case R.id.updatebtn:
                registerForContextMenu(updatebtn);
                openContextMenu(updatebtn);

            default:
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("バージョンを選択");
        menu.add(Menu.NONE, CONTEXT_MENU_DOWNLOAD, Menu.NONE, "安定");
        menu.add(Menu.NONE, CONTEXT_MENU_ACTUAL, Menu.NONE, "最新");
        menu.add(Menu.NONE, CONTEXT_MENU_TEST, Menu.NONE, "開発");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        Uri apkUri=null;
        switch (item.getItemId()) {

           case CONTEXT_MENU_DOWNLOAD:
                mBTService.stop();
                apkUri = Uri.parse("https://www.air-logi.com/app/air-logi/pic_stable.apk");
                Intent downloadIntent1 = new Intent(Intent.ACTION_VIEW, apkUri);
                startActivity(downloadIntent1);
                break;
            case CONTEXT_MENU_ACTUAL:
                mBTService.stop();
                apkUri = Uri.parse("https://www.air-logi.com/app/air-logi/pic_latest.apk");
                Intent downloadIntent2 = new Intent(Intent.ACTION_VIEW, apkUri);
                startActivity(downloadIntent2);
                break;

            case CONTEXT_MENU_TEST:
                mBTService.stop();
                apkUri = Uri.parse("https://www.air-logi.com/app/air-logi/pic_dev.apk");
                Intent downloadIntent = new Intent(Intent.ACTION_VIEW, apkUri);
                startActivity(downloadIntent);
                break;

        }

        SharedPreferences.Editor editor= spDomain.edit();
        editor.putBoolean(ISUSERLOGIN, false);
        editor.commit();
        return super.onContextItemSelected(item);
    }

}
