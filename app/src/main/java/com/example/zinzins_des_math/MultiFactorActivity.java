package com.example.zinzins_des_math;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private int id = R.layout.custom_popup_endgame;
    private int difficulty, target, multiTurn, dbPlace;
    private String defi, role, difficultyString;
    private TextView targetCount, scoreCount;
    private LinearLayout layout;
    private ImageView scorePlace, targetPlace, gridBackground, back;
    private boolean blocked = false;
    private boolean evolution = false;
    private boolean sound_theme_state, sound_effect_state;
    private boolean multi = false;
    private ArrayList<Integer> iterBubble;
    private FirebaseDatabase database;
    private FirebaseAuth fAuth;
    private DatabaseReference uDatabase, rDatabase;
    private MediaPlayer soundtheme;
    public int bubbleSize = 190;
    public static final int NUMBERBUBBLEROW = 6;
    public static final int NUMBERBUBBLECOLUMN = 4;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ETAT_SOUND_THEME = "etat_sound_theme";
    public static final String ETAT_SOUND_EFFECT = "etat_sound_effect";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multifactor);
        loadData();

        connection();
        setMulti();
        context = getApplicationContext();

        if (sound_theme_state) {
            soundtheme = MediaPlayer.create(context, R.raw.multifactor_sound);
        }

        DisplayMetrics displayrealMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayrealMetrics);
        Resources resources = context.getResources();
        int resourcesId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int windowHeight = displayrealMetrics.heightPixels - resources.getDimensionPixelSize(resourcesId);
        int windowWidth = displayrealMetrics.widthPixels;

        layout = findViewById(R.id.multifactor);

        int backHeight = (int)getResources().getDimension(R.dimen.height_backbutton);
        int backMarginTop = (int)(44*displayrealMetrics.density);
        int imageHeight = 107;
        int relativeHeight = 220;
        int topHeight = backHeight + backMarginTop + imageHeight*2 + relativeHeight * 2 + relativeHeight/8 * 2 + (int)(relativeHeight/3.3) * 2;

        while(topHeight < (int)(windowHeight * 0.47)) {
            imageHeight++;
            relativeHeight++;
            topHeight = backHeight + backMarginTop + imageHeight*2 + relativeHeight * 2 + relativeHeight/8 * 2 + (int)(relativeHeight/3.3) * 2;
        }

        while(topHeight >= (int)(windowHeight * 0.47)) {
            imageHeight--;
            relativeHeight--;
            topHeight = backHeight + backMarginTop + imageHeight*2 + relativeHeight * 2 + relativeHeight/8 * 2 + (int)(relativeHeight/3.3) * 2;
        }

        int gridBgHeight = windowHeight - topHeight;

        back = setImageView(new ImageView(context), (int)getResources().getDimension(R.dimen.wight_backbutton), backHeight, (int)(16*displayrealMetrics.density), backMarginTop, 0, 0, R.drawable.ic_baseline_arrow_back_ios_24);;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQuitPopup();
            }
        });

        ImageView scoreImage = setImageView(new ImageView(context), windowWidth, imageHeight, 0, 0, 0, 0, R.drawable.multifactor_scoretext);
        RelativeLayout relativeScore = setRelativeLayout(windowWidth, relativeHeight, 0, relativeHeight/8, 0, (int)(relativeHeight/3.3));
        ImageView targetImage = setImageView(new ImageView(context), windowWidth, imageHeight, 0, 0, 0, 0, R.drawable.multifactor_targettext);
        RelativeLayout relativeTarget = setRelativeLayout(windowWidth, relativeHeight, 0, relativeHeight/8, 0, (int)(relativeHeight/3.3));
        RelativeLayout gridLayout = setRelativeLayout(windowWidth, gridBgHeight, 0, 0, 0, 14);

        scorePlace = setImageView(new ImageView(context), windowWidth, relativeHeight, 0, 0, 0, 0, 0);
        scoreCount = setTextView(windowWidth, relativeHeight);
        relativeScore.addView(scorePlace);
        relativeScore.addView(scoreCount);

        targetPlace = setImageView(new ImageView(context), windowWidth, relativeHeight, 0, 0, 0, 0, 0);
        targetCount = setTextView(windowWidth, relativeHeight);
        relativeTarget.addView(targetPlace);
        relativeTarget.addView(targetCount);

        gridBackground = setImageView(new ImageView(context), windowWidth, gridBgHeight, 0, 0, 0, 0, 0);

        setDifficulty();
        setBubbleSize(gridBgHeight);
        newTarget();
        resetCounter();

        List<BubbleItem> bubbles = setBubbleList();

        RelativeLayout.LayoutParams gridParams = new RelativeLayout.LayoutParams(NUMBERBUBBLECOLUMN*bubbleSize+100,bubbleSize*NUMBERBUBBLEROW);
        gridParams.setMargins((windowWidth - (NUMBERBUBBLECOLUMN*bubbleSize+70))/2,(gridBgHeight - bubbleSize*NUMBERBUBBLEROW)/2,0,0);
        GridView grid = new GridView(context);
        grid.setNumColumns(NUMBERBUBBLECOLUMN);
        grid.setLayoutParams(gridParams);
        grid.setAdapter(new BubbleItemAdapter(this, bubbles));

        gridLayout.addView(gridBackground);
        gridLayout.addView(grid);

        layout.addView(back);
        layout.addView(scoreImage);
        layout.addView(relativeScore);
        layout.addView(targetImage);
        layout.addView(relativeTarget);
        layout.addView(gridLayout);
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sound_theme_state = sharedPreferences.getBoolean(ETAT_SOUND_THEME, true);
        sound_effect_state = sharedPreferences.getBoolean(ETAT_SOUND_EFFECT, true);
    }

    public void connection() {
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            database = FirebaseDatabase.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            uDatabase = database.getReference("users").child(user.getUid());
        }
    }

    public void setMulti() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            multi = true;
            multiTurn = 0;
            defi = extras.getString("defi");
            role = extras.getString("role");
            String roomName = extras.getString("roomName");
            rDatabase = database.getReference("rooms").child(roomName);
        }
    }

    public RelativeLayout setRelativeLayout(int width, int height, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(width, height);
        relativeParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        relativeLayout.setLayoutParams(relativeParams);
        return relativeLayout;
    }

    public ImageView setImageView(ImageView view, int width, int height, int marginLeft, int marginTop, int marginRight, int marginBottom, int imageId) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        view.setLayoutParams(params);
        if(imageId != 0) {
            view.setImageResource(imageId);
        }
        return view;
    }

    public TextView setTextView(int width, int height) {
        TextView view = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        view.setLayoutParams(params);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(getColor(R.color.first_game_text));
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        return view;
    }

    public void setDifficulty() {
        difficulty = getIntent().getFlags();
        if(difficulty == 3) {
            evolution = true;
            difficulty = 0;
        }
        setDifficultyTheme();
    }

    public List<BubbleItem> setBubbleList() {
        List<BubbleItem> bubbles = new ArrayList<>();
        int notPlaced = 0;
        int random;

        iterBubble = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            iterBubble.add(0);
        }

        for(int i = 0; i < NUMBERBUBBLECOLUMN*NUMBERBUBBLEROW; i++) {
            random = (int) (Math.random()*8+2);
            while(i > NUMBERBUBBLECOLUMN*NUMBERBUBBLEROW/2 && notPlaced != -1 && notPlaced != random) {
                notPlaced = numberNotPlaced();
                random = Math.max(notPlaced, 2);
            }
            iterBubble.set(random, iterBubble.get(random) + 1);
            bubbles.add(new BubbleItem(random));
        }
        return bubbles;
    }

    public void setBubbleSize(int gridBgHeight) {
        while(bubbleSize*NUMBERBUBBLEROW < gridBgHeight-70) {
            bubbleSize++;
        }
        while(bubbleSize*NUMBERBUBBLEROW > gridBgHeight-70) {
            bubbleSize--;
        }
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
        int candidate = 0;
        for(int i = 0; i < lessPlaced.size(); i++) {
            if(timePlaced == 0 && (lessPlaced.get(i) == 2 || lessPlaced.get(i) == 3 || lessPlaced.get(i) == 5 || lessPlaced.get(i) == 7)) {
                candidate = lessPlaced.get(i);
                iterBubble.set(candidate, 1);
            }
        }
        if(candidate == 0) {
            candidate = (int) (Math.random() * 8 + 2);
            iterBubble.set(candidate, iterBubble.get(candidate) + 1);
        }
        return candidate;
    }

    public void setDifficultyTheme() {
        if(difficulty == 0) {
            difficultyString = "facile";
            dbPlace = 5;
        }
        else if(difficulty == 1) {
            difficultyString = "moyen";
            dbPlace = 6;
        }
        else {
            difficultyString = "difficile";
            dbPlace = 4;
        }
        int resId = getResources().getIdentifier("multifactor_bg_" + difficultyString, "drawable", getPackageName());
        layout.setBackground(getDrawable(resId));
        resId = getResources().getIdentifier("multifactor_score_" + difficultyString, "drawable", getPackageName());
        scorePlace.setImageResource(resId);
        resId = getResources().getIdentifier("multifactor_target_" + difficultyString, "drawable", getPackageName());
        targetPlace.setImageResource(resId);
        resId = getResources().getIdentifier("multifactor_terrain_" + difficultyString, "drawable", getPackageName());
        gridBackground.setImageResource(resId);
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
                point = (int)(120 * ((float)(varMult - countMult + getMinMult() + difficulty*2) /varMult/ numberOfTry));
                numberOfTry = 1;
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
                numberOfTry++;
            }
            countMult = 0;
            setGameOver(counter == target, point, evolutionScoreTour);
        }
    }

    public void evolutionDifficulty() {
        Log.e("MultiFactorActivity", "Pk je suis dans evolutionDifficulty ?");
        difficulty = -1;
        int varMult, palier;
        do {
            difficulty++;
            varMult = (difficulty+2)*2;
            palier = 600 * (varMult + difficulty*2)/varMult;
        } while(evolutionScore >= palier);
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

        if(win) {
            if (multi) {
                multiTurn++;
            }

            LinearLayout linearLayout = dialogView.findViewById(R.id.layout_popup_victoire);

            if (sound_effect_state){
                MediaPlayer soundgood = MediaPlayer.create(context, R.raw.good_sound);
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
                        dbScore = Math.toIntExact((long) dataSnapshot.child(fields[dbPlace].getName()).getValue());
                        if (dbScore > -points)
                            uDatabase.child("scoreMultifactor" + difficultyString.substring(0, 1).toUpperCase() + difficultyString.substring(1)).setValue(-points);
                        if (multi && multiTurn == 5) {
                            if (role.equals("host")) {
                                rDatabase.child("scorePlayer1").setValue(points);
                            } else if (role.equals("client")) {
                                rDatabase.child("scorePlayer2").setValue(points);
                                compareScore();
                            }
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
                MediaPlayer soundwrong = MediaPlayer.create(context, R.raw.vrong_sound);
                soundwrong.start();
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
                if(multi) {
                    difficulte = new Intent(context, RoomListActivity.class);
                }
                else if(evolution) {
                    difficulte = new Intent(context, LevelActivity.class);
                }
                else if(difficulty == 0) {
                    difficulte = new Intent(context, FacileActivity.class);
                }
                else if(difficulty == 1) {
                    difficulte = new Intent(context, MoyenActivity.class);
                }
                else {
                    difficulte = new Intent(context, DifficileActivity.class);
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
            Intent retour = new Intent(context, RoomListActivity.class);
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
                if(multi) {
                    difficulte = new Intent(context, RoomListActivity.class);
                }
                else if(evolution) {
                    difficulte = new Intent(context, LevelActivity.class);
                }
                else if(difficulty == 0) {
                    difficulte = new Intent(context, FacileActivity.class);
                }
                else if(difficulty == 1) {
                    difficulte = new Intent(context, MoyenActivity.class);
                }
                else {
                    difficulte = new Intent(context, DifficileActivity.class);
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
            soundtheme.setLooping(true);
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
                            int newPoints = oldPoints - 1;
                            refUsers.child(player1).child("victoiresMultifactor").setValue(newPoints);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                    AlertDialog.Builder defaite = new AlertDialog.Builder(MultiFactorActivity.this, R.style.MyDialogTheme);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(MultiFactorActivity.this).inflate(R.layout.custom_popup_multi1, viewGroup, false);
                    defaite.setView(dialogView);
                    AlertDialog alertDialog = defaite.create();

                    TextView votreScore = dialogView.findViewById(R.id.text_votre_score1);
                    TextView monScore = dialogView.findViewById(R.id.text_son_score1);
                    Button quitter = dialogView.findViewById(R.id.button_quitter_multi1);
                    LinearLayout popup_back = dialogView.findViewById(R.id.layout_popup_multi1);
                    popup_back.setBackground(getDrawable(R.drawable.popup_defaite_mj1));

                    votreScore.setText("" + scorePlayer2);
                    monScore.setText("" + scorePlayer1);

                    quitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent back = new Intent(context, RoomListActivity.class);
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
                            int oldPoints1 = Math.toIntExact((Long) snapshot.child(player1).child("victoiresMultifactor").getValue());
                            int newPoints1 = oldPoints1 - 1;
                            int oldPoints2 = Math.toIntExact((Long) snapshot.child(player2).child("victoiresMultifactor").getValue());
                            int newPoints2 = oldPoints2 - 1;
                            refUsers.child(player1).child("victoiresMultifactor").setValue(newPoints1);
                            refUsers.child(player2).child("victoiresMultifactor").setValue(newPoints2);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                    AlertDialog.Builder nul = new AlertDialog.Builder(MultiFactorActivity.this, R.style.MyDialogTheme);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(MultiFactorActivity.this).inflate(R.layout.custom_popup_multi1, viewGroup, false);
                    nul.setView(dialogView);
                    AlertDialog alertDialog = nul.create();

                    TextView votreScore = dialogView.findViewById(R.id.text_votre_score1);
                    TextView monScore = dialogView.findViewById(R.id.text_son_score1);
                    Button quitter = dialogView.findViewById(R.id.button_quitter_multi1);
                    LinearLayout popup_back = dialogView.findViewById(R.id.layout_popup_multi1);
                    popup_back.setBackground(getDrawable(R.drawable.popup_egalite_mj1));

                    votreScore.setText("" + scorePlayer2);
                    monScore.setText("" + scorePlayer1);

                    quitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rDatabase.removeValue();
                            Intent back = new Intent(context, RoomListActivity.class);
                            startActivity(back);
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
                            int oldPoints = Math.toIntExact((Long) snapshot.child(player2).child("victoiresMultifactor").getValue());
                            int newPoints = oldPoints - 1;
                            refUsers.child(player2).child("victoiresMultifactor").setValue(newPoints);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                    AlertDialog.Builder bravo = new AlertDialog.Builder(MultiFactorActivity.this, R.style.MyDialogTheme);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(MultiFactorActivity.this).inflate(R.layout.custom_popup_multi1, viewGroup, false);
                    bravo.setView(dialogView);
                    AlertDialog alertDialog = bravo.create();

                    TextView votreScore = dialogView.findViewById(R.id.text_votre_score1);
                    TextView monScore = dialogView.findViewById(R.id.text_son_score1);
                    Button quitter = dialogView.findViewById(R.id.button_quitter_multi1);
                    LinearLayout popup_back = dialogView.findViewById(R.id.layout_popup_multi1);
                    popup_back.setBackground(getDrawable(R.drawable.popup_victoire_mj1));

                    votreScore.setText("" + scorePlayer2);
                    monScore.setText("" + scorePlayer1);

                    quitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rDatabase.removeValue();
                            Intent back = new Intent(context, RoomListActivity.class);
                            startActivity(back);
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