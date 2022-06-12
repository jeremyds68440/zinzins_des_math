package com.example.zinzins_des_math;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Enigme7Activity extends AppCompatActivity {

    private Enigme7Activity enigme7Activity = this;
    EditText inputAnswer;
    Button submit_answer_btn;
    TextView titre, enonce;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enigme1);

        //Titre de l'énigme
        titre = findViewById(R.id.titre_enigme);
        titre.setText("Enigme 7");

        //L'image s'il y en a une


        //Enoncé de l'énigme
        enonce = findViewById(R.id.question);
        enonce.setText("Enoncé de l'énigme...");

        //Réponse entrée par le joueur
        inputAnswer = findViewById(R.id.answer_attempt);

        submit_answer_btn= findViewById(R.id.submit_answer_btn);
        inputAnswer.addTextChangedListener(textWatcher);

        submit_answer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }

        });

        //L'explication de la réponse
        Button explication = findViewById(R.id.explication);
        explication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder expli = new AlertDialog.Builder(enigme7Activity);
                expli.setTitle("Explication");
                expli.setMessage("..." );
                expli.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                expli.show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent roulette = new Intent(getApplicationContext(), RouletteActivity.class);
        startActivity(roulette);
    }

    public void checkAnswer(){

    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String answer = inputAnswer.getText().toString();

            submit_answer_btn.setEnabled(!answer.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
