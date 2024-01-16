package com.itechvision.ecrobo.pickman.Models.DaimaruShippingSpecification;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Chatman.WWW6Server.DimaruShipping.DaimaruShippingSpecificationActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 12/27/2018.
 */

public class DaimaruCheckSpecification {

    String TAG = "CheckSpecification";


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

//        U.beepKakutei(activity, "検品データを登録しました。");
        DaimaruShippingSpecificationActivity act = ((DaimaruShippingSpecificationActivity) activity);
        Map<String, String> map = new HashMap<String, String>();
        String count="";
        String flag="";
        String itemsCount= "";
        String box_size="0";
        String shipping_company= "";
        String track_no = "";


        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            count = row.getStringOrNull("all_order_count");
            itemsCount = row.getStringOrNull("koguchi");
            if (row.containsKey("shipping_method")) {

                shipping_company = row.getStringOrNull("shipping_method");
                track_no = row.getStringOrNull("tracking_no");
            }
            if (row.containsKey("shipped_flag"))
            {
                flag=row.getStringOrNull("shipped_flag");
            }


        }
        Log.e("CheckSpecification ","Sepcification>>>>>>>>>>>>>");
        if(flag.equals("1")){
//            act.mTextToSpeak.startSpeaking("Shukka sa reta");
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
            txt.setText("出荷済みです");
            ImageView close=(ImageView)dialog.findViewById(R.id.icon_close);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });
            // Make dialog box visible.
            dialog.show();
        }
        else {

            map.put("mediacode",act._gts(R.id.trackingNumber));
            map.put("quantity",itemsCount);
            Log.e(TAG, ">>>>>>>koguchiiii   "+itemsCount);
            map.put("shipping_company",shipping_company);
            map.put("processedCnt", "0");

            act.startTimer();
            act.setProductList(map);
            ArrayList<String> list1 = new ArrayList<>();

            if(!shipping_company.equals("0")){

               String track[] = track_no.split(",");
                Log.e(TAG, ">>>>>>>>>>>>>>>"+track[0]);
               for(int i =0; i< track.length ; i++){
                   list1.add(track[i]);
                   Log.e(TAG, ">>>>>>>>>>>>>>>111111"+list1);
               }

            }

            act.setTrack(list1);
            act.updateBadge3(count);
            act.nextWork();
        }
    }


    public void valid(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((DaimaruShippingSpecificationActivity) activity).setProc(DaimaruShippingSpecificationActivity.PROC_TRACKID);
    }

}
