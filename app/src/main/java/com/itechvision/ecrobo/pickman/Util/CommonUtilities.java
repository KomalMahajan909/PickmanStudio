package com.itechvision.ecrobo.pickman.Util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.itechvision.ecrobo.pickman.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class CommonUtilities {
    static Context ctx;
    LinearLayout Gpslayout, Internetlayout ;
    static boolean wifiDataAvailable = false;
    static boolean mobileDataAvailable = false;
    static boolean isConnected = false;
    static RelativeLayout rl;
    static SimpleDateFormat sdf;
    static String DIRECTORY_NAME = "Purlieu", tempfilepath;
    static File tempFile;
    Dialog dialog_Forgot;
    public static SlidingMenu menu;
    ImageView dataimage, locimage;
    static ActionBar actionBar;
    public static TextView txtTitle,txtRightTitle,toast_text_view1,toast_text;
    public static ImageView imgLeft,imgRight,img3ActionBar;
    public static  RelativeLayout relLayout1,relLayout2,relLayout3;
    public static Button btnRed,btnGreen,btnBlue;

    public static void hideKeyboard(Context context, EditText edit) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);

    }
    public static void hideKeyboard(Context context , Button button)
    {

        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(button.getWindowToken(), 0);
    }

    public static SlidingMenu setSlidingMenu(Context c) {
        menu = new SlidingMenu(c);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.setBehindWidth(setWidth(c)/2+140);


        menu.attachToActivity((Activity) c, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.activity_slider);

        menu.addIgnoredView(menu);

        return menu;
    }


    @SuppressLint("NewApi")
    public static Bitmap blur(Context context, Bitmap image,
                              float BITMAP_SCALE, float BLUR_RADIUS) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height,
                false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs,
                Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

	/*public static void showMessage(String msg, Context context) {
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View layout = inflater.inflate(R.layout.viewww,
				(ViewGroup) ((Activity) context)
						.findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);

		 * text.setWidth(500); text.setHeight(50);

		text.setText(msg);
 		Toast toast = new Toast(context);
		toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 70);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}*/

    public static int getSuitableWidth(Activity active) {

        Display display = active.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        width = (width / 2) + 80;

        return width;
    }





    public static int getHeight(Context c) {
        Display display = ((Activity) c).getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        return height;
    }

    public static int setWidth(Context c) {
        Display display = ((Activity) c).getWindowManager().getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        int width;
        if (h < 500) {
            width = (w - 60);
        } else {
            width = (w - 100);
        }
        return width;

    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(),
                    Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    // Check if Internet Network is active
    public static boolean getConnectivityStatus(Context context) {

        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
/*		NetworkInfo[] networkInfo = conManager.getAllNetworkInfo();
		for (NetworkInfo netInfo : networkInfo) {
		 if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
		  if (netInfo.isConnected())
					wifiDataAvailable = true;
			if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
				if (netInfo.isConnected())
					mobileDataAvailable = true;
		}
		return wifiDataAvailable || mobileDataAvailable;
///uncomment

		ConnectivityManager cm = (ConnectivityManager) context
		.getSystemService(Context.CONNECTIVITY_SERVICE);*/

        NetworkInfo activeNetwork = conManager.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)

                isConnected = true;
            // return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                // return TYPE_MOBILE;
                isConnected = true;
        } else {
            isConnected = false;
        }
// return TYPE_NOT_CONNECTED;
        return isConnected;

    }


    public static String saveimagetosdcard(Context ctx, Bitmap photo) {

        // Bitmap bitmap = null;
        FileOutputStream output;
        File file;
        file = getOutputMediaFile();
        try {

            output = new FileOutputStream(getOutputMediaFile());

            // Compress into png format image from 0% - 100%
            photo.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();
            /*
             * Toast.makeText(ctx,
             * "Image Saved to "+getOutputMediaFile().getAbsolutePath(),
             * Toast.LENGTH_SHORT) .show()
             */;
        }

        catch (Exception e) {
            Toast.makeText(ctx, "Try Again.", Toast.LENGTH_SHORT).show();
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public static File getOutputMediaFile() {

        // External sdcard location
        File mediaStorageDir = Environment.getExternalStorageDirectory();

        // Create a new folder in SD Card
        File dir = new File(mediaStorageDir.getAbsolutePath() + "/Purlieu/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        File mediaFile;

        mediaFile = new File(dir.getPath() + File.separator + "IMG_"
                + timeStamp + ".png");

        return mediaFile;
    }
    public static void scrollToView(ScrollView scrollViewParent, View view) {
        // Get deepChild Offset
        view.requestFocus();
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }
    public static void getDeepChildOffset(ViewGroup mainParent, ViewParent parent, View child, Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }
    public static String getTWTimeString(Long fromdate) {

        long then;
        then = fromdate;
        Log.e("time", then + "");
        Date date = new Date(then);

        StringBuffer dateStr = new StringBuffer();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar now = Calendar.getInstance();

        int year = yearBetween(calendar.getTime(), now.getTime());
        int months = monthBetween(calendar.getTime(), now.getTime());
        int days = daysBetween(calendar.getTime(), now.getTime());
        int minutes = minuteBetween(calendar.getTime(), now.getTime());
        int hours = hoursBetween(calendar.getTime(), now.getTime());
        Log.e("days", "" + days + " aaaaa " + hours);

        if (days == 0) {

            int second = secondsBetween(calendar.getTime(), now.getTime());
            if (minutes > 60) {

                // dateStr.append(hours).append(hours > 1 ? "h" : "h");

                if (hours == 1) {
                    dateStr.append(hours).append(" hour ago");
                } else if (hours > 1 && hours <= 24) {
                    dateStr.append(hours).append(" hours ago");
                }

            } else {

                if (second <= 10) {
                    dateStr.append("Now");
                } else if (second > 10 && second <= 30) {
                    dateStr.append("few seconds ago");
                } else if (second > 30 && second <= 60) {
                    dateStr.append(second).append(" seconds ago");
                } else if (second >= 60 && minutes <= 60) {
                    dateStr.append(minutes).append(" minutes ago");
                }
            }
        } else if (hours >= 24 && days == 1) {

            dateStr.append(days).append(" day ago");

        } else if (hours > 24 && days <= 31 && days > 1) {

            dateStr.append(days).append(" days ago");
        } else if (days > 31 && days < 365) {

            dateStr.append(months).append(" month ago");
        } else if (days > 365) {

            dateStr.append(year).append(" year ago");

        } else {
            dateStr.append(sdf.format(date));

        }

        return dateStr.toString();
    }

    public static int secondsBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.SECOND_IN_MILLIS);
    }

    public static int minuteBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.MINUTE_IN_MILLIS);
    }

    public static int hoursBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.HOUR_IN_MILLIS);
    }

    public static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.DAY_IN_MILLIS);
    }

    public static int monthBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (DateUtils.DAY_IN_MILLIS * 30));
    }

    public static int yearBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (DateUtils.YEAR_IN_MILLIS));
    }

    public static Boolean chkIfHitWebserviceOrNot(Long timeinmili) {
        Boolean bool = false;
        Date currentdate = new Date(System.currentTimeMillis());
        Date Nextdate = new Date(timeinmili);
        System.out.println("CURRENT  " + currentdate);
        System.out.println("Next " + Nextdate);
        int i = CommonUtilities.secondsBetween(currentdate, Nextdate);
        System.out.println("TIME LEFT " + i);
        if (i < 0) {
            bool = true;
        } else {
            bool = false;
        }

        return bool;

    }

    public static String createTempFile(String path) {
        int rotation_degree = getExifOrientation(path);
        Log.e("ROTATION DEGREE", "" + rotation_degree);
        Bitmap temp = BitmapFactory.decodeFile(path);
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation_degree);

        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory() + "/"
                        + DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(DIRECTORY_NAME, "Oops! Failed create " + DIRECTORY_NAME
                        + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        tempFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"
                + timeStamp + ".jpg");
        tempfilepath = Environment.getExternalStorageDirectory() + "/"
                + DIRECTORY_NAME + "/IMG_" + timeStamp + ".jpg";
        temp = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(),
                temp.getHeight(), matrix, true);

        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(tempFile);
            temp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return tempfilepath;
    }

    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognise a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }

        return degree;
    }



    public static void actionbarImplement(Activity _Context,String strTitle,String strRighttTitle,Integer leftImage,boolean showblue,boolean showred, boolean showgreen ){

        actionBar = (ActionBar)_Context.findViewById(R.id.actionbar);
        relLayout1=(RelativeLayout)actionBar.findViewById(R.id.relLayout1);
        relLayout2=(RelativeLayout)actionBar.findViewById(R.id.relLayout2);
        txtTitle=(TextView)actionBar.findViewById(R.id.txtTitle);

//      txtRightTitle=(TextView)actionBar.findViewById(R.id.txtRightTitle);
        imgLeft=(ImageView)actionBar.findViewById(R.id.img1ActionBar);
//      imgRight=(ImageView)actionBar.findViewById(R.id.img2ActionBar);

        btnBlue=(Button) actionBar.findViewById(R.id.notif_count_blue);
        btnGreen=(Button)actionBar.findViewById(R.id.notif_count_green);
        btnRed=(Button)actionBar.findViewById(R.id.notif_count_red);


        txtTitle.setText(strTitle);
//      txtRightTitle.setText(strRighttTitle);
        imgLeft.setBackgroundResource(leftImage);
//      imgRight.setBackgroundResource(righttImage);

        if(showred)
            btnRed.setVisibility(View.VISIBLE);
        else
            btnRed.setVisibility(View.GONE);

        if(showblue)
            btnBlue.setVisibility(View.VISIBLE);
        else
            btnBlue.setVisibility(View.GONE);

        if(showgreen)
            btnGreen.setVisibility(View.VISIBLE);
        else
            btnGreen.setVisibility(View.GONE);

		    /*
		    toastview=(View)_Context.findViewById(R.id.toastview);
		   toast_text =(TextView)toastview.findViewById(R.id.toast_text_view1);
		*/

    }

    //second actionbar

    public static void buildAlertMessageNoGps(final Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(
                "Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mContext.startActivity(new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


    public static void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(
                "Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    @SuppressWarnings("unused") final DialogInterface dialog,
                                    @SuppressWarnings("unused") final int id) {
                                ctx.startActivity(new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        @SuppressWarnings("unused") final int id) {
                        dialog.cancel();

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }
    public static void openInternetDialog(Context c) {
        ctx = c;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder.setTitle("Internet Alert!");
        alertDialogBuilder
                .setMessage(
                        "You are not connected to Internet..Please Enable Internet!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                final Intent intent = new Intent(
                                        Intent.ACTION_MAIN, null);
                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                final ComponentName cn = new ComponentName(
                                        "com.android.settings",
                                        "com.android.settings.wifi.WifiSettings");
                                intent.setComponent(cn);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                ctx.startActivity(intent);
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            // do nothing return null
            return;
        }
        // set listAdapter in loop for getting final size
        int totalHeight = 0;
        //  Log.e("length are---->", String.valueOf(myListAdapter.getCount()));

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
        //	  Log.i("height of listItem:", String.valueOf(totalHeight));
    }
    public static String convertTime(long time)
    {
        long uptime = time;
        long hours = TimeUnit.MILLISECONDS
                .toHours(time);
        uptime -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS
                .toMinutes(uptime);
        uptime -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS
                .toSeconds(uptime);
        String finaltime=hours+":"+minutes+":"+seconds;
        Log.e("final time becomes",">>>>"+finaltime);
        return finaltime;
    }
    public static String getDateFromMillis(int totalSecs) {

        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return timeString;
    }
    public  static String convertbytestoMB(long bytes)
    {
        double step = Math.pow(1024, 2);
        return String.format("%3.2f %s", bytes / step, "MB");

//			 return Long.toString(bytes);
    }
    public static  String getMimeType(Uri uri, Context ctx) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = ctx.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }


    public static String RemovebarcodeAl(String str) {

        // Regex to remove leading
        // zeros from a string
        String regex = "^AL095-I+(?!$)";

        // Replaces the matched
        // value with given string
        str = str.replaceAll(regex, "");
        return str;
    }
}




