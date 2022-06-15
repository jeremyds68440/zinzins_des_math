package com.example.zinzins_des_math;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zinzins_des_math.adapter.BubbleItemAdapter;
import com.example.zinzins_des_math.models.BubbleItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MultiFactorActivity extends AppCompatActivity {

    private Context context;
    private int points = 0;
    private int numberOfTry = 1;
    private int counter = 0;
    private int countMult = 0;
    private int evolutionScore = 0;
    private int difficulty, target, multiTurn;
    private Bundle extras;
    private String defi, role, roomName, difficultyString;
    private int id = R.layout.custom_popup_endgame;
    private TextView targetCount, scoreCount;
    private LinearLayout layout;
    private ImageView scorePlace, targetPlace, gridBackground, back;
    private boolean blocked = false;
    private boolean evolution = false;
    private boolean sound_theme_state, sound_effect_state;
    private ArrayList<Integer> iterBubble;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private FirebaseAuth fAuth;
    private DatabaseReference uDatabase, rDatabase;
    private MediaPlayer soundtheme, soundgood, soundvrong;
    public int BUBBLECOLUMN = 190;
    public int BUBBLEROW = 190;
    public static final int NUMBERBUBBLEROW = 6;
    public static final int NUMBERBUBBLECOLUMN = 4;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ETAT_SOUND_THEME = "etat_sound_theme";
    public static final String ETAT_SOUND_EFFECT = "etat_sound_effect";


    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sound_theme_state = sharedPreferences.getBoolean(ETAT_SOUND_THEME, true);
        sound_effect_state = sharedPreferences.getBoolean(ETAT_SOUND_EFFECT, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multifactor);
        loadData();

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            multiTurn = 0;
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

        iterBubble = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            iterBubble.add(0);
        }

        if (sound_theme_state) {
            this.soundtheme = MediaPlayer.create(getApplicationContext(), R.raw.multifactor_sound);
        }
        context = getApplicationContext();

        DisplayMetrics displayrealMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayrealMetrics);

        Resources resources = context.getResources();
        int resourcesId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = displayrealMetrics.heightPixels - resources.getDimensionPixelSize(resourcesId);
        int width = displayrealMetrics.widthPixels;


        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);


        layout = findViewById(R.id.multifactor);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)getResources().getDimension(R.dimen.wight_backbutton), (int)getResources().getDimension(R.dimen.height_backbutton));
        params.setMargins((int)(16*displayrealMetrics.density),(int)(44*displayrealMetrics.density),0,0);
        back = new ImageView(getApplicationContext());
        back.setLayoutParams(params);
        back.setImageResource(R.drawable.ic_baseline_arrow_back_ios_24);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQuitPopup();
            }
        });
        layout.addView(back);

        ImageView scoreImage = new ImageView(getApplicationContext());
        params = new LinearLayout.LayoutParams(width, 107);
        scoreImage.setLayoutParams(params);
        scoreImage.setImageResource(R.drawable.multifactor_scoretext);

        layout.addView(scoreImage);

        RelativeLayout relativeScore = new RelativeLayout(getApplicationContext());
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(width, 220);
        relativeParams.setMargins(0, 27, 0, 66);
        relativeScore.setLayoutParams(relativeParams);

        layout.addView(relativeScore);

        scorePlace = new ImageView(getApplicationContext());
        params = new LinearLayout.LayoutParams(width, 220);
        scorePlace.setLayoutParams(params);

        relativeScore.addView(scorePlace);

        scoreCount = new TextView(getApplicationContext());
        params = new LinearLayout.LayoutParams(width, 220);
        scoreCount.setLayoutParams(params);
        scoreCount.setGravity(Gravity.CENTER);
        scoreCount.setTextColor(context.getResources().getColor(R.color.first_game_text));
        scoreCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);

        relativeScore.addView(scoreCount);

        ImageView targetImage = new ImageView(getApplicationContext());
        params = new LinearLayout.LayoutParams(width, 107);
        targetImage.setLayoutParams(params);
        targetImage.setImageResource(R.drawable.multifactor_targettext);

        layout.addView(targetImage);

        RelativeLayout relativeTarget = new RelativeLayout(getApplicationContext());
        relativeParams = new RelativeLayout.LayoutParams(width, 220);
        relativeParams.setMargins(0, 27, 0, 55);
        relativeTarget.setLayoutParams(relativeParams);

        layout.addView(relativeTarget);

        targetPlace = new ImageView(getApplicationContext());
        params = new LinearLayout.LayoutParams(width, 220);
        targetPlace.setLayoutParams(params);

        relativeTarget.addView(targetPlace);

        targetCount = new TextView(getApplicationContext());
        params = new LinearLayout.LayoutParams(width, 220);
        targetCount.setLayoutParams(params);
        targetCount.setGravity(Gravity.CENTER);
        targetCount.setTextColor(context.getResources().getColor(R.color.first_game_text));
        targetCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);

        relativeTarget.addView(targetCount);

        int gridBgHeight = height - 849 - (int)(44*displayrealMetrics.density) - (int)getResources().getDimension(R.dimen.height_backbutton);

        RelativeLayout gridLayout = new RelativeLayout(getApplicationContext());
        relativeParams = new RelativeLayout.LayoutParams(width, gridBgHeight);
        relativeParams.setMargins(0,0,0,14);
        gridLayout.setLayoutParams(relativeParams);

        gridBackground = new ImageView(getApplicationContext());
        params = new LinearLayout.LayoutParams(width, gridBgHeight);
        gridBackground.setLayoutParams(params);

        difficulty = getIntent().getFlags();
        if(difficulty == 3) {
            evolution = true;
            difficulty = 0;
        }
        setDifficultyTheme();

        while(BUBBLEROW*NUMBERBUBBLEROW < gridBgHeight-70) {
            BUBBLECOLUMN++;
            BUBBLEROW++;
        }

        while(BUBBLEROW*NUMBERBUBBLEROW > gridBgHeight-70) {
            BUBBLECOLUMN--;
            BUBBLEROW--;
        }

        context = getApplicationContext();

        newTarget();
        resetCounter();

        List<BubbleItem> bubbles = new ArrayList<>();
        ImageView number;
        int notPlaced = 0;
        int random;
        for(int i = 0; i < NUMBERBUBBLECOLUMN*NUMBERBUBBLEROW; i++) {
            random = (int) (Math.random()*8+2);
            while(i > NUMBERBUBBLECOLUMN*NUMBERBUBBLEROW/2 && notPlaced != -1 && notPlaced != random) {
                notPlaced = numberNotPlaced();
                random = Math.max(notPlaced, 2);
            }
            iterBubble.set(random, iterBubble.get(random) + 1);
            bubbles.add(new BubbleItem(random));
        }

        RelativeLayout.LayoutParams gridParams = new RelativeLayout.LayoutParams(NUMBERBUBBLECOLUMN*BUBBLECOLUMN+100,BUBBLEROW*NUMBERBUBBLEROW);
        RelativeLayout.LayoutParams bgParams = new RelativeLayout.LayoutParams(width,BUBBLEROW*NUMBERBUBBLEROW + 100);
        gridParams.setMargins((width - (NUMBERBUBBLECOLUMN*BUBBLECOLUMN+70))/2,(gridBgHeight - BUBBLEROW*NUMBERBUBBLEROW)/2,0,0);
        GridView grid = new GridView(getApplicationContext());
        grid.setNumColumns(NUMBERBUBBLECOLUMN);
        grid.setLayoutParams(gridParams);
        grid.setAdapter(new BubbleItemAdapter(this, bubbles));

        gridLayout.addView(gridBackground);
        gridLayout.addView(grid);
        layout.addView(gridLayout);
    }

    public int numberNotPlaced() {
        for(int i = 2; i < iterBubble.size(); i++) {
            if(iterBubble.get(i) == 0) {
                return i;
            }
        }
        return -1;
    }

    public void decreaseByOne(int i) {
        iterBubble.set(i, iterBubble.get(i) - 1);
    }

    public int getLessPlaced() {
        ArrayList<Integer> lessPlaced = new ArrayList<>();
        lessPlaced.add(2);
        int timePlaced = iterBubble.get(2);
        for(int i = 3; i < iterBubble.size(); i++) {
            if(timePlaced > iterBubble.get(i)) {
                lessPlaced = new ArrayList<>();
                lessPlaced.add(i);
                timePlaced = iterBubble.get(i);
            }
            else if(timePlaced == iterBubble.get(i)) {
                lessPlaced.add(i);
            }
        }
        int candidate;
        if(timePlaced == 0 && (lessPlaced.get(0) == 2 || lessPlaced.get(0) == 3 || lessPlaced.get(0) == 5 || lessPlaced.get(0) == 7)) {
            candidate = lessPlaced.get(0);
            iterBubble.set(candidate, iterBubble.get(candidate) + 1);
        }
        else {
            candidate = (int) (Math.random() * 8 + 2);
            iterBubble.set(candidate, iterBubble.get(candidate) + 1);
        }
        return candidate;
    }

    public void setDifficultyTheme() {
        if(difficulty == 0) {
            layout.setBackground(getDrawable(R.drawable.multifactor_bg_facile));
            scorePlace.setImageResource(R.drawable.multifactor_score_facile);
            targetPlace.setImageResource(R.drawable.multifactor_target_facile);
            difficultyString = "facile";
            gridBackground.setImageResource(R.drawable.multifactor_terrain_facile);
        }
        else if(difficulty == 1) {
            layout.setBackground(getDrawable(R.drawable.multifactor_bg_moyen));
            scorePlace.setImageResource(R.drawable.multifactor_score_moyen);
            targetPlace.setImageResource(R.drawable.multifactor_target_moyen);
            difficultyString = "moyen";
            gridBackground.setImageResource(R.drawable.multifactor_terrain_moyen);
        }
        else {
            layout.setBackground(getDrawable(R.drawable.multifactor_bg_difficile));
            scorePlace.setImageResource(R.drawable.multifactor_score_difficile);
            targetPlace.setImageResource(R.drawable.multifactor_target_difficile);
            difficultyString = "difficile";
            gridBackground.setImageResource(R.drawable.multifactor_terrain_difficile);
        }
    }


    public void onBackPressed() {
        setQuitPopup();
    }

    public void multCounter(int x) {
        if(counter == 0) {
            counter = x;
        }
        else {
            counter *= x;
        }
        countMult++;
    }

    public int getCounter() {
        return counter;
    }

    public TextView getScoreCount() {
        return scoreCount;
    }

    public void verify() {
        if(counter >= target) {
            blocked = true;
            int point = 0;
            int evolutionScoreTour = 0;
            if(counter == target) {
                id = R.layout.custom_popup_mj1;
                int varMult = (difficulty+2)*2;
                point += 120 * ((float)(varMult - countMult + getMinMult() + difficulty*2) /varMult/ numberOfTry);
                numberOfTry = 1;
                countMult = 0;
                points += point;
                if(evolution) {
                    evolutionScoreTour = point - 60 * (varMult + difficulty * 2) / varMult;
                    evolutionScore += evolutionScoreTour;
                    evolutionScore = Math.max(evolutionScore, 0);
                    evolutionDifficulty();
                }
            }
            else {
                id = R.layout.custom_popup_back;
                countMult = 0;
                numberOfTry++;
            }
            setGameOver(counter == target, point, evolutionScoreTour);
        }
    }

    public void evolutionDifficulty() {
        difficulty = 0;
        int varMult = (difficulty+2)*2;
        int palier = 600 * (varMult + difficulty*2)/varMult;
        while(evolutionScore >= palier) {
            difficulty++;
            varMult = (difficulty+2)*2;
            palier += 600 * (varMult + difficulty*2)/varMult;
        }
        setDifficultyTheme();
    }

    public int getMinMult() {
        int targetInter = target;
        int res = 0;
        int i = 9;
        while(targetInter != 1) {
            while(targetInter % i == 0) {
                targetInter /= i;
                res++;
            }
            i--;
        }
        return res;
    }

    public void setGameOver(boolean win , int point, int evolutionScoreTour) {
        AlertDialog.Builder popup = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(id, viewGroup, false);
        popup.setView(dialogView);
        AlertDialog alertDialog = popup.create();

        Button quitter = dialogView.findViewById(R.id.button_quitter);
        Button reprendre = dialogView.findViewById((R.id.button_rep_jeu));


        String positive;
        if(win) {
            if (defi != null) {
                multiTurn++;
            }

            LinearLayout linearLayout = dialogView.findViewById(R.id.layout_popup_victoire);

            if (sound_effect_state){
                this.soundgood = MediaPlayer.create(getApplicationContext(), R.raw.good_sound);
                soundgood.start();
            }

            if (sound_theme_state) {
                soundtheme.setVolume(0.2f, 0.2f);
            }

            String imagePopup = "victoire_mj1_" + difficultyString;
            int resId = getResources().getIdentifier(imagePopup, "drawable", getPackageName());
            linearLayout.setBackground(getDrawable(resId));

            TextView scoreDialog = dialogView.findViewById(R.id.text_score_mj);
            TextView totalScoreDialog = dialogView.findViewById(R.id.text_point_mj1);
            if(evolution) {
                scoreDialog.setText("" + evolutionScoreTour);
                totalScoreDialog.setText("" + evolutionScore);
            }
            else {
                scoreDialog.setText("" + point);
                totalScoreDialog.setText("" + points);
            }
            if (fAuth.getCurrentUser() != null) {
                uDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int dbScore;
                        final User utilisateur = new User();
                        final Field[] fields = utilisateur.getClass().getDeclaredFields();
                        switch (getIntent().getFlags()) {
                            case 0:
                                dbScore = Math.toIntExact((long) dataSnapshot.child(fields[5].getName()).getValue());
                                if (dbScore < points)
                                    uDatabase.child("scoreMultifactorFacile").setValue(points);
                                if (defi != null && multiTurn == 5) {
                                    if (role.equals("host")) {
                                        rDatabase.child("scorePlayer1").setValue(points);
                                    } else if (role.equals("client")) {
                                        rDatabase.child("scorePlayer2").setValue(points);
                                        compareScore();
                                    }
                                }
                                break;
                            case 1:
                                dbScore = Math.toIntExact((long) dataSnapshot.child(fields[6].getName()).getValue());
                                if (dbScore < points)
                                    uDatabase.child("scoreMultifactorMoyen").setValue(points);
                                if (defi != null && multiTurn == 5) {
                                    if (role.equals("host")) {
                                        rDatabase.child("scorePlayer1").setValue(points);
                                    } else if (role.equals("client")) {
                                        rDatabase.child("scorePlayer2").setValue(points);
                                        compareScore();
                                    }
                                }
                                break;
                            case 2:
                                dbScore = Math.toIntExact((long) dataSnapshot.child(fields[4].getName()).getValue());
                                if (dbScore < points)
                                    uDatabase.child("scoreMultifactorDifficile").setValue(points);
                                if (defi != null && multiTurn == 5) {
                                    if (role.equals("host")) {
                                        rDatabase.child("scorePlayer1").setValue(points);
                                    } else if (role.equals("client")) {
                                        rDatabase.child("scorePlayer2").setValue(points);
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
        }
        else {
            if (sound_effect_state){
                this.soundvrong = MediaPlayer.create(getApplicationContext(), R.raw.vrong_sound);
                soundvrong.start();
            }

            if (sound_theme_state) {
                soundtheme.setVolume(Float.parseFloat(getString(R.string.sound_0_2)),Float.parseFloat(getString(R.string.sound_0_2)));
            }

            String imagePopup = "perdu_mj1_" + difficultyString;
            int resId = getResources().getIdentifier(imagePopup, "drawable", getPackageName());
            LinearLayout linearLayout = dialogView.findViewById(R.id.layout_popup_back);
            linearLayout.setBackground(getDrawable(resId));

        }

        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent difficulte;
                if(extras != null) {
                    difficulte = new Intent(getApplicationContext(), RoomListActivity.class);
                }
                else if(evolution) {
                    difficulte = new Intent(getApplicationContext(), LevelActivity.class);
                }
                else if(difficulty == 0) {
                    difficulte = new Intent(getApplicationContext(), FacileActivity.class);
                }
                else if(difficulty == 1) {
                    difficulte = new Intent(getApplicationContext(), MoyenActivity.class);
                }
                else {
                    difficulte = new Intent(getApplicationContext(), DifficileActivity.class);
                }
                startActivity(difficulte);
                alertDialog.dismiss();
                finish();
            }
        });
        reprendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(win) {
                    newTarget();
                }

                if (sound_theme_state) {
                    SplashScreenActivity.general_sound.pause();
                    soundtheme.setVolume(Float.parseFloat(getString(R.string.sound_on)),Float.parseFloat(getString(R.string.sound_on)));
                }
                resetCounter();
                unBlocked();
                alertDialog.dismiss();
            }
        });

        if(defi == null) {
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
        else if(multiTurn < 5) {
            if(win) {
                newTarget();
            }
            resetCounter();
            unBlocked();
        }
        else if(role.equals("host")) {
            Intent retour = new Intent(getApplicationContext(), FacileActivity.class);
            startActivity(retour);
            finish();
        }
    }

    public void setQuitPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView_back = LayoutInflater.from(this).inflate(R.layout.custom_popup_back, viewGroup, false);
        builder.setView(dialogView_back);
        AlertDialog alertDialog = builder.create();


        Button quitter = dialogView_back.findViewById(R.id.button_quitter);
        Button reprendre = dialogView_back.findViewById((R.id.button_rep_jeu));
        LinearLayout popup_back = dialogView_back.findViewById((R.id.layout_popup_back));

        String imagePopup = "quitter_mj1_" + difficultyString;
        int resId = getResources().getIdentifier(imagePopup, "drawable", getPackageName());
        popup_back.setBackground(getDrawable(resId));

        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent difficulte;
                if(extras != null) {
                    difficulte = new Intent(getApplicationContext(), RoomListActivity.class);
                }
                else if(evolution) {
                    difficulte = new Intent(getApplicationContext(), LevelActivity.class);
                }
                else if(difficulty == 0) {
                    difficulte = new Intent(getApplicationContext(), FacileActivity.class);
                }
                else if(difficulty == 1) {
                    difficulte = new Intent(getApplicationContext(), MoyenActivity.class);
                }
                else {
                    difficulte = new Intent(getApplicationContext(), DifficileActivity.class);
                }
                startActivity(difficulte);
                alertDialog.dismiss();
                finish();
            }
        });

        reprendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setColorFilter(Color.argb(0, 0, 0, 0));
                alertDialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void resetCounter() {
        scoreCount.setTextColor(context.getResources().getColor(R.color.first_game_text));
        scoreCount.setText("0");
        counter = 0;
    }

    public void newTarget() {
        int ancienTarget = target;
        while(target == ancienTarget) {
            target = 1;
            int i = 0;
            while((i < difficulty + 2 || target < Math.pow(10, difficulty + 1)) && target < Integer.MAX_VALUE/10) {
                target *= (int) (Math.random() * 9) + 2;
                i++;
            }
        }
        targetCount.setText("" + target);
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void unBlocked() {
        blocked = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sound_theme_state) {
            soundtheme.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sound_theme_state) {
            SplashScreenActivity.general_sound.pause();
            soundtheme.setVolume(Float.parseFloat(getString(R.string.sound_on)), Float.parseFloat(getString(R.string.sound_on)));
            soundtheme.start();
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
                AlertDialog.Builder finiDefi = new AlertDialog.Builder(MultiFactorActivity.this);
                if (scorePlayer1 > scorePlayer2) {
                    refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int oldPoints = Math.toIntExact((Long) snapshot.child(player1).child("victoiresMultifactor").getValue());
                            int newPoints = oldPoints + 1;
                            refUsers.child(player1).child("victoiresMultifactor").setValue(newPoints);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    finiDefi.setTitle("Dommage");
                    finiDefi.setMessage("Vous avez perdu avec " + scorePlayer2 + " pts contre " + scorePlayer1 + " pts");
                    finiDefi.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            rDatabase.removeValue();
                            Intent back = new Intent(getApplicationContext(), RoomListActivity.class);
                            startActivity(back);
                            finish();
                        }
                    });
                    finiDefi.setCancelable(false);
                    finiDefi.show();
                } else {
                    refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int oldPoints = Math.toIntExact((Long) snapshot.child(player2).child("victoiresMultifactor").getValue());
                            int newPoints = oldPoints + 1;
                            refUsers.child(player2).child("victoiresMultifactor").setValue(newPoints);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    finiDefi.setTitle("Bravo");
                    finiDefi.setMessage("Vous avez perdu avec " + scorePlayer2 + " pts contre " + scorePlayer1 + " pts");
                    finiDefi.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            rDatabase.removeValue();
                            Intent back = new Intent(getApplicationContext(), RoomListActivity.class);
                            startActivity(back);
                            finish();
                        }
                    });
                    finiDefi.setCancelable(false);
                    finiDefi.show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}