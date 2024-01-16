package com.itechvision.ecrobo.pickman.Util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;


import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by netset on 30/9/16.
 */

public class UtilsMethods {

 // static ActionBar actionBar;
    public static TextView txtRight;
    public static RelativeLayout relLeft, relRight;
    public static SimpleDateFormat sdf;
    public static DatePickerDialog datePicker;
    public static TimePickerDialog timePicker;
    static ImageView imgLeft, imgRight;
    public static Toolbar toolbar;
    public static int CHECK_CONNECTION;

    public static String getCurrentDate(String format) {

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat(format);
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getCurrentDatefromMilli(String milli, String format) {

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat(format);
        String formattedDate = df.format(new Date(Long.parseLong(milli)));
        return formattedDate;
    }

    public static boolean CheckPermissions(Context c, String permission, int requestcode) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(c,
                permission)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e("checkSelfPermission", "PERMISSION_GRANTED");

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(((Activity) c),
                    permission)) {
                Log.e("checkSelfPermission", "shouldShowRequestPermissionRationale");
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                if (c instanceof AppCompatActivity) {
                    Log.e("AppCompatActivity", "true");

                    ActivityCompat.requestPermissions(((AppCompatActivity) c),
                    new String[]{permission},
                    requestcode);
                } else if (c instanceof FragmentActivity) {
                    Log.e("FragmentActivity", "true");
                    ActivityCompat.requestPermissions(((FragmentActivity) c),
                            new String[]{permission},
                            requestcode);
                }

            } else {

                // No explanation needed, we can request the permission.
                if (c instanceof AppCompatActivity)
                    ActivityCompat.requestPermissions(((AppCompatActivity) c),
                            new String[]{permission},
                            requestcode);
                else if (c instanceof FragmentActivity)
                    ActivityCompat.requestPermissions(((FragmentActivity) c),
                            new String[]{permission},
                            requestcode);

                Log.e("checkSelfPermission", "no shouldShowRequestPermissionRationale");
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
            }
            return false;
        } else {
            return true;
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void ShakeEditText(final View view) {
        view.requestFocus(View.FOCUS_UP);
        YoYo.with(Techniques.Shake).playOn(view);
    }

    public static void ViewAnimate(final View view) {
        view.requestFocus(View.FOCUS_UP);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(view);
    }


  /*  public static void CheckMsg(Context context, JSONObject jsonObject){
        try {
            if(jsonObject.getString("message").equalsIgnoreCase(context.getString(R.string.Wrongtoken))){
                SharedPrefrences.set_loginbool(context,false);
                UtilsMethods.ShowToast(context, context.getString(R.string.alreadylogin));
                context.startActivity(new Intent(context, Splash.class));
                ((AppCompatActivity)context).finish();
            }else {
                UtilsMethods.ShowToast(context,jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void CheckMsg(Context context, JSONObject jsonObject,boolean isToast){
        try {
            if(jsonObject.getString("message").equalsIgnoreCase(context.getString(R.string.Wrongtoken))){
                SharedPrefrences.set_loginbool(context,false);
                UtilsMethods.ShowToast(context, context.getString(R.string.alreadylogin));
                context.startActivity(new Intent(context, SplashActivity.class));
                ((AppCompatActivity)context).finish();
            }else {
                if(isToast)
                UtilsMethods.ShowToast(context,jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static boolean compareDates(String d1, String d2) {
        try {
            // If you already have date objects then skip 1

            //1
            // Create 2 dates starts
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);

            System.out.println("Date1" + sdf.format(date1));
            System.out.println("Date2" + sdf.format(date2));
            System.out.println();

            // Create 2 dates ends
            //1

            // Date object is having 3 methods namely after,before and equals for comparing
            // after() will return true if and only if date1 is after date 2
            if (date1.after(date2)) {
                System.out.println("Date1 is after Date2");
                return false;
            }
            // before() will return true if and only if date1 is before date2
            if (date1.before(date2)) {
                System.out.println("Date1 is before Date2");
                return true;
            }

            //equals() returns true if both the dates are equal
            if (date1.equals(date2)) {
                System.out.println("Date1 is equal Date2");
                return false;
            }

            System.out.println();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    /*public static BitmapDescriptor getBitmapDescriptor(Context context,int id, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawable vectorDrawable = (VectorDrawable)context. getResources().getDrawable(id);
            vectorDrawable.setTint(context.getResources().getColor(color));
            int h = vectorDrawable.getIntrinsicHeight();
            int w = vectorDrawable.getIntrinsicWidth();

            vectorDrawable.setBounds(0, 0, w, h);

            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bm);
            vectorDrawable.draw(canvas);


            return BitmapDescriptorFactory.fromBitmap(bm);

        } else {
            return BitmapDescriptorFactory.fromResource(id);
        }
    }*/

    public static File takeScreenshot(Activity context) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        File imageFile = null;
        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            toolbar.setDrawingCacheEnabled(false);
            View v1 = context.getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

//            imageFile = new File (Environment
//                    .getExternalStorageDirectory().getAbsolutePath()+"/SGjobs/trip_print.jpg");
//            if (!imageFile.exists()) {
//                imageFile.mkdirs();
//            }
            imageFile = getOutputMediaFile();


            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

//            openScreenshot(imageFile);
            return imageFile;
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
        return imageFile;
    }

    public static String ValidateString(String string) {
        if (!string.equals("") && string != null && !string.equals("null")) {
            return string;
        }

        return "";
    }

    public static boolean isMyServiceRunning(Context c, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String ConvertTimeZone(String OurDate, String timeZone, String presentformat) {
        try {
//            "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
            SimpleDateFormat formatter = new SimpleDateFormat(presentformat);
            formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
            Date value = formatter.parse(OurDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat(presentformat); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            OurDate = dateFormatter.format(value);
            Log.e("OurDate", OurDate);
        } catch (Exception e) {
            e.printStackTrace();
            OurDate = "00-00-0000 00:00";
        }
        return OurDate;
    }

    public static boolean CheckPermissions(Context c, Fragment fragment, String permission, int requestcode) {
        // Here, thisActivity is the current activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(c,
                    permission)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.e("checkSelfPermission", "PERMISSION_GRANTED");

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(((Activity) c),
                        permission)) {
                    Log.e("checkSelfPermission", "shouldShowRequestPermissionRationale");
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.


                    fragment.requestPermissions(
                            new String[]{permission},
                            requestcode);


                } else {

                    fragment.requestPermissions(
                            new String[]{permission},
                            requestcode);
                    Log.e("checkSelfPermission", "no shouldShowRequestPermissionRationale");
                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

 /*   public static void buildAlertMessageNoGps(final Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.DialogThemee);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mContext.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }*/

    public static boolean IsLocationEnabled(Context context) {

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception ex) {
        }

        return gps_enabled || network_enabled;
    }


    // Print hashKey for FB app
    public static void printHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo("com.ulawn_customer",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("TEMPTAGHASH KEY:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    public static String getDateFormat(String presentfromat, String date, String reqFormat) throws ParseException {
        String strNewDate = "";
        try {
            DateFormat df = new SimpleDateFormat(presentfromat, Locale.ENGLISH);
            Date d = df.parse(date);
            sdf = new SimpleDateFormat(reqFormat, Locale.ENGLISH);

            strNewDate = sdf.format(d);
            Log.e("New Date", "Date==  " + strNewDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strNewDate;
    }

    public static long getDateFormatinMilli(String presentfromat, String date, String reqFormat) throws ParseException {
        long timeInMilliseconds = 0l;
        try {
            DateFormat df = new SimpleDateFormat(presentfromat, Locale.ENGLISH);
            Date d = df.parse(date);
            timeInMilliseconds = d.getTime();

            Log.e("New Date", "Date==  " + timeInMilliseconds);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }


     public static void setupParent(final Context context, View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        try {
            if (!(view instanceof EditText)) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        hideSoftKeyboard(context);
                        return false;
                    }
                });
            }
            //If a layout container, iterate over children
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    View innerView = ((ViewGroup) view).getChildAt(i);
                    setupParent(context, innerView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideSoftKeyboard(Context context) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && ((AppCompatActivity) context).getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(((AppCompatActivity) context).getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /////////Email Validation
    public static boolean validateEmailId(String email) {
        // TODO Auto-generated method stub
        final Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                        + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                        + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

        boolean validEmail = EMAIL_ADDRESS_PATTERN.matcher(email).matches();
        // validate email address
        return validEmail;

    }

    public static void requestFocus(Activity context, View view) {
        if (view.requestFocus()) {
            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static String formatToYesterdayOrToday(long timestampInMilliSeconds, boolean day) throws ParseException {
        Date date = new Date();
        date.setTime(timestampInMilliSeconds);
        String formattedDate = new SimpleDateFormat("MMM dd, yyyy").format(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestampInMilliSeconds);
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        if (day) {
            String timeFormatter = new SimpleDateFormat("EEE, MMM dd, yyyy").format(calendar.getTime());
            Log.e("TIMEE>", "timeFormatter  >>   " + timeFormatter);
            return timeFormatter;
        } else {
            Log.e("TIMEE>", ">>   " + formattedDate);
            return formattedDate;
        }

       /* if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return "Today " + formattedDate;
        } else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            return "Yesterday " + formattedDate;
        } else {
            return timeFormatter;
        }*/

    }

    public static void colorStatusBar(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window windoww = activity.getWindow();
            windoww.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            windoww.setStatusBarColor(color);
        }
    }

    public static void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }

    public static void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                } catch (NoSuchFieldException e) {
                    Log.w("setNumbPickerTextColor", e);
                } catch (IllegalAccessException e) {
                    Log.w("setNuerPickerTextColor", e);
                } catch (IllegalArgumentException e) {
                    Log.w("setNuerPickerTextColor", e);
                }
            }
        }
        return false;
    }

    public static String formatTime(long millis) {
        String output = "";
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds = seconds % 60;
//	    minutes = minutes % 60;
        hours = hours % 60;

        String secondsD = String.valueOf(seconds);
        String minutesD = String.valueOf(minutes);
        String hoursD = String.valueOf(hours);

        if (seconds < 10)
            secondsD = "0" + seconds;
        if (minutes < 10)
            minutesD = "0" + minutes;

	  /*  if (hours < 10)
            hoursD = "0" + hours;*/

        output = /*hoursD+" : "+*/minutesD + " : " + secondsD;

        return output;
    }

    public static String GetString(EditText editText) {

        return editText.getText().toString();
    }

    public static String saveimagetosdcard(Context ctx, Bitmap photo) {

        // Bitmap bitmap = null;
        FileOutputStream output;
        File file = null;

        try {
            file = getOutputMediaFile();

            output = new FileOutputStream(file);

            // Compress into png format image from 0% - 100%
            photo.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();

        } catch (Exception e) {
            Toast.makeText(ctx, "Try Again.", Toast.LENGTH_SHORT).show();
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static File getOutputMediaFile() {

        // External sdcard location
        File mediaStorageDir =
                getExternalStorageDirectory();
        Log.e("Absoletete Path", mediaStorageDir.getAbsolutePath());
        // Create a new folder in SD Card
        File dir = new File(mediaStorageDir.getAbsolutePath() + "/SGjobs/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Log.e("DIrectoryyy", dir.toString());
        // Create a media file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
        Locale.getDefault()).format(new Date());

        File mediaFile;

        mediaFile = new File(dir.getPath() + File.separator
                + "IMG_" + timeStamp + ".png");


        return mediaFile;
    }

    public static String copyFile(String inputPath, String inputFile) {

        InputStream in = null;
        File dir = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            dir = new File(
                    getExternalStorageDirectory().getAbsolutePath() + "/.Trvlease/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Log.e("tag file dir   ", dir.toString());

            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(dir.getAbsolutePath() + "/" + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        return dir.getAbsolutePath() + "/" + inputFile;

    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

  /*  public static void getDisplayImage(Context context, String string, ImageView imgView) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic)
                .showImageOnLoading(R.drawable.no_image)
                .showImageOnFail(R.drawable.no_image).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .displayer(new CircleBitmapDisplayer(Color.parseColor("#10BDAA"), 3))
                .build();

        ImageLoader.getInstance().displayImage(string, imgView, options);
     }

    public static void getDisplayImageRect(Context context, String string, ImageView imgView) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.no_image)
                .showImageOnLoading(R.drawable.no_image)
                .showImageOnFail(R.drawable.no_image).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer())
                .build();

        ImageLoader.getInstance().displayImage(string, imgView, options);

    }
*/
    public static void hideKeyboard(Activity activity, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            // do nothing return null
            return;
        }
        // set listAdapter in loop for getting final size
        int totalHeight = 0;
        Log.e("length are---->", String.valueOf(myListAdapter.getCount()));

        int s = myListAdapter.getCount();

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(myListView.getWidth(),
                View.MeasureSpec.AT_MOST);

        View view = null;
        for (int size = 0; size < s; size++) {
            view = myListAdapter.getView(size, view, myListView);
            if (size == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        // setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight
                + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
    }

    public static boolean isPackageExisted(Context c, String targetPackage) {

        PackageManager pm = c.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage,
                    PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }


    public static void Block_SpaceInEditText(EditText edittext) {

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

        edittext.setFilters(new InputFilter[]{filter});

    }


}
