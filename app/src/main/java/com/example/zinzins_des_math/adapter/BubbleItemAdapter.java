package com.example.zinzins_des_math.adapter;

import android.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zinzins_des_math.MultiFactorActivity;
import com.example.zinzins_des_math.R;
import com.example.zinzins_des_math.models.BubbleItem;

import java.util.List;

public class BubbleItemAdapter extends BaseAdapter {

    private MultiFactorActivity context;
    private List<BubbleItem> bubbles;
    private LayoutInflater inflater;

    public BubbleItemAdapter(MultiFactorActivity context, List<BubbleItem> bubbles) {
        this.context = context;
        this.bubbles = bubbles;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bubbles.size();
    }

    @Override
    public BubbleItem getItem(int position) {
        return bubbles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView image = new ImageView(context);
        ViewGroup.LayoutParams params = new ActionBar.LayoutParams(context.bubbleSize ,context.bubbleSize);

        BubbleItem bubble = getItem(position);
        image.setLayoutParams(params);
        String imageName = bubble.getName();
        int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        image.setImageResource(resId);
        image.setPadding(2,2,2,2);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!context.isBlocked()) {
                    context.decreaseByOne(bubble.getNumber());
                    int i = context.getLessPlaced();
                    String imageName = "bubble_" + i;
                    int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                    image.setImageResource(resId);
                    context.multCounter(bubble.getNumber());
                    TextView scoreCount = context.getScoreCount();
                    scoreCount.setText("" + context.getCounter());
                    bubble.setNumber(i);
                    context.verify();
                }
            }
        });

        return image;
    }
}
