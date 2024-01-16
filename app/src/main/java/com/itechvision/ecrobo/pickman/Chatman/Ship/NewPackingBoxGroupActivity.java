package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.FixedPicking;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewPackingBoxGroupActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    @BindView(R.id.lvPackingList)ListView lv;
    protected List<Map<String, String>> mPackingList = new ArrayList<Map<String, String>>();

    protected String orderId = "";
    protected String track_no ="";
    protected Integer productListSize = 0;
    protected long orderCount = 0;
    protected long packedCount = 0;
    public TextView pLog;
    String TAG = "NewPackingBoxGroupActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing_box);
        ButterKnife.bind(this);
        Intent i = getIntent();
        Log.d(TAG,"On Create ");
        if (i.hasExtra("orderId")){
            orderId = i.getStringExtra("orderId");
            if(BaseActivity.getOrderInfoBy()== SettingActivity.ORDER_TRACKING_NO)
                track_no= i.getStringExtra("tracking_no");
//            productListSize = i.getIntExtra("productListSize", 0);
            orderCount = i.getLongExtra("orderQtySize",0);
            packedCount = i.getLongExtra("packedQtySize",0);
        }


        createList();

        lv = ((ListView) this.findViewById(R.id.lvPackingList));
        lv.setAdapter(null);
        if(mPackingList.size() > 0) {
            ListViewItems data = new ListViewItems();
            int j = 0;
            for (Map<String, String> map : mPackingList){
                ++j;
                data.add(data.newItem().add(R.id.pck_0, j+"")
                        .add(R.id.pck_1, map.get("code"))
                        .add(R.id.pck_2, map.get("barcode"))
                        .add(R.id.pck_3, map.get("quantity")));
//                        .add(R.id.pck_4, "box-" + map.get("boxNo")));
            }
            if (data.getData().size() > 0) {
                ListViewAdapter adapter = new ListViewAdapter(
                        this.getApplicationContext()
                        , data
                        , R.layout.list_packing_items);
                lv.setAdapter(adapter);
            }
        }

    }

    void createList(){
        Map<String, String> mPackItem = new HashMap<String, String>();

        for(Map<String,String> map1 : NewShippingGroupActivity.packData){
            boolean repeat = false;
            if (mPackingList.size() > 0) {

                String _b1 = map1.get("barcode");

                Log.e(TAG, "mtarget  barcode " + _b1 );
                for (Map<String, String> map : mPackingList) {
                    String _b = map.get("barcode");
                    String _box = map.get("boxNo");
                    Log.e(TAG, "pack data  barcode " + _b );
                    StringBuffer temp_qty = new StringBuffer();

                    if (_b.equals(_b1)  ) {

                        String qnty = U.plusTo(map.get("quantity"), map1.get("quantity"));
                        Log.e(TAG, "map  quantity " + map.get("quantity"));
                        map.put("quantity", qnty);
                        repeat = true;
                    }
                }
            }

            if (repeat == false) {

                mPackItem = new HashMap<String, String>();
                mPackItem.put("code", map1.get("code"));
                mPackItem.put("barcode", map1.get("barcode"));
                mPackItem.put("location", map1.get("location"));
                mPackItem.put("quantity", map1.get("quantity"));
                mPackItem.put("orderSubId", map1.get("orderSubId"));
                mPackingList.add(mPackItem);

            }
            Log.e(TAG, " mpackItemm " + mPackingList);

        }

    }

    @OnClick(R.id.btn_nextBox) void nextBox (View view){
        Log.d("BTN", "NextBox Button");
        new AlertDialog.Builder(this)
                .setTitle("Change Box?")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent();
                        i.putExtra(NEXTBOX, ADDNEXTBOX);
                        NewPackingBoxGroupActivity.this.setResult(Activity.RESULT_OK, i);
                        finish();
//                                addBox();
                        dialog.cancel();

                    }
                })
                .setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    @OnClick(R.id.packing_close_btn) void closeBtn(){
        Intent i = new Intent();
        i.putExtra(FINISH, "finish");
        NewPackingBoxGroupActivity.this.setResult(Activity.RESULT_CANCELED, i);
        finish();
    }
    @OnClick (R.id.btn_finishBox) void finishBox (){
        Log.d("BTN", "Finish Button");
        new AlertDialog.Builder(NewPackingBoxGroupActivity.this)
                .setTitle("Finish?")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (FixedPicking.empty==1) {
                            Intent i = new Intent();
                            i.putExtra(FINISH, "finish");
                            NewPackingBoxGroupActivity.this.setResult(Activity.RESULT_OK, i);
                            finish();
                        } else U.beepError(NewPackingBoxGroupActivity.this, "There is something remaining, Please clear and do again");
                    }
                })
                .setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
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
    protected void onDestroy() {
        super.onDestroy();
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
    public void onClick(View v) {

    }

    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {

    }

    @Override
    public void onPostError(int flag) {

    }
}
