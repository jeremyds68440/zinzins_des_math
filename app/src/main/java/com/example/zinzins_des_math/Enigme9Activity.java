package com.example.zinzins_des_math;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Enigme9Activity extends AppCompatActivity {

    private Enigme9Activity enigme9Activity = this;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enigme1);

        //Titre de l'énigme
        TextView titre = findViewById(R.id.titre_enigme);
        titre.setText("Enigme 9");

        //L'image s'il y en a une


        //Enoncé de l'énigme
        ((TextView) findViewById(R.id.question)).setText("Enoncé de l'énigme...");

        //Bouton submit
        Button submit_answer_btn = findViewById(R.id.submit_answer_btn);
        submit_answer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Enigme9Activity.this, "Réponse soumise", Toast.LENGTH_SHORT).show();

            }
        });

        //L'explication de la réponse
        ImageView explication = findViewById(R.id.explication);
        explication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder expli = new AlertDialog.Builder(enigme9Activity);
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
}
