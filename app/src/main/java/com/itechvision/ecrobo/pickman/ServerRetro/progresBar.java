package com.itechvision.ecrobo.pickman.ServerRetro;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.itechvision.ecrobo.pickman.R;


public class progresBar {
    Dialog progressDoalog;
    Context context;

    public progresBar(Context context){
        this.context = context;
        progressDoalog = new Dialog(context);
        progressDoalog.setContentView(R.layout.dialog_api);
        progressDoalog.setCanceledOnTouchOutside(false);
        progressDoalog.setCancelable(false);
        progressDoalog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void Show(){
        if(!((Activity) context).isFinishing())
        {
            if(progressDoalog != null){
               progressDoalog.show();
            }
        }

    }

    public void Dismiss(){
        if(!((Activity) context).isFinishing())
        {
          if(progressDoalog != null){
               progressDoalog.dismiss();
         }
         }

    }

    public boolean isShowing(){
            if(progressDoalog.isShowing()){
                return true;
            }
            else return false;
    }

}
