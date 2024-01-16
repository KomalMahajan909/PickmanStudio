package com.itechvision.ecrobo.pickman.Chatman.PickWork.NewBatcPicking;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Adapter.Adap_productlist;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.BarcodePickRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.ProductList.ProductData;
import com.itechvision.ecrobo.pickman.Models.BatchPick.ProductList.ProductlistResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.Util.U;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class BatchProductlistActivity extends Activity implements DataManager.GetProductcall {

    @BindView(R.id.back) ImageView back;
    @BindView(R.id.barcode) TextView barcode;
    @BindView(R.id.list) ListView list;
    String TAG = BatchProductlistActivity.class.getSimpleName();

    String Date="",shipid="",authod="",adminid="",batchid="",Barcode="" ;
    DataManager manager;
    progresBar progress;
    Adap_productlist adap ;
    ArrayList<ProductData> arr ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.batch_productlist);
        ButterKnife.bind(this);
        Log.d(TAG,"On Create ");

        Intent intent = getIntent();

        Date=intent.getStringExtra("date");
        shipid=intent.getStringExtra("shopid");
        authod=intent.getStringExtra("authid");
        adminid=intent.getStringExtra("adminid");
        batchid=intent.getStringExtra("batchid");
        Barcode=intent.getStringExtra("barcode");
        progress = new progresBar(this);
        manager = new DataManager();
        arr = new ArrayList<>();

        barcode.setText(Barcode);

        progress.Show();
        BarcodePickRequest req = new BarcodePickRequest(authod,adminid,shipid,Date,batchid, Barcode );
        manager.ProductCheck(req,this);



    }

    @OnClick(R.id.back)  void back(){
        finish();
    }

    @Override
    public void onSucess(int status, ProductlistResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("0")){

            arr = message.getBarcode_data();

            if (arr.size()!=0){
                adap = new Adap_productlist(this,arr);
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
                            Intent in = new Intent(BatchProductlistActivity.this, LoginActivity.class);
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
