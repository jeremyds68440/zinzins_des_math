package com.example.zinzins_des_math;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    EditText registerEmail, registerPassword, registerUsername;
    ImageView register;
    TextView toLogin;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmail = (EditText) findViewById(R.id.registerEmail);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        registerUsername = (EditText) findViewById(R.id.registerUsername);
        register = (ImageView) findViewById(R.id.register);
        toLogin = (TextView) findViewById(R.id.btnToLogin);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("users");
        fAuth = FirebaseAuth.getInstance();
        user = new User();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MultiplayerActivity.class));
            finish();
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString().trim();
                String username = registerUsername.getText().toString();
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Remplir tous les champs", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    registerUser(email, username, password);
                }
            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }
    public void registerUser(String email, String username, String password){
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Connecté",Toast.LENGTH_SHORT).show();
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setzAvatar(3);
                    updateUI(user);
                }else{
                    Toast.makeText(RegisterActivity.this,"Echec d'authentification",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void updateUI(User user){
        mDatabase.child(fAuth.getCurrentUser().getUid()).setValue(user);
        Toast.makeText(RegisterActivity.this, "Votre compte a été créé", Toast.LENGTH_SHORT).show();
        Intent loginIntent = new Intent(this, MultiplayerActivity.class);
        startActivity(loginIntent);
        finish();
    }
}