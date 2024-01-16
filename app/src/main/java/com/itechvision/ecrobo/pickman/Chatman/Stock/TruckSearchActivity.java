package com.itechvision.ecrobo.pickman.Chatman.Stock;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.api.SearchStock;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.itechvision.ecrobo.pickman.Util.CommonDialogs.customToast;

public class TruckSearchActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.listProduct)
    ListView lv;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
//    @BindView(R.id.layout_number) RelativeLayout layout;

    ECRApplication app=new ECRApplication();
    String adminID="";

    private boolean visible = false;

    public Context mcontext=this;
    private boolean showKeyboard;

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";

    String qty = "", location = "" , id = "" , stock_id = "";

    Map<String,String> map = new HashMap<>();
    protected List<Map<String, String>> stockList = new ArrayList<Map<String, String>>();
    public static Map<String, String>  orderList = new HashMap<>();

    public static int mRequestStatus =0;

    public static final int REQ_INITIAL = 1;
    public static final int REQ_USE = 2;

    String TAG= TruckSearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_search);
        ButterKnife.bind(TruckSearchActivity.this);

        getIDs();
        Log.d(TAG,"On Create ");
        Log.e(TAG,"Lotnooooooo    "+getLotPress());
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID=spDomain.getString("admin_id",null);

        Intent i = getIntent();
        if (i.hasExtra("barcode")){
            map.put("barcode",i.getStringExtra("barcode"));
            map.put("code",i.getStringExtra("code"));
            map.put("location",i.getStringExtra("location"));
            map.put("quantity",i.getStringExtra("quantity"));
            map.put("order_id",i.getStringExtra("order_id"));
            map.put("batch_id",i.getStringExtra("batch_id"));
            map.put("psh_id",i.getStringExtra("psh_id"));
            map.put("truck",i.getStringExtra("truck"));
            map.put("shipping_schedule",i.getStringExtra("shipping_schedule"));

        }
        if(!map.isEmpty()){
            _sts(R.id.barcode,map.get("barcode"));
            _sts(R.id.product_code,map.get("code"));
        }

        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("barcode", map.get("code")));
        Globals.getterList.add(new ParamsGetter("mode","search"));
        Globals.getterList.add(new ParamsGetter("location", map.get("location")));
        Globals.getterList.add(new ParamsGetter("truck_id", map.get("truck")));
        Globals.getterList.add(new ParamsGetter("shipping_schedule", map.get("shipping_schedule")));

        Globals.getterList.add(new ParamsGetter("quantity", map.get("quantity")));
        Globals.getterList.add(new ParamsGetter("order_id", map.get("order_id")));
        Globals.getterList.add(new ParamsGetter("batch_id", map.get("batch_id")));
        Globals.getterList.add(new ParamsGetter("psh_id", map.get("psh_id")));

        mRequestStatus = REQ_INITIAL;
        new MainAsyncTask(this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckSearchActivity.this, "Form", Globals.getterList, true).execute();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long ids) {
                // ロケが選択されたら次工程
                Map<String,String> map = new HashMap<>();
                map = stockList.get(position);
                Log.e(TAG, "MAp data got is  "+map);

                qty = map.get("quantity");
                location = map.get("location");

                stock_id = map.get("product_stock_id");

                U.beepNext();
            }
        });
    }

    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        actionbarImplement(this, "商品検索", " ",
                    0, false,true,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        relLayout1.setOnClickListener(this);
        btnRed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:

                break;


        }
    }



    @OnClick(R.id.backbtn) void back(){
        Intent i = new Intent();
        setResult(Activity.RESULT_OK, i);
        finish();
    }
    @OnClick(R.id.useLocation) void useLocation()
    {

        Globals.getterList = new ArrayList<>();


        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        Globals.getterList.add(new ParamsGetter("barcode", map.get("code")));
        Globals.getterList.add(new ParamsGetter("mode","change_location"));
        Globals.getterList.add(new ParamsGetter("new_location", location));
        Globals.getterList.add(new ParamsGetter("truck_id",  map.get("truck")));
        Globals.getterList.add(new ParamsGetter("shipping_schedule", map.get("shipping_schedule")));
        Globals.getterList.add(new ParamsGetter("quantity", map.get("quantity")));
        Globals.getterList.add(new ParamsGetter("order_id", map.get("order_id")));
        Globals.getterList.add(new ParamsGetter("batch_id", map.get("batch_id")));
        Globals.getterList.add(new ParamsGetter("psh_id", map.get("psh_id")));
        Globals.getterList.add(new ParamsGetter("product_stock_id", stock_id));

        mRequestStatus = REQ_USE;
        new MainAsyncTask(this, Globals.Webservice.Dbatch_Truck_Picking, 1, TruckSearchActivity.this, "Form", Globals.getterList, true).execute();



    }


    public int convert(int x) {
        Resources r =mcontext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                x, r.getDisplayMetrics()
        );
        return px;
    }

    public void getStockData(List<Map<String, String>> stock)
    {
        stockList =stock;
    }

    @Override
    public void nextProcess() {

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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void deleteEvent(String barcode) {

    }

    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {
        HashMap<String, String> mHash=new HashMap<>();
        Log.e(TAG,result.toString());

        try {

            String response= result.toString();
            JsonPullParser parser = JsonPullParser.newParser(response);
            JsonHash map1 = JsonHash.fromParser(parser);
            Log.e(TAG," "+map1);

            String msg="";
            JsonArray result1;
            String code = map1.getStringOrNull("code");
            msg=map1.getStringOrNull("message");
            result1= map1.getJsonArrayOrNull("results");
            if (code == null) {
                Log.e(TAG,"CODEeee============Nulllll");
                customToast(getApplicationContext(),"Network error occured");

            }
            if ("0".equals(code) == true) {
                Log.e("SendLogs111",code+"  "+msg+"  "+result1);
                if(mRequestStatus == REQ_INITIAL)
                    new SearchStock().post(code,msg,result1, mHash,TruckSearchActivity.this);
                else if(mRequestStatus == REQ_USE)
                {

                    JsonHash row = (JsonHash) result1.get(0);
                    Log.e(TAG,">>>>>>>>>>>"+row);

                    if(row.containsKey("product_row")) {
                        JsonHash row1 = row.getJsonHashOrNull("product_row");

                        map.put("location", row1.getStringOrNull("location"));
                        map.put("barcode",row1.getStringOrNull("barcode"));
                        map.put("code",row1.getStringOrNull("code"));
                        map.put("quantity",row1.getStringOrNull("rest_quantity"));
                        map.put("frontage",row1.getStringOrNull("frontage_number"));
                        map.put("order_sub_id",row1.getStringOrNull("order_sub_id"));
                        map.put("psh_id",row1.getStringOrNull("psh_id"));
                        map.put("truck_id",row1.getStringOrNull("truck_id"));
                        map.put("batch_id",row1.getStringOrNull("batch_id"));
                        map.put("order_id",row1.getStringOrNull("order_id"));
                        map.put("color",row1.getStringOrNull("spec1"));
                        map.put("size",row1.getStringOrNull("spec2"));
                        map.put("cancel_flag",row1.getStringOrNull("cancel_flag"));
                        map.put("processedCnt","0");

                        Log.e(TAG,">>>>>>>>>>>11111mappppppp    "+map);

                    }

                    orderList.putAll(map);
                    Intent i = new Intent();

                    i.putExtra("location", location);
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(TruckSearchActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else {
                if (mRequestStatus == REQ_INITIAL)
                    new SearchStock().valid(code, msg, result1, mHash, TruckSearchActivity.this);
                else if (mRequestStatus == REQ_USE)
                {
                  U.beepError(this,msg);
                }
            }
        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }
}
