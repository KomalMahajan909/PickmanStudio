package com.itechvision.ecrobo.pickman.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;


import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFunctions {
    public static String baseUrl = "" ;
    static MainAsynListener<String> listener;
    static String IPaddress;
    static String TAG = "CommonFunctions";

    public CommonFunctions() {

    }
    public static void setAccessPoint(String url) {
        baseUrl = url;
    }
    public static String getOkHttpClient(String Url, int flag, String tag, List<ParamsGetter> getters) {
        String strResponse="";
        try {
            OkHttpClient okk=new OkHttpClient();
            okk.setConnectTimeout(2, TimeUnit.MINUTES); // connect timeout
            okk.setReadTimeout(2, TimeUnit.MINUTES);    // socket timeout

            Request request=null;
            Log.e("WEbsrvice inkkk",baseUrl+Url);

            if(tag.equals("")) {
                request = new Request.Builder()
                        .url(baseUrl+Url)
                        .build();
            }
            if(tag.equalsIgnoreCase("Form")){
                FormEncodingBuilder formbody= new FormEncodingBuilder();
                for (int i = 0; i <getters.size() ; i++) {
                    Log.e("KEY VALUES",">>>>    "+getters.get(i).getKey()+":"+getters.get(i).getValues());
                    formbody.add(getters.get(i).getKey(),getters.get(i).getValues());
                }
                RequestBody req=formbody.build();
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .url(baseUrl+Url)
                        .post(req)
                        .build();
            }
            if(tag.equalsIgnoreCase("Multipart")){
                final MediaType MEDIA_TYPE_TXT = MediaType.parse("txt");
                final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                final MediaType MEDIA_TYPE_Video = MediaType.parse("video/*");
                MultipartBuilder builder=new MultipartBuilder();
                for (int i = 0; i <getters.size() ; i++) {
                    if(getters.get(i).getFile()!=null) {
                        if (getters.get(i).getFile().getAbsolutePath().endsWith(".png") || getters.get(i).getFile().getAbsolutePath().endsWith(".jpg")) {
                            builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getFile().getName(), RequestBody.create(MEDIA_TYPE_PNG, getters.get(i).getFile()));
                            Log.e("ENds With",">>>  "+getters.get(i).getFile().getAbsolutePath());
                        }
                        if (getters.get(i).getFile().getAbsolutePath().endsWith(".mp4") || getters.get(i).getFile().getAbsolutePath().endsWith(".mpeg")|| getters.get(i).getFile().getAbsolutePath().endsWith(".3gp")|| getters.get(i).getFile().getAbsolutePath().endsWith(".avi")) {
                            builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getFile().getName(), RequestBody.create(MEDIA_TYPE_Video, getters.get(i).getFile()));
                            Log.e("ENds With",">>>  "+getters.get(i).getKey()+">>>>>" +getters.get(i).getFile().getAbsolutePath());
                        }
                        if (getters.get(i).getFile().getAbsolutePath().endsWith(".txt") || getters.get(i).getFile().getAbsolutePath().endsWith(".log")) {
                            builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getFile().getName(), RequestBody.create(MEDIA_TYPE_TXT, getters.get(i).getFile()));
                            Log.e("ENds With",">>>  "+getters.get(i).getFile().getAbsolutePath());
                        }
                    }else {
                        Log.e("Key SEND Without Image",getters.get(i).getKey() + "<><>><"+getters.get(i).getValues());
                        builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getValues());
                    }
                }
                RequestBody req=builder.build();
                request = new Request.Builder()
                        .url(baseUrl+Url)

                        .post(req)
                        .build();
                Log.e("Senttttttt ",".........."+req);

            }

            Response response = okk.newCall(request).execute();
            strResponse=response.body().string();
            Log.e("String Response",">>>>   "+strResponse);
        } catch (IOException e) {
            e.printStackTrace();
            listener.onPostError(flag);
        }

        return strResponse;
    }
    public static String getOkHttp(String Url, int flag, String tag, List<ParamsGetter> getters) {
        String strResponse="";
        try {
            OkHttpClient okk=new OkHttpClient();
            okk.setConnectTimeout(2, TimeUnit.MINUTES); // connect timeout
            okk.setReadTimeout(2, TimeUnit.MINUTES);    // socket timeout

            Request request=null;
            Log.e("WEbsrvice inkkk",Url);

            if(tag.equals("")) {
                request = new Request.Builder()
                        .url(Url)
                        .build();
            }
            if(tag.equalsIgnoreCase("Form")){
                FormEncodingBuilder formbody= new FormEncodingBuilder();
                for (int i = 0; i <getters.size() ; i++) {
                    Log.e("KEY VALUES",">>>>    "+getters.get(i).getKey()+":"+getters.get(i).getValues());
                    formbody.add(getters.get(i).getKey(),getters.get(i).getValues());
                }
                RequestBody req=formbody.build();
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .url(Url)
                        .post(req)
                        .build();
            }
            if(tag.equalsIgnoreCase("Multipart")){
                final MediaType MEDIA_TYPE_TXT = MediaType.parse("txt");
                final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                final MediaType MEDIA_TYPE_Video = MediaType.parse("video/*");
                MultipartBuilder builder=new MultipartBuilder();
                for (int i = 0; i <getters.size() ; i++) {
                    if(getters.get(i).getFile()!=null) {
                        if (getters.get(i).getFile().getAbsolutePath().endsWith(".png") || getters.get(i).getFile().getAbsolutePath().endsWith(".jpg")) {
                            builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getFile().getName(), RequestBody.create(MEDIA_TYPE_PNG, getters.get(i).getFile()));
                            Log.e("ENds With",">>>  "+getters.get(i).getFile().getAbsolutePath());
                        }
                        if (getters.get(i).getFile().getAbsolutePath().endsWith(".mp4") || getters.get(i).getFile().getAbsolutePath().endsWith(".mpeg")|| getters.get(i).getFile().getAbsolutePath().endsWith(".3gp")|| getters.get(i).getFile().getAbsolutePath().endsWith(".avi")) {
                            builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getFile().getName(), RequestBody.create(MEDIA_TYPE_Video, getters.get(i).getFile()));
                            Log.e("ENds With",">>>  "+getters.get(i).getKey()+">>>>>" +getters.get(i).getFile().getAbsolutePath());
                        }
                        if (getters.get(i).getFile().getAbsolutePath().endsWith(".txt") || getters.get(i).getFile().getAbsolutePath().endsWith(".log")) {
                            builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getFile().getName(), RequestBody.create(MEDIA_TYPE_TXT, getters.get(i).getFile()));
                            Log.e("ENds With",">>>  "+getters.get(i).getFile().getAbsolutePath());
                        }
                    }else {
                        Log.e("Key SEND Without Image",getters.get(i).getKey() + "<><>><"+getters.get(i).getValues());
                        builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getValues());
                    }
                }
                RequestBody req=builder.build();
                request = new Request.Builder()
                        .url(Url)

                        .post(req)
                        .build();
                Log.e("Senttttttt ",".........."+req);

            }

            Response response = okk.newCall(request).execute();
            strResponse=response.body().string();
            Log.e("String Response",">>>>   "+strResponse);
        } catch (IOException e) {
            e.printStackTrace();
            listener.onPostError(flag);
        }

        return strResponse;
    }
    public static String getOkHttpClient(String Url, int flag, String tag, List<ParamsGetter> getters,List <String> list,String listtag) {
        Log.e("getOkHttpClient","Seting up rootUrl" +baseUrl);
//      rootUrl=BaseActivity.getUrl();
        String strResponse="";
        MainAsynListener<String> listener=null;
        try {
            OkHttpClient okk=new OkHttpClient();
            okk.setConnectTimeout(2, TimeUnit.MINUTES); // connect timeout
            okk.setReadTimeout(2, TimeUnit.MINUTES);    // socket timeout

            Request request=null;
            Log.e("WEbsrvice inkkk",baseUrl+Url);

            if(tag.equals("")) {
                request = new Request.Builder()
                        .url(baseUrl+Url)
                        .build();
            }
            if(tag.equalsIgnoreCase("Form")){
                FormEncodingBuilder formbody= new FormEncodingBuilder();

                StringBuffer serial = new StringBuffer();

                for (String maps : list) {
//                    if(serial.equals(""))
                    serial.append("\t").append(maps);

                }
                Log.e("LIST VALUES",">>>>    "+listtag+":"+serial.substring(0));

                formbody.add(listtag,serial.substring(1));

                for (int i = 0; i <getters.size() ; i++) {
                    Log.e("KEY VALUES",">>>>    "+getters.get(i).getKey()+":"+getters.get(i).getValues());
                    formbody.add(getters.get(i).getKey(),getters.get(i).getValues());
                }
                RequestBody req=formbody.build();
                request = new Request.Builder()
//						.addHeader("Content-Type", "application/json; charset=utf-8")
                        .url(baseUrl+Url)
                        .post(req)
                        .build();
                Log.e("Senttttttt ",".........."+req);

            }
            if(tag.equalsIgnoreCase("Multipart")){
                final MediaType MEDIA_TYPE_PNG = MediaType.parse("txt");
//				final MediaType MEDIA_TYPE_Video = MediaType.parse("video/*");
                MultipartBuilder builder=new MultipartBuilder();
                for (int i = 0; i <getters.size() ; i++) {
                    if(getters.get(i).getFile()!=null) {
                        if (getters.get(i).getFile().getAbsolutePath().endsWith(".txt") ) {
                            builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getFile().getName(),RequestBody.create(MEDIA_TYPE_PNG, getters.get(i).getFile()));
                            Log.e("ENds With",">>>  "+getters.get(i).getKey() +getters.get(i).getFile().getAbsolutePath());
                        }
//						if (getters.get(i).getFile().getAbsolutePath().endsWith(".mp4") || getters.get(i).getFile().getAbsolutePath().endsWith(".mpeg")|| getters.get(i).getFile().getAbsolutePath().endsWith(".3gp")|| getters.get(i).getFile().getAbsolutePath().endsWith(".avi")) {
//							builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getFile().getName(), RequestBody.create(MEDIA_TYPE_Video, getters.get(i).getFile()));
//							Log.e("ENds With",">>>  "+getters.get(i).getKey()+">>>>>" +getters.get(i).getFile().getAbsolutePath());
//						}
                    }else {
                        Log.e("Key SEND Without Image",getters.get(i).getKey() + "<><>><"+getters.get(i).getValues());
                        builder.type(MultipartBuilder.FORM).addFormDataPart(getters.get(i).getKey(), getters.get(i).getValues());
                    }
                }
                RequestBody req=builder.build();
                request = new Request.Builder()
                        .url(baseUrl+Url)

                        .post(req)
                        .build();
                Log.e("Senttttttt",".......... "+req);

            }

            Response response = okk.newCall(request).execute();
            strResponse=response.body().string();
            Log.e("String Response",">>>>   "+strResponse);
        } catch (IOException e) {
            e.printStackTrace();
            listener.onPostError(flag);
        }

        return strResponse;
    }

    public static String NetwordDetect(Context c) {

        boolean WIFI = false;
        boolean MOBILE = false;

        ConnectivityManager CM = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = CM.getAllNetworkInfo();

        for (NetworkInfo netInfo : networkInfo) {

            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))

                if (netInfo.isConnected())

                    WIFI = true;

            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))

                if (netInfo.isConnected())

                    MOBILE = true;
        }

        if(WIFI == true)
         {
            IPaddress = GetDeviceipWiFiData(c);

        }
         if(MOBILE == true)
        {

            IPaddress = GetDeviceipMobileData(c);

        }
        return (IPaddress);
    }


    public static String GetDeviceipMobileData(Context c){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }
        return null;
    }

    public static String GetDeviceipWiFiData(Context c)
    {

        WifiManager wm = (WifiManager)c.getSystemService(Context.WIFI_SERVICE);
         @SuppressWarnings("deprecation")
         String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        return ip;

    }

    public static String getBracode(String barcode){

        if ((BaseActivity.getUrl().equals("http://165.100.112.154/service") || BaseActivity.getUrl().equals("http://52.198.136.69/service") || BaseActivity.getUrl().equals("https://165.100.112.154/service")) && (BaseActivity.getShopId().equals("1139")) || BaseActivity.getShopId().equals("1242") ) {
            if(barcode.length()>18){
                String[] splitdata =barcode.split(",");
                Log.e(TAG, "ScannedEventttttt   " + " splitdata 000 "  + splitdata[0] +" splitdata last "  + splitdata[splitdata.length-2]  +" splitdata last -11"  + splitdata[splitdata.length-1]);
                char first = splitdata[splitdata.length-1].charAt(0);

                Log.e(TAG," first character  "+first);

                boolean hasUpperCase = matchString(first+"",".*[A-Z].*");

                String finalbarcode=null;
                if (hasUpperCase)
                {
                    finalbarcode=splitdata[0]+"-"+first;
                    Log.e(TAG," character  data becomes "+finalbarcode);
                }
                else {
                    boolean hasUpperCase1 = matchString(first + "",".*[0-9].*");

                    if (hasUpperCase1) {
                        finalbarcode = splitdata[0];
                        Log.e(TAG, " digit data becomes 11111" + finalbarcode);
                    }
                    else{
                        char first1 = splitdata[splitdata.length-1].charAt(3);
                        Log.e(TAG," first character1111  "+first1);

                        boolean hasUpperCase2 = matchString(first1+"",".*[A-Z].*");

                        if (hasUpperCase2)
                        {
                            finalbarcode=splitdata[0]+"-"+first1;
                            Log.e(TAG," character  data becomes1111 "+finalbarcode);
                        }
                        else
                        {
                            first1 = splitdata[splitdata.length-1].charAt(4);
                            Log.e(TAG," first character1111  "+first1);

                            hasUpperCase2 = matchString(first1+"",".*[A-Z].*");
                            if(hasUpperCase2)
                            {
                                finalbarcode=splitdata[0]+"-"+first1;
                                Log.e(TAG," character  data becomes1111 "+finalbarcode);
                            }
                            else
                                finalbarcode = splitdata[0];

                        }
                    }
                }
                barcode= finalbarcode;
            }
        }
        return barcode;

    }
    public static boolean matchString (String str,String match)
    {
        Pattern p = Pattern.compile(match);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

}

