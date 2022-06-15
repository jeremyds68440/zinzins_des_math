package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

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

        actionClickImageFacile(back, DifficultyActivity.class,12);
        actionClickImageFacile(multifactor, MultiFactorActivity.class,0);
        actionClickImageFacile(mathemaquizz, MathemaQuizzActivity.class,0);
        actionClickImageFacile(roulette, RouletteActivity.class,0);

        ImageView infoMultifactor = findViewById(R.id.info_multifactor_facile);
        ImageView infoMathemaquizz = findViewById(R.id.info_mathemaquizz_facile);
        ImageView infoRoulette = findViewById(R.id.info_roulette_facile);

        actionClickInfo(infoMultifactor, "multifactor");
        actionClickInfo(infoMathemaquizz, "mathemaquizz");
        actionClickInfo(infoRoulette, "roulette");

    }

    private void actionClickImageFacile(ImageView button, Class act, int difficulty) {
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
                createPopupInfo(gameName);
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DifficultyActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void createPopupInfo(String gameName){
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
            }
        });
        alertDialog.show();
    }
}