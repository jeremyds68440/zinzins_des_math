package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView jouer, Parameter, multiplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jouer = findViewById(R.id.jouer);
        jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jouer.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent difficulty = new Intent(getApplicationContext(), DifficultyActivity.class);
                startActivity(difficulty);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        multiplayer = findViewById(R.id.multiplayer);
        multiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiplayer.setColorFilter(Color.argb(80, 0, 0, 0));
                //Intent multiPlayerActivity = new Intent(getApplicationContext(), MultiplayerActivity.class);
                //startActivity(multiPlayerActivity);
                Intent auth = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(auth);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });


        Parameter = findViewById(R.id.parametres);
        Parameter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parameter.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent menuParametre = new Intent(getApplicationContext(), ParametreActivity.class);
                startActivity(menuParametre);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }

}