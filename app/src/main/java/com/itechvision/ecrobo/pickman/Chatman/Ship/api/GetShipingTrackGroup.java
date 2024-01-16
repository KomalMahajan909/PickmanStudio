package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.Chatman.Ship.NewShippingGroupActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itechvision.ecrobo.pickman.Chatman.Ship.NewShippingGroupActivity.order;
import static com.itechvision.ecrobo.pickman.Chatman.Ship.NewShippingGroupActivity.orderSubid;

/**
 * Created by lenovo on 10/26/2019.
 */

public class GetShipingTrackGroup {
    String TAG = "GetShipingTrack";
    int scaned=0;
    String order_id = null;
    String order_SubId= null;
    String name=null;
    String all_row_count = "0";
    Map<String, String> map = new HashMap<String, String>();
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        final NewShippingGroupActivity act = (NewShippingGroupActivity) activity;
        act.isNextBarcode ="";
        act.mNextBarcode =false;
        String all_order_count = "0";
        String zone_alert ="";
        String user="";
        boolean match=false;
        if (list.size() > 0){
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            String short_inspected = row.getStringOrNull("shortage_row_count");
            all_order_count = row.getStringOrNull("all_order_count");

            String not_inspected = row.getStringOrNull("not_inspection_row_count");
            if(row.containsKey("user")){
                user = row.getStringOrNull("user");
            }
            all_row_count = U.plusTo(not_inspected, short_inspected);
            act.updateBadge1(all_order_count);
            act.updateBadge3(not_inspected);
            act.updateBadge2(all_row_count);
            act.setProc(NewShippingGroupActivity.PROC_BARCODE);
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

                order_id = map.get("order_id");

                order_SubId = map.get("order_sub_id");
                name = map.get("name");
                final String barcode = map.get("barcode");
                String is_scanned = map.get("is_scanned");
                final String code0 = map.get("code");
                final String pdt_quantity = map.get("quantity");

                Log.e("GetShipingTrack ","order_sub_id becomes "+order_SubId  +"orderid is "+ order);


                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("BoxID",NewShippingGroupActivity.mBoxNo+"");
                map1.put("quantity", row2.getStringOrNull("quantity"));
                map1.put("barcode", row2.getStringOrNull("barcode"));
                map1.put("code", row2.getStringOrNull("code"));
                map1.put("processedCnt", "0");

                data.add(map1);

                if(row2.containsKey("zone_alert"))
                {
                    zone_alert = row2.getStringOrNull("zone_alert");
                }

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
                            scaned=2;
                            act.setProc(NewShippingGroupActivity.PROC_BARCODE);
                            dialog.dismiss();
                        }
                    });
                    // Make dialog box visible.
                    dialog.show();
                }

                else {
                    String[] parts=order_SubId.split(",");
                    String[] orders =order_id.split(",");


                    for(int i =0; i<parts.length; i++){
                        if(orderSubid.size()>0){
                            boolean match1=false;
                            for (String map3 : orderSubid) {

                                if (parts[i].equals(map3)) {
                                    match1 = true;
                                    break;
                                }
                            }
                            if (match1==false){
                                orderSubid.add(parts[i]);}

                            Log.e("GetShipingTrack ","order List becomes "+ orderSubid);
                        }  else {
                            orderSubid.add(parts[i]);
                            Log.e("GetShipingTrack ","order List becomes111 "+ orderSubid);}


                    }



                    for(int i =0; i<orders.length; i++){

                        boolean match1=false;
                        if(order.size()>0){
                            for (String map3 : order) {

                                if (orders[i].equals(map3)) {
                                    match = true;
                                    break;
                                }
                            }
                            if (match==false){
                                order.add(orders[i]);}

                            Log.e("GetShipingTrack ","order List becomes "+ order);
                        }
                        else {
                            order.add(orders[i]);
                            Log.e("GetShipingTrack ","order List becomes111 "+ order);}

                    }



                    act._sts(R.id.orderName, name);


                    act._sts(R.id.quantity, "");
                    act._sts(R.id.productCode, code0);
                    act._sts(R.id.productQuantity, pdt_quantity);
                    act.setProductList(data);
                    act.currLineData(map);
//				act.updateLineNo(map.get("no"));
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
                        txt.setText("Zone alert");
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
                    else
                    {

                        if(NewShippingGroupActivity.serialPresent.equals("1"))
                            act.setProc(NewShippingGroupActivity.PROC_SERIAL_NO);
                        else
                            act.nextWork();
                    }

                    act.mTarget=null;

                }} else {
                String msg  = "No data found!";

                valid(code, msg, list, params, activity);
            }
        } else {
            String msg  = "No record found!";

            valid(code, msg, list, params, activity);
        }}


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        NewShippingGroupActivity act = (NewShippingGroupActivity) activity;
        act.errorDialog = true;
        act.showErrorPopup();
        U.beepError(activity, message);
    }

}
