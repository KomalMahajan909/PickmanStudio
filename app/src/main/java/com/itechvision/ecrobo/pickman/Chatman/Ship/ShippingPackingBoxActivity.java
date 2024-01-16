package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShippingPackingBoxActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {

    @BindView(R.id.lvPackingList)ListView lv;
    protected List<Map<String, String>> mPackingList = new ArrayList<Map<String, String>>();

    protected String orderId = "";
    protected String track_no ="";
    protected Integer productListSize = 0;
    protected long orderCount = 0;
    protected long packedCount = 0;
    public TextView pLog;
    String TAG = "ShippingPackingBoxActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing_box);
        ButterKnife.bind(ShippingPackingBoxActivity.this);
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
        mPackingList = ShippingActivity.packBoxData;


        //selectAll = (CheckBox) this.findViewById(R.id.selectAllLVItems);
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


    @OnClick(R.id.btn_nextBox) void nextBox (View view){
        Log.d("BTN", "NextBox Button");
        new AlertDialog.Builder(this)
                .setTitle("Change Box?")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent();
                        i.putExtra(NEXTBOX, ADDNEXTBOX);
                        ShippingPackingBoxActivity.this.setResult(Activity.RESULT_OK, i);
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
        ShippingPackingBoxActivity.this.setResult(Activity.RESULT_CANCELED, i);
        finish();
    }
    @OnClick (R.id.btn_finishBox) void finishBox (){
        Log.d("BTN", "Finish Button");
        new AlertDialog.Builder(ShippingPackingBoxActivity.this)
                .setTitle("Finish?")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (FixedPicking.empty==1) {
                            Intent i = new Intent();
                            i.putExtra(FINISH, "finish");
                            ShippingPackingBoxActivity.this.setResult(Activity.RESULT_OK, i);
                            finish();
                        } else U.beepError(ShippingPackingBoxActivity.this, "There is something remaining, Please clear and do again");
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

