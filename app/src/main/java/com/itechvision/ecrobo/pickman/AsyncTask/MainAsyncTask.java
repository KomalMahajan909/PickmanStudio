package com.itechvision.ecrobo.pickman.AsyncTask;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainAsyncTask extends AsyncTask<String, Void, String> {

    String TAG =MainAsyncTask.class.getSimpleName();
    MainAsynListener<String> listener;
    int receivedId;
    boolean  isSuccess = false,listpresent = false;
    Context context;
    public CommonFunctions sSetconnection;
    String url, tag ,listTag;
    List<ParamsGetter> getterList;
    Boolean isProgress;
    public static SweetAlertDialog dialogBox;
    public static boolean dialogshow = false;
    ECRApplication app=new ECRApplication();
    String adminID="";
    boolean dataAddition = false,seprateRequest =  false;
    List<String> list,tagList;

    public MainAsyncTask(Context context, String url, int receivedId,
                         MainAsynListener<String> listener, String tag, List<ParamsGetter> getterList) {

        this.context = context;
        this.url = url;
        this.receivedId = receivedId;
        this.listener = listener;
        this.tag=tag;
        this.getterList=getterList;
        this.isProgress=true;
    }

    public MainAsyncTask(Context context, String url,boolean seprate, int receivedId,
                         MainAsynListener<String> listener, String tag, List<ParamsGetter> getterList,boolean addition)
    {

        this.context = context;
        this.url = url;
        this.receivedId = receivedId;
        this.listener = listener;
        this.tag=tag;
        this.getterList=getterList;
        this.isProgress=true;
        this.dataAddition = addition;
        if (dataAddition)
            addEssentials();
        this.seprateRequest =seprate;

    }
    public MainAsyncTask(Context context, String url, int receivedId,
                         MainAsynListener<String> listener, String tag, List<ParamsGetter> getterList,boolean addition) {

        this.context = context;
        this.url = url;
        this.receivedId = receivedId;
        this.listener = listener;
        this.tag=tag;
        this.getterList=getterList;
        this.isProgress=true;
        this.dataAddition = addition;
        if (dataAddition)
            addEssentials();

    }


    public MainAsyncTask(Context context, String url, int receivedId,
                         MainAsynListener<String> listener, String tag, List<ParamsGetter> getterList,List<String> taglist,String listTag,boolean listpresent,boolean isProgress,boolean addition) {
        Log.e("MAinAsyncTask  ", "data received" + url);
        this.context = context;
        this.url = url;
        this.receivedId = receivedId;
        this.listener = listener;
        this.tag=tag;
        this.getterList=getterList;
        this.list = list;
        this.isProgress=isProgress;
        this.listpresent =listpresent;
        this.listTag = listTag;
        this.dataAddition = addition;
        if (dataAddition)
            addEssentials();

    }
    public void addEssentials(){
        Log.e(TAG,"shopidddddd  "+ BaseActivity.getShopId());
        Log.e(TAG,"Admin id>>>>>>>>>>>>>>"+app.getAdminId());
        Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
        Globals.getterList.add(new ParamsGetter("shop_id",BaseActivity.getShopId()));
        Globals.getterList.add(new ParamsGetter("app_version", context.getResources().getString(R.string.version)));

    }

    @Override
    public void onPreExecute() {
        sSetconnection = new CommonFunctions();
        Log.e(TAG,"Progresssssssssss      "+isProgress);
        if(isProgress) {

            try {
                if(dialogBox!=null){

                    if (dialogBox.isShowing()) {
                        Log.e(TAG, "dialogboxxxx showing");
                       dialogBox.dismiss();

                    }
                }
                dialogshow =true;
                Log.e(TAG, "dialogboxxxx showwwwwwwwwww");
                dialogBox = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                dialogBox.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                dialogBox.setTitleText("Loading");
                dialogBox.setCancelable(false);
                dialogBox.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String doInBackground(String... arg0) {
        String mResult = null;

//		JSONObject json = new JSONObject();


        try {
            if(CommonUtilities.getConnectivityStatus(context)) {
                if (seprateRequest)
                    mResult = CommonFunctions.getOkHttp(url, receivedId, tag, getterList);
                else {
                    if (listpresent == true)
                        mResult = CommonFunctions.getOkHttpClient(url, receivedId, tag, getterList, list, listTag);
                    else
                        mResult = CommonFunctions.getOkHttpClient(url, receivedId, tag, getterList);
                }


                if (mResult != null) {
                    isSuccess = true;
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            Toast.makeText(context, "Please Try Again..", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mResult;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onPostExecute(String _result) {
        try {

            if (isSuccess) {
                if(_result==null){
                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
					         Toast.makeText(context, "Please Try Again..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                Log.e("result><><><>",""+_result);
                listener.onPostSuccess(_result, receivedId, isSuccess);
            } else {
                listener.onPostError(receivedId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(isProgress) {

            try {
                Log.e(TAG, "dialogboxxxx dismissssssssssssssssss");
                dialogBox.dismiss();
                dialogshow = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
         super.onCancelled();
     }

}
