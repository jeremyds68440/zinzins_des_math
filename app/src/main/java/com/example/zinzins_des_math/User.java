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
    public int scoreRouletteDifficile;
    public int scoreRouletteFacile;
    public int scoreRouletteMoyen;
    public String username;
    public int victoiresMathemaquizz;
    public int victoiresMultifactor;
    public int victoiresRoulette;
    public int zAvatar;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    /*public User(String email, int scoreMathemaquizz, int scoreMultifactor, String username) {
        this.email = email;
        this.scoreMathemaquizz = scoreMathemaquizz;
        this.scoreMultifactor = scoreMultifactor;
        this.username = username;
    }*/

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

    public int getScoreRouletteFacile() {
        return scoreRouletteFacile;
    }

    public void setScoreRouletteFacile(int scoreRouletteFacile) {
        this.scoreRouletteFacile = scoreRouletteFacile;
    }

    public int getScoreRouletteMoyen() {
        return scoreRouletteMoyen;
    }

    public void setScoreRouletteMoyen(int scoreRouletteMoyen) {
        this.scoreRouletteMoyen = scoreRouletteMoyen;
    }

    public int getScoreRouletteDifficile() {
        return scoreRouletteDifficile;
    }

    public void setScoreRouletteDifficile(int scoreRouletteDifficile) {
        this.scoreRouletteDifficile = scoreRouletteDifficile;
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

    public int getVictoiresRoulette() {
        return victoiresRoulette;
    }

    public void setVictoiresRoulette(int victoiresRoulette) {
        this.victoiresRoulette = victoiresRoulette;
    }

    public int getzAvatar() {
        return zAvatar;
    }

    public void setzAvatar(int zAvatar) {
        this.zAvatar = zAvatar;
    }
}