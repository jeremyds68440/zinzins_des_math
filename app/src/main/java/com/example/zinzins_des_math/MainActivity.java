package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
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

    private ImageView jouer, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.jouer = findViewById(R.id.jouer);
        this.settings = findViewById(R.id.parametres);
        fAuth = FirebaseAuth.getInstance();
        loadData();
        actionClickImage(this.jouer, ChooseSoloMultiActivity.class);
        if (fAuth.getCurrentUser() != null) {
            actionClickImage(this.settings, ParametreActivity.class);
        }else{
            actionClickImage(this.settings, parametreSansConnexionActivity.class);

        }
    }

    public void actionClickImage(ImageView button, Class act){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_effect_state){
                    bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                button.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent intent = new Intent(getApplicationContext(), act);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }

}