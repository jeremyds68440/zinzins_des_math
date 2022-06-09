package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SecondGameActivity extends AppCompatActivity {

    Button btn_ans0,btn_ans1,btn_ans2,btn_ans3;
    TextView equation,timer,score,soluaff;
    ProgressBar progresstimer;

    Game g = new Game();
    int secondsRemaining = 30;
    CountDownTimer temps = new CountDownTimer(30000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            secondsRemaining--;
            timer.setText(Integer.toString(secondsRemaining));
            progresstimer.setProgress(30 - secondsRemaining);
        }

        @Override
        public void onFinish() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_game);
        temps.start();
        btn_ans0 = findViewById(R.id.btn_ans0);
        btn_ans1 = findViewById(R.id.btn_ans1);
        btn_ans2 = findViewById(R.id.btn_ans2);
        btn_ans3 = findViewById(R.id.btn_ans3);

        progresstimer = findViewById(R.id.progressBar);
        timer = findViewById(R.id.timer);
        equation = findViewById(R.id.equation);
        score = findViewById(R.id.score);
        soluaff = findViewById(R.id.answersolus);

        timer.setText("0sec");
        equation.setText("");
        nextTurn();

        View.OnClickListener answerButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button) v;
                int answerselec = Integer.parseInt(buttonClicked.getText().toString());

                g.checkAnswer(answerselec);
                score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        };

        btn_ans0.setOnClickListener(answerButtonClickListener);
        btn_ans1.setOnClickListener(answerButtonClickListener);
        btn_ans2.setOnClickListener(answerButtonClickListener);
        btn_ans3.setOnClickListener(answerButtonClickListener);

    }

    private void nextTurn(){

        g.newEquation();
        int [] answer = g.getCurrentEquation().getAnswerArray();
        btn_ans0.setText(Integer.toString(answer[0]));
        btn_ans1.setText(Integer.toString(answer[1]));
        btn_ans2.setText(Integer.toString(answer[2]));
        btn_ans3.setText(Integer.toString(answer[3]));

        btn_ans0.setEnabled(true);
        btn_ans1.setEnabled(true);
        btn_ans2.setEnabled(true);
        btn_ans3.setEnabled(true);

        equation.setText(g.getCurrentEquation().getEquationPhrase());
        soluaff.setText(g.getNumberCorrect() + "/" + (g.getTotalEquations()-1));

    }
}