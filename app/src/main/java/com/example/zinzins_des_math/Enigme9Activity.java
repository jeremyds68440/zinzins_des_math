package com.example.zinzins_des_math;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Enigme9Activity extends AppCompatActivity {

    private Enigme9Activity enigmeActivity = this;
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
    private String difficultyString;
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
                questByDifficulty = "Cinq machines sont capables de rédiger cinq articles en cinq minutes. Avec ce rythme de production, en combien de temps 100 machines peuvent-elles écrire 100 articles ?";
                repByDifficulty = "5 minutes";
                valueRepByDifficulty = "5";
                imgByDifficulty = R.drawable.img_enigme9_facile ;
                difficultyString = "facile";
                root.setBackground(getDrawable(R.drawable.enigme_bg_facile));

                break;
            case 1 :
                questByDifficulty = "En se rendant à un point d’eau, un zèbre croise 6 girafes qui s’y rendaient également. Chaque girafe portait sur son dos 3 singes. Chaque singe portait 2 oiseaux qui eux-mêmes portaient chacun 4 mouches.\n" +
                        "Combien d’animaux au total se retrouvent au point d’eau ? ";
                repByDifficulty = "205 animaux";
                valueRepByDifficulty = "205";
                expliByDifficulty = "Explication : 1 zèbre + 6 girafes + 18 (3x6) singes + 36 (2x18) oiseaux + 144 (4x36) mouches";
                imgByDifficulty = R.drawable.img_enigme_9;
                difficultyString = "moyen";
                root.setBackground(getDrawable(R.drawable.enigme_bg_moyen));
                break;
            case 2 :
                questByDifficulty = "On multiplie tous les entiers de 1 jusqu’à 176 800. Puis on additionne tous les chiffres qui composent le résultat obtenu. On additionne ensuite tous les chiffres qui composent ce nouveau résultat. On répète l’opération plusieurs fois. Au bout d’un certain \"temps\", on tombe sur un résultat inférieur à 16. Lequel ?";
                repByDifficulty = "9";
                valueRepByDifficulty = "9";
                expliByDifficulty = "Explication : Il suffit de multiplier les premiers entiers (pas la peine d'aller jusqu'à 176 800 !) et d'effectuer l'algorithme proposé. Quel que soit le nombre de facteurs pris au départ, le résultat reste toujours le même, 9 !";
                imgByDifficulty = R.drawable.defis9;
                difficultyString = "difficile";
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
                            Toast.makeText(Enigme9Activity.this, "Il te reste " + (3-cpt) + " tentative(s)" , Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (cpt==3){
                        cpt=0;
                        //Toast.makeText(Enigme1Activity.this, "Veux-tu la réponse?" , Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder echec = new AlertDialog.Builder(enigmeActivity, R.style.MyDialogTheme);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(enigmeActivity).inflate(R.layout.custom_popup_back, viewGroup, false);
                        echec.setView(dialogView);
                        AlertDialog alertDialog = echec.create();

                        Button reponse = dialogView.findViewById(R.id.button_quitter);
                        Button reprendre = dialogView.findViewById((R.id.button_rep_jeu));
                        LinearLayout popup_back = dialogView.findViewById((R.id.layout_popup_back));

                        String imagePopup = "perdu_mj3_" + difficultyString;
                        int resId = getResources().getIdentifier(imagePopup, "drawable", getPackageName());
                        popup_back.setBackground(getDrawable(resId));

                        reprendre.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });

                        reponse.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                explication.setEnabled(true);
                                inputAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                } else {
                    //score++;
                    cpt = 0 ;
                    AlertDialog.Builder success = new AlertDialog.Builder(enigmeActivity, R.style.MyDialogTheme);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(enigmeActivity).inflate(R.layout.custom_popup_mj3_victoire, viewGroup, false);
                    success.setView(dialogView);
                    AlertDialog alertDialog = success.create();

                    Button quitter = dialogView.findViewById(R.id.button_quitter_mj3);
                    LinearLayout popup_back = dialogView.findViewById((R.id.layout_popup_victoire_mj3));

                    String imagePopup = "victoire_mj3_" + difficultyString;
                    int resId = getResources().getIdentifier(imagePopup, "drawable", getPackageName());
                    popup_back.setBackground(getDrawable(resId));

                    quitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), RouletteActivity.class);
                            intent.setFlags(getIntent().getFlags());
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            alertDialog.dismiss();
                            finish();
                        }
                    });
                    alertDialog.show();

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
        AlertDialog.Builder retour = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_popup_back, viewGroup, false);
        retour.setView(dialogView);
        AlertDialog alertDialog = retour.create();

        Button quitter = dialogView.findViewById(R.id.button_quitter);
        Button reprendre = dialogView.findViewById((R.id.button_rep_jeu));
        LinearLayout popup_back = dialogView.findViewById((R.id.layout_popup_back));

        String imagePopup = "quitter_mj3_" + difficultyString;
        int resId = getResources().getIdentifier(imagePopup, "drawable", getPackageName());
        popup_back.setBackground(getDrawable(resId));

        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RouletteActivity.class);
                intent.setFlags(getIntent().getFlags());
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                alertDialog.dismiss();
                finish();
            }
        });

        reprendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                back.setColorFilter(Color.argb(0, 0, 0, 0));
            }
        });
        alertDialog.show();
    }
}