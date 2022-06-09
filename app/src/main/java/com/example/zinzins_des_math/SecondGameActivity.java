package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SecondGameActivity extends AppCompatActivity {

    ImageView btn_ans0,btn_ans1,btn_ans2,btn_ans3;

    TextView equation,timer,score,soluaff,btn_ans0_txtv,btn_ans1_txtv,btn_ans2_txtv,btn_ans3_txtv;
    ProgressBar progresstimer;
    private SecondGameActivity secondGameActivity;

    Game g = new Game();
    int secondsRemaining = 30;
    CountDownTimer temps = new CountDownTimer(30000,1000) {
        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            secondsRemaining--;
            timer.setText(Integer.toString(secondsRemaining));
            progresstimer.setProgress(30 - secondsRemaining);
        }

        @Override
        public void onFinish() {
            AlertDialog.Builder fini = new AlertDialog.Builder(secondGameActivity);
            fini.setTitle("Bien joué");
            fini.setMessage("Vous avez fait : " + score.getText() + " pts" );
            fini.setPositiveButton("Réssayer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    secondsRemaining =30;
                    g = new Game();
                    nextTurn();
                    temps.start();
                }
            });
            fini.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent difficulte = new Intent(getApplicationContext(), DifficultyActivity.class);
                    startActivity(difficulte);
                    finish();
                }
            });

            fini.setCancelable(false);
            fini.show();
            g.setNumberCorrect(0);
            g.setNumberIncorrect(0);
            g.setScore(0);
            score.setText(Integer.toString(g.getScore()));
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_game);
        temps.start();

        this.secondGameActivity = this;
        btn_ans0 = findViewById(R.id.btn_ans0);
        btn_ans1 = findViewById(R.id.btn_ans1);
        btn_ans2 = findViewById(R.id.btn_ans2);
        btn_ans3 = findViewById(R.id.btn_ans3);

        btn_ans0_txtv = findViewById(R.id.btn_ans0_txtv);
        btn_ans1_txtv = findViewById(R.id.btn_ans1_txtv);
        btn_ans2_txtv = findViewById(R.id.btn_ans2_txtv);
        btn_ans3_txtv = findViewById(R.id.btn_ans3_txtv);

        progresstimer = findViewById(R.id.progressBar);
        timer = findViewById(R.id.timer);
        equation = findViewById(R.id.equation);
        score = findViewById(R.id.score);
        soluaff = findViewById(R.id.answersolus);

        timer.setText("0sec");
        equation.setText("");
        nextTurn();

        btn_ans0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.checkAnswer(Integer.parseInt(btn_ans0_txtv.getText().toString()));
                score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        });

        btn_ans1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                g.checkAnswer(Integer.parseInt(btn_ans1_txtv.getText().toString()));
                score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        });

        btn_ans2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                g.checkAnswer(Integer.parseInt(btn_ans2_txtv.getText().toString()));
                score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        });

        btn_ans3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                g.checkAnswer(Integer.parseInt(btn_ans3_txtv.getText().toString()));
                score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        });



        btn_ans0_txtv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                g.checkAnswer(Integer.parseInt(btn_ans0_txtv.getText().toString()));
                score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        });

        btn_ans1_txtv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                g.checkAnswer(Integer.parseInt(btn_ans1_txtv.getText().toString()));
                score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        });

        btn_ans2_txtv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                g.checkAnswer(Integer.parseInt(btn_ans2_txtv.getText().toString()));
                score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        });

        btn_ans3_txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.checkAnswer(Integer.parseInt(btn_ans3_txtv.getText().toString()));
                score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        });

        ImageView back = findViewById(R.id.second_game_back2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent main = new Intent(getApplicationContext(), DifficultyActivity.class);
                startActivity(main);
                finish();
            }
        });
    }

    private void nextTurn(){

        g.newEquation();
        int [] answer = g.getCurrentEquation().getAnswerArray();
        btn_ans0_txtv.setText(Integer.toString(answer[0]));
        btn_ans1_txtv.setText(Integer.toString(answer[1]));
        btn_ans2_txtv.setText(Integer.toString(answer[2]));
        btn_ans3_txtv.setText(Integer.toString(answer[3]));


        equation.setText(g.getCurrentEquation().getEquationPhrase());
        soluaff.setText(g.getNumberCorrect() + "/" + (g.getTotalEquations()-1));
        if(g.getScore() < 0){
            g.setNumberIncorrect(0);
            g.setNumberCorrect(0);
            g.setScore(0);
            score.setText(Integer.toString(g.getScore()));
        }
    }

}