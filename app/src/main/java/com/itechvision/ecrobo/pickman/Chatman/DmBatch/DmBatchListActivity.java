package com.itechvision.ecrobo.pickman.Chatman.DmBatch;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Adapter.AdapBatchListDaimaru;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BatchListModel.BatchListData;

import com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmBatchListModel.DmBatchListRequest;
import com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmBatchListModel.DmBatchListResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class DmBatchListActivity extends BaseActivity implements View.OnClickListener, AdapBatchListDaimaru.onClickCallback, CompoundButton.OnCheckedChangeListener, DataManager.DmBatchListcall{
    static SlidingMenu menu;
    SlideMenu slidemenu;

    @BindView(R.id.date)
    TextView etDate;
    @BindView(R.id.cbpending)
    CheckBox cbpending;
    @BindView(R.id.cbworking)
    CheckBox cbworking;
    @BindView(R.id.cbdone)
    CheckBox cbdone;
    @BindView(R.id.cbremark)
    CheckBox cbremark;
    @BindView(R.id.cbnoremark)
    CheckBox cbnoremark;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.scrollMain)
    ScrollView scrollMain;

    DataManager manager;
    progresBar progress;

    ECRApplication app = new ECRApplication();
    String adminID = "";

    DataManager.DmBatchListcall batchListcall;
    AdapBatchListDaimaru adap;
    AdapBatchListDaimaru.onClickCallback adapclick;

    String cb_pending, cb_working, cb_done;
    public Context mcontext = this;

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public static final int BATCH_ACTIVITY = 11;

    ArrayList<BatchListData> batchList = new ArrayList<>();
    String username;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String TAG = DmBatchListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm_batch_list);

            ButterKnife.bind(this);

            getIDs();

            manager = new DataManager();
            progress = new progresBar(this);
            batchListcall = this;
            adapclick = this;

            spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
            adminID = spDomain.getString("admin_id", null);

            username = spDomain.getString("user_name","");
            cbworking.setOnCheckedChangeListener(this);
            cbpending.setOnCheckedChangeListener(this);
            cbdone.setOnCheckedChangeListener(this);
            cbremark.setOnCheckedChangeListener(this);
            cbnoremark.setOnCheckedChangeListener(this);


            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                    String date = "";

                    if (month < 10) {
                        if (day < 10)
                            date = year + "-0" + month + "-0" + day;
                        else
                            date = year + "-0" + month + "-" + day;
                    } else {
                        if (day < 10)
                            date = year + "-" + month + "-0" + day;
                        else
                            date = year + "-" + month + "-" + day;
                    }
                    etDate.setText(date);
                }
            };
            etDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTruitonDatePickerDialog(etDate);
                }
            });
            scrollToView(scrollMain, _g(R.id.date));
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

        //set title and icons on actionbar
        private void getIDs() {
            // TODO Auto-generated method stub

            CommonUtilities.actionbarImplement(this, "Dバッチ", " ",
                    0, false, false, false);
            //menubarr
            menu = CommonUtilities.setSlidingMenu(this);
            slidemenu = new SlideMenu(menu, this);

            CommonUtilities.relLayout1.setOnClickListener(this);

            progress = new progresBar(this);

        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId()) {

                case R.id.relLayout1:

                    menu.showMenu();
                    break;

                default:
                    break;
            }
        }

        public void showTruitonDatePickerDialog(View v) {

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,
                    year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }


        @Override
        public void onClick(int position) {
//        eop = position;
            if (!batchList.get(position).getBatch_status().equalsIgnoreCase("2") ) {
                if (!batchList.get(position).getBatch_status().equalsIgnoreCase("1") ) {
                    Intent i = new Intent(DmBatchListActivity.this, DmBatchBoxActivity.class);
                    i.putExtra("Batch_id", batchList.get(position).getBatch_id());
                    i.putExtra("Batch_no", batchList.get(position).getBatch_no());
                    i.putExtra("date", etDate.getText().toString());
                    i.putExtra("auth_id", app.getSerial());
                    i.putExtra("invoice_status", batchList.get(position).getInvoice_status());
                    i.putExtra("admin_id", adminID);

                    startActivityForResult(i, BATCH_ACTIVITY);
                }
                else if(username.equalsIgnoreCase(batchList.get(position).getUsername()))
                {
                    Intent i = new Intent(DmBatchListActivity.this, DmBatchBoxActivity.class);
                    i.putExtra("Batch_id", batchList.get(position).getBatch_id());
                    i.putExtra("Batch_no", batchList.get(position).getBatch_no());
                    i.putExtra("date", etDate.getText().toString());
                    i.putExtra("auth_id", app.getSerial());
                    i.putExtra("invoice_status", batchList.get(position).getInvoice_status());
                    i.putExtra("admin_id", adminID);

                    startActivityForResult(i, BATCH_ACTIVITY);
                }
            } else {
//            showBottomSheetDialog(position);
            }
        }

        @OnClick(R.id.enter)
        void enter(){
            if(etDate.getText().toString().equals("")) {
                U.beepError(this, "日付が選択されていません");
                return;
            }
            String batchstatus = "";
            String batchremark = "";

            if(cbnoremark.isChecked())
                batchremark= "0";
            if(cbremark.isChecked()){
                if(!batchremark.equalsIgnoreCase(""))
                    batchremark = batchremark + ",1";
                else
                    batchremark = "1";
            }

                if(cbpending.isChecked())
                    batchstatus = "0";
            if(cbworking.isChecked()) {
                if(!batchstatus.equalsIgnoreCase(""))
                    batchstatus = batchstatus + ",1";
                else
                    batchstatus = "1";
            }
            if(cbdone.isChecked()) {
                if(!batchstatus.equalsIgnoreCase(""))
                    batchstatus = batchstatus + ",2";
                else
                    batchstatus = "2";
            }

        Log.e(TAG,">>>111  "+batchstatus );

            if(CommonUtilities.getConnectivityStatus(this)) {

                progress.Show();
                DmBatchListRequest request = new DmBatchListRequest(adminID, app.getSerial(), BaseActivity.getShopId(),getResources().getString(R.string.version),etDate.getText().toString(),batchstatus,batchremark);
                manager.DmBatchListAPI(request, batchListcall);
            } else {

                CommonUtilities.openInternetDialog(this);
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            Log.e("sdfghjkcvbnmghkcvbn    ", ">>>>>>>>>>>>>>>>>>>>>>>>" + requestCode + ",  " + resultCode + ",  " + data);
            if (requestCode == BATCH_ACTIVITY) {
                enter();

            }
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.cbpending:
                    if (cbpending.isChecked())
                        cb_pending = "1";
                    else
                        cb_pending = "0";
                    break;
                case R.id.cbworking:
                    if (cbworking.isChecked())
                        cb_working = "1";
                    else
                        cb_working = "0";
                    break;
                case R.id.cbdone:
                    if (cbdone.isChecked())
                        cb_done = "1";
                    else
                        cb_done = "0";
                    break;

            }
        }

        @Override
        public void onSucess(int status, DmBatchListResponse message) throws JsonIOException {
            progress.Dismiss();
            if (message.getCode().equalsIgnoreCase("0")) {
                batchList = new ArrayList<>();

                batchList = message.getResults();

                if (batchList.size() != 0) {

                    nodata.setVisibility(View.INVISIBLE);

//                click = true ;
                    adap = new AdapBatchListDaimaru(this, batchList, adapclick);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                    list.setLayoutManager(mLayoutManager);
                    list.setItemAnimator(new DefaultItemAnimator());
                    list.setAdapter(adap);
                }
                else {
                    U.beepError(this, null);
                    nodata.setVisibility(View.VISIBLE);
                    list.setAdapter(null);
                }
            }
            else
                U.beepError(this, message.getMessage());

        }

    @Override
    public void onError(int status, ResponseBody error) {

    }


    @Override
        public void onFaliure() {

        }

        @Override
        public void onNetworkFailure() {

        }

    }