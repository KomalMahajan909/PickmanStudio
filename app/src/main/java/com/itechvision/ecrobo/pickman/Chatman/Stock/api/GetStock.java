package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.widget.ListView;

import com.itechvision.ecrobo.pickman.Chatman.Stock.StockAdjustmentActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 3/15/2019.
 */

public class GetStock {
    private boolean orderRequestSettings;
    String TAG = "GetStock";

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {

        ListViewItems data = new ListViewItems();
        StockAdjustmentActivity act = (StockAdjustmentActivity) activity;
        String barcode = null;
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);

            JsonArray list2 = row.getJsonArrayOrNull("data");
            if (list2 != null) {
                for (int j = 0; j < list2.size(); j++) {
                    JsonHash row2 = (JsonHash) list2.get(j);
                    String loc = row2.getStringOrNull("location");


                    data.add(data.newItem().add(R.id.stc_prd_0, loc)
                            .add(R.id.stc_prd_1, row2.getStringOrNull("quantity"))
                            .add(R.id.stc_prd_2, row2.getStringOrNull("st_name"))
                            .add(R.id.stc_prd_3, row2.getStringOrNull("id")));

                    if(row2.containsKey("barcode"))
                    {
                        barcode = row2.getStringOrNull("barcode");
                        if(!act._gts(R.id.barcode).equals(barcode))
                            act._sts(R.id.barcode,barcode);
                    }


                }
            }
        }
        if (data.getData().size() == 0) {
            this.valid(null, "該当商品は在庫がありません", null, params, activity);
            return;
        }

        ListViewAdapter adapter = new ListViewAdapter(
                activity.getApplicationContext()
                , data
                , R.layout.stock_list);
        ListView lv = (ListView) activity.findViewById(R.id.listProduct);
        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        act.setProc(StockAdjustmentActivity.PROC_QTYSUB);
        act.startTimer();
        U.beepNext();

    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        StockAdjustmentActivity act = (StockAdjustmentActivity) activity;
        act.setProc(StockAdjustmentActivity.PROC_BARCODE);
    }

}
