package com.example.zinzins_des_math;

import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Enigme1Activity extends AppCompatActivity {

    private Enigme1Activity enigmeActivity = this;
    private EditText inputAnswer;
    private Button submit_answer_btn;
    private Button explication;
    private TextView titre, question;
    private ImageView image;
    int cpt = 0;
    int score = 0;

    private String questByDifficulty;
    private String repByDifficulty;
    private String valueRepByDifficulty;
    private String expliByDifficulty = "";
    private int imgByDifficulty;
    public ConstraintLayout root;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enigme1);

        root = findViewById(R.id.root);

        switch(getIntent().getFlags()){

            case 0 :
                questByDifficulty = "Combien y a-t-il de triangles ?";
                repByDifficulty = "24 triangles";
                valueRepByDifficulty= "24";
                imgByDifficulty = R.drawable.img_enigme1_facile ;
                root.setBackground(getDrawable(R.drawable.enigme_bg_facile));
                break;
            case 1 :
                questByDifficulty = "On désire construire un château de cartes avec 340 paquets de 32 cartes.\n" +
                        "Combien d’étages peut-on construire avec ces 340 paquets ?\n";
                repByDifficulty = "85 étages";
                valueRepByDifficulty = "85";
                expliByDifficulty = "Explication : nombre de cartes nécessaires pour n étages est égal n(3n+1)/2. Trouver n revient à résoudre l’équation n(3n+1)/2 = 10880.";
                imgByDifficulty = R.drawable.img_enigme_1;
                root.setBackground(getDrawable(R.drawable.enigme_bg_moyen));
                break;
            case 2 :
                questByDifficulty = "";
                repByDifficulty = "";
                expliByDifficulty = "";
                //imgByDifficulty = ;
                root.setBackground(getDrawable(R.drawable.enigme_bg_difficile));
                break;
        }


        titre = findViewById(R.id.titre_enigme);
        question = findViewById(R.id.question);
        image = findViewById(R.id.figure_optionelle);
        inputAnswer = findViewById(R.id.answer_attempt);
        submit_answer_btn= findViewById(R.id.submit_answer_btn);
        explication = findViewById(R.id.explication);


        //Titre de l'énigme
        titre.setText("Enigme 1");

        //L'image
        image.setImageResource(imgByDifficulty);

        //Enoncé de l'énigme
        question.setText(questByDifficulty);

        //Réponse entrée par le joueur

        inputAnswer.addTextChangedListener(textWatcher);

        submit_answer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputAnswer.getText().toString();
                if (!input.equals(valueRepByDifficulty)){

                    if (cpt<4){
                        cpt++;
                        if (cpt<3){
                            Toast.makeText(Enigme1Activity.this, "Il te reste " + (3-cpt) + " tentative(s)" , Toast.LENGTH_SHORT).show();
                            Toast.makeText(Enigme1Activity.this, score , Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (cpt==3){
                        cpt=0;
                        //Toast.makeText(Enigme1Activity.this, "Veux-tu la réponse?" , Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder echec = new AlertDialog.Builder(enigmeActivity);
                        echec.setTitle("Oops !");
                        echec.setMessage("Tu as épuisé le nombre de tentatives. Tu peux retenter ta chance ou voir la solution." );
                        echec.setPositiveButton("Réessayer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        echec.setNegativeButton("Voir la solution", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                explication.setEnabled(true);
                                inputAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                            }
                        });
                        echec.show();
                    }

                } else {
                    cpt = 0 ;
                    AlertDialog.Builder sucess = new AlertDialog.Builder(enigmeActivity);
                    sucess.setTitle("Bravo !");
                    sucess.setMessage("Tu as trouvé la bonne réponse." );
                    sucess.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            explication.setEnabled(true);
                            inputAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    });
                    sucess.show();

                    if(cpt == 1) score += 3;
                    else if (cpt == 2) score += 2;
                    else if (cpt == 3) score += 1;

                }

                inputAnswer.setText("");
            }

        });

        //L'explication de la réponse
        explication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder expli = new AlertDialog.Builder(enigmeActivity);
                expli.setTitle("Réponse : "+ repByDifficulty);

                //Récupération de la réponse depuis la base de données
                expli.setMessage(expliByDifficulty);
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
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
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
