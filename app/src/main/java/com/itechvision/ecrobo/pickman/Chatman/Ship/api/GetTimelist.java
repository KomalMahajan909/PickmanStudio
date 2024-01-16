package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Ship.DbatchIncludeActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 5/9/2019.
 */

public class GetTimelist {
    private String TAG = "GetTimelist";
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.simple_spinner_dropdown_item;

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        ArrayList<String> data = new ArrayList<>();
        DbatchIncludeActivity act = (DbatchIncludeActivity) activity;
            JsonHash row = (JsonHash) list.get(0);
        data.add("select time");
        JsonArray time = row.getJsonArrayOrNull("times");
        if (time != null) {
            for (int i=0;i<time.size();i++){

                data.add(time.get(i)+"");
            }
        }
        if (data.size()> 0)
            {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes,data);
                adapter.setDropDownViewResource(_dropdownRes);
                Spinner spinner = (Spinner) activity.findViewById(R.id.timespinner);
                spinner.setAdapter(adapter);
            }
            act.setTimeArray(data);
           act.setProc(DbatchIncludeActivity.PROC_TIME);
        }
    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }
}
