package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zinzins_des_math.adapter.BubbleItemAdapter;
import com.example.zinzins_des_math.models.BubbleItem;

import java.util.ArrayList;
import java.util.List;

public class FirstGameActivity extends AppCompatActivity {

    private DisplayMetrics displayMetrics;
    private RelativeLayout activity;
    private Context context;
    private GridView grid;
    private int counter = 0;
    private int countMult = 0;
    private int difficulty;
    private int target;
    private TextView targetText;
    private TextView countText;
    private boolean blocked = false;
    public static final int BUBBLECOLUMN = 190;
    public static final int BUBBLEROW = 190;
    public static final int NUMBERBUBBLEROW = 6;
    public static final int NUMBERBUBBLECOLUMN = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_game);
        activity = (RelativeLayout) findViewById(R.id.grid_relative_layout);
        context = getApplicationContext();

        ImageView back = findViewById(R.id.first_game_back);

        difficulty = getIntent().getFlags();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQuitButton();
            }
        });

        targetText = findViewById(R.id.bubbleTarget);
        targetText.setTextColor(context.getResources().getColor(R.color.first_game_text));
        newTarget();

        countText = findViewById(R.id.bubbleScore);
        resetCounter();


        List<BubbleItem> bubbles = new ArrayList<BubbleItem>();
        ImageView number;
        for(int i = 0; i < NUMBERBUBBLECOLUMN*NUMBERBUBBLEROW; i++) {
            bubbles.add(new BubbleItem((int) (Math.random()*8+2)));
        }

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        /*System.out.println("Dpi : " + displayMetrics.densityDpi);
        System.out.println("Densité : " + displayMetrics.density);
        System.out.println("Scale Densité : " + displayMetrics.scaledDensity);
        System.out.println("XDpi : " + displayMetrics.xdpi);
        System.out.println("YDpi : " + displayMetrics.ydpi);*/


        RelativeLayout.LayoutParams gridParams = new RelativeLayout.LayoutParams(NUMBERBUBBLECOLUMN*BUBBLECOLUMN+100,BUBBLEROW*NUMBERBUBBLEROW);
        RelativeLayout.LayoutParams bgParams = new RelativeLayout.LayoutParams(displayMetrics.widthPixels,BUBBLEROW*NUMBERBUBBLEROW + 100);
        gridParams.setMargins((displayMetrics.widthPixels - (NUMBERBUBBLECOLUMN*BUBBLECOLUMN+70))/2,30,0,0);
        grid = new GridView(getApplicationContext());
        grid.setNumColumns(NUMBERBUBBLECOLUMN);
        grid.setLayoutParams(gridParams);
        grid.setAdapter(new BubbleItemAdapter(this, bubbles));

        ImageView bg = findViewById(R.id.grid_bg);
        bg.setLayoutParams(bgParams);

        activity.addView(grid);
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

    public void verify() {
        ImageView test = findViewById(R.id.mesure);
        System.out.println("80dp = " + test.getHeight());
        System.out.println("width : " + displayMetrics.widthPixels + " height : " + displayMetrics.heightPixels);
        if(counter >= target) {
            blocked = true;
            setGameOver(counter == target);
        }
    }

    public void setGameOver(boolean win) {
        AlertDialog.Builder popup = new AlertDialog.Builder(this);
        String positive;
        if(win) {
            countText.setTextColor(context.getResources().getColor(R.color.win));
            popup.setTitle("Bravo !");
            popup.setMessage("Cible atteinte !");
            positive = "Rejouer";
        }
        else {
            countText.setTextColor(context.getResources().getColor(R.color.lose));
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
                Intent difficulte = new Intent(getApplicationContext(), DifficultyActivity.class);
                startActivity(difficulte);
                finish();
            }
        });

        popup.setCancelable(false);
        popup.show();
    }

    public void setQuitButton() {
        AlertDialog.Builder quit = new AlertDialog.Builder(this);
        quit.setTitle("Quitter");
        quit.setMessage("Êtes-vous sûre de vouloir quitter ?");
        quit.setNegativeButton("Retour", null);
        quit.setPositiveButton("Quitter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent difficulte = new Intent(getApplicationContext(), DifficultyActivity.class);
                startActivity(difficulte);
                finish();
            }
        });
        quit.show();
    }

    public void resetCounter() {
        countText.setTextColor(context.getResources().getColor(R.color.first_game_text));
        countText.setText("0");
        counter = 0;
    }

    public void newTarget() {
        target = 1;
        int i = 0;
        while(i < difficulty + 2 || target < Math.pow(10, difficulty+1)) {
            target *= (int)(Math.random()*9)+2;
            i++;
        }
        targetText.setText("" + target);
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void unBlocked() {
        blocked = false;
    }
}