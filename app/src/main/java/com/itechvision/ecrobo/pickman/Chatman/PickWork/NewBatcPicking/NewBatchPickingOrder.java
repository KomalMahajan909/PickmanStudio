package com.itechvision.ecrobo.pickman.Chatman.PickWork.NewBatcPicking;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Adapter.AdapBatch_rav;
import com.itechvision.ecrobo.pickman.Adapter.AdapBatch_tas;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BatchPick_Request;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BatchpickResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BatchpickResponse_result;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

import static com.itechvision.ecrobo.pickman.Chatman.PickWork.NewBatcPicking.NewdbatchPickingActivity.Datte;

public class NewBatchPickingOrder extends BaseActivity implements View.OnClickListener, DataManager.GetOrderBatchcall, AdapBatch_tas.onClickCallback {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.setDate)
    EditText setedt;
    @BindView(R.id.list)
    RecyclerView list;
    ArrayList<BatchpickResponse_result> arr ;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    protected List<Map<String, String>> mBatchList = null;
    ArrayList<String> inspectionstatusList= new ArrayList<>();
    int[] statusarray;
    private String TAG = "BatchPickingOrder";
     String userid;
    long totalbatch =0, totalorders=0;
    AdapBatch_tas adap;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    ECRApplication app=new ECRApplication();
    String adminID="";
    DataManager manger;
    progresBar progress ;
    AdapBatch_tas.onClickCallback adapclick;
   public static boolean click = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newbatchpicking_rav);
        ButterKnife.bind(NewBatchPickingOrder.this);
        adapclick= this;
        getIDs();
        Log.d(TAG,"On Create ");
        _sts(R.id.setDate, BaseActivity.getdate());
         spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);

        Log.e("skdjojdojd",">>>>>>>>  "+spDomain);

        userid = spDomain.getString("admin_id",null);
        adminID = spDomain.getString("admin_id", null);

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                    month = month + 1;
                    Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                    String date = "";

                if (month < 10){
                    if (day < 10)
                        date = year + "-0" + month + "-0"+  day;
                    else
                        date = year + "-0" + month + "-"+  day;
                }
                else {
                    if (day < 10)
                    date = year + "-" + month + "-0" + day;
                    else
                        date = year + "-" + month + "-" + day;
                }
                    setedt.setText(date);
             }
        };


        setedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 showTruitonDatePickerDialog(setedt);
            }
        });
    }
    @OnClick(R.id.setbtn) void dateset (){
        if(_gts(R.id.setDate).equals("")){
            U.beepError(this,"No date selected");
        }
        else {
           // Globals.getterList = new ArrayList<>();

            adminID = spDomain.getString("admin_id", null);
            Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());


            progress.Show();
            BatchPick_Request req = new BatchPick_Request(app.getSerial(),adminID, BaseActivity.getShopId(),_gts(R.id.setDate));
            manger.BatchPick(req,this);
         /*   Globals.getterList.add(new ParamsGetter("admin_id", adminID));
            Globals.getterList.add(new ParamsGetter("create_date", _gts(R.id.setDate)));
             BaseActivity.setdate(_gts(R.id.setDate));
            new MainAsyncTask(this, Globals.Webservice.batchpicking, 1, NewBatchPickingOrder.this, "Form", Globals.getterList, true).execute();
*/        }
    }

    public void showTruitonDatePickerDialog(View v) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                NewBatchPickingOrder.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "TASバッチ選択", " ", 0, false,false,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);

        progress = new progresBar(this);
        manger = new DataManager();
        arr = new ArrayList<>();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;

            default:
                break;
        }}
    @Override
    public void inputedEvent(String buff) {

    }

    @Override
    public void clearEvent() {

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


    public void nextProcess() {

    }

    @Override
    public void allclearEvent() {
        CommonDialogs.customToast(this, "No action");
    }

    @Override
    public void skipEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (click==true){
            progress.Show();
            BatchPick_Request req = new BatchPick_Request(app.getSerial(),spDomain.getString("admin_id", null), BaseActivity.getShopId(),_gts(R.id.setDate));
            manger.BatchPick(req,this);

        }else if ((NewdbatchPickingActivity.back.equalsIgnoreCase("1"))){
            setedt.setText(Datte);
            progress.Show();
            BatchPick_Request req = new BatchPick_Request(app.getSerial(),spDomain.getString("admin_id", null), BaseActivity.getShopId(),Datte);
            manger.BatchPick(req,this);
        }else{

        }
    }

    @Override
    public void onSucess(int status, BatchpickResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){
            arr= message.getResults();
            if (arr.size()!=0){
                 click = true ;
                 adap = new AdapBatch_tas(this, arr,adapclick);
                 RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                 list.setLayoutManager(mLayoutManager);
                 list.setItemAnimator(new DefaultItemAnimator());
                 list.setAdapter(adap);

            }else{
                        new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage("TAS可能なデータが存在しません。")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                adap = new AdapBatch_tas(NewBatchPickingOrder.this, arr,adapclick);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(NewBatchPickingOrder.this);
                                list.setLayoutManager(mLayoutManager);
                                list.setItemAnimator(new DefaultItemAnimator());
                                list.setAdapter(adap);

                                dialog.dismiss();
                            }
                        })

                        .show();
            }

        }else if(message.getCode().equalsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent in = new Intent(NewBatchPickingOrder.this, LoginActivity.class);
                            in.putExtra("ACTION", "logout" );
                            startActivity(in);
                            finish();

                            dialog.dismiss();
                        }
                    })
                     .show();
        }else if (message.getCode().equalsIgnoreCase("401")){
            U.beepError(this,message.getMessage());
            Log.e("khdfhkdf",message.getMessage());
        }else{
            U.beepError(this,message.getMessage());

            Log.e("khdfhkdf",message.getMessage()); }

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

    @Override
    public void onClick(int position) {
        if(arr.get(position).getBatch_status().equalsIgnoreCase("2")){

        }else{

            if (arr.get(position).getInvoice_status().equalsIgnoreCase("2")){
                Intent in = new Intent(NewBatchPickingOrder.this, NewdbatchPickingActivity.class);
                in.putExtra("date",_gts(R.id.setDate));
                in.putExtra("batchid",arr.get(position).getBatch_id());
                in.putExtra("adminid", spDomain.getString("admin_id", null));
                in.putExtra("shopid", BaseActivity.getShopId());
                in.putExtra("authid", app.getSerial());
                startActivity(in);
            }else{
                Intent in = new Intent(NewBatchPickingOrder.this, NewinvoiceCheckActivity.class);
                in.putExtra("date",_gts(R.id.setDate));
                in.putExtra("batchid",arr.get(position).getBatch_id());
                in.putExtra("adminid", spDomain.getString("admin_id", null));
                in.putExtra("shopid", BaseActivity.getShopId());
                in.putExtra("authid", app.getSerial());
                startActivity(in);
            }

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG,"On Destroy ");

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}