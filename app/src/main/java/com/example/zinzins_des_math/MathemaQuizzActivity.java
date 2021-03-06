package com.example.zinzins_des_math;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    Button btn_ans0, btn_ans1, btn_ans2, btn_ans3;
    MediaPlayer soundtheme, chrono;

    TextView equation, timer, score;
    ProgressBar progresstimer;
    ConstraintLayout bg;
    ImageView nuage_equation,back;
    String defi, role, roomName, difficultyString;
    FirebaseDatabase database;
    FirebaseUser user;
    DatabaseReference uDatabase;
    DatabaseReference rDatabase;
    FirebaseAuth fAuth;

    private MathemaQuizzActivity mathemaQuizzActivity;
    Bundle extras;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ETAT_SOUND_THEME = "etat_sound_theme";
    public static final String ETAT_SOUND_EFFECT = "etat_sound_effect";

    private boolean sound_theme_state;
    private boolean sound_effect_state;

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sound_theme_state = sharedPreferences.getBoolean(ETAT_SOUND_THEME, true);
        sound_effect_state = sharedPreferences.getBoolean(ETAT_SOUND_EFFECT, true);

    }

    Game g = new Game();
    int secondsRemaining = 30;
    CountDownTimer temps = new CountDownTimer(30000, 1000) {
        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            secondsRemaining--;
            timer.setText(Integer.toString(secondsRemaining));
            progresstimer.setProgress(30 - secondsRemaining);
        }

        @Override
        public void onFinish() {
            fAuth = FirebaseAuth.getInstance();
            if (fAuth.getCurrentUser() != null) {
                uDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int dbScore;
                        int actualScore;
                        final User utilisateur = new User();
                        final Field[] fields = utilisateur.getClass().getDeclaredFields();
                        switch (getIntent().getFlags()) {
                            case 0:
                                dbScore = Math.toIntExact((long) dataSnapshot.child(fields[2].getName()).getValue());
                                actualScore = -Integer.parseInt((String) score.getText());
                                if (dbScore > actualScore)
                                    uDatabase.child("scoreMathemaquizzFacile").setValue(actualScore);
                                if (defi != null) {
                                    if (role.equals("host")) {
                                        rDatabase.child("scorePlayer1").setValue(Integer.parseInt((String) score.getText()));
                                    } else if (role.equals("client")) {
                                        rDatabase.child("scorePlayer2").setValue(Integer.parseInt((String) score.getText()));
                                        compareScore();
                                    }
                                }
                                break;
                            case 1:
                                dbScore = Math.toIntExact((long) dataSnapshot.child(fields[3].getName()).getValue());
                                actualScore = -Integer.parseInt((String) score.getText());
                                if (dbScore > actualScore)
                                    uDatabase.child("scoreMathemaquizzMoyen").setValue(actualScore);
                                if (defi != null) {
                                    if (role.equals("host")) {
                                        rDatabase.child("scorePlayer1").setValue(Integer.parseInt((String) score.getText()));
                                    } else if (role.equals("client")) {
                                        rDatabase.child("scorePlayer2").setValue(Integer.parseInt((String) score.getText()));
                                        compareScore();
                                    }
                                }
                                break;
                            case 2:
                                dbScore = Math.toIntExact((long) dataSnapshot.child(fields[1].getName()).getValue());
                                actualScore = -Integer.parseInt((String) score.getText());
                                if (dbScore > actualScore)
                                    uDatabase.child("scoreMathemaquizzDifficile").setValue(actualScore);
                                if (defi != null) {
                                    if (role.equals("host")) {
                                        rDatabase.child("scorePlayer1").setValue(Integer.parseInt((String) score.getText()));
                                    } else if (role.equals("client")) {
                                        rDatabase.child("scorePlayer2").setValue(Integer.parseInt((String) score.getText()));
                                        compareScore();
                                    }
                                }
                                break;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            if(defi != null && role.equals("host")) {
                Intent quit = new Intent(getApplicationContext(), RoomListActivity.class);
                startActivity(quit);
                finish();
            }

            AlertDialog.Builder fini = new AlertDialog.Builder(mathemaQuizzActivity, R.style.MyDialogTheme);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView_endgame = LayoutInflater.from(mathemaQuizzActivity).inflate(R.layout.custom_popup_endgame, viewGroup, false);
            fini.setView(dialogView_endgame);
            AlertDialog alertDialog = fini.create();

            TextView score_dialog = dialogView_endgame.findViewById(R.id.text_score_mj);
            Button quitter_endgame = dialogView_endgame.findViewById(R.id.button_quitter);
            Button reprendre_endgame = dialogView_endgame.findViewById((R.id.button_rep_jeu));
            LinearLayout popup_endgame = dialogView_endgame.findViewById((R.id.layout_popup_endGame));

            String imagePopup = "fin_mj2_" + difficultyString;
            int resId = getResources().getIdentifier(imagePopup, "drawable", getPackageName());
            popup_endgame.setBackground(getDrawable(resId));

            score_dialog.setText(score.getText());

            quitter_endgame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (getIntent().getFlags()) {
                        case 0:
                            Intent main = new Intent(getApplicationContext(), FacileActivity.class);
                            startActivity(main);
                            break;
                        case 1:
                            main = new Intent(getApplicationContext(), MoyenActivity.class);
                            startActivity(main);
                            break;
                        case 2:
                            main = new Intent(getApplicationContext(), DifficileActivity.class);
                            startActivity(main);
                            break;
                        case 3:
                            main = new Intent(getApplicationContext(), LevelActivity.class);
                            startActivity(main);
                            break;
                    }
                    temps.cancel();
                    alertDialog.dismiss();
                    finish();
                }
            });
            reprendre_endgame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    secondsRemaining = 30;
                    g = new Game();
                    nextTurn();
                    temps.start();

                    if(sound_theme_state) {
                        soundtheme = MediaPlayer.create(getApplicationContext(), R.raw.mathemaquizz_sound);
                        soundtheme.setVolume(1f, 1f);
                        soundtheme.start();
                        SplashScreenActivity.general_sound.pause();
                    }

                    if(sound_effect_state) {
                        chrono = MediaPlayer.create(getApplicationContext(), R.raw.chrono_sound);
                        chrono.setVolume(0.2f, 0.2f);
                        chrono.start();
                    }

                    g.setNumberCorrect(0);
                    g.setNumberIncorrect(0);
                    score.setText(Integer.toString(g.getScore()));
                    alertDialog.dismiss();
                    back.setColorFilter(Color.argb(0, 0, 0, 0));
                }
            });
            if(defi == null) {
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathemaquizz);
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            extras = getIntent().getExtras();
            if (extras != null) {
                defi = extras.getString("defi");
                role = extras.getString("role");
                roomName = extras.getString("roomName");
            }
            database = FirebaseDatabase.getInstance();
            user = FirebaseAuth.getInstance().getCurrentUser();
            uDatabase = database.getReference("users").child(user.getUid());
            if (defi != null)
                rDatabase = database.getReference("rooms").child(roomName);
        }
        temps.start();

        this.soundtheme = MediaPlayer.create(getApplicationContext(), R.raw.mathemaquizz_sound);
        this.chrono = MediaPlayer.create(getApplicationContext(), R.raw.chrono_sound);

        this.mathemaQuizzActivity = this;
        btn_ans0 = findViewById(R.id.btn_ans0);
        btn_ans1 = findViewById(R.id.btn_ans1);
        btn_ans2 = findViewById(R.id.btn_ans2);
        btn_ans3 = findViewById(R.id.btn_ans3);

        progresstimer = findViewById(R.id.progressBar);
        timer = findViewById(R.id.timer);
        equation = findViewById(R.id.equation);
        score = findViewById(R.id.score);
        bg = findViewById(R.id.bg_math_quizz);
        nuage_equation = findViewById(R.id.nuage_enigme);
        loadData();
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

        back = findViewById(R.id.second_game_back2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_effect_state){
                    MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                back.setColorFilter(Color.argb(80, 0, 0, 0));
                setQuitPopup();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundtheme.pause();
        chrono.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sound_theme_state) {
            soundtheme.setVolume(1f, 1f);
            SplashScreenActivity.general_sound.pause();
            soundtheme.start();
        }

        if (sound_effect_state) {
            chrono.setVolume(0.2f, 0.2f);
            chrono.start();
        }
    }

    public void onBackPressed() {
        setQuitPopup();
    }

    private void setQuitPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView_back = LayoutInflater.from(this).inflate(R.layout.custom_popup_back, viewGroup, false);
        builder.setView(dialogView_back);
        AlertDialog alertDialog = builder.create();

        Button quitter = dialogView_back.findViewById(R.id.button_quitter);
        Button reprendre = dialogView_back.findViewById((R.id.button_rep_jeu));
        LinearLayout popup_back = dialogView_back.findViewById((R.id.layout_popup_back));

        String imagePopup = "quitter_mj2_" + difficultyString;
        int resId = getResources().getIdentifier(imagePopup, "drawable", getPackageName());
        popup_back.setBackground(getDrawable(resId));

        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main;
                if(defi != null) {
                    main = new Intent(getApplicationContext(), RoomListActivity.class);
                }
                else {
                    switch (getIntent().getFlags()) {
                        case 0:
                            main = new Intent(getApplicationContext(), FacileActivity.class);
                            break;
                        case 1:
                            main = new Intent(getApplicationContext(), MoyenActivity.class);
                            break;
                        case 2:
                            main = new Intent(getApplicationContext(), DifficileActivity.class);
                            break;
                        case 3:
                            main = new Intent(getApplicationContext(), LevelActivity.class);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + getIntent().getFlags());
                    }
                }
                startActivity(main);
                temps.cancel();
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

    private void nextTurn() {

        int difficulty = getIntent().getFlags();
        if (difficulty == 0) {
            bg.setBackground(getDrawable(R.drawable.bg_quizz_facile));
            difficultyString = "facile";
            g.newEquationFacile(g);
        } else if (difficulty == 1) {
            bg.setBackground(getDrawable(R.drawable.bg_quizz_moyen));
            nuage_equation.setImageDrawable(getDrawable(R.drawable.grosnuage_quizz_moyen));
            equation.setTextColor(getColor(R.color.white));
            difficultyString = "moyen";
            g.newEquationMoyen(g);
        } else if (difficulty == 2) {
            bg.setBackground(getDrawable(R.drawable.bg_quizz_difficile));
            nuage_equation.setImageDrawable(getDrawable(R.drawable.quizz_grosnuage_difficile));
            equation.setTextColor(getColor(R.color.white));
            difficultyString = "difficile";
            g.newEquationDifficile(g);
        } else if (difficulty == 3) {
            temps.cancel();
            timer.setVisibility(View.INVISIBLE);
            progresstimer.setVisibility(View.INVISIBLE);

            chrono.stop();
            if(sound_theme_state) {
                soundtheme.setLooping(true);
                soundtheme.start();
            }

            if (g.getScore() < 50) {
                bg.setBackground(getDrawable(R.drawable.bg_quizz_facile));
                nuage_equation.setImageDrawable(getDrawable(R.drawable.grosnuage));
                equation.setTextColor(getColor(R.color.black));
                difficultyString = "facile";
            }else  if (g.getScore() < 100) {
                bg.setBackground(getDrawable(R.drawable.bg_quizz_moyen));
                nuage_equation.setImageDrawable(getDrawable(R.drawable.grosnuage_quizz_moyen));
                equation.setTextColor(getColor(R.color.black));
                difficultyString = "moyen";
            }else if (g.getScore() < 150) {
                equation.setTextColor(getColor(R.color.white));
                bg.setBackground(getDrawable(R.drawable.bg_quizz_difficile));
                nuage_equation.setImageDrawable(getDrawable(R.drawable.quizz_grosnuage_difficile));
                difficultyString = "difficile";
            }else  if (g.getScore() < 200) {
                equation.setTextColor(getColor(R.color.white));
                bg.setBackground(getDrawable(R.drawable.bg_quizz_difficile));
                nuage_equation.setImageDrawable(getDrawable(R.drawable.quizz_grosnuage_difficile));
                difficultyString = "difficile";
            }else {
                equation.setTextColor(getColor(R.color.white));
                bg.setBackground(getDrawable(R.drawable.bg_quizz_difficile));
                nuage_equation.setImageDrawable(getDrawable(R.drawable.quizz_grosnuage_difficile));
                difficultyString = "difficile";
            }
            g.newEquationEvolution(g);
        }

        int[] answer = g.getCurrentEquation().getAnswerArray();
        btn_ans0.setText(Integer.toString(answer[0]));
        btn_ans1.setText(Integer.toString(answer[1]));
        btn_ans2.setText(Integer.toString(answer[2]));
        btn_ans3.setText(Integer.toString(answer[3]));


        equation.setText(g.getCurrentEquation().getEquationPhrase());
        if (g.getScore() < 0) {
            g.setNumberIncorrect(0);
            g.setNumberCorrect(0);
            g.setScore(0);
            score.setText(Integer.toString(g.getScore()));
        }

    }

    private void compareScore() {
        rDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String player1;
                String player2;
                int scorePlayer1;
                int scorePlayer2;
                final Room room = new Room();
                final Field[] fields = room.getClass().getDeclaredFields();
                player1 = (String) dataSnapshot.child(fields[1].getName()).getValue();
                player2 = (String) dataSnapshot.child(fields[2].getName()).getValue();
                scorePlayer1 = Math.toIntExact((Long) dataSnapshot.child(fields[3].getName()).getValue());
                scorePlayer2 = Math.toIntExact((Long) dataSnapshot.child(fields[4].getName()).getValue());
                DatabaseReference refUsers = database.getReference("users");
                AlertDialog.Builder finiDefi = new AlertDialog.Builder(mathemaQuizzActivity);
                if (scorePlayer1 > scorePlayer2) {
                    refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int oldPoints = Math.toIntExact((Long) snapshot.child(player1).child("victoiresMathemaquizz").getValue());
                            int newPoints = oldPoints - 1;
                            refUsers.child(player1).child("victoiresMathemaquizz").setValue(newPoints);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                    AlertDialog.Builder defaite = new AlertDialog.Builder(mathemaQuizzActivity, R.style.MyDialogTheme);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(mathemaQuizzActivity).inflate(R.layout.custom_popup_multi2, viewGroup, false);
                    defaite.setView(dialogView);
                    AlertDialog alertDialog = defaite.create();

                    TextView votreScore = dialogView.findViewById(R.id.text_votre_score2);
                    TextView monScore = dialogView.findViewById(R.id.text_son_score2);
                    Button quitter = dialogView.findViewById((R.id.button_quitter_multi2));
                    LinearLayout popup_back = dialogView.findViewById((R.id.layout_popup_back));
                    popup_back.setBackground(getDrawable(R.drawable.popup_defaite_mj2));

                    votreScore.setText("" + scorePlayer2);
                    monScore.setText("" + scorePlayer1);

                    quitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent back = new Intent(getApplicationContext(), RoomListActivity.class);
                            startActivity(back);
                            rDatabase.removeValue();
                            alertDialog.dismiss();
                            finish();
                        }
                    });
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
                else if(scorePlayer1 == scorePlayer2) {
                    refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int oldPoints1 = Math.toIntExact((Long) snapshot.child(player1).child("victoiresMathemaquizz").getValue());
                            int newPoints1 = oldPoints1 - 1;
                            int oldPoints2 = Math.toIntExact((Long) snapshot.child(player2).child("victoiresMathemaquizz").getValue());
                            int newPoints2 = oldPoints2 - 1;
                            refUsers.child(player1).child("victoiresMathemaquizz").setValue(newPoints1);
                            refUsers.child(player2).child("victoiresMathemaquizz").setValue(newPoints2);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                    AlertDialog.Builder nul = new AlertDialog.Builder(mathemaQuizzActivity, R.style.MyDialogTheme);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(mathemaQuizzActivity).inflate(R.layout.custom_popup_multi2, viewGroup, false);
                    nul.setView(dialogView);
                    AlertDialog alertDialog = nul.create();

                    TextView votreScore = dialogView.findViewById(R.id.text_votre_score2);
                    TextView monScore = dialogView.findViewById(R.id.text_son_score2);
                    Button quitter = dialogView.findViewById((R.id.button_quitter_multi2));
                    LinearLayout popup_back = dialogView.findViewById((R.id.layout_popup_back));
                    popup_back.setBackground(getDrawable(R.drawable.popup_egalite_mj2));

                    votreScore.setText("" + scorePlayer2);
                    monScore.setText("" + scorePlayer1);

                    quitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent back = new Intent(getApplicationContext(), RoomListActivity.class);
                            startActivity(back);
                            rDatabase.removeValue();
                            alertDialog.dismiss();
                            finish();
                        }
                    });
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
                else {
                    refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int oldPoints = Math.toIntExact((Long) snapshot.child(player2).child("victoiresMathemaquizz").getValue());
                            int newPoints = oldPoints - 1;
                            refUsers.child(player2).child("victoiresMathemaquizz").setValue(newPoints);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                    AlertDialog.Builder bravo = new AlertDialog.Builder(mathemaQuizzActivity, R.style.MyDialogTheme);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(mathemaQuizzActivity).inflate(R.layout.custom_popup_multi2, viewGroup, false);
                    bravo.setView(dialogView);
                    AlertDialog alertDialog = bravo.create();

                    TextView votreScore = dialogView.findViewById(R.id.text_votre_score2);
                    TextView monScore = dialogView.findViewById(R.id.text_son_score2);
                    Button quitter = dialogView.findViewById((R.id.button_quitter_multi2));
                    LinearLayout popup_back = dialogView.findViewById((R.id.layout_popup_multi2));
                    popup_back.setBackground(getDrawable(R.drawable.popup_victoire_mj2));

                    votreScore.setText("" + scorePlayer2);
                    monScore.setText("" + scorePlayer1);

                    quitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent back = new Intent(getApplicationContext(), RoomListActivity.class);
                            startActivity(back);
                            rDatabase.removeValue();
                            alertDialog.dismiss();
                            finish();
                        }
                    });
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}