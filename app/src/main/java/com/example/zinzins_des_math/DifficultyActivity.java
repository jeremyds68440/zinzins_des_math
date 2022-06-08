package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DifficultyActivity extends AppCompatActivity {

    ImageView back,easy,medium,hard,IA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
                finish();
            }
        });

        easy = findViewById(R.id.facile);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Facile = new Intent(getApplicationContext(), LevelActivity.class);
                startActivity(Facile);
                finish();
            }
        });

        medium = findViewById(R.id.moyen);

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moyen = new Intent(getApplicationContext(), LevelActivity.class);
                startActivity(moyen);
                finish();
            }
        });

        hard = findViewById(R.id.difficile);

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent difficile = new Intent(getApplicationContext(), LevelActivity.class);
                startActivity(difficile);
                finish();
            }
        });

        IA = findViewById(R.id.evolution);

        IA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent evolution  = new Intent(getApplicationContext(), LevelActivity.class);
                startActivity(evolution);
                finish();
            }
        });
    }
}