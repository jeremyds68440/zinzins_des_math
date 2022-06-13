package com.example.zinzins_des_math;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;

public class MathemaQuizzActivity extends AppCompatActivity {

    Button btn_ans0,btn_ans1,btn_ans2,btn_ans3;

    TextView equation,timer,score,soluaff;
    ProgressBar progresstimer;
    ConstraintLayout bg;
    ImageView nuage_equation;
    private MathemaQuizzActivity mathemaQuizzActivity;

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
            AlertDialog.Builder fini = new AlertDialog.Builder(mathemaQuizzActivity);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference mDatabase = database.getReference("users").child(user.getUid());
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int dbScore;
                    final User utilisateur = new User();
                    final Field[] fields = utilisateur.getClass().getDeclaredFields();
                    switch (getIntent().getFlags()) {
                        case 0 :
                            dbScore = Math.toIntExact((long) dataSnapshot.child(fields[2].getName()).getValue());
                            if (dbScore < Integer.parseInt((String) score.getText()))
                                mDatabase.child("scoreMathemaquizzFacile").setValue(Integer.parseInt((String) score.getText()));
                            break;
                        case 1 :
                            dbScore = Math.toIntExact((long) dataSnapshot.child(fields[3].getName()).getValue());
                            if (dbScore < Integer.parseInt((String) score.getText()))
                                mDatabase.child("scoreMathemaquizzMoyen").setValue(Integer.parseInt((String) score.getText()));
                            break;
                        case 2 :
                            dbScore = Math.toIntExact((long) dataSnapshot.child(fields[1].getName()).getValue());
                            if (dbScore < Integer.parseInt((String) score.getText()))
                                mDatabase.child("scoreMathemaquizzDifficile").setValue(Integer.parseInt((String) score.getText()));
                            break;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            fini.setTitle("Bien joué");
            fini.setMessage("Vous avez fait : " + score.getText() + " pts" );
            fini.setPositiveButton("Réssayer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    secondsRemaining =30;
                    g = new Game();
                    nextTurn();
                    temps.start();
                    g.setNumberCorrect(0);
                    g.setNumberIncorrect(0);
                    score.setText(Integer.toString(g.getScore()));
                }
            });
            fini.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent difficulte = new Intent(getApplicationContext(), DifficultyActivity.class);
                    startActivity(difficulte);
                }
            });

            fini.setCancelable(false);
            fini.show();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_game);
        temps.start();

        this.mathemaQuizzActivity = this;
        btn_ans0 = findViewById(R.id.btn_ans0);
        btn_ans1 = findViewById(R.id.btn_ans1);
        btn_ans2 = findViewById(R.id.btn_ans2);
        btn_ans3 = findViewById(R.id.btn_ans3);

        progresstimer = findViewById(R.id.progressBar);
        timer = findViewById(R.id.timer);
        equation = findViewById(R.id.equation);
        score = findViewById(R.id.score);
        soluaff = findViewById(R.id.answersolus);
        bg = findViewById(R.id.bg_math_quizz);
        nuage_equation = findViewById(R.id.nuage_enigme);

        timer.setText("0sec");
        equation.setText("");
        nextTurn();

        btn_ans0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.checkAnswer(Integer.parseInt(btn_ans0.getText().toString()));
                score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        });

        btn_ans1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                g.checkAnswer(Integer.parseInt(btn_ans1.getText().toString()));
                score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        });

        btn_ans2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                g.checkAnswer(Integer.parseInt(btn_ans2.getText().toString()));
                score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        });

        btn_ans3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                g.checkAnswer(Integer.parseInt(btn_ans3.getText().toString()));
                score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        });

        ImageView back = findViewById(R.id.second_game_back2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setColorFilter(Color.argb(80, 0, 0, 0));
                setQuitPopup();
            }
        });
    }

    public void onBackPressed() {
        setQuitPopup();
    }

    private void setQuitPopup(){
        AlertDialog.Builder quit = new AlertDialog.Builder(this);
        quit.setTitle("Quitter");
        quit.setMessage("Êtes-vous sûre de vouloir quitter ?");
        quit.setNegativeButton("Retour", null);
        quit.setPositiveButton("Quitter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (getIntent().getFlags()){
                    case 0 :
                        Intent main = new Intent(getApplicationContext(), FacileActivity.class);
                        startActivity(main);
                        break;
                    case 1 :
                        main = new Intent(getApplicationContext(), MoyenActivity.class);
                        startActivity(main);
                        break;
                    case 2 :
                        main = new Intent(getApplicationContext(), DifficileActivity.class);
                        startActivity(main);
                        break;

                }
                finish();
            }
        });
        quit.show();
    }
    private void nextTurn(){

        int difficulty = getIntent().getFlags();
        if(difficulty == 0){
            bg.setBackground(getDrawable(R.drawable.bg_quizz_facile));
            g.newEquationFacile();
        }else if(difficulty == 1){
            bg.setBackground(getDrawable(R.drawable.bg_quizz_moyen));
            nuage_equation.setImageDrawable(getDrawable(R.drawable.grosnuage_quizz_moyen));
            equation.setTextColor(getColor(R.color.white));
            g.newEquationMoyen();
        }else if(difficulty == 2){

            this.setTheme(android.R.style.Theme);
            bg.setBackground(getDrawable(R.drawable.bg_quizz_difficile));
            nuage_equation.setImageDrawable(getDrawable(R.drawable.quizz_grosnuage_difficile));
            equation.setTextColor(getColor(R.color.white));
            g.newEquationDifficile();
        }

        int [] answer = g.getCurrentEquation().getAnswerArray();
        btn_ans0.setText(Integer.toString(answer[0]));
        btn_ans1.setText(Integer.toString(answer[1]));
        btn_ans2.setText(Integer.toString(answer[2]));
        btn_ans3.setText(Integer.toString(answer[3]));


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