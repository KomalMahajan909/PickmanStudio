package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetDbatchList;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DBatchActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
        static SlidingMenu menu;
        SlideMenu slidemenu;
        @BindView(R.id.actionbar)
        ActionBar actionbar;
        @BindView(R.id.setDate)
        EditText setedt;



        SharedPreferences spDomain;
        public static final String DOMAINPREFERENCE = "domain";

        protected List<Map<String, String>> mBatchList = null;
        ArrayList<String> inspectionstatusList= new ArrayList<>();
        int[] statusarray;
        private String TAG = "DBatchActivity";
        ListView lv;
        String userid;
        long totalbatch =0, totalorders=0;


        private DatePickerDialog.OnDateSetListener mDateSetListener;

        ECRApplication app=new ECRApplication();
        String adminID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbatch);
        ButterKnife.bind(this);

        getIDs();
        Log.d(TAG,"On Create ");
        _sts(R.id.setDate,BaseActivity.getdate());
        lv = (ListView)findViewById(R.id.listview);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);

        userid = spDomain.getString("admin_id",null);


        Globals.getterList = new ArrayList<>();
        adminID = spDomain.getString("admin_id", null);
        Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());
        Globals.getterList.add(new ParamsGetter("admin_id", adminID));
        if(!_gts(R.id.setDate).equals(""))
            Globals.getterList.add(new ParamsGetter("create_date", _gts(R.id.setDate)));
//            mRequestStatus = REQ_BARCODE;
        new MainAsyncTask(this, Globals.Webservice.DBatchPicking, 1, this, "Form", Globals.getterList, true).execute();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Map<String, String> row = mBatchList.get(position);
                Log.e(TAG, ">>>>>>>>>>>>>>>" + position);
                if(row.get("admin_id").matches(adminID) || row.get("admin_id").equals("")){
                if(!row.get("batch_status").equals("2")){
                    String createdate = "";
                    if (setedt.getText().toString().equals("")) {
                        String date = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).format(new Date());
                        Log.e(TAG, ">>>>>>>>>>>>>>>   " + date);

                        createdate = date;

                    } else {
                        createdate = setedt.getText().toString();

                    }
                    BaseActivity.setdate(createdate);
                    Intent i = new Intent(DBatchActivity.this, BatchBoxActivity.class);
                    i.putExtra("batch_no", row.get("batch_no"));
                    i.putExtra("batch_id", row.get("batch_id"));
                    i.putExtra("create_date", createdate);

                    startActivity(i);
                }
                }
                else {
                    CommonDialogs.customToast(DBatchActivity.this, "Batch Already being used");
                }
            }
        });

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
                Globals.getterList = new ArrayList<>();

                adminID = spDomain.getString("admin_id", null);
                Log.e(TAG, "shopidddddd  " + BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                Globals.getterList.add(new ParamsGetter("create_date", _gts(R.id.setDate)));
                BaseActivity.setdate(_gts(R.id.setDate));
                new MainAsyncTask(this, Globals.Webservice.DBatchPicking, 1, this, "Form", Globals.getterList, true).execute();
            }
        }

        public void showTruitonDatePickerDialog(View v) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    DBatchActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }

        //set title and icons on actionbar
        private void getIDs() {
            // TODO Auto-generated method stub

            actionbarImplement(this, "D バッチ検品", " ",
                    0, false,false,false );
            //menubarr
            menu = setSlidingMenu(this);
            slidemenu = new SlideMenu(menu, this);


            relLayout1.setOnClickListener(this);
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
            CommonDialogs.customToast(this, "No action");
        }
        public void setBadge1(long cnt) {
            super.setBadge1(cnt);
            totalbatch = cnt;
        }
        public void setBadge2(long cnt) {
            super.setBadge2(cnt);
            totalorders = cnt;
        }
        public void setBatchbadge(List<Map<String, String>> data)
        {
            mBatchList = data;
            Log.e(TAG,">>>>>>>>>>>>>>>mBatchList     "+ mBatchList );
            initWorkList();

        }
        public void setStatusList(ArrayList<String> data)
        {
            inspectionstatusList =data;
            Log.e(TAG,">>>>>>>>>>>>>>>>>>>>    "+inspectionstatusList);
            Log.e(TAG,">>>>>>>>>>>>>>>>>>>>    "+inspectionstatusList.size());
        }

        protected void initWorkList() {
            Log.e("NewShippingActivity", "initWorkList");
            lv.setAdapter(null);
            ListViewItems data = new ListViewItems();
            ArrayList<Integer> used = new ArrayList<>();
            statusarray = new int[inspectionstatusList.size()];
            for(int i=0;i< inspectionstatusList.size();i++){
                Map<String, String> row = mBatchList.get(i);
                Log.e(TAG,">>>>>>>>>>>>>>>"+i);
                String status ="";
                status =row.get("status");
//            if (!status.equals("") ){
//                Log.e(TAG,"no action  >>>>>>>>>>>>>>>"+i);
//                row.put("status","未");
//                statusarray[i]=R.drawable.yellow_rounded_corner_button;
//                mBatchList.add(i,row);
//
//            }
//            else
                if(inspectionstatusList.get(i).equals("0"))
                {
                    statusarray[i]=R.drawable.grey_rounded_btn;
                }
                else if(inspectionstatusList.get(i).equals("1"))
                {
                    statusarray[i]=R.drawable.red_rounded_corner_button;
                }
                else if(inspectionstatusList.get(i).equals("2"))
                {
                    statusarray[i]=R.drawable.green_rounded_corner_btn;
                }
            }
            Log.e(TAG,">>>>>>>>>>>>>>>statusarray     "+ statusarray.length);
            Log.e(TAG,">>>>>>>>>>>>>>>mBatchList     "+ mBatchList.size() + "     "+mBatchList);
            for (int i =0 ; i <= mBatchList.size() - 1; i++) {
                Map<String, String> row = mBatchList.get(i);

			/*String rowsQty = row.get("quantity");
			if(row.get("code").equals(_gts(id.productCode))){
				rowsQty = String.valueOf(Integer.valueOf(rowsQty) - Integer.valueOf(_gts(id.quantity)));
			}*/
                String username ="";
//            username =row.get("username");

                data.add(data.newItem().add(R.id.rbatch_0, row.get("batch_no"))
                        .add(R.id.rbatch_1, row.get("batch_type"))
                        .add(R.id.rbatch_3, row.get("batch_order_count"))
                        .add(R.id.rbatch_4, row.get("status"))
                        .add(R.id.rbatch_5, row.get("sku_count"))
                );
//            if(!username.equals(""))
//                if(userid.equals(username))
//                {
//                    Log.e(TAG,"indext of item is >>>>>>>>>>>"+row);
                used.add(i);
//                lv.getChildAt(i).setEnabled(false);
//                }
            }

            ListViewAdapter adapter = new ListViewAdapter(
                    getApplicationContext()
                    , data
                    , R.layout.batch_picking_row) {
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);

                    TextView img = (TextView) v.findViewById(R.id.rbatch_4);
                    img.setBackground(getResources().getDrawable(statusarray[position]));
                    return v;
                }
            };
            lv.setAdapter(adapter);
//        lv.getChildAt(1).setEnabled(false);
            // 単一選択モードにする
            lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

            // デフォルト値をセットする
            if (data.getData().size() > 0)
                lv.setItemChecked(0, true);
//        return data;
        }
        private ListView getListView()
        {
            ListView lv= (ListView)findViewById(R.id.listview);
            return lv;
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
                    CommonDialogs.customToast(getApplicationContext(),"Network error occured");

                }
                if ("0".equals(code) == true) {

                    Log.e("SendLogs111",code+"  "+msg+"  "+result1);

                    new GetDbatchList().post(code,msg,result1, mHash,DBatchActivity.this);
                }else if(code.equalsIgnoreCase("1020")){
                    new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                            .setTitle("Error!")
                            .setMessage(msg)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent in = new Intent(DBatchActivity.this,LoginActivity.class);
                                    in.putExtra("ACTION", "logout" );
                                    startActivity(in);
                                    finish();

                                    dialog.dismiss();
                                }
                            })

                            .show();
                }
                else {
                    new GetDbatchList().valid(code, msg, result1, mHash, DBatchActivity.this);
                }}
            catch (Exception e)
            {
                System.out.print(e);
            }
        }

        @Override
        public void onPostError(int flag) {

        }
    }
