package com.example.zinzins_des_math;

import java.util.HashMap;
import java.util.Map;

public class User {

    public String email;
    public int scoreMathemaquizzDifficile;
    public int scoreMathemaquizzFacile;
    public int scoreMathemaquizzMoyen;
    public int scoreMultifactorDifficile;
    public int scoreMultifactorFacile;
    public int scoreMultifactorMoyen;
    public String username;
    public int victoiresMathemaquizz;
    public int victoiresMultifactor;
    public int zAvatar;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScoreMathemaquizzFacile() {
        return scoreMathemaquizzFacile;
    }

    public void setScoreMathemaquizzFacile(int scoreMathemaquizzFacile) {
        this.scoreMathemaquizzFacile = scoreMathemaquizzFacile;
    }

    public int getScoreMathemaquizzMoyen() {
        return scoreMathemaquizzMoyen;
    }

    public void setScoreMathemaquizzMoyen(int scoreMathemaquizzMoyen) {
        this.scoreMathemaquizzMoyen = scoreMathemaquizzMoyen;
    }

    public int getScoreMathemaquizzDifficile() {
        return scoreMathemaquizzDifficile;
    }

    public void setScoreMathemaquizzDifficile(int scoreMathemaquizzDifficile) {
        this.scoreMathemaquizzDifficile = scoreMathemaquizzDifficile;
    }

    public int getScoreMultifactorFacile() {
        return scoreMultifactorFacile;
    }

    public void setScoreMultifactorFacile(int scoreMultifactorFacile) {
        this.scoreMultifactorFacile = scoreMultifactorFacile;
    }

    public int getScoreMultifactorMoyen() {
        return scoreMultifactorMoyen;
    }

    public void setScoreMultifactorMoyen(int scoreMultifactorMoyen) {
        this.scoreMultifactorMoyen = scoreMultifactorMoyen;
    }

    public int getScoreMultifactorDifficile() {
        return scoreMultifactorDifficile;
    }

    public void setScoreMultifactorDifficile(int scoreMultifactorDifficile) {
        this.scoreMultifactorDifficile = scoreMultifactorDifficile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getVictoiresMathemaquizz() {
        return victoiresMathemaquizz;
    }

    public void setVictoiresMathemaquizz(int victoiresMathemaquizz) {
        this.victoiresMathemaquizz = victoiresMathemaquizz;
    }

    public int getVictoiresMultifactor() {
        return victoiresMultifactor;
    }

    public void setVictoiresMultifactor(int victoiresMultifactor) {
        this.victoiresMultifactor = victoiresMultifactor;
    }

    public int getzAvatar() {
        return zAvatar;
    }

    public void setzAvatar(int zAvatar) {
        this.zAvatar = zAvatar;
    }
}