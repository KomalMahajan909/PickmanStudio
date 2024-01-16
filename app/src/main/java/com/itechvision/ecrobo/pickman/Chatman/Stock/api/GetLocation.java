package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.widget.ListView;

import com.itechvision.ecrobo.pickman.Chatman.Stock.MoveLocationActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 1/15/2019.
 */

public class GetLocation {

    String TAG = "GetLocation";

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {

        ListViewItems data = new ListViewItems();

        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            String productCode = row.getStringOrNull("code");
            String barcode =row.getStringOrNull("barcode");
            long qty = 0;
            JsonArray list2 = row.getJsonArrayOrNull("stocks");
            if (list2 != null) {
                for (int j = 0; j < list2.size(); j++) {
                    JsonHash row2 = (JsonHash) list2.get(j);
                    if (row2.getStringOrNull("quantity") != null)
                        qty += Long.parseLong(row2.getStringOrNull("quantity"));
                }
            }
            data.add(data.newItem()
                    .add(R.id.arr_all_0, productCode)
                    .add(R.id.arr_all_2,barcode)
                    .add(R.id.arr_all_1, Long.toString(qty)));
        }
        if (data.getData().size() == 0) {
            this.valid(null, "該当ロケーションに在庫がありません", null, params, activity);
            return;
        }

        ListViewAdapter adapter = new ListViewAdapter(
                activity.getApplicationContext()
                , data
                , R.layout.list_locations);
        ListView lv = (ListView) activity.findViewById(R.id.listProduct);
        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        U.beepSuccess();
        MoveLocationActivity act = (MoveLocationActivity) activity;
        act.startTimer();
        act.setProc(MoveLocationActivity.PROC_DESTINATION);
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        MoveLocationActivity act = ((MoveLocationActivity) activity);
        U.beepError(activity, message);
        act.setProc(act.PROC_SOURCE);
    }
}
