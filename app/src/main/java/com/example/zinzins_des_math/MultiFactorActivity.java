package com.example.zinzins_des_math;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
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
    int points = 0;
    int numberoftry = 1;
    private int counter = 0;
    private int countMult = 0;
    private int difficulty;
    private int target;
    private TextView targetCount;
    private TextView scoreCount;
    private boolean blocked = false;
    public int BUBBLECOLUMN = 190;
    public int BUBBLEROW = 190;
    public static final int NUMBERBUBBLEROW = 6;
    public static final int NUMBERBUBBLECOLUMN = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multifactor);

        difficulty = getIntent().getFlags();

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


        LinearLayout layout = findViewById(R.id.multifactor);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)getResources().getDimension(R.dimen.wight_backbutton), (int)getResources().getDimension(R.dimen.height_backbutton));
        params.setMargins((int)(16*displayrealMetrics.density),(int)(44*displayrealMetrics.density),0,0);
        ImageView back = new ImageView(getApplicationContext());
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

        ImageView scorePlace = new ImageView(getApplicationContext());
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

        ImageView targetPlace = new ImageView(getApplicationContext());
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

        ImageView gridBackground = new ImageView(getApplicationContext());
        params = new LinearLayout.LayoutParams(width, gridBgHeight);
        gridBackground.setLayoutParams(params);

        setDifficulty(layout, scorePlace, targetPlace, gridBackground, getIntent().getFlags());

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
        for(int i = 0; i < NUMBERBUBBLECOLUMN*NUMBERBUBBLEROW; i++) {
            bubbles.add(new BubbleItem((int) (Math.random()*8+2)));
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

    public void setDifficulty(LinearLayout layout, ImageView scorePlace, ImageView targetPlace, ImageView gridBackground, int difficulty) {
        this.difficulty = difficulty;
        if(difficulty == 0) {
            layout.setBackground(getDrawable(R.drawable.multifactor_bg_facile));
            scorePlace.setImageResource(R.drawable.multifactor_score_facile);
            targetPlace.setImageResource(R.drawable.multifactor_target_facile);
            gridBackground.setImageResource(R.drawable.multifactor_terrain_facile);
        }
        else if(difficulty == 1) {
            layout.setBackground(getDrawable(R.drawable.multifactor_bg_moyen));
            scorePlace.setImageResource(R.drawable.multifactor_score_moyen);
            targetPlace.setImageResource(R.drawable.multifactor_target_moyen);
            gridBackground.setImageResource(R.drawable.multifactor_terrain_moyen);
        }
        else if(difficulty == 2) {
            layout.setBackground(getDrawable(R.drawable.multifactor_bg_difficile));
            scorePlace.setImageResource(R.drawable.multifactor_score_difficile);
            targetPlace.setImageResource(R.drawable.multifactor_target_difficile);
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
            if(counter == target) {
                int varMult = (difficulty+2)*2;
                point += 120 * ((float)(varMult - countMult + getMinMult() + difficulty*2) /varMult/numberoftry);
                numberoftry = 1;
                countMult = 0;
                points += point;
            }
            else {
                countMult = 0;
                numberoftry++;
            }
            setGameOver(counter == target, point);
        }
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

    public void setGameOver(boolean win , int point) {
        AlertDialog.Builder popup = new AlertDialog.Builder(this);
        String positive;
        if(win) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference mDatabase = database.getReference("users").child(user.getUid());
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int dbScore;
                    final User utilisateur = new User();
                    final Field[] fields = utilisateur.getClass().getDeclaredFields();
                    switch (getIntent().getFlags()) {
                        case 0 :
                            dbScore = Math.toIntExact((long) dataSnapshot.child(fields[5].getName()).getValue());
                            if (dbScore < points)
                                mDatabase.child("scoreMultifactorFacile").setValue(points);
                            break;
                        case 1 :
                            dbScore = Math.toIntExact((long) dataSnapshot.child(fields[6].getName()).getValue());
                            if (dbScore < points)
                                mDatabase.child("scoreMultifactorMoyen").setValue(points);
                            break;
                        case 2 :
                            dbScore = Math.toIntExact((long) dataSnapshot.child(fields[4].getName()).getValue());
                            if (dbScore < points)
                                mDatabase.child("scoreMultifactorDifficile").setValue(points);
                            break;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            scoreCount.setTextColor(context.getResources().getColor(R.color.win));
            popup.setTitle("Bravo !");
            popup.setMessage("Cible atteinte !\nVous avez gagné " + point + " points.\nVous avez " + points + " points au total.");
            positive = "Continuer";
        }
        else {
            scoreCount.setTextColor(context.getResources().getColor(R.color.lose));
            popup.setTitle("Perdu !");
            popup.setMessage("Cible dépassée !");
            positive = "Réssayer";
        }
        popup.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(win) {
                    newTarget();
                }
                resetCounter();
                unBlocked();
            }
        });
        popup.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent difficulte;
                if(difficulty == 0) {
                    difficulte = new Intent(getApplicationContext(), FacileActivity.class);
                }
                else if(difficulty == 1) {
                    difficulte = new Intent(getApplicationContext(), MoyenActivity.class);
                }
                else {
                    difficulte = new Intent(getApplicationContext(), DifficileActivity.class);
                }
                startActivity(difficulte);
                finish();
            }
        });

        popup.setCancelable(false);
        popup.show();
    }

    public void setQuitPopup() {
        AlertDialog.Builder quit = new AlertDialog.Builder(this);
        quit.setTitle("Quitter");
        quit.setMessage("Êtes-vous sûre de vouloir quitter ?");
        quit.setNegativeButton("Retour", null);
        quit.setPositiveButton("Quitter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent difficulte;
                if(difficulty == 0) {
                    difficulte = new Intent(getApplicationContext(), FacileActivity.class);
                }
                else if(difficulty == 1) {
                    difficulte = new Intent(getApplicationContext(), MoyenActivity.class);
                }
                else {
                    difficulte = new Intent(getApplicationContext(), DifficileActivity.class);
                }
                startActivity(difficulte);
                finish();
            }
        });
        quit.show();
    }

    public void resetCounter() {
        scoreCount.setTextColor(context.getResources().getColor(R.color.first_game_text));
        scoreCount.setText("0");
        counter = 0;
    }

    public void newTarget() {
        target = 1;
        int i = 0;
        while(i < difficulty + 2 || target < Math.pow(10, difficulty+1)) {
            target *= (int)(Math.random()*9)+2;
            i++;
        }
        targetCount.setText("" + target);
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void unBlocked() {
        blocked = false;
    }
}