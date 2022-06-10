package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import java.util.Random;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class RouletteActivity extends AppCompatActivity {

    private static String[] sectors = {"1", "2", "3", "4", "5", "6", "7","8","9"};
    private static final int[] sectorDegrees = new int[sectors.length];
    private static final Random random = new Random();
    private int degree = 0;
    private ImageView wheel;

    private Button test;

    private RouletteActivity activity;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);


        final ImageView startBtn = findViewById(R.id.startBtn);
        this.wheel = findViewById(R.id.wheel);

        getDegreeForSectors();

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spin();
            }
        });



    }

    private void spin() {
        degree = random.nextInt(sectors.length - 1);

        RotateAnimation rotateAnimation = new RotateAnimation(0, (360 * sectors.length) + sectorDegrees[degree], RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3600);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(RouletteActivity.this, "Enigme"+sectors[sectors.length - (degree + 1)], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        wheel.startAnimation(rotateAnimation);
    }

    private void getDegreeForSectors() {

        int sectorDegree = 360 / sectors.length;

        for (int i = 0; i < sectors.length; i++) {
            sectorDegrees[i] = (i + 1) * sectorDegree;
        }
    }
}