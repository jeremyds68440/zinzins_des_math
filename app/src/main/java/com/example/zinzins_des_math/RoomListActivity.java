package com.example.zinzins_des_math;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

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

public class RoomListActivity extends AppCompatActivity {

    ListView listView;
    Button toLeaderboard, button;
    ImageView back;

    List<String> roomsList;

    String username = "";
    String roomName = "";
    String gameName;

    FirebaseUser user;
    DatabaseReference uDatabase;
    DatabaseReference gDatabase;
    FirebaseDatabase database;
    DatabaseReference roomRef;
    DatabaseReference roomGame;
    DatabaseReference roomsRef;

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
        setContentView(R.layout.activity_room_list);
        loadData();
        if (sound_theme_state) {
            SplashScreenActivity.general_sound.start();
        }

        toLeaderboard = findViewById(R.id.toLeaderboard);
        toLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_effect_state) {
                    bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                back.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent main = new Intent(getApplicationContext(), LeaderboardActivity.class);
                startActivity(main);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        back = findViewById(R.id.back_choose);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_effect_state) {
                    bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                back.setColorFilter(Color.argb(80, 0, 0, 0));
                Intent main = new Intent(getApplicationContext(), ChooseSoloMultiActivity.class);
                startActivity(main);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        database = FirebaseDatabase.getInstance();

        listView = (ListView) findViewById(R.id.roomList);
        button = (Button) findViewById(R.id.createRoom);

        roomsList = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        uDatabase = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        gDatabase = FirebaseDatabase.getInstance().getReference("rooms");

        uDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                final User utilisateur = new User();
                final Field[] fields = utilisateur.getClass().getDeclaredFields();
                username = (String) dataSnapshot.child(fields[7].getName()).getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_effect_state) {
                    bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                button.setText("CREATING ROOM");
                button.setEnabled(false);
                PopupMenu game = new PopupMenu(RoomListActivity.this, v);
                game.getMenuInflater().inflate(R.menu.menu_popup, game.getMenu());
                game.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.roomMultifactorFacile:
                                roomName = username+" : "+"Multifactor Facile";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("MultifactorFacile");
                                database.getReference("rooms/"+roomName+"/scorePlayer1").setValue(0);
                                addRoomEventListener(MultiFactorActivity.class,0,"host", roomName);
                                return true;
                            case R.id.roomMultifactorMoyen:
                                roomName = username+" : "+"Multifactor Moyen";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("MultifactorMoyen");
                                database.getReference("rooms/"+roomName+"/scorePlayer1").setValue(0);
                                addRoomEventListener(MultiFactorActivity.class,1,"host", roomName);
                                return true;
                            case R.id.roomMultifactorDifficile:
                                roomName = username+" : "+"Multifactor Difficile";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("MultifactorDifficile");
                                database.getReference("rooms/"+roomName+"/scorePlayer1").setValue(0);
                                addRoomEventListener(MultiFactorActivity.class,2,"host", roomName);
                                return true;
                            case R.id.roomMathemaquizzFacile:
                                roomName = username+" : "+"Mathemaquizz Facile";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("MathemaquizzFacile");
                                database.getReference("rooms/"+roomName+"/scorePlayer1").setValue(0);
                                addRoomEventListener(MathemaQuizzActivity.class,0,"host", roomName);
                                return true;
                            case R.id.roomMathemaquizzMoyen:
                                roomName = username+" : "+"Mathemaquizz Moyen";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("MathemaquizzMoyen");
                                database.getReference("rooms/"+roomName+"/scorePlayer1").setValue(0);
                                addRoomEventListener(MathemaQuizzActivity.class,1,"host", roomName);
                                return true;
                            case R.id.roomMathemaquizzDifficile:
                                roomName = username+" : "+"Mathemaquizz Difficile";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("MathemaquizzDifficile");
                                database.getReference("rooms/"+roomName+"/scorePlayer1").setValue(0);
                                addRoomEventListener(MathemaQuizzActivity.class,2,"host", roomName);
                                return true;
                        }
                        return false;
                    }
                });
                button.setEnabled(true);
                game.show();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (sound_effect_state) {
                    bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
                    bouton_sound.start();
                }
                String toRoomName = roomsList.get(position);
                String[] extract = toRoomName.split(" : ");
                roomName = extract[0];
                gameName = extract[1];
                roomRef = database.getReference("rooms/"+toRoomName+"/player2");
                database.getReference("rooms/"+toRoomName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!user.getUid().equals(snapshot.child("player1").getValue())){
                            switch (gameName){
                                case "Multifactor Facile":
                                    roomRef.setValue(user.getUid());
                                    addRoomEventListener(MultiFactorActivity.class,0,"client", toRoomName);
                                    break;
                                case "Multifactor Moyen":
                                    roomRef.setValue(user.getUid());
                                    addRoomEventListener(MultiFactorActivity.class,1,"client", toRoomName);
                                    break;
                                case "Multifactor Difficile":
                                    roomRef.setValue(user.getUid());
                                    addRoomEventListener(MultiFactorActivity.class,2,"client", toRoomName);
                                    break;
                                case "Mathemaquizz Facile":
                                    roomRef.setValue(user.getUid());
                                    addRoomEventListener(MathemaQuizzActivity.class,0,"client", toRoomName);
                                    break;
                                case "Mathemaquizz Moyen":
                                    roomRef.setValue(user.getUid());
                                    addRoomEventListener(MathemaQuizzActivity.class,1,"client", toRoomName);
                                    break;
                                case "Mathemaquizz Difficile":
                                    roomRef.setValue(user.getUid());
                                    addRoomEventListener(MathemaQuizzActivity.class,2,"client", toRoomName);
                                    break;
                                case "Roulette Facile":
                                    roomRef.setValue(user.getUid());
                                    addRoomEventListener(RouletteActivity.class,0,"client", toRoomName);
                                    break;
                                case "Roulette Moyen":
                                    roomRef.setValue(user.getUid());
                                    addRoomEventListener(RouletteActivity.class,1,"client", toRoomName);
                                    break;
                                case "Roulette Difficile":
                                    roomRef.setValue(user.getUid());
                                    addRoomEventListener(RouletteActivity.class,2,"client", toRoomName);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        addRoomsEventListener();
    }

    private void addRoomEventListener(Class jeu, int difficulty, String role, String roomName){
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                button.setText("Create room");
                button.setEnabled(true);
                Intent intent = new Intent(getApplicationContext(), jeu);
                intent.setFlags(difficulty);
                intent.putExtra("defi","defi");
                intent.putExtra("role",role);
                intent.putExtra("roomName", roomName);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                button.setText("Create room");
                button.setEnabled(true);
                Toast.makeText(RoomListActivity.this, "Error !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addRoomsEventListener() {
        roomsRef = database.getReference("rooms");
        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomsList.clear();
                Iterable<DataSnapshot> rooms = dataSnapshot.getChildren();
                for (DataSnapshot snapshot : rooms) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_textview, roomsList);
                    listView.setAdapter(adapter);
                    roomsList.add(snapshot.getKey());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onBackPressed() {
        if (sound_effect_state) {
            bouton_sound = MediaPlayer.create(getApplicationContext(), R.raw.bouton_sound);
            bouton_sound.start();
        }
        back.setColorFilter(Color.argb(80, 0, 0, 0));
        Intent main = new Intent(getApplicationContext(), ChooseSoloMultiActivity.class);
        startActivity(main);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sound_theme_state) {
            SplashScreenActivity.general_sound.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (sound_theme_state) {
            SplashScreenActivity.general_sound.start();

        }
    }
}