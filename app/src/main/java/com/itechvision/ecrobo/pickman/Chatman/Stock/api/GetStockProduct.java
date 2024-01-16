package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.StockChangeActivity;
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
 * Created by lenovo on 3/18/2019.
 */

public class GetStockProduct {
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;

    String TAG = "GetStockProduct";
    //for multiple partno. and single barcode
    public boolean multicode = false;

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        List<Map<String, String>> stocklist = new ArrayList<Map<String, String>>();
        ArrayList<String> PartnoList =new ArrayList<String>();

        ListViewItems data = new ListViewItems();
        StockChangeActivity act = (StockChangeActivity) activity;
        String barcode = null;
        PartnoList.add("Select Part ");

        Log.e(TAG, "LIST of response is>>>>>    "+list);
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            Log.e(TAG, "Row of response is      " + row);
            JsonArray list2 = row.getJsonArrayOrNull("data");
            Log.e(TAG, "DATALIST of response is      " + list2);
            if (list2 != null) {
                for (int j = 0; j < list2.size(); j++) {
                    JsonHash row2 = (JsonHash) list2.get(j);
                    Map<String,String> map = new HashMap<String, String>();

                    if(row2.containsKey("multi_codes")){
                        multicode = true;

                    }
                    Log.e("GetArrivallll","multicode  " +multicode);
                    data.add(data.newItem()
                            .add(R.id.stc_cls_1, row2.getStringOrNull("quantity"))
                            .add(R.id.stc_cls_2, row2.getStringOrNull("st_name")));

                    Log.e(TAG, "DATA becomessssssssssssssssss      " + data);

                    map.put("code",row2.getStringOrNull("code"));
                    map.put("quantity",row2.getStringOrNull("quantity"));
                    map.put("st_name",row2.getStringOrNull("st_name"));
                    map.put("product_id",row2.getStringOrNull("product_id"));
                    map.put("stock_type_id",row2.getStringOrNull("stock_type_id"));
                    Log.e(TAG, "map data becomes   "+ map);

                    boolean match = findRepeat(row2.getStringOrNull("code"), PartnoList);
                    Log.e(TAG, "match data becomes   "+ match);
                    if(!match)
                        PartnoList.add(row2.getStringOrNull("code"));

                    Log.e(TAG, "PartnoList data becomes   "+ PartnoList);


                    if(j==0 && list2.size()==1)
                    {
                        act._sts(R.id.totalQuantity,row2.getStringOrNull("quantity"));
                        act._sts(R.id.stockClassification,row2.getStringOrNull("st_name"));
                        StockChangeActivity.mSelectedId = row2.getStringOrNull("stock_type_id");
                        StockChangeActivity.totalqnty =row2.getStringOrNull("quantity");
                        StockChangeActivity.productID =row2.getStringOrNull("product_id");
                    }
                    stocklist.add(map);
                }
            }
        }



        if(multicode) {
            act.partnoLayout.setVisibility(View.VISIBLE);

            if (PartnoList.size()> 1)
            {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, PartnoList);
                adapter1.setDropDownViewResource(_dropdownRes);
                Spinner spinner = (Spinner) activity.findViewById(R.id.partnoSpinner);
                spinner.setAdapter(adapter1);
                if(PartnoList.size() == 2){
                    Log.e(TAG,"spinnerrrrr  ");
                    spinner.setSelection(1);
                }
            }
            act.getMultiPartList(stocklist,data,  multicode, PartnoList);
            act.setProc(StockChangeActivity.PROC_PARTNO);
        }
        else {

            if (data.getData().size() == 0) {
                this.valid(null, "該当商品は在庫がありません", null, params, activity);
                return;
            }

            ListViewAdapter adapter = new ListViewAdapter(
                    activity.getApplicationContext()
                    , data
                    , R.layout.list_stock_classifications);
            ListView lv = (ListView) activity.findViewById(R.id.listProduct);
            lv.setAdapter(adapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


            act.startTimer();
            if (data.getData().size() == 1)
                act.setProc(StockChangeActivity.PROC_QTY);
            else
                act.setProc(StockChangeActivity.PROC_STOCK_ID);
//        act._sts(R.id.quantity,"1");
            U.beepNext();
            act.getStockData(stocklist);
        }
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((StockChangeActivity) activity).setProc(StockChangeActivity.PROC_BARCODE);
    }

    boolean findRepeat(String part,ArrayList<String> PartList){
        for(String x : PartList){
            if(x.equals(part)){
                return true;
            }
        }
        return false;
    }

}
