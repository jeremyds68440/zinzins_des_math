package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

public class LevelActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        ImageView back = findViewById(R.id.back_to_evolution);
        ImageView multifactor = findViewById(R.id.multifactor_evolution);
        ImageView mathemaquizz = findViewById(R.id.mathemaquizz_evolution);
        ImageView roulette = findViewById(R.id.roulette_evolution);

        actionClickImagefacile(back, DifficultyActivity.class,12);
        actionClickImagefacile(multifactor, MultiFactorActivity.class,3);
        actionClickImagefacile(mathemaquizz, MathemaQuizzActivity.class,3);
        actionClickImagefacile(roulette, RouletteActivity.class,3);

    }

    private void actionClickImagefacile(ImageView button, Class act, int difficulty) {
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
