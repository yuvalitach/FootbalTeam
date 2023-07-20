package com.example.footbalteam;


public class Player {
    private int id;
    private String name;
    private int rate;
    private int team;
    private String position;

    public Player() {
    }

    public Player(int id, String name, int rate, int team, String position) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.team = team;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Player setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public int getRate() {
        return rate;
    }

    public Player setRate(int rate) {
        this.rate = rate;
        return this;
    }

    public int getTeam() {
        return team;
    }

    public Player setTeam(int team) {
        this.team = team;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public Player setPosition(String position) {
        this.position = position;
        return this;
    }

    @Override
    public String toString() {
        return
                "תז: " + id +
                ", שם: " + name + '\'' +
                ", דירוג: " + rate +
                ", תפקיד: " + position + '\'' +
                '}';
    }
}
