package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewShippingGroupActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 10/26/2019.
 */

public class GetPickingOrderGroup {
    public static String getbarcode= null;
    public static String getlot= null;
    private TextToSpeak mTextToSpeak;
    public static boolean gotbox = false;
    public static int track=0;
    public static int group=0;
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    public  static String remark= "";
    String TAG = "GetPickingOrderGroup";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        //List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        ArrayList<String> tracklist = new ArrayList<>();
//        NewShippingGroupActivity.count=0;

        final NewShippingGroupActivity act = (NewShippingGroupActivity) activity;

        String all_order_count = "0";
        String all_row_count = "0";
        String tracking = null;
        String order_id = null;
        String track_present = null;
        String group_order= null;
        String group_no =null;
        String is_scanned=null;
        String user="";
        String remarkPresent= "false";
        mTextToSpeak = new TextToSpeak(activity);
        if (list.size() > 0) {
            remark= "";
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            all_order_count = row.getStringOrNull("all_order_count");

            String not_inspected = row.getStringOrNull("not_inspection_row_count");
            String short_inspected = row.getStringOrNull("shortage_row_count");
            user=row.getStringOrNull("user");
            if (BaseActivity.getOrderInfoBy() == SettingActivity.ORDER_TRACKING_NO)
                track_present = row.getStringOrNull("is_tracking_no");
            all_row_count = U.plusTo(not_inspected, short_inspected);
            if(row.containsKey("group"))
            {
                group_order=row.getStringOrNull("group");
                if(group_order.equals("1"))
                {
                    group=1;
                    group_no=row.getStringOrNull("group_order_no");
                    act.groupOrderno=group_no;
                }
            }

            if (row.containsKey("data")) {

                JsonArray list2 = row.getJsonArrayOrNull("data");
                JsonHash row2 = (JsonHash) list2.get(0);
                map = (HashMap) row2;
                Log.e("GetPikingOrder", " " + map);
                map.put("processedCnt", "0");

                // insert data into desire variables and fields for further processing.

                tracking = map.get("mediacode");
                String track1[] = tracking.split(",");
                for (String t:track1) {
                    tracklist.add(t);
                }
                Log.e(TAG,"tracking list  "+tracklist);
                order_id = map.get("order_id");
                String name = map.get("name");
                String barcode = map.get("barcode");
                getbarcode = barcode;
                String lot = "";
                if (BaseActivity.getLotPress() == true) {
                    lot = map.get("lot");
                    getlot = lot;

                }


                if(BaseActivity.getRemarkPress() ==true){
                    if(row2.containsKey("remark_warehouse"))
                    {
                        Log.e("GetPickingggg","remark_warehouse present");
                        remarkPresent = map.get("remark_warehouse");
                        remark= map.get("pikking_method");
                        Log.e("GetPickingggg","remark_warehouse  "+remarkPresent +"  remark is  "+remark);
                    }
                    if(remarkPresent.equals("true"))
                    {   Log.e("GetPickingggg","remark_warehouse  "+remarkPresent );
                        act.getRemarkvoice();
                    }
                }

                String location = map.get("location");
                String code0 = map.get("code");
                String pdt_quantity = map.get("quantity");
                String stndrd_one = map.get("spec1");
                String stndrd_two = map.get("spec2");
                String prod_name = map.get("product_name");
                is_scanned = map.get("is_scanned");

                act.currLineData(map);

                act.updateBadge1(all_order_count);
                act.updateBadge3(not_inspected);

                act.updateBadge2(all_row_count);

                if (act.isBarcodeChange) {
//                    act._sts(R.id.barcode, barcode);
                    act._sts(R.id.quantity, "");


                    act.inputedEvent(barcode, true);

                } else {
                    Log.e("GetPikinggggg", "ELSE____BarCodeChangeeee");
                    act.startTimer();
                    act._sts(R.id.orderName, name);
                    act._sts(R.id.barcode, "");
                    act._sts(R.id.quantity, "");

                    /* Conditional set next process whether barcode or trackingNo or orderNo */

                    switch (BaseActivity.getOrderInfoBy()) {
                        case SettingActivity.ORDER_ID:

                            if (tracking.equals("") ||  BaseActivity.getTrackCheck()) {
                                Log.e("GetPikinggggg", "SWITCH__ORDER_ID");
                                act.setProc(NewShippingGroupActivity.PROC_BARCODE);
                            } else {
                                Log.e("GetPikinggggg", "SWITCH__ORDER_ID_ELSE");
                                act.setmTrackId(tracklist);
                                act.setProc(NewShippingGroupActivity.PROC_TRACKID);
                            }
                            break;

                        case SettingActivity.ORDER_NUMBER:
                            act._sts(R.id.orderId, order_id);
                            if (tracking.equals("") || BaseActivity.getTrackCheck()) {
                                Log.e("GetPikinggggg", "SWITCH__ORDER_NUMBERRRRRRR");
                                act.setProc(NewShippingGroupActivity.PROC_BARCODE);
                            } else {
                                Log.e("GetPikinggggg", "SWITCH__ORDER_NUMBER_ELSE");
                                act.setmTrackId(tracklist);
                                act.setProc(NewShippingGroupActivity.PROC_TRACKID);
                            }
                            break;
                        case SettingActivity.ORDER_TRACKING_NO:
                            Log.e("GetPikinggggg", "SWITCH__ORDER_TRACKING_NO");
                            if (!track_present.equals("")) {
                                track = 1;
                                act.setProc(NewShippingGroupActivity.PROC_BARCODE);
                                act.orderId.setVisibility(View.GONE);
                                act.orderlabel.setVisibility(View.GONE);
                                act.locationlabel.setVisibility(View.GONE);
                                act.location.setVisibility(View.GONE);
                            } else {
                                track = 0;
                                act._sts(R.id.orderId, order_id);
                                act.setProc(NewShippingGroupActivity.PROC_BARCODE);
                            }
                            break;
                    }
                }


            }else {
                Log.e("GetPikinggggg", "No data found!!!!!!!!!!!!");
                String msg = "No data found!";
                valid(code, msg, list, params, activity);
            }
        } else {
            Log.e("GetPikinggggg", "No record found!!!!!!!!!!!!!");
            String msg = "No record found!";
            valid(code, msg, list, params, activity);
        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }
}
