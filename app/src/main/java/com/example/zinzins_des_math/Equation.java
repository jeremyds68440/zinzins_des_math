package com.example.zinzins_des_math;

import java.util.Random;

public class Equation {
    private int firstNbr,thirdNbr,secondNbr;
    private int answer;
    private int [] answerArray;
    private int answerSol;
    private int maxNbr;
    private String equationPhrase;

    public Equation(int maxNbr, int difficulty){

        if (difficulty == 0){
            this.maxNbr = maxNbr;
            Random randomNbr = new Random();

            this.firstNbr = randomNbr.nextInt(maxNbr);
            this.secondNbr = randomNbr.nextInt(maxNbr);
            this.answer = this.firstNbr + this.secondNbr;
            this.equationPhrase = firstNbr + "+" + secondNbr;

            this.answerSol = randomNbr.nextInt(4);
            this.answerArray = new int[] {0,1,2,3};

            this.answerArray[0] = answer - randomNbr.nextInt(50);
            this.answerArray[1] = answer + randomNbr.nextInt(50);
            this.answerArray[2] = answer - randomNbr.nextInt(50);
            this.answerArray[3] = answer + randomNbr.nextInt(50);

            this.answerArray = shuffleArray(this.answerArray);

            answerArray[answerSol] = answer;
        }

        if (difficulty == 1){
            this.maxNbr = maxNbr;
            Random randomNbr = new Random();

            int type_de_calcule = randomNbr.nextInt(2);
            if (type_de_calcule == 1) {
                this.firstNbr = randomNbr.nextInt(maxNbr);
                this.secondNbr = randomNbr.nextInt(maxNbr);
                this.answer = this.firstNbr + this.secondNbr;
                this.equationPhrase = firstNbr + "+" + secondNbr;
            }else{
                this.firstNbr = randomNbr.nextInt(10);
                this.secondNbr = randomNbr.nextInt(10);
                this.answer = this.firstNbr * this.secondNbr;
                this.equationPhrase = firstNbr + "*" + secondNbr;
            }

            this.answerSol = randomNbr.nextInt(4);
            this.answerArray = new int[] {0,1,2,3};

            this.answerArray[0] = answer - randomNbr.nextInt(100);
            this.answerArray[1] = answer + randomNbr.nextInt(100);
            this.answerArray[2] = answer - randomNbr.nextInt(100);
            this.answerArray[3] = answer + randomNbr.nextInt(100);

            this.answerArray = shuffleArray(this.answerArray);

            answerArray[answerSol] = answer;
        }

        if (difficulty == 2){
            this.maxNbr = maxNbr;
            Random randomNbr = new Random();

            int type_de_calcule = randomNbr.nextInt(2);
            if (type_de_calcule == 1) {
                this.firstNbr = randomNbr.nextInt(maxNbr);
                this.secondNbr = randomNbr.nextInt(maxNbr);
                this.thirdNbr = randomNbr.nextInt(10);
                this.answer = this.firstNbr + this.secondNbr + this.thirdNbr * this.thirdNbr;
                this.equationPhrase = firstNbr + "+" + secondNbr + "+" + thirdNbr + "^2" ;
            }else{
                this.firstNbr = randomNbr.nextInt(20);
                this.secondNbr = randomNbr.nextInt(20);
                this.answer = this.firstNbr * this.secondNbr;
                this.equationPhrase = firstNbr + "*" + secondNbr;
            }

            this.answerSol = randomNbr.nextInt(4);
            this.answerArray = new int[] {0,1,2,3};

            this.answerArray[0] = answer - randomNbr.nextInt(100);
            this.answerArray[1] = answer + randomNbr.nextInt(100);
            this.answerArray[2] = answer - randomNbr.nextInt(100);
            this.answerArray[3] = answer + randomNbr.nextInt(100);

            this.answerArray = shuffleArray(this.answerArray);

            answerArray[answerSol] = answer;
        }
    }

    private int [] shuffleArray(int[] array){
        int index,temp;
        Random randNbr = new Random();

        for(int i = array.length - 1; i > 0;i--){
            index = randNbr.nextInt(i+1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }

    public int getFirstNbr() {
        return firstNbr;
    }

    public void setFirstNbr(int firstNbr) {
        this.firstNbr = firstNbr;
    }

    public int getSecondNbr() {
        return secondNbr;
    }

    public void setSecondNbr(int secondNbr) {
        this.secondNbr = secondNbr;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int[] getAnswerArray() {
        return answerArray;
    }

    public void setAnswerArray(int[] answerArray) {
        this.answerArray = answerArray;
    }

    public int getAnswerSol() {
        return answerSol;
    }

    public void setAnswerSol(int answerSol) {
        this.answerSol = answerSol;
    }

    public int getMaxNbr() {
        return maxNbr;
    }

    public void setMaxNbr(int maxNbr) {
        this.maxNbr = maxNbr;
    }

    public String getEquationPhrase() {
        return equationPhrase;
    }

    public void setEquationPhrase(String equationPhrase) {
        this.equationPhrase = equationPhrase;
    }
}
