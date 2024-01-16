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
import com.itechvision.ecrobo.pickman.Chatman.Stock.MoveLocationActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 1/15/2019.
 */

public class MoveLocation {
    public static Dialog dialog;
    public static boolean dialogdismissed=false;
    String TAG = "MoveLocation";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, final Activity activity) {
//		U.beepNext();
        U.beepKakutei(activity, "設定を登録しました");
        ((BaseActivity) activity).toast("ロケーションを移動しました。");
        ((BaseActivity) activity).nextProcess();
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            String source = row.getStringOrNull("source");
            String destination = row.getStringOrNull("destination");
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogdismissed=true;
            // Set GUI of login screen
            dialog.setContentView(R.layout.status_submit);
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = dialog.getWindow();
            lp.copyFrom(window.getAttributes());


            dialog.setCanceledOnTouchOutside(false);

            // Init button of login GUI
            TextView msg=(TextView)dialog.findViewById(R.id.txt);
            msg.setText("ロケ"+source+"の在庫を"+destination+"へ全て移動しました。");
//			ロケsinghの在庫をb3388bb7へ全て移動しました。
            Button ok = (Button) dialog.findViewById(R.id.btn_ok_dialog);


            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    U.beepNext();
                    dialogdismissed=false;
                    dialog.dismiss();
                }
            });
            // Make dialog box visible.
            dialog.show();


            ((BaseActivity) activity).toast("ロケーションを移動しました。");
            ((BaseActivity) activity).nextProcess();
        }
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        MoveLocationActivity act = ((MoveLocationActivity) activity);
        U.beepError(activity, message);
        act.setProc(MoveLocationActivity.PROC_DESTINATION);
    }

}
