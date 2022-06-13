package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MoyenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moyen);

        ImageView back = findViewById(R.id.back_to_moyen);
        ImageView multifactor = findViewById(R.id.multifactor_moyen);
        ImageView mathemaquizz = findViewById(R.id.mathemaquizz_moyen);
        ImageView roulette = findViewById(R.id.roulette_moyen);

        actionClickImageMoyen(back, DifficultyActivity.class,12);
        actionClickImageMoyen(multifactor, MultiFactorActivity.class,1);
        actionClickImageMoyen(mathemaquizz, SecondGameActivity.class,1);
        actionClickImageMoyen(roulette, RouletteActivity.class,1);

    }

    private void actionClickImageMoyen(ImageView button, Class act, int difficulty) {
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