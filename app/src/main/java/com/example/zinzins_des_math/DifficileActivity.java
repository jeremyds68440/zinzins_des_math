package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DifficileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficile);

        ImageView back = findViewById(R.id.back_to_difficile);
        ImageView multifactor = findViewById(R.id.multifactor_difficile);
        ImageView mathemaquizz = findViewById(R.id.mathemaquizz_difficile);
        ImageView roulette = findViewById(R.id.roulette_difficile);

        actionClickImageDifficile(back, DifficultyActivity.class,12);
        actionClickImageDifficile(multifactor, MultiFactorActivity.class,2);
        actionClickImageDifficile(mathemaquizz, SecondGameActivity.class,2);
        actionClickImageDifficile(roulette, RouletteActivity.class,2);

    }

    private void actionClickImageDifficile(ImageView button, Class act, int difficulty) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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