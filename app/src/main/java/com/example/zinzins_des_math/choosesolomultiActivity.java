package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class choosesolomultiActivity extends AppCompatActivity {

    ImageView Back, multi, solo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_solo_or_multi);

        Back = findViewById(R.id.choix_mode_de_jeu_back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                multi.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent multiPlayerActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(multiPlayerActivity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }
}