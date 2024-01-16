package com.itechvision.ecrobo.pickman.ServerRetro;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;


public class CommonUtilities {

    private static Context ctx;

    public static boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
             if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }

    public static void openInternetDialog(Context c) {
        ctx = c;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder.setTitle("Internet Alert!");
        alertDialogBuilder
        .setMessage("You are not connected to Internet..Please Enable Internet!")
        .setCancelable(false)
        .setPositiveButton("Yes",
        new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName(
           "com.android.settings",
            "com.android.settings.wifi.WifiSettings");
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
                dialog.cancel();

               }
               });

        AlertDialog alertDialog = alertDialogBuilder.create();
        if(!((Activity) c).isFinishing()) {
            alertDialog.show();
        }
    }


    public static boolean CheckGPS(Context c){
        LocationManager service = (LocationManager) c.getSystemService(c.LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
             return true;
        }else{
            return false;
        }
    }

    public static void openGPSDialog(final Context c) {
        ctx = c;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder.setTitle("GPS Enable Alert!");
        alertDialogBuilder
                .setMessage("Your GPS seems to be disabled,please enable it")
                .setCancelable(false)
                .setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                c.startActivity(intent);
                                dialog.cancel();
                            }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        if(!((Activity) c).isFinishing()) {
            alertDialog.show();
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService( Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
            activity.getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            Log.e("tag",e.toString());
        }

    }

}
