package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class choosesolomultiActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ETAT_SOUND_THEME = "etat_sound_theme";
    public static final String ETAT_SOUND_EFFECT = "etat_sound_effect";

    private boolean sound_theme_state;
    private boolean sound_effect_state;

    private MediaPlayer bouton_sound;

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sound_theme_state = sharedPreferences.getBoolean(ETAT_SOUND_THEME, true);
        sound_effect_state = sharedPreferences.getBoolean(ETAT_SOUND_EFFECT, true);

    }
    ImageView Back, multi, solo, toDuel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_solo_or_multi);
        loadData();
        Back = findViewById(R.id.choix_mode_de_jeu_back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_effect_state){
                    bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                Back.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent difficulty = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(difficulty);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        solo = findViewById(R.id.soloView);
        solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_effect_state){
                    bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                solo.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent difficulty = new Intent(getApplicationContext(),DifficultyActivity.class);
                startActivity(difficulty);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        multi = findViewById(R.id.multiView);
        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_effect_state){
                    bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                multi.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent multiPlayerActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(multiPlayerActivity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        toDuel = findViewById(R.id.toDuel);
        toDuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_effect_state){
                    bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                toDuel.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent multiPlayerActivity = new Intent(getApplicationContext(), RoomListActivity.class);
                startActivity(multiPlayerActivity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }


    public void onBackPressed() {
        Intent difficulty = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(difficulty);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}