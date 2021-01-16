package com.epicburger.epicburgerapp;

public class Cheeseburger {
    private String name;
    private double cost;
    private int imageResourceId;

    public static final Cheeseburger[] cheeseburgers = {
        new Cheeseburger("Standard Cheeseburger", 1.20, R.drawable.cheeseburger_standard),
        new Cheeseburger("Double Cheeseburger", 1.50, R.drawable.cheeseburger_double),
        new Cheeseburger("Epic Cheeseburger", 2.10, R.drawable.cheeseburger_epic),
        new Cheeseburger("Junior Cheeseburger", 3.20, R.drawable.cheeseburger_junior),
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
