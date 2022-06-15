package com.example.zinzins_des_math;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class LeaderboardActivity extends AppCompatActivity {

    ImageView leadMultifactor,leadMathemaquizz, leadRoulette,leadFacile,leadMoyen,leadDifficile;
    TextView titleLeaderboard;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ETAT_SOUND_THEME = "etat_sound_theme";
    public static final String ETAT_SOUND_EFFECT = "etat_sound_effect";

    private boolean sound_theme_state;
    private boolean sound_effect_state;

    private MediaPlayer bouton_sound;

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sound_theme_state = sharedPreferences.getBoolean(ETAT_SOUND_THEME, true);
        sound_effect_state = sharedPreferences.getBoolean(ETAT_SOUND_EFFECT, true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("users");
        listView = (ListView) findViewById(R.id.leaderboard);
        titleLeaderboard = (TextView) findViewById(R.id.titleLeaderboard);
        leadMultifactor = (ImageView) findViewById(R.id.leadMultifactor);
        leadMathemaquizz = (ImageView) findViewById(R.id.leadMathemaquizz);
        leadRoulette = (ImageView) findViewById(R.id.leadRoulette);
        leadFacile = (ImageView) findViewById(R.id.leadFacile);
        leadMoyen = (ImageView) findViewById(R.id.leadMoyen);
        leadDifficile = (ImageView) findViewById(R.id.leadDifficile);
        loadData();
        ImageView back = findViewById(R.id.back_menu2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_effect_state){
                    MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                back.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent multi = new Intent(getApplicationContext(), RoomListActivity.class);
                startActivity(multi);
                finish();
            }
        });
        leadMultifactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_effect_state){
                    MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                leadMultifactor.setColorFilter(Color.argb(80, 0, 0, 0));
                leadMathemaquizz.setColorFilter(Color.argb(0, 0, 0, 0));
                leadRoulette.setColorFilter(Color.argb(0, 0, 0, 0));

                leadFacile.setColorFilter(Color.argb(0, 0, 0, 0));
                leadMoyen.setColorFilter(Color.argb(0, 0, 0, 0));
                leadDifficile.setColorFilter(Color.argb(0, 0, 0, 0));

                leadFacile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sound_effect_state){
                            MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                            bouton_sound.start();
                        }
                        leadFacile.setColorFilter(Color.argb(80, 0, 0, 0));
                        leadMoyen.setColorFilter(Color.argb(0, 0, 0, 0));
                        leadDifficile.setColorFilter(Color.argb(0, 0, 0, 0));

                        titleLeaderboard.setText("Classement Multifactor Facile");
                        showFacile("Multifactor", 5);
                    }
                });
                leadMoyen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sound_effect_state){
                            MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                            bouton_sound.start();
                        }
                        leadFacile.setColorFilter(Color.argb(0, 0, 0, 0));
                        leadMoyen.setColorFilter(Color.argb(80, 0, 0, 0));
                        leadDifficile.setColorFilter(Color.argb(0, 0, 0, 0));

                        titleLeaderboard.setText("Classement Multifactor Moyen");
                        showMoyen("Multifactor", 6);
                    }
                });
                leadDifficile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sound_effect_state){
                            MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                            bouton_sound.start();
                        }
                        leadFacile.setColorFilter(Color.argb(0, 0, 0, 0));
                        leadMoyen.setColorFilter(Color.argb(0, 0, 0, 0));
                        leadDifficile.setColorFilter(Color.argb(80, 0, 0, 0));

                        titleLeaderboard.setText("Classement Multifactor Difficile");
                        showDifficile("Multifactor", 4);
                    }
                });
            }
        });
        leadMathemaquizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_effect_state){
                    MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                leadMultifactor.setColorFilter(Color.argb(0, 0, 0, 0));
                leadMathemaquizz.setColorFilter(Color.argb(80, 0, 0, 0));
                leadRoulette.setColorFilter(Color.argb(0, 0, 0, 0));

                leadFacile.setColorFilter(Color.argb(0, 0, 0, 0));
                leadMoyen.setColorFilter(Color.argb(0, 0, 0, 0));
                leadDifficile.setColorFilter(Color.argb(0, 0, 0, 0));

                leadFacile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sound_effect_state){
                            MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                            bouton_sound.start();
                        }
                        leadFacile.setColorFilter(Color.argb(80, 0, 0, 0));
                        leadMoyen.setColorFilter(Color.argb(0, 0, 0, 0));
                        leadDifficile.setColorFilter(Color.argb(0, 0, 0, 0));

                        titleLeaderboard.setText("Classement Mathemaquizz Facile");
                        showFacile("Mathemaquizz", 2);
                    }
                });
                leadMoyen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sound_effect_state){
                            MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                            bouton_sound.start();
                        }
                        leadFacile.setColorFilter(Color.argb(0, 0, 0, 0));
                        leadMoyen.setColorFilter(Color.argb(80, 0, 0, 0));
                        leadDifficile.setColorFilter(Color.argb(0, 0, 0, 0));

                        titleLeaderboard.setText("Classement Mathemaquizz Moyen");
                        showMoyen("Mathemaquizz", 3);
                    }
                });
                leadDifficile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sound_effect_state){
                            MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                            bouton_sound.start();
                        }
                        leadFacile.setColorFilter(Color.argb(0, 0, 0, 0));
                        leadMoyen.setColorFilter(Color.argb(0, 0, 0, 0));
                        leadDifficile.setColorFilter(Color.argb(80, 0, 0, 0));

                        titleLeaderboard.setText("Classement Mathemaquizz Difficile");
                        showDifficile("Mathemaquizz", 1);
                    }
                });
            }
        });
        leadRoulette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_effect_state){
                    MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                leadMultifactor.setColorFilter(Color.argb(0, 0, 0, 0));
                leadMathemaquizz.setColorFilter(Color.argb(0, 0, 0, 0));
                leadRoulette.setColorFilter(Color.argb(80, 0, 0, 0));

                leadFacile.setColorFilter(Color.argb(0, 0, 0, 0));
                leadMoyen.setColorFilter(Color.argb(0, 0, 0, 0));
                leadDifficile.setColorFilter(Color.argb(0, 0, 0, 0));

                leadFacile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sound_effect_state){
                            MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                            bouton_sound.start();
                        }
                        leadFacile.setColorFilter(Color.argb(80, 0, 0, 0));
                        leadMoyen.setColorFilter(Color.argb(0, 0, 0, 0));
                        leadDifficile.setColorFilter(Color.argb(0, 0, 0, 0));

                        titleLeaderboard.setText("Classement Roulette Facile");
                        showFacile("Roulette", 8);
                    }
                });
                leadMoyen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sound_effect_state){
                            MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                            bouton_sound.start();
                        }
                        leadFacile.setColorFilter(Color.argb(0, 0, 0, 0));
                        leadMoyen.setColorFilter(Color.argb(80, 0, 0, 0));
                        leadDifficile.setColorFilter(Color.argb(0, 0, 0, 0));

                        titleLeaderboard.setText("Classement Roulette Moyen");
                        showMoyen("Roulette", 9);
                    }
                });
                leadDifficile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sound_effect_state){
                            MediaPlayer bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                            bouton_sound.start();
                        }
                        leadFacile.setColorFilter(Color.argb(0, 0, 0, 0));
                        leadMoyen.setColorFilter(Color.argb(0, 0, 0, 0));
                        leadDifficile.setColorFilter(Color.argb(80, 0, 0, 0));

                        titleLeaderboard.setText("Classement Roulette Difficile");
                        showDifficile("Roulette", 7);
                    }
                });
            }
        });
    }

    public void onBackPressed() {
        Intent multi = new Intent(getApplicationContext(), MultiplayerActivity.class);
        startActivity(multi);
        finish();
    }

    public void showFacile(String game, int dbNumber){
        arrayList.clear();
        Query sortedDatabase = mDatabase.orderByChild("score"+game+"Facile");
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        sortedDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                final User utilisateur = new User();
                final Field[] fields = utilisateur.getClass().getDeclaredFields();
                String username = (String) dataSnapshot.child(fields[10].getName()).getValue();
                Long newValue = (Long) dataSnapshot.child(fields[dbNumber].getName()).getValue();
                String value =  Long.toString(-newValue);
                arrayList.add(username +" : "+ value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void showMoyen(String game, int dbNumber){
        arrayList.clear();
        Query sortedDatabase = mDatabase.orderByChild("score"+game+"Moyen");
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        sortedDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                final User utilisateur = new User();
                final Field[] fields = utilisateur.getClass().getDeclaredFields();
                String username = (String) dataSnapshot.child(fields[10].getName()).getValue();
                Long newValue = (Long) dataSnapshot.child(fields[dbNumber].getName()).getValue();
                String value =  Long.toString(-newValue);
                arrayList.add(username +" : "+ value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void showDifficile(String game, int dbNumber){
        arrayList.clear();
        Query sortedDatabase = mDatabase.orderByChild("score"+game+"Difficile");
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        sortedDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                final User utilisateur = new User();
                final Field[] fields = utilisateur.getClass().getDeclaredFields();
                String username = (String) dataSnapshot.child(fields[10].getName()).getValue();
                Long newValue = (Long) dataSnapshot.child(fields[dbNumber].getName()).getValue();
                String value =  Long.toString(-newValue);
                arrayList.add(username +" : "+ value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}