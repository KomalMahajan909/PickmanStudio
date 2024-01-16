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

import com.itechvision.ecrobo.pickman.Chatman.Ship.BoxSizeActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 3/26/2019.
 */

public class CheckMediaBoxNumber {
    private TextToSpeak mTextToSpeak;
    String TAG = "CheckMediaBoxNumber";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        mTextToSpeak = new TextToSpeak(activity);
//        U.beepKakutei(activity, "検品データを登録しました。");
        BoxSizeActivity act = ((BoxSizeActivity) activity);
        Map<String, String> map = new HashMap<String, String>();
        String count="";
        String flag="";
        String itemsCount= "";
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            count= row.getStringOrNull("all_order_count");
            itemsCount = row.getStringOrNull("koguchi");
            if (row.containsKey("shipped_flag"))
            {

                flag=row.getStringOrNull("shipped_flag");
            }
        }
        Log.e("CheckSpecification ","Sepcification>>>>>>>>>>>>>");
        if(flag.equals("1")){
            mTextToSpeak.startSpeaking("Shukka sa reta");
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
            map.put("processedCnt", "0");

            act.startTimer();
            act._sts(R.id.quantity,itemsCount);
            act.updateBadge1(count);
            act.setProc(BoxSizeActivity.PROC_BOXNO);

            // if tracking no. already updated then give dialog box
        }
    }


    public void valid(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((BoxSizeActivity) activity).setProc(BoxSizeActivity.PROC_TRACKID);
    }

}
