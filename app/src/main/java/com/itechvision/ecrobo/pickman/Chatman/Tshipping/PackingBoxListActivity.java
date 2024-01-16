package com.itechvision.ecrobo.pickman.Chatman.Tshipping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Adapter.BoxCashRegister.PackingBoxAdapter;
import com.itechvision.ecrobo.pickman.Adapter.BoxCashRegister.PackingBoxlistclickAdapter;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxCashList;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxCashOrderIDRequest;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxCashOrderIDResp;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxProductclickList.BoxProductClickRequest;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxProductclickList.BoxProductclickListResponse;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Packing.PackListData;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Recreate.BoxRecreateReq;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Recreate.BoxRecreateResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.itechvision.ecrobo.pickman.Util.U;
import com.rey.material.app.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class PackingBoxListActivity extends AppCompatActivity implements DataManager.GetBoxRecreatecallback,
        DataManager.GetOrderIDCallback, PackingBoxAdapter.OnCheckBoxClick,CompoundButton.OnCheckedChangeListener,
        PackingBoxAdapter.OnlistClick,DataManager.boxproductlistclickcallback
{

    @BindView(R.id.list1)
    RecyclerView listview;
    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.Allselect)
    CheckBox allselectCheck;

    PackingBoxAdapter packadapter;
    public List<PackListData> list = new ArrayList<>();
    public static ArrayList<BoxCashList> productList = new ArrayList<>();

    progresBar progress;
    private DataManager manager;
    private DataManager.GetBoxRecreatecallback getBoxRecreatecallback;
    private DataManager.GetOrderIDCallback getOrderIDCallback;

    protected String orderId = "";
    public static String packed_count = "0";

    PackingBoxAdapter.OnlistClick listclick;
    BottomSheetDialog bottomSheetDialog ;
    RecyclerView  listpack ;
    DataManager.boxproductlistclickcallback getboxlist ;
    PackingBoxlistclickAdapter addp ;

    ECRApplication app = new ECRApplication();

    public String adminID = "", ID = "";
    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPrefs";
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
String TAG = PackingBoxListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_packing_box_list);
        ButterKnife.bind(this);


        progress= new progresBar(this);
        manager = new DataManager();
        getBoxRecreatecallback = this;
        getOrderIDCallback = this;
        listclick = this;
        getboxlist = this;
        Log.d(TAG,"On Create ");
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);


        adminID = spDomain.getString("admin_id", null);
        ID = BaseActivity.getShopId();

        Intent i = getIntent();
        if (i.hasExtra("orderId"))
            orderId = i.getStringExtra("orderId");

        list = BoxCashRegisterActivity.packingList;

        packadapter = new PackingBoxAdapter(this,list,this,listclick);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        listview.setLayoutManager(mLayoutManager);
        listview.setItemAnimator(new DefaultItemAnimator());
        listview.setAdapter(packadapter);
        packadapter.notifyDataSetChanged();

        allselectCheck.setOnCheckedChangeListener(this);
    }

    @OnClick(R.id.close) void close(View view){
        Intent i = new Intent();
        i.putExtra(BoxCashRegisterActivity.BACK, "back");
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    @OnClick(R.id.recreateBtn) void recreateBtn(View view) {

        StringBuffer boxSize = new StringBuffer();

        for (PackListData val : list) {
            if (val.isChecked()) {
                boxSize.append(",").append(val. getBox_no());
            }
        }
        if(boxSize.length()>1) {
            if (!CommonUtilities.getConnectivityStatus(PackingBoxListActivity.this))
                CommonUtilities.openInternetDialog(PackingBoxListActivity.this);
            else{
                progress.Show();
                BoxRecreateReq req = new BoxRecreateReq(adminID,app.getSerial(), ID,orderId, boxSize.substring(1));
                manager.GetBoxRecreateAPI(req,getBoxRecreatecallback);
            }

        } else {
            U.beepError(this, "箱番号を入力してください。");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        RecyclerView.Adapter currentadapter = listview.getAdapter();
        if(currentadapter instanceof PackingBoxAdapter){
            for(PackListData val: list){
                val.setChecked(isChecked);
            }
            packadapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCheckboxItemClickListener(PackListData val, boolean isChecked) {

    }

    @Override
    public void onSucess(int status, BoxRecreateResponse message) throws JsonIOException {
        progress.Dismiss();
        String result = message.getResult();
        if(result!= null)
        {

        }
        else{
            result = "";
        }
        if (result .equalsIgnoreCase("OK")) {

            progress.Dismiss();
            packed_count = message.getPacked_count();

            if (!CommonUtilities.getConnectivityStatus(this))
                CommonUtilities.openInternetDialog(this);
            else {
                progress.Show();
                BoxCashOrderIDRequest req = new BoxCashOrderIDRequest (adminID,app.getSerial(), ID, orderId,getResources().getString(R.string.version));
                manager.GetBoxOrderID(req ,getOrderIDCallback);
            }

        }
        else {
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, BoxCashOrderIDResp message) throws JsonIOException {
        progress.Dismiss();
        String result = message.getResult();
        if (result != null) {

        } else {
            result = "";
        }
        if (result.equalsIgnoreCase("OK")) {
            productList = message.getData();

            Intent i = new Intent();
            i.putExtra(BoxCashRegisterActivity.NEXTBOX, BoxCashRegisterActivity.ADDNEXTBOX);
            setResult(Activity.RESULT_OK, i);
            finish();
        }
        else
            U.beepError(this,message.getMessage());
    }

    //list
    @Override
    public void onSucess(int status, BoxProductclickListResponse message) throws JsonIOException {

        progress.Dismiss();
        String result = message.getResult();
        if (result != null) {

        } else {
            result = "";
        }
        if (result.equalsIgnoreCase("OK")) {
                addp = new PackingBoxlistclickAdapter(this,message.getProduct_list());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                listpack.setLayoutManager(mLayoutManager);
                listpack.setItemAnimator(new DefaultItemAnimator());
                listpack.setAdapter(addp);
                addp.notifyDataSetChanged();

        }
        else U.beepError(this, message.getMessage());
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

    //listclick
    @Override
    public void OnlistClick(int position) {
        showBottomSheetDialog(position);
    }


    private void showBottomSheetDialog(int position) {

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.packingboxclicklist);
        bottomSheetDialog.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);

        listpack = (RecyclerView) bottomSheetDialog.findViewById(R.id.list1);
        ImageView close = (ImageView) bottomSheetDialog.findViewById(R.id.close);

        progress.Show();
        BoxProductClickRequest req = new BoxProductClickRequest(adminID,app.getSerial(), ID, orderId,list.get(position).getBox_no());
        manager.boxproductlistclick(req,getboxlist)  ;

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }

}