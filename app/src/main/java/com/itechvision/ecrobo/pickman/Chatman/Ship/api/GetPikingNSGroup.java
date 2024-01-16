package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.Chatman.Ship.NewShippingGroupActivity;
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
 * Created by lenovo on 10/26/2019.
 */

public class GetPikingNSGroup {
    Map<String, String> map = new HashMap<String, String>();
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    String all_row_count = "0";
    String TAG = "GetPikingNSGroup";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

//        NewShippingGroupActivity.count=0;
        final NewShippingGroupActivity act = (NewShippingGroupActivity) activity;
        String all_order_count = "0";
        String user="";
        String zone_alert= "";
        if (list.size() > 0){
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            final String short_inspected = row.getStringOrNull("shortage_row_count");
            all_order_count = row.getStringOrNull("all_order_count");
            if(row.containsKey("user")){
                user = row.getStringOrNull("user");
            }
            String not_inspected = row.getStringOrNull("not_inspection_row_count");

            all_row_count = U.plusTo(not_inspected, short_inspected);
            act.updateBadge1(all_order_count);
            act.updateBadge3(not_inspected);

            if (Integer.parseInt(all_row_count)==0)
            {
                Toast.makeText(act,"未検品はありません。nextboxを押してください",Toast.LENGTH_SHORT).show();
            }
            if (row.containsKey("data")) {
                JsonArray list2 = row.getJsonArrayOrNull("data");
                JsonHash row2 = (JsonHash) list2.get(0);
                map = (HashMap) row2;
                map.put("processedCnt", "0");
                if (row2.containsKey("serial_no_flg")) {
                    String serialpresent = map.get("serial_no_flg");
                    NewShippingGroupActivity.serialPresent = serialpresent;
                }
                // insert data into desire variables and fields for further processing.
                final String tracking = map.get("mediacode");
                final String order_id = map.get("order_id");
                final String name = map.get("name");
                String barcode = map.get("barcode");
                final String location = map.get("location");
                final String code0 = map.get("code");
                String is_scanned = map.get("is_scanned");
                final String pdt_quantity = map.get("quantity");
                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("BoxID",NewShippingGroupActivity.mBoxNo+"");
                map1.put("location", row2.getStringOrNull("location"));
                map1.put("order_id",row2.getStringOrNull("order_id"));
                map1.put("quantity", row2.getStringOrNull("quantity"));
                map1.put("barcode", row2.getStringOrNull("barcode"));
                map1.put("code", row2.getStringOrNull("code"));
                map1.put("orderSubId", row2.getStringOrNull("order_sub_id"));
                map1.put("processedCnt", "0");
                data.add(map1);
                if(row2.containsKey("zone_alert"))
                {
                    zone_alert = row2.getStringOrNull("zone_alert");}


                if (Integer.parseInt(is_scanned) == 1 && !user.equals("")) {
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
                    TextView txt=(TextView)dialog.findViewById(R.id.txt) ;
                    txt.setText("ゾーンアラートです");
                    ImageView close=(ImageView)dialog.findViewById(R.id.icon_close);


                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            act.setProc(NewShippingGroupActivity.PROC_BARCODE);
                            dialog.dismiss();
                        }
                    });
                    // Make dialog box visible.
                    dialog.show();
                }
                else {
                    act._sts(R.id.orderName, name);
                    act._sts(R.id.orderId, order_id);
//										act._sts(R.id.trackingNumber, tracking);
				/*if (barcode.length() >= 4)
					act._sts(id.shortBarcode, barcode.substring(barcode.length() - 4));
				else
					act._sts(id.shortBarcode, barcode);*/
                    act._sts(R.id.location, location);
                    act._sts(R.id.quantity, "");
                    act._sts(R.id.serialno,"");
                    act._sts(R.id.productCode, code0);
                    act._sts(R.id.productQuantity, pdt_quantity);
                    act.setProductList(data);
                    act.currLineData(map);
                    act.updateBadge2(all_row_count);

                    if(zone_alert.equals("1")){
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
                        TextView txt=(TextView)dialog.findViewById(R.id.txt) ;
                        txt.setText("出荷単位に注意！");
                        ImageView close=(ImageView)dialog.findViewById(R.id.icon_close);


                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(NewShippingGroupActivity.serialPresent.equals("1"))
                                    act.setProc(NewShippingGroupActivity.PROC_SERIAL_NO);
                                else
                                    act.nextWork();

                                dialog.dismiss();
                            }
                        });
                        // Make dialog box visible.
                        dialog.show();
                    }
                    else {
                        if(NewShippingGroupActivity.serialPresent.equals("1"))
                            act.setProc(NewShippingGroupActivity.PROC_SERIAL_NO);
                        else
                            act.nextWork();

                    }

                    act.mTarget = null;

                }


            } else {
                String msg  = "No data found!";
                valid(code, msg, list, params, activity);
                act._sts(id.location, "");
                act._sts(id.quantity, "");
                act._sts(id.serialno,"");
                act._sts(id.productCode, "");
                act._sts(id.productQuantity, "");
            }
        } else {
            String msg  = "No record found!";
            valid(code, msg, list, params, activity);
        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        NewShippingGroupActivity act = (NewShippingGroupActivity) activity;
        act.errorDialog = true;
        act.showErrorPopup();
        U.beepError(activity, message);
    }

}
