package com.epicburger.epicburgerapp;

public class Cheeseburger {
    private String name;
    private double cost;
    private int imageResourceId;

    public static final Cheeseburger[] cheeseburgers = {
        new Cheeseburger("Cheeseburger 1", 1, R.drawable.cheeseburger_1),
        new Cheeseburger("Cheeseburger 2", 2, R.drawable.cheeseburger_2),
        new Cheeseburger("Cheeseburger 3", 3, R.drawable.cheeseburger_3),
        new Cheeseburger("Cheeseburger 4", 4, R.drawable.cheeseburger_4),
    };

    private Cheeseburger(String name, double cost, int imageResourceId) {
        this.name = name;
        this.cost = cost;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
