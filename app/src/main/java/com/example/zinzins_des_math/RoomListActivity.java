package com.example.zinzins_des_math;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    Button button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

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
                username = (String) dataSnapshot.child(fields[10].getName()).getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                addRoomEventListener(MultiFactorActivity.class,0,"host", roomName);
                                return true;
                            case R.id.roomMultifactorMoyen:
                                roomName = username+" : "+"Multifactor Moyen";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("MultifactorMoyen");
                                addRoomEventListener(MultiFactorActivity.class,1,"host", roomName);
                                return true;
                            case R.id.roomMultifactorDifficile:
                                roomName = username+" : "+"Multifactor Difficile";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("MultifactorDifficile");
                                addRoomEventListener(MultiFactorActivity.class,2,"host", roomName);
                                return true;
                            case R.id.roomMathemaquizzFacile:
                                roomName = username+" : "+"Mathemaquizz Facile";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("MathemaquizzFacile");
                                addRoomEventListener(MathemaQuizzActivity.class,0,"host", roomName);
                                return true;
                            case R.id.roomMathemaquizzMoyen:
                                roomName = username+" : "+"Mathemaquizz Moyen";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("MathemaquizzMoyen");
                                addRoomEventListener(MathemaQuizzActivity.class,1,"host", roomName);
                                return true;
                            case R.id.roomMathemaquizzDifficile:
                                roomName = username+" : "+"Mathemaquizz Difficile";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("MathemaquizzDifficile");
                                addRoomEventListener(MathemaQuizzActivity.class,2,"host", roomName);
                                return true;
                            case R.id.roomRouletteFacile:
                                roomName = username+" : "+"Roulette Facile";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("RouletteFacile");
                                addRoomEventListener(RouletteActivity.class,0,"host", roomName);
                                return true;
                            case R.id.roomRouletteMoyen:
                                roomName = username+" : "+"Roulette Moyen";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("RouletteMoyen");
                                addRoomEventListener(RouletteActivity.class,1,"host", roomName);
                                return true;
                            case R.id.roomRouletteDifficile:
                                roomName = username+" : "+"Roulette Difficile";
                                roomRef = database.getReference("rooms/"+roomName+"/player1");
                                roomRef.setValue(user.getUid());
                                roomGame = database.getReference("rooms/"+roomName+"/jeu");
                                roomGame.setValue("RouletteDifficile");
                                addRoomEventListener(RouletteActivity.class,2,"host", roomName);
                                return true;
                        }
                        return false;
                    }
                });
                game.show();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String toRoomName = roomsList.get(position);
                String[] extract = toRoomName.split(" : ");
                roomName = extract[0];
                gameName = extract[1];
                roomRef = database.getReference("rooms/"+toRoomName+"/player2");
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, roomsList);
                    listView.setAdapter(adapter);
                    roomsList.add(snapshot.getKey());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}