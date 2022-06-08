package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Layout;

public class FirstGameActivity extends AppCompatActivity {

    ConstraintLayout activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_game);

        activity = (ConstraintLayout) findViewById(R.id.firstGame);


    }
}