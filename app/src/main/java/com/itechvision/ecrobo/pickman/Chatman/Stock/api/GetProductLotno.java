package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Stock.MoveStockActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 1/10/2019.
 */

public class GetProductLotno {
    String TAG = "GetProductLotno";
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.simple_spinner_dropdown_item;


    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        MoveStockActivity act = (MoveStockActivity) activity;
        MoveStockActivity.count =0;
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        String product_code = null;
        String stndrd_two = null;
        String stndrd_one = null;
        String currentstock = null;

        ArrayList<String> classificationdata =new ArrayList<String>();
        ArrayList<String> classificationArray =new ArrayList<String>();

        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);

            if (i == 0) {
                product_code = row.getStringOrNull("code");
                stndrd_one = row.getStringOrNull("spec1");
                stndrd_two = row.getStringOrNull("spec2");

            }
            String loctxt = act._gts(R.id.source);
            String lot= act._gts(R.id.lotno);
            JsonArray list2 = row.getJsonArrayOrNull("stocks");
            if (list2 != null) {
                for (int j = 0; j < list2.size(); j++) {
                    JsonHash row2 = (JsonHash) list2.get(j);
                    String loc = row2.getStringOrNull("location");
                    String lotno= row2.getStringOrNull("lot");

                    if ("z".equals(loc) == false) {
                        if (loc.equals(loctxt) &&  lotno.equals(lot)) {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("location", row2.getStringOrNull("location"));
                            map.put("quantity", row2.getStringOrNull("quantity"));
                            map.put("lot",row2.getStringOrNull("lot"));
                            map.put("stock_type_label", row2.getStringOrNull("stock_type_label"));
                            map.put("id", row2.getStringOrNull("id"));
                            data.add(map);
                            currentstock = row2.getStringOrNull("quantity");
                        }
                    }

                }
            }
            if( row.containsKey("stock_ids"))
            {
                JsonArray row2= row.getJsonArrayOrNull("stock_ids");
                Log.e("GetArriavllllllll","Stock Ids List  "+row2  +row2.size());
                classificationdata.add("在庫区分を選択");
                classificationArray.add("");
                for (int j = 0; j < row2.size(); j++) {
                    JsonHash row1 = (JsonHash) row2.get(j);
                    String id=row1.getStringOrNull("id");
                    String name=row1.getStringOrNull("name");
                    String classification= name + "  :  "+ id;
                    classificationdata.add(classification);
                    classificationArray.add(row1.getStringOrNull("id"));
                }
            }
        }
        if (data.size() == 0) {
            this.valid(null, "該当商品は在庫がありません", null, params, activity);
            return;
        }
        if (classificationdata.size()> 0)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                    _singleItemRes, classificationdata);
            adapter.setDropDownViewResource(_dropdownRes);
            Spinner spinner = (Spinner) activity.findViewById(R.id.classificationspinner);
            spinner.setAdapter(adapter);
        }

        act.setProductList(data);
        act._stxtv(R.id.standard1, stndrd_one);
        act.spec1.setSelected(true);
        act._stxtv(R.id.standard2, stndrd_two);
        act.spec2.setSelected(true);
        act._sts(R.id.product_code, product_code);
        act._sts(R.id.currentStock, currentstock);
        act.startTimer();
//        U.beepSiekai(activity, null);
        act._sts(R.id.quantity, "1");
        act.mTextToSpeak.startSpeaking("1");
        act.setProc(MoveStockActivity.PROC_QTY);
        act.setclassificationIdArray(classificationArray);
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((MoveStockActivity) activity).setProc(MoveStockActivity.PROC_LOT);
    }

}
