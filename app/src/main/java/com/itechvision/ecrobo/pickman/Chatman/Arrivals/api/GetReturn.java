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
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ReturnAllocationActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lenovo on 1/16/2019.
 */

public class GetReturn {
    private boolean orderRequestSettings;
    int proc = ReturnAllocationActivity.getKey();
    public static ListViewItems data = new ListViewItems();
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;
    public ArrayList<String> classificationdata ;
    public static String Returnclass ="";
    public void post(String code, String message, JsonArray list,JsonArray ylocList, JsonArray locList,

                     HashMap<String, String> params, Activity activity) {
        orderRequestSettings= BaseActivity.getLotPress();
        classificationdata =new ArrayList<String>();
        ArrayList<String> classificationArray =new ArrayList<String>();
        ArrayList<HashMap<String, String>> allocationlist=new ArrayList<>();
        ArrayList<HashMap<String, String>> ylocationlist = new ArrayList<>();
        ArrayList<HashMap<String, String>> locationlist = new ArrayList<>();

        int count=0;
        String name = "";
        String standard1 = "";
        String standard2 = "";
        String productCode = "";
        String selectedlot = null;
        String selectedexp = null;
        String selectedqty = null;
        data.getData().clear();

        long qty = 0;

        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            Log.e("GetArrival",">>>>>>>>>>>>  "+row);
            String loc = row.getStringOrNull("location");
            HashMap<String, String> map = new HashMap<>();
            Returnclass     = row.getStringOrNull("shop_return_class");
            ReturnAllocationActivity.Good= row.getStringOrNull("classification_g") ;
            ReturnAllocationActivity.Bad  = row.getStringOrNull("classification_b");
            if ("".equals(loc) == false) {
                if (orderRequestSettings) {
/*
                    data.add(data.newItem().add(R.id.arr_all_0, row.getStringOrNull("location"))
                    .add(R.id.arr_all_2, row.getStringOrNull("lot"))
                    .add(R.id.arr_all_1, row.getStringOrNull("expiration_date"))
                    .add(R.id.arr_all_3, row.getStringOrNull("quantity")));
*/
                    map.put("location", row.getStringOrNull("location"));
                    map.put("quantity", row.getStringOrNull("quantity"));
                    String lot = row.getStringOrNull("lot");
                    String expiration = row.getStringOrNull("expiration_date");
                    String qnty = row.getStringOrNull("quantity");
                    String location = row.getStringOrNull("location");
                    String return_class_name = row.getStringOrNull("return_class_name") ;

                    map.put("lot", lot);
                    map.put("expiration",expiration);
                    map.put("return_class_name",return_class_name);
                 /*    map.put("classification_g",classification_g);
                       map.put("classification_b",classification_b);
*/
                    if (location.equals("y") && !lot.equals("") && Integer.parseInt(qnty) > 0) {
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
                allocationlist.add(map);

                name = row.getStringOrNull("name");
                standard1 = row.getStringOrNull("spec1");
                standard2 = row.getStringOrNull("spec2");

                if (qty == 0) qty = Long.parseLong(row.getStringOrNull("rest"));
                if (i == 0) productCode = row.getStringOrNull("code");

                if (row.containsKey("fix_location_flag"))
                {
                    ReturnAllocationActivity.fixed_location = row.getStringOrNull("fix_location_flag");
                    if(!loc.equals("z") && !loc.equals("y"))
                        ReturnAllocationActivity.location_fixed = loc;
                    Log.e("GetReturnAllocation  ", "fixedLocation "+ReturnAllocationActivity.location_fixed);
                }
            }
        }

        Log.e("GetReturnAllocation", "YlocList   "+ylocList);
        for (int i = 0; i < ylocList.size(); i++) {
            JsonHash row = (JsonHash) ylocList.get(i);
            HashMap<String, String> map = new HashMap<>();
            String loc = row.getStringOrNull("location");

            if ("".equals(loc) == false) {
                if (orderRequestSettings) {

                    map.put("location", row.getStringOrNull("location"));
                    map.put("quantity", row.getStringOrNull("quantity"));
                    map.put("id", row.getStringOrNull("id"));
                    map.put("product_id", row.getStringOrNull("product_id"));
                    String lot = row.getStringOrNull("lot");
                    String expiration = row.getStringOrNull("expiration_date");
                    String qnty = row.getStringOrNull("quantity");
                    String location = row.getStringOrNull("location");
                    String return_class_name = row.getStringOrNull("return_class_name") ;
                  /*  String classification_g= row.getStringOrNull("classification_g") ;
                    String classification_b     = row.getStringOrNull("classification_b");
*/

                    map.put("lot", lot);
                    map.put("expiration", expiration);
                    map.put("return_class_name", return_class_name);
               /*     map.put("classification_g",classification_g);
                    map.put("classification_b",classification_b);*/

                    if (location.equals("y") && !lot.equals("") && Integer.parseInt(qnty) > 0) {
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
                ylocationlist.add(map);
            }
        }
        Log.e("GetReturnAllocation", "locList   "+locList);
        data.getData().clear();
        for (int i = 0; i < locList.size(); i++) {
            JsonHash row = (JsonHash) locList.get(i);
            HashMap<String, String> map = new HashMap<>();
            String loc = row.getStringOrNull("location");

            if ("".equals(loc) == false) {
                if (orderRequestSettings) {
                    data.add(data.newItem().add(R.id.arr_all_0, row.getStringOrNull("location"))
                            .add(R.id.arr_all_2, row.getStringOrNull("lot"))
                            .add(R.id.arr_all_1, row.getStringOrNull("expiration_date"))
                            .add(R.id.arr_all_3, row.getStringOrNull("quantity"))
                            .add(R.id.arr_all_4, row.getStringOrNull("return_class_name")));

                    map.put("location", row.getStringOrNull("location"));
                    map.put("quantity", row.getStringOrNull("quantity"));
                    String lot = row.getStringOrNull("lot");
                    String expiration = row.getStringOrNull("expiration_date");
                    String qnty = row.getStringOrNull("quantity");
                    String location = row.getStringOrNull("location");
                    map.put("lot", lot);
                    map.put("expiration", expiration);

                } else {
                    data.add(data.newItem().add(R.id.arr_all_0, row.getStringOrNull("location"))
                            .add(R.id.arr_all_3, row.getStringOrNull("quantity")));
                    map.put("location", row.getStringOrNull("location"));
                    map.put("quantity", row.getStringOrNull("quantity"));
                }
                locationlist.add(map);
            }
        }

        if (data.getData().size() > 0) {
            ListViewAdapter adapter = new ListViewAdapter(activity.getApplicationContext(), data, R.layout.list_allocation_row){

                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    LinearLayout lot = (LinearLayout)v.findViewById(R.id.layoutlot);
                    LinearLayout exp = (LinearLayout)v.findViewById(R.id.layoutexp);
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
            ListView lv = (ListView) activity.findViewById(R.id.listLocations);
            lv.setAdapter(adapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_NONE);

        }
        ReturnAllocationActivity act = (ReturnAllocationActivity) activity;

        if (classificationdata.size()> 0) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(), _singleItemRes, classificationdata);
            adapter.setDropDownViewResource(_dropdownRes);
            Spinner spinner = (Spinner) activity.findViewById(R.id.classificationspinner);
            spinner.setAdapter(adapter);

        }
        U.beepSuccess();

        if(orderRequestSettings) {
            if (proc == ReturnAllocationActivity.PROC_LOT_NO) {
                act.lotexist=true;
                act.setProc(ReturnAllocationActivity.PROC_QTY);
            }else{
                if(count==0) {
                    act.lotexist=false;
                    act.lotlayout.setVisibility(View.GONE);
                    act.explayout.setVisibility(View.GONE);
                    act.setProc(ReturnAllocationActivity.PROC_QTY);
                } else {
                    act.lotexist=false;
                    act._sts(R.id.lotno, selectedlot);
                    act._sts(R.id.expiration, selectedexp);
                    act._sts(R.id.totalquantity, selectedqty);
                    act.setProc(ReturnAllocationActivity.PROC_QTY);
                }
            }
        } else {
            act.setProc(ReturnAllocationActivity.PROC_QTY);
        }

        act.startTimer();
        act.setAllocationlist(allocationlist);
        act.setYAllocationlist(ylocationlist);
        act.setlocationlist(locationlist);
        act._sts(R.id.quantity, "1");
        act._sts(R.id.totalquantity, String.valueOf(qty));
        act._sts(R.id.product_code, productCode);
        act._stxtv(R.id.product_name, name);
        act.productName.setSelected(true);
        act._sts(R.id.standard, standard1+"  "+standard2);
        act.mTextToSpeak.startSpeaking("1");
        act.getClassificationList();

        Log.e("GetAllocation","<<<<<<<<<<>>>>>>>>>>>>");
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((ReturnAllocationActivity) activity).setProc(ReturnAllocationActivity.PROC_BARCODE);
    }


}
