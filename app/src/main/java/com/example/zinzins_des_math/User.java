package com.example.zinzins_des_math;

import java.util.HashMap;
import java.util.Map;

public class User {

    public String email;
    public int scoreMathemaquizz;
    public int scoreMultifactor;
    public String username;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, int scoreMathemaquizz, int scoreMultifactor, String username) {
        this.email = email;
        this.scoreMathemaquizz = scoreMathemaquizz;
        this.scoreMultifactor = scoreMultifactor;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScoreMultifactor() { return scoreMultifactor; }

    public void setScoreMultifactor(int scoreMultifactor) { this.scoreMultifactor = scoreMultifactor; }

    public int getScoreMathemaquizz() {
        return scoreMathemaquizz;
    }

    public void setScoreMathemaquizz(int scoreMathemaquizz) { this.scoreMathemaquizz = scoreMathemaquizz; }

}