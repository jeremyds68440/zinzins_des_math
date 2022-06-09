package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

    private RelativeLayout activity;
    private Context context;
    private int counter = 0;
    private int countMult = 0;
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
        System.out.println(findViewById(R.id.btn_ans1));
        activity = (RelativeLayout) findViewById(R.id.grid_relative_layout);
        context = getApplicationContext();

        ImageView back = findViewById(R.id.first_game_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent main = new Intent(getApplicationContext(), DifficultyActivity.class);
                startActivity(main);
                finish();
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
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(NUMBERBUBBLECOLUMN*BUBBLECOLUMN+100,BUBBLEROW*NUMBERBUBBLEROW);
        params.setMargins(120,30,0,0);
        GridView grid = new GridView(getApplicationContext());
        grid.setLayoutParams(params);
        grid.setNumColumns(NUMBERBUBBLECOLUMN);

        grid.setAdapter(new BubbleItemAdapter(this, bubbles));


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
        if(counter > target) {
            countText.setTextColor(context.getResources().getColor(R.color.lose));
            blocked = true;

            AlertDialog.Builder lose = new AlertDialog.Builder(this);
            lose.setTitle("Perdu !");
            lose.setMessage("Cible dépassée !");
            lose.setPositiveButton("Réssayer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    resetCounter();
                    unBlocked();
                }
            });
            lose.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent difficulte = new Intent(getApplicationContext(), DifficultyActivity.class);
                    startActivity(difficulte);
                    finish();
                }
            });

            lose.setCancelable(false);
            lose.show();
        }
        else if(counter == target) {
            countText.setTextColor(context.getResources().getColor(R.color.win));
            blocked = true;

            AlertDialog.Builder win = new AlertDialog.Builder(this);
            win.setTitle("Bravo !");
            win.setMessage("Cible atteinte !");
            win.setPositiveButton("Rejouer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    resetCounter();
                    newTarget();
                    unBlocked();
                }
            });
            win.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent difficulte = new Intent(getApplicationContext(), DifficultyActivity.class);
                    startActivity(difficulte);
                    finish();
                }
            });

            win.setCancelable(false);
            win.show();
        }
    }

    public void resetCounter() {
        countText.setTextColor(context.getResources().getColor(R.color.first_game_text));
        countText.setText("0");
        counter = 0;
    }

    public void newTarget() {
        target = 1;
        for(int i = 0; i < 3; i++) {
            target *= (int)(Math.random()*9)+2;
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