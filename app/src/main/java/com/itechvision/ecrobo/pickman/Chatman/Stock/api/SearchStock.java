package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.graphics.Color;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.itechvision.ecrobo.pickman.Chatman.Stock.StockAdjustmentActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.TruckSearchActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 10/4/2019.
 */

public class SearchStock {
    String TAG = "SearchStock";

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {

        ListViewItems data = new ListViewItems();
        TruckSearchActivity act = (TruckSearchActivity) activity;
        String barcode = null;
        List<Map<String, String>> stocklist = new ArrayList<Map<String, String>>();
            JsonHash row = (JsonHash) list.get(0);
           if(row.containsKey("product_stock_list")){
            JsonArray list2 = row.getJsonArrayOrNull("product_stock_list");
            if (list2 != null) {
                for (int j = 0; j < list2.size(); j++) {
                    JsonHash row2 = (JsonHash) list2.get(j);
                    Map<String,String> map = new HashMap<String, String>();
                    String loc = row2.getStringOrNull("location");


                    Log.e(TAG, "locccc  "+loc);

                    map.put("location",loc);
                    map.put("quantity",row2.getStringOrNull("quantity"));
                    map.put("product_stock_id", row2.getStringOrNull("product_stock_id"));
                    map.put("id",  row2.getStringOrNull("id"));

                   if(findRepeat(loc,stocklist)==false){
                    stocklist.add(map);

                       data.add(data.newItem().add(R.id.stc_prd_1, loc)
                               .add(R.id.stc_prd_2, row2.getStringOrNull("quantity")));

                   }}
            }

        if (data.getData().size() == 0) {
            this.valid(null, "該当商品は在庫がありません", null, params, activity);
            return;
        }

        ListViewAdapter adapter = new ListViewAdapter(activity.getApplicationContext(), data
                , R.layout.location_list){

            @Override
               public View getView(final int position, View convertView, ViewGroup parent) {
                   View v = super.getView(position, convertView, parent);

                       if (position == 0) {
                           v.setEnabled(false);
                       } else {
                           v.setEnabled(true);
                       }

                   return v;
               }
           };
        ListView lv = (ListView) activity.findViewById(R.id.listProduct);

        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        act.getStockData(stocklist);
        U.beepNext();

    }
    else {
               U.beepError(act,"No Location found");
           }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);

    }
    boolean findRepeat(String loc, List<Map<String,String>> PartList){
        for(Map<String,String> map : PartList){
            String location = map.get("location");
            if(location.equals(loc)){
                return true;

            }
        }
        return false;
    }
}
