package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TotalPickListActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 1/3/2020.
 */

public class GetPickRow {

    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;
    private String TAG = "GetPickRow";
    public static int nyukacount =0;
    //for multiple partno. and single barcode
    public boolean multicode = false;

    TextToSpeak mTextToSpeak;


    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {


        nyukacount = 0;
        mTextToSpeak = new TextToSpeak(activity);
        TotalPickListActivity act = (TotalPickListActivity) activity;

        Map<String, String> data = new HashMap<>();
        ArrayList<String> codeList = new ArrayList<String>();

        List<Map<String, String>> pickData = new ArrayList<Map<String, String>>();
        List<Map<String, String>> usedData = new ArrayList<Map<String, String>>();
        List<Map<String, String>> otheruserData = new ArrayList<Map<String, String>>();

        String product_code = null, name = null;

        JsonHash row = (JsonHash) list.get(0);

        JsonArray list1 = row.getJsonArrayOrNull("picking_none");


        for (int i = 0; i < list1.size(); i++) {
            JsonHash row4 = list1.getJsonHashOrNull(i);
            Map<String, String> map2 = (HashMap) row4;

            String val = row4.getStringOrNull("picking_no");

            if (!val.equals("")) {
                boolean match = findRepeat(val, pickData);
                if (!match) {
                    pickData.add(map2);
                }
            }
        }

        JsonArray list2 = row.getJsonArrayOrNull("picking_user");

        for(int i = 0; i< list2.size(); i++)
        {
            JsonHash row2 = list2.getJsonHashOrNull(i);
            Map<String,String> map = (HashMap)row2;

            String val = row2.getStringOrNull("picking_no");

            if (!val.equals("")) {
                boolean match = findRepeat(val, usedData);
                if (!match) {
                    usedData.add(map);
                }
            }
        }

        JsonArray list3 = row.getJsonArrayOrNull("picking_other");

        for(int i = 0; i< list3.size(); i++)
        {
            JsonHash row2 = list3.getJsonHashOrNull(i);
            Map<String,String> map = (HashMap)row2;
            Log.e(TAG, "listtttt333333  "+row2);

            String val = row2.getStringOrNull("picking_no");

            if (!val.equals("")) {
                boolean match = findRepeat(val, otheruserData);
                if (!match) {
                    otheruserData.add(map);
                }
            }
        }

        if (TotalPickListActivity.mRequestStatus == TotalPickListActivity.REQ_BARCODE)
        {
            if (row.containsKey("product_row")) {
                JsonArray list4 = row.getJsonArrayOrNull("product_row");
                JsonHash row3 = list4.getJsonHashOrNull(0);
                Map<String, String> map = (HashMap) row3;
                map.put("processedCnt", "0");
                JsonArray list5 = row3.getJsonArrayOrNull("barcode");
                Log.e(TAG, "listtttt444444444444  " + list5);
                codeList = (ArrayList) list5;
                Log.e(TAG, "listtttt55555555  " + codeList);


                data = map;
                act.currLineData(data, codeList);
                act.setProc(TotalPickListActivity.PROC_BARCODE);
                act._sts(R.id.sku, data.get("code"));
                act._stxtv(R.id.productName, data.get("name"));
                act.productName.setSelected(true);
                act._sts(R.id.totalquantity, data.get("quantity"));
                act._sts(R.id.quantity,"1");



                act.setListData(pickData,usedData,otheruserData);
                int size = usedData.size()+ otheruserData.size();
                act.updateBadge1(usedData.size()+"");
                act.updateBadge2(size+"");
                act.nextWork();
            }
            else
                {
                U.beepError(act, "ゾーンリストが見つかりません");
            }
    }
    else if (TotalPickListActivity.mRequestStatus == TotalPickListActivity.REQ_QTY) {

            if (pickData.size() > 0) {
                act.setListData(pickData, usedData, otheruserData);
                act.setProc(TotalPickListActivity.PROC_BARCODE);
                act.cProductList.clear();
                act._sts(R.id.sku, "");
                act._stxtv(R.id.productName, "");
                act._sts(R.id.totalquantity, "");
                act._sts(R.id.quantity, "");
                act._sts(R.id.barcode, "");

                int size = usedData.size() + otheruserData.size();
                act.updateBadge1(usedData.size() + "");
                act.updateBadge2(size + "");


            } else act.nextProcess();
        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        if (TotalPickListActivity.mRequestStatus == TotalPickListActivity.REQ_BARCODE)
        ((TotalPickListActivity) activity).setProc(TotalPickListActivity.PROC_BARCODE);
        else if (TotalPickListActivity.mRequestStatus == TotalPickListActivity.REQ_QTY)
            ((TotalPickListActivity) activity).setProc(TotalPickListActivity.PROC_QTY);
    }
    boolean findRepeat(String val,List<Map<String, String>> list){
        for(Map<String, String> map : list){
            String x = map.get("picking_no");
            if(x.equals(val)){
                return true;

            }
        }
        return false;
    }
}

