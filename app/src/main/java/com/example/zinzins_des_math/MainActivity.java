package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView jouer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jouer = findViewById(R.id.jouer);

        jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent difficulty = new Intent(getApplicationContext(), DifficultyActivity.class);
                startActivity(difficulty);
                finish();
            }
        });
    }
}