package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class LevelActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_level);
        ImageView back = findViewById(R.id.back_to_evolution);
        ImageView multifactor = findViewById(R.id.multifactor_evolution);
        ImageView mathemaquizz = findViewById(R.id.mathemaquizz_evolution);
        loadData();
        if (sound_theme_state) {
            SplashScreenActivity.general_sound.start();
        }

        actionClickImageEvolution(back, DifficultyActivity.class,12);
        actionClickImageEvolution(multifactor, MultiFactorActivity.class,3);
        actionClickImageEvolution(mathemaquizz, MathemaQuizzActivity.class,3);

        ImageView infoMultifactor = findViewById(R.id.info_multifactor_evolution);
        ImageView infoMathemaquizz = findViewById(R.id.info_mathemaquizz_evolution);

        actionClickInfo(infoMultifactor, "multifactor");
        actionClickInfo(infoMathemaquizz, "mathemaquizz");
    }

    private void actionClickImageEvolution(ImageView button, Class act, int difficulty) {
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

    private void actionClickInfo(ImageView button, String gameName) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setColorFilter(Color.argb(80, 0, 0, 0));
                createPopupInfo(gameName, button);
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DifficultyActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void createPopupInfo(String gameName, ImageView button){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView_back = LayoutInflater.from(this).inflate(R.layout.custom_popup_info, viewGroup, false);
        builder.setView(dialogView_back);
        AlertDialog alertDialog = builder.create();

        Button close = dialogView_back.findViewById(R.id.button_cancel);
        LinearLayout popup= dialogView_back.findViewById((R.id.layout_popup_info));

        String imagePopup = gameName + "_info";
        int resId = getResources().getIdentifier(imagePopup, "drawable", getPackageName());
        popup.setBackground(getDrawable(resId));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                button.setColorFilter(Color.argb(0,0,0,0));
            }
        });
        alertDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sound_theme_state) {
            SplashScreenActivity.general_sound.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (sound_theme_state) {
            SplashScreenActivity.general_sound.start();

        }
    }
}
