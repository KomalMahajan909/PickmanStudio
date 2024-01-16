package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.SearchProductActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 12/17/2018.
 */

public class GetProduct {
    String TAG = "GetProductStock";
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.simple_spinner_dropdown_item;
    int proc = SearchProductActivity.getKey();

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
		/*ListViewItems data = new ListViewItems();*/
        List<Map<String, String>> data = new ArrayList<>();
        ArrayList<String> productIDs = new ArrayList<String>();

        String code0 ="";
        String name = "";
        String stndrd_one="" ;
        String stndrd_two="";
        String qntytotal ="0";
        String grandTotal ="0";
        String barcode = null;
        String stock="0";
        Log.e("mprocLotttttt", "Lot is pressed" +proc);
        SearchProductActivity act = (SearchProductActivity) activity;
        for (int i = 0; i < list.size(); i++) {

            JsonHash row = (JsonHash) list.get(i);
            code0 = row.getStringOrNull("code");
            stndrd_one = row.getStringOrNull("spec1");
            stndrd_two = row.getStringOrNull("spec2");
            name =row.getStringOrNull("name");
            if(row.containsKey("validstock"))
                stock = (row.getStringOrNull("validstock") == "") ? "0" : row.getStringOrNull("validstock");

            if(row.containsKey("untracked"))
            {
                String totalcount= row.getStringOrNull("untracked");
                act.updateBadge2(totalcount);
            }

            Log.e("GetPRODUCTTTTTTTT", "code ="+ code0+"  Stanadrd _1 ="+stndrd_one +"  Standard_2 ="+stndrd_two);
            JsonArray list2 = row.getJsonArrayOrNull("stocks");
            Log.e("GetProducttttt","Stockkkkkkksssssss        "+list2);
            if (list2 != null) {
                if (list2.size() > 0) {
                    for (int j = 0; j < list2.size(); j++) {
                        JsonHash row2 = (JsonHash) list2.get(j);
                        Log.e("GetProducttttt","Stockkkkkkksssssss    111111    "+row2);
                        String loc = row2.getStringOrNull("location");

                        if ("z".equals(loc) == false) {
                            String location = (row2.getStringOrNull("location") == "") ? "       " : row2.getStringOrNull("location");
                            String quantity = (row2.getStringOrNull("quantity") == "") ? "    " : row2.getStringOrNull("quantity");
                            String label = (row2.getStringOrNull("stock_type_label") == "") ? "      " : row2.getStringOrNull("stock_type_label");
                            productIDs.add(row2.getStringOrNull("id"));
                            String order = (row2.getStringOrNull("remained") == "") ? "0" : row2.getStringOrNull("remained");
//							total= U.plusTo(quantity,order);
                            Map<String, String>  searchData = new  HashMap<String, String> ();

                            // previously used like this
//							searchData = "\t" + location + "\t\t\t" + " : " + "\t\t\t" + quantity + "\t\t" + " : " + "\t\t" + label + "\t\t"+ " : " + "\t\t" + total + "\t\t";
//                            searchData = "\t" + location + "\t\t\t" + " : " + "\t\t\t" + quantity + "\t\t" + " : " + "\t\t" + label + "\t\t";
                            searchData.put("location",location);
                            searchData.put("quantity",quantity);
                            searchData.put("stock_type_label",label);
                            data.add(searchData);
                            qntytotal =U.plusTo(qntytotal,quantity);

                        }

                    }

                }
            }

        }

        act.getProductList(data);
        act.setproductIdArray(productIDs);


        act._sts(R.id.productCode,code0);
        act._stxtv(R.id.productname,name);
        act.productname.setSelected(true);
        act._stxtv(R.id.standard1,stndrd_one);
        act.spec1.setSelected(true);
        act._stxtv(R.id.standard2,stndrd_two);
        act.spec2.setSelected(true);
        act._sts(R.id.totalQuantity, qntytotal);
        act._sts(R.id.orderQuantity,stock);


        U.beepKakutei(act, null);
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        Log.e("mprocLotttttt", "Lot is pressed  " + proc);
        if (proc == SearchProductActivity.PROC_LOT_NO) {
            U.beepError(activity, "LotNo. Not found");
            ((SearchProductActivity) activity).setProc(SearchProductActivity.PROC_LOT_NO);

        } else {
            U.beepError(activity, message);
            ((SearchProductActivity) activity).setProc(SearchProductActivity.PROC_BARCODE);
        }
    }
}
