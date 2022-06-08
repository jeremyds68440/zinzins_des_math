package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView jouer = findViewById(R.id.jouer);
        jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jouer.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent difficulty = new Intent(getApplicationContext(), DifficultyActivity.class);
                startActivity(difficulty);
                finish();
            }
        });

        ImageView Parameter = findViewById(R.id.parametres);

        Parameter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parameter.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent menuParametre = new Intent(getApplicationContext(), ParametreActivity.class);
                startActivity(menuParametre);
                finish();
            }
        });
    }

}