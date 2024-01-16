package com.itechvision.ecrobo.pickman.Util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.R;


public class CommonDialogs {
    public static void customToast(Context c, String msg) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View layout = inflater.inflate(R.layout.viewww,
                (ViewGroup) ((Activity) c).findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);

        Toast toast = new Toast(c);
     // toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    public static void setdialog (String msg,Activity activity ,int layout)
    {

       final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set GUI of login screen

        dialog.setContentView(layout);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        lp.copyFrom(window.getAttributes());

        dialog.setCanceledOnTouchOutside(true);

        // Init button of login GUI
        TextView txt=(TextView)dialog.findViewById(R.id.txt) ;
        txt.setText(msg);
        ImageView close=(ImageView)dialog.findViewById(R.id.icon_close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Log.e("commomndialog",">>>>>>>>>>>>>>");
                    dialog.cancel();
                    Log.e("commomndialog",">>>>>111111111");
                    dialog.dismiss();
            }
        });
        // Make dialog box visible.
        dialog.show();
    }


    public static void dialogAlert(String msg, Context context){
        new android.app.AlertDialog.Builder(context, R.style.DialogThemee)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .show();
    }
}
