package com.example.zinzins_des_math;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Enigme8Activity extends AppCompatActivity {

    private Enigme8Activity enigme8Activity = this;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enigme1);

        //Titre de l'énigme
        TextView titre = (TextView) findViewById(R.id.titre_enigme);
        titre.setText("Enigme 8");

        //L'image s'il y en a une


        //Enoncé de l'énigme
        ((TextView) findViewById(R.id.question)).setText("Enoncé de l'énigme...");

        //Les trois choix
        final Button choix1 = (Button) findViewById(R.id.choix1);
        choix1.setText("Choix 1");
        final Button choix2 = (Button) findViewById(R.id.choix2);
        choix2.setText("Choix 2");
        final Button choix3 = (Button) findViewById(R.id.choix3);
        choix3.setText("Choix 3");

        //L'explication de la réponse
        ImageView explication = (ImageView) findViewById(R.id.explication);
        explication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder expli = new AlertDialog.Builder(enigme8Activity);
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
}
