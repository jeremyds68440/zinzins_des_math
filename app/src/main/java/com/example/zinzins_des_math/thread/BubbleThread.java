package com.example.zinzins_des_math.thread;

import com.example.zinzins_des_math.FirstGameActivity;

public class BubbleThread extends Thread {
    private FirstGameActivity context;
    private boolean win;

    public BubbleThread(FirstGameActivity context, boolean win) {
        this.context = context;
        this.win = win;
    }

    public void run() {
        try {
                Thread.sleep(3000);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        if(win) {
            context.resetCounter();
            context.newTarget();
        }
        else {
            context.resetCounter();
        }
    }

}
