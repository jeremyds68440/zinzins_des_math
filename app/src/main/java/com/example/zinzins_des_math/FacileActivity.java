package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FacileActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facile);
        loadData();
        ImageView back = findViewById(R.id.back_to_facile);
        ImageView multifactor = findViewById(R.id.multifactor_facile);
        ImageView mathemaquizz = findViewById(R.id.mathemaquizz_facile);
        ImageView roulette = findViewById(R.id.roulette_facile);

        actionClickImagefacile(back, DifficultyActivity.class,12);
        actionClickImagefacile(multifactor, MultiFactorActivity.class,0);
        actionClickImagefacile(mathemaquizz, MathemaQuizzActivity.class,0);
        actionClickImagefacile(roulette, RouletteActivity.class,0);

    }

    private void actionClickImagefacile(ImageView button, Class act, int difficulty) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_effect_state){
                    bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                button.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent intent = new Intent(getApplicationContext(), act);
                intent.setFlags(difficulty);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DifficultyActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}