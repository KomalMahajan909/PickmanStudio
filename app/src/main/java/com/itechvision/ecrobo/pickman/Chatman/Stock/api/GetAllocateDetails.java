package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.SlipPrinterActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.StockChangeActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 9/19/2019.
 */

public class GetAllocateDetails {
    String TAG = "GetAllocateDetails";

    public void post( JsonArray list, Activity activity) {
        Map<String, String> productlist = new HashMap<>();
        Map<String, String> submissionlist = new HashMap<>();
        ListViewItems data = new ListViewItems();

        String [] recipients;
        String company;
        String remark;
        SlipPrinterActivity act = (SlipPrinterActivity) activity;
        String barcode = null;
        Log.e(TAG, "LIST of response is>>>>>    "+list);
        for (int i = 0; i < list.size(); i++) {
            JsonHash row1 = (JsonHash) list.get(i);
            String remaining_orders = row1.getStringOrNull("remaining_orders");
            String total = row1.getStringOrNull("total_orders");
            act.updateBadge1(total);
            act.updateBadge2(remaining_orders);

            Log.e(TAG," listtt row"+row1 + BaseActivity.getShopId());

            if(row1.containsKey("orders")){
                Log.e(TAG," listtt row1111   ");
                if(row1.containsKey("order")){
                    JsonArray result1 = row1.getJsonArrayOrNull("order");
                    for (int j = 0; j < result1.size(); j++) {
                        JsonHash row = (JsonHash) result1.get(j);
                        submissionlist =(HashMap) row;
                    }

                }

                act.getsubmitList(submissionlist);

                U.beepKakutei(activity, "検品データを登録しました。");

                remark = row1. getStringOrNull("pikking_method");
                if(!remark.equals("") && BaseActivity.getRemarkPress()) {

                    act.dilaogCustomercancel(remark + "(注文番号: " + submissionlist.get("order_no") + ")");

                }
                else if(BaseActivity.getBoxSelected() == true){
                    act.CallBoxSizeScreen();
                }

                else {
                    if(remaining_orders.equals("0") && total.equals("0"))
                    {
                    act.nextProcess();
                    }
                    else if(remaining_orders.equals("0") && !total.equals("0"))
                    {

                        act.setProc(SlipPrinterActivity.PROC_BATCH);
                        act.resetspinner();
                        act.setlayout();
                    }
                    else {
                        act._sts(R.id.barcode,"");
                       act.setProc(SlipPrinterActivity.PROC_BARCODE);
                       act.setlayout();
                    }
                }

            }
            else if(row1.containsKey("data") ){
                    JsonArray list1 = row1.getJsonArrayOrNull("data");
                    JsonHash row2 = (JsonHash) list1.get(0);
                    productlist =(HashMap) row2;
                    productlist.put("processedCnt","0");

                    act._sts(R.id.totalQuantity,productlist.get("product_count"));
                    act._sts(R.id.productCode,productlist.get("code"));
                    act._sts(R.id.productName,productlist.get("product_name"));
                    act._sts(R.id.orderNo,productlist.get("order_no"));
                    act._sts(R.id.receiver_name,productlist.get("receiver_name"));

                    String processedCnt = productlist.get("processedCnt");
                    productlist.put("processedCnt", U.plusTo(processedCnt, "1"));
                    act.nextWork(productlist);
            }
        }
        }
}
