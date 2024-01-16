package com.itechvision.ecrobo.pickman.Chatman.Account.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;

public class ListShopAll  {

    private final int _singleItemRes = R.layout.spinner_layout;
    private final int _dropdownRes = R.layout.simple_spinner_dropdown_item;

    public void post(String code, String message, JsonArray list,
                     final Activity activity) {
        // TODO 自動生成されたメソッド・スタブ

        ArrayList<String> data = new ArrayList<String>();
        SettingActivity act = (SettingActivity)activity;
        // Listにデータを入れる
//        data.add("店舗を選択");

//        for (int i = 0; i < list.size(); i++) {
//            JsonHash row = (JsonHash) list.get(0);
//            JsonArray list1 ;
//            if(row.containsKey("shop_list")) {
//                list1 = row.getJsonArrayOrNull("shop_list");
//                for (int j = 0; j < list1.size(); j++) {
//                    JsonHash row1 = (JsonHash) list1.get(j);
//
//                    if (row1.getStringOrNull("shop_id") != null && row1.getStringOrNull("shop_id").equals("") == false) {
//                        data.add(row1.getStringOrNull("shop_id") + ":" + row1.getStringOrNull("shop_name"));
//                    }
//                }
//                if(row .containsKey("new_version")){
//                    if (SettingActivity.version.equalsIgnoreCase(row.getStringOrNull("new_version"))){
//
//                    }else{
//                        act.AlertUpdate();
//                    }
//                }
//            }
//        }
//        data.add("");
//        // Adapterの作成
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
//                _singleItemRes, data);
//
//        // ドロップダウンのレイアウトを指定
//        adapter.setDropDownViewResource(_dropdownRes);
//
//        // ListViewにAdapterを関連付ける
//        Spinner spinner = (Spinner) activity.findViewById(R.id.spinnershopID);
//
//        spinner.setAdapter(adapter);
//
//        spinner.setSelection(BaseActivity.getshoppos());
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view,
//                                       int position, long id) {
//                String item = (String) adapterView.getItemAtPosition(position);
//                String[] shop_id = item.split(":");
//
//                try {
//                    Integer.parseInt(shop_id[0]);
//                    ((ECRApplication) activity.getApplication()).setShopId(shop_id[0]);
//                    BaseActivity.setShopId(shop_id[0]);
//                    BaseActivity.setShopinfo(shop_id[1]);
//                    BaseActivity.setshoppos(position);
//
//                } catch (NumberFormatException e) {
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // 選択されなかった場合
//            }
//        });

        data.add("店舗を選択");
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);

            if (row.getStringOrNull("shop_id") != null && row.getStringOrNull("shop_id").equals("") == false) {
                data.add(row.getStringOrNull("shop_id") + ":" + row.getStringOrNull("shop_name"));
            }
        }
        data.add("");
        // Adapterの作成
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                _singleItemRes, data);

        // ドロップダウンのレイアウトを指定
        adapter.setDropDownViewResource(_dropdownRes);

        // ListViewにAdapterを関連付ける
        Spinner spinner = (Spinner) activity.findViewById(R.id.spinnershopID);

        spinner.setAdapter(adapter);
        //spinner.setSelection(0);
        spinner.setSelection(BaseActivity.getshoppos());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = (String) adapterView.getItemAtPosition(position);

                String[] shop_id = item.split(":");


                try {
                    Integer.parseInt(shop_id[0]);
                    ((ECRApplication) activity.getApplication()).setShopId(shop_id[0]);
                    BaseActivity.setShopId(shop_id[0]);


                    BaseActivity.setShopinfo(shop_id[1]);
                    BaseActivity.setshoppos(position);


                    if (BaseActivity.getUrl().equalsIgnoreCase("https://api.air-logi.com/service") && BaseActivity.getShopId().equalsIgnoreCase("3366")||BaseActivity.getUrl().equalsIgnoreCase("https://staging.air-logi.com/service") && BaseActivity.getShopId().equalsIgnoreCase("3366") ){
                        SlideMenu.loopshipping.setVisibility(View.VISIBLE);
                    }else{
                        SlideMenu.loopshipping.setVisibility(View.GONE);
                    }

                } catch (NumberFormatException e) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void valid(String code, String message, JsonArray list,
                      Activity activity) {
        // TODO 自動生成されたメソッド・スタブ
        U.beepError(activity, message);
        Log.d("LISTSHOPS", "Not Success");
    }

}

