package com.itechvision.ecrobo.pickman.Chatman.PickWork.NewBatcPicking;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BatchpickResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BatchpickResponse_result;
import com.itechvision.ecrobo.pickman.Models.BatchPick.CheckTasOrder.CheckTasorderRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.CheckTasOrder.TasworkingData;
import com.itechvision.ecrobo.pickman.Models.BatchPick.CheckTasOrder.TasworkingRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.CheckTasOrder.TasworkingRespose;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class NewtaspickDeliverynotesActivity extends BaseActivity implements DataManager.GetCheckTasordercall, DataManager.GetTasworkingrcall {

    @BindView(R.id.a) RelativeLayout a;
    @BindView(R.id.img1ActionBar) ImageView img1ActionBar;
    @BindView(R.id.b) LinearLayout b;
    @BindView(R.id.setDate) EditText setDate;
    @BindView(R.id.c) LinearLayout c;
    @BindView(R.id.notedelivery) EditText notedelivery;
    @BindView(R.id.d) Space d;
    @BindView(R.id.layout_number) RelativeLayout layoutNumber;
    public static final int PROC_BARCODE = 4;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    protected int mProcNo = 0;
     private String TAG = "BatchPickingOrder";
    String userid;
    long totalbatch =0, totalorders=0;
     private DatePickerDialog.OnDateSetListener mDateSetListener;
     ECRApplication app=new ECRApplication();
    String adminID="";
    DataManager manger;
    progresBar progress ;
    String date = "";
    private boolean showKeyboard = false;
    public Context mcontext = this;

    static SlidingMenu menu;
    SlideMenu slidemenu;
    ArrayList<BatchpickResponse_result> arr ;
    ArrayList<TasworkingData> tasworking ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtaspick_deliverynotes);
        ButterKnife.bind(this);
        Log.d(TAG,"On Create ");

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        showKeyboard = BaseActivity.getaddKeyboard();
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        setProc(PROC_BARCODE);
        Log.e("skdjojdojd",">>>>>>>>  "+spDomain);

        manger = new DataManager();
        progress= new progresBar(this);
        arr = new ArrayList<>();
        tasworking = new ArrayList<>();
        userid = spDomain.getString("admin_id",null);
        adminID = spDomain.getString("admin_id", null);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         date = df.format(c);
        setDate.setText(date);


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);


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
                setDate.setText(date);

            }
        };


        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonDatePickerDialog(setDate);
            }
        });

        img1ActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.showMenu();
            }
        });


        progress.Show();
        TasworkingRequest req = new TasworkingRequest(app.getSerial(),adminID, BaseActivity.getShopId()  );
        manger.Tasworking(req,this);


    }

    public void showTruitonDatePickerDialog(View v) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                NewtaspickDeliverynotesActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

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
    public void nextProcess() {
    }

    @Override
    public void inputedEvent(String buff) {
        inputedEvent(buff, false);

    }

    @Override
    public void clearEvent() {
        notedelivery.setText("");



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
                _sts(R.id.notedelivery, buff);

                break;

        }

    }


    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {

            case PROC_BARCODE:
                _gt(R.id.notedelivery).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));

                break;

        }
    }

    public void inputedEvent(String buff, boolean isScaned) {
        switch (mProcNo) {

            case PROC_BARCODE:
                String barcode = _gts(R.id.notedelivery);
                //  Log.e(TAG,"inputedEvent_BARCODE  "+barcode);

                if (barcode.equals("")) {
                    U.beepError(this, "バーコードは必須です");
                    _gt(R.id.notedelivery).setFocusableInTouchMode(true);
                    break;
                }
                //   boolean match = checkBarcode(buff);
              //API
                progress.Show();
CheckTasorderRequest req = new CheckTasorderRequest(app.getSerial(),adminID, BaseActivity.getShopId(),date,notedelivery.getText().toString());
                manger.CheckTasOrder(req,this);
                break;

        }
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
                        Log.e(TAG, "Barcode after chopping first and last character becomes " + result);
                        barcode = result;
                    }
                }
                String finalbarcode = CommonFunctions.getBracode(barcode);
                barcode = finalbarcode;
                _sts(R.id.notedelivery, barcode);
                //  setProc(PROC_QTY);
            }


        }
        this.inputedEvent(barcode, true);
    }






    @Override
    public void enterEvent() {

    }
    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {

            case PROC_BARCODE:    // バーコード
                _sts(R.id.notedelivery, barcode);
                break;

        }
    }


    @OnClick(R.id.save) void onSaveClick() {
        //TODO implement
        Intent in = new Intent(this, TasbatchlistActivity.class);
        in.putExtra("authid",app.getSerial());
        in.putExtra("adminid",adminID);
        in.putExtra("shopid", BaseActivity.getShopId());
        in.putExtra("date",date);
        startActivity(in);

    }


    @OnClick(R.id.setbtn) void onSetbtnClick() {
        //TODO implement
    }


    @OnClick(R.id.add_layout) void onAddLayoutClick() {
        //TODO implement

        if (showKeyboard == false) {
            layoutNumber.setVisibility(View.VISIBLE);
            showKeyboard = true;
        } else {
            layoutNumber.setVisibility(View.GONE);
            showKeyboard = false;
        }
    }



    @OnClick(R.id.enter) void onEnterClick() {
        //TODO implement
        if (notedelivery.getText().toString().length()==0){
            U.beepError(this,"Please Enter OrderID");
        }else{
            progress.Show();
            CheckTasorderRequest req = new CheckTasorderRequest(app.getSerial(),adminID, BaseActivity.getShopId(),date,notedelivery.getText().toString());
            manger.CheckTasOrder(req,this);
        }



    }



    @OnClick(R.id.clear) void onClearClick() {
        //TODO implement
    }




    @Override
    public void onSucess(int status, BatchpickResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){
            arr= message.getResults();
            if (arr.size()!=0){
                if(arr.get(0).getBatch_status().equalsIgnoreCase("2")){

                }else{

                    if (arr.get(0).getInvoice_status().equalsIgnoreCase("2")){

                        Intent in = new Intent(NewtaspickDeliverynotesActivity.this, NewdbatchPickingActivity.class);
                        in.putExtra("date",_gts(R.id.setDate));
                        in.putExtra("batchid",arr.get(0).getBatch_id());
                        in.putExtra("adminid", spDomain.getString("admin_id", null));
                        in.putExtra("shopid", BaseActivity.getShopId());
                        in.putExtra("authid", app.getSerial());
                        startActivity(in);

                    }else{

                        Intent in = new Intent(NewtaspickDeliverynotesActivity.this, NewinvoiceCheckActivity.class);
                        in.putExtra("date",_gts(R.id.setDate));
                        in.putExtra("batchid",arr.get(0).getBatch_id());
                        in.putExtra("adminid", spDomain.getString("admin_id", null));
                        in.putExtra("shopid", BaseActivity.getShopId());
                        in.putExtra("authid", app.getSerial());
                        startActivity(in);

                    }


                }
            }else{
                new AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage("No Data Found on this Selected Date.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                dialog.dismiss();
                            }
                        })

                        .show();
            }

        }else if(message.getCode().equalsIgnoreCase("1020")){
            new AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent in = new Intent(NewtaspickDeliverynotesActivity.this, LoginActivity.class);
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
    public void onSucess(int status, TasworkingRespose message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){
            tasworking = message.getResult() ;
            if (tasworking.size()!=0){

              //  String msg = "Batch no "+ tasworking.get(0).getBatch_id()+ " is pending on date of " + tasworking.get(0).getCreate_date()+". Do you want to complete this batch?" ;
               String msg = "バッチ番号"+ tasworking.get(0).getBatch_no()+ "は" + tasworking.get(0).getCreate_date()+"に作業が中断されています。中断されたバッチの作業を続けますか?" ;

                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(NewtaspickDeliverynotesActivity.this);
                builder.setMessage(msg);
                builder.setTitle("アラート !");
                builder.setCancelable(false);

                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                // When the user click yes button
                                // then app will close
                                if(tasworking.get(0).getBatch_status().equalsIgnoreCase("2")){

                                }else{

                                    if (tasworking.get(0).getInvoice_status().equalsIgnoreCase("2")){

                                        Intent in = new Intent(NewtaspickDeliverynotesActivity.this, NewdbatchPickingActivity.class);
                                        in.putExtra("date",tasworking.get(0).getCreate_date());
                                        in.putExtra("batchid",tasworking.get(0).getBatch_id());
                                        in.putExtra("adminid", spDomain.getString("admin_id", null));
                                        in.putExtra("shopid", BaseActivity.getShopId());
                                        in.putExtra("authid", app.getSerial());
                                        startActivity(in);

                                    }else{

                                        Intent in = new Intent(NewtaspickDeliverynotesActivity.this, NewinvoiceCheckActivity.class);
                                        in.putExtra("date",tasworking.get(0).getCreate_date());
                                        in.putExtra("batchid",tasworking.get(0).getBatch_id());
                                        in.putExtra("adminid", spDomain.getString("admin_id", null));
                                        in.putExtra("shopid", BaseActivity.getShopId());
                                        in.putExtra("authid", app.getSerial());
                                        startActivity(in);

                                    }


                                }
                            }
                        });

                builder .setNegativeButton( "No",   new DialogInterface .OnClickListener() {
                                     @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        dialog.cancel();
                                    }
                                });
                  AlertDialog alertDialog = builder.create();
                 alertDialog.show();



            }else{

            }

        }else if(message.getCode().equalsIgnoreCase("1020")){
            new AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent in = new Intent(NewtaspickDeliverynotesActivity.this, LoginActivity.class);
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
}

