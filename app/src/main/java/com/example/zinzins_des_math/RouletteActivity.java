package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import java.util.Random;


public class RouletteActivity extends AppCompatActivity {

    private static String[] sectors = {"1", "2", "3", "4", "5", "6", "7","8","9"};
    private static final int[] sectorDegrees = new int[sectors.length];
    private static final Random random = new Random();
    private int degree = 0;
    private ImageView wheel;
    private RouletteActivity activity;
    int cpt1 = 0;
    int cpt2 = 0;

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
                //Toast.makeText(RouletteActivity.this, "Enigme" + sectors[sectors.length - (degree + 1)], Toast.LENGTH_SHORT).show();
                switch (sectors[sectors.length - (degree + 1)]){
                    case "1" :
                        Intent enigme1 = new Intent(getApplicationContext(), Enigme1Activity.class);
                        startActivity(enigme1);
                        finish();
                        break;
                    case "2" :
                        Intent enigme2 = new Intent(getApplicationContext(), Enigme2Activity.class);
                        startActivity(enigme2);
                        finish();
                        break;
                    case "3" :
                        Intent enigme3 = new Intent(getApplicationContext(), Enigme3Activity.class);
                        startActivity(enigme3);
                        finish();
                        break;
                    case "4" :
                        Intent enigme4 = new Intent(getApplicationContext(), Enigme4Activity.class);
                        startActivity(enigme4);
                        finish();
                        break;
                    case "5" :
                        Intent enigme5 = new Intent(getApplicationContext(), Enigme5Activity.class);
                        startActivity(enigme5);
                        finish();
                        break;
                    case "6" :
                        Intent enigme6 = new Intent(getApplicationContext(), Enigme6Activity.class);
                        startActivity(enigme6);
                        finish();break;
                    case "7" :
                        Intent enigme7 = new Intent(getApplicationContext(), Enigme7Activity.class);
                        startActivity(enigme7);
                        finish();
                        break;
                    case "8" :
                        Intent enigme8 = new Intent(getApplicationContext(), Enigme8Activity.class);
                        startActivity(enigme8);
                        finish();
                        break;
                    case "9" :
                        Intent enigme9 = new Intent(getApplicationContext(), Enigme9Activity.class);
                        startActivity(enigme9);
                        finish();
                        break;
                }


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