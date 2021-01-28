package com.epicburger.epicburgerapp;

import androidx.annotation.NonNull;

public class Order {
    private String name;
    private double cost;

    public Order(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public static final Order[] orders = {
            new Order("Order1", 10.5),
            new Order("Order2", 20.5),
            new Order("Order3", 30.5)};

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
