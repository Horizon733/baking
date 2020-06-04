package com.example.baking.models;

import java.io.Serializable;

public class Ingredients implements Serializable {

    private double quantity;
    private String measure;
    private String ingredient;

    public Ingredients(double quantity, String measure, String ingredients) {
        this.ingredient = ingredients;
        this.quantity = quantity;
        this.measure = measure;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public String getMeasure() {
        return this.measure;
    }

    public String getIngredient() {
        return this.ingredient;
    }
}
