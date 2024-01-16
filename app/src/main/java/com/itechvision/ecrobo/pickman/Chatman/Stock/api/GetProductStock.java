package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.AllocationActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.api.GetDirectReturn;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
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

public class GetProductStock {
    String TAG = "GetProductStock";
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.simple_spinner_dropdown_item;
    public static Spinner spinner ;
    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        MoveStockActivity.count = 0;
        MoveStockActivity act = (MoveStockActivity) activity;
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        ArrayList<String> classificationdata = new ArrayList<String>();
        ArrayList<String> classificationArray = new ArrayList<String>();
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();
        ArrayList<String> partNoList = new ArrayList<>();

        String barcode = null;
        String product_code = null;
        String stndrd_two = null;
        String stndrd_one = null;
        String currentstock = null;
        boolean multicode = false;
        String product_id = "";

        partNoList.add("Select Part ");

     /*   if (BaseActivity.getLotPress())
            act.setProc(MoveStockActivity.PROC_LOT);
        else {*/
            for (int i = 0; i < list.size(); i++) {
                JsonHash row = (JsonHash) list.get(i);

                if (i == 0) {
                    product_id = row.getStringOrNull("product_id");
                    product_code = row.getStringOrNull("code");
                    stndrd_one = row.getStringOrNull("spec1");
                    stndrd_two = row.getStringOrNull("spec2");
                }
                JsonArray list2 = row.getJsonArrayOrNull("stocks");
                if (list2 != null) {

                    for (int j = 0; j < list2.size(); j++) {
                        JsonHash row2 = (JsonHash) list2.get(j);
                        String loc = row2.getStringOrNull("location");
                        //  stock_type_id = row2.getStringOrNull("stock_type_id");

                        String loctxt = act._gts(R.id.source);
                        if ("z".equals(loc) == false) {
                            if (loc.equalsIgnoreCase(loctxt)) {
                                Map<String, String> map = (HashMap) row2;
                                map.put("product_id",product_id);
                                data.add(map);

                                currentstock = row2.getStringOrNull("quantity");
                            }
                        }
                    }
                }
                if (row.containsKey("multi_codes")) {

                    multicode = true;
                    Log.e(TAG, "Lot is pressed multi_codes " + multicode);
                    HashMap<String, String> map1 = new HashMap<>();
                    if (list2 != null) {
                        for (int j = 0; j < list2.size(); j++) {
                            JsonHash row2 = (JsonHash) list2.get(j);
                            map1 = (HashMap) row2;
                        }
                    }

                    map1.put("spec1",row.getStringOrNull("spec1")) ;
                    map1.put("spec2",row.getStringOrNull("spec2"));
                    jsonData.add(map1);
                    Log.e(TAG, " multi_codes1111 " + map1);
                    boolean match = findPart(row.getStringOrNull("code"), partNoList);
                    if (!match)
                        partNoList.add(row.getStringOrNull("code"));

                }

                if (row.containsKey("stock_ids")) {
                    JsonArray row2 = row.getJsonArrayOrNull("stock_ids");
                    Log.e(TAG, "Stock Ids List  " + row2 + row2.size());
                    classificationdata.add("在庫区分を選択");
                    classificationArray.add("");
                    for (int j = 0; j < row2.size(); j++) {
                        JsonHash row1 = (JsonHash) row2.get(j);
                        String id = row1.getStringOrNull("id");
                        String name = row1.getStringOrNull("name");
                        String classification = name + "  :  " + id;
                        classificationdata.add(classification);
                        classificationArray.add(row1.getStringOrNull("id"));
                    }
                }
            }
            Log.e(TAG, "multicode 11111111  "+multicode);
            if (data.size() == 0) {
                valid(null, "該当商品は在庫がありません", null, params, activity);
                return;
            }
            Log.e(TAG, "multicode 222222222222  "+multicode);
            if (classificationdata.size() > 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, classificationdata);
                adapter.setDropDownViewResource(_dropdownRes);
                  spinner = (Spinner) activity.findViewById(R.id.classificationspinner);
                spinner.setAdapter(adapter);



            }




            Log.e(TAG, "multicode 33333333333333  "+multicode);
            if (MoveStockActivity.mBatchList.size() > 0) {
                currentstock = findRepeat(activity, product_code, currentstock);
            }
            Log.e(TAG, "multicode 444444444444  "+multicode);
            act.startTimer();
            if (Integer.parseInt(currentstock) > 0 && !multicode) {
                Log.e(TAG, "multicode 555555555555555  "+multicode);
                act.setProductList(data);
                act._stxtv(R.id.standard1, stndrd_one);
                act.spec1.setSelected(true);
                act._stxtv(R.id.standard2, stndrd_two);
                act.spec2.setSelected(true);
                act._sts(R.id.product_code, product_code);
//                act._sts(R.id.currentStock, currentstock);

                act.nextWork();
                act.setclassificationIdArray(classificationArray);


            } else if (Integer.parseInt(currentstock) > 0 && multicode) {
                Log.e(TAG, "multicode   "+multicode);
                act.partnoLayout.setVisibility(View.VISIBLE);
                act.setProc(MoveStockActivity.PROC_PARTNO);
                act.setProductList(data);
                act.setclassificationIdArray(classificationArray);
                act.getJsonData(jsonData,multicode, partNoList);

                if (partNoList.size()> 1)
                {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                            _singleItemRes, partNoList);
                    adapter.setDropDownViewResource(_dropdownRes);
                    Spinner spinner = (Spinner) activity.findViewById(R.id.partnoSpinner);
                    spinner.setAdapter(adapter);
                    if(partNoList.size() == 2){
                        Log.e(TAG,"spinnerrrrr  ");
                        spinner.setSelection(1);
                    }
                }
            }

             else {
                U.beepError(activity, "No stocks left");
                act.setProc(MoveStockActivity.PROC_BARCODE);
            }

        }
//    }




    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((MoveStockActivity) activity).setProc(MoveStockActivity.PROC_BARCODE);
    }

    String findRepeat(Activity activity,String code, String qty)
    {
            String temp = qty;
            for (Map<String, String> map : MoveStockActivity.mBatchList){
                Log.e(TAG, "fineRepeat");
                String _c = map.get("code");
                String _q = map.get("quantity");
                if(_c.equals(code)){
                    temp = U.minusTo(temp,_q);
                    Log.e(TAG, "fineRepeat11111  "+temp);
                }

            }

        return temp;

    }
    boolean findPart(String part,ArrayList<String> PartList){
        for(String x : PartList){
            if(x.equals(part)){
                return true;

            }
        }
        return false;
    }



}
