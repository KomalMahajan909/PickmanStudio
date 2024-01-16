package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.AllocationActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lenovo on 1/4/2019.
 */

public class GetAllocation {
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;

    String TAG = "GetAllocation";
    private boolean orderRequestSettings;
    int proc = AllocationActivity.getKey();
    public static ListViewItems data = new ListViewItems();
    public boolean multicode = false;

    public void post(String code, String message, JsonArray list,JsonArray zlocList, JsonArray locList,
                     HashMap<String, String> params, Activity activity)
    {

        ArrayList<HashMap<String, String>> allocationlist = new ArrayList<>();
        ArrayList<HashMap<String, String>> zlocationlist = new ArrayList<>();
        ArrayList<HashMap<String, String>> locationlist = new ArrayList<>();
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();
        ArrayList<String> partNoList = new ArrayList<>();

        long qty = 0;
        int count=0;
        String productCode = "";
        String name = "";
        String standard1 = "";
        String standard2 = "";
        //  String abc_flag = "";
        String selectedlot = null;
        String selectedexp = null;
        String selectedqty = null;
        String caseqty = "";
        data.getData().clear();
        AllocationActivity act = (AllocationActivity) activity;
        orderRequestSettings= BaseActivity.getLotPress();
        Log.e("mprocLotttttt", "Lot is pressed  " + proc);

        partNoList.add("Select Part ");
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            HashMap<String, String> map = new HashMap<>();
            String loc = row.getStringOrNull("location");
            if (row.containsKey("multi_codes")) {

                multicode = true;
                Log.e(TAG, "Lot is pressed multi_codes " + multicode);
                HashMap<String, String> map1 = new HashMap<>();
                map1 = (HashMap) row;
                jsonData.add(map1);
                Log.e(TAG, " multi_codes1111 " + map1);
                boolean match = findRepeat(row.getStringOrNull("code"), partNoList);
                if(!match)
                    partNoList.add(row.getStringOrNull("code"));

            }
            if ("".equals(loc) == false) {
                if (orderRequestSettings) {

                    map.put("location", row.getStringOrNull("location"));
                    map.put("quantity", row.getStringOrNull("quantity"));
                    String lot = row.getStringOrNull("lot");
                    String expiration = row.getStringOrNull("expiration_date");
                    String qnty = row.getStringOrNull("quantity");
                    String location = row.getStringOrNull("location");
                    caseqty = row.getStringOrNull("case_qty");



                    map.put("lot", lot);
                    map.put("expiration", expiration);
                    map.put("case_qty",caseqty);

                    if (location.equals("z") && !lot.equals("") && Integer.parseInt(qnty) > 0) {
                        selectedlot = lot;
                        selectedqty = qnty;
                        selectedexp = expiration;

                        count++;
                    }
                } else {
/*
                        data.add(data.newItem().add(R.id.arr_all_0, row.getStringOrNull("location"))
                                .add(R.id.arr_all_3, row.getStringOrNull("quantity")));
*/
                    map.put("location", row.getStringOrNull("location"));
                    map.put("quantity", row.getStringOrNull("quantity"));
                }

                if (row.containsKey("fix_location_flag")) {
                    AllocationActivity.fixed_location_flag = row.getStringOrNull("fix_location_flag");
                    if (!loc.equals("z") && !loc.equals("y") && row.containsKey("fixed_location"))
                        AllocationActivity.location_fixed = row.getStringOrNull("fixed_location");
                    Log.e(TAG, "fixedLocation " + AllocationActivity.location_fixed);
                }
                allocationlist.add(map);
            }

            name = row.getStringOrNull("name");
            standard1 = row.getStringOrNull("spec1");
            standard2 = row.getStringOrNull("spec2");
            //    abc_flag = row.getStringOrNull("abc_flag");
            if (qty == 0) qty = Long.parseLong(row.getStringOrNull("rest"));
            if (i == 0) productCode = row.getStringOrNull("code");
        }

        Log.e(TAG, "ZlocList   "+zlocList);
        for (int i = 0; i < zlocList.size(); i++) {
            JsonHash row = (JsonHash) zlocList.get(i);
            HashMap<String, String> map = new HashMap<>();
            String loc = row.getStringOrNull("location");

            if ("".equals(loc) == false) {
                if (orderRequestSettings) {

                    map.put("location", row.getStringOrNull("location"));
                    map.put("quantity", row.getStringOrNull("quantity"));
                    String lot = row.getStringOrNull("lot");
                    String expiration = row.getStringOrNull("expiration_date");
                    String qnty = row.getStringOrNull("quantity");
                    String location = row.getStringOrNull("location");

                    map.put("lot", lot);
                    map.put("id", row.getStringOrNull("id"));
                    map.put("product_id", row.getStringOrNull("product_id"));

                    map.put("expiration", expiration);
                    map.put("case_qty",row.getStringOrNull("case_qty"));

                    if (location.equals("z") && !lot.equals("") && Integer.parseInt(qnty) > 0)
                    {
                        selectedlot = lot;
                        selectedqty = qnty;
                        selectedexp = expiration;
                    }
                } else {
                    map.put("location", row.getStringOrNull("location"));
                    map.put("quantity", row.getStringOrNull("quantity"));
                    map.put("id", row.getStringOrNull("id"));
                    map.put("product_id", row.getStringOrNull("product_id"));

                }
                zlocationlist.add(map);
            }
        }
        Log.e(TAG, "locList   "+locList);
        data.getData().clear();
        for (int i = 0; i < locList.size(); i++) {
            JsonHash row = (JsonHash) locList.get(i);
            HashMap<String, String> map = new HashMap<>();
            String loc = row.getStringOrNull("location");



            if ("".equals(loc) == false) {
                if (orderRequestSettings) {
                    data.add(data.newItem().add(id.arr_all_0, row.getStringOrNull("location"))
                            .add(id.arr_all_2, row.getStringOrNull("lot"))
                            .add(id.arr_all_1, row.getStringOrNull("expiration_date"))
                            .add(id.arr_all_3, row.getStringOrNull("quantity")));

                    map.put("location", row.getStringOrNull("location"));
                    map.put("quantity", row.getStringOrNull("quantity"));
                    String lot = row.getStringOrNull("lot");
                    String expiration = row.getStringOrNull("expiration_date");
                    String qnty = row.getStringOrNull("quantity");
                    String location = row.getStringOrNull("location");

                    map.put("lot", lot);
                    map.put("expiration", expiration);


                } else {
                    data.add(data.newItem().add(id.arr_all_0, row.getStringOrNull("location"))
                            .add(id.arr_all_3, row.getStringOrNull("quantity")));
                    map.put("location", row.getStringOrNull("location"));
                    map.put("quantity", row.getStringOrNull("quantity"));
                }
                locationlist.add(map);
            }
        }


        if(!multicode){
            Log.e(TAG, "multicode condition 1111111111 "+multicode);
            if (data.getData().size() > 0) {
                ListViewAdapter adapter = new ListViewAdapter(
                        activity.getApplicationContext()
                        , data
                        , R.layout.list_allocation_row) {
                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {
                        View v = super.getView(position, convertView, parent);
                        LinearLayout lot = (LinearLayout) v.findViewById(id.layoutlot);
                        LinearLayout exp = (LinearLayout) v.findViewById(id.layoutexp);
                        if (orderRequestSettings) {
                            lot.setVisibility(View.VISIBLE);
                            exp.setVisibility(View.VISIBLE);
                        } else {
                            lot.setVisibility(View.GONE);
                            exp.setVisibility(View.GONE);
                        }
                        return v;
                    }
                };
                ListView lv = (ListView) activity.findViewById(id.listLocations);
                lv.setAdapter(adapter);
                lv.setChoiceMode(ListView.CHOICE_MODE_NONE);
            }

            U.beepSuccess();

            if (orderRequestSettings) {
                if (proc == AllocationActivity.PROC_LOT_NO) {
                    act.lotexist = true;
                    act.setProc(AllocationActivity.PROC_QTY);
                } else {
                    if (count == 0) {
                        act.lotexist = false;
                        act.lotlayout.setVisibility(View.GONE);
                        act.explayout.setVisibility(View.GONE);
                        act.setProc(AllocationActivity.PROC_QTY);
                    } else {
                        act.lotexist = false;
                        act._sts(id.lotno, selectedlot);
                        act._sts(id.expiration, selectedexp);
                        act._sts(id.totalquantity, selectedqty);
                        act.setProc(AllocationActivity.PROC_QTY);
                    }
                }
                if(BaseActivity.get_CaseQtyCheckSelfPut())
                    act.setProc(AllocationActivity.PROC_LOCATION);
            } else {
                if(BaseActivity.get_CaseQtyCheckSelfPut())
                    act.setProc(AllocationActivity.PROC_LOCATION);
                else
                    act.setProc(AllocationActivity.PROC_QTY);
            }
            act.startTimer();
            if(BaseActivity.get_CaseQtyCheckSelfPut())
                act._sts(id.quantity, caseqty);
            else
                act._sts(id.quantity, "1");
//            act._sts(id.totalquantity, String.valueOf(qty));
            act._sts(id.product_code, productCode);
            //   act._sts(id.abc, abc_flag);
            act._stxtv(id.product_name, name);
            act.productName.setSelected(true);
            act._sts(id.standard, standard1 + "  " + standard2);
            act.setAllocationlist(allocationlist);

            act.setZlocationlist(zlocationlist);
            act.setlocationlist(locationlist);

            Log.e(TAG, "getClassificationList condition 22222222222 "+multicode);
            act.getClassificationList();
        }
        else{
            U.beepSuccess();
            Log.e(TAG, "multicode condition 22222222222 "+multicode);
            act.partnoLayout.setVisibility(View.VISIBLE);
            act.setProc(AllocationActivity.PROC_PARTNO);
            act.getJsonData(jsonData,multicode, partNoList);


            if (partNoList.size()> 1)
            {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, partNoList);
                adapter.setDropDownViewResource(_dropdownRes);
                Spinner spinner = (Spinner) activity.findViewById(id.partnoSpinner);
                spinner.setAdapter(adapter);
                if(partNoList.size() == 2){
                    Log.e(TAG,"spinnerrrrr  ");
                    spinner.setSelection(1);
                }
            }
        }

    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        Log.e("mprocLotttttt", "Lot is pressed  " + proc);
        if (proc == AllocationActivity.PROC_LOT_NO) {
            AllocationActivity act = (AllocationActivity) activity;
            U.beepError(activity, "LotNo. Not found");
            act._sts(id.lotno, "");
            ((AllocationActivity) activity).setProc(AllocationActivity.PROC_LOT_NO);

        } else {
            U.beepError(activity, message);
            ((AllocationActivity) activity).setProc(AllocationActivity.PROC_BARCODE);
        }
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
