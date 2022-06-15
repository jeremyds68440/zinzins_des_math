package com.example.zinzins_des_math;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;

public class ParametreActivity extends AppCompatActivity {

    TextView username, scoreMathemaquizzFacile, mail;
    Switch switch_sound_theme;
    Switch switch_sound_effect;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ETAT_SOUND_THEME = "etat_sound_theme";
    public static final String ETAT_SOUND_EFFECT = "etat_sound_effect";

    private boolean sound_theme_state;
    private boolean sound_effect_state;

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ETAT_SOUND_THEME, switch_sound_theme.isChecked());
        editor.putBoolean(ETAT_SOUND_EFFECT, switch_sound_effect.isChecked());

        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sound_theme_state = sharedPreferences.getBoolean(ETAT_SOUND_THEME, true);
        sound_effect_state = sharedPreferences.getBoolean(ETAT_SOUND_EFFECT, true);

    }

    public void updateViews(){
        switch_sound_theme.setChecked(sound_theme_state);
        switch_sound_effect.setChecked(sound_effect_state);

    }

    FirebaseUser user;
    FirebaseUser email;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);
        switch_sound_theme = (Switch) findViewById(R.id.Sound_etat);
        switch_sound_effect = (Switch) findViewById(R.id.effect_etat);
        username = (TextView) findViewById(R.id.nomUtil);
        scoreMathemaquizzFacile = (TextView) findViewById(R.id.scoreMathemaquizz);
        mail = (TextView) findViewById(R.id.mailUtil);


        loadData();
        updateViews();

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final User utilisateur = new User();
                final Field[] fields = utilisateur.getClass().getDeclaredFields();
                username.setText((String) dataSnapshot.child(fields[10].getName()).getValue());
                scoreMathemaquizzFacile.setText(Long.toString((Long) dataSnapshot.child(fields[2].getName()).getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        switch_sound_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        switch_sound_effect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });


    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

}