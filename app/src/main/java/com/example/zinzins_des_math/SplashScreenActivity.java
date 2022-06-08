package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.nio.channels.InterruptedByTimeoutException;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_SCREEN_TIME = 3000;
    private ImageView logo;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags();
        setContentView(R.layout.activity_splash_screen);

        logo = (ImageView) findViewById(R.id.logo_icon);
        loading = (ProgressBar) findViewById(R.id.loading);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        logo.startAnimation(myanim);
        loading.startAnimation(myanim);
        final Intent i = new Intent(this, MainActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(SPLASH_SCREEN_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
                    finish();
                }
            }
        };
        timer.start();
    }
}