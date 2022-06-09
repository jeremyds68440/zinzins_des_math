package com.example.zinzins_des_math;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Equation> equation;

    public List<Equation> getEquation() {
        return equation;
    }

    public void setEquation(List<Equation> equation) {
        this.equation = equation;
    }

    public Equation getCurrentEquation() {
        return currentEquation;
    }

    public void setCurrentEquation(Equation currentEquation) {
        this.currentEquation = currentEquation;
    }

    public int getNumberCorrect() {
        return numberCorrect;
    }

    public void setNumberCorrect(int numberCorrect) {
        this.numberCorrect = numberCorrect;
    }

    public int getNumberIncorrect() {
        return numberIncorrect;
    }

    public void setNumberIncorrect(int numberIncorrect) {
        this.numberIncorrect = numberIncorrect;
    }

    public int getTotalEquations() {
        return totalEquations;
    }

    public void setTotalEquations(int totalEquations) {
        this.totalEquations = totalEquations;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private Equation currentEquation;
    private int numberCorrect,numberIncorrect,totalEquations,score;

    public Game(){
        numberCorrect = 0;
        numberIncorrect = 0;
        totalEquations = 0;
        score = 0;
        currentEquation = new Equation(10);
        equation = new ArrayList<Equation>();
    }

    public void newEquation(){
        currentEquation = new Equation(totalEquations * 2+5);
        totalEquations ++;
        equation.add(currentEquation);
    }

    public boolean checkAnswer(int submittedAnswer){
        boolean isCorrect;
        if(currentEquation.getAnswer() == submittedAnswer){
            numberCorrect++;
            isCorrect=true;
        }else {
            numberIncorrect++;
            isCorrect=false;
        }
        score = numberCorrect *10 - numberIncorrect * 5;
        return isCorrect;
    }
}
