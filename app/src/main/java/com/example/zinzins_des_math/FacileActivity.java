package com.example.zinzins_des_math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FacileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facile);

        ImageView multifactor = findViewById(R.id.multifactor_facile);
        ImageView mathemaquizz = findViewById(R.id.mathemaquizz_facile);

        actionClickImagefacile(multifactor, FirstGameActivity.class);
        actionClickImagefacile(mathemaquizz, SecondGameActivity.class);
    }

    private void actionClickImagefacile(ImageView button, Class act) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent intent = new Intent(getApplicationContext(), act);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }
}