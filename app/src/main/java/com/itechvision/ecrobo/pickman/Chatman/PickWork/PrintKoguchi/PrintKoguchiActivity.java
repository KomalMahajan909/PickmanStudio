package com.itechvision.ecrobo.pickman.Chatman.PickWork.PrintKoguchi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.KoguchiActivity;
import com.itechvision.ecrobo.pickman.Models.PrintKoguchi.CSV_spinner.CSVList;
import com.itechvision.ecrobo.pickman.Models.PrintKoguchi.CSV_spinner.GetCSVSpinnerReq;
import com.itechvision.ecrobo.pickman.Models.PrintKoguchi.CSV_spinner.GetCSVSpinnerResult;
import com.itechvision.ecrobo.pickman.Models.PrintKoguchi.KoguchiReqst;
import com.itechvision.ecrobo.pickman.Models.PrintKoguchi.KoguchiResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class PrintKoguchiActivity extends BaseActivity implements View.OnClickListener,DataManager.PrintKoguchiCallback, DataManager.GetCSVSpinnerCallback{
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.koguchispinnerLayout) RelativeLayout spinnerLayout;
    @BindView(R.id.csv_spinnerLayout) RelativeLayout csv_spinnerLayout;

    @BindView(R.id.layout_number) RelativeLayout layout_number;
    @BindView(R.id.koguchiSpinner) EditSpinner koguchiSpinner;
    @BindView(R.id.csv_spinner) EditSpinner CSVSpinner;
    @BindView(R.id.orderid) EditText orderid;

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    ECRApplication app=new ECRApplication();
    String adminID="";
    private TextToSpeak mTextToSpeak;
    String TAG= KoguchiActivity.class.getSimpleName();
    DataManager manager;
    progresBar progress;
    private  DataManager.PrintKoguchiCallback printKoguchiCallback;
    protected int mProcNo = 0;
    public static final int PROC_KOGUCHI = 1;
    public static final int PROC_ORDERID = 2;
    String koguchi_count = "";
    ArrayList <String> sizes = new ArrayList<>();
    ArrayList <CSVList> CSVlist = new ArrayList<>();
    ArrayList <String> list = new ArrayList<>();

    String selectedCSV = "";
    boolean visible = true;
    int pos =0, koguchipos = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_koguchi);
        ButterKnife.bind(this);
        getIDs();
        Log.d(TAG,"On Create ");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);

        manager = new DataManager();
        progress = new progresBar(this);
        printKoguchiCallback = this;

        adminID = spDomain.getString("admin_id",null);
        mTextToSpeak = new TextToSpeak(this);

        koguchipos = BaseActivity.getKoguchiSpinnerPos();

        setKoguchiSpinner();

        GetCSVSpinnerReq req = new GetCSVSpinnerReq(app.getSerial(), BaseActivity.getShopId(), adminID);
        manager.GetCSVSpinnerAPI(req,this);

        if (mProcNo == 0) nextProcess();
        pos = BaseActivity.getCSVSpinnerPos();

    }

    @OnClick(R.id.add_layout) void AddLayout() {
        if(visible) {
            layout_number.setVisibility(View.INVISIBLE);
            visible = false;
        } else{
            layout_number.setVisibility(View.VISIBLE);
            visible = true;
        }
    }

    private void getIDs() {
        actionbarImplement(this, "送り状CSV", " ", 0, false,false,false );
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);
        relLayout1.setOnClickListener(this);
        //  btnBlue.setOnClickListener(this);
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

    public void setProc(int procNo) {
        mProcNo = procNo;
        mBuff.delete(0, mBuff.length());
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {
            case PROC_KOGUCHI:
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                orderid.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                break;
            case PROC_ORDERID:
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                orderid.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                orderid.setFocusableInTouchMode(true);

                break;
        }
    }

    @Override
    public void inputedEvent(String buff) {

        switch (mProcNo) {
            case PROC_KOGUCHI:
                String ar= koguchiSpinner.getText().toString();

                break;
            case PROC_ORDERID:
                String order = orderid.getText().toString();
                if (order.equalsIgnoreCase("") || order.equalsIgnoreCase("0")) {
                    U.beepError(this, "注文番号を入力してください。");
                    setProc(PROC_ORDERID);
                }
                if (koguchiSpinner.getText().toString().equalsIgnoreCase("")) {
                    if(koguchipos ==0){
                        U.beepError(this, "個口を入力してください。");
                        setProc(PROC_ORDERID);
                        break;}
                    else{
                        U.beepError(this, "個口を入力してください。");
                        setProc(PROC_ORDERID);
                        break;

                    }}

                else {
                    //API Submit
                    if (!CommonUtilities.getConnectivityStatus(this)) {
                        CommonUtilities.openInternetDialog(this);
                    } else {
                        progress.Show();
                        if(pos>0){
                            selectedCSV = CSVlist.get(pos-1).getId();
                        }
                        if(koguchipos>0)
                            koguchi_count = koguchipos+"";
                        KoguchiReqst req = new KoguchiReqst(app.getSerial(), BaseActivity.getShopId(), adminID, order, koguchi_count,"0",selectedCSV);
                        manager.PrintKoguchiAPI( req, printKoguchiCallback);
                    }
                    break;
                }
        }
    }

    private void setKoguchiSpinner() {
        sizes = new ArrayList<>();
        sizes.add("選択");
        for (int i = 1; i<= 10; i++) {
            sizes.add(i+"");
        }

        ArrayAdapter adapter = new ArrayAdapter(this,spinner , sizes) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };

        adapter.setDropDownViewResource(_singleItemRes);
        koguchiSpinner.setAdapter(adapter);
        if(koguchipos>0)
            koguchiSpinner.setText(koguchipos+"");
        else
            koguchiSpinner.setText("選択");

        koguchiSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    koguchiSpinner.setText(sizes.get(position));
                    koguchipos = position;
                    BaseActivity.setKoguchiSpinnerPos(koguchipos);
                }
                else
                    koguchipos=0;
            }
        });
    }

    @OnClick(R.id.clear_kogu) void clear_kogu() {
        setProc(PROC_KOGUCHI);
        koguchiSpinner.clearListSelection();
        koguchiSpinner.setText("選択");
        koguchipos = 0;
    }


    @OnClick(R.id.koguchienter) void koguchienter() {
        Log.e(TAG, "KOGUCHI   "+koguchiSpinner.getText().toString());
        if(koguchiSpinner.getText().toString().equalsIgnoreCase("")|| koguchiSpinner.getText().toString().equalsIgnoreCase("0")){
            U.beepError(this,"個口を入力してください。");
            setProc(PROC_KOGUCHI);
        } else if(!U.isNumber(koguchiSpinner.getText().toString())) {
            U.beepError(this, "個口を入力してください。");
            setProc(PROC_KOGUCHI);
        } else {
            koguchi_count = koguchiSpinner.getText().toString();
            orderid.setText("");
            setProc(PROC_ORDERID);


            /* //API Submit
                if (!CommonUtilities.getConnectivityStatus(BoxCashRegisterActivity.this)) {
                    CommonUtilities.openInternetDialog(BoxCashRegisterActivity.this);
                } else {
                    progressBar.Show();
                    koguchi_count = koguchi.getText().toString();
                    KoguchiReq req = new KoguchiReq(orderId.getText().toString(), koguchi_count);
                    manager.SetKoguchi(PrefsManager.getToken(BoxCashRegisterActivity.this), req, setKoguchiCallback);
            }*/
        }
    }


    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
        nextProcess();
        BaseActivity.setCSVSpinnerPos(0);
        BaseActivity.setKoguchiSpinnerPos(0);
        selectedCSV = "";
        pos =0;
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
            case PROC_KOGUCHI:    // バーコード
                koguchiSpinner.setText(buff);
                break;
            case PROC_ORDERID: // 数量
                orderid.setText(buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (!barcode.equals("")) {

            if (mProcNo == PROC_ORDERID){
                orderid.setText(barcode);
            }
            if (mProcNo == PROC_KOGUCHI){
                koguchiSpinner.setText(barcode);
            }
        }

        this.inputedEvent(barcode);
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_KOGUCHI:    // バーコード
                koguchiSpinner.setText(barcode);
                break;
            case PROC_ORDERID: // 数量
                orderid.setText(barcode);
                break;
        }
    }

    @Override
    public void nextProcess() {

        orderid.setText("");
        koguchi_count ="";
        //  quantity="0";
        setProc(PROC_ORDERID);
    }

    @Override
    public void onSucess(int status, KoguchiResponse message) throws JsonIOException {
        if(message.getCode().equalsIgnoreCase("0")) {
            progress.Dismiss();
            U.beepKakutei(this, "CSV印刷");
            // orderid.setText("");
            koguchiSpinner.setText(koguchi_count);
            //  setProc(PROC_ORDERID);
            if(BaseActivity.getBoxSelected() == true){
                Intent i = new Intent(PrintKoguchiActivity.this, NewShippingSpecificationBoxSize.class);
                i.putExtra("orderid",orderid.getText().toString());
                startActivity(i);

            }
            else {
                orderid.setText("");
                setProc(PROC_ORDERID);
            }
        }else if(message.getCode().equals("1022")){
            progress.Dismiss();
            RePrint_Dialog();
        } else {
            progress.Dismiss();
            U.beepError(this,message.getMessage());

            Log.e("hello",message.getMessage());
            setProc(PROC_ORDERID);
        }
    }

    @Override
    public void onSucess(int status, GetCSVSpinnerResult message) throws JsonIOException {
        progress.Dismiss();
        CSVlist = new ArrayList<>();
        if(message.getCode().equalsIgnoreCase("0")) {
            CSVlist = message.getResults();
            if(CSVlist.size()>0){
                setCSVSpinner();
            }
        }
        else
            U.beepError(this,message.getMessage());
    }

    private void setCSVSpinner() {
        list = new ArrayList<>();
        list.add("プルダウン選択");
        for (int i = 0; i<CSVlist.size(); i++) {
            list.add(CSVlist.get(i).getName());
        }

        ArrayAdapter adapter = new ArrayAdapter(this,spinner , list) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };

        adapter.setDropDownViewResource(_singleItemRes);
        CSVSpinner.setAdapter(adapter);
        CSVSpinner.setText("プルダウン選択");
        CSVSpinner.setText(list.get(pos));
        if(pos>0)
            selectedCSV = CSVlist.get(pos-1).getId();

        CSVSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    CSVSpinner.setText(list.get(position));
                    selectedCSV = CSVlist.get(position-1).getId();
                    pos = position;
                    BaseActivity.setCSVSpinnerPos(pos);
                }
                else
                    selectedCSV = "";
            }
        });
    }

    @Override
    public void onError(int status, ResponseBody error) {
        progress.Dismiss();
        U.beepError(this,error.toString());
        setProc(PROC_ORDERID);
    }

    @Override
    public void onFaliure() {
        progress.Dismiss();
        U.beepError(this,"Server error");
    }

    @Override
    public void onNetworkFailure() {
        progress.Dismiss();
        U.beepError(this,"Server error");
    }

    public  void RePrint_Dialog(){
        new AlertDialog.Builder(this, R.style.DialogThemee)
                .setMessage("再印刷しますか？")
                .setCancelable(false)
                .setPositiveButton("する", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        progress.Show();
                        KoguchiReqst req = new KoguchiReqst(app.getSerial(), BaseActivity.getShopId(), adminID, orderid.getText().toString(), koguchi_count,"1",selectedCSV);
                        manager.PrintKoguchiAPI( req, printKoguchiCallback);
                        selectedCSV="";
                    }
                })
                .setNegativeButton("しない", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        nextProcess();
                    }
                })
                .show();
    }
}