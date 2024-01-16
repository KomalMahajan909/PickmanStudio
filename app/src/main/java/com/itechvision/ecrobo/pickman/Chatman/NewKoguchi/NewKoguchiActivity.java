package com.itechvision.ecrobo.pickman.Chatman.NewKoguchi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.KoguchiShipCompany.NKoguchiShipCompResult;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.KoguchiShipCompany.NKoguchiShipData;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.KoguchiShipCompany.NKoguchi_ShipCompanyReq;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.NewKoguchi_orderIDResponse;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.NewKoguchi_orderidReq;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.NewkoguchiPrint.NewKoguchiPrintRequest;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.NewkoguchiPrint.NewkoguchiPrintResult;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.SubmitNewKoguchi.SubmitKoguchiResult;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.SubmitNewKoguchi.SubmitnewKoguchiReq;
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

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

import static com.itechvision.ecrobo.pickman.Chatman.PickWork.PrintKoguchi.PrintKoguchiActivity.PROC_KOGUCHI;

public class NewKoguchiActivity extends BaseActivity implements DataManager.NewKoguchiOrderIDCallback, DataManager.NewKoguchiShipCompanycall, DataManager.NewKoguchiSubmitcall, DataManager.NewKoguchiPrintcall, View.OnClickListener {

    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.layout_main) RelativeLayout layoutMain;
    @BindView(R.id.orderid) EditText orderid;
    @BindView(R.id.shippingcmnpy) TextView shippingcmnpy;
    @BindView(R.id.orderkoguchi) TextView orderkoguchi;
    @BindView(R.id.spinnerlayout) RelativeLayout spinnerlayout;
    @BindView(R.id.changeshippingcmnpy) EditSpinner changeshippingcmnpy;
    @BindView(R.id.spinnerlayout1) RelativeLayout spinnerlayout1;
    @BindView(R.id.koguchi) EditSpinner koguchi;
    @BindView(R.id.layout_number) RelativeLayout layoutNumber;
    @BindView(R.id.viewpager) ViewPager viewpager;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.id_c_koguchi) Button id_c_koguchi;
    @BindView(R.id.id_c_company) Button id_c_company;
    @BindView(R.id.scrollView) ScrollView scrollView;

    ArrayList<String> sizes = new ArrayList<>();
    public String size = "", mBoxNo = "0";
    int post = 0, eop;
    private String koguchi_count = "";
    protected int mProcNo = 0;
    public static final int PROC_ORDERID = 1;
    public static final int PROC_KAGUCHI = 2;
    public static final int PROC_SHIP = 3;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    ECRApplication app = new ECRApplication();
    String adminID = "", ChangedShipId = "";
    boolean visible = true;
    private TextToSpeak mTextToSpeak;
    String TAG = NewKoguchiActivity.class.getSimpleName();
    DataManager manager;
    progresBar progress;
    static SlidingMenu menu;
    SlideMenu slidemenu;
    DataManager.NewKoguchiOrderIDCallback getorderId;
    DataManager.NewKoguchiShipCompanycall GetShipComp;
    DataManager.NewKoguchiSubmitcall Submitkoguchi;
    ArrayList<NKoguchiShipData> shipcompdata;
    ArrayList<String> arrayList;
    ArrayList<String> arr1;
    ArrayAdapter<String> adapter1;
    String Kog = "";
    boolean print = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_koguchi);
        ButterKnife.bind(this);
        getorderId = this;
        GetShipComp = this;
        Submitkoguchi = this;
        getIDs();
        setProc(PROC_ORDERID);
        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);
        manager = new DataManager();
        progress = new progresBar(this);
        shipcompdata = new ArrayList<>();
        arrayList = new ArrayList<String>();
        arr1 = new ArrayList<String>();

        progress.Show();
        NKoguchi_ShipCompanyReq req = new NKoguchi_ShipCompanyReq(adminID, app.getSerial(), BaseActivity.getShopId());
        manager.NewKoguchiShipCompany(req, GetShipComp);
        setKoguchiSpinner();
    }

    private void getIDs() {

        actionbarImplement(this, "送り状印刷", " ", 0, false, false, false);
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);
        relLayout1.setOnClickListener(this);
        //  btnBlue.setOnClickListener(this);

    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value :  " + mProcNo);
        switch (procNo) {
            case PROC_KAGUCHI:
                spinnerlayout1.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                spinnerlayout1.setFocusableInTouchMode(true);
                // CommonUtilities.scrollToView(scrollView, koguchi);
                orderid.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                break;
            case PROC_ORDERID:
                spinnerlayout1.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                orderid.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                orderid.setFocusableInTouchMode(true);
                break;
            case PROC_SHIP:
                orderid.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                spinnerlayout1.setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                spinnerlayout.setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                spinnerlayout.setFocusableInTouchMode(true);
                break;
        }
    }

    @Override
    public void inputedEvent(String buff) {
        switch (mProcNo) {
            case PROC_KAGUCHI:
                String val = koguchi.getText().toString();
                if ("".equals(val) || "0".equalsIgnoreCase(val)) {
                    U.beepError(this, "個口を入力してください。");
                    koguchi.setFocusableInTouchMode(true);
                    break;
                }
                if (!U.isNumber(val)) {
                    U.beepError(this, "個口を数字のみで入力してください。");
                    koguchi.setFocusableInTouchMode(true);
                    break;
                }
                if (koguchi_count.equalsIgnoreCase(val)) {

                } else {
                    /*if (!CommonUtilities.getConnectivityStatus(BoxCashRegisterActivity.this))
                        CommonUtilities.openInternetDialog(BoxCashRegisterActivity.this);
                    else{
                        progressBar.Show();
                        koguchi_count = val;
                        KoguchiReq req = new KoguchiReq(orderId.getText().toString(), koguchi_count);
                        manager.SetKoguchi(PrefsManager.getToken(BoxCashRegisterActivity.this), req,setKoguchiCallback);
                    }*/
                }
                break;
            case PROC_ORDERID:
                if (print) {
                    onPrintClick();
                } else {

                    String order = orderid.getText().toString();
                    if (order.equalsIgnoreCase("") || order.equalsIgnoreCase("0")) {
                        U.beepError(this, "注文番号を入力してください。");
                        setProc(PROC_ORDERID);
                    } else {
                        //API Submit
                        if (!CommonUtilities.getConnectivityStatus(this)) {
                            CommonUtilities.openInternetDialog(this);
                        } else {
                            progress.Show();
                            NewKoguchi_orderidReq req = new NewKoguchi_orderidReq(adminID, app.getSerial(), BaseActivity.getShopId(), order);
                            manager.NewKoguchiOrderID(req, getorderId);
                        }
                        break;
                    }
                }
        }
    }

    @Override
    public void clearEvent() {
        // mTextToSpeak.startSpeaking("clear");
        nextProcess();
        orderid.setText("");
        shippingcmnpy.setText("");
        orderkoguchi.setText("");
        changeshippingcmnpy.setText("配送会社を選択");
        koguchi.setText("個口を選択");
        print = false;
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
            case PROC_KAGUCHI:    // バーコード
                koguchi.setText(buff);
                break;
            case PROC_ORDERID: // 数量
                orderid.setText(buff);
                break;
            case PROC_SHIP: // 数量
                changeshippingcmnpy.setText(buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (!barcode.equals("")) {
            if (mProcNo == PROC_ORDERID) {
                orderid.setText(barcode);
            } else if (mProcNo == PROC_KOGUCHI) {
                koguchi.setText(barcode);
            } else if (mProcNo == PROC_SHIP) {
                changeshippingcmnpy.setText(barcode);
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
            case PROC_KAGUCHI:    // バーコード
                koguchi.setText(barcode);
                break;
            case PROC_ORDERID: // 数量
                orderid.setText(barcode);
                break;
            case PROC_SHIP: // 数量
                changeshippingcmnpy.setText(barcode);
                break;
        }
    }

    @Override
    public void nextProcess() {
        koguchi.setText("個口を選択");
        orderid.setText("");
        koguchi_count = "";
        //  quantity="0";
        setProc(PROC_KOGUCHI);
    }

    @OnClick(R.id.add_layout)
    void AddLayout() {
        if (visible) {
            layoutNumber.setVisibility(View.INVISIBLE);
            visible = false;
        } else {
            layoutNumber.setVisibility(View.VISIBLE);
            visible = true;
        }
    }

    @OnClick(R.id.submit)
    void submit() {
        if (changeshippingcmnpy.getText().toString().equals("配送会社を選択") && koguchi.getText().toString().equals("個口を選択")) {
            U.beepError(this, "個口や運送会社を更新してください。");
        } else {
            if (koguchi.getText().toString().equals("個口を選択")) {
                Kog = "";
            } else {
                Kog = koguchi.getText().toString();
            }
            progress.Show();
            SubmitnewKoguchiReq req = new SubmitnewKoguchiReq(adminID, app.getSerial(), BaseActivity.getShopId(), orderid.getText().toString(), ChangedShipId, koguchi.getText().toString(), getResources().getString(R.string.version));
            manager.NewKoguchiSubmit(req, Submitkoguchi);
        }

    }

    @OnClick(R.id.id_c_koguchi) void id_c_koguchi() {
        koguchi.setText("個口を選択");
        setProc(PROC_KAGUCHI);
    }

    @OnClick(R.id.id_c_company) void id_c_company() {
        changeshippingcmnpy.setText("配送会社を選択");
        setProc(PROC_SHIP);
    }

    @OnClick(R.id.print) void onPrintClick() {
        if (ChangedShipId.equals("") && koguchi.getText().toString().equals("個口を選択")) {
            progress.Show();
            NewKoguchiPrintRequest request = new NewKoguchiPrintRequest(orderid.getText().toString(), BaseActivity.getShopId(), adminID, app.getSerial());
            manager.NewKoguchiPrint(request, this);
        } else {
            U.beepError(this, "個口や運送会社を更新してください。");
        }
    }

    private void setKoguchiSpinner() {
        sizes = new ArrayList<>();
        // sizes.add("Select Koguchi");
        for (int i = 1; i <= 10; i++) {
            sizes.add(i + "");
        }

        ArrayAdapter adapter = new ArrayAdapter(this, spinner, sizes) {
        @Override
        public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                } else {
                    return true;
                }
            }
        };

        adapter.setDropDownViewResource(_singleItemRes);
        koguchi.setAdapter(adapter);
     // koguchi.setText(koguchi_count);
        koguchi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    eop = position;
                    if (koguchi_count.equalsIgnoreCase(sizes.get(position))) {
                    } else {

                    }
                }
            }
        });
    }

    //GetOrder ID
    @Override
    public void onSucess(int status, NewKoguchi_orderIDResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("0")) {
            shippingcmnpy.setText(message.getShipping_name());
            orderkoguchi.setText(message.getKoguchi());
            print = true;
        } else if (message.getCode().equals("403")) {
            print = false;
            U.beepError(this, message.getMessage());
        }
    }

    //Get Ship Comp
    @Override
    public void onSucess(int status, NKoguchiShipCompResult message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("0")) {
            arrayList.clear();
            shipcompdata.clear();
            shipcompdata = message.getShipping_methods();
            if (shipcompdata.size() != 0) {
                for (int i = 0; i < shipcompdata.size(); i++) {
                    shipcompdata.get(i).getId();
                    shipcompdata.get(i).getNamed();
                    String a = shipcompdata.get(i).getNamed();
                    arrayList.add(a);
                }

                adapter1 = new ArrayAdapter<String>(getApplicationContext(), spinner, arrayList);
                adapter1.setDropDownViewResource(_singleItemRes);
                changeshippingcmnpy.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
                changeshippingcmnpy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String name = shipcompdata.get(i).getNamed();
                        String id = shipcompdata.get(i).getId();
                        changeshippingcmnpy.setText(name);
                        Log.e("selected shopID", id);
                        ChangedShipId = id;
                        changeshippingcmnpy.setText(shipcompdata.get(i).getNamed());
                    }
                });
            } else {
                print = false;
                U.beepError(this, "運送会社が見つかりません。");
            }
        } else if (message.getCode().equals("403")) {
            U.beepError(this, message.getMessage());
            print = false;
        }
    }

    //Submission
    @Override
    public void onSucess(int status, SubmitKoguchiResult message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("0")) {
            koguchi.setText("個口を選択");
            changeshippingcmnpy.setText("配送会社を選択");

            setProc(PROC_ORDERID);
            ChangedShipId = "";
            U.beepFinish(this, "終了致しました。");

            NewKoguchi_orderidReq req = new NewKoguchi_orderidReq(adminID, app.getSerial(), BaseActivity.getShopId(), orderid.getText().toString());
            manager.NewKoguchiOrderID(req, getorderId);
        } else if (message.getCode().equals("403")) {
            print = false;
            U.beepError(this, message.getMessage());
        } else {
            print = false;
            U.beepError(this, message.getMessage());
        }
    }

    @Override
    public void onSucess(int status, NewkoguchiPrintResult message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equals("0")) {
            U.beepFinish(this, message.getMessage());

            orderid.setText("");
            shippingcmnpy.setText("");
            orderkoguchi.setText("");
            changeshippingcmnpy.setText("配送会社を選択");
            koguchi.setText("個口を選択");

            ChangedShipId = "";
            print = false;
        } else if (message.getCode().equals("403")) {
            Log.e("scgvjsdghvkshv", ">>>>>>: " + message.getMessage());
            U.beepError(this, message.getMessage());
            print = false;
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

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

}
