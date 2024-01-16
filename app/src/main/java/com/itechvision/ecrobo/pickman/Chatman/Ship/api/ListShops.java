package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Ship.TotalPickListActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;

/**
 * Created by lenovo on 12/31/2019.
 */

public class ListShops {

    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;

    public void post(String code, String message, JsonArray list,
                     final Activity activity) {
        // TODO 自動生成されたメソッド・スタブ

        ArrayList<String> data = new ArrayList<String>();
        final TotalPickListActivity act = (TotalPickListActivity) activity;

        // Listにデータを入れる
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
        Spinner spinner = (Spinner) activity.findViewById(R.id.shopspinner);
        spinner.setAdapter(adapter);

//        spinner.setSelection(BaseActivity.getPickshop());

    }

    public void valid(String code, String message, JsonArray list,
                      Activity activity) {
        // TODO 自動生成されたメソッド・スタブ
        U.beepError(activity, message);
        Log.d("LISTSHOPS", "Not Success");
    }

}


