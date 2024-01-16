package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.AllocationActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lenovo on 1/7/2019.
 */

public class ListStockId {
    private boolean orderRequestSettings;
    int proc = AllocationActivity.getKey();

    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        ArrayList<String> classificationArray = new ArrayList<String>();
        ArrayList<String> classificationdata = new ArrayList<String>();
        ArrayList<String> defaultclassificationdata = new ArrayList<String>();
        ListViewItems data = new ListViewItems();
        String barcode = null;
        long qty = 0;
        int count = 0;
        AllocationActivity act = (AllocationActivity) activity;
        orderRequestSettings = BaseActivity.getLotPress();
        Log.e("mprocLotttttt", "Lot is pressed  " + proc);


        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            HashMap<String, String> map = new HashMap<>();

            if (row.containsKey("stock_ids")) {
                act.defaultclassificationIdArray.clear();
                JsonArray row2 = row.getJsonArrayOrNull("stock_ids");
                Log.e("GetArriavllllllll", "Stock Ids List  " + row2 + row2.size());
                classificationdata.add("在庫区分を選択");
                classificationArray.add("");
                for (int j = 0; j < row2.size(); j++) {
                    JsonHash row1 = (JsonHash) row2.get(j);
                    String id = row1.getStringOrNull("id");
                    String name = row1.getStringOrNull("name");
                    String classification = id + "  :  " + name;
                    classificationdata.add(classification);
                    defaultclassificationdata.add(classification);
                    classificationArray.add(row1.getStringOrNull("id"));
                }
            }

        }
     //   if (GetAllocation.data.getData().size() > 0) {
            Spinner spinner = (Spinner) activity.findViewById(R.id.classificationspinner);
            if (classificationdata.size() > 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, classificationdata);
                adapter.setDropDownViewResource(_dropdownRes);
                spinner.setVisibility(View.VISIBLE);
                spinner.setAdapter(adapter);
            } else {
                spinner.setVisibility(View.GONE);
            }
            U.beepSuccess();

            act.setclassificationIdArray(classificationArray);

//            act.setdefaultclassificationIdArray(classificationArray);
//            act.setdefaultclassificationArray(defaultclassificationdata);
            String size = classificationArray.size()+"";
//            act.updateBadge3(size);
        }
  //  }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        Log.e("mprocLott", "Lot is pressed  " + proc);

    }
}
