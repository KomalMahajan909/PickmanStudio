package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.MoveStockActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 1/10/2019.
 */

public class MoveStock2 {
    public static Dialog dialog1;
    public static boolean dialogdismissed1=false;
    String TAG = "MoveStock";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, final Activity activity) {
//		U.beepNext();
        MoveStockActivity.count =0;
        U.beepKakutei(activity, "設定を登録しました");
        ((BaseActivity) activity).toast("在庫を移動しました。");
        ((BaseActivity) activity).nextProcess();
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            String source=row.getStringOrNull("source");
            String destination = row.getStringOrNull("destination");
            String qnty=row.getStringOrNull("qty");
            String product =row.getStringOrNull("code");
            dialogdismissed1=true;


                dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

                // Set GUI of login screen
                dialog1.setContentView(R.layout.status_submit);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog1.getWindow();
                lp.copyFrom(window.getAttributes());


                dialog1.setCanceledOnTouchOutside(false);

                // Init button of login GUI
                TextView msg = (TextView) dialog1.findViewById(R.id.txt);
                msg.setText("品番" + product + "をロケ" + source + "から" + destination + "に" + qnty + "個移動しました。");
//			    品番0787-02222をロケAB1-03-03-04-03からsinghに4個移動しました。
                Button ok = (Button) dialog1.findViewById(R.id.btn_ok_dialog);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        U.beepNext();
                        dialog1.dismiss();
                        dialogdismissed1 = false;
                    }
                });
                // Make dialog box visible.
                dialog1.show();
            }

    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((MoveStockActivity) activity).setProc(MoveStockActivity.PROC_DESTINATION);
    }

}
