package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zinzins_des_math.adapter.BubbleItemAdapter;
import com.example.zinzins_des_math.models.BubbleItem;
import com.example.zinzins_des_math.thread.BubbleThread;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class FirstGameActivity extends AppCompatActivity {

    private LinearLayout activity;
    private Context context;
    private int counter = 0;
    private int countMult = 0;
    private int target;
    private TextView targetText;
    private TextView countText;
    private boolean blocked = false;
    public static final int BUBBLESIZE = 190;
    public static final int NUMBERBUBBLEROW = 6;
    public static final int NUMBERBUBBLECOLUMN = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_game);

        activity = (LinearLayout) findViewById(R.id.firstGame);
        context = getApplicationContext();

        targetText = findViewById(R.id.bubbleTarget);
        targetText.setTextSize(50);
        targetText.setLastBaselineToBottomHeight(250);
        targetText.setFirstBaselineToTopHeight(150);
        newTarget();

        countText = findViewById(R.id.bubbleScore);
        countText.setTextSize(50);
        countText.setLastBaselineToBottomHeight(150);
        countText.setFirstBaselineToTopHeight(250);
        resetCounter();


        List<BubbleItem> bubbles = new ArrayList<BubbleItem>();
        ImageView number;
        for(int i = 0; i < NUMBERBUBBLECOLUMN*NUMBERBUBBLEROW; i++) {
            bubbles.add(new BubbleItem((int) (Math.random()*8+2)));
        }
        ViewGroup.LayoutParams params = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        GridView grid = new GridView(getApplicationContext());
        grid.setLayoutParams(params);
        grid.setNumColumns(NUMBERBUBBLECOLUMN);

        grid.setAdapter(new BubbleItemAdapter(this, bubbles));
        grid.setGravity(View.TEXT_ALIGNMENT_CENTER);

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
            countText.setTextColor(Color.RED);
            Thread t = new BubbleThread(this, false);
            t.start();
        }
        else if(counter == target) {
            countText.setTextColor(Color.GREEN);
            Thread t = new BubbleThread(this, true);
            t.start();
        }
    }

    public void resetCounter() {
        countText.setTextColor(Color.BLACK);
        countText.setText("Score : 0");
        counter = 0;
    }

    public void newTarget() {
        target = 1;
        for(int i = 0; i < 4; i++) {
            target *= (int)(Math.random()*8)+2;
        }
        targetText.setText("Target : " + target);
    }
}