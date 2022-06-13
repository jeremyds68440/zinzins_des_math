package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DifficultyActivity extends AppCompatActivity {

    private ImageView back, easy, medium, hard, IA;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
        this.mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.zinzin_sound);

        this.back = findViewById(R.id.back);
        this.easy = findViewById(R.id.facile);
        this.medium = findViewById(R.id.moyen);
        this.hard = findViewById(R.id.difficile);
        this.IA = findViewById(R.id.evolution);

        actionClickImage(this.back, choosesolomultiActivity.class);
        actionClickImage(this.easy, FacileActivity.class);
        actionClickImage(this.medium, MoyenActivity.class);
        actionClickImage(this.hard, DifficileActivity.class);
        actionClickImage(this.IA, LevelActivity.class);

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

    public void actionClickImage(ImageView button, Class act) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent intent = new Intent(getApplicationContext(), act);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), choosesolomultiActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}