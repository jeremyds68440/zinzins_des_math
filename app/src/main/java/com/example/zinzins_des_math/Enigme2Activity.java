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

public class Enigme2Activity extends AppCompatActivity {

    private Enigme2Activity enigmeActivity = this;
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
                questByDifficulty = "Pyramide mathématique";
                repByDifficulty = "8";
                valueRepByDifficulty = "8";
                imgByDifficulty = R.drawable.img_enigme2_facile ;
                difficultyString = "facile";
                root.setBackground(getDrawable(R.drawable.enigme_bg_facile));
                break;
            case 1 :
                questByDifficulty = "Un cube a des arêtes de 5cm. On perfore ce cube de part en part : chaque trou a la forme d’un parallélépipède rectangle dont la section est un carré de 1cm de côté. Les douze trous ainsi formés sont disposés « régulièrement » comme l’indique la figure ci-contre. Quel est le volume total du cube ainsi perforé en cm3 .";
                repByDifficulty = "81 cm3";
                valueRepByDifficulty = "81";
                expliByDifficulty = "Explication : 1er étage = 3e étage = 5e étage = 21 cm3\n" +
                        "2e étage = 4e étage = 9cm3\n" +
                        "Le tout = 3 x 21 + 2 x 9 = 81 cm3\n";
                imgByDifficulty = R.drawable.img_enigme_2;
                difficultyString = "moyen";
                root.setBackground(getDrawable(R.drawable.enigme_bg_moyen));
                break;
            case 2 :
                questByDifficulty = "Gaston a récolté ses pastèques. Elles sont bien juteuses et constituées de 91% d'eau. Il les stocke dans un hangar durant deux jours. Lorsqu'il souhaite les vendre , il s'aperçoit que ses pastèques ne contiennent plus que 90% d'eau pour un poids total de 1814,4 kg. Quel était le poids des pastèques avant stockage ?";
                repByDifficulty = "2016 kg";
                valueRepByDifficulty = "2016";
                expliByDifficulty = "Explication : Deux jours après la récolte , les pastèques sont constituées de 90% d'eau pour un poids total de 1814,4 kg. Cela correspond à 10% de matière sèche , soit : 10% * 1814,4 = 181,44 kg Le jour de la récolte , la quantité de matière sèche était aussi de 181,44 kg. Or , à ce moment-là , les pastèques contiennent 91% d'eau : 181,44 * 100/9 = 2016 kg.";
                imgByDifficulty = R.drawable.defis2;
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
                            Toast.makeText(Enigme2Activity.this, "Il te reste " + (3-cpt) + " tentative(s)" , Toast.LENGTH_SHORT).show();
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
