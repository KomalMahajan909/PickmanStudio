package com.itechvision.ecrobo.pickman.Chatman.Account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.Settings.SettingRequest;
import com.itechvision.ecrobo.pickman.Models.Settings.SettingResult;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.Util.U;
import com.reginald.editspinner.EditSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class SoundSettingsActivity extends Activity implements DataManager.Postsettingcall, DataManager.GetSettingscall
{
    @BindView(R.id.error_spinner) EditSpinner errorSpinner;
    @BindView(R.id.ok_spinner)
    EditSpinner okSpinner;
    @BindView(R.id.back)
    ImageView back;

    String errorarr[] = {"Voice_1", "Voice_2", "Voice_3","Voice_4"};
    String okarr[] = {"Voice_1", "Voice_2", "Voice_3","Voice_4"};

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    String adminid = "",errorVoice = "0", okvoice = "0";


    ECRApplication app=new ECRApplication();

    DataManager manager ;
    progresBar progress ;
    DataManager.GetSettingscall getcall;

    String TAG = SoundSettingsActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sound_settings);


        ButterKnife.bind(this);

        manager = new  DataManager();
        progress = new progresBar(this);
        getcall= this ;

        Log.d(TAG,"On Create ");

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminid = spDomain.getString("admin_id", null);

        errorVoice = BaseActivity.getErrorSound()+"";
        okvoice = BaseActivity.getOkSound()+"";


        Log.e(TAG, "setErrorSound   "+BaseActivity.getErrorSound());
        Log.e(TAG, "setokSound   "+BaseActivity.getOkSound());

        ArrayAdapter adapter1 = new ArrayAdapter (this,R.layout.voice_spinner,errorarr);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_item);

        errorSpinner.setAdapter(adapter1);
        errorSpinner.setText(errorarr[BaseActivity.getErrorSound()]);

        errorSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseActivity.setErrorSound(position);
                U.beepError(SoundSettingsActivity.this, null);
                errorVoice = position+"";
            }

        });


        ArrayAdapter adapter2 = new ArrayAdapter (this,R.layout.voice_spinner,okarr);

        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);

        okSpinner.setAdapter(adapter2);
        okSpinner.setText(okarr[BaseActivity.getOkSound()]);
        okSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseActivity.setOkSound(position);
                U.beepKakutei(SoundSettingsActivity.this, null);
                okvoice = position+"";

            }

        });
    }

    @OnClick(R.id.save) void Save(){
        progress.Show();
        SettingRequest req = new SettingRequest(adminid,app.getSerial(),"","","","",""
                ,"","","","","","","","","",
                "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","" ,"","", "", errorVoice, okvoice,"","","","","","","","","","","", "", "","","","","","","");
        manager.PostSettingStatus(req,this);
    }

    @OnClick(R.id.back) void back(){
        startActivity(new Intent(SoundSettingsActivity.this,SettingActivity.class));
        finish();
    }

    @Override
    public void onSucess(int status, ResponseBody message) throws JsonIOException {
        progress.Dismiss();

        if (status==200){
            progress.Show();
            manager.GetSettings(adminid,app.getSerial(),"",getcall);
        }
    }


    @Override
    public void onSucess(int status, SettingResult message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("200")) {

            Log.e(TAG, ">>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<batchScreenRg" + message.getResult().getNg_tune());

            if (message.getResult().getNg_tune().equalsIgnoreCase("")) {
                BaseActivity.setErrorSound(0);


            } else {
                errorVoice = message.getResult().getNg_tune();
                BaseActivity.setErrorSound(Integer.parseInt(errorVoice));
            }

            if (message.getResult().getOk_tune().equalsIgnoreCase("")) {
                BaseActivity.setOkSound(0);

            } else {
                okvoice = message.getResult().getOk_tune();
                BaseActivity.setOkSound(Integer.parseInt(okvoice));
            }
        }
    }

    @Override
    public void onError(int status, ResponseBody error) {
        progress.Dismiss();
    }

    @Override
    public void onFaliure() {
        progress.Dismiss();
    }

    @Override
    public void onNetworkFailure() {
        progress.Dismiss();
    }
}