package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MultiplayerActivity extends AppCompatActivity {

    ImageView back, multifactor, mathemaQuizz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        back = findViewById(R.id.back_menu);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent main = new Intent(getApplicationContext(), choosesolomultiActivity.class);
                startActivity(main);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        multifactor = findViewById(R.id.multifactor);
        multifactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multifactor.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent multifactorView = new Intent(getApplicationContext(), MultiFactorActivity.class);
                startActivity(multifactorView);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        mathemaQuizz = findViewById(R.id.mathemaquizz);
        mathemaQuizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mathemaQuizz.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent mathemaQuizzView = new Intent(getApplicationContext(), SecondGameActivity.class);
                startActivity(mathemaQuizzView);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }
}