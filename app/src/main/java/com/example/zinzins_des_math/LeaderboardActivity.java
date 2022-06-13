package com.example.zinzins_des_math;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    ImageView leadMultifactor,leadMathemaquizz, leadRoulette,leadFacile,leadMoyen,leadDifficile;
    DatabaseReference mDatabase;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("users");
        listView = (ListView) findViewById(R.id.leaderboard);
        leadMultifactor = (ImageView) findViewById(R.id.leadMultifactor);
        leadMathemaquizz = (ImageView) findViewById(R.id.leadMathemaquizz);
        leadRoulette = (ImageView) findViewById(R.id.leadRoulette);
        leadFacile = (ImageView) findViewById(R.id.leadFacile);
        leadMoyen = (ImageView) findViewById(R.id.leadMoyen);
        leadDifficile = (ImageView) findViewById(R.id.leadDifficile);
        leadMultifactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leadFacile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFacile("Multifactor", 5);
                    }
                });
                leadMoyen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMoyen("Multifactor", 6);
                    }
                });
                leadDifficile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDifficile("Multifactor", 4);
                    }
                });
            }
        });
        leadMathemaquizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leadFacile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFacile("Mathemaquizz", 2);
                    }
                });
                leadMoyen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMoyen("Mathemaquizz", 3);
                    }
                });
                leadDifficile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDifficile("Mathemaquizz", 1);
                    }
                });
            }
        });
        leadRoulette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leadFacile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFacile("Roulette", 8);
                    }
                });
                leadMoyen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMoyen("Roulette", 9);
                    }
                });
                leadDifficile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDifficile("Roulette", 7);
                    }
                });
            }
        });
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
                String value =  Long.toString((long) dataSnapshot.child(fields[dbNumber].getName()).getValue());
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
                String value =  Long.toString((long) dataSnapshot.child(fields[dbNumber].getName()).getValue());
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
                String value =  Long.toString((long) dataSnapshot.child(fields[dbNumber].getName()).getValue());
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