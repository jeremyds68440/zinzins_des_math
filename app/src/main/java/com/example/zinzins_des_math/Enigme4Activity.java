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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Enigme4Activity extends AppCompatActivity {

    private Enigme4Activity enigmeActivity = this;
    private EditText inputAnswer;
    private Button submit_answer_btn;
    private Button explication;
    private TextView titre, question;
    private ImageView image;
    int cpt = 0;

    private String questByDifficulty;
    private String repByDifficulty;
    private String valueRepByDifficulty;
    private String expliByDifficulty = "";
    private int imgByDifficulty;
    public ConstraintLayout root;
    private ImageView back;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enigme1);
        root = findViewById(R.id.root);

        switch(getIntent().getFlags()){
            case 0 :
                questByDifficulty = "Dans un car pouvant accueillir 80 personnes, il y a 15 personnes installées. \n" +
                        "10 montent au premier arrêt.\n" +
                        "Au 2ème arrêt, il monte 9 personnes de moins qu'au 1er arrêt. Combien reste-t-il de places dans ce car? \n";
                repByDifficulty = "54 places";
                valueRepByDifficulty = "54";
                imgByDifficulty = R.drawable.img_enigme4_facile ;
                root.setBackground(getDrawable(R.drawable.enigme_bg_facile));
                break;
            case 1 :
                questByDifficulty = "Un père promet à son fils de lui offrir 5€ pour chaque bonne réponse mais le fiston devra lui donner 8€ à chaque mauvaise réponse.\n" +
                        "Au bout de 26 questions, le père et le fils ne se doivent rien. Combien le fils a-t-il donné de bonnes réponses ?\n";
                repByDifficulty = "16 bonnes réponses";
                valueRepByDifficulty = "16";
                expliByDifficulty = "Explication : Soit x le nombre de bonnes réponses et y le nombre de mauvaises réponses.\n" +
                        "Il y a 26 questions, donc : x + y = 26, soit y = 26 - x.\n" +
                        "Le père donne a son fils 5€ par bonne réponse. Il donne en tout 5x euros.\n" +
                        "Le fils lui rend 8€ par mauvaise réponse. Le fils donne en tout 8y euros.\n" +
                        "A la fin, le père et le fils ne se doivent rien, donc : 5x = 8y.\n" +
                        "En remplaçant y = 26 - x dans cette nouvelle équation, on obtient : 5x = 8(26 - x), soit :\n" +
                        "5x = 208 - 8x\n" +
                        "5x + 8x = 208\n" +
                        "13x = 208\n" +
                        "x = 208/13 = 16\n";
                imgByDifficulty = R.drawable.img_enigme_10;
                root.setBackground(getDrawable(R.drawable.enigme_bg_moyen));
                break;
            case 2 :
                questByDifficulty = "Une entreprise souhaite obtenir une estimation de la proportion de personnes de plus de 60 ans parmi ses clients, au niveau de confiance de 95%, avec un intervalle d’amplitude inférieure à 0, 05. Quel est le nombre minimum de clients à interroger ?";
                repByDifficulty = "1600 clients";
                valueRepByDifficulty = "1600";
                expliByDifficulty = "Explication : \n" +
                        "Un intervalle de confiance au niveau de confiance 95% est [ f − 1 /√n , f + 1/√n]  où f est la fréquence de personnes de plus de 60 ans observée dans l’échantillon et n est l’effectif de l’échantillon. L’amplitude de cet intervalle de confiance est 2/√n. \n" +
                        "2/√n <= 0, 05\n" +
                        "⇔ 2/√n <= 5/100 \n" +
                        "⇔ √n/2 >= 100/5 \n" +
                        "⇔ √n >= 40 \n" +
                        "⇔ n >= 1600\n";
                imgByDifficulty = R.drawable.defis4;
                root.setBackground(getDrawable(R.drawable.enigme_bg_difficile));
                break;
        }

        titre = findViewById(R.id.titre_enigme);
        question = findViewById(R.id.question);
        image = findViewById(R.id.figure_optionelle);
        inputAnswer = findViewById(R.id.answer_attempt);
        submit_answer_btn= findViewById(R.id.submit_answer_btn);
        explication = findViewById(R.id.explication);
        back = findViewById(R.id.back_enigme);


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
                            Toast.makeText(Enigme4Activity.this, "Il te reste " + (3-cpt) + " tentative(s)" , Toast.LENGTH_SHORT).show();
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
                    //score++;
                    cpt = 0 ;
                    AlertDialog.Builder success = new AlertDialog.Builder(enigmeActivity);
                    success.setTitle("Bravo !");
                    success.setMessage("Tu as trouvé la bonne réponse." );
                    success.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setBackButton();
                        }
                    });
                    success.setNegativeButton("Voir la solution", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            explication.setEnabled(true);
                            inputAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    });
                    success.show();
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackButton();
            }
        });

    }

    @Override
    public void onBackPressed() {
        setBackButton();
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

    public void setBackButton(){
        Intent intent;
        System.out.println(getIntent().getFlags());
        intent = new Intent(getApplicationContext(), RouletteActivity.class);
        intent.setFlags(getIntent().getFlags());
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
