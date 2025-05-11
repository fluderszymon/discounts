package com.szymonfluder.model;

import java.util.ArrayList;

public class Order{

    private String id;
    private double value;
    private ArrayList<String> promotions;

    public Order() {
    }

    public Order(String id, double value, ArrayList<String> promotions) {
        this.id = id;
        this.value = value;
        this.promotions = promotions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ArrayList<String> getPromotions() {
        return promotions;
    }

    public void setPromotions(ArrayList<String> promotions) {
        this.promotions = promotions;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", promotions=" + promotions +
                '}';
    }
}
