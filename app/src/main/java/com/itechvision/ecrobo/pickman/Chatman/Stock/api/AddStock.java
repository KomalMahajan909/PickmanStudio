package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Chatman.Stock.StockAdjustmentActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 3/15/2019.
 */

public class AddStock {
    String TAG= "AddStockkkk";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        StockAdjustmentActivity act = ((StockAdjustmentActivity) activity);
        Log.e(TAG, "add stockkkk");
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);

            String location = row.getStringOrNull("location");
            String qnty = row.getStringOrNull("quantity_changed");

            Log.e(TAG," first character  "+qnty);
            if(U.compareNumeric(qnty,"0")== -1)
            {
                qnty="+"+qnty;
            }
            Log.e(TAG," quantity becomes  "+qnty);


            String product = row.getStringOrNull("code");

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
            txt.setText("ロケ" + location + "の品番" + product + "を" + qnty + "しました。");
            ImageView close=(ImageView)dialog.findViewById(R.id.icon_close);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });
            // Make dialog box visible.
            dialog.show();


            U.beepKakutei(activity, "検品データを登録しました。");
            act.nextProcess();
        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }
}
