package com.itechvision.ecrobo.pickman.ServerRetro;

import android.content.Context;
import android.util.Log;

import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Context ctx;
  //public static final String BASE_URL = "http://18.188.223.209/api/v1/";
  //public static final String BASE_URL1 = "http://18.188.223.209/";
  //public static final String BASE_URL1 = "https://web-forte.in/storage/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level .BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                    .baseUrl(BaseActivity.getUrl()+"/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
      //}
        return retrofit;
    }
}


                  