package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.AllocationActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.DirectarrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 12/4/2018.
 */

public class MoveStock {
    public static Dialog dialog;
    public static boolean dialogdismissed=false;

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, final Activity activity) {

        String destination = "";
        String qnty = "";
        String product ="";
        U.beepKakutei(activity, "設定を登録しました");


        ((BaseActivity) activity).nextProcess();
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);

            destination = row.getStringOrNull("destination");
            qnty = row.getStringOrNull("qty");
            product = row.getStringOrNull("code");


        }
        if(dialog != null){
            dialog.dismiss();
        }


        dialogdismissed = true;
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set GUI of login screen
        dialog.setContentView(R.layout.status_submit);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());

        dialog.setCanceledOnTouchOutside(true);

        // Init button of login GUI
        TextView msg = (TextView) dialog.findViewById(R.id.txt);
        msg.setText("品番" + product + "をロケ" + destination + "に" +qnty+"個棚入れしました");

        Button ok = (Button) dialog.findViewById(R.id.btn_ok_dialog);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                U.beepNext();

                dialog.dismiss();
                dialogdismissed=false;
            }
        });
        // Make dialog box visible.
        dialog.show();
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((AllocationActivity) activity).setProc(AllocationActivity.PROC_LOCATION);
    }


}