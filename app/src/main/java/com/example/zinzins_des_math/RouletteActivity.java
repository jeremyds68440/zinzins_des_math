package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Random;


public class RouletteActivity extends AppCompatActivity {

    private static String[] sectors = {"1", "2", "3", "4", "5", "6", "7","8","9"};
    private static final int[] sectorDegrees = new int[sectors.length];
    private static final Random random = new Random();
    private int degree = 0;
    private ImageView wheel;
    private RouletteActivity rouletteActivity = this;
    int cpt1 = 0;
    int cpt2 = 0;
    public ConstraintLayout rootRoulette;
    private ImageView fleche;
    private ImageView lancerBtn;
    private ImageView back_roulette;
    private String difficultyString;

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
        setContentView(R.layout.activity_roulette);
        loadData();
        rootRoulette = findViewById(R.id.rootRoulette);
        wheel = findViewById(R.id.wheel);
        fleche = findViewById(R.id.flecheRoulette);
        lancerBtn = findViewById(R.id.startBtn);

        //Backgrounds
        switch(getIntent().getFlags()){
            case 0 :
                rootRoulette.setBackground(getDrawable(R.drawable.background_roulette_facile));
                fleche.setImageResource(R.drawable.fleche_roulette_facile);
                wheel.setImageResource(R.drawable.roulette);
                difficultyString = "facile";
                lancerBtn.setImageResource(R.drawable.lancer_roulette);
                break;
            case 1 :
                rootRoulette.setBackground(getDrawable(R.drawable.accueil_roulette_moyen));
                fleche.setImageResource(R.drawable.fleche_roulette_moyen);
                wheel.setImageResource(R.drawable.roulette_moy);
                difficultyString = "moyen";
                lancerBtn.setImageResource(R.drawable.lancer_roulette_difficile);
                break;
            case 2 :
                rootRoulette.setBackground(getDrawable(R.drawable.accueil_roulette_difficile));
                fleche.setImageResource(R.drawable.fleche_roulette_difficile);
                wheel.setImageResource(R.drawable.roulette_diff);
                difficultyString = "difficile";
                lancerBtn.setImageResource(R.drawable.lancer_roulette_difficile);
                break;
        }


        final ImageView startBtn = findViewById(R.id.startBtn);
        this.wheel = findViewById(R.id.wheel);
        back_roulette = findViewById(R.id.back_roulette);
        startBtn.setColorFilter(Color.argb(0, 0, 0, 0));

        getDegreeForSectors();

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_effect_state){
                    bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                startBtn.setColorFilter(Color.argb(80, 0, 0, 0));
                spin();
            }
        });

        back_roulette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_effect_state){
                    bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                back_roulette.setColorFilter(Color.argb(80, 0, 0, 0));
                setBackPopup();
            }
        });


    }

    private void spin() {
        degree = random.nextInt(sectors.length - 1);

        RotateAnimation rotateAnimation = new RotateAnimation(0, (360 * sectors.length) + sectorDegrees[degree], RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3600);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //Toast.makeText(RouletteActivity.this, "Enigme" + sectors[sectors.length - (degree + 1)], Toast.LENGTH_SHORT).show();
               /* Intent enigme1 = new Intent(getApplicationContext(), Enigme9Activity.class);
                enigme1.setFlags(getIntent().getFlags());
                startActivity(enigme1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                */
                Intent enigme;
                switch (sectors[sectors.length - (degree + 1)]){
                    case "1" :
                        enigme = new Intent(getApplicationContext(), Enigme1Activity.class);
                        break;
                    case "2" :
                        enigme = new Intent(getApplicationContext(), Enigme2Activity.class);
                        break;
                    case "3" :
                        enigme = new Intent(getApplicationContext(), Enigme3Activity.class);
                        break;
                    case "4" :
                        enigme = new Intent(getApplicationContext(), Enigme4Activity.class);
                        break;
                    case "5" :
                        enigme = new Intent(getApplicationContext(), Enigme5Activity.class);
                        break;
                    case "6" :
                        enigme = new Intent(getApplicationContext(), Enigme6Activity.class);
                        break;
                    case "7" :
                        enigme = new Intent(getApplicationContext(), Enigme7Activity.class);
                        break;
                    case "8" :
                        enigme = new Intent(getApplicationContext(), Enigme8Activity.class);
                        break;
                    case "9" :
                        enigme = new Intent(getApplicationContext(), Enigme9Activity.class);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + sectors[sectors.length - (degree + 1)]);
                }
                enigme.setFlags(getIntent().getFlags());
                startActivity(enigme);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        wheel.startAnimation(rotateAnimation);
    }

    private void getDegreeForSectors() {

        int sectorDegree = 360 / sectors.length;

        for (int i = 0; i < sectors.length; i++) {
            sectorDegrees[i] = (i + 1) * sectorDegree;
        }
    }

    @Override
    public void onBackPressed() {
        setBackPopup();
    }

    public void setBackPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView_back = LayoutInflater.from(this).inflate(R.layout.custom_popup_back, viewGroup, false);
        builder.setView(dialogView_back);
        AlertDialog alertDialog = builder.create();


        Button quitter = dialogView_back.findViewById(R.id.button_quitter);
        Button repJeu = dialogView_back.findViewById((R.id.button_rep_jeu));
        LinearLayout popup_back = dialogView_back.findViewById((R.id.layout_popup_back));

        String imagePopup = "quitter_mj3_" + difficultyString;
        int resId = getResources().getIdentifier(imagePopup, "drawable", getPackageName());
        popup_back.setBackground(getDrawable(resId));

        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent difficulte;
                switch (getIntent().getFlags()) {
                    case 0:
                        difficulte = new Intent(getApplicationContext(), FacileActivity.class);
                        break;
                    case 1:
                        difficulte = new Intent(getApplicationContext(), MoyenActivity.class);
                        break;
                    case 2:
                        difficulte = new Intent(getApplicationContext(), DifficileActivity.class);
                        break;
                    default:
                        difficulte = new Intent(getApplicationContext(), DifficultyActivity.class);
                }
                startActivity(difficulte);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                alertDialog.dismiss();
                finish();
            }
        });

        repJeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_roulette.setColorFilter(Color.argb(0, 0, 0, 0));
                alertDialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}