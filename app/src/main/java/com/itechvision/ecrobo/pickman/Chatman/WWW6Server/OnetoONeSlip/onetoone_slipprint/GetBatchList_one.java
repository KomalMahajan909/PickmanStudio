package com.itechvision.ecrobo.pickman.Chatman.WWW6Server.OnetoONeSlip.onetoone_slipprint;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.WWW6Server.OnetoONeSlip.One_to_One_SlipPrinter;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 9/12/2019.
 */

public class GetBatchList_one {

    private String TAG = "GetBatchList";
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;

    public void post( JsonArray list, Activity activity) {
        List<Map<String, String>> data = new ArrayList<>();
        List<String> batch = new ArrayList<>();
        One_to_One_SlipPrinter act = (One_to_One_SlipPrinter) activity;

          JsonHash row = (JsonHash) list.get(0);
            Log.e(TAG,"<<<<<<<<<<<<<<<<<<<"+row);
            JsonArray batchList = row.getJsonArrayOrNull("batch_list");
            Log.e(TAG,"<<<<<<<<<<<<<<<<<<<11111111111111111"+batchList);
            batch.add("Select batch");


            if (batchList.size()>0) {
                for (int j=0;j<batchList.size();j++){
                    JsonHash row1 = (JsonHash) batchList.get(j);
                    Log.e(TAG,"<<<<<<<<<<<<<<<<<<<2222222222222"+row1);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("batch_id",row1.getStringOrNull("batch_id"));
                    map.put("batch_list_name",row1.getStringOrNull("batch_list_name"));
                    if(row1.containsKey("remaining_orders"))
                    map.put("remaining_orders",row1.getStringOrNull("remaining_orders"));
                    batch.add(row1.getStringOrNull("batch_list_name"));
                    data.add(map);
                    Log.e(TAG,"<<<<<<<<<<333333333"+data);
                }
            }
        if (batch.size()> 1)
            {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, batch);
                adapter.setDropDownViewResource(_dropdownRes);
                Spinner spinner = (Spinner) activity.findViewById(R.id.batchspinner);
                spinner.setAdapter(adapter);

                if(data.size()> 0 ){
                    act.setProductsArray(data);
                    act.setProc(One_to_One_SlipPrinter.PROC_BATCH);
                    act.setBatchPosition();
                }
            }

            else {
               U.beepError(activity, "no product found");
        }


    }
    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }
}
