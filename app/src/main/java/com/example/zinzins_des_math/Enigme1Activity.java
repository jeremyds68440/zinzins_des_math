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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Enigme1Activity extends AppCompatActivity {

    private Enigme1Activity enigme1Activity = this;
    private EditText inputAnswer;
    private Button submit_answer_btn;
    private Button explication;
    private TextView titre, question;
    private ImageView image;
    int cpt = 0;

    //private DatabaseReference databaseRef1;
    //private DatabaseReference databaseRef2;
    //private DatabaseReference databaseRef3;




    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enigme1);

        titre = findViewById(R.id.titre_enigme);
        question = findViewById(R.id.question);
        image = findViewById(R.id.figure_optionelle);
        inputAnswer = findViewById(R.id.answer_attempt);
        submit_answer_btn= findViewById(R.id.submit_answer_btn);
        explication = findViewById(R.id.explication);


/*
        //Récupération de l'énigme depuis la base de données
        databaseRef1 = FireBaseDatabase.getInstance().getReference().child("enigmeQuestion").orderByChild("id").equalTo("1");
        databaseRef1.addValueEventListener(new ValueEventListener(){
            @Override
            public  void  onDataChange(@NonNull DataSnapshot dataSnapshot){
                if (dataSnapshot.exists()){
                    String data = dataSnapshot.getValue.toString();
                    question.setText(data);
                }
            }
            @Override
            public  void  onCancelled(@NonNull DatabaseError databaseError){

            }
        });

        //Récupération de l'image depuis la base de données
        databaseRef2 = FireBaseDatabase.getInstance().getReference().child("enigmaImage").orderByChild("id").equalTo("1");
        databaseRef2.addValueEventListener(new ValueEventListener(){
            @Override
            public  void  onDataChange(@NonNull DataSnapshot dataSnapshot){
                if (dataSnapshot.exists()) {
                    String data = dataSnapshot.getValue.toString();
                    image.setImageResource(R.drawable.data);
                }
            }
            @Override
            public  void  onCancelled(@NonNull DatabaseError databaseError){

            }
        });



*/
        //Titre de l'énigme
        titre.setText("Enigme 1");

        //L'image s'il y en a une


        //Enoncé de l'énigme
        question.setText("Enoncé de l'énigme...");

        //Réponse entrée par le joueur

        inputAnswer.addTextChangedListener(textWatcher);

        submit_answer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputAnswer.getText().toString();
                //if (!checkAnswer(input)){

                    if (cpt<4){
                        cpt++;
                        if (cpt<3){
                            Toast.makeText(Enigme1Activity.this, "Il te reste " + (3-cpt) + " tentative(s)" , Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (cpt==3){
                        cpt=0;
                        //Toast.makeText(Enigme1Activity.this, "Veux-tu la réponse?" , Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder echec = new AlertDialog.Builder(enigme1Activity);
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

                /*
                } else {
                    score++;
                    AlertDialog.Builder sucess = new AlertDialog.Builder(enigme1Activity);
                    sucess.setTitle("Bravo !");
                    sucess.setMessage("Tu as trouvé la bonne réponse." );
                    sucess.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent roulette = new Intent(getApplicationContext(), RouletteActivity.class);
                            startActivity(roulette);                        }
                    });
                    sucess.show();

                 }*/


                inputAnswer.setText("");
            }

        });

        //L'explication de la réponse
        explication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder expli = new AlertDialog.Builder(enigme1Activity);
                expli.setTitle("Solution");

                //Récupération de la réponse depuis la base de données
                /*
                databaseRef3 = FireBaseDatabase.getInstance().getReference().child("enigmeReponse").orderByChild("id").equalTo("1");
                databaseRef3.addValueEventListener(new ValueEventListener(){
                    @Override
                    public  void  onDataChange(@NonNull DataSnapshot dataSnapshot){
                        if (dataSnapshot.exists()) {
                            String data = dataSnapshot.getValue.toString();
                            expli.setMessage(data);
                        }
                    }
                    @Override
                    public  void  onCancelled(@NonNull DatabaseError databaseError){

                    }
                });*/
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

    /*
    public boolean checkAnswer(String s){

        String reponseData;
        //Récupération de la réponse depuis la base de données
        databaseRef1 = FireBaseDatabase.getInstance().getReference().child("enigmeReponse").orderByChild("id").equalTo("1");
        databaseRef1.addValueEventListener(new ValueEventListener(){
            @Override
            public  void  onDataChange(@NonNull DataSnapshot dataSnapshot){
                if (dataSnapshot.exists()){
                    reponseData = dataSnapshot.getValue.toString();
                }
            }
            @Override
            public  void  onCancelled(@NonNull DatabaseError databaseError){

            }
        });
        if (reponseData == s){
            return true;
        } else {
            return false;
        }

    }*/

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
