package com.example.myproject;


public class Game {

    String homeTeam;
    int scoreHomeTeam;
    String awayTeam;
    int scoreAwayTeam;
    int id;
    String date;

    public Game(){

    }

    public Game(int id, String date, String homeTeam, String awayTeam, int scoreHomeTeam, int scoreAwayTeam) {
        this.id = id;
        this.date = date;
        this.homeTeam = homeTeam;
        this.scoreHomeTeam = scoreHomeTeam;
        this.awayTeam = awayTeam;
        this.scoreAwayTeam = scoreAwayTeam;
    }

    public int getId() { return  id; }

    public String getDate() {
        return date;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeTeamScore() {
        return scoreHomeTeam;
    }

    public int getAwayTeamScore() {
        return scoreAwayTeam;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHomeTeam(String teamA) {
        this.homeTeam = teamA;
    }

    public void setAwayTeam(String teamB) {
        this.awayTeam = teamB;
    }

    public void setHomeTeamScore(int scoreTeamA) {
        this.scoreHomeTeam = scoreTeamA;
    }

    public void setAwayTeamScore(int scoreTeamB) {
        this.scoreAwayTeam = scoreTeamB;
    }
}