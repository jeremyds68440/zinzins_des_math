package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DifficultyActivity extends AppCompatActivity {

    ImageView back,easy,medium,hard,IA;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.zinzin_sound);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        easy = findViewById(R.id.facile);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easy.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent Facile = new Intent(getApplicationContext(), FirstGameActivity.class);
                startActivity(Facile);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        medium = findViewById(R.id.moyen);

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medium.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent moyen = new Intent(getApplicationContext(), LevelActivity.class);
                startActivity(moyen);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        hard = findViewById(R.id.difficile);

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hard.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent difficile = new Intent(getApplicationContext(), LevelActivity.class);
                startActivity(difficile);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        IA = findViewById(R.id.evolution);

        IA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IA.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent evolution = new Intent(getApplicationContext(), LevelActivity.class);
                startActivity(evolution);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();

    }
}