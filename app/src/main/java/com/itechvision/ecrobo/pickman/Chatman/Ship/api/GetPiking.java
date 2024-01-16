package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewPickingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.id;

import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2/6/2019.
 */

public class GetPiking {
    public static String getbarcode= null;
    public static String getlot= null;

    public static int track=0;
    public static int group=0;

    public static boolean permission = true;
    public  static String remark;

    String TAG = "GetPiking";
    Context ctx ;
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        //List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        ArrayList<String> tracklist = new ArrayList<>();
        final NewPickingActivity act = (NewPickingActivity) activity;
        String all_order_count = "0";
        String all_row_count = "0";
        String tracking = null;
        String order_id = null;
        String order_picking = "0";

        String group_order= null;
        String group_no =null;

        String remarkPresent= "false";
        String user="";


        if (list.size() > 0){
            remark= "";
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            all_order_count = row.getStringOrNull("all_order_count");

            String not_inspected = row.getStringOrNull("not_inspection_row_count");
            String short_inspected = row.getStringOrNull("shortage_row_count");

            all_row_count  = U.plusTo(not_inspected, short_inspected);
            user= row.getStringOrNull("user");

            if(row.containsKey("order_picking"))
                order_picking = row.getStringOrNull("order_picking");

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

            if (row.containsKey("data"))
            {

                JsonArray list2 = row.getJsonArrayOrNull("data");
                JsonHash row2 = (JsonHash) list2.get(0);
                map = (HashMap) row2;
                Log.e(TAG, " " + map);
                map.put("processedCnt", "0");

                // insert data into desire variables and fields for further processing.

                tracking = map.get("mediacode");
                String track[] = tracking.split(",");
                for (String t:track) {
                    tracklist.add(t);
                }
                Log.e(TAG,"tracking list  "+tracklist);
                order_id = map.get("order_id");
                String name = map.get("name");
                String barcode = map.get("barcode");
                getbarcode = barcode.trim();

                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("BoxID", NewPickingActivity.mBoxNo+"");
                map1.put("location", row2.getStringOrNull("location"));
                map1.put("order_id",row2.getStringOrNull("order_id"));
                map1.put("mediacode",row2.getStringOrNull("mediacode"));
                map1.put("quantity", row2.getStringOrNull("quantity"));
                map1.put("barcode", row2.getStringOrNull("barcode"));
                map1.put("code", row2.getStringOrNull("code"));
                map1.put("orderSubId", row2.getStringOrNull("order_sub_id"));
                map1.put("multi_barcodes",row2.getStringOrNull("multi_barcodes"));
                map1.put("product_stock_history_id",row2.getStringOrNull("product_stock_history_id"));
                map1.put("processedCnt", "0");
                data.add(map1);


                if (row2.containsKey("serial_no_flg")) {
                    String serialpresent = map.get("serial_no_flg");
                    NewPickingActivity.serialPresent = serialpresent;
                    if(serialpresent.equals("1"))
                        act.setLayout();
                }

                String lot="";
                if(BaseActivity.getLotPress() == true) {
                    lot = map.get("lot");
                    getlot=lot;
                }
                // if Clicked Remarks on Setting Page
                if(BaseActivity.getRemarkPress() ==true){
                    if(row2.containsKey("remark_warehouse"))
                    {
                        Log.e(TAG,"remark_warehouse present");
                        remarkPresent = map.get("remark_warehouse");
                        if(row2.containsKey("remark_warehouse"))
                        remark= map.get("pikking_method");
                        Log.e(TAG,"remark_warehouse  "+remarkPresent +"  remark is  "+remark);
                    }
                    if(remarkPresent.equals("true"))
                    {   Log.e(TAG,"remark_warehouse  "+remarkPresent );
                        act.getRemarkvoice();
                    }
                }
                String location = map.get("location");
                String code0 = map.get("code");
                String pdt_quantity = map.get("quantity");
                String stndrd_one = map.get("spec1");
                String stndrd_two = map.get("spec2");
                String prod_name = map.get("product_name");
                String is_scanned = map.get("is_scanned");


                if(row2.containsKey("zone_alert"))
                {
                    Log.e(TAG,"zone alert tag present  ");
                    String zone_alert = row2.getStringOrNull("zone_alert");
                    if(zone_alert.equals("1")){
                        Log.e(TAG,"zone alert tag value  " +zone_alert);

                        // Create Object of Dialog class
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        // Set GUI of login screen
                        dialog.setContentView(R.layout.new_picking_dialog);
                        dialog.getWindow().setBackgroundDrawable(
                                new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        Window window = dialog.getWindow();
                        lp.copyFrom(window.getAttributes());


                        dialog.setCanceledOnTouchOutside(false);

                        // Init button of login GUI
                        TextView txt=(TextView)dialog.findViewById(id.txt) ;
                        txt.setText("ゾーンアラートです");
                        ImageView close=(ImageView)dialog.findViewById(id.icon_close);


                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        // Make dialog box visible.
                        dialog.show();
                    }
                }

                if (Integer.parseInt(is_scanned) == 1 && !user.equalsIgnoreCase("")) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    builder1.setMessage("ユーザー " +user +
                            " が作業中です。");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "この行をやる",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    permission = true;
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "別を選択する",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    permission = false;
                                    dialog.cancel();
                                    act.setProc(NewPickingActivity.PROC_LINE_NO);
//									act.dialog_location();

                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }


                //act.speakProducts(all_row_count);
                if (barcode.length() >= 4)
                    act._sts(id.shortBarcode, barcode.substring(barcode.length() - 4));
                else
                    act._sts(id.shortBarcode, barcode);

                if (act.isBarcodeChange) {
                    Log.e(TAG, "BarCodeChangeeee");
                    act._sts(id.barcode, barcode.trim());
                    act._sts(id.quantity, "");
                    act._sts(id.serialno, "");
                    act._sts(id.plateno, "");
                    act._sts(id.location, location);
                    act._sts(id.productCode, code0);
                    act._sts(id.productQuantity, pdt_quantity);
                    act._sts(id.standard1, stndrd_one);
                    act._sts(id.standard2, stndrd_two);
                    act._stxtv(id.productname, prod_name);
                    act.productName.setSelected(true);
                    act.repeatLocation(2000);
                    act.inputedEvent(barcode, true);
                    act.serialList.clear();

//					mTextToSpeak.startSpeaking("数量",pdt_quantity);
                } else {
                    Log.e(TAG, "ELSE____BarCodeChangeeee");
                    act.startTimer();
                    act._sts(id.orderName, name);
                    act._sts(id.barcode, "");
                    act._sts(id.quantity, "");
                    act._sts(id.serialno, "");
                    act._sts(id.plateno, "");
                    act._sts(id.location, location);
                    act._sts(id.standard1, stndrd_one);
                    act._sts(id.standard2, stndrd_two);
                    act._sts(id.productCode, code0);
                    act._stxtv(id.productname, prod_name);
                    act.productName.setSelected(true);
                    act._sts(id.productQuantity, pdt_quantity);
                    act.serialList.clear();
                    /* Conditional set next process whether barcode or trackingNo or orderNo */
                    switch (BaseActivity.getOrderInfoBy()) {
                        case SettingActivity.ORDER_ID:

                            if (tracking.equals("") || BaseActivity.getTrackCheck()) {
                                Log.e(TAG, "SWITCH__ORDER_ID");
                                act.repeatLocation(2000);
                                act.setProc(NewPickingActivity.PROC_BARCODE);
                            } else {
                                Log.e(TAG,"SWITCH__ORDER_ID_ELSE");
                                act.setmTrackId(tracklist);
                                act.setProc(NewPickingActivity.PROC_TRACKID);
                            }
                            break;

                        case SettingActivity.ORDER_NUMBER:
                            act._sts(id.orderId, order_id);
                            if (tracking.equals("") || BaseActivity.getTrackCheck()) {
                                Log.e(TAG, "SWITCH__ORDER_NUMBERRRRRRR");
                                act.repeatLocation(2000);
                                act.setProc(NewPickingActivity.PROC_BARCODE);
                            } else {
                                Log.e(TAG, "SWITCH__ORDER_NUMBER_ELSE");
                                act.setmTrackId(tracklist);
                                act.setProc(NewPickingActivity.PROC_TRACKID);
                            }
                            break;
                        case SettingActivity.ORDER_TRACKING_NO:
                            Log.e(TAG, "SWITCH__ORDER_TRACKING_NO");
                            act.setProc(NewPickingActivity.PROC_BARCODE);
                            act._sts(id.trackingNumber, tracking);
                            act._sts(id.orderId, order_id);
                            act.repeatLocation(2000);

                            break;
                    }
                }

                Log.e(TAG,"setPRoductttttt    ");
                act.updateBadge1(all_order_count);
                act.updateBadge3(not_inspected);
                act.updateBadge2(short_inspected);
                Log.e(TAG,"setPRoductttttt1111111111111    "+map.get("no"));
                act.updateLineNo(map.get("no"));
                Log.e(TAG,"setPRoductttttt1111122222222222   ");
                act.currLineData(map);
                act.setProductList(data);
            }

            else{
                Log.e(TAG,"No data found!!!!!!!!!!!!");
                String msg  = "No data found!";
                valid(code, msg, list, params, activity);
            }
        } else {
            Log.e(TAG,"No record found!!!!!!!!!!!!!");
            String msg  = "No record found!";
            valid(code, msg, list, params, activity);
        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        NewPickingActivity act = (NewPickingActivity) activity;

        switch (BaseActivity.getOrderInfoBy()) {
            case SettingActivity.ORDER_ID:
                Log.e(TAG, "nextProcessssssssss_ORDER_ID");
                act.setProc(NewPickingActivity.PROC_ORDERID);
                break;
            case SettingActivity.ORDER_NUMBER:
                Log.e(TAG, "nextProcessssssssss_ORDER_NUMBER");
                act._sts(id.orderNo, "");
                act.setProc(NewPickingActivity.PROC_ORDER_NO);
                break;
            case SettingActivity.ORDER_TRACKING_NO:
                Log.e(TAG, "nextProcessssssssss_ORDER_TRACKING_NO");
                act.setProc(NewPickingActivity.PROC_TRACKID);
                break;
        }
    }
}
