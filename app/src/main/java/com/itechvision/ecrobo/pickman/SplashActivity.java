package com.itechvision.ecrobo.pickman;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class SplashActivity extends Activity  {

    private ImageView a;
    private ShimmerTextView shimmerTv;
    Shimmer shimmer ;
    String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        a = (ImageView) findViewById(R.id.a);
        Log.d(TAG,"On Create ");
        shimmerTv = (ShimmerTextView) findViewById(R.id.shimmer_tv);

        shimmer = new Shimmer();
        shimmer.start(shimmerTv);
        shimmer.setRepeatCount(10000)
                .setDuration(800)
                .setStartDelay(300)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                Intent in = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(in);
                finish();
            }
        }, 1500);

    }

}
