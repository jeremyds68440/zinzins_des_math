package com.example.zinzins_des_math.models;

import android.app.ActionBar;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BubbleItem {

    private String name = "bubble_";
    private int number;

    public BubbleItem(int number) {
        this.number = number;
    }

    public String getName() {
        return name + number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
