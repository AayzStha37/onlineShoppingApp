package com.example.aayzstha.pnpapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Aayz Stha on 7/15/2017.
 */
public class Splash extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread spl=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Intent itent3=new Intent(Splash.this,LogIn.class);
                    startActivity(itent3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        spl.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}


