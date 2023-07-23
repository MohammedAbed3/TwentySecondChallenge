package com.tegogames.twentysecondchallenge.Database;

public class PlayerModel {


    private long id;
    private String player1Name;
    private String player2Name;
    private int strikes1;
    private int strikes2;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public int getStrikes1() {
        return strikes1;
    }

    public void setStrikes1(int strikes1) {
        this.strikes1 = strikes1;
    }

    public int getStrikes2() {
        return strikes2;
    }

    public void setStrikes2(int strikes2) {
        this.strikes2 = strikes2;
    }
}



