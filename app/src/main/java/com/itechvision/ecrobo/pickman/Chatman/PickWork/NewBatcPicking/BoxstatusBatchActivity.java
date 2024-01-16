package com.itechvision.ecrobo.pickman.Chatman.PickWork.NewBatcPicking;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Adapter.Adap_Checkbox;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Tshipping.BoxCashRegisterActivity;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BoxDetail.BoxData;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BoxDetail.BoxlistRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BoxDetail.BoxlistResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.Util.U;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class BoxstatusBatchActivity extends BaseActivity implements DataManager.GetBoxststuscall {

    @BindView(R.id.back) ImageView back;
    @BindView(R.id.list) GridView list;
    DataManager manager;
    progresBar progress;
    Adap_Checkbox adap ;
    ArrayList<BoxData> arr ;
    String TAG = BoxstatusBatchActivity.class.getSimpleName();
    String Date="",shipid="",authod="",adminid="",batchid="",Barcode="" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boxstatus_batch);
        ButterKnife.bind(this);
        progress = new progresBar(this);
        manager = new DataManager();
        arr = new ArrayList<>();
        Log.d(TAG,"On Create ");
        Intent intent = getIntent();
        // 98154 69000 - Sai over 2

        Date=intent.getStringExtra("date");
        shipid=intent.getStringExtra("shopid");
        authod=intent.getStringExtra("authid");
        adminid=intent.getStringExtra("adminid");
        batchid=intent.getStringExtra("batchid");
        Barcode=intent.getStringExtra("barcode");

        progress.Show();
        BoxlistRequest req = new BoxlistRequest(authod,adminid,shipid,batchid);
        manager.BoxStatus(req,this);

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

    @Override
    public void nextProcess() {

    }

    @OnClick(R.id.back)  void back(){
        finish();
  }

    @Override
    public void onSucess(int status, BoxlistResponse message) throws JsonIOException {
        progress.Dismiss();


        if (message.getCode().equalsIgnoreCase("0")){
            arr= message.getBatch_status();

            if (arr.size()!=0){
                adap = new Adap_Checkbox(this,arr);
                list.setAdapter(adap);
                adap.notifyDataSetChanged();
            }else{
                U.beepError(this,"No Data Found");
            }
        }else if (message.getCode().equalsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(BoxstatusBatchActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        }else {
            U.beepError(this,message.getMessage());
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
