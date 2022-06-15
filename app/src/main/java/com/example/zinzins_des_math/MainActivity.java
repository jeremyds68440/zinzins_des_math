package com.example.zinzins_des_math;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference mDatabase;
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

    private ImageView jouer, settings,Logo_Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.jouer = findViewById(R.id.jouer);
        this.settings = findViewById(R.id.parametres);
        Logo_Profile = (ImageView) findViewById(R.id.Logo_profile);

        SplashScreenActivity.general_sound.start();

        fAuth = FirebaseAuth.getInstance();
        loadData();
        actionClickImage(this.jouer, ChooseSoloMultiActivity.class);
        if (fAuth.getCurrentUser() != null) {
            actionClickImage(this.settings, ParametreActivity.class);
        } else {
            actionClickImage(this.settings, parametreSansConnexionActivity.class);
        }

        if (fAuth.getCurrentUser() != null) {
            user = FirebaseAuth.getInstance().getCurrentUser();
            mDatabase = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final User utilisateur = new User();
                    final Field[] fields = utilisateur.getClass().getDeclaredFields();
                    int idAvatar = Math.toIntExact((Long) dataSnapshot.child(fields[14].getName()).getValue());
                    switch (idAvatar) {
                        case 0:
                            Logo_Profile.setImageDrawable(getDrawable(getResources().getIdentifier("logo_bleu_blanc", "drawable", getPackageName())));
                            break;
                        case 1:
                            Logo_Profile.setImageDrawable(getDrawable(getResources().getIdentifier("logo_orange_blanc", "drawable", getPackageName())));
                            break;
                        case 2:
                            Logo_Profile.setImageDrawable(getDrawable(getResources().getIdentifier("logo_rouge_blanc", "drawable", getPackageName())));
                            break;
                        case 3:
                            Logo_Profile.setImageDrawable(getDrawable(getResources().getIdentifier("logo_vert_blanc", "drawable", getPackageName())));
                            break;
                        case 4:
                            Logo_Profile.setImageDrawable(getDrawable(getResources().getIdentifier("logo_violet_blanc", "drawable", getPackageName())));
                            break;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            Logo_Profile.setImageDrawable(getDrawable(getResources().getIdentifier("logo_vert_blanc", "drawable", getPackageName())));
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

    public void onBackPressed() {
    }
}