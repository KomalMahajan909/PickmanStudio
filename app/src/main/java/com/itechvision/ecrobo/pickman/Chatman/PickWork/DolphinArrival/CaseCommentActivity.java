package com.itechvision.ecrobo.pickman.Chatman.PickWork.DolphinArrival;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Activity.ScannerBindActivity;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.TotalArival.TotalArrivalSubmitReq;
import com.itechvision.ecrobo.pickman.Models.TotalArival.TotalArrivalSubmitResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

import static com.itechvision.ecrobo.pickman.Chatman.BaseActivity.FINISH;

public class CaseCommentActivity extends ScannerBindActivity implements DataManager.TotalArrivalSubmissioncallback{

    @BindView(R.id.quantity) TextView case_quantity;
    @BindView(R.id.productcode) TextView productcode;
    @BindView(R.id.productname) TextView productname;
    @BindView(R.id.comment) EditText comment;
    @BindView(R.id.list) ListView list;
    @BindView(R.id.txt_total_qty)TextView totalQty;
    @BindView(R.id.txt_total_plate)TextView total_plate;
    @BindView(R.id.txt_total_no)TextView txt_total_no;
    @BindView(R.id.enter)
    Button enter;

    ECRApplication app = new ECRApplication();
    public String adminID = "", ID = "";
    SharedPreferences spDomain;
    public Context mcontext = this;
    public static final String DOMAINPREFERENCE = "domain";

    DataManager manager;
    progresBar progress;
    DataManager.TotalArrivalSubmissioncallback totalarrivecall;
    ArrayList<Map<String,String>> datta= new ArrayList<>();
    long TIME = 1 * 1000;

    String caseQty = "",attribute_type= "", date ="", code ="",barcode = "",et_date="",
            name= "", lot = "",nyukaId= "", productId="",comm="";
    String TAG = CaseCommentActivity.class.getSimpleName();
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_case_comment);

        ButterKnife.bind(this);


        manager = new DataManager();
        progress = new progresBar(this);
        totalarrivecall = this;
        Log.d(TAG,"On Create ");

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);

        Intent intent = getIntent();
        if(intent.hasExtra("caseQty")){

            caseQty = intent.getStringExtra("caseQty");
            attribute_type = intent.getStringExtra("attribute_type");
            date= intent.getStringExtra("arrival_date");
            code= intent.getStringExtra("code");
            name = intent.getStringExtra("product_name");
            lot = intent.getStringExtra("lot_no");
            nyukaId= intent.getStringExtra("nyuka_id");
            productId = intent.getStringExtra("product_id");
            comm = intent.getStringExtra("comment");
            ID = intent.getStringExtra("shopID");
            barcode = intent.getStringExtra("barcode");
            et_date = intent.getStringExtra("date");

        }

        datta = CaseQuantityActivity.datta;

        productcode.setText(code);
        productname.setText(name);
        productname.setSelected(true);

        if(!comm.equalsIgnoreCase(""))
            comment.setText(comm);

        comment.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
        //  comment.setFocusableInTouchMode(true);
        CommonUtilities.hideKeyboard(this,comment);
        setList();

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

    @OnClick(R.id.backbtn) void back(){
        Intent i = new Intent();
        i.putExtra(FINISH,"finish");
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    @OnClick(R.id.enter) void Enter() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        else {
            progress.Show();
            StringBuffer caseval = new StringBuffer();
            StringBuffer qty = new StringBuffer();
            StringBuffer plate = new StringBuffer();
            StringBuffer case_qty = new StringBuffer();
            for (Map<String, String> map : datta) {
                if (!map.get("case").equals("0")) {
                    caseval.append(",").append(map.get("case"));
                    qty.append(",").append(map.get("qty"));
                    plate.append(",").append(map.get("platee"));
                    case_qty.append(",").append(map.get("totalQty"));
                }
            }

            String printer = "";
            if (BaseActivity.getPrinterSelected()) {
                printer = BaseActivity.getbarcodeselectedPrinterID();
            }

            TotalArrivalSubmitReq req = new TotalArrivalSubmitReq(adminID, app.getSerial(), ID, getResources().getString(R.string.version),
                    productId, nyukaId, caseval.substring(1), case_qty.substring(1), qty.substring(1), plate.substring(1), comment.getText().toString(), printer);
            manager.TotalArrivalSubmit(req, totalarrivecall);

                /*TotalArrivalSubmitReq req = new TotalArrivalSubmitReq(adminID,app.getSerial(), ID,  getResources().getString(R.string.version),
                productId, nyukaId,  caseval.substring(1), qty.substring(1),case_qty.substring(1), plate.substring(1), comment.getText().toString());
                manager.TotalArrivalSubmit(req, totalarrivecall);*/
        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }


    void setList(){
        ListViewItems data = new ListViewItems();
        String qty= "0", plateqty = "0", totalqty = "0";
        for (int i = 0; i < datta.size(); i++) {
            Map<String, String>map = new HashMap<>();
            map= datta.get(i);

            data.add(data.newItem()
                    .add(R.id.txt_1, map.get("case_qty"))
                    .add(R.id.txt_2,  map.get("case"))
                    .add(R.id.txt_3,  map.get("qty"))
                    .add(R.id.txt_4,  map.get("platee"))
                    .add(R.id.txt_5,map.get("totalQty")));

            Log.e(TAG, "Quantityyyyy  "+qty+"   "+map.get("qty"));

            qty = U.plusTo(qty,map.get("qty"));
            plateqty = U.plusTo(plateqty,map.get("platee"));
            totalqty = U.plusTo(totalqty, map.get("totalQty"));
        }

        ListViewAdapter adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.case_comment_list_row);

        list.setAdapter(adapter);

        total_plate.setText(plateqty);
        totalQty.setText(qty);

        txt_total_no.setText(totalqty);
        case_quantity.setText(totalqty);
    }

    @Override
    public void onSucess(int status, TotalArrivalSubmitResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")) {
            if(!datta.isEmpty()){
                datta.clear();
                CaseQuantityActivity.datta.clear();
            }

            Intent i = new Intent(CaseCommentActivity.this, TotalArrivalActivity.class);
            startActivity(i);
            finish();

        }else if (message.getCode().equalsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(CaseCommentActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }

                    })
                    .show();

        }  else {
            U.beepError(this, message.getMessage());
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
        Toast.makeText(this, "Intenet not working", Toast.LENGTH_SHORT).show();
    }

//    @OnClick(R.id.next_btn)void next(){
//        String s = mBuff.toString();
//        mBuff.delete(0, mBuff.length());
//        inputedEvent(s);
//    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

}