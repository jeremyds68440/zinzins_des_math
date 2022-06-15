package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.nio.channels.InterruptedByTimeoutException;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_SCREEN_TIME = 3000;
    private ProgressBar loading;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ETAT_SOUND_THEME = "etat_sound_theme";
    public static final String ETAT_SOUND_EFFECT = "etat_sound_effect";

    private boolean sound_theme_state;
    private boolean sound_effect_state;

    private MediaPlayer soundtheme;

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sound_theme_state = sharedPreferences.getBoolean(ETAT_SOUND_THEME, true);
        sound_effect_state = sharedPreferences.getBoolean(ETAT_SOUND_EFFECT, true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        loadData();
        this.soundtheme = MediaPlayer.create(getApplicationContext(), R.raw.theme_zinzins_lofi);
        if (sound_theme_state) {
            soundtheme.start();
        }
        this.loading = (ProgressBar) findViewById(R.id.loading);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        this.loading.startAnimation(myanim);

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