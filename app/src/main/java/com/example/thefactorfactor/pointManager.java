package com.example.thefactorfactor;

public class pointManager {
    static public int points;

    public pointManager() {
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void increment(int add){
        this.points+=add;
    }
}
